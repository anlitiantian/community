package com.mrliu.community.enums;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-05-01 22:19
 **/
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if(commentTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
