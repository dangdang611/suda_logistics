package com.example.admin.componet;

import com.example.system.componet.Constant;
import com.example.system.componet.RedisOperator;
import com.example.system.service.LogisticsUserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SignatureException;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private RedisOperator redisOperator;

    private JwtOperator jwtOperator;

    private LogisticsUserService logisticsUserService;

    public LoginInterceptor setRedisOperator(RedisOperator redisOperator){
        this.redisOperator = redisOperator;
        return this;
    }

    public LoginInterceptor setJwtOperator(JwtOperator jwtOperator){
        this.jwtOperator = jwtOperator;
        return this;
    }

    public LoginInterceptor setLogisticsUserService(LogisticsUserService logisticsUserService){
        this.logisticsUserService = logisticsUserService;
        return this;
    }

    //目标方法执行前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 登录注册地址过滤
        String uri = request.getRequestURI() ;
        if (uri.contains("/login") || uri.contains("/register")){
            return true ;
        }

        // 后台地址，资讯显示
        if (uri.contains("/system") || uri.contains("/jobInformation") || uri.contains("/newsInformation")){
            return true;

        }

        //前台地址
        // Token 验证
        String token = request.getHeader(Constant.TOKEN_HEAD);
        if(StringUtils.isEmpty(token)){
            token = request.getParameter(Constant.TOKEN_HEAD);
        }

        if(StringUtils.isEmpty(token)){
            throw new SignatureException("请登录");
        }

        String tokenIsExists = redisOperator.get(token);//查看redis中有没有该token

        if(tokenIsExists == null)
            throw new SignatureException("请登录");

        Claims claims = null;
        try{
            claims = jwtOperator.getTokenClaim(token);
            if(claims == null || jwtOperator.isTokenExpired(claims.getExpiration())){
                if(tokenIsExists != null)redisOperator.expire(token,0);//存在该键值就设置过期
                throw new SignatureException(Constant.TOKEN_HEAD + "失效，请重新登录。");
            }
        }catch (Exception e){
            if(tokenIsExists != null)redisOperator.expire(token,0);//存在该键值就设置过期
            throw new SignatureException(Constant.TOKEN_HEAD + "失效，请重新登录。");
        }

        if(!logisticsUserService.exitsAccount(claims.getSubject()))//查无此用户
            throw new SignatureException(Constant.TOKEN_HEAD + "失效，请重新登录。");

        if(!claims.getSubject().equals(Constant.ROOT_USERNAME)) return true; //非管理员用户可以进入

        throw new SignatureException("用户权限不够");

    }

    //目标方法执行后（controller层方法执行完后）
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //log.info("postHandle执行{}", modelAndView);
    }

    //页面渲染后（请求执行完后）
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //log.info("afterCompletion执行异常{}", ex);
    }
}