package com.bootdo.mia.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bootdo.common.domain.DictDO;
import com.bootdo.common.utils.DictUtils;
import com.bootdo.common.utils.excel.annotation.ExcelField;
import com.bootdo.system.domain.UserDO;
import org.apache.commons.lang3.StringUtils;


/**
 * 1)用于记录会员信息。
2)通过前端用户注册产生记录。
3)该实体主要由会员登录、注册、发表文章
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:36
 */
public class MemberDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//会员标识
	private Integer memId;
	//会员头像
	private String headImg;
	//姓名
	private String memName;
	//性别 1男 2女
	private String sex;
	//手机号
	private String phone;
	//状态：0正常1禁用
	private String status;
	//密码
	private String password;
	//创建时间
	private Date createDate;
	//最后登录时间
	private Date lastTime;
	//单位
	private String company;
	//专长
	private String specialty;
	//号码牌
	private String numberCard;
	//审核人
	private Integer auditBy;
	private UserDO auditUser;
	//审核时间
	private Date auditDate;
	//审核内容
	private String auditContent;
	//邮箱
	private String email;
	
	//审核
	private String auditStatus;
	//多个字符串
	private String ids;
	private String headUrl;
	/**
	 * 设置：会员标识
	 */
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	/**
	 * 获取：会员标识
	 */
	 
	@ExcelField(title="会员标识", type=2, align=2, sort=1)
	
	public Integer getMemId() {
		return memId;
	}
	/**
	 * 设置：会员头像
	 */
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	/**
	 * 获取：会员头像
	 */
	 
	@ExcelField(title="会员头像", type=2, align=2, sort=2)
	
	public String getHeadImg() {
		return headImg;
	}
	/**
	 * 设置：姓名
	 */
	public void setMemName(String memName) {
		this.memName = memName;
	}
	/**
	 * 获取：姓名
	 */
	 
	@ExcelField(title="姓名", type=2, align=2, sort=3)
	
	public String getMemName() {
		return memName;
	}
	/**
	 * 设置：性别 1男 2女
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别 1男 2女
	 */
	 
	@ExcelField(title="性别 1男 2女", type=2, align=2, sort=4)
	
	public String getSex() {
		return sex;
	}
	/**
	 * 设置：手机号
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：手机号
	 */
	 
	@ExcelField(title="手机号", type=2, align=2, sort=5)
	
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：状态：0正常1禁用
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态：0正常1禁用
	 */
	 
	@ExcelField(title="状态：0正常1禁用", type=2, align=2, sort=6)
	
	public String getStatus() {
		return status;
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
	 
	@ExcelField(title="创建时间", type=2, align=2, sort=7)
	
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：最后登录时间
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * 获取：最后登录时间
	 */
	 
	@ExcelField(title="最后登录时间", type=2, align=2, sort=8)
	
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置：单位
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * 获取：单位
	 */
	 
	@ExcelField(title="单位", type=2, align=2, sort=9)
	
	public String getCompany() {
		return company;
	}
	/**
	 * 设置：专长
	 */
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	/**
	 * 获取：专长
	 */
	 
	@ExcelField(title="专长", type=2, align=2, sort=10)
	
	public String getSpecialty() {
		return specialty;
	}
	/**
	 * 设置：号码牌
	 */
	public void setNumberCard(String numberCard) {
		this.numberCard = numberCard;
	}
	/**
	 * 获取：号码牌
	 */
	 
	@ExcelField(title="号码牌", type=2, align=2, sort=11)
	
	public String getNumberCard() {
		return numberCard;
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
	 
	@ExcelField(title="审核人", type=2, align=2, sort=12)
	
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
	 
	@ExcelField(title="审核时间", type=2, align=2, sort=13)
	
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
	 
	@ExcelField(title="审核内容", type=2, align=2, sort=14)
	
	public String getAuditContent() {
		return auditContent;
	}
	
	public String getAuditStatus() {
		return auditStatus;
	}
	
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public String getIds() {
		return ids;
	}
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public UserDO getAuditUser() {
		return auditUser;
	}
	
	public void setAuditUser(UserDO auditUser) {
		this.auditUser = auditUser;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getSpecialtyName() {

		List list = new ArrayList();
		Map<String, String> map = DictUtils.getDictList("specialty").stream().
				collect(Collectors.toMap(DictDO::getValue, DictDO::getName));
		if(StringUtils.isBlank(specialty)) {
			return "";
		}
		for(String spec : specialty.split(",")) {
			list.add(map.get(spec));
		}
		return StringUtils.join(list, ",");
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
}
