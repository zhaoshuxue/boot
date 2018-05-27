package com.zsx.service;

import com.zsx.entity.FunImages;
import com.zsx.util.PageData;
import com.zsx.vo.app.AlbumList;
import com.zsx.vo.json.JsonData;

import java.util.Map;

/**
 * Created by highness on 2018/5/26 0026.
 */
public interface ImageService {

    PageData<FunImages> getFunImagesPageList(Map<String, Object> search, Integer pageNum, Integer pageSize);

    JsonData addFunImages(FunImages funImages);
}
