package com.funimg.web.model;

import java.util.List;
import java.util.Map;

/**
 * 
 * easyUiTree 模型
 * 
 */
public class Tree implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;//节点id
	private String text;//节点文本
	private String state = "open";//节点状态 open,closed
	private boolean checked = false;//是否选中
	private Map<String, Object> attributes;//自定义属性 
	private List<Tree> children;//子节点
	private String iconCls;//节点前的图标
	
	
	public Tree() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public List<Tree> getChildren() {
		return children;
	}
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}


	
}
