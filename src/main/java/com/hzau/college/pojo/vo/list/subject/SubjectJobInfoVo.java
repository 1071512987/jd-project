package com.hzau.college.pojo.vo.list.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("存放专业信息中——就业情况相关Vo")
public class SubjectJobInfoVo {

    @ApiModelProperty("就业地区去向")
    private List<SubjectJobAreaVo> areas;

    @ApiModelProperty("就业行业去向")
    private List<SubjectJobIndustryVo> industries;

    @ApiModelProperty("就业职业去向")
    private List<SubjectJobPositionVo> positions;
}
