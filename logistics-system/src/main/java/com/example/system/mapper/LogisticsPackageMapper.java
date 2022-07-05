package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.entity.LogisticsPackageDO;
import com.example.system.entity.LogisticsUserDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 包装服务表 Mapper 接口
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
public interface LogisticsPackageMapper extends BaseMapper<LogisticsPackageDO> {

    //查询是否存在该包装服务名
    @Select("select package_id,package_deleted from logistics_package where package_name = #{packageName}")
    LogisticsPackageDO exitsPackage(@Param("packageName") String packageName);

    //物理删除
    @Delete("delete from logistics_package where package_id = #{packageId}")
    int deleteByIdForever(@Param("packageId") Long packageId);

}
