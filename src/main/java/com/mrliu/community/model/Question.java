package com.mrliu.community.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-27 14:20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
}
