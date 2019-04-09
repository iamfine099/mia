package com.bootdo.mia.dao;

import com.bootdo.mia.domain.EmailCodeDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 1)用于记录验证码信息。
2)通过前台忘记密码发送验证码产生记录。
3)该实体主要由忘记密码等业
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-22 16:32:06
 */
@Mapper
public interface EmailCodeDao {

	EmailCodeDO get(Integer ecId);
	
	List<EmailCodeDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(EmailCodeDO emailCode);
	
	int update(EmailCodeDO emailCode);
	
	int remove(Integer ec_id);
	
	int batchRemove(Integer[] ecIds);

	int checkVerify(Map<String, Object> map);
}
