package com.mrliu.community.mapper;

import com.mrliu.community.model.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-27 14:07
 **/
public interface QuestionMapper {

    void insert(Question question);

    List<Question> list();

    List<Question> listByAccountId(@Param("accountId")String accountId);

    Question selectById(@Param("id")Integer id);
}
