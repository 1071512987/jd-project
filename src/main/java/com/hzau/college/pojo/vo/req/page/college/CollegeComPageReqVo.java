package com.hzau.college.pojo.vo.req.page.college;

import com.hzau.college.pojo.vo.req.page.PageReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("院校比对时传递参数的对象")
public class CollegeComPageReqVo extends PageReqVo {

    @NotBlank
    @ApiModelProperty("保存的用户id")
    private String userId;

    @NotBlank
    @ApiModelProperty("保存的院校id【多所院校以逗号分隔，最多四个】")
    private String collegeIds;
}
