package com.hzau.college.pojo.vo.list;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放补充用户信息的Vo")
public class FrontUserInfoVo {

    @ApiModelProperty("分数")
    private Integer mark;

    @ApiModelProperty("排名")
    private Integer rank;

    @ApiModelProperty("选科")
    private String course;

    @ApiModelProperty("区域")
    private String area;


}

