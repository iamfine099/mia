package com.bootdo.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件上传
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-19 16:02:20
 */
public class FileDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    // 文件类型
    private Integer type;
    // URL地址
    private String url;
    // 创建时间
    private Date createDate;
    // 附件名称
    private String fileName;
    
    //上传多张图片使用
    private String fileDate;
    private String fileType;
    private String isDel;

    
    
    public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public FileDO() {
        super();
    }


    public FileDO(Integer type, String url, Date createDate,String fileName) {
        super();
        this.type = type;
        this.url = url;
        this.createDate = createDate;
        this.fileName = fileName;
    }


    /**
     * 设置：
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：文件类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取：文件类型
     */
    public Integer getType() {
        return type;
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

    /**
     * 设置：创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    public String getFileDate() {
		return fileDate;
	}

	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	
	public String getFileType() {
		return fileType;
	}
	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	@Override
    public String toString() {
        return "FileDO{" +
                "id=" + id +
                ", type=" + type +
                ", url='" + url + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
