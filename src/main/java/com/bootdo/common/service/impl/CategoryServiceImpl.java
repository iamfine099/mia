package com.bootdo.common.service.impl;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bootdo.cms.util.CmsUtils;
import com.bootdo.common.config.Constant;
import com.bootdo.common.dao.CategoryDao;
import com.bootdo.common.domain.CategoryDO;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.service.CategoryService;
import com.bootdo.common.utils.BuildTree;
import com.bootdo.common.utils.CacheUtils;
import com.bootdo.common.utils.DictUtils;
import com.bootdo.common.utils.excel.ImportExcel;

import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;



@Service
public class CategoryServiceImpl extends BaseServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public CategoryDO get(Integer id){
		return categoryDao.get(id);
	}
	
	@Override
	public List<CategoryDO> list(Map<String, Object> map){
		return categoryDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return categoryDao.count(map);
	}
	
	@Override
	public int save(CategoryDO category){
		CmsUtils.removeCache("mainNavList_"+category.getParentId());
		return categoryDao.save(category);
	}
	
	@Override
	public int update(CategoryDO category){
		CmsUtils.removeCache("mainNavList_"+category.getParentId());
		return categoryDao.update(category);
	}
	
	@Override
	public int remove(Integer id){
		CmsUtils.removeCache("mainNavList_"+id);
		return categoryDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		for(Integer id : ids){
			CmsUtils.removeCache("mainNavList_"+id);
		}
		return categoryDao.batchRemove(ids);
	}
	/**
	 * 职位类别导出EXCEL
	 * @param request
	 * @param response
	 * @param out
	 */
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map,
			ServletOutputStream out) {
		TemplateExportParams params = new TemplateExportParams( bootdoConfig.getUploadPath()+"templates/doc/Category.xls");
		List<CategoryDO> list = categoryDao.list(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("maplist", list);
		Workbook workbook = ExcelExportUtil.exportExcel(params,map1);
		try {
			String fileName = "Category"+new Date().getTime()/1000+".xls";
			response.setContentType("application/octet-stream");
            response.setHeader("name", fileName);
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setDateHeader("Expires", 0);
            response.setHeader("Content-disposition","attachment; filename=\""+URLEncoder.encode(fileName, "UTF-8")+ "\"");
			 workbook.write(out);
			 workbook.close();
			 out.flush();
			 out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 导入excel
	 * @param fileName
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	public void importExcel(String fileName, MultipartFile file) throws Exception {
		
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CategoryDO> list = ei.getDataList(CategoryDO.class);
			//
			for (CategoryDO category : list) {
				
				if(category != null) {
					 categoryDao.save( category);
				}
				
			}
	}
	
	@Override
	public Tree<CategoryDO> getTree() {
		List<Tree<CategoryDO>> trees = new ArrayList<Tree<CategoryDO>>();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("notInId", new Integer[]{
				Constant.CATEGORY_ADVERT_BANNER,
				Constant.CATEGORY_MEM_ARTICLE,
				Constant.CATEGORY_RECOMMEND_ARTICLE,
				Constant.CATEGORY_MEM_MIA});
		List<CategoryDO> categorys = categoryDao.list(params);
		for (CategoryDO category : categorys) {
			Tree<CategoryDO> tree = new Tree<CategoryDO>();
			tree.setId(category.getId().toString());
			tree.setParentId(category.getParentId().toString());
			tree.setText(category.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			tree.setState(state);
			tree.setModule(category.getModule());
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<CategoryDO> t = BuildTree.build(trees);
		return t;
	}
	
	@Override
	public boolean checkCategoryHasArticle(Integer id) {
		int result = categoryDao.getCategoryArticleNumber(id);
		return result==0?true:false;
	}

	@Override
	public List<CategoryDO> cmsList() {
		return categoryDao.cmsList();
	}
}
