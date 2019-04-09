package com.bootdo.mia.service;

import com.bootdo.common.utils.R;
import com.bootdo.mia.domain.MemberDO;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;
/**
 * 1)用于记录会员信息。
2)通过前端用户注册产生记录。
3)该实体主要由会员登录、注册、发表文章
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:36
 */
public interface MemberService {
	
	MemberDO get(Integer memId);
	
	List<MemberDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MemberDO member);
	
	int update(MemberDO member);
	
	int remove(Integer memId);
	
	int batchRemove(Integer[] memIds);
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
	 * 前端会员注册
	 * @param params
	 * @param session
	 * @return
	 */
	R registerPost(Map<String, Object> params, HttpSession session);

	/**
	 * 审核
	 */
	int memberUpdateCheck(MemberDO member);
	/**
	 * 判断会员是否可以删除
	 */
	boolean checkRemove(Integer memId);
}
