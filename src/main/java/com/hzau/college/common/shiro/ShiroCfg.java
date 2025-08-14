package com.hzau.college.common.shiro;

import com.hzau.college.common.shiro.filter.CorsFilter;
import com.hzau.college.common.shiro.filter.JwtFilter;
import com.hzau.college.common.shiro.realm.TokenValidateAndAuthorizingRealm;
import com.hzau.college.common.shiro.realm.UsernamePasswordRealm;
import com.hzau.college.filter.ErrorFilter;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置类
 * 需要配置Realm和ShiroFilterFactoryBean，并将他们放入Ioc容器中
 */
@Configuration
public class ShiroCfg {


    /**
     * 用于用户名密码登录时认证的realm
     */
    @Bean
    public Realm userRealm() {
        UsernamePasswordRealm userRealm = new UsernamePasswordRealm();
        return userRealm;
    }

    /**
     * 用于JWT token认证的realm
     */
    @Bean
    public Realm TokenValidateAndAuthorizingRealm( ) {
        TokenValidateAndAuthorizingRealm jwtRealm = new TokenValidateAndAuthorizingRealm();
        return jwtRealm;
    }


    /**
     * ShiroFilterFactoryBean用来告诉Shiro如何进行拦截
     * 1.需要哪些Filter
     * 2.拦截哪些URL
     * 3.每个filter需要拦截哪些URL
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SessionsSecurityManager securityManager, JwtFilter jwtFilter){
        ShiroFilterFactoryBean filterBean = new ShiroFilterFactoryBean();

        //添加一些自定义的Filter
        Map<String, Filter> filters = new HashMap<>();
        filters.put("cors",new CorsFilter());
        filters.put("jwt", jwtFilter);
        filterBean.setFilters(filters);

        // 设置URL如何拦截
        Map<String, String> urlMap = new LinkedHashMap<>(); // LinkedHashMap保证内容按顺序存取
        // ↓配置不参与验证的映射路径。
//        // 验证码
//        urlMap.put("/sysUsers/captcha", "anon");
//        // 用户登录
//        urlMap.put("/sysUsers/login", "anon");

        // swagger
        urlMap.put("/swagger*/**", "anon");
        urlMap.put("/swagger-ui.html/**", "anon");
        urlMap.put("/v2/api-docs/**", "anon"); // swagger的文档信息访问路径
        urlMap.put("/swagger-ui.html","anon");
        urlMap.put("/swagger/**","anon");
        urlMap.put("/webjars/**", "anon");
        urlMap.put("/swagger-resources/**","anon");
        urlMap.put("/v2/**","anon");
        urlMap.put("/static/**", "anon");
        urlMap.put("/configuration/security", "anon");
        urlMap.put("/csrf", "anon");
        urlMap.put("/api-docs", "anon");
        urlMap.put("/api-docs/**", "anon");
        urlMap.put("/favicon.ico", "anon");
        urlMap.put("/doc.html", "anon");
        urlMap.put("/api/pay/alipay/notify", "anon");
        urlMap.put("/api/pay/alipay/return", "anon");
        // 全局Filter的异常处理
        urlMap.put(ErrorFilter.ERROR_URI, "anon");
        urlMap.put("/static/*", "anon");
        urlMap.put("/api/frontUser/**", "anon");
        urlMap.put("/api/adminUser/**", "anon");


        // 关键：jwt验证过滤器。
        //↓ 此处采用shiro1.6新增的默认过滤器：authcBearer-BearerHttpAuthenticationFilter。jwt验证的很多操作都由该filter自动完成，以致我们只需理解其机制而无需亲手实现
//        urlMap.put("/**", "authcBearer");
        urlMap.put("/**", "jwt");//用自定义的jwt代替authcBearer，前者是后者的子类。以完善异常处理


        //↓ 这句非常关键：配置NoSessionCreationFilter，把整个项目变成无状态服务。
        filterBean.setGlobalFilters(Arrays.asList("cors", "noSessionCreation"));
//        filterBean.setGlobalFilters(Arrays.asList("noSessionCreation"));

        //安全管理器
        filterBean.setSecurityManager(securityManager);
        filterBean.setFilterChainDefinitionMap(urlMap);
        return filterBean;
    }

    /**
     * 解决：@RequiresPermissions导致控制接口404
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator proxyCreator(){
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setUsePrefix(true);
        return proxyCreator;
    }

    @Bean
    protected Authorizer authorizer() {
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        return authorizer;
    }


    /**
     * 初始化Authenticator 认证器 身份认证
     */
    @Bean
    public Authenticator authenticator(UsernamePasswordRealm usernamePasswordRealm, TokenValidateAndAuthorizingRealm tokenValidateAndAuthorizingRealm) {
        CustomModularRealmAuthenticator authenticator = new CustomModularRealmAuthenticator();
        // 设置两个Realm，一个用于用户登录验证和访问权限获取；一个用于jwt token的认证
        authenticator.setRealms(Arrays.asList(tokenValidateAndAuthorizingRealm, usernamePasswordRealm));
        // 设置多个realm认证策略，一个成功即跳过其它的
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }


//    /**
//     * 系统自带的Realm管理，主要针对多realm
//     */
//    @Bean
//    public ModularRealmAuthenticator modularRealmAuthenticator() {
//        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
//        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
//        return modularRealmAuthenticator;
//    }


}
