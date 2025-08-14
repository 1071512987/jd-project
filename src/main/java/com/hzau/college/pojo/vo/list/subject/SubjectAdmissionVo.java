package com.hzau.college.pojo.vo.list.subject;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("存放院校所有开设专业录取分数的Vo")
public class SubjectAdmissionVo {

        // 年份
        private String year;

        // 当年专业
        private List<SubjectVo> subjects;
}
