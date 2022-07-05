package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.entity.LogisticsExpressDO;
import com.example.system.entity.LogisticsUserDO;
import com.example.system.mapper.LogisticsExpressMapper;
import com.example.system.service.LogisticsExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 快递产品服务表 服务实现类
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Service
public class LogisticsExpressServiceImpl extends ServiceImpl<LogisticsExpressMapper, LogisticsExpressDO> implements LogisticsExpressService {

    @Autowired
    private LogisticsExpressMapper mapper;

    @Override
    public int insertExpress(LogisticsExpressDO logisticsExpress) {
        return mapper.insert(logisticsExpress);
    }

    @Override
    public int updateExpress(LogisticsExpressDO logisticsExpress) {
        return mapper.updateById(logisticsExpress);
    }

    @Override
    public List<LogisticsExpressDO> selectExpressItems(Integer page, Integer size) {
        QueryWrapper<LogisticsExpressDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("express_id");//降序输出

        Page<LogisticsExpressDO> pageItem = new Page<>(page,size);
        mapper.selectPage(pageItem,queryWrapper);
        return pageItem.getRecords();
    }

    @Override
    public boolean exitsProduct(String product) {
        LogisticsExpressDO logisticsExpressDO = mapper.exitsProduct(product);
        if (logisticsExpressDO == null)return false;
        else{
            if (logisticsExpressDO.getExpressDeleted() == 1){ //和已经删除的冲突，直接把已经删除的物理删除
                mapper.deleteByIdForever(logisticsExpressDO.getExpressId());
                return false;
            }else return true;
        }
    }

    @Override
    public List<LogisticsExpressDO> selectExpressLikeProduct(String product) {
        QueryWrapper<LogisticsExpressDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("express_product",product);

        return mapper.selectList(queryWrapper);
    }


}
