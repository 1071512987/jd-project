package com.hzau.college.pojo.vo.list;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("志愿表Vo")
public class GuidanceTableVo {

    @ApiModelProperty("所有志愿表信息")
    private List<GuidanceTableInfoVo> infoVos;

}
