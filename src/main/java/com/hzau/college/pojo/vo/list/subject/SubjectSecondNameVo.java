package com.hzau.college.pojo.vo.list.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("存放二级专业名称集合的Vo")
public class SubjectSecondNameVo {

    @ApiModelProperty("二级专业名称")
    private String secondName;

    @ApiModelProperty("三级专业集合")
    private List<SubjectThirdNameVo> thirdNameVos;


}
