package com.hzau.college.pojo.vo.list.college;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放院校基本信息【用于基本div条目中展示】的Vo")
public class CollegeSearchVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("院校名")
    private String name;

    @ApiModelProperty("教育等级")
    private String educationLevel;

    @ApiModelProperty("院校代码")
    private String collegeCode;

    @ApiModelProperty("创建时间")
    private String createDate;

    @ApiModelProperty("隶属机构")
    private String department;

    @ApiModelProperty("院校类型【如理工类】")
    private String typeName;

    @ApiModelProperty("办学形式【如公办】")
    private String organizationName;

    @ApiModelProperty("院校层次【如本科】")
    private String levelName;

    @ApiModelProperty("所在省份")
    private String provinceName;

    @ApiModelProperty("所在市区")
    private String cityName;

    @ApiModelProperty("所在区县")
    private String countyName;

    @ApiModelProperty("是否为985院校【1代表是，0代表不是】")
    private Short en985;

    @ApiModelProperty("是否为211院校【1代表是，0代表不是】")
    private Short en211;

    @ApiModelProperty("是否为双一流院校【1代表是，0代表不是】")
    private Short enShuangYiLiu;

    @ApiModelProperty("简单标签")
    private String simpleLabel;

    @ApiModelProperty("所有标签")
    private String allLabel;

    @ApiModelProperty("校友会排行【中国】")
    private Integer wslRank;

    @ApiModelProperty("武书连排名【中国】")
    private Integer xyhZhRank;

    @ApiModelProperty("软科排名【中国】")
    private Integer rkZhRank;

    @ApiModelProperty("泰晤士排名【中国】")
    private String twsRank;

    @ApiModelProperty("泰晤士排名【中国】排序")
    private Integer twsRankSort;

    @ApiModelProperty("US排名【世界】")
    private Integer usRank;

    @ApiModelProperty("QS排名【世界】")
    private String qsRank;

    @ApiModelProperty("QS排名【世界】排序")
    private Integer qsRankSort;

    @ApiModelProperty("排名分数")
    private Integer rankMark;

    @ApiModelProperty("大学热度")
    private String collegeHot;

    @ApiModelProperty("院校logo")
    private String logo;

    @ApiModelProperty("院校官网")
    private String website;

}

