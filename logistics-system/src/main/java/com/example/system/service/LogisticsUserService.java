package com.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.entity.LogisticsUserDO;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
public interface LogisticsUserService extends IService<LogisticsUserDO> {

    int insertUser(LogisticsUserDO user);

    int updateUser(LogisticsUserDO user);

    List<LogisticsUserDO> selectUserItems(Integer page,Integer size);

    boolean exitsAccount(String account);

    List<LogisticsUserDO> selectUserLikeAccount(String s);

    boolean verifyAccount(String account, String password);

    List<Integer> getSevenUserAddNum();
}
