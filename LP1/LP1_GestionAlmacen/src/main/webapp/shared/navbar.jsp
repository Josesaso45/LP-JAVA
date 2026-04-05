<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/InicioServlet">Gestión de Almacén</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/InicioServlet">Inicio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/ProductoServlet?opcion=listar">Productos</a>
                </li>
                <li class="nav-item">
                    <%-- Enlace al futuro ProveedorServlet --%>
                    <a class="nav-link" href="#">Proveedores</a>
                </li>
                <li class="nav-item">
                    <%-- Enlace al futuro PersonalServlet --%>
                    <a class="nav-link" href="#">Personal</a>
                </li>
                 <li class="nav-item">
                    <%-- Enlace al futuro UbicacionServlet --%>
                    <a class="nav-link" href="#">Ubicaciones</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
