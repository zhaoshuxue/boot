package com.funimg.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "image")
public class Image implements java.io.Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", nullable = false, length = 36)
	private String id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "second_name")
	private String secondName;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "size")
	private String size;
	
	@Column(name = "width")
	private String width;
	
	@Column(name = "height")
	private String height;
	
	@Column(name = "uploadTime")
	private String uploadTime;
	
	@Column(name = "modifyTime")
	private String modifyTime;
	
	@Column(name = "remark")
	private String remark;
	
	public Image() {
	}

	public Image(String id) {
		this.id = id;
	}

	public Image(String id, String name, String secondName, String url, 
			String userId, String type, String size, String width,
			String height, String uploadTime, String modifyTime, String remark) {
		super();
		this.id = id;
		this.name = name;
		this.secondName = secondName;
		this.url = url;
		this.userId = userId;
		this.type = type;
		this.size = size;
		this.width = width;
		this.height = height;
		this.uploadTime = uploadTime;
		this.modifyTime = modifyTime;
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	


}
