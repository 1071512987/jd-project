package com.hzau.college.common.aop;

import com.hzau.college.common.threadLocal.ThreadLocalInfo;
import com.hzau.college.pojo.vo.DataJsonVo;
import com.hzau.college.pojo.vo.JsonVo;
import com.hzau.college.pojo.vo.PageJsonVo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component
@Slf4j
public class TokenAspect {
    @Resource
    private ThreadLocalInfo threadLocalInfo;

    /**
     * 切点，需要被拦截的点
     */
    @Pointcut("execution(public * com.hzau.college.controller.*.*(..))")
    public void aspect() {

    }

    /**
     * 针对切点定义环绕事件
     * 获得拦截方法换回的R对象，向R对象put token
     *
     * @param point 切点
     * @return Object 原返回内容
     * @throws Throwable 抛出的异常
     */
    @Around("aspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        log.info("Current thread ID: " + Thread.currentThread().getId());

        // 查看token是否有值（新token）
        String token = threadLocalInfo.getToken();
        Object object = point.proceed();

        // 无新token则执行原返回结果
        if (token == null) {
            return object;
        }
        // 有新值，先清空，再添加返回结果
        threadLocalInfo.clearToken();
        // 依据返回结果进行新token的添加
        if (point.proceed() instanceof PageJsonVo) {
            // 原结果返回PageJsonVo
            PageJsonVo pageJsonVo = (PageJsonVo) point.proceed();
            pageJsonVo.setToken(token);
            return pageJsonVo;
        } else // 原结果返回DataJsonVo
            if (point.proceed() instanceof DataJsonVo) {
                DataJsonVo dataJsonVo = (DataJsonVo) point.proceed();
                dataJsonVo.setToken(token);
                return dataJsonVo;
            } else {
                // 原结果返回JsonVo
                JsonVo jsonVo = (JsonVo) point.proceed();
                jsonVo.setToken(token);
                return jsonVo;
            }
    }
}