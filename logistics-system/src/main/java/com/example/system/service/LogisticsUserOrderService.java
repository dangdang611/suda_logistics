package com.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.entity.LogisticsUserDO;
import com.example.system.entity.LogisticsUserOrderDO;

import java.util.List;

/**
 * <p>
 * 用户订单表 服务类
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
public interface LogisticsUserOrderService extends IService<LogisticsUserOrderDO> {

    int updateUserOrder(LogisticsUserOrderDO user);

    List<LogisticsUserOrderDO> selectUserOrderItems(Integer page, Integer size ,Integer orderStatus);

    List<LogisticsUserOrderDO> selectUserOrderLikeAccount(String s , Integer orderStatus);

    List<Integer> getSevenUserOrderAddNum();

}
