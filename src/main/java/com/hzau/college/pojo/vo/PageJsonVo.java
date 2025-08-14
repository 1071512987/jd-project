package com.hzau.college.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 返回带结果的——分页数据——（成功）Json信息
 *  额外携带：
 *  count ： 总条数
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageJsonVo<T> extends DataJsonVo<List<T>> {
    @ApiModelProperty("总数")
    private Long count;

}
