package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import db.MySqlConexion;
import entidades.Bus;
import interfaces.IBusDAO;

public class MySqlBusDAO implements IBusDAO {

    @Override
    public ArrayList<Bus> listarBuses() {
        ArrayList<Bus> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MySqlConexion.getConexion();
            String sql = "SELECT * FROM tb_buses";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Bus b = new Bus(
                    rs.getString("placa"),
                    rs.getString("modelo"),
                    rs.getInt("capacidad")
                );
                lista.add(b);
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
    public int registrar(Bus bus) {
        int salida = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MySqlConexion.getConexion();
            String sql = "INSERT INTO tb_buses (placa, modelo, capacidad) VALUES (?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, bus.getPlaca());
            ps.setString(2, bus.getModelo());
            ps.setInt(3, bus.getCapacidad());
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
    public int actualizar(Bus bus) {
        int salida = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MySqlConexion.getConexion();
            String sql = "UPDATE tb_buses SET modelo=?, capacidad=? WHERE placa=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, bus.getModelo());
            ps.setInt(2, bus.getCapacidad());
            ps.setString(3, bus.getPlaca());
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
    public Bus buscar(String placa) {
        Bus b = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MySqlConexion.getConexion();
            String sql = "SELECT * FROM tb_buses WHERE placa=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, placa);
            rs = ps.executeQuery();
            if (rs.next()) {
                b = new Bus(
                    rs.getString("placa"),
                    rs.getString("modelo"),
                    rs.getInt("capacidad")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return b;
    }
}
