<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro de Bus</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/shared/navbar.jsp" />
    <div class="container mt-4">
        <h3>Registrar Nuevo Bus</h3>
        <form action="bus" method="post" class="card p-4">
            <input type="hidden" name="accion" value="registrar"/>
            <div class="mb-3">
                <label for="placa" class="form-label">Placa</label>
                <input type="text" class="form-control" id="placa" name="placa" required>
            </div>
            <div class="mb-3">
                <label for="modelo" class="form-label">Modelo</label>
                <input type="text" class="form-control" id="modelo" name="modelo" required>
            </div>
            <div class="mb-3">
                <label for="capacidad" class="form-label">Capacidad</label>
                <input type="number" class="form-control" id="capacidad" name="capacidad" required>
            </div>
            <button type="submit" class="btn btn-success">Registrar Bus</button>
            <a href="bus?accion=listar" class="btn btn-secondary mt-2">Cancelar</a>
        </form>
    </div>
</body>
</html>
