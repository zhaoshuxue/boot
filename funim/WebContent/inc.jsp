<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="webroot" value="${pageContext.request.contextPath }"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 
<script type="text/javascript" 
src="${pageContext.request.contextPath}/jslib/extBrowser.js" charset="utf-8"></script>
-->
<!-- 引入jQuery -->
<script src="${pageContext.request.contextPath}/jslib/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>

<!-- 引入bootstrap样式 
<link href="${pageContext.request.contextPath}/jslib/bootstrap-3.2.0-dist/css/bootstrap.min.css" rel="stylesheet" media="screen">
-->

<!--  
<link id="easyuiTheme" rel="stylesheet" href="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.3/themes/<c:out value="${cookie.easyuiThemeName.value}" default="bootstrap"/>/easyui.css" type="text/css">
-->
<!-- 引入EasyUI 
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.4/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.4/themes/default/easyui.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.4/themes/icon.css" type="text/css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js"></script>
-->

<!-- 引入my97日期时间控件 
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/My97DatePicker4.8b3/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
-->
<!-- 引入kindEditor插件 --> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/jslib/MyKindeditor/themes/default/default.css" />
<script charset="utf-8" src="${pageContext.request.contextPath}/jslib/MyKindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/jslib/MyKindeditor/zh_CN.js"></script>

