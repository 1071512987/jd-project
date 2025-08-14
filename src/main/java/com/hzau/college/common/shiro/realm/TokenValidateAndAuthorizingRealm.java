package com.hzau.college.common.shiro.realm;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.hzau.college.common.util.DbData;
import com.hzau.college.common.util.Jwts;
import com.hzau.college.pojo.vo.list.JwtVo;
import com.hzau.college.service.front.FrontSysResourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 验证token与鉴权realm
 * 为了有鉴权方法，最高只能继承AuthorizingRealm，但是这个父类会自动生成一个SimpleCredentialsMatcher强制走matcher。只能说这套Realm的继承树不够成熟
 * 鉴权方法放到usernamePasswordRealm里实现也可以，这样这个Realm可以直接继承Realm，最清爽简洁。
 * 但是逻辑上应该放这里，毕竟除了login之外的所有业务接口都靠这个realm验证身份。且u+p的验证方式是可选项，jwt的必然存在
 *
 */
@Slf4j
public class TokenValidateAndAuthorizingRealm extends AuthorizingRealm {

    @Resource
    private FrontSysResourceService frontSysResourceService;

    @Resource
    private DbData dbData;

    // 前端角色权限映射表（数量有限且一般固定，项目部署时直接静态存储），只从中get没有线程问题
//    private Map<String, List<String>> frontRolePermissionsMap;

//    @Resource
//    public void setUserService(FrontSysResourceService resourceService){
//        this.frontSysResourceService = resourceService;
//        frontRolePermissionsMap = resourceService.getRolePermissionMap();
//    }

//    @Autowired
//    public TokenValidateAndAuthorizingRealm(TokenValidateAndAuthorizingMatcher matcher) {
//        //CredentialsMatcher，自定义匹配策略（即验证jwt token的策略）
//        super(matcher);
//    }

    @Override
    public String getName() {
        return "TokenValidateAndAuthorizingRealm";
    }

    @Override
    public Class<?> getAuthenticationTokenClass() {
        //设置由本realm处理的token类型。BearerToken是在filter里自动装配的。
        return BearerToken.class;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        boolean res=super.supports(token);
        log.debug("[TokenValidateRealm is supports]" + res);
        return res;
    }


    /**
     * 鉴权部分
     * @param principalCollection the primary identifying principals of the AuthorizationInfo that should be retrieved.
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.debug("doGetAuthorizationInfo 权限验证");
//        JwtVo user = (JwtVo) SecurityUtils.getSubject().getPrincipal();
        JwtVo user = (JwtVo) principalCollection.getPrimaryPrincipal();
//        if (user.getId() == null){
//            return JsonVos.raise(false, CodeMsg.LACK_PERMISSION);
//        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(user.getRoles()); //roles跟着user走，放到token里。普通功能直接用token中的。只有在重要操作时才需去数据库验一遍，减轻压力

        Set<String> permissions = new HashSet<>();
        // 前后台用户鉴权方式不同
        // 前台用户：静态获取（一般不会更改）
        // 后台用户：从缓存动态获取（可能变更）
        Map<String, List<String>> frontRolePermissionsMap = dbData.getFrontRolePermissionsMap();
        if (user.getUserType().equals("front")) {
            for (String role : user.getRoles()) {
                permissions.addAll(dbData.getFrontRolePermissionsMap().get(role));
            }
        }else if (user.getUserType().equals("admin")){
            for (String role : user.getRoles()) {
                permissions.addAll(dbData.getAdminRolePermissionsMap().get(role));
            }
        }
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 方法本来负责验证身份，即确定当前用户是谁，此处更重要的内容是装配用户信息
     *
     * 这个方法会从传入的AuthenticationToken（即BearerToken）中获取身份信息（即JWT token），
     * 然后解析JWT token以获取用户信息，并将这些信息封装在一个SimpleAuthenticationInfo对象中返回。
     * 这个SimpleAuthenticationInfo对象包含了用户的身份信息（JwtVo对象）和凭证信息（JWT token字符串），
     * 这些信息将被Shiro用于后续的授权检查(doGetAuthorizationInfo方法)。
     */
    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException, TokenExpiredException {
        log.debug("doGetAuthenticationInfo 将token装载成用户信息");

        BearerToken bearerToken = (BearerToken) authenticationToken;
        String bearerTokenString = bearerToken.getToken();

        JwtVo jwtVo = Jwts.recreateUserFromToken(bearerTokenString); // 只带着用户名、roles和userType

        SimpleAuthenticationInfo res = new SimpleAuthenticationInfo(jwtVo, bearerTokenString, this.getName());
        /*Constructor that takes in an account's identifying principal(s) and its corresponding credentials that verify the principals.*/
//        这个返回值是造Subject用的，返回值供createSubject使用
        return res;


    }

}
