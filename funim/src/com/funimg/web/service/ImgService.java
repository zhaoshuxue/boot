package com.funimg.web.service;


import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.funimg.web.entity.HomepageImage;
import com.funimg.web.entity.Image;
import com.funimg.web.entity.Post;
import com.funimg.web.model.json.JsonModel;
import com.qiniu.api.auth.AuthException;
/**
 * Image接口
 *
 */
public interface ImgService {
	/**
	 * 帖子列表
	 */
	List<Post> postList(int page, int rows);
	/**
	 * 所有相册列表
	 */
	List<Post> postList();
	/**
	 * 帖子详细
	 */
	Map<String, Object> show(String postId);
	Map<String, Object> show2(String postId);
	/**
	 * 根据post url Num 获取Post对象
	 */
	Post getPostIdByUrlNum(String UrlNum);
	/**
	 * 根据行数获取总页数
	 */
	String getTotalPageByRows(String rows);
	/**
	 * 我的帖子列表
	 */
	List<Post> myPostList(String userId);
	/**
	 * 新增帖子
	 */
	JsonModel addMyPost(String userId, String title);
	/**
	 * 删除帖子
	 */
	JsonModel delMyPost(String postId);
	/**
	 * 获取uptoken
	 */
	String getMyUptoken(String id) throws AuthException, JSONException;
	/**
	 * 添加图片信息
	 */
	Image addImage(String name, String secondName, String url, String type, String size, String uploadTime);
	/**
	 * 添加单条图文
	 */
	JsonModel addMyItem(String postId, String title, String imageIds, String description,
			String order);
	/**
	 * 获取图片列表
	 */
	List<Image> getImageList(int page, int rows);
	
	/**
	 * 修改图片信息
	 */
	boolean updateImage(Image image);
	
	
	Map<String, Object> queryImageList(String pageIndex, String pageSize,
			String sortField, String sortOrder);
	
	List<HomepageImage> home_page_show_image();
	
	Map<String, Object> queryPostList(String pageIndex, String pageSize,
			String sortField, String sortOrder);
	
	Map<String, Object> queryPostList2(String pageIndex, String pageSize,
			String sortField, String sortOrder);
	
	JsonModel savePost(String userid, String data);
	
	JsonModel updateAlbumService(String id, String title, String cover,
			String description);
	
	Map<String, Object> getImageList(String pageIndex, String pageSize,
			String sortField, String sortOrder);
	
	JsonModel saveImage(String userid, String name, String secondName, String url, String remark);
	
	JsonModel updateImageService(String id, String name, String secondName,
			String url, String remark);
	
	
	
}
