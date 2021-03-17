package Logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    Connection conexion;
    public String cadenaConexion = "jdbc:sqlserver://NICO\\SQLEXPRESS:1433;databaseName=DBSistVentas";
    public String usuario = "sa";
    public String password = "nico123";

    public Conexion() {
    }

    public Connection Conectar() {        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conexion = DriverManager.getConnection(cadenaConexion, usuario, password);

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showConfirmDialog(null, e.toString());
        }
        return conexion;
    }

    public void Desconectar() {
        try {

            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
