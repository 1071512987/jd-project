package com.hzau.college.pojo.vo.req.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdminSysUserPageReqVo extends KeywordPageReqVo {

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("用户名")
    private String username;
}
