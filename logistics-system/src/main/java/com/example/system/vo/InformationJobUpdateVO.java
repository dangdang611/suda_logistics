package com.example.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="招聘资讯表修改对象", description="招聘资讯表修改对象")
public class InformationJobUpdateVO {

    @ApiModelProperty(value = "招聘资讯表唯一标识")
    private Long jobId;

    @ApiModelProperty(value = "岗位名")
    private String jobName;

    @ApiModelProperty(value = "岗位要求")
    private String jobRequire;

    @ApiModelProperty(value = "岗位要求的html格式")
    private String jobRequireFormat;

    @ApiModelProperty(value = "工作地点")
    private String jobLocation;

    @ApiModelProperty(value = "学历要求")
    private String jobRequireEducation;

    @ApiModelProperty(value = "要求的工作年限")
    private String jobRequireYearNum;

    @ApiModelProperty(value = "薪资范围")
    private String jobSalary;

    @ApiModelProperty(value = "职位类型")
    private String jobType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")//接收的日期格式化
    @ApiModelProperty(value = "截止日期")
    private Date jobDiedDate;

    @ApiModelProperty(value = "需要人数")
    private Integer jobPeopleCount;

    @ApiModelProperty(value = "工作职责")
    private String jobDuty;

    @ApiModelProperty(value = "工作职责的html格式")
    private String jobDutyFormat;

    @ApiModelProperty(value = "联系方式")
    private String jobContact;

}
