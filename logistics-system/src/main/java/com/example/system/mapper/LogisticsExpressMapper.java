package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.entity.LogisticsExpressDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 快递产品服务表 Mapper 接口
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
public interface LogisticsExpressMapper extends BaseMapper<LogisticsExpressDO> {

    //查询是否存在该产品
    @Select("select express_id,express_deleted from logistics_express where express_product = #{product}")
    LogisticsExpressDO exitsProduct(String product);

    //物理删除
    @Delete("delete from logistics_express where express_id = #{expressId}")
    int deleteByIdForever(@Param("expressId") Long expressId);
}
