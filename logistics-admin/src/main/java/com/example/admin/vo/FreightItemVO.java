package com.example.admin.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="运费时效获取对象", description="运费时效获取对象")
public class FreightItemVO {

    @ApiModelProperty(value = "快递类型")
    private Integer type;

    @ApiModelProperty(value = "始发地")
    private String locationFrom;

    @ApiModelProperty(value = "始发地详细地址")
    private String locationFromDetail;

    @ApiModelProperty(value = "目的地")
    private String locationTo;

    @ApiModelProperty(value = "目的地详细地址")
    private String locationToDetail;

    @ApiModelProperty(value = "重量")
    private BigDecimal weight;

    @ApiModelProperty(value = "体积")
    private BigDecimal volume;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @ApiModelProperty(value = "寄件时间")
    private Date sendTime;


}
