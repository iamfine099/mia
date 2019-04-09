package com.bootdo.common.dao;

import com.bootdo.common.domain.UserMessageDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 平台用户可有限制的向其它用户发送消息，消息仅限一问一答，不支持即时通讯。
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-28 17:10:17
 */
@Mapper
public interface UserMessageDao {

	UserMessageDO get(Integer fMsgId);
	
	List<UserMessageDO> list(Map<String,Object> map);
	
	List<UserMessageDO> notifylistByMember(Map<String,Object> map);
	
	List<UserMessageDO> notifylistByExpert(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(UserMessageDO userMessage);
	
	int update(UserMessageDO userMessage);
	
	int remove(Integer fMsgIds);
	
	int batchRemove(Integer[] fMsgIds);

	List<UserMessageDO> receiverList(Map<String, Object> map);

	int receiverCount(Map<String, Object> map);

	List<UserMessageDO> userMessageList(Map<String, Object> map);

	int userMessageCount(Map<String, Object> map);

	int notifylistByMemberCount(Map<String, Object> map);
	
	int notifylistByExpertCount(Map<String, Object> map);

	UserMessageDO prevUserMessage(Map<String, Object> params);

	UserMessageDO nextUserMessage(Map<String, Object> params);
}
