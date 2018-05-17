package com.zsx.service;

import com.zsx.util.PageData;
import com.zsx.vo.app.*;

import java.util.List;
import java.util.Map;

/**
 * Created by highness on 2018/5/10 0010.
 */
public interface FunAlbumService {

    /**
     * 给小程序提供的分页查询专辑列表接口
     *
     * @param search
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageData<AlbumList> getFunAlbumPageList(Map<String, Object> search, Integer pageNum, Integer pageSize);

    AlbumDetail getAlbumData(Long albumId);

    PageData<AlbumData> getFunHotImagePageList(Map<String, Object> search, Integer pageNum, Integer pageSize);

    ImageComment getImageComment(Long id);

    List<Comment> getImageCommentList(Long albumDetailId);

}
