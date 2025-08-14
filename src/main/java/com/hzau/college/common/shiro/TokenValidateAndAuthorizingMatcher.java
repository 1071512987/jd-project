package com.hzau.college.common.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;


@Slf4j
public class TokenValidateAndAuthorizingMatcher implements CredentialsMatcher {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        BearerToken bearerToken = (BearerToken) authenticationToken;
        String bearerTokenString = bearerToken.getToken();
        log.debug(bearerTokenString);

//        // 验证Token格式合法性
//        if (!Jwts.verifyTokenOfUser(bearerTokenString)){
//            return false;
//        }
        // 查看Redis中的用户令牌是否拉黑
//        String value = stringRedisTemplate.opsForValue().get(Constants.Redis.REDIS_TOKEN_KEY + bearerTokenString);
//        log.info("doCredentialsMatch 是否存在Redis黑名单中");
//        boolean res = (Strings.isEmpty(value) || !value.equals(Constants.Redis.LOGOUT_TOKEN_VALUE));
//
//        return res;
    return false;
    }
}
