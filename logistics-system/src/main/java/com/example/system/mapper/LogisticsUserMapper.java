package com.example.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.entity.LogisticsUserDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
public interface LogisticsUserMapper extends BaseMapper<LogisticsUserDO> {

    //查询是否存在该账号
    @Select("select user_id,user_deleted from logistics_user where user_account = #{account}")
    LogisticsUserDO exitsAccount(@Param("account") String account);

    //物理删除
    @Delete("delete from logistics_user where user_id = #{userId}")
    int deleteByIdForever(@Param("userId") Long userId);

    //查找插入日期
    @Select("select insert_time from logistics_user")
    List<Date> selectInsertTimeList();
}
