package com.hzau.college.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzau.college.pojo.dto.SubjectSecondNameDto;
import com.hzau.college.pojo.po.Admission;
import com.hzau.college.pojo.vo.list.subject.SubjectAdmissionVo;

public interface AdmissionMapper extends BaseMapper<Admission> {

    SubjectSecondNameDto listSubject(Integer collegeId);

    SubjectAdmissionVo getSubjectAdmission(Integer collegeId, String year);
}

