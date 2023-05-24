package com.gulimall.ware.constant;

public enum PurchaseDetailStatus {
    CREATED(0,"新建"),ASSIGNED(1,"已分配"),
    BUYING(2,"正在采购"),FINISH(3,"已完成"),
    HASERROR(4,"采购失败");
    private int code;
    private String msg;

    PurchaseDetailStatus(int code,String msg){
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
