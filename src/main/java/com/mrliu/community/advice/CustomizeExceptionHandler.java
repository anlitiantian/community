package com.mrliu.community.advice;

import com.mrliu.community.dto.ResultDTO;
import com.mrliu.community.exception.CustomizeErrorCode;
import com.mrliu.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-30 20:50
 **/
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    Object handle(HttpServletRequest request, Throwable ex, Model model){
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            //返回json
            if(ex instanceof CustomizeException){
                return ResultDTO.errorOf((CustomizeException) ex);
            }else {
                return ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
        }else {
            //错误页面跳转
            if(ex instanceof CustomizeException){
                model.addAttribute("message",ex.getMessage());
            }else {
                model.addAttribute("message",CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }

}
