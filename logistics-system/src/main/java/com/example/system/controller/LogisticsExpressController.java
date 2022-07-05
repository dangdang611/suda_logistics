package com.example.system.controller;


import com.example.system.componet.Constant;
import com.example.system.entity.LogisticsExpressDO;
import com.example.system.entity.LogisticsUserAddressDO;
import com.example.system.entity.LogisticsUserDO;
import com.example.system.exception.OperationException;
import com.example.system.service.LogisticsExpressService;
import com.example.system.vo.LogisticsExpressAddVO;
import com.example.system.vo.LogisticsExpressListVO;
import com.example.system.vo.LogisticsExpressUpdateVO;
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
 * 快递产品服务表 前端控制器
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Api(tags = "快递产品服务表交互")
@RestController
@RequestMapping("/system/express")
public class LogisticsExpressController {

    @Autowired
    private LogisticsExpressService logisticsExpressService;

    @ApiOperation("查询总快递产品数")
    @GetMapping("/count")
    public Result countExpress(){
        return Result.success(logisticsExpressService.count());
    }

    @ApiOperation("搜索快递产品")
    @GetMapping("/search")
    public Result searchExpress(@ApiParam("搜索需要的快递产品名称")@RequestParam(value = "product" , required = false)
                                         String product){
        List<LogisticsExpressDO> logisticsExpressDOS ;
        if (product == null)logisticsExpressDOS = logisticsExpressService.selectExpressLikeProduct("");//传进来空参数就查询全部
        else logisticsExpressDOS = logisticsExpressService.selectExpressLikeProduct(product);

        //DO转换VO
        List<LogisticsExpressListVO> logisticsExpressListVOS = new ArrayList<>();
        for (LogisticsExpressDO logisticsExpressDO : logisticsExpressDOS) {
            LogisticsExpressListVO logisticsExpressListVO = new LogisticsExpressListVO();
            BeanUtils.copyProperties(logisticsExpressDO,logisticsExpressListVO);
            logisticsExpressListVOS.add(logisticsExpressListVO);
        }

        return  Result.success(logisticsExpressListVOS).message("搜索快递产品数据成功");
    }

    @ApiOperation("插入快递产品接口")
    @PostMapping("/insert")
    public Result insertExpress(@ApiParam("需要增加的用户产品信息") @RequestBody LogisticsExpressAddVO logisticsExpressAddVO){

        if(logisticsExpressAddVO.getExpressProduct() != null && logisticsExpressAddVO.getExpressProduct().equals(""))
            Result.failure("快递产品名不能为空").code(Constant.FAILURE_HTML_CODE);
        if(logisticsExpressService.exitsProduct(logisticsExpressAddVO.getExpressProduct()))
            Result.failure("快递产品名重复").code(Constant.FAILURE_HTML_CODE);

        LogisticsExpressDO logisticsExpressDO = new LogisticsExpressDO();
        BeanUtils.copyProperties(logisticsExpressAddVO,logisticsExpressDO);
        int row = logisticsExpressService.insertExpress(logisticsExpressDO);
        if(row < 1)return Result.failure("插入快递产品失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("插入快递产品成功");
    }

    @ApiOperation("删除快递产品接口")
    @DeleteMapping("/delete")
    public Result deleteExpress(@ApiParam("需要删除的快递产品id")@RequestParam(value = "expressId" , required = false) Long expressId ,
                                @ApiParam("需要删除的快递产品id集合") @RequestParam(value = "expressIds" , required = false) List<Long> expressIds){
        if(expressId == null && expressIds == null)Result.failure("传入删除快递产品id错误").code(Constant.FAILURE_HTML_CODE);
        boolean flag = false;//删除是否成功
        if(expressId == null)logisticsExpressService.removeByIds(expressIds);
        else flag = logisticsExpressService.removeById(expressId);
        if(!flag)return Result.failure("删除快递产品失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("删除快递产品成功");
    }

    @ApiOperation("更改快递产品接口")
    @PutMapping("/update")
    public Result updateExpress(@ApiParam("需要更改的快递产品信息") @RequestBody LogisticsExpressUpdateVO logisticsExpressUpdateVO){

        if(logisticsExpressUpdateVO.getExpressProduct() != null && logisticsExpressUpdateVO.getExpressProduct().equals(""))
            Result.failure("快递产品名不能为空").code(Constant.FAILURE_HTML_CODE);

        LogisticsExpressDO logisticsExpressDO = new LogisticsExpressDO();
        BeanUtils.copyProperties(logisticsExpressUpdateVO,logisticsExpressDO);
        int row = logisticsExpressService.updateExpress(logisticsExpressDO);
        if(row < 1)return Result.failure("更改快递产品失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("更改快递产品成功");
    }

    @ApiOperation("快递产品展示接口")
    @GetMapping("/listExpress")
    public Result listExpress(@ApiParam("当前页码")
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
        List<LogisticsExpressDO> logisticsExpressDOS = logisticsExpressService.selectExpressItems(page, size);

        //DO转换VO
        List<LogisticsExpressListVO> logisticsExpressListVOS = new ArrayList<>();
        for (LogisticsExpressDO logisticsExpressDO : logisticsExpressDOS) {
            LogisticsExpressListVO logisticsExpressListVO = new LogisticsExpressListVO();
            BeanUtils.copyProperties(logisticsExpressDO,logisticsExpressListVO);
            logisticsExpressListVOS.add(logisticsExpressListVO);
        }

        return  Result.success(logisticsExpressListVOS).message("获取快递产品数据成功");

    }

}

