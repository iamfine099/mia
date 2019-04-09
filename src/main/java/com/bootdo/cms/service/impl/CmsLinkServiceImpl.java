package com.bootdo.cms.service.impl;

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

import com.bootdo.cms.dao.CmsLinkDao;
import com.bootdo.cms.domain.CmsLinkDO;
import com.bootdo.cms.service.CmsLinkService;
import com.bootdo.common.utils.excel.ImportExcel;
import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;



@Service
public class CmsLinkServiceImpl implements CmsLinkService {
	@Autowired
	private CmsLinkDao cmsLinkDao;
	
	@Override
	public CmsLinkDO get(Integer fId){
		return cmsLinkDao.get(fId);
	}
	
	@Override
	public List<CmsLinkDO> list(Map<String, Object> map){
		return cmsLinkDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return cmsLinkDao.count(map);
	}
	
	@Override
	public int save(CmsLinkDO cmsLink){
		return cmsLinkDao.save(cmsLink);
	}
	
	@Override
	public int update(CmsLinkDO cmsLink){
		return cmsLinkDao.update(cmsLink);
	}
	
	@Override
	public int remove(Integer fId){
		return cmsLinkDao.remove(fId);
	}
	
	@Override
	public int batchRemove(Integer[] fIds){
		return cmsLinkDao.batchRemove(fIds);
	}
}
