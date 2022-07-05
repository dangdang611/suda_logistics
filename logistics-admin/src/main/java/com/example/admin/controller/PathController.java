package com.example.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.admin.vo.PathItemVO;
import com.example.system.componet.Constant;
import com.example.system.entity.LogisticsUserOrderDO;
import com.example.system.entity.LogisticsUserOrderPathDO;
import com.example.system.service.LogisticsUserOrderPathService;
import com.example.system.service.LogisticsUserOrderService;
import com.example.system.vo.unify.MyGeneral;
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
 * 快递路线 前端控制器
 * </p>
 *
 * @author YY
 * @since 2022-01-03
 */
@Api(tags = "快递路线控制")
@RestController
public class PathController {

    @Autowired
    private LogisticsUserOrderPathService pathService;

    @Autowired
    private LogisticsUserOrderService orderService;

    @ApiOperation("地图显示")
    @GetMapping("/mapItem")
    public Result mapItem(@ApiParam("需要显示路径的订单id")@RequestParam(value = "orderId" , required = false)Long orderId){

        if (orderId == null)return Result.failure("传入订单id错误").code(Constant.FAILURE_HTML_CODE);

        QueryWrapper<LogisticsUserOrderPathDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",orderId);
        LogisticsUserOrderDO orderDO = orderService.getById(orderId);
        List<LogisticsUserOrderPathDO> list = pathService.list(queryWrapper);

        List<PathItemVO> pathItemVOS = new ArrayList<>();
        for (LogisticsUserOrderPathDO logisticsUserOrderPathDO : list) {
            PathItemVO pathItemVO = new PathItemVO();
            BeanUtils.copyProperties(logisticsUserOrderPathDO,pathItemVO);
            pathItemVOS.add(pathItemVO);
        }

        return Result.success(new MyGeneral<PathItemVO>().data(pathItemVOS).count(Long.valueOf(orderDO.getOrderLocationReachNum())));

    }

    @ApiOperation("模拟POS机扫码快递入库")
    @GetMapping("/system/POStest")
    public Result POStest(@ApiParam("被扫描的快递的id")@RequestParam(value = "ordeId",required = false)Long orderId){

        LogisticsUserOrderDO orderDO = orderService.getById(orderId);
        if (orderDO.getOrderStatus() == 3){
            return Result.success("已签收，请勿重复扫描");
        }
        if (orderDO.getOrderStatus() == 1){//刚刚入库
            orderDO.setOrderStatus(2);//已经揽件
            orderDO.setOrderLocationReachNum(orderDO.getOrderLocationReachNum()+1);//经过的城市+1

            orderService.updateById(orderDO);//更改数据

            QueryWrapper<LogisticsUserOrderPathDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_id",orderId);
            queryWrapper.eq("path_location_reach_order",orderDO.getOrderLocationReachNum()+1);//查找下一个需要到达的城市

            return Result.success("入库成功！下一个站点：" + pathService.getOne(queryWrapper).getPathLocationReach());

        }else {
            QueryWrapper<LogisticsUserOrderPathDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_id",orderId);

            orderDO.setOrderLocationReachNum(orderDO.getOrderLocationReachNum()+1);//经过的城市+1

            int count = pathService.count(queryWrapper);
            if (count <= orderDO.getOrderLocationReachNum()){//总共的路线城市小于等于已经走过的路线城市
                orderDO.setOrderStatus(3);//已经签收
                orderService.updateById(orderDO);
                return Result.success("已经到达"+orderDO.getOrderAddressTo()+orderDO.getOrderAddressFromDetail()+"  签收成功 ~");
            }
            orderService.updateById(orderDO);

            queryWrapper.eq("path_location_reach_order",orderDO.getOrderLocationReachNum()+1);//查找下一个需要到达的城市
            return Result.success("下一个站点：" + pathService.getOne(queryWrapper).getPathLocationReach());
        }

    }


}

