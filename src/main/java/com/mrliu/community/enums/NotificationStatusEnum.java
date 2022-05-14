package com.mrliu.community.enums;

public enum NotificationStatusEnum {
    UNREAD(1),READ(0);
    private int status;

    NotificationStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
