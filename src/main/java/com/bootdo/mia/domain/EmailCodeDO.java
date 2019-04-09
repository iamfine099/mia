package com.bootdo.mia.domain;

import java.io.Serializable;
import java.util.Date;

import com.bootdo.common.utils.excel.annotation.ExcelField;



/**
 * 1)用于记录验证码信息。
2)通过前台忘记密码发送验证码产生记录。
3)该实体主要由忘记密码等业
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-22 16:32:06
 */
public class EmailCodeDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//标识
	private Integer ecId;
	//邮箱
	private String ecEmail;
	//验证码
	private String ecCode;
	//有效时间
	private Date ecValidDate;
	//创建时间
	private Date createDate;

	/**
	 * 设置：标识
	 */
	public void setEcId(Integer ecId) {
		this.ecId = ecId;
	}
	/**
	 * 获取：标识
	 */
	 
	@ExcelField(title="标识", type=2, align=2, sort=1)
	
	public Integer getEcId() {
		return ecId;
	}
	/**
	 * 设置：邮箱
	 */
	public void setEcEmail(String ecEmail) {
		this.ecEmail = ecEmail;
	}
	/**
	 * 获取：邮箱
	 */
	 
	@ExcelField(title="邮箱", type=2, align=2, sort=2)
	
	public String getEcEmail() {
		return ecEmail;
	}
	/**
	 * 设置：验证码
	 */
	public void setEcCode(String ecCode) {
		this.ecCode = ecCode;
	}
	/**
	 * 获取：验证码
	 */
	 
	@ExcelField(title="验证码", type=2, align=2, sort=3)
	
	public String getEcCode() {
		return ecCode;
	}
	/**
	 * 设置：有效时间
	 */
	public void setEcValidDate(Date ecValidDate) {
		this.ecValidDate = ecValidDate;
	}
	/**
	 * 获取：有效时间
	 */
	 
	@ExcelField(title="有效时间", type=2, align=2, sort=4)
	
	public Date getEcValidDate() {
		return ecValidDate;
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
	 
	@ExcelField(title="创建时间", type=2, align=2, sort=5)
	
	public Date getCreateDate() {
		return createDate;
	}
}
