package com.hzau.college.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 返回带结果的（成功）Json信息
 * 额外附带：
 * @param <T> 成功信息的类型
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataJsonVo<T> extends JsonVo {

    @ApiModelProperty("返回数据")
    private T data;

    //无参构造方法，使子类不报错
    public DataJsonVo(){

    }

    public DataJsonVo(String msg, T data){
        super(true, msg);
        this.data = data;
    }

    public DataJsonVo(String msg, T data, String token){
        super(true, msg, token);
        this.data = data;
    }

    public DataJsonVo(T data){
        this(null, data);
    }

    public DataJsonVo(T data, String token){
        this(null, data, token);
    }

}
