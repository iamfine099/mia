package com.bootdo.mia.dao;

import com.bootdo.mia.domain.MemberDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 1)用于记录会员信息。
2)通过前端用户注册产生记录。
3)该实体主要由会员登录、注册、发表文章
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:36
 */
@Mapper
public interface MemberDao {

	MemberDO get(Integer memId);
	
	List<MemberDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(MemberDO member);
	
	int update(MemberDO member);
	
	int remove(Integer mem_id);
	
	int batchRemove(Integer[] memIds);

	int memberUpdateCheck(MemberDO member);
}
