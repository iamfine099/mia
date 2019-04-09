package com.bootdo.mia.dao;

import com.bootdo.mia.domain.CommentDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 1)用于记录评论。
2)通过专家对文章评论产生记录。
3)该实体主要由评论审核、评论推荐等业务使
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:33
 */
@Mapper
public interface CommentDao {

	CommentDO get(Integer cId);
	
	List<CommentDO> list(Map<String,Object> map);
	
	List<CommentDO> recommendlist(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(CommentDO comment);
	
	int update(CommentDO comment);
	
	int remove(Integer c_id);
	
	int batchRemove(Integer[] cIds);

	int commentAuditUpdateCheck(CommentDO comment);
}
