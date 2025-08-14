package com.hzau.college.pojo.vo.list.evaluate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放评测内容的Vo")
public class EvaluateBodyVo {

    @ApiModelProperty("评测名称")
    private String name;

    @ApiModelProperty("评测内容")
    private String body;
}

