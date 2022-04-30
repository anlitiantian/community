package com.mrliu.community.service;

import com.mrliu.community.mapper.UserMapper;
import com.mrliu.community.model.User;
import com.mrliu.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-30 10:40
 **/
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);

        if(users.size() != 0){
            //除了创建时间、账户id，其他都可能会变
            user.setGmtCreate(users.get(0).getGmtCreate());

            UserExample example = new UserExample();
            example.createCriteria().andAccountIdEqualTo(user.getAccountId());
            userMapper.updateByExampleSelective(user, example);
        }else {
            //将用户的信息存入数据库
            userMapper.insert(user);
        }
    }
}
