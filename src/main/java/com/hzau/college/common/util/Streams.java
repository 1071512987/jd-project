package com.hzau.college.common.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * JDK1.8新特性
 * 函数式编程: 将list中的每个元素<T>通过function进行转换，并将转换后的结果收集到一个新的List<R>中。
 * Function<T, R>是一个函数式接口，用于将T类型的对象转换为R类型的对象。这里的T是list中的元素类型，R是转换后的类型。
 * <p>
 * 首先调用list的stream()方法将list转换为一个流，然后调用流的map方法，
 * 将function应用到流中的每个元素上，最后通过collect方法将结果收集到一个新的列表中。
 */
public class Streams {
    public static <T, R> List<R> map(Collection<T> list, Function<T, R> function){
        //stream 是 Collection的方法
        return list.stream().map(function).collect(Collectors.toList());
    }
}
