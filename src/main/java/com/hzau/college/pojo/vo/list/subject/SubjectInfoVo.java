package com.hzau.college.pojo.vo.list.subject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubjectInfoVo {
    @ApiModelProperty("专业id，同时也是三级专业id")
    private Integer subjectId;

    @ApiModelProperty("专业名称，同时也是三级专业名称【不含备注】")
    private String subjectName;

    @ApiModelProperty("专业备注")
    private String subjectRemark;

    @ApiModelProperty("专业代码")
    private String subjectCode;

    @ApiModelProperty("专业层次")
    private String subjectLevel;

    @ApiModelProperty("当年录取最低分")
    private Integer lowRank;

    @ApiModelProperty("当年录取最低位次")
    private Integer lowMark;

    @ApiModelProperty("当年【计划】招生人数")
    private Integer number;

    @ApiModelProperty("当年学制")
    private String duration;

    @ApiModelProperty("当年学费")
    private String tuition;

    @ApiModelProperty("专业需求各选科所对应的id集合，70000-70005：物理、化学、生物、政治、历史、地理;70008:不限")
    private String requireCourseId;

    @ApiModelProperty("专业需求选科类型id,80001:不限;80002:多选一;80003:缺一不可")
    private String requireCourseTypeId;



}
