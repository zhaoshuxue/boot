package com.zsx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zsx.entity.FunVideo;
import com.zsx.ext.FunVideoDao;
import com.zsx.service.VideoService;
import com.zsx.util.PageData;
import com.zsx.vo.app.VideoData;
import com.zsx.vo.json.JsonData;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by highness on 2018/6/6 0006.
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private FunVideoDao funVideoDao;

    @Override
    public PageData<VideoData> getFunVideoDataPageList(Map<String, Object> search, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize, "update_time desc");
        List<FunVideo> funVideos = funVideoDao.selectByParams(search);
        PageInfo pageInfo = new PageInfo(funVideos);

        List<VideoData> videoDataList = Lists.newArrayList();
        VideoData videoData = null;
        for (FunVideo funVideo : funVideos) {
            videoData = new VideoData();

            videoData.setId(funVideo.getId());
            videoData.setTitle(funVideo.getTitle());
            videoData.setThumbnail(funVideo.getThumbnail());
            videoData.setLinkUrl(funVideo.getVideoLink());
            videoData.setHeight(funVideo.getHeight());
            videoData.setWidth(funVideo.getWidth());

            videoDataList.add(videoData);
        }
        PageData pageData = new PageData(pageInfo.getTotal(), pageInfo.getPages(), videoDataList);
        pageData.setPageNum(pageInfo.getPageNum());
        pageData.setPageSize(pageInfo.getPageSize());
        return pageData;
    }

    @Override
    public PageData<FunVideo> getFunVideoPageList(Map<String, Object> search, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "id desc");
        List<FunVideo> funVideos = funVideoDao.selectByParams(search);
        PageInfo pageInfo = new PageInfo(funVideos);

        PageData pageData = new PageData(pageInfo.getTotal(), pageInfo.getPages(), funVideos);
        pageData.setPageNum(pageInfo.getPageNum());
        pageData.setPageSize(pageInfo.getPageSize());
        return pageData;
    }

    @Override
    public JsonData addFunVideo(FunVideo funVideo) {
        int i = funVideoDao.insertSelective(funVideo);
        if (i == 1) {
            return JsonData.returnObject(funVideo);
        }
        return JsonData.fail("保存失败");
    }

    @Override
    public VideoData getVideoData(Long id) {
        VideoData videoData = new VideoData();
        Map<String, Object> search = Maps.newHashMap();
        search.put("del", 0);
        search.put("lessId", id);
        PageHelper.startPage(1, 2, "id desc");
        List<FunVideo> funVideos = funVideoDao.selectByParams(search);
        if (CollectionUtils.isNotEmpty(funVideos)){
            FunVideo funVideo = funVideos.get(0);

            videoData.setId(funVideo.getId());
            videoData.setTitle(funVideo.getTitle());
            videoData.setThumbnail(funVideo.getThumbnail());
            videoData.setLinkUrl(funVideo.getVideoLink());
            videoData.setWidth(funVideo.getWidth());
            videoData.setHeight(funVideo.getHeight());

            if (funVideos.size() == 2){
                videoData.setNextId(funVideos.get(1).getId());
            }else{
                videoData.setNextId(null);
            }
        }
        return videoData;
    }
}
