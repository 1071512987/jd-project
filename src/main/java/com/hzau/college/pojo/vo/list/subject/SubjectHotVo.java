package com.hzau.college.pojo.vo.list.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放专业热度基本信息的Vo")
public class SubjectHotVo {

    @ApiModelProperty("专业代码")
    private String subjectCode;

    @ApiModelProperty("学制")
    private String duration;

    @ApiModelProperty("男生占比")
    private String boyRate;

    @ApiModelProperty("【三级】专业id")
    private Integer subjectId;

    @ApiModelProperty("【三级】专业名称")
    private String subjectName;

    @ApiModelProperty("【总】浏览量")
    private String viewTotal;

}
