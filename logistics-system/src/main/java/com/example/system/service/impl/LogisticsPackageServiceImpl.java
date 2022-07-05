package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.entity.LogisticsExpressDO;
import com.example.system.entity.LogisticsPackageDO;
import com.example.system.mapper.LogisticsPackageMapper;
import com.example.system.service.LogisticsPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 包装服务表 服务实现类
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Service
public class LogisticsPackageServiceImpl extends ServiceImpl<LogisticsPackageMapper, LogisticsPackageDO> implements LogisticsPackageService {

    @Autowired
    private LogisticsPackageMapper mapper;

    @Override
    public int insertPackage(LogisticsPackageDO packageDO) {
        return mapper.insert(packageDO);
    }

    @Override
    public int updatePackage(LogisticsPackageDO packageDO) {
        return mapper.updateById(packageDO);
    }

    @Override
    public List<LogisticsPackageDO> selectPackageItems(Integer page, Integer size) {
        QueryWrapper<LogisticsPackageDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("package_id");//降序输出

        Page<LogisticsPackageDO> pageItem = new Page<>(page,size);
        mapper.selectPage(pageItem,queryWrapper);
        return pageItem.getRecords();
    }

    @Override
    public boolean exitsPackageName(String packageName) {
        LogisticsPackageDO logisticsPackageDO = mapper.exitsPackage(packageName);
        if (logisticsPackageDO == null)return false;
        else{
            if (logisticsPackageDO.getPackageDeleted() == 1){ //和已经删除的冲突，直接把已经删除的物理删除
                mapper.deleteByIdForever(logisticsPackageDO.getPackageId());
                return false;
            }else return true;
        }
    }

    @Override
    public List<LogisticsPackageDO> selectUserLikePackageName(String packageName) {
        QueryWrapper<LogisticsPackageDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("package_name",packageName);

        return mapper.selectList(queryWrapper);
    }
}
