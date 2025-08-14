package com.hzau.college.common.annotation.validator;

import com.hzau.college.common.util.RegexPatterns;
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
@Constraint(validatedBy = Phone.PhoneValidator.class )
public @interface Phone { // 接口内部类默认都是public
    String message() default "手机号格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    class PhoneValidator implements ConstraintValidator<Phone, String> {
        @Override
        public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext){
            return Strings.isEmpty(str) || str.matches(RegexPatterns.PHONE_REGEX);
        }
    }
}