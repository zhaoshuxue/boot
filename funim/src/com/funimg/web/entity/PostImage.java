package com.funimg.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post_image")
public class PostImage implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", nullable = false, length = 36)
	private String id;
	
	@Column(name = "post_id")
	private String postId;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "image_ids")
	private String imageIds;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "sort")
	private String sort;
	
	@Column(name = "createTime")
	private String createTime;
	
	@Column(name = "remark")
	private String remark;
	
	
	public PostImage() {
	}

	public PostImage(String id) {
		this.id = id;
	}

	public PostImage(String id, String postId, String title, String imageIds,
			String description, String sort, String createTime, String remark) {
		super();
		this.id = id;
		this.postId = postId;
		this.title = title;
		this.imageIds = imageIds;
		this.description = description;
		this.sort = sort;
		this.createTime = createTime;
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getImageIds() {
		return imageIds;
	}

	public void setImageIds(String imageIds) {
		this.imageIds = imageIds;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	

	


}
