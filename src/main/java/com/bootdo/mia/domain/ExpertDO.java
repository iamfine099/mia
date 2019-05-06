package com.bootdo.mia.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.Pattern;

import com.bootdo.common.domain.DictDO;
import com.bootdo.common.utils.DictUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.bootdo.common.utils.excel.annotation.ExcelField;



/**
 * 1)用于记录专家信息。
2)通过后台添加产生记录。
3)该实体主要由专家登录、专家管理等业务使用
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:34
 */
public class ExpertDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//专家标识
	private Integer expertId;
	//专家头像
	private String headImg;
	//姓名
/*	@NotEmpty(message="专家名不能为空")
	@Pattern(regexp="[\\u4e00-\\u9fa5]") 
	@Length(min=2,max=20,message="专家姓名长度不正确")*/
	private String expertName;
	//性别 1男 2女
	private String sex;
	//手机号
	/*@NotEmpty(message="手机号不能为空")
	@Length(min=11,max=11,message="手机号不正确")*/
	private String phone;
	//状态：0正常1禁用
	private String status;
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
	//密码
	/*@NotEmpty(message="密码不能为空")
	@Length(min=6,max=16,message="密码长度不正确，得在6-16之间")*/
	private String password;
	//个人履历
	private String experience;
	//学术成就
	private String achievement;
	//邮箱
	/*@NotEmpty(message="邮箱不能为空")
    @Email(message="邮箱格式不正确")*/
	private String email;
	 // URL地址
    private String url;
    //办公电话
    private String officePhone;
    //协会职务
    private String office;


	//专长
	private String specialtyName;
	/**
	 * 设置：专家标识
	 */
	public void setExpertId(Integer expertId) {
		this.expertId = expertId;
	}
	/**
	 * 获取：专家标识
	 */
	 
	@ExcelField(title="专家标识", type=2, align=2, sort=1)
	
	public Integer getExpertId() {
		return expertId;
	}
	/**
	 * 设置：专家头像
	 */
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	/**
	 * 获取：专家头像
	 */
	 
	@ExcelField(title="专家头像", type=2, align=2, sort=2)
	
	public String getHeadImg() {
		return headImg;
	}
	/**
	 * 设置：姓名
	 */
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	/**
	 * 获取：姓名
	 */
	 
	@ExcelField(title="姓名", type=2, align=2, sort=3)
	
	public String getExpertName() {
		return expertName;
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
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getExperience() {
		return experience;
	}
	
	public void setExperience(String experience) {
		this.experience = experience;
	}
	
	public String getAchievement() {
		return achievement;
	}
	
	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	 /**
     * 设置：URL地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取：URL地址
     */
    public String getUrl() {
        return url;
    }
    
	public String getOfficePhone() {
		return officePhone;
	}
	
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	
	public String getOffice() {
		return office;
	}
	
	public void setOffice(String office) {
		this.office = office;
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
}
