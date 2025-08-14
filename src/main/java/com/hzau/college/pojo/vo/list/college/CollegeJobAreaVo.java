package com.hzau.college.pojo.vo.list.college;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放院校就业地区去向Vo")
public class CollegeJobAreaVo {

    @ApiModelProperty("地区")
    private String area;

    @ApiModelProperty("占比")
    private String rate;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("年份")
    private String year;
}
