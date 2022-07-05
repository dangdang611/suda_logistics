package com.example.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="新闻资讯具体对象", description="新闻资讯具体对象")
public class NewsItemVO {

    @ApiModelProperty(value = "新闻资讯表唯一标识")
    private Long newsId;

    @ApiModelProperty(value = "新闻标题")
    private String newsTitle;

    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "Asia/Shanghai")//展示的日期格式化
    @ApiModelProperty(value = "发布日期")
    private Date insertTime;

    @ApiModelProperty(value = "发布者")
    private String newsAuthor;

    @ApiModelProperty(value = "发布内容")
    private String newsContext;

    @ApiModelProperty(value = "发布内容的html格式")
    private String newsContextFormat;

}
