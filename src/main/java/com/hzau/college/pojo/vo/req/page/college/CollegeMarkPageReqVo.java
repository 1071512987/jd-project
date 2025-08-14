package com.hzau.college.pojo.vo.req.page.college;

import com.hzau.college.common.annotation.validator.Mark;
import com.hzau.college.common.annotation.validator.Year;
import com.hzau.college.pojo.vo.req.page.KeywordPageReqVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CollegeMarkPageReqVo extends KeywordPageReqVo {

    @ApiModelProperty("院校名称")
    private String collegeName;

    @Mark
    @ApiModelProperty("筛选的院校最低的最低分")
    private Integer lowLowMark;

    @ApiModelProperty("筛选的院校最低的最低位次")
    private Integer lowLowRank;

    @Mark
    @ApiModelProperty("筛选的院校最高的最低分")
    private Integer highLowMark;

    @ApiModelProperty("筛选的院校最高的最低位次")
    private Integer highLowRank;

    @Year
    @ApiModelProperty("筛选记录的年份")
    private String year;
}

