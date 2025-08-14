package com.hzau.college.common.enhance;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/*
    nested的作用是将所有的like语句用()包裹，使多查询之间以默认的and语句过度
    因为nested是父类的方法，默认返回LambdaQueryWrapper<T>类型的值，因此需要强转
    此处传入的wrapper即为本类MpQueryWrapper的实例
 */

public class MpQueryWrapper<T> extends QueryWrapper<T> {
    @SafeVarargs
    public final MpQueryWrapper<T> like(Object val, String... columns){
        if (val == null) return this;
        String str = val.toString();
        if (str.isEmpty()) return this;
         return (MpQueryWrapper<T>) nested((wrapper) -> {
            for(String column : columns) {
                wrapper.like(column, str).or();
            }
        });
    }
}
