package com.zsx.vo.app;

import java.io.Serializable;
import java.util.List;

/**
 * Created by highness on 2018/5/16 0016.
 */
public class AlbumDetail implements Serializable {

    private String title;

    private List<AlbumData> albumData;


    public AlbumDetail(String title, List<AlbumData> albumData) {
        this.title = title;
        this.albumData = albumData;
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
