package com.zsx.vo;

import com.zsx.entity.FunAlbumDetail;
import com.zsx.entity.FunImages;

import java.io.Serializable;
import java.util.List;

/**
 * Created by highness on 2018/5/27 0027.
 */
public class FunAlbumDetailVO extends FunAlbumDetail implements Serializable {

    List<FunImages> images;

    public List<FunImages> getImages() {
        return images;
    }

    public void setImages(List<FunImages> images) {
        this.images = images;
    }
}
