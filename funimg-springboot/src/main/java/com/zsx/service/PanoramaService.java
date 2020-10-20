package com.zsx.service;

import com.zsx.entity.FunPanoramaImage;
import com.zsx.util.PageData;
import com.zsx.vo.app.VideoData;
import com.zsx.vo.json.JsonData;

import java.util.Map;

public interface PanoramaService {

    PageData<FunPanoramaImage> getFunPanoramaImagePageList(Map<String, Object> search, Integer pageNum, Integer pageSize);

    JsonData addFunPanoramaImage(FunPanoramaImage funPanoramaImage);

    JsonData getPanoramaImage(Long id);
}
