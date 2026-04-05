<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Buses</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
    <jsp:include page="/shared/navbar.jsp" />
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h3>Gestión de Buses</h3>
            <a href="bus?accion=nuevo" class="btn btn-primary">
                <i class="bi bi-bus-front-fill"></i> Nuevo Bus
            </a>
        </div>
        <table class="table table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>Placa</th>
                    <th>Modelo</th>
                    <th>Capacidad</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="bus" items="${listaBuses}">
                    <tr>
                        <td>${bus.placa}</td>
                        <td>${bus.modelo}</td>
                        <td>${bus.capacidad}</td>
                        <td>
                            <a href="bus?accion=editar&placa=${bus.placa}" class="btn btn-sm btn-warning">
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
