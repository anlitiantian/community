package com.mrliu.community.controller;

import com.mrliu.community.mapper.QuestionMapper;
import com.mrliu.community.model.Question;
import com.mrliu.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-27 11:02
 **/
@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Question question, HttpServletRequest request, Model model){
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        if(question.getTitle() == null || "".equals(question.getTitle())){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if(question.getDescription() == null || "".equals(question.getDescription())){
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if(question.getTag() == null || "".equals(question.getTag())){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        //question里只有title、description、tag，其他信息需要补全
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.insert(question);
        return "redirect:/";
    }
}
