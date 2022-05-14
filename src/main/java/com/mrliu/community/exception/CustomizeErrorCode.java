package com.mrliu.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"您请求的问题不存在，要不换一个试试？"),
    TARGET_COMMENT_NOT_FOUND(2002,"您回复的评论找不到（可能被删除）！"),
    NO_LOGIN(2003,"您当前尚未登录呦"),
    SYSTEM_ERROR(2004,"客官，服务太热了，要不凉快会儿再过来~"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"您操作的评论不存在，要不换一个试试"),
    CONTENT_IS_EMPTY(2007,"评论的内容怎么能是空呢！"),
    READ_NOTIFICATION_FAIL(2008,"你怎么能读别人的通知呢！"),
    NOTIFICATION_NOT_FOUND(2009,"你找的消息不存在哦"),

    ;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
