<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@page import="com.funimg.web.entity.Notebook"%>
<%@page import="com.funimg.web.entity.Note"%>
<%@page import="java.util.*"%>
		<% 
			List<Notebook> notebook = (List)request.getAttribute("notebook");
			List<Note> note = (List)request.getAttribute("note");
		%>
		<% for(int i=0; i<notebook.size(); i++){%>
			note book name : <%=notebook.get(i).getName() %>
		<%} %>
		<br><br><br><br><br><br><br>
		<% if(note.size() != 0){%>
			<% for(int i=0; i<note.size(); i++){%>
				note name : <%=note.get(i).getName() %>
				<br>
				note : <%=note.get(i).getNote() %>
			<%} %>
		<%} %>
</body>
</html>