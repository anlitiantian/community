package com.mrliu.community.controller;

import com.mrliu.community.dto.NotificationDTO;
import com.mrliu.community.enums.NotificationTypeEnum;
import com.mrliu.community.model.User;
import com.mrliu.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-05-09 17:01
 **/
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String read(HttpServletRequest request,
                       @PathVariable(name = "id")Long id){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);

        if(NotificationTypeEnum.nameOf(notificationDTO.getType())){
            return "redirect:/question/" + notificationDTO.getQuestionId();
        }else {
            return "redirect:/";
        }
    }
}
