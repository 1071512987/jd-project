package com.hzau.college.controller.admin;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.hzau.college.common.annotation.IPLimit;
import com.hzau.college.common.annotation.LoginSign;
import com.hzau.college.common.annotation.validator.Phone;
import com.hzau.college.common.threadLocal.ThreadLocalInfo;
import com.hzau.college.common.util.JsonVos;
import com.hzau.college.common.util.Jwts;
import com.hzau.college.common.util.RedisUtil;
import com.hzau.college.common.util.Strings;
import com.hzau.college.pojo.result.CodeMsg;
import com.hzau.college.pojo.vo.DataJsonVo;
import com.hzau.college.pojo.vo.JsonVo;
import com.hzau.college.pojo.vo.LoginVo;
import com.hzau.college.pojo.vo.req.CodeVerifyReqVo;
import com.hzau.college.pojo.vo.req.LoginReqVo;
import com.hzau.college.pojo.vo.req.LostReqVo;
import com.hzau.college.service.admin.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;


@RestController
@RequestMapping("api/adminUser")
@Api("后台用户相关接口")
@Slf4j
@Validated
public class AdminUserController {

    @Resource
    private AdminUserService service;

    @Resource
    private ThreadLocalInfo threadLocalInfo;




    /**
     * 获取匿名登录token
     * 通过 @IPLimit 限制分发量，防止爆破
     *
     * @return 带有token的普通返回结果
     */
    @IPLimit
    @ApiOperation("1. 【获取匿名登录的token】接口")
    @PostMapping("/getAuun")
    @ApiOperationSupport(order = 1)
    public JsonVo getAuun() {
        return service.getAuun();
    }

    /**
     * 登录接口
     *
     * 需要标识前后台
     * 【手机号验证码登录】或【用户名密码登陆】
     * @param reqVo 登录参数，如有手机号和验证码，优先使用手机号登录/注册
     * @return token + 用户基础data
     */
    @ApiOperation("2. 【登录】接口【需要标识前后台，优先手机号】")
    @PostMapping("/login")
    @ApiOperationSupport(order = 2)
    public DataJsonVo<LoginVo> login(HttpServletRequest request, @Valid LoginReqVo reqVo){
        LoginVo res;
        if (!Strings.isEmpty(reqVo.getPhone()) && !Strings.isEmpty(reqVo.getCode())){
            res = service.loginByPhone(request, reqVo);
        }else {
            res = service.loginByPassword(request, reqVo);
        }
        String token = res.getTempToken();
        return JsonVos.ok(res, token);
    }

    /**
     * geetest人机验证服务端接口
     *
     * @param reqVo 前端人机验证获取的参数
     * @return 验证成功与否的信息
     */
    @ApiOperation("3. 【人机验证】接口")
    @PostMapping("/codeVerify")
    @ApiOperationSupport(order = 3)
    public DataJsonVo<HashMap<String, String>> codeVerify(HttpServletRequest request,
                                              @RequestBody @Valid CodeVerifyReqVo reqVo) {
        return JsonVos.ok(service.codeVerity(request, reqVo));
    }


    /**
     * 不用标识前后台
     * 发送手机验证码
     * @param phone 待发送的手机号
     * @return 操作成功/操作失败
     */
    @ApiOperation("4. 【登录、注册发送手机验证码】接口")
    @PostMapping("/loginCode")
    @ApiOperationSupport(order = 4)
    public JsonVo loginCode(HttpServletRequest request,
                            @RequestParam @Phone @NotBlank(message = "手机号不能为空") String phone) {
        // 发送短信验证码并保存验证码
        return service.loginCode(request, phone);
    }


    /**
     * 忘记密码接口
     *
     * 需要标识前后台，仅限未登录用户使用
     * 通过手机号在redis中校验验证码，通过则通过手机号找到用户，修改密码
     * @param reqVo 找回密码参数
     * @return 操作成功/操作失败
     */
    @ApiOperation("5. 【忘记密码】接口【未登录用户使用，需要标识前后台】")
    @PostMapping("/lostPassword")
    @ApiOperationSupport(order = 5)
    public JsonVo setPassword(@Valid LostReqVo reqVo) {
        boolean res = service.lostPassword(reqVo);
        if(res){
            return JsonVos.ok(CodeMsg.OPERATE_OK);
        }
        return JsonVos.error(CodeMsg.OPERATE_ERROR);
    }


    /**
     * 不用标识前后台
     * 发送手机验证码
     *
     * @param phone 待发送的手机号
     * @return 操作成功/操作失败
     */
    @ApiOperation("6. 【忘记密码发送手机验证码】接口")
    @PostMapping("/lostCode")
    @ApiOperationSupport(order = 6)
    public JsonVo lostCode(HttpServletRequest request,
                           @RequestParam @Phone @NotBlank(message = "手机号不能为空") String phone) {
        // 发送短信验证码并保存验证码
        return service.lostCode(request, phone);
    }

    /**
     * 设置密码接口
     *
     * 不用标识前后台
     * 通过token找到userType和id，更新对应前后台表中字段
     *  @param request HttpServletRequest
     *  @param newPassword 新密码
     *  @return 操作成功/操作失败
     */
    @ApiOperation("7. 【设置密码】接口【已登录用户使用】")
    @PostMapping("/setPassword")
    @ApiOperationSupport(order = 7)
    public JsonVo setPassword(HttpServletRequest request,@RequestParam @NotBlank(message = "新密码不能为空") String newPassword) {
        boolean res = service.setPassword(request, newPassword);
        if(res){
            return JsonVos.ok(CodeMsg.OPERATE_OK);
        }
        return JsonVos.error(CodeMsg.OPERATE_ERROR);
    }

    /**
     * 退出登录接口
     *
     * 不用标识前后台
     * 通过token找到id，删除缓存中对应value以实现退出登录
     * @param request HttpServletRequest
     * @return 操作成功/操作失败
     */
    @LoginSign
    @ApiOperation("8. 【退出登录】接口")
    @PostMapping("/logout")
    @ApiOperationSupport(order = 8)
    public JsonVo logout(HttpServletRequest request) {
        boolean res = service.logout(request);
        // 清除ThreadLocal残留：为下一个线程做准备
//        threadLocalInfo.clearString();
//        threadLocalInfo.clearObject();
        threadLocalInfo.clearToken();
        if(res){
            return JsonVos.ok(CodeMsg.OPERATE_OK);
        }
        return JsonVos.error(CodeMsg.OPERATE_ERROR);
    }

    @ApiOperation("【开发阶段】给当前token一个验证成功的标识")
    @PostMapping("/manMachineTrue")
    @ApiOperationSupport(order = 9)
    public JsonVo manMachineTrue(HttpServletRequest request) {
        String token = Jwts.getRequestToken(request);
        if(Strings.isEmpty(token)){
            return JsonVos.raise(false, CodeMsg.NO_TOKEN);
        }
        RedisUtil.Credential.setCredential(token);
        return JsonVos.ok(CodeMsg.OPERATE_OK);
    }

}

