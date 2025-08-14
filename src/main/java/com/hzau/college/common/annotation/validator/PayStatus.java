package com.hzau.college.common.annotation.validator;

import cn.hutool.core.util.ArrayUtil;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.hzau.college.common.util.Constants.Rank.PAY_STATUS;

/**
 * 字段必须是数字类型且在一定范围内：具体内容在Contains.Rank.PAY_STATUS 中声明
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = PayStatus.RankNameValidator.class )
public @interface PayStatus { // 接口内部类默认都是public
    String message() default "订单支付状态错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    class RankNameValidator implements ConstraintValidator<PayStatus, Integer> {
        @Override
        public boolean isValid(Integer status, ConstraintValidatorContext constraintValidatorContext){
            return status == null || ArrayUtil.contains(PAY_STATUS, null);
        }
    }
}