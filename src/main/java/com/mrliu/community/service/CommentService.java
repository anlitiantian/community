package com.mrliu.community.service;

import com.mrliu.community.dto.CommentDTO;
import com.mrliu.community.enums.CommentTypeEnum;
import com.mrliu.community.exception.CustomizeErrorCode;
import com.mrliu.community.exception.CustomizeException;
import com.mrliu.community.mapper.*;
import com.mrliu.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-05-01 22:22
 **/
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentEtxMapper commentEtxMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExcMapper questionExcMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_COMMENT_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_COMMENT_NOT_FOUND);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);

            dbComment.setCommentCount(1);
            commentEtxMapper.increaseComment(dbComment);
        }else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);

            question.setCommentCount(1);
            questionExcMapper.increaseComment(question);
        }
    }

    public List<CommentDTO> listByParentId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if(comments.size() == 0){
            return new ArrayList<>();
        }
        // 由于每个评论对应的评论者有可能有重复，所以需要去重
        List<String> userIds = comments.stream().map(comment -> comment.getCommentator()).distinct().collect(Collectors.toList());

        // 获取评论人并转换为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getAccountId(), user -> user));

        //转换comment 为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
