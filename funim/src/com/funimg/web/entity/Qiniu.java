package com.funimg.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "qiniu")
public class Qiniu implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", nullable = false, length = 36)
	private String id;
	
	@Column(name = "AccessKey")
	private String AccessKey;
	
	@Column(name = "SecretKey")
	private String SecretKey;
	
	@Column(name = "uptoken")
	private String uptoken;
	
	@Column(name = "deadline")
	private String deadline;
	
	public Qiniu() {
	}

	public Qiniu(String id) {
		this.id = id;
	}

	public Qiniu(String id, String accessKey, String secretKey, String uptoken,
			String deadline) {
		super();
		this.id = id;
		AccessKey = accessKey;
		SecretKey = secretKey;
		this.uptoken = uptoken;
		this.deadline = deadline;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccessKey() {
		return AccessKey;
	}

	public void setAccessKey(String accessKey) {
		AccessKey = accessKey;
	}

	public String getSecretKey() {
		return SecretKey;
	}

	public void setSecretKey(String secretKey) {
		SecretKey = secretKey;
	}

	public String getUptoken() {
		return uptoken;
	}

	public void setUptoken(String uptoken) {
		this.uptoken = uptoken;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	

	


}
