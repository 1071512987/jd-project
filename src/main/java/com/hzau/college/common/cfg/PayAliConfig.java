package com.hzau.college.common.cfg;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author 半夜里咳嗽的狼
 * @Date 2023/1/15 23:26
 * @Description: 支付宝支付配置类
 */
// TODO 为什么不能使用@Configuration注解？因为@Configuration注解是用来标记配置类的，而这个类是用来读取配置文件的，所以使用@Component注解
@Data
@Component
@PropertySource(value = "classpath:application-${spring.profiles.active}.yml") // 正确读取application-xxx配置文件
public class PayAliConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    @Value("${alipay.app_id}")
    public  String AppId;
    // 商户私钥，您的PKCS8格式RSA2私钥
    @Value("${alipay.merchant_private_key}")
    public String privateKey;
    // 参数返回格式
    @Value("${alipay.format}")
    public String format;
    // 字符编码格式
    @Value("${alipay.charset}")
    public  String charset;
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    @Value("${alipay.alipay_public_key}")
    public String alipayPublicKey;
    // 签名方式
    @Value("${alipay.sign_type}")
    public String signType;
    // AES秘钥
    @Value("${alipay.decrypt_key}")
    public String decryptKey;
    // 加密算法：默认为AES
    @Value("${encrypt_type}")
    public  String encryptType;
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    @Value("${alipay.notify_url}")
    public  String notify_url;
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    @Value("${alipay.return_url}")
    public  String return_url;
    // 支付宝网关
    @Value("${alipay.gatewayUrl}")
    public String serverUrl;
    // 日志存放
    @Value("${alipay.log_path}")
    public String log_path;
    // 电脑支付场景下的product_code
    @Value("${alipay.product_code_web_pay}")
    public String product_code_web_pay;
}
