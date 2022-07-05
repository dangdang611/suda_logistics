package com.example.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="包装服务添加对象", description="包装服务添加对象")
public class LogisticsPackageAddVO {

    @ApiModelProperty(value = "包装服务名")
    private String packageName;

    @ApiModelProperty(value = "包装尺寸详情")
    private String packageSize;

    @ApiModelProperty(value = "重量")
    private BigDecimal packageWeight;

    @ApiModelProperty(value = "体积")
    private BigDecimal packageVolume;

    @ApiModelProperty(value = "价格")
    private BigDecimal packagePrice;

}
