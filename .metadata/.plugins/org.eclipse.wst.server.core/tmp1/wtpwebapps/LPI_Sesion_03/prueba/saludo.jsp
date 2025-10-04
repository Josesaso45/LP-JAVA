<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String nombre = request.getAttribute("nombre").toString();
		int edad = Integer.parseInt((request.getAttribute("edad")).toString());
	%>
	
	<div class="container">
		<div class="card">
			<div class="card-body">
				Hola <%=nombre%>, tu edad es <%=edad %>
			</div>
		</div>
	</div>
</body>
</html>