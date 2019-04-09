package com.bootdo.mia.service;

import com.bootdo.mia.domain.EmailCodeDO;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
/**
 * 1)用于记录验证码信息。
2)通过前台忘记密码发送验证码产生记录。
3)该实体主要由忘记密码等业
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-22 16:32:06
 */
public interface EmailCodeService {
	
	EmailCodeDO get(Integer ecId);
	
	List<EmailCodeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(EmailCodeDO emailCode);
	
	int update(EmailCodeDO emailCode);
	
	int remove(Integer ecId);
	
	int batchRemove(Integer[] ecIds);
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

	/**
	 * 校验验证码
	 * @param map
	 * @return
	 */
	int checkVerify(Map<String, Object> map);
}
