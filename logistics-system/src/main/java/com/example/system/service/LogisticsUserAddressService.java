package com.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.entity.LogisticsUserAddressDO;

import java.util.List;

/**
 * <p>
 * 用户地址簿 服务类
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
public interface LogisticsUserAddressService extends IService<LogisticsUserAddressDO> {

    int insertUserAddress(LogisticsUserAddressDO logisticsUserAddress);

    int updateUserAddress(LogisticsUserAddressDO logisticsUserAddress);

    List<LogisticsUserAddressDO> selectUserLikeAddressPhone(String addressPhone);

}
