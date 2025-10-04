<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de Productos</title>
<!-- Incluyendo Bootstrap para un diseño más amigable -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2>Gestión de Productos</h2>
        <a href="ProductoServlet?opcion=nuevo" class="btn btn-primary mb-3">Nuevo Producto</a>
        
        <table class="table table-striped table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Precio</th>
                    <th>Stock</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="producto" items="${listaProductos}">
                    <tr>
                        <td>${producto.id_producto}</td>
                        <td>${producto.nombre_producto}</td>
                        <td>${producto.descripcion}</td>
                        <td>S/ ${producto.precio_unitario}</td>
                        <td>${producto.cantidad_stock}</td>
                        <td>
                            <a href="ProductoServlet?opcion=editar&id=${producto.id_producto}" class="btn btn-warning btn-sm">Editar</a>
                            <a href="ProductoServlet?opcion=eliminar&id=${producto.id_producto}" class="btn btn-danger btn-sm" onclick="return confirm('¿Estás seguro de que deseas eliminar este producto?');">Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
