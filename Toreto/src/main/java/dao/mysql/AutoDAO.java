package dao.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.MySQLConexion;
import entidades.Auto;
import interfaces.IAutoDAO;

public class AutoDAO implements IAutoDAO {
	private static IAutoDAO instancia;
	
	public static IAutoDAO getInstancia() {
		if (instancia == null) {
			instancia = new AutoDAO();
		}
		return instancia;
	}
	
	private AutoDAO() {}
	
	@Override
	public int crear(Auto auto) {
		int value = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "insert into autos values (null, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, auto.getModelo());
			ps.setInt(2, auto.getMarcaId());
			ps.setString(3, auto.getColor());
			ps.setBigDecimal(4, auto.getPrecio());
			
			value = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al crear auto: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return value;
	}

	@Override
	public ArrayList<Auto> listar() {
		ArrayList<Auto> lista = new ArrayList<Auto>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "select * from autos";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Auto auto = new Auto(
						rs.getInt("auto_id"),
						rs.getString("modelo"),
						rs.getInt("marca_id"),
						rs.getString("color"),
						rs.getBigDecimal("precio")
						);
				lista.add(auto);
			}
		} catch (Exception e) {
			System.out.println("Error al listar autos: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return lista;
	}
	
	@Override
	public Auto obtener(int autoId) {
		Auto auto = null;		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "select * from autos where auto_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, autoId);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				auto = new Auto(
					rs.getInt("auto_id"),
					rs.getString("modelo"),
					rs.getInt("marca_id"),
					rs.getString("color"),
					rs.getBigDecimal("precio")
				);
			}
		} catch (Exception e) {
			System.out.println("Error al obtener auto: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return auto;
	}

	@Override
	public int actualizar(Auto auto) {
		int value = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "update autos set modelo = ?, marca_id = ?, color = ?, precio = ? where auto_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, auto.getModelo());
			ps.setInt(2, auto.getMarcaId());
			ps.setString(3, auto.getColor());
			ps.setBigDecimal(4, auto.getPrecio());
			ps.setInt(5, auto.getAutoId());
			
			value = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al actualizar auto: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return value;
	}

	@Override
	public int eliminar(int autoId) {
		int value = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "delete from autos where auto_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, autoId);
			
			value = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al eliminar auto: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return value;
	}
	
	@Override
	public ArrayList<Auto> buscar(String texto) {
		ArrayList<Auto> lista = new ArrayList<Auto>();
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "{CALL usp_buscar_auto(?)}";
			cs = con.prepareCall(sql);
			cs.setString(1, texto);
			
			rs = cs.executeQuery();
			
			while(rs.next()) {
				Auto auto = new Auto(
						rs.getInt("auto_id"),
						rs.getString("modelo"),
						rs.getInt("marca_id"),
						rs.getString("color"),
						rs.getBigDecimal("precio")
						);
				auto.setNombreMarca(rs.getString("nombre_marca"));
				lista.add(auto);
			}
		} catch (Exception e) {
			System.out.println("Error al buscar autos: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		
		return lista;
	}
}