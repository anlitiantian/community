package com.mrliu.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mrliu.community.dto.NotificationDTO;
import com.mrliu.community.dto.QuestionDTO;
import com.mrliu.community.enums.NotificationStatusEnum;
import com.mrliu.community.enums.NotificationTypeEnum;
import com.mrliu.community.exception.CustomizeErrorCode;
import com.mrliu.community.exception.CustomizeException;
import com.mrliu.community.mapper.CommentMapper;
import com.mrliu.community.mapper.NotificationMapper;
import com.mrliu.community.mapper.QuestionMapper;
import com.mrliu.community.mapper.UserMapper;
import com.mrliu.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-05-09 11:19
 **/
@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    public PageInfo<NotificationDTO> listByReceiverId(Integer pageNo, Integer size, String accountId) {

        PageHelper.startPage(pageNo, size);
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(accountId);
        example.setOrderByClause("status desc, gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExample(example);

        //导航条默认显示5个
        PageInfo<Notification> notificationPageInfo = new PageInfo<>(notifications, 5);

        List<NotificationDTO> notificationDTOs = new ArrayList<>();
        for (Notification notification : notifications) {
            // 找到该评论的创建者
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(notification.getNotifier());
            User user = userMapper.selectByExample(userExample).get(0);

            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setNotifierUser(user);

            // 找到该评论的父类标题（问题就是问题标题，评论就是评论内容）
            if (notification.getType() == NotificationTypeEnum.REPLY_QUESTION.getType()) {
                Question question = questionMapper.selectByPrimaryKey(notification.getParentId());
                notificationDTO.setTitle(question.getTitle());
                notificationDTO.setType(NotificationTypeEnum.REPLY_QUESTION.getName());
            } else if (notification.getType() == NotificationTypeEnum.REPLY_COMMENT.getType()) {
                Comment comment = commentMapper.selectByPrimaryKey(notification.getParentId());
                notificationDTO.setTitle(comment.getDescription());
                notificationDTO.setType(NotificationTypeEnum.REPLY_COMMENT.getName());
            }
            notificationDTOs.add(notificationDTO);
        }

        // 分页
        PageInfo<NotificationDTO> notificationDTOPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(notificationPageInfo, notificationDTOPageInfo);
        notificationDTOPageInfo.setList(notificationDTOs);
        return notificationDTOPageInfo;
    }

    @Transactional
    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        // 将通知标记为已读
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        if (!notification.getReceiver().equals(user.getAccountId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setType(NotificationTypeEnum.nameOf(notification.getType()));
        return notificationDTO;
    }

    public Long unreadCount(String accountId) {
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(accountId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        long count = notificationMapper.countByExample(example);
        return count;
    }
}
