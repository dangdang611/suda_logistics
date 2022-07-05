package com.example.admin.controller;


import com.example.admin.vo.JobItemVO;
import com.example.admin.vo.JobListVO;
import com.example.system.entity.InformationJobDO;
import com.example.system.service.InformationJobService;
import com.example.system.vo.unify.MyGeneral;
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
@Api(tags = "人才招聘")
@RestController
@RequestMapping("/jobInformation")
public class JobController {

    @Autowired
    private InformationJobService jobService;

    @ApiOperation("搜索招聘岗位")
    @GetMapping("/search")
    public Result searchInformationNews(@ApiParam("搜索需要的字段")
                                            @RequestParam(value = "fieldList" , required = false) List<String> fieldList ,
                                        @ApiParam("搜索需要的值")
                                            @RequestParam(value = "contextList" , required = false) List<String> contextList ,
                                        @ApiParam("当前页码")
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

        MyGeneral<InformationJobDO> myPage = jobService.selectInformationJobDOLikeFieldDescTime(fieldList,contextList,page,size);

        List<InformationJobDO> informationJobDOS = myPage.getData();

        List<JobListVO> jobListVOS = new ArrayList<>();
        for (InformationJobDO informationJobDO : informationJobDOS) {
            JobListVO jobListVO = new JobListVO();
            BeanUtils.copyProperties(informationJobDO,jobListVO);
            jobListVOS.add(jobListVO);
        }

        return  Result.success(new MyGeneral<JobListVO>().data(jobListVOS).count(myPage.getCount())).message("搜索招聘岗位成功");
    }

    @ApiOperation("展示新闻一条招聘资讯")
    @GetMapping("/item")
    public Result itemNews(@ApiParam("需要展示招聘资讯id")
                           @RequestParam(value = "jobId", required = false) Long jobId){
        InformationJobDO informationJobDO = jobService.getById(jobId);
        JobItemVO jobItemVO = new JobItemVO();
        BeanUtils.copyProperties(informationJobDO,jobItemVO);

        return Result.success(jobItemVO);
    }

}

