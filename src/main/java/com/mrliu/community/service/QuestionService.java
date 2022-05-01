package com.mrliu.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mrliu.community.dto.QuestionDTO;
import com.mrliu.community.exception.CustomizeErrorCode;
import com.mrliu.community.exception.CustomizeException;
import com.mrliu.community.mapper.QuestionMapper;
import com.mrliu.community.mapper.UserMapper;
import com.mrliu.community.model.Question;
import com.mrliu.community.model.QuestionExample;
import com.mrliu.community.model.User;
import com.mrliu.community.model.UserExample;
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
        List<Question> questions = questionMapper.selectByExample(new QuestionExample());

        //导航条默认显示5个
        PageInfo<Question> questionPageInfo = new PageInfo<>(questions, 5);

        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for(Question question : questions){
            //找到该问题的创建者
            UserExample example = new UserExample();
            example.createCriteria().andAccountIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(example);
            User user = users.get(0);

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

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(accountId);
        List<Question> questions = questionMapper.selectByExample(questionExample);

        //导航条默认显示5个
        PageInfo<Question> questionPageInfo = new PageInfo<>(questions, 5);

        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for(Question question : questions){
            //找到该问题的创建者
            UserExample example = new UserExample();
            example.createCriteria().andAccountIdEqualTo(question.getCreator());
            User user = userMapper.selectByExample(example).get(0);

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
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(question.getCreator());
        User user = userMapper.selectByExample(example).get(0);

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question, String isInserting) {

        if("Yes".equals(isInserting)){
            // 创建
            question.setGmtCreate(question.getGmtModified());
            questionMapper.insert(question);
        }else {
            //更新
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(question, example);
        }
    }
}
