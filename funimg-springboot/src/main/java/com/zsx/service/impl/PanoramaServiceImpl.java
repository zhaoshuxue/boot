package com.zsx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsx.entity.FunPanoramaImage;
import com.zsx.ext.FunPanoramaImageDao;
import com.zsx.service.PanoramaService;
import com.zsx.util.PageData;
import com.zsx.vo.json.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PanoramaServiceImpl implements PanoramaService {

    @Autowired
    private FunPanoramaImageDao funPanoramaImageDao;

    @Override
    public PageData<FunPanoramaImage> getFunPanoramaImagePageList(Map<String, Object> search, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "id desc");
        List<FunPanoramaImage> list = funPanoramaImageDao.selectByParams(search);
        PageInfo pageInfo = new PageInfo(list);

        PageData pageData = new PageData(pageInfo.getTotal(), pageInfo.getPages(), list);
        pageData.setPageNum(pageInfo.getPageNum());
        pageData.setPageSize(pageInfo.getPageSize());
        return pageData;
    }

    @Override
    public JsonData addFunPanoramaImage(FunPanoramaImage funPanoramaImage) {
        int i = funPanoramaImageDao.insertSelective(funPanoramaImage);
        if (i == 1) {
            return JsonData.returnObject(funPanoramaImage);
        }
        return JsonData.fail("保存失败");
    }
}
