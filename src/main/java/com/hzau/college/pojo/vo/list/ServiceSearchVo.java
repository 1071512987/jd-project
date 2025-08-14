package com.hzau.college.pojo.vo.list;

import com.hzau.college.pojo.vo.PageVo;
import com.hzau.college.pojo.vo.list.college.CollegeSearchVo;
import com.hzau.college.pojo.vo.list.subject.SubjectInfoVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServiceSearchVo {

    @ApiModelProperty("院校搜索相关结果【院校名称】")
    private PageVo<CollegeSearchVo> collegeVos;

    @ApiModelProperty("本科专业搜索相关结果【专业名称】")
    private PageVo<SubjectInfoVo> majorVos;

    @ApiModelProperty("专科专业搜索相关结果【专业名称】")
    private PageVo<SubjectInfoVo> juniorVos;

    @ApiModelProperty("文章搜索相关结果【文章标题】【暂无】")
    private PageVo<PostVo> news;
}
