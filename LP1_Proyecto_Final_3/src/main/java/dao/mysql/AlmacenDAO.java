package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.MySQLConexion;
import entidades.Almacen;
import interfaces.IAlmacenDAO;

public class AlmacenDAO implements IAlmacenDAO {
	private static IAlmacenDAO instancia;
	
	public static IAlmacenDAO getInstancia() {
		if (instancia == null) {
			instancia = new AlmacenDAO();
		}
		return instancia;
	}
	
	private AlmacenDAO() {}

	@Override
	public int crear(Almacen almacen) {
		int value = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "insert into almacenes values (null, ?, ? , ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, almacen.getNombre());
			ps.setString(2, almacen.getUbicacion());
			ps.setString(3, almacen.getDescripcion());			
			value = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al crear: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return value;
	}

	@Override
	public ArrayList<Almacen> listar() {
		ArrayList<Almacen> lista = new ArrayList<Almacen>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "select * from almacenes";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Almacen almacen = new Almacen(
						rs.getInt("almacen_id"),
						rs.getString("nombre"),
						rs.getString("ubicacion"),
						rs.getString("descripcion")
						);
				lista.add(almacen);
			}
		} catch (Exception e) {
			System.out.println("Error al listar: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		
		return lista;
	}

	@Override
	public Almacen obtener(int almacenId) {
		Almacen almacen = null;		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "select * from almacenes where almacen_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, almacenId);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				almacen = new Almacen(
					rs.getInt("almacen_id"),
					rs.getString("nombre"),
					rs.getString("ubicacion"),
					rs.getString("descripcion")
				);
			}
		} catch (Exception e) {
			System.out.println("Error al obtener el registro: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		
		return almacen;
	}

	@Override
	public int actualizar(Almacen almacen) {
		int value = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "update almacenes set nombre = ? , ubicacion = ? , descripcion = ? where almacen_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, almacen.getNombre());
			ps.setString(2, almacen.getUbicacion());
			ps.setString(3, almacen.getDescripcion());
			ps.setInt(4, almacen.getAlmacenId());
			
			value = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al actualizar el registro: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return value;
	}

	@Override
	public int eliminar(int almacenId) {
		int value = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "delete from almacenes where almacen_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, almacenId);
			
			value = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al eliminar el registro: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return value;
	}

}
