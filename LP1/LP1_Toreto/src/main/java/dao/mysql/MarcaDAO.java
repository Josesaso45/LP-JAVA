package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.MySQLConexion;
import entidades.Marca;
import interfaces.IMarcaDAO;

public class MarcaDAO implements IMarcaDAO {
	private static IMarcaDAO instancia;
	
	public static IMarcaDAO getInstancia() {
		if (instancia == null) {
			instancia = new MarcaDAO();
		}
		return instancia;
	}
	
	private MarcaDAO() {}

	@Override
	public int crear(Marca marca) {
		int value = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "insert into marcas values (null, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, marca.getNombreMarca());
			
			value = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al crear marca: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return value;
	}

	@Override
	public ArrayList<Marca> listar() {
		ArrayList<Marca> lista = new ArrayList<Marca>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "select * from marcas";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Marca marca = new Marca(
						rs.getInt("marca_id"),
						rs.getString("nombre_marca")
						);
				lista.add(marca);
			}
		} catch (Exception e) {
			System.out.println("Error al listar marcas: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return lista;
	}

	@Override
	public Marca obtener(int marcaId) {
		Marca marca = null;		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "select * from marcas where marca_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, marcaId);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				marca = new Marca(
					rs.getInt("marca_id"),
					rs.getString("nombre_marca")
				);
			}
		} catch (Exception e) {
			System.out.println("Error al obtener marca: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return marca;
	}

	@Override
	public int actualizar(Marca marca) {
		int value = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "update marcas set nombre_marca = ? where marca_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, marca.getNombreMarca());
			ps.setInt(2, marca.getMarcaId());
			
			value = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al actualizar marca: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return value;
	}

	@Override
	public int eliminar(int marcaId) {
		int value = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MySQLConexion.getConexion();
			String sql = "delete from marcas where marca_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, marcaId);
			
			value = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al eliminar marca: " + e.getMessage());
		} finally {
			MySQLConexion.closeConexion(con);
		}
		return value;
	}
}