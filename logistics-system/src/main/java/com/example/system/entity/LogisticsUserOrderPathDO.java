package com.example.system.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 快递路线表
 * </p>
 *
 * @author YY
 * @since 2022-01-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "logistics_user_order_path")
@ApiModel(value="LogisticsUserOrderPath对象", description="快递路线表")
public class LogisticsUserOrderPathDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "路线id")
    @TableId(value = "path_id", type = IdType.ASSIGN_ID)
    private Long pathId;

    @ApiModelProperty(value = "经过的城市")
    private String pathLocationReach;

    @ApiModelProperty(value = "此城市是第几个到达的")
    private Integer pathLocationReachOrder;

    @ApiModelProperty(value = "经度")
    private BigDecimal pathLocationLatitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal pathLocationLongitude;

    @ApiModelProperty(value = "属于哪个订单的路线")
    private Long orderId;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer pathDeleted;


}
