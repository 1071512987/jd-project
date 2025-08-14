package com.hzau.college.common.annotation.validator;

import cn.hutool.core.util.ArrayUtil;
import com.hzau.college.common.util.Strings;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.hzau.college.common.util.Constants.Rank.YEAR_ARRAY;

/**
 * 字段必须是排名年份：具体内容在Contains.Rank.YEAR_ARRAY中声明
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER}) // 字段、形参
@Constraint(validatedBy = Year.RankYearValidator.class )
public @interface Year { // 接口内部类默认都是public
    String message() default "年份名称错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    class RankYearValidator implements ConstraintValidator<Year, String> {
        @Override
        public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext){
            return Strings.isEmpty(str) || ArrayUtil.contains(YEAR_ARRAY, str);
        }
    }
}