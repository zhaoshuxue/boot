package com.zsx.vo.app;

import java.io.Serializable;

/**
 * Created by ZSX on 2018/5/17.
 *
 * @author ZSX
 */
public class Comment implements Serializable {

    private Long albumDetailId; // 被评论图文的id

    private String nickName; // 评论人昵称
    private String headImg; // 评论人头像地址
    private String text; // 评论内容
    private String toNickName; // 被回复人昵称

    private Long commentId; // 当前评论的主键id
    private String createTime;

    private String fromUser; // 评论人openid
    private String toUser; // 被回复人openid


    public Long getAlbumDetailId() {
        return albumDetailId;
    }

    public void setAlbumDetailId(Long albumDetailId) {
        this.albumDetailId = albumDetailId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getToNickName() {
        return toNickName;
    }

    public void setToNickName(String toNickName) {
        this.toNickName = toNickName;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
}
