package com.example.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.system.componet.RedisOperator;
import com.example.admin.componet.JwtOperator;
import com.example.system.componet.Constant;
import com.example.system.entity.LogisticsUserDO;
import com.example.system.service.LogisticsUserService;
import com.example.system.utils.CodeUtil;
import com.example.system.utils.SendUtil;
import com.example.system.vo.LogisticsUserAddVO;
import com.example.system.vo.unify.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@Api(tags = "Redis交互控制器")
@RestController
public class RedisController {

    @Autowired
    private LogisticsUserService logisticsUserService;

    @Autowired
    private RedisOperator redisOperator;//redis操作工具

    @Autowired
    private JwtOperator jwtOperator;

    @ApiOperation("用户登录")
    @PostMapping("/user/login")
    public Result userLogin(@ApiParam("需要登录的用户信息")@RequestBody LogisticsUserAddVO logisticsUserAddVO) {

        String account = logisticsUserAddVO.getUserAccount();
        String password = logisticsUserAddVO.getUserPassword();

        boolean flag = logisticsUserService.verifyAccount(account,password);//验证用户名和密码

        if (!flag) return Result.failure("用户名或密码错误").code(Constant.FAILURE_HTML_CODE);

        JSONObject json = new JSONObject();

        String token = jwtOperator.createToken(account,60 * 60 * 12) ;//12个小时过期

        redisOperator.set(token,"value",60 * 60 * 12);//redis存入一份记录，注销即失效

        if (!StringUtils.isEmpty(token)) {
            json.put(Constant.TOKEN_HEAD,token) ;
        }else return Result.failure("未知错误");

        return Result.success(json);

    }

    @ApiOperation("用户注销")
    @PostMapping("/user/logout")
    public Result userLogout(HttpServletRequest request){

        String token = request.getHeader(Constant.TOKEN_HEAD);
        if(StringUtils.isEmpty(token)){
            token = request.getParameter(Constant.TOKEN_HEAD);
        }
        String tokenIsExists = redisOperator.get(token);
        if(tokenIsExists != null)redisOperator.expire(token,0);//存在该键值就设置过期

        return Result.success();

    }

    @ApiOperation("系统管理员登录")
    @PostMapping("/system/login")
    public Result rootLogin(@ApiParam("管理员账户")@RequestParam(value = "root" , required = false)String root ,
                            @ApiParam("管理员密码")@RequestParam(value = "password" , required = false)String password) {

        if(root == null || password == null)Result.failure("管理员账户或密码不能为空").code(Constant.FAILURE_HTML_CODE);;

        if(root.equals(Constant.ROOT_USERNAME) && password.equals(Constant.ROOT_PASSWORD)){
            JSONObject json = new JSONObject();
            String token = jwtOperator.createToken(root,60 * 60 * 1) ;//1个小时过期
            if (!StringUtils.isEmpty(token)) {
                json.put(Constant.TOKEN_HEAD_ROOT,token) ;
            }
            return Result.success(json);
        }
        return Result.failure("账户名或密码错误");

    }

    @ApiOperation("系统管理员注销")
    @PostMapping("/system/logout")
    public Result systemLogout(){

        String sysToken = redisOperator.get(Constant.ROOT_USERNAME_REDIS_KEY);

        if(sysToken != null)//取消管理员在redis中的键值
            redisOperator.expire(Constant.ROOT_USERNAME_REDIS_KEY,0);//存在该键值就设置过期

        return Result.success();

    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result register(@ApiParam("需要注册的用户信息")@RequestBody LogisticsUserAddVO logisticsUserAddVO) {
        String account = logisticsUserAddVO.getUserAccount();
        String messageCode = logisticsUserAddVO.getMessageCode();
        if(account == null || account.equals(""))Result.failure("手机号不能为空").code(Constant.FAILURE_HTML_CODE);
        if(logisticsUserService.exitsAccount(account))Result.failure("该手机号已注册").code(Constant.FAILURE_HTML_CODE);
        if(messageCode == null || messageCode.equals("")){ //此时是传入手机号获取验证码
            String code = CodeUtil.generateVerifyCode(6);
            SendUtil.sendCode(account,code);
            //将key进行md5加密
            redisOperator.set(DigestUtils.md5DigestAsHex(account.getBytes(StandardCharsets.UTF_8)),code,60 * 3);
            //将验证码存入redis中 , 3分钟过期
            redisOperator.set(DigestUtils.md5DigestAsHex((account+ Constant.REGISTER_SALT).
                            getBytes(StandardCharsets.UTF_8)),"exist",60 * 10);
            //证明用户获取过验证码，10分钟过期
            return Result.success().message("验证码已发送");
        }else{//获取验证码之后输入密码验证
            String code = redisOperator.get(DigestUtils.md5DigestAsHex(account.getBytes(StandardCharsets.UTF_8)));
            if(code == null){
                String flag = redisOperator.get(DigestUtils.md5DigestAsHex((account+ Constant.REGISTER_SALT).
                        getBytes(StandardCharsets.UTF_8)));
                //查看是否获取过验证码
                if (flag == null) return Result.failure("请先获取验证码");
                else return Result.failure("验证码已经过期，请重新获取");
            }
            if(messageCode.equals(code)){//验证成功，开始保存
                LogisticsUserDO user = new LogisticsUserDO();
                BeanUtils.copyProperties(logisticsUserAddVO,user);
                logisticsUserService.insertUser(user);
                return Result.success().message("注册成功");
            }
            return Result.failure("验证码错误");
        }
    }

}
