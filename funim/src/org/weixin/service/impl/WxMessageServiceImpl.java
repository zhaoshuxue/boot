package org.weixin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weixin.dao.WxMessageDao;
import org.weixin.entity.WxMessage;
import org.weixin.service.WxMessageService;

@Service
@Transactional
public class WxMessageServiceImpl implements WxMessageService {
	
	@Autowired
	private WxMessageDao wxMessageDao;
	
	@Override
	public Map<String, Object> getWxMessageList(String pageIndex, String pageSize, String sortField, String sortOrder) {
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from WxMessage ";
		
		if (sortField != null && !"".equals(sortField)) {
			hql += " order by " + sortField + " " + sortOrder;
		}
		
		List<WxMessage> list = wxMessageDao.find(hql, Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
		
		Long count = wxMessageDao.count("select count(*) " + hql);
		
		if (count % Long.valueOf(pageSize) == 0) {
			map.put("totalpages", count / Long.valueOf(pageSize));
		}else {
			map.put("totalpages", count / Long.valueOf(pageSize) + 1);
		}
		
		map.put("currpage", pageIndex);
		map.put("totalrecords", count);
		map.put("data", list);
		
		return map;
	}
}
