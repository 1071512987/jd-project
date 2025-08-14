package com.hzau.college.pojo.vo.list.college;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放热门院校排名基本信息")
public class CollegeHotVo {

    @ApiModelProperty("院校id")
    private Integer collegeId;

    @ApiModelProperty("院校名称")
    private String collegeName;

    @ApiModelProperty("院校热度")
    private String collegeHot;

}
