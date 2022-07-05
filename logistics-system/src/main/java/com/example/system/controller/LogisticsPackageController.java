package com.example.system.controller;


import com.example.system.componet.Constant;
import com.example.system.entity.LogisticsPackageDO;
import com.example.system.entity.LogisticsUserAddressDO;
import com.example.system.entity.LogisticsUserDO;
import com.example.system.exception.OperationException;
import com.example.system.service.LogisticsPackageService;
import com.example.system.vo.LogisticsPackageAddVO;
import com.example.system.vo.LogisticsPackageListVO;
import com.example.system.vo.LogisticsPackageUpdateVO;
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
 * 包装服务表 前端控制器
 * </p>
 *
 * @author YY
 * @since 2022-04-03
 */
@Api(tags = "包装服务表交互")
@RestController
@RequestMapping("/system/package")
public class LogisticsPackageController {

    @Autowired
    private LogisticsPackageService logisticsPackageService;

    @ApiOperation("查询包装服务总数")
    @GetMapping("/count")
    public Result countPackage(){
        return Result.success(logisticsPackageService.count());
    }

    @ApiOperation("搜索包装服务")
    @GetMapping("/search")
    public Result searchPackage(@ApiParam("搜索需要的包装服务")@RequestParam(value = "packageName" , required = false)String packageName){
        List<LogisticsPackageDO> logisticsPackageDOS ;
        if (packageName == null)logisticsPackageDOS = logisticsPackageService.selectUserLikePackageName("");//传进来空参数就查询全部
        else logisticsPackageDOS = logisticsPackageService.selectUserLikePackageName(packageName);

        //DO转换VO
        List<LogisticsPackageListVO> logisticsPackageListVOS = new ArrayList<>();
        for (LogisticsPackageDO logisticsPackageDO : logisticsPackageDOS) {
            LogisticsPackageListVO logisticsPackageListVO = new LogisticsPackageListVO();
            BeanUtils.copyProperties(logisticsPackageDO,logisticsPackageListVO);
            logisticsPackageListVOS.add(logisticsPackageListVO);
        }

        return  Result.success(logisticsPackageListVOS).message("搜索包装服务数据成功");
    }

    @ApiOperation("插入包装服务接口")
    @PostMapping("/insert")
    public Result insertPackage(@ApiParam("需要插入的包装服务信息") @RequestBody LogisticsPackageAddVO logisticsPackageAddVO){

        if(logisticsPackageAddVO.getPackageName() != null && logisticsPackageAddVO.getPackageName().equals(""))
            Result.failure("包装服务名不能为空").code(Constant.FAILURE_HTML_CODE);
        if(logisticsPackageService.exitsPackageName(logisticsPackageAddVO.getPackageName()))
            Result.failure("该包装服务已存在").code(Constant.FAILURE_HTML_CODE);

        LogisticsPackageDO logisticsPackageDO = new LogisticsPackageDO();
        BeanUtils.copyProperties(logisticsPackageAddVO,logisticsPackageDO);
        int row = logisticsPackageService.insertPackage(logisticsPackageDO);
        if(row < 1)return Result.failure("插入包装服务失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("插入包装服务成功");
    }

    @ApiOperation("删除包装服务接口")
    @DeleteMapping("/delete")
    public Result deletePackage(@ApiParam("需要删除的包装服务id")@RequestParam(value = "packageId" ,
                                        required = false) Long packageId ,
                                @ApiParam("需要删除的用户id集合") @RequestParam(value = "packageIds" ,
                                        required = false) List<Long> packageIds){//required 的意思是传进来没有这个参数也不报错
        if(packageId == null && packageIds == null)Result.failure("传入包装服务id错误").code(Constant.FAILURE_HTML_CODE);
        boolean flag = false ;//删除是否成功
        if(packageId == null)flag = logisticsPackageService.removeByIds(packageIds);
        else flag = logisticsPackageService.removeById(packageId);
        if(!flag)return Result.failure("删除包装服务失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("删除包装服务成功");
    }

    @ApiOperation("更改包装服务接口")
    @PutMapping("/update")
    public Result updatePackage(@ApiParam("需要更改的包装服务") @RequestBody LogisticsPackageUpdateVO logisticsPackageUpdateVO){

        if(logisticsPackageUpdateVO.getPackageName() != null && logisticsPackageUpdateVO.getPackageName().equals(""))
            Result.failure("包装服务名不能为空").code(Constant.FAILURE_HTML_CODE);

        LogisticsPackageDO logisticsPackageDO = new LogisticsPackageDO();
        BeanUtils.copyProperties(logisticsPackageUpdateVO,logisticsPackageDO);
        int row = logisticsPackageService.updatePackage(logisticsPackageDO);
        if(row < 1)return Result.failure("更改包装服务失败").code(Constant.FAILURE_HTML_CODE);
        return Result.success().message("更改包装服务成功");
    }

    @ApiOperation("展示包装服务接口")
    @GetMapping("/listPackages")
    public Result listPackage(@ApiParam("当前页码")
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

        List<LogisticsPackageDO> logisticsPackageDOS = logisticsPackageService.selectPackageItems(page, size);

        //DO转换VO
        List<LogisticsPackageListVO> logisticsPackageListVOS = new ArrayList<>();
        for (LogisticsPackageDO logisticsPackageDO : logisticsPackageDOS) {
            LogisticsPackageListVO logisticsPackageListVO = new LogisticsPackageListVO();
            BeanUtils.copyProperties(logisticsPackageDO,logisticsPackageListVO);
            logisticsPackageListVOS.add(logisticsPackageListVO);
        }

        return  Result.success(logisticsPackageListVOS).message("获取包装服务数据数据成功");

    }

}

