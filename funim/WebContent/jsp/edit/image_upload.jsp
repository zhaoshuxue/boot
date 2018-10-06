<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div style="border:1px solid #888; height:200px;">
	<img id="<%=request.getParameter("img_prev") %>" src="" width="285px" height="160px" style="float:left; margin:15px 0 0 170px;"/>
	<span class="" style="float:left;margin:78px 0 0 30px;">
		<label for="<%=request.getParameter("file") %>" style="padding: 8px;cursor: pointer;color: #fff;background-color: #428bca;border-color: #357ebd;">浏览</label>
		<br>
		<input type='file' name="img" id="<%=request.getParameter("file") %>" onchange="readURL(this,'<%=request.getParameter("img_prev") %>');" style="display:none;"/>  
	</span>
</div>
<br>
<% //accept="image/jpeg" %>
