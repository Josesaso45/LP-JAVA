package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.MySqlConexion;
import entidades.Curso;
import interfaces.ICursoModel;

public class CursoModel implements ICursoModel{

	@Override
	public int crear(Curso curso) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Curso> listar() {
		ArrayList<Curso> lista = new ArrayList<Curso>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs= null; //Conjunto de resultados para leer de lo que traiga la abse de datos
		
		try {
			con = MySqlConexion.getConexion();
			String sql = "select * from cursos";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Curso curso = new Curso(
						rs.getInt("curso_id"),
						rs.getString("codigo"),
						rs.getString("nombre"),
						rs.getInt("vacantes")
						);
				lista.add(curso);
			}
		} catch (Exception e) {
			System.out.println("Error al listar: " + e.getMessage());
		}finally {
			MySqlConexion.closeConexion(con);
		}
		
		return null;
	}

	@Override
	public Curso obtener(int cursoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(Curso curso) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(int cursoId) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
