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
		    	<li class="breadcrumb-item"><a href="producto">Producto</a></li>
		    	<li class="breadcrumb-item active" aria-current="page">Mantenimiento</li>
			</ol>
		</nav>
		<h1>Mantenimiento de Producto</h1>
	</div>
	<div class="container" >
		<%
			Producto producto = (Producto)request.getAttribute("registro");
			ArrayList<Almacen> almacenes = (ArrayList<Almacen>)request.getAttribute("listaAlmacenes");
			ArrayList<Proveedor> proveedores = (ArrayList<Proveedor>)request.getAttribute("listaProveedores");
		%>
		
		<form id="formProducto" action="producto?opcion=registrar" method="post" >
			<input type="hidden" name="productoId" value="<%= producto.getProductoId() %>" />
			<div class="mb-3 row">
				<label class="col-sm-2 col-form-label">Nombre</label>
				<div class="col-sm-10" >
					<input type="text" name="nombre" class="form-control" value="<%= producto.getNombre() %>" />
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 col-form-label">Descripción</label>
				<div class="col-sm-10" >
					<input type="text" name="descripcion" class="form-control" value="<%= producto.getDescripcion() %>" />
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 col-form-label">Precio</label>
				<div class="col-sm-10" >
					<input type="text" name="precio" class="form-control" value="<%= producto.getPrecio() %>" />
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 col-form-label">Stock</label>
				<div class="col-sm-10" >
					<input type="text" name="stock" class="form-control" value="<%= producto.getStock() %>" />
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 col-form-label">Proveedor</label>
				<div class="col-sm-10" >
					<select name="proveedorId" class="form-select" >
						<% for (Proveedor p : proveedores) { %>
							<option value="<%=p.getProveedorId()%>" <%=(p.getProveedorId() == producto.getProveedorId() ? "selected=\"selected\"" : "") %> >
								<%=p.getNombre() %>
							</option>
						<% } %>
					</select>
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 col-form-label">Almacén</label>
				<div class="col-sm-10" >
					<select name="almacenId" class="form-select" >
						<% for (Almacen a : almacenes) { %>
							<option value="<%=a.getAlmacenId()%>" <%=(a.getAlmacenId() == producto.getAlmacenId() ? "selected=\"selected\"" : "") %> >
								<%=a.getNombre() %>
							</option>
						<% } %>
					</select>
				</div>
			</div>
			<div class="row" >
				<button type="submit" class="btn btn-primary" >Enviar</button>
			</div>
		</form>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js" integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q" crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.5/dist/jquery.validate.min.js" ></script>
	<script type="text/javascript">
		$(() => {
			
			$("#formProducto").validate({
				rules: {
					nombre: {
						required: true,
						minlength: 5
					},
					descripcion: {
						required: true,
						minlength: 5
					},
					precio: {
						min: 1,
						max: 1000000
					},
					stock: {
						min: 1,
						max: 1000
					}
				},
				messages: {
					nombre: {
						required: 'El campo nombre es requerido.'
					},
					descripcion: {
						required: 'El campo descripción es requerido.'
					},
					precio: {
						min: 'El precio debe ser mayor a 1.',
						max: 'El precio debe ser menor a 1000000.'
					},
					stock: {
						min: 'La cantidad de stock debe ser mayor a 1.',
						max: 'La cantidad de stock debe ser menor a 1000.'
					}
				}
			});
			
		});
	</script>
</body>
</html>