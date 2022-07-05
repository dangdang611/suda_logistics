package com.example.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="快递产品添加对象", description="快递产品添加对象")
public class LogisticsExpressAddVO {

    @ApiModelProperty(value = "产品名")
    private String expressProduct;

    @ApiModelProperty(value = "省外计费规则")
    private String expressAccountingRuleOut;

    @ApiModelProperty(value = "省内计费规则")
    private String expressAccountingRuleIn;

}
