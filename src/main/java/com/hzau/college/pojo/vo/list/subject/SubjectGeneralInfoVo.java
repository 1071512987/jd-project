package com.hzau.college.pojo.vo.list.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("存放专业信息中——专业概况模块的Vo")
public class SubjectGeneralInfoVo {

    @ApiModelProperty("相似专业")
    private List<ThirdSubjectVo> relation;

    @ApiModelProperty("男生占比")
    private String boyRate;

    @ApiModelProperty("专业简介")
    private String intro;

    @ApiModelProperty("就业前景")
    private String job;

    @ApiModelProperty("从事工作")
    private String doing;

    @ApiModelProperty("升学方向")
    private String postgraduateDirection;

    @ApiModelProperty("专业课程")
    private String learn;

    @ApiModelProperty("专业名人")
    private String celebrity;


}
