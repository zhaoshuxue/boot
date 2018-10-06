<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片编辑</title>
</head>
<body>
	<div>
		<label for="">页数</label><input type="text" id="page" value="1"/>
		<label for="">行数</label><input type="text" id="rows" value="5"/>
		<button id="getImageList">确定</button>
	</div>
	<div>
		<input type="text" id="image_update" style="width: 1024px; height: 30px; color: red;">
		<input type="button" id="update" value="修改"/>
		<span id="remind" style="color: green;font-family: arial;font-weight: bold;"></span>
	</div>
	<div id="imageList">
	</div>
</body>
<script>
$(function(){
	//
	$("#getImageList").click(function(){
		$.post('${webroot}/image/getImageListJson',
				{
					page : $("#page").val(), 
					rows : $("#rows").val()
				},
        		function(data){
					//JSON.stringify(jsonobj); //可以将json对象转换成json对符串 
					$("#imageList").html(JSON.stringify(data));
        		},
        		"json"
        );
    });
	
	//
	$("#update").click(function(){
		$.post('${webroot}/image/jsonUpdateImage',
				{
					image : $("#image_update").val()
				},
        		function(data){
					$("#remind").html(data);
        		}
        );
    });
});
</script>
</html>