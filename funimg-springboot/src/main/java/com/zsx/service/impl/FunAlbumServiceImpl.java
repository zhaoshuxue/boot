package com.zsx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.zsx.entity.FunAlbum;
import com.zsx.ext.FunAlbumDao;
import com.zsx.service.FunAlbumService;
import com.zsx.util.PageData;
import com.zsx.vo.app.AlbumList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by highness on 2018/5/10 0010.
 */
@Service
public class FunAlbumServiceImpl implements FunAlbumService {

    @Autowired
    private FunAlbumDao funAlbumDao;


    @Override
    public PageData<AlbumList> getFunAlbumPageList(Map<String, Object> search, Integer pageNum, Integer pageSize) {

        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");

        PageHelper.startPage(pageNum, pageSize);
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
}
