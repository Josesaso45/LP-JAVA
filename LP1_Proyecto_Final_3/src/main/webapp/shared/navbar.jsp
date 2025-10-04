<%@page import="entidades.Usuario"%>
<%
	HttpSession miSession = request.getSession();
	Usuario autenticacionUsuario = new Usuario();
	if (miSession.getAttribute("usuario") != null)
		autenticacionUsuario = (Usuario) miSession.getAttribute("usuario");
%>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container">
    <a class="navbar-brand" href="inicio">Almacen2.0</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link" href="inicio">Inicio</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="almacen">Almacenes</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="producto">Productos</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="proveedor">Proveedores</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="usuario">Usuarios</a>
        </li>
      </ul>
    </div>
      <div class="d-flex">
      	<ul class="navbar-nav me-auto mb-2 mb-lg-0">
      		<li>
      			<a class="nav-link" href="#" ><strong>Conectado:</strong> <%= autenticacionUsuario.getNombre() %></a>
      		</li>
      		<li>  </li>
      		<li>
      			<a class="nav-link" href="autenticacion?opcion=logout" > <i class="bi bi-box-arrow-left"></i> Cerrar sesión</a>
      		</li>
      	</ul>
      </div>
  </div>
</nav>
