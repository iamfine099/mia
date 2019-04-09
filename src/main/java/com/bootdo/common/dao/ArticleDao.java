package com.bootdo.common.dao;

import com.bootdo.common.domain.ArticleDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 文章表
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:31
 */
@Mapper
public interface ArticleDao {

	ArticleDO get(Integer id);
	
	List<ArticleDO> list(Map<String,Object> map);
	
	List<ArticleDO> articleCommentsList(Map<String,Object> map);
	
	List<ArticleDO> expertRecommendList(Map<String,Object> map);
	
	List<ArticleDO> photolist(Map<String,Object> map);

	List<ArticleDO> draftslist(Map<String, Object> map);
	
	List<ArticleDO> myarticlelist(Map<String, Object> map);
	
	int count(Map<String,Object> map);
	
	int expertRecommendCount(Map<String,Object> map);
	
	int articleCommentsCount(Map<String,Object> map);
	
	int save(ArticleDO article);
	
	int update(ArticleDO article);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
	List<ArticleDO> articleListForCategoryId(Integer categoryId);

	List<ArticleDO> getRoundList();

	int articleReviewBatchCheck(ArticleDO article);

	int articleReviewBatchScoreCheck(ArticleDO article);
	
	ArticleDO prevArticle(Map<String, Object> map);

	ArticleDO nextArticle(Map<String, Object> map);

	List<ArticleDO> articleScoreList(Map<String, Object> map);

	int articleScoreListCount(Map<String, Object> map);

	List<ArticleDO> experevaluateList(Map<String, Object> map);

	int experevaluateListCount(Map<String, Object> map);

	List<ArticleDO> articleReviewlist(Map<String, Object> map);
	
	int articleReviewCount(Map<String,Object> map);
}
