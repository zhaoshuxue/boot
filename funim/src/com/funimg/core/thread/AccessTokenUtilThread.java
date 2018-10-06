package com.funimg.core.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 线程刷新 access_token
 * 
 */
@Component
public class AccessTokenUtilThread extends Thread {
	
	private static Logger log = LoggerFactory.getLogger(AccessTokenUtilThread.class);

	public void init() {
		this.start();
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			try {
				if (false){//null != accessToken
					//save 
					Thread.sleep(7000);//休眠7000秒
				}else {
					// 如果access_token为null，60秒后再获取  
                    Thread.sleep(1000 * 60 * 60); 
                    log.info("休息休息一下");
                    log.info("{}{}{}{}{}{}","第一个值","2","3","4","5","6");
                    
				}
				System.out.println("线程开启");
			} catch (InterruptedException e) {
				e.printStackTrace();
				try {  
                    Thread.sleep(60 * 1000);  
                } catch (InterruptedException e1) {  
                      
                } 
			}
			
		}
	}

}