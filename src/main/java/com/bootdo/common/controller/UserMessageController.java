package com.bootdo.common.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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

import com.bootdo.common.domain.MessageReceiverDO;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.domain.UserMessageDO;
import com.bootdo.common.service.MessageReceiverService;
import com.bootdo.common.service.UserMessageService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.common.vo.CategoryVO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;

/**
 * 平台用户可有限制的向其它用户发送消息，消息仅限一问一答，不支持即时通讯。
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-28 17:10:17
 */
 
@Controller
@RequestMapping("/common/userMessage")
public class UserMessageController {
	@Autowired
	private UserMessageService userMessageService;
	@Autowired
	private MessageReceiverService messageReceiverService;
	@Autowired
	private UserService userService;
	
	@RequestMapping()
	@RequiresPermissions("common:userMessage:userMessage")
	String UserMessage(){
	    return "common/userMessage/userMessage";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("common:userMessage:userMessage")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        List<UserMessageDO> userMessageList = userMessageService.list(query);
        int total = userMessageService.count(query);
        PageUtils pageUtils = new PageUtils(userMessageList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("common:userMessage:add")
	String add(){
	    return "common/userMessage/add";
	}
	
	/**
	 * 实习职位、就业职位消息推送功能
	 * @param pushType
	 * @param ids
	 * @param model
	 * @return
	 */
	@GetMapping("/messPushInit")
	@RequiresPermissions("common:userMessage:add")
	String messPushInit(String pushType,String ids,Model model){
		model.addAttribute("pushType", pushType);
		model.addAttribute("ids", ids);
	    return "common/userMessage/messagePush";
	}
	/**
	 * 实习职位、就业职位消息推送功能
	 * @param pushType
	 * @param ids
	 * @param model
	 * @return
	 */
	@ResponseBody
	@PostMapping("/messPushSave")
	@RequiresPermissions("common:userMessage:add")
	public R messPushSave(String pushType,String ids, UserMessageDO userMessage){
		
		if(userMessageService.messPushSave(pushType,ids,userMessage)>0){
			return R.ok();
		}
		
		return R.error();
	}
	
	@GetMapping("/edit/{id}")
	@RequiresPermissions("common:userMessage:edit")
	String edit( @PathVariable("id") Integer fMsgId,Model model){
		
		UserMessageDO userMessage = userMessageService.get(fMsgId);
		model.addAttribute("userMessage", userMessage);
		return "common/userMessage/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("common:userMessage:add")
	public R save( UserMessageDO userMessage){
		userMessage.setfSendTime(new Date());
		userMessage.getfTitle();
		if(userMessageService.save(userMessage)>0){
			return R.ok();
		}
		
		return R.error();
	}
	/**
	 * 填写回复消息
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("common:userMessage:edit")
	public R update( UserMessageDO userMessage){
		userMessageService.update(userMessage);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("common:userMessage:remove")
	public R remove( Integer fMsgId){
		if(userMessageService.remove(fMsgId)>0){
			messageReceiverService.removeForMsgId(fMsgId);
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("common:userMessage:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] fMsgIds){
		userMessageService.batchRemove(fMsgIds);
		messageReceiverService.batchRemoveForMsgIds(fMsgIds);
		return R.ok();
	}
	/**
	 * 导出
	 */
	@RequestMapping("/exportExcel")
	@RequiresPermissions("common:userMessage:add")
	public void exportExcel(@RequestParam Map<String, Object> params,HttpServletRequest request, HttpServletResponse response,  ServletOutputStream out){
	
		//查询列表数据
		userMessageService.exportExcel(request, response, params, out);
		
		return ;
	}
	@GetMapping("/importView")
	@RequiresPermissions("common:userMessage:add")
	String importView(){
	    return "common/userMessage/importExcel";
	}
	/**
	 * 导入
	 */
	@ResponseBody
	@PostMapping("/importExcel")
	@RequiresPermissions("common:userMessage:add")
	public R importExcel(@RequestParam("file") MultipartFile file) {
	        String fileName = file.getOriginalFilename();
	        try {
	           userMessageService.importExcel(fileName, file);
	        } catch (Exception e) {
	        	 return R.error();
	        }
	        return R.ok();
	}
	/**
	 * 加载全部分类Tree
	 * @param params
	 * @return
	 */
	@ResponseBody
	@PostMapping("/getAllCategoryTree")
	public List<Tree<CategoryVO>> getAllCategoryTree(@RequestParam Map<String, Object> params){

		return userMessageService.getAllCategoryTree(params);
	}

	
	
	
	/***************前端页面调用开始*******************/
	/**
	 * 学生端/企业端消息列表查询
	 * @param model
	 * @return
	 */
	@RequestMapping("/front/list")
	public String list(@RequestParam Map<String, Object> params,Model model){
		//查询列表数据
		// 获取到学生信息标识
		
			String msgType = "readedMessage";//消息类型
//			if(params.get("msgType")  != null ) {//消息类型为空
//				msgType = params.get("msgType").toString();
//			}
//	        List<UserMessageDO> userMessageList = null;
//	        int total = 0;
//	        Query query =  new Query(params);
//	        if("receiverMsg".equals(msgType)||"readedMessage".equals(msgType) ) {
//	        	query.put("fToType", ShiroUtils.getUser().getUser().getfType());
//	        	query.put("fTo", ShiroUtils.getUser().getUser().getUserId());
//	        	if("readedMessage".equals(msgType)) {
//	        		query.put("fToReaded", "0");
//	        	}
//	        	userMessageList = userMessageService.receiverList(query);
//	        	total  = userMessageService.receiverCount(query);
//	        }else if ("userMessage".equals(msgType)) {
//	        	query.put("fFrom", ShiroUtils.getUser().getUser().getUserId());
//	        	userMessageList = userMessageService.userMessageList(query);
//	        	total  = userMessageService.userMessageCount(query);
//	        }
//	        PageUtils pageUtils = new PageUtils(userMessageList, total,query);
//	        model.addAttribute("pageUtils", pageUtils);    
//	    	String loginType = ShiroUtils.getUser().getLoginType();
//			model.addAttribute("loginType", loginType); 
//			model.addAttribute("aTagert", "userMessageList");
//			model.addAttribute("divTagert", "menuList1");
			
		if("readedMessage".equals(msgType)) {//未读消息
			return "front/common/message_readed";
		}else if("userMessage".equals(msgType)) {//我发送的消息
			return "front/common/message_user";
		}else if("receiverMsg".equals(msgType)) {//我接收的消息
			return "front/common/message_receiver";
		}
		return "front/common/message_readed";
		
	}
	
	
	
	
	
	
	
	/**
	 * 发送消息-添加页面初始化
	 * @param model
	 * @return
	 */
	@GetMapping("/front/frontAdd")
	String frontAdd(Model model){
//		String loginType = ShiroUtils.getUser().getLoginType();
//		model.addAttribute("loginType", loginType); 
//		model.addAttribute("aTagert", "userMessageList");
//		model.addAttribute("divTagert", "menuList1");
	    return "front/common/message_add";
	}
	/**
	 * 发送消息-添加保存
	 * @param model
	 * @return
	 */
	@ResponseBody
	@PostMapping("/front/save")
	public R frontSave( UserMessageDO userMessage){
		
		if(userMessageService.frontSave(userMessage)>0){
			return R.ok();
		}
		return R.error();
	}
	
	
	/**
	 * 发送消息-修改页面初始化
	 * @param model
	 * @return
	 */
	@GetMapping("/front/frontEdit")
	String frontEdit( Integer fMsgId,Integer fId  ,Model model){
		
		UserMessageDO userMessage = userMessageService.get(fMsgId);
		model.addAttribute("userMessage", userMessage);
		MessageReceiverDO  messageReceiver = new MessageReceiverDO();
		if(fId!= 0) {
			messageReceiver = messageReceiverService.get(fId);
			if(messageReceiver.getfToReaded() != 1){
				messageReceiver.setfToReaded(1);
				messageReceiver.setfToTime(new Date());
				messageReceiverService.update(messageReceiver);
			}
		}else{
			messageReceiver.setfMsgId(fMsgId);
			messageReceiver.setfTo(ShiroUtils.getUserId()+"");
			messageReceiver.setfReplyTime(new Date());
			messageReceiver.setfToReaded(1);
			messageReceiver.setfToTime(new Date());
			messageReceiverService.save(messageReceiver);
			
		}
		
		model.addAttribute("messageReceiver", messageReceiver);
		
//		String loginType = ShiroUtils.getUser().getLoginType();
//		model.addAttribute("loginType", loginType); 
//		model.addAttribute("aTagert", "userMessageList");
//		model.addAttribute("divTagert", "menuList1");
		
		 return "front/common/message_edit";
	   
	}
	/**
	 * 发送消息-添加保存
	 * @param model
	 * @return
	 */
	@ResponseBody
	@PostMapping("/front/update")
	public R frontUpdate( UserMessageDO userMessage,MessageReceiverDO messageReceiver){
		if(messageReceiver.getfId() == 0) {//新增
			messageReceiver.setfTo(ShiroUtils.getUserId()+"");
			messageReceiver.setfReplyTime(new Date());
			messageReceiver.setfToReaded(1);
			messageReceiver.setfToTime(new Date());
			if(messageReceiverService.save(messageReceiver)>0){
				return R.ok();
			}
		}else {//修改
			messageReceiver.setfReplyTime(new Date());
			/*messageReceiver.setfTo(ShiroUtils.getUserId()+"");
			messageReceiver.setfToReaded(1);
			messageReceiver.setfToTime(new Date());*/
			messageReceiverService.update(messageReceiver);
			if(messageReceiverService.update(messageReceiver)>0){
				return R.ok();
			}
		}
		return R.ok();
	}
	
	/**
	 * 消息发送页面初始化
	 * @param type
	 * @param userMessage
	 * @param aTagert
	 * @param divTagert
	 * @param model
	 * @return
	 */
	@RequestMapping("/front/msgsendInit")
	public String msgsendInit( String type,UserMessageDO userMessage ,String aTagert,String  divTagert, Model model){
		
		if(StringUtils.isEmpty(divTagert)) {
			divTagert = "menuList1";
		}
		if(StringUtils.isEmpty(aTagert)) {
			aTagert = "userMessageList";
		}
		model.addAttribute("type", type); 
		model.addAttribute("userMessage", userMessage); 
		
//		String loginType = ShiroUtils.getUser().getLoginType();
//		model.addAttribute("loginType", loginType); 
//		model.addAttribute("aTagert", aTagert);
//		model.addAttribute("divTagert", divTagert);
		
		 return "front/common/message_init";
	   
	}

	/**
	 * 留言、举报、投诉、建议消息保存
	 * @return
	 */
	@ResponseBody
	@PostMapping("/front/msgsendSave")
	public R msgsendSave(String type,UserMessageDO userMessage){
		if("E".equals(userMessage.getfType())){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("busId",userMessage.getfTo());
			map.put("fType",userMessage.getfType());
			UserDO user = userService.getIdType(map);
			if(user != null){
				userMessage.setfTo(user.getUserId().toString());
			}
		}
		if(userMessageService.msgsendSave(type,userMessage)>0){
			return R.ok();
		}
		return R.error();
	}
	
	/***************前端页面调用结束*******************/
}
