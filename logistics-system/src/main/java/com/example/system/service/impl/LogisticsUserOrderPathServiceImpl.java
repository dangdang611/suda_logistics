package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.system.entity.LogisticsUserOrderPathDO;
import com.example.system.mapper.LogisticsUserOrderPathMapper;
import com.example.system.service.LogisticsUserOrderPathService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 快递路线 服务实现类
 * </p>
 *
 * @author YY
 * @since 2022-01-03
 */
@Service
public class LogisticsUserOrderPathServiceImpl extends ServiceImpl<LogisticsUserOrderPathMapper, LogisticsUserOrderPathDO> implements LogisticsUserOrderPathService {

    @Autowired
    LogisticsUserOrderPathMapper mapper;

    @Override
    public boolean deleteByOrderId(Long orderId) {
        QueryWrapper<LogisticsUserOrderPathDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",orderId);

        int delete = mapper.delete(queryWrapper);
        return delete > 0;
    }
}
