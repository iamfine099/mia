package com.bootdo.mia.dao;

import com.bootdo.mia.domain.LeaveMessageTypeDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 留言类型管理
 */
@Mapper
public interface LeaveMessageTypeDao {

	LeaveMessageTypeDO get(Integer mtId);
	
	List<LeaveMessageTypeDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(LeaveMessageTypeDO leaveMessageType);
	
	int update(LeaveMessageTypeDO leaveMessageType);
	
	int remove(Integer mtId);
	
	int batchRemove(Integer[] mtIds);
}
