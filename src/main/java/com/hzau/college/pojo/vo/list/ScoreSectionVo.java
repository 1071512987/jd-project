package com.hzau.college.pojo.vo.list;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("data_score_section")
public class ScoreSectionVo {
    //主键
    private Long id;
    //分数段
    private Integer score;
    //本段人数
    private Integer scoreNum;
    //累计人数
    private Integer totalNum;
    //年份
    private String year;
    //省份
    private String province;
}

