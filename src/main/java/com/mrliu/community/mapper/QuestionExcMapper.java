package com.mrliu.community.mapper;

import com.mrliu.community.model.Question;

import java.util.List;

/**
 * @program: community
 * @description: 对于自动生成代码的补充
 * @author: Mr.Liu
 * @create: 2022-05-01 20:38
 **/
public interface QuestionExcMapper {

    int increaseView(Question row);
    int increaseComment(Question row);
    // 根据传入question的tag查相关问题
    List<Question> selectRelated(Question question);
}
