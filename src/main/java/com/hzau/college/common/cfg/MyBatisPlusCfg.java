package com.hzau.college.common.cfg;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.hzau.college.mapper")
public class MyBatisPlusCfg implements InitializingBean {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor innerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        // 当页数超过总页数时，自动跳回到第1页 共50页，传52页 -> 返回第一页
//        innerInterceptor.setOverflow(true);
        interceptor.addInnerInterceptor(innerInterceptor);
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }

    /*
	首先，拥有lambda cache的实体类，才能使用LambdaQueryWrapper<Entity\>
    而默认情况下，只有`BaseMapper<Entity\>`泛型的Entity类，才会被MP构建lambda cache，方便以后通过lambda技术进行查询
    其他类需要通过TableInfoHelper手动添加lambda cache
	*/
    @Override
    public void afterPropertiesSet() throws Exception {
        MapperBuilderAssistant assistant = new MapperBuilderAssistant(new MybatisConfiguration(), "");
//        TableInfoHelper.initTableInfo(assistant, ExamPlaceCourse.class);
    }
}