package com.bootdo.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.bootdo.common.utils.excel.annotation.ExcelField;
import com.bootdo.system.domain.UserDO;



/**
 * 文章表
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:31
 */
public class ArticleDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//标识
	private Integer id;
	//栏目编号
	private Integer categoryId;
	//标题
	private String title;
	//文章图片
	private String image;
	//关键字
	private String keywords;
	//描述、摘要
	private String description;
	//点击数
	private Integer hits;
	//删除标记
	private String delFlag;
	//备注信息
	private String remarks;
	//创建人
	private Integer createBy;
	//创建时间
	private Date createTime;
	//状态(0未审核 -1 未通过 1通过)
	private String status;
	//评分
	private BigDecimal score;
	//推荐次数
	private Integer recommendNum;
	//是否推荐(1是 0否)
	private String isRecommend;
	//是否置顶(1是 0否)
	private String isTop;
	//文章类型
	private String type;
	//审核人
	private Integer auditBy;
	//审核时间
	private Date auditDate;
	//审核内容
	private String auditContent;
	//发布时间
	private Date publishTime;
	//是否发布
	private String isPublish;
	//推荐时间
	private Date recommendDate;
	//置顶时间
	private Date topDate;
	//点赞数量
	private Integer likeNum;
	//附件
	private String attachment;
	
	//文章内容
	private String content;
	//文章来源
	private String copyfrom;
	
	//作者
	private UserDO create;
	//审核人
	private UserDO audit;
	//专家标识 ,隔开
	private String expertIds;
	//多个字符串
	private String ids;
	//开始发布时间
	private Date beginpublishTime;
	//结束发布时间
	private Date endpublishTime;
	// URL地址
    private String url;
    // 发布时间查询
    private Integer datesearch;
    // 创建时间查询
    private Integer createdatesearch;
    //评价的专家
    private Integer expertId;
    //专家推荐
    private String isexpertrecommend;
    //评价的专家姓名
    private String expertNames;
    //专家专长分类
    private String specialty;
    //作者的 单位
    private String company;
    //作者的电话
    private String phone;
	//是否点赞 专家 新文章评分使用
    private String isLike;
    //文章评分排名
    private Integer ranking;
    //栏目名称
  	private String categoryName;
  	//文章链接
  	private String href;
  	
  	//是否同步到轮播图
  	private String isSyncAdvert;
	
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
	 * 设置：栏目编号
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取：栏目编号
	 */
	 
	@ExcelField(title="栏目编号", type=2, align=2, sort=2)
	
	public Integer getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	 
	@ExcelField(title="标题", type=2, align=2, sort=3)
	
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：文章图片
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取：文章图片
	 */
	 
	@ExcelField(title="文章图片", type=2, align=2, sort=4)
	
	public String getImage() {
		return image;
	}
	/**
	 * 设置：关键字
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	/**
	 * 获取：关键字
	 */
	 
	@ExcelField(title="关键字", type=2, align=2, sort=5)
	
	public String getKeywords() {
		return keywords;
	}
	/**
	 * 设置：描述、摘要
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：描述、摘要
	 */
	 
	@ExcelField(title="描述、摘要", type=2, align=2, sort=6)
	
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：点击数
	 */
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	/**
	 * 获取：点击数
	 */
	 
	@ExcelField(title="点击数", type=2, align=2, sort=7)
	
	public Integer getHits() {
		return hits;
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
	 
	@ExcelField(title="删除标记", type=2, align=2, sort=8)
	
	public String getDelFlag() {
		return delFlag;
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
	 
	@ExcelField(title="备注信息", type=2, align=2, sort=9)
	
	public String getRemarks() {
		return remarks;
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
	 
	@ExcelField(title="创建人", type=2, align=2, sort=10)
	
	public Integer getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	 
	@ExcelField(title="创建时间", type=2, align=2, sort=11)
	
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：状态(0未审核 -1 未通过 1通过)
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态(0未审核 -1 未通过 1通过)
	 */
	 
	@ExcelField(title="状态(0未审核 -1 未通过 1通过)", type=2, align=2, sort=12)
	
	public String getStatus() {
		return status;
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
	 
	@ExcelField(title="评分", type=2, align=2, sort=13)
	
	public BigDecimal getScore() {
		return score;
	}
	/**
	 * 设置：推荐次数
	 */
	public void setRecommendNum(Integer recommendNum) {
		this.recommendNum = recommendNum;
	}
	/**
	 * 获取：推荐次数
	 */
	 
	@ExcelField(title="推荐次数", type=2, align=2, sort=14)
	
	public Integer getRecommendNum() {
		return recommendNum;
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
	 
	@ExcelField(title="是否推荐(1是 0否)", type=2, align=2, sort=15)
	
	public String getIsRecommend() {
		return isRecommend;
	}
	/**
	 * 设置：是否置顶(1是 0否)
	 */
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	/**
	 * 获取：是否置顶(1是 0否)
	 */
	 
	@ExcelField(title="是否置顶(1是 0否)", type=2, align=2, sort=16)
	
	public String getIsTop() {
		return isTop;
	}
	/**
	 * 设置：文章类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：文章类型
	 */
	 
	@ExcelField(title="文章类型", type=2, align=2, sort=17)
	
	public String getType() {
		return type;
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
	 
	@ExcelField(title="审核人", type=2, align=2, sort=18)
	
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
	 
	@ExcelField(title="审核时间", type=2, align=2, sort=19)
	
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
	 
	@ExcelField(title="审核内容", type=2, align=2, sort=20)
	
	public String getAuditContent() {
		return auditContent;
	}
	/**
	 * 设置：发布时间
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	/**
	 * 获取：发布时间
	 */
	 
	@ExcelField(title="发布时间", type=2, align=2, sort=21)
	
	public Date getPublishTime() {
		return publishTime;
	}
	
	/**
	 * 获取：是否发布
	 */
	@ExcelField(title="是否发布", type=2, align=2, sort=22)
	
	public String getIsPublish() {
		return isPublish;
	}
	/**
	 * 设置：是否发布
	 */
	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}
	
	/**
	 * 读取： 推荐时间
	 */
	@ExcelField(title="推荐时间", type=2, align=2, sort=23)
	
	public Date getRecommendDate() {
		return recommendDate;
	}
	
	/**
	 * 设置： 推荐时间
	 */
	public void setRecommendDate(Date recommendDate) {
		this.recommendDate = recommendDate;
	}
	
	/**
	 * 读取： 置顶时间
	 */
	@ExcelField(title="置顶时间", type=2, align=2, sort=24)
	
	public Date getTopDate() {
		return topDate;
	}
	
	/**
	 * 设置： 置顶时间
	 */
	public void setTopDate(Date topDate) {
		this.topDate = topDate;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCopyfrom() {
		return copyfrom;
	}
	
	public void setCopyfrom(String copyfrom) {
		this.copyfrom = copyfrom;
	}
	
	public UserDO getCreate() {
		return create;
	}
	
	public void setCreate(UserDO create) {
		this.create = create;
	}
	
	public UserDO getAudit() {
		return audit;
	}
	
	public void setAudit(UserDO audit) {
		this.audit = audit;
	}
	
	public String getExpertIds() {
		return expertIds;
	}
	
	public void setExpertIds(String expertIds) {
		this.expertIds = expertIds;
	}
	
	public String getIds() {
		return ids;
	}
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public Date getBeginpublishTime() {
		return beginpublishTime;
	}
	
	public void setBeginpublishTime(Date beginpublishTime) {
		this.beginpublishTime = beginpublishTime;
	}
	
	public Date getEndpublishTime() {
		return endpublishTime;
	}
	
	public void setEndpublishTime(Date endpublishTime) {
		this.endpublishTime = endpublishTime;
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
    
	public Integer getLikeNum() {
		return likeNum;
	}
	
	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}
	
    public void setDatesearch(Integer datesearch) {
        this.datesearch = datesearch;
    }
    
    public Integer getDatesearch() {
        return datesearch;
    }
    
	public String getIsLike() {
		return isLike;
	}
	
	public void setIsLike(String isLike) {
		this.isLike = isLike;
	}
	public Integer getCreatedatesearch() {
		return createdatesearch;
	}
	public void setCreatedatesearch(Integer createdatesearch) {
		this.createdatesearch = createdatesearch;
	}
	public Integer getExpertId() {
		return expertId;
	}
	public void setExpertId(Integer expertId) {
		this.expertId = expertId;
	}
	public String getIsexpertrecommend() {
		return isexpertrecommend;
	}
	public void setIsexpertrecommend(String isexpertrecommend) {
		this.isexpertrecommend = isexpertrecommend;
	}
	public String getExpertNames() {
		return expertNames;
	}
	public void setExpertNames(String expertNames) {
		this.expertNames = expertNames;
	}
	 public String getSpecialty() {
			return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getIsSyncAdvert() {
		return isSyncAdvert;
	}
	public void setIsSyncAdvert(String isSyncAdvert) {
		this.isSyncAdvert = isSyncAdvert;
	}
	
}
