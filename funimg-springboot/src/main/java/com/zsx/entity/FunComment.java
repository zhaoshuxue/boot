package com.zsx.entity;

import java.io.Serializable;
import java.util.Date;

public class FunComment implements Serializable {
    private Long id;

    private Long albumDetailId;

    private String openid;

    private Long commentId;

    private String text;

    private Integer del;

    private Date createTime;

    private Date updateTime;

    private String creatorId;

    private String creatorName;

    private String updaterId;

    private String updaterName;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlbumDetailId() {
        return albumDetailId;
    }

    public void setAlbumDetailId(Long albumDetailId) {
        this.albumDetailId = albumDetailId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId == null ? null : creatorId.trim();
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId == null ? null : updaterId.trim();
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName == null ? null : updaterName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", albumDetailId=").append(albumDetailId);
        sb.append(", openid=").append(openid);
        sb.append(", commentId=").append(commentId);
        sb.append(", text=").append(text);
        sb.append(", del=").append(del);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", creatorId=").append(creatorId);
        sb.append(", creatorName=").append(creatorName);
        sb.append(", updaterId=").append(updaterId);
        sb.append(", updaterName=").append(updaterName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}