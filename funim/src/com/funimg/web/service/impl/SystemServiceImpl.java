package com.funimg.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.funimg.core.util.IpUtil;
import com.funimg.web.dao.PvInfoDao;
import com.funimg.web.entity.PvInfo;
import com.funimg.web.service.SystemService;
import com.funimg.web.util.DateUtil;

@Service
@Transactional
public class SystemServiceImpl implements SystemService {

	@Autowired
	private PvInfoDao	pvInfoDao;
	
	public void addPVInfo(String ip){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ip", ip);
		PvInfo pvInfo = pvInfoDao.get("from PvInfo p where p.ip = :ip",params);
		if (pvInfo != null) {
			pvInfo.setTime(DateUtil.timeFormat());
			pvInfo.setPv(pvInfo.getPv() + 1);
			
			pvInfoDao.update(pvInfo);
		}else {
			PvInfo pvInfo2 = new PvInfo();
			
			pvInfo2.setId(UUID.randomUUID().toString());
			pvInfo2.setIp(ip);
			pvInfo2.setAddr(IpUtil.getIpInfo(ip));
			pvInfo2.setTime(DateUtil.timeFormat());
			pvInfo2.setPv(1);
			
			pvInfoDao.save(pvInfo2);
		}
	}

	public List<PvInfo> getPvInfoList(){
		return pvInfoDao.find("from PvInfo p order by p.time desc ");
	}




}
