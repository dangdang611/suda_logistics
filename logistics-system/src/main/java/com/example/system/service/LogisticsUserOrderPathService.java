package com.example.system.service;

import com.example.system.entity.LogisticsUserOrderPathDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 快递路线 服务类
 * </p>
 *
 * @author YY
 * @since 2022-01-03
 */
public interface LogisticsUserOrderPathService extends IService<LogisticsUserOrderPathDO> {

    boolean deleteByOrderId(Long orderId);

}
