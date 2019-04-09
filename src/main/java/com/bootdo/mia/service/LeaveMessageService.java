package com.bootdo.mia.service;

import com.bootdo.mia.domain.LeaveMessageDO;

import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
/**
 * 1)用于记录留言信息。
2)通过前端用户填写留言产生记录。
3)该实体主要由会员留言、留言管理等
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:35
 */
public interface LeaveMessageService {
	
	LeaveMessageDO get(Integer lmId);
	
	List<LeaveMessageDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(LeaveMessageDO leaveMessage);
	
	int update(LeaveMessageDO leaveMessage);
	
	int remove(Integer lmId);
	
	int batchRemove(Integer[] lmIds);
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
}
