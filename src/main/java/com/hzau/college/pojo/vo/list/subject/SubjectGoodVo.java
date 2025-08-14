package com.hzau.college.pojo.vo.list.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("存放优秀专业基本信息的Vo")
public class SubjectGoodVo {

    @ApiModelProperty("重点专业")
    private List<SubjectThirdNameVo> keySubject;

    @ApiModelProperty("省重点专业")
    private List<SubjectThirdNameVo> provinceKeySubject;

    @ApiModelProperty("国家重点专业")
    private List<SubjectThirdNameVo> nationalKeySubject;
}
