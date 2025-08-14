package com.hzau.college.common.cfg;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ValidatorCfg {
    @Bean
    public Validator validator() {
        return Validation
                .byProvider(HibernateValidator.class)
                .configure()
                .failFast(true) // 后端校验快速失败：有一处失败立即返回异常，不再向下检查
                .buildValidatorFactory().getValidator();


    }
}
