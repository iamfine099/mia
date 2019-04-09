package com.bootdo.system.filter;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;

import com.bootdo.system.shiro.UsernamePasswordLoginTypeToken;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    public static final String DEFAULT_LOGIN_TYPE_PARAM = "loginType";
    private boolean kickOutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private int maxSession = 1; //同一个帐号最大会话数 默认1
    @Value("ceshi")
    private String kickOutSessionCacheName;
    private Cache<String, Deque<Serializable>> cache;
    private ReentrantLock reentrantLock = new ReentrantLock();
    private String loginTypeParamName = DEFAULT_LOGIN_TYPE_PARAM;

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //如果其他过滤器已经,验证失败了,则禁止登陆,不再进行身份验证
        if (request.getAttribute(getFailureKeyAttribute()) != null) {
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }

    /**
     * 重写登陆成功后的处理方法,使其跳转到指定的页面,这里是successUrl
     *
     * @param token    令牌
     * @param subject  用户信息
     * @param request  请求
     * @param response 响应
     * @return true 继续过滤,false 跳过之后的过滤;
     * @throws Exception 异常
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        Session session = subject.getSession();
        String username = (String) subject.getPrincipal();
        Serializable sessionId = session.getId();
        reentrantLock.lock();
        Deque<Serializable> deque = cache.get(username);
        if (deque == null) {
            deque = new LinkedList<>();
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId)) {
            deque.push(sessionId);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
            Serializable kickOutSessionId;
            if (kickOutAfter) { //如果踢出后者
                kickOutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickOutSessionId = deque.removeLast();
            }
            /*try {
               Session kickOutSession = sessionManager.getSession(new DefaultSessionKey(kickOutSessionId));
                if (kickOutSession != null) {
                    //设置会话的 kickOut 属性表示踢出了
                    kickOutSession.setAttribute("kickOut", true);
                }
            } catch (Exception e) {//ignore exception
            }*/
        }

        cache.put(username, deque);
        reentrantLock.unlock();
        WebUtils.getAndClearSavedRequest(request);
        
        WebUtils.redirectToSavedRequest(request, response,"/index");
        return true;
    }

    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae);
    }

    /**
     * 重写该方法,为了将loginType参数保存到token中
     *
     * @param request  请求
     * @param response 响应
     * @return
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String loginType = getLoginType(request);
        return createToken(username, password, request, response);
    }

    private AuthenticationToken createToken(String username, String password, ServletRequest request, ServletResponse response, String loginType) {
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        return createToken(username, password, rememberMe, host, loginType);
    }

    private AuthenticationToken createToken(String username, String password, boolean rememberMe, String host, String loginType) {
        System.out.println("==pcz==createToken");
    	
    	return new UsernamePasswordLoginTypeToken(username, password, rememberMe, host, loginType);
    }
    
    private AuthenticationToken createToken(String username, String password, String loginType) {
        System.out.println("==pcz2==createToken");
    	
    	return new UsernamePasswordLoginTypeToken(username, password, loginType);
    }

    private String getLoginType(ServletRequest request) {
        return WebUtils.getCleanParam(request, getLoginTypeParamName());
    }


	public String getLoginTypeParamName() {
		return loginTypeParamName;
	}


	public void setLoginTypeParamName(String loginTypeParamName) {
		this.loginTypeParamName = loginTypeParamName;
	}

}
