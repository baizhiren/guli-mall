package com.common.exception;

public enum CodeEnum {
    VALID_EXCEPTION(10001, "参数校验错误"),
    UNKNOWN_EXCEPTION(10000, "未知错误");

    int code;
    String message;

    CodeEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
