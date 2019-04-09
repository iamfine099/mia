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

import com.bootdo.mia.dao.LeaveMessageDao;
import com.bootdo.mia.domain.LeaveMessageDO;
import com.bootdo.mia.service.LeaveMessageService;
import com.bootdo.common.utils.excel.ImportExcel;

import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;



@Service
public class LeaveMessageServiceImpl implements LeaveMessageService {
	@Autowired
	private LeaveMessageDao leaveMessageDao;
	
	@Override
	public LeaveMessageDO get(Integer lmId){
		return leaveMessageDao.get(lmId);
	}
	
	@Override
	public List<LeaveMessageDO> list(Map<String, Object> map){
		return leaveMessageDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return leaveMessageDao.count(map);
	}
	
	@Override
	public int save(LeaveMessageDO leaveMessage){
		return leaveMessageDao.save(leaveMessage);
	}
	
	@Override
	public int update(LeaveMessageDO leaveMessage){
		return leaveMessageDao.update(leaveMessage);
	}
	
	@Override
	public int remove(Integer lmId){
		return leaveMessageDao.remove(lmId);
	}
	
	@Override
	public int batchRemove(Integer[] lmIds){
		return leaveMessageDao.batchRemove(lmIds);
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
		TemplateExportParams params = new TemplateExportParams("templates/doc/LeaveMessage.xls");
		List<LeaveMessageDO> list = leaveMessageDao.list(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("maplist", list);
		Workbook workbook = ExcelExportUtil.exportExcel(params,map1);
		try {
			String fileName = "LeaveMessage"+new Date().getTime()/1000+".xls";
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
			List<LeaveMessageDO> list = ei.getDataList(LeaveMessageDO.class);
			//
			for (LeaveMessageDO leaveMessage : list) {
				
				if(leaveMessage != null) {
					 leaveMessageDao.save( leaveMessage);
				}
				
			}
	}
}
