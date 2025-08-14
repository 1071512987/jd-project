package com.hzau.college.common.annotation.validator;

import cn.hutool.core.util.ArrayUtil;
import com.hzau.college.common.util.Strings;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.hzau.college.common.util.Constants.Rank.COLLEGE_TYPE_LEVEL;

/**
 * 字段必须是院校类型：具体内容在Contains.Rank.COLLEGE_TYPE_LEVEL中声明
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD}) // 字段、形参
@Constraint(validatedBy = CollegeType.CollegeTypeValidator.class )
public @interface CollegeType { // 接口内部类默认都是public
    String message() default "院校类型名称错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    class CollegeTypeValidator implements ConstraintValidator<CollegeType, String> {
        @Override
        public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext){
            return Strings.isEmpty(str) || ArrayUtil.contains(COLLEGE_TYPE_LEVEL, str);
        }
    }
}