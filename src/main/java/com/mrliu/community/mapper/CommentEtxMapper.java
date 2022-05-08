package com.mrliu.community.mapper;

import com.mrliu.community.model.Comment;
import com.mrliu.community.model.CommentExample;
import com.mrliu.community.model.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentEtxMapper {

    int increaseComment(Comment row);
}