package com.bootdo.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bootdo.system.domain.StuVectorDO;

/**
 * 学生向量表
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-08-22 09:15:15
 */
@Mapper
public interface StuVectorDao {

	StuVectorDO get(Integer fId);
	
	List<StuVectorDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(StuVectorDO stuVector);
	
	int update(StuVectorDO stuVector);
	
	int remove(Integer f_id);
	
	int batchRemove(Integer[] fIds);

	/**
	 * 查询学生信息-服务于学生向量生成
	 * @param param
	 * @return
	 */
	List<StuVectorDO> getStudentByYear(Map<String, Object> param);
	/**
	 * 查询学生信息-服务于学生向量生成 删除以往历史记录
	 * @param param
	 * @return
	 */
	void deleteStuVector(int curYear);

	List<StuVectorDO> flist(Map<String, Object> params);

	int fListCount(Map<String, Object> params);
}
