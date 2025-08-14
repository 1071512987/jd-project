package com.hzau.college.pojo.vo.list.college;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放院校基本排名的Vo")
public class CollegeRankBaseVo {

    @ApiModelProperty("wsl排名")
    private Integer wslRank;

    @ApiModelProperty("xyh排名")
    private Integer xyhRank;

    @ApiModelProperty("qs排名")
    private Integer qsRank;

    @ApiModelProperty("排名年份")
    private String year;
}
