package com.gulimall.product.constant;

public class Attr {
    public enum AttrType{
        Sale("sale", 0),
        Base("base", 1);

        String type;
        int code;
        AttrType(String type, int code){
            this.type = type;
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public int getCode() {
            return code;
        }
    }


}
