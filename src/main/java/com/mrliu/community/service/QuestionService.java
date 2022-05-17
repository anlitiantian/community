package com.mrliu.community.service;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mrliu.community.dto.QuestionDTO;
import com.mrliu.community.exception.CustomizeErrorCode;
import com.mrliu.community.exception.CustomizeException;
import com.mrliu.community.mapper.QuestionExcMapper;
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
import java.util.stream.Collectors;

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
    private QuestionExcMapper questionExcMapper;

    @Autowired
    private UserMapper userMapper;

    public PageInfo<QuestionDTO> list(String search, int pageNo, int size) {
        PageHelper.startPage(pageNo, size);

        // 按检索词搜索
        if(StrUtil.isNotBlank(search)){
            search = search.replace(" ", "|");
        }
        List<Question> questions = questionExcMapper.selectRelatedByWord(search);

        //导航条默认显示5个
        PageInfo<Question> questionPageInfo = new PageInfo<>(questions, 5);

        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : questions) {
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
        BeanUtils.copyProperties(questionPageInfo, questionDTOPageInfo);
        questionDTOPageInfo.setList(questionDTOs);

        return questionDTOPageInfo;
    }

    public PageInfo<QuestionDTO> listByAccountId(int pageNo, int size, String accountId) {
        PageHelper.startPage(pageNo, size);

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(accountId);
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExample(questionExample);

        //导航条默认显示5个
        PageInfo<Question> questionPageInfo = new PageInfo<>(questions, 5);

        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : questions) {
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
        BeanUtils.copyProperties(questionPageInfo, questionDTOPageInfo);
        questionDTOPageInfo.setList(questionDTOs);

        return questionDTOPageInfo;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(question.getCreator());
        User user = userMapper.selectByExample(example).get(0);

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question, String isInserting) {

        if ("Yes".equals(isInserting)) {
            // 创建
            question.setGmtCreate(question.getGmtModified());
            questionMapper.insertSelective(question);
        } else {
            // 更新
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(question, example);
        }
    }

    public void increaceView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExcMapper.increaseView(question);
    }

    public List<QuestionDTO> getRelatedQues(QuestionDTO questionDTO) {
        if (StrUtil.isBlank(questionDTO.getTag())) {
            return new ArrayList<>();
        }
        String regExp = StrUtil.replace(questionDTO.getTag(), ",", "|");
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regExp);

        List<Question> questions = questionExcMapper.selectRelated(question);
        List<QuestionDTO> questionDTOs = questions.stream().map(q -> {
            QuestionDTO dto = new QuestionDTO();
            BeanUtils.copyProperties(q, dto);
            return dto;
        }).collect(Collectors.toList());


        return questionDTOs;

    }
}
