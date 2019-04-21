package com.bootdo.cms.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bootdo.common.domain.AchievementDO;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.utils.DictUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bootdo.cms.domain.CmsLinkDO;
import com.bootdo.cms.service.CmsLinkService;
import com.bootdo.common.annotation.Log;
import com.bootdo.common.config.EmailConfig;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.ArticleDO;
import com.bootdo.common.domain.ArticleDataDO;
import com.bootdo.common.domain.CategoryDO;
import com.bootdo.common.domain.FileDO;
import com.bootdo.common.domain.UserMessageDO;
import com.bootdo.common.service.ArticleDataService;
import com.bootdo.common.service.ArticleService;
import com.bootdo.common.service.CategoryService;
import com.bootdo.common.service.FileService;
import com.bootdo.common.service.UserMessageService;
import com.bootdo.common.utils.MD5Utils;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.RandomValidateCodeUtils;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.mia.domain.CommentDO;
import com.bootdo.mia.domain.EmailCodeDO;
import com.bootdo.mia.domain.ExperevaluateDO;
import com.bootdo.mia.domain.ExpertDO;
import com.bootdo.mia.domain.LeaveMessageDO;
import com.bootdo.mia.domain.LeaveMessageTypeDO;
import com.bootdo.mia.domain.MemberDO;
import com.bootdo.mia.service.CommentService;
import com.bootdo.mia.service.EmailCodeService;
import com.bootdo.mia.service.ExperevaluateService;
import com.bootdo.mia.service.ExpertService;
import com.bootdo.mia.service.LeaveMessageService;
import com.bootdo.mia.service.LeaveMessageTypeService;
import com.bootdo.mia.service.MemberService;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import com.bootdo.system.shiro.LoginUser;
import com.bootdo.system.shiro.UsernamePasswordLoginTypeToken;




/**
 * 首页接口
 */
@RequestMapping("/front/cms")
@Controller
public class FrontCmsController extends BaseController {

	@Autowired
    private ArticleService articleService;
	@Autowired
    private ArticleDataService articleDataService;
	@Autowired
    private CommentService commentService;
	@Autowired
    private ExpertService expertService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private CmsLinkService cmsLinkService;
	@Autowired
	private UserService userService;
	@Autowired
	private FileService sysFileService;
	@Autowired
	private LeaveMessageService leaveMessageService;
	@Autowired
	private LeaveMessageTypeService leaveMessageTypeService;
	@Autowired
	private UserMessageService usermessageService;
	@Autowired
	private ExperevaluateService experevaluateService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private EmailConfig emailConfig;
	@Autowired
	private EmailCodeService emailCodeService;
	@Autowired
	private CategoryService categoryService;




//	@RequestMapping()
//		
//		String frontCmsController(){
//		    return "front/cms/index";
//		}

	/***
	 * 跳转主页
	 * @param model
	 * @return
	 */
	@GetMapping()
	String frontCmsController(String achievement, String specialty, Model model) {
		LoginUser loginUser = ShiroUtils.getUser();

		//如果当前登陆人是管理员，则退出登录
		if(loginUser != null && "A".equals(loginUser.getUser().getfType())){
			ShiroUtils.logout();
			loginUser = null;
		}
		UserDO user=new UserDO();
		user.setfType("U");
		user.setUsername("用户未登录");
		if(loginUser!=null){
			model.addAttribute("user", ShiroUtils.getUser().getUser());
		}else{
			model.addAttribute("user", user);
		}
		
		//获取新闻动态
		Map<String, Object> param = new HashMap<>();
		param.put("categoryId", 15);
		param.put("delFlag", "1");
		//param.put("status", "1");
		param.put("offset", 0);
		param.put("limit", 6);
		List<ArticleDO> newarticleList = articleService.list(param);
		model.addAttribute("newarticleList", newarticleList);
		
		// 获取医学动态
		param = new HashMap<>();
		param.put("categoryId", 16);
		param.put("delFlag", "1");
		// param.put("status", "1");
		param.put("offset", 0);
		param.put("limit", 6);
		List<ArticleDO> intelligenceList = articleService.list(param);
		model.addAttribute("intelligenceList", intelligenceList);
		
		/*//获取行业资讯
	    param = new HashMap<>();
	    param.put("categoryId", 2);
		param.put("offset", 0);
		param.put("limit", 6);
		List<ArticleDO> jobarticleList = articleService.list(param);
		model.addAttribute("jobarticleList", jobarticleList);*/
		//会员单位
		param = new HashMap<>();
	    param.put("categoryId", 13);
		param.put("offset", 0);
		param.put("limit", 8);
		List<ArticleDO> unitList = articleService.list(param);
		model.addAttribute("unitList", unitList);
		//下载专区
		param = new HashMap<>();
	    param.put("categoryId", 14);
		param.put("offset", 0);
		param.put("limit", 6);
		List<ArticleDO> downList = articleService.list(param);
		model.addAttribute("downList", downList);
		
		//获取通知通告
		param = new HashMap<>();
		param.put("categoryId", 1);
		param.put("delFlag", "1");
		//param.put("status", "1");
		param.put("offset", 0);
		param.put("limit", 8);
		List<ArticleDO> notifyarticleList = articleService.list(param);
		model.addAttribute("notifyarticleList", notifyarticleList);

		//获取推荐文章(成果展示)
		param = new HashMap<>();
		param.put("categoryId", 4);
		//param.put("delFlag", "1");
		param.put("status", "1");
		param.put("isRecommend", "1");
		param.put("offset", 0);
		param.put("limit", 8);
		if(StringUtils.isNotBlank(achievement)) {
			param.put("type", achievement);
		}
        List<ArticleDO> recommendarticleList = articleService.list(param);
		List<ArticleDO> newestAchievementList = recommendarticleList.stream().filter(a -> a.getClassify() != null && a.getClassify().contains("1")).collect(Collectors.toList());
		List<ArticleDO> recommendAchievementList = recommendarticleList.stream().filter(a -> a.getClassify() != null && a.getClassify().contains("2")).collect(Collectors.toList());
		List<ArticleDO> deployAchievementList = recommendarticleList.stream().filter(a -> a.getClassify() != null && a.getClassify().contains("3")).collect(Collectors.toList());
		model.addAttribute("recommendarticleList", recommendarticleList);
		model.addAttribute("newestAchievementList", newestAchievementList);
		model.addAttribute("recommendAchievementList", recommendAchievementList);
		model.addAttribute("deployAchievementList", deployAchievementList);

		//获取精彩评论
		param = new HashMap<>();
		param.put("offset", 0);
		param.put("limit", 5);
		param.put("isRecommend","1");
		param.put("status", "1");
        List<CommentDO> recommendcommentList = commentService.recommendlist(param);
		model.addAttribute("recommendcommentList", recommendcommentList);

		//获取专家展示
	    param = new HashMap<>();
		param.put("offset", 0);
		param.put("limit", 6);
		param.put("status", "0");
		if(StringUtils.isNotBlank(specialty)) {
			param.put("specialty", specialty);
		}
		List<ExpertDO> expertList = expertService.list(param);
		model.addAttribute("expertList", expertList);
		
		//获取图片新闻
		param = new HashMap<>();
		param.put("offset", 0);
		param.put("limit", 5);
		param.put("categoryId", 3);
		param.put("type", 1);
		List<ArticleDO> photoarticleList = articleService.photolist(param);
		model.addAttribute("photoarticleList", photoarticleList);

		//中部广告
		param = new HashMap<>();
		param.put("offset", 0);
		param.put("limit", 1);
		param.put("categoryId", 3);
		param.put("type", 2);
		photoarticleList = articleService.photolist(param);
		ArticleDO advert = new ArticleDO();
		if(photoarticleList != null && photoarticleList.size() > 0){
			advert = photoarticleList.get(0);
		}
		model.addAttribute("advert", advert);

		// 政策法规
		param = new HashMap<>();
		String policiesCategoryId = DictUtils.getDictValue("policies", "home_page_item", "");
		param.put("categoryId", policiesCategoryId);
		param.put("delFlag", "1");
		param.put("offset", 0);
		param.put("limit", 6);
		List<ArticleDO> policiesList = articleService.list(param);
		model.addAttribute("policiesList", policiesList);
		model.addAttribute("policiesCategoryId", policiesCategoryId);

		// 视频模块
		param = new HashMap<>();
		param.put("categoryType", "videoList");
		param.put("delFlag", "1");
		param.put("offset", 0);
		param.put("limit", 3);
		List<ArticleDO> videoList = articleService.list(param);
		model.addAttribute("videoList", videoList);
		//model.addAttribute("videoCategoryId", videoCategoryId);

		//获取友情链接
		param = new HashMap<>();
		param.put("offset", 0);
		param.put("limit", 10);
		param.put("fStatus",1);
		param.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(param);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		param.put("fRel","2");
		cmsLinkList = cmsLinkService.list(param);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		param.put("fRel","3");
		cmsLinkList = cmsLinkService.list(param);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		param.put("fRel","4");
		cmsLinkList = cmsLinkService.list(param);
		model.addAttribute("cmsLinkList4", cmsLinkList);

		// 成果展示类型
		List<DictDO> achievementTypes = DictUtils.getDictList("business_type");
		model.addAttribute("achievementTypes", achievementTypes);
		// 专家展示类型
		List<DictDO> specialtyTypes = DictUtils.getDictList("specialty");
		model.addAttribute("specialtyTypes", specialtyTypes);

		model.addAttribute("achievement", achievement);
		model.addAttribute("specialty", specialty);
		return "front/cms/index";
	}

	//专家列表展示
	@RequestMapping("/open/expertlist")
	public String expertList(@RequestParam Map<String, Object> params, String name, Model model) {

	    params.put("status","0");
		if(StringUtils.isNotBlank(name)) {
		    params.put("expertName", name);
        }
	    Query query = new Query(params);
		List<ExpertDO> expertList = expertService.list(query);
		int total = expertService.count(query);
		PageUtils pageUtils = new PageUtils(expertList, total, query);
		model.addAttribute("pageUtils", pageUtils);
		
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		return "front/cms/expertList";
	}
	
	//专家详情
	@GetMapping("/open/expert/detail")
	public String expertDetail(@RequestParam Integer expertId, Model model) {
		ExpertDO expert = expertService.get(expertId);
		model.addAttribute("expert", expert);
		FileDO fileObj=new FileDO();
		if(StringUtils.isNotEmpty(expert.getHeadImg())){
			fileObj=sysFileService.get(Long.parseLong(expert.getHeadImg()));	
		}
		model.addAttribute("fileObj", fileObj);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		return "front/cms/expertExhibition";
	}
	
	//通知公告列表展示
	@RequestMapping("/open/notifyarticlelist")
	public String notifyarticleList(@RequestParam Map<String, Object> params,Model model) {
		params.put("categoryId", 1);
		params.put("delFlag", "1");
		//params.put("status", "1");
		Query query = new Query(params);
		List<ArticleDO> notifyarticleList = articleService.list(query);
		int total = articleService.count(query);
		PageUtils pageUtils = new PageUtils(notifyarticleList, total, query);
		model.addAttribute("pageUtils", pageUtils);
		
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		
		return "front/cms/notifyArticle";
	}
		
	
		
	//新闻动态列表展示
	@RequestMapping("/open/newsdylist")
	public String newsdyList(@RequestParam Map<String, Object> params,Model model) {
		//params.put("categoryId", 2);
		params.put("delFlag", "1");
		//params.put("status", "1");
		Query query = new Query(params);
		List<ArticleDO> newsdyList = articleService.list(query);
		int total = articleService.count(query);
		PageUtils pageUtils = new PageUtils(newsdyList, total, query);
		model.addAttribute("pageUtils", pageUtils);
		
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		
		return "front/cms/newsDy";
	}
	
	
	
	//推荐文章列表展示
	@RequestMapping("/open/recommendarticlelist")
	public String recommendarticleList(@RequestParam Map<String, Object> params,Model model) {
		LoginUser loginUser = ShiroUtils.getUser();
		params.put("isRecommend", "1");
		params.put("status", "1");
		if(!params.containsKey("title")){
			params.put("title", "");
		}
		if(!params.containsKey("datesearch")){
			params.put("datesearch", "");
		}
		if(!params.containsKey("type")){
			params.put("type", "");
		}
		//params.put("categoryId", 4);
		Query query = new Query(params);
		List<ArticleDO> recommendarticleList = articleService.list(query);
		int total = articleService.count(query);
		PageUtils pageUtils = new PageUtils(recommendarticleList, total, query);
		model.addAttribute("pageUtils", pageUtils);
		
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		
		if(loginUser!=null)
		{
			model.addAttribute("user", ShiroUtils.getUser().getUser());
			return "front/cms/recommendArticle";
		}else{
			return "front/cms/recommendArticlePersonal";
		}
	}


	//成果展示列表展示
	@RequestMapping("/open/achievementList")
	public String achievementList(@RequestParam Map<String, Object> params, String title, Model model) {

		LoginUser loginUser = ShiroUtils.getUser();
		params.put("isRecommend", "1");
		params.put("status", "1");
		if(!params.containsKey("title")){
			params.put("title", "");
		}
		if(!params.containsKey("datesearch")){
			params.put("datesearch", "");
		}
		if(!params.containsKey("type")){
			params.put("type", "");
		}
		if(StringUtils.isNotBlank(title)) {
		    params.put("title", title);
        }
		//params.put("categoryId", 4);
		Query query = new Query(params);
		List<AchievementDO> achievementList = articleService.listAchievement(query);
		int total = articleService.count(query);
		PageUtils pageUtils = new PageUtils(achievementList, total, query);

		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		model.addAttribute("cmsLinkList1", cmsLinkService.list(params));
		params.put("fRel","2");
		model.addAttribute("cmsLinkList2", cmsLinkService.list(params));
		params.put("fRel","3");
		model.addAttribute("cmsLinkList3", cmsLinkService.list(params));
		params.put("fRel","4");
		model.addAttribute("cmsLinkList4", cmsLinkService.list(params));
		model.addAttribute("pageUtils", pageUtils);

		if(loginUser != null) {
			model.addAttribute("user", ShiroUtils.getUser().getUser());
		}
		model.addAttribute("pageUtils", pageUtils);
		return "front/cms/achievementList";
	}

	//首页搜索文章
	@GetMapping("/search/{title}")
	String search(@PathVariable("title") String title,Model model){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		model.addAttribute("title", title);
		params.put("isRecommend", "1");
		params.put("status", "1");
		if(!params.containsKey("title")){
			params.put("title", "");
		}
		if(!params.containsKey("datesearch")){
			params.put("datesearch", "");
		}
		if(!params.containsKey("type")){
			params.put("type", "");
		}
		//params.put("categoryId", 4);
		Query query = new Query(params);
		List<ArticleDO> recommendarticleList = articleService.list(query);
		int total = articleService.count(query);
		PageUtils pageUtils = new PageUtils(recommendarticleList, total, query);
		model.addAttribute("pageUtils", pageUtils);
		
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		return "front/cms/recommendArticlePersonal";
	}
	
	
	//文章详情
	@GetMapping("/open/article/detail")
	public String articleDetail(@RequestParam Integer id, Model model) {
		LoginUser loginUser = ShiroUtils.getUser();
		if(loginUser!=null){
			model.addAttribute("user", ShiroUtils.getUser().getUser());
		}else{
			UserDO user=new UserDO();
			user.setfType("U");
			model.addAttribute("user",user);
		}
		ArticleDO article = articleService.get(id);
		if(article.getHits()!=null){
			article.setHits(article.getHits() + 1);
		}else{
			article.setHits(1);
		}
		articleService.update(article);
		model.addAttribute("article", article);
		List<FileDO> fileList = new ArrayList<FileDO>();
		if(StringUtils.isNotEmpty(article.getImage()) && article.getCategoryId() == 4){
			String[] imageArray = article.getImage().split(",");
			for (int i = 0; i < imageArray.length; i++) {
				FileDO fileObj = sysFileService.get(Long.parseLong(imageArray[i]));
				if(fileObj != null){
					fileList.add(fileObj);
				}
			}
		}else if(StringUtils.isNotEmpty(article.getAttachment()) && article.getCategoryId() != 4){
			String[] imageArray = article.getAttachment().split(",");
			for (int i = 0; i < imageArray.length; i++) {
				FileDO fileObj = sysFileService.get(Long.parseLong(imageArray[i]));
				if(fileObj != null){
					fileList.add(fileObj);
				}
			}
		}
		model.addAttribute("fileList", fileList);
		/*FileDO fileObj=new FileDO();
		if(StringUtils.isNotEmpty(article.getImage())){
			fileObj=sysFileService.get(Long.parseLong(article.getImage()));	
		}
		model.addAttribute("fileObj", fileObj);*/
		
		//上一篇
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", article.getId());
		map.put("categoryId", article.getCategoryId());
		map.put("delFlag", article.getDelFlag());
		map.put("status", article.getStatus());
		map.put("type", article.getType());
		map.put("isRecommend", article.getIsRecommend());
		ArticleDO prevArticle = articleService.prevArticle(map);
		model.addAttribute("prevArticle", prevArticle);
		
		//下一篇
		ArticleDO nextArticle = articleService.nextArticle(map);
		model.addAttribute("nextArticle", nextArticle);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		
		return "front/cms/ArticleDetail";
	}
		
	//精彩评论列表展示
	@RequestMapping("/open/goodCommentlist")
	public String goodCommentList(@RequestParam Map<String, Object> params,Model model) {
		params.put("isRecommend", "1");
		params.put("status", "1");
		params.put("limit", 5);
		Query query = new Query(params);
		List<CommentDO> commentList = commentService.recommendlist(query);
		int total = commentService.count(query);
		PageUtils pageUtils = new PageUtils(commentList, total, query);
		model.addAttribute("pageUtils", pageUtils);
		
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		
		return "front/cms/goodComment";
	}
	
	//忘记密码
	@RequestMapping("/forgetPwd")
	public String forgetPwd(Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		return "front/article/forgetPwd";
	}
	
	@RequestMapping("/forgetPwdPost")
	@ResponseBody
	public R forgetPwdPost(@RequestParam Map<String, Object> params) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("username", params.get("phone").toString());
		List<UserDO> userlist = userService.list(map);
		if(userlist != null && userlist.size() > 0){
			UserDO user = userlist.get(0);
			//校验验证码
			map = new HashMap<String, Object>();
			map.put("ecEmail", params.get("email").toString());
			map.put("ecCode", params.get("code").toString());
			if(emailCodeService.checkVerify(map) > 0){
				//修改密码
				user.setPassword(MD5Utils.encrypt(user.getUsername().trim(),params.get("pwd").toString()));
				if(userService.update(user) > 0){
					return R.ok();
				}else{
					return R.error("更改密码失败");
				}
			}else{
				return R.error("验证码不正确");
			}
		}else{
			return R.error("手机号暂未注册");
		}
	}
	
	@RequestMapping("/register")
	public String frontRegister(Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		return "front/article/register";
	}
	
	@RequestMapping("/registerPost")
	@ResponseBody
	public R frontRegisterPost(@RequestParam Map<String, Object> params, HttpSession session) {
		return memberService.registerPost(params,session);
	}
	
	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxFrontLogin(String username, String password, String yz, HttpSession session) {//, String loginType
	  
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
			return R.error("等待后台的审核");
		}catch (AuthenticationException e) {
			return R.error("用户或密码错误");
		}
	}
	/***
	 * 退出
	 * @return
	 */
	@GetMapping("/out")
	String out() {
		ShiroUtils.logout();
		return "redirect:/front/cms";
	}
	
	/**
	 * 个人信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/personalInfo")
	public String frontPersonalInfo(Model model) {
		LoginUser loginUser = ShiroUtils.getUser();
		model.addAttribute("user", ShiroUtils.getUser().getUser());
		MemberDO member = new MemberDO();
		FileDO fileObj=new FileDO();
		
		if("M".equals(loginUser.getUser().getfType())){
			member = memberService.get(Integer.parseInt(loginUser.getUser().getBusId()));
		}
		
		if(StringUtils.isNotEmpty(member.getHeadImg())){
			fileObj=sysFileService.get(Long.parseLong(member.getHeadImg()));	
		}
		model.addAttribute("member", member);
		model.addAttribute("fileObj", fileObj);
		Map<String, Object> params = new HashMap<>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		return "front/cms/personalInfo";
	}
	/**
	 * 个人信息修改
	 * @param model
	 * @return
	 */
	@RequestMapping("/modifyPersonalInfo")
	public String editStatus( Model model){	
		//修改用户表信息
		LoginUser loginUser = ShiroUtils.getUser();
		model.addAttribute("user", ShiroUtils.getUser().getUser());
		MemberDO member = memberService.get(Integer.parseInt(loginUser.getUser().getBusId()));
		model.addAttribute("member", member);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		return "front/cms/modifyPersonalInfo";
		/*return R.ok();*/
	}
	/*public String ModifyPersonalInfo(MemberDO member) {
		member.getCreateDate();
		member.setLastTime(new Date());
		memberService.update(member); 
		
	}*/
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/modifyPersonalInfo/update")
	public R update(MemberDO member){	
					if(memberService.update(member) > 0){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("busId", member.getMemId());
						map.put("fType", new String[]{"M"});
						List<UserDO> userlist = userService.list(map);
						for (UserDO userDO : userlist) {
							if("0".equals(member.getStatus())){
								userDO.setStatus(1);
							}else{
								userDO.setStatus(0);
							}
							userService.update(userDO);
						}
					}
		return R.ok();
	}
	/**
	 * 修改密码
	 * @param model
	 * @return
	 */
	@RequestMapping("/modifyPwd")
	public String ModifyPwd(Model model) {
		model.addAttribute("user", ShiroUtils.getUser().getUser());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		return "front/cms/modifyPwd";
	}

	
	
	/**
	 * 修改
	 * @param old_Pwd
	 * @param new_Pwd
	 * @return
	 */
	@RequestMapping("/modifyPwd/update")
	@ResponseBody
	public R modifyPwd(String old_Pwd, String new_Pwd){
		if(!StringUtils.isNotEmpty(old_Pwd)){
			return R.error("请输入原密码");
		}else if(!StringUtils.isNotEmpty(new_Pwd)){
			return R.error("请输入新密码");
		}else{
			UserDO userdo = ShiroUtils.getUser().getUser();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("busId", userdo.getBusId());
			map.put("fType", userdo.getfType());
			if(userdo != null){
				if(userdo.getPassword().equals(MD5Utils.encrypt(userdo.getUsername().trim(),old_Pwd))){
					userdo.setPassword(MD5Utils.encrypt(userdo.getUsername().trim(),new_Pwd));
					userService.update(userdo);
					return R.ok("密码修改成功");
				}else{
					return R.error("旧密码不正确");
				}
			}else{
				return R.error("用户信息不存在");
			}
		}
	}
	/**
	 * 我要发布
	 * @param model
	 * @return
	 */	
	@RequestMapping("/release")
	public String Release(Model model) {		
		model.addAttribute("user", ShiroUtils.getUser().getUser());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		return "front/article/release";
	}
	@RequestMapping("/releasePost")
	@ResponseBody
	public R ReleasePost(AchievementDO article, MultipartFile[] file, HttpServletRequest request) {

		//默认值
		LoginUser loginUser = ShiroUtils.getUser();
		article.setCategoryId(4);
		article.setCreateBy(Integer.parseInt(loginUser.getUser().getUserId().toString()));
		article.setCreateTime(new Date());
		article.setStatus("0");
		article.setScore(new BigDecimal(0));
		article.setRecommendNum(0);
		article.setPublishTime(article.getCreateTime());
		article.setIsPublish("1");
		article.setIsRecommend("0");
		article.setIsTop("0");
		article.setHits(0);
		article.setLikeNum(0);
		String image = "";
		if(file !=null && file.length > 0) {
			for(MultipartFile f : file){
				FileDO fileDo = this.uploadFile(f, request);
				if(null != fileDo) {
					image += fileDo.getId() + ",";
				}
			}
		}
		if(StringUtils.isNotEmpty(image)){
			article.setImage(image.substring(0,image.length()-1));
		}
		if(articleService.saveAchievement(article) > 0){

			ArticleDataDO articleData = new ArticleDataDO();
			articleData.setId(article.getId());
			articleData.setContent(article.getContent());
			return R.ok();
		}
		return R.error();
	}
	/***
	 * 草稿箱暂存
	 * @param article
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping("/articleStorage")
	@ResponseBody
	public R articleStorage(ArticleDO article,MultipartFile[] file, HttpServletRequest request) {
		
		//默认值
		LoginUser loginUser = ShiroUtils.getUser();
		article.setCategoryId(4);
		article.setCreateBy(Integer.parseInt(loginUser.getUser().getUserId().toString()));
		article.setCreateTime(new Date());
		article.setStatus("0");
		article.setScore(new BigDecimal(0));
		article.setRecommendNum(0);
		article.setPublishTime(article.getCreateTime());
		article.setIsPublish("0");
		article.setIsRecommend("0");
		article.setIsTop("0");
		article.setHits(0);
		article.setLikeNum(0);
		String image = "";
		if(file !=null && file.length > 0) {
			for(MultipartFile f : file){
				FileDO fileDo = this.uploadFile(f, request);
				if(null != fileDo) {
					image += fileDo.getId() + ",";
				}
			}
		}
		if(StringUtils.isNotEmpty(image)){
			article.setImage(image.substring(0,image.length()-1));
		}
		if(articleService.save(article) > 0){
			ArticleDataDO articleData = new ArticleDataDO();
			articleData.setId(article.getId());
			articleData.setContent(article.getContent());
			return R.ok();
		}
		
		return R.error();
	}
	//草稿箱暂存修改
	@RequestMapping("/articleStoragEdit")
	@ResponseBody
	public R articleStorageEdit(ArticleDO article, String fileId, String isDel, MultipartFile[] file, HttpServletRequest request) {
		//附件
		String image = "";
		if (StringUtils.isNotEmpty(fileId) && StringUtils.isNotEmpty(isDel)) {
			String[] fileIdArray = fileId.split(",");
			String[] isDelArray = isDel.split(",");
			for (int i = 0; i < isDelArray.length; i++) {
				if("0".equals(isDelArray[i])){
					image += fileIdArray[i]+",";
				}else{
					this.deleteFile(Long.parseLong(fileIdArray[i]));
				}
			}
		}
		if(file !=null && file.length > 0) {
			for(MultipartFile f : file){
				FileDO fileDo = this.uploadFile(f, request);
				if(null != fileDo) {
					image += fileDo.getId() + ",";
				}
			}
		}
		if(StringUtils.isNotEmpty(image)){
			article.setImage(image.substring(0,image.length()-1));
		}
		
		articleService.update(article);
		ArticleDataDO articleData = new ArticleDataDO();
		articleData.setId(article.getId());
		articleData.setContent(article.getContent());
		articleDataService.update(articleData);
		return R.ok();
	}
	
	//草稿箱提交
	@RequestMapping("/releasePostEdit")
	@ResponseBody
	public R releasePostEdit(ArticleDO article, String fileId, String isDel, MultipartFile[] file, HttpServletRequest request) {
		// 附件
		String image = "";
		if (StringUtils.isNotEmpty(fileId) && StringUtils.isNotEmpty(isDel)) {
			String[] fileIdArray = fileId.split(",");
			String[] isDelArray = isDel.split(",");
			for (int i = 0; i < isDelArray.length; i++) {
				if ("0".equals(isDelArray[i])) {
					image += fileIdArray[i] + ",";
				} else {
					this.deleteFile(Long.parseLong(fileIdArray[i]));
				}
			}
		}
		if (file != null && file.length > 0) {
			for (MultipartFile f : file) {
				FileDO fileDo = this.uploadFile(f, request);
				if (null != fileDo) {
					image += fileDo.getId() + ",";
				}
			}
		}
		if (StringUtils.isNotEmpty(image)) {
			article.setImage(image.substring(0, image.length() - 1));
		}
		article.setIsPublish("1");
		articleService.update(article);
		ArticleDataDO articleData = new ArticleDataDO();
		articleData.setId(article.getId());
		articleData.setContent(article.getContent());
		articleDataService.update(articleData);
		return R.ok();
	}
	
	/**
	 * 草稿箱
	 * @param params
	 * @return
	 */
	@RequestMapping("/drafts")
	public String Drafts(@RequestParam Map<String, Object> params,Model model) {
		model.addAttribute("user", ShiroUtils.getUser().getUser());
		if(!params.containsKey("title")){
			params.put("title", "");
		}
		if(!params.containsKey("createdatesearch")){
			params.put("createdatesearch", "");
		}
		if(!params.containsKey("type")){
			params.put("type", "");
		}
		LoginUser loginUser = ShiroUtils.getUser();
		params.put("categoryId", 4);
		params.put("isPublish", "0");
		params.put("createBy", Integer.parseInt(loginUser.getUser().getUserId().toString()));
		Query query = new Query(params);
		List<ArticleDO> draftsList = articleService.draftslist(query);
		int total = articleService.count(query);
		PageUtils pageUtils = new PageUtils(draftsList, total, query);
		model.addAttribute("pageUtils", pageUtils);
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		return "front/cms/drafts";
	}
	
	/**
	 * 草稿箱编辑
	 * @param id
	 * @return
	 */
	@RequestMapping("/draftsEdit")
	public String draftsEdit(Integer id,Model model) {
		model.addAttribute("user", ShiroUtils.getUser().getUser());
		//文章详情
		ArticleDO articleDO = articleService.get(id);
		AchievementDO achievementDO = articleService.getAchievement(id);
		BeanUtils.copyProperties(articleDO, achievementDO);
		model.addAttribute("articleDO", achievementDO);
		//附件
		List<FileDO> fileList = new ArrayList<FileDO>();
		if(StringUtils.isNotEmpty(articleDO.getImage())){
			String[] imageArray = articleDO.getImage().split(",");
			for (int i = 0; i < imageArray.length; i++) {
				FileDO fileObj = sysFileService.get(Long.parseLong(imageArray[i]));
				if(fileObj != null){
					fileList.add(fileObj);
				}
			}
		}
		model.addAttribute("fileList", fileList);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		return "front/cms/draftsEdit";
	}
	/**
	 * 草稿箱删除
	 * @param id
	 * @return
	 */
	@PostMapping("/draftsDelete")
	@ResponseBody
	public R draftsDelete(Integer id) {
		ArticleDO article = articleService.get(id);
		if(articleService.remove(id)>0){
			//刪除附件
			if(StringUtils.isNotEmpty(article.getImage())){
				String[] imageArray = article.getImage().split(",");
				for(int i=0; i<imageArray.length; i++){
					this.deleteFile(Long.parseLong(imageArray[i]));
				}
			}
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 草稿箱暂存
	 */
	@RequestMapping("/draftstemporary")
	@ResponseBody
	public R DraftsTemporary(ArticleDO article,MultipartFile[] file, HttpServletRequest request) {		
		LoginUser loginUser = ShiroUtils.getUser();
		article.setCategoryId(4);
		article.setCreateBy(Integer.parseInt(loginUser.getUser().getUserId().toString()));
		article.setCreateTime(new Date());
		article.setStatus("0");
		article.setScore(new BigDecimal(0));
		article.setRecommendNum(0);
		article.setPublishTime(article.getCreateTime());
		article.setIsPublish("0");
		article.setHits(0);
		String image = "";
		if(file !=null && file.length > 0) {
			for(MultipartFile f : file){
				FileDO fileDo = this.uploadFile(f, request);
				if(null != fileDo) {
					image += fileDo.getId() + ",";
				}
			}
		}
		if(StringUtils.isNotEmpty(image)){
			article.setImage(image.substring(0,image.length()-1));
		}
		articleService.update(article);
		return R.ok();
	}
	/**
	 * 我的文章 我的文章是 文章分类为4的， 然后创建人是当前登录人的
	 * @param params
	 * @return
	 */
	@RequestMapping("/myArticle")
	public String MyArticle(@RequestParam Map<String, Object> params,Model model) {
		model.addAttribute("user", ShiroUtils.getUser().getUser());
		if(!params.containsKey("title")){
			params.put("title", "");
		}
		if(!params.containsKey("datesearch")){
			params.put("datesearch", "");
		}
		if(!params.containsKey("type")){
			params.put("type", "");
		}
		if(!params.containsKey("isRecommend")){
			params.put("isRecommend", "");
		}
		LoginUser loginUser = ShiroUtils.getUser();
		params.put("categoryId", 4);
		params.put("createBy", loginUser.getId());
		params.put("isPublish", "1");
		Query query = new Query(params);
		List<ArticleDO> myarticlelList = articleService.myarticlelist(query);
		int total = articleService.count(query);
		PageUtils pageUtils = new PageUtils(myarticlelList, total, query);
		model.addAttribute("pageUtils", pageUtils);
		
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		return "front/cms/myArticle";
	}
	
	/**
	 * 我的文章详情
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/myArticle/detail")
	public String myarticleDetail(@RequestParam Integer id, Model model) {
		ArticleDO article = articleService.get(id);
		ArticleDataDO data=articleDataService.get(id);
		if(article.getHits()!=null){
			article.setHits(article.getHits() + 1);
		}else{
			article.setHits(1);
		}
		articleService.update(article);
		model.addAttribute("data", data);
		model.addAttribute("article", article);
		FileDO fileObj=new FileDO();
		if(StringUtils.isNotEmpty(article.getImage())){
			fileObj=sysFileService.get(Long.parseLong(article.getImage()));	
		}
		model.addAttribute("fileObj", fileObj);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		return "front/article/myArticleDetail";
	}

	/**
	 * 消息通知
	 * @param params
	 * @return
	 */
	@RequestMapping("/mynews")
	public String Mynews(@RequestParam Map<String, Object> params,Model model) {
		LoginUser loginUser = ShiroUtils.getUser();
		UserDO user=new UserDO();
		user.setfType("U");
		if(loginUser!=null){
			model.addAttribute("user", ShiroUtils.getUser().getUser());
		}else{
			model.addAttribute("user",user);
		}
		if(loginUser.getUser().getfType().equals("M"))
		{
			Query query = new Query(params);
			List<UserMessageDO> messageList = usermessageService.notifylistByMember(query);
			int total = usermessageService.notifylistByMemberCount(query);
			PageUtils pageUtils = new PageUtils(messageList, total, query);
			model.addAttribute("pageUtils", pageUtils);
			
			
		}
		if(loginUser.getUser().getfType().equals("E"))
		{
			Query query = new Query(params);
			List<UserMessageDO> messageList = usermessageService.notifylistByExpert(query);
			int total = usermessageService.notifylistByExpertCount(query);
			PageUtils pageUtils = new PageUtils(messageList, total, query);
			model.addAttribute("pageUtils", pageUtils);
			
		}
		
		if(loginUser.getUser().getfType().equals("A"))
		{
			Query query = new Query(params);
			List<UserMessageDO> messageList = new ArrayList<UserMessageDO>();
			int total = 0;
			PageUtils pageUtils = new PageUtils(messageList, total, query);
			model.addAttribute("pageUtils", pageUtils);
			
		}
		if(!params.containsKey("title")){
			params.put("title", "");
		}
		/*Query query = new Query(params);
		List<UserMessageDO> mynewsList = usermessageService.list(query);
		int total = usermessageService.count(query);
		PageUtils pageUtils = new PageUtils(mynewsList, total, query);
		model.addAttribute("pageUtils", pageUtils);*/
		
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		return "front/cms/mynews";
	}
/***
 * 消息通知详情
 * @param id
 * @param model
 * @return
 */
		@GetMapping("/mynews/detail")
		public String notifAarticleDetail(@RequestParam Integer id, Model model) {
			LoginUser loginUser = ShiroUtils.getUser();
			UserDO user=new UserDO();
			user.setfType("U");
			if(loginUser!=null){
				model.addAttribute("user", ShiroUtils.getUser().getUser());
			}else{
				model.addAttribute("user",user);
			}
			UserMessageDO usermessage = usermessageService.get(id);
			model.addAttribute("usermessage", usermessage);
			
			//上一篇
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("fMsgId",id);
			params.put("fToType",loginUser.getUser().getfType());
			UserMessageDO prevUserMessage  = usermessageService.prevUserMessage(params);
			model.addAttribute("prevUserMessage", prevUserMessage);
			
			//下一篇
			UserMessageDO nextUserMessage  = usermessageService.nextUserMessage(params);
			model.addAttribute("nextUserMessage", nextUserMessage);
			
			params = new HashMap<String, Object>();
			params.put("offset", 0);
			params.put("limit", 10);
			params.put("fStatus",1);
			params.put("fRel","1");
			List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
			model.addAttribute("cmsLinkList1", cmsLinkList);
			params.put("fRel","2");
			cmsLinkList = cmsLinkService.list(params);
			model.addAttribute("cmsLinkList2", cmsLinkList);
			params.put("fRel","3");
			cmsLinkList = cmsLinkService.list(params);
			model.addAttribute("cmsLinkList3", cmsLinkList);
			params.put("fRel","4");
			cmsLinkList = cmsLinkService.list(params);
			model.addAttribute("cmsLinkList4", cmsLinkList);
			
			return "front/cms/notifyArticleDetail";
		}
	
	/**
	 * 专家个人信息
	 * @param params
	 * @return
	 */
	@RequestMapping("/expertInfo")
	public String frontExpertInfo(Model model) {
		LoginUser loginUser = ShiroUtils.getUser();
		model.addAttribute("user", ShiroUtils.getUser().getUser());
		ExpertDO expert = expertService.get(Integer.parseInt(loginUser.getUser().getBusId()));
		model.addAttribute("expert", expert);
		FileDO fileObj=new FileDO();
		if(StringUtils.isNotEmpty(expert.getHeadImg())){
			fileObj=sysFileService.get(Long.parseLong(expert.getHeadImg()));	
		}
		model.addAttribute("fileObj", fileObj);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		return "front/article/expertInfo";
	}
	
	//专家 我的评论
	@GetMapping("/open/expertCommentList")
	public String expertCommentList(@RequestParam Integer expertId, Model model) {
		model.addAttribute("user", ShiroUtils.getUser().getUser());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("expertId", expertId);
		Query query = new Query(params);
		List<CommentDO> commentList = commentService.list(query);
		int total = commentService.count(query);
		PageUtils pageUtils = new PageUtils(commentList, total, query);
		model.addAttribute("pageUtils", pageUtils);
		
	    params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		
		return "front/article/expertComment";
	}
		
	//专家文章推荐列表展示
	@RequestMapping("/open/expertRecommendArticleList")
	public String expertRecommendArticleList(@RequestParam Map<String, Object> params,Model model) {
		model.addAttribute("user", ShiroUtils.getUser().getUser());
		ExpertDO expert=expertService.get(Integer.parseInt(ShiroUtils.getUser().getUser().getBusId()));
		model.addAttribute("expert", expert);
		if(!params.containsKey("title")){
			params.put("title", "");
		}
		if(!params.containsKey("datesearch")){
			params.put("datesearch", "");
		}
		if(!params.containsKey("type")){
			params.put("type", "");
		}
		if(!params.containsKey("isRecommend")){
			params.put("isRecommend", "");
		}
		params.put("expertId",Integer.parseInt(ShiroUtils.getUser().getUser().getBusId()));
		Query query = new Query(params);
		List<ArticleDO> articleList = articleService.expertRecommendList(query);
		int total =articleService.expertRecommendCount(query);
		PageUtils pageUtils = new PageUtils(articleList, total, query);
		model.addAttribute("pageUtils", pageUtils);
		params=new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		
		return "front/article/expertRecommend";
	}
	
	//专家文章推荐详情
	@GetMapping("/open/expertRecommendArticle/detail")
	public String expertRecommendArticleDetail(@RequestParam Integer id, Model model) {
		model.addAttribute("user", ShiroUtils.getUser().getUser());
		ArticleDO article = articleService.get(id);
		ArticleDataDO data=articleDataService.get(id);
		if(article.getHits()!=null){
			article.setHits(article.getHits() + 1);
		}else{
			article.setHits(1);
		}
		articleService.update(article);
		model.addAttribute("data", data);
		model.addAttribute("article", article);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", "1");
		params.put("articleId", id);
		Query query = new Query(params);
		List<CommentDO> commentList = commentService.list(query);
		int total = commentService.count(query);
		PageUtils pageUtils = new PageUtils(commentList, total, query);
		model.addAttribute("pageUtils", pageUtils);
		
	    params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		
		return "front/article/expertRecommendArticleDetail";
	}
		
	//专家已评文章
	@RequestMapping("/open/expertCommentArticleList")
	public String expertCommentArticleList(@RequestParam Map<String, Object> params, Model model) {
		model.addAttribute("user", ShiroUtils.getUser().getUser());
		if(!params.containsKey("title")){
			params.put("title", "");
		}
		if(!params.containsKey("datesearch")){
			params.put("datesearch", "");
		}
		if(!params.containsKey("type")){
			params.put("type", "");
		}
		params.put("expertId", ShiroUtils.getUser().getUser().getBusId());
		Query query = new Query(params);
		//查询已评文章
		List<ArticleDO> experevaluateList = articleService.experevaluateList(query);
		int total =articleService.experevaluateListCount(query);
		PageUtils pageUtils = new PageUtils(experevaluateList, total, query);
		model.addAttribute("pageUtils", pageUtils);
		
		
	    params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		
		return "front/article/expertCommentedArticle";
	}
	
	//专家已评文章详情
	@GetMapping("/open/expertCommentArticle/detail")
	public String expertCommentArticleDetail(@RequestParam Integer id, Model model) {
		model.addAttribute("user", ShiroUtils.getUser().getUser());
		ArticleDO article = articleService.get(id);
		ArticleDataDO data=articleDataService.get(id);
		if(article.getHits()!=null){
			article.setHits(article.getHits() + 1);
		}else{
			article.setHits(1);
		}
		articleService.update(article);
		model.addAttribute("data", data);
		model.addAttribute("article", article);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", "1");
		params.put("articleId", id);
		Query query = new Query(params);
		List<CommentDO> commentList = commentService.list(query);
		int total = commentService.count(query);
		PageUtils pageUtils = new PageUtils(commentList, total, query);
		model.addAttribute("pageUtils", pageUtils);
		
	    params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		
		return "front/article/expertCommentedArticleDetail";
	}
			
	
	//专家 新文章评分列表展示
	@RequestMapping("/open/articleScorelist")
	public String articleScoreList(@RequestParam Map<String, Object> params,Model model) {
		LoginUser loginUser = ShiroUtils.getUser();
		model.addAttribute("user", loginUser.getUser());
		//查询需要评分的文章
		if(!params.containsKey("title")){
			params.put("title", "");
		}
		if(!params.containsKey("datesearch")){
			params.put("datesearch", "");
		}
		if(!params.containsKey("type")){
			params.put("type", "");
		}
		params.put("expertId", loginUser.getUser().getBusId());
		Query query = new Query(params);
		List<ArticleDO> articleScoreList = articleService.articleScoreList(query);
		int total = articleService.articleScoreListCount(query);
		PageUtils pageUtils = new PageUtils(articleScoreList, total, query);
		model.addAttribute("pageUtils", pageUtils);

		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		
		return "front/article/articleScoreList";
	}
	
	//专家新文章评分详情
	@GetMapping("/open/articleScore/detail")
	public String articleScoreDetail(@RequestParam Integer id, Model model) {

		LoginUser loginUser = ShiroUtils.getUser();
		//查询文章信息
		ArticleDO article = articleService.get(id);
		AchievementDO achievementDO = articleService.getAchievement(id);
		BeanUtils.copyProperties(article, achievementDO);
		ArticleDataDO data=articleDataService.get(id);
		if(article.getHits()!=null){
			article.setHits(article.getHits() + 1);
		}else{
			article.setHits(1);
		}
		articleService.update(article);

		model.addAttribute("data", data);
		model.addAttribute("article", achievementDO);

		Map<String, Object> params = new HashMap<String, Object>();
		ExperevaluateDO experevaluate = new ExperevaluateDO();
		
		if(loginUser != null){
			model.addAttribute("user", loginUser.getUser());
			model.addAttribute("userType", loginUser.getUser().getfType());
			//专家文章关联表
			params.put("articleId", id);
			params.put("expertId", loginUser.getUser().getBusId());
			List<ExperevaluateDO> experevaluateList=experevaluateService.list(params);
			if(experevaluateList != null && experevaluateList.size() > 0){
				experevaluate = experevaluateList.get(0);
			}
			
			//该专家评论
			params = new HashMap<String, Object>();
			params.put("expertId", loginUser.getUser().getBusId());
			params.put("articleId", id);
			List<CommentDO> comment = commentService.list(params);
			if(comment != null && comment.size() > 0){
				CommentDO commentDO = comment.get(0);
				model.addAttribute("commentDO", commentDO);
			}else{
				CommentDO commentDO=new CommentDO();
				commentDO.setcContent("说点什么...");
				model.addAttribute("commentDO", commentDO);
			}
		}else{
			model.addAttribute("user", null);
			model.addAttribute("userType", "U");
		}
		model.addAttribute("experevaluate", experevaluate);
		
		//评论列表
		params = new HashMap<String, Object>();
		params.put("status", "1");
		params.put("articleId", id);
		Query query = new Query(params);
		List<CommentDO> commentList = commentService.list(query);
		int total = commentService.count(query);
		PageUtils pageUtils = new PageUtils(commentList, total, query);
		model.addAttribute("pageUtils", pageUtils);
		
		//友情链接
	    params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);

		//return "front/article/articleScore";
		return "front/cms/achievement";
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/recommend")
	public R recommend(Integer id,String isRecommend){
		ArticleDO article=articleService.get(id);
		if("1".equals(isRecommend)){
			article.setIsRecommend("0");
			articleService.update(article);
			ExperevaluateDO experevaluate=new ExperevaluateDO();
			UserDO user = ShiroUtils.getUser().getUser();
			experevaluate.setIsRecommend("0");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("articleId", article.getId());
			params.put("expertId", Integer.parseInt(user.getBusId()));
			if(experevaluateService.count(params)>0){
				List<ExperevaluateDO> experevaluatelist = experevaluateService.list(params);
			    experevaluate = new ExperevaluateDO();
				if(experevaluatelist != null && experevaluatelist.size() > 0){
					experevaluate = experevaluatelist.get(0);
					experevaluate.setRecommendDate(null);
					experevaluate.setIsRecommend("0");
					experevaluateService.update(experevaluate);
				}
				return R.ok();
			}else{
				experevaluate.setArticleId(article.getId());
				experevaluate.setExpertId(Integer.parseInt(user.getBusId()));;
				experevaluateService.save(experevaluate);
				return R.ok();
			}
			
		}else{
			article.setRecommendDate(new Date());
			article.setIsRecommend("1");
			articleService.update(article);
			ExperevaluateDO experevaluate=new ExperevaluateDO();
			UserDO user = ShiroUtils.getUser().getUser();
			experevaluate.setRecommendDate(new Date());
			experevaluate.setIsRecommend("1");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("articleId", article.getId());
			params.put("expertId", Integer.parseInt(user.getBusId()));
			if(experevaluateService.count(params)>0){
				List<ExperevaluateDO> experevaluatelist = experevaluateService.list(params);
			    experevaluate = new ExperevaluateDO();
				if(experevaluatelist != null && experevaluatelist.size() > 0){
					experevaluate = experevaluatelist.get(0);
					experevaluate.setRecommendDate(new Date());
					experevaluate.setIsRecommend("1");
					experevaluateService.update(experevaluate);
				}
				return R.ok();
			}else{
				experevaluate.setArticleId(article.getId());
				experevaluate.setExpertId(Integer.parseInt(user.getBusId()));;
				experevaluateService.save(experevaluate);
			}
			return R.ok();
		}
	}
	@ResponseBody
	@RequestMapping("/score")
	public R score(Integer id,Double score){
		BigDecimal s=new BigDecimal(score);
		ArticleDO article=articleService.get(id);
		UserDO user = ShiroUtils.getUser().getUser();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("expertId", user.getBusId());
		params.put("articleId", article.getId());
		List<ExperevaluateDO> list = experevaluateService.list(params);
		if(list != null && list.size() > 0){
			ExperevaluateDO experevaluate = list.get(0);
			experevaluate.setScore(s);
			experevaluate.setScoreDate(new Date());
			experevaluateService.update(experevaluate);
		}
		
		//判断该文章评分是否全部完成
		params = new HashMap<String, Object>();
		params.put("expertId", user.getBusId());
		params.put("articleId", article.getId());
		int completeCount = experevaluateService.notCompleteCount(params);
		if(completeCount == 0){
			//同一文章，不同专家评分，求平均
			article.setScore(new BigDecimal(experevaluateService.avgScore(article.getId())));
			//总推荐数
			article.setRecommendNum(experevaluateService.recommendNum(article.getId()));
			//总点赞数
			article.setLikeNum(experevaluateService.likeNum(article.getId()));
			//更新文章分数、总推荐数和点赞数
			articleService.update(article);
		}
		return R.ok();
	}
	@ResponseBody
	@RequestMapping("/comment")
	public R comment(Integer id,Integer cId,String cContent){
		CommentDO comment=new CommentDO();
		ArticleDO article=articleService.get(id);
		UserDO user = ShiroUtils.getUser().getUser();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("articleId", article.getId());
		params.put("expertId", Integer.parseInt(user.getBusId()));
		if(commentService.count(params)>0){
			CommentDO commentDO=new CommentDO();
			commentDO.setcId(cId);
			commentDO.setCreateDate(comment.getCreateDate());
			commentDO.setcContent(cContent);
			commentService.update(commentDO);
			return R.ok();
		}else {
			comment.setExpertId(Integer.parseInt(user.getBusId()));
			comment.setCreateDate(new Date());
			comment.setIsRecommend("0");
			comment.setStatus("0");
			comment.setArticleId(id);
			comment.setcContent(cContent);
			commentService.save(comment);
			return R.ok();	
		}
	}
	@ResponseBody
	@RequestMapping("/recommendnum")
	public R recommendnum(Integer id,String isLike){
		ArticleDO article=articleService.get(id);
		if(StringUtils.isNotEmpty(isLike)){
			article.setIsLike(isLike);
		}else{
			article.setIsLike("0");
		}
		UserDO user = ShiroUtils.getUser().getUser();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("expertId", user.getBusId());
		params.put("articleId", article.getId());
		//查询关联表信息
		List<ExperevaluateDO> list = experevaluateService.list(params);
		if(list != null && list.size() > 0){
			ExperevaluateDO experevaluate = list.get(0);
			if("1".equals(article.getIsLike())){
				experevaluate.setIsLike("0");
			}else{
				experevaluate.setIsLike("1");
				experevaluate.setLikeDate(new Date());
			}
			experevaluateService.update(experevaluate);
		}
		//更新该文章的总点赞数
		article.setLikeNum(experevaluateService.likeNum(article.getId()));
		//更新文章点赞数
		articleService.update(article);
		return R.ok();
	}
	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	public R remove(Integer id){		
		if(commentService.remove(id)>0){
			return R.ok();
		}
		return R.error();
	}
//	@RequestMapping("/expertInfo")
//	public String frontExpertInfo(@RequestParam Map<String, Object> params) {
//		return "front/cms/expertInfo";
//	}
	
	@PostMapping("/sendCode")
	@ResponseBody
	public R sendCode(String email){
		try {
			RandomValidateCodeUtils randomValidateCode = new RandomValidateCodeUtils();
			String randomString = randomValidateCode.sendCode();
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(emailConfig.getUsername());
			message.setTo(email);
			message.setSubject("【山东省】找回密码");
			message.setText("【山东省】您正在找回密码验证，验证码："+randomString+"，请在15分钟内按页面提示提交验证码，切勿将验证码泄露于他人。");
			mailSender.send(message);
			
			//保存信息
			Date nowDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nowDate);
			calendar.add(Calendar.MINUTE, 15);
			
			EmailCodeDO emailCode = new EmailCodeDO();
			emailCode.setEcCode(randomString);
			emailCode.setEcEmail(email);
			emailCode.setCreateDate(nowDate);
			emailCode.setEcValidDate(calendar.getTime());
			emailCodeService.save(emailCode);
			return R.ok("验证码发送成功，请注意查收");
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("操作失败");
		}
	}
	
	
	//我要留言
	@RequestMapping("/leaveMessage")
	public String frontleaveMessage(Model model) {
		LoginUser loginUser = ShiroUtils.getUser();
		Map<String, Object> params = new HashMap<String, Object>();
		List<LeaveMessageTypeDO> leaveMessageTypeList= leaveMessageTypeService.list(params);
		model.addAttribute("leaveMessageTypeList", leaveMessageTypeList);
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus",1);
		params.put("fRel","1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel","2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel","3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel","4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);
		if(loginUser!=null)
		{
			model.addAttribute("user", ShiroUtils.getUser().getUser());
			return "front/article/leaveMessage";
		}else{
			return "front/cms/messageBoard";
		}
	}
	
	
	//提交留言
	@RequestMapping("/leaveMessage/add")
	@ResponseBody
	public R leaveMessageAdd(LeaveMessageDO leaveMessage) {
		
		//默认值
		LoginUser loginUser = ShiroUtils.getUser();
		leaveMessage.setCreateDate(new Date());
		leaveMessage.setStatus("0");
		if(loginUser!=null){
			leaveMessage.setCreateBy(Integer.parseInt(loginUser.getUser().getUserId().toString()));
			leaveMessage.setLoginStatus("0");
		}else{
			leaveMessage.setLoginStatus("1");
		}
		if(leaveMessageService.save(leaveMessage) > 0){
			return R.ok();
		}else {
			return R.error();
		}
	}
	//layeropen
	@RequestMapping("/layeropen")
	public String layeropen() {
		return "front/cms/layeropen";
	}
	
	/**
	 * 
	 * @Description: 文章展示页面或者文章列表页面
	 * @param categoryId
	 * @param model
	 * @return
	 * @author: 窦恩虎
	 * @date 2018年7月26日 下午9:07:23
	 */
	@RequestMapping("/open/page/{categoryId}")
	String about(@PathVariable("categoryId") int categoryId, Model model, @RequestParam Map<String, Object> params) {
		CategoryDO category = categoryService.get(categoryId);// 获取分类信息
		CategoryDO categoryDO = new CategoryDO(); //子类或者父类
		if ("list".equals(category.getModule())) {// 列表展示文章
			
			if(category.getParentId() != 0){
				//二级分类
				params.put("parentId", category.getParentId());
			}else{
				//一级分类
				params.put("parentId", categoryId);
			}
			List<CategoryDO> categoryList = categoryService.list(params);
			params = new HashMap<String, Object>();
			params.put("categoryId", category.getId());
			if(category.getParentId() != 0){
				//二级分类
				categoryDO = categoryService.get(category.getParentId());
			}else if(categoryList != null && categoryList.size() > 0){
				//一级分类
				categoryDO = categoryList.get(0);
				params.put("categoryId", categoryDO.getId());
			}
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("category", category);
			model.addAttribute("categoryDO", categoryDO);
			
			Query query = new Query(params);
			List<ArticleDO> bContentList = articleService.list(query);
			int total = articleService.count(query);
			PageUtils pageUtils = new PageUtils(bContentList, total, query);
			model.addAttribute("pageUtils", pageUtils);

			return "front/cms/articleList";
			// 调转到list展示页面
			
			
		} else if ("article".equals(category.getModule())) {// 单文章
			LoginUser loginUser = ShiroUtils.getUser();
			if(loginUser!=null){
				model.addAttribute("user", ShiroUtils.getUser().getUser());
			}else{
				UserDO user=new UserDO();
				user.setfType("U");
				model.addAttribute("user",user);
			}
			// 获取第一篇文章信息
			Map<String, Object> map = new HashMap<>(16);
			map.put("categoryId", categoryId);
			ArticleDO bContentDO = null;
			if (articleService.list(map).size() > 0) {
				bContentDO = articleService.list(map).get(0);
				
				if(bContentDO.getHits()!=null){
					bContentDO.setHits(bContentDO.getHits() + 1);
				}else{
					bContentDO.setHits(1);
				}
				articleService.update(bContentDO);
			}
			model.addAttribute("article", bContentDO);
			model.addAttribute("category", category);
			
			List<FileDO> fileList = new ArrayList<FileDO>();
			if(StringUtils.isNotEmpty(bContentDO.getAttachment())){
				String[] imageArray = bContentDO.getAttachment().split(",");
				for (int i = 0; i < imageArray.length; i++) {
					FileDO fileObj = sysFileService.get(Long.parseLong(imageArray[i]));
					if(fileObj != null){
						fileList.add(fileObj);
					}
				}
			}
			model.addAttribute("fileList", fileList);
			
			return "front/cms/article";
		}else if ("videoList".equals(category.getModule())) {

			if(category.getParentId() != 0){
				//二级分类
				params.put("parentId", category.getParentId());
			}else{
				//一级分类
				params.put("parentId", categoryId);
			}
			List<CategoryDO> categoryList = categoryService.list(params);
			params = new HashMap<String, Object>();
			params.put("categoryId", category.getId());
			if(category.getParentId() != 0){
				//二级分类
				categoryDO = categoryService.get(category.getParentId());
			}else if(categoryList != null && categoryList.size() > 0){
				//一级分类
				categoryDO = categoryList.get(0);
				params.put("categoryId", categoryDO.getId());
			}
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("category", category);
			model.addAttribute("categoryDO", categoryDO);

			Query query = new Query(params);
			List<ArticleDO> bContentList = articleService.list(query);
			int total = articleService.count(query);
			PageUtils pageUtils = new PageUtils(bContentList, total, query);
			model.addAttribute("pageUtils", pageUtils);

			return "front/cms/videoList";
		} else if ("achievementList".equals(category.getModule())) {

			if(category.getParentId() != 0){
				//二级分类
				params.put("parentId", category.getParentId());
			}else{
				//一级分类
				params.put("parentId", categoryId);
			}
			List<CategoryDO> categoryList = categoryService.list(params);
			params = new HashMap<String, Object>();
			params.put("categoryId", category.getId());
			if(category.getParentId() != 0){
				//二级分类
				categoryDO = categoryService.get(category.getParentId());
			}else if(categoryList != null && categoryList.size() > 0){
				//一级分类
				categoryDO = categoryList.get(0);
				params.put("categoryId", categoryDO.getId());
			}
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("category", category);
			model.addAttribute("categoryDO", categoryDO);

			Query query = new Query(params);
			List<ArticleDO> bContentList = articleService.list(query);
			int total = articleService.count(query);
			PageUtils pageUtils = new PageUtils(bContentList, total, query);
			model.addAttribute("pageUtils", pageUtils);

			return "front/cms/achievementList";
		}
		return "front/cms/articleList";
	}
	
		//文章详情
	@GetMapping("/open/aArticle/detail")
	public String aArticleDetail(@RequestParam Integer id, Model model) {

			ArticleDO article = articleService.get(id);
			if(article.getHits()!=null){
				article.setHits(article.getHits() + 1);
			}else{
				article.setHits(1);
			}
			articleService.update(article);
			model.addAttribute("article", article);
			List<FileDO> fileList = new ArrayList<FileDO>();
			if(StringUtils.isNotEmpty(article.getAttachment())){
				String[] imageArray = article.getAttachment().split(",");
				for (int i = 0; i < imageArray.length; i++) {
					FileDO fileObj = sysFileService.get(Long.parseLong(imageArray[i]));
					if(fileObj != null){
						fileList.add(fileObj);
					}
				}
			}
			model.addAttribute("fileList", fileList);
			
			CategoryDO category = categoryService.get(article.getCategoryId());// 获取分类信息
			CategoryDO categoryDO = new CategoryDO(); //子类或者父类
			Map<String,Object> params = new HashMap<String, Object>();
			if(category.getParentId() != 0){
				//二级分类
				params.put("parentId", category.getParentId());
			}else{
				//一级分类
				params.put("parentId", article.getCategoryId());
			}
			List<CategoryDO> categoryList = categoryService.list(params);
			if(category.getParentId() != 0){
				//二级分类
				categoryDO = categoryService.get(category.getParentId());
			}else if(categoryList != null && categoryList.size() > 0){
				//一级分类
				categoryDO = categoryList.get(0);
			}
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("category", category);
			model.addAttribute("categoryDO", categoryDO);
			
			//上一篇
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", article.getId());
			map.put("categoryId", article.getCategoryId());
			map.put("delFlag", article.getDelFlag());
			map.put("status", article.getStatus());
			map.put("type", article.getType());
			ArticleDO prevArticle = articleService.prevArticle(map);
			model.addAttribute("prevArticle", prevArticle);
			
			//下一篇
			ArticleDO nextArticle = articleService.nextArticle(map);
			model.addAttribute("nextArticle", nextArticle);
			
			params = new HashMap<String, Object>();
			params.put("offset", 0);
			params.put("limit", 10);
			params.put("fStatus",1);
			params.put("fRel","1");
			List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
			model.addAttribute("cmsLinkList1", cmsLinkList);
			params.put("fRel","2");
			cmsLinkList = cmsLinkService.list(params);
			model.addAttribute("cmsLinkList2", cmsLinkList);
			params.put("fRel","3");
			cmsLinkList = cmsLinkService.list(params);
			model.addAttribute("cmsLinkList3", cmsLinkList);
			params.put("fRel","4");
			cmsLinkList = cmsLinkService.list(params);
			model.addAttribute("cmsLinkList4", cmsLinkList);

			if("videoList".equals(category.getModule())) {

				return "front/cms/video";
			}
			return "front/cms/aArticleDetail";
	}
		
	// 党建列表
	@GetMapping("/open/partyBuilding/list")
	public String partyBuildingList(Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		CategoryDO category = categoryService.get(12);// 获取分类信息
		model.addAttribute("category", category);
		params.put("categoryId", 12);
		Query query = new Query(params);
		List<ArticleDO> bContentList = articleService.list(query);
		int total = articleService.count(query);
		PageUtils pageUtils = new PageUtils(bContentList, total, query);
		model.addAttribute("pageUtils", pageUtils);

		params = new HashMap<String, Object>();
		params.put("offset", 0);
		params.put("limit", 10);
		params.put("fStatus", 1);
		params.put("fRel", "1");
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList1", cmsLinkList);
		params.put("fRel", "2");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList2", cmsLinkList);
		params.put("fRel", "3");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList3", cmsLinkList);
		params.put("fRel", "4");
		cmsLinkList = cmsLinkService.list(params);
		model.addAttribute("cmsLinkList4", cmsLinkList);

		return "front/cms/partyBuilding";
	}
}
