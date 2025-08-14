package com.hzau.college.pojo.vo.req.page.college;

import com.hzau.college.common.annotation.validator.Rank;
import com.hzau.college.common.annotation.validator.Year;
import com.hzau.college.pojo.vo.req.page.KeywordPageReqVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class CollegeRankPageReqVo extends KeywordPageReqVo {
    @ApiModelProperty("排名类型【xyh, wsl, qs】")
    @Rank
    @NotNull(message = "排名类型不能为空")
    private String rankType;

    @ApiModelProperty("排名年份【如2022】")
    @Year @NotNull(message = "年份不能为空")
    private String year;
}

