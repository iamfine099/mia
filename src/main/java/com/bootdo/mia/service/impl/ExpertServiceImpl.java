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

import com.bootdo.mia.dao.ExpertDao;
import com.bootdo.mia.domain.ExpertDO;
import com.bootdo.mia.service.ExpertService;
import com.bootdo.system.dao.UserDao;
import com.bootdo.system.domain.UserDO;
import com.bootdo.common.utils.MD5Utils;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.common.utils.excel.ImportExcel;

import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;



@Service
public class ExpertServiceImpl implements ExpertService {
	@Autowired
	private ExpertDao expertDao;
	@Autowired
	private UserDao userDao;
	
	@Override
	public ExpertDO get(Integer expertId){
		return expertDao.get(expertId);
	}
	
	@Override
	public List<ExpertDO> list(Map<String, Object> map){
		return expertDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return expertDao.count(map);
	}
	
	@Override
	public int save(ExpertDO expert){
		if(expertDao.save(expert) > 0){
			UserDO user = new UserDO();
			user.setUsername(expert.getPhone());
			user.setName(expert.getExpertName());
			if(StringUtils.isNotEmpty(expert.getPassword())){
				user.setPassword(MD5Utils.encrypt(expert.getPhone().trim(),expert.getPassword()));
			}else{
				user.setPassword(MD5Utils.encrypt(expert.getPhone().trim(),"123456"));
			}
			user.setStatus(1);
			user.setGmtCreate(new Date());
			user.setfType("E");
			user.setBusId(expert.getExpertId().toString());
			return userDao.save(user);
		}
		return 0;
	}
	
	@Override
	public int update(ExpertDO expert){
		return expertDao.update(expert);
	}
	
	@Override
	public int remove(Integer expertId){
		return expertDao.remove(expertId);
	}
	
	@Override
	public int batchRemove(Integer[] expertIds){
		return expertDao.batchRemove(expertIds);
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
		TemplateExportParams params = new TemplateExportParams("templates/doc/Expert.xls");
		List<ExpertDO> list = expertDao.list(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("maplist", list);
		Workbook workbook = ExcelExportUtil.exportExcel(params,map1);
		try {
			String fileName = "Expert"+new Date().getTime()/1000+".xls";
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
			List<ExpertDO> list = ei.getDataList(ExpertDO.class);
			//
			for (ExpertDO expert : list) {
				
				if(expert != null) {
					 expertDao.save( expert);
				}
				
			}
	}
}
