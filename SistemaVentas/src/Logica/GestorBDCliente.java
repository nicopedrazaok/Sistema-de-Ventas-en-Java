package Logica;

import Datos.Cliente;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestorBDCliente {

    Conexion conexionSQL = new Conexion();
    Connection conn = conexionSQL.Conectar();
    private String consultaSQL = "";

    public boolean insertar(Cliente cliente) {
        consultaSQL = "INSERT INTO Clientes (dni,nombre,telefono,email,direccion,razonSocial) VALUES (?,?,?,?,?,?)";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);
            pst.setString(1, cliente.getDni());
            pst.setString(2, cliente.getNombre());
            pst.setString(3, cliente.getTelefono());
            pst.setString(4, cliente.getEmail());
            pst.setString(5, cliente.getDireccion());
            pst.setString(6, cliente.getRazonSocial());

            int inserto = pst.executeUpdate();
            if (inserto != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e.toString());
            return false;
        } finally {
            conexionSQL.Desconectar();
        }
    }

    public boolean editar(Cliente cliente) {
        consultaSQL = "UPDATE Clientes SET dni = ?,nombre = ?, telefono = ?,email = ?,direccion = ?,razonSocial = ? WHERE idCliente = ?";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);

            pst.setString(1, cliente.getDni());
            pst.setString(2, cliente.getNombre());
            pst.setString(3, cliente.getTelefono());
            pst.setString(4, cliente.getEmail());
            pst.setString(5, cliente.getDireccion());
            pst.setString(6, cliente.getRazonSocial());
            pst.setInt(7, cliente.getIdCliente());

            int modifico = pst.executeUpdate();
            if (modifico != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        } finally {
            conexionSQL.Desconectar();
        }
    }

    public boolean eliminar(int idCliente) {
        consultaSQL = "DELETE FROM Clientes WHERE idCliente = ?";

        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);

            pst.setInt(1, idCliente);

            int elimino = pst.executeUpdate();
            if (elimino != 0) {
                return true;

            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);

        } finally {
            conexionSQL.Desconectar();
        }
        return false;
    }

    public ArrayList<Cliente> listado() {
        ArrayList<Cliente> lista = new ArrayList<>();
        consultaSQL = "SELECT * FROM Clientes";
        try {
            conexionSQL.Conectar();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consultaSQL);
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setDni(rs.getString("dni"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setRazonSocial(rs.getString("razonSocial"));

                lista.add(cliente);
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e.toString());
        } finally {
            conexionSQL.Desconectar();
        }
        return lista;
    }

    public Cliente buscarCliente(String dni) {
        Cliente cliente = new Cliente();
        consultaSQL = "SELECT * FROM Clientes WHERE dni = ?";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);
            pst.setString(1, dni);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {

                cliente.setNombre(rs.getString("nombre"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setRazonSocial(rs.getString("razonSocial"));

            }
        } catch (SQLException e) {
        }
        return cliente;
    }
}
