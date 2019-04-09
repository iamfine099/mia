package com.bootdo.system.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by Lancelot on 2017/3/17.
 * 重写{@link #UsernamePasswordToken},增加{@link #loginType}属性,该属性是在登陆界面form表单中传递过来的,定义了用户使用的登陆类型
 */
public class UsernamePasswordLoginTypeToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 7134536615448037793L;
    /**
    *登陆类型
    */
    private String loginType;

    public UsernamePasswordLoginTypeToken(String username, String password, boolean rememberMe, String host, String loginType) {
        super(username, password, rememberMe, host);
        this.loginType = loginType;
    }
    
    public UsernamePasswordLoginTypeToken(String username, String password, String loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
