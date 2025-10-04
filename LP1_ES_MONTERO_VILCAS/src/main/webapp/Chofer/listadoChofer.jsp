<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Choferes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
    <jsp:include page="/shared/navbar.jsp" />
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h3>Gestión de Choferes</h3>
            <a href="chofer?accion=nuevo" class="btn btn-primary">
                <i class="bi bi-person-plus-fill"></i> Nuevo Chofer
            </a>
        </div>
        <table class="table table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>Código</th>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="chofer" items="${listaChoferes}">
                    <tr>
                        <td>${chofer.codigo}</td>
                        <td>${chofer.nombre}</td>
                        <td>${chofer.apellidos}</td>
                        <td>
                            <c:if test="${chofer.estado == 0}"><span class="badge bg-success">Libre</span></c:if>
                            <c:if test="${chofer.estado == 1}"><span class="badge bg-danger">Asignado</span></c:if>
                        </td>
                        <td>
                            <a href="chofer?accion=editar&codigo=${chofer.codigo}" class="btn btn-sm btn-warning">
                                <i class="bi bi-pencil-fill"></i> Editar
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
