package com.example.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="用户订单表添加对象", description="用户订单表添加对象")
public class OrderItemVO {

    @ApiModelProperty(value = "寄件人姓名")
    private String orderNameFrom;

    @ApiModelProperty(value = "寄件人电话")
    private String orderPhoneFrom;

    @ApiModelProperty(value = "寄件人公司名称")
    private String orderCompanyFrom;

    @ApiModelProperty(value = "寄件人固定电话")
    private String orderFixedPhoneFrom;

    @ApiModelProperty(value = "寄件地址")
    private String orderAddressFrom;

    @ApiModelProperty(value = "寄件地址的详细地址")
    private String orderAddressFromDetail;

    @ApiModelProperty("寄件地址经度")
    private BigDecimal FromLongitude;

    @ApiModelProperty("寄件地址纬度")
    private BigDecimal FromLatitude;

    @ApiModelProperty(value = "收件人姓名")
    private String orderNameTo;

    @ApiModelProperty(value = "收件人电话")
    private String orderPhoneTo;

    @ApiModelProperty(value = "收件人公司名称")
    private String orderCompanyTo;

    @ApiModelProperty(value = "收件人固定电话")
    private String orderFixedPhoneTo;

    @ApiModelProperty(value = "收件地址")
    private String orderAddressTo;

    @ApiModelProperty(value = "收件地址的详细地址")
    private String orderAddressToDetail;

    @ApiModelProperty("收件地址经度")
    private BigDecimal toLongitude;

    @ApiModelProperty("收件地址纬度")
    private BigDecimal toLatitude;

    @ApiModelProperty(value = "寄送物品重量")
    private BigDecimal orderWeight;

    @ApiModelProperty(value = "快递服务名")
    private String expressProduct;

    @ApiModelProperty(value = "寄送物品类型")
    private String orderItemType;

    @ApiModelProperty(value = "订单状态（1、待处理，2、进行中，3、已完成）")
    private Integer orderStatus;

    @ApiModelProperty(value = "运费")
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "属于哪个用户")
    private String userAccount;

    @ApiModelProperty(value = "用户备注")
    private String orderUserDetail;

    @ApiModelProperty(value = "支付方式（1、到付，2、现付）")
    private Integer orderPayment;

}
