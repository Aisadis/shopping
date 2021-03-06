package com.neuedu.common;

/**
 * d定义响应状态码
 */
public enum  ResponseCode {

    PARAM_EMPTY(2,"参数为空"),
    EXISTS_USERNAME(3,"用户名已存在"),
    EXISTS_EMAIL(4,"邮箱已存在"),
    NOT_EXISTS_USERNAME(5,"用户名不存在"),
    USER_NOT_LOGIN(6,"用户未登录"),
    NO_PRIVILEGE(7,"无权限操作");


    private  int status;
    private  String msg;

     ResponseCode() {

    }

     ResponseCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public enum ProductStatusEnum{
         PRODUGT_ONLINE(1,"在售"),
        PRODUGT_OFFINE(2,"下架"),
        PRODUGT_DELETE(3,"删除"),
        ;
         private int code;
         private String desc;
        ProductStatusEnum(int code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }


}
