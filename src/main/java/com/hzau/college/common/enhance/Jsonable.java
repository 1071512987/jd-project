package com.hzau.college.common.enhance;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 本接口的作用是将对象转为JSON字符串格式
 * 指定类实现本接口后，即可调用jsonString方法将对象转为JSON字符串格式
 */
public interface Jsonable {

    // default关键字的作用是将方法声明为默认方法，即接口的默认实现，节约代码量
    default String jsonString() throws Exception{
        // Jackson框架的核心对象
        ObjectMapper mapper = new ObjectMapper();

        //空内容不返回
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);

        // 此方法将对象转为JSON字符串格式，如-> {"array":["80","90","95"],"height":170,"innerBaseOb ject":{"array":["65","68","75}
        return mapper.writeValueAsString(this);
    }
}
