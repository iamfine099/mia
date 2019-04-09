package com.bootdo.common.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.bootdo.common.domain.Tree;
import com.bootdo.common.domain.UserMessageDO;
import com.bootdo.common.utils.Query;
import com.bootdo.common.vo.CategoryVO;
/**
 * 平台用户可有限制的向其它用户发送消息，消息仅限一问一答，不支持即时通讯。
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-28 17:10:17
 */
public interface UserMessageService {
	
	UserMessageDO get(Integer fMsgId);
	
	List<UserMessageDO> list(Map<String, Object> map);
	
    List<UserMessageDO> notifylistByMember(Map<String,Object> map);
    
    int notifylistByMemberCount(Map<String,Object> map);
	
	List<UserMessageDO> notifylistByExpert(Map<String,Object> map);
	
	int notifylistByExpertCount(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserMessageDO userMessage);
	
	int update(UserMessageDO userMessage);
	
	int remove(Integer fMsgId);
	
	int batchRemove(Integer[] fMsgIds);
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @param map
	 * @param out
	 */
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map,
			ServletOutputStream out);
	/**
	 * 导入excel
	 * @param fileName
	 * @param file
	 * @return
	 */
	public void importExcel(String fileName, MultipartFile file)throws Exception;



	
	
	/**
	 * 获取全部Tree数据
	 * @param params 
	 * @return
	 */
	public List<Tree<CategoryVO>> getAllCategoryTree(Map<String, Object> params);
	
	

	
	

	/**
	 * 未读消息和我的接收消息列表查询
	 * @param query
	 * @return
	 */
	public List<UserMessageDO> receiverList(Map<String, Object> map);
	/**
	 * 未读消息和我的接收消息列表查询
	 * @param query
	 * @return
	 */
	public int receiverCount(Map<String, Object> map);
	/**
	 * 消息保存
	 * @param type
	 * @param userMessage
	 * @return
	 */
	public int msgsendSave(String type, UserMessageDO userMessage);
	/**
	 * 我发送的消息记录
	 * @param query
	 * @return
	 */
	public List<UserMessageDO> userMessageList(Map<String, Object> map);
	/**
	 * 我发送的消息记录
	 * @param query
	 * @return
	 */
	public int userMessageCount(Map<String, Object> map);
	/**
	 * 学生端、企业端消息发送保存
	 * @param query
	 * @return
	 */
	public int frontSave(UserMessageDO userMessage);
	/**
	 * 批量审核同时发送消息
	 * @param type
	 * @param fMessage
	 * @param ids
	 * @return
	 */
	public int batchCheckSend(String type, String fMessage,List list);
	/**
	 * 推送消息保存
	 * @param ids 
	 * @param pushType 
	 * @param userMessage
	 * @return
	 */
	public int messPushSave(String pushType, String ids, UserMessageDO userMessage);

	//上一篇
	UserMessageDO prevUserMessage(Map<String, Object> params);

	//下一篇
	UserMessageDO nextUserMessage(Map<String, Object> params);




	

	
}
