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

	<div class="container" >
		<h1>Registro de Productos</h1>
		
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