package dao.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.MySQLConexion;
import entidades.Producto;
import interfaces.IProductoDAO;

public class ProductoDAO implements IProductoDAO {

	private static IProductoDAO instancia;
	
	public static IProductoDAO getInstancia() {
	if (instancia == null) {
		instancia = new ProductoDAO();
		}
		return instancia;
	}
	private ProductoDAO() {}
	
	@Override
	public int crear(Producto producto) {
		int value = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "insert into productos values (null, ?, ?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, producto.getNombre());
			ps.setString(2, producto.getDescripcion());
			ps.setInt(3, producto.getPrecio());
			ps.setString(4, producto.getStock());
			ps.setInt(5, producto.getAlmacenId());
			ps.setInt(6, producto.getProveedorId());
			value = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al crear: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return value;
	}

	@Override
	public ArrayList<Producto> listar() {
		ArrayList<Producto> lista = new ArrayList<Producto>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "select * from productos";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Producto producto = new Producto(
						rs.getInt("producto_id"),
						rs.getString("nombre"),
						rs.getString("descripcion"),
						rs.getInt("precio"),
						rs.getString("stock"),
						rs.getInt("almacen_id"),
						rs.getInt("proveedor_id")
						);
				
				lista.add(producto);
			}

		} catch (Exception e) {
			System.out.println("Error al listar: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		
		return lista;
	}

	@Override
	public Producto obtener(int productoId) {
		Producto producto = null;		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "select * from productos where producto_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, productoId);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				producto = new Producto(
					rs.getInt("producto_id"),
					rs.getString("nombre"),
					rs.getString("descripcion"),
					rs.getInt("precio"),
					rs.getString("stock"),
					rs.getInt("proveedor_id"),
					rs.getInt("almacen_id")
				);
			}
		} catch (Exception e) {
			System.out.println("Error al obtener el registro: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		
		return producto;
	}

	@Override
	public int actualizar(Producto producto) {
		int value = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "update productos set nombre = ?, descripcion = ?, precio = ?, stock = ?, proveedor_id = ?, almacen_id = ? where producto_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, producto.getNombre());
			ps.setString(2, producto.getDescripcion());
			ps.setInt(3, producto.getPrecio());
			ps.setString(4, producto.getStock());
			ps.setInt(5, producto.getProveedorId());
			ps.setInt(6, producto.getAlmacenId());
			ps.setInt(7, producto.getProductoId());
			
			value = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al actualizar el registro: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return value;
	}

	@Override
	public int eliminar(int productoId) {
		int value = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "delete from productos where producto_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, productoId);
			
			value = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al eliminar el registro: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return value;
	}
	@Override
	public ArrayList<Producto> buscar(String texto) {
		ArrayList<Producto> lista = new ArrayList<Producto>();
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "{CALL usp_obtener_producto(?)}";
			cs = con.prepareCall(sql);
			cs.setString(1, texto);
			
			rs = cs.executeQuery();
			
			while(rs.next()) {
				Producto producto = new Producto();
			    producto.setProductoId(rs.getInt("producto_id"));
			    producto.setNombre(rs.getString("nombreProducto"));
			    producto.setDescripcion(rs.getString("descripcion"));
			    producto.setPrecio(rs.getInt("precio"));
			    producto.setStock(rs.getString("stock"));
			    producto.setNombreProveedor(rs.getString("nombreProveedor"));
			    producto.setNombreAlmacen(rs.getString("nombreAlmacen"));
				lista.add(producto);
			}
		} catch (Exception e) {
			System.out.println("Error al lista con busqueda: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		
		return lista;
	}
}

