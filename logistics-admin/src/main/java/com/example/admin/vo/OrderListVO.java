package com.example.admin.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="用户订单表展示对象", description="用户订单表展示对象")
public class OrderListVO {

    @ApiModelProperty(value = "运单号")
    private Long orderId;

    @ApiModelProperty(value = "寄件人姓名")
    private String orderNameFrom;

    @ApiModelProperty(value = "寄件地址")
    private String orderAddressFrom;

    @ApiModelProperty(value = "收件人姓名")
    private String orderNameTo;

    @ApiModelProperty(value = "收件地址")
    private String orderAddressTo;

    @ApiModelProperty(value = "订单状态（1、待处理，2、进行中，3、已完成）")
    private Integer orderStatus;

    @ApiModelProperty(value = "运费")
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "用户备注")
    private String orderUserDetail;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Shanghai")//展示的日期格式化
    @ApiModelProperty(value = "签收时间")
    private Date orderTimeSigner;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Shanghai")//展示的日期格式化
    @ApiModelProperty(value = "下单时间")
    private Date insertTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Shanghai")//展示的日期格式化
    @ApiModelProperty(value = "寄出时间")
    private Date orderTimeSend;

}
