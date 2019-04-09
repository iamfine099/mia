package com.bootdo.mia.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.mia.domain.LeaveMessageTypeDO;
import com.bootdo.mia.service.LeaveMessageTypeService;

/**
 * 此页用于留言类型的管理
 */
 
@Controller
@RequestMapping("/mia/leaveMessageType")
public class LeaveMessageTypeController {
	//依赖注入
	@Autowired
	private LeaveMessageTypeService leaveMessageTypeService ;
	
	//@RequestMapping()
	//@RequiresPermissions("mia:leaveMessage:leaveMessage")
	//String LeaveMessage(){
	    //return "mia/leaveMessage/leaveMessage";
	//}
	
	@RequestMapping()
	@RequiresPermissions("mia:leaveMessageType:leaveMessageType")
	String LeaveMessageType(Model model){
		//查询留言类型
		
		List<LeaveMessageTypeDO> leaveMessageTypeList = leaveMessageTypeService.list(new HashMap<String, Object>());
			model.addAttribute("leaveMessageTypeList",leaveMessageTypeList);
		    return "mia/leaveMessageType/leaveMessageType";
	}
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("mia:leaveMessageType:leaveMessageType")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<LeaveMessageTypeDO> leaveMessageTypeList = leaveMessageTypeService.list(query);
		int total = leaveMessageTypeService.count(query);
		PageUtils pageUtils = new PageUtils(leaveMessageTypeList, total);
		return pageUtils;
	}
	
	//@GetMapping("/add")
	//@RequiresPermissions("mia:leaveMessage:add")
	//String add(){
	    //return "mia/leaveMessage/add";
	//}
	@GetMapping("/add")
	@RequiresPermissions("mia:leaveMessageType:add")
	String add(Model model){
		    return "mia/leaveMessageType/add";
	}

	@GetMapping("/edit/{mtId}")
	@RequiresPermissions("mia:leaveMessageType:edit")
	String edit(@PathVariable("mtId") Integer mtId,Model model){
		//查询留言类型
		LeaveMessageTypeDO leaveMessageType = leaveMessageTypeService.get(mtId);
		model.addAttribute("leaveMessageType", leaveMessageType);
	    return "mia/leaveMessageType/edit";
	}
	
	
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("mia:leaveMessageType:add")
	public R save( LeaveMessageTypeDO leaveMessageType){
		if(leaveMessageTypeService.save(leaveMessageType)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("mia:leaveMessageType:edit")
	public R update( LeaveMessageTypeDO leaveMessageType){
		leaveMessageTypeService.update(leaveMessageType);
		return R.ok();
	}
	
	
	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("mia:leaveMessageType:remove")
	public R remove(Integer mtId){
		if(leaveMessageTypeService.remove(mtId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("mia:leaveMessageType:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] mtIds){
		leaveMessageTypeService.batchRemove(mtIds);
		return R.ok();
	}
	/**
	 * 导出
	 */
	@RequestMapping("/exportExcel")
	@RequiresPermissions("mia:leaveMessageType:add")
	public void exportExcel(@RequestParam Map<String, Object> params,HttpServletRequest request, HttpServletResponse response,  ServletOutputStream out){
	
		//查询列表数据
		leaveMessageTypeService.exportExcel(request, response, params, out);
		
		return ;
	}
	@GetMapping("/importView")
	@RequiresPermissions("mia:leaveMessageType:add")
	String importView(){
	    return "mia/leaveMessageType/importExcel";
	}
	/**
	 * 导入
	 */
	@ResponseBody
	@PostMapping("/importExcel")
	@RequiresPermissions("mia:leaveMessageType:add")
	public R importExcel(@RequestParam("file") MultipartFile file) {
	        String fileName = file.getOriginalFilename();
	        try {
	           leaveMessageTypeService.importExcel(fileName, file);
	        } catch (Exception e) {
	        	 return R.error();
	        }
	        return R.ok();
	}
	
	
	

}
