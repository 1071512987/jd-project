package com.hzau.college.pojo.vo.list.subject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubjectJobAreaVo {

    @ApiModelProperty("地区")
    private String areaName;

    @ApiModelProperty("占比")
    private String rate;
}
