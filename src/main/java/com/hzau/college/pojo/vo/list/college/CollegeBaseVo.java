package com.hzau.college.pojo.vo.list.college;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放院校信息中——顶部卡片基本信息的Vo")
public class CollegeBaseVo {

    // logo

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("院校名")
    private String name;

    @ApiModelProperty("官网地址")
    private String website;

    @ApiModelProperty("创建时间")
    private String createDate;

    @ApiModelProperty("院校层次")
    private String levelName;

    @ApiModelProperty("办学形式【如公办】")
    private String organizationName;

    @ApiModelProperty("院校类型名称【如理工类】")
    private String typeName;

    @ApiModelProperty("隶属机构")
    private String department;

    @ApiModelProperty("所在省份")
    private String provinceName;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("所在区县")
    private String countyName;

    @ApiModelProperty("硕士点数量【>1说明院校有硕士点，0说明没有】")
    private Integer numMaster;

    @ApiModelProperty("博士点数量【>1说明院校有博士点，0说明没有】")
    private Integer numDoctor;

    // TODO allLabel
}
