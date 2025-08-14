package com.hzau.college.pojo.vo.list.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("角色信息")
public class SysRoleVo {

    @ApiModelProperty("id")
    private Short id;

    @ApiModelProperty("名称")
    private String name;
}

