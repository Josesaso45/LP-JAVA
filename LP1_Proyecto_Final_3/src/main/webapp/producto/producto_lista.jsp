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
<title>Mantenimiento de productos</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
</head>
<body>

	<%@include file="/shared/navbar.jsp" %>
	<div class="container mt-3 mb-5" >
		<nav style="--bs-breadcrumb-divider: url(&#34;data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='8' height='8'%3E%3Cpath d='M2.5 0L1 1.5 3.5 4 1 6.5 2.5 8l4-4-4-4z' fill='%236c757d'/%3E%3C/svg%3E&#34;);" aria-label="breadcrumb">
			<ol class="breadcrumb">
		    	<li class="breadcrumb-item"><a href="inicio">Inicio</a></li>
		    	<li class="breadcrumb-item active" aria-current="page">Producto</li>
			</ol>
		</nav>
		<h1>Lista de Productos</h1>
	</div>
	<div class="container">
		<%
			ArrayList<Producto> lista = (ArrayList<Producto>)request.getAttribute("lista");
			
			String texto = "";
			if (request.getParameter("texto") != null) {
				texto = request.getParameter("texto");
			}
		%>
		
		<div class="row mb-3" >
			<div class="col" >
				<a href="producto?opcion=editar" class="btn btn-sm btn-success"><i class="bi bi-box-seam"></i> Agregar Producto</a>
			</div>
			<div style="width: 200px">
				<form id="formBuscar" action="producto" method="get" >
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
			<tbody class="table-group-divider">
				<% for(Producto p : lista) { %>
					<tr>
						<td><%= p.getNombre() %></td>
						<td><%= p.getDescripcion() %></td>
						<td><%= p.getPrecio() %></td>
						<td><%= p.getStock() %></td>
						<td><%= p.getNombreProveedor()  %></td>
						<td><%= p.getNombreAlmacen()  %></td>
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
			const respuesta = confirm('¿Desea eliminar el producto?');
			if (respuesta) {
				document.querySelector('#formEliminar input[name=id]').value = id;
				document.getElementById('formEliminar').submit();
			}
		};
	</script>
</body>
</html>