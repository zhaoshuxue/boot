package com.zsx.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zsx.entity.FunAlbum;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by highness on 2018/5/27 0027.
 */
public class FunAlbumVO extends FunAlbum implements Serializable {


    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date publishDate;

    @Override
    public Date getPublishDate() {
        return publishDate;
    }

    @Override
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
