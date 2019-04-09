package com.bootdo.mia.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.bootdo.common.utils.excel.annotation.ExcelField;



/**
 * 1)用于记录专家对文章的推荐和评分。
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:35
 */
public class ExperevaluateDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//评价标识
	private Integer eetId;
	//文章标识
	private Integer articleId;
	//专家标识
	private Integer expertId;
	//评分
	private BigDecimal score;
	//评分时间
	private Date scoreDate;
	//是否推荐(1是 0否)
	private String isRecommend;
	//推荐时间
	private Date recommendDate;
	//是否点赞
	private String isLike;
	//点赞时间
	private Date likeDate;
	//文章标题
	private String title;
	//文章作者
	private String memName;
	//评荐专家
	private String expertName;
	//文章发布时间
	private Date publishTime;

	
	/**
	 * 设置：评价标识
	 */
	public void setEetId(Integer eetId) {
		this.eetId = eetId;
	}
	/**
	 * 获取：评价标识
	 */
	 
	@ExcelField(title="评价标识", type=2, align=2, sort=1)
	
	public Integer getEetId() {
		return eetId;
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
	 
	@ExcelField(title="文章标识", type=2, align=2, sort=2)
	
	public Integer getArticleId() {
		return articleId;
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
	 
	@ExcelField(title="专家标识", type=2, align=2, sort=3)
	
	public Integer getExpertId() {
		return expertId;
	}
	/**
	 * 设置：评分
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * 获取：评分
	 */
	 
	@ExcelField(title="评分", type=2, align=2, sort=4)
	
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置：评分时间
	 */
	public void setScoreDate(Date scoreDate) {
		this.scoreDate = scoreDate;
	}
	/**
	 * 获取：评分时间
	 */
	 
	@ExcelField(title="评分时间", type=2, align=2, sort=5)
	
	public Date getScoreDate() {
		return scoreDate;
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
	 
	@ExcelField(title="是否推荐(1是 0否)", type=2, align=2, sort=6)
	
	public String getIsRecommend() {
		return isRecommend;
	}
	/**
	 * 设置：推荐时间
	 */
	public void setRecommendDate(Date recommendDate) {
		this.recommendDate = recommendDate;
	}
	/**
	 * 获取：推荐时间
	 */
	 
	@ExcelField(title="推荐时间", type=2, align=2, sort=7)
	
	public Date getRecommendDate() {
		return recommendDate;
	}
	
	public String getIsLike() {
		return isLike;
	}
	
	public void setIsLike(String isLike) {
		this.isLike = isLike;
	}
	
	public Date getLikeDate() {
		return likeDate;
	}
	
	public void setLikeDate(Date likeDate) {
		this.likeDate = likeDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getExpertName() {
		return expertName;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
}
