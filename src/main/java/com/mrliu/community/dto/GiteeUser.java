package com.mrliu.community.dto;

import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-26 14:21
 **/
@Data
public class GiteeUser {
    private String login;
    private String id;
    private String bio;
    private String avatarUrl;
}
