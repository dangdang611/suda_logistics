package com.example.system.entity;

import java.math.BigDecimal;
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
 * 包装服务表
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Data
@TableName(value = "logistics_package")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="LogisticsPackage对象", description="包装服务表")
public class LogisticsPackageDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "包装服务表唯一标识")
    @TableId(value = "package_id", type = IdType.ASSIGN_ID)
    private Long packageId;

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

    @ApiModelProperty(value = "逻辑删除字段")
    @TableLogic // 逻辑删除字段
    private Integer packageDeleted;

}
