package com.hzau.college.common.annotation.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 字段必须是高考分数
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER}) // 字段、形参
@Constraint(validatedBy = Mark.RankYearValidator.class )
public @interface Mark { // 接口内部类默认都是public
    String message() default "分数输入错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    class RankYearValidator implements ConstraintValidator<Mark, Integer> {
        @Override
        public boolean isValid(Integer mark, ConstraintValidatorContext constraintValidatorContext){
            return mark == null || (mark >= 0 && mark <= 750);
        }
    }
}