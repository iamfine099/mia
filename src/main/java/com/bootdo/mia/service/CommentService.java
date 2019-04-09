package com.bootdo.mia.service;

import com.bootdo.mia.domain.CommentDO;

import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
/**
 * 1)用于记录评论。
2)通过专家对文章评论产生记录。
3)该实体主要由评论审核、评论推荐等业务使
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:33
 */
public interface CommentService {
	
	CommentDO get(Integer cId);
	
	List<CommentDO> list(Map<String, Object> map);
	
	List<CommentDO> recommendlist(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(CommentDO comment);
	
	int update(CommentDO comment);
	
	int remove(Integer cId);
	
	int batchRemove(Integer[] cIds);
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

	int commentAuditUpdateCheck(CommentDO comment);
}
