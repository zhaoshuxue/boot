<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/home_page/largeOrSmall.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/img/album.css"/>
		
		<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
		<script type="text/javascript" src="http://www.gbtags.com/gb/networks/uploads//995213fe-077d-4862-9096-27af941814d3.js" charset="utf-8"></script>
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
		
		<!-- 内容 start -->

		<div class="main">
		
<%@page import="com.funimg.web.entity.Post"%>
<%@page import="java.util.*"%>
				
				<!-- 循环 -->
        		<% 
					List<Post> list = (List)request.getAttribute("list");
				%>
				<% 
					if(list != null || list.size() > 0){
						for(int i=0; i<list.size(); i++){
				%>
        		<div class="div_col">
		            <div class="ih-item square colored effect from_top_and_bottom">
		            	<a href="${pageContext.request.contextPath}/image/show2?pnum=<%=list.get(i).getUrlNum() %>">
			                <div class="img">
			                	<img src="<%=list.get(i).getCover() %>" alt="img">
			                	<%-- <img src="http://funimg.qiniudn.com/ba365e6eaed0475eab11663ba5076728.gif" alt="img"> --%>
			                	
			                </div>
			                <div class="info">
			                    <h3><%=list.get(i).getTitle() %></h3>
			                    <p><%=list.get(i).getCreateTime() %></p>
			                </div>
		            	</a>
		            </div>
		        </div>
        		
        		<%}} %>
				
		        
		        
		        
		        
			<div style="height: 0;width: 0;clear: both;"></div>

		</div>
		
		<!-- 内容 end -->		
		
        <!-- 底部 -->
        <div class="clear"><!-- 必不可少 --></div>
        <footer>
			免责声明
			<br />
	 		<!--&nbsp;|&nbsp;-->
	 		<span>
	     		<a target="_blank" href="http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=IhQRFhUbEhYTFWJTUwxBTU8" style="text-decoration: none;";>
	          		联系邮箱
	      		</a>
	  		</span>
	  		<br />
			<span>
			      Copyright&nbsp;© 2014 Highness. All Rights Reserved.
			</span>
		</footer>
	</body>
</html>
