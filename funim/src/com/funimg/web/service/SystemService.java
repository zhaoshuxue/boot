package com.funimg.web.service;

import java.util.List;

import com.funimg.web.entity.PvInfo;


/**
 * 接口
 *
 */
public interface SystemService {
	void addPVInfo(String ip);
	
	List<PvInfo> getPvInfoList();
	
}
