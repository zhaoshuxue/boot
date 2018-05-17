package com.zsx.vo.app;

import java.io.Serializable;
import java.util.List;

/**
 * Created by highness on 2018/5/16 0016.
 */
public class ImageComment implements Serializable {

    private AlbumData imgData;

    private List<Comment> comments;

    public AlbumData getImgData() {
        return imgData;
    }

    public void setImgData(AlbumData imgData) {
        this.imgData = imgData;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
