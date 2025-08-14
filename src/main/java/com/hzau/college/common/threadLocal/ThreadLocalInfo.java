package com.hzau.college.common.threadLocal;


import org.springframework.stereotype.Component;


@Component
public class ThreadLocalInfo {
    private ThreadLocal<String> localString = new ThreadLocal<>();

//    private ThreadLocal<Object> localObject = new ThreadLocal<>();

    public void setToken(String token){
        localString.set(token);
    }

    public String getToken(){
        return localString.get();
    }

    public void clearToken(){
        localString.remove();
    }

//    public void setUserInfo(FrontUserInfoVo infoVo){
//        localObject.set(infoVo);
//    }
//
//    public FrontUserInfoVo getUserInfo(){
//        return localObject.get();
//    }
//
//    public void clearObject(){
//        localObject.remove();
//    }
}