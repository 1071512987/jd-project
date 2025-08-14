package com.hzau.college.common.cfg;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 半夜里咳嗽的狼
 * @Date 2023/3/30 14:48
 * @Description: TODO
 */
@Configuration  // 声明这是一个配置类,相当于xml配置文件
public class OssClientConfig {
    @Value("${aliyun.oss.endpoint}")
    String endpoint ;
    @Value("${aliyun.oss.accessKeyId}")
    String accessKeyId ;
    @Value("${aliyun.oss.accessKeySecret}")
    String accessKeySecret;

    @Bean
    public OSSClient createOssClient() {
        return (OSSClient)new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
