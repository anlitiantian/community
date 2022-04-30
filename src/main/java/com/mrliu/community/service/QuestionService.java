package com.mrliu.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mrliu.community.dto.QuestionDTO;
import com.mrliu.community.mapper.QuestionMapper;
import com.mrliu.community.mapper.UserMapper;
import com.mrliu.community.model.Question;
import com.mrliu.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-27 21:55
 **/
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PageInfo<QuestionDTO> list(int pageNo, int size) {
        PageHelper.startPage(pageNo, size);
        List<Question> questions = questionMapper.list();
        //导航条默认显示5个
        PageInfo<Question> questionPageInfo = new PageInfo<>(questions, 5);

        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for(Question question : questions){
            User user = userMapper.selectByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }

        PageInfo<QuestionDTO> questionDTOPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(questionPageInfo,questionDTOPageInfo);
        questionDTOPageInfo.setList(questionDTOs);

        return questionDTOPageInfo;
    }

    public PageInfo<QuestionDTO> listByAccountId(int pageNo, int size, String accountId) {
        PageHelper.startPage(pageNo, size);
        List<Question> questions = questionMapper.listByAccountId(accountId);
        //导航条默认显示5个
        PageInfo<Question> questionPageInfo = new PageInfo<>(questions, 5);

        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for(Question question : questions){
            User user = userMapper.selectByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }

        PageInfo<QuestionDTO> questionDTOPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(questionPageInfo,questionDTOPageInfo);
        questionDTOPageInfo.setList(questionDTOs);

        return questionDTOPageInfo;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectById(id);
        User user = userMapper.selectByAccountId(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }
}
