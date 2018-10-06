<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/home_page/largeOrSmall.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/img/show.css"/>
		
		<script type="text/javascript" src="http://bcs.duapp.com/extend/js/backtop.js" charset="utf-8"></script>
	</head>
	<body style="height: 100%;">
		<div class="header" style="position: absolute;">
			<div class="inner small">
				<h1 class="hh">
					<a href="${pageContext.request.contextPath}">
						<img src="${pageContext.request.contextPath}/Image/fun_img_01.png" alt="logo">
					</a>
				</h1>
				<ul class="nav">
					<!--  
					<li><a href="" title="首页">首页</a>
					</li>
					<li><a href="" title="代码">代码</a>
					</li>
					<li><a href="" title="素材">素材</a>
					</li>
					<li><a href="" title="模板">模板</a>
					</li>-->
				</ul>

			</div>

		</div>
		
		<!-- head end -->
		
		<!-- 内容 start -->

		<div class="main">
			<div class="title">
				<span><%=request.getAttribute("title") %></span>
				<a href="${pageContext.request.contextPath}/image/album">
					<img src="${pageContext.request.contextPath}/Image/left_arrow.png" />
				</a>
			</div>
			
			<div class="img_list">
<%@page import="com.funimg.web.entity.PostImage"%>
<%@page import="com.funimg.web.entity.Image"%>
<%@page import="java.util.*"%>
		<% 
			Map<String,Object> map = (Map)request.getAttribute("map");
			List<PostImage> list = (List)map.get("list");
			List<Image> imageList = new ArrayList<Image>();
		%>
		
		<% for(int i=0; i<list.size(); i++){%>
				<!-- 循环 -->
				<div class="imgs">
					<div class="num">
						<%=i+1 %>
					</div>
					<div class="image_title">
						<%=list.get(i).getTitle() %>
					</div>
					<!-- 图片循环 -->
					<% imageList = (List)map.get(list.get(i).getId()); %>
					<% for(int j=0; j<imageList.size(); j++){ %>
					<div class="img_show">
						<img src="<%=imageList.get(j).getUrl() %>"/>
					</div>
					<%} %>

					<div class="description">
						<%=list.get(i).getDescription() %>
					</div>
					<div class="other">
						
					</div>
				</div>
				
		<%} %>
				
			</div>
		</div>
		
		<!-- 内容 end -->
		
		
		
	</body>
</html>
