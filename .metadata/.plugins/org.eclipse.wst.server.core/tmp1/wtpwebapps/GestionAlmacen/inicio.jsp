<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inicio - Gestión de Almacén</title>
    <!-- Incluyendo Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    
    <!-- Incluimos la barra de navegación -->
    <jsp:include page="/shared/navbar.jsp" />

    <div class="container mt-5">
        <div class="p-5 mb-4 bg-light rounded-3">
            <div class="container-fluid py-5">
                <h1 class="display-5 fw-bold">¡Bienvenido al Sistema de Gestión de Almacenes!</h1>
                <p class="col-md-8 fs-4">
                    Utiliza la barra de navegación para comenzar a gestionar los productos, proveedores, personal y ubicaciones del almacén.
                </p>
                <a href="${pageContext.request.contextPath}/ProductoServlet?opcion=listar" class="btn btn-primary btn-lg" type="button">Ver Productos</a>
            </div>
        </div>
    </div>

    <!-- Incluyendo el JavaScript de Bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
