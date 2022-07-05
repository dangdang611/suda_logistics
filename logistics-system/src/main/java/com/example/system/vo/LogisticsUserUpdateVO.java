package com.example.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="用户修改对象", description="用户修改对象")
public class LogisticsUserUpdateVO {

    @ApiModelProperty(value = "用户信息表唯一标识")
    private Long userId;

    @ApiModelProperty(value = "用户账户（手机号）")
    private String userAccount;

    @ApiModelProperty(value = "用户密码")
    private String userPassword;

}
