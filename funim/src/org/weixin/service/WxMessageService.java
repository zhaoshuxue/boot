package org.weixin.service;

import java.util.Map;



/**
 * 接口
 */
public interface WxMessageService {
	
	/**
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @return
	 */
	public Map<String, Object> getWxMessageList(String pageIndex,
			String pageSize, String sortField, String sortOrder);
		
}
