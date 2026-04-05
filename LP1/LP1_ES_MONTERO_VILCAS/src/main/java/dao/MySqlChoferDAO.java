package dao;

import java.sql.*;
import java.util.ArrayList;
import db.MySqlConexion;
import entidades.Chofer;
import interfaces.IChoferDAO;

public class MySqlChoferDAO implements IChoferDAO {

    @Override
    public ArrayList<Chofer> listarChoferes() {
        ArrayList<Chofer> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MySqlConexion.getConexion();
            String sql = "SELECT * FROM tb_chofer";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Chofer c = new Chofer(
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getInt("estado")
                );
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return lista;
    }

    @Override
    public int actualizarEstado(int codigo, int estado) {
        int salida = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MySqlConexion.getConexion();
            String sql = "UPDATE tb_chofer SET estado=? WHERE codigo=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, estado);
            ps.setInt(2, codigo);
            salida = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return salida;
    }
    
    @Override
    public Chofer buscar(int codigo) {
        Chofer c = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MySqlConexion.getConexion();
            String sql = "SELECT * FROM tb_chofer WHERE codigo=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                c = new Chofer(
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getInt("estado")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return c;
    }

    @Override
    public int registrar(Chofer chofer) {
        int salida = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MySqlConexion.getConexion();
            String sql = "INSERT INTO tb_chofer (nombre, apellidos, estado) VALUES (?, ?, 0)";
            ps = con.prepareStatement(sql);
            ps.setString(1, chofer.getNombre());
            ps.setString(2, chofer.getApellidos());
            salida = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return salida;
    }

    @Override
    public int actualizar(Chofer chofer) {
        int salida = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MySqlConexion.getConexion();
            String sql = "UPDATE tb_chofer SET nombre=?, apellidos=? WHERE codigo=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, chofer.getNombre());
            ps.setString(2, chofer.getApellidos());
            ps.setInt(3, chofer.getCodigo());
            salida = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return salida;
    }
}
