package com.hzau.college.pojo.vo.list.subject;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;


@Data
@ApiModel("存放院校所有开设专业招生计划Vo")
public class SubjectProjectVo {

    // 年份
    private String year;

    // 当年专业
    private List<SubjectVo>  subjects;

}
