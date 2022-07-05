package com.example.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="前台新闻资讯展示多条对象", description="前台新闻资讯展示多条对象")
public class NewsListVO {

    @ApiModelProperty(value = "新闻资讯表唯一标识")
    private Long newsId;

    @ApiModelProperty(value = "新闻标题")
    private String newsTitle;

    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "Asia/Shanghai")//展示的日期格式化
    @ApiModelProperty(value = "发布日期")
    private Date insertTime;

}
