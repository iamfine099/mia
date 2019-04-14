package com.bootdo.common.domain;

import com.bootdo.common.utils.excel.annotation.ExcelField;
import com.bootdo.system.domain.UserDO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 文章表
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:31
 */

public class AchievementDO extends ArticleDO implements Serializable {

    private String province;
    private String intro;
    private String author;
    private String tel;
    private String money;
    private String date;

    private String achievementIntro;
    private String applicationCategory;
    private String prospectAnalysis;
    private String detailInformation;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAchievementIntro() {
        return achievementIntro;
    }

    public void setAchievementIntro(String achievementIntro) {
        this.achievementIntro = achievementIntro;
    }

    public String getApplicationCategory() {
        return applicationCategory;
    }

    public void setApplicationCategory(String applicationCategory) {
        this.applicationCategory = applicationCategory;
    }

    public String getProspectAnalysis() {
        return prospectAnalysis;
    }

    public void setProspectAnalysis(String prospectAnalysis) {
        this.prospectAnalysis = prospectAnalysis;
    }

    public String getDetailInformation() {
        return detailInformation;
    }

    public void setDetailInformation(String detailInformation) {
        this.detailInformation = detailInformation;
    }
}
