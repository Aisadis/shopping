package com.neuedu.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 封装返回前端的高复用对象
 * @param <T>
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse <T>{
    //状态码
    private  int status;
    //    返回接口数据
    private   T data;
    //    接口提示信息
    private  String msg;

    private  ServerResponse(){
    }
    private  ServerResponse(int status){
        this.status=status;
    }
    private  ServerResponse(T data){
        this.data = data ;
    }
    private  ServerResponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }
    private  ServerResponse(int status,T data,String msg){
        this.status = status;
        this.data = data ;
        this.msg = msg;
    }
    /**封装一个方法判断我们接口是否调用成功
     */
     @JsonIgnore
    public  boolean isSuccess(){
        return  this.status == Const.SUCCESS_CODE;
    }

    /**
     *
     * 接口返回成功
     * 在common中创建一个常量Const类，设置返回结果，用于ServerResponse调用
     * @return
     */
    public static ServerResponse createServerResponseBySuccess(){
        return  new ServerResponse(Const.SUCCESS_CODE);

    }
    public static ServerResponse createServerResponseBySuccess(String msg){
        return  new ServerResponse(Const.SUCCESS_CODE,msg);

    }

    /**
     * 三种参数都需要。就需要传上泛型《T> 将方法定位泛型方法
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ServerResponse createServerResponseBySuccess(String msg,T data){
        return  new ServerResponse(Const.SUCCESS_CODE,data,msg);

    }

    /**
     * 返回失败
     * @return
     */
     public static ServerResponse createServerResponseByError(){
         return  new ServerResponse(Const.SUCCESS_ERROR);
     }
    public static ServerResponse createServerResponseByError(String msg){
        return  new ServerResponse(Const.SUCCESS_ERROR,msg);
    }
    public static ServerResponse createServerResponseByError(int status){
        return  new ServerResponse(status);
    }
    public static ServerResponse createServerResponseByError(int status,String msg){
        return  new ServerResponse(status,msg);
    }




    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
