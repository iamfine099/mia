package com.bootdo.cms.domain;

import java.io.Serializable;
import java.util.Date;

import com.bootdo.common.utils.excel.annotation.ExcelField;



/**
 * 友情链接
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-25 09:53:54
 */
public class CmsLinkDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//友情链接标识
	private Integer fId;
	//状态
	private Integer fStatus;
	//排序
	private Integer fListOrder;
	//友情链接描述
	private String fDescription;
	//友情链接地址
	private String fUrl;
	//友情链接名称
	private String fName;
	//友情链接图标
	private Long fImage;
	
	//友情链接打开方式
	private String fTarget;
	//链接与网站的关系
	private String fRel;
	
	/**
	 * 辅助字段
	 */
	private String imageUrl;
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getfId() {
		return fId;
	}
	public void setfId(Integer fId) {
		this.fId = fId;
	}
	public Integer getfStatus() {
		return fStatus;
	}
	public void setfStatus(Integer fStatus) {
		this.fStatus = fStatus;
	}
	public Integer getfListOrder() {
		return fListOrder;
	}
	public void setfListOrder(Integer fListOrder) {
		this.fListOrder = fListOrder;
	}
	public String getfDescription() {
		return fDescription;
	}
	public void setfDescription(String fDescription) {
		this.fDescription = fDescription;
	}
	public String getfUrl() {
		return fUrl;
	}
	public void setfUrl(String fUrl) {
		this.fUrl = fUrl;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public Long getfImage() {
		return fImage;
	}
	public void setfImage(Long fImage) {
		this.fImage = fImage;
	}
	public String getfTarget() {
		return fTarget;
	}
	public void setfTarget(String fTarget) {
		this.fTarget = fTarget;
	}
	public String getfRel() {
		return fRel;
	}
	public void setfRel(String fRel) {
		this.fRel = fRel;
	}

	
}
