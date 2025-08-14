package com.hzau.college.pojo.vo.list;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hzau.college.pojo.dto.guidance.CurrentYearInfoDto;
import com.hzau.college.pojo.dto.guidance.CurrentYearTypeDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PreferSubjectVo {

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

    // 需要遍历
    @ApiModelProperty("三年的数据 size=3")
    private List<CurrentYearInfoDto> info;

    /*
     三年的类型，最后只取第一年，此处仅为了强制合并数据
     */

    @JsonIgnore
    private List<CurrentYearTypeDto> type;

}
