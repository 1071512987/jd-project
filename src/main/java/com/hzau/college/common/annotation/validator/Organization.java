package com.hzau.college.common.annotation.validator;

import cn.hutool.core.util.ArrayUtil;
import com.hzau.college.common.util.Strings;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.hzau.college.common.util.Constants.Rank.ORGANIZATION_ARRAY;

/**
 * 字段必须是办学组织：具体内容在Contains.Rank.ORGANIZATION_ARRAY中声明
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD}) // 字段、形参
@Constraint(validatedBy = Organization.OrganizationValidator.class )
public @interface Organization { // 接口内部类默认都是public
    String message() default "办学组织名称错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    class OrganizationValidator implements ConstraintValidator<Organization, String> {
        @Override
        public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext){
            return Strings.isEmpty(str) || ArrayUtil.contains(ORGANIZATION_ARRAY, str);
        }
    }
}