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
	    <a href="javascript:void(0)" onclick="history.go(-1)">返回</a>
		<br><br><br>
	    <input type="button" value="获取session中的值" onclick="zhao()">
    	<br><br><br>
    	<div id="image"></div> 
</body>
<script type="text/javascript">
function zhao(){
	$.get("${webroot }/sys/name2",
			function(data){
				$("#image").append(data.msg);
			}
	);
}
</script>
</html>