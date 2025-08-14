package com.hzau.college.pojo.vo.list.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("二级专业对象")
public class SecondSubjectVo {

    @ApiModelProperty("二级专业id")
    private String secondId;

    @ApiModelProperty("二级专业名称")
    private String secondName;

    @ApiModelProperty("包含的三级专业")
    private List<ThirdSubjectVo> children;
}
