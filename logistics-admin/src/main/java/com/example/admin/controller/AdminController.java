package com.example.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.vo.*;
import com.example.system.componet.Constant;
import com.example.system.entity.LogisticsExpressDO;
import com.example.system.entity.LogisticsPackageDO;
import com.example.system.entity.LogisticsUserOrderDO;
import com.example.system.entity.LogisticsUserOrderPathDO;
import com.example.system.service.LogisticsExpressService;
import com.example.system.service.LogisticsPackageService;
import com.example.system.service.LogisticsUserOrderPathService;
import com.example.system.service.LogisticsUserOrderService;
import com.example.system.utils.PathUtil;
import com.example.system.vo.unify.MyGeneral;
import com.example.system.vo.unify.Point;
import com.example.system.vo.unify.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 招聘资讯表 前端控制器
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Api(tags = "主页交互")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final String EXPRESS_ID1 = "1512036288756539393"; //速达标快id

    private final String EXPRESS_ID2 = "1512039211968327681"; //速达特快id

    private final String EXPRESS_ID3 = "1512987009085685762"; //速达卡航id

    private final BigDecimal PRICE_12000 = new BigDecimal("12000");// 12000的计算BigDecimal数据对象

    private final BigDecimal PRICE_6000 = new BigDecimal("6000");// 6000的计算BigDecimal数据对象

    private final BigDecimal PRICE_1 = new BigDecimal("1");// 1的计算BigDecimal数据对象

    @Autowired
    private LogisticsUserOrderService orderService;

    @Autowired
    private LogisticsPackageService packageService;

    @Autowired
    private LogisticsExpressService expressService;

    @Autowired
    private LogisticsUserOrderPathService pathService;

    @ApiOperation("收寄范围查询")
    @GetMapping("/expressScope")
    public Result expressScope(@ApiParam("需要展示招聘资讯id")
                           @RequestParam(value = "location", required = false) String location){

        if (location == null) return Result.failure("地点输入为空，无法判断");

        if (location.contains("新疆") && location.contains("阿拉尔")) return Result.failure("此地区无法送达");
        if (location.contains("西藏") && location.contains("阿里地区")) return Result.failure("此地区无法送达");
        if (location.contains("青海") && location.contains("西宁")) return Result.failure("此地区无法送达");

        return Result.success().message("此地区全境可送达");
    }

    @ApiOperation("时效运费查询")
    @PostMapping("/freightQuery")
    public Result freightQuery(@ApiParam("查询所需要的字段")@RequestBody FreightItemVO itemVO ){

        if (itemVO.getSendTime() == null)itemVO.setSendTime(new Date()); // 时间默认是现在
        if (itemVO.getVolume() == null)itemVO.setVolume(new BigDecimal("1"));
        if (itemVO.getWeight() == null)itemVO.setWeight(new BigDecimal("1"));

        if(itemVO.getType() == 1){ //快递服务

            BigDecimal weight = itemVO.getWeight(); // 重量
            BigDecimal volume = itemVO.getVolume(); // 体积

            FreightListVO freightListVO1 = new FreightListVO(); // 速达标快
            LogisticsExpressDO logisticsExpressDO1 = expressService.getById(EXPRESS_ID1);
            freightListVO1.setProductName(logisticsExpressDO1.getExpressProduct());

            FreightListVO freightListVO2 = new FreightListVO(); // 速达特快
            LogisticsExpressDO logisticsExpressDO2 = expressService.getById(EXPRESS_ID2);
            freightListVO2.setProductName(logisticsExpressDO2.getExpressProduct());

            //共同信息
            String fromLocation = itemVO.getLocationFrom(); // 始发地
            String toLocation = itemVO.getLocationTo(); // 目的地
            Date sendTime = itemVO.getSendTime(); // 发送时间

            if (fromLocation.equals(toLocation)){ //目的地和始发地一样
                Result.failure("始发地和目的地不能一样").code(Constant.FAILURE_HTML_CODE);
            }

            int i = 0;
            while(fromLocation.charAt(i) != ',' && i<fromLocation.length() && i<toLocation.length()){
                if (fromLocation.charAt(i) != toLocation.charAt(i)) break; //判断省是否一样
                i++;
            }

            if(i > 0){ //同省

                freightListVO1.setPriceRule(logisticsExpressDO1.getExpressAccountingRuleIn());
                freightListVO1.setSendTime(new Date(sendTime.getTime() + 60 * 60 * 24 * 1000));

                freightListVO2.setPriceRule(logisticsExpressDO2.getExpressAccountingRuleIn());
                freightListVO2.setSendTime(new Date(sendTime.getTime() + 60 * 60 * 12 * 1000));

                volume = volume.divide(PRICE_12000,1,BigDecimal.ROUND_HALF_UP);//四舍五入的体积重量

                if (weight.compareTo(PRICE_1) == -1)weight = PRICE_1; // 没达到1就按1算
                if (volume.compareTo(PRICE_1) == -1)volume = PRICE_1;

                if (weight.compareTo(volume) == 1){//重量大就选重量
                    freightListVO1.setWeight(weight);
                    freightListVO2.setWeight(weight);
                }
                else {// 体积重量大就选体积重量
                    freightListVO1.setWeight(volume);
                    freightListVO2.setWeight(volume);
                }

                freightListVO1.setPrice(new BigDecimal("8"));
                freightListVO2.setPrice(new BigDecimal("12"));

                if(weight.compareTo(new BigDecimal("1")) == 1){ // 超过首重部分

                    BigDecimal extra = freightListVO1.getWeight().subtract(PRICE_1);//减去首重
                    freightListVO1.setPrice(freightListVO1.getPrice().add(extra.multiply(
                            new BigDecimal("6")).setScale(0, BigDecimal.ROUND_DOWN)));//去掉小数部分
                    freightListVO2.setPrice(freightListVO2.getPrice().add(extra.multiply(
                            new BigDecimal("8")).setScale(0, BigDecimal.ROUND_DOWN)));

                }


            }else{
                freightListVO1.setPriceRule(logisticsExpressDO1.getExpressAccountingRuleOut());
                freightListVO1.setSendTime(new Date(sendTime.getTime() + 60 * 60 * 48 * 1000));

                freightListVO2.setPriceRule(logisticsExpressDO2.getExpressAccountingRuleOut());
                freightListVO2.setSendTime(new Date(sendTime.getTime() + 60 * 60 * 24 * 1000));

                BigDecimal volume1 = volume.divide(PRICE_12000,1,BigDecimal.ROUND_HALF_UP); // 速达标快
                BigDecimal volume2 = volume.divide(PRICE_6000,1,BigDecimal.ROUND_HALF_UP); // 速达特快

                if (weight.compareTo(volume1) == 1)freightListVO1.setWeight(weight);
                else freightListVO1.setWeight(volume1);

                if (weight.compareTo(volume2) == 1)freightListVO2.setWeight(weight);
                else freightListVO2.setWeight(volume2);

                if (freightListVO1.getWeight().compareTo(PRICE_1) == -1)freightListVO1.setPrice(PRICE_1); // 没达到1就按1算
                if (freightListVO2.getWeight().compareTo(PRICE_1) == -1)freightListVO2.setPrice(PRICE_1);

                freightListVO1.setPrice(new BigDecimal("10"));
                freightListVO2.setPrice(new BigDecimal("15"));

                if(freightListVO1.getWeight().compareTo(new BigDecimal("1")) == 1){ // 速达标快超过首重部分
                    BigDecimal extra = freightListVO1.getWeight().subtract(PRICE_1);//减去首重
                    freightListVO1.setPrice(freightListVO1.getPrice().add(extra.multiply(new BigDecimal("6.5"))
                            .setScale(0, BigDecimal.ROUND_DOWN)));//去掉小数
                }

                if(freightListVO2.getWeight().compareTo(new BigDecimal("1")) == 1){ // 速达特快超过首重部分
                    BigDecimal extra = freightListVO2.getWeight().subtract(new BigDecimal("1"));//减去首重
                    freightListVO2.setPrice(freightListVO2.getPrice().add(extra.multiply(new BigDecimal("8.5"))
                            .setScale(0, BigDecimal.ROUND_DOWN)));
                }
            }

            List<FreightListVO> list = new ArrayList<>();
            list.add(freightListVO1);
            list.add(freightListVO2);

            return Result.success(list);

        }

        if(itemVO.getType() == 2){ //大件服务

            BigDecimal weight = itemVO.getWeight(); // 重量
            BigDecimal volume = itemVO.getVolume();// 体积

            FreightListVO freightListVO = new FreightListVO(); // 速达卡航
            LogisticsExpressDO logisticsExpressDO = expressService.getById(EXPRESS_ID3);
            freightListVO.setProductName(logisticsExpressDO.getExpressProduct());

            //共同信息
            String fromLocation = itemVO.getLocationFrom(); // 始发地
            String toLocation = itemVO.getLocationTo(); // 目的地
            Date sendTime = itemVO.getSendTime(); // 发送时间

            if (fromLocation.equals(toLocation)){ //目的地和始发地一样
                Result.failure("始发地和目的地不能一样").code(Constant.FAILURE_HTML_CODE);
            }

            int i = 0;
            while(fromLocation.charAt(i) != ',' && i<fromLocation.length() && i<toLocation.length()){
                if (fromLocation.charAt(i) != toLocation.charAt(i)) break; //判断省是否一样
                i++;
            }

            volume = volume.divide(PRICE_12000,1,BigDecimal.ROUND_HALF_UP);// 体积重量
            if (weight.compareTo(new BigDecimal("20")) == -1)weight = new BigDecimal("20"); // 没达到20就按20算
            if (volume.compareTo(new BigDecimal("20")) == -1)volume = new BigDecimal("20");

            if (weight.compareTo(volume) == 1)freightListVO.setWeight(weight);
            else freightListVO.setWeight(volume);

            if(i > 0) { //同省
                freightListVO.setPriceRule(logisticsExpressDO.getExpressAccountingRuleIn());
                freightListVO.setSendTime(new Date(sendTime.getTime() + 60 *60 * 24 *1000));
                freightListVO.setPrice(new BigDecimal("40"));
                if (freightListVO.getWeight().compareTo(new BigDecimal("20")) == 1){
                    BigDecimal extra = freightListVO.getWeight().subtract(new BigDecimal("20"));//减去首重
                    freightListVO.setPrice(freightListVO.getPrice().add(extra.multiply(new BigDecimal("2.2"))
                            .setScale(0, BigDecimal.ROUND_DOWN)));
                }
            }else{
                freightListVO.setPriceRule(logisticsExpressDO.getExpressAccountingRuleOut());
                freightListVO.setSendTime(new Date(sendTime.getTime() + 60 *60 * 48 *1000));
                freightListVO.setPrice(new BigDecimal("45"));
                if (freightListVO.getWeight().compareTo(new BigDecimal("20")) == 1){
                    BigDecimal extra = freightListVO.getWeight().subtract(new BigDecimal("20"));//减去首重
                    freightListVO.setPrice(freightListVO.getPrice().add(extra.multiply(new BigDecimal("2.4"))
                            .setScale(0, BigDecimal.ROUND_DOWN)));
                }
            }
            List<FreightListVO> list = new ArrayList<>();
            list.add(freightListVO);

            return Result.success(list);
        }

        return Result.failure("产品服务参数错误");

    }

    @ApiOperation("显示包装服务")
    @GetMapping("/packageList")
    public Result packageList(){

        //查询
        List<LogisticsPackageDO> list = packageService.list();

        //DO转VO
        List<PackageListVO> packageListVOS = new ArrayList<>();
        for (LogisticsPackageDO logisticsPackageDO : list) {
            PackageListVO packageListVO = new PackageListVO();
            BeanUtils.copyProperties(logisticsPackageDO,packageListVO);
            packageListVOS.add(packageListVO);
        }

        return Result.success(packageListVOS);
    }

@ApiOperation("添加用户订单")
@PostMapping("/addOrder")
public Result addUserOrder(@ApiParam("获取的订单数据")@RequestBody OrderItemVO orderItemVO){

    orderItemVO.setOrderStatus(1);
    LogisticsUserOrderDO orderDO = new LogisticsUserOrderDO();
    BeanUtils.copyProperties(orderItemVO,orderDO);
    boolean save = orderService.save(orderDO);
    if (!save) return Result.failure("添加用户订单失败");

    String addressFrom = orderItemVO.getOrderAddressFrom();
    String addressTo = orderItemVO.getOrderAddressTo();

    int i = 0;
    while(i < addressTo.length() && i < addressFrom.length() && addressFrom.charAt(i) == addressTo.charAt(i))i++;

    int reachOrder = 1;

    if (i == addressTo.length() && i == addressFrom.length())return Result.failure("始发地和目的地不可以一样").code(Constant.FAILURE_HTML_CODE);
    if (i > 0) { //同省

        LogisticsUserOrderPathDO pathDO = new LogisticsUserOrderPathDO();
        pathDO.setOrderId(orderDO.getOrderId());

        pathDO.setPathLocationReachOrder(reachOrder);
        reachOrder ++;
        pathDO.setPathLocationReach(addressFrom + "," +orderItemVO.getOrderAddressFromDetail());//第一个地点是始发地
        pathDO.setPathLocationLatitude(orderItemVO.getFromLatitude());//始发地的经纬度
        pathDO.setPathLocationLongitude(orderItemVO.getFromLongitude());
        pathService.save(pathDO);
        pathDO.setPathId(null); //将自动生成的id变成空，再让它自动生成

        pathDO.setPathLocationReachOrder(reachOrder);
        pathDO.setPathLocationReach(addressTo + "," +orderItemVO.getOrderAddressToDetail());//第二个地点是目的地
        pathDO.setPathLocationLatitude(orderItemVO.getToLatitude());//目的地的经纬度
        pathDO.setPathLocationLongitude(orderItemVO.getToLongitude());
        pathService.save(pathDO);

    }else{

        LogisticsUserOrderPathDO pathDO = new LogisticsUserOrderPathDO();
        pathDO.setOrderId(orderDO.getOrderId());

        pathDO.setPathLocationReachOrder(reachOrder);
        reachOrder ++;
        pathDO.setPathLocationReach(addressFrom + "," +orderItemVO.getOrderAddressFromDetail());//第一个地点是始发地
        pathDO.setPathLocationLatitude(orderItemVO.getFromLatitude());//始发地的经纬度
        pathDO.setPathLocationLongitude(orderItemVO.getFromLongitude());
        pathService.save(pathDO);
        pathDO.setPathId(null); //将自动生成的id变成空，再让它自动生成

        Point fromCap = PathUtil.getProCpt(addressFrom);

        if (!fromCap.getLocation().equals(addressFrom)){//和省会城市不一样
            pathDO.setPathLocationReachOrder(reachOrder);
            reachOrder ++;
            pathDO.setPathLocationReach(fromCap.getLocation());//第二个地点
            pathDO.setPathLocationLatitude(fromCap.getLatitude());//第二个地点的经纬度
            pathDO.setPathLocationLongitude(fromCap.getLongitude());
            pathService.save(pathDO);
            pathDO.setPathId(null); //将自动生成的id变成空，再让它自动生成
        }

        Point toCap = PathUtil.getProCpt(addressTo);
        if (!toCap.getLocation().equals(addressTo)){//和省会城市不一样
            pathDO.setPathLocationReachOrder(reachOrder);
            reachOrder ++;
            pathDO.setPathLocationReach(toCap.getLocation());//第三个地点
            pathDO.setPathLocationLatitude(toCap.getLatitude());//第三个地点的经纬度
            pathDO.setPathLocationLongitude(toCap.getLongitude());
            pathService.save(pathDO);
            pathDO.setPathId(null); //将自动生成的id变成空，再让它自动生成

        }

        pathDO.setPathLocationReachOrder(reachOrder);
        pathDO.setPathLocationReach(addressTo + "," +orderItemVO.getOrderAddressToDetail());//最后一个地点目的地
        pathDO.setPathLocationLatitude(orderItemVO.getToLatitude());//目的地的经纬度
        pathDO.setPathLocationLongitude(orderItemVO.getToLongitude());
        pathService.save(pathDO);

    }

    return Result.success("添加用户订单成功");
}

    @ApiOperation("运单显示")
    @GetMapping("/searchOrder")
    public Result searchOrder(@ApiParam("需要搜索的关键字")@RequestParam(value = "context" , required = false ,
            defaultValue = "") String context , @ApiParam("搜索邮寄给我的还是我邮寄的")@RequestParam(value = "type" ,
            required = false , defaultValue = "1") Integer type , @ApiParam("页码")@RequestParam(value = "page" ,
            required = false , defaultValue = "1") Integer page , @ApiParam("一页大小")@RequestParam(value = "size" ,
            required = false , defaultValue = "5") Integer size ,@ApiParam("需要查询的订单的id")@RequestParam(
            value = "orderId" , required = false) Long orderId){

        if (type !=1 && type !=2) type = 1; //数据过滤
        if (page < 1)page = 1;
        if (size < 1)size = 5;
        QueryWrapper<LogisticsUserOrderDO> queryWrapper = new QueryWrapper<>();

        if(type == 1)queryWrapper.eq("user_account",context);//根据类型查询哪个
        else queryWrapper.like("order_phone_to",context);

        if (orderId != null)queryWrapper.like("order_id",orderId);

        Page<LogisticsUserOrderDO> lpage = new Page<>(page,size);
        orderService.page(lpage, queryWrapper);

        List<OrderListVO> orderListVOS = new ArrayList<>();
        for (LogisticsUserOrderDO record : lpage.getRecords()) {
            OrderListVO orderListVO = new OrderListVO();
            BeanUtils.copyProperties(record,orderListVO);
            orderListVOS.add(orderListVO);
        }

        return Result.success(new MyGeneral<OrderListVO>().data(orderListVOS).count(lpage.getTotal()));

    }

    @ApiOperation("删除用户订单")
    @DeleteMapping("/deleteOrder")
    public Result deleteOrder(@ApiParam("需要删除的用户订单id")@RequestParam(value = "orderId" , required = false)
                                     Long orderId){
        if(orderId == null)Result.failure("传入删除用户订单id错误").code(Constant.FAILURE_HTML_CODE);
        boolean flag = false ;//删除是否成功
        flag = orderService.removeById(orderId);
        pathService.deleteByOrderId(orderId);
        if(!flag)return Result.failure("删除用户订单失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("删除用户订单成功");
    }



}

