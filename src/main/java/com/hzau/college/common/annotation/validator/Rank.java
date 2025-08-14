package com.hzau.college.common.annotation.validator;

import cn.hutool.core.util.ArrayUtil;
import com.hzau.college.common.util.Strings;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.hzau.college.common.util.Constants.Rank.RANK_ARRAY;

/**
 * 字段必须是排名类型：具体内容在Contains.Rank.RANK_NAME_ARRAY 中声明
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = Rank.RankNameValidator.class )
public @interface Rank { // 接口内部类默认都是public
    String message() default "排名类型错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    class RankNameValidator implements ConstraintValidator<Rank, String> {
        @Override
        public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext){
            return Strings.isEmpty(str) || ArrayUtil.contains(RANK_ARRAY, str);
        }
    }
}