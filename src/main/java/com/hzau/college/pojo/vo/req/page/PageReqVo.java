package com.hzau.college.pojo.vo.req.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//请求参数：不需要加@ApiModel
@Data
public class PageReqVo {
    private static final int DEFAULT_SIZE = 10;

    @ApiModelProperty(value = "页码", position = 9998)
    private Long page;

    @ApiModelProperty(value = "每页的大小", position = 9999)
    private Long size;

    public long getPage() {
        return page == null ? 1 : Math.max(page, 1);
    }

    public long getSize() {
        return size == null ? DEFAULT_SIZE : size < 1 ? DEFAULT_SIZE : size;
    }

}
