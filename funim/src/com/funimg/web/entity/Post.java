package com.funimg.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", nullable = false, length = 36)
	private String id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "author")
	private String author;
	
	@Column(name = "createTime")
	private String createTime;
	
	@Column(name = "modifyTime")
	private String modifyTime;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "url_num")
	private String urlNum;
	
	@Column(name = "cover")
	private String cover;
	
	@Column(name = "description")
	private String description;
	
	public Post() {
	}

	public Post(String id) {
		this.id = id;
	}

	public Post(String id, String title, String author, String createTime,
			String modifyTime, String userId, String urlNum, String cover, String description) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.userId = userId;
		this.urlNum = urlNum;
		this.cover = cover;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUrlNum() {
		return urlNum;
	}

	public void setUrlNum(String urlNum) {
		this.urlNum = urlNum;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	

	


}
