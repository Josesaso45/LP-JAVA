<%@page import="entidades.Almacen"%>
<%@page import="entidades.Producto"%>
<%@page import="entidades.Proveedor"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
</head>
<body>

	<%@include file="/shared/navbar.jsp" %>

	<div class="container">
		<h1>Lista de Productos</h1>
		
		<%
			ArrayList<Producto> lista = (ArrayList<Producto>)request.getAttribute("lista");
			
			String texto = "";
			if (request.getParameter("texto") != null) {
				texto = request.getParameter("texto");
			}
		%>
		
		<div class="row" >
			<div class="col" >
				<a href="producto?opcion=editar" class="btn btn-sm btn-success"><i class="bi bi-person-plus"></i>Agregar</a>
			</div>
			<div style="width: 200px">
				<form id="formBuscar" action="curso" method="get" >
					<input type="text" name="texto" class="form-control" placeholder="Buscar datos..." value="<%= texto %>" />
				</form>
			</div>	
		</div>
		
		<table class="table table-striped table-hover table-sm" >
			<thead>
				<tr>
					<th>Nombre</th>
					<th>Descripcion</th>
					<th>Precio</th>
					<th>Stock</th>
					<th>Proveedor</th>
					<th>Almacen</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<% for(Producto p : lista) { %>
					<tr>
						<td><%= p.getNombre() %></td>
						<td><%= p.getDescripcion() %></td>
						<td><%= p.getPrecio() %></td>
						<td><%= p.getStock() %></td>
						<td><%= p.getNombreProveedor()  %>></td>
						<td><%= p.getNombreAlmacen()  %>></td>
						<td>
							<div class="btn-group">
								<a class="btn btn-sm btn-primary" href="producto?opcion=editar&id=<%=p.getProductoId()%>"><i class="bi bi-pencil-fill"></i> Editar</a>
								<a class="btn btn-sm btn-danger" href="javascript:eliminar(<%=p.getProductoId()%>)"><i class="bi bi-x-circle"></i> Eliminar</a>
							</div>
						</td>
					</tr>
				<% } %>
			</tbody>
		</table>
		
	</div>
	
	<form id="formEliminar" action="producto?opcion=eliminar" method="post">
		<input type="hidden" name="id" />
	</form>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js" integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q" crossorigin="anonymous"></script>
	<script type="text/javascript">
		const eliminar = (id) => {
			const respuesta = confirm('¿Desea eliminar el curso?');
			if (respuesta) {
				document.querySelector('#formEliminar input[name=id]').value = id;
				document.getElementById('formEliminar').submit();
			}
		};
	</script>
</body>
</html>