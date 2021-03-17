package Presentacion;

import Datos.Cliente;
import Datos.Config;
import Datos.Producto;
import Datos.Proveedor;
import Datos.Venta;
import Logica.GestorBDCliente;
import Logica.GestorBDProducto;
import Logica.GestorBDProveedor;
import Logica.GestorBDVenta;
import Reportes.ReporteProductoExcel;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import Datos.DetalleVenta;
import Datos.Eventos;
import Datos.Usuario;
import Reportes.ReporteGrafico;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

@SuppressWarnings("serial")
public class MenuPrincipal extends javax.swing.JFrame {

    Cliente cliente = new Cliente();
    GestorBDCliente gestorCliente = new GestorBDCliente();
    Proveedor proveedor = new Proveedor();
    GestorBDProveedor gestorProveedor = new GestorBDProveedor();
    Producto producto = new Producto();
    GestorBDProducto gestorProducto = new GestorBDProducto();
    Venta venta = new Venta();
    GestorBDVenta gestorVenta = new GestorBDVenta();
    DetalleVenta detalleVenta = new DetalleVenta();
    Config config = new Config();
    Eventos event = new Eventos();
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel modelo = new DefaultTableModel();
    int item;
    double total = 0.00;
    Date fechaVenta = new Date();
    String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(fechaVenta);

    public MenuPrincipal() {
        initComponents();    
    }
    public MenuPrincipal(Usuario user){
        initComponents();
        this.setLocationRelativeTo(null);
        inhabilitarCliente();
        inhabilitarProveedor();
        inhabilitarProducto();
        ocultarCamposClienteV();
        listadoConfig();
        txtIdVenta.setVisible(false);
        txtIdEmpresa.setVisible(false);
        txtIdVentas.setVisible(false);
        if(user.getRol().equals("Asistente")){
            btnProducto.setEnabled(false);
            btnProveedor.setEnabled(false);
            lblVendedor.setText(user.getNombre());
        }else{
            lblVendedor.setText(user.getNombre());
        }
    }
    private void ocultarCamposClienteV() {
        txtTelefonoCV.setVisible(false);
        txtEmailCV.setVisible(false);
        txtDireccionCV.setVisible(false);
        txtRazonSocialCV.setVisible(false);
    }

    private void inhabilitarCliente() {
        txtIdCliente.setVisible(false);
        txtDniRucCliente.setEnabled(false);
        txtNombreCliente.setEnabled(false);
        txtTelefonoCliente.setEnabled(false);
        txtEmailCliente.setEnabled(false);
        txtDireccionCliente.setEnabled(false);
        txtRazonSocialCliente.setEnabled(false);
    }

    private void habilitarCliente() {
        txtIdCliente.setVisible(false);
        txtDniRucCliente.setEnabled(true);
        txtNombreCliente.setEnabled(true);
        txtTelefonoCliente.setEnabled(true);
        txtEmailCliente.setEnabled(true);
        txtDireccionCliente.setEnabled(true);
        txtRazonSocialCliente.setEnabled(true);
    }

    private void listadoCliente() {
        ArrayList<Cliente> lista = gestorCliente.listado();
        model = (DefaultTableModel) tblListadoCliente.getModel();
        Object[] obj = new Object[7];
        for (int i = 0; i < lista.size(); i++) {
            obj[0] = lista.get(i).getIdCliente();
            obj[1] = lista.get(i).getDni();
            obj[2] = lista.get(i).getNombre();
            obj[3] = lista.get(i).getTelefono();
            obj[4] = lista.get(i).getEmail();
            obj[5] = lista.get(i).getDireccion();
            obj[6] = lista.get(i).getRazonSocial();
            model.addRow(obj);
        }
        tblListadoCliente.setModel(model);
    }

    private void listadoProveedor() {
        ArrayList<Proveedor> lista = gestorProveedor.listado();
        model = (DefaultTableModel) tblListadoProveedor.getModel();
        Object[] obj = new Object[7];
        for (int i = 0; i < lista.size(); i++) {
            obj[0] = lista.get(i).getIdProveedor();
            obj[1] = lista.get(i).getRuc();
            obj[2] = lista.get(i).getNombre();
            obj[3] = lista.get(i).getTelefono();
            obj[4] = lista.get(i).getEmail();
            obj[5] = lista.get(i).getDireccion();
            obj[6] = lista.get(i).getPaginaWeb();
            model.addRow(obj);
        }
        tblListadoProveedor.setModel(model);
    }

    private void inhabilitarProveedor() {
        txtIdProveedor.setVisible(false);
        txtRucProveedor.setEnabled(false);
        txtNombreProveedor.setEnabled(false);
        txtTelefonoProveedor.setEnabled(false);
        txtEmailProveedor.setEnabled(false);
        txtDireccionProveedor.setEnabled(false);
        txtPaginaWebProveedor.setEnabled(false);
    }

    private void habilitarProveedor() {
        txtIdProveedor.setVisible(false);
        txtRucProveedor.setEnabled(true);
        txtNombreProveedor.setEnabled(true);
        txtTelefonoProveedor.setEnabled(true);
        txtEmailProveedor.setEnabled(true);
        txtDireccionProveedor.setEnabled(true);
        txtPaginaWebProveedor.setEnabled(true);
    }

    private void inhabilitarProducto() {
        txtIdProducto.setVisible(false);
        txtCodigoProducto.setEnabled(false);
        txtDescripcionProducto.setEnabled(false);
        txtCantidadProducto.setEnabled(false);
        txtPrecioProducto.setEnabled(false);
        cboProveedor.setEnabled(false);
    }

    private void habilitarProducto() {
        txtIdProducto.setVisible(false);
        txtCodigoProducto.setEnabled(true);
        txtDescripcionProducto.setEnabled(true);
        txtCantidadProducto.setEnabled(true);
        txtPrecioProducto.setEnabled(true);
        cboProveedor.setEnabled(true);
    }

    private void listadoProducto() {
        ArrayList<Producto> lista = gestorProducto.listado();
        model = (DefaultTableModel) tblListadoProducto.getModel();
        Object[] obj = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            obj[0] = lista.get(i).getIdProducto();
            obj[1] = lista.get(i).getCodigo();
            obj[2] = lista.get(i).getDescripcion();
            obj[3] = lista.get(i).getCantidad();
            obj[4] = lista.get(i).getPrecio();
            obj[5] = lista.get(i).getProveedor();

            model.addRow(obj);
        }
        tblListadoProducto.setModel(model);
    }

    private void limpiarTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    private void limpiarCamposCliente() {
        txtIdCliente.setText("");
        txtDniRucCliente.setText("");
        txtNombreCliente.setText("");
        txtTelefonoCliente.setText("");
        txtEmailCliente.setText("");
        txtDireccionCliente.setText("");
        txtRazonSocialCliente.setText("");
    }

    private void limpiarCamposProveedor() {
        txtIdProveedor.setText("");
        txtRucProveedor.setText("");
        txtNombreProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtEmailProveedor.setText("");
        txtDireccionProveedor.setText("");
        txtPaginaWebProveedor.setText("");
    }

    private void limpiarCamposProducto() {
        txtIdProducto.setText("");
        txtCodigoProducto.setText("");
        txtDescripcionProducto.setText("");
        txtCantidadProducto.setText("");
        txtPrecioProducto.setText("");
    }

    private void totalPagar() {
        total = 0.00;
        int nroFila = tblListadoVenta.getRowCount();
        for (int i = 0; i < nroFila; i++) {
            double can = Double.parseDouble(String.valueOf(tblListadoVenta.getModel().getValueAt(i, 5)));
            total = total + can;
        }
        lblTotal.setText(String.format("%.2f", total));
    }

    private void LimpiarCamposVenta() {
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtCantidad.setText("");
        txtStockDisponible.setText("");
        txtPrecio.setText("");
        txtIdVenta.setText("");
    }

    private void listadoConfig() {
        config = gestorProducto.buscarDato();
        txtIdEmpresa.setText("" + config.getId());
        txtNombreEmpresa.setText("" + config.getNombre());
        txtRucEmpresa.setText("" + config.getRuc());
        txtTelefonoEmpresa.setText("" + config.getTelefono());
        txtEmailEmpresa.setText("" + config.getEmail());
        txtDireccionEmpresa.setText("" + config.getDireccion());
        txtRazonSocialEmpresa.setText("" + config.getRazonSocial());
    }

    private void listadoVentas() {
        ArrayList<Venta> lista = gestorVenta.listado();
        model = (DefaultTableModel) tblListadoVentas.getModel();
        Object[] obj = new Object[5];
        for (int i = 0; i < lista.size(); i++) {
            obj[0] = lista.get(i).getIdVenta();
            obj[1] = lista.get(i).getCliente();
            obj[2] = lista.get(i).getVendedor();
            obj[3] = lista.get(i).getTotal();
            obj[4] = lista.get(i).getFecha();

            model.addRow(obj);
        }
        tblListadoVentas.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btnNuevaVenta = new javax.swing.JButton();
        btnCliente = new javax.swing.JButton();
        btnProveedor = new javax.swing.JButton();
        btnVenta = new javax.swing.JButton();
        btnProducto = new javax.swing.JButton();
        btnConfiguracion = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lblVendedor = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListadoVenta = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtDniRucClienteV = new javax.swing.JTextField();
        txtNombreClienteV = new javax.swing.JTextField();
        btnInsertarVenta = new javax.swing.JButton();
        lblTotalPagar = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        txtIdVenta = new javax.swing.JTextField();
        txtTelefonoCV = new javax.swing.JTextField();
        txtEmailCV = new javax.swing.JTextField();
        txtDireccionCV = new javax.swing.JTextField();
        txtRazonSocialCV = new javax.swing.JTextField();
        txtStockDisponible = new javax.swing.JTextField();
        dtcFecha = new com.toedter.calendar.JDateChooser();
        btnReporteGrafico = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtDniRucCliente = new javax.swing.JTextField();
        txtNombreCliente = new javax.swing.JTextField();
        txtTelefonoCliente = new javax.swing.JTextField();
        txtEmailCliente = new javax.swing.JTextField();
        txtDireccionCliente = new javax.swing.JTextField();
        txtRazonSocialCliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblListadoCliente = new javax.swing.JTable();
        btnGuardarCliente = new javax.swing.JButton();
        btnActualizarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        txtIdCliente = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        txtIdProveedor = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtRucProveedor = new javax.swing.JTextField();
        txtNombreProveedor = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtTelefonoProveedor = new javax.swing.JTextField();
        txtEmailProveedor = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtDireccionProveedor = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtPaginaWebProveedor = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblListadoProveedor = new javax.swing.JTable();
        btnGuardarProveedor = new javax.swing.JButton();
        btnActualizarProveedor = new javax.swing.JButton();
        btnNuevoProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        txtIdProducto = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtCodigoProducto = new javax.swing.JTextField();
        txtDescripcionProducto = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtCantidadProducto = new javax.swing.JTextField();
        txtPrecioProducto = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblListadoProducto = new javax.swing.JTable();
        btnGuardarProducto = new javax.swing.JButton();
        btnActualizarProducto = new javax.swing.JButton();
        btnNuevoProducto = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        cboProveedor = new javax.swing.JComboBox<>();
        btnReporteProducto = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblListadoVentas = new javax.swing.JTable();
        btnReporteVentas = new javax.swing.JButton();
        txtIdVentas = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        txtNombreEmpresa = new javax.swing.JTextField();
        txtEmailEmpresa = new javax.swing.JTextField();
        txtRucEmpresa = new javax.swing.JTextField();
        txtDireccionEmpresa = new javax.swing.JTextField();
        txtTelefonoEmpresa = new javax.swing.JTextField();
        txtRazonSocialEmpresa = new javax.swing.JTextField();
        txtIdEmpresa = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Ventas");

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        btnNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/Nventa.png"))); // NOI18N
        btnNuevaVenta.setText("Nueva Venta");
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });

        btnCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/Clientes.png"))); // NOI18N
        btnCliente.setText("Clientes");
        btnCliente.setMaximumSize(new java.awt.Dimension(149, 49));
        btnCliente.setMinimumSize(new java.awt.Dimension(149, 49));
        btnCliente.setPreferredSize(new java.awt.Dimension(149, 49));
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });

        btnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/proveedor.png"))); // NOI18N
        btnProveedor.setText("Proveedor");
        btnProveedor.setMaximumSize(new java.awt.Dimension(149, 49));
        btnProveedor.setMinimumSize(new java.awt.Dimension(149, 49));
        btnProveedor.setPreferredSize(new java.awt.Dimension(149, 49));
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });

        btnVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/compras.png"))); // NOI18N
        btnVenta.setText("Ventas");
        btnVenta.setMaximumSize(new java.awt.Dimension(149, 49));
        btnVenta.setMinimumSize(new java.awt.Dimension(149, 49));
        btnVenta.setPreferredSize(new java.awt.Dimension(149, 49));
        btnVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentaActionPerformed(evt);
            }
        });
        btnVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnVentaKeyPressed(evt);
            }
        });

        btnProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/producto.png"))); // NOI18N
        btnProducto.setText("Producto");
        btnProducto.setMaximumSize(new java.awt.Dimension(149, 49));
        btnProducto.setMinimumSize(new java.awt.Dimension(149, 49));
        btnProducto.setPreferredSize(new java.awt.Dimension(149, 49));
        btnProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductoActionPerformed(evt);
            }
        });

        btnConfiguracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/config.png"))); // NOI18N
        btnConfiguracion.setText("Configuración");
        btnConfiguracion.setMaximumSize(new java.awt.Dimension(149, 49));
        btnConfiguracion.setMinimumSize(new java.awt.Dimension(149, 49));
        btnConfiguracion.setPreferredSize(new java.awt.Dimension(149, 49));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/logo.png"))); // NOI18N

        lblVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblVendedor.setForeground(new java.awt.Color(0, 102, 255));
        lblVendedor.setText("Vendedor: ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevaVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(lblVendedor)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(btnNuevaVenta)
                .addGap(18, 18, 18)
                .addComponent(btnCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));

        jLabel1.setFont(new java.awt.Font("Wide Latin", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Sistema de Ventas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jTabbedPane1.setEnabled(false);

        jLabel2.setText("Código");

        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        jLabel3.setText("Descripción");

        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });

        jLabel4.setText("Cantidad");

        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        jLabel5.setText("Precio");

        txtPrecio.setEditable(false);

        jLabel6.setText("Stock Disponible");

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/eliminar.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        tblListadoVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CODIGO", "DESCRIPCIÓN", "CANTIDAD", "PRECIO UNITARIO", "PRECIO TOTAL"
            }
        ));
        jScrollPane1.setViewportView(tblListadoVenta);
        if (tblListadoVenta.getColumnModel().getColumnCount() > 0) {
            tblListadoVenta.getColumnModel().getColumn(0).setMinWidth(0);
            tblListadoVenta.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblListadoVenta.getColumnModel().getColumn(0).setMaxWidth(0);
            tblListadoVenta.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblListadoVenta.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        jLabel7.setText("D.N.I/ R.U.C");

        jLabel8.setText("NOMBRE DEL CLIENTE");

        txtDniRucClienteV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDniRucClienteVKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniRucClienteVKeyTyped(evt);
            }
        });

        txtNombreClienteV.setEditable(false);

        btnInsertarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/print.png"))); // NOI18N
        btnInsertarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarVentaActionPerformed(evt);
            }
        });

        lblTotalPagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/money.png"))); // NOI18N
        lblTotalPagar.setText("TOTAL A PAGAR: ");

        lblTotal.setText("000.000");

        txtStockDisponible.setEditable(false);

        btnReporteGrafico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/torta.png"))); // NOI18N
        btnReporteGrafico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteGraficoActionPerformed(evt);
            }
        });

        jLabel10.setText("Seleccionar fecha:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(55, 55, 55)
                                .addComponent(jLabel5))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnReporteGrafico)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtDniRucClienteV, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(txtNombreClienteV, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(txtTelefonoCV, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtEmailCV, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDireccionCV, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRazonSocialCV, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34)
                        .addComponent(btnInsertarVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotalPagar)
                        .addGap(18, 18, 18)
                        .addComponent(lblTotal)
                        .addGap(25, 25, 25))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dtcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnReporteGrafico, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtDniRucClienteV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombreClienteV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnInsertarVenta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEmailCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDireccionCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRazonSocialCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefonoCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTotalPagar)
                        .addComponent(lblTotal)))
                .addContainerGap(134, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel3);

        jLabel11.setText("DNI/RUC:");

        jLabel12.setText("NOMBRE COMPLETO:");

        jLabel13.setText("TELEFONO:");

        jLabel14.setText("EMAIL:");

        jLabel15.setText("DIRECCIÓN:");

        jLabel16.setText("RAZON SOCIAL:");

        tblListadoCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDCLIENTE", "DNI/RUC", "NOMBRE", "TELEFONO", "EMAIL", "DIRECCION", "RAZON SOCIAL"
            }
        ));
        tblListadoCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListadoClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblListadoCliente);
        if (tblListadoCliente.getColumnModel().getColumnCount() > 0) {
            tblListadoCliente.getColumnModel().getColumn(0).setMinWidth(0);
            tblListadoCliente.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblListadoCliente.getColumnModel().getColumn(0).setMaxWidth(0);
            tblListadoCliente.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblListadoCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblListadoCliente.getColumnModel().getColumn(3).setPreferredWidth(60);
            tblListadoCliente.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblListadoCliente.getColumnModel().getColumn(5).setPreferredWidth(80);
            tblListadoCliente.getColumnModel().getColumn(6).setPreferredWidth(80);
        }

        btnGuardarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/GuardarTodo.png"))); // NOI18N
        btnGuardarCliente.setText("Guardar");
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });

        btnActualizarCliente.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnActualizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/Actualizar.png"))); // NOI18N
        btnActualizarCliente.setText("Actualizar");
        btnActualizarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarClienteActionPerformed(evt);
            }
        });

        btnEliminarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/eliminar.png"))); // NOI18N
        btnEliminarCliente.setText("Eliminar");
        btnEliminarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        btnNuevoCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/nuevo.png"))); // NOI18N
        btnNuevoCliente.setText("Nuevo");
        btnNuevoCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDniRucCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDireccionCliente)
                                .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtRazonSocialCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNuevoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnGuardarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnActualizarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(38, 38, 38)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtDniRucCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtRazonSocialCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGuardarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnActualizarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNuevoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(271, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", jPanel4);

        jLabel17.setText("RUC:");

        jLabel18.setText("NOMBRE:");

        jLabel19.setText("TELEFONO:");

        jLabel20.setText("EMAIL:");

        jLabel21.setText("DIRECCIÓN:");

        jLabel22.setText("PAGINA WEB:");

        tblListadoProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RUC", "NOMBRE", "TELEFONO", "EMAIL", "DIRECCION", "PAGINA WEB"
            }
        ));
        tblListadoProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListadoProveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblListadoProveedor);
        if (tblListadoProveedor.getColumnModel().getColumnCount() > 0) {
            tblListadoProveedor.getColumnModel().getColumn(0).setMinWidth(0);
            tblListadoProveedor.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblListadoProveedor.getColumnModel().getColumn(0).setMaxWidth(0);
            tblListadoProveedor.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblListadoProveedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblListadoProveedor.getColumnModel().getColumn(3).setPreferredWidth(40);
            tblListadoProveedor.getColumnModel().getColumn(4).setPreferredWidth(50);
            tblListadoProveedor.getColumnModel().getColumn(5).setPreferredWidth(80);
            tblListadoProveedor.getColumnModel().getColumn(6).setPreferredWidth(80);
        }

        btnGuardarProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/GuardarTodo.png"))); // NOI18N
        btnGuardarProveedor.setText("Guardar");
        btnGuardarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProveedorActionPerformed(evt);
            }
        });

        btnActualizarProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnActualizarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/Actualizar.png"))); // NOI18N
        btnActualizarProveedor.setText("Actualizar");
        btnActualizarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarProveedorActionPerformed(evt);
            }
        });

        btnNuevoProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/nuevo.png"))); // NOI18N
        btnNuevoProveedor.setText("Nuevo");
        btnNuevoProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedorActionPerformed(evt);
            }
        });

        btnEliminarProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/eliminar.png"))); // NOI18N
        btnEliminarProveedor.setText("Eliminar");
        btnEliminarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btnGuardarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnActualizarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtRucProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDireccionProveedor)
                                        .addComponent(txtEmailProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtPaginaWebProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btnEliminarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNuevoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtRucProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtEmailProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txtPaginaWebProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGuardarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnActualizarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNuevoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(271, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab3", jPanel5);

        jLabel23.setText("CÓDIGO:");

        jLabel24.setText("DESCRIPCIÓN:");

        jLabel25.setText("CANTIDAD:");

        txtPrecioProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProductoKeyTyped(evt);
            }
        });

        jLabel26.setText("PRECIO:");

        jLabel27.setText("PROVEEDOR:");

        tblListadoProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CÓDIGO", "DESCRIPCIÓN", "CANTIDAD", "PRECIO", "PROVEEDOR"
            }
        ));
        tblListadoProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListadoProductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblListadoProducto);
        if (tblListadoProducto.getColumnModel().getColumnCount() > 0) {
            tblListadoProducto.getColumnModel().getColumn(0).setMinWidth(0);
            tblListadoProducto.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblListadoProducto.getColumnModel().getColumn(0).setMaxWidth(0);
            tblListadoProducto.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblListadoProducto.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblListadoProducto.getColumnModel().getColumn(3).setPreferredWidth(40);
            tblListadoProducto.getColumnModel().getColumn(4).setPreferredWidth(50);
            tblListadoProducto.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

        btnGuardarProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/GuardarTodo.png"))); // NOI18N
        btnGuardarProducto.setText("Guardar");
        btnGuardarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProductoActionPerformed(evt);
            }
        });

        btnActualizarProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnActualizarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/Actualizar.png"))); // NOI18N
        btnActualizarProducto.setText("Actualizar");
        btnActualizarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarProductoActionPerformed(evt);
            }
        });

        btnNuevoProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/nuevo.png"))); // NOI18N
        btnNuevoProducto.setText("Nuevo");
        btnNuevoProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProductoActionPerformed(evt);
            }
        });

        btnEliminarProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/eliminar.png"))); // NOI18N
        btnEliminarProducto.setText("Eliminar");
        btnEliminarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });

        cboProveedor.setEditable(true);

        btnReporteProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnReporteProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/excel.png"))); // NOI18N
        btnReporteProducto.setText("Reporte");
        btnReporteProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnGuardarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnActualizarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btnEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNuevoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel23))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDescripcionProducto)
                                    .addComponent(txtPrecioProducto)
                                    .addComponent(txtCantidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnReporteProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txtDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(txtCantidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(txtPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(cboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGuardarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnActualizarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNuevoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnReporteProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(260, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", jPanel6);

        tblListadoVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL", "FECHA"
            }
        ));
        tblListadoVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListadoVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblListadoVentas);
        if (tblListadoVentas.getColumnModel().getColumnCount() > 0) {
            tblListadoVentas.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblListadoVentas.getColumnModel().getColumn(1).setPreferredWidth(80);
            tblListadoVentas.getColumnModel().getColumn(2).setPreferredWidth(80);
            tblListadoVentas.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        btnReporteVentas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnReporteVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/pdf.png"))); // NOI18N
        btnReporteVentas.setText("Reportes de las Ventas");
        btnReporteVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteVentasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 962, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnReporteVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(txtIdVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReporteVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(186, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab5", jPanel7);

        jLabel28.setText("NOMBRE: ");

        jLabel29.setText("EMAIL: ");

        jLabel30.setText("RUC");

        jLabel31.setText("DIRECCIÓN:");

        jLabel32.setText("TELEFONO:");

        jLabel33.setText("RAZON SOCIAL:");

        jLabel34.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel34.setText("DATOS DE LA EMPRESA");

        btnActualizar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Files/Actualizar.png"))); // NOI18N
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        btnActualizar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnActualizarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel28)
                            .addComponent(txtIdEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(267, 267, 267)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel30)
                                        .addComponent(txtDireccionEmpresa)
                                        .addComponent(txtRucEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
                                    .addComponent(jLabel31))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel33)
                                    .addComponent(txtTelefonoEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRazonSocialEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmailEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(369, 369, 369))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel34)
                        .addGap(53, 53, 53))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtIdEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel30)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRucEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefonoEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel31)
                    .addComponent(jLabel33))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmailEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccionEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRazonSocialEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(313, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab6", jPanel8);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentaActionPerformed
        jTabbedPane1.setSelectedIndex(4);
        limpiarTable();
        listadoVentas();
    }//GEN-LAST:event_btnVentaActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        if (txtDniRucCliente.getText().length() == 0) {
            JOptionPane.showConfirmDialog(rootPane, "Debes ingresar el DNI/RUC al cliente");
            txtDniRucCliente.requestFocus();
            return;
        }
        if (txtNombreCliente.getText().length() == 0) {
            JOptionPane.showConfirmDialog(rootPane, "Debes ingresar el nombre del cliente");
            txtNombreCliente.requestFocus();
            return;
        }
        if (txtTelefonoCliente.getText().length() == 0) {
            JOptionPane.showConfirmDialog(rootPane, "Debes ingresar algun numero de telefono al cliente");
            txtTelefonoCliente.requestFocus();
            return;
        }
        if (txtDireccionCliente.getText().length() == 0) {
            JOptionPane.showConfirmDialog(rootPane, "Debes ingresar alguna direccion del cliente");
            txtDireccionCliente.requestFocus();
            return;
        }
        cliente.setDni(txtDniRucCliente.getText());
        cliente.setNombre(txtNombreCliente.getText());
        cliente.setTelefono(txtTelefonoCliente.getText());
        cliente.setEmail(txtEmailCliente.getText());
        cliente.setDireccion(txtDireccionCliente.getText());
        cliente.setRazonSocial(txtRazonSocialCliente.getText());

        gestorCliente.insertar(cliente);
        JOptionPane.showMessageDialog(null, "Se registro Correctamente");
        limpiarTable();
        limpiarCamposCliente();
        listadoCliente();
        inhabilitarCliente();
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        limpiarTable();
        listadoCliente();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btnClienteActionPerformed

    private void tblListadoClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListadoClienteMouseClicked
        int fila = tblListadoCliente.rowAtPoint(evt.getPoint());

        txtIdCliente.setText(tblListadoCliente.getValueAt(fila, 0).toString());
        txtDniRucCliente.setText(tblListadoCliente.getValueAt(fila, 1).toString());
        txtNombreCliente.setText(tblListadoCliente.getValueAt(fila, 2).toString());
        txtTelefonoCliente.setText(tblListadoCliente.getValueAt(fila, 3).toString());
        txtEmailCliente.setText(tblListadoCliente.getValueAt(fila, 4).toString());
        txtDireccionCliente.setText(tblListadoCliente.getValueAt(fila, 5).toString());
        txtRazonSocialCliente.setText(tblListadoCliente.getValueAt(fila, 6).toString());
        habilitarCliente();
    }//GEN-LAST:event_tblListadoClienteMouseClicked

    private void btnActualizarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarClienteActionPerformed

        if (!txtIdCliente.getText().equals("")) {
            cliente.setDni(txtDniRucCliente.getText());
            cliente.setNombre(txtNombreCliente.getText());
            cliente.setTelefono(txtTelefonoCliente.getText());
            cliente.setEmail(txtEmailCliente.getText());
            cliente.setDireccion(txtDireccionCliente.getText());
            cliente.setRazonSocial(txtRazonSocialCliente.getText());
            cliente.setIdCliente(Integer.parseInt(txtIdCliente.getText()));
            if (!txtDniRucCliente.getText().equals("") || !txtNombreCliente.getText().equals("") || !txtTelefonoCliente.getText().equals("") || !txtEmailCliente.getText().equals("") || !txtDireccionCliente.getText().equals("") || !txtRazonSocialCliente.getText().equals("")) {
                gestorCliente.editar(cliente);
                JOptionPane.showMessageDialog(null, "Se actualizo los datos correctamente");
                limpiarTable();
                limpiarCamposCliente();
                listadoCliente();
                inhabilitarCliente();
            } else {
                JOptionPane.showMessageDialog(null, "Algunos de los campos estan vacios");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btnActualizarClienteActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        if (!txtIdCliente.getText().equals("")) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿ Estas seguro de Eliminar al Cliente " + txtNombreCliente.getText() + " ?");
            if (pregunta == 0) {
                int idCliente = Integer.parseInt(txtIdCliente.getText());
                gestorCliente.eliminar(idCliente);
                limpiarTable();
                limpiarCamposCliente();
                listadoCliente();
            }
        }
        inhabilitarCliente();
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        habilitarCliente();
        txtDniRucCliente.requestFocus();
        limpiarCamposCliente();
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
        limpiarTable();
        listadoProveedor();
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_btnProveedorActionPerformed

    private void btnGuardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProveedorActionPerformed
        if (txtRucProveedor.getText().length() == 0) {
            JOptionPane.showConfirmDialog(rootPane, "Debes ingresar el RUC al proveedor");
            txtRucProveedor.requestFocus();
            return;
        }
        if (txtNombreProveedor.getText().length() == 0) {
            JOptionPane.showConfirmDialog(rootPane, "Debes ingresar el nombre del proveedor");
            txtNombreCliente.requestFocus();
            return;
        }
        if (txtTelefonoProveedor.getText().length() == 0) {
            JOptionPane.showConfirmDialog(rootPane, "Debes ingresar algun numero de telefono al proveedor");
            txtTelefonoProveedor.requestFocus();
            return;
        }
        if (txtDireccionProveedor.getText().length() == 0) {
            JOptionPane.showConfirmDialog(rootPane, "Debes ingresar alguna direccion del proveedor");
            txtDireccionProveedor.requestFocus();
            return;
        }
        proveedor.setRuc(txtRucProveedor.getText());
        proveedor.setNombre(txtNombreProveedor.getText());
        proveedor.setTelefono(txtTelefonoProveedor.getText());
        proveedor.setEmail(txtEmailProveedor.getText());
        proveedor.setDireccion(txtDireccionProveedor.getText());
        proveedor.setPaginaWeb(txtPaginaWebProveedor.getText());

        gestorProveedor.insertar(proveedor);
        JOptionPane.showMessageDialog(null, "Se registro Correctamente");
        limpiarTable();
        limpiarCamposProveedor();
        listadoProveedor();
        inhabilitarProveedor();
    }//GEN-LAST:event_btnGuardarProveedorActionPerformed

    private void btnActualizarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarProveedorActionPerformed
        if (!txtIdProveedor.getText().equals("")) {
            proveedor.setRuc(txtRucProveedor.getText());
            proveedor.setNombre(txtNombreProveedor.getText());
            proveedor.setTelefono(txtTelefonoProveedor.getText());
            proveedor.setEmail(txtEmailProveedor.getText());
            proveedor.setDireccion(txtDireccionProveedor.getText());
            proveedor.setPaginaWeb(txtPaginaWebProveedor.getText());
            proveedor.setIdProveedor(Integer.parseInt(txtIdProveedor.getText()));
            if (!txtRucProveedor.getText().equals("") || !txtNombreProveedor.getText().equals("") || !txtTelefonoProveedor.getText().equals("") || !txtEmailProveedor.getText().equals("") || !txtDireccionProveedor.getText().equals("")) {
                gestorProveedor.editar(proveedor);
                JOptionPane.showMessageDialog(null, "Se actualizo los datos correctamente");
                limpiarTable();
                limpiarCamposProveedor();
                listadoProveedor();
                inhabilitarProveedor();
            } else {
                JOptionPane.showMessageDialog(null, "Algunos de los campos estan vacios");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btnActualizarProveedorActionPerformed

    private void tblListadoProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListadoProveedorMouseClicked
        int fila = tblListadoProveedor.rowAtPoint(evt.getPoint());

        txtIdProveedor.setText(tblListadoProveedor.getValueAt(fila, 0).toString());
        txtRucProveedor.setText(tblListadoProveedor.getValueAt(fila, 1).toString());
        txtNombreProveedor.setText(tblListadoProveedor.getValueAt(fila, 2).toString());
        txtTelefonoProveedor.setText(tblListadoProveedor.getValueAt(fila, 3).toString());
        txtEmailProveedor.setText(tblListadoProveedor.getValueAt(fila, 4).toString());
        txtDireccionProveedor.setText(tblListadoProveedor.getValueAt(fila, 5).toString());
        txtPaginaWebProveedor.setText(tblListadoProveedor.getValueAt(fila, 6).toString());
        habilitarProveedor();
    }//GEN-LAST:event_tblListadoProveedorMouseClicked

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        if (!txtIdProveedor.getText().equals("")) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿ Estas seguro de Eliminar al Proveedor " + txtNombreProveedor.getText() + " ?");
            if (pregunta == 0) {
                int idProveedor = Integer.parseInt(txtIdProveedor.getText());
                gestorProveedor.eliminar(idProveedor);
                limpiarTable();
                limpiarCamposProveedor();
                listadoProveedor();
                inhabilitarProveedor();
            }
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnNuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProveedorActionPerformed
        habilitarProveedor();
        txtRucProveedor.requestFocus();
        limpiarCamposProveedor();
    }//GEN-LAST:event_btnNuevoProveedorActionPerformed

    private void btnProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductoActionPerformed
        limpiarTable();
        listadoProducto();
        AutoCompleteDecorator.decorate(cboProveedor);
        gestorProducto.listadoProveedor(cboProveedor);
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_btnProductoActionPerformed

    private void btnNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProductoActionPerformed
        habilitarProducto();
        txtCodigoProducto.requestFocus();
        limpiarCamposProducto();
    }//GEN-LAST:event_btnNuevoProductoActionPerformed

    private void btnGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProductoActionPerformed
        if (txtCodigoProducto.getText().length() == 0) {
            JOptionPane.showConfirmDialog(rootPane, "Debes ingresar algun codigo al producto");
            txtCodigoProducto.requestFocus();
            return;
        }
        if (txtDescripcionProducto.getText().length() == 0) {
            JOptionPane.showConfirmDialog(rootPane, "Debes ingresarla la descripcion del producto");
            txtDescripcionProducto.requestFocus();
            return;
        }
        if (txtCantidadProducto.getText().length() == 0) {
            JOptionPane.showConfirmDialog(rootPane, "Debes ingresar la cantidad de stock del producto");
            txtCantidadProducto.requestFocus();
            return;
        }
        if (txtPrecioProducto.getText().length() == 0) {
            JOptionPane.showConfirmDialog(rootPane, "Debes ingresar el precio del producto");
            txtPrecioProducto.requestFocus();
            return;
        }
        producto.setCodigo(txtCodigoProducto.getText());
        producto.setDescripcion(txtDescripcionProducto.getText());
        producto.setCantidad(Integer.parseInt(txtCantidadProducto.getText()));
        producto.setPrecio(Double.parseDouble(txtPrecioProducto.getText()));
        producto.setProveedor(cboProveedor.getSelectedItem().toString());

        gestorProducto.insertar(producto);
        JOptionPane.showMessageDialog(null, "Se registro Correctamente");
        limpiarTable();
        limpiarCamposProducto();
        listadoProducto();
        inhabilitarProducto();
    }//GEN-LAST:event_btnGuardarProductoActionPerformed

    private void tblListadoProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListadoProductoMouseClicked
        int fila = tblListadoProducto.rowAtPoint(evt.getPoint());

        txtIdProducto.setText(tblListadoProducto.getValueAt(fila, 0).toString());
        txtCodigoProducto.setText(tblListadoProducto.getValueAt(fila, 1).toString());
        txtDescripcionProducto.setText(tblListadoProducto.getValueAt(fila, 2).toString());
        txtCantidadProducto.setText(tblListadoProducto.getValueAt(fila, 3).toString());
        txtPrecioProducto.setText(tblListadoProducto.getValueAt(fila, 4).toString());
        cboProveedor.setSelectedItem(tblListadoProducto.getValueAt(fila, 5).toString());
        habilitarProducto();
    }//GEN-LAST:event_tblListadoProductoMouseClicked

    private void btnActualizarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarProductoActionPerformed
        if (!txtIdProducto.getText().equals("")) {
            producto.setCodigo(txtCodigoProducto.getText());
            producto.setDescripcion(txtDescripcionProducto.getText());
            producto.setCantidad(Integer.parseInt(txtCantidadProducto.getText()));
            producto.setPrecio(Double.parseDouble(txtPrecioProducto.getText()));
            producto.setProveedor(cboProveedor.getSelectedItem().toString());

            producto.setIdProducto(Integer.parseInt(txtIdProducto.getText()));
            if (!txtCodigoProducto.getText().equals("") || !txtDescripcionProducto.getText().equals("") || !txtCantidadProducto.getText().equals("") || !txtPrecioProducto.getText().equals("") || !cboProveedor.getSelectedItem().equals("")) {
                gestorProducto.editar(producto);
                JOptionPane.showMessageDialog(null, "Se actualizo los datos correctamente");
                limpiarTable();
                limpiarCamposProducto();
                listadoProducto();
                inhabilitarProducto();
            } else {
                JOptionPane.showMessageDialog(null, "Algunos de los campos estan vacios");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btnActualizarProductoActionPerformed

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        if (!txtIdProducto.getText().equals("")) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿ Estas seguro de Eliminar el producto " + txtDescripcionProducto.getText() + " ?");
            if (pregunta == 0) {
                int idProducto = Integer.parseInt(txtIdProducto.getText());
                gestorProducto.eliminar(idProducto);
                limpiarTable();
                limpiarCamposProducto();
                listadoProducto();
                inhabilitarProducto();
            }
        }
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnReporteProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteProductoActionPerformed
        ReporteProductoExcel.reporte();
    }//GEN-LAST:event_btnReporteProductoActionPerformed

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!txtCodigo.getText().equals("")) {
                String codigo = txtCodigo.getText();
                producto = gestorProducto.buscarProducto(codigo);
                if (producto.getDescripcion() != null) {
                    txtDescripcion.setText("" + producto.getDescripcion());
                    txtStockDisponible.setText("" + producto.getCantidad());
                    txtPrecio.setText("" + producto.getPrecio());
                    txtCantidad.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto correctamente", "ERROR", 0);
                    LimpiarCamposVenta();
                    txtCodigo.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto");
                txtCodigo.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!txtCantidad.getText().equals("")) {
                String codigo = txtCodigo.getText();
                String descripcion = txtDescripcion.getText();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                double precio = Double.parseDouble(txtPrecio.getText());
                double precioTotal = cantidad * precio;
                int stock = Integer.parseInt(txtStockDisponible.getText());
                if (stock >= cantidad) {
                    item = item + 1;
                    modelo = (DefaultTableModel) tblListadoVenta.getModel();
                    for (int i = 0; i < tblListadoVenta.getRowCount(); i++) {
                        if (tblListadoVenta.getValueAt(i, 2).equals(txtDescripcion.getText())) {
                            JOptionPane.showMessageDialog(null, "El Producto Ya esta Registrado");
                            return;
                        }
                    }
                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(codigo);
                    lista.add(descripcion);
                    lista.add(cantidad);
                    lista.add(precio);
                    lista.add(precioTotal);
                    Object[] Obj = new Object[6];
                    Obj[0] = lista.get(0);
                    Obj[1] = lista.get(1);
                    Obj[2] = lista.get(2);
                    Obj[3] = lista.get(3);
                    Obj[4] = lista.get(4);
                    Obj[5] = lista.get(5);
                    modelo.addRow(Obj);
                    tblListadoVenta.setModel(modelo);
                    totalPagar();
                    LimpiarCamposVenta();
                    txtCodigo.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese la cantidad del producto");
            }
        }
    }//GEN-LAST:event_txtCantidadKeyPressed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        model = (DefaultTableModel) tblListadoVenta.getModel();
        model.removeRow(tblListadoVenta.getSelectedRow());
        totalPagar();
        txtCodigo.requestFocus();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtDniRucClienteVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniRucClienteVKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!txtDniRucClienteV.getText().equals("")) {
                String dni = txtDniRucClienteV.getText();
                cliente = gestorCliente.buscarCliente(dni);
                if (cliente.getNombre() != null) {
                    txtNombreClienteV.setText("" + cliente.getNombre());
                    txtTelefonoCV.setText("" + cliente.getTelefono());
                    txtEmailCV.setText("" + cliente.getEmail());
                    txtDireccionCV.setText("" + cliente.getDireccion());
                    txtRazonSocialCV.setText("" + cliente.getRazonSocial());
                } else {
                    txtDniRucClienteV.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe", "ERROR", 0);
                }
            }
        }
    }//GEN-LAST:event_txtDniRucClienteVKeyPressed

    private void btnInsertarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarVentaActionPerformed
        if (tblListadoVenta.getRowCount() > 0) {
            if (!txtNombreClienteV.getText().equals("")) {
                registrarVenta();
                registrarDetalleVenta();
                actualizarStock();
                pdf();
                limpiarTablaVenta();
                txtDniRucClienteV.setText("");
                txtNombreClienteV.setText("");
                txtTelefonoCV.setText("");
                txtEmailCV.setText("");
                txtDireccionCV.setText("");
                txtRazonSocialCV.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Debes buscar al cliente");
            }
        } else {
            JOptionPane.showMessageDialog(null, "NO Hay Producto en el venta");
        }

    }//GEN-LAST:event_btnInsertarVentaActionPerformed

    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        limpiarTable();
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped

    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtDniRucClienteVKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniRucClienteVKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtDniRucClienteVKeyTyped

    private void txtPrecioProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProductoKeyTyped
        event.numberDecimalKeyPress(evt, txtPrecioProducto);
    }//GEN-LAST:event_txtPrecioProductoKeyTyped

    private void btnActualizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnActualizarKeyPressed

    }//GEN-LAST:event_btnActualizarKeyPressed

    private void btnVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnVentaKeyPressed

    }//GEN-LAST:event_btnVentaKeyPressed

    private void tblListadoVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListadoVentasMouseClicked
        int fila = tblListadoVentas.rowAtPoint(evt.getPoint());
        txtIdVentas.setText(tblListadoVentas.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_tblListadoVentasMouseClicked

    private void btnReporteVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteVentasActionPerformed
        try {
            int id = Integer.parseInt(txtIdVentas.getText());
            File file = new File("src/ReportesVenta/ReporteDeVentas" + id + ".pdf");
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnReporteVentasActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (!txtIdEmpresa.getText().equals("")) {
            config.setNombre(txtNombreEmpresa.getText());
            config.setRuc(txtRucEmpresa.getText());
            config.setTelefono(txtTelefonoEmpresa.getText());
            config.setEmail(txtEmailEmpresa.getText());
            config.setDireccion(txtDireccionEmpresa.getText());
            config.setRazonSocial(txtRazonSocialEmpresa.getText());

            config.setId(Integer.parseInt(txtIdProducto.getText()));
            if (!txtNombreEmpresa.getText().equals("") || !txtRucEmpresa.getText().equals("") || !txtTelefonoEmpresa.getText().equals("") || !txtEmailEmpresa.getText().equals("") || !txtDireccionEmpresa.getText().equals("") || !txtRazonSocialEmpresa.getText().equals("")) {
                gestorProducto.actualizarDatos(config);
                listadoConfig();
                JOptionPane.showMessageDialog(null, "Se actualizo los datos correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Algunos de los campos estan vacios");
            }
        } else {
            JOptionPane.showMessageDialog(null, "");
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnReporteGraficoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteGraficoActionPerformed
        String fechaReporte = new SimpleDateFormat("dd/MM/yyyy").format(dtcFecha.getDate());
        ReporteGrafico.graficar(fechaReporte);
    }//GEN-LAST:event_btnReporteGraficoActionPerformed
    private void registrarVenta() {
        String clien = txtNombreClienteV.getText();
        String vendedor = lblVendedor.getText();
        double monto = total;

        venta.setCliente(clien);
        venta.setVendedor(vendedor);
        venta.setTotal(monto);
        venta.setFecha(fechaActual);
        gestorVenta.insertarVenta(venta);
    }

    private void registrarDetalleVenta() {
        int idVenta = gestorVenta.idVenta();
        for (int i = 0; i < tblListadoVenta.getRowCount(); i++) {
            String codigoProducto = tblListadoVenta.getValueAt(i, 1).toString();
            int cantidad = Integer.parseInt(tblListadoVenta.getValueAt(i, 3).toString());
            double precio = Double.parseDouble(tblListadoVenta.getValueAt(i, 5).toString());
            detalleVenta.setCodProducto(codigoProducto);
            detalleVenta.setCantidad(cantidad);
            detalleVenta.setPrecio(precio);
            detalleVenta.setIdVenta(idVenta);
            gestorVenta.insertarDetalleVenta(detalleVenta);
        }
    }

    private void actualizarStock() {
        for (int i = 0; i < tblListadoVenta.getRowCount(); i++) {
            String codigo = tblListadoVenta.getValueAt(i, 1).toString();
            int cantidad = Integer.parseInt(tblListadoVenta.getValueAt(i, 3).toString());
            producto = gestorProducto.buscarProducto(codigo);
            int stockActual = producto.getCantidad() - cantidad;
            gestorVenta.actualizarStock(stockActual, codigo);
        }
    }

    private void limpiarTablaVenta() {
        modelo = (DefaultTableModel) tblListadoVenta.getModel();
        int fila = tblListadoVenta.getRowCount();
        for (int i = 0; i < fila; i++) {
            modelo.removeRow(0);
        }
    }

    private void pdf() {
        try {
            int id = gestorVenta.idVenta();
            FileOutputStream archivo;
            File file = new File("src/ReportesVenta/ReporteDeVentas" + id + ".pdf");
            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);

            doc.open();

            Image img = Image.getInstance("src/Files/logo.png");
            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            fecha.add("Fecha: " + new SimpleDateFormat("dd-mm-yyyy").format(date) + "\n\n");

            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] columnaEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(columnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            Encabezado.addCell(img);

            String ruc = txtRucEmpresa.getText();
            String nombre = txtNombreEmpresa.getText();
            String telefono = txtTelefonoEmpresa.getText();
            String email = txtEmailEmpresa.getText();
            String direccion = txtDireccionEmpresa.getText();
            String razonSocial = txtRazonSocialEmpresa.getText();

            Encabezado.addCell("");
            Encabezado.addCell("RUC: " + ruc + "\nNombre: " + nombre + "\nTelefono: " + telefono + "\nEmail: " + email + "\nDireccion: " + direccion + "\nRazon Social: " + razonSocial);
            Encabezado.addCell(fecha);
            doc.add(Encabezado);

            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add("Datos de los clientes" + "\n\n");
            doc.add(cli);

            PdfPTable tablaCliente = new PdfPTable(4);
            tablaCliente.setWidthPercentage(100);
            tablaCliente.getDefaultCell().setBorder(0);
            float[] columnaCliente = new float[]{20f, 50f, 30f, 40f};
            tablaCliente.setWidths(columnaCliente);
            tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell client1 = new PdfPCell(new Phrase("DNI / RUC", negrita));
            PdfPCell client2 = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell client3 = new PdfPCell(new Phrase("Telefono", negrita));
            PdfPCell client4 = new PdfPCell(new Phrase("Dirección", negrita));
            client1.setBorder(0);
            client2.setBorder(0);
            client3.setBorder(0);
            client4.setBorder(0);
            tablaCliente.addCell(client1);
            tablaCliente.addCell(client2);
            tablaCliente.addCell(client3);
            tablaCliente.addCell(client4);
            tablaCliente.addCell(txtDniRucClienteV.getText());
            tablaCliente.addCell(txtNombreClienteV.getText());
            tablaCliente.addCell(txtTelefonoCV.getText());
            tablaCliente.addCell(txtDireccionCV.getText());
            doc.add(tablaCliente);

            Paragraph prud = new Paragraph();
            prud.add(Chunk.NEWLINE);
            prud.add("Productos" + "\n\n");
            doc.add(prud);

            PdfPTable tablaProducto = new PdfPTable(4);
            tablaProducto.setWidthPercentage(100);
            tablaProducto.getDefaultCell().setBorder(0);
            float[] columnaProducto = new float[]{10f, 50f, 15f, 20f};
            tablaProducto.setWidths(columnaProducto);
            tablaProducto.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell produc1 = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell produc2 = new PdfPCell(new Phrase("Descripción", negrita));
            PdfPCell produc3 = new PdfPCell(new Phrase("Precio Unitario", negrita));
            PdfPCell produc4 = new PdfPCell(new Phrase("Precio Total", negrita));
            produc1.setBorder(0);
            produc2.setBorder(0);
            produc3.setBorder(0);
            produc4.setBorder(0);
            produc1.setBackgroundColor(BaseColor.DARK_GRAY);
            produc2.setBackgroundColor(BaseColor.DARK_GRAY);
            produc3.setBackgroundColor(BaseColor.DARK_GRAY);
            produc4.setBackgroundColor(BaseColor.DARK_GRAY);
            tablaProducto.addCell(produc1);
            tablaProducto.addCell(produc2);
            tablaProducto.addCell(produc3);
            tablaProducto.addCell(produc4);
            for (int i = 0; i < tblListadoVenta.getRowCount(); i++) {
                String cantidad = tblListadoVenta.getValueAt(i, 3).toString();
                String descripcion = tblListadoVenta.getValueAt(i, 2).toString();
                String precio = tblListadoVenta.getValueAt(i, 4).toString();
                String preciototal = tblListadoVenta.getValueAt(i, 5).toString();
                tablaProducto.addCell(cantidad);
                tablaProducto.addCell(descripcion);
                tablaProducto.addCell(precio);
                tablaProducto.addCell(preciototal);
            }

            doc.add(tablaProducto);

            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total a Pagar: " + total);
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Cancelación y Firma\n\n");
            firma.add("------------------------");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);

            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("Gracias por su compra");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);

            doc.close();
            archivo.close();
            Desktop.getDesktop().open(file);
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnActualizarCliente;
    private javax.swing.JButton btnActualizarProducto;
    private javax.swing.JButton btnActualizarProveedor;
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnConfiguracion;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarProducto;
    private javax.swing.JButton btnGuardarProveedor;
    private javax.swing.JButton btnInsertarVenta;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoProducto;
    private javax.swing.JButton btnNuevoProveedor;
    private javax.swing.JButton btnProducto;
    private javax.swing.JButton btnProveedor;
    private javax.swing.JButton btnReporteGrafico;
    private javax.swing.JButton btnReporteProducto;
    private javax.swing.JButton btnReporteVentas;
    private javax.swing.JButton btnVenta;
    private javax.swing.JComboBox<String> cboProveedor;
    private com.toedter.calendar.JDateChooser dtcFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalPagar;
    private javax.swing.JLabel lblVendedor;
    private javax.swing.JTable tblListadoCliente;
    private javax.swing.JTable tblListadoProducto;
    private javax.swing.JTable tblListadoProveedor;
    private javax.swing.JTable tblListadoVenta;
    private javax.swing.JTable tblListadoVentas;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCantidadProducto;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoProducto;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDescripcionProducto;
    private javax.swing.JTextField txtDireccionCV;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JTextField txtDireccionEmpresa;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtDniRucCliente;
    private javax.swing.JTextField txtDniRucClienteV;
    private javax.swing.JTextField txtEmailCV;
    private javax.swing.JTextField txtEmailCliente;
    private javax.swing.JTextField txtEmailEmpresa;
    private javax.swing.JTextField txtEmailProveedor;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdEmpresa;
    private javax.swing.JTextField txtIdProducto;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtIdVentas;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreClienteV;
    private javax.swing.JTextField txtNombreEmpresa;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtPaginaWebProveedor;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtPrecioProducto;
    private javax.swing.JTextField txtRazonSocialCV;
    private javax.swing.JTextField txtRazonSocialCliente;
    private javax.swing.JTextField txtRazonSocialEmpresa;
    private javax.swing.JTextField txtRucEmpresa;
    private javax.swing.JTextField txtRucProveedor;
    private javax.swing.JTextField txtStockDisponible;
    private javax.swing.JTextField txtTelefonoCV;
    private javax.swing.JTextField txtTelefonoCliente;
    private javax.swing.JTextField txtTelefonoEmpresa;
    private javax.swing.JTextField txtTelefonoProveedor;
    // End of variables declaration//GEN-END:variables
}
