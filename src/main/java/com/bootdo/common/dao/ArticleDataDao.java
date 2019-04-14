package com.bootdo.common.dao;

import com.bootdo.common.domain.AchievementDO;
import com.bootdo.common.domain.ArticleDataDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 文章数据表
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:31
 */
@Mapper
public interface ArticleDataDao {

	ArticleDataDO get(Integer id);

	AchievementDO getAchievement(Integer id);

	List<ArticleDataDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ArticleDataDO articleData);

	int saveAchievement(AchievementDO articleData);

	int update(ArticleDataDO articleData);

	int updateAchievement(AchievementDO articleData);

	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
