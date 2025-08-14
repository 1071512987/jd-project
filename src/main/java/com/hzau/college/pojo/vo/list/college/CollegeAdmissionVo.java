package com.hzau.college.pojo.vo.list.college;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("存放院每年校投档信息的Vo")
public class CollegeAdmissionVo {

    private Integer collegeId;

    private String collegeName;

    private String collegeCode;

    private Integer lowMark;

    private Integer lowRank;

    private Integer highMark;

    private Integer highRank;

    private Integer num;

    private String year;
}
