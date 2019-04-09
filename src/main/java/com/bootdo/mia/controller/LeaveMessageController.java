package com.bootdo.mia.controller;

import java.sql.SQLException;
import java.util.Date;
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
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.mia.domain.LeaveMessageDO;
import com.bootdo.mia.domain.LeaveMessageTypeDO;
import com.bootdo.mia.service.LeaveMessageService;
import com.bootdo.mia.service.LeaveMessageTypeService;
import com.bootdo.system.domain.UserDO;

/**
 * 1)用于记录留言信息。
2)通过前端用户填写留言产生记录。
3)该实体主要由会员留言、留言管理等
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:35
 */
 
@Controller
@RequestMapping("/mia/leaveMessage")
public class LeaveMessageController {
	//依赖注入
	@Autowired
	private LeaveMessageService leaveMessageService;
	@Autowired
	private LeaveMessageTypeService leaveMessageTypeService ;
	
	//@RequestMapping()
	//@RequiresPermissions("mia:leaveMessage:leaveMessage")
	//String LeaveMessage(){
	    //return "mia/leaveMessage/leaveMessage";
	//}
	
	@RequestMapping()
	@RequiresPermissions("mia:leaveMessage:leaveMessage")
	String LeaveMessage(Model model){
		//查询留言类型
		
		List<LeaveMessageTypeDO> leaveMessageTypeList = leaveMessageTypeService.list(new HashMap<String, Object>());
			model.addAttribute("leaveMessageTypeList",leaveMessageTypeList);
		    return "mia/leaveMessage/leaveMessage";
	}
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("mia:leaveMessage:leaveMessage")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<LeaveMessageDO> leaveMessageList = leaveMessageService.list(query);
		int total = leaveMessageService.count(query);
		PageUtils pageUtils = new PageUtils(leaveMessageList, total);
		return pageUtils;
	}
	
	//@GetMapping("/add")
	//@RequiresPermissions("mia:leaveMessage:add")
	//String add(){
	    //return "mia/leaveMessage/add";
	//}
	@GetMapping("/add")
	@RequiresPermissions("mia:leaveMessage:add")
	String add(Model model){
		//查询留言类型
		
		List<LeaveMessageTypeDO> leaveMessageTypeList = leaveMessageTypeService.list(new HashMap<String, Object>());
			model.addAttribute("leaveMessageTypeList",leaveMessageTypeList);
		    return "mia/leaveMessage/add";
	}

	@GetMapping("/edit/{lmId}")
	@RequiresPermissions("mia:leaveMessage:edit")
	String edit(@PathVariable("lmId") Integer lmId,Model model){
		//查询留言类型
		List<LeaveMessageTypeDO> leaveMessageTypeList = leaveMessageTypeService.list(new HashMap<String, Object>());
		model.addAttribute("leaveMessageTypeList",leaveMessageTypeList);
		//查询留言信息
		LeaveMessageDO leaveMessage = leaveMessageService.get(lmId);
		model.addAttribute("leaveMessage", leaveMessage);
	    return "mia/leaveMessage/edit";
	}
	@GetMapping("/audit/{lmId}")
	@RequiresPermissions("mia:leaveMessage:edit")
	String audit(@PathVariable("lmId") Integer lmId,Model model){
		LeaveMessageDO leaveMessage = leaveMessageService.get(lmId);
		leaveMessage.setAuditDate(new Date());
		UserDO user = ShiroUtils.getUser().getUser();
		leaveMessage.setAuditBy(user.getUserId().intValue());
		model.addAttribute("leaveMessage", leaveMessage);
	    return "mia/leaveMessage/audit";
	}
	@GetMapping("/reply/{lmId}")
	@RequiresPermissions("mia:leaveMessage:edit")
	String reply(@PathVariable("lmId") Integer lmId,Model model){
		LeaveMessageDO leaveMessage = leaveMessageService.get(lmId);
		leaveMessage.setReplyDate(new Date());
		UserDO user = ShiroUtils.getUser().getUser();
		leaveMessage.setReplyBy(user.getUserId().intValue());
		model.addAttribute("leaveMessage", leaveMessage);
	    return "mia/leaveMessage/reply";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("mia:leaveMessage:add")
	public R save( LeaveMessageDO leaveMessage){
		if(leaveMessageService.save(leaveMessage)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("mia:leaveMessage:edit")
	public R update( LeaveMessageDO leaveMessage){
		leaveMessageService.update(leaveMessage);
		return R.ok();
	}
	/**
	 * 审核
	 */
	@ResponseBody
	@RequestMapping("/audit")
	@RequiresPermissions("mia:leaveMessage:edit")
	public R audit( LeaveMessageDO leaveMessage){
		leaveMessage.setAuditDate(new Date());
		UserDO user = ShiroUtils.getUser().getUser();
		leaveMessage.setAuditBy(user.getUserId().intValue());
		leaveMessageService.update(leaveMessage);
		return R.ok();
	}
	/**
	 * 回复
	 */
	@ResponseBody
	@RequestMapping("/reply")
	@RequiresPermissions("mia:leaveMessage:edit")
	public R reply( LeaveMessageDO leaveMessage){
		leaveMessage.setReplyDate(new Date());
		UserDO user = ShiroUtils.getUser().getUser();
		leaveMessage.setReplyBy(user.getUserId().intValue());
		leaveMessageService.update(leaveMessage);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("mia:leaveMessage:remove")
	public R remove( Integer lmId){
		if(leaveMessageService.remove(lmId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("mia:leaveMessage:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] lmIds){
		leaveMessageService.batchRemove(lmIds);
		return R.ok();
	}
	/**
	 * 导出
	 */
	@RequestMapping("/exportExcel")
	@RequiresPermissions("mia:leaveMessage:add")
	public void exportExcel(@RequestParam Map<String, Object> params,HttpServletRequest request, HttpServletResponse response,  ServletOutputStream out){
	
		//查询列表数据
		leaveMessageService.exportExcel(request, response, params, out);
		
		return ;
	}
	@GetMapping("/importView")
	@RequiresPermissions("mia:leaveMessage:add")
	String importView(){
	    return "mia/leaveMessage/importExcel";
	}
	/**
	 * 导入
	 */
	@ResponseBody
	@PostMapping("/importExcel")
	@RequiresPermissions("mia:leaveMessage:add")
	public R importExcel(@RequestParam("file") MultipartFile file) {
	        String fileName = file.getOriginalFilename();
	        try {
	           leaveMessageService.importExcel(fileName, file);
	        } catch (Exception e) {
	        	 return R.error();
	        }
	        return R.ok();
	}
	
	
	

}
