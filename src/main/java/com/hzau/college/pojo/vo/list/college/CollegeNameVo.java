package com.hzau.college.pojo.vo.list.college;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放院校名称及其简称的Vo")
public class CollegeNameVo {

    @ApiModelProperty("院校id")
    private String id;

    @ApiModelProperty("院校名称")
    private String name;

    @ApiModelProperty("院校简称")
    private String shortName;

}

