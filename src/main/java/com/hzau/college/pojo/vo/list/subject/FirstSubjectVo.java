package com.hzau.college.pojo.vo.list.subject;

import com.hzau.college.pojo.vo.list.subject.SecondSubjectVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("一级专业对象")
public class FirstSubjectVo {

    @ApiModelProperty("一级专业id")
    private String firstId;

    @ApiModelProperty("一级专业名称")
    private String firstName;

    @ApiModelProperty("包含的二级专业")
    private List<SecondSubjectVo> children;
}
