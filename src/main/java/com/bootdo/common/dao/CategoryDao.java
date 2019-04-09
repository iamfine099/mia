package com.bootdo.common.dao;

import com.bootdo.common.domain.CategoryDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 栏目表
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-03 13:58:50
 */
@Mapper
public interface CategoryDao {

	CategoryDO get(Integer id);
	
	List<CategoryDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(CategoryDO category);
	
	int update(CategoryDO category);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	int getCategoryArticleNumber(Integer id);

	List<CategoryDO> cmsList();
}
