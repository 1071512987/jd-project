package com.hzau.college.common.enhance;

import lombok.Data;

/**
 * 这个类的主要作用是提供一个通用的数据封装方式，可以用于封装各种类型的数据。
 */
@Data
public class Stop<T> {
    private T data;

    public static <T> Stop<T> create() {
        return new Stop<>();
    }

    public static <T> Stop<T> create(T data) {
        Stop<T> stop = new Stop<>();
        stop.setData(data);
        return stop;
    }
}
