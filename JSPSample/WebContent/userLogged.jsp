<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<%response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);%>
</head>
<body>
Welcome 
<%= session.getAttribute("SessionUser") %> </br> </br>
<form method="get" action="/JSPSample/UserLogout.jsp">
<button type="submit" >Logout</button>
</form>
</body>
</html>