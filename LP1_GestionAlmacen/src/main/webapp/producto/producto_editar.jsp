<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Formulario de Producto</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2><c:if test="${producto == null}">Nuevo Producto</c:if><c:if test="${producto != null}">Editar Producto</c:if></h2>
        
        <form action="ProductoServlet" method="post">
            <!-- Campo oculto para la opción -->
            <input type="hidden" name="opcion" value="guardar">
            
            <!-- Campo oculto para el ID del producto (solo en modo edición) -->
            <c:if test="${producto != null}">
                <input type="hidden" name="id_producto" value="${producto.id_producto}">
            </c:if>

            <div class="mb-3">
                <label for="nombre_producto" class="form-label">Nombre del Producto</label>
                <input type="text" class="form-control" id="nombre_producto" name="nombre_producto" value="${producto.nombre_producto}" required>
            </div>
            <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <textarea class="form-control" id="descripcion" name="descripcion" rows="3">${producto.descripcion}</textarea>
            </div>
            <div class="mb-3">
                <label for="precio_unitario" class="form-label">Precio Unitario</label>
                <input type="number" step="0.01" class="form-control" id="precio_unitario" name="precio_unitario" value="${producto.precio_unitario}" required>
            </div>
            <div class="mb-3">
                <label for="cantidad_stock" class="form-label">Cantidad en Stock</label>
                <input type="number" class="form-control" id="cantidad_stock" name="cantidad_stock" value="${producto.cantidad_stock}" required>
            </div>
            
            <!-- Estos campos deberían ser desplegables cargados desde la base de datos -->
            <div class="mb-3">
                <label for="id_proveedor" class="form-label">ID Proveedor</label>
                <input type="number" class="form-control" id="id_proveedor" name="id_proveedor" value="${producto.id_proveedor}" required>
            </div>
            <div class="mb-3">
                <label for="id_ubicacion" class="form-label">ID Ubicación</label>
                <input type="number" class="form-control" id="id_ubicacion" name="id_ubicacion" value="${producto.id_ubicacion}" required>
            </div>
            
            <button type="submit" class="btn btn-success">Guardar</button>
            <a href="ProductoServlet?opcion=listar" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>
</body>
</html>
