package com.bootdo.common.domain;

import java.io.Serializable;
import java.util.Date;

import com.bootdo.common.utils.excel.annotation.ExcelField;



/**
 * 文章数据表
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:31
 */
public class ArticleDataDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//标识
	private Integer id;
	//文章内容
	private String content;
	//文章来源
	private String copyfrom;

	/**
	 * 设置：标识
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：标识
	 */
	 
	@ExcelField(title="标识", type=2, align=2, sort=1)
	
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：文章内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：文章内容
	 */
	 
	@ExcelField(title="文章内容", type=2, align=2, sort=2)
	
	public String getContent() {
		return content;
	}
	/**
	 * 设置：文章来源
	 */
	public void setCopyfrom(String copyfrom) {
		this.copyfrom = copyfrom;
	}
	/**
	 * 获取：文章来源
	 */
	 
	@ExcelField(title="文章来源", type=2, align=2, sort=3)
	
	public String getCopyfrom() {
		return copyfrom;
	}
}
