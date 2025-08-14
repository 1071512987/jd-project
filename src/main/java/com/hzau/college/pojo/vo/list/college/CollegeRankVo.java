package com.hzau.college.pojo.vo.list.college;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放院校基本信息及排名的Vo")
public class CollegeRankVo {

    @ApiModelProperty("id")
    @JsonIgnore
    private Integer id;

    @ApiModelProperty("院校id")
    private Integer collegeId;

    @ApiModelProperty("院校名称")
    private String collegeName;

    @ApiModelProperty("院校所有标签")
    private String allLabel;

    @ApiModelProperty("所在名称")
    private String provinceName;

    @ApiModelProperty("所在市区")
    private String cityName;

    @ApiModelProperty("所在区县")
    private String countyName;

    @ApiModelProperty("院校类型【如理工类】")
    private String typeName;

    @ApiModelProperty("办学形式【如公办】")
    private String organizationName;

    @ApiModelProperty("隶属机构")
    private String department;

    @ApiModelProperty("排名类型【如wsl】")
    private String rankType;

    @ApiModelProperty("名次")
    private Integer rank;

    @ApiModelProperty("排名所属年份")
    private String year;

    @ApiModelProperty("院校热度")
    private String collegeHot;


}

