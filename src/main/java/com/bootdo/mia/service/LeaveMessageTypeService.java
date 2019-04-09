package com.bootdo.mia.service;

import com.bootdo.mia.domain.LeaveMessageTypeDO;

import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
/**
 * 留言类型管理
 */
public interface LeaveMessageTypeService {
	
	LeaveMessageTypeDO get(Integer mtId);
	
	List<LeaveMessageTypeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(LeaveMessageTypeDO leaveMessageType);
	
	int update(LeaveMessageTypeDO leaveMessageType);
	
	int remove(Integer mtId);
	
	int batchRemove(Integer[] mtIds);
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
