package com.mrliu.community.controller;

import cn.hutool.core.util.StrUtil;
import com.mrliu.community.dto.CommentDTO;
import com.mrliu.community.dto.ResultDTO;
import com.mrliu.community.enums.CommentTypeEnum;
import com.mrliu.community.exception.CustomizeErrorCode;
import com.mrliu.community.model.Comment;
import com.mrliu.community.model.User;
import com.mrliu.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-05-01 21:53
 **/
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            //这里返回实体类而不是抛出异常，便于前端处理
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentDTO == null || StrUtil.isBlankIfStr(commentDTO.getDescription())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        //只有description、parentId、type是从commentDTO中传过来的
        commentDTO.setCommentator(user.getAccountId());
        commentDTO.setGmtModified(System.currentTimeMillis());
        commentDTO.setGmtCreate(commentDTO.getGmtModified());

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO,comment);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable(name = "id")Long id){
        List<CommentDTO> commentDTOS = commentService.listByParentId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
