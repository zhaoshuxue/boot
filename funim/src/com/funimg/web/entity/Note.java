package com.funimg.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "note")
public class Note implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", nullable = false, length = 36)
	private String id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "addTime")
	private String addTime;
	
	@Column(name = "modifyTime")
	private String modifyTime;
	
	@Column(name = "book_id")
	private String bookId;
	
	public Note() {
	}

	public Note(String id) {
		this.id = id;
	}

	public Note(String id, String name, String note, String addTime,
			String modifyTime, String bookId) {
		super();
		this.id = id;
		this.name = name;
		this.note = note;
		this.addTime = addTime;
		this.modifyTime = modifyTime;
		this.bookId = bookId;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	

	


}
