package com.zsx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zsx.entity.FunAlbum;
import com.zsx.entity.FunAlbumDetail;
import com.zsx.entity.FunImages;
import com.zsx.ext.FunAlbumDao;
import com.zsx.ext.FunAlbumDetailDao;
import com.zsx.ext.FunImagesDao;
import com.zsx.service.FunAlbumService;
import com.zsx.util.PageData;
import com.zsx.vo.app.AlbumData;
import com.zsx.vo.app.AlbumDetail;
import com.zsx.vo.app.AlbumList;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by highness on 2018/5/10 0010.
 */
@Service
public class FunAlbumServiceImpl implements FunAlbumService {

    @Autowired
    private FunAlbumDao funAlbumDao;
    @Autowired
    private FunAlbumDetailDao funAlbumDetailDao;
    @Autowired
    private FunImagesDao funImagesDao;


    @Override
    public PageData<AlbumList> getFunAlbumPageList(Map<String, Object> search, Integer pageNum, Integer pageSize) {

        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");

        PageHelper.startPage(pageNum, pageSize, "publish_date desc");
        List<FunAlbum> funAlbums = funAlbumDao.selectByParams(search);
        PageInfo pageInfo = new PageInfo(funAlbums);

        List<AlbumList> albumLists = Lists.newArrayList();
        AlbumList albumList = null;
        for (FunAlbum funAlbum : funAlbums) {
            albumList = new AlbumList();

            albumList.setAlbumId(funAlbum.getId());
            String title = sdf.format(funAlbum.getPublishDate()) + "：" + funAlbum.getTitle();
            albumList.setTitle(title);
            albumList.setImgUrl(funAlbum.getImgUrl());

            albumLists.add(albumList);
        }

        PageData pageData = new PageData(pageInfo.getTotal(), pageInfo.getPages(), albumLists);
        pageData.setPageNum(pageInfo.getPageNum());
        pageData.setPageSize(pageInfo.getPageSize());
        return pageData;
    }


    @Override
    public AlbumDetail getAlbumData(Long albumId) {

        FunAlbum funAlbum = funAlbumDao.selectByPrimaryKey(albumId);
        if (funAlbum == null){
            return null;
        }
        List<AlbumData> resultData = Lists.newArrayList();

        List<FunAlbumDetail> funAlbumDetails = funAlbumDetailDao.selectByAlbumId(albumId);
        if (CollectionUtils.isEmpty(funAlbumDetails)){
            return null;
        }

        AlbumData albumData;
        for (FunAlbumDetail funAlbumDetail : funAlbumDetails) {
            albumData = new AlbumData();

            albumData.setId(funAlbumDetail.getId());
            albumData.setTitle(funAlbumDetail.getTitle());

            String imgUuids = funAlbumDetail.getImgUuids();
            String[] imageIds = imgUuids.split("!@#");
            for (String imageId : imageIds) {
                FunImages funImages = funImagesDao.selectByPrimaryKey(Long.valueOf(imageId));
                if (funImages == null){
                    continue;
                }
                AlbumData.ImageList imageList = AlbumData.imgListBuilder()
                        .imgUrl(funImages.getImgUrl())
                        .type(funImages.getImgType())
                        .width(funImages.getWidth())
                        .height(funImages.getHeight());

                albumData.addImgList(imageList);
            }

            resultData.add(albumData);
        }

        return new AlbumDetail(funAlbum.getTitle(), resultData);
    }
}
