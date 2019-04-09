package com.bootdo.common.service;

import com.bootdo.common.domain.MessageReceiverDO;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
/**
 * 对于群发消息，本表记录消息接收方信息；对于一对一的消息，消息接收方表中对应一条记录。
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-28 17:10:17
 */
public interface MessageReceiverService {
	
	MessageReceiverDO get(Integer fId);
	
	List<MessageReceiverDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MessageReceiverDO messageReceiver);
	
	int update(MessageReceiverDO messageReceiver);
	
	int remove(Integer fId);
	
	int batchRemove(Integer[] fIds);
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

	int removeForMsgId(Integer fMsgId);

	int batchRemoveForMsgIds(Integer[] fMsgIds);
}
