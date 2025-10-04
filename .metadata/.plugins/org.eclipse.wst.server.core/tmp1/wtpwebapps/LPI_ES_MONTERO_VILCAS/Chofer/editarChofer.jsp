<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Chofer</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/shared/navbar.jsp" />
    <div class="container mt-4">
        <h3>Editar Chofer: ${chofer.nombre} ${chofer.apellidos}</h3>
        <form action="chofer" method="post" class="card p-4">
            <input type="hidden" name="accion" value="actualizar"/>
            <input type="hidden" name="codigo" value="${chofer.codigo}"/>
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="nombre" name="nombre" value="${chofer.nombre}" required>
            </div>
            <div class="mb-3">
                <label for="apellidos" class="form-label">Apellidos</label>
                <input type="text" class="form-control" id="apellidos" name="apellidos" value="${chofer.apellidos}" required>
            </div>
            <button type="submit" class="btn btn-warning">Actualizar Cambios</button>
            <a href="chofer?accion=listar" class="btn btn-secondary mt-2">Cancelar</a>
        </form>
    </div>
</body>
</html>
