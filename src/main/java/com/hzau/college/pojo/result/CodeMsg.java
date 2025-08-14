package com.hzau.college.pojo.result;

import lombok.Getter;

@Getter
public enum CodeMsg {
    BAD_REQUEST(400, "请求出错"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    INTERNAL_SERVER_NO_RESPONDING(500, "子服务器错误"),

    OPERATE_OK(0, "操作成功"),
    SAVE_OK(0, "保存成功"),
    REMOVE_OK(0, "删除成功"),

    OPERATE_ERROR(40001, "操作失败"),
    SAVE_ERROR(40002, "保存失败"),
    REMOVE_ERROR(40003, "删除失败"),
    UPLOAD_IMG_ERROR(40004, "图片上传失败"),
    NO_INFO_UPLOAD(40005, "必要信息未上传"),
    VALIDATE_ERROR(40006, "后端校验错误"),


    WRONG_USERNAME(50001, "用户名不存在"),
    WRONG_PASSWORD(50002, "密码错误"),
    USER_LOCKED(50003, "用户被锁定，无法正常登录"),
    WRONG_CAPTCHA(50004, "验证码错误"),
    WRONG_PHONE(50005, "手机号格式错误"),
    REPEAT_LOGIN(50006, "重复登录"),
    NOT_SET_PASSWORD(50007, "密码未定义，请使用手机号登录"),
    NOT_MAN_MACHINE(50008, "未进行人机验证或已过期,请先验证"),

    LACK_PERMISSION(60001,"缺少相关权限"),
    NO_TOKEN(60002, "Token不能为空，请申请或登录后再访问"),
    TOKEN_EXPIRED(60003, "Token过期，请重新登陆"),
    TOKEN_FORMAT_ERROR(60004, "Token格式错误"),
    TOKEN_NOT_RENEWED(60005, "Token已重新发放，但您未使用，请重新登录并联系管理员"),
    TOKEN_LOAD_ERROR(60006, "Token内容不合法，可能已经被更改"),
    HAVE_PERMISSION(60007,"已拥有相关权限");


    private final int code;
    private final String msg;

    CodeMsg(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
