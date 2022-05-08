package com.mrliu.community.dto;

import com.mrliu.community.exception.CustomizeErrorCode;
import com.mrliu.community.exception.CustomizeException;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-05-01 22:24
 **/
@Data
@AllArgsConstructor
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorOf(CustomizeErrorCode noLogin) {
        ResultDTO resultDTO = new ResultDTO(noLogin.getCode(), noLogin.getMessage(), null);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeException exception){
        return new ResultDTO(exception.getCode(),exception.getMessage(), null);
    }

    public static ResultDTO okOf(){
        return new ResultDTO(200,"success",null);
    }
    public static <T> ResultDTO okOf(T data){
        return new ResultDTO(200,"success",data);
    }
}
