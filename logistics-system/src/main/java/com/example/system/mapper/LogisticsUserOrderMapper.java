package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.entity.LogisticsUserOrderDO;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户订单表 Mapper 接口
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
public interface LogisticsUserOrderMapper extends BaseMapper<LogisticsUserOrderDO> {

    //查找插入日期
    @Select("select insert_time from logistics_user_order")
    List<Date> selectInsertTimeList();
}
