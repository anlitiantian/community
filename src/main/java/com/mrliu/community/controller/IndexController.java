package com.mrliu.community.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.mrliu.community.dto.QuestionDTO;
import com.mrliu.community.mapper.QuestionMapper;
import com.mrliu.community.mapper.UserMapper;
import com.mrliu.community.model.Question;
import com.mrliu.community.model.User;
import com.mrliu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @Value("${gitee.redirect.uri}")
    private String redirectUri;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer pageNo,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        @RequestParam(name = "search", required = false) String search) {

        //前端只传了页码，其实每页显示的记录数也可以是个固定值
        PageInfo<QuestionDTO> questionDTOList = questionService.list(search, pageNo, size);
        model.addAttribute("questions", questionDTOList);
        if(StrUtil.isNotBlank(search)){
            model.addAttribute("search", search);
        }
        return "index";
    }
}
