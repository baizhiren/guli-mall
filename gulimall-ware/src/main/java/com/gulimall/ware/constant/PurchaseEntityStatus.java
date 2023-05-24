package com.gulimall.ware.constant;

public enum PurchaseEntityStatus {
    CREATED(0,"新建"),ASSIGNED(1,"已分配"),
    RECEIVE(2,"已领取"),FINISH(3,"已完成"),
    HASERROR(4,"有异常");
    private int code;
    private String msg;

    PurchaseEntityStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
