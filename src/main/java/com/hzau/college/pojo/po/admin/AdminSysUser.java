package com.hzau.college.pojo.po.admin;

import lombok.Data;

import java.util.Date;

@Data
public class AdminSysUser {
    //主键
    private Long id;
    //手机号码
    private String phone;
    //昵称
    private String nickname;
    //登录用的用户名
    private String username;
    //登录用的密码
    private String password;
    //加密所用盐
    private String salt;
    private String icon;
    //创建的时间
    private Date createTime;
    //最后一次登录的时间
    private Date loginTime;
    //账号的状态，0是正常，1是锁定
    private Short status;
}

