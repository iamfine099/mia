package com.bootdo.common.domain;

import java.io.Serializable;
import java.util.Date;

import com.bootdo.common.utils.excel.annotation.ExcelField;



/**
 * 栏目表
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-03 13:58:50
 */
public class CategoryDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Integer id;
	//父级编号
	private Integer parentId;
	//所有父级编号
	private String parentIds;
	//栏目模块
	private String module;
	//栏目名称
	private String name;
	//栏目图片
	private String image;
	//链接
	private String href;
	//目标
	private String target;
	//描述
	private String description;
	//排序（升序）
	private Integer sort;
	//是否在导航中显示
	private String inMenu;
	//创建者
	private String createBy;
	//创建时间
	private Date createDate;
	//更新者
	private String updateBy;
	//更新时间
	private Date updateDate;
	//备注信息
	private String remarks;
	//删除标记
	private String delFlag;
	/**
	 * 首页展示位置
	 */
	private int showPosition; 
	
	

	/**
	 * 设置：编号
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：编号
	 */
	 
	@ExcelField(title="编号", type=2, align=2, sort=1)
	
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：父级编号
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：父级编号
	 */
	 
	@ExcelField(title="父级编号", type=2, align=2, sort=2)
	
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * 设置：所有父级编号
	 */
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	/**
	 * 获取：所有父级编号
	 */
	 
	@ExcelField(title="所有父级编号", type=2, align=2, sort=3)
	
	public String getParentIds() {
		return parentIds;
	}
	/**
	 * 设置：栏目模块
	 */
	public void setModule(String module) {
		this.module = module;
	}
	/**
	 * 获取：栏目模块
	 */
	 
	@ExcelField(title="栏目模块", type=2, align=2, sort=6)
	
	public String getModule() {
		return module;
	}
	/**
	 * 设置：栏目名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：栏目名称
	 */
	 
	@ExcelField(title="栏目名称", type=2, align=2, sort=7)
	
	public String getName() {
		return name;
	}
	/**
	 * 设置：栏目图片
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取：栏目图片
	 */
	 
	@ExcelField(title="栏目图片", type=2, align=2, sort=8)
	
	public String getImage() {
		return image;
	}
	/**
	 * 设置：链接
	 */
	public void setHref(String href) {
		this.href = href;
	}
	/**
	 * 获取：链接
	 */
	 
	@ExcelField(title="链接", type=2, align=2, sort=9)
	
	public String getHref() {
		return href;
	}
	/**
	 * 设置：目标
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	/**
	 * 获取：目标
	 */
	 
	@ExcelField(title="目标", type=2, align=2, sort=10)
	
	public String getTarget() {
		return target;
	}
	/**
	 * 设置：描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：描述
	 */
	 
	@ExcelField(title="描述", type=2, align=2, sort=11)
	
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：排序（升序）
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：排序（升序）
	 */
	 
	@ExcelField(title="排序（升序）", type=2, align=2, sort=13)
	
	public Integer getSort() {
		return sort;
	}
	/**
	 * 设置：是否在导航中显示
	 */
	public void setInMenu(String inMenu) {
		this.inMenu = inMenu;
	}
	/**
	 * 获取：是否在导航中显示
	 */
	 
	@ExcelField(title="是否在导航中显示", type=2, align=2, sort=14)
	
	public String getInMenu() {
		return inMenu;
	}
	/**
	 * 设置：创建者
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建者
	 */
	 
	@ExcelField(title="创建者", type=2, align=2, sort=15)
	
	public String getCreateBy() {
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
	 
	@ExcelField(title="创建时间", type=2, align=2, sort=16)
	
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：更新者
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：更新者
	 */
	 
	@ExcelField(title="更新者", type=2, align=2, sort=17)
	
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：更新时间
	 */
	 
	@ExcelField(title="更新时间", type=2, align=2, sort=18)
	
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 设置：备注信息
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取：备注信息
	 */
	 
	@ExcelField(title="备注信息", type=2, align=2, sort=19)
	
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 设置：删除标记
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：删除标记
	 */
	 
	@ExcelField(title="删除标记", type=2, align=2, sort=20)
	public String getDelFlag() {
		return delFlag;
	}
	
	@ExcelField(title="首先显示位置", type=2, align=2, sort=21)
	public int getShowPosition() {
		return showPosition;
	}
	public void setShowPosition(int showPosition) {
		this.showPosition = showPosition;
	}
	
	
}
