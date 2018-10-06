<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style/home_page/register.css"/>
</head>
<body>
<div class="main">
	
	<div class="header">
		<div class="logo">LOGO</div>
		<div class="login-link">
			<span>我已注册，现在就</span>
			<button class="login-btn" id="login_btn">登录</button>
		</div>
	</div>
	
	<div id="register" class="register">
	<form action="" id="" method="post">
	<table id="tab" border="0" cellspacing="0" cellpadding="0" class="">
		<tbody>
			<tr>
				<th class="titip"><span class="must">*</span>用户名&nbsp;:&nbsp;</th>
				<td>
					<input class="" id="user_name" maxlength="20" name=""
					placeholder="全站唯一" size="30" type="text" onblur="checkusername()">
					<p class="remind">3-20个字符，允许中文、英文字母、数字和符号</p>
				</td>
			</tr>
	
			<tr>
				<th class="titip"><span class="must">*</span>邮箱&nbsp;:&nbsp;</th>
				<td>
					<input class="" id="user_email" name="" size="30"
					type="text">
					<ul>
						<li class="remind">请填写常用邮箱，以便我们给您发送安全服务邮件</li>
					</ul>
				</td>
			</tr>
			<tr>
				<th class="titip"><span class="must">*</span>密码&nbsp;:&nbsp;</th>
				<td>
					<input class="" id="user_password" name=""
					placeholder="6-20个字符" size="30" type="password">
					<p class="remind">长度为6~14个字符,支持数字、大小写字母和标点符号,不允许有空格</p>
				</td>
			</tr>
			<tr>
				<th><span class="must">*</span>确认密码&nbsp;:&nbsp;</th>
				<td>
					<input class="required" id="user_confirm" size="30" type="password"
					onBlur="validatePwd();">
				</td>
			</tr>
	
			<tr>
				<td colspan="2" class="line">&nbsp;</td>
			</tr>
	
			<tr>
				<th><span class="must">*</span>验证码&nbsp;:&nbsp;</th>
				<td>
					<input class="required" id="validateCode" size="30" type="text"
					maxlength="4"> 
					<span> 
						<img id="verify" title="验证码图片" alt="验证码图片" class="pass-verifyCode"
						src="${webroot }/validatecode"  onclick="reloadcode()" style="cursor: pointer; vertical-align: middle;">
					</span> 
					<a onclick="reloadcode()" href="javascript:void(0);" class="change">换一张</a>
				</td>
			</tr>
	
			<tr>
				<td colspan="2" class="line">&nbsp;</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td><input name="checkbox" type="checkbox" id="auto"
					checked="checked"> <label for="auto" class="auto">我已经阅读并同意&nbsp;</label>
				<a href="#" target="_blank" class="clause">注册条款</a></td>
			</tr>
		</tbody>
	</table>
	
	<div class="reg-sms" style="">
		<div class="tip"></div>
		<div id="warn" style="display:none;">
			<div class="tishi">提示</div>
			<div id="warn_msg" class="tsmsg"></div>
		</div>
	</div>
	
	<div class="">
		<input type="button" name="button" id="button"
		value="注　册" class="submit_btn">
	</div>
	</form>
	</div>
	
	
	<div class="footer">
		<div class="">
			<div class="copy-box">2014&nbsp;©Org</div>
		</div>
	</div>

</div>
</body>
</html>