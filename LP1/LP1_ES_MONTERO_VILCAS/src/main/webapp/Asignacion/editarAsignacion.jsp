<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Asignación</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/shared/navbar.jsp" />

    <div class="container mt-4">
        <h3>Editar Asignación N°: ${asignacion.numero}</h3>
        
        <form action="asignacion" method="post" class="card p-4">
            <input type="hidden" name="accion" value="editar"/>
            <input type="hidden" name="numero" value="${asignacion.numero}"/>
            
            <div class="row">
                <div class="col-md-4 mb-3">
                    <label for="chofer" class="form-label">Chofer</label>
                    <select name="codigo_chofer" id="chofer" class="form-select" required>
                        <option value="">-- Seleccione --</option>
                        <c:forEach var="chofer" items="${listaChoferesParaCombo}">
                            <c:if test="${chofer.codigo == asignacion.codigo_chofer || chofer.estado == 0}">
                                <option value="${chofer.codigo}" ${chofer.codigo == asignacion.codigo_chofer ? 'selected' : ''}>
                                    ${chofer.nombre} ${chofer.apellidos}
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="bus" class="form-label">Bus</label>
                    <select name="placa_bus" id="bus" class="form-select" required>
                         <option value="">-- Seleccione --</option>
                        <c:forEach var="bus" items="${listaBusesParaCombo}">
                            <option value="${bus.placa}" ${bus.placa == asignacion.placa_bus ? 'selected' : ''}>
                                ${bus.placa} - ${bus.modelo}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="fecha" class="form-label">Fecha de Asignación</label>
                    <input type="date" name="fecha" id="fecha" class="form-control" value="${asignacion.fecha}" required/>
                </div>
            </div>
            <div class="mt-3">
                <button type="submit" class="btn btn-warning">Actualizar Cambios</button>
                <a href="asignacion?accion=listar" class="btn btn-secondary">Cancelar</a>
            </div>
        </form>
    </div>
</body>
</html>
