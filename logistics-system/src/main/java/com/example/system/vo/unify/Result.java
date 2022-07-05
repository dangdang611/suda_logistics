package com.example.system.vo.unify;

import com.example.system.componet.Constant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("统一返回结果类")
public class Result<T> implements Serializable{

    static final Long serialVersionUID = 1L;

    @ApiModelProperty("状态码")
    private String code;

    @ApiModelProperty("提示消息（用户）")
    private String message;

    @ApiModelProperty("提示消息（开发人员）")
    private String errorMessage;

    @ApiModelProperty("响应数据")
    private T data;

    //私有化构造方法
    private Result() {}

    //成功包装
    public static Result success(){
        return new Result().code(Constant.SUCCESS_CODE).data(null).message("success");
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>().code(Constant.SUCCESS_CODE).data(data).message("receive success");//接收成功
    }

    public static <T> Result<T> success(String code,String message,T data) {
        return new Result<T>().code(code).data(data).message(message);//接收成功
    }

    //失败包装
    public static <T> Result<T> failure(String message) {
        return new Result<T>().code(Constant.FAILURE_JAVA_CODE).data(null).message(message);
    }

    public static <T> Result<T> failure(String code,String message) {
        return new Result<T>().code(code).data(null).message(message);
    }

    public static <T> Result<T> failure(String code,String message,T data) {
        return new Result<T>().code(code).data(data).message(message);
    }

    //链式编程
    public Result<T> code(String code) {
        this.code = code;
        return this;
    }

    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    public Result<T> errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }
}

