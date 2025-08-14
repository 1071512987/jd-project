package com.hzau.college.common.annotation;

import java.lang.annotation.*;

/**
 * 这个注解没什么鸟用 就是让我记住这是个登录用户接口
 */
@Documented
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.SOURCE)
public @interface LoginSign {

}
