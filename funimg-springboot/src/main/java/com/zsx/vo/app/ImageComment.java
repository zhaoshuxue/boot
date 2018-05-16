package com.zsx.vo.app;

import java.io.Serializable;

/**
 * Created by highness on 2018/5/16 0016.
 */
public class ImageComment implements Serializable {

    private AlbumData imgData;

    public AlbumData getImgData() {
        return imgData;
    }

    public void setImgData(AlbumData imgData) {
        this.imgData = imgData;
    }
}
