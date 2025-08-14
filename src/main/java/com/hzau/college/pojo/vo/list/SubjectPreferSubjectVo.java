package com.hzau.college.pojo.vo.list;

import com.hzau.college.pojo.dto.guidance.CurrentYearInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("专业优先筛选：以【专业-院校】形式排序")
public class SubjectPreferSubjectVo {
    @ApiModelProperty("组Id，对应唯一的院校名+专业名+专业备注（不含年份）")
    private Integer collegeMajorGroupId;

    @ApiModelProperty("专业id,同时也是三级专业id")
    private String subjectId;

    @ApiModelProperty("专业名称，同时也是三级专业名称【不含备注】")
    private String subjectName;

    @ApiModelProperty("专业备注")
    private String subjectRemark;

    @ApiModelProperty("专业代码")
    private String subjectCode;

    @ApiModelProperty("院校id")
    private Integer collegeId;

    @ApiModelProperty("院校名称")
    private String collegeName;

    @ApiModelProperty("院校代码")
    private String collegeCode;

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

    @ApiModelProperty("是否中外合作")
    private Integer enNationalCoop;

    @ApiModelProperty("是否校企合作")
    private Integer enEnterpriseCoop;

    @ApiModelProperty("是否地方专项")
    private Integer enLocalProject;

    @ApiModelProperty("是否师范类")
    private Integer enTeacherKind;

    @ApiModelProperty("专业需求各选科所对应的id集合")
    private String requireCourseId;

    @ApiModelProperty("专业需求选科类型id")
    private String requireCourseTypeId;

    @ApiModelProperty("专业录取概率")
    private Integer admissionRate;

    @ApiModelProperty("专业填报风险【冲、稳、保】")
    private String risk;

    @ApiModelProperty("三年的数据 size=3")
    private List<CurrentYearInfoDto> info;


    //
//    //一级专业id
//    private Integer firstId;
//    //一级专业名称
//    private String firstName;
//    //二级专业id
//    private Integer secondId;
//    //二级专业名称
//    private String secondName;

}
