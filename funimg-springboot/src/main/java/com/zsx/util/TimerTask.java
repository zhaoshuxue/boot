package com.zsx.util;

import com.zsx.service.FunAlbumService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by highness on 2018/7/25 0025.
 */
@Configuration
@EnableScheduling // 启用定时配置
public class TimerTask {

    @Scheduled(cron = "0 30 4 * * ?")
    public void name() {
//		TestServiceImpl bean = (TestServiceImpl) SpringUtil.getBean("testService");

//		TestService bean = SpringUtil.getBean(TestService.class);

//		TestService bean = SpringUtil.getBean("testService", TestService.class);

//		TestService bean = SpringUtil.getBean(TestServiceImpl.class);

        FunAlbumService funAlbumService = SpringUtil.getBean(FunAlbumService.class);
        funAlbumService.flushFunAlbum();
    }
}
