package com.bootdo.mia.dao;

import com.bootdo.mia.domain.ExperevaluateDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 1)用于记录专家对文章的推荐和评分。
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:35
 */
@Mapper
public interface ExperevaluateDao {

	ExperevaluateDO get(Integer eetId);
	
	List<ExperevaluateDO> list(Map<String,Object> map);
	
	List<ExperevaluateDO> detailList(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int detailCount(Map<String,Object> map);
	
	int save(ExperevaluateDO experevaluate);
	
	int update(ExperevaluateDO experevaluate);
	
	int remove(Integer eet_id);
	
	int batchRemove(Integer[] eetIds);

	int notCompleteCount(Map<String,Object> map);

	String avgScore(Integer articleId);

	Integer recommendNum(Integer articleId);

	Integer likeNum(Integer articleId);
}
