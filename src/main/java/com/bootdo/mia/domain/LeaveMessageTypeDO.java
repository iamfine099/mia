package com.bootdo.mia.domain;

import java.io.Serializable;
import java.util.Date;

import com.bootdo.common.utils.excel.annotation.ExcelField;



/**
 * 用于记录留言类型。
 * 
 * 
 * 
 * 
 */
public class LeaveMessageTypeDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//留言类型id
	private Integer mtId;
	//留言类型名称
	private String mtName;
	//留言类型备注
	private String remarks;
	
	
	/**
	 * 设置：留言类型id
	 */
	public void setMtId(Integer mtId) {
		this.mtId = mtId;
	}
	/**
	 * 获取：留言类型id
	 */
	 
	@ExcelField(title="留言类型标识", type=2, align=2, sort=1)
	
	public Integer getMtId() {
		return mtId;
	}
	/**
	 * 设置：留言类型名称
	 */
	public void setMtName(String mtName) {
		this.mtName = mtName;
	}
	/**
	 * 获取：留言类型名称
	 */
	 
	@ExcelField(title="留言类型", type=2, align=2, sort=2)
	
	public String getMtName() {
		return mtName;
	}
	/**
	 * 设置：留言类型备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取：留言类型备注
	 */
	 
	@ExcelField(title="留言类型备注", type=2, align=2, sort=3)
	
	public String getRemarks() {
		return remarks;
	}
}
