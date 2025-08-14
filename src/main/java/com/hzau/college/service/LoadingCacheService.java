package com.hzau.college.service;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutionException;

public interface LoadingCacheService {

    public RateLimiter getIPLimiter(String ipAddr) throws ExecutionException;
}
