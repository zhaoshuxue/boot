package com.zsx.vo.app;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by highness on 2018/5/10 0010.
 */
public class AlbumList implements Serializable{

    private Long albumId;

    private String title;

    private String imgUrl;

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
