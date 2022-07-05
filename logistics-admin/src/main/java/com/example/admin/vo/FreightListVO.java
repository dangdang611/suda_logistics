package com.example.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="运费时效展示对象", description="运费时效展示对象")
public class FreightListVO {

    @ApiModelProperty(value = "快递产品名")
    private String productName;

    @ApiModelProperty(value = "体积重量")
    private BigDecimal weight;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Shanghai")//展示的日期格式化
    @ApiModelProperty(value = "预计时效")
    private Date sendTime;

    @ApiModelProperty(value = "寄付价")
    private BigDecimal price;

    @ApiModelProperty(value = "计费规则")
    private String priceRule;




}
