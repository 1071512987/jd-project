package com.hzau.college.pojo.vo.list.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户信息")
public class SysUserVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("用户名")
    private String username;

//    @ApiModelProperty("密码")
//    private String password;

    @ApiModelProperty("最后登录时间")
    // UNIX时间戳：从1970-1-1 0:0:0 开始到当前时间走过的毫秒数。无地域性限制
    private Long loginTime;

    @ApiModelProperty("账号的状态【0是正常，1是锁定，默认0】")
    private Short status;
}

