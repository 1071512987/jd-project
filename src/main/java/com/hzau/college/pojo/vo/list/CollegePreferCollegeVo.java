package com.hzau.college.pojo.vo.list;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("院校优先筛选：以【院校-专业】形式排序")
public class CollegePreferCollegeVo {

        @ApiModelProperty("院校id")
        private Integer collegeId;

        @ApiModelProperty("院校名称")
        private String collegeName;

        @ApiModelProperty("院校层次")
        private String collegeLevelName;

        @ApiModelProperty("办学组织【如公办】")
        private String collegeOrganizationName;

        @ApiModelProperty("院校类型")
        private String collegeTypeName;

        @ApiModelProperty("隶属机构")
        private String collegeDepartment;

        @ApiModelProperty("院校所在省份")
        private String collegeProvinceName;

        @ApiModelProperty("院校所在城市")
        private String collegeCityName;

        @ApiModelProperty("院校所在区县")
        private String collegeCountyName;

        @ApiModelProperty("院校所有标签")
        private String collegeAllLabel;

        /*
                院校的计划招生信息以及真实录取信息,仅仅由查询出的结果决定
         */

        @ApiModelProperty("院校计划招生人数")
        private Integer collegeNumber;

        @ApiModelProperty("院校录取最低分")
        private Integer collegeLowMark;

        @ApiModelProperty("院校录取最低位次")
        private Integer collegeLowRank;

        @ApiModelProperty("院校录取概率")
        private Integer collegeAdmissionRate;

        @ApiModelProperty("院校填报风险【冲、稳、保】")
        private String collegeRisk;

        @ApiModelProperty("院校下含的专业列表")
        private List<PreferSubjectVo> children;





}
