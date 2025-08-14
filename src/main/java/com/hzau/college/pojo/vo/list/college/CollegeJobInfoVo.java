package com.hzau.college.pojo.vo.list.college;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("存放院校信息中——就业情况相关Vo")
public class CollegeJobInfoVo {

    @ApiModelProperty("出国率")
    private String abroadRate;

    @ApiModelProperty("就业率")
    private String jobRate;

    @ApiModelProperty("升学率")
    private String postgraduateRate;

    @ApiModelProperty("就业地区去向")
    private List<CollegeJobAreaVo> areas;

    @ApiModelProperty("就业单位去向")
    private String companies;


}

