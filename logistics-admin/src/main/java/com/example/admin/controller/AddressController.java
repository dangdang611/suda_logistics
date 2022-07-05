package com.example.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.system.componet.Constant;
import com.example.system.entity.LogisticsUserAddressDO;
import com.example.system.exception.OperationException;
import com.example.system.service.LogisticsUserAddressService;
import com.example.system.service.LogisticsUserOrderPathService;
import com.example.system.vo.LogisticsUserAddressAddVO;
import com.example.system.vo.LogisticsUserAddressListVO;
import com.example.system.vo.LogisticsUserAddressUpdateVO;
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
 * 用户地址簿 前端控制器
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Api(tags = "用户地址簿交互")
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private LogisticsUserAddressService logisticsUserAddressService;

    @ApiOperation("搜索用户地址")
    @GetMapping("/search")
    public Result searchAddress(@ApiParam("搜索需要的包装服务")@RequestParam(value = "addressPhone" ,
            required = false)String addressPhone){
        List<LogisticsUserAddressDO> logisticsUserAddressDOS ;
        if (addressPhone == null)logisticsUserAddressDOS = logisticsUserAddressService.selectUserLikeAddressPhone("");//传进来空参数就查询全部
        else logisticsUserAddressDOS = logisticsUserAddressService.selectUserLikeAddressPhone(addressPhone);

        //DO转换VO
        List<LogisticsUserAddressListVO> logisticsUserAddressListVOS = new ArrayList<>();
        for (LogisticsUserAddressDO logisticsUserAddressDO : logisticsUserAddressDOS) {
            LogisticsUserAddressListVO logisticsUserAddressListVO = new LogisticsUserAddressListVO();
            BeanUtils.copyProperties(logisticsUserAddressDO,logisticsUserAddressListVO);
            logisticsUserAddressListVOS.add(logisticsUserAddressListVO);
        }

        return  Result.success(logisticsUserAddressListVOS).message("搜索用户地址簿数据成功");
    }

    @ApiOperation("插入地址接口")
    @PostMapping("/insert")
    public Result insertUserAddress(@ApiParam("需要插入的地址信息") @RequestBody LogisticsUserAddressAddVO logisticsUserAddressAddVO){
        LogisticsUserAddressDO logisticsUserAddressDO = new LogisticsUserAddressDO();
        BeanUtils.copyProperties(logisticsUserAddressAddVO,logisticsUserAddressDO);
        int row = logisticsUserAddressService.insertUserAddress(logisticsUserAddressDO);
        if(row < 1)return Result.failure("插入地址失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("插入地址成功");
    }

    @ApiOperation("删除地址接口")
    @DeleteMapping("/delete")
    public Result deleteUserAddress(@ApiParam("需要删除的地址id") @RequestParam(value = "addressId" ,
            required = false) Long addressId , @ApiParam("需要删除的地址id集合") @RequestParam(value = "addressIds" ,
            required = false) List<Long> addressIds){
        if(addressId == null && addressIds == null)Result.failure("传入删除地址数据错误").code(Constant.FAILURE_HTML_CODE);
        boolean flag = false ;//删除是否成功
        if(addressId == null)flag = logisticsUserAddressService.removeByIds(addressIds);
        else flag = logisticsUserAddressService.removeById(addressId);
        if(!flag)return Result.failure("删除地址失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("删除地址成功");
    }

    @ApiOperation("更改地址接口")
    @PutMapping("/update")
    public Result updateUserAddress(@ApiParam("需要更改的地址信息") @RequestBody LogisticsUserAddressUpdateVO logisticsUserAddressUpdateVO){
        LogisticsUserAddressDO logisticsUserAddressDO = new LogisticsUserAddressDO();
        BeanUtils.copyProperties(logisticsUserAddressUpdateVO,logisticsUserAddressDO);
        int row = logisticsUserAddressService.updateUserAddress(logisticsUserAddressDO);
        if(row < 1)return Result.failure("更改地址失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("更改地址成功");
    }

    @ApiOperation("展示地址簿接口")
    @GetMapping("/listAddress")
    public Result listUsers(@ApiParam("需要查找的用户的地址的用户账户") @RequestParam(value = "userAccount" ,
            required = false) String userAccount){

        QueryWrapper<LogisticsUserAddressDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",userAccount);
        List<LogisticsUserAddressDO> logisticsUserAddressDOS = logisticsUserAddressService.list(queryWrapper);

        //DO转换VO
        List<LogisticsUserAddressListVO> logisticsUserAddressListVOS = new ArrayList<>();
        for (LogisticsUserAddressDO logisticsUserAddressDO : logisticsUserAddressDOS) {
            LogisticsUserAddressListVO logisticsUserAddressListVO = new LogisticsUserAddressListVO();
            BeanUtils.copyProperties(logisticsUserAddressDO,logisticsUserAddressListVO);
            logisticsUserAddressListVOS.add(logisticsUserAddressListVO);
        }

        return  Result.success(logisticsUserAddressListVOS).message("获取地址簿数据成功");

    }

}

