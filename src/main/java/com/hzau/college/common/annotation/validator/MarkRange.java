package com.hzau.college.common.annotation.validator;

import com.hzau.college.common.util.Integers;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 字段必须是一个分数范围，以逗号分隔。
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = MarkRange.MarkRangeValidator.class )
public @interface MarkRange { // 接口内部类默认都是public
    String message() default "分数段范围不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    // 不用再写public
    class MarkRangeValidator implements ConstraintValidator<MarkRange, String> {
        //自己校验，返回ture表示校验成功，false表示失败并抛出异常
        @Override
        public boolean isValid(String markRange, ConstraintValidatorContext constraintValidatorContext){
            int lowMark;
            int highMark;
            try {
                String[] markRangeStr = markRange.split(",");
                lowMark = Integer.parseInt(markRangeStr[0]);
                highMark = Integer.parseInt(markRangeStr[1]);
            }catch (Exception e){
                return false;
            }
            return (!Integers.isLegal(lowMark) || !Integers.isLegal(highMark) || lowMark >= highMark);
        }
    }
}