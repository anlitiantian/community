package com.mrliu.community.controller;

import com.mrliu.community.mapper.UserMapper;
import com.mrliu.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-25 16:58
 **/
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        //检查cookies数组是否为空,在cookies中找是否有token,从而判断登陆状态
        if(cookies.length != 0){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("qiyu_token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        return "index";
    }
}
