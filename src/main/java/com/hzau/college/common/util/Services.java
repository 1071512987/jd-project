package com.hzau.college.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.hzau.college.common.prop.GuidanceProperties;
import com.hzau.college.pojo.vo.list.CollegePreferSubjectVo;
import com.hzau.college.pojo.vo.list.PreferSubjectVo;
import com.hzau.college.pojo.vo.list.SubjectPreferSubjectVo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Services {

    private static final GuidanceProperties.Sms SMS;
    static {
        SMS = GuidanceProperties.get().getSms();
    }

    public static List<Integer> getPastNumberArray(Integer num, Integer size){
        if (num <= 0 || size <=0 ) return null;
        List<Integer> res = new ArrayList<>();
        for (int i = num + 1; i <= num + size; i++) {
            res.add(i);
        }
        return res;
    }

    private static final Integer LITTLE_RATE = 1 ;
    private static final Integer MEDIUM_RATE = 60 ;
    private static final Integer LARGE_RATE = 85 ;

    /*
    用于院校优先筛选
     此处为详细的判断逻辑
     注：录取位次越大，对应分数越低
    【目前的使用去年【2022】院校录取最低位次判断】，阈值为15000，即
                            传入真实位次     院校录取最低位次+15000    -> 冲： 1%
     院校录取最低位次+15000    传入真实位次     院校录取最低位次 +6000   -> 冲： 1%  ~ 30%
     院校录取最低位次 +6000    传入真实位次     院校录取最低位次-15000    -> 稳： 31% ~ 70%
     院校录取最低位次-15000    传入真实位次                             -> 保： 71% ~ 99%


//     【废弃】【目前简单的使用去年【2022】录取最低分数判断】
//       0     lowMarkLast     -10     -> 冲： 1%  ~ 10% 【随机】
//     -10     lowMarkLast     +12     -> 稳： 60% ~ 70% 【随机】
//     +12     lowMarkLast     750     -> 保： 85% ~ 95% 【随机】

  */

    public static void marks(CollegePreferSubjectVo vo, Integer realMark, Integer realRank){

        // 院校的上一年录取最低分
        Integer lowRankLast = vo.getInfo().get(vo.getInfo().size()-1).getLowRank();
        // TODO 此处应该预测三年的位次；
        Integer predRank = lowRankLast;

        int threshold = 15000; // 阈值
        int probability  = 0; // 初始概率

        int diff = (int)predRank - (int)realRank; // 计算位次差

        if (diff > threshold) {
        // 最大
            probability = 99;
        } else if (diff < -threshold) {
        // 最小
            probability = 1;
        }else {
            // 计算录取率
            // TODO 此处待优化：目前位次等于预测位次的话，录取率为50
            float rate = (float) diff / (float) threshold;
            probability = 50 + (int) (rate * 50);
        }

        // 结果赋值
        if (probability <= 30){
            vo.setAdmissionRate(probability);
            vo.setRisk("冲");
        }else if (probability <= 70){
            vo.setAdmissionRate(probability);
            vo.setRisk("稳");
        }else {
            vo.setAdmissionRate(probability);
            vo.setRisk("保");
        }

        // 无需返回值，因为变量已给到vo中
    }


       /*
       用于专业优先筛选
         此处为详细的判断逻辑

         注：录取位次越大，对应分数越低
        【目前的使用去年【2022】院校录取最低位次判断】，阈值为15000，即
                                传入真实位次     院校录取最低位次+15000    -> 冲： 1%
         院校录取最低位次+15000    传入真实位次     院校录取最低位次 +6000   -> 冲： 1%  ~ 30%
         院校录取最低位次 +6000    传入真实位次     院校录取最低位次-15000    -> 稳： 31% ~ 70%
         院校录取最低位次-15000    传入真实位次                             -> 保： 71% ~ 99%


//     废弃【目前简单的使用去年【2022】录取最低分数判断】
//       0     lowMarkLast     -12    -> 冲： 1%  ~ 10% 【随机】
//     -12     lowMarkLast     +12     -> 稳： 60% ~ 70% 【随机】
//     +12     lowMarkLast     750       -> 保： 85% ~ 95% 【随机】
  */

    public static void marks(SubjectPreferSubjectVo vo, Integer realMark, Integer realRank){

        // 院校的上一年录取最低分
        Integer lowRankLast = vo.getInfo().get(vo.getInfo().size()-1).getLowRank();
        // TODO 此处应该预测三年的位次；
        Integer predRank = lowRankLast;

        int threshold = 15000; // 阈值
        int probability  = 0; // 初始概率

        int diff = (int)predRank - (int)realRank; // 计算位次差

        if (diff > threshold) {
            // 最大
            probability = 99;
        } else if (diff < -threshold) {
            // 最小
            probability = 1;
        }else {
            // 计算录取率
            // TODO 此处待优化：目前位次等于预测位次的话，录取率为50
            float rate = (float) diff / (float) threshold;
            probability = 50 + (int) (rate * 50);
        }

        // 结果赋值
        if (probability <= 30){
            vo.setAdmissionRate(probability);
            vo.setRisk("冲");
        }else if (probability <= 70){
            vo.setAdmissionRate(probability);
            vo.setRisk("稳");
        }else {
            vo.setAdmissionRate(probability);
            vo.setRisk("保");
        }

        // 无需返回值，因为变量已给到vo中

    }

    /*
         此处为详细的判断逻辑
         【目前简单的使用去年【2022】录取最低分数判断】
           0     lowMarkLast     -12    -> 冲： 1%  ~ 10% 【随机】
         -12     lowMarkLast     +12     -> 稳： 60% ~ 70% 【随机】
         +12     lowMarkLast     750       -> 保： 85% ~ 95% 【随机】
      */

    public static void marks(PreferSubjectVo vo, Integer realMark, Integer studentRank){

        Integer lowMarkLast = vo.getInfo().get(0).getLowMark();
        if (Integers.rangeIn(realMark, 0  , lowMarkLast - 10)){
            vo.setAdmissionRate(LITTLE_RATE + (int)(Math.random() * 9));
            vo.setRisk("冲");
        }

        if (Integers.rangeIn(realMark, lowMarkLast - 10  , lowMarkLast + 10)){
            vo.setAdmissionRate(MEDIUM_RATE + (int)(Math.random() * 10));
            vo.setRisk("稳");
        }

        if (Integers.rangeIn(realMark, lowMarkLast + 10  , 750 )){
            vo.setAdmissionRate(LARGE_RATE + (int)(Math.random() * 10));
            vo.setRisk("保");
        }

        // 无需返回值，因为变量已给到vo中
    }


    /**
     * /*
     * 	SMS : 短讯服务 Short Messaging Service
     * 	返回 ‘0’ 视为发送成功，其他内容为错误提示内容
     *
     * 	错误码	错误描述
     * 	30	错误密码
     * 	40	账号不存在
     * 	41	余额不足
     * 	43	IP地址限制
     * 	50	内容含有敏感词
     * 	51	手机号码不正确
     *
     * @param phone 接收的手机号;群发时多个手机号以逗号分隔，一次不要超过99个号码
     * @param code 需要发送的验证码
     */
    public static boolean sms(String phone, String code){
    String username =  SMS.getSmsUsername(); // 在短信宝注册的用户名
    String password = SMS.getSmsPassword(); // 在短信宝注册的密码
    String content = "【满意志愿】您的验证码是" + code + ",此信息５分钟内有效。若非本人操作请忽略此消息。"; // 注意测试时，也请带上公司简称或网站签名，发送正规内容短信。千万不要发送无意义的内容：例如 测一下、您好。否则可能会收不到

    // 普通接口
    String httpUrl = "http://api.smsbao.com/sms";

    StringBuffer httpArg = new StringBuffer();
		httpArg.append("u=").append(username).append("&");
		httpArg.append("p=").append(md5(password)).append("&");
		httpArg.append("m=").append(phone).append("&");
		httpArg.append("c=").append(encodeUrlString(content, "UTF-8"));

        String result = request(httpUrl, httpArg.toString());
//        return JsonVos.raise("短信接口出现错误，请联系管理员!");
        return result.equals("0");
    }

    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = reader.readLine();
            if (strRead != null) {
                sbf.append(strRead);
                while ((strRead = reader.readLine()) != null) {
                    sbf.append("\n");
                    sbf.append(strRead);
                }
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * MD5加密
     * @param plainText 加密的原字段
     * @return 加密后的字段
     */
    public static String md5(String plainText) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    /**
     * 将指定字符串进行URL编码
     * 调用: encodeUrlString(str, "UTF-8")
     * @param str 转码字符串
     * @param charset 字符集类型
     * @return 转码后的字符串
     */
    public static String encodeUrlString(String str, String charset) {
        String strret = null;
        if (str == null)
            return str;
        try {
            strret = java.net.URLEncoder.encode(str, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strret;
    }

    /**
     * 将指定字符串进行URL解码
     * 调用: decodeUrlString(str, "UTF-8")
     * @param str 待解码字符串
     * @param charset 字符集类型
     * @return 原字符串
     */
    public static String decodeUrlString(String str, String charset) {
        String strret = null;
        if (str == null)
            return str;
        try {
            strret = java.net.URLDecoder.decode(str, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strret;
    }


        /**
         * 生成订单号
         * @return 商品前缀+唯一订单号
         */
        public static String createOrderId() {
            return "UPG_ID" + getSnowFlakeId();
        }

    /**
     * 由雪花算法生成唯一id
     * @return 唯一id
     */
    public static String getSnowFlakeId() {
        //数据中心id和工作机器id在单机环境可以任意指定
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        String uuid = String.valueOf(snowflake.nextId());
        return uuid;
    }

}
