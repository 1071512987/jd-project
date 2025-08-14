package com.hzau.college.common.prop;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@ConfigurationProperties("guidance")
@Component
@Data
public class GuidanceProperties implements ApplicationContextAware {
    private Cors cors;
    private Sms sms;
    private Geetest geetest;
//    private ES es;
    private static GuidanceProperties properties;

    public static GuidanceProperties get() {
        return properties;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        properties = this;
    }

    @Data
    public static class Cors{
        private String[] corsOrigins;
    }

    @Data
    public static class Sms{
        // 短信宝用户名
        private String smsUsername;
        // 短信宝密码
        private String smsPassword;
    }

    @Data
    public static class Geetest{
        private String captchaId;
        private String captchaKey;
        private String domain;
    }

//    @Data
//    public static class ES{
//        // es用户名
//        private String username;
//        // es密码
//        private String password;
//        // es地址
//        private String address;
//        // es端口
//        private String port;
//    }




}
