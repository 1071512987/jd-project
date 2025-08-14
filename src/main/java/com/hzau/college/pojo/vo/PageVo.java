package com.hzau.college.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 业务层返回的分页数据
 * @param <T> 数据类型
 */
@Data
@ApiModel("分页结果")
public class PageVo<T> {

    @ApiModelProperty("总数")
    private Long count;

    @ApiModelProperty("总页数")
    private Long pages;

    @ApiModelProperty("数据")
    private List<T> data;

}
