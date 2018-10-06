<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FUNIMG</title>
<link rel="stylesheet" href="jslib/jquery.onepage-scroll/onepage-scroll.css" />
<link rel="stylesheet" type="text/css" href="style/home_page/largeOrSmall.css"/>
<link rel="stylesheet" type="text/css" href="style/home_page/loginDiv.css"/>

<script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script charset="utf-8" src="jslib/jquery.onepage-scroll/jquery.onepage-scroll.min.js"></script>

<style>
.a { background-color: #81ca9d;}
.b { background-color: #35b5d2;}
.c { background-color: #a2c371;}
.d { background-color: #323436;}

.first_page {
	position: absolute;
	width: 980px;
	height: 208px;
	top: 50%;
	left: 50%;
	margin-left: -490px;
	margin-top: -84px;
}
</style>


</head>
<body>
<!-- 头部内容——开始 -->
<div class="header">
	<div class="inner large">
		<h1>
			<a href="">
				<img src="Image/fun_img_01.png" alt="logo">
			</a>
		</h1>
		<ul class="nav">
			<li><a href="javascript:void(0);" title="首页">首页</a></li>
			<li><a href="${pageContext.request.contextPath}/image/album" title="乐图">乐图</a></li>
			<li><a href="${pageContext.request.contextPath}/image/post_list" title="图文">图文</a></li>
			<li><a href="" title="其它">其它</a></li>
		</ul>
	</div>
</div>
<!-- 头部内容——结束 -->

<!-- 全屏展示——开始 -->
<div class="main">
	<!-- 第一页 -->
	<section class="a">
	 	<div class="first_page">
			<img src="Image/just_fun_01.png" alt="">
	 	</div>
	</section>
	<!-- 第二页 -->
	<section class="b">
	 	<div id="image_show" style="text-align: center;position: relative; top:30%;">
			<!-- <img src="Image/1.gif" height="200px"/>
			<img src="Image/2.jpg" height="200px"/>
			<img src="Image/3.gif" height="200px"/>
			<img src="Image/4.gif" height="200px"/> -->
		</div>
	</section>
	<!-- 第三页 -->
	<section class="c">
	</section>
	<!-- 第四页 -->
	<section class="d">
		<footer style="border-top: 1px solid #ffffff;padding: 30px 0;
					   text-align: center;color:#fff;position: absolute;bottom: 5px;
			           width: 100%;">
			免责声明
			<br />
	 		<!--&nbsp;|&nbsp;-->
	 		<span>
	     		<a target="_blank" href="http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=IhQRFhUbEhYTFWJTUwxBTU8" style="color:white;text-decoration: none;";>
	          		联系邮箱
	      		</a>
	  		</span>
	  		<br />
			<span>
			      Copyright&nbsp;© 2014 Highness. All Rights Reserved.
			</span>
		</footer>
	</section>
</div>
<!-- 全屏展示——结束 -->		

<!-- login start -->
<div class="login_div" style="display:none;">
  	<div class="login_title">
  		<span class="denglu">登录</span>
  		<div class="close_btn">
  			<a href="javascript:void(0)" style="text-decoration: none;color:#888;"
  				onclick="login_close()">X</a>
  		</div>
  	</div>
  	<div class="login_form">
  		<form id="" class="" method="POST" autocomplete="off">
  			<p id="" class="userName_div" style="">
				<label for="userName" id="" class="pass_label_userName"></label>
				<input id="userName" type="text" name="userName" class="pass-text-input" autocomplete="off" placeholder="手机/邮箱/用户名">
			</p>
			<p id="" class="password_div" style="">
				<label for="password" id="" class="pass_label_password"></label>
				<input id="password" type="password" name="password" class="pass-text-input" placeholder="密码">
			</p>
			<p id="" style="display: none;">
				<input id="" type="checkbox" name="" class="" checked="checked">
				<label for="" id="" class="">下次自动登录</label>
			</p>
			<p id="" class="submit_div">
				<input id="" type="submit" value="登录" class="pass_button_submit">
				<a class="pass-reglink" href="" target="_blank">
					立即注册
				</a>
				<a class="pass-fgtpwd" href="" target="_blank">忘记密码？</a>
			</p>
   		</form>
   	</div>
</div>
<!-- login end -->

<style  type="text/css">
	.layer_mask {
		position:fixed;
		top:0px;
		left:0px;
		width:100%;
		height:100%;
		z-index:100000;
 		opacity:0.25;
 		background-color:rgb(0,0,0);
	}
</style>
<div class="layer_mask" style="display:none;"></div>
</body>
<script type="text/javascript">
	
	 $(function(){
		 
		$.get("${pageContext.request.contextPath}/image/homepageimage",
				function(data){
					var html = "";
					for(var i=0; i<data.length; i++){
						html += '<img src="' + data[i].url + '" height="' + data[i].height + 'px" style="margin: 0px 5px;" />';
					}
					$("#image_show").html(html);
				}
		);
		 
	    $('.main').onepage_scroll({
	    	beforeMove: function(index){
				switch(index){
					case 1:
						toLarge();
						break;
					case 2:
						toSmall();
						break;
					case 3:
						toSmall();
						break;
					case 4:
						toSmall();
						break;
				}
			}
	    });
	 });
function toSmall(){
	$('.inner').removeClass('large').addClass('small');
}
function toLarge(){
	$('.inner').removeClass('small').addClass('large');
}

function login_show(){
	$(".login_div").show();
	$(".layer_mask").show();
}
function login_close(){
	$(".login_div").hide();
	$(".layer_mask").hide();
}
</script>

</html>