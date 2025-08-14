package com.hzau.college.pojo.vo.req;

import com.hzau.college.common.annotation.validator.Phone;
import com.hzau.college.common.annotation.validator.PhoneCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel("用户登录接口，手机号验证码/用户名密码登录二者选其一【优先前者】")
public class LoginReqVo {

    @ApiModelProperty("用户类型【front、admin】")
    @Pattern(regexp = "front|admin", message = "用户类型格式错误") @NotNull(message = "用户类型不能为空")
    private String userType;

    @ApiModelProperty("登录注册的验证码")
    @PhoneCode
    private String code;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("手机号")
    @Phone
    private String phone;

    @ApiModelProperty("【用户首次注册时的】推荐人(如果数据库中有则不修改)")
    private Long promoterId;



}
