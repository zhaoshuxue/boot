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
	<div style="margin:25px;">
		<span style="border:1px solid #888;padding:5px;"><%=list.get(i).getId() %></span>
		<span style="border:1px solid #888;padding:5px;"><%=list.get(i).getTitle() %></span>
		<span style="border:1px solid #888;padding:5px;"><%=list.get(i).getAuthor() %></span>
		<span style="border:1px solid #888;padding:5px;"><%=list.get(i).getCreateTime() %></span>
		<span style="border:1px solid #888;padding:5px;"><%=list.get(i).getUrlNum() %></span>
		<a href="${webroot }/image/toeditPost?postId=<%=list.get(i).getId() %>">编辑</a>
		<a href="javascript:void(0)" onclick="delPost('<%=list.get(i).getId() %>');">删除</a>
	</div>
<%}} %>
<input type="text" id="title"/>
<br/>
<input type="checkbox" id="zhuan" onclick="zhuanfa();" >
<br>
<input type="button" id="add_btn" value="新增"/>
</body>
<script>
$(function(){ 
	//
	var add_btn = $("#add_btn");
	add_btn.click(function(){
		$.post("${webroot }/image/addMyPost",
				{
					"title" : $('#title').val()
				},
				function(data){
					location.reload();
				}
		);
	});
	
	
});

function zhuanfa(){
	document.getElementById('zhuan').checked ? $('#title').val('[转]') : $('#title').val('');
}

function delPost(id){
	$.post("${webroot }/image/delPost",
			{
				postId : id
			},
			function(data){
				location.reload();
			}
	);
}


</script>
</html>