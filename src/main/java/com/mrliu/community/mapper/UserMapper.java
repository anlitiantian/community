package com.mrliu.community.mapper;

import com.mrliu.community.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-26 16:53
 **/
@Mapper
public interface UserMapper {

    void insert(User user);

}
