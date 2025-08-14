package com.hzau.college.pojo.vo.list.college;

import com.hzau.college.pojo.vo.list.subject.SubjectGoodVo;
import com.hzau.college.pojo.vo.list.subject.SubjectSecondNameVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("存放院校信息中——院校概况模块的Vo")
public class CollegeGeneralVo {

    // 院校排名
    private List<CollegeRankBaseVo> rankBaseVo;

    // 联系方式
    private CollegeContactVo contactVo;

    // 男生占比
    private String boyRate;

    // 院校简介
    private String intro;

    // 师资力量
    private String teacherIntro;

    // 院校风采

    // 开设专业【二级专业 + 三级专业】
    private List<SubjectSecondNameVo> openSubject;

    // 优秀专业
    private SubjectGoodVo goodVo;

    //  TODO 专业评级

}
