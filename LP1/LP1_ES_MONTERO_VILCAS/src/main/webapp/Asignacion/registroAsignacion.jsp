<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro de Asignación</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/shared/navbar.jsp" />

    <div class="container mt-4">
        <h3>Registrar Nueva Asignación</h3>
        
        <c:if test="${not empty mensajeError}">
            <div class="alert alert-danger">${mensajeError}</div>
        </c:if>

        <form action="asignacion" method="post" class="card p-4">
            <input type="hidden" name="accion" value="registrar"/>
            <div class="row">
                <div class="col-md-4 mb-3">
                    <label for="chofer" class="form-label">Chofer (Solo libres)</label>
                    <select name="codigo_chofer" id="chofer" class="form-select" required>
                        <option value="">-- Seleccione un chofer --</option>
                        <c:if test="${not empty listaChoferesParaCombo}">
                            <c:forEach var="chofer" items="${listaChoferesParaCombo}">
                                <c:if test="${chofer.estado == 0}">
                                    <option value="${chofer.codigo}">${chofer.nombre} ${chofer.apellidos}</option>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="bus" class="form-label">Bus</label>
                    <select name="placa_bus" id="bus" class="form-select" required>
                        <option value="">-- Seleccione un bus --</option>
                        <c:if test="${not empty listaBusesParaCombo}">
                            <c:forEach var="bus" items="${listaBusesParaCombo}">
                                <option value="${bus.placa}">${bus.placa} - ${bus.modelo}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="fecha" class="form-label">Fecha de Asignación</label>
                    <input type="date" name="fecha" id="fecha" class="form-control" required/>
                </div>
            </div>
            <div class="mt-3">
                <button type="submit" class="btn btn-success">Registrar Asignación</button>
                <a href="asignacion?accion=listar" class="btn btn-secondary">Cancelar</a>
            </div>
        </form>
    </div>
</body>
</html>
