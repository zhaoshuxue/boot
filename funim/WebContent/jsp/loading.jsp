<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
/*----------遮罩层------------*/
.black_overlay {
	display: none;
	position: fixed;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	background-color: #eee;
	z-index: 99999;
	-moz-opacity: 0.8;
	opacity: .80;
	filter: alpha(opacity = 80);
}
</style>
<!-- 遮罩层 -->
<div id="fade" class="black_overlay">
	<div id=""style="position: absolute;top: 40%;left: 45%;">
	<!--  
		<img src="http://bcs.duapp.com/wechatimages/image/loading.gif" >
	-->
		<br><br>
		<span style='font-size:20px;'>请&nbsp;稍&nbsp;后&nbsp;... </span>
	</div>
</div>

