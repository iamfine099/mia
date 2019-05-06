package com.bootdo.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.common.annotation.Log;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.utils.MD5Utils;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.RandomValidateCodeUtils;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.system.domain.MenuDO;
import com.bootdo.system.service.MenuService;
import com.bootdo.system.service.UserService;
import com.bootdo.system.shiro.UsernamePasswordLoginTypeToken;

@Controller
public class LoginController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MenuService menuService;

	@Autowired
	UserService userService;
	@GetMapping({ "/", "" })
	String welcome(Model model) {
		return "forward:/front/cms";
	}

	@Log("请求访问主页")
	@GetMapping({ "/index" })
	String index(Model model) {
		
		//获取到登录类型
		 String fType = getUser().getUser().getfType();
		 if("M".equals(fType)){
			 //普通会员个人中心
			 return "redirect:/front/cms/personalInfo";
		 }else if("E".equals(fType)){
			 //专家个人中心
			 return "redirect:/front/cms/expertInfo";
		 }else{
			 String backUrl = "index_v1";
			 List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
			 model.addAttribute("menus", menus);
			 model.addAttribute("name", getUser().getName());
			 model.addAttribute("picUrl","/img/logo.png");
			 model.addAttribute("username", getUser().getUsername());
			 return backUrl;
		 }
	}
	/**
	 * 微信登录首地址
	 * @param model
	 * @return
	 */
	@Log("微信请求访问主页")
	@GetMapping({ "/wxIndex" })
	String wxIndex(Model model) {
		
		//获取到登录类型
//		 String loginType = getUser().getLoginType();
		 String backUrl = "index_v1";
//		 if("student".equals(loginType)) {//学生端
//			 
//				model.addAttribute("aTagert", "userMessageList");
//				model.addAttribute("divTagert", "menuList1");
//				return "redirect:/common/userMessage/front/list";
//			
//		 }else if("employer".equals(loginType)) {//用人单位
//			 model.addAttribute("aTagert", "userMessageList");
//			 model.addAttribute("divTagert", "menuList1");
//			 return "redirect:/common/userMessage/front/list";
//			 
//		 }else{//后台管理端
//			 
//				List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
//				model.addAttribute("menus", menus);
//				model.addAttribute("name", getUser().getName());
//				model.addAttribute("picUrl","/img/logo.png");
//				/*FileDO fileDO = fileService.get(getUser().getPicId());
//				if(fileDO!=null&&fileDO.getUrl()!=null){
//					if(fileService.isExist(fileDO.getUrl())){
//						model.addAttribute("picUrl",fileDO.getUrl());
//					}else {
//						model.addAttribute("picUrl","/img/photo_s.jpg");
//					}
//				}else {
//					model.addAttribute("picUrl","/img/photo_s.jpg");
//				}*/
//				model.addAttribute("username", getUser().getUsername());
//			 
//		 }
		 return backUrl;
	
		
	
	}

	@GetMapping("/login")
	String login() {
		return "login";
	}
	/**
	 * 绑定openid
	 * @return
	 */
	@GetMapping("/front/band")
	String band() {
		return "front/band";
	}
	@Log("绑定")
	@PostMapping("/front/band")
	@ResponseBody
	R band(String username, String password, String loginType) {
		password = MD5Utils.encrypt(username,password);
		//UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		
		UsernamePasswordLoginTypeToken loginToken = new UsernamePasswordLoginTypeToken(username, password, loginType);
		
		
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(loginToken);
			
			//绑定微信openid
			userService.bandOpenid();
			
			return R.ok();
		} catch (Exception  e) {
			return R.error(e.getMessage());
		}
		
	}
	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password,String yz, HttpSession session) {//, String loginType
		R r = RandomValidateCodeUtils.checkVerify(yz, session);
		  if("500".equals(r.get("code").toString())){
			  return r;
		  }
		String loginType = "admin";
		username = username.trim();

		password = MD5Utils.encrypt(username,password);
		//UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		
		UsernamePasswordLoginTypeToken loginToken = new UsernamePasswordLoginTypeToken(username, password, loginType);
		
		
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(loginToken);
			return R.ok();
		} catch ( IncorrectCredentialsException  e) {
			return R.error("用户或密码错误");
		} catch (LockedAccountException e) {
			return R.error("账号被锁定，请联系管理员");
		}catch (AuthenticationException e) {
			return R.error("用户或密码错误");
		}
	}

	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main() {
		return "main";
	}

//  获取验证码
  @RequestMapping("/code/image")
  @ResponseBody
  public String getSysManageLoginCode(HttpServletResponse response, HttpServletRequest request) {
      response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
      response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
      response.setHeader("Cache-Control", "no-cache");
      response.setHeader("Set-Cookie", "name=value; HttpOnly");//设置HttpOnly属性,防止Xss攻击
      response.setDateHeader("Expire", 0);
      RandomValidateCodeUtils randomValidateCode = new RandomValidateCodeUtils();
      try {
          randomValidateCode.getRandcode(request, response);// 输出图片方法
      } catch (Exception e) {
          e.printStackTrace();
      }
      return "";
  }
}
