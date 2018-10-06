<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body onload="startTimes();">
<img src="${pageContext.request.contextPath}/error/404_1.png" style="width: 250px;" alt="资源未找到" />
<div>错误代码：404</div>
<div>错误描述：资源未找到</div>

<h1 id="error">
            遇到错误，&nbsp;<span id="secondes">5</span>&nbsp;秒后将自动跳转，立即跳转请点击&nbsp;
            <a href="javascript:resetTimer();">返回</a>
        </h1>

</body>

<script language="javascript" type="text/javascript">
            var timer;
            //启动跳转的定时器
            function startTimes() {
                timer = window.setInterval(showSecondes,1000);
            }
            //定义倒计时的时间
            var i = 5;
            //显示秒数
            function showSecondes() {
                if (i > 0) {
                    i--;
                    document.getElementById("secondes").innerHTML = i;
                }
                else {
                    window.clearInterval(timer);
                    //window.open("${pageContext.request.contextPath}");
                    document.location.href = "${pageContext.request.contextPath}";
                }
            }
            //取消跳转
            function resetTimer() {
                if (timer != null && timer != undefined) {
                    window.clearInterval(timer);
                    //window.open("${pageContext.request.contextPath}");
                    document.location.href = "${pageContext.request.contextPath}";
                }
            }
</script> 


</html>