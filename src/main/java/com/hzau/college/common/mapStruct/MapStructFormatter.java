package com.hzau.college.common.mapStruct;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

/**
 * 本类用于定义MapStruct特殊的转换方法
 */
public class MapStructFormatter {


    /*
      自定义方式1：粗粒度转换，直接使用@Named注解，更简单直接
      这里定义一个方法，用于将Date类型转换为Long类型毫秒
      可以在@Mapping注解中标记@Named注解中的内容使用
     */
    @Named("Date2Millis")
    public static Long date2millis(Date date){
        if (date == null) return null;
        return date.getTime();
    }

    /*
        自定义方式2：细粒度转换，使用@Qualifier注解用元注解定义新注解，类似抽取代码。更高的灵活性和扩展性
        这里定义一个方法，用于将Long类型毫秒转换为Date类型
        可以在@Mapping注解中标记@Qualifier注解中的内容使用
     */

    @Qualifier
    @Target(ElementType.METHOD) // 作用目标
    @Retention(RetentionPolicy.CLASS) // 保留阶段
    public @interface Mills2Date {}

    @Mills2Date
    public static Date mills2Date(Long millis){
        if (millis == null) return null;
        return new Date(millis);
    }

// date2millis()方法的粗粒度方式
//    @Qualifier
//    @Target(ElementType.METHOD) // 作用目标
//    @Retention(RetentionPolicy.CLASS) // 保留阶段
//    public @interface Date2Millis {}
}
