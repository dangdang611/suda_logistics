package com.example.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="用户展示对象", description="用户展示对象")
public class LogisticsUserListVO {

    @ApiModelProperty(value = "用户信息表唯一标识")
    private Long userId;

    @ApiModelProperty(value = "用户账户（手机号）")
    private String userAccount;

    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "Asia/Shanghai")//展示的日期格式化
    @ApiModelProperty(value = "用户创建时间")
    private Date insertTime;

}
