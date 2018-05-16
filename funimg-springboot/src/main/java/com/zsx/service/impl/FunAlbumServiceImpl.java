package com.zsx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zsx.entity.FunAlbum;
import com.zsx.entity.FunAlbumDetail;
import com.zsx.entity.FunHotImages;
import com.zsx.entity.FunImages;
import com.zsx.ext.FunAlbumDao;
import com.zsx.ext.FunAlbumDetailDao;
import com.zsx.ext.FunHotImagesDao;
import com.zsx.ext.FunImagesDao;
import com.zsx.service.FunAlbumService;
import com.zsx.util.PageData;
import com.zsx.vo.app.AlbumData;
import com.zsx.vo.app.AlbumDetail;
import com.zsx.vo.app.AlbumList;
import com.zsx.vo.app.ImageComment;
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
    @Autowired
    private FunHotImagesDao funHotImagesDao;


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
        if (funAlbum == null) {
            return null;
        }
        List<AlbumData> resultData = Lists.newArrayList();

        List<FunAlbumDetail> funAlbumDetails = funAlbumDetailDao.selectByAlbumId(albumId);
        if (CollectionUtils.isEmpty(funAlbumDetails)) {
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
                if (funImages == null) {
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

        AlbumDetail albumDetail = new AlbumDetail(albumId, funAlbum.getTitle(), resultData);

        Long lastAlbumId = funAlbumDao.getLastAlbumId(funAlbum.getPublishDate());
        Long nextAlbumId = funAlbumDao.getNextAlbumId(funAlbum.getPublishDate());

        albumDetail.setLastAlbumId(lastAlbumId);
        albumDetail.setNextAlbumId(nextAlbumId);
        return albumDetail;
    }


    @Override
    public PageData<AlbumData> getFunHotImagePageList(Map<String, Object> search, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize, "update_time desc");
        List<FunHotImages> funHotImages = funHotImagesDao.selectByParams(search);

        List<Long> linkIds = Lists.transform(funHotImages, new Function<FunHotImages, Long>() {
            @Override
            public Long apply(FunHotImages funHotImages) {
                return funHotImages.getLinkId();
            }
        });
        HashMap<String, Object> params = Maps.newHashMap();
        params.put("del", 0);
        params.put("ids", linkIds);
        List<FunAlbumDetail> funAlbumDetails = funAlbumDetailDao.selectByParams(params);

        ImmutableMap<Long, FunAlbumDetail> funAlbumDetailMap = Maps.uniqueIndex(funAlbumDetails, new Function<FunAlbumDetail, Long>() {
            @Override
            public Long apply(FunAlbumDetail funAlbumDetail) {
                return funAlbumDetail.getId();
            }
        });


        ArrayList<AlbumData> albumDatas = Lists.newArrayList();
        AlbumData albumData;
        for (FunHotImages funHotImage : funHotImages) {
            albumData = new AlbumData();

            FunAlbumDetail funAlbumDetail = funAlbumDetailMap.get(funHotImage.getLinkId());
            if (funAlbumDetail == null){
                continue;
            }

            albumData.setId(funAlbumDetail.getId());
            albumData.setTitle(funAlbumDetail.getTitle());
            String imgIds = funAlbumDetail.getImgUuids();
            String[] imageIds = imgIds.split("!@#");
            for (String imageId : imageIds) {
                FunImages funImages = funImagesDao.selectByPrimaryKey(Long.valueOf(imageId));
                if (funImages == null) {
                    continue;
                }
                AlbumData.ImageList imageList = AlbumData.imgListBuilder()
                        .imgUrl(funImages.getImgUrl())
                        .type(funImages.getImgType())
                        .width(funImages.getWidth())
                        .height(funImages.getHeight());

                albumData.addImgList(imageList);
            }

            albumDatas.add(albumData);
        }

        PageInfo pageInfo = new PageInfo(funHotImages);

        PageData pageData = new PageData(pageInfo.getTotal(), pageInfo.getPages(), albumDatas);
        pageData.setPageNum(pageInfo.getPageNum());
        pageData.setPageSize(pageInfo.getPageSize());
        return pageData;
    }


    @Override
    public ImageComment getImageComment(Long id) {
        ImageComment comment = new ImageComment();

        FunAlbumDetail funAlbumDetail = funAlbumDetailDao.selectByPrimaryKey(id);

        AlbumData albumData = new AlbumData();

        albumData.setId(funAlbumDetail.getId());
        albumData.setTitle(funAlbumDetail.getTitle());
        String imgIds = funAlbumDetail.getImgUuids();
        String[] imageIds = imgIds.split("!@#");
        for (String imageId : imageIds) {
            FunImages funImages = funImagesDao.selectByPrimaryKey(Long.valueOf(imageId));
            if (funImages == null) {
                continue;
            }
            AlbumData.ImageList imageList = AlbumData.imgListBuilder()
                    .imgUrl(funImages.getImgUrl())
                    .type(funImages.getImgType())
                    .width(funImages.getWidth())
                    .height(funImages.getHeight());

            albumData.addImgList(imageList);
        }

        comment.setImgData(albumData);

        return comment;
    }
}
