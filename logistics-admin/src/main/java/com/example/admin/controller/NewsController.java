package com.example.admin.controller;


import com.example.admin.vo.NewsItemVO;
import com.example.admin.vo.NewsListVO;
import com.example.system.componet.Constant;
import com.example.system.entity.InformationNewsDO;
import com.example.system.exception.OperationException;
import com.example.system.service.InformationNewsService;
import com.example.system.vo.unify.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 新闻资讯表 前端控制器
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Api(tags = "新闻中心")
@RestController
@RequestMapping("/newsInformation")
public class NewsController {

    //新闻资讯表插入时间字段
    private String INFORMATION_NEWS_INSERT_TIME = "insert_time";

    @Autowired
    private InformationNewsService newsService;

    @ApiOperation("查询新闻资讯总数")
    @GetMapping("/count")
    public Result countInformationNews(){
        return Result.success(newsService.count());
    }

    @ApiOperation("展示新闻一条新闻资讯")
    @GetMapping("/item")
    public Result itemNews(@ApiParam("当前新闻资讯id")
                               @RequestParam(value = "newsId", required = false) Long newsId){

        InformationNewsDO informationNewsDO = newsService.getById(newsId);
        NewsItemVO newsItemVO = new NewsItemVO();
        BeanUtils.copyProperties(informationNewsDO,newsItemVO);

        return Result.success(newsItemVO);
    }

    @ApiOperation("展示新闻上一条新闻资讯")
    @GetMapping("/upItem")
    public Result upItemNews(@ApiParam("当前新闻资讯id")
                           @RequestParam(value = "newsId", required = false) Long newsId){

        InformationNewsDO informationNewsDO = newsService.selectUpById(newsId);
        if (informationNewsDO == null)Result.failure("别点了新闻资讯已经到顶了").code(Constant.FAILURE_HTML_CODE);
        NewsItemVO newsItemVO = new NewsItemVO();
        BeanUtils.copyProperties(informationNewsDO,newsItemVO);

        return Result.success(newsItemVO);
    }

    @ApiOperation("展示新闻下一条新闻资讯")
    @GetMapping("/downItem")
    public Result downItemNews(@ApiParam("当前新闻资讯id")
                           @RequestParam(value = "newsId", required = false) Long newsId){

        InformationNewsDO informationNewsDO = newsService.selectDownById(newsId);
        if (informationNewsDO == null)Result.failure("别点了新闻资讯已经到顶了").code(Constant.FAILURE_HTML_CODE);
        NewsItemVO newsItemVO = new NewsItemVO();
        BeanUtils.copyProperties(informationNewsDO,newsItemVO);

        return Result.success(newsItemVO);
    }

    @ApiOperation("展示新闻资讯接口")
    @GetMapping("/listNews")
    public Result listInformationNews(@ApiParam("当前页码")
                            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                            @ApiParam("每页大小")
                            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size){
        // 参数校验
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = 5;
        }

        List<InformationNewsDO> informationNewsDOS = newsService.selectInformationNewsItemsDescField(page, size,INFORMATION_NEWS_INSERT_TIME);

        //DO转换VO
        List<NewsListVO> informationNewsListVOS = new ArrayList<>();
        for ( InformationNewsDO informationNewsDO: informationNewsDOS) {
            NewsListVO informationNewsListVO = new NewsListVO();
            BeanUtils.copyProperties(informationNewsDO,informationNewsListVO);
            informationNewsListVOS.add(informationNewsListVO);
        }

        return  Result.success(informationNewsListVOS).message("获取新闻资讯数据成功");

    }

}

