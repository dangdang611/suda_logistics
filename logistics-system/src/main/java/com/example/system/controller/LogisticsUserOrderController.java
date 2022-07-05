package com.example.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.system.componet.Constant;
import com.example.system.entity.LogisticsUserDO;
import com.example.system.entity.LogisticsUserOrderDO;
import com.example.system.entity.LogisticsUserOrderPathDO;
import com.example.system.exception.OperationException;
import com.example.system.service.LogisticsUserOrderPathService;
import com.example.system.service.LogisticsUserOrderService;
import com.example.system.utils.PathUtil;
import com.example.system.vo.*;
import com.example.system.vo.unify.Point;
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
 * 用户订单表 前端控制器
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Api(tags = "用户订单表交互")
@RestController
@RequestMapping("/system/order")
public class LogisticsUserOrderController {

    @Autowired
    private LogisticsUserOrderService logisticsUserOrderService;

    @Autowired
    private LogisticsUserOrderPathService logisticsUserOrderPathService;

    @ApiOperation("查询用户订单总数")
    @GetMapping("/count")
    public Result countUserOrder(@ApiParam("订单状态") @RequestParam(value = "orderStatus",
                required = false, defaultValue = "1") Integer orderStatus){
        QueryWrapper<LogisticsUserOrderDO>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_status",orderStatus);

        return Result.success(logisticsUserOrderService.count(queryWrapper));
    }

    @ApiOperation("增加用户订单")
    @PostMapping("/insert")
    public Result insertUserOrder(@RequestBody LogisticsUserOrderListVO logisticsUserOrderListVO){

        LogisticsUserOrderDO logisticsUserOrderDO = new LogisticsUserOrderDO();
        BeanUtils.copyProperties(logisticsUserOrderListVO,logisticsUserOrderDO);

        logisticsUserOrderService.save(logisticsUserOrderDO);

        return Result.success();

    }

    @ApiOperation("搜索用户订单")
    @GetMapping("/search")
    public Result searchUser(@ApiParam("搜索需要的用户订单所属账户号")@RequestParam(value = "account" ,
            required = false)String account , @ApiParam("订单状态") @RequestParam(value = "orderStatus",
            required = false, defaultValue = "1") Integer orderStatus){
        List<LogisticsUserOrderDO> logisticsUserOrderDOS ;
        if (account == null)logisticsUserOrderDOS = logisticsUserOrderService.selectUserOrderLikeAccount("",orderStatus);//传进来空参数就查询全部
        else logisticsUserOrderDOS = logisticsUserOrderService.selectUserOrderLikeAccount(account , orderStatus);

        //DO转换VO
        List<LogisticsUserOrderListVO> logisticsUserOrderListVOS = new ArrayList<>();
        for (LogisticsUserOrderDO logisticsUserOrderDO : logisticsUserOrderDOS) {
            LogisticsUserOrderListVO logisticsUserOrderListVO = new LogisticsUserOrderListVO();
            BeanUtils.copyProperties(logisticsUserOrderDO,logisticsUserOrderListVO);
            logisticsUserOrderListVOS.add(logisticsUserOrderListVO);
        }

        return  Result.success(logisticsUserOrderListVOS).message("搜索用户订单数据成功");
    }

    @ApiOperation("删除用户订单接口")
    @DeleteMapping("/delete")
    public Result deleteUser(@ApiParam("需要删除的用户订单id")@RequestParam(value = "orderId" , required = false)
                                         Long orderId ,
                             @ApiParam("需要删除的用户订单id集合") @RequestParam(value = "orderIds" , required = false)
                                     List<Long> orderIds){
        if(orderId == null && orderIds == null)Result.failure("传入删除用户订单id错误").code(Constant.FAILURE_HTML_CODE);
        boolean flag = false ;//删除是否成功
        if(orderId == null){
            flag = logisticsUserOrderService.removeByIds(orderIds);
            for (Long oid : orderIds) {
                logisticsUserOrderPathService.deleteByOrderId(oid);
            }
        }
        else {
            flag = logisticsUserOrderService.removeById(orderId);
            logisticsUserOrderPathService.deleteByOrderId(orderId);
        }
        if(!flag)return Result.failure("删除用户订单失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("删除用户订单成功");
    }

    @ApiOperation("更改用户账户接口")
    @PutMapping("/update")
    public Result updateUser(@ApiParam("需要更改的用户信息") @RequestBody
                                         LogisticsUserOrderUpdateVO logisticsUserOrderUpdateVO){

        LogisticsUserOrderDO logisticsUserOrderDO = new LogisticsUserOrderDO();
        BeanUtils.copyProperties(logisticsUserOrderUpdateVO,logisticsUserOrderDO);
        int row = logisticsUserOrderService.updateUserOrder(logisticsUserOrderDO);
        if(row < 1)return Result.failure("更改用户订单失败").code(Constant.FAILURE_HTML_CODE);

        if (logisticsUserOrderDO.getOrderAddressTo() != null){
            QueryWrapper<LogisticsUserOrderPathDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_id",logisticsUserOrderUpdateVO.getOrderId());
            queryWrapper.eq("path_location_reach_order",1);//查询始发地以便于重新规划路线
            LogisticsUserOrderPathDO fromPath = logisticsUserOrderPathService.getOne(queryWrapper);

            logisticsUserOrderPathService.deleteByOrderId(logisticsUserOrderUpdateVO.getOrderId());//删除和其相关的路线规划

            String addressFrom = logisticsUserOrderUpdateVO.getOrderAddressFrom();//获取始发地和目的地
            String addressTo = logisticsUserOrderUpdateVO.getOrderAddressTo();

            int i = 0;
            while(i < addressTo.length() && i < addressFrom.length() && addressFrom.charAt(i) == addressTo.charAt(i))i++;

            int reachOrder = 1;

            if (i == addressTo.length() && i == addressFrom.length())return Result.failure("始发地和目的地不可以一样").code(Constant.FAILURE_HTML_CODE);
            if (i > 0) { //同省

                LogisticsUserOrderPathDO pathDO = new LogisticsUserOrderPathDO();
                pathDO.setOrderId(logisticsUserOrderUpdateVO.getOrderId());

                pathDO.setPathLocationReachOrder(reachOrder);
                reachOrder ++;
                pathDO.setPathLocationReach(addressFrom + "," +logisticsUserOrderUpdateVO.getOrderAddressFromDetail());//第一个地点是始发地
                pathDO.setPathLocationLatitude(fromPath.getPathLocationLatitude());//始发地的经纬度
                pathDO.setPathLocationLongitude(fromPath.getPathLocationLongitude());
                logisticsUserOrderPathService.save(pathDO);
                pathDO.setPathId(null); //将自动生成的id变成空，再让它自动生成

                pathDO.setPathLocationReachOrder(reachOrder);
                pathDO.setPathLocationReach(addressTo + "," +logisticsUserOrderUpdateVO.getOrderAddressToDetail());//第二个地点是目的地
                pathDO.setPathLocationLatitude(logisticsUserOrderUpdateVO.getToLatitude());//目的地的经纬度
                pathDO.setPathLocationLongitude(logisticsUserOrderUpdateVO.getToLongitude());
                logisticsUserOrderPathService.save(pathDO);

            }else{

                LogisticsUserOrderPathDO pathDO = new LogisticsUserOrderPathDO();
                pathDO.setOrderId(logisticsUserOrderUpdateVO.getOrderId());

                pathDO.setPathLocationReachOrder(reachOrder);
                reachOrder ++;
                pathDO.setPathLocationReach(addressFrom + "," +logisticsUserOrderUpdateVO.getOrderAddressFromDetail());//第一个地点是始发地
                pathDO.setPathLocationLatitude(fromPath.getPathLocationLatitude());//始发地的经纬度
                pathDO.setPathLocationLongitude(fromPath.getPathLocationLongitude());
                logisticsUserOrderPathService.save(pathDO);
                pathDO.setPathId(null); //将自动生成的id变成空，再让它自动生成

                Point fromCap = PathUtil.getProCpt(addressFrom);

                if (!fromCap.getLocation().equals(addressFrom)){//和省会城市不一样
                    pathDO.setPathLocationReachOrder(reachOrder);
                    reachOrder ++;
                    pathDO.setPathLocationReach(fromCap.getLocation());//第二个地点
                    pathDO.setPathLocationLatitude(fromCap.getLatitude());//第二个地点的经纬度
                    pathDO.setPathLocationLongitude(fromCap.getLongitude());
                    logisticsUserOrderPathService.save(pathDO);
                    pathDO.setPathId(null); //将自动生成的id变成空，再让它自动生成
                }

                Point toCap = PathUtil.getProCpt(addressTo);
                if (!toCap.getLocation().equals(addressTo)){//和省会城市不一样
                    pathDO.setPathLocationReachOrder(reachOrder);
                    reachOrder ++;
                    pathDO.setPathLocationReach(toCap.getLocation());//第三个地点
                    pathDO.setPathLocationLatitude(toCap.getLatitude());//第三个地点的经纬度
                    pathDO.setPathLocationLongitude(toCap.getLongitude());
                    logisticsUserOrderPathService.save(pathDO);
                    pathDO.setPathId(null); //将自动生成的id变成空，再让它自动生成

                }

                pathDO.setPathLocationReachOrder(reachOrder);
                pathDO.setPathLocationReach(addressTo + "," +logisticsUserOrderUpdateVO.getOrderAddressToDetail());//最后一个地点目的地
                pathDO.setPathLocationLatitude(logisticsUserOrderUpdateVO.getToLatitude());//目的地的经纬度
                pathDO.setPathLocationLongitude(logisticsUserOrderUpdateVO.getToLongitude());
                logisticsUserOrderPathService.save(pathDO);

            }

        }
        return Result.success().message("更改用户订单成功");
    }

    @ApiOperation("展示用户订单接口")
    @GetMapping("/listUserOrders")
    public Result listUsers(@ApiParam("当前页码")
                            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                            @ApiParam("每页大小")
                            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                            @ApiParam("订单状态")
                            @RequestParam(value = "orderStatus", required = false, defaultValue = "1") Integer orderStatus){
        // 参数校验
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = 5;
        }
        if(orderStatus >3 || orderStatus <1)orderStatus = 1;

        List<LogisticsUserOrderDO> logisticsUserOrderDOS = logisticsUserOrderService.selectUserOrderItems(page, size ,orderStatus);

        //DO转换VO
        List<LogisticsUserOrderListVO> logisticsUserOrderListVOS = new ArrayList<>();
        for (LogisticsUserOrderDO logisticsUserOrderDO : logisticsUserOrderDOS) {
            LogisticsUserOrderListVO logisticsUserOrderListVO = new LogisticsUserOrderListVO();
            BeanUtils.copyProperties(logisticsUserOrderDO,logisticsUserOrderListVO);
            logisticsUserOrderListVOS.add(logisticsUserOrderListVO);
        }

        return  Result.success(logisticsUserOrderListVOS).message("获取用户订单数据成功");

    }

}

