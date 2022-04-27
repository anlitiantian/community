package com.mrliu.community.mapper;

import com.mrliu.community.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-26 16:53
 **/
public interface UserMapper {

    void insert(User user);

    User findByToken(@Param("token")String token);
}
