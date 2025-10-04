<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inicio</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
</head>
<body>
	<%@include file="/shared/navbar.jsp" %>
	
	<div class="container mt-3 mb-5" >
		<h1>Bienvenido Usuario</h1>
	</div>
	<div class="container">
	  <div class="row">
	    <div class="col-sm">
			<div class="card mb-3" style="width: 18rem;">
				<img class="card-img-top" src="images/almacen.jpg" alt="Almacenes"/>
			  	<div class="card-body">			  	
			    	<h5 class="card-title">Almacenes</h5>
			    	<p>Revise información de nuestros almacenes, actualice y  agregue nuevos.</p>
			    	<a class="btn btn-primary" href="almacen">Almacenes <i class="bi bi-arrow-right-circle"></i></a>
			  	</div>
			</div>
	    </div>
	    <div class="col-sm">
			<div class="card mb-3" style="width: 18rem;">
				<img class="card-img-top" src="images/productos.jpg" alt="Productos"/>
			  	<div class="card-body">
					<h5 class="card-title">Productos</h5>
					<p>Revise información de todos los productos, actualice y agregue nuevos.</p>
					<a class="btn btn-primary" href="producto">Productos <i class="bi bi-arrow-right-circle"></i></a>
			  </div>
			</div>
	    </div>
	    <div class="col-sm">
			<div class="card mb-3" style="width: 18rem;">
				<img class="card-img-top" src="images/proveedor.jpg" alt="Proveedor"/>
				<div class="card-body">
			    	<h5 class="card-title">Proveedores</h5>
			    	<p>Revise información de los proveedores, actualice y agregue nuevos.</p>
 			    	<a class="btn btn-primary" href="proveedor">Proveedores <i class="bi bi-arrow-right-circle"></i></a>
			  </div>
			</div>
	    </div>
	    <div class="col-sm">
		    <div class="card mb-3" style="width: 18rem;">
		    	<img class="card-img-top" src="images/equipo.jpg" alt="Trabajadores"/>
		    	<div class="card-body">
		    		<h5 class="card-title">Usuarios</h5>
		    		<p>Revise información de todos los trabajadores y usuarios del sistema digital.</p>
			    	<a class="btn btn-primary" href="autenticacion">Usuarios <i class="bi bi-arrow-right-circle"></i></a>
			  </div>
			</div>
		</div>
	  </div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js" integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q" crossorigin="anonymous"></script>
</body>
</html>