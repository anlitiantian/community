package com.mrliu.community.dto;

import com.mrliu.community.model.Question;
import com.mrliu.community.model.User;
import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-05-09 11:19
 **/
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    // 标志已读/未读 0/1
    private Integer status;
    // 评论的对象id
    private Long parentId;
    // 标题（问题/评论）
    private String title;
    // 类型（用于在页面拼接，回复了问题、回复了评论等）
    private String type;
    // 评论人
    private User notifierUser;
    // 该评论所在的问题 id
    private Long questionId;
}
