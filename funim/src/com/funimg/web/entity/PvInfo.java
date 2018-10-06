package com.funimg.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pv_info")
public class PvInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", nullable = false, length = 36)
	private String id;
	
	@Column(name = "ip")
	private String ip;
	
	@Column(name = "addr")
	private String addr;
	
	@Column(name = "time")
	private String time;
	
	@Column(name = "pv")
	private Integer pv;
	
	public PvInfo() {
	}

	public PvInfo(String id) {
		this.id = id;
	}

	public PvInfo(String id, String ip, String addr, String time, Integer pv) {
		super();
		this.id = id;
		this.ip = ip;
		this.addr = addr;
		this.time = time;
		this.pv = pv;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	


}
