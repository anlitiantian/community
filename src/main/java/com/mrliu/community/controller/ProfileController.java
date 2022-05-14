package com.mrliu.community.controller;

import com.github.pagehelper.PageInfo;
import com.mrliu.community.dto.NotificationDTO;
import com.mrliu.community.dto.QuestionDTO;
import com.mrliu.community.enums.NotificationStatusEnum;
import com.mrliu.community.model.Notification;
import com.mrliu.community.model.User;
import com.mrliu.community.service.NotificationService;
import com.mrliu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-28 21:02
 **/
@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request, @PathVariable(name = "action")String action,
                          @RequestParam(name = "page", defaultValue = "1")Integer pageNo,
                          @RequestParam(name = "size", defaultValue = "10")Integer size,
                          Model model){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }

        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
            //分页查询
            PageInfo<QuestionDTO> questionDTOList = questionService.listByAccountId(pageNo, size, user.getAccountId());
            model.addAttribute("sectionContent", questionDTOList);
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            // 转到回复页面
            PageInfo<NotificationDTO> notificationDTOList = notificationService.listByReceiverId(pageNo, size, user.getAccountId());
            model.addAttribute("sectionContent", notificationDTOList);
        }
        return "profile";
    }



}
