package com.zsx.vo.app;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZSX on 2018/5/14.
 *
 * @author ZSX
 */
public class AlbumData implements Serializable {

    private Long id;
    private String title;
    private List<ImageList> imgList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ImageList> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImageList> imgList) {
        this.imgList = imgList;
    }

    public void addImgList(ImageList imageList) {
        if (CollectionUtils.isEmpty(this.imgList)) {
            this.imgList = Lists.newArrayList();
        }
        this.imgList.add(imageList);
    }


    public static ImageList imgListBuilder(String imgUrl, Integer type, Integer width, Integer height) {
        return new ImageList(imgUrl, type, width, height);
    }

    public static ImageList imgListBuilder() {
        return new ImageList();
    }

    public static class ImageList implements Serializable {

        public ImageList() {
        }

        public ImageList(String imgUrl, Integer type, Integer width, Integer height) {
            this.imgUrl = imgUrl;
            this.type = type;
            this.width = width;
            this.height = height;
        }

        public ImageList imgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
            return this;
        }

        public ImageList type(Integer type) {
            this.type = type;
            return this;
        }

        public ImageList width(Integer width) {
            this.width = width;
            return this;
        }

        public ImageList height(Integer height) {
            this.height = height;
            return this;
        }


        private String imgUrl;
        private Integer type;
        private Integer width;
        private Integer height;


        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }
    }

}
