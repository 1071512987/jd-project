package com.hzau.college.common.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @Author 半夜里咳嗽的狼
 * @Date 2023/4/23 21:00
 * @Description: 通过RestTemplate发送http请求
 */
public class Https {

     public static ResponseEntity<String> SendHTTPJSON(String requestBody, String url, HttpMethod method){
        RestTemplate restTemplate = new RestTemplate();
        //①：请求体为一个json格式的字符串
        /*
          ②：若请求体为json字符串的时候，需要在头中设置Content-Type=application/json；
          若body是普通的java类的时候，无需指定这个，RestTemplate默认自动配上Content-Type=application/json
         */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //③：请求实体（body，头、请求方式，uri）
        RequestEntity<String> requestEntity = new RequestEntity<>(requestBody, headers, method, URI.create(url));
        //④：发送请求(请求实体，返回值需要转换的类型)
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                requestEntity,
                new ParameterizedTypeReference<String>() {
                });
        return responseEntity;
    }
}
