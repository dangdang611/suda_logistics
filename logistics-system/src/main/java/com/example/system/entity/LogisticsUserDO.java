package com.example.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Data
@TableName(value = "logistics_user")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="LogisticsUser对象", description="用户信息表")
public class LogisticsUserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户信息表唯一标识")
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    @ApiModelProperty(value = "用户账户（手机号）")
    private String userAccount;

    @ApiModelProperty(value = "用户密码")
    private String userPassword;

    @ApiModelProperty(value = "用户创建时间" , name = "insertTime")
    @TableField(fill = FieldFill.INSERT)//自动创建时候填充
    private Date insertTime;

    @ApiModelProperty(value = "逻辑删除字段")
    @TableLogic // 逻辑删除字段
    private Integer userDeleted;

}
