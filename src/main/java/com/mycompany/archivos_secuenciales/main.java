package com.mycompany.archivos_secuenciales;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import java.io.EOFException;
import java.io.RandomAccessFile;


public class main extends javax.swing.JFrame {

    DataOutputStream write;
    DataInputStream read;
    String path = "C:\\Proyecto\\vc.txt";

    Files f;
    contacto cto;
    contacto admin;

    piezas_File pf;
    piezas pi;

    reparaciones_File rf;
    reparaciones rep;

    Vehiculos_Files v;
    Vehiculos vcs;

    VC_File vcf;
    vehiculo_cliente vc;

    Cliente_File fc;
    cliente c;

    String IdUs;

    boolean ban = false;
    boolean ban_vehiculos = false;
    boolean ban_reparaciones = false;
    boolean ban_piezas = false;

    public main() throws IOException {
        initComponents();
        f = new Files();
        pf = new piezas_File();
        rf = new reparaciones_File();
        fc = new Cliente_File();
        v = new Vehiculos_Files();
        vcf = new VC_File();

        admin = new contacto();
        admin.setId(0);
        admin.setNombre("Admi");
        admin.setPaterno("Nistra");
        admin.setMaterno("Dor");
        admin.setUsername("super");
        admin.setTelefono("3359525148");
        admin.setDireccion("plata");
        admin.setPassword("123");
        admin.setPerfil("Admin");

        btn_V_Guardar.setEnabled(false);
        btn_V_Nuevo.setEnabled(true);
        btn_V_Editar.setEnabled(false);
        btn_V_Eliminar.setEnabled(false);
        btn_V_Cancelar.setEnabled(false);

        btn_C_Guardar.setEnabled(false);
        btn_C_Nuevo.setEnabled(true);
        btn_C_Editar.setEnabled(false);
        btn_C_Eliminar.setEnabled(false);
        btn_C_Cancelar.setEnabled(false);

        btn_R_Guardar.setEnabled(false);
        btn_R_Nuevo.setEnabled(true);
        btn_R_Editar.setEnabled(false);
        btn_R_Eliminar.setEnabled(false);
        btn_R_Cancelar.setEnabled(false);

        btn_P_Guardar.setEnabled(false);
        btn_P_Nuevo.setEnabled(true);
        btn_P_Editar.setEnabled(false);
        btn_P_Eliminar.setEnabled(false);
        btn_P_Cancelar.setEnabled(false);

        try {
            if (f.BuscarContacto(admin) == null) {
                f.Guardar(admin);
            }
        } catch (FileNotFoundException ex) {

        }
        tpane.setEnabledAt(1, false);
        tpane.setEnabledAt(2, false);
        tpane.setEnabledAt(3, false);
        tpane.setEnabledAt(4, false);
        tpane.setEnabledAt(5, false);

    }

    public boolean ValidaNum(String dato) {
        return dato.matches("[0-9]*");
    }

   public void cb_vehiculos() {
    cb_V_SeleccioneCliente.removeAllItems();
    cb_V_SeleccioneCliente.addItem("Seleccione");
    String us = "", cl = "";
    try (DataInputStream read = new DataInputStream(new FileInputStream(path))) {
        while (true) {
            try {
                us = read.readUTF();
                cl = read.readUTF();
                if (us.equals(IdUs) || "0".equals(IdUs)) {
                    cb_V_SeleccioneCliente.addItem(cl);
                }
            } catch (EOFException e) {
                // Fin del archivo alcanzado
                break;
            }
        }
    } catch (FileNotFoundException ex) {
        ex.printStackTrace(); // Log de error
    } catch (IOException ex) {
        ex.printStackTrace(); // Log de error
    }
}

    public void cb_R_vehiculos() {
        String vl = "C:\\Proyecto\\vehiculos.txt";
        cb_R_IdVehiculo.removeAllItems();
        cb_R_IdVehiculo.addItem("Seleccione");

        String cl = "", mat = "", marc = "", mo = "", fe = "", color = "", nota = "";
        int id;

        try (RandomAccessFile read = new RandomAccessFile(vl, "r")) {
            while (true) {
                try {
                    // Read data from specific positions if needed
                    cl = read.readUTF();
                    id = read.readInt();
                    mat = read.readUTF();
                    marc = read.readUTF();
                    mo = read.readUTF();
                    fe = read.readUTF();
                    color = read.readUTF();
                    nota = read.readUTF();

                    // Add the vehicle ID to the combo box
                    cb_R_IdVehiculo.addItem(String.valueOf(id));
                } catch (EOFException e) {
                    // End of file reached
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(); // Log the error
        } catch (IOException ex) {
            ex.printStackTrace(); // Log the error
        }
    }

    public void cb_R_Pieza() {
        String pz = "C:\\Proyecto\\piezas.txt";
        cb_R_IdPieza.removeAllItems();
        cb_R_IdPieza.addItem("Seleccione");
        int id = 0, stock = 0;
        String item = "", des = "";
        try {
            read = new DataInputStream(new FileInputStream(pz));
            while (true) {
                id = read.readInt();
                des = read.readUTF();
                stock = read.readInt();

                item = String.valueOf(id);
                cb_R_IdPieza.addItem(item);
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
        }
        try {
            read.close();
        } catch (IOException ex) {

        }
    }

    public boolean txt_R_Control() {
        boolean ban = false;
        try {
            pi = new piezas();
            pi.SetPiz(Integer.parseInt(cb_R_IdPieza.getSelectedItem().toString()));
            pi = pf.BuscarPiezas(pi);
            if (Integer.parseInt(txt_R_ControlPiezas.getText()) <= pi.getStock() && Integer.parseInt(txt_R_ControlPiezas.getText()) != 0) {
                ban = true;
            }
//pi.SetStock(Integer.parseInt(txt_R_ControlPiezas.getText()));
        } catch (FileNotFoundException ex) {
        }
        return ban;
    }

    public void Habilitar() {
        txtNombre.setEditable(true);
        txtPaterno.setEditable(true);
        txtMaterno.setEditable(true);
        txtTelefono.setEditable(true);
        txtUsername.setEditable(true);
        cbPerfil.setEditable(true);
        txtDireccion.setEditable(true);
        txtPsw.setEditable(true);
    }

    public void Deshabilitar() {
        txtNombre.setEditable(false);
        txtPaterno.setEditable(false);
        txtMaterno.setEditable(false);
        txtTelefono.setEditable(false);
        txtUsername.setEditable(false);
        cbPerfil.setEditable(false);
        txtDireccion.setEditable(false);
        txtPsw.setEditable(false);

        txtNombre.setText("");
        txtTelefono.setText("");
        txtPaterno.setText("");
        txtMaterno.setText("");
        txtUsername.setText("");
        cbPerfil.setSelectedItem("");
        txtDireccion.setText("");

    }

    public void Vehiculos_Habilitar() {
        txt_V_Matricula.setEditable(true);
        txt_V_Marca.setEditable(true);
        txt_V_Modelo.setEditable(true);
        jdt_V_Fecha.setEnabled(true);
        txt_V_Color.setEditable(true);
        jTextArea1.setEditable(true);
    }

    public void Reparaciones_Habilitar() {
        txt_R_Falla.setEditable(true);
        txt_R_ControlPiezas.setEditable(true);
        jdt_E_Fecha.setEnabled(true);
        jdt_S_Fecha.setEnabled(true);
    }

    public void Piezas_Habilitar() {
        txt_P_Descripcion.setEditable(true);
        txt_P_Stock.setEditable(true);
    }

    public void Vehiculos_Deshabilitar() {
        txt_V_Matricula.setEditable(false);
        txt_V_Marca.setEditable(false);
        txt_V_Modelo.setEditable(false);
        jdt_V_Fecha.setEnabled(false);
        txt_V_Color.setEditable(false);
        jTextArea1.setEditable(false);
        jdt_V_Fecha.setDate(null);
        jdt_V_Fecha.cleanup();
        //int maxID = v.getMax();
        //txt_V_IdVehiculo.setText(""String.valueOf(maxID)"");
        txt_V_IdVehiculo.setText("");
        txt_V_Matricula.setText("");
        txt_V_Marca.setText("");
        txt_V_Modelo.setText("");
        txt_V_Color.setText("");
        jTextArea1.setText("");
    }

    public void Reparaciones_Deshabilitar() {
        txt_R_Falla.setEditable(false);
        jdt_E_Fecha.setEnabled(false);
        jdt_S_Fecha.setEnabled(false);

        jdt_E_Fecha.setDate(null);
        jdt_E_Fecha.cleanup();

        jdt_S_Fecha.setDate(null);
        jdt_S_Fecha.cleanup();

        int maxID = rf.getMax();
        txt_R_IdReparacion.setText(String.valueOf(maxID));

        txt_R_Falla.setText("");
        txt_R_IdReparacion.setText("");
        txt_R_ControlPiezas.setText("");
    }

    public void Piezas_Deshabilitar() {
        txt_P_Descripcion.setEditable(false);
        txt_P_Stock.setEditable(false);

        //int maxID = pf.getMax();
        //txt_P_IdPieza.setText(String.valueOf(maxID));
        txt_P_IdPieza.setText("");
        txt_P_Descripcion.setText("");
        txt_P_Stock.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jDialog1 = new javax.swing.JDialog();
        lbl_V_Fecha2 = new javax.swing.JLabel();
        tpane = new javax.swing.JTabbedPane();
        pnlLogin = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnAutentificar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        pnlUsuarios = new javax.swing.JPanel();
        txtID = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMaterno = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        cbPerfil = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        txtPsw = new javax.swing.JTextField();
        btnSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPaterno = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lblPassword1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        pnlClientes = new javax.swing.JPanel();
        lbl_C_IdUsuario = new javax.swing.JLabel();
        lbl_C_IdCliente = new javax.swing.JLabel();
        lbl_C_Id = new javax.swing.JLabel();
        lbl_C_Nombre = new javax.swing.JLabel();
        lbl_C_ApellidoPaterno = new javax.swing.JLabel();
        lbl_C_ApellidoMaterno = new javax.swing.JLabel();
        txt_C_Buscar = new javax.swing.JTextField();
        txt_C_IdCliente = new javax.swing.JTextField();
        txt_C_Nombre = new javax.swing.JTextField();
        txt_C_ApellidoPaterno = new javax.swing.JTextField();
        txt_C_ApellidoMaterno = new javax.swing.JTextField();
        btn_C_Buscar = new javax.swing.JButton();
        btn_C_Salir = new javax.swing.JButton();
        txt_C_IdUsuario = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btn_C_Nuevo = new javax.swing.JButton();
        btn_C_Guardar = new javax.swing.JButton();
        btn_C_Cancelar = new javax.swing.JButton();
        btn_C_Editar = new javax.swing.JButton();
        btn_C_Eliminar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        pnlReparaciones = new javax.swing.JPanel();
        lbl_R_Id = new javax.swing.JLabel();
        lbl_R_IdVehiculo = new javax.swing.JLabel();
        lbl_R_IdPieza = new javax.swing.JLabel();
        lbl_R_Id_Reparacion = new javax.swing.JLabel();
        lbl_R_Falla = new javax.swing.JLabel();
        lbl_R_ControlPiezas = new javax.swing.JLabel();
        lbl_R_FechaEntrada = new javax.swing.JLabel();
        lbl_R_FechaSalida = new javax.swing.JLabel();
        cb_R_IdVehiculo = new javax.swing.JComboBox<>();
        cb_R_IdPieza = new javax.swing.JComboBox<>();
        txt_R_Id = new javax.swing.JTextField();
        txt_R_IdReparacion = new javax.swing.JTextField();
        txt_R_Falla = new javax.swing.JTextField();
        txt_R_ControlPiezas = new javax.swing.JTextField();
        btn_R_Buscar = new javax.swing.JButton();
        btn_R_Salir = new javax.swing.JButton();
        jdt_E_Fecha = new com.toedter.calendar.JDateChooser();
        jdt_S_Fecha = new com.toedter.calendar.JDateChooser();
        jPanel8 = new javax.swing.JPanel();
        btn_R_Nuevo = new javax.swing.JButton();
        btn_R_Guardar = new javax.swing.JButton();
        btn_R_Cancelar = new javax.swing.JButton();
        btn_R_Editar = new javax.swing.JButton();
        btn_R_Eliminar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        pnlVehiculos = new javax.swing.JPanel();
        lbl_V_SeleccioneCliente = new javax.swing.JLabel();
        lbl_V_IdVehiculo = new javax.swing.JLabel();
        lbl_V_Matricula = new javax.swing.JLabel();
        lbl_V_Marca = new javax.swing.JLabel();
        lbl_V_Modelo = new javax.swing.JLabel();
        lbl_V_Id = new javax.swing.JLabel();
        lbl_V_Fecha = new javax.swing.JLabel();
        txt_V_Buscar = new javax.swing.JTextField();
        txt_V_IdVehiculo = new javax.swing.JTextField();
        txt_V_Matricula = new javax.swing.JTextField();
        txt_V_Marca = new javax.swing.JTextField();
        txt_V_Modelo = new javax.swing.JTextField();
        cb_V_SeleccioneCliente = new javax.swing.JComboBox<>();
        btn_V_Buscar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jdt_V_Fecha = new com.toedter.calendar.JDateChooser();
        lbl_V_Color = new javax.swing.JLabel();
        txt_V_Color = new javax.swing.JTextField();
        lbl_V_Notas = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        btn_V_Nuevo = new javax.swing.JButton();
        btn_V_Guardar = new javax.swing.JButton();
        btn_V_Cancelar = new javax.swing.JButton();
        btn_V_Editar = new javax.swing.JButton();
        btn_V_Eliminar = new javax.swing.JButton();
        btn_R_Salir1 = new javax.swing.JButton();
        pnlPiezas = new javax.swing.JPanel();
        lbl_P_Id = new javax.swing.JLabel();
        lbl_P_IdPieza = new javax.swing.JLabel();
        lbl_P_Descripcion = new javax.swing.JLabel();
        lbl_P_Stock = new javax.swing.JLabel();
        txt_P_Id = new javax.swing.JTextField();
        txt_P_IdPieza = new javax.swing.JTextField();
        txt_P_Descripcion = new javax.swing.JTextField();
        txt_P_Stock = new javax.swing.JTextField();
        btn_P_Buscar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btn_P_Nuevo = new javax.swing.JButton();
        btn_P_Guardar = new javax.swing.JButton();
        btn_P_Cancelar = new javax.swing.JButton();
        btn_P_Editar = new javax.swing.JButton();
        btn_P_Eliminar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        btn_P_Salir = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();

        jButton3.setText("Nuevo");

        jButton12.setText("jButton1");

        jButton7.setText("jButton1");

        jButton14.setText("jButton1");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        lbl_V_Fecha2.setText("Color");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlLogin.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(2, 131, 211));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 192, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlLogin.add(jPanel2);
        jPanel2.setBounds(830, 0, 192, 733);

        jPanel1.setLayout(null);

        lblUsuario.setFont(new java.awt.Font("MS PGothic", 0, 36)); // NOI18N
        lblUsuario.setText("Usuario");
        lblUsuario.setMaximumSize(new java.awt.Dimension(60, 24));
        lblUsuario.setName(""); // NOI18N
        lblUsuario.setPreferredSize(new java.awt.Dimension(60, 24));
        jPanel1.add(lblUsuario);
        lblUsuario.setBounds(190, 80, 120, 40);
        jPanel1.add(txtUsuario);
        txtUsuario.setBounds(330, 80, 180, 40);

        jLabel2.setFont(new java.awt.Font("MS PGothic", 0, 36)); // NOI18N
        jLabel2.setText("Password");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(160, 140, 150, 37);

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        jPanel1.add(txtPassword);
        txtPassword.setBounds(330, 140, 180, 37);

        btnAutentificar.setFont(new java.awt.Font("MS PGothic", 0, 18)); // NOI18N
        btnAutentificar.setText("Autentificar");
        btnAutentificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutentificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnAutentificar);
        btnAutentificar.setBounds(320, 270, 180, 32);

        pnlLogin.add(jPanel1);
        jPanel1.setBounds(10, 250, 800, 390);

        jPanel3.setLayout(null);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/iniciar-sesion.png"))); // NOI18N
        jPanel3.add(jLabel7);
        jLabel7.setBounds(370, 0, 130, 222);

        jCheckBox1.setText("Modo oscuro");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel3.add(jCheckBox1);
        jCheckBox1.setBounds(710, 0, 110, 40);

        pnlLogin.add(jPanel3);
        jPanel3.setBounds(-5, 20, 830, 266);

        tpane.addTab("Login", pnlLogin);

        pnlUsuarios.setLayout(null);

        txtID.setEditable(false);
        pnlUsuarios.add(txtID);
        txtID.setBounds(144, 49, 200, 30);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        pnlUsuarios.add(btnBuscar);
        btnBuscar.setBounds(371, 7, 100, 30);
        pnlUsuarios.add(txtBuscar);
        txtBuscar.setBounds(144, 6, 200, 30);
        pnlUsuarios.add(txtNombre);
        txtNombre.setBounds(144, 92, 200, 30);
        pnlUsuarios.add(txtTelefono);
        txtTelefono.setBounds(144, 218, 200, 30);

        jLabel3.setText("Apellido Paterno");
        pnlUsuarios.add(jLabel3);
        jLabel3.setBounds(20, 134, 100, 30);

        jLabel4.setText("Apellido Materno");
        pnlUsuarios.add(jLabel4);
        jLabel4.setBounds(20, 176, 100, 30);

        jLabel5.setText("Username:");
        pnlUsuarios.add(jLabel5);
        jLabel5.setBounds(20, 260, 100, 30);
        pnlUsuarios.add(txtMaterno);
        txtMaterno.setBounds(144, 176, 200, 30);
        pnlUsuarios.add(txtUsername);
        txtUsername.setBounds(144, 260, 200, 30);

        cbPerfil.setEditable(true);
        cbPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Gerente", "Secretaria", "Mecánico" }));
        pnlUsuarios.add(cbPerfil);
        cbPerfil.setBounds(144, 380, 200, 30);

        jLabel6.setText("Perfil:");
        pnlUsuarios.add(jLabel6);
        jLabel6.setBounds(20, 380, 100, 30);

        lblPassword.setText("Password");
        pnlUsuarios.add(lblPassword);
        lblPassword.setBounds(20, 296, 100, 30);
        pnlUsuarios.add(txtDireccion);
        txtDireccion.setBounds(144, 338, 200, 30);
        pnlUsuarios.add(txtPsw);
        txtPsw.setBounds(144, 296, 200, 30);

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        pnlUsuarios.add(btnSalir);
        btnSalir.setBounds(371, 50, 100, 30);

        jLabel1.setText("Ingrese ID");
        pnlUsuarios.add(jLabel1);
        jLabel1.setBounds(20, 6, 100, 30);

        jLabel8.setText("ID");
        pnlUsuarios.add(jLabel8);
        jLabel8.setBounds(20, 49, 100, 30);

        jLabel9.setText("Nombre");
        pnlUsuarios.add(jLabel9);
        jLabel9.setBounds(20, 92, 100, 30);
        pnlUsuarios.add(txtPaterno);
        txtPaterno.setBounds(144, 134, 200, 30);

        jLabel10.setText("Telefono");
        pnlUsuarios.add(jLabel10);
        jLabel10.setBounds(20, 218, 100, 30);

        lblPassword1.setText("Dirección");
        pnlUsuarios.add(lblPassword1);
        lblPassword1.setBounds(20, 338, 100, 30);

        jPanel4.setBackground(new java.awt.Color(39, 53, 99));

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnSalvar.setText("Guardar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnRemover.setText("Eliminar");
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(466, 466, 466)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(444, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlUsuarios.add(jPanel4);
        jPanel4.setBounds(6, 431, 1016, 302);

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/user-interface.png"))); // NOI18N
        pnlUsuarios.add(jLabel11);
        jLabel11.setBounds(590, 30, 330, 310);

        tpane.addTab("Usuarios", pnlUsuarios);

        pnlClientes.setLayout(null);

        lbl_C_IdUsuario.setText("ID Usuario");
        pnlClientes.add(lbl_C_IdUsuario);
        lbl_C_IdUsuario.setBounds(561, 6, 100, 30);

        lbl_C_IdCliente.setText("ID Cliente");
        pnlClientes.add(lbl_C_IdCliente);
        lbl_C_IdCliente.setBounds(20, 86, 100, 30);

        lbl_C_Id.setText("ID");
        pnlClientes.add(lbl_C_Id);
        lbl_C_Id.setBounds(20, 7, 100, 30);

        lbl_C_Nombre.setText("Nombre");
        pnlClientes.add(lbl_C_Nombre);
        lbl_C_Nombre.setBounds(20, 128, 100, 30);

        lbl_C_ApellidoPaterno.setText("Apellido Paterno");
        pnlClientes.add(lbl_C_ApellidoPaterno);
        lbl_C_ApellidoPaterno.setBounds(20, 170, 100, 30);

        lbl_C_ApellidoMaterno.setText("Apellido Materno");
        pnlClientes.add(lbl_C_ApellidoMaterno);
        lbl_C_ApellidoMaterno.setBounds(20, 212, 100, 30);
        pnlClientes.add(txt_C_Buscar);
        txt_C_Buscar.setBounds(138, 6, 200, 30);

        txt_C_IdCliente.setEditable(false);
        pnlClientes.add(txt_C_IdCliente);
        txt_C_IdCliente.setBounds(138, 86, 200, 30);

        txt_C_Nombre.setEditable(false);
        pnlClientes.add(txt_C_Nombre);
        txt_C_Nombre.setBounds(138, 128, 200, 30);

        txt_C_ApellidoPaterno.setEditable(false);
        pnlClientes.add(txt_C_ApellidoPaterno);
        txt_C_ApellidoPaterno.setBounds(138, 170, 200, 30);

        txt_C_ApellidoMaterno.setEditable(false);
        pnlClientes.add(txt_C_ApellidoMaterno);
        txt_C_ApellidoMaterno.setBounds(138, 212, 200, 30);

        btn_C_Buscar.setText("Buscar");
        btn_C_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_C_BuscarActionPerformed(evt);
            }
        });
        pnlClientes.add(btn_C_Buscar);
        btn_C_Buscar.setBounds(356, 7, 100, 30);

        btn_C_Salir.setText("Salir");
        btn_C_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_C_SalirActionPerformed(evt);
            }
        });
        pnlClientes.add(btn_C_Salir);
        btn_C_Salir.setBounds(834, 7, 100, 30);

        txt_C_IdUsuario.setEditable(false);
        pnlClientes.add(txt_C_IdUsuario);
        txt_C_IdUsuario.setBounds(667, 10, 130, 22);

        jPanel5.setBackground(new java.awt.Color(39, 53, 99));
        jPanel5.setLayout(null);

        btn_C_Nuevo.setText("Nuevo");
        btn_C_Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_C_NuevoActionPerformed(evt);
            }
        });
        jPanel5.add(btn_C_Nuevo);
        btn_C_Nuevo.setBounds(10, 10, 99, 30);

        btn_C_Guardar.setText("Guardar");
        btn_C_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_C_GuardarActionPerformed(evt);
            }
        });
        jPanel5.add(btn_C_Guardar);
        btn_C_Guardar.setBounds(120, 10, 100, 30);

        btn_C_Cancelar.setText("Cancelar");
        btn_C_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_C_CancelarActionPerformed(evt);
            }
        });
        jPanel5.add(btn_C_Cancelar);
        btn_C_Cancelar.setBounds(230, 10, 100, 30);

        btn_C_Editar.setText("Editar");
        btn_C_Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_C_EditarActionPerformed(evt);
            }
        });
        jPanel5.add(btn_C_Editar);
        btn_C_Editar.setBounds(340, 10, 100, 30);

        btn_C_Eliminar.setText("Eliminar");
        btn_C_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_C_EliminarActionPerformed(evt);
            }
        });
        jPanel5.add(btn_C_Eliminar);
        btn_C_Eliminar.setBounds(450, 10, 100, 30);

        pnlClientes.add(jPanel5);
        jPanel5.setBounds(6, 433, 1022, 300);

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/la-satisfaccion-del-cliente.png"))); // NOI18N
        pnlClientes.add(jLabel13);
        jLabel13.setBounds(570, 90, 280, 290);

        tpane.addTab("Clientes", pnlClientes);

        pnlReparaciones.setLayout(null);

        lbl_R_Id.setText("ID");
        pnlReparaciones.add(lbl_R_Id);
        lbl_R_Id.setBounds(20, 10, 100, 30);

        lbl_R_IdVehiculo.setText("ID Vehiculo");
        pnlReparaciones.add(lbl_R_IdVehiculo);
        lbl_R_IdVehiculo.setBounds(20, 80, 100, 30);

        lbl_R_IdPieza.setText("ID Pieza");
        pnlReparaciones.add(lbl_R_IdPieza);
        lbl_R_IdPieza.setBounds(20, 121, 100, 30);

        lbl_R_Id_Reparacion.setText("ID Reparación");
        pnlReparaciones.add(lbl_R_Id_Reparacion);
        lbl_R_Id_Reparacion.setBounds(20, 163, 100, 30);

        lbl_R_Falla.setText("Falla");
        pnlReparaciones.add(lbl_R_Falla);
        lbl_R_Falla.setBounds(20, 199, 100, 30);

        lbl_R_ControlPiezas.setText("Control Piezas");
        pnlReparaciones.add(lbl_R_ControlPiezas);
        lbl_R_ControlPiezas.setBounds(20, 241, 100, 30);

        lbl_R_FechaEntrada.setText("Fecha Entrada");
        pnlReparaciones.add(lbl_R_FechaEntrada);
        lbl_R_FechaEntrada.setBounds(20, 283, 100, 30);

        lbl_R_FechaSalida.setText("Fecha Salida");
        pnlReparaciones.add(lbl_R_FechaSalida);
        lbl_R_FechaSalida.setBounds(20, 331, 100, 30);

        cb_R_IdVehiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione" }));
        pnlReparaciones.add(cb_R_IdVehiculo);
        cb_R_IdVehiculo.setBounds(138, 79, 200, 30);

        cb_R_IdPieza.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione" }));
        pnlReparaciones.add(cb_R_IdPieza);
        cb_R_IdPieza.setBounds(138, 121, 200, 30);
        pnlReparaciones.add(txt_R_Id);
        txt_R_Id.setBounds(140, 10, 200, 30);
        txt_R_Id.getAccessibleContext().setAccessibleName("");

        txt_R_IdReparacion.setEditable(false);
        pnlReparaciones.add(txt_R_IdReparacion);
        txt_R_IdReparacion.setBounds(138, 163, 200, 30);

        txt_R_Falla.setEditable(false);
        pnlReparaciones.add(txt_R_Falla);
        txt_R_Falla.setBounds(138, 199, 200, 30);

        txt_R_ControlPiezas.setEditable(false);
        pnlReparaciones.add(txt_R_ControlPiezas);
        txt_R_ControlPiezas.setBounds(138, 241, 200, 30);

        btn_R_Buscar.setText("Buscar");
        btn_R_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_R_BuscarActionPerformed(evt);
            }
        });
        pnlReparaciones.add(btn_R_Buscar);
        btn_R_Buscar.setBounds(360, 10, 100, 30);

        btn_R_Salir.setText("Salir");
        btn_R_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_R_SalirActionPerformed(evt);
            }
        });
        pnlReparaciones.add(btn_R_Salir);
        btn_R_Salir.setBounds(920, 10, 100, 30);

        jdt_E_Fecha.setDateFormatString("dd-MM-yyyy");
        jdt_E_Fecha.setEnabled(false);
        pnlReparaciones.add(jdt_E_Fecha);
        jdt_E_Fecha.setBounds(138, 283, 200, 30);

        jdt_S_Fecha.setDateFormatString("dd-MM-yyyy");
        jdt_S_Fecha.setEnabled(false);
        pnlReparaciones.add(jdt_S_Fecha);
        jdt_S_Fecha.setBounds(138, 331, 200, 30);

        jPanel8.setBackground(new java.awt.Color(39, 53, 99));
        jPanel8.setLayout(null);

        btn_R_Nuevo.setText("Nuevo");
        btn_R_Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_R_NuevoActionPerformed(evt);
            }
        });
        jPanel8.add(btn_R_Nuevo);
        btn_R_Nuevo.setBounds(10, 10, 100, 30);

        btn_R_Guardar.setText("Guardar");
        btn_R_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_R_GuardarActionPerformed(evt);
            }
        });
        jPanel8.add(btn_R_Guardar);
        btn_R_Guardar.setBounds(120, 10, 100, 30);

        btn_R_Cancelar.setText("Cancelar");
        btn_R_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_R_CancelarActionPerformed(evt);
            }
        });
        jPanel8.add(btn_R_Cancelar);
        btn_R_Cancelar.setBounds(230, 10, 100, 30);

        btn_R_Editar.setText("Editar");
        btn_R_Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_R_EditarActionPerformed(evt);
            }
        });
        jPanel8.add(btn_R_Editar);
        btn_R_Editar.setBounds(340, 10, 100, 30);

        btn_R_Eliminar.setText("Eliminar");
        btn_R_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_R_EliminarActionPerformed(evt);
            }
        });
        jPanel8.add(btn_R_Eliminar);
        btn_R_Eliminar.setBounds(450, 10, 100, 30);

        pnlReparaciones.add(jPanel8);
        jPanel8.setBounds(8, 437, 1030, 310);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/reparar.png"))); // NOI18N
        pnlReparaciones.add(jLabel14);
        jLabel14.setBounds(510, 40, 340, 324);

        tpane.addTab("Reparaciones", pnlReparaciones);

        pnlVehiculos.setLayout(null);

        lbl_V_SeleccioneCliente.setText("Cliente");
        pnlVehiculos.add(lbl_V_SeleccioneCliente);
        lbl_V_SeleccioneCliente.setBounds(20, 49, 100, 30);

        lbl_V_IdVehiculo.setText("ID Vehiculo");
        pnlVehiculos.add(lbl_V_IdVehiculo);
        lbl_V_IdVehiculo.setBounds(20, 91, 100, 30);

        lbl_V_Matricula.setText("Matricula");
        pnlVehiculos.add(lbl_V_Matricula);
        lbl_V_Matricula.setBounds(20, 133, 100, 30);

        lbl_V_Marca.setText("Marca");
        pnlVehiculos.add(lbl_V_Marca);
        lbl_V_Marca.setBounds(20, 175, 100, 30);

        lbl_V_Modelo.setText("Modelo");
        pnlVehiculos.add(lbl_V_Modelo);
        lbl_V_Modelo.setBounds(20, 217, 100, 30);

        lbl_V_Id.setText("ID");
        pnlVehiculos.add(lbl_V_Id);
        lbl_V_Id.setBounds(20, 6, 100, 30);

        lbl_V_Fecha.setText("Fecha");
        pnlVehiculos.add(lbl_V_Fecha);
        lbl_V_Fecha.setBounds(20, 259, 100, 30);
        pnlVehiculos.add(txt_V_Buscar);
        txt_V_Buscar.setBounds(138, 6, 200, 30);

        txt_V_IdVehiculo.setEditable(false);
        txt_V_IdVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_V_IdVehiculoActionPerformed(evt);
            }
        });
        pnlVehiculos.add(txt_V_IdVehiculo);
        txt_V_IdVehiculo.setBounds(138, 91, 200, 30);

        txt_V_Matricula.setEditable(false);
        pnlVehiculos.add(txt_V_Matricula);
        txt_V_Matricula.setBounds(138, 133, 200, 30);

        txt_V_Marca.setEditable(false);
        pnlVehiculos.add(txt_V_Marca);
        txt_V_Marca.setBounds(138, 175, 200, 30);

        txt_V_Modelo.setEditable(false);
        txt_V_Modelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_V_ModeloActionPerformed(evt);
            }
        });
        pnlVehiculos.add(txt_V_Modelo);
        txt_V_Modelo.setBounds(138, 217, 200, 30);

        cb_V_SeleccioneCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione" }));
        pnlVehiculos.add(cb_V_SeleccioneCliente);
        cb_V_SeleccioneCliente.setBounds(138, 49, 200, 30);

        btn_V_Buscar.setText("Buscar");
        btn_V_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_V_BuscarActionPerformed(evt);
            }
        });
        pnlVehiculos.add(btn_V_Buscar);
        btn_V_Buscar.setBounds(365, 7, 100, 30);
        pnlVehiculos.add(jSeparator2);
        jSeparator2.setBounds(0, 569, 1028, 0);

        jdt_V_Fecha.setDateFormatString("dd-MM-yyyy");
        jdt_V_Fecha.setEnabled(false);
        pnlVehiculos.add(jdt_V_Fecha);
        jdt_V_Fecha.setBounds(138, 259, 200, 30);

        lbl_V_Color.setText("Color");
        pnlVehiculos.add(lbl_V_Color);
        lbl_V_Color.setBounds(20, 310, 29, 16);

        txt_V_Color.setEditable(false);
        txt_V_Color.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_V_ColorActionPerformed(evt);
            }
        });
        pnlVehiculos.add(txt_V_Color);
        txt_V_Color.setBounds(140, 302, 200, 30);

        lbl_V_Notas.setText("Notas");
        pnlVehiculos.add(lbl_V_Notas);
        lbl_V_Notas.setBounds(370, 50, 43, 16);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        pnlVehiculos.add(jScrollPane1);
        jScrollPane1.setBounds(370, 80, 320, 190);

        jPanel9.setBackground(new java.awt.Color(39, 53, 99));
        jPanel9.setLayout(null);

        btn_V_Nuevo.setText("Nuevo");
        btn_V_Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_V_NuevoActionPerformed(evt);
            }
        });
        jPanel9.add(btn_V_Nuevo);
        btn_V_Nuevo.setBounds(10, 10, 100, 30);

        btn_V_Guardar.setText("Guardar");
        btn_V_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_V_GuardarActionPerformed(evt);
            }
        });
        jPanel9.add(btn_V_Guardar);
        btn_V_Guardar.setBounds(120, 10, 100, 30);

        btn_V_Cancelar.setText("Cancelar");
        btn_V_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_V_CancelarActionPerformed(evt);
            }
        });
        jPanel9.add(btn_V_Cancelar);
        btn_V_Cancelar.setBounds(230, 10, 100, 30);

        btn_V_Editar.setText("Editar");
        btn_V_Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_V_EditarActionPerformed(evt);
            }
        });
        jPanel9.add(btn_V_Editar);
        btn_V_Editar.setBounds(340, 10, 100, 30);

        btn_V_Eliminar.setText("Eliminar");
        btn_V_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_V_EliminarActionPerformed(evt);
            }
        });
        jPanel9.add(btn_V_Eliminar);
        btn_V_Eliminar.setBounds(450, 10, 100, 30);

        pnlVehiculos.add(jPanel9);
        jPanel9.setBounds(10, 370, 1060, 420);

        btn_R_Salir1.setText("Salir");
        btn_R_Salir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_R_Salir1ActionPerformed(evt);
            }
        });
        pnlVehiculos.add(btn_R_Salir1);
        btn_R_Salir1.setBounds(910, 10, 100, 30);

        tpane.addTab("Vehículos", pnlVehiculos);

        pnlPiezas.setLayout(null);

        lbl_P_Id.setText("ID");
        pnlPiezas.add(lbl_P_Id);
        lbl_P_Id.setBounds(20, 6, 100, 30);

        lbl_P_IdPieza.setText("ID Pieza");
        pnlPiezas.add(lbl_P_IdPieza);
        lbl_P_IdPieza.setBounds(20, 60, 100, 30);

        lbl_P_Descripcion.setText("Descripción");
        pnlPiezas.add(lbl_P_Descripcion);
        lbl_P_Descripcion.setBounds(20, 100, 100, 30);

        lbl_P_Stock.setText("Stock");
        pnlPiezas.add(lbl_P_Stock);
        lbl_P_Stock.setBounds(20, 140, 100, 30);
        pnlPiezas.add(txt_P_Id);
        txt_P_Id.setBounds(138, 6, 200, 30);

        txt_P_IdPieza.setEditable(false);
        pnlPiezas.add(txt_P_IdPieza);
        txt_P_IdPieza.setBounds(140, 60, 200, 30);

        txt_P_Descripcion.setEditable(false);
        pnlPiezas.add(txt_P_Descripcion);
        txt_P_Descripcion.setBounds(140, 100, 200, 30);

        txt_P_Stock.setEditable(false);
        pnlPiezas.add(txt_P_Stock);
        txt_P_Stock.setBounds(140, 140, 200, 30);

        btn_P_Buscar.setText("Buscar");
        btn_P_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_P_BuscarActionPerformed(evt);
            }
        });
        pnlPiezas.add(btn_P_Buscar);
        btn_P_Buscar.setBounds(370, 10, 100, 30);

        jPanel6.setBackground(new java.awt.Color(39, 53, 99));

        btn_P_Nuevo.setText("Nuevo");
        btn_P_Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_P_NuevoActionPerformed(evt);
            }
        });

        btn_P_Guardar.setText("Guardar");
        btn_P_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_P_GuardarActionPerformed(evt);
            }
        });

        btn_P_Cancelar.setText("Cancelar");
        btn_P_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_P_CancelarActionPerformed(evt);
            }
        });

        btn_P_Editar.setText("Editar");
        btn_P_Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_P_EditarActionPerformed(evt);
            }
        });

        btn_P_Eliminar.setText("Eliminar");
        btn_P_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_P_EliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_P_Nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_P_Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_P_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_P_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_P_Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_P_Nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_P_Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_P_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_P_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_P_Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(334, Short.MAX_VALUE))
        );

        pnlPiezas.add(jPanel6);
        jPanel6.setBounds(10, 370, 1020, 370);

        jPanel7.setLayout(null);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/piezas-de-repuesto.png"))); // NOI18N
        jPanel7.add(jLabel12);
        jLabel12.setBounds(90, 10, 256, 256);

        pnlPiezas.add(jPanel7);
        jPanel7.setBounds(477, 18, 0, 0);

        btn_P_Salir.setText("Salir");
        btn_P_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_P_SalirActionPerformed(evt);
            }
        });
        pnlPiezas.add(btn_P_Salir);
        btn_P_Salir.setBounds(910, 10, 100, 30);

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/piezas-de-repuesto.png"))); // NOI18N
        pnlPiezas.add(jLabel15);
        jLabel15.setBounds(550, 40, 270, 270);

        tpane.addTab("Piezas", pnlPiezas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tpane, javax.swing.GroupLayout.PREFERRED_SIZE, 1028, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tpane, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_R_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_R_SalirActionPerformed
        tpane.setSelectedIndex(0);

        tpane.setEnabledAt(0, true);
        tpane.setEnabledAt(1, false);
        tpane.setEnabledAt(2, false);
        tpane.setEnabledAt(3, false);
        tpane.setEnabledAt(4, false);
        tpane.setEnabledAt(5, false);
    }//GEN-LAST:event_btn_R_SalirActionPerformed

    private void btn_R_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_R_NuevoActionPerformed
        Reparaciones_Habilitar();
        cb_R_vehiculos();
        cb_R_Pieza();

        int maxID = rf.getMax();

        btn_R_Guardar.setEnabled(true);
        btn_R_Nuevo.setEnabled(false);
        btn_R_Editar.setEnabled(false);
        btn_R_Eliminar.setEnabled(false);
        btn_R_Cancelar.setEnabled(true);

        cb_R_IdVehiculo.setSelectedItem("");
        cb_R_IdPieza.setSelectedItem("");
        txt_R_IdReparacion.setText(String.valueOf(maxID));
        txt_R_Falla.setText("");
        txt_R_ControlPiezas.setText("");

        jdt_E_Fecha.setDate(null);
        jdt_S_Fecha.setDate(null);

    }//GEN-LAST:event_btn_R_NuevoActionPerformed

    private void btn_R_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_R_GuardarActionPerformed

        if ("Seleccione".equals(cb_R_IdVehiculo.getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(null, "Eliga el Id del Vehiculo");
            return;
        }

        if ("Seleccione".equals(cb_R_IdPieza.getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(null, "Eliga el Id de la pieza a usar");
            return;
        }

        if ("".equals(txt_R_Falla.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese la falla");
            return;
        }

        if ("".equals(txt_R_ControlPiezas.getText()) || !ValidaNum(txt_R_ControlPiezas.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Ingrese la cantidad de piezas a usar");
            return;
        }

        if (!txt_R_Control()) {
            JOptionPane.showMessageDialog(null, "Ingrese una cantidad posible, o en su defecto, escoja una pieza disponible");
            return;
        }

        if (jdt_E_Fecha.getDate() == null || jdt_S_Fecha.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Escoja una fecha del calendario");
            return;
        }

        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
        String fecha_E = fecha.format(jdt_E_Fecha.getDate());
        String fecha_S = fecha.format(jdt_S_Fecha.getDate());

        Date actual = new Date();
        Date fentrada = jdt_E_Fecha.getDate();
        Date fsalida = jdt_S_Fecha.getDate();

        if ((fentrada.before(actual) || fentrada.equals(actual)) && (fsalida.before(actual) || fsalida.equals(actual))) {
            if (fsalida.after(fentrada)) {
                try {
                    rep = new reparaciones();
                    rep.setId_re(Integer.parseInt(txt_R_IdReparacion.getText()));

                    if (ban_reparaciones != true && rf.BuscarReparacion(rep) != null) {
                        JOptionPane.showMessageDialog(null, "Ese Id de reparacion ya existe");
                        return;
                    }

                    int aux = Integer.parseInt(txt_R_ControlPiezas.getText());

                    pi = new piezas();
                    pi.SetPiz(Integer.parseInt(cb_R_IdPieza.getSelectedItem().toString()));
                    pi = pf.BuscarPiezas(pi);
                    aux = (pi.getStock() - aux);
                    pi.SetStock(aux);

                    try {
                        pf.Editar(pi);
                    } catch (IOException ex) {

                    }

                    rep.setId_ve(Integer.parseInt(cb_R_IdVehiculo.getSelectedItem().toString()));
                    rep.setId_pi(Integer.parseInt(cb_R_IdPieza.getSelectedItem().toString()));
                    rep.setFalla(txt_R_Falla.getText());
                    rep.setId_contrl(Integer.parseInt(txt_R_ControlPiezas.getText()));
                    rep.setFecha_e(fecha_E);
                    rep.setFecha_s(fecha_S);

                    if (ban_reparaciones != true) {
                        rf.Guardar(rep);
                        JOptionPane.showMessageDialog(null, "Guardado con Éxito");
                    } else {
                        ban_reparaciones = false;
                        try {
                            rf.Editar(rep);

                            JOptionPane.showMessageDialog(null, "Editado con Éxito");
                            System.out.println("SI");
                        } catch (IOException ex) {

                        }
                    }

                    Reparaciones_Deshabilitar();

                    btn_R_Guardar.setEnabled(false);
                    btn_R_Nuevo.setEnabled(true);
                    btn_R_Editar.setEnabled(false);
                    btn_R_Eliminar.setEnabled(false);
                    btn_R_Cancelar.setEnabled(false);

                    cb_R_vehiculos();
                    cb_R_Pieza();

                } catch (FileNotFoundException ex) {

                }
            } else {
                JOptionPane.showMessageDialog(null, "La Fecha de Entrada tiene que ser anterior a la Fecha de Salida");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Elija una fecha posible");
        }

    }//GEN-LAST:event_btn_R_GuardarActionPerformed

    private void btn_R_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_R_EditarActionPerformed
        Reparaciones_Habilitar();
        btn_R_Guardar.setEnabled(true);
        btn_R_Nuevo.setEnabled(false);
        btn_R_Editar.setEnabled(false);
        btn_R_Eliminar.setEnabled(false);
        btn_R_Cancelar.setEnabled(true);

        ban_reparaciones = true;
    }//GEN-LAST:event_btn_R_EditarActionPerformed

    private void btn_R_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_R_CancelarActionPerformed
        Reparaciones_Deshabilitar();
        cb_R_vehiculos();
        cb_R_Pieza();

        btn_R_Guardar.setEnabled(false);
        btn_R_Nuevo.setEnabled(true);
        btn_R_Editar.setEnabled(false);
        btn_R_Eliminar.setEnabled(false);
        btn_R_Cancelar.setEnabled(false);

        jdt_E_Fecha.setEnabled(false);
        jdt_S_Fecha.setEnabled(false);

        ban_reparaciones = false;

    }//GEN-LAST:event_btn_R_CancelarActionPerformed

    private void btn_R_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_R_EliminarActionPerformed

        rep.setId_ve(Integer.parseInt(cb_R_IdVehiculo.getSelectedItem().toString()));
        rep.setId_pi(Integer.parseInt(cb_R_IdPieza.getSelectedItem().toString()));
        rep.setId_re(Integer.parseInt(txt_R_IdReparacion.getText()));
        rep.setFalla(txt_R_Falla.getText());
        rep.setId_contrl(Integer.parseInt(txt_R_ControlPiezas.getText()));

        SimpleDateFormat dformat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dformat.format(jdt_E_Fecha.getDate());
        rep.setFecha_e(date);

        date = dformat.format(jdt_S_Fecha.getDate());
        rep.setFecha_s(date);

        try {
            rf.Eliminar_Reparacion(rep);
        } catch (IOException ex) {

        }

        Reparaciones_Habilitar();
        cb_R_vehiculos();
        cb_R_Pieza();

        cb_R_IdVehiculo.setSelectedItem("");
        cb_R_IdPieza.setSelectedItem("");
        txt_R_IdReparacion.setText("");
        txt_R_Falla.setText("");
        txt_R_ControlPiezas.setText("");

        btn_R_Guardar.setEnabled(false);
        btn_R_Nuevo.setEnabled(true);
        btn_R_Editar.setEnabled(false);
        btn_R_Eliminar.setEnabled(false);
        btn_R_Cancelar.setEnabled(false);
    }//GEN-LAST:event_btn_R_EliminarActionPerformed

    private void btn_R_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_R_BuscarActionPerformed
        if (txt_R_Id.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el ID de la reparacion");
            txt_R_Id.setText("");
            cb_R_IdVehiculo.setEditable(true);
            cb_R_IdPieza.setEditable(true);
            cb_R_IdVehiculo.setSelectedItem("Seleccione");
            cb_R_IdPieza.setSelectedItem("Seleccione");
            cb_R_IdPieza.setEditable(false);
            cb_R_IdVehiculo.setEditable(false);
            txt_R_IdReparacion.setText("");
            txt_R_Falla.setText("");
            txt_R_ControlPiezas.setText("");
            jdt_E_Fecha.setDate(null);
            jdt_S_Fecha.setDate(null);

            return;
        }

        if (!ValidaNum(txt_R_Id.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID valido");
            txt_R_Id.setText("");
            cb_R_IdVehiculo.setEditable(true);
            cb_R_IdPieza.setEditable(true);
            cb_R_IdVehiculo.setSelectedItem("Seleccione");
            cb_R_IdPieza.setSelectedItem("Seleccione");
            cb_R_IdPieza.setEditable(false);
            cb_R_IdVehiculo.setEditable(false);
            txt_R_IdReparacion.setText("");
            txt_R_Falla.setText("");
            txt_R_ControlPiezas.setText("");
            jdt_E_Fecha.setDate(null);
            jdt_S_Fecha.setDate(null);
            return;
        }

        Reparaciones_Deshabilitar();
        try {
            rep = new reparaciones();
            rep.setId_re(Integer.parseInt(txt_R_Id.getText()));
            rep = rf.BuscarReparacion(rep);

            if (rep != null) {
                cb_R_IdVehiculo.setSelectedItem(String.valueOf(rep.getId_ve()));
                cb_R_IdPieza.setSelectedItem(String.valueOf(rep.getId_pi()));
                txt_R_IdReparacion.setText(String.valueOf(rep.getId_re()));
                txt_R_Falla.setText(rep.getFalla());
                txt_R_ControlPiezas.setText(String.valueOf(rep.getId_contrl()));

                SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
                Date formato = null;
                try {
                    formato = fecha.parse(rep.getFecha_e());
                } catch (ParseException ex) {
                }

                jdt_E_Fecha.setDate(formato);

                try {
                    formato = fecha.parse(rep.getFecha_s());
                } catch (ParseException ex) {
                }

                jdt_S_Fecha.setDate(formato);

                btn_R_Guardar.setEnabled(false);
                btn_R_Nuevo.setEnabled(false);
                btn_R_Editar.setEnabled(true);
                btn_R_Eliminar.setEnabled(true);
                btn_R_Cancelar.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "No existe ese ID");

                btn_R_Guardar.setEnabled(false);
                btn_R_Nuevo.setEnabled(true);
                btn_R_Editar.setEnabled(false);
                btn_R_Eliminar.setEnabled(false);
                btn_R_Cancelar.setEnabled(false);

                txt_R_Id.setText("");
                cb_R_IdVehiculo.setEditable(true);
                cb_R_IdPieza.setEditable(true);
                cb_R_IdVehiculo.setSelectedItem("Seleccione");
                cb_R_IdPieza.setSelectedItem("Seleccione");
                cb_R_IdPieza.setEditable(false);
                cb_R_IdVehiculo.setEditable(false);
                txt_R_IdReparacion.setText("");
                txt_R_Falla.setText("");
                txt_R_ControlPiezas.setText("");
                jdt_E_Fecha.setDate(null);
                jdt_S_Fecha.setDate(null);

            }

        } catch (FileNotFoundException ex) {

        }

        txt_R_Id.setText("");

    }//GEN-LAST:event_btn_R_BuscarActionPerformed

    private void btnAutentificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutentificarActionPerformed
        cto = new contacto();
        cto.setUsername(txtUsuario.getText());

        String pw;
        pw = String.valueOf(txtPassword.getPassword());

        try {
            cto = f.BuscarUsuario(cto);
        } catch (FileNotFoundException ex) {

        }
        if (cto != null) {
            if (cto.getPassword().equals(pw)) {

                IdUs = String.valueOf(cto.getId());
                txt_C_IdUsuario.setText(IdUs);

                int maxID = v.getMax();
                //txt_V_IdVehiculo.setText(String.valueOf(maxID));
                cb_vehiculos();
                cb_R_vehiculos();
                cb_R_Pieza();

                txtUsuario.setText("");
                txtPassword.setText("");

                if ("Admin".equals(cto.getPerfil())) {
                    tpane.setEnabledAt(0, false);
                    tpane.setEnabledAt(1, true);
                    tpane.setEnabledAt(2, true);
                    tpane.setEnabledAt(3, true);
                    tpane.setEnabledAt(4, true);
                    tpane.setEnabledAt(5, true);

                    tpane.setSelectedIndex(1);

                    btnSalvar.setEnabled(false);
                    btnNuevo.setEnabled(true);
                    btnEditar.setEnabled(false);
                    btnRemover.setEnabled(false);
                    btnCancelar.setEnabled(false);

                    btn_C_Nuevo.setVisible(true);
                    btn_C_Guardar.setVisible(true);
                    btn_C_Cancelar.setVisible(true);
                    btn_C_Editar.setVisible(true);
                    btn_C_Eliminar.setVisible(true);

                    btn_V_Nuevo.setVisible(true);
                    btn_V_Guardar.setVisible(true);
                    btn_V_Cancelar.setVisible(true);
                    btn_V_Editar.setVisible(true);
                    btn_V_Eliminar.setVisible(true);

                    btn_R_Nuevo.setVisible(true);
                    btn_R_Guardar.setVisible(true);
                    btn_R_Cancelar.setVisible(true);
                    btn_R_Editar.setVisible(true);
                    btn_R_Eliminar.setVisible(true);
                }
                if ("Gerente".equals(cto.getPerfil())) {

                    tpane.setEnabledAt(0, false);
                    tpane.setEnabledAt(1, false);
                    tpane.setEnabledAt(2, true);
                    tpane.setEnabledAt(3, false);
                    tpane.setEnabledAt(4, true);
                    tpane.setEnabledAt(5, false);

                    btn_C_Nuevo.setVisible(true);
                    btn_C_Guardar.setVisible(true);
                    btn_C_Cancelar.setVisible(true);
                    btn_C_Editar.setVisible(false);
                    btn_C_Eliminar.setVisible(false);

                    btn_R_Nuevo.setVisible(false);
                    btn_R_Guardar.setVisible(true);
                    btn_R_Cancelar.setVisible(true);
                    btn_R_Editar.setVisible(true);
                    btn_R_Eliminar.setVisible(false);

                    tpane.setSelectedIndex(2);
                }
                if ("Secretaria".equals(cto.getPerfil())) {

                    tpane.setEnabledAt(0, false);
                    tpane.setEnabledAt(1, false);
                    tpane.setEnabledAt(2, true);
                    tpane.setEnabledAt(3, true);
                    tpane.setEnabledAt(4, false);
                    tpane.setEnabledAt(5, false);

                    btn_C_Nuevo.setVisible(true);
                    btn_C_Guardar.setVisible(true);
                    btn_C_Cancelar.setVisible(true);
                    btn_C_Editar.setVisible(false);
                    btn_C_Eliminar.setVisible(false);

                    btn_V_Nuevo.setVisible(true);
                    btn_V_Guardar.setVisible(true);
                    btn_V_Cancelar.setVisible(true);
                    btn_V_Editar.setVisible(false);
                    btn_V_Eliminar.setVisible(false);

                    tpane.setSelectedIndex(2);
                }
                if ("Mecánico".equals(cto.getPerfil())) {

                    tpane.setEnabledAt(0, false);
                    tpane.setEnabledAt(1, false);
                    tpane.setEnabledAt(2, false);
                    tpane.setEnabledAt(3, false);
                    tpane.setEnabledAt(4, true);
                    tpane.setEnabledAt(5, false);

                    btn_R_Nuevo.setVisible(true);
                    btn_R_Guardar.setVisible(true);
                    btn_R_Cancelar.setVisible(true);
                    btn_R_Editar.setVisible(false);
                    btn_R_Eliminar.setVisible(false);

                    tpane.setSelectedIndex(4);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                txtPassword.setText("");
            }

        } else {
            JOptionPane.showMessageDialog(null, "No existe ese Usuario");
            txtPassword.setText("");
        }
    }//GEN-LAST:event_btnAutentificarActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        try {
        if (jCheckBox1.isSelected()) {
            // Cambia a FlatDarculaLaf cuando checkbox1 está seleccionado
            FlatDarculaLaf.setup();
            UIManager.setLookAndFeel(new FlatDarculaLaf());

            // Actualiza la interfaz para que los cambios se apliquen inmediatamente
            SwingUtilities.updateComponentTreeUI(this);
            System.out.println("Tema FlatDarcula activado");
        } else {
            // Si se deselecciona el checkbox1, cambia a un tema claro (FlatIntelliJLaf)
            FlatIntelliJLaf.setup();  // O puedes usar FlatLightLaf.setup();
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            
            // Actualiza la interfaz para que los cambios se apliquen inmediatamente
            SwingUtilities.updateComponentTreeUI(this);
            System.out.println("Tema FlatIntelliJ activado");
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        tpane.setSelectedIndex(0);

        tpane.setEnabledAt(0, true);
        tpane.setEnabledAt(1, false);
        tpane.setEnabledAt(2, false);
        tpane.setEnabledAt(3, false);
        tpane.setEnabledAt(4, false);
        tpane.setEnabledAt(5, false);

        txtBuscar.setText("");

        txtID.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtPaterno.setText("");
        txtMaterno.setText("");
        txtUsername.setText("");
        cbPerfil.setSelectedItem("");
        txtDireccion.setText("");

        Deshabilitar();

        Vehiculos_Deshabilitar();

        txt_C_IdUsuario.setEditable(false);
        txt_C_IdCliente.setEditable(false);
        txt_C_Nombre.setEditable(false);
        txt_C_ApellidoPaterno.setEditable(false);
        txt_C_ApellidoMaterno.setEditable(false);

        cb_V_SeleccioneCliente.removeAllItems();

        int maxId = fc.getMaxId();

        txt_C_Buscar.setText("");
        txt_C_IdUsuario.setText(IdUs);
        txt_C_IdCliente.setText(String.valueOf(maxId));
        txt_C_Nombre.setText("");
        txt_C_ApellidoPaterno.setText("");
        txt_C_ApellidoMaterno.setText("");
        btn_V_Guardar.setEnabled(false);
        btn_V_Nuevo.setEnabled(true);
        btn_V_Editar.setEnabled(false);
        btn_V_Eliminar.setEnabled(false);
        btn_V_Cancelar.setEnabled(false);

        btn_C_Guardar.setEnabled(false);
        btn_C_Nuevo.setEnabled(true);
        btn_C_Editar.setEnabled(false);
        btn_C_Eliminar.setEnabled(false);
        btn_C_Cancelar.setEnabled(false);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Habilitar();
        btnSalvar.setEnabled(false);
        btnNuevo.setEnabled(true);
        btnEditar.setEnabled(false);
        btnRemover.setEnabled(false);
        btnCancelar.setEnabled(false);

        txtBuscar.setText("");

        txtID.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtPaterno.setText("");
        txtMaterno.setText("");
        txtUsername.setText("");
        cbPerfil.setSelectedItem("");
        txtDireccion.setText("");

        ban = false;
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        try {
            cto = new contacto();
            cto.setId(Integer.parseInt(txtID.getText()));
            cto = f.BuscarContacto(cto);

            if (cto != null) {
                try {
                    if (cto != null) {
                        f.Eliminar(cto);
                        btnSalvar.setEnabled(false);
                        btnNuevo.setEnabled(true);
                        btnEditar.setEnabled(false);
                        btnRemover.setEnabled(false);
                        btnCancelar.setEnabled(true);

                        txtID.setText("");
                        txtNombre.setText("");
                        txtTelefono.setText("");
                        txtPaterno.setText("");
                        txtMaterno.setText("");
                        txtUsername.setText("");
                        cbPerfil.setSelectedItem("");
                        txtDireccion.setText("");

                    } else {
                        JOptionPane.showMessageDialog(null, "No existe el registro");
                    }
                } catch (IOException ex) {

                }
            } else {
                JOptionPane.showMessageDialog(null, "No existe el registro");
            }
        } catch (FileNotFoundException ex) {

        }
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if (txtBuscar.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el ID del usuario");
            txtID.setText("");
            txtNombre.setText("");
            txtTelefono.setText("");
            txtPaterno.setText("");
            txtMaterno.setText("");
            txtUsername.setText("");
            txtPsw.setText("");
            cbPerfil.setEditable(true);
            cbPerfil.setSelectedItem("");
            cbPerfil.setEditable(false);
            txtDireccion.setText("");

            return;
        }

        if (!ValidaNum(txtBuscar.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID valido");
            txtBuscar.setText("");
            txtID.setText("");
            txtNombre.setText("");
            txtTelefono.setText("");
            txtPaterno.setText("");
            txtMaterno.setText("");
            txtUsername.setText("");
            txtPsw.setText("");
            cbPerfil.setEditable(true);
            cbPerfil.setSelectedItem("");
            cbPerfil.setEditable(false);
            txtDireccion.setText("");

            return;
        }

        try {
            cto = new contacto();
            cto.setId(Integer.parseInt(txtBuscar.getText()));

            cto = f.BuscarContacto(cto);

            if (cto != null) {
                if (cto.getId() == 0) {
                    btnSalvar.setEnabled(false);
                    btnNuevo.setEnabled(false);
                    btnEditar.setEnabled(false);
                    btnRemover.setEnabled(false);
                    btnCancelar.setEnabled(true);
                } else {
                    btnSalvar.setEnabled(false);
                    btnNuevo.setEnabled(false);
                    btnEditar.setEnabled(true);
                    btnRemover.setEnabled(true);
                    btnCancelar.setEnabled(true);
                }
                Deshabilitar();
                txtPsw.setVisible(false);
                lblPassword.setVisible(false);

                txtBuscar.setText("");
                txtID.setText(String.valueOf(cto.getId()));
                txtNombre.setText(cto.getNombre());
                txtTelefono.setText(cto.getTelefono());
                txtPaterno.setText(cto.getPaterno());
                txtMaterno.setText(cto.getMaterno());
                txtUsername.setText(cto.getUsername());
                txtPsw.setText(cto.getPassword());
                cbPerfil.setSelectedItem(cto.getPerfil());
                txtDireccion.setText(cto.getDireccion());

            } else {
                JOptionPane.showMessageDialog(null, "No existe ese ID");
                txtBuscar.setText("");
                txtID.setText("");
                txtNombre.setText("");
                txtTelefono.setText("");
                txtPaterno.setText("");
                txtMaterno.setText("");
                txtUsername.setText("");
                txtPsw.setText("");
                cbPerfil.setSelectedItem("");
                txtDireccion.setText("");

            }

        } catch (FileNotFoundException ex) {

        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        try {
            cto = new contacto();
            cto.setUsername(txtUsername.getText());
            String contra;

            if (ban != true && f.BuscarUsuario(cto) != null) {
                JOptionPane.showMessageDialog(null, "Ese Nombre de Usuario ya existe");
                return;
            }

            //Validar que no existan campos vacios
            if (txtNombre.getText().equals("") || txtTelefono.getText().equals("") || txtPaterno.getText().equals("") || txtMaterno.getText().equals("") || txtUsername.getText().equals("") || txtPsw.getText().equals("") || cbPerfil.getSelectedItem().equals("") || txtDireccion.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos");
                return;
            }

            cto.setId(Integer.parseInt(txtID.getText()));
            cto.setNombre(txtNombre.getText());
            cto.setTelefono(txtTelefono.getText());
            cto.setPaterno(txtPaterno.getText());
            cto.setMaterno(txtMaterno.getText());
            cto.setPassword(txtPsw.getText());
            cto.setPerfil(cbPerfil.getSelectedItem().toString());
            cto.setDireccion(txtDireccion.getText());

            if (ban != true) {
                cto.setPassword(txtPsw.getText());
                f.Guardar(cto);
                txtID.setText("");
                txtNombre.setText("");
                txtTelefono.setText("");
                txtPaterno.setText("");
                txtMaterno.setText("");
                txtUsername.setText("");
                txtPsw.setText("");
                cbPerfil.setSelectedItem("");
                txtDireccion.setText("");

                JOptionPane.showMessageDialog(null, "Guardado con Éxito");
            } else {
                ban = false;
                try {
                    contra = f.BuscarUsuario(cto).getPassword();
                    System.out.println(contra);
                    cto.setPassword(contra);
                    f.Editar(cto);
                    JOptionPane.showMessageDialog(null, "Editado con Éxito");
                    System.out.println("SI");
                } catch (IOException ex) {

                }
            }
            btnSalvar.setEnabled(false);
            btnNuevo.setEnabled(true);
            btnEditar.setEnabled(false);
            btnRemover.setEnabled(false);
            btnCancelar.setEnabled(false);

            //band=true;
        } catch (FileNotFoundException ex) {

        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        Habilitar();
        btnSalvar.setEnabled(true);
        btnNuevo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnRemover.setEnabled(false);
        btnCancelar.setEnabled(true);
        ban = true;
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        try {
            txtID.setText(String.valueOf(f.Id()));
        } catch (FileNotFoundException ex) {
        }

        Habilitar();
        txtPsw.setVisible(true);
        lblPassword.setVisible(true);

        btnSalvar.setEnabled(true);
        btnNuevo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnRemover.setEnabled(false);
        btnCancelar.setEnabled(true);

        txtNombre.setText("");
        txtTelefono.setText("");
        txtPaterno.setText("");
        txtMaterno.setText("");
        txtUsername.setText("");
        cbPerfil.setSelectedItem("");
        txtDireccion.setText("");
        txtPsw.setText("");
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btn_C_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_C_SalirActionPerformed
        tpane.setSelectedIndex(0);

        Vehiculos_Deshabilitar();

        txt_C_IdUsuario.setEditable(false);
        txt_C_IdCliente.setEditable(false);
        txt_C_Nombre.setEditable(false);
        txt_C_ApellidoPaterno.setEditable(false);
        txt_C_ApellidoMaterno.setEditable(false);

        cb_V_SeleccioneCliente.removeAllItems();

        int maxId = fc.getMaxId();

        txt_C_Buscar.setText("");
        txt_C_IdUsuario.setText(IdUs);
        txt_C_IdCliente.setText(String.valueOf(maxId));
        txt_C_Nombre.setText("");
        txt_C_ApellidoPaterno.setText("");
        txt_C_ApellidoMaterno.setText("");

        tpane.setEnabledAt(0, true);
        tpane.setEnabledAt(1, false);
        tpane.setEnabledAt(2, false);
        tpane.setEnabledAt(3, false);
        tpane.setEnabledAt(4, false);
        tpane.setEnabledAt(5, false);

        btn_V_Guardar.setEnabled(false);
        btn_V_Nuevo.setEnabled(true);
        btn_V_Editar.setEnabled(false);
        btn_V_Eliminar.setEnabled(false);
        btn_V_Cancelar.setEnabled(false);

        btn_C_Guardar.setEnabled(false);
        btn_C_Nuevo.setEnabled(true);
        btn_C_Editar.setEnabled(false);
        btn_C_Eliminar.setEnabled(false);
        btn_C_Cancelar.setEnabled(false);
    }//GEN-LAST:event_btn_C_SalirActionPerformed

    private void btn_C_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_C_BuscarActionPerformed
        //Verificar que el campo txt_C_Id no este vacio
        if (txt_C_Buscar.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el ID del cliente");
            txt_C_Buscar.setText("");
            txt_C_IdUsuario.setText(IdUs);
            txt_C_IdCliente.setText("");
            txt_C_Nombre.setText("");
            txt_C_ApellidoPaterno.setText("");
            txt_C_ApellidoMaterno.setText("");

            return;
        }

        if (!ValidaNum(txt_C_Buscar.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID valido");
            txt_C_Buscar.setText("");
            txt_C_IdUsuario.setText(IdUs);
            txt_C_IdCliente.setText("");
            txt_C_Nombre.setText("");
            txt_C_ApellidoPaterno.setText("");
            txt_C_ApellidoMaterno.setText("");
            return;
        }

        // Obtener el id
        int id = Integer.parseInt(txt_C_Buscar.getText());

        // validar que el id si exista
        c = new cliente();
        c = fc.buscar(id);
        if (c != null) {
            txt_C_Buscar.setText("");
            txt_C_IdUsuario.setText(String.valueOf(c.getIdUsuario()));
            // Mostrar los datos del cliente

            txt_C_IdCliente.setText(String.valueOf(c.getId()));
            txt_C_Nombre.setText(c.getNombre());
            txt_C_ApellidoPaterno.setText(c.getApellidoPaterno());
            txt_C_ApellidoMaterno.setText(c.getApellidoMaterno());
        } else {
            JOptionPane.showMessageDialog(null, "No existe el cliente");
            txt_C_Buscar.setText("");
            txt_C_IdUsuario.setText(IdUs);
            txt_C_IdCliente.setText("");
            txt_C_Nombre.setText("");
            txt_C_ApellidoPaterno.setText("");
            txt_C_ApellidoMaterno.setText("");

        }

        btn_C_Guardar.setEnabled(false);
        btn_C_Nuevo.setEnabled(false);
        btn_C_Editar.setEnabled(true);
        btn_C_Eliminar.setEnabled(true);
        btn_C_Cancelar.setEnabled(true);

        txt_C_IdUsuario.setEditable(false);
        txt_C_IdCliente.setEditable(false);
        txt_C_Nombre.setEditable(true);
        txt_C_ApellidoPaterno.setEditable(true);
        txt_C_ApellidoMaterno.setEditable(true);
    }//GEN-LAST:event_btn_C_BuscarActionPerformed

    private void btn_C_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_C_EliminarActionPerformed
        //Verificar que el campo txt_C_Id no este vacio
        /*if(txt_C_Buscar.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el ID del cliente");
            return;
        }*/

        // Obtener el id
        int id = Integer.parseInt(txt_C_IdCliente.getText());

        // validar que el id si exista
        c = fc.buscar(id);

        // Eliminar el cliente
        fc.eliminar(c);
        JOptionPane.showMessageDialog(null, "Eliminado correctamente");

        vc = new vehiculo_cliente();
        vc.setIdUsuario(txt_C_IdUsuario.getText());
        vc.setIdCliente(txt_C_IdCliente.getText());

        vcf.eliminar(vc);
        cb_vehiculos();

        txt_C_IdUsuario.setText(IdUs);
        txt_C_IdCliente.setText("");
        txt_C_Nombre.setText("");
        txt_C_ApellidoPaterno.setText("");
        txt_C_ApellidoMaterno.setText("");

        btn_C_Guardar.setEnabled(false);
        btn_C_Nuevo.setEnabled(true);
        btn_C_Editar.setEnabled(false);
        btn_C_Eliminar.setEnabled(false);
        btn_C_Cancelar.setEnabled(false);
    }//GEN-LAST:event_btn_C_EliminarActionPerformed

    private void btn_C_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_C_EditarActionPerformed
        //Boton para editar
        //Verificar que el campo txt_C_IdCliente no este vacio

        if (txt_C_IdCliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el ID del cliente");
            return;
        }
        //Verificar que el campo txt_C_Nombre no este vacio
        if (txt_C_Nombre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del cliente");
            return;
        }
        //Verificar que el campo txt_C_ApellidoPaterno no este vacio
        if (txt_C_ApellidoPaterno.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el apellido paterno del cliente");
            return;
        }
        //Verificar que el campo txt_C_ApellidoMaterno no este vacio
        if (txt_C_ApellidoMaterno.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el apellido materno del cliente");
            return;
        }
        //Se crea un objeto de tipo cliente
        c = new cliente();
        //Se le asignan los valores a los atributos del objeto
        c.setId(Integer.parseInt(txt_C_IdCliente.getText()));
        c.setNombre(txt_C_Nombre.getText());
        c.setApellidoPaterno(txt_C_ApellidoPaterno.getText());
        c.setApellidoMaterno(txt_C_ApellidoMaterno.getText());

        vc = new vehiculo_cliente();
        vc.setIdUsuario(txt_C_IdUsuario.getText());
        vc.setIdCliente(txt_C_IdCliente.getText());
        //Se crea un objeto de tipo archivoCliente
        //Cliente_File ac = new Cliente_File();
        //Se llama al metodo guardarCliente y se le pasa como parametro el objeto cliente
        fc.editar(c);
        vcf.editar(vc);
        //Se muestra un mensaje de que se guardo correctamente
        JOptionPane.showMessageDialog(null, "Editado correctamente");
        //Se limpian los campos de texto
        txt_C_IdUsuario.setText(IdUs);
        txt_C_IdCliente.setText("");
        txt_C_Nombre.setText("");
        txt_C_ApellidoPaterno.setText("");
        txt_C_ApellidoMaterno.setText("");

        btn_C_Guardar.setEnabled(false);
        btn_C_Nuevo.setEnabled(true);
        btn_C_Editar.setEnabled(false);
        btn_C_Eliminar.setEnabled(false);
        btn_C_Cancelar.setEnabled(false);
    }//GEN-LAST:event_btn_C_EditarActionPerformed

    private void btn_C_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_C_CancelarActionPerformed

        txt_C_Buscar.setText("");
        txt_C_IdUsuario.setText(IdUs);
        txt_C_IdCliente.setText("");
        txt_C_Nombre.setText("");
        txt_C_ApellidoPaterno.setText("");
        txt_C_ApellidoMaterno.setText("");

        btn_C_Guardar.setEnabled(false);
        btn_C_Nuevo.setEnabled(true);
        btn_C_Editar.setEnabled(false);
        btn_C_Eliminar.setEnabled(false);
        btn_C_Cancelar.setEnabled(false);
    }//GEN-LAST:event_btn_C_CancelarActionPerformed

    private void btn_C_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_C_GuardarActionPerformed
        //Verificar que el campo txt_C_IdCliente no este vacio

        if (txt_C_IdCliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el ID del cliente");
            return;
        }
        //Verificar que el campo txt_C_Nombre no este vacio
        if (txt_C_Nombre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del cliente");
            return;
        }
        //Verificar que el campo txt_C_ApellidoPaterno no este vacio
        if (txt_C_ApellidoPaterno.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el apellido paterno del cliente");
            return;
        }
        //Verificar que el campo txt_C_ApellidoMaterno no este vacio
        if (txt_C_ApellidoMaterno.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el apellido materno del cliente");
            return;
        }
        //Se crea un objeto de tipo cliente
        c = new cliente();
        //Se le asignan los valores a los atributos del objeto
        c.setIdUsuario(Integer.parseInt(txt_C_IdUsuario.getText()));
        c.setId(Integer.parseInt(txt_C_IdCliente.getText()));
        c.setNombre(txt_C_Nombre.getText());
        c.setApellidoPaterno(txt_C_ApellidoPaterno.getText());
        c.setApellidoMaterno(txt_C_ApellidoMaterno.getText());

        vc = new vehiculo_cliente();
        vc.setIdUsuario(txt_C_IdUsuario.getText());
        vc.setIdCliente(txt_C_IdCliente.getText());
        //Se crea un objeto de tipo archivoCliente
        //Cliente_File ac = new Cliente_File();
        //Se llama al metodo guardarCliente y se le pasa como parametro el objeto cliente
        fc.guardar(c);
        vcf.guardar(vc);
        cb_vehiculos();
        //Se muestra un mensaje de que se guardo correctamente
        JOptionPane.showMessageDialog(null, "Guardado correctamente");
        //Se limpian los campos de texto
        txt_C_IdCliente.setText("");
        txt_C_Nombre.setText("");
        txt_C_ApellidoPaterno.setText("");
        txt_C_ApellidoMaterno.setText("");

        btn_C_Guardar.setEnabled(false);
        btn_C_Nuevo.setEnabled(true);
        btn_C_Editar.setEnabled(false);
        btn_C_Eliminar.setEnabled(false);
        btn_C_Cancelar.setEnabled(false);
    }//GEN-LAST:event_btn_C_GuardarActionPerformed

    private void btn_C_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_C_NuevoActionPerformed
        //Cliente_File clienteFile = new Cliente_File();
        int maxId = fc.getMaxId();

        txt_C_Buscar.setText("");
        txt_C_IdUsuario.setText(IdUs);
        txt_C_IdCliente.setText(String.valueOf(maxId));
        txt_C_Nombre.setText("");
        txt_C_ApellidoPaterno.setText("");
        txt_C_ApellidoMaterno.setText("");

        txt_C_Nombre.setEditable(true);
        txt_C_ApellidoPaterno.setEditable(true);
        txt_C_ApellidoMaterno.setEditable(true);

        btn_C_Guardar.setEnabled(true);
        btn_C_Nuevo.setEnabled(false);
        btn_C_Editar.setEnabled(false);
        btn_C_Eliminar.setEnabled(false);
        btn_C_Cancelar.setEnabled(true);
    }//GEN-LAST:event_btn_C_NuevoActionPerformed

    private void btn_V_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_V_BuscarActionPerformed
        if (txt_V_Buscar.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el ID del vehiculo");
            txt_V_Buscar.setText("");
            cb_V_SeleccioneCliente.setSelectedItem("Seleccione");
            txt_V_IdVehiculo.setText("");
            txt_V_Matricula.setText("");
            txt_V_Marca.setText("");
            txt_V_Modelo.setText("");
            jdt_V_Fecha.setDate(null);
            txt_V_Color.setText("");
            jTextArea1.setText("");
            return;
        }

        if (!ValidaNum(txt_V_Buscar.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID valido");
            txt_V_Buscar.setText("");
            cb_V_SeleccioneCliente.setSelectedItem("Seleccione");
            txt_V_IdVehiculo.setText("");
            txt_V_Matricula.setText("");
            txt_V_Marca.setText("");
            txt_V_Modelo.setText("");
            jdt_V_Fecha.setDate(null);
            txt_V_Color.setText("");
            jTextArea1.setText("");
            return;
        }

        Vehiculos_Deshabilitar();
        try {
            vcs = new Vehiculos();
            vcs.setId_vehiculo(Integer.parseInt(txt_V_Buscar.getText()));
            vcs = v.BuscarIdVehiculo(vcs);

            if (vcs != null) {

                cb_V_SeleccioneCliente.setSelectedItem(vcs.getCliente());
                txt_V_IdVehiculo.setText(String.valueOf(vcs.getId_vehiculo()));
                txt_V_Matricula.setText(vcs.getMatricula());
                txt_V_Marca.setText(vcs.getMarca());
                txt_V_Modelo.setText(vcs.getModelo());
                txt_V_Color.setText(vcs.getColor());
                jTextArea1.setText(vcs.getNota());

                SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
                Date formato = null;
                try {
                    formato = fecha.parse(vcs.getFecha());
                } catch (ParseException ex) {
                }
                jdt_V_Fecha.setDate(formato);

                btn_V_Guardar.setEnabled(false);
                btn_V_Nuevo.setEnabled(false);
                btn_V_Editar.setEnabled(true);
                btn_V_Eliminar.setEnabled(true);
                btn_V_Cancelar.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "No existe ese ID");

                btn_V_Guardar.setEnabled(false);
                btn_V_Nuevo.setEnabled(true);
                btn_V_Editar.setEnabled(false);
                btn_V_Eliminar.setEnabled(false);
                btn_V_Cancelar.setEnabled(true);
            }

        } catch (FileNotFoundException ex) {

        }

        txt_V_Buscar.setText("");
    }//GEN-LAST:event_btn_V_BuscarActionPerformed

    private void btn_V_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_V_EliminarActionPerformed
        vcs.setCliente(cb_V_SeleccioneCliente.getSelectedItem().toString());
        vcs.setId_vehiculo(Integer.parseInt(txt_V_IdVehiculo.getText()));
        vcs.setMatricula(txt_V_Matricula.getText());
        vcs.setMarca(txt_V_Marca.getText());
        vcs.setModelo(txt_V_Modelo.getText());
        vcs.setColor(txt_V_Color.getText());
        vcs.setNota(jTextArea1.getText());

        SimpleDateFormat dformat = new SimpleDateFormat("dd-MM-YYYY");
        String date = dformat.format(jdt_V_Fecha.getDate());
        vcs.setFecha(date);

        try {
            v.Eliminar_Vehiculos(vcs);
            JOptionPane.showMessageDialog(null, "Eliminado correctamente");
        } catch (IOException ex) {

        }

        Vehiculos_Deshabilitar();
        cb_vehiculos();

        btn_V_Guardar.setEnabled(false);
        btn_V_Nuevo.setEnabled(true);
        btn_V_Editar.setEnabled(false);
        btn_V_Eliminar.setEnabled(false);
        btn_V_Cancelar.setEnabled(false);
    }//GEN-LAST:event_btn_V_EliminarActionPerformed

    private void btn_V_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_V_EditarActionPerformed
        Vehiculos_Habilitar();

        btn_V_Guardar.setEnabled(true);
        btn_V_Nuevo.setEnabled(false);
        btn_V_Editar.setEnabled(false);
        btn_V_Eliminar.setEnabled(false);
        btn_V_Cancelar.setEnabled(true);

        ban_vehiculos = true;
    }//GEN-LAST:event_btn_V_EditarActionPerformed

    private void btn_V_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_V_CancelarActionPerformed
        Vehiculos_Deshabilitar();
        cb_vehiculos();
        btn_V_Guardar.setEnabled(false);
        btn_V_Nuevo.setEnabled(true);
        btn_V_Editar.setEnabled(false);
        btn_V_Eliminar.setEnabled(false);
        btn_V_Cancelar.setEnabled(false);
    }//GEN-LAST:event_btn_V_CancelarActionPerformed

    private void btn_V_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_V_GuardarActionPerformed

        // Validaciones de campos
    if ("Seleccione".equals(cb_V_SeleccioneCliente.getSelectedItem().toString())) {
        JOptionPane.showMessageDialog(null, "Eliga el cliente");
        return;
    }

    if ("".equals(txt_V_Matricula.getText()) || "".equals(txt_V_Marca.getText()) || "".equals(txt_V_Modelo.getText())) {
        JOptionPane.showMessageDialog(null, "Rellene los textos faltantes");
        return;
    }

    if (jdt_V_Fecha.getDate() == null) {
        JOptionPane.showMessageDialog(null, "Escoja una fecha del calendario");
        return;
    }

    // Verificar si la matrícula ya existe
    try {
        vcs = new Vehiculos();
        vcs.setMatricula(txt_V_Matricula.getText());
        vcs = v.BuscarMatricula(vcs);


    } catch (FileNotFoundException ex) {
        ex.printStackTrace(); // Log del error
        JOptionPane.showMessageDialog(null, "Error al buscar la matrícula. Verifique el archivo.");
        return;
    }

    // Configurar el objeto Vehiculos
    vcs = new Vehiculos();
    vcs.setMatricula(txt_V_Matricula.getText());
    vcs.setCliente(cb_V_SeleccioneCliente.getSelectedItem().toString());
    vcs.setId_vehiculo(Integer.parseInt(txt_V_IdVehiculo.getText()));
    vcs.setMatricula(txt_V_Matricula.getText());
    vcs.setMarca(txt_V_Marca.getText());
    vcs.setModelo(txt_V_Modelo.getText());
    vcs.setColor(txt_V_Color.getText());
    vcs.setNota(jTextArea1.getText());

    // Formatear la fecha
    SimpleDateFormat dformat = new SimpleDateFormat("dd-MM-yyyy");
    String date = dformat.format(jdt_V_Fecha.getDate());
    vcs.setFecha(date);

    // Validar fecha
    Date fa = new Date();
    Date s = jdt_V_Fecha.getDate();

    if (s.before(fa) || s.equals(fa)) {
        if (!ban_vehiculos) {
            try {
                v.Guardar(vcs);
                JOptionPane.showMessageDialog(null, "Guardado correctamente");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace(); // Log del error
                JOptionPane.showMessageDialog(null, "Error al guardar el vehículo. Verifique el archivo.");
            }
        } else {
            try {
                v.Editar_Vehiculo(vcs);
                ban_vehiculos = false;
                JOptionPane.showMessageDialog(null, "Editado correctamente");
            } catch (IOException ex) {
                ex.printStackTrace(); // Log del error
                JOptionPane.showMessageDialog(null, "Error al editar el vehículo. Verifique el archivo.");
            }
        }

        // Actualizar la interfaz de usuario
        Vehiculos_Deshabilitar();
        cb_vehiculos();
        btn_V_Guardar.setEnabled(false);
        btn_V_Nuevo.setEnabled(true);
        btn_V_Editar.setEnabled(false);
        btn_V_Eliminar.setEnabled(false);
        btn_V_Cancelar.setEnabled(false);

        txt_V_IdVehiculo.setText("");
    } else {
        JOptionPane.showMessageDialog(null, "Elija una fecha válida");
    }
    }//GEN-LAST:event_btn_V_GuardarActionPerformed

    
    private void btn_V_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_V_NuevoActionPerformed
        Vehiculos_Habilitar();
        jdt_V_Fecha.setDate(null);
        cb_vehiculos();

        int maxID = v.getMax();

        btn_V_Guardar.setEnabled(true);
        btn_V_Nuevo.setEnabled(false);
        btn_V_Editar.setEnabled(false);
        btn_V_Eliminar.setEnabled(false);
        btn_V_Cancelar.setEnabled(true);

        txt_V_IdVehiculo.setText(String.valueOf(maxID));
        txt_V_Matricula.setText("");
        txt_V_Marca.setText("");
        txt_V_Modelo.setText("");
    }//GEN-LAST:event_btn_V_NuevoActionPerformed

    private void txt_V_ModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_V_ModeloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_V_ModeloActionPerformed

    private void btn_P_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_P_SalirActionPerformed
        tpane.setSelectedIndex(0);

        tpane.setEnabledAt(0, true);
        tpane.setEnabledAt(1, false);
        tpane.setEnabledAt(2, false);
        tpane.setEnabledAt(3, false);
        tpane.setEnabledAt(4, false);
        tpane.setEnabledAt(5, false);
    }//GEN-LAST:event_btn_P_SalirActionPerformed

    private void btn_P_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_P_EliminarActionPerformed

        pi.SetPiz(Integer.parseInt(txt_P_IdPieza.getText()));
        pi.SetDescrp(txt_P_Descripcion.getText());
        pi.SetStock(Integer.parseInt(txt_P_Stock.getText()));

        try {
            pf.Eliminar_Piezas(pi);
        } catch (IOException ex) {

        }

        Piezas_Habilitar();

        txt_P_IdPieza.setText("");
        txt_P_Descripcion.setText("");
        txt_P_Stock.setText("");

        btn_P_Guardar.setEnabled(false);
        btn_P_Nuevo.setEnabled(true);
        btn_P_Editar.setEnabled(false);
        btn_P_Eliminar.setEnabled(false);
        btn_P_Cancelar.setEnabled(false);
    }//GEN-LAST:event_btn_P_EliminarActionPerformed

    private void btn_P_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_P_EditarActionPerformed
        Piezas_Habilitar();

        btn_P_Guardar.setEnabled(true);
        btn_P_Nuevo.setEnabled(false);
        btn_P_Editar.setEnabled(false);
        btn_P_Eliminar.setEnabled(false);
        btn_P_Cancelar.setEnabled(true);

        ban_piezas = true;
    }//GEN-LAST:event_btn_P_EditarActionPerformed

    private void btn_P_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_P_CancelarActionPerformed
        Piezas_Deshabilitar();

        btn_P_Guardar.setEnabled(false);
        btn_P_Nuevo.setEnabled(true);
        btn_P_Editar.setEnabled(false);
        btn_P_Eliminar.setEnabled(false);
        btn_P_Cancelar.setEnabled(false);

        ban_piezas = false;
    }//GEN-LAST:event_btn_P_CancelarActionPerformed

    private void btn_P_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_P_GuardarActionPerformed

        if ("".equals(txt_P_Descripcion.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese la descripcion de la pieza");
            return;
        }

        if ("".equals(txt_P_Stock.getText())) {
            JOptionPane.showMessageDialog(null, "Indique la cantidad disponible");
            return;
        }

        try {
            pi = new piezas();
            pi.SetPiz(Integer.parseInt(txt_P_IdPieza.getText()));

            if (ban_piezas != true && pf.BuscarPiezas(pi) != null) {
                JOptionPane.showMessageDialog(null, "Ese Id de pieza ya existe");
                return;
            }

            pi.SetPiz(Integer.parseInt(txt_P_IdPieza.getText()));
            pi.SetDescrp(txt_P_Descripcion.getText());
            pi.SetStock(Integer.parseInt(txt_P_Stock.getText()));

            if (ban_piezas != true) {
                pf.Guardar(pi);
                JOptionPane.showMessageDialog(null, "Guardado con Éxito");
            } else {
                ban_piezas = false;
                try {
                    pf.Editar(pi);
                    JOptionPane.showMessageDialog(null, "Editado con Éxito");
                    System.out.println("SI");
                } catch (IOException ex) {

                }
            }

            Piezas_Deshabilitar();

            btn_P_Guardar.setEnabled(false);
            btn_P_Nuevo.setEnabled(true);
            btn_P_Editar.setEnabled(false);
            btn_P_Eliminar.setEnabled(false);
            btn_P_Cancelar.setEnabled(false);

        } catch (FileNotFoundException ex) {

        }
    }//GEN-LAST:event_btn_P_GuardarActionPerformed

    private void btn_P_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_P_NuevoActionPerformed
        Piezas_Habilitar();

        int maxID = pf.getMax();

        btn_P_Guardar.setEnabled(true);
        btn_P_Nuevo.setEnabled(false);
        btn_P_Editar.setEnabled(false);
        btn_P_Eliminar.setEnabled(false);
        btn_P_Cancelar.setEnabled(true);

        txt_P_IdPieza.setText(String.valueOf(maxID));
        txt_P_Descripcion.setText("");
        txt_P_Stock.setText("");
    }//GEN-LAST:event_btn_P_NuevoActionPerformed

    private void btn_P_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_P_BuscarActionPerformed
        if (txt_P_Id.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el ID de la pieza");
            txt_P_Id.setText("");
            txt_P_IdPieza.setText("");
            txt_P_Descripcion.setText("");
            txt_P_Stock.setText("");
            return;
        }

        if (!ValidaNum(txt_P_Id.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID valido");
            txt_P_Id.setText("");
            txt_P_IdPieza.setText("");
            txt_P_Descripcion.setText("");
            txt_P_Stock.setText("");
            return;
        }

        Piezas_Deshabilitar();
        try {
            pi = new piezas();
            pi.SetPiz(Integer.parseInt(txt_P_Id.getText()));
            pi = pf.BuscarPiezas(pi);

            if (pi != null) {
                txt_P_IdPieza.setText(String.valueOf(pi.getPiz()));
                txt_P_Descripcion.setText(pi.getDescrp());
                txt_P_Stock.setText(String.valueOf(pi.getStock()));

                btn_P_Guardar.setEnabled(false);
                btn_P_Nuevo.setEnabled(false);
                btn_P_Editar.setEnabled(true);
                btn_P_Eliminar.setEnabled(true);
                btn_P_Cancelar.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "No existe ese ID");

                btn_P_Guardar.setEnabled(false);
                btn_P_Nuevo.setEnabled(true);
                btn_P_Editar.setEnabled(false);
                btn_P_Eliminar.setEnabled(false);
                btn_P_Cancelar.setEnabled(false);

                txt_P_Id.setText("");
                txt_P_IdPieza.setText("");
                txt_P_Descripcion.setText("");
                txt_P_Stock.setText("");
            }

        } catch (FileNotFoundException ex) {

        }

        txt_P_Id.setText("");
    }//GEN-LAST:event_btn_P_BuscarActionPerformed

    private void txt_V_ColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_V_ColorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_V_ColorActionPerformed

    private void txt_V_IdVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_V_IdVehiculoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_V_IdVehiculoActionPerformed

    private void btn_R_Salir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_R_Salir1ActionPerformed
        // TODO add your handling code here:
        tpane.setSelectedIndex(0);

        tpane.setEnabledAt(0, true);
        tpane.setEnabledAt(1, false);
        tpane.setEnabledAt(2, false);
        tpane.setEnabledAt(3, false);
        tpane.setEnabledAt(4, false);
        tpane.setEnabledAt(5, false);
    }//GEN-LAST:event_btn_R_Salir1ActionPerformed

    /**
     * @param args the command line arguments
     */
 
    
    public static void main(String args[]) {
    try {
        // Establecer el tema Flat IntelliJ
        FlatIntelliJLaf.setup();

        // Alternativamente, si prefieres usar UIManager directamente:
        // javax.swing.UIManager.setLookAndFeel(new FlatIntelliJLaf());
        
        // Después de configurar el tema, puedes continuar con el resto del código
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    main myFrame = new main();
                    myFrame.setVisible(true);
                    myFrame.setResizable(false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    } catch (Exception ex) {
        ex.printStackTrace();
    }
        //</editor-fold>
       
        
        /* Create and display the form */
        

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAutentificar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRemover;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btn_C_Buscar;
    private javax.swing.JButton btn_C_Cancelar;
    private javax.swing.JButton btn_C_Editar;
    private javax.swing.JButton btn_C_Eliminar;
    private javax.swing.JButton btn_C_Guardar;
    private javax.swing.JButton btn_C_Nuevo;
    private javax.swing.JButton btn_C_Salir;
    private javax.swing.JButton btn_P_Buscar;
    private javax.swing.JButton btn_P_Cancelar;
    private javax.swing.JButton btn_P_Editar;
    private javax.swing.JButton btn_P_Eliminar;
    private javax.swing.JButton btn_P_Guardar;
    private javax.swing.JButton btn_P_Nuevo;
    private javax.swing.JButton btn_P_Salir;
    private javax.swing.JButton btn_R_Buscar;
    private javax.swing.JButton btn_R_Cancelar;
    private javax.swing.JButton btn_R_Editar;
    private javax.swing.JButton btn_R_Eliminar;
    private javax.swing.JButton btn_R_Guardar;
    private javax.swing.JButton btn_R_Nuevo;
    private javax.swing.JButton btn_R_Salir;
    private javax.swing.JButton btn_R_Salir1;
    private javax.swing.JButton btn_V_Buscar;
    private javax.swing.JButton btn_V_Cancelar;
    private javax.swing.JButton btn_V_Editar;
    private javax.swing.JButton btn_V_Eliminar;
    private javax.swing.JButton btn_V_Guardar;
    private javax.swing.JButton btn_V_Nuevo;
    private javax.swing.JComboBox<String> cbPerfil;
    private javax.swing.JComboBox<String> cb_R_IdPieza;
    private javax.swing.JComboBox<String> cb_R_IdVehiculo;
    private javax.swing.JComboBox<String> cb_V_SeleccioneCliente;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextArea jTextArea1;
    private com.toedter.calendar.JDateChooser jdt_E_Fecha;
    private com.toedter.calendar.JDateChooser jdt_S_Fecha;
    private com.toedter.calendar.JDateChooser jdt_V_Fecha;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPassword1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lbl_C_ApellidoMaterno;
    private javax.swing.JLabel lbl_C_ApellidoPaterno;
    private javax.swing.JLabel lbl_C_Id;
    private javax.swing.JLabel lbl_C_IdCliente;
    private javax.swing.JLabel lbl_C_IdUsuario;
    private javax.swing.JLabel lbl_C_Nombre;
    private javax.swing.JLabel lbl_P_Descripcion;
    private javax.swing.JLabel lbl_P_Id;
    private javax.swing.JLabel lbl_P_IdPieza;
    private javax.swing.JLabel lbl_P_Stock;
    private javax.swing.JLabel lbl_R_ControlPiezas;
    private javax.swing.JLabel lbl_R_Falla;
    private javax.swing.JLabel lbl_R_FechaEntrada;
    private javax.swing.JLabel lbl_R_FechaSalida;
    private javax.swing.JLabel lbl_R_Id;
    private javax.swing.JLabel lbl_R_IdPieza;
    private javax.swing.JLabel lbl_R_IdVehiculo;
    private javax.swing.JLabel lbl_R_Id_Reparacion;
    private javax.swing.JLabel lbl_V_Color;
    private javax.swing.JLabel lbl_V_Fecha;
    private javax.swing.JLabel lbl_V_Fecha2;
    private javax.swing.JLabel lbl_V_Id;
    private javax.swing.JLabel lbl_V_IdVehiculo;
    private javax.swing.JLabel lbl_V_Marca;
    private javax.swing.JLabel lbl_V_Matricula;
    private javax.swing.JLabel lbl_V_Modelo;
    private javax.swing.JLabel lbl_V_Notas;
    private javax.swing.JLabel lbl_V_SeleccioneCliente;
    private javax.swing.JPanel pnlClientes;
    private javax.swing.JPanel pnlLogin;
    private javax.swing.JPanel pnlPiezas;
    private javax.swing.JPanel pnlReparaciones;
    private javax.swing.JPanel pnlUsuarios;
    private javax.swing.JPanel pnlVehiculos;
    private javax.swing.JTabbedPane tpane;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtMaterno;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPaterno;
    private javax.swing.JTextField txtPsw;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JTextField txt_C_ApellidoMaterno;
    private javax.swing.JTextField txt_C_ApellidoPaterno;
    private javax.swing.JTextField txt_C_Buscar;
    private javax.swing.JTextField txt_C_IdCliente;
    private javax.swing.JTextField txt_C_IdUsuario;
    private javax.swing.JTextField txt_C_Nombre;
    private javax.swing.JTextField txt_P_Descripcion;
    private javax.swing.JTextField txt_P_Id;
    private javax.swing.JTextField txt_P_IdPieza;
    private javax.swing.JTextField txt_P_Stock;
    private javax.swing.JTextField txt_R_ControlPiezas;
    private javax.swing.JTextField txt_R_Falla;
    private javax.swing.JTextField txt_R_Id;
    private javax.swing.JTextField txt_R_IdReparacion;
    private javax.swing.JTextField txt_V_Buscar;
    private javax.swing.JTextField txt_V_Color;
    private javax.swing.JTextField txt_V_IdVehiculo;
    private javax.swing.JTextField txt_V_Marca;
    private javax.swing.JTextField txt_V_Matricula;
    private javax.swing.JTextField txt_V_Modelo;
    // End of variables declaration//GEN-END:variables
}
