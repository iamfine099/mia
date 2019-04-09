package com.bootdo.mia.service;

import com.bootdo.mia.domain.ExpertDO;

import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
/**
 * 1)用于记录专家信息。
2)通过后台添加产生记录。
3)该实体主要由专家登录、专家管理等业务使用
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:34
 */
public interface ExpertService {
	
	ExpertDO get(Integer expertId);
	
	List<ExpertDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ExpertDO expert);
	
	int update(ExpertDO expert);
	
	int remove(Integer expertId);
	
	int batchRemove(Integer[] expertIds);
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
