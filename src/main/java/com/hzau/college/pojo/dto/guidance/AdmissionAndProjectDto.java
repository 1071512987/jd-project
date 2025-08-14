package com.hzau.college.pojo.dto.guidance;

import lombok.Data;

import java.util.List;

@Data
public class AdmissionAndProjectDto {

    private Integer collegeId;

    private String collegeName;

    // 组Id，对应唯一的院校名+专业名+专业备注（不含年份）
    private Integer collegeMajorGroupId;

    //专业id，同时也是三级专业id
    private String subjectId;

    //专业名称，同时也是三级专业名称【不含备注】
    private String subjectName;

    // 专业代码
    private String subjectCode;
    // 一级专业id
    private Integer firstId;
    // 一级专业名称
    private String firstName;
    // 二级专业id
    private Integer secondId;
    // 二级专业名称
    private String secondName;

    // 三年的数据 size=3
    private List<CurrentYearInfoDto> info;

    // 三年的类型，最后只取第一年，此处仅为了强制合并数据
    private List<CurrentYearTypeDto> type;
}
