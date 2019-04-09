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

import com.bootdo.mia.dao.CommentDao;
import com.bootdo.mia.domain.CommentDO;
import com.bootdo.mia.service.CommentService;
import com.bootdo.system.shiro.LoginUser;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.common.utils.excel.ImportExcel;

import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;



@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDao commentDao;
	
	@Override
	public CommentDO get(Integer cId){
		return commentDao.get(cId);
	}
	
	@Override
	public List<CommentDO> list(Map<String, Object> map){
		return commentDao.list(map);
	}
	
	@Override
	public List<CommentDO> recommendlist(Map<String, Object> map){
		return commentDao.recommendlist(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return commentDao.count(map);
	}
	
	@Override
	public int save(CommentDO comment){
		return commentDao.save(comment);
	}
	
	@Override
	public int update(CommentDO comment){
		return commentDao.update(comment);
	}
	
	@Override
	public int remove(Integer cId){
		return commentDao.remove(cId);
	}
	
	@Override
	public int batchRemove(Integer[] cIds){
		return commentDao.batchRemove(cIds);
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
		TemplateExportParams params = new TemplateExportParams("templates/doc/Comment.xls");
		List<CommentDO> list = commentDao.list(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("maplist", list);
		Workbook workbook = ExcelExportUtil.exportExcel(params,map1);
		try {
			String fileName = "Comment"+new Date().getTime()/1000+".xls";
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
			List<CommentDO> list = ei.getDataList(CommentDO.class);
			//
			for (CommentDO comment : list) {
				
				if(comment != null) {
					 commentDao.save( comment);
				}
				
			}
	}

	@Override
	public int commentAuditUpdateCheck(CommentDO comment) {
		LoginUser loginUser = ShiroUtils.getUser();
		comment.setAuditBy(Integer.parseInt(loginUser.getUser().getUserId().toString()));
		return commentDao.commentAuditUpdateCheck(comment);
	}
}
