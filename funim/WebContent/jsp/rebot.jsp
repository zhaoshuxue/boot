<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机器人聊天</title>
<style type="text/css">
body {
	background-color: #f6f6f6;
	width: 100%;
	height: 100%;
	font-family: Helvetica, "Microsoft Yahei", Arial, sans-serif;
	font-size: 14px;
	line-height: 1.42857143;
	color: #555;
	position: relative;
}

*:focus {
	outline: none;
}

div {
	display: block;
}

.display {
	display: none;
}

.exper-right {
	position: relative;
	min-height: 360px;
	height: 100%;
	background: #fff;
	border: 1px solid #ddd;
	border-radius: 4px;
	padding: 30px;
	text-align: center;
	margin: 10px;
}

.exper-right .user-speech, .exper-right .bot-speech {
	margin-bottom: 5px;
	padding: 10px;
	border: 1px solid #ddd;
	border-radius: 4px;
	clear: both;
	max-width: 420px;
	word-break: break-all;
	word-wrap: break-word
}

.bot-speech {
	float: left;
	background: #61B3E6;
	color: #fff;
	border: 1px solid #61B3E6;
}

.user-speech {
	float: right;
	text-align: left;
}

.exper-right .record {
	/*overFlow-y: scroll;*/
	overFlow-x: hidden;
	width: 100%;
	min-height: 361px;
	padding-right: 10px;
}

.exper-right .reply {
	white-space: nowrap;
	clear: both;
	margin-top: 15px;
}

.exper-right .reply input[type='text'] {
	width: 50%;
	height: 36px;
	font-size: 14px;
	border: 1px solid #bbb;
	border-radius: 2px;
	color: #888;
	padding: 0px 14px;
}

.exper-right .reply input[type='text']:focus {
	border: 1px solid #66B8EE;
	box-shadow: -1px 1px 10px #A3D4F5;
}

.exper-right .reply input[type='submit'] {
	width: 70px;
	cursor: pointer;
	height: 36px;
	border: none;
	background: #61B3E6;
	color: #fff;
	margin-right: 18px;
	padding: 0px 20px;
	border-radius: 2px;
	opacity: 0.85;
	filter: alpha(opacity=85);
}

.exper-right .reply input[type='submit']:hover {
	opacity: 1.0;
	filter: alpha(opacity=100);
}
</style>
</head>
<body>
	<div class="exper-right">
			<div id="record" class="record">
				<div class="bot-speech">您好，有什么可以帮您的吗？</div>
			</div>
			<div class="reply">
				<form action="" onsubmit="return userSpeech()">
					<input type="text" id="user-speech-text" placeholder="欢迎聊天">
					<input type="submit" value="发送">
				</form>
			</div>
		</div>
	<div id="replace_div" class="display"></div>
</body>
<script type="text/javascript">

$(function () { 

}); 

function userSpeech() {
	var text = $("#user-speech-text").val();
	text = $("#replace_div").text(text).html();
	$("#user-speech-text").val("");
	if (text == "") {
		alert("您发送的是空字符哦！");
		return false;
	}
	
	var dataType = "text";
	var uRecord = $("#record").html() + "<div class = \"user-speech\">" + text + "</div>";
	$("#record").html(uRecord);
	
	scrollScreen();

	
	//encodeURIComponent(text)
	$.post("${webroot }/sys/rebotChat",
			{
				"question" : text
			},
			function(result){
				var bRecord = $("#record").html() 
							  + "<div class = \"bot-speech\">" + result + "</div>";
				$("#record").html(bRecord);
				//
				scrollScreen();
			}, 
	dataType);

	//document.body.scrollTop = document.body.scrollTop + 200;
	$('#user-speech-text').focus();
	
	return false;
}

function scrollScreen(){
	var h = document.body.scrollTop + 300;
	$("body").animate({scrollTop: h}, 600);
}


</script>

</html>