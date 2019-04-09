package com.bootdo.mia.dao;

import com.bootdo.mia.domain.ExpertDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 1)用于记录专家信息。
2)通过后台添加产生记录。
3)该实体主要由专家登录、专家管理等业务使用
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:34
 */
@Mapper
public interface ExpertDao {

	ExpertDO get(Integer expertId);
	
	List<ExpertDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ExpertDO expert);
	
	int update(ExpertDO expert);
	
	int remove(Integer expert_id);
	
	int batchRemove(Integer[] expertIds);
}
