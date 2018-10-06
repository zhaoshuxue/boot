<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="com.funimg.web.entity.Image"%>
<%@page import="java.util.*"%>

<% 
	List<Image> list = (List)request.getAttribute("list");
%>
<% for(int i=0; i<list.size(); i++){%>
		<li>
			<div class="water_pic">
				<a href=""><img src="<%=list.get(i).getUrl() %>" alt="" /></a>
			</div>
			<div class="water_user"><%=list.get(i).getRemark() %></div>
		</li>
<%} %>
