package com.example.system.exception;

//前端操作错误
public class OperationException extends RuntimeException{

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    public OperationException() {
        super();
    }
    public OperationException(String message) {
        super(message);
    }
}
