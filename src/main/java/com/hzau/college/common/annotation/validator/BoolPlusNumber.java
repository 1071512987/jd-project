package com.hzau.college.common.annotation.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 布尔类型的数字+1：要么取1(表示肯定)，要么取2(表示否定)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = BoolPlusNumber.BoolPlusNumberValidator.class )
public @interface BoolPlusNumber { // 接口内部类默认都是public
    String message() default "参数只能是1或2";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    // 不用再写public
    class BoolPlusNumberValidator implements ConstraintValidator<BoolPlusNumber, Short> {
        //自己校验，返回ture表示校验成功，false表示失败并抛出异常
        @Override
        public boolean isValid(Short num, ConstraintValidatorContext constraintValidatorContext){
            return num == null || num == 1 || num == 2 ;
        }
    }
}