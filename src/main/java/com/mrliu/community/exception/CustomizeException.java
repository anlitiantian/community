package com.mrliu.community.exception;

/**
 * @program: community
 * @description: 自定义异常类
 * @author: Mr.Liu
 * @create: 2022-04-30 21:00
 **/
public class CustomizeException extends RuntimeException{
    private String message;

    public CustomizeException(ICustomizeErrorCode code){
        this.message = code.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
