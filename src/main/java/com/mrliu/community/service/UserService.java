package com.mrliu.community.service;

import com.mrliu.community.mapper.UserMapper;
import com.mrliu.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        User userOrNull = userMapper.selectByAccountId(user.getAccountId());
        if(userOrNull != null){
            //除了创建时间、账户id，其他都可能会变
            user.setGmtCreate(userOrNull.getGmtCreate());
            userMapper.update(user);
        }else {
            //将用户的信息存入数据库
            userMapper.insert(user);
        }
    }
}
