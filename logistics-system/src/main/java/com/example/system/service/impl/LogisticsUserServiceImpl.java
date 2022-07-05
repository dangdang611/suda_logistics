package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.entity.LogisticsUserDO;
import com.example.system.exception.OperationException;
import com.example.system.mapper.LogisticsUserMapper;
import com.example.system.service.LogisticsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Service
public class LogisticsUserServiceImpl extends ServiceImpl<LogisticsUserMapper, LogisticsUserDO> implements LogisticsUserService {

    @Autowired
    private LogisticsUserMapper mapper;

    @Override
    public int insertUser(LogisticsUserDO user) {
        //进行密码加密
        user.setUserPassword(DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes(StandardCharsets.UTF_8)));
        return mapper.insert(user);
    }

    @Override
    public int updateUser(LogisticsUserDO user) {
        //进行密码加密
        String userPassword = user.getUserPassword();
        if(userPassword!=null)//更改了密码再加密
            user.setUserPassword(DigestUtils.md5DigestAsHex(userPassword.getBytes(StandardCharsets.UTF_8)));
        return mapper.updateById(user);
    }

    @Override
    public List<LogisticsUserDO> selectUserItems(Integer page,Integer size){
        QueryWrapper<LogisticsUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("user_id");//降序输出

        Page<LogisticsUserDO> pageItem = new Page<>(page,size);
        mapper.selectPage(pageItem,queryWrapper);
        return pageItem.getRecords();
    }

    @Override
    public boolean exitsAccount(String account) {

        LogisticsUserDO logisticsUserDO = mapper.exitsAccount(account);
        if (logisticsUserDO == null)return false;
        else{
            if (logisticsUserDO.getUserDeleted() == 1){ //和已经删除的冲突，直接把已经删除的物理删除
                mapper.deleteByIdForever(logisticsUserDO.getUserId());
                return false;
            }else return true;
        }
    }

    @Override
    public List<LogisticsUserDO> selectUserLikeAccount(String account) {
        QueryWrapper<LogisticsUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_account",account);

        return mapper.selectList(queryWrapper);
    }

    @Override
    public boolean verifyAccount(String account, String password) {
        QueryWrapper<LogisticsUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",account);
        LogisticsUserDO logisticsUserDO = mapper.selectOne(queryWrapper);
        if(logisticsUserDO.getUserPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8))))return true;
        return false;
    }

    @Override
    public List<Integer> getSevenUserAddNum() {

        List<Date> dateList = mapper.selectInsertTimeList();//获取所有时间

        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
        Date day1 = null;//今天
        try {
            day1 = new Date(dayFormat.parse(dayFormat.format(new Date())).getTime());
        } catch (ParseException e) {
            throw new OperationException(e.getMessage());
        }
        Date day2 = new Date(day1.getTime() - 60 * 60 * 24 * 1000);
        Date day3 = new Date(day2.getTime() - 60 * 60 * 24 * 1000);
        Date day4 = new Date(day3.getTime() - 60 * 60 * 24 * 1000);
        Date day5 = new Date(day4.getTime() - 60 * 60 * 24 * 1000);
        Date day6 = new Date(day5.getTime() - 60 * 60 * 24 * 1000);
        Date day7 = new Date(day6.getTime() - 60 * 60 * 24 * 1000);
        Date day8 = new Date(day7.getTime() - 60 * 60 * 24 * 1000);

        int num1,num2,num3,num4,num5,num6,num7,num8; //计数
        num1 = num2 = num3 = num4 = num5 = num6 = num7 = num8 = 0 ;

        for (Date date : dateList) { // 按区间计算8天之内的日期
            if (date.getTime() >= day1.getTime())num1 ++;
            else if (date.getTime() >= day2.getTime())num2 ++;
            else if (date.getTime() >= day3.getTime())num3 ++;
            else if (date.getTime() >= day4.getTime())num4 ++;
            else if (date.getTime() >= day5.getTime())num5 ++;
            else if (date.getTime() >= day6.getTime())num6 ++;
            else if (date.getTime() >= day7.getTime())num7 ++;
            else if (date.getTime() >= day8.getTime())num8 ++;
        }

        List<Integer> list = new ArrayList<>();

        list.add(num8);list.add(num7);list.add(num6);list.add(num5);list.add(num4);list.add(num3);list.add(num2);list.add(num1);

        return list;
    }
}
