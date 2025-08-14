package com.hzau.college.pojo.vo.list;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("志愿表Vo：id、加密md5、上次更改时间戳和前台传入json")
public class GuidanceTableInfoVo {
    @ApiModelProperty("志愿表id")
    private String tableId;

    @ApiModelProperty("加密md5")
    private String md5Json;

    @ApiModelProperty("上次更改的时间戳")
    private Long unix;

    @ApiModelProperty("前台传入json")
    private String json;
}
