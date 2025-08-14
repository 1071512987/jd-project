package com.hzau.college.common.aop;

import com.google.common.util.concurrent.RateLimiter;
import com.hzau.college.common.util.IPs;
import com.hzau.college.common.util.JsonVos;
import com.hzau.college.service.LoadingCacheService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
@Scope
@Aspect
public class IPLimitAspect {

    @Resource
    private HttpServletRequest request;
    @Resource
    private LoadingCacheService loadingCacheService;

    @Pointcut("@annotation(com.hzau.college.common.annotation.IPLimit)")
    public void ipLimit() {

    }

    @Around("ipLimit()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = null;
        String ipAddr = IPs.getIpAddr(request);
        RateLimiter limiter = loadingCacheService.getIPLimiter(ipAddr);
        if (limiter.tryAcquire()) {
            // 获得令牌（不限制访问）
            obj = joinPoint.proceed();
        } else {
            // 未获得令牌（限制访问）
            obj = JsonVos.raise("访问次数过多");
        }
        return obj;
    }

}
