package com.funimg.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.print.attribute.ResolutionSyntax;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JMenu;

import org.hamcrest.core.Is;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.funimg.core.util.IpUtil;
import com.funimg.core.util.JsonUtil;
import com.funimg.core.util.QiniuUtil;
import com.funimg.web.entity.HomepageImage;
import com.funimg.web.entity.Image;
import com.funimg.web.entity.Post;
import com.funimg.web.entity.PvInfo;
import com.funimg.web.model.json.JsonModel;
import com.funimg.web.service.ImgService;
import com.funimg.web.service.SystemService;
import com.funimg.web.service.UserService;
import com.funimg.web.util.DateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.qiniu.api.auth.AuthException;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.regexp.internal.REUtil;
/**
 * 图片
 */
@Controller
@RequestMapping("image")
public class ImgController {

	@Autowired
	private UserService userService;
	@Autowired
	private ImgService imgService;
	@Autowired
	private SystemService systemService;
	
	/**
	 * 跳转到图片表格页面
	 */
	@RequestMapping("/imagegrid")
	public String toMiniuiImageGrid(){
		return "jsp/sys/image_grid";
	}
	
	/**
	 * 跳转到相册页面
	 */
	@RequestMapping("/album")
	public String gotoAlbumPage(HttpServletRequest request, HttpServletResponse response){
		List<Post> list = imgService.postList();
		request.setAttribute("list", list);
		String ip = IpUtil.getIpAddr(request);
		systemService.addPVInfo(ip);
		return "jsp/img/album";
	}
	
	/**
	 * 跳转到相册表格页面
	 */
	@RequestMapping("/albumgrid")
	public String toMiniuiAlbumGrid(){
		return "jsp/edit/post_grid";
	}
	
	/**
	 * 获取相册列表Json 格式数据
	 */
	@RequestMapping("getAlbumListJson")
	public void getAlbumListJson(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		
		Map<String, Object> map = imgService.queryPostList(pageIndex, pageSize, sortField, sortOrder);
		
		response.getWriter().write(new GsonBuilder().setPrettyPrinting().create().toJson(map));
	}
	
	/**
	 * 获取相册列表Json 格式数据
	 */
	@RequestMapping("getAlbumListJson2")
	public void getAlbumListJson2(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		String pageIndex = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		String sortField = request.getParameter("sidx");
		String sortOrder = request.getParameter("sord");
		
		Map<String, Object> map = imgService.queryPostList2(pageIndex, pageSize, sortField, sortOrder);
		
		response.getWriter().write(new GsonBuilder().setPrettyPrinting().create().toJson(map));
	}
	
	/**
	 * 保存相册列表
	 */
	@RequestMapping("/savePost")
	@ResponseBody
	public void savePost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		String userId = "1";
		String json = request.getParameter("data");
		JsonModel jModel = imgService.savePost(userId, json);
		JsonUtil.objectToJson(jModel, response);
	}
	
	/**
	 * 修改相册信息
	 */
	@RequestMapping("/updateAlbum")
	@ResponseBody
	public void updateAlbum(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String cover = request.getParameter("cover");
		String description = request.getParameter("description");
		
		JsonModel jModel = imgService.updateAlbumService(id, title, cover, description);
		JsonUtil.objectToJson(jModel, response);
	}
	
	/**
	 * 跳转到帖子列表
	 */
	@RequestMapping(value = "/post_list")
	public ModelAndView toPostList(HttpServletRequest request, HttpServletResponse response) {
		//分页
		int page = 1;//当前页
		int rows = 20;//每页显示的行数
		String pageString = (request.getParameter("page") == null) ? "1" : request.getParameter("page");
		page = Integer.valueOf(pageString);
//		rows = Integer.valueOf(request.getParameter("rows"));
		List<Post> list = imgService.postList(page, rows);
		
		request.setAttribute("list", list);
		
		request.setAttribute("curpage", pageString);
		request.setAttribute("totalpage", imgService.getTotalPageByRows(Integer.toString(rows)));
		//
		String ip = IpUtil.getIpAddr(request);
		systemService.addPVInfo(ip);
		
		return new ModelAndView("/jsp/img/post_list");
	}
	
	/**
	 * 跳转到帖子详细内容
	 */
	@RequestMapping(value = "/show")
	public String showImage(HttpServletRequest request, HttpServletResponse response) {
		
		Post post = imgService.getPostIdByUrlNum(request.getParameter("pnum"));
		if (post != null) {
			Map<String, Object> map = imgService.show(post.getId());
			request.setAttribute("map", map);
			request.setAttribute("title", post.getTitle());
			return "jsp/img/show";
		}else {
			return "jsp/error/500";
		}
		
	}
	
	
	/**
	 * 跳转到帖子详细内容
	 */
	@RequestMapping(value = "/show2")
	public String showImage2(HttpServletRequest request, HttpServletResponse response) {
		
		Post post = imgService.getPostIdByUrlNum(request.getParameter("pnum"));
		if (post != null) {
			Map<String, Object> map = imgService.show(post.getId());
			request.setAttribute("map", map);
			request.setAttribute("title", post.getTitle());
			return "jsp/img/show";
		}else {
			return "jsp/error/500";
		}
		
	}
	
	
	/**
	 * 获取我的帖子列表 —— 编辑
	 */
	@RequestMapping("getMyPost")
	public String getMyPostList(HttpServletRequest request,
			HttpServletResponse response){
		String userId = "1";
		List<Post> list = imgService.myPostList(userId);
		request.setAttribute("list", list);
		return "jsp/edit/getMyPost";
	}
	
	/**
	 * 新增帖子
	 * @throws IOException 
	 */
	@RequestMapping("addMyPost")
	public void addMyPostList(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String userId = "1";
		String title = request.getParameter("title");
		JsonModel jModel = imgService.addMyPost(userId, title);
		JsonUtil.objectToJson(jModel, response);
	}
	
	/**
	 * 添加用户
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public void addUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		// 多文件
		/**
		 * form 里面 可以多个input file name相同  ，  也可以 一个input file 多个文件  multiple="multiple"
		 */
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = new LinkedList<MultipartFile>();  
        files = multipartRequest.getFiles("img");  
        
        
        
        for (MultipartFile file : files) {
			System.out.println(file.getOriginalFilename());
			System.out.println(file.getInputStream().available());
			System.out.println();
			
			
		}
		
		// 单文件
//		InputStream input = null;
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		MultipartFile mfile = multipartRequest.getFile("img");
//		String filename = mfile.getOriginalFilename();
//		try {
//			input = mfile.getInputStream();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//
//		System.out.println(filename);
//		System.out.println(input.available());
	}
	
	
	/**
	 * 跳转到帖子编辑页面
	 */
	@RequestMapping("/toeditPost")
	public String toEditPost(HttpServletRequest request,
			HttpServletResponse response){
//		request.getParameter(arg0)
		return "jsp/edit/edit_post";
	}
	
	/**
	 * 删除帖子
	 */
	@RequestMapping("delPost")
	public void delPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String postId = request.getParameter("postId");
		JsonModel jModel = imgService.delMyPost(postId);
		JsonUtil.objectToJson(jModel, response);
	}
	
	
	/**
	 * 添加单条图文
	 */
	@RequestMapping("/addItem")
	@ResponseBody
	public void addItem(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AuthException, JSONException {
		request.setCharacterEncoding("UTF-8");
		//文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = new LinkedList<MultipartFile>();  
        files = multipartRequest.getFiles("img");  
        //存储图片ID
        StringBuilder sb = new StringBuilder();
        for (MultipartFile file : files) {
        	InputStream is = file.getInputStream();
        	//排除没有文件的input file
        	int fileSize = is.available();
        	if (is.available() > 0) {
        		//name 原文件名
				String name = file.getOriginalFilename();
				//文件格式 ———— 后缀名 
				String fileType = name.substring(name.lastIndexOf("."));
				//secondName 生成的uuID文件名
				String secondName = UUID.randomUUID().toString().replace("-", "") + fileType;
        		//上传
        		boolean res = QiniuUtil.uploadImageToQiniu(imgService.getMyUptoken("1"), is, secondName);
    			if (res) {
    				//url
    				String url = "http://highnessss.qiniudn.com/" + secondName;
    							  
    				//保存图片信息
    				Image image = imgService.addImage(name, secondName, url, fileType, fileSize + "", DateUtil.timeFormat());
    				//拼接图片id
    				sb.append(image.getId());
    				sb.append(",");
    			}
			}
		}
        //描述
        String content = request.getParameter("editor");
        //貌似暂时不要双引号 html 也可以解析
//        content = content.replace("\"", "");
//        System.out.println(content.replace("\"", ""));//&quot; &apos;
        //
		String postId = request.getParameter("postId");
		//顺序
		String sort = request.getParameter("sort");
		//顺序
		String title = request.getParameter("title");
		if (title == null || "null".equals(title)) {
			title = "";
		}
		//添加
		JsonModel jModel = imgService.addMyItem(postId, title, sb.toString(), content, sort);
		JsonUtil.objectToJson(jModel, response);
	}

	
	/**
	 * 跳转到PV页面
	 */
	@RequestMapping("/pv")
	public String toPV(HttpServletRequest request,
			HttpServletResponse response){
		request.setAttribute("list", systemService.getPvInfoList());
		return "jsp/sys/getPV";
	}
	
	/**
	 * 跳转到PV页面
	 */
	@RequestMapping("/pv2")
	public String getPV_echarts(){
		return "jsp/sys/getPV_echarts";
	}
	
	/**
	 * 
	 */
	@RequestMapping("pvDataList")
	public void getPV_echarts_data_list(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<PvInfo> pvInfoList = systemService.getPvInfoList();
		for (PvInfo pvInfo : pvInfoList) {
			String time = pvInfo.getTime();
			time = time.substring(0, 10);
			
			boolean containsKey = map.containsKey(time);
			if (containsKey) {
				Object object = map.get(time);
				int pv = Integer.valueOf(object.toString()) + pvInfo.getPv();
				map.put(time, pv);
			}else {
				map.put(time, pvInfo.getPv());
			}
		}
		
		List<String> list_key = new ArrayList<>();
		List<Integer> list_value = new ArrayList<>();
		
		for(Map.Entry<String, Object> entry : map.entrySet()){
			list_key.add(entry.getKey());
			list_value.add((Integer) entry.getValue());
		}
		
		Collections.reverse(list_key);
		Collections.reverse(list_value);
		
		map = new HashMap<String, Object>();
		map.put("x", list_key);
		map.put("y", list_value);
		
		response.getWriter().write(new GsonBuilder().setPrettyPrinting().create().toJson(map));
	}
	
	/**
	 * 获取图片列表
	 * @throws IOException 
	 */
	@RequestMapping("getImageList")
	public void getImageList(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String page = request.getParameter("page");
		int num = Integer.valueOf(page);
		List<Image> list = imgService.getImageList(num, 10);
//		request.setAttribute("list", list);
		response.getWriter().write(new GsonBuilder().setPrettyPrinting().create().toJson(list));
//		return "jsp/img/waterfall_imageList";
	}
	
	/**
	 * 获取图片列表Json 格式数据
	 * @throws IOException 
	 */
	@RequestMapping("getImageListJson2")
	public void getImageListJson2(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		
		Map<String, Object> map = imgService.queryImageList(pageIndex, pageSize, sortField, sortOrder);
		
		response.getWriter().write(new GsonBuilder().setPrettyPrinting().create().toJson(map));
	}
	
	/**
	 * 获取图片列表Json 格式数据
	 * @throws IOException 
	 */
	@RequestMapping("getImageListJson")
	public void getImageListJson(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		List<Image> list = imgService.getImageList(Integer.valueOf(page), Integer.valueOf(rows));
		
		response.getWriter().write(new GsonBuilder().setPrettyPrinting().create().toJson(list));
	}
	
	/**
	 * 获取图片列表Json 格式数据
	 */
	@RequestMapping("getImageListJson3")
	public void getImageListJson3(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		String pageIndex = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		String sortField = request.getParameter("sidx");
		String sortOrder = request.getParameter("sord");
		
		Map<String, Object> map = imgService.getImageList(pageIndex, pageSize, sortField, sortOrder);
		
		response.getWriter().write(new GsonBuilder().setPrettyPrinting().create().toJson(map));
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/saveImage")
	@ResponseBody
	public void saveImage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		String userId = "1";
		String json = request.getParameter("data");
		String[] strs = json.split("&");
		String name = strs[0].split("=")[1];
		String secondName = strs[1].split("=")[1];
		String url = strs[2].split("=")[1];
		String remark = strs[3].split("=")[1];
		JsonModel jModel = imgService.saveImage(userId, name, secondName, url, remark);
		JsonUtil.objectToJson(jModel, response);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/updateImage")
	@ResponseBody
	public void updateImage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String secondName = request.getParameter("secondName");
		String url = request.getParameter("url");
		String remark = request.getParameter("remark");
		
		JsonModel jModel = imgService.updateImageService(id, name, secondName, url, remark);
		JsonUtil.objectToJson(jModel, response);
	}
	
	/**
	 * 修改图片信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("jsonUpdateImage")
	public void jsonUpdateImage(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		//单个对象时，json不需要加[]
		String json = "{\"id\": \"1\",\"name\": \"1.jpg\", \"secondName\": \"safsafsaf.jpg\", \"url\": \"http://highness.qiniudn.com/just_fun_01.png\",\"userId\": \"1\",\"type\": \"png\"}";
		json = request.getParameter("image").trim();
		PrintWriter out = response.getWriter();
		try {
			Image image = JSON.parseObject(json, Image.class);
			if (imgService.updateImage(image)) {
				out.write("修改成功");
			}else {
				out.write("修改失败");
			}
		} catch (Exception e) {
			out.write("修改失败");
		}
		
//		String jsonList = "[{\"id\": \"1\",\"name\": \"1.jpg\", \"secondName\": \"safsafsaf.jpg\", \"url\": \"http://highness.qiniudn.com/just_fun_01.png\",\"userId\": \"1\",\"type\": \"png\"},{\"id\": \"1\",\"name\": \"1.jpg\", \"secondName\": \"safsafsaf.jpg\", \"url\": \"http://highness.qiniudn.com/just_fun_01.png\",\"userId\": \"1\",\"type\": \"png\"},{\"id\": \"1\",\"name\": \"1.jpg\", \"secondName\": \"safsafsaf.jpg\", \"url\": \"http://highness.qiniudn.com/just_fun_01.png\",\"userId\": \"1\",\"type\": \"png\"}]";
//		List<Image> list = JSON.parseArray(jsonList, Image.class);
//		System.out.println(list.size());
	}
	
	/**
	 * 
	 */
	@RequestMapping("homepageimage")
	public void homePageImage(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		List<HomepageImage> list = imgService.home_page_show_image();
		response.getWriter().write(new GsonBuilder().setPrettyPrinting().create().toJson(list));
	}
}
