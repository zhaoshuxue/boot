<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
		<br><br><br>
		输入值存入session中
    	<input type="text" id="name">
    	<input type="button" value="提交" onclick="zhao()">
    	<br><br><br>
    	<div id="image"></div>
    	<br><br><br>  
		<a href="${webroot}/sys/test4">打开新的页面获取session值</a>
    	

</body>
<script type="text/javascript">
	function zhao(){
		$.post("${webroot }/sys/name",
				{
					"name" : $("#name").val()
				},
				function(data){
					$("#image").append(data.msg);
				}
		);
	}
</script>
</html>