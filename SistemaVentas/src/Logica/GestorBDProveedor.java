package Logica;

import Datos.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class GestorBDProveedor {
     Conexion conexionSQL = new Conexion();
    Connection conn = conexionSQL.Conectar();
    private String consultaSQL = "";

    public boolean insertar(Proveedor proveedor) {
        consultaSQL = "INSERT INTO Proveedores (ruc,nombre,telefono,email,direccion,paginaWeb) VALUES (?,?,?,?,?,?)";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);
            pst.setString(1, proveedor.getRuc());
            pst.setString(2, proveedor.getNombre());
            pst.setString(3, proveedor.getTelefono());
            pst.setString(4, proveedor.getEmail());
            pst.setString(5, proveedor.getDireccion());
            pst.setString(6, proveedor.getPaginaWeb());

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

    public boolean editar(Proveedor proveedor) {
        consultaSQL = "UPDATE Proveedores SET ruc = ?,nombre = ?, telefono = ?,email = ?,direccion = ?,paginaWeb = ? WHERE idProveedor = ?";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);

            pst.setString(1, proveedor.getRuc());
            pst.setString(2, proveedor.getNombre());
            pst.setString(3, proveedor.getTelefono());
            pst.setString(4, proveedor.getEmail());
            pst.setString(5, proveedor.getDireccion());
            pst.setString(6, proveedor.getPaginaWeb());
            pst.setInt(7, proveedor.getIdProveedor());

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

    public boolean eliminar(int idProveedor) {
        consultaSQL = "DELETE FROM Proveedores WHERE idProveedor = ?";

        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);

            pst.setInt(1, idProveedor);

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

    public ArrayList<Proveedor> listado() {
        ArrayList<Proveedor> lista = new ArrayList<>();
        consultaSQL = "SELECT * FROM Proveedores";
        try {
            conexionSQL.Conectar();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consultaSQL);
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setIdProveedor(rs.getInt("idProveedor"));
                proveedor.setRuc(rs.getString("ruc"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setEmail(rs.getString("email"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedor.setPaginaWeb(rs.getString("paginaWeb"));

                lista.add(proveedor);
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e.toString());
        } finally {
            conexionSQL.Desconectar();
        }
        return lista;
    }
}
