package com.hzau.college.common.annotation.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 布尔类型的数字：要么取0，要么取1
 *
 * - @Documented 对注解生成文档
 * - @Retention(RetentionPolicy.RUNTIME) 持续时间：到程序运行时也有效
 * - @Target(ElementType.FIELD) 作用的对象：属性
 * - @Constraint(validatedBy = BoolNumber.BoolNumberValidator.class ) 约束/自定义校验器：通过哪个类去校验
 *      一般会在注解内容再写一个类
 *
 * - String message() default "只能是0或1";  抛出的异常信息
 * - Class<?>[] groups() default {};
 * - Class<? extends Payload>[] payload() default { };
 *
 * - implements ConstraintValidator<BoolNumber, Short> 自定义校验类必须实现一个接口
 *      <实现的注解名, 作用的对象类型>
 * - 在 isValid()方法中实现校验方法 返回true验证通过，返回false抛出异常message
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = BoolNumber.BoolNumberValidator.class )
public @interface BoolNumber{ // 接口内部类默认都是public
    String message() default "只能是0或1";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    // 不用再写public
    class BoolNumberValidator implements ConstraintValidator<BoolNumber, Short> {
        //自己校验，返回ture表示校验成功，false表示失败并抛出异常
        @Override
        public boolean isValid(Short num, ConstraintValidatorContext constraintValidatorContext){
            return num == null || num == 0 || num == 1 ;
        }
    }
}