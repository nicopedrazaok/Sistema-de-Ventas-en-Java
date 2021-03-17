package Logica;

import Datos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class GestorLogin {

    Conexion conexionSQL = new Conexion();
    Connection conn = conexionSQL.Conectar();
    private String consultaSQL = "";

    public Usuario login(String correoElectronico, String contraseña) {
        Usuario user = new Usuario();
        consultaSQL = "SELECT * FROM Usuarios WHERE correoElectronico = ? AND contraseña = ? ";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);
            pst.setString(1, correoElectronico);
            pst.setString(2, contraseña);
            pst.executeQuery();
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                user.setIdUsuario(rs.getInt("idUsuario"));
                user.setNombre(rs.getString("nombre"));
                user.setCorreoElectronico(rs.getString("correoElectronico"));
                user.setContraseña(rs.getString("contraseña"));
                user.setRol(rs.getString("rol"));
            }

        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        } finally {
            conexionSQL.Desconectar();
        }
        return user;
    }

    public boolean insertar(Usuario usuario) {
        consultaSQL = "INSERT INTO Usuarios (nombre,correoElectronico,contraseña,rol) VALUES (?,?,?,?)";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);
            pst.setString(1, usuario.getNombre());
            pst.setString(2, usuario.getCorreoElectronico());
            pst.setString(3, usuario.getContraseña());
            pst.setString(4, usuario.getRol());

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
}
