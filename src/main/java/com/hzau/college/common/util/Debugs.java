package com.hzau.college.common.util;

import org.springframework.beans.factory.annotation.Value;

// 本类的作用：在开发阶段，可以通过设置 DEBUG 为 false 来关闭调试信息
public class Debugs {
    @Value("${spring.profiles.active}")
    public static String ACTIVE;

    public static void run(Runnable runnable){
        if (!ACTIVE.equals(Constants.Base.DEV)) return;
        if (runnable == null) return;
        runnable.run();
    }
}
