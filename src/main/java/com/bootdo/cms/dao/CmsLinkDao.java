package com.bootdo.cms.dao;

import com.bootdo.cms.domain.CmsLinkDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 友情链接
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-25 09:53:54
 */
@Mapper
public interface CmsLinkDao {

	CmsLinkDO get(Integer fId);
	
	List<CmsLinkDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(CmsLinkDO cmsLink);
	
	int update(CmsLinkDO cmsLink);
	
	int remove(Integer f_id);
	
	int batchRemove(Integer[] fIds);
}
