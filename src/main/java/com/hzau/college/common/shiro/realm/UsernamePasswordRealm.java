package com.hzau.college.common.shiro.realm;

import com.hzau.college.common.shiro.token.BaseUsernamePasswordToken;
import com.hzau.college.common.util.Strings;
import com.hzau.college.pojo.dto.SysUserAndRolesDto;
import com.hzau.college.service.admin.AdminSysUserService;
import com.hzau.college.service.front.FrontSysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

//Username Password Realm，用户名密码登陆专用Realm
@Slf4j
public class UsernamePasswordRealm extends AuthenticatingRealm { //只管验证不管鉴权


    @Resource
    private FrontSysUserService frontUserService;
    @Resource
    private AdminSysUserService adminUserService;

    /*
        构造器里配置Matcher
        Matcher的作用是验证用户输入的密码是否正确（即比较前台传入密码和后台查询到的密码是否一致）
    */
    public UsernamePasswordRealm() {
        super();
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 加密方式
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列次数
        hashedCredentialsMatcher.setHashIterations(2);
        // 存储凭证十六进制编码
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        this.setCredentialsMatcher(hashedCredentialsMatcher);
    }

    /**
     * 通过该方法来判断是否由本realm来处理login请求
     *
     * 调用{@code doGetAuthenticationInfo(AuthenticationToken)}之前会shiro会调用{@code supper.supports(AuthenticationToken)}
     * 来判断该realm是否支持对应类型的AuthenticationToken,如果相匹配则会走此realm
     *
     * @return 所支持的类
     */
    @Override
    public Class<?> getAuthenticationTokenClass() {
        log.info("getAuthenticationTokenClass");
        return BaseUsernamePasswordToken.class;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        //继承但啥都不做就为了打印一下info
        boolean res = super.supports(token);//会调用↑getAuthenticationTokenClass来判断当前的Realm是否支持处理传入的Token
        log.debug("[UsernamePasswordRealm is supports]" + res);
        return res;
    }

    /**
     * 用户名和密码验证，login接口专用。
     * 只有调用Subject.login(token)后才会调用此方法，且传入的token必须且一定是BaseUsernamePasswordToken(getAuthenticationTokenClass()方法确定)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        SysUserAndRolesDto frontUserFromDB = null;
        String passwordFromDB = null;
        String salt = null;
        SimpleAuthenticationInfo info = null;

        BaseUsernamePasswordToken baseToken = (BaseUsernamePasswordToken) token;
        String userType = baseToken.getUserType();
        if (userType.equals("front")){
            // 从前台RABC表中查出用户和角色信息
            frontUserFromDB = frontUserService.getRolesByName(baseToken.getUsername());
            // 没有查出对象，说明用户名不存在
            if (frontUserFromDB == null) {
                throw new UnknownAccountException();
            }
            passwordFromDB = frontUserFromDB.getPassword();
            // 查出对象的密码为空，说明用户还未设置密码，让其使用手机号验证码登录
            if(Strings.isEmpty(passwordFromDB)){
                throw new DisabledAccountException(); // 禁用的账号
            }
            // 加密的盐，因为每个用户加密时所用的盐是唯一的，因此必须从数据库获取
             salt = frontUserFromDB.getSalt();
            // 此处将需要传递的信息(frontUserFromDB)放入SimpleAuthenticationInfo中，以便通过getSubject()方法拿取
            frontUserFromDB.setUserType(userType);
        }
        else if (userType.equals("admin")) {
            // 从前台RABC表中查出用户和角色信息
            frontUserFromDB = adminUserService.getRolesByName(baseToken.getUsername());
            // 没有查出对象，说明用户名不存在
            if (frontUserFromDB == null) {
                throw new UnknownAccountException(); // 未知的账号
            }
            passwordFromDB = frontUserFromDB.getPassword();
            // 查出对象的密码为空，说明用户还未设置密码，让其使用手机号验证码登录
            if(Strings.isEmpty(passwordFromDB)){
                throw new DisabledAccountException(); // 禁用的账号
            }
            // 加密的盐，因为每个用户加密时所用的盐是唯一的，因此必须从数据库获取
            salt = frontUserFromDB.getSalt();
            // 此处将需要传递的信息(frontUserFromDB)放入SimpleAuthenticationInfo中，以便通过getSubject()方法拿取
            frontUserFromDB.setUserType(userType);
        }else {
            throw new UnknownAccountException();
        }

        // 采用SimpleAuthenticationInfo最复杂的，带有盐值的构造方法
        info = new SimpleAuthenticationInfo(frontUserFromDB, passwordFromDB, ByteSource.Util.bytes(salt),
                getName());
        return info;
    }
}
