package com.hzau.college.service.admin;

import com.hzau.college.pojo.vo.JsonVo;
import com.hzau.college.pojo.vo.LoginVo;
import com.hzau.college.pojo.vo.req.CodeVerifyReqVo;
import com.hzau.college.pojo.vo.req.LoginReqVo;
import com.hzau.college.pojo.vo.req.LostReqVo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface AdminUserService {

    LoginVo loginByPassword(HttpServletRequest request, LoginReqVo reqVo);

    LoginVo loginByPhone(HttpServletRequest request, LoginReqVo reqVo);

    boolean logout(HttpServletRequest request);

    JsonVo loginCode(HttpServletRequest request, String phone);

    boolean setPassword(HttpServletRequest request, String newPassword);

    boolean lostPassword(LostReqVo reqVo);

    JsonVo lostCode(HttpServletRequest request,String phone);

    JsonVo getAuun();

    HashMap<String, String> codeVerity(HttpServletRequest request, CodeVerifyReqVo reqVo);
}
