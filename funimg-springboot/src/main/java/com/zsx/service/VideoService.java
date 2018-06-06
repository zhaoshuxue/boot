package com.zsx.service;

import com.zsx.entity.FunImages;
import com.zsx.entity.FunVideo;
import com.zsx.util.PageData;
import com.zsx.vo.app.AlbumData;
import com.zsx.vo.app.VideoData;
import com.zsx.vo.json.JsonData;

import java.util.Map;

/**
 * Created by highness on 2018/5/26 0026.
 */
public interface VideoService {

    PageData<VideoData> getFunVideoDataPageList(Map<String, Object> search, Integer pageNum, Integer pageSize);


//    *******************************************************************
//    web页面接口
//    *******************************************************************


    PageData<FunVideo> getFunVideoPageList(Map<String, Object> search, Integer pageNum, Integer pageSize);

    JsonData addFunVideo(FunVideo funVideo);
}
