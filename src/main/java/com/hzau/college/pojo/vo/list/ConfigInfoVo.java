package com.hzau.college.pojo.vo.list;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("全局json Vo：加密md5、时间戳和json")
public class ConfigInfoVo {

    @ApiModelProperty("加密md5")
    private String md5Json;

    @ApiModelProperty("上次更改的时间戳")
    private Long unix;

    @ApiModelProperty("前台传入json")
    private String json;
}
