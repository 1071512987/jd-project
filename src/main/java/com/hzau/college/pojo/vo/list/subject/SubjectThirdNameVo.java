package com.hzau.college.pojo.vo.list.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放三级专业基本信息的Vo")
public class SubjectThirdNameVo {

    @ApiModelProperty("【三级】专业id")
    private Integer thirdId;

    @ApiModelProperty("【三级】专业名称")
    private String thirdName;
}
