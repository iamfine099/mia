package com.bootdo.common.service;

import com.bootdo.common.domain.CategoryDO;
import com.bootdo.common.domain.Tree;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
/**
 * 栏目表
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-03 13:58:50
 */
public interface CategoryService {
	
	CategoryDO get(Integer id);
	
	List<CategoryDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(CategoryDO category);
	
	int update(CategoryDO category);
	
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

	boolean checkCategoryHasArticle(Integer id);

	Tree<CategoryDO> getTree();

	List<CategoryDO> cmsList();
}
