package com.example.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="后端主页展示对象" , description = "后端主页展示对象")
public class SystemIndexListVO {

    @ApiModelProperty(value = "近8日用户增长的数量")
    private List<Integer> userAddNums;

    @ApiModelProperty(value = "近日用户订单增长的数量")
    private List<Integer> userOrderAddNums;

    @ApiModelProperty(value = "近日用户流量量")
    private List<Integer> pageViewAddNums;

    @ApiModelProperty(value = "总用户数量")
    private Integer userNums;

    @ApiModelProperty(value = "总用户订单数量")
    private Integer userOrderNums;

    @ApiModelProperty(value = "总浏览量")
    private Integer pageViews;

}
