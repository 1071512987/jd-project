package com.hzau.college.service.impl.admin;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzau.college.common.enhance.MpLambdaQueryWrapper;
import com.hzau.college.common.mapStruct.MapStructs;
import com.hzau.college.common.prop.GuidanceProperties;
import com.hzau.college.common.shiro.token.BaseUsernamePasswordToken;
import com.hzau.college.common.util.*;
import com.hzau.college.pojo.dto.SysUserAndRolesDto;
import com.hzau.college.pojo.po.admin.AdminSysRole;
import com.hzau.college.pojo.po.admin.AdminSysUser;
import com.hzau.college.pojo.po.admin.AdminSysUserRole;
import com.hzau.college.pojo.po.front.FrontSysRole;
import com.hzau.college.pojo.po.front.FrontSysUser;
import com.hzau.college.pojo.po.front.FrontSysUserRole;
import com.hzau.college.pojo.result.CodeMsg;
import com.hzau.college.pojo.vo.JsonVo;
import com.hzau.college.pojo.vo.LoginVo;
import com.hzau.college.pojo.vo.list.GeeResBody;
import com.hzau.college.pojo.vo.list.JwtVo;
import com.hzau.college.pojo.vo.req.CodeVerifyReqVo;
import com.hzau.college.pojo.vo.req.LoginReqVo;
import com.hzau.college.pojo.vo.req.LostReqVo;
import com.hzau.college.service.admin.AdminSysUserRoleService;
import com.hzau.college.service.admin.AdminSysUserService;
import com.hzau.college.service.admin.AdminUserService;
import com.hzau.college.service.front.FrontSysUserRoleService;
import com.hzau.college.service.front.FrontSysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.hzau.college.common.util.Constants.Redis.*;
import static com.hzau.college.common.util.Constants.Role.PROGRAMMER_ROLE_NAME;
import static com.hzau.college.common.util.Constants.Role.USER_ROLE_NAME;
import static com.hzau.college.common.util.Constants.User.USER_NICK_NAME_PREFIX;

@Slf4j
@Service("AdminUserService")
public class AdminUserServiceImpl implements AdminUserService {

    private static GuidanceProperties.Geetest GEETEST;

    static {
        GEETEST = GuidanceProperties.get().getGeetest();
    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private FrontSysUserService frontUserService;
    @Resource
    private AdminSysUserService adminUserService;
    @Resource
    private FrontSysUserRoleService frontUserRoleService;
    @Resource
    private AdminSysUserRoleService adminUserRoleService;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private DbData dbData;


    @Override
    public JsonVo getAuun() {
        // 生成token
        String token = Jwts.createJwtTokenAuun();
        String uuid = UUID.randomUUID().toString();
        // 将Token放入Redis缓存
        stringRedisTemplate.opsForValue().set(REDIS_ANON_TOKEN_KEY + uuid, token, TOKEN_CACHE_TTL / 2, TimeUnit.MINUTES);

        // 生成返回信息
        JsonVo res = JsonVos.ok();
        res.setToken(token);
        return res;
    }

    /**
     * 忘记密码的手机号校验
     * @param phone 手机号
     * @return 操作成功/操作失败
     */
    @Override
    public JsonVo loginCode(HttpServletRequest request, String phone) {
        // 检查是否有人机验证的凭证
        String token = Jwts.getRequestToken(request);
        if (Strings.isEmpty(token)){
            return JsonVos.raise(false, CodeMsg.NO_TOKEN);
        }
        if (RedisUtil.Credential.getCredential(token)){
            return JsonVos.raise(false, CodeMsg.NOT_MAN_MACHINE);
        }
        // 增加一次凭证次数
        RedisUtil.Credential.increaseCount(token);

        // 1.校验手机号
        if (Regexs.isPhoneInvalid(phone)){
            // 2.如果不符合，返回错误信息
            return JsonVos.raise(false, CodeMsg.WRONG_PHONE);
        }
        // 3. 符合，生成验证码
        String code = RandomUtil.randomNumbers(4);
        // 4.发送验证码
        boolean res = Services.sms(phone, code);
        log.info("发送短信验证码成功，验证码{}", code);
        if (!res){ // 发送失败，服务器内部错误
            return JsonVos.raise(CodeMsg.INTERNAL_SERVER_ERROR);
        }
        // 5.保存验证码到redis
        // 此处耦合前后台验证码在同一个目录下
        stringRedisTemplate.opsForValue().set(REDIS_ADMIN_LOGIN_CODE_KEY + phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);
        return JsonVos.ok();
    }

    @Override
    public HashMap<String, String> codeVerity(HttpServletRequest request, CodeVerifyReqVo reqVo) {
        String token = Jwts.getRequestToken(request);
        if(Strings.isEmpty(token)){
            return JsonVos.raise(false, CodeMsg.NO_TOKEN);
        }
        // 1.初始化极验参数信息
        String captchaId = GEETEST.getCaptchaId();
        String captchaKey = GEETEST.getCaptchaKey();
        String domain = GEETEST.getDomain(); // geetest二次验证接口

        // 2.获取用户验证后前端传过来的验证流水号等参数
        String lotNumber = reqVo.getLot_number();
        String captchaOutput = reqVo.getCaptcha_output();
        String passToken = reqVo.getPass_token();
        String genTime = reqVo.getGen_time();

        // 3.生成签名
        // 生成签名使用标准的hmac算法，使用用户当前完成验证的流水号lot_number作为原始消息message，使用客户验证私钥作为key
        // 采用sha256散列算法将message和key进行单向散列生成最终的签名
        String signToken = new HMac(HmacAlgorithm.HmacSHA256, captchaKey.getBytes(StandardCharsets.UTF_8)).digestHex(lotNumber);

        // 4.上传校验参数到极验二次验证接口, 校验用户验证状态
        // 4.upload verification parameters to the secondary verification interface of GeeTest to validate the user verification status
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("lot_number", lotNumber);
        queryParams.add("captcha_output", captchaOutput);
        queryParams.add("pass_token", passToken);
        queryParams.add("gen_time", genTime);
        queryParams.add("sign_token", signToken);
        // captcha_id 参数建议放在 url 后面, 方便请求异常时可以在日志中根据id快速定位到异常请求
        String url = String.format(domain + "/validate" + "?captcha_id=%s", captchaId);
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        GeeResBody geeResBody = new GeeResBody();
        //注意处理接口异常情况，当请求极验二次验证接口异常时做出相应异常处理
        //保证不会因为接口请求超时或服务未响应而阻碍业务流程
        try {
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(queryParams, headers);
            ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
            String resBody = response.getBody();
            geeResBody = objectMapper.readValue(resBody, GeeResBody.class);
        }catch (Exception e){
            geeResBody.setResult("success");
            geeResBody.setReason("request geetest api fail");
        }

        // 5.根据极验返回的用户验证状态, 网站主进行自己的业务逻辑
        HashMap<String, String> res = new HashMap<>();

        if (geeResBody.getResult().equals("success")) {
            // 将成功凭证存入redis
            RedisUtil.Credential.setCredential(token);
            res.put("login", "success");
            res.put("reason", geeResBody.getReason());
        } else {
            res.put("login", "fail");
            res.put("reason", geeResBody.getReason());
        }
        return res;
    }


    /**
     * 忘记密码时，为传入手机号生成短信验证码
     * @param phone 手机号
     * @return 操作成功/操作失败
     */
    @Override
    public JsonVo lostCode(HttpServletRequest request, String phone) {
        // 检查是否有人机验证的凭证
        String token = Jwts.getRequestToken(request);
        if (Strings.isEmpty(token)){
            return JsonVos.raise(false, CodeMsg.NO_TOKEN);
        }
        if (RedisUtil.Credential.getCredential(token)){
            return JsonVos.raise(false, CodeMsg.NOT_MAN_MACHINE);
        }
        // 增加一次凭证次数
        RedisUtil.Credential.increaseCount(token);

        // 1.校验手机号
        if (Regexs.isPhoneInvalid(phone)){
            // 2.如果不符合，返回错误信息
            return JsonVos.raise(false, CodeMsg.WRONG_PHONE);
        }
        // 3. 符合，生成验证码
        String code = RandomUtil.randomNumbers(4);
        // 4.发送验证码
        boolean res = Services.sms(phone, code);
        log.info("发送短信验证码成功，验证码{}", code);
        if (!res){ // 发送失败，服务器内部错误
            return JsonVos.raise(CodeMsg.INTERNAL_SERVER_ERROR);
        }
        // 5.保存验证码到redis
        stringRedisTemplate.opsForValue().set(REDIS_ADMIN_LOST_CODE_KEY + phone, code, LOST_CODE_TTL, TimeUnit.MINUTES);
        return JsonVos.ok();
    }


    /**
     * 前台手机号登录业务层
     * @param reqVo 登录参数
     * @return 登录的签证token，以及告知前台这是否为新用户
     */
    @Override
    public LoginVo loginByPhone(HttpServletRequest request, LoginReqVo reqVo) {
        // 检查是否有人机验证的凭证
        String token = Jwts.getRequestToken(request);
        if (Strings.isEmpty(token)){
            return JsonVos.raise(false, CodeMsg.NO_TOKEN);
        }
        if (RedisUtil.Credential.getCredential(token)){
            return JsonVos.raise(false, CodeMsg.NOT_MAN_MACHINE);
        }
        // 增加一次凭证次数
        RedisUtil.Credential.increaseCount(token);

        Boolean isNew = null;
        String userType = reqVo.getUserType();

        // 1.校验手机号
        String phone = reqVo.getPhone();
        if (Regexs.isPhoneInvalid(phone)) {
            // 2.如果不符合，返回错误信息
            return JsonVos.raise(false, CodeMsg.WRONG_PHONE);
        }
        // 3.从redis获取验证码并校验
        String cacheCode = stringRedisTemplate.opsForValue().get(REDIS_ADMIN_LOGIN_CODE_KEY + phone);
        String code = reqVo.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            // 不一致，报错
            return JsonVos.raise(false, CodeMsg.WRONG_CAPTCHA);
        }

        // 4.一致，根据手机号和用户类型，去对应数据库表中查询用户 select * from tb_user where phone = ?
        SysUserAndRolesDto userFromDB = null;
        if (Objects.equals(userType, "front")){
            userFromDB = frontUserService.getRolesByPhone(phone);
        }else if (Objects.equals(userType, "admin")) {
            // TODO 图形验证码验证
            userFromDB = adminUserService.getRolesByPhone(phone);
        }else {
            return JsonVos.raise(false, CodeMsg.TOKEN_FORMAT_ERROR);
        }

            // 5.判断用户是否存在
        if (userFromDB == null) {
            // 6.不存在，创建新用户并保存,再次查询
            createUserWithPhone(phone, userType);
            // TODO 不必再查询数据库，直接存入默认的角色信息（因为刚创建）
            if (Objects.equals(userType, "front")){
                userFromDB = frontUserService.getRolesByPhone(phone);
            }else if (Objects.equals(userType, "admin")) {
                userFromDB = adminUserService.getRolesByPhone(phone);
            }

            // 告知前端为此手机创建新用户
            isNew = true;
        }
        // 存入必要的信息
        userFromDB.setUserType(userType);

        // 生成Token
        String newToken = null;
        if (userFromDB.getUserType().equals("front")){
            JwtVo jwtVo = new JwtVo(userFromDB.getId(), userFromDB.getUsername(),
                    Streams.map(userFromDB.getFrontRoles(), FrontSysRole::getName), userFromDB.getUserType());
            newToken = Jwts.createJwtTokenByUser(jwtVo);
            // 将Token放入Redis缓存
            stringRedisTemplate.opsForValue().set(Constants.Redis.REDIS_FRONT_LOGIN_TOKEN_KEY + userFromDB.getId(), newToken, TOKEN_CACHE_TTL, TimeUnit.MINUTES);
        }else if (userFromDB.getUserType().equals("admin")){
            JwtVo jwtVo = new JwtVo(userFromDB.getId(), userFromDB.getUsername(),
                    Streams.map(userFromDB.getAdminRoles(), AdminSysRole::getName), userFromDB.getUserType());
            newToken = Jwts.createJwtTokenByUser(jwtVo);
            // 将Token放入Redis缓存
            stringRedisTemplate.opsForValue().set(REDIS_ADMIN_LOGIN_TOKEN_KEY + userFromDB.getId(), newToken, TOKEN_CACHE_TTL, TimeUnit.MINUTES);
        }
        // 7.生成返回信息
        LoginVo loginVo = MapStructs.INSTANCE.dto2LoginVo(userFromDB);
        loginVo.setTempToken(newToken);
        loginVo.setIsNew(isNew);
        if(userFromDB.getAdminRoles() != null){
            loginVo.setRoles(Streams.map(userFromDB.getAdminRoles(), AdminSysRole::getName)); // 获取该用户角色信息
        }
        return loginVo;
    }

    /**
     * 用户名密码登录
     * @param reqVo 登录
     * @return 登录成功的用户基本信息
     */
    @Override
    public LoginVo loginByPassword(HttpServletRequest request, LoginReqVo reqVo) {
        // 检查是否有人机验证的凭证
        String token = Jwts.getRequestToken(request);
        if (Strings.isEmpty(token)){
            return JsonVos.raise(false, CodeMsg.NO_TOKEN);
        }
        if (RedisUtil.Credential.getCredential(token)){
            return JsonVos.raise(false, CodeMsg.NOT_MAN_MACHINE);
        }
        // 增加一次凭证次数
        RedisUtil.Credential.increaseCount(token);

        // TODO 后端验证
        String username = reqVo.getUsername();
        if (Strings.isEmpty(username) ) return JsonVos.raise(false, CodeMsg.WRONG_USERNAME);
        String password = reqVo.getPassword();
        if (Strings.isEmpty(password)) return JsonVos.raise(false, CodeMsg.WRONG_PASSWORD);

        BaseUsernamePasswordToken baseUsernamePasswordToken = new BaseUsernamePasswordToken(username, password, reqVo.getUserType());
        // TODO 将这里的验证放到Handler中是不是更好些
        try {
            SecurityUtils.getSubject().login(baseUsernamePasswordToken); //realms会根据UsernamePasswordToken这个类型选用合适的realm来处理登陆:UsernamePasswordRealm
        }catch (IncorrectCredentialsException e){
                return JsonVos.raise(false, CodeMsg.WRONG_PASSWORD);
        }catch (UnknownAccountException e){ // 未知的账号
            return JsonVos.raise(false, CodeMsg.WRONG_USERNAME);
        }catch (LockedAccountException e) { // 账号被锁定
            return JsonVos.raise(false, CodeMsg.USER_LOCKED);
        }catch (DisabledAccountException e){ // 该账号的密码不存在(禁用的账号)；此异常为上个异常:LockedAccountException的父类
            return JsonVos.raise(false, CodeMsg.NOT_SET_PASSWORD);
        }catch (Exception e){ // 兜底异常
            return JsonVos.raise(CodeMsg.OPERATE_ERROR);
        }
        // 来到这步，说明用户名密码通过
        SysUserAndRolesDto userFromDB = (SysUserAndRolesDto) SecurityUtils.getSubject().getPrincipal();
        // 去缓存查询该用户是否已经登录过(是否有token)
        Long id = userFromDB.getId();

        // TODO 目前是单登录顶替，模式待更改
//        if (BooleanUtil.isTrue(stringRedisTemplate.hasKey(Constants.Redis.REDIS_TOKEN_KEY + id))){
//            return JsonVos.raise(CodeMsg.REPEAT_LOGIN);
//        }

        // TODO 后台验证图形验证码？
        // 生成Token
        String newToken = null;
        if (userFromDB.getUserType().equals("front")){
            JwtVo jwtVo = new JwtVo(userFromDB.getId(), userFromDB.getUsername(),
                    Streams.map(userFromDB.getFrontRoles(), FrontSysRole::getName), userFromDB.getUserType());
             newToken = Jwts.createJwtTokenByUser(jwtVo);
            // 将Token放入Redis缓存
            stringRedisTemplate.opsForValue().set(Constants.Redis.REDIS_FRONT_LOGIN_TOKEN_KEY + id, newToken, TOKEN_CACHE_TTL, TimeUnit.MINUTES);
        }else if (userFromDB.getUserType().equals("admin")){
            JwtVo jwtVo = new JwtVo(userFromDB.getId(), userFromDB.getUsername(),
                    Streams.map(userFromDB.getAdminRoles(), AdminSysRole::getName), userFromDB.getUserType());
             newToken = Jwts.createJwtTokenByUser(jwtVo);
            // 将Token放入Redis缓存
            stringRedisTemplate.opsForValue().set(REDIS_ADMIN_LOGIN_TOKEN_KEY + id, newToken, TOKEN_CACHE_TTL, TimeUnit.MINUTES);
        }

        //生成返回值
        LoginVo loginVo = MapStructs.INSTANCE.dto2LoginVo(userFromDB);
        loginVo.setTempToken(newToken);
        if(userFromDB.getAdminRoles() != null){
            loginVo.setRoles(Streams.map(userFromDB.getAdminRoles(), AdminSysRole::getName)); // 获取该用户角色信息
        }
        return loginVo;
    }


    @Override
    public boolean logout(HttpServletRequest request) {
        // 获取令牌并验证
        String token = Jwts.getRequestToken(request);
        if(Strings.isEmpty(token)){ // TOKEN无效
            return JsonVos.raise(false, CodeMsg.TOKEN_FORMAT_ERROR);
        }
        Long id = Jwts.getId(token);
        // 将此令牌从Redis缓存删除
        boolean delete = BooleanUtil.isTrue(stringRedisTemplate.delete(Constants.Redis.REDIS_FRONT_LOGIN_TOKEN_KEY + id));
        return delete;
    }


    /**
     * 创新新用户并返回创建对象和更新的id
     * @param phone 用户手机号
     * @param userType 用户类型
     * @return 新创建用户
     */
    private void createUserWithPhone(String phone, String userType) {

        if (Objects.equals(userType, "front")){
            // 1.创建用户:初始用户名等于密码，昵称随机
            FrontSysUser userPo = new FrontSysUser();
            userPo.setPhone(phone);
            userPo.setUsername(phone);
            userPo.setNickname(USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
            // 没有设置密码时，不设置盐值
//        userPo.setSalt(RandomUtil.randomNumbers(4));
            frontUserService.save(userPo); // 保存用户:此处自动更新id
            FrontSysUserRole userRolePo = new FrontSysUserRole();
            Short addRoleId = dbData.getFrontRoleMap().get(USER_ROLE_NAME); // TODO USER_ROLE_NAME
            userRolePo.setRoleId(addRoleId);
            userRolePo.setUserId(userPo.getId());
            frontUserRoleService.save(userRolePo);
        }else if (Objects.equals(userType, "admin")) {
            // TODO 后台保存
            // 1.创建用户:初始用户名等于密码，昵称随机
            AdminSysUser userPo = new AdminSysUser();
            userPo.setPhone(phone);
            userPo.setUsername(phone);
            userPo.setNickname(USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
            adminUserService.save(userPo); // 保存用户:此处自动更新id
            AdminSysUserRole userRolePo = new AdminSysUserRole();
            Short addRoleId = dbData.getAdminRoleMap().get(PROGRAMMER_ROLE_NAME); // TODO USER_ROLE_NAME
            userRolePo.setRoleId(addRoleId);
            userRolePo.setUserId(userPo.getId());
            adminUserRoleService.save(userRolePo);
        }
//        System.out.println("保存后:" + userPo );
        // 3.查询出服务器自动补全id的新用户 ×  直接get就能获取
//        FrontSysUser newUser = userService.query().eq("phone", phone).one();
//        return userPo;
    }

    @Override
    public boolean lostPassword(LostReqVo reqVo) {
        boolean res = false; // 如果不为前后台用户表示，则返回false
        String userType = reqVo.getUserType();

        // 1.校验手机号
        String phone = reqVo.getPhone();
        if (Regexs.isPhoneInvalid(phone)) {
            // 如果不符合，返回错误信息
            return JsonVos.raise(false, CodeMsg.WRONG_PHONE);
        }
        // 2.从redis获取验证码并校验
        String cacheCode = stringRedisTemplate.opsForValue().get(REDIS_ADMIN_LOST_CODE_KEY + phone);
        String code = reqVo.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            // 不一致，报错
            return JsonVos.raise(false, CodeMsg.WRONG_CAPTCHA);
        }
        // 3.验证码一致：设置新密码
        if (Objects.equals(userType, "front")) {
            MpLambdaQueryWrapper<FrontSysUser> wrapper = new MpLambdaQueryWrapper<>();
            wrapper.eq(FrontSysUser::getPhone, phone); // 条件：phone相同

            FrontSysUser user = new FrontSysUser();
            // 将密码进行加密，此时生成新盐
            String salt = RandomUtil.randomNumbers(4);
            String digestHex = new Digester(DigestAlgorithm.MD5).setSalt(salt.getBytes()).setDigestCount(2).digestHex(reqVo.getNewPassword());
            //更新字段的值
            user.setSalt(salt);
            user.setPassword(digestHex);
            res = frontUserService.update(user, wrapper);
        } else if (Objects.equals(userType, "admin")) {
            MpLambdaQueryWrapper<AdminSysUser> wrapper = new MpLambdaQueryWrapper<>();
            wrapper.eq(AdminSysUser::getPhone, phone); // 条件：phone相同

            AdminSysUser user = new AdminSysUser();
            // 将密码进行加密，此时生成新盐
            String salt = RandomUtil.randomNumbers(4);
            String digestHex = new Digester(DigestAlgorithm.MD5).setSalt(salt.getBytes()).setDigestCount(2).digestHex(reqVo.getNewPassword());
            user.setSalt(salt);
            user.setPassword(digestHex);
            res = adminUserService.update(user, wrapper);
        }
        return  res;
    }

    /**
     * 【已登录用户使用】设置密码
     */
    @Override
    public boolean setPassword(HttpServletRequest request, String newPassword) {
        boolean res = false;
        // 获取令牌并验证
        String token = Jwts.getRequestToken(request);
        if(Strings.isEmpty(token)){ // TOKEN无效
            return JsonVos.raise(false, CodeMsg.TOKEN_FORMAT_ERROR);
        }
        String userType = Jwts.getUserType(token);
        Long id = Jwts.getId(token);
        if(Objects.equals(userType, "front")){
            // 根据id去查找数据库更新密码
            FrontSysUser user = new FrontSysUser();
            user.setId(id); //根据id作为更新条件
            // 将密码进行加密，此时生成新盐
            String salt = RandomUtil.randomNumbers(4);
            String digestHex = new Digester(DigestAlgorithm.MD5).setSalt(salt.getBytes()).setDigestCount(2).digestHex(newPassword);
            //更新字段的值
            user.setSalt(salt);
            user.setPassword(digestHex);
            res = frontUserService.updateById(user);
        }else if (Objects.equals(userType, "admin")){
            // 根据id去查找数据库更新密码
            AdminSysUser user = new AdminSysUser();
            user.setId(id); //根据id作为更新条件
            // 将密码进行加密，此时生成新盐
            String salt = RandomUtil.randomNumbers(4);
            String digestHex = new Digester(DigestAlgorithm.MD5).setSalt(salt.getBytes()).setDigestCount(2).digestHex(newPassword);
            //更新字段的值
            user.setSalt(salt);
            user.setPassword(digestHex);
            res = adminUserService.updateById(user);
        }else {
            return false;
        }

        return res;
    }

}
