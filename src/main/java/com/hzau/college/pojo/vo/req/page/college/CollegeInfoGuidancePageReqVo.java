package com.hzau.college.pojo.vo.req.page.college;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hzau.college.common.annotation.validator.*;
import com.hzau.college.pojo.vo.req.page.PageReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("院校优先筛选时传递的参数对象")
public class CollegeInfoGuidancePageReqVo extends PageReqVo {

    @ApiModelProperty("教育等级【本科、专科】")
    @CollegeEducationLevel
    private String levelName;

    @ApiModelProperty("所在省份id【多个省份id以逗号,分隔】")
    private String provinceId;

    @ApiModelProperty("大学类型【如综合、理工、师范】【多个类型以逗号,分隔】")
    @CollegeType
    private String typeName;

    @ApiModelProperty("是否为985院校【1说明是，0说明不是】")
    @BoolPlusNumber
    private String en985;

    @ApiModelProperty("是否为211院校【1说明是，0说明不是】")
    @BoolPlusNumber
    private String en211;

    @ApiModelProperty("是否为双一流院校【1说明是，0说明不是】")
    @BoolPlusNumber
    private String enDualClass;

    @ApiModelProperty(value = "办学组织【公办、民办、中外合作】")
    @Organization
    private String organizationName;

    @ApiModelProperty(value = "需要搜索的院校id【多个id以逗号,分隔】")
    private String searchCollegeId;

//    @ApiModelProperty(value = "考生分数", required = true, position = 998)
//    private Integer realMark;
//
//    @ApiModelProperty(value = "考生位次", required = true, position = 997)
//    private Integer realRank;

    @ApiModelProperty(value = "专业分数范围【格式为(最低分,最高分)，以逗号分隔】", required = true, position = 999)
    @MarkRange
    private String markRange;

    @JsonIgnore
    @ApiModelProperty("筛选的临界最低分数段，为冲、稳、保设计")
    private Integer filterLowMark;

    @JsonIgnore
    @ApiModelProperty("筛选的临界最高分数段，为冲、稳、保设计")
    private Integer filterHighMark;

    @Override
    public long getPage() {
        return Math.min(super.getPage(), 20);
    }

}

