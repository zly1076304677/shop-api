package com.fh.shop.api.common;

public enum ResponseEnum {

    CART_PRODUCT_IS_NO(3002,"输入的商品信息不合法"),
    CART_PRODUCT_IS_NULL(3000,"添加的商品不存在"),
    CART_PRODUCT_IS_DOWN(3001,"商品已下架"),

    LOGIN_XINXI_IS_NULL(2000,"登录信息为空"),
    LOGIN_NAME_IS_NOT(2001,"会员名不存在"),
    LOGIN_PASSWORD_NOT(2002,"密码错误"),
    LOGIN_HEARD_IS_MISS(2003,"头信息丢失"),
    LOGIN_HEARD_IS_BUWANZHENG(2004,"头信息不完整"),
    LOGIN_HEARD_IS_BEICUAN(2005,"头信息别篡改"),
    LOGIN_TIME_CHAOSHI(2006,"登陆超时"),



    REG_Member_Is_NULL(1004,"信息为空"),
    GET_PHONE_IS_ESIST(1003,"手机号已存在"),
    GET_MAIL_IS_ESIST(1002,"邮箱已存在"),
    GET_MEMBERNAME_IS_ESIST(1001,"会员名已存在"),
   GET_MEMBER_IS_NULL(1000,"注册会员信息为空");

    private int code;
    private String msg;

    private ResponseEnum(int code, String msg) {
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
