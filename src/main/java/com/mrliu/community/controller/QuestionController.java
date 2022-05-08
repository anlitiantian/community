package com.mrliu.community.controller;

import com.mrliu.community.dto.CommentDTO;
import com.mrliu.community.dto.QuestionDTO;
import com.mrliu.community.enums.CommentTypeEnum;
import com.mrliu.community.service.CommentService;
import com.mrliu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-30 09:32
 **/
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id, Model model) {
        QuestionDTO questions = questionService.getById(id);
        List<QuestionDTO> questionsRelated = questionService.getRelatedQues(questions);

        List<CommentDTO> comments = commentService.listByParentId(id, CommentTypeEnum.QUESTION);

        // 增加阅读数
        questionService.increaceView(id);
        questions.setViewCount(questions.getViewCount() + 1);
        model.addAttribute("question", questions);
        model.addAttribute("comments", comments);
        model.addAttribute("questionsRelated", questionsRelated);
        return "question";
    }
}
