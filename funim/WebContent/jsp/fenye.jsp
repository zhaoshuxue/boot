<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<% 
	int curPage = Integer.valueOf(request.getParameter("curpage"));
	int totalPage = Integer.valueOf(request.getParameter("totalpage"));
	int beforePage = 5;
	String reqUrl = request.getRequestURL().toString();
%>
<body>
<!--分页-->
<div id="pages">
	<% if(curPage == 1){ %>
		<span>上一页</span>
	<%} else { %>
		<a href="<%=reqUrl %>?totalpage=<%=totalPage %>&curpage=<%=curPage - 1%>">上一页</a>
	<%} %>
	
	<% for(int i=(curPage > (beforePage + 1) && totalPage > (beforePage + 4)) ? curPage - beforePage : 1; i<= (((curPage + beforePage) > totalPage) ? totalPage : (curPage + beforePage)); i++){%>
		<% if(i == curPage){ %>
			<a href="<%=reqUrl %>?totalpage=<%=totalPage %>&curpage=<%=curPage%>" style="font-size:50px;color:red;"><%=i %></a>
		<% }  else{%>
			<a href="<%=reqUrl %>?totalpage=<%=totalPage %>&curpage=<%=i%>" ><%=i %></a>
		<% } %>
	<% }%>
   	<% if(curPage == totalPage){ %>
		<span>下一页</span>
	<%} else { %>
		<a href="<%=reqUrl %>?totalpage=<%=totalPage %>&curpage=<%=curPage + 1%>">下一页</a>
	<%} %>
     
</div>
</body>
</html>