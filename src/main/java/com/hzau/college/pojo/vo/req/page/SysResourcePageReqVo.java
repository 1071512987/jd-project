package com.hzau.college.pojo.vo.req.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysResourcePageReqVo extends KeywordPageReqVo {


    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("权限标识")
    private String permission;

    @ApiModelProperty("URI")
    private String uri;
}


