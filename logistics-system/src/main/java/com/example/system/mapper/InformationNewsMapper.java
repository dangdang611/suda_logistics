package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.entity.InformationNewsDO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 新闻资讯表 Mapper 接口
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
public interface InformationNewsMapper extends BaseMapper<InformationNewsDO> {

    //查询所有id值
    @Select("select news_id from information_news where news_deleted = 0 order by insert_time desc")
    List<Long> selectListId();
}
