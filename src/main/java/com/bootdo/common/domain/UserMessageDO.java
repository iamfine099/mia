package com.bootdo.common.domain;

import java.io.Serializable;
import java.util.Date;

import com.bootdo.common.utils.excel.annotation.ExcelField;

/**
 * 平台用户可有限制的向其它用户发送消息，消息仅限一问一答，不支持即时通讯。
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-28 17:10:17
 */
public class UserMessageDO implements Serializable {
	private static final long serialVersionUID = 1L;

	// 消息编号
	private Integer fMsgId;
	// 消息发送方
	private String fFrom;
	// 消息内容
	private String fMessage;
	// 发送时间
	private Date fSendTime;
	// 引用网址
	private String fUrl;
	// 备注信息
	private String fMemo;
	// 消息类型
	private String fType;
	// 消息标题
	private String fTitle;
	

	// 消息接收方类型
	private String fToType;
	// 消息接收方编号
	private String fCollegeId;

	private String fTo;// 接收者

	// 消息接收对象
	private MessageReceiverDO receiver;
	// 消息接收对象 id
	private int fId;
	// 回复内容
	private String fReply;
	// 回复时间
	private Date fReplyTime;
	// 接收方已读
	private Integer fToReaded;
	// 发送方已读
	private Integer fFromReaded;
	// 接收方读取时间
	private Date fToTime;
	// 发送方读取时间
	private Date fFromTime;
	
	private String fToReadDesc;
	


	public String getfToReadDesc() {
		return fToReadDesc;
	}

	public void setfToReadDesc(String fToReadDesc) {
		this.fToReadDesc = fToReadDesc;
	}

	public int getfId() {
		return fId;
	}

	public void setfId(int fId) {
		this.fId = fId;
	}

	public String getfReply() {
		return fReply;
	}

	public void setfReply(String fReply) {
		this.fReply = fReply;
	}

	public Date getfReplyTime() {
		return fReplyTime;
	}

	public void setfReplyTime(Date fReplyTime) {
		this.fReplyTime = fReplyTime;
	}

	public Integer getfToReaded() {
		return fToReaded;
	}

	public void setfToReaded(Integer fToReaded) {
		this.fToReaded = fToReaded;
	}

	public Integer getfFromReaded() {
		return fFromReaded;
	}

	public void setfFromReaded(Integer fFromReaded) {
		this.fFromReaded = fFromReaded;
	}

	public Date getfToTime() {
		return fToTime;
	}

	public void setfToTime(Date fToTime) {
		this.fToTime = fToTime;
	}

	public Date getfFromTime() {
		return fFromTime;
	}

	public void setfFromTime(Date fFromTime) {
		this.fFromTime = fFromTime;
	}

	public String getfType() {
		return fType;
	}

	public void setfType(String fType) {
		this.fType = fType;
	}
	public String getfTitle() {
		return fTitle;
	}

	public void setfTitle(String fTitle) {
		this.fTitle = fTitle;
	}

	public String getfToType() {
		return fToType;
	}

	public void setfToType(String fToType) {
		this.fToType = fToType;
	}

	public String getfCollegeId() {
		return fCollegeId;
	}

	public void setfCollegeId(String fCollegeId) {
		this.fCollegeId = fCollegeId;
	}


	public MessageReceiverDO getReceiver() {
		return receiver;
	}

	public void setReceiver(MessageReceiverDO receiver) {
		this.receiver = receiver;
	}

	public Integer getfMsgId() {
		return fMsgId;
	}

	public void setfMsgId(Integer fMsgId) {
		this.fMsgId = fMsgId;
	}

	public String getfFrom() {
		return fFrom;
	}

	public void setfFrom(String fFrom) {
		this.fFrom = fFrom;
	}

	public String getfMessage() {
		return fMessage;
	}

	public void setfMessage(String fMessage) {
		this.fMessage = fMessage;
	}

	public Date getfSendTime() {
		return fSendTime;
	}

	public void setfSendTime(Date fSendTime) {
		this.fSendTime = fSendTime;
	}

	public String getfUrl() {
		return fUrl;
	}

	public void setfUrl(String fUrl) {
		this.fUrl = fUrl;
	}

	public String getfMemo() {
		return fMemo;
	}

	public void setfMemo(String fMemo) {
		this.fMemo = fMemo;
	}

	public String getfTo() {
		return fTo;
	}

	public void setfTo(String fTo) {
		this.fTo = fTo;
	}

}
