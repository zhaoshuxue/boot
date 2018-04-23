package com.weixin.entity.message.resp;
/**
 * 回复图片消息  —— 发送
 */
public class ImageMessage extends BaseMessage{

	// 通过上传多媒体文件，得到的id。
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	
	
	
}
