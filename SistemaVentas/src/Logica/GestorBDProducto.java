package Logica;

import Datos.Config;
import Datos.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class GestorBDProducto {

    Conexion conexionSQL = new Conexion();
    Connection conn = conexionSQL.Conectar();
    private String consultaSQL = "";

    public boolean insertar(Producto producto) {
        consultaSQL = "INSERT INTO productos (codigo,descripcion,stock,precio,proveedor) VALUES (?,?,?,?,?)";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);
            pst.setString(1, producto.getCodigo());
            pst.setString(2, producto.getDescripcion());
            pst.setInt(3, producto.getCantidad());
            pst.setDouble(4, producto.getPrecio());
            pst.setString(5, producto.getProveedor());

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

    public boolean editar(Producto producto) {
        consultaSQL = "UPDATE Productos SET codigo = ?,descripcion = ?, stock = ?,precio = ?,proveedor = ? WHERE idProducto = ?";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);

            pst.setString(1, producto.getCodigo());
            pst.setString(2, producto.getDescripcion());
            pst.setInt(3, producto.getCantidad());
            pst.setDouble(4, producto.getPrecio());
            pst.setString(5, producto.getProveedor());
            pst.setInt(6, producto.getIdProducto());

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

    public boolean eliminar(int idProducto) {
        consultaSQL = "DELETE FROM Productos WHERE idProducto = ?";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);

            pst.setInt(1, idProducto);

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

    public ArrayList<Producto> listado() {
        ArrayList<Producto> lista = new ArrayList<>();
        consultaSQL = "SELECT * FROM Productos";
        try {
            conexionSQL.Conectar();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consultaSQL);
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setCodigo(rs.getString("codigo"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setCantidad(rs.getInt("stock"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setProveedor(rs.getString("proveedor"));

                lista.add(producto);
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e.toString());
        } finally {
            conexionSQL.Desconectar();
        }
        return lista;
    }

    public void listadoProveedor(JComboBox proveedor) {
        consultaSQL = "SELECT nombre FROM Proveedores";
        try {
            conexionSQL.Conectar();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consultaSQL);
            while (rs.next()) {
                proveedor.addItem(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e.toString());
        } finally {
            conexionSQL.Desconectar();
        }
    }

    public Producto buscarProducto(String codigo) {
        Producto producto = new Producto();

        consultaSQL = "SELECT * FROM Productos WHERE codigo = ?";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);

            pst.setString(1, codigo);

            pst.executeQuery();
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setCantidad(rs.getInt("stock"));
                producto.setPrecio(rs.getDouble("precio"));
            }

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        } finally {
            conexionSQL.Desconectar();
        }
        return producto;
    }

    public Config buscarDato() {
        Config config = new Config();

        consultaSQL = "SELECT * FROM Config ";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);
            pst.executeQuery();
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                config.setId(rs.getInt("id"));
                config.setNombre(rs.getString("nombre"));
                config.setRuc(rs.getString("ruc"));
                config.setTelefono(rs.getString("telefono"));
                config.setEmail(rs.getString("email"));
                config.setDireccion(rs.getString("direccion"));
                config.setRazonSocial(rs.getString("razonSocial"));
            }

        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        } finally {
            conexionSQL.Desconectar();
        }
        return config;
    }

    public boolean actualizarDatos(Config config) {
        consultaSQL = "UPDATE Config SET nombre = ?,ruc = ?, telefono = ?,email = ?,direccion = ?,razonSocial = ? WHERE id = ?";
        try {
            conexionSQL.Conectar();
            PreparedStatement pst = conn.prepareStatement(consultaSQL);

            pst.setString(1, config.getNombre());
            pst.setString(2, config.getRuc());
            pst.setString(3, config.getTelefono());
            pst.setString(4, config.getEmail());
            pst.setString(5, config.getDireccion());
            pst.setString(5, config.getRazonSocial());
            pst.setInt(7, config.getId());

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
}
