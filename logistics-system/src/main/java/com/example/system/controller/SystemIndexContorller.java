package com.example.system.controller;

import com.example.system.componet.RedisOperator;
import com.example.system.exception.OperationException;
import com.example.system.service.LogisticsUserOrderService;
import com.example.system.service.LogisticsUserService;
import com.example.system.vo.SystemIndexListVO;
import com.example.system.vo.unify.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = "主页交互")
@RestController
@RequestMapping("/system/index")
public class SystemIndexContorller {

    @Autowired
    private LogisticsUserService userService;

    @Autowired
    private LogisticsUserOrderService orderService;

    @Autowired
    private RedisOperator redisOperator;

    public static Integer pageView = 0;//今日浏览量

    public static Integer totalView = 0;//总计量

    public static Date today = null;//今天的日期

    @ApiOperation("主页数据展示")
    @GetMapping("/getData")
    public Result countUser(){

        if (pageView == 0 || totalView == 0 || today == null){//可能项目重启了
            pageView = Integer.valueOf(redisOperator.get("today_page_view"));
            totalView = Integer.valueOf(redisOperator.get("total_page_view"));
            today = new Date(Long.valueOf(redisOperator.get("today")));//取出今天的日期
        }

        List<Integer> userNums = userService.getSevenUserAddNum();//查询近七天的用户新增数量

        List<Integer> userOrderNums = orderService.getSevenUserOrderAddNum();//查询近七天的用户订单新增数量

        List<Integer> pageViewList = redisOperator.range("page_view_list", 0, 6);//查询近七天的浏览量

        pageViewList.add(pageView);

        return Result.success(new SystemIndexListVO(userNums,userOrderNums,pageViewList,userService.count()
                                ,orderService.count(),totalView));

    }

    @ApiOperation("页面浏览量 +1")
    @GetMapping("/pageViews")
    public Result pageViews(){

        if (pageView == 0 || totalView == 0 || today == null){//可能项目重启了
            pageView = Integer.valueOf(redisOperator.get("today_page_view"));
            totalView = Integer.valueOf(redisOperator.get("total_page_view"));
            today = new Date(Long.valueOf(redisOperator.get("today")));//取出今天的日期
        }

        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;//今天
        try {
            date = new Date(dayFormat.parse(dayFormat.format(new Date())).getTime());
        } catch (ParseException e) {
            throw new OperationException(e.getMessage());
        }

        if (date.getTime() != today.getTime()){//新的一天了
            redisOperator.set("today",String.valueOf(date.getTime()));//更新今天日期
            today = date;
            redisOperator.lpop("page_view_list");
            redisOperator.rpush("page_view_list",pageView);//保存昨天的浏览量
            redisOperator.set("total_page_view",totalView);//保存现在的总浏览量
            redisOperator.set("today_page_view",0);//将今天的浏览量重置
            pageView = 0;
        }
        pageView ++;//浏览量加一次
        totalView ++;//总浏览量加一次

        return Result.success("浏览量增加成功");
    }


}
