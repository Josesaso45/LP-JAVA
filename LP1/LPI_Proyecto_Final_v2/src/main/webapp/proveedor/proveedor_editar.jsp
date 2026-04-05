<%@page import="entidades.Proveedor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mantenimiento de Proveedores</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
</head>
<body>

	<%@include file="/shared/navbar.jsp" %>
	<div class="container mt-3 mb-5" >
		<nav style="--bs-breadcrumb-divider: url(&#34;data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='8' height='8'%3E%3Cpath d='M2.5 0L1 1.5 3.5 4 1 6.5 2.5 8l4-4-4-4z' fill='%236c757d'/%3E%3C/svg%3E&#34;);" aria-label="breadcrumb">
			<ol class="breadcrumb">
		    	<li class="breadcrumb-item"><a href="inicio">Inicio</a></li>
		    	<li class="breadcrumb-item"><a href="proveedor">Proveedor</a></li>
		    	<li class="breadcrumb-item active" aria-current="page">Mantenimiento</li>
			</ol>
		</nav>
		<h1>Mantenimiento de Proveedor</h1>
	</div>
	<div class="container" >
		
		<%
			Proveedor proveedor = (Proveedor)request.getAttribute("registro");
		%>
		
		<form id="formProveedor" action="proveedor?opcion=registrar" method="post" >
			<input type="hidden" name="proveedorId" value="<%= proveedor.getProveedorId() %>" />
			<div class="mb-3 row">
				<label class="col-sm-2 col-form-label">Nombre</label>
				<div class="col-sm-10" >
					<input type="text" name="nombre" class="form-control" value="<%= proveedor.getNombre() %>" />
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 col-form-label">Contacto</label>
				<div class="col-sm-10" >
					<input type="text" name="contacto" class="form-control" value="<%= proveedor.getContacto() %>" />
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 col-form-label">Telefono</label>
				<div class="col-sm-10" >
					<input type="text" name="telefono" class="form-control" value="<%= proveedor.getTelefono() %>" />
				</div>
			</div>
			<div class="mb-3 row">
				<label class="col-sm-2 col-form-label">Email</label>
				<div class="col-sm-10" >
					<input type="text" name="email" class="form-control" value="<%= proveedor.getEmail() %>" />
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
			
			$("#formProveedor").validate({
				rules: {
					nombre: {
						required: true,
						minlength: 5
					},
					contacto: {
						required: true,
						minlength: 3
					},
					telefono: {
						required: true,
						minlength: 5,
						number: true
					},
					email: {
						required: true,
						minlength: 4,
						email: true
					}
				},
				messages: {
					nombre: {
						required: 'El campo nombre es requerido',
						minlength: 'El campo debe tener un mínimo de 5 caracteres'
					},
					contacto: {
						required: 'El campo contacto es requerido',
						minlength: 'El campo debe tener un mínimo de 3 caracteres'
					},
					telefono: {
						required: 'El campo telefono es requerido',
						minlength: 'El campo debe tener un mínimo de 5 caracteres'
					},
					email: {
						required: 'El campo email es requerido',
						minlength: 'El campo email debe tener al menos 5 caracateres'
						email: 'Debe tener el formato correcto: email@email.com'
					}
				}
			});
			
		});
	</script>
</body>
</html>