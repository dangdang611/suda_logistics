package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.entity.LogisticsPackageDO;
import com.example.system.entity.LogisticsUserAddressDO;
import com.example.system.entity.LogisticsUserDO;
import com.example.system.mapper.LogisticsUserAddressMapper;
import com.example.system.service.LogisticsUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户地址簿 服务实现类
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Service
public class LogisticsUserAddressServiceImpl extends ServiceImpl<LogisticsUserAddressMapper, LogisticsUserAddressDO> implements LogisticsUserAddressService {

    @Autowired
    private LogisticsUserAddressMapper mapper;

    @Override
    public int insertUserAddress(LogisticsUserAddressDO logisticsUserAddress) {
        return mapper.insert(logisticsUserAddress);
    }

    @Override
    public int updateUserAddress(LogisticsUserAddressDO logisticsUserAddress) {
        return mapper.updateById(logisticsUserAddress);
    }

    @Override
    public List<LogisticsUserAddressDO> selectUserLikeAddressPhone(String addressPhone) {
        QueryWrapper<LogisticsUserAddressDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("address_phone",addressPhone);

        return mapper.selectList(queryWrapper);
    }
}
