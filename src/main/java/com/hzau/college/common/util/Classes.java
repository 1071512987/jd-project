package com.hzau.college.common.util;

import com.hzau.college.common.enhance.Stop;

import java.lang.reflect.Field;

public class Classes {
    /**
     * 返回第一个不是Object.class的类
     */
    public static Class<?> notObject(Class<?>... sources) {
        if (sources == null) return null;
        for (Class<?> source : sources) {
            if (!source.equals(Object.class)) return source;
        }
        return null;
    }

    /**
     * 获取 cls 类中的 fieldName 属性
     */
    public static Field getField(Class<?> cls, String fieldName) throws Exception {
        return enumerateFields(cls, (field, curCls) -> {
            if (field.getName().equals(fieldName)) return Stop.create(field);
            return null;
        });
    }

    /**
     * 遍历 cls 中的所有属性
     */
    public static <T> T enumerateFields(Class<?> cls,
                                        FieldConsumer<T> fieldConsumer) throws Exception {
        if (fieldConsumer == null || cls == null) return null;
        Class<?> curCls = cls;
        while (!curCls.equals(Object.class)) {
            for (Field field : curCls.getDeclaredFields()) {
                Stop<T> stop = fieldConsumer.accept(field, curCls);
                if (stop != null) return stop.getData();
            }
            curCls = curCls.getSuperclass();
        }
        return null;
    }

    public interface FieldConsumer<T> {
        Stop<T> accept(Field field, Class<?> ownerCls) throws Exception;
    }

}
