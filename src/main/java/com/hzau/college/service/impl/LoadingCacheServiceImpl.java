package com.hzau.college.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import com.hzau.college.service.LoadingCacheService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service("LoadingCacheService")
public class LoadingCacheServiceImpl implements LoadingCacheService {
    @Override
    public RateLimiter getIPLimiter(String ipAddr) throws ExecutionException {
        return ipRequestCaches.get(ipAddr);
    }

    LoadingCache<String, RateLimiter> ipRequestCaches = CacheBuilder.newBuilder()
            .maximumSize(1000)// 设置缓存个数
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String s) throws Exception {
                    return RateLimiter.create(45);// 新的IP初始化 (限流每秒45个令牌响应(同一个ip1秒访问45次接口))
                }
            });

}
