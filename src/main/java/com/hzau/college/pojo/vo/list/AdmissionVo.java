package com.hzau.college.pojo.vo.list;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("")
public class AdmissionVo {

    private String id;
    // 组Id，对应唯一的院校名+专业名+专业备注（不含年份）
    private Integer collegeMajorGroupId;
    //专业id，同时也是三级专业id
    private Integer subjectId;
    //专业名称，同时也是三级专业名称【不含备注】
    private String subjectName;
    //专业备注
    private String subjectRemark;
    // 专业代码
    private String majorShortCode;
    //院校id
    private Integer collegeId;
    //院校名称
    private String collegeName;
    // 当年录取最低分
    private Integer lowRank;
    // 当年录取最低位次
    private Integer lowMark;
    // 记录年份
    private String year;
    // 对应的招生计划记录id
    private Integer projectId;
    //是否中外合作
    private Integer enNationalCoop;
    //是否校企合作
    private Integer enEnterpriseCoop;
    //是否地方专项
    private Integer enLocalProject;
    //是否师范类
    private Integer enTeacherKind;
    //专业需求各选科所对应的id集合
    //70000 - 70005物理、化学、生物、政治、历史、地理 70008 不限
    private String requireCourseId;
    //专业需求选科类型id
    //80001 不限 80002 多选一 80003 缺一不可
    private String requireCourseTypeId;
    //投档对应省份名称
    private String provinceName;
    //    //省分数线
//    private Integer provinceScore;
    //一级专业id
    private Integer firstId;
    //一级专业名称
    private String firstName;
    //二级专业id
    private Integer secondId;
    //二级专业名称
    private String secondName;
}

