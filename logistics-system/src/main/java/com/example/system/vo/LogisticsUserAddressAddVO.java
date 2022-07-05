package com.example.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="地址簿添加对象", description="地址簿添加对象")
public class LogisticsUserAddressAddVO {

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

}
