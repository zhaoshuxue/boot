<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- %@include file="/inc.jsp"%>      -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>笑口常开</title>
</head>
<body>

<div onclick="re()" style="font-size: 25px; color: cornflowerblue; cursor: pointer;">${title }
	<span style="font-size: 12px; color: royalblue;">点击标题刷新</span>
</div>

<img src="${image }" alt="${title }"  width="100%" height="100%" onclick="re()" >

<div style="font-size: 12px;color: darkgrey;">免责声明:
	<div id="" style="margin-left:30px">
		本页展示图片来源于嘻嘻哈哈网,更多精彩请移步http://www.xxhh.com/
	</div>
	<div id="" style="margin-left:30px">
		如果您发现网站上有侵犯您的知识产权的作品，请与我们取得联系，我们会及时修改或删除。
	</div>
</div>
</body>
<script type="text/javascript">
	function re(){
		window.location.reload();
	}
</script>
</html>