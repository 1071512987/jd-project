package com.hzau.college.common.util;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Constants {

    /*
    系统基本配置
     */
    public static class Base {
        public static final String LATEST_LAST_YEAR = "2023";
        public static final String DEV = "dev";
        public static final String FRO = "fro";
        public static final String PRO = "pro";

    }

    /*
    //武书连排名【中国】 wslRank;
    //校友会排行【中国】 xyhZongheRank;
    //软科排名【中国】 ruankeZongheRank;
    //泰晤士排名【中国】 twsRank;
    //泰晤士排名【中国】排序 twsRankSort;
    //US排名【世界】 usRank;
    //QS排名【世界】 qsRank;
    //QS排名【世界】排序 qsRankSort;
     */

    public static class Rank {
        public static final String WSL = "wsl";
        public static final String XYH = "xyh";
        public static final String QS = "qs";

        public static final String[] RANK_ARRAY = {WSL, XYH, QS};

        // 目前：2015 - 2023
        public static final String[] YEAR_ARRAY = {"2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", };

        public static final Map<String, Integer> YEAR_INDEX_MAP = Stream.of(new Object[][] {
                { "2023", 0 },
                { "2022", 1 },
                { "2021", 2 },
                { "2020", 3 },
                { "2019", 4 },
                { "2018", 5 },
                { "2017", 6 },
                { "2016", 7 },
                { "2015", 8 },
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

        // 院校教育等级
        public static final String[] COLLEGE_EDUCATION_LEVEL_ARRAY = {"普通本科", "专科（高职）"};

        // 专业教育等级
        public static final String[] SUBJECT_EDUCATION_LEVEL_ARRAY = {"本科", "专科（高职）"};


        // 院校类型
        public static final String[] COLLEGE_TYPE_LEVEL = {
                "体育类", "其他", "军事类", "农林类", "医药类", "师范类", "政法类", "民族类",
                "理工类", "综合类", "艺术类", "语言类", "财经类"
        };

        // 办学组织
        public static final String[] ORGANIZATION_ARRAY = {"公办", "民办", "中外合作办学", "内地与港澳台地区合作办学"};

        // 评测名称
        public static final String[] EVALUATE_NAME_ARRAY = {"DISC", "MBTI", "霍兰德"};

        // 订单交易状态
        public static final Integer[] TRADE_STATUS = {0, 1, 2, 3};

        // 订单支付状态
        public static final Integer[] PAY_STATUS = {0, 1, 2};
    }


    public static class SysUserStatus {
        public static final int NORMAL = 0;
        public static final int LOCKED = 1;
    }

    public static class SysResourceType {
        public static final int DIR = 0;
        public static final int MENU = 1;
        public static final int BTN = 2;
    }

    /**
     * 前后台共用同一套权限，不同角色
     * 前台角色： user vip
     * 后台角色: root service php
     */
    public static class Permission {

        /*
            RABC相关
         */
        public static final String SYS_USER_LIST = "admin:sysUser:list";
        public static final String SYS_USER_SAVEORUPDATE = "admin:sysUser:saveOrUpdate";
        public static final String SYS_USER_REMOVE = "admin:sysUser:remove";

        public static final String SYS_ROLE_LIST = "admin:sysRole:list";
        public static final String SYS_ROLE_SAVEORUPDATE = "admin:sysRole:saveOrUpdate";
        public static final String SYS_ROLE_REMOVE = "admin:sysRole:remove";

        public static final String SYS_RESOURCE_LIST = "admin:sysResource:list";
        public static final String SYS_RESOURCE_SAVEORUPDATE = "admin:sysResource:saveOrUpdate";
        public static final String SYS_RESOURCE_REMOVE = "admin:sysResource:remove";

        /*
         院校相关接口
         */
        public static final String COLLEGE_INFO_LIST = "collegeInfo:list";
        public static final String COLLEGE_INFO_INFO = "collegeInfo:info";


        public static final String COLLEGE_INFO_SAVEORUPDATE = "admin:collegeInfo:saveOrUpdate";
        public static final String COLLEGE_INFO_REMOVE = "admin:collegeInfo:remove";
//        public static final String COLLEGE_RANK_LIST = "collegeRank:list";
        public static final String COLLEGE_RANK_SAVEORUPDATE = "admin:collegeRank:saveOrUpdate";
        public static final String COLLEGE_RANK_REMOVE = "admin:collegeRank:remove";
//        public static final String COLLEGE_MARK_LIST = "collegeMark:list";
        public static final String COLLEGE_MARK_SAVEORUPDATE = "admin:collegeMark:saveOrUpdate";
        public static final String COLLEGE_MARK_REMOVE = "admin:collegeMark:remove";
//        public static final String COLLEGE_INFO_LIST_LIST = "collegeInfoList:list";
        public static final String COLLEGE_INFO_LIST_SAVEORUPDATE = "admin:collegeInfoList:saveOrUpdate";
        public static final String COLLEGE_INFO_LIST_REMOVE = "admin:collegeInfoList:remove";


        // 专业相关接口
        public static final String SUBJECT_INFO_LIST = "subjectInfo:list";
        public static final String SUBJECT_INFO_INFO = "subjectInfo:info";
        public static final String SUBJECT_INFO_SAVEORUPDATE = "admin:subjectInfo:saveOrUpdate";
        public static final String SUBJECT_INFO_REMOVE = "admin:subjectInfo:remove";
        // 专业详细信息相关接口
//        public static final String SUBJECT_DETAIL_SAVEORUPDATE = "admin:subjectDetail:saveOrUpdate";
//        public static final String SUBJECT_DETAIL_REMOVE = "admin:subjectDetail:remove";

        /*
         志愿填报相关接口
         */
        public static final String COLLEGE_PREFER_GUIDANCE_LIST = "collegePrefer:list";
        public static final String SUBJECT_PREFER_GUIDANCE_LIST = "subjectPrefer:list";
        public static final String INTELLIGENT_GUIDANCE_LIST = "intelligent:list";        // 智能填报

        /*
         评测相关接口
         */
        public static final String EVALUATE_LIST = "evaluate:list";
        public static final String EVALUATE_SAVE_OR_UPDATE = "evaluate:saveOrUpdate";
        public static final String EVALUATE__REMOVE = "evaluate:remove";


        /*
        公告相关接口
        */
        public static final String INFORM_LIST = "inform:list";

        /*
       订单相关接口
       */
        public static final String ORDER_LIST = "order:list";
        public static final String ORDER_SAVEORUPDATE = "admin:order:saveOrUpdate";
        public static final String ORDER_REMOVE = "admin:order:remove";

        /*
        VIP相关接口
       */
        public static final String VIP_ALL = "vip:all";

        /*
         用户相关接口
         */
        public static final String LOGINUSER_LIST = "loginuser:list";




    }

    /**
     * Redis相关常量
     */
    public static class Redis {
        /*
            标识
         */
        public static final String CREDENTIAL_FLAG = "credential";
        public static final Integer CREDENTIAL_USER_LIMIT = 5;


        /*
            Key前缀
         */

        // 人机验证
        public static final String REDIS_MAN_MACHINE_KEY = "cache:man_machine:";
        // 人机验证有效次数
        public static final String REDIS_MAN_MACHINE_COUNT_KEY = "cache:man_machine:count:";
        // vip角色
        public static final String REDIS_VIP_ADD = "cache:user:vip:";
        //
        public static final String REDIS_ANON_TOKEN_KEY = "cache:sys:anno:token:";


        /* 前台 */

        // 前台token存放位置
        public static final String REDIS_FRONT_LOGIN_TOKEN_KEY = "cache:sys:front:login:token:";

        // 前台用户信息缓存
        public static final String REDIS_USER_INFO_KEY = "cache:front:long:info:";

        // 前台院校缓存
        public static final String REDIS_USER_UN_KEY = "cache:front:long:sys:un:";
        // 登录、注册手机验证码
        public static final String REDIS_FRONT_LOGIN_CODE_KEY = "cache:front:long:login:code:";
        // 忘记密码手机验证码
        public static final String REDIS_FRONT_LOST_CODE_KEY = "cache:front:long:lost:code:";
        // 前台全局配置
        public static final String REDIS_CONFIG_KEY = "cache:front:long:config:";
        public static final String REDIS_TABLE_KEY = "cache:front:long:table:";

        /* 后台 */

        // 登录、注册手机验证码
        public static final String REDIS_ADMIN_LOGIN_CODE_KEY = "cache:admin:long:login:code:";
        // 忘记密码手机验证码
        public static final String REDIS_ADMIN_LOST_CODE_KEY = "cache:admin:long:lost:code:";
        // 后台token存放位置
        public static final String REDIS_ADMIN_LOGIN_TOKEN_KEY = "cache:sys:admin:login:token:";




        /*
            Key 后缀
         */
        public static final String REDIS_JSON_SUF = ":json";
        public static final String REDIS_UNIX_SUF = ":unix";
        public static final String REDIS_MD5_SUF = ":md5";

        /*
            TTL
         */
        // 用户Token缓存
        public static final Long TOKEN_CACHE_TTL = 24 * 4 * 60L;  // 24 * 4小时

        // 用户附加信息
        public static final Long USER_INFO_TTL = 2 * 60L;  // 2小时

        public static final Long CACHE_NULL_TTL = 2L;
        public static final Long CACHE_TEMP_TTL = 5L;


        public static final Long LOGIN_CODE_TTL = 10L;
        public static final Long LOST_CODE_TTL = 10L;

    }

    /**
     * 用户相关常量
     */
    public static class User{
        public static final String USER_NICK_NAME_PREFIX = "user_";
    }

    /**
     * 请求相关常量
     */
    public static class Request{
        public static final String AUTHORIZATION = "Authorization";
        public static final Integer SUBSTRING_NUM = 7;
    }

    /**
     * JWT相关常量
     */
    public static class JWT{
        // 秘钥
        static final String JWT_TOKEN_SECRET_KEY = "iskR^8v@e!XhJ&cbQuEtoMQJVxUTWEAR_LZWYM";
        // token过期时间（毫秒）
        public static final Integer TOKEN_EXPIRE = 24 * 2 * 60 * 60 * 1000;  // 24 * 2小时

        // 续期的过期时间差
        private static final Integer NEED_CREATE_TIME = 30 * 60 * 1000; // 30分钟
    }

    /*
        hutool ：BeanUtils.copyProperties() 忽略的字段

     */


    /**
     * 支付宝付款相关常量
     */
    public static class AliPay{
        /*
            网站扫码支付
         */
        // 销售产品码
       public static final String product_code = "FAST_INSTANT_TRADE_PAY";
    }

    /**
     * 角色相关常量
     */
    public static class Role{

        public  static final String VIP_ROLE_NAME = "vip";
        public  static final String USER_ROLE_NAME = "user";

        public  static final String SERVICE_ROLE_NAME = "客服";
        public  static final String PROGRAMMER_ROLE_NAME = "程序员";
        public  static final String MASSAGER_ROLE_NAME = "经理";

    }

    /**
     * 用户类型
     */
    public static class UserType{

        public  static final String FRONT_USER_TYPE = "front";
        public  static final String ADMIN_USER_TYPE = "admin";

    }

    /**
     * 志愿填报相关参数
     */
    public static class Guidance{

        public  static final Integer DEFAULT_MARK = 30;
    }


}
