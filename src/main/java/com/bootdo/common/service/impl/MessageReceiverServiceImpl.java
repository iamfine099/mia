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

import com.bootdo.common.dao.MessageReceiverDao;
import com.bootdo.common.domain.MessageReceiverDO;
import com.bootdo.common.service.MessageReceiverService;
import com.bootdo.common.utils.excel.ImportExcel;

import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;



@Service
public class MessageReceiverServiceImpl extends BaseServiceImpl implements MessageReceiverService {
	@Autowired
	private MessageReceiverDao messageReceiverDao;
	
	@Override
	public MessageReceiverDO get(Integer fId){
		return messageReceiverDao.get(fId);
	}
	
	@Override
	public List<MessageReceiverDO> list(Map<String, Object> map){
		return messageReceiverDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return messageReceiverDao.count(map);
	}
	
	@Override
	public int save(MessageReceiverDO messageReceiver){
		return messageReceiverDao.save(messageReceiver);
	}
	
	@Override
	public int update(MessageReceiverDO messageReceiver){
		return messageReceiverDao.update(messageReceiver);
	}
	
	@Override
	public int remove(Integer fId){
		return messageReceiverDao.remove(fId);
	}
	
	@Override
	public int batchRemove(Integer[] fIds){
		return messageReceiverDao.batchRemove(fIds);
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
		TemplateExportParams params = new TemplateExportParams( bootdoConfig.getUploadPath()+"templates/doc/MessageReceiver.xls");
		List<MessageReceiverDO> list = messageReceiverDao.list(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("maplist", list);
		Workbook workbook = ExcelExportUtil.exportExcel(params,map1);
		try {
			String fileName = "MessageReceiver"+new Date().getTime()/1000+".xls";
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
			List<MessageReceiverDO> list = ei.getDataList(MessageReceiverDO.class);
			//
			for (MessageReceiverDO messageReceiver : list) {
				
				if(messageReceiver != null) {
					 messageReceiverDao.save( messageReceiver);
				}
				
			}
	}

	@Override
	public int removeForMsgId(Integer fMsgId) {
		return messageReceiverDao.removeForMsgId(fMsgId);
	}

	@Override
	public int batchRemoveForMsgIds(Integer[] fMsgIds) {
		return messageReceiverDao.batchRemoveForMsgIds(fMsgIds);
	}
}
