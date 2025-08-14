package com.hzau.college.common.annotation.validator;

import cn.hutool.core.util.ArrayUtil;
import com.hzau.college.common.util.Strings;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.hzau.college.common.util.Constants.Rank.EVALUATE_NAME_ARRAY;

/**
 * 字段必须是评测名称 ：具体内容在Contains.Rank.EVALUATE_NAME_ARRAY中声明
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER}) // 字段、形参
@Constraint(validatedBy = EvaluateName.EvaluateNameValidator.class )
public @interface EvaluateName { // 接口内部类默认都是public
    String message() default "评测类型名称错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    class EvaluateNameValidator implements ConstraintValidator<EvaluateName, String> {
        @Override
        public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext){
            return Strings.isEmpty(str) || ArrayUtil.contains(EVALUATE_NAME_ARRAY, str);
        }
    }
}