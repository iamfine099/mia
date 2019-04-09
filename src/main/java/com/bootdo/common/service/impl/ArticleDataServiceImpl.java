package com.bootdo.common.service.impl;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bootdo.common.dao.ArticleDataDao;
import com.bootdo.common.domain.ArticleDataDO;
import com.bootdo.common.service.ArticleDataService;
import com.bootdo.common.utils.excel.ImportExcel;

import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;



@Service
public class ArticleDataServiceImpl implements ArticleDataService {
	@Autowired
	private ArticleDataDao articleDataDao;
	
	@Override
	public ArticleDataDO get(Integer id){
		return articleDataDao.get(id);
	}
	
	@Override
	public List<ArticleDataDO> list(Map<String, Object> map){
		return articleDataDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return articleDataDao.count(map);
	}
	
	@Override
	public int save(ArticleDataDO articleData){
		return articleDataDao.save(articleData);
	}
	
	@Override
	public int update(ArticleDataDO articleData){
		return articleDataDao.update(articleData);
	}
	
	@Override
	public int remove(Integer id){
		return articleDataDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return articleDataDao.batchRemove(ids);
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
		TemplateExportParams params = new TemplateExportParams("templates/doc/ArticleData.xls");
		List<ArticleDataDO> list = articleDataDao.list(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("maplist", list);
		Workbook workbook = ExcelExportUtil.exportExcel(params,map1);
		try {
			String fileName = "ArticleData"+new Date().getTime()/1000+".xls";
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
			List<ArticleDataDO> list = ei.getDataList(ArticleDataDO.class);
			//
			for (ArticleDataDO articleData : list) {
				
				if(articleData != null) {
					 articleDataDao.save( articleData);
				}
				
			}
	}
}
