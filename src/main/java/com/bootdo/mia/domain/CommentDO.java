package com.bootdo.mia.domain;

import java.io.Serializable;
import java.util.Date;

import com.bootdo.common.domain.ArticleDO;
import com.bootdo.common.utils.excel.annotation.ExcelField;
import com.bootdo.system.domain.UserDO;



/**
 * 1)用于记录评论。
2)通过专家对文章评论产生记录。
3)该实体主要由评论审核、评论推荐等业务使
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:33
 */
public class CommentDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//评论标识
	private Integer cId;
	//专家标识
	private Integer expertId;
	private ExpertDO expert;
	//文章标识
	private Integer articleId;
	private ArticleDO article;
	//状态(0未审核 -1未通过 1通过)
	private String status;
	//评论内容
	private String cContent;
	//创建时间
	private Date createDate;
	//审核人
	private Integer auditBy;
	private UserDO user;
	//审核时间
	private Date auditDate;
	//审核内容
	private String auditContent;
	//是否推荐(1是 0否)
	private String isRecommend;
	//多个字符串
	private String ids;

	/**
	 * 设置：评论标识
	 */
	public void setcId(Integer cId) {
		this.cId = cId;
	}
	/**
	 * 获取：评论标识
	 */
	 
	@ExcelField(title="评论标识", type=2, align=2, sort=1)
	
	public Integer getcId() {
		return cId;
	}
	/**
	 * 设置：专家标识
	 */
	public void setExpertId(Integer expertId) {
		this.expertId = expertId;
	}
	/**
	 * 获取：专家标识
	 */
	 
	@ExcelField(title="专家标识", type=2, align=2, sort=2)
	
	public Integer getExpertId() {
		return expertId;
	}
	/**
	 * 设置：文章标识
	 */
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	/**
	 * 获取：文章标识
	 */
	 
	@ExcelField(title="文章标识", type=2, align=2, sort=3)
	
	public Integer getArticleId() {
		return articleId;
	}
	/**
	 * 设置：状态(0未审核 -1未通过 1通过)
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态(0未审核 -1未通过 1通过)
	 */
	 
	@ExcelField(title="状态(0未审核 -1未通过 1通过)", type=2, align=2, sort=4)
	
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：评论内容
	 */
	public void setcContent(String cContent) {
		this.cContent = cContent;
	}
	/**
	 * 获取：评论内容
	 */
	 
	@ExcelField(title="评论内容", type=2, align=2, sort=5)
	
	public String getcContent() {
		return cContent;
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
	 
	@ExcelField(title="创建时间", type=2, align=2, sort=6)
	
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
	 
	@ExcelField(title="审核人", type=2, align=2, sort=7)
	
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
	 
	@ExcelField(title="审核时间", type=2, align=2, sort=8)
	
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
	 
	@ExcelField(title="审核内容", type=2, align=2, sort=9)
	
	public String getAuditContent() {
		return auditContent;
	}
	/**
	 * 设置：是否推荐(1是 0否)
	 */
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	/**
	 * 获取：是否推荐(1是 0否)
	 */
	 
	@ExcelField(title="是否推荐(1是 0否)", type=2, align=2, sort=10)
	
	public String getIsRecommend() {
		return isRecommend;
	}
	
	public ArticleDO getArticle() {
		return article;
	}
	
	public void setArticle(ArticleDO article) {
		this.article = article;
	}
	
	public ExpertDO getExpert() {
		return expert;
	}
	
	public void setExpert(ExpertDO expert) {
		this.expert = expert;
	}
	
	public UserDO getUser() {
		return user;
	}
	
	public void setUser(UserDO user) {
		this.user = user;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}
