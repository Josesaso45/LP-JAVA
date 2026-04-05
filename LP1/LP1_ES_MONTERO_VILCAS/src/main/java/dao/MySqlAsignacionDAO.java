package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import db.MySqlConexion;
import entidades.Asignacion;
import interfaces.IAsignacionDAO;

public class MySqlAsignacionDAO implements IAsignacionDAO {

    @Override
    public int registrar(Asignacion a) {
        int salida = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MySqlConexion.getConexion();
            String sql = "INSERT INTO tb_asignacion (codigo_chofer, placa_bus, fecha) VALUES (?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, a.getCodigo_chofer());
            ps.setString(2, a.getPlaca_bus());
            ps.setDate(3, java.sql.Date.valueOf(a.getFecha()));
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
    public int editar(Asignacion a) {
        // Lógica para editar una asignación
        return 0; // Implementar si es necesario
    }

    @Override
    public int eliminar(int numero) {
        int salida = 0;
        Connection con = null;
        CallableStatement cstm = null;
        try {
            con = MySqlConexion.getConexion();
            String sql = "{call usp_Eliminar_Asignacion(?)}";
            cstm = con.prepareCall(sql);
            cstm.setInt(1, numero);
            salida = cstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (cstm != null) cstm.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return salida;
    }

    @Override
    public ArrayList<Asignacion> listarAsignaciones() {
        return listarAsignacionesConNombres();
    }
    
    public ArrayList<Asignacion> listarAsignacionesConNombres() {
        ArrayList<Asignacion> lista = new ArrayList<>();
        Connection con = null;
        // --- CORRECCIÓN: Se usa CallableStatement para llamar al Stored Procedure ---
        CallableStatement cstm = null;
        ResultSet rs = null;
        try {
            con = MySqlConexion.getConexion();
            String sql = "{call usp_Listar_Asignaciones()}";
            cstm = con.prepareCall(sql);
            rs = cstm.executeQuery();
            while (rs.next()) {
                Asignacion a = new Asignacion();
                a.setNumero(rs.getInt("numero"));
                a.setFecha(rs.getDate("fecha").toString());
                a.setPlaca_bus(rs.getString("placa_bus"));
                a.setNombre_completo_chofer(rs.getString("nombre_completo_chofer"));
                lista.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (cstm != null) cstm.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return lista;
    }

    @Override
    public Asignacion buscar(int numero) {
        // Lógica para buscar una asignación por su número
        return null; // Implementar si es necesario
    }
}
