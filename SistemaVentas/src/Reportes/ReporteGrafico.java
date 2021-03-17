package Reportes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;

import org.jfree.data.general.DefaultPieDataset;

public class ReporteGrafico {

    public static void graficar(String fecha) {
        String cadenaConexion = "jdbc:sqlserver://NICO\\SQLEXPRESS:1433;databaseName=DBSistVentas";
        String usuario = "sa";
        String password = "nico123";
        try {
            String consultaSQL = "SELECT total FROM ventas WHERE fecha = ?";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conexion = DriverManager.getConnection(cadenaConexion, usuario, password);
            PreparedStatement pst = conexion.prepareStatement(consultaSQL);
            pst.setString(1,fecha);
            ResultSet rs = pst.executeQuery();
            DefaultPieDataset dateset = new DefaultPieDataset();
            while(rs.next()){
                dateset.setValue(rs.getString("total"), rs.getDouble("total"));
            }
            JFreeChart jfc = ChartFactory.createPieChart("Reporte de Venta", dateset);
            ChartFrame cf = new ChartFrame("Total de ventas por dias",jfc);
            cf.setSize(1000, 500);
            cf.setLocationRelativeTo(null);
            cf.setVisible(true);
        } catch (ClassNotFoundException | SQLException e){ 
            System.out.println(e.toString());
        }
    }
}
