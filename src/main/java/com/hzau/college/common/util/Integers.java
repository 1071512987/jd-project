package com.hzau.college.common.util;

public class Integers {

    // 判断整型格式是否合法(不为null且大于0)
    public static boolean isLegal(Integer num){
        return num != null && num >= 0;
    }

    // 判断是否在范围内(true为在范围内)
    public static boolean rangeIn(Integer a, Integer min, Integer max) {

        return Math.max(min, a) == Math.min(a, max);
    }

    // 判断两个范围是否重叠(true为重叠)
    public static boolean rangeUnite(Integer a, Integer b, Integer min, Integer max) {

        return Math.max(a, min) <= Math.min(b, max);
    }
}
