package com.hzau.college.pojo.dto;

import com.hzau.college.pojo.po.admin.AdminSysRole;
import com.hzau.college.pojo.po.front.FrontSysRole;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysUserAndRolesDto {

    // 用户id
    private Long id;
    //手机号
    private String phone;
    //昵称
    private String nickname;
    //登录用的用户名
    private String username;
    //登录用的密码
    private String password;
    //加密所用盐
    private String salt;
    //头像地址
    private String icon;
    //创建的时间
    private Date createTime;
    //最后一次登录的时间
    private Date loginTime;
    //账号的状态，0是正常，1是锁定
    private Short status;

    // 用户类型
    private String userType;

    /*
        手动设置
     */

    private List<FrontSysRole> frontRoles;//用一对多映射查询，联u,u-r,r三表，集合里只放role的name

    private List<AdminSysRole> adminRoles;//用一对多映射查询，联u,u-r,r三表，集合里只放role的name




}
