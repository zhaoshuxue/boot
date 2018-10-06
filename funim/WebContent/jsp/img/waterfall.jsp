<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>图片瀑布流</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/style/img/waterfall.css" type="text/css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-2.1.1.min.js"></script>
</head>
<body>
<div id="container">
	<ul class="col">
		<li>
			<div class="water_pic"><a href=""><img src="http://highness.qiniudn.com/just_fun_01.png" alt="" /></a></div>
			<div class="water_user">美女小清新_1</div>
		</li>
		<!--
		<li>
			<div class="water_pic"><a href=""><img src="img/3.jpg" alt="" /></a></div>
			<div class="water_user">美女小清新_3</div>
			<div class="water_option">
				<a href="" class="option_item">收藏 4</a>
				<span class="split"></span>
				<a href="" class="option_item option_comt">评论 8</a>
				<span class="split"></span>
				<a href="" class="option_item option_relay">转播 124</a>
			</div>
		</li>
		-->
	</ul>
	
	<ul class="col"></ul>
	
	<ul class="col" style="margin-right:0"></ul>
	
	<a href="javascript:" class="loadMeinvMOre" id="loadMeinvMOre">加载更多</a>
	
</div><!-- end-->

</body>
<script type="text/javascript">
$(function(){
	var page = 1;
	function loadMeinv(){
		var html = '';
		//
		$.post("${pageContext.request.contextPath}/image/getImageList",
				{
					page : page
				},
				function(data){
					for(var i =0; i < data.length; i++){
						var url = data[i].url;
						var remark = data[i].remark;
						if(url != undefined ){
							html = imageHtml(url, remark);
							$minUl = getMinUl();
							$minUl.append(html);
						}
					}
				},"JSON"
		);
		//
		page++;
	}
	//
	loadMeinv();
	//无限加载
	$(window).on("scroll", function() {
		$minUl = getMinUl();
		if ($minUl.height() <= $(window).scrollTop() + $(window).height()) {
			//当最短的ul的高度比窗口滚出去的高度+浏览器高度大时加载新图片
			//loadMeinv();//加载新图片
		}
	});
	//
	function getMinUl() {//每次获取最短的ul,将图片放到其后
		var $arrUl = $("#container .col");
		var $minUl = $arrUl.eq(0);
		$arrUl.each(function(index, elem) {
			if ($(elem).height() < $minUl.height()) {
				$minUl = $(elem);
			}
		});
		return $minUl;
	}
	//点击更多加载
	$("#loadMeinvMOre").click(function() {
		for(var i=1; i<=10; i++){
			$minUl = getMinUl();
			loadMeinv();
		}
	});
	
	function imageHtml(url, remark){
		return  '<li>' + '<div class="water_pic">' + 
				'<a href=""><img src="' + url + '" alt="" /></a>' + 
				'</div>' + 
				'<div class="water_user">' + remark + '</div>' + '</li>';
	}
	
});
</script>
</html>