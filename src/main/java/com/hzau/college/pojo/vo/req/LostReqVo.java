package com.hzau.college.pojo.vo.req;

import com.hzau.college.common.annotation.validator.Phone;
import com.hzau.college.common.annotation.validator.PhoneCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class LostReqVo {

    @ApiModelProperty("用户类型【front,admin】")
    @Pattern(regexp = "front|admin", message = "用户类型格式错误") @NotNull(message = "用户类型不能为空")
    private String userType;

    @ApiModelProperty("重置密码的验证码")
    @PhoneCode @NotBlank(message = "验证码不能为空")
    private String code;

    @ApiModelProperty("新密码")
    private String newPassword;

    @ApiModelProperty("手机号")
    @Phone @NotBlank(message = "手机号不能为空")
    private String phone;

}
