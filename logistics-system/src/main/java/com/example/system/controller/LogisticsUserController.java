package com.example.system.controller;

import com.example.system.componet.Constant;
import com.example.system.entity.LogisticsUserDO;
import com.example.system.exception.OperationException;
import com.example.system.service.LogisticsUserService;
import com.example.system.vo.LogisticsUserAddVO;
import com.example.system.vo.LogisticsUserUpdateVO;
import com.example.system.vo.LogisticsUserListVO;
import com.example.system.vo.unify.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Api(tags = "用户信息表交互")
@RestController
@RequestMapping("/system/user")
public class LogisticsUserController {

    @Autowired
    private LogisticsUserService logisticsUserService;

    @ApiOperation("查询总用户数")
    @GetMapping("/count")
    public Result countUser(){
        return Result.success(logisticsUserService.count());
    }

    @ApiOperation("搜索用户账户")
    @GetMapping("/search")
    public Result searchUser(@ApiParam("搜索需要的用户账户片段")@RequestParam(value = "account" , required = false)String account){
        List<LogisticsUserDO> logisticsUserDOS ;
        if (account == null)logisticsUserDOS = logisticsUserService.selectUserLikeAccount("");//传进来空参数就查询全部
        else logisticsUserDOS = logisticsUserService.selectUserLikeAccount(account);

        //DO转换VO
        List<LogisticsUserListVO> logisticsUserListVOS = new ArrayList<>();
        for (LogisticsUserDO logisticsUserDO : logisticsUserDOS) {
            LogisticsUserListVO logisticsUserListVO = new LogisticsUserListVO();
            BeanUtils.copyProperties(logisticsUserDO,logisticsUserListVO);
            logisticsUserListVOS.add(logisticsUserListVO);
        }

        return  Result.success(logisticsUserListVOS).message("搜索用户数据成功");
    }

    @ApiOperation("插入用户账户接口")
    @PostMapping("/insert")
    public Result insertUser(@ApiParam("需要插入的用户信息") @RequestBody LogisticsUserAddVO logisticsUserAddVO){

        if(logisticsUserAddVO.getUserAccount() != null && logisticsUserAddVO.getUserAccount().equals(""))
            Result.failure("手机号不能为空").code(Constant.FAILURE_HTML_CODE);
        if(logisticsUserAddVO.getUserPassword() != null && logisticsUserAddVO.getUserPassword().equals(""))
            Result.failure("密码不能为空").code(Constant.FAILURE_HTML_CODE);
        if(logisticsUserService.exitsAccount(logisticsUserAddVO.getUserAccount()))
            Result.failure("该手机号已经存在").code(Constant.FAILURE_HTML_CODE);

        LogisticsUserDO logisticsUserDO = new LogisticsUserDO();
        BeanUtils.copyProperties(logisticsUserAddVO,logisticsUserDO);
        int row = logisticsUserService.insertUser(logisticsUserDO);
        if(row < 1)return Result.failure("插入用户账户失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("插入用户账户成功");
    }

    @ApiOperation("删除用户账户接口")
    @DeleteMapping("/delete")
    public Result deleteUser(@ApiParam("需要删除的用户id")@RequestParam(value = "userId" , required = false) Long userId ,
                             @ApiParam("需要删除的用户id集合") @RequestParam(value = "userIds" , required = false) List<Long> userIds){
        if(userId == null && userIds == null)Result.failure("传入删除用户账号id错误").code(Constant.FAILURE_HTML_CODE);
        boolean flag = false ;//删除是否成功
        if(userId == null)flag = logisticsUserService.removeByIds(userIds);
        else flag = logisticsUserService.removeById(userId);
        if(!flag)return Result.failure("删除用户账户失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("删除用户账户成功");
    }

    @ApiOperation("更改用户账户接口")
    @PutMapping("/update")
    public Result updateUser(@ApiParam("需要更改的用户信息") @RequestBody LogisticsUserUpdateVO logisticsUserUpdateVO){

        if(logisticsUserUpdateVO.getUserAccount() != null && logisticsUserUpdateVO.getUserAccount().equals(""))
            Result.failure("手机号不能修改为空").code(Constant.FAILURE_HTML_CODE);
        if(logisticsUserUpdateVO.getUserPassword() != null && logisticsUserUpdateVO.getUserPassword().equals(""))
            Result.failure("密码不能修改为空").code(Constant.FAILURE_HTML_CODE);

        LogisticsUserDO logisticsUserDO = new LogisticsUserDO();
        BeanUtils.copyProperties(logisticsUserUpdateVO,logisticsUserDO);
        int row = logisticsUserService.updateUser(logisticsUserDO);
        if(row < 1)return Result.failure("更改用户账号失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("更改用户账户成功");
    }

    @ApiOperation("展示用户接口")
    @GetMapping("/listUsers")
    public Result listUsers(@ApiParam("当前页码")
                                @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                            @ApiParam("每页大小")
                                @RequestParam(value = "size", required = false, defaultValue = "5") Integer size){
        // 参数校验
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = 5;
        }

        List<LogisticsUserDO> logisticsUserDOS = logisticsUserService.selectUserItems(page, size);

        //DO转换VO
        List<LogisticsUserListVO> logisticsUserListVOS = new ArrayList<>();
        for (LogisticsUserDO logisticsUserDO : logisticsUserDOS) {
            LogisticsUserListVO logisticsUserListVO = new LogisticsUserListVO();
            BeanUtils.copyProperties(logisticsUserDO,logisticsUserListVO);
            logisticsUserListVOS.add(logisticsUserListVO);
        }

        return  Result.success(logisticsUserListVOS).message("获取用户数据成功");

    }

}

