package com.hzau.college.common.cfg;//package com.hzau.college.common.cfg;
//
//import com.hzau.college.common.prop.GuidanceProperties;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.RestClients;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
//import javax.annotation.Resource;
//
///**
// * @Author 半夜里咳嗽的狼
// * @Date 2023/2/6 23:28
// * @Description: ES 配置类
// */
//@Configuration
//@EnableElasticsearchRepositories
//public class EsCfg {
//
//    @Resource
//    private GuidanceProperties properties;
//
//    @Bean
//    public RestHighLevelClient client() {
//        ClientConfiguration clientConfiguration
//                = ClientConfiguration.builder()
//                .connectedTo(properties.getEs().getAddress() + ":" + properties.getEs().getPort())
//                .build();
//
//        return RestClients.create(clientConfiguration).rest();
//    }
//
//    @Bean
//    public ElasticsearchOperations elasticsearchTemplate() {
//        return new ElasticsearchRestTemplate(client());
//    }
//}