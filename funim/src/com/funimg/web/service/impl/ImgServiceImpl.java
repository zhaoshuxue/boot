package com.funimg.web.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.funimg.core.util.GetShortUUID;
import com.funimg.core.util.QiniuUtil;
import com.funimg.web.dao.HomepageImageDao;
import com.funimg.web.dao.ImageDao;
import com.funimg.web.dao.PostDao;
import com.funimg.web.dao.PostImageDao;
import com.funimg.web.dao.QiniuDao;
import com.funimg.web.dao.UserDao;
import com.funimg.web.entity.HomepageImage;
import com.funimg.web.entity.Image;
import com.funimg.web.entity.Post;
import com.funimg.web.entity.PostImage;
import com.funimg.web.entity.Qiniu;
import com.funimg.web.entity.User;
import com.funimg.web.model.json.JsonModel;
import com.funimg.web.service.ImgService;
import com.funimg.web.util.DateUtil;
import com.qiniu.api.auth.AuthException;

@Service
@Transactional
public class ImgServiceImpl implements ImgService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private PostDao postDao;
	@Autowired
	private PostImageDao postImageDao;
	@Autowired
	private HomepageImageDao homepageImageDao;
	@Autowired
	private ImageDao imageDao;
	@Autowired
	private QiniuDao qiniuDao;

	@Override
	public List<Post> postList(int page, int rows) {
		return postDao.find("from Post p order by p.createTime desc", page,
				rows);
	}

	@Override
	public List<Post> postList() {
		return postDao.find("from Post p order by p.createTime desc");
	}

	@Override
	public Map<String, Object> queryPostList(String pageIndex, String pageSize,
			String sortField, String sortOrder) {
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from Post ";

		if (sortField != null && !"".equals(sortField)) {
			hql += " order by " + sortField + " " + sortOrder;
		}

		List<Post> list = postDao.find(hql, Integer.valueOf(pageIndex),
				Integer.valueOf(pageSize));

		Long count = postDao.count("select count(*) " + hql);

		map.put("data", list);
		map.put("total", count);

		return map;
	}

	@Override
	public Map<String, Object> queryPostList2(String pageIndex,
			String pageSize, String sortField, String sortOrder) {
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from Post ";

		if (sortField != null && !"".equals(sortField)) {
			hql += " order by " + sortField + " " + sortOrder;
		}

		List<Post> list = postDao.find(hql, Integer.valueOf(pageIndex),
				Integer.valueOf(pageSize));

		Long count = postDao.count("select count(*) " + hql);

		if (count % Long.valueOf(pageSize) == 0) {
			map.put("totalpages", count / Long.valueOf(pageSize));
		} else {
			map.put("totalpages", count / Long.valueOf(pageSize) + 1);
		}

		map.put("currpage", pageIndex);
		map.put("totalrecords", count);
		map.put("data", list);

		return map;
	}

	@Override
	public Map<String, Object> show(String postId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PostImage> postImageList = new ArrayList<PostImage>();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("postId", postId);

		List<PostImage> postImages = postImageDao.find(
				"from PostImage p where p.postId = :postId order by p.sort ",
				params);
		if (postImages.size() > 0) {
			for (PostImage postImage : postImages) {
				// 添加条数
				postImageList.add(postImage);

				// 添加单条中的图片
				String imageIdStr = postImage.getImageIds();
				List<Image> imageList = new ArrayList<Image>();
				if (imageIdStr != null) {
					String[] imageIds = imageIdStr.split(",");
					for (int j = 0; j < imageIds.length; j++) {
						Image image = imageDao.get(Image.class, imageIds[j]);
						// 获取不到图片对象则 不添加进去，避免jsp出错
						if (image != null) {
							imageList.add(image);
						}
					}
				}
				// 添加单条中的图片
				map.put(postImage.getId(), imageList);

			}
		}
		map.put("list", postImageList);
		return map;
	}

	@Override
	public Map<String, Object> show2(String postId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PostImage> postImageList = new ArrayList<PostImage>();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("postId", postId);

		List<PostImage> postImages = postImageDao
				.find("from PostImage p where p.postId = :postId order by p.createTime desc ",
						params);
		if (postImages.size() > 0) {
			for (PostImage postImage : postImages) {
				// 添加条数
				postImageList.add(postImage);

				// 添加单条中的图片
				String imageIdStr = postImage.getImageIds();
				List<Image> imageList = new ArrayList<Image>();
				if (imageIdStr != null) {
					String[] imageIds = imageIdStr.split(",");
					for (int j = 0; j < imageIds.length; j++) {
						Image image = imageDao.get(Image.class, imageIds[j]);
						// 获取不到图片对象则 不添加进去，避免jsp出错
						if (image != null) {
							imageList.add(image);
						}
					}
				}
				// 添加单条中的图片
				map.put(postImage.getId(), imageList);

			}
		}
		map.put("list", postImageList);
		return map;
	}

	@Override
	public Post getPostIdByUrlNum(String UrlNum) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("urlNum", UrlNum);
		return postDao.get("from Post p where p.urlNum = :urlNum ", params);
	}

	@Override
	public String getTotalPageByRows(String rows) {
		long count = postDao.count("select count(*) from Post as post");
		System.out.println("count : = " + count);
		int row = Integer.valueOf(rows);
		int total = (int) (count % row);
		if (total == 0) {
			// 如果整除，则返回相除的值
			return Long.toString(count / row);
		} else {
			// 不能整除，说明有富余的行，就进位成整页
			return Long.toString(count / row + 1);
		}
	}

	@Override
	public List<Post> myPostList(String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return postDao
				.find("from Post p where p.userId = :userId order by p.createTime desc",
						params);
	}

	@Override
	public JsonModel addMyPost(String userId, String title) {
		JsonModel jModel = new JsonModel();

		Post post = new Post();
		post.setId(UUID.randomUUID().toString());
		post.setAuthor(userDao.get(User.class, userId).getName());
		post.setTitle(title);
		post.setCreateTime(DateUtil.timeFormat());
		post.setUserId(userId);
		post.setUrlNum(GetShortUUID.generateShortUuid());

		postDao.save(post);
		jModel.setSuccess(true);
		jModel.setMsg("OK");
		return jModel;
	}

	/**
	 * 删除帖子
	 */
	@Override
	public JsonModel delMyPost(String postId) {
		JsonModel jModel = new JsonModel();
		Post post = postDao.get(Post.class, postId);
		if (post != null) {
			postDao.delete(post);
			//
			List<PostImage> list = postImageDao
					.find("from PostImage p where p.postId = '" + postId + "' ");
			for (int i = 0; i < list.size(); i++) {
				postImageDao.delete(list.get(i));
			}

			jModel.setSuccess(true);
			jModel.setMsg("OK");
		} else {
			jModel.setSuccess(false);
			jModel.setMsg("NO");
		}
		return jModel;
	}

	@Override
	public JsonModel addMyItem(String postId, String title, String imageIds,
			String description, String order) {
		JsonModel jModel = new JsonModel();

		PostImage postImage = new PostImage();
		postImage.setId(UUID.randomUUID().toString());
		postImage.setPostId(postId);
		postImage.setTitle(title);
		postImage.setImageIds(imageIds);
		postImage.setDescription(description);
		postImage.setSort(order);

		postImageDao.save(postImage);

		jModel.setSuccess(true);
		jModel.setMsg("");
		return jModel;
	}

	@Override
	public Image addImage(String name, String secondName, String url,
			String type, String size, String uploadTime) {
		Image image = new Image();
		image.setId(UUID.randomUUID().toString());
		image.setName(name);
		image.setSecondName(secondName);
		image.setUrl(url);
		image.setType(type);
		image.setSize(size);
		image.setUploadTime(uploadTime);

		imageDao.save(image);
		return image;
	}

	public String getMyUptoken(String id) throws AuthException, JSONException {
		Qiniu qiniu = qiniuDao.get(Qiniu.class, id);
		if (qiniu != null) {
			long deadline = Long.valueOf(qiniu.getDeadline());
			if (System.currentTimeMillis() > deadline) {
				String uptoken = QiniuUtil.getUptoken();
				qiniu.setUptoken(uptoken);
				qiniu.setDeadline(Long.toString(System.currentTimeMillis() + 1000 * 3500));
				qiniuDao.update(qiniu);
				return uptoken;
			} else {
				return qiniu.getUptoken();
			}
		}
		return null;
	}

	// public JsonModel checkUsernameIsExist(String name){
	// JsonModel json = new JsonModel();
	// User user = userDao.get("from User t where t.userName = '" + name.trim()
	// + "'");
	// if (user == null) {
	// json.setSuccess(true);
	// json.setMsg("用户名不存在");
	// }else {
	// json.setSuccess(false);
	// json.setMsg("用户名已经存在");
	// }
	// return json;
	// }

	@Override
	public List<Image> getImageList(int page, int rows) {
		return imageDao.find("from Image ", page, rows);
	}

	@Override
	public Map<String, Object> queryImageList(String pageIndex,
			String pageSize, String sortField, String sortOrder) {
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from Image ";

		if (sortField != null && !"".equals(sortField)) {
			hql += " order by " + sortField + " " + sortOrder;
		}

		List<Image> list = imageDao.find(hql, Integer.valueOf(pageIndex),
				Integer.valueOf(pageSize));

		Long count = imageDao.count("select count(*) " + hql);

		map.put("data", list);
		map.put("total", count);

		return map;
	}

	@Override
	public Map<String, Object> getImageList(String pageIndex, String pageSize,
			String sortField, String sortOrder) {
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from Image ";

		if (sortField != null && !"".equals(sortField)) {
			hql += " order by " + sortField + " " + sortOrder;
		}

		List<Image> list = imageDao.find(hql, Integer.valueOf(pageIndex),
				Integer.valueOf(pageSize));

		Long count = imageDao.count("select count(*) " + hql);

		if (count % Long.valueOf(pageSize) == 0) {
			map.put("totalpages", count / Long.valueOf(pageSize));
		} else {
			map.put("totalpages", count / Long.valueOf(pageSize) + 1);
		}

		map.put("currpage", pageIndex);
		map.put("totalrecords", count);
		map.put("data", list);

		return map;
	}

	@Override
	public boolean updateImage(Image image) {
		try {
			imageDao.update(image);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<HomepageImage> home_page_show_image() {
		return homepageImageDao
				.find("from HomepageImage where show = '1' order by sort asc ");
	}

	@Override
	public JsonModel savePost(String userId, String data) {
		JsonModel jModel = new JsonModel();

		JSONArray array = JSON.parseArray(data);
		for (int i = 0; i < array.size(); i++) {
			JSONObject object = array.getJSONObject(i);
			String _state = object.getString("_state");
			if ("added".equals(_state)) {
				Post post = new Post();
				post.setId(UUID.randomUUID().toString());
				post.setTitle(object.getString("title"));
				post.setAuthor(userDao.get(User.class, userId).getName());
				post.setCover(object.getString("cover"));
				post.setDescription(object.getString("description"));
				post.setCreateTime(DateUtil.timeFormat());
				post.setModifyTime(DateUtil.timeFormat());
				post.setUrlNum(GetShortUUID.generateShortUuid());
				post.setUserId(userId);

				postDao.save(post);

			} else if ("modified".equals(_state)) {
				Post post = postDao.get(Post.class, object.getString("id"));
				if (post != null) {
					if (object.getString("title") != null) {
						post.setTitle(object.getString("title"));
					}
					if (object.getString("cover") != null) {
						post.setCover(object.getString("cover"));
					}
					if (object.getString("description") != null) {
						post.setDescription(object.getString("description"));
					}

					post.setModifyTime(DateUtil.timeFormat());
					postDao.update(post);
				}

			} else if ("removed".equals(_state)) {
				Post post = postDao.get(Post.class, object.getString("id"));
				if (post != null) {
					postDao.delete(post);
				}
			}
		}
		jModel.setSuccess(true);
		jModel.setMsg("操作成功！");
		return jModel;
	}

	@Override
	public JsonModel updateAlbumService(String id, String title, String cover,
			String description) {
		JsonModel jModel = new JsonModel();

		Post post = postDao.get(Post.class, id);
		if (post != null) {
			post.setTitle(title);
			post.setCover(cover);
			post.setDescription(description);

			post.setModifyTime(DateUtil.timeFormat());
			postDao.update(post);

			jModel.setSuccess(true);
			jModel.setMsg("修改成功！");
		}

		return jModel;
	}

	@Override
	public JsonModel saveImage(String userid, String name, String secondName,
			String url, String remark) {
		JsonModel jModel = new JsonModel();

		Image image = new Image();
		image.setId(UUID.randomUUID().toString());
		image.setName(name);
		image.setSecondName(secondName);
		image.setUrl(url);
		image.setRemark(remark);

		image.setUploadTime(DateUtil.timeFormat());
		image.setUserId(userid);

		imageDao.save(image);

		jModel.setSuccess(true);
		jModel.setMsg("添加成功！");

		return jModel;
	}

	@Override
	public JsonModel updateImageService(String id, String name,
			String secondName, String url, String remark) {
		JsonModel jModel = new JsonModel();
		Image image = imageDao.get(Image.class, id);
		if (image != null) {
			image.setName(name);
			image.setSecondName(secondName);
			image.setUrl(url);
			image.setRemark(remark);
			image.setModifyTime(DateUtil.timeFormat());

			imageDao.update(image);

			jModel.setSuccess(true);
			jModel.setMsg("修改成功！");
		} else {
			jModel.setSuccess(false);
			jModel.setMsg("修改失败！");
		}
		return jModel;
	}

}
