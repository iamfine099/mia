package com.bootdo.system.dao;

import com.bootdo.system.domain.UserDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 09:45:11
 */
@Mapper
public interface UserDao {

	UserDO get(Long userId);
	/**
	 * 根据参数查询user对象
	 * @param param
	 * @return
	 */
	UserDO getByParam(Map<String, Object> param);
	
	List<UserDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(UserDO user);
	
	int update(UserDO user);
	
	int remove(Long userId);
	
	int batchRemove(Long[] userIds);
	
	Long[] listAllDept();

	int batchCheck(UserDO user);

	void updateOpenid(String openid);
	
	int only(Map<String, Object> map);
	int removeByParam(Map<String, Object> param);
	int batchUpdateStatus();
	UserDO getIdType(Map<String, Object> param);

}
