package com.bootdo.mia.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bootdo.mia.domain.EmailCodeDO;
import com.bootdo.mia.service.EmailCodeService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 1)用于记录验证码信息。
2)通过前台忘记密码发送验证码产生记录。
3)该实体主要由忘记密码等业
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-22 16:32:06
 */
 
@Controller
@RequestMapping("/mia/emailCode")
public class EmailCodeController {
	@Autowired
	private EmailCodeService emailCodeService;
	
	@RequestMapping()
	@RequiresPermissions("mia:emailCode:emailCode")
	String EmailCode(){
	    return "mia/emailCode/emailCode";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("mia:emailCode:emailCode")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<EmailCodeDO> emailCodeList = emailCodeService.list(query);
		int total = emailCodeService.count(query);
		PageUtils pageUtils = new PageUtils(emailCodeList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("mia:emailCode:add")
	String add(){
	    return "mia/emailCode/add";
	}

	@GetMapping("/edit/{ecId}")
	@RequiresPermissions("mia:emailCode:edit")
	String edit(@PathVariable("ecId") Integer ecId,Model model){
		EmailCodeDO emailCode = emailCodeService.get(ecId);
		model.addAttribute("emailCode", emailCode);
	    return "mia/emailCode/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("mia:emailCode:add")
	public R save( EmailCodeDO emailCode){
		if(emailCodeService.save(emailCode)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("mia:emailCode:edit")
	public R update( EmailCodeDO emailCode){
		emailCodeService.update(emailCode);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("mia:emailCode:remove")
	public R remove( Integer ecId){
		if(emailCodeService.remove(ecId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("mia:emailCode:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ecIds){
		emailCodeService.batchRemove(ecIds);
		return R.ok();
	}
	/**
	 * 导出
	 */
	@RequestMapping("/exportExcel")
	@RequiresPermissions("mia:emailCode:add")
	public void exportExcel(@RequestParam Map<String, Object> params,HttpServletRequest request, HttpServletResponse response,  ServletOutputStream out){
	
		//查询列表数据
		emailCodeService.exportExcel(request, response, params, out);
		
		return ;
	}
	@GetMapping("/importView")
	@RequiresPermissions("mia:emailCode:add")
	String importView(){
	    return "mia/emailCode/importExcel";
	}
	/**
	 * 导入
	 */
	@ResponseBody
	@PostMapping("/importExcel")
	@RequiresPermissions("mia:emailCode:add")
	public R importExcel(@RequestParam("file") MultipartFile file) {
	        String fileName = file.getOriginalFilename();
	        try {
	           emailCodeService.importExcel(fileName, file);
	        } catch (Exception e) {
	        	 return R.error();
	        }
	        return R.ok();
	}
}
