package com.bootdo.system.shiro;

import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.CollectionUtils;

/**
 * Created by Lancelot on 2017/3/17.
 * 重写模块化用户验证器,根据登录界面传递的loginType参数,获取唯一匹配的realm
 */
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {
    //private final Logger log = LoggerFactory.getLogger(MyModularRealmAuthenticator.class);
	
	//private Map<String, Object> definedRealms;


    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) throws AuthenticationException {
        Realm uniqueRealm = getUniqueRealm(realms, token);
        if (uniqueRealm == null) {
            throw new UnsupportedTokenException("没有匹配类型的realm");
        }
        return uniqueRealm.getAuthenticationInfo(token);
    }

    /**
     * 判断realms是否匹配,并返回唯一的可用的realm,否则返回空
     *
     * @param realms realm集合
     * @param token  登陆信息
     * @return 返回唯一的可用的realm
     */
    private Realm getUniqueRealm(Collection<Realm> realms, AuthenticationToken token) {
        for (Realm realm : realms) {
            if (realm.supports(token)) {
                return realm;
            }
        }
        //log.error("一个可用的realm都没有找到......");
        System.out.println("一个可用的realm都没有找到......");
        return null;
    }
    
    /**
     * 判断登录类型执行操作
     */
//    @Override
//    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)throws AuthenticationException {
//        //this.assertRealmsConfigured();
//        Realm realm = null;
//        UsernamePasswordLoginTypeToken token = (UsernamePasswordLoginTypeToken) authenticationToken;
//        //判断是否是后台用户
//        if (token.getLoginType().equals("student")) {
//            realm = (Realm) this.definedRealms.get("StudentRealm");
//        }
//        else{
//            realm = (Realm) this.definedRealms.get("UserRealm");
//        }
//
//        return this.doSingleRealmAuthentication(realm, authenticationToken);
//    }
    
    /**
     * 判断realm是否为空
     */
//    @Override
//    protected void assertRealmsConfigured() throws IllegalStateException {
//        this.definedRealms = this.getDefinedRealms();
//        if (CollectionUtils.isEmpty(this.definedRealms)) {
//            throw new ShiroException("值传递错误!");
//        }
//    }
//
//    
//    public Map<String, Object> getDefinedRealms() {
//        return this.definedRealms;
//    }
//
//    public void setDefinedRealms(Map<String, Object> definedRealms) {
//        this.definedRealms = definedRealms;
//    }

}
