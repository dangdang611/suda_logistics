package com.example.admin.exception;

import com.example.system.componet.Constant;
import com.example.system.exception.OperationException;
import com.example.system.vo.unify.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;

//异常统一处理
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //请求方式不支持
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                      HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址{ "+ requestURI +"},不支持'{}'请求" + e);
        return Result.failure(e.getMessage());
    }

    //拦截运行时异常
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址{ "+ requestURI +"},发生未知运行异常." + e);
        return Result.failure(e.getMessage());
    }

    //系统异常
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址{ "+ requestURI +"},发生系统异常." + e);
        return Result.failure(e.getMessage());
    }

    //接收前端参数引发的错误
    @ExceptionHandler(OperationException.class)
    public Result handleOperationException(OperationException e , HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.info("请求地址{ "+ requestURI +"},发生数据库操作异常." + e);
        return Result.failure(e.getMessage()).code(Constant.FAILURE_HTML_CODE);
    }

    //重要字段修改值变成重复引发的错误
    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e , HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.info("请求地址{ "+ requestURI +"},发生数据库操作异常." + e);
        return Result.failure("用户名，服务名，快递产品名等不能重复").code(Constant.FAILURE_HTML_CODE);
    }

}
