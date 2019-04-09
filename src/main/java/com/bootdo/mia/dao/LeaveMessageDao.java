package com.bootdo.mia.dao;

import com.bootdo.mia.domain.LeaveMessageDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 1)用于记录留言信息。
2)通过前端用户填写留言产生记录。
3)该实体主要由会员留言、留言管理等
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:35
 */
@Mapper
public interface LeaveMessageDao {

	LeaveMessageDO get(Integer lmId);
	
	List<LeaveMessageDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(LeaveMessageDO leaveMessage);
	
	int update(LeaveMessageDO leaveMessage);
	
	int remove(Integer lm_id);
	
	int batchRemove(Integer[] lmIds);
}
