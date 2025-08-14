package com.hzau.college.common.cfg;

import com.hzau.college.common.prop.GuidanceProperties;
import com.hzau.college.filter.ErrorFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.Filter;

@Configuration
public class WebCfg implements WebMvcConfigurer {

    @Resource
    private GuidanceProperties properties;

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // /** 表示对所有的路径开放全局跨域访问权限
        registry.addMapping("/**")
                //开放哪些IP、端口、域名的访问权限——从配置文件中获取
                .allowedOrigins(properties.getCors().getCorsOrigins())
                //是否允许发送Cookie信息
                .allowCredentials(true)
                //哪些HTTP方法允许跨域访问
                .allowedMethods("GET", "POST");
    }

    /**
     * 注册全局异常拦截器
     */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        // 添加Filter和拦截范围
        bean.setFilter(new ErrorFilter());
        bean.addUrlPatterns("/*");
        // 赋予权限：最高权限
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        configurer.addPathPrefix("api", c -> c.isAnnotationPresent(RequestMapping.class));
//    }

    //    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
}
