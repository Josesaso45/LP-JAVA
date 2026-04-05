<%@page import="jdk.internal.org.jline.terminal.TerminalBuilder.SystemOutput"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1> JSP Page</h1>
	
	<p> Hola Mundo desde JSP</p>
	
	<%
		int edad = 35;
		double salario = 1300;
		double descuento = 0.30 * salario;
		
		out.print("La edad que tengo es: "+ edad);
	
	%>	
	<br>
	<br>
	<%
		out.print("El sueldo que manejo actualmente es: " + salario + " Y mi descuento es: " + descuento);

	%>
</body>
</html>