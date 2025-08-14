package com.hzau.college.pojo.vo.list.evaluate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
@ApiModel("存放评测分类列表的Vo【树形结构】")
public class EvaluateListVo {

    @ApiModelProperty("分类名")
    private String className;

    @ApiModelProperty("该分类下评测")
    private ArrayList<EvaluateInfoVo> infos;

}
