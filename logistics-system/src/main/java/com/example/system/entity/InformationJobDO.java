package com.example.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 招聘资讯表
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Data
@TableName(value = "information_job")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="InformationJob对象", description="招聘资讯表")
public class InformationJobDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "招聘资讯表唯一标识")
    @TableId(value = "job_id", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "发布日期")
    @TableField(fill = FieldFill.INSERT)//自动创建时候填充
    private Date insertTime;

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

    @ApiModelProperty(value = "逻辑删除字段")
    @TableLogic // 逻辑删除字段
    private Integer jobDeleted;

}
