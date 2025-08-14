package com.hzau.college.pojo.vo.list.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放专业信息中——就业行业去向相关信息的Vo")
public class SubjectJobIndustryVo {

    @ApiModelProperty("行业名称")
    private String industryName;

    @ApiModelProperty("行业占比")
    private String rate;
}
