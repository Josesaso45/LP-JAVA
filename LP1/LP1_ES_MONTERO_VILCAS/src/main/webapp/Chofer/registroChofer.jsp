<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro de Chofer</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/shared/navbar.jsp" />
    <div class="container mt-4">
        <h3>Registrar Nuevo Chofer</h3>
        <form action="chofer" method="post" class="card p-4">
            <input type="hidden" name="accion" value="registrar"/>
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="nombre" name="nombre" required>
            </div>
            <div class="mb-3">
                <label for="apellidos" class="form-label">Apellidos</label>
                <input type="text" class="form-control" id="apellidos" name="apellidos" required>
            </div>
            <button type="submit" class="btn btn-success">Registrar Chofer</button>
            <a href="chofer?accion=listar" class="btn btn-secondary mt-2">Cancelar</a>
        </form>
    </div>
</body>
</html>
