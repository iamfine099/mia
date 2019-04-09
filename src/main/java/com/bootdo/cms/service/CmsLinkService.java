package com.bootdo.cms.service;

import com.bootdo.cms.domain.CmsLinkDO;

import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
/**
 * 友情链接
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-25 09:53:54
 */
public interface CmsLinkService {
	
	CmsLinkDO get(Integer fId);
	
	List<CmsLinkDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(CmsLinkDO cmsLink);
	
	int update(CmsLinkDO cmsLink);
	
	int remove(Integer fId);
	
	int batchRemove(Integer[] fIds);
}
