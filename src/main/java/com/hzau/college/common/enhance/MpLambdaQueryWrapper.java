package com.hzau.college.common.enhance;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
/*
    - 本类的作用是改进MP的LambdaQueryWrapper类，使其支持多字段模糊查询
    - SFunction为MP官方使用的配置，泛型在创建泛型类时传入，通配符在定义时占位，在实例化时声明
      最终返回本类，以完成链式调用
    - nested方法是MP的LambdaQueryWrapper类中的方法，作用是将多个查询条件用()包裹
      nested的作用是将所有的like语句用()包裹，使多查询之间以默认的and语句过度
      因为nested是父类的方法，默认返回LambdaQueryWrapper<T>类型的值，因此需要强转
      此处传入的wrapper即为本类MpQueryWrapper的实例
*/
public class MpLambdaQueryWrapper<T> extends LambdaQueryWrapper<T> {


    @SafeVarargs // 此注解的作用是告诉编译器，此方法是类型安全的，不会出现类型转换异常
    public final MpLambdaQueryWrapper<T> like(Object val, SFunction<T, ?>... funcs){
        if (val == null) return this;
        String str = val.toString();
        if (str.isEmpty()) return this;
        return (MpLambdaQueryWrapper<T>) nested(wrapper -> {
            for(SFunction<T, ?> func : funcs) {
                wrapper.like(func, str).or(); // 检验每个项目中是否含有关键字，并且用or连接
            }
//            return wrapper; // 本行代码是多余的，因为LambdaQueryWrapper<T>的nested方法返回值即为LambdaQueryWrapper<T>
        });
    }
}

