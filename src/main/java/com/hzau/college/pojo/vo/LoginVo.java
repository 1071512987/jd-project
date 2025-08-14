package com.hzau.college.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("用户登录的返回结果")
public class LoginVo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("头像地址")
    private String icon;

    @ApiModelProperty("是否为新用户")
    private Boolean isNew;

    @ApiModelProperty("该用户所有角色")
    private List<String> roles;

    @ApiModelProperty(value = "此token为周转，无需理会", hidden = true)
    @JsonIgnore
    private String tempToken;
}
