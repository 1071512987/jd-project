package com.hzau.college.pojo.vo.list.college;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放院校详细信息【用于对比】的Vo")
public class CollegeCompVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("院校名")
    private String name;

    @ApiModelProperty("院校类型名称【如理工类】")
    private String typeName;

    @ApiModelProperty("办学形式【如公办】")
    private String organizationName;

    @ApiModelProperty("院校层次")
    private String levelName;

    @ApiModelProperty("隶属机构")
    private String department;

    @ApiModelProperty("创建时间")
    private String createDate;

    @ApiModelProperty("所在省份")
    private String provinceName;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("所在区县")
    private String countyName;

    @ApiModelProperty("国家重点学科数量【>1说明院校有国家重点学科，0说明没有】")
    private Integer numSubject;

    @ApiModelProperty("硕士点数量【>1说明院校有硕士点，0说明没有】")
    private Integer numMaster;

    @ApiModelProperty("博士点数量【>1说明院校有博士点，0说明没有】")
    private Integer numDoctor;

    @ApiModelProperty("院士数量【>1说明院校有院士，0说明没有】")
    private Integer numAcademician;

    @ApiModelProperty("是否为985院校【1代表是，2代表不是】")
    private String en985;

    @ApiModelProperty("是否为211院校【1代表是，2代表不是】")
    private String en211;

    @ApiModelProperty("是否为双一流院校【1代表是，2代表不是】")
    private String enDualClass;

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

    //院校男生比例【百分比】
    private Short boyRatio;

    @ApiModelProperty("院校logo")
    private String logo;
    //院校官网
    private String collegeSite;
    //院校地址
    private String address;
    //院校介绍
    private String intro;
    //【不含中外合作】山东省2022年录取最低分
    private Integer shandongLowMark2022;
    //【不含中外合作】山东2022录取最低位次
    private Integer shandongLowRank2022;
    //【含中外合作】山东省2022年录取最低分
    private Integer shandongLowMarkReal2022;
    //【含中外合作】山东2022录取最低位次
    private Integer shandongLowRankReal2022;
    //院校热度
    private String collegeHot;

}

