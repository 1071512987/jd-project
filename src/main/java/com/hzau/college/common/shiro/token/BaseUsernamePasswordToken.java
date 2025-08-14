package com.hzau.college.common.shiro.token;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义Token（需要继承指定类以符合Shiro规定）
 * 在Subject.login()方法中传入，增加了一点属性以满足前后端判断的业务需要
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseUsernamePasswordToken extends UsernamePasswordToken {

    /*
        父类包含username password
     */

    private String userType;

    public BaseUsernamePasswordToken(String username, String password, String userType){
        super(username, password);
        this.userType = userType;
    }

}

