package com.mrliu.community.dto;

import com.mrliu.community.model.User;
import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-05-01 21:59
 **/
@Data
public class CommentDTO {
    private Long id;

    private Long parentId;

    private Integer type;

    private Integer likeCount;

    private Integer commentCount;

    private String commentator;

    private Long gmtCreate;

    private Long gmtModified;

    private String description;

    private User user;
}
