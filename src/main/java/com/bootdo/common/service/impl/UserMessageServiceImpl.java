package com.bootdo.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bootdo.common.dao.UserMessageDao;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.domain.UserMessageDO;
import com.bootdo.common.service.UserMessageService;
import com.bootdo.common.utils.BuildTree;
import com.bootdo.common.utils.DictUtils;
import com.bootdo.common.vo.CategoryVO;



@Service
public class UserMessageServiceImpl extends BaseServiceImpl  implements UserMessageService {

	@Autowired
	private UserMessageDao userMessageDao;
	
	@Override
	public UserMessageDO get(Integer fMsgId) {
		return userMessageDao.get(fMsgId);
	}

	@Override
	public List<UserMessageDO> list(Map<String, Object> map) {
		return userMessageDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return userMessageDao.count(map);
	}

	@Override
	public int save(UserMessageDO userMessage) {
		return userMessageDao.save(userMessage);
	}

	@Override
	public int update(UserMessageDO userMessage) {
		return userMessageDao.update(userMessage);
	}

	@Override
	public int remove(Integer fMsgId) {
		return userMessageDao.remove(fMsgId);
	}

	@Override
	public int batchRemove(Integer[] fMsgIds) {
		return userMessageDao.batchRemove(fMsgIds);
	}

	@Override
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map,
			ServletOutputStream out) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void importExcel(String fileName, MultipartFile file)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserMessageDO> receiverList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int receiverCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int msgsendSave(String type, UserMessageDO userMessage) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserMessageDO> userMessageList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int userMessageCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int frontSave(UserMessageDO userMessage) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int batchCheckSend(String type, String fMessage, List list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int messPushSave(String pushType, String ids,
			UserMessageDO userMessage) {
		// TODO Auto-generated method stub
		return 0;
	}
//	@Autowired
//	private UserMessageDao userMessageDao;
//	@Autowired
//	private MessageReceiverDao messageReceiverDao;
//    @Autowired
//    private  UserDao userMapper;
//	
//	@Override
//	public UserMessageDO get(Integer fMsgId){
//		return userMessageDao.get(fMsgId);
//	}
//	
//	@Override
//	public List<UserMessageDO> list(Map<String, Object> map){
//		return userMessageDao.list(map);
//	}
//	/**
//	 * 未读消息和我的接收消息列表查询
//	 * @param query
//	 * @return
//	 */
//	public List<UserMessageDO> receiverList(Map<String, Object> map){
//		return userMessageDao.receiverList(map);
//	}
//	/**
//	 * 未读消息和我的接收消息列表查询
//	 * @param query
//	 * @return
//	 */
//	public int receiverCount(Map<String, Object> map){
//		return userMessageDao.receiverCount(map);
//	}
//	/**
//	 * 我发送的消息记录
//	 * @param query
//	 * @return
//	 */
//	public List<UserMessageDO> userMessageList(Map<String, Object> map){
//		return userMessageDao.userMessageList(map);
//	}
//	/**
//	 * 我发送的消息记录
//	 * @param query
//	 * @return
//	 */
//	public int userMessageCount(Map<String, Object> map) {
//		return userMessageDao.userMessageCount(map);
//	}
//	
//	@Override
//	public int count(Map<String, Object> map){
//		return userMessageDao.count(map);
//	}
//	
//	@Override
//	public int save(UserMessageDO userMessage){
//		//点对点保存
//			
//			UserMessageDO tempMessage = null; 
//			String fto = userMessage.getFto();//消息接收者
//			if(StringUtils.isEmpty(fto)) {//发送全部
//				this.saveUserMessage(userMessage, "public", "A", "");
//			}else {//按照列表发送
//				
//				String [] ftos = fto.split(",");
//				
//				MessageReceiverDO messageReceiver = null;
//				
//				for (String to : ftos) {
//					
//					
//					String [] tos = to.split("_");//id分拆 判断级别
//					if("c".equals(tos[0])) {//辅导员
//						if(tos.length == 2) {//全部辅导员
//							this.saveUserMessage(userMessage, "public", "C", "");
//						}else if(tos.length == 3) {//学员辅导员保存
//							this.batchSaveC(userMessage, tos);
//						
//						}else if(tos.length == 4) {
//							UserMessageDO mess = this.saveUserMessage(userMessage, "private", "", "");
//							this.saveReceiver(mess.getfMsgId(), tos[3]);
//						}
//						
//					}else if("s".equals(tos[0])) {//学生
//						if(tos.length == 2) {//全部学生
//							this.saveUserMessage(userMessage, "public", "S", "");
//						}else if(tos.length == 3) {//学院
//							this.saveUserMessage(userMessage, "public", "S",tos[2] );
//						}else if(tos.length == 4) {//专业
//							this.batchSave(userMessage,tos);
//						}else if(tos.length == 5) {//年级
//							this.batchSave(userMessage,tos);
//						}else if(tos.length == 6) {//班级
//							//班级查询学生
//							this.batchSave(userMessage,tos);
//							
//						}else if(tos.length == 7) {//单个学生
//							UserMessageDO mess = this.saveUserMessage(userMessage, "private", "", "");
//							this.saveReceiver(mess.getfMsgId(), tos[6]);
//						}
//						
//					}else if("e".equals(tos[0])) {//企业
//						if(tos.length == 2) {//全部企业
//							this.saveUserMessage(userMessage, "public", "E", "");
//						}else if(tos.length == 3) {//单个企业
//							UserMessageDO mess = this.saveUserMessage(userMessage, "private", "", "");
//							this.saveReceiver(mess.getfMsgId(), tos[2]);
//						}
//					}else if("p".equals(tos[0])) {//教育指导中心
//						if(tos.length == 2) {//就业指导中心
//							this.batchSaveP(userMessage, tos);
//						}
//					}
//					
//				}
//				
//			}
//		return 1;
//	}
//	/**
//	 * 保存发送消息
//	 * @param fType 系统消息记为"public"，私信消息记为"private"
//	 * @param fToType "S"表示学生，"E"表示单位，"C"表示辅导员 "A" 全部消息
//	 */
//	private UserMessageDO saveUserMessage(UserMessageDO userMessage,String fType,String fToType,String fCollegeId) {
//		UserMessageDO tempMessage = new UserMessageDO();
//		tempMessage.setfMessage(userMessage.getfMessage());
//		tempMessage.setfUrl(userMessage.getfUrl());
//		tempMessage.setfMemo(userMessage.getfMemo());
//		tempMessage.setfFrom(ShiroUtils.getUser().getId()+"");
//		tempMessage.setfSendTime(new Date());
//		tempMessage.setfType(fType);//一对一组
//		tempMessage.setfToType(fToType);//"S"表示学生，"E"表示单位，"C"表示辅导员,"P"表示教育指导中心
//		tempMessage.setfCollegeId(fCollegeId);
//		userMessageDao.save(tempMessage);
//		return tempMessage;
//	}
//	
//	/**
//	 * 保存消息接收表信息
//	 * @return
//	 */
//	private MessageReceiverDO saveReceiver(int mesId,String fTo ) {
//		MessageReceiverDO messageReceiver = new MessageReceiverDO();
//		messageReceiver.setfFromReaded(-1);
//		messageReceiver.setfToReaded(0);
//		messageReceiver.setfMsgId(mesId);
//		messageReceiver.setfTo(fTo);
//		messageReceiverDao.save(messageReceiver);
//		return messageReceiver;
//	}
//	
//	@Override
//	public int update(UserMessageDO userMessage){
//		return userMessageDao.update(userMessage);
//	}
//	
//	@Override
//	public int remove(Integer fMsgId){
//		return userMessageDao.remove(fMsgId);
//	}
//	
//	@Override
//	public int batchRemove(Integer[] fMsgIds){
//		return userMessageDao.batchRemove(fMsgIds);
//	}
//	/**
//	 * 职位类别导出EXCEL
//	 * @param request
//	 * @param response
//	 * @param out
//	 */
//	public void exportExcel(HttpServletRequest request,
//			HttpServletResponse response, Map<String, Object> map,
//			ServletOutputStream out) {
//		TemplateExportParams params = new TemplateExportParams( bootdoConfig.getUploadPath()+"templates/doc/UserMessage.xls");
//		List<UserMessageDO> list = userMessageDao.list(map);
//		Map<String, Object> map1 = new HashMap<String, Object>();
//		map1.put("maplist", list);
//		Workbook workbook = ExcelExportUtil.exportExcel(params,map1);
//		try {
//			String fileName = "UserMessage"+new Date().getTime()/1000+".xls";
//			response.setContentType("application/octet-stream");
//            response.setHeader("name", fileName);
//            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
//            response.setHeader("Pragma", "public");
//            response.setDateHeader("Expires", 0);
//            response.setHeader("Content-disposition","attachment; filename=\""+URLEncoder.encode(fileName, "UTF-8")+ "\"");
//			 workbook.write(out);
//			 workbook.close();
//			 out.flush();
//			 out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
//	/**
//	 * 导入excel
//	 * @param fileName
//	 * @param file
//	 * @return
//	 * @throws IOException 
//	 * @throws InvalidFormatException 
//	 */
//	public void importExcel(String fileName, MultipartFile file) throws Exception {
//		
//			ImportExcel ei = new ImportExcel(file, 1, 0);
//			List<UserMessageDO> list = ei.getDataList(UserMessageDO.class);
//			//
//			for (UserMessageDO userMessage : list) {
//				
//				if(userMessage != null) {
//					 userMessageDao.save( userMessage);
//				}
//				
//			}
//	}

	@Override
	public List<Tree<CategoryVO>> getAllCategoryTree(Map<String, Object> params){
		
		List<Tree<CategoryVO>> trees = new ArrayList<Tree<CategoryVO>>();	
		String busType = "specialty";
		List<DictDO> dictList =DictUtils.getDictList("specialty");
		int s=1;
		for (DictDO dict : dictList) {
			Tree<CategoryVO> tree = new Tree<CategoryVO>();
			tree.setId(String.valueOf(s));
			s=s+1;
			tree.setParentId("0");
			tree.setText(dict.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("closed", true);
			tree.setState(state);
			Map<String, Object> attributes = new HashMap<>(16);
			attributes.put("ftype", "specialty");
			tree.setAttributes(attributes);
			tree.setChildren(true);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		List<Tree<CategoryVO>>  t = BuildTree.buildList(trees,"0");
		
		return t;
	}

	@Override
	public List<UserMessageDO> notifylistByMember(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return userMessageDao.notifylistByMember(map);
	}

	@Override
	public List<UserMessageDO> notifylistByExpert(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return userMessageDao.notifylistByExpert(map);
	}

	@Override
	public int notifylistByMemberCount(Map<String, Object> map) {
		return userMessageDao.notifylistByMemberCount(map);
	}
	
	@Override
	public int notifylistByExpertCount(Map<String, Object> map) {
		return userMessageDao.notifylistByExpertCount(map);
	}

	@Override
	public UserMessageDO prevUserMessage(Map<String, Object> params) {
		return userMessageDao.prevUserMessage(params);
	}

	@Override
	public UserMessageDO nextUserMessage(Map<String, Object> params) {
		return userMessageDao.nextUserMessage(params);
	}
	
//	/**
//	 * 消息保存
//	 * @param type
//	 * @param userMessage
//	 * @return
//	 */
//	public int msgsendSave(String type, UserMessageDO userMessage1) {
//		
//		userMessage1.setfFrom(ShiroUtils.getUserId()+"");
//		userMessage1.setfSendTime(new Date());
//		if("1".equals(type)) {//留言
//			userMessage1.setfMessage("[留言]"+userMessage1.getfMessage());
//		}else if("2".equals(type)) {//举报
//			userMessage1.setfMessage("[举报]"+userMessage1.getfMessage());
//		}else if("3".equals(type)) {//建议
//			userMessage1.setfMessage("[建议]"+userMessage1.getfMessage());
//		}else if("4".equals(type)) {//投诉
//			userMessage1.setfMessage("[投诉]"+userMessage1.getfMessage());
//		}
//		int i =this.frontSave( userMessage1);
//		return i;
//	}
	
}
