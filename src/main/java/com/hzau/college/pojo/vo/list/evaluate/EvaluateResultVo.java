package com.hzau.college.pojo.vo.list.evaluate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放评测结果的Vo")
public class EvaluateResultVo {

    @ApiModelProperty("评测结果类型，如ENFP，ISTG")
    private String value;

    @ApiModelProperty("评测结果内容")
    private String result;
}
