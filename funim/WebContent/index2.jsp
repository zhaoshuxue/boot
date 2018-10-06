<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="jslib/jquery.onepage-scroll/onepage-scroll.css" />

<script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script charset="utf-8" src="jslib/jquery.onepage-scroll/jquery.onepage-scroll.min.js"></script>

<style>
.a { background-color: #1bbc9b;}
.b { background-color: #FF7F50;}
.c { background-color: #4BBFC3;}
.d { background-color: #f90;}
</style>


</head>
<body>
<div class="main">
    <section class="a">第一屏</section>
    <section class="b">第二屏</section>
    <section class="c">第三屏</section>
    <section class="d">第四屏</section>
</div>

</body>
<script type="text/javascript">
	 $(function(){
	    $('.main').onepage_scroll();
	 });
</script>

</html>