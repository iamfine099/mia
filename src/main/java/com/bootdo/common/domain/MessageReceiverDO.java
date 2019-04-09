package com.bootdo.common.domain;

import java.io.Serializable;
import java.util.Date;

import com.bootdo.common.utils.excel.annotation.ExcelField;



/**
 * 对于群发消息，本表记录消息接收方信息；对于一对一的消息，消息接收方表中对应一条记录。
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-28 17:10:17
 */
public class MessageReceiverDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//序号
	private Integer fId;
	//消息编号
	private Integer fMsgId;
	//消息接收方
	private String fTo;
	//回复内容
	private String fReply;
	//回复时间
	private Date fReplyTime;
	//接收方已读
	private Integer fToReaded;
	//发送方已读
	private Integer fFromReaded;
	//备注信息
	private String fMemo;
	
	
	//接收方读取时间
	private Date  fToTime;
	//发送方读取时间
	private Date  fFromTime;
	
	
	
	public Integer getfId() {
		return fId;
	}
	public void setfId(Integer fId) {
		this.fId = fId;
	}
	public Integer getfMsgId() {
		return fMsgId;
	}
	public void setfMsgId(Integer fMsgId) {
		this.fMsgId = fMsgId;
	}
	public String getfTo() {
		return fTo;
	}
	public void setfTo(String fTo) {
		this.fTo = fTo;
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
	public String getfMemo() {
		return fMemo;
	}
	public void setfMemo(String fMemo) {
		this.fMemo = fMemo;
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
	
	
}
