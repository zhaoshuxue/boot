package com.funimg.web.model;

import java.util.ArrayList;
import java.util.List;
/**
 * EasyUI DataGrid模型
 * @author zsx
 *
 */
@SuppressWarnings("unchecked")
public class DataGrid {

	private int total;
	private List rows;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
	

	
}
