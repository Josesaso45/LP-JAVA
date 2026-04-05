<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand" href="asignacion?accion=listar">Urbanito Transporte</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link" href="asignacion?accion=listar">Asignaciones</a>
        </li>
        <li class="nav-item">
          <%-- Este enlace ahora apunta al nuevo ChoferServlet --%>
          <a class="nav-link" href="chofer?accion=listar">Choferes</a>
        </li>
        <li class="nav-item">
          <%-- Este enlace ahora apunta al nuevo BusServlet --%>
          <a class="nav-link" href="bus?accion=listar">Buses</a>
        </li>
      </ul>
      <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link" href="inicio">Cerrar Sesión</a>
        </li>
      </ul>
    </div>
  </div>
</nav>
