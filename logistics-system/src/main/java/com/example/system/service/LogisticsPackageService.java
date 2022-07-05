package com.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.entity.LogisticsPackageDO;
import com.example.system.entity.LogisticsUserDO;

import java.util.List;

/**
 * <p>
 * 包装服务表 服务类
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
public interface LogisticsPackageService extends IService<LogisticsPackageDO> {

    int insertPackage(LogisticsPackageDO packageDO);

    int updatePackage(LogisticsPackageDO packageDO);

    List<LogisticsPackageDO> selectPackageItems(Integer page, Integer size);

    boolean exitsPackageName(String packageName);

    List<LogisticsPackageDO> selectUserLikePackageName(String packageName);

}
