package com.example.admin.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="快递路线", description="快递路线")
public class PathItemVO {

    @ApiModelProperty(value = "经过的城市")
    private String pathLocationReach;

    @ApiModelProperty(value = "此城市是第几个到达的")
    private Integer pathLocationReachOrder;

    @ApiModelProperty(value = "经度")
    private BigDecimal pathLocationLatitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal pathLocationLongitude;

}
