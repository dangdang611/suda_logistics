package com.example.system.vo.unify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("自定义通用类")
public class MyGeneral<T> {

    @ApiModelProperty("和数据相关的数量")
    private Long count;

    @ApiModelProperty("响应数据")
    private List<T> data;

    //链式编程
    public MyGeneral<T> count(Long count) {
        this.count = count;
        return this;
    }

    public MyGeneral<T> data(List<T> data) {
        this.data = data;
        return this;
    }

}
