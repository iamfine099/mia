/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bootdo.cms.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.bootdo.cms.domain.CmsLinkDO;
import com.bootdo.cms.service.CmsLinkService;
import com.bootdo.common.config.ApplicationContextRegister;
import com.bootdo.common.config.Constant;
import com.bootdo.common.domain.ArticleDO;
import com.bootdo.common.domain.CategoryDO;
import com.bootdo.common.service.ArticleService;
import com.bootdo.common.service.CategoryService;
import com.bootdo.common.utils.CacheUtils;
import com.bootdo.common.utils.JsonMapper;

/**
 * 内容管理工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class CmsUtils {
	/**
	 * 固定分类
	 */
	public static int CATEGORY_ID_STUDENT = 44;//优秀在校生
	
	public static int CATEGORY_ID_EMPLOYMENT = 41;//优秀毕业生
	
	public static int CATEGORY_ID_EMPLOYER = 40;//优秀用人单位
	
	public static int CATEGORY_ID_NETJOB = 35;//网络招聘职位
	
	public static int CATEGORY_ID_CAMPUSJOB = 34;//校园招聘
	
	private static CategoryService categoryService =  ApplicationContextRegister.getBean(CategoryService.class);

	
	private static ArticleService articleService = ApplicationContextRegister.getBean(ArticleService.class);
	private static CmsLinkService cmsLinkService = ApplicationContextRegister.getBean(CmsLinkService.class);

	private static final String CMS_CACHE = "cmsCache";
	
	
	/**
	 * 获得主导航列表
	 */
	@SuppressWarnings("unchecked")
	public static List<CategoryDO> getMainNavList(){
		CacheUtils.remove(CMS_CACHE, "mainNavList");
		List<CategoryDO> mainNavList = (List<CategoryDO>)CacheUtils.get(CMS_CACHE, "mainNavList");
		if (mainNavList == null || mainNavList.size()<1){
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("parentId","0");
			param.put("inMenu", Constant.SHOW);
			param.put("delFlag", Constant.SHOW);
			
			mainNavList = categoryService.list(param);
			CacheUtils.put(CMS_CACHE, "mainNavList", mainNavList);
		}
		return mainNavList;
	}
	/**
	 * 获取栏目
	 * @param categoryId 栏目编号
	 * @return
	 */
	public static CategoryDO getCategory(int categoryId){
		return categoryService.get(categoryId);
	}
	/**
	 * 获得栏目列表
	 * @param siteId 站点编号
	 * @param parentId 分类父编号
	 * @param number 获取数目
	 * @param param  预留参数，例： key1:'value1', key2:'value2' ...
	 */
	public static List<CategoryDO> getCategoryList( int parentId, int number, String param){
		
		List<CategoryDO> mainNavList = (List<CategoryDO>)CacheUtils.get(CMS_CACHE, "mainNavList_"+parentId);
		if (mainNavList == null){
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("parentId",parentId);
			params.put("delFlag", Constant.SHOW);
			/*if (StringUtils.isNoneBlank(param)){
				@SuppressWarnings({ "unused", "rawtypes" })
				Map map = JsonMapper.getInstance().fromJson("{"+param+"}", Map.class);
			}*/
			if(number>0){
				params.put("offset",0);
				params.put("limit",number);
			}
			mainNavList = categoryService.list(params);
			
			CacheUtils.put(CMS_CACHE, "mainNavList_"+parentId, mainNavList);
			
		}
		
		return mainNavList;
	}
	/**
	 * 获取文章
	 * @param articleId 文章编号
	 * @return
	 */
	public static ArticleDO getArticle(int articleId){
		return articleService.get(articleId);
	}
	/**
	 * 获取文章列表
	 * @param categoryId 分类编号
	 * @param number 获取数目
	 * @param param  预留参数，例： key1:'value1', key2:'value2' ...
	 *          sort 排序字符串 order
	 * @return
	 * ${fnc:getArticleList(category.site.id, category.id, not empty pageSize?pageSize:8, 'posid:2, orderBy: \"hits desc\"')}"
	 */
	public static List<ArticleDO> getArticleList( String categoryId, int number, String param){
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("categoryId",categoryId);
		params.put("delFlag", Constant.SHOW);
		if (StringUtils.isNotBlank(param)){
			@SuppressWarnings({ "rawtypes" })
			Map map = JsonMapper.getInstance().fromJson("{"+param+"}", Map.class);
			if (new Integer(1).equals(map.get("posid")) || new Integer(2).equals(map.get("posid"))){
				params.put("posid", String.valueOf(map.get("posid")));
			}
			if (StringUtils.isNotBlank((String)map.get("sort"))&& StringUtils.isNotBlank((String)map.get("order"))){
				params.put("sort",String.valueOf(map.get("sort")));
				params.put("order",String.valueOf(map.get("order")));
			}
		}
		if(number>0){
			params.put("offset",0);
			params.put("limit",number);
		}
		List<ArticleDO> articleList =  articleService.list(params);
		
		return articleList;
	}
	/**
	 * 获取链接列表
	 * @param number 获取数目
	 * @param param  预留参数，例： key1:'value1', key2:'value2' ...
	 * @return
	 */
	public static List<CmsLinkDO> getLinkList( int number, String param){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("delFlag", Constant.SHOW);
		
		if (StringUtils.isNotBlank(param)){
			@SuppressWarnings({ "unused", "rawtypes" })
			Map map = JsonMapper.getInstance().fromJson("{"+param+"}", Map.class);
		}
		if(number>0){
			params.put("offset",0);
			params.put("limit",number);
		}
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(params);
		return cmsLinkList;
	}
	
	/**
	 * 获取轮播图
	 * @return
	 */
	public static List<ArticleDO> getRoundList(){
		List<ArticleDO> articleList = articleService.getRoundList();
		return articleList;
	}
	
	public static Map<String,Object> getCategoryList(){
		Map<String,Object> back = new HashMap<String,Object>();
		//查询栏目第一栏前四个
		List<CategoryDO> categoryList = categoryService.cmsList();
		for (int i = 0; i < categoryList.size(); i++) {
			//根据栏目查询文章
			List<ArticleDO> articleList = articleService.articleListForCategoryId(categoryList.get(i).getId());
			if(i == 0){
				back.put("articleListOne", articleList);
			}else if(i == 1){
				back.put("articleListTwo", articleList);
			}else if(i == 2){
				back.put("articleListThree", articleList);
			}else if(i == 3){
				back.put("articleListFour", articleList);
			}
		}
		back.put("categoryList", categoryList);
		return back;
	}
	// ============== Cms Cache ==============
	
	public static Object getCache(String key) {
		return CacheUtils.get(CMS_CACHE, key);
	}

	public static void putCache(String key, Object value) {
		CacheUtils.put(CMS_CACHE, key, value);
	}

	public static void removeCache(String key) {
		CacheUtils.remove(CMS_CACHE, key);
	}
	
}