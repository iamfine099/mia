package com.bootdo.mia.domain;

import java.io.Serializable;
import java.util.Date;

import com.bootdo.common.utils.excel.annotation.ExcelField;



/**
 * 1)用于记录留言信息。
2)通过前端用户填写留言产生记录。
3)该实体主要由会员留言、留言管理等
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:35
 */
public class LeaveMessageDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//留言标识
	private Integer lmId;
	//留言内容
	private String lmContent;
	//创建人
	private Integer createBy;
	//创建时间
	private Date createDate;
	//审核人
	private Integer auditBy;
	//审核时间
	private Date auditDate;
	//审核内容
	private String auditContent;
	//回复人
	private Integer replyBy;
	//回复时间
	private Date replyDate;
	//回复内容
	private String replyContent;
	//状态
	private String status;
	//类型
	private String type;
	//未登录用户姓名
	private String name;
	//未登录用户电话
	private String phone;
	//未登录用户邮箱
	private String email;
	//未登录用户单位
	private String company;
	//未登录用户地址
	private String address;
	//未登录用户微信
	private String weChat;
	//是否登录
	private String loginStatus;

	/**
	 * 设置：留言标识
	 */
	public void setLmId(Integer lmId) {
		this.lmId = lmId;
	}
	/**
	 * 获取：留言标识
	 */
	 
	@ExcelField(title="留言标识", type=2, align=2, sort=1)
	
	public Integer getLmId() {
		return lmId;
	}
	/**
	 * 设置：留言内容
	 */
	public void setLmContent(String lmContent) {
		this.lmContent = lmContent;
	}
	/**
	 * 获取：留言内容
	 */
	 
	@ExcelField(title="留言内容", type=2, align=2, sort=2)
	
	public String getLmContent() {
		return lmContent;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建人
	 */
	 
	@ExcelField(title="创建人", type=2, align=2, sort=3)
	
	public Integer getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	 
	@ExcelField(title="创建时间", type=2, align=2, sort=4)
	
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：审核人
	 */
	public void setAuditBy(Integer auditBy) {
		this.auditBy = auditBy;
	}
	/**
	 * 获取：审核人
	 */
	 
	@ExcelField(title="审核人", type=2, align=2, sort=5)
	
	public Integer getAuditBy() {
		return auditBy;
	}
	/**
	 * 设置：审核时间
	 */
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	/**
	 * 获取：审核时间
	 */
	 
	@ExcelField(title="审核时间", type=2, align=2, sort=6)
	
	public Date getAuditDate() {
		return auditDate;
	}
	/**
	 * 设置：审核内容
	 */
	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}
	/**
	 * 获取：审核内容
	 */
	 
	@ExcelField(title="审核内容", type=2, align=2, sort=7)
	
	public String getAuditContent() {
		return auditContent;
	}
	/**
	 * 设置：回复人
	 */
	public void setReplyBy(Integer replyBy) {
		this.replyBy = replyBy;
	}
	/**
	 * 获取：回复人
	 */
	 
	@ExcelField(title="回复人", type=2, align=2, sort=8)
	
	public Integer getReplyBy() {
		return replyBy;
	}
	/**
	 * 设置：回复时间
	 */
	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}
	/**
	 * 获取：回复时间
	 */
	 
	@ExcelField(title="回复时间", type=2, align=2, sort=9)
	
	public Date getReplyDate() {
		return replyDate;
	}
	/**
	 * 设置：回复内容
	 */
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	/**
	 * 获取：回复内容
	 */
	 
	@ExcelField(title="回复内容", type=2, align=2, sort=10)
	
	public String getReplyContent() {
		return replyContent;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态
	 */
	 
	@ExcelField(title="状态", type=2, align=2, sort=11)
	
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型
	 */
	 
	@ExcelField(title="类型", type=2, align=2, sort=12)
	
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWeChat() {
		return weChat;
	}
	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}
	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
}
