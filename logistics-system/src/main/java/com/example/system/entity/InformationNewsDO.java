package com.example.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 新闻资讯表
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Data
@TableName(value = "information_news")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="InformationNews对象", description="新闻资讯表")
public class InformationNewsDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "新闻资讯表唯一标识")
    @TableId(value = "news_id", type = IdType.ASSIGN_ID)
    private Long newsId;

    @ApiModelProperty(value = "新闻标题")
    private String newsTitle;

    @ApiModelProperty(value = "发布日期")
    @TableField(fill = FieldFill.INSERT)//自动创建时候填充
    private Date insertTime;

    @ApiModelProperty(value = "发布者")
    private String newsAuthor;

    @ApiModelProperty(value = "发布内容")
    private String newsContext;

    @ApiModelProperty(value = "发布内容的html格式")
    private String newsContextFormat;

    @ApiModelProperty(value = "逻辑删除字段")
    @TableLogic // 逻辑删除字段
    private Integer newsDeleted;

}
