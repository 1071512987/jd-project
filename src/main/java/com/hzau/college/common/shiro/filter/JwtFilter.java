package com.hzau.college.common.shiro.filter;

import cn.hutool.core.util.BooleanUtil;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.hzau.college.common.threadLocal.ThreadLocalInfo;
import com.hzau.college.common.util.Constants;
import com.hzau.college.common.util.JsonVos;
import com.hzau.college.common.util.Jwts;
import com.hzau.college.pojo.result.CodeMsg;
import com.hzau.college.pojo.vo.list.JwtVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.hzau.college.common.util.Constants.Redis.TOKEN_CACHE_TTL;
import static com.hzau.college.common.util.Jwts.getRequestToken;

@Slf4j
@Component
@Scope("prototype")
public class JwtFilter extends BearerHttpAuthenticationFilter {

    @Resource
    private ThreadLocalInfo threadLocalInfo;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 当isAccessAllowed返回false时，就会调用这个方法。
     * 本类没有重写 isAccessAllowed 方法，这意味着它会使用父类的该方法。这个方法的默认实现是始终返回 false，
     * 这就意味着无论什么情况，onAccessDenied 方法总是会被调用。
     *
     * 在这个方法中进行token的校验，如果校验通过，就会调用executeLogin方法，进入realm进行登陆操作。
     *
     * 如果返回true：允许访问。可以进入下一个链条调用（比如Filter、拦截器、控制器等）
     * 如果返回false：不允许访问。不会进入下一个链条调用（比如Filter、拦截器、控制器等）
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req= (HttpServletRequest) request;
        log.info("Current thread ID: " + Thread.currentThread().getId());

        //清空threadLocal的 String
        threadLocalInfo.clearToken();
        String token = getRequestToken(req); // 获取请求头中的token并判空和格式验证

        try{
            log.info("onAccessDenied token合法性验证");
            // 验证token是否有效，如果验证有问题（1.令牌内容有问题、2.令牌过期）就会抛出异常
            Jwts.verifyTokenOfUser(token);
        }catch (TokenExpiredException e){ // 出现令牌过期异常，再次判断Redis
            // 判断redis是否还有令牌，进行续期操作
            Long id = Jwts.getId(token);
            if(BooleanUtil.isTrue(stringRedisTemplate.hasKey(Constants.Redis.REDIS_FRONT_LOGIN_TOKEN_KEY + id))){
                /*
                 redis里面存在令牌，查看是否与客户端一致。
                  不一致说明过期仍使用，抛出token已续签未更换异常；一致说明客户端的令牌过期，但是redis令牌还未过期，需要续签
                  删除redis中的旧令牌token。获取userid并生成新的令牌，并存进redis和threadLocalToken
                */
                // ①不一致，抛出异常
                if (!Objects.equals(stringRedisTemplate.opsForValue().get(Constants.Redis.REDIS_FRONT_LOGIN_TOKEN_KEY + id), token)) {
                    return  JsonVos.raise(false, CodeMsg.TOKEN_NOT_RENEWED);
                }
                // ②一致，删除旧令牌，生成新令牌
                stringRedisTemplate.delete(token);
                JwtVo newJwtVo = Jwts.recreateUserFromToken(token);
                String newToken = Jwts.createJwtTokenByUser(newJwtVo);
                stringRedisTemplate.opsForValue().set(Constants.Redis.REDIS_FRONT_LOGIN_TOKEN_KEY + id, newToken, TOKEN_CACHE_TTL, TimeUnit.MINUTES);
                threadLocalInfo.setToken(newToken);
            } else{//客户端令牌过期了，并且服务端的令牌也过期了，此时需要重新登陆
               return  JsonVos.raise(false, CodeMsg.TOKEN_EXPIRED);
            }
        }catch (Exception e){ //令牌格式有问题
            return JsonVos.raise(false, CodeMsg.TOKEN_FORMAT_ERROR);
        }

        // 验证通过，执行Realm类
        boolean res = executeLogin(request,response);
        // 兜底的异常
        if (!res){
            return JsonVos.raise(false, CodeMsg.TOKEN_FORMAT_ERROR);
        }
        return res;
    }
//        boolean res =
//        log.info("onAccessDenied "+res);
//        if (!res){
//            return JsonVos.raise("token失效或异常，请重新登录");
////            throw new RuntimeException("token失效或异常，请重新登录");//jwt验证器的错误抛不上来，应该是shiro机制的不完善（）
//        }
////        jwt验证失败导致的登陆失败里，拿不到jwt验证失败的具体异常，因为要试过多个realm jwt的token错了还会去试其他realm，
////        导致他把具体异常截断了，这里只拿得到一个"试过所有realm但是都没登陆成功"的异常。
//        return res;
//    }

}
