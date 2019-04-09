package com.bootdo.common.controller;

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

import com.bootdo.common.domain.MessageReceiverDO;
import com.bootdo.common.service.MessageReceiverService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;

/**
 * 对于群发消息，本表记录消息接收方信息；对于一对一的消息，消息接收方表中对应一条记录。
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-28 17:10:17
 */
 
@Controller
@RequestMapping("/common/messageReceiver")
public class MessageReceiverController {
	@Autowired
	private MessageReceiverService messageReceiverService;
	
	@RequestMapping()
	@RequiresPermissions("common:messageReceiver:messageReceiver")
	String MessageReceiver(String fMsgId,Model model){
		model.addAttribute("fMsgId",fMsgId);
	    return "common/messageReceiver/messageReceiver";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("common:messageReceiver:messageReceiver")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<MessageReceiverDO> messageReceiverList = messageReceiverService.list(query);
		int total = messageReceiverService.count(query);
		PageUtils pageUtils = new PageUtils(messageReceiverList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("common:messageReceiver:add")
	String add(){
	    return "common/messageReceiver/add";
	}

	@GetMapping("/edit/{fId}")
	@RequiresPermissions("common:messageReceiver:edit")
	String edit(@PathVariable("fId") Integer fId,Model model){
		MessageReceiverDO messageReceiver = messageReceiverService.get(fId);
		model.addAttribute("messageReceiver", messageReceiver);
	    return "common/messageReceiver/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("common:messageReceiver:add")
	public R save( MessageReceiverDO messageReceiver){
		if(messageReceiverService.save(messageReceiver)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("common:messageReceiver:edit")
	public R update( MessageReceiverDO messageReceiver){
		messageReceiverService.update(messageReceiver);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("common:messageReceiver:remove")
	public R remove( Integer fId){
		if(messageReceiverService.remove(fId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("common:messageReceiver:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] fIds){
		messageReceiverService.batchRemove(fIds);
		return R.ok();
	}
	
	/***************前端页面调用开始*******************/
	/**
	 * 前端列表查询
	 * @param params
	 * @return
	 */
//	@GetMapping("/front/list")
//	public  String frontList(@RequestParam Map<String, Object> params,Model model){
//		//查询列表数据
//		//Query query = new Query(params);
//		List<MessageReceiverDO> messageReceiverList = messageReceiverService.list(params);
//		/*int total = messageReceiverService.count(query);
//		PageUtils pageUtils = new PageUtils(messageReceiverList, total);*/
//		model.addAttribute("messageReceiverList", messageReceiverList);
//		String loginType = ShiroUtils.getUser().getLoginType();
//		model.addAttribute("loginType", loginType); 
//		model.addAttribute("aTagert", "userMessageList");
//		model.addAttribute("divTagert", "menuList1");
//		
//		return "front/common/message_list";
//	}
	
	/***************前端页面调用结束*******************/
}
