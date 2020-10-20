package com.zsx.service.impl;

import com.zsx.entity.FunPanoramaImage;
import com.zsx.ext.FunPanoramaImageDao;
import com.zsx.service.PanoramaService;
import com.zsx.util.PageData;
import com.zsx.vo.json.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PanoramaServiceImpl implements PanoramaService {

    @Autowired
    private FunPanoramaImageDao funPanoramaImageDao;

    @Override
    public PageData<FunPanoramaImage> getFunPanoramaImagePageList(Map<String, Object> search, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public JsonData addFunPanoramaImage(FunPanoramaImage funPanoramaImage) {
        return null;
    }
}
