package com.bootdo.mia.service;

import com.bootdo.mia.domain.ExperevaluateDO;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
/**
 * 1)用于记录专家对文章的推荐和评分。
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:35
 */
public interface ExperevaluateService {
	
	ExperevaluateDO get(Integer eetId);
	
	List<ExperevaluateDO> list(Map<String, Object> map);
	
	List<ExperevaluateDO> detailList(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int detailCount(Map<String, Object> map);
	
	int save(ExperevaluateDO experevaluate);
	
	int update(ExperevaluateDO experevaluate);
	
	int remove(Integer eetId);
	
	int batchRemove(Integer[] eetIds);
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

	/**
	 * 
	 * @param id
	 * @return
	 */
	int notCompleteCount(Map<String, Object> map);

	String avgScore(Integer articleId);

	Integer recommendNum(Integer articleId);

	Integer likeNum(Integer articleId);

	int deleteByArticleId(Integer articleId);

}
