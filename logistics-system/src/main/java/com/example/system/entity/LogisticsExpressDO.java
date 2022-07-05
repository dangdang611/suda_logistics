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
 * 快递产品服务表
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Data
@TableName(value = "logistics_express")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="LogisticsExpress对象", description="快递产品服务表")
public class LogisticsExpressDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "快递产品表唯一标识")
    @TableId(value = "express_id", type = IdType.ASSIGN_ID)
    private Long expressId;

    @ApiModelProperty(value = "产品名")
    private String expressProduct;

    @ApiModelProperty(value = "省外计费规则")
    private String expressAccountingRuleOut;

    @ApiModelProperty(value = "省内计费规则")
    private String expressAccountingRuleIn;

    @ApiModelProperty(value = "逻辑删除字段")
    @TableLogic // 逻辑删除字段
    private Integer expressDeleted;

}
