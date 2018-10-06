<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/home_page/largeOrSmall.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/img/post_list.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/public/pagination.css"/>
		
		<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
		<script type="text/javascript" src="http://bcs.duapp.com/extend/js/backtop.js" charset="utf-8"></script>
	</head>
	<body>
		<!--头部 -->
		<div class="header">
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
					</li> -->
				</ul>

			</div>

		</div>
		<!-- 描述：头部 end  -->
        <nav class="nav_div">
        	
        </nav>
        
        <div class="title">乐图 >>></div>
        
<%@page import="com.funimg.web.entity.Post"%>
<%@page import="java.util.*"%>
        <!-- 列表 -->
        <center class="center_div">
        	<div class="items_list">
        		<div class="items_top">
        			
        		</div>
        		<!-- 循环 -->
        		<% 
					List<Post> list = (List)request.getAttribute("list");
				%>
				
				<% 
					if(list != null || list.size() > 0){
						for(int i=0; i<list.size(); i++){
				%>
        		<div class="item">
        			<div class="item_num">*</div>
        			<div class="item_title">
        				<a href="${pageContext.request.contextPath}/image/show?pnum=<%=list.get(i).getUrlNum() %>"><%=list.get(i).getTitle() %></a>
        			</div>
        			<div class="item_author item_style"><%=list.get(i).getAuthor() %></div>
        			<div class="item_date item_style"><%=list.get(i).getCreateTime() %></div>
        			<div class="item_other"></div>
        		</div>
        		<%}} %>
        		
        		
            	<!-- 分页 -->
            	<% 
            		int curPage = Integer.valueOf(request.getAttribute("curpage").toString());
					int totalPage = Integer.valueOf(request.getAttribute("totalpage").toString());
					int beforePage = 5;
				%>
            	<div class="pagination">
            		<span class="page_num">第<%=curPage %>页,共<%=totalPage %>页</span>
            		<% if(curPage == 1){ %>
						<span class="page_num">上一页</span>
					<%} else { %>
						<a class="page_num" href="${pageContext.request.contextPath}/image/post_list?page=<%=curPage - 1%>">上一页</a>
					<%} %>
					
					<% for(int i=(curPage > (beforePage + 1) && totalPage > (beforePage + 4)) ? curPage - beforePage : 1; i<= (((curPage + beforePage) > totalPage) ? totalPage : (curPage + beforePage)); i++){%>
						<% if(i == curPage){ %>
							<a class="page_num cur" href="${pageContext.request.contextPath}/image/post_list?page=<%=curPage%>"><%=i %></a>
						<% }  else{%>
							<a class="page_num" href="${pageContext.request.contextPath}/image/post_list?page=<%=i%>" ><%=i %></a>
						<% } %>
					<% }%>
				   	<% if(curPage == totalPage){ %>
						<span  class="page_num">下一页</span>
					<%} else { %>
						<a class="page_num" href="${pageContext.request.contextPath}/image/post_list?page=<%=curPage + 1%>">下一页</a>
					<%} %>
            	</div>
        		 
        	</div>
        	
        </center>
        
        <!-- 底部 -->
        <div class="clear"><!-- 必不可少 --></div>
        <footer>
        	<span>
		        Copyright&nbsp;© 2014 By Highness. All Rights Reserved.
		    </span>
        </footer>
	</body>
</html>
