package com.bootdo.system.shiro;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.bootdo.common.config.ApplicationContextRegister;
import com.bootdo.common.config.Constant;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.dao.UserDao;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.MenuService;

public class CounsellorRealm extends AuthorizingRealm {
/*	@Autowired
	UserDao userMapper;
	@Autowired
	MenuService menuService;*/
	
//	@Override
//    public String getName() {
//        return "Student";
//    }
	
	/**
     * 支持的登陆类型
     */
    private String supportedLoginType = "counsellor";

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		Long userId = ShiroUtils.getUserId();
		MenuService menuService = ApplicationContextRegister.getBean(MenuService.class);
		Set<String> perms = menuService.listPerms(userId);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(perms);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		Map<String, Object> map = new HashMap<>(16);
		map.put("username", username);
		map.put("fType",new String[] {Constant.USER_TYPE_COUNSELLOR});
		String password = new String((char[]) token.getCredentials());

		
		
		
		UserDao userMapper = ApplicationContextRegister.getBean(UserDao.class);
		// 查询用户信息
		UserDO user = userMapper.list(map).get(0);

		// 账号不存在
		if (user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}

		// 密码错误
		if (!password.equals(user.getPassword())) {
			throw new IncorrectCredentialsException("账号或密码不正确");
		}

		// 账号锁定
		if (user.getStatus() == 0) {
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}
		LoginUser loginUser = new LoginUser(user.getUserId(),user.getUsername(),user.getName(),"admin",user,null);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(loginUser, password, getName());
		return info;
	}
	
	public boolean supports(AuthenticationToken token) {
		if (token instanceof UsernamePasswordLoginTypeToken) {
			UsernamePasswordLoginTypeToken usernamePasswordLoginTypeToken = (UsernamePasswordLoginTypeToken) token;
			if(usernamePasswordLoginTypeToken != null){
				String sLoginType = getSupportedLoginType();
				String uLoginType = usernamePasswordLoginTypeToken.getLoginType();
				//return getSupportedLoginType().equals(usernamePasswordLoginTypeToken.getLoginType());
				boolean b = false;
				if(sLoginType != null && uLoginType != null){
					b = sLoginType.equals(uLoginType);
				}
				return b;
			}
		}
		return false;
	}
	
	public String getSupportedLoginType() {
		return supportedLoginType;
	}

	/**
	 * spring注入
	 */
	public void setSupportedLoginType(String supportedLoginType) {
		this.supportedLoginType = supportedLoginType;
	}

}
