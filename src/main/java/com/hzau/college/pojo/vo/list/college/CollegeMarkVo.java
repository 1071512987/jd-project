package com.hzau.college.pojo.vo.list.college;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("存放院校分数的Vo")
public class CollegeMarkVo {
    
    private Integer id;
    
    private String name;
    
    private String code;
    
    private Integer shandongLowMarkReal;
    
    private Integer shandongLowRankReal;
    
    private Integer shandongHighMarkReal;
    
    private Integer shandongHighRankReal;

    private Integer shandongNum;

    private String year;
}

