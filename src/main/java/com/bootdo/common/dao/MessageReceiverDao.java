package com.bootdo.common.dao;

import com.bootdo.common.domain.MessageReceiverDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 对于群发消息，本表记录消息接收方信息；对于一对一的消息，消息接收方表中对应一条记录。
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-28 17:10:17
 */
@Mapper
public interface MessageReceiverDao {

	MessageReceiverDO get(Integer fId);
	
	List<MessageReceiverDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(MessageReceiverDO messageReceiver);
	
	int update(MessageReceiverDO messageReceiver);
	
	int remove(Integer f_id);
	
	int batchRemove(Integer[] fIds);

	int removeForMsgId(Integer fMsgId);

	int batchRemoveForMsgIds(Integer[] fMsgIds);
}
