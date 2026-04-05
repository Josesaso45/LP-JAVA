package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.MySQLConexion;
import entidades.Proveedor;
import interfaces.IProveedorDAO;

public class ProveedorDAO implements IProveedorDAO {
	private static IProveedorDAO instancia;
	
	public static IProveedorDAO getInstancia() {
		if (instancia == null) {
			instancia = new ProveedorDAO();
		}
		return instancia;
	}
	
	private ProveedorDAO() {}

	@Override
	public int crear(Proveedor proveedor) {
		int value = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "insert into proveedores values (null, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, proveedor.getNombre());
			ps.setString(2, proveedor.getContacto());
			ps.setString(3, proveedor.getTelefono());
			ps.setString(4, proveedor.getEmail());
			value = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al crear: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return value;
	}

	@Override
	public ArrayList<Proveedor> listar() {
		ArrayList<Proveedor> lista = new ArrayList<Proveedor>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "select * from proveedores";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Proveedor proveedor = new Proveedor(
						rs.getInt("proveedor_id"),
						rs.getString("nombre"),
						rs.getString("contacto"),
						rs.getString("telefono"),
						rs.getString("email")
						);
				lista.add(proveedor);
			}
		} catch (Exception e) {
			System.out.println("Error al listar: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		
		return lista;
	}

	@Override
	public Proveedor obtener(int proveedorId) {
		Proveedor proveedor = null;		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "select * from proveedores where proveedor_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, proveedorId);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				proveedor = new Proveedor(
					rs.getInt("proveedor_id"),
					rs.getString("nombre"),
					rs.getString("contacto"),
					rs.getString("telefono"),
					rs.getString("email")
				);
			}
		} catch (Exception e) {
			System.out.println("Error al obtener el registro: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		
		return proveedor;
	}

	@Override
	public int actualizar(Proveedor proveedor) {
		int value = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "update proveedores set nombre = ? , contacto = ? , telefono = ?, email = ? where proveedor_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, proveedor.getNombre());
			ps.setString(2, proveedor.getContacto());
			ps.setString(3, proveedor.getTelefono());
			ps.setString(3, proveedor.getEmail());
			ps.setInt(5, proveedor.getProveedorId());
			
			value = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al actualizar el registro: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return value;
	}

	@Override
	public int eliminar(int proveedorId) {
		int value = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "delete from proveedores where proveedor_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, proveedorId);
			
			value = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al eliminar el registro: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return value;
	}

}