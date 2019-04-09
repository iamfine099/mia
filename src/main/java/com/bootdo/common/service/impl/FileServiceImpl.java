package com.bootdo.common.service.impl;

import com.bootdo.common.config.BootdoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bootdo.common.dao.FileDao;
import com.bootdo.common.domain.FileDO;
import com.bootdo.common.service.FileService;
import com.bootdo.common.utils.FileUtil;

import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;


@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private FileDao sysFileMapper;

	@Autowired
	private BootdoConfig bootdoConfig;
	@Override
	public FileDO get(Long id){
		return sysFileMapper.get(id);
	}
	
	@Override
	public List<FileDO> list(Map<String, Object> map){
		return sysFileMapper.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return sysFileMapper.count(map);
	}
	
	@Override
	public int save(FileDO sysFile){
		return sysFileMapper.save(sysFile);
	}
	
	@Override
	public int update(FileDO sysFile){
		return sysFileMapper.update(sysFile);
	}
	
	@Override
	public int remove(Long id){
		return sysFileMapper.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return sysFileMapper.batchRemove(ids);
	}

    @Override
    public Boolean isExist(String url) {
		Boolean isExist = false;
		if (!StringUtils.isEmpty(url)) {
			String filePath = url.replace("/files/", "");
			filePath = bootdoConfig.getUploadPath() + filePath;
			File file = new File(filePath);
			if (file.exists()) {
				isExist = true;
			}
		}
		return isExist;
	}
    /**
     * 下载附件
     */
	@Override
	public void down(HttpServletRequest request, HttpServletResponse response, Long id, ServletOutputStream out) {
		String fileName = bootdoConfig.getUploadPath() + sysFileMapper.get(id).getUrl().replace("/files/", "");
		FileUtil.downFile(new File(fileName), request, response);
	}
	/**
     * 下载EXCEL模板
	 * @throws FileNotFoundException 
     */
	@Override
	public void downExcelTemplate(HttpServletRequest request, HttpServletResponse response, String templateName,
			ServletOutputStream out)  {
		try {
			File path = new File(ResourceUtils.getURL("classpath:").getPath());
			String fileName =  bootdoConfig.getUploadPath()+"templates/exceltemplate/"+templateName+".xls";
			//String fileName = "templates/exceltemplate/"+templateName+".xls";
			FileUtil.downFile(new File(fileName), request, response);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
    
    
}
