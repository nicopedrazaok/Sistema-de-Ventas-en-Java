package Logica;

import Datos.DetalleVenta;
import Datos.Venta;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GestorBDVenta {

    Conexion conexionSQL = new Conexion();
    Connection conn = conexionSQL.Conectar();
    private String consultaSQL = "";
    int resultado;

    public int insertarVenta(Venta venta) {
        consultaSQL = "INSERT INTO Ventas (cliente,vendedor,total,fecha) VALUES (?,?,?,?)";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);
            pst.setString(1, venta.getCliente());
            pst.setString(2, venta.getVendedor());
            pst.setDouble(3, venta.getTotal());
            pst.setString(4, venta.getFecha());

            pst.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e.toString());

        } finally {
            conexionSQL.Desconectar();
        }
        return resultado;
    }

    public int idVenta() {
        int idVenta = 0;
        consultaSQL = "SELECT MAX(idVenta) FROM Ventas";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idVenta = rs.getInt(1);
            }

        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e.toString());

        } finally {
            conexionSQL.Desconectar();
        }
        return idVenta;
    }

    public int insertarDetalleVenta(DetalleVenta detalleVenta) {
        consultaSQL = "INSERT INTO detalle_venta (codProducto,cantidad,precio,idVenta) VALUES (?,?,?,?)";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);
            pst.setString(1, detalleVenta.getCodProducto());
            pst.setInt(2, detalleVenta.getCantidad());
            pst.setDouble(3, detalleVenta.getPrecio());
            pst.setInt(4, detalleVenta.getIdVenta());

            pst.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e.toString());

        } finally {
            conexionSQL.Desconectar();
        }
        return resultado;
    }

    public boolean actualizarStock(int cantidad, String codigo) {
        consultaSQL = "UPDATE Productos SET stock = ? WHERE codigo = ?";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);

            pst.setInt(1, cantidad);
            pst.setString(2, codigo);

            pst.execute();

            return true;

        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        } finally {
            conexionSQL.Desconectar();
        }

    }

    public ArrayList<Venta> listado() {
        ArrayList<Venta> lista = new ArrayList<>();
        consultaSQL = "SELECT * FROM Ventas";
        try {
            conexionSQL.Conectar();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consultaSQL);
            while (rs.next()) {
                Venta venta = new Venta();
                venta.setIdVenta(rs.getInt("idVenta"));
                venta.setCliente(rs.getString("cliente"));
                venta.setVendedor(rs.getString("vendedor"));
                venta.setTotal(rs.getInt("total"));
                venta.setFecha(rs.getString("fecha"));

                lista.add(venta);
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e.toString());
        } finally {
            conexionSQL.Desconectar();
        }
        return lista;
    }
}
