package com.hzau.college.common.util;

import cn.hutool.core.util.NumberUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hzau.college.pojo.result.CodeMsg;
import com.hzau.college.pojo.vo.list.JwtVo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hzau.college.common.util.Constants.JWT.JWT_TOKEN_SECRET_KEY;
import static com.hzau.college.common.util.Constants.JWT.TOKEN_EXPIRE;

/**
 * JWT工具类
 * 用于生成token、校验token、获取token中的信息
 */
@Slf4j
public class Jwts {

    /**
     * 创建匿名用户的token
     */
    public static String createJwtTokenAuun() {
        Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRE);
        Algorithm algorithm = Algorithm.HMAC256(JWT_TOKEN_SECRET_KEY);    //使用密钥进行哈希
        // 附带username信息的token
        List<String> roles = new ArrayList<>();
        roles.add("anon");
        return JWT.create()
                .withClaim("userType", "front")
                .withClaim("username", "annoUser")
                .withClaim("roles", roles)
                .withExpiresAt(date)  //过期时间
                .sign(algorithm);     //签名算法
    }

    /**
     * 创建登录用户的token
     */
    public static String createJwtTokenByUser(JwtVo vo) {
        Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRE);
        Algorithm algorithm = Algorithm.HMAC256(JWT_TOKEN_SECRET_KEY);    //使用密钥进行哈希
        // 附带username信息的token
        List<String> roles = vo.getRoles();
        return JWT.create()
                .withClaim("id", vo.getId())
                .withClaim("userType", vo.getUserType())
                .withClaim("username", vo.getUsername())
                .withClaim("roles", roles)
//                .withClaim("permissions",permissionService.getPermissionsByUser(vo))
                .withExpiresAt(date)  //过期时间
                .sign(algorithm);     //签名算法
        //r-p的映射在服务端运行时做，不放进token中
    }


    /**
     * 校验token是否正确
     */
    public static boolean verifyTokenOfUser(String token) throws JWTVerificationException {//user要从securityManager拿，确保用户用的是自己的token
        log.info("verifyTokenOfUser");

        //根据密钥生成JWT效验器
        String username = getUsername(token);
        Algorithm algorithm = Algorithm.HMAC256(JWT_TOKEN_SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("username", username)//从不加密的消息体中取出username
                .build();
        // 一个是直接从客户端传来的token，一个是根据盐和用户名等信息生成secret后再生成的token
        try {
            DecodedJWT jwt = verifier.verify(token);
        }catch (TokenExpiredException te){ // Token过期异常，直接抛出
            return JsonVos.raise(false, CodeMsg.TOKEN_EXPIRED);
        }catch (JWTVerificationException je){ // 其他异常：token格式错误
            return  false;
        }
        //能走到这里
        return true;
    }


    /**
     * 在token中获取到username信息
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 在token中获取到id信息
     */
    public static Long getId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Claim id = jwt.getClaim("id");
            return NumberUtil.parseLong(id.toString());
        } catch (JWTDecodeException | NumberFormatException e) {
            return null;
        }
    }

    /**
     * 在token中获取到userType信息
     */
    public static String getUserType(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String userType = jwt.getClaim("userType").asString();

            return userType;
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 在token中获取到roles信息
     */
    public static List<String> getRoles(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            List<String> roles = jwt.getClaim("roles").asList(String.class);

            return roles;
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 在token中获取到claim信息
     */
    public static String getClaim(String token, String claimName) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claimName).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 在token中获取到所有claim信息 // 临时测试
     */
//    @ApiIgnore
    public static String getAllClaim(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            ArrayList<String> claims = new ArrayList<>();
            claims.add(jwt.getClaim("id").asLong().toString());
            claims.add(jwt.getClaim("username").asString());
            claims.add(jwt.getClaim("userType").asString());
            claims.add(jwt.getClaim("roles").asString());

            System.out.println(claims);

        } catch (JWTDecodeException e) {
            return null;
        }
        return null;
    }

    /**
     * 从token中重建用户
     */
    public static JwtVo recreateUserFromToken(String token) {
        JwtVo vo = new JwtVo();
        DecodedJWT jwt = JWT.decode(token);

        vo.setId(jwt.getClaim("id").asLong());
        vo.setUsername(jwt.getClaim("username").asString());
        vo.setRoles(jwt.getClaim("roles").asList(String.class));
        vo.setUserType(jwt.getClaim("userType").asString());
        //r-p映射在运行时去取
        return vo;
    }

    /**
     * 判断是否过期
     */
    public static boolean isExpire(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().getTime() < System.currentTimeMillis();
    }

    /**
     * 从请求中获取token
     */
    public static String getRequestToken(HttpServletRequest request){
        log.info("请求地址: " + request.getRequestURI());
        //从请求头获取token
        String token = "";
        token = request.getHeader("Authorization");
        if(Strings.isEmpty(token)){
            //从请求体获取token
            token = request.getParameter("Authorization");
        }
        // 获取不到token，抛出异常
        if(Strings.isEmpty(token)) return JsonVos.raise(false, CodeMsg.NO_TOKEN);
        // token格式不正确，抛出异常
        if (token.length() < 8 || !token.startsWith("Bearer") || !Jwts.verifyTokenOfUser(token.substring(7))) {
            return JsonVos.raise(false, CodeMsg.TOKEN_FORMAT_ERROR);
        }
        return token.substring(7);
    }

//    /**
//     * 获取过期时间
//     */
//    /**
//     * 是否需要重新生成token （为了延续token时长）
//     *
//     */
//    public static boolean needCreate(String token) {
//        //根据密钥生成JWT效验器
//        String username = getUsername(token);
//        Algorithm algorithm = Algorithm.HMAC256(JWT_TOKEN_SECRET_KEY);
//        JWTVerifier verifier = JWT.require(algorithm)
//                .withClaim("username", username)//从不加密的消息体中取出username
//                .build();
//        // 一个是直接从客户端传来的token，一个是根据盐和用户名等信息生成secret后再生成的token
//        DecodedJWT jwt = verifier.verify(token);
//        Date timeoutDate = jwt.getExpiresAt();
//        Calendar calendar = Calendar.getInstance();
//        int i = EXPIRE_TIME - NEED_CREATE_TIME;
//        calendar.add(Calendar.MILLISECOND, EXPIRE_TIME - NEED_CREATE_TIME);
//        return timeoutDate.before(calendar.getTime());
//    }
}
