package com.hzau.college.pojo.vo.req.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class KeywordPageReqVo extends PageReqVo {

    @ApiModelProperty(value = "搜索关键词", position = 99997)
//    @NotBlank(message = "搜索关键词不能为空")
    private String keyword;
}
