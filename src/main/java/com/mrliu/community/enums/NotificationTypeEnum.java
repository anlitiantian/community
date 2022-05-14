package com.mrliu.community.enums;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-05-09 10:43
 **/
public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论")
    ;
    private int type;
    private String name;

    public static String nameOf(Integer type) {
        for (NotificationTypeEnum typeEnum : NotificationTypeEnum.values()){
            if(typeEnum.getType() == type){
                return typeEnum.getName();
            }
        }
        return null;
    }

    public static boolean nameOf(String type) {
        for (NotificationTypeEnum typeEnum : NotificationTypeEnum.values()){
            if(typeEnum.getName() == type){
                return true;
            }
        }
        return false;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int status, String naem) {
        this.type = status;
        this.name = naem;
    }
}
