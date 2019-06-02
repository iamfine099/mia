package com.bootdo.mia.service.impl;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bootdo.mia.dao.MemberDao;
import com.bootdo.mia.domain.MemberDO;
import com.bootdo.mia.service.MemberService;
import com.bootdo.system.dao.UserDao;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.shiro.LoginUser;
import com.bootdo.common.dao.ArticleDao;
import com.bootdo.common.utils.MD5Utils;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.RandomValidateCodeUtils;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.common.utils.excel.ImportExcel;

import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;



@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ArticleDao articleDao;
	@Override
	public MemberDO get(Integer memId){
		return memberDao.get(memId);
	}
	
	@Override
	public List<MemberDO> list(Map<String, Object> map){
		return memberDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return memberDao.count(map);
	}
	
	@Override
	public int save(MemberDO member){
		return memberDao.save(member);
	}
	
	@Override
	public int update(MemberDO member){

		LoginUser loginUser = ShiroUtils.getUser();
		member.setAuditBy(Integer.parseInt(loginUser.getUser().getUserId().toString()));
		member.setAuditDate(new Date());
		return memberDao.update(member);
	}
	
	@Transactional
	@Override
	public int remove(Integer memId){
		return memberDao.remove(memId);
	}
	/**
	 * 判断会员是否可以删除
	 */
	public  boolean checkRemove(Integer memId) {
		//判断会员是否发表的有文章
		//根据memId查询userId
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("busId",memId );
		param.put("fType","M" );
		UserDO user = userDao.getByParam(param);
		param.clear();
		param.put("createBy",user.getUserId() );
		param.put("status", "1");
		int count = articleDao.count(param);
		if(count>0) {
			return false;
		}
		return true;
		
	}
	@Override
	public int batchRemove(Integer[] memIds){
		return memberDao.batchRemove(memIds);
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
		TemplateExportParams params = new TemplateExportParams("templates/doc/Member.xls");
		List<MemberDO> list = memberDao.list(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("maplist", list);
		Workbook workbook = ExcelExportUtil.exportExcel(params,map1);
		try {
			String fileName = "Member"+new Date().getTime()/1000+".xls";
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
	 * @throws Exception
	 */
	public void importExcel(String fileName, MultipartFile file) throws Exception {
		
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MemberDO> list = ei.getDataList(MemberDO.class);
			//
			for (MemberDO member : list) {
				
				if(member != null) {
					 memberDao.save( member);
				}
				
			}
	}

	/**
	 * 会员注册
	 *
	 * @param params
	 * @param session
	 * @return
	 */
	@Override
	public R registerPost(Map<String, Object> params, HttpSession session) {

		//判断用户是否存在
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", params.get("phone"));
		if(userDao.count(map)>0){
			return R.error("手机号已被注册");
		}else{
			//验证码是否正确
			R r = RandomValidateCodeUtils.checkVerify(params.get("code").toString(), session);
			if("500".equals(r.get("code").toString())){
				return r;
			}else{
				//保存用户信息
				MemberDO member = new MemberDO();
				member.setMemName(params.get("uName").toString());
				member.setPhone(params.get("phone").toString());
				member.setEmail(params.get("email").toString());
				//member.setSpecialty(params.get("specialty").toString());
				member.setSpecialty(params.get("sp_id").toString());
				member.setStatus("1"); //状态：0正常 1禁用
				member.setCreateDate(new Date());
				member.setLastTime(new Date());
				member.setCompany(params.get("job").toString());
				member.setSex(params.get("sex").toString());

				if(memberDao.save(member) > 0){
					UserDO user = new UserDO();
					user.setUsername(params.get("phone").toString());
					user.setName(params.get("uName").toString());
					user.setPassword(MD5Utils.encrypt(params.get("phone").toString().trim(),params.get("pwd").toString()));
					user.setStatus(0); // 状态 0:禁用 1:正常
					user.setGmtCreate(member.getCreateDate());
					user.setfType("M");
					user.setBusId(member.getMemId().toString());
					if(userDao.save(user) > 0){
						return R.ok("注册成功");
					}else{
						return R.error("注册失败");
					}
				}else{
					return R.error("保存会员信息失败");
				}
			}
		}
	}

	@Override
	public int memberUpdateCheck(MemberDO member) {

		LoginUser loginUser = ShiroUtils.getUser();
		if("1".equals(member.getAuditStatus())){
			//通过
			member.setStatus("0");
		}else{
			member.setStatus("1");
		}
		member.setAuditBy(Integer.parseInt(loginUser.getUser().getUserId().toString()));
		if(memberDao.memberUpdateCheck(member) > 0){
			//修改sys_user表信息
			UserDO user = new UserDO();
			user.setBusIds(member.getIds());
			user.setfType("M");
			if("1".equals(member.getAuditStatus())){
				//通过
				user.setStatus(1);
			}else{
				user.setStatus(0);
			}
			return userDao.batchCheck(user);
		}else{
			return 0;
		}
	}
}
