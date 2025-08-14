package com.hzau.college.pojo.vo.list;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("")
public class ProjectVo {
    private String id;
    // 组Id，对应唯一的院校名+专业名+专业备注（不含年份）
    private Integer collegeMajorGroupId;
    //专业id，同时也是三级专业id
    private Integer subjectId;
    //专业名称，同时也是三级专业名称【不含备注】
    private String subjectName;
    // 专业备注
    private String subjectRemark;
    // 专业代码
    private String majorShortCode;
    //院校id
    private Integer collegeId;
    //院校名称
    private String collegeName;
    //一级专业id
    private Integer firstId;
    //一级专业名称
    private String firstName;
    //二级专业id
    private Integer secondId;
    //二级专业名称
    private String secondName;
    // 当年计划学费
    private Integer tuition;
    // 当年计划招生人数
    private Integer number;
    // 当年计划学制
    private String duration;
    // 记录年份
    private String year;
    //计划对应省份名称
    private String provinceName;
    //专业需求各选科所对应的id集合
    private String requireCourseId;
    //专业需求选科类型id
    private String requireCourseTypeId;
    //是否中外合作
    private Integer enNationalCoop;
    //是否校企合作
    private Integer enEnterpriseCoop;
    //是否地方专项
    private Integer enLocalProject;
    //是否师范类、
    private Integer isTeacherKind;
}

