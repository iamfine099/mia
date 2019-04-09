package com.bootdo.common.service;

import com.bootdo.common.domain.ArticleDataDO;

import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
/**
 * 文章数据表
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:31
 */
public interface ArticleDataService {
	
	ArticleDataDO get(Integer id);
	
	List<ArticleDataDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ArticleDataDO articleData);
	
	int update(ArticleDataDO articleData);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @param map
	 * @param out
	 */
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map,
			ServletOutputStream out);
	/**
	 * 导入excel
	 * @param fileName
	 * @param file
	 * @return
	 */
	public void importExcel(String fileName, MultipartFile file)throws Exception;
}
