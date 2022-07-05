package com.example.system.controller;


import com.example.system.componet.Constant;
import com.example.system.entity.InformationJobDO;
import com.example.system.exception.OperationException;
import com.example.system.service.InformationJobService;
import com.example.system.vo.*;
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
 * 招聘资讯表 前端控制器
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Api(tags = "招聘资讯表交互")
@RestController
@RequestMapping("/system/jobInformation")
public class InformationJobController {

    @Autowired
    private InformationJobService informationJobService;

    @ApiOperation("查询招聘资讯总数")
    @GetMapping("/count")
    public Result countInformationJob(){
        return Result.success(informationJobService.count());
    }

    @ApiOperation("搜索招聘岗位")
    @GetMapping("/search")
    public Result searchInformationNews(@ApiParam("搜索需要的招聘岗位名")
                                        @RequestParam(value = "jobName" , required = false)
                                                String jobName){
        List<InformationJobDO> informationJobDOS ;
        if (jobName == null)informationJobDOS = informationJobService.selectInformationJobDOLikeJobName("");//传进来空参数就查询全部
        else informationJobDOS = informationJobService.selectInformationJobDOLikeJobName(jobName);

        //DO转换VO
        List<InformationJobListVO> informationJobListVOS = new ArrayList<>();
        for (InformationJobDO informationJobDO : informationJobDOS) {
            InformationJobListVO informationNewsListVO = new InformationJobListVO();
            BeanUtils.copyProperties(informationJobDO,informationNewsListVO);
            informationJobListVOS.add(informationNewsListVO);
        }

        return  Result.success(informationJobListVOS).message("搜索招聘岗位成功");
    }

    @ApiOperation("插入招聘资讯接口")
    @PostMapping("/insert")
    public Result insertInformationJob(@ApiParam("需要插入的招聘资讯信息") @RequestBody InformationJobAddVO informationJobAddVO){
        InformationJobDO informationJobDO = new InformationJobDO();
        BeanUtils.copyProperties(informationJobAddVO,informationJobDO);
        int row = informationJobService.insertInformationJob(informationJobDO);
        if(row < 1)return Result.failure("插入招聘资讯失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("插入招聘资讯成功");
    }

    @ApiOperation("删除招聘资讯接口")
    @DeleteMapping("/delete")
    public Result deleteInformationJob(@ApiParam("需要删除的招聘资讯id")@RequestParam(value = "jobId" ,
            required = false) Long jobId , @ApiParam("需要删除的招聘资讯id集合") @RequestParam(value = "jobIds" ,
            required = false) List<Long> jobIds){//required 的意思是传进来没有这个参数也不报错
        if(jobId == null && jobIds == null)Result.failure("传入删除招聘资讯id错误").code(Constant.FAILURE_HTML_CODE);
        boolean flag = false;//删除是否成功
        if(jobId == null)informationJobService.removeByIds(jobIds);
        else flag = informationJobService.removeById(jobId);
        if(!flag)return Result.failure("删除招聘资讯失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("删除招聘资讯成功");
    }

    @ApiOperation("更改招聘资讯接口")
    @PutMapping("/update")
    public Result updateInformationJob(@ApiParam("需要更改的招聘资讯信息") @RequestBody InformationJobUpdateVO informationJobUpdateVO){
        InformationJobDO informationJobDO = new InformationJobDO();
        BeanUtils.copyProperties(informationJobUpdateVO,informationJobDO);
        int row = informationJobService.updateInformationJob(informationJobDO);
        if(row < 1)return Result.failure("更改招聘资讯失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("更改招聘资讯成功");
    }

    @ApiOperation("展示招聘资讯接口")
    @GetMapping("/listInformationJobs")
    public Result listInformationJobs(@ApiParam("当前页码")
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

        List<InformationJobDO> informationJobDOS = informationJobService.selectInformationJobItems(page, size);

        //DO转换VO
        List<InformationJobListVO> informationJobListVOS = new ArrayList<>();
        for ( InformationJobDO informationJobDO: informationJobDOS) {
            InformationJobListVO informationJobListVO = new InformationJobListVO();
            BeanUtils.copyProperties(informationJobDO,informationJobListVO);
            informationJobListVOS.add(informationJobListVO);
        }

        return  Result.success(informationJobListVOS).message("获取招聘资讯数据成功");

    }

}

