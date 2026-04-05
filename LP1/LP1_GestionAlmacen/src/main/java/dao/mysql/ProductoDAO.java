package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.MySQLConexion;
import entidades.Producto;
import interfaces.IProductoDAO;

public class ProductoDAO implements IProductoDAO {

    @Override
    public int agregar(Producto producto) {
        int resultado = 0;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = MySQLConexion.getConexion();
            String sql = "INSERT INTO Producto (nombre_producto, descripcion, precio_unitario, cantidad_stock, id_proveedor, id_ubicacion) VALUES (?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre_producto());
            ps.setString(2, producto.getDescripcion());
            ps.setBigDecimal(3, producto.getPrecio_unitario());
            ps.setInt(4, producto.getCantidad_stock());
            ps.setInt(5, producto.getId_proveedor());
            ps.setInt(6, producto.getId_ubicacion());
            
            resultado = ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                // No cerramos la conexión aquí para poder reutilizarla.
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    @Override
    public int actualizar(Producto producto) {
        int resultado = 0;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = MySQLConexion.getConexion();
            String sql = "UPDATE Producto SET nombre_producto = ?, descripcion = ?, precio_unitario = ?, cantidad_stock = ?, id_proveedor = ?, id_ubicacion = ? WHERE id_producto = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre_producto());
            ps.setString(2, producto.getDescripcion());
            ps.setBigDecimal(3, producto.getPrecio_unitario());
            ps.setInt(4, producto.getCantidad_stock());
            ps.setInt(5, producto.getId_proveedor());
            ps.setInt(6, producto.getId_ubicacion());
            ps.setInt(7, producto.getId_producto());
            
            resultado = ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    @Override
    public int eliminar(int idProducto) {
        int resultado = 0;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = MySQLConexion.getConexion();
            String sql = "DELETE FROM Producto WHERE id_producto = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProducto);
            
            resultado = ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    @Override
    public Producto obtenerPorId(int idProducto) {
        Producto producto = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = MySQLConexion.getConexion();
            String sql = "SELECT * FROM Producto WHERE id_producto = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                producto = new Producto();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre_producto(rs.getString("nombre_producto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio_unitario(rs.getBigDecimal("precio_unitario"));
                producto.setCantidad_stock(rs.getInt("cantidad_stock"));
                producto.setId_proveedor(rs.getInt("id_proveedor"));
                producto.setId_ubicacion(rs.getInt("id_ubicacion"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return producto;
    }

    @Override
    public List<Producto> obtenerTodos() {
        List<Producto> listaProductos = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = MySQLConexion.getConexion();
            String sql = "SELECT * FROM Producto";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre_producto(rs.getString("nombre_producto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio_unitario(rs.getBigDecimal("precio_unitario"));
                producto.setCantidad_stock(rs.getInt("cantidad_stock"));
                producto.setId_proveedor(rs.getInt("id_proveedor"));
                producto.setId_ubicacion(rs.getInt("id_ubicacion"));
                listaProductos.add(producto);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaProductos;
    }
}
