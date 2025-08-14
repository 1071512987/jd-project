package com.hzau.college.common.annotation.validator;

import cn.hutool.core.util.ArrayUtil;
import com.hzau.college.common.util.Strings;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.hzau.college.common.util.Constants.Rank.COLLEGE_EDUCATION_LEVEL_ARRAY;

/**
 * 字段必须是教育等级：具体内容在Contains.Rank.EDUCATION_LEVEL_ARRAY中声明
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD}) // 字段、形参
@Constraint(validatedBy = CollegeEducationLevel.EducationLevelValidator.class )
public @interface CollegeEducationLevel { // 接口内部类默认都是public
    String message() default "教育等级名称错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    class EducationLevelValidator implements ConstraintValidator<CollegeEducationLevel, String> {
        @Override
        public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext){
            return Strings.isEmpty(str) || ArrayUtil.contains(COLLEGE_EDUCATION_LEVEL_ARRAY, str);
        }
    }
}