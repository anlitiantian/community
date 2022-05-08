package com.mrliu.community.exception;

/**
 * @program: community
 * @description: 自定义异常类
 * @author: Mr.Liu
 * @create: 2022-04-30 21:00
 **/
public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode code){
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
