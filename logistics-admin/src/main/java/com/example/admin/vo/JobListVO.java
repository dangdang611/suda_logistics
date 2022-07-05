package com.example.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="招聘资讯表多条展示对象", description="招聘资讯表多条展示对象")
public class JobListVO {

    @ApiModelProperty(value = "招聘资讯表唯一标识")
    private Long jobId;

    @ApiModelProperty(value = "岗位名")
    private String jobName;

    @ApiModelProperty(value = "工作地点")
    private String jobLocation;

    @ApiModelProperty(value = "学历要求")
    private String jobRequireEducation;

    @ApiModelProperty(value = "要求的工作年限")
    private String jobRequireYearNum;

    @ApiModelProperty(value = "薪资范围")
    private String jobSalary;

}
