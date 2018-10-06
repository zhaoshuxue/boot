package com.funimg.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "homepage_image")
public class HomepageImage implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", nullable = false, length = 36)
	private String id;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "sort")
	private String sort;
	
	@Column(name = "show")
	private String show;
	
	@Column(name = "height")
	private String height;
	
	
	public HomepageImage() {
	}

	public HomepageImage(String id) {
		this.id = id;
	}

	public HomepageImage(String id, String url, String sort, String show,
			String height) {
		super();
		this.id = id;
		this.url = url;
		this.sort = sort;
		this.show = show;
		this.height = height;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	

	
	

	


}
