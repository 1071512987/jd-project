package com.hzau.college.pojo.vo.list.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放专业信息中——顶部卡片基本信息的Vo")
public class SubjectBaseInfoVo {

    @ApiModelProperty("【三级】专业id")
    private Integer subjectId;

    @ApiModelProperty("【三级】专业名称")
    private String subjectName;

    @ApiModelProperty("【二级】专业名称")
    private String secondName;

    @ApiModelProperty("【一级】专业名称")
    private String firstName;

    @ApiModelProperty("专业层次【本科、专科】")
    private String subjectLevel;

    @ApiModelProperty("学制")
    private String duration;

    @ApiModelProperty("授予学位")
    private String degree;
}
