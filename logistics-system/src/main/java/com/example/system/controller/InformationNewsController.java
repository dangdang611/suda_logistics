package com.example.system.controller;


import com.example.system.componet.Constant;
import com.example.system.entity.InformationNewsDO;
import com.example.system.entity.LogisticsExpressDO;
import com.example.system.exception.OperationException;
import com.example.system.service.InformationNewsService;
import com.example.system.vo.InformationNewsAddVO;
import com.example.system.vo.InformationNewsListVO;
import com.example.system.vo.InformationNewsUpdateVO;
import com.example.system.vo.LogisticsExpressListVO;
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
@Api(tags = "新闻资讯表交互")
@RestController
@RequestMapping("/system/newsInformation")
public class InformationNewsController {

    //新闻资讯表id字段
    private final String INFORMATION_NEWS_ID = "news_id";

    @Autowired
    private InformationNewsService informationNewsService;

    @ApiOperation("查询新闻资讯总数")
    @GetMapping("/count")
    public Result countInformationNews(){
        return Result.success(informationNewsService.count());
    }

    @ApiOperation("搜索新闻资讯")
    @GetMapping("/search")
    public Result searchInformationNews(@ApiParam("搜索需要的新闻资讯标题")
                                            @RequestParam(value = "newsTitle" , required = false)
                                        String newsTitle){
        List<InformationNewsDO> informationNewsDOS ;
        if (newsTitle == null)informationNewsDOS = informationNewsService.selectInformationNewsLikeNewsTitle("");//传进来空参数就查询全部
        else informationNewsDOS = informationNewsService.selectInformationNewsLikeNewsTitle(newsTitle);

        //DO转换VO
        List<InformationNewsListVO> informationNewsListVOS = new ArrayList<>();
        for (InformationNewsDO informationNewsDO : informationNewsDOS) {
            InformationNewsListVO informationNewsListVO = new InformationNewsListVO();
            BeanUtils.copyProperties(informationNewsDO,informationNewsListVO);
            informationNewsListVOS.add(informationNewsListVO);
        }

        return  Result.success(informationNewsListVOS).message("搜索新闻资讯数据成功");
    }

    @ApiOperation("插入新闻资讯接口")
    @PostMapping("/insert")
    public Result insertInformationNews(@ApiParam("需要插入的新闻资讯信息") @RequestBody InformationNewsAddVO informationNewsAddVO){
        InformationNewsDO informationNewsDO = new InformationNewsDO();
        BeanUtils.copyProperties(informationNewsAddVO,informationNewsDO);
        int row = informationNewsService.insertInformationNews(informationNewsDO);
        if(row < 1)return Result.failure("插入新闻资讯失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("插入新闻资讯成功");
    }

    @ApiOperation("删除新闻资讯接口")
    @DeleteMapping("/delete")
    public Result deleteInformationNews(@ApiParam("需要删除的新闻资讯id")@RequestParam(value = "newsId" ,
            required = false) Long newsId , @ApiParam("需要删除的新闻资讯id集合") @RequestParam(value = "newsIds" ,
            required = false) List<Long> newsIds){//required 的意思是传进来没有这个参数也不报错
        if(newsId == null && newsIds == null)Result.failure("传入删除新闻资讯id错误").code(Constant.FAILURE_HTML_CODE);
        boolean flag = false;//删除是否成功
        if(newsId == null)informationNewsService.removeByIds(newsIds);
        else flag = informationNewsService.removeById(newsId);
        if(!flag)return Result.failure("删除新闻资讯失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("删除新闻资讯成功");
    }

    @ApiOperation("更改新闻资讯接口")
    @PutMapping("/update")
    public Result updateInformationNews(@ApiParam("需要更改的新闻资讯信息") @RequestBody InformationNewsUpdateVO informationNewsUpdateVO){
        InformationNewsDO informationNewsDO = new InformationNewsDO();
        BeanUtils.copyProperties(informationNewsUpdateVO,informationNewsDO);
        int row = informationNewsService.updateInformationNews(informationNewsDO);
        if(row < 1)return Result.failure("更改新闻资讯失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("更改新闻资讯成功");
    }

    @ApiOperation("展示新闻资讯接口")
    @GetMapping("/listInformationNews")
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

        List<InformationNewsDO> informationNewsDOS = informationNewsService.selectInformationNewsItemsDescField(page, size , INFORMATION_NEWS_ID);

        //DO转换VO
        List<InformationNewsListVO> informationNewsListVOS = new ArrayList<>();
        for ( InformationNewsDO informationNewsDO: informationNewsDOS) {
            InformationNewsListVO informationNewsListVO = new InformationNewsListVO();
            BeanUtils.copyProperties(informationNewsDO,informationNewsListVO);
            informationNewsListVOS.add(informationNewsListVO);
        }

        return  Result.success(informationNewsListVOS).message("获取新闻资讯数据成功");

    }

}

