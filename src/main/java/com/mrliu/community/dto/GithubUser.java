package com.mrliu.community.dto;

import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-25 20:47
 **/
@Data
public class GithubUser {
    //登录名
    private String login;
    private Long id;

    private String bio;
    private String avatarUrl;
}
