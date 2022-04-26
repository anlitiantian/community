package com.mrliu.community.model;

import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-26 16:54
 **/
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
}
