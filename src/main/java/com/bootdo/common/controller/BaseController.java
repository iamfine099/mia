package com.bootdo.common.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.bootdo.common.beanvalidator.BeanValidators;
import com.bootdo.common.config.BootdoConfig;
import com.bootdo.common.domain.FileDO;
import com.bootdo.common.service.FileService;
import com.bootdo.common.utils.FileType;
import com.bootdo.common.utils.FileUtil;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.shiro.LoginUser;

@Controller
public class BaseController {
	
	@Autowired
	protected FileService sysFileService; //文件上传service

	@Autowired
	private BootdoConfig bootdoConfig; //boot配置文件
	
	public LoginUser getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
	
	/**
	 * 上传附件公共方法
	 * @param file
	 * @param request
	 * @return
	 */
	public FileDO uploadFile( MultipartFile file, HttpServletRequest request) {
		
		String fileOldName = file.getOriginalFilename();
		if(StringUtils.isEmpty(fileOldName)) {
			return null;
		}
		String fileName = FileUtil.renameToUUID(fileOldName);
		FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date(),fileOldName);
		try {
			FileUtil.uploadFile(file.getBytes(), bootdoConfig.getUploadPath(), fileName);
		} catch (Exception e) {
			return null;
		}
		if(sysFileService.save(sysFile)>0) {
			return sysFile;
		}else {
			return null;
		}
	}
	
	/**
	 * 上传多个附件公共方法
	 * @param files
	 * @param request
	 * @return
	 */
	public String uploadFiles( MultipartFile[] files, HttpServletRequest request) {
		String fileId = "";
		for(MultipartFile file : files){
			String fileOldName = file.getOriginalFilename();
			if(StringUtils.isEmpty(fileOldName)) {
				continue;
			}
			String fileName = FileUtil.renameToUUID(fileOldName);
			FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date(),fileOldName);
			try {
				FileUtil.uploadFile(file.getBytes(), bootdoConfig.getUploadPath(), fileName);
			} catch (Exception e) {
				continue;
			}
			if(sysFileService.save(sysFile)>0) {
				fileId += sysFile.getId()+",";
			}else {
				continue;
			}
		}
		if(StringUtils.isNotEmpty(fileId)){
			fileId = fileId.substring(0,fileId.length()-1);
		}
		return fileId;
	}
	
	/**
	 * 上传多个附件ajax
	 * @param files
	 * @return
	 */
	public List<FileDO> uploadFilesAjax(MultipartFile[] files) {
		List<FileDO> fileList = new ArrayList<FileDO>();
		for(MultipartFile file : files){
			String fileOldName = file.getOriginalFilename();
			if(StringUtils.isEmpty(fileOldName)) {
				continue;
			}
			String fileName = FileUtil.renameToUUID(fileOldName);
			FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date(),fileOldName);
			try {
				FileUtil.uploadFile(file.getBytes(), bootdoConfig.getUploadPath(), fileName);
				fileList.add(sysFile);
			} catch (Exception e) {
				continue;
			}
		}
		return fileList;
	}
	
	/**
	 * 删除上传附件ajax
	 * @param url
	 */
	public  void deleteFileAjax(String url) {
		String fileName = bootdoConfig.getUploadPath() + url.replace("/files/", "");
		FileUtil.deleteFile(fileName);
	}
	
	/**
	 * 删除上传附件
	 * @param fileId
	 * @return
	 */
	public  void deleteFile(Long fileId) {
		if(fileId == null || fileId < 1) return ;
		FileDO fileDO = sysFileService.get(fileId);
		if(fileDO != null) {
			String fileName = bootdoConfig.getUploadPath() + fileDO.getUrl().replace("/files/", "");
			if (sysFileService.remove(fileId) > 0) {
				FileUtil.deleteFile(fileName);
			}
		}
	}

	/**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;

	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
	 */
	protected String beanValidatorDO(Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			StringBuilder message = addMessage(list.toArray(new String[]{}));
			return message.toString();
		}
		return null;
	}
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组，不传入此参数时，同@Valid注解验证
	 * @return 验证成功：继续执行；验证失败：抛出异常跳转400页面。
	 */
	protected void beanValidator(Object object, Class<?>... groups) {
		BeanValidators.validateWithException(validator, object, groups);
	}
	
	/**
	 * 添加Model消息
	 * @param message
	 */
	protected StringBuilder addMessage( String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message.split(":")[1]).append(messages.length>1?"<br/>":"");
		}
		
		return sb;
	}
	
	
}