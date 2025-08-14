package com.hzau.college.pojo.vo.list.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("三级专业对象")
public class ThirdSubjectVo {

    @ApiModelProperty("三级专业id")
    private String subjectId;

    @ApiModelProperty("三级专业名称")
    private String subjectName;
}
