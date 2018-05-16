package com.zsx.vo.app;

import java.io.Serializable;
import java.util.List;

/**
 * Created by highness on 2018/5/16 0016.
 */
public class AlbumDetail implements Serializable {

    private Long albumId;
    private Long lastAlbumId;
    private Long nextAlbumId;
    private String title;

    private List<AlbumData> albumData;


    public AlbumDetail(Long albumId, String title, List<AlbumData> albumData) {
        this.albumId = albumId;
        this.title = title;
        this.albumData = albumData;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getLastAlbumId() {
        return lastAlbumId;
    }

    public void setLastAlbumId(Long lastAlbumId) {
        this.lastAlbumId = lastAlbumId;
    }

    public Long getNextAlbumId() {
        return nextAlbumId;
    }

    public void setNextAlbumId(Long nextAlbumId) {
        this.nextAlbumId = nextAlbumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AlbumData> getAlbumData() {
        return albumData;
    }

    public void setAlbumData(List<AlbumData> albumData) {
        this.albumData = albumData;
    }
}
