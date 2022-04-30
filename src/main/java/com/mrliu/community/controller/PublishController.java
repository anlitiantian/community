package com.mrliu.community.controller;

import com.mrliu.community.dto.QuestionDTO;
import com.mrliu.community.model.Question;
import com.mrliu.community.model.User;
import com.mrliu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description: 保证这里操作的都是questionDTO
 * @author: Mr.Liu
 * @create: 2022-04-27 11:02
 **/
@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id, Model model){
        QuestionDTO question = questionService.getById(id);

        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(name = "isInserting")String isInserting,
                            @RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "tag", required = false) String tag,
                            HttpServletRequest request, Model model){
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

//        model.addAttribute("title",question.getTitle());
//        model.addAttribute("description",question.getDescription());
//        model.addAttribute("tag",question.getTag());
        //判断几种不符合的情况
        if(title == null || "".equals(title)){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if(description == null || "".equals(description)){
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if(tag == null || "".equals(tag)){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);

        //question里只有title、description、tag，其他信息需要补全
        question.setCreator(user.getAccountId());
        question.setGmtModified(System.currentTimeMillis());

        questionService.createOrUpdate(question, isInserting);
        return "redirect:/";
    }
}
