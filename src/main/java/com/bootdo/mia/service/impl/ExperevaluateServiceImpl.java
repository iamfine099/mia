package com.bootdo.mia.service.impl;

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

import com.bootdo.mia.dao.ExperevaluateDao;
import com.bootdo.mia.domain.ExperevaluateDO;
import com.bootdo.mia.service.ExperevaluateService;
import com.bootdo.common.utils.excel.ImportExcel;

import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;



@Service
public class ExperevaluateServiceImpl implements ExperevaluateService {
	@Autowired
	private ExperevaluateDao experevaluateDao;
	
	@Override
	public ExperevaluateDO get(Integer eetId){
		return experevaluateDao.get(eetId);
	}
	
	@Override
	public List<ExperevaluateDO> list(Map<String, Object> map){
		return experevaluateDao.list(map);
	}
	
	@Override
	public List<ExperevaluateDO> detailList(Map<String, Object> map){
		return experevaluateDao.detailList(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return experevaluateDao.count(map);
	}
	
	@Override
	public int detailCount(Map<String, Object> map){
		return experevaluateDao.detailCount(map);
	}
	
	@Override
	public int save(ExperevaluateDO experevaluate){
		return experevaluateDao.save(experevaluate);
	}
	
	@Override
	public int update(ExperevaluateDO experevaluate){
		return experevaluateDao.update(experevaluate);
	}
	
	@Override
	public int remove(Integer eetId){
		return experevaluateDao.remove(eetId);
	}
	
	@Override
	public int batchRemove(Integer[] eetIds){
		return experevaluateDao.batchRemove(eetIds);
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
		TemplateExportParams params = new TemplateExportParams("templates/doc/Experevaluate.xls");
		List<ExperevaluateDO> list = experevaluateDao.list(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("maplist", list);
		Workbook workbook = ExcelExportUtil.exportExcel(params,map1);
		try {
			String fileName = "Experevaluate"+new Date().getTime()/1000+".xls";
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
			List<ExperevaluateDO> list = ei.getDataList(ExperevaluateDO.class);
			//
			for (ExperevaluateDO experevaluate : list) {
				
				if(experevaluate != null) {
					 experevaluateDao.save( experevaluate);
				}
				
			}
	}

	@Override
	public int notCompleteCount(Map<String,Object> map) {
		return experevaluateDao.notCompleteCount(map);
	}

	@Override
	public String avgScore(Integer articleId) {
		return experevaluateDao.avgScore(articleId);
	}

	@Override
	public Integer recommendNum(Integer articleId) {
		return experevaluateDao.recommendNum(articleId);
	}

	@Override
	public Integer likeNum(Integer articleId) {
		return experevaluateDao.likeNum(articleId);
	}
}
