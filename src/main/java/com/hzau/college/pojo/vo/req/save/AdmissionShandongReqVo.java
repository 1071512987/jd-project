package com.hzau.college.pojo.vo.req.save;

import lombok.Data;

@Data
public class AdmissionShandongReqVo {
    
    private Integer specialId;
    //专业名称【含备注】
    private String subjectName;
    //院校id
    private Integer collegeId;
    //院校名称
    private String collegeName;
    //2022年录取最低分
    private Integer lowMark2022;
    //2022年录取最低位次
    private Integer lowRank2022;
    //2021年录取最低分
    private Integer lowMark2021;
    //2021年录取最低位次
    private Integer lowRank2021;
    //2020年录取最低分
    private Integer lowMark2020;
    //2021年录取最低位次
    private Integer lowRank2020;
    
    private String id;
    //id的数字后缀
    private Integer idSuffix;
    //专业id
    private Integer subjectId;
    //是否为中外合作【1代表是，2代表不是】
    private Short enZwhz;
    //2022年录取最高分
    private String highMark2022;
    //2021年录取最高分
    private String highMark2021;
    //2020年录取最高分
    private String highMark2020;
    //专业选科要求
    private String requireCourse;
    //专业需求各选科所对应的id集合
    private String requireCourseId;
    //专业需求选科类型id
    private String requireCourseTypeId;
    //省份所对应的id
    private Integer provinceId;
    //投档对应省份名称
    private String provinceName;
    //省分数线
    private Integer provinceScore;
    //分段
    private String batchName;
    //未知用处的id
    private String single;
}

