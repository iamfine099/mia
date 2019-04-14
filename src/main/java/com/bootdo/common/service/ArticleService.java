package com.bootdo.common.service;

import com.bootdo.common.domain.AchievementDO;
import com.bootdo.common.domain.ArticleDO;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
/**
 * 文章表
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:31
 */
public interface ArticleService {
	
	ArticleDO get(Integer id);

	AchievementDO getAchievement(Integer id);

	List<ArticleDO> draftslist(Map<String, Object> map);
	
	List<ArticleDO> myarticlelist(Map<String, Object> map);
	
	List<ArticleDO> photolist(Map<String, Object> map);
	
	List<ArticleDO> list(Map<String, Object> map);
	
	List<ArticleDO> articleCommentsList(Map<String,Object> map);
	
	List<ArticleDO> expertRecommendList(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int expertRecommendCount(Map<String, Object> map);
	
	int articleCommentsCount(Map<String, Object> map);
	
	int save(ArticleDO article);

	int saveAchievement(AchievementDO article);

	int update(ArticleDO article);

	int updateAchievement(AchievementDO article);

	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @param map
	 * @param out
	 */
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map,
			ServletOutputStream out);
	/**
	 * 导入excel
	 * @param fileName
	 * @param file
	 * @return
	 */
	public void importExcel(String fileName, MultipartFile file)throws Exception;
	
	List<ArticleDO> articleListForCategoryId(Integer categoryId);
	
	List<ArticleDO> getRoundList();

	int articleReviewBatchCheck(ArticleDO article);

	void articleCommentsExportExcel(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> params,
			ServletOutputStream out);
	//上一篇
	ArticleDO prevArticle(Map<String, Object> map);
	//下一篇
	ArticleDO nextArticle(Map<String, Object> map);

	//需要专家打分文章列表
	List<ArticleDO> articleScoreList(Map<String, Object> map);

	int articleScoreListCount(Map<String, Object> map);

	/**
	 * 已评文章列表
	 * @param map
	 * @return
	 */
	List<ArticleDO> experevaluateList(Map<String, Object> map);

	int experevaluateListCount(Map<String, Object> map);

	List<ArticleDO> articleReviewlist(Map<String, Object> map);
	
	int articleReviewCount(Map<String, Object> map);

	int articleReviewBatchScoreCheck(ArticleDO article);
}
