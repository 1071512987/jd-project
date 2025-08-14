package com.hzau.college.common.annotation.validator;

import com.hzau.college.common.util.Strings;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 字段必须是办学组织：具体内容在Contains.Rank.ORGANIZATION_ARRAY中声明
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER}) // 字段、形参
@Constraint(validatedBy = PhoneCode.PhoneCodeValidator.class )
public @interface PhoneCode { // 接口内部类默认都是public
    String message() default "验证码长度错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    class PhoneCodeValidator implements ConstraintValidator<PhoneCode, String> {
        @Override
        public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext){
            return Strings.isEmpty(str) || str.length() == 4;
        }
    }
}