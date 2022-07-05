package com.example.system.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户订单表
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Data
@TableName(value = "logistics_user_order")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="LogisticsUserOrder对象", description="用户订单表")
public class LogisticsUserOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户订单唯一标识（运单号）")
    @TableId(value = "order_id", type = IdType.ASSIGN_ID)
    private Long orderId;

    @ApiModelProperty(value = "寄件人姓名")
    private String orderNameFrom;

    @ApiModelProperty(value = "寄件人电话")
    private String orderPhoneFrom;

    @ApiModelProperty(value = "寄件人公司名称")
    private String orderCompanyFrom;

    @ApiModelProperty(value = "寄件人固定电话")
    private String orderFixedPhoneFrom;

    @ApiModelProperty(value = "收件人姓名")
    private String orderNameTo;

    @ApiModelProperty(value = "收件人电话")
    private String orderPhoneTo;

    @ApiModelProperty(value = "收件人公司名称")
    private String orderCompanyTo;

    @ApiModelProperty(value = "收件人固定电话")
    private String orderFixedPhoneTo;

    @ApiModelProperty(value = "寄件地址")
    private String orderAddressFrom;

    @ApiModelProperty(value = "寄件地址的详细地址")
    private String orderAddressFromDetail;

    @ApiModelProperty(value = "收件地址")
    private String orderAddressTo;

    @ApiModelProperty(value = "收件地址的详细地址")
    private String orderAddressToDetail;

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

    @ApiModelProperty(value = "下单时间")
    @TableField(fill = FieldFill.INSERT)//自动创建时候填充
    private Date insertTime;

    @ApiModelProperty(value = "寄出时间")
    private Date orderTimeSend;

    @ApiModelProperty(value = "签收时间")
    private Date orderTimeSigner;

    @ApiModelProperty(value = "属于哪个用户")
    private String userAccount;

    @ApiModelProperty(value = "用户备注")
    private String orderUserDetail;

    @ApiModelProperty(value = "支付方式（1、到付，2、现付）")
    private Integer orderPayment;

    @ApiModelProperty(value = "该订单已经经过几个城市了")
    private Integer orderLocationReachNum;

    @ApiModelProperty(value = "逻辑删除字段")
    @TableLogic // 逻辑删除字段
    private Integer orderDeleted;

}
