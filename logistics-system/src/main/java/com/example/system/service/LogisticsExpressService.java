package com.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.entity.LogisticsExpressDO;
import com.example.system.entity.LogisticsUserDO;

import java.util.List;

/**
 * <p>
 * 快递产品服务表 服务类
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
public interface LogisticsExpressService extends IService<LogisticsExpressDO> {
    int insertExpress(LogisticsExpressDO logisticsExpress);

    int updateExpress(LogisticsExpressDO logisticsExpress);

    boolean exitsProduct(String product);

    List<LogisticsExpressDO> selectExpressItems(Integer page, Integer size);

    List<LogisticsExpressDO> selectExpressLikeProduct(String s);
}
