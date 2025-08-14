package com.hzau.college.pojo.vo.req.save.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Data
public class AdminSysUserReqVo {

    @ApiModelProperty("id【大于0代表更新，否则代表添加】")
    private Long id;

    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty(value = "昵称【不能为空】", required = true)
    private String nickname;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名【不能为空】", required = true)
    private String username;

    @ApiModelProperty(value = "密码【保存时不能为空；更新时可以为空，代表不修改，否则代表覆盖】")
    private String password;

    @Range(min = 0, max = 1, message = "状态只能是0或1")
    @ApiModelProperty(value = "账号的状态【0是正常，1是锁定，默认0】")
    private Short status;

    @ApiModelProperty("角色id【多个id之间用逗号,隔开】")
    private String roleIds;
}

