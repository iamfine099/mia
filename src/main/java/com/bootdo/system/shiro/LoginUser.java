package com.bootdo.system.shiro;


import com.bootdo.system.domain.UserDO;

public class LoginUser {

	private long id;
	
	private String username ;
	
	private String name;
	
	private String loginType;
	
	private UserDO user; //后台登录用户


	
	

	
	

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LoginUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserDO getUser() {
		return user;
	}

	public void setUser(UserDO user) {
		this.user = user;
	}

	public LoginUser(long id, String username,String name, String loginType, UserDO
			user,Object busDo) {
		super();
		this.id = id;
		this.username = username;
		this.loginType = loginType;
		this.user = user;
		
		
		
		this.user = (UserDO)user;
		this.name = name;
	}

	
	
	
	
	
}
