package com.example.system.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="快递产品修改对象", description="快递产品修改对象")
public class LogisticsExpressUpdateVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "快递产品表唯一标识")
    private Long expressId;

    @ApiModelProperty(value = "产品名")
    private String expressProduct;

    @ApiModelProperty(value = "省外计费规则")
    private String expressAccountingRuleOut;

    @ApiModelProperty(value = "省内计费规则")
    private String expressAccountingRuleIn;

}
