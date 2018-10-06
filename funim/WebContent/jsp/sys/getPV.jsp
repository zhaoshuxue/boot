<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@page import="com.funimg.web.entity.PvInfo"%>
<%@page import="java.util.*"%>
<!-- 循环 -->
<% 
	List<PvInfo> list = (List)request.getAttribute("list");
%>

<% 
if(list != null || list.size() > 0){
	for(int i=0; i<list.size(); i++){
%>
	<div style="margin:25px;">
		<span style="border:1px solid #888;padding:5px;"><%=list.get(i).getId() %></span>
		<span style="border:1px solid #888;padding:5px;"><%=list.get(i).getIp() %></span>
		<span style="border:1px solid #888;padding:5px;"><%=list.get(i).getTime() %></span>
		<span style="border:1px solid #888;padding:5px;"><%=list.get(i).getPv() %></span>
		<span style="border:1px solid #888;padding:5px;"><%=list.get(i).getAddr() %></span>
	</div>
<%}} %>
</body>
<script>
$(function(){ 
	//
	
});


</script>
</html>