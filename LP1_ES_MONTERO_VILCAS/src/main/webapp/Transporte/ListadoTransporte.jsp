<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Transporte</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
    <%-- 1. Incluimos la barra de navegación --%>
    <jsp:include page="/shared/navbar.jsp" />

    <div class="container mt-4">
        <div class="row">
            <div class="col-md-8">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h3>Asignaciones Registradas</h3>
                    <a href="asignacion?accion=cargarFormulario" class="btn btn-primary">
                        <i class="bi bi-plus-circle"></i> Nueva Asignación
                    </a>
                </div>

                <c:if test="${not empty param.mensajeExito}">
                    <div class="alert alert-success">${param.mensajeExito}</div>
                </c:if>

                <table class="table table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>#</th>
                            <th>Fecha</th>
                            <th>Chofer</th>
                            <th>Bus</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="asignacion" items="${listaAsignaciones}">
                             <tr>
                                <td>${asignacion.numero}</td>
                                <td>${asignacion.fecha}</td>
                                <td>${asignacion.nombre_completo_chofer}</td>
                                <td>${asignacion.placa_bus}</td>
                                <td>
                                    <a href="asignacion?accion=editar&numero=${asignacion.numero}" class="btn btn-sm btn-warning">
                                        <i class="bi bi-pencil-fill"></i> Editar
                                    </a>
                                    <a href="asignacion?accion=eliminar&numero=${asignacion.numero}" class="btn btn-sm btn-danger" onclick="return confirm('¿Estás seguro de que deseas eliminar esta asignación?');">
                                        <i class="bi bi-trash-fill"></i> Eliminar
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <%-- 3. Columna lateral para mostrar el estado de los choferes desde la sesión --%>
            <div class="col-md-4">
                <h3>Estado de Choferes</h3>
                <ul class="list-group">
                    <c:forEach var="chofer" items="${sessionScope.listaChoferes}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            ${chofer.nombre} ${chofer.apellidos}
                            <c:if test="${chofer.estado == 0}">
                                <span class="badge bg-success rounded-pill">Libre</span>
                            </c:if>
                            <c:if test="${chofer.estado == 1}">
                                <span class="badge bg-danger rounded-pill">Asignado</span>
                            </c:if>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>