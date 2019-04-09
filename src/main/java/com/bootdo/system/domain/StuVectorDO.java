package com.bootdo.system.domain;

import java.io.Serializable;
import java.util.Date;

import com.bootdo.common.utils.excel.annotation.ExcelField;



/**
 * 学生向量表
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-08-22 09:15:15
 */
public class StuVectorDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Integer fId;
	//学生编号
	private String fStuId;
	//入学成绩
	private Float fEntranceGrade;
	//学习成绩
	private Float fGrade;
	//德育成绩
	private Float fMoralEdu;
	//外语水平
	private String fLanguageLevel;
	//计算机水平
	private String fComputerLevel;
	//政治面貌
	private String fPoliticalStatus;
	//备注信息
	private String fMemo;
	
	//入学成绩最大最小值
	private Float maxFEntranceGrade;
	private Float minFEntranceGrade;
	
	//学习成绩最大最小值
	private Float maxFGrade;
	private Float minFGrade;
	
	//德育成绩最大最小值
	private Float maxFMoralEdu;
	private Float minFMoralEdu;
	
	//单位性质
	private String fUnitCategoryId;
	private String fUnitCategory;
	//单位名称
	private String fUnitName;
	
	
	
	public String getfUnitName() {
		return fUnitName;
	}
	public void setfUnitName(String fUnitName) {
		this.fUnitName = fUnitName;
	}
	public String getfUnitCategoryId() {
		return fUnitCategoryId;
	}
	public void setfUnitCategoryId(String fUnitCategoryId) {
		this.fUnitCategoryId = fUnitCategoryId;
	}
	public String getfUnitCategory() {
		return fUnitCategory;
	}
	public void setfUnitCategory(String fUnitCategory) {
		this.fUnitCategory = fUnitCategory;
	}
	public Float getMaxFEntranceGrade() {
		return maxFEntranceGrade;
	}
	public void setMaxFEntranceGrade(Float maxFEntranceGrade) {
		this.maxFEntranceGrade = maxFEntranceGrade;
	}
	public Float getMinFEntranceGrade() {
		return minFEntranceGrade;
	}
	public void setMinFEntranceGrade(Float minFEntranceGrade) {
		this.minFEntranceGrade = minFEntranceGrade;
	}
	public Float getMaxFGrade() {
		return maxFGrade;
	}
	public void setMaxFGrade(Float maxFGrade) {
		this.maxFGrade = maxFGrade;
	}
	public Float getMinFGrade() {
		return minFGrade;
	}
	public void setMinFGrade(Float minFGrade) {
		this.minFGrade = minFGrade;
	}
	public Float getMaxFMoralEdu() {
		return maxFMoralEdu;
	}
	public void setMaxFMoralEdu(Float maxFMoralEdu) {
		this.maxFMoralEdu = maxFMoralEdu;
	}
	public Float getMinFMoralEdu() {
		return minFMoralEdu;
	}
	public void setMinFMoralEdu(Float minFMoralEdu) {
		this.minFMoralEdu = minFMoralEdu;
	}
	public Integer getfId() {
		return fId;
	}
	public void setfId(Integer fId) {
		this.fId = fId;
	}
	public String getfStuId() {
		return fStuId;
	}
	public void setfStuId(String fStuId) {
		this.fStuId = fStuId;
	}
	public Float getfEntranceGrade() {
		return fEntranceGrade;
	}
	public void setfEntranceGrade(Float fEntranceGrade) {
		this.fEntranceGrade = fEntranceGrade;
	}
	public Float getfGrade() {
		return fGrade;
	}
	public void setfGrade(Float fGrade) {
		this.fGrade = fGrade;
	}
	public Float getfMoralEdu() {
		return fMoralEdu;
	}
	public void setfMoralEdu(Float fMoralEdu) {
		this.fMoralEdu = fMoralEdu;
	}
	public String getfLanguageLevel() {
		return fLanguageLevel;
	}
	public void setfLanguageLevel(String fLanguageLevel) {
		this.fLanguageLevel = fLanguageLevel;
	}
	public String getfComputerLevel() {
		return fComputerLevel;
	}
	public void setfComputerLevel(String fComputerLevel) {
		this.fComputerLevel = fComputerLevel;
	}
	public String getfPoliticalStatus() {
		return fPoliticalStatus;
	}
	public void setfPoliticalStatus(String fPoliticalStatus) {
		this.fPoliticalStatus = fPoliticalStatus;
	}
	public String getfMemo() {
		return fMemo;
	}
	public void setfMemo(String fMemo) {
		this.fMemo = fMemo;
	}

}
