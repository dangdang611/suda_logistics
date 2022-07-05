package com.example.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户地址簿
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Data
@TableName(value = "logistics_user_address")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="LogisticsUserAddress对象", description="用户地址簿")
public class LogisticsUserAddressDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "地址簿唯一标识")
    @TableId(value = "address_id", type = IdType.ASSIGN_ID)
    private Long addressId;

    @ApiModelProperty(value = "改地址绑定的用户名")
    private String addressName;

    @ApiModelProperty(value = "该地址绑定的手机号")
    private String addressPhone;

    @ApiModelProperty(value = "该地址所在地区（省市区）")
    private String addressLocation;

    @ApiModelProperty(value = "该地址所在地区的详细地址（某某镇多少号）")
    private String addressLocationDetail;

    @ApiModelProperty(value = "公司名")
    private String addressCompany;

    @ApiModelProperty(value = "固定手机号")
    private String addressPhoneFixed;

    @ApiModelProperty(value = "该地址簿属于哪个用户")
    private String userAccount;

    @ApiModelProperty(value = "逻辑删除字段")
    @TableLogic // 逻辑删除字段
    private Integer addressDeleted;

}
