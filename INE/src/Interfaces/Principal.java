/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;


import Clases.Archivo;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.alee.laf.WebLookAndFeel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

//Importamos los TDA del paquete Clases
import Clases.ManagerUsers;
import Clases.ManagerInventario;
import Clases.ManagerMySQL;
import Clases.ManagerPermisos;
import Clases.ManagerSolicitud;
import Clases.ManagerComplemento;
import Clases.ManagerVehiculos;
import Clases.ManejadorInventario;
import Clases.ManagerAsignarEquipo;


//Importamos los formularios
import Formularios.addEmpleados;
import Formularios.addInventario;
import Formularios.addInventarioGranel;
import Formularios.addResguardo;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.sql.SQLException;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JSpinner;
import static Interfaces.ventana_modificar_vehiculo.campo;

/**
 *
 * @author Kevin
 */
public class Principal extends javax.swing.JFrame {
            
    //VARIABLES PARA CLASES
    ManagerUsers manager_users;
    ManagerInventario manager_inventario;
    ManagerMySQL manajerMySQL;
    ManagerPermisos manager_permisos;
    ManagerVehiculos managerVehiculos;
    ManagerSolicitud manager_solicitud;
    ManagerComplemento manager_complemento;
    ManejadorInventario manejador_inventario;
    ManagerAsignarEquipo manager_asignar;
    
    public String Responsable,Cargo,Area,Tipo_de_uso,Municipio,Localidad,Responsable1,Cargo1,Area1,Tipo_de_uso1,Municipio1,Localidad1;
    
    
    //VARIABLES QUE FUNCIONAN COMO BANDERAS
    
    //Está variable es para identificar cuando voy a dar de alta o cuando voy a 
    //actualizar un usuario (reutilizare la misma ventana addEmpleado) 
    public static int banderaUser = 1;
    //Está variable es para identificar si es el inventario o el inventario a granel.
    //Me ayuda a indentificar la función del boton añadir y saber que ventana llamar, los popmenu correspondientes
    //el doble clic y el modelo de la tabla
    public static int banderaInventario = 1;
    
    //Esta bandera es para la ventana de solicitudes, y es para saber que clave debe tomar
    public static int banderaSolicitud = 1;
    
    //Esta bandera es para la ventana de permsos de solicitudes y es para saber si solo es consulta o puede actualzar la info
    public static int banderaPermisosSolicitud = 1;
    
    /*PESTAÑA DE MANEJADOR KEVIN*/
    //Esta bandera es para conocer si selecciono un empleado al momento de querer asignar equipos
    boolean empleadoSeleccionado = false;
    //Esta bandera es saber si son a granel o no
    boolean esGranel = false;
    
    
    //VARIABLES GLOBALES
    public static String usuario = "",prodInventario = "",UserUpdate = "",estadoPendiente = "",estadoSolicitud = "",Username = "",productoAsignacionReemplazo = "",productoAREstado = "",empleadoSolicitud = "";
    public static int idPendiente,productoIDVale;
    public DefaultTableModel modelotablaMAsignados,modeloRecoleccion,modeloObjetosEntregados;
    public static String Claves[];
    public static int Cantidad[],IDVales[];
            
    Vector IPS = new Vector();
    public static DefaultTableModel modeloTablaIP;
    public static Component temporalSolicitud;
    public static int pestañas = 0;
    /**
     * Creates new form Principal
     */
    public Principal() throws ClassNotFoundException, SQLException, IOException {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Iconos/IEE.png")).getImage());

        //Asignamos memoria al objeto
        manager_users = new ManagerUsers();
        manager_inventario = new ManagerInventario();
        manajerMySQL = new ManagerMySQL();
        manager_permisos = new ManagerPermisos();
        manager_solicitud = new ManagerSolicitud();
        manager_complemento = new ManagerComplemento();
        managerVehiculos = new ManagerVehiculos();
        manejador_inventario = new ManejadorInventario();
        manager_asignar = new ManagerAsignarEquipo();
        //Para obtener el nombre de usuario con el que se logearon
        leer();
        
        //Deshabilitamos el movimiento de los encabezados de las tablas
        tablaUsuarios.getTableHeader().setReorderingAllowed(false);
        tablaInventario.getTableHeader().setReorderingAllowed(false);
        tablaIP.getTableHeader().setReorderingAllowed(false);
        tablaBD.getTableHeader().setReorderingAllowed(false);
        tablaVehiculos.getTableHeader().setReorderingAllowed(false);
        tablaSolicitudesPersonal.getTableHeader().setReorderingAllowed(false);
        tablaSolicitudes.getTableHeader().setReorderingAllowed(false);
        tablaResguardo.getTableHeader().setReorderingAllowed(false);

        //Obtenemos el modelo de la tabla 
        modeloTablaIP = (DefaultTableModel) tablaIP.getModel();
        modelotablaMAsignados = (DefaultTableModel) tablaMAsignados.getModel();
        modeloRecoleccion = (DefaultTableModel) tablaRecoleccion.getModel();
        modeloObjetosEntregados = (DefaultTableModel) tablaObjetosEntregados.getModel();
        
        campoObservaciones.setLineWrap(true);
        //Quitar editable a spinner
        ((JSpinner.DefaultEditor) campoip1.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) campoip2.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) campoip3.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) campoip4.getEditor()).getTextField().setEditable(false);

        etiquetaMarca.setText("");
        etiquetaLinea.setText("");
        etiquetaKilometraje.setText("");
        etiquetaAño.setText("");
        campoObservaciones.setText("");
        campoObservaciones.setEditable(false);
        zoom.setVisible(false);
        
        //Aplicamos el autocompletar a los combo
        AutoCompleteDecorator.decorate(this.comboEmpleado);
        
    }//Constructor
      
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MenuUsuarios = new javax.swing.JPopupMenu();
        Eliminar = new javax.swing.JMenuItem();
        Actualizar = new javax.swing.JMenuItem();
        Promover = new javax.swing.JMenuItem();
        Permisos = new javax.swing.JMenuItem();
        ActualizarInfoU = new javax.swing.JMenuItem();
        MenuInventario = new javax.swing.JPopupMenu();
        ActualizarInfoG = new javax.swing.JMenuItem();
        MenuSolicitudes = new javax.swing.JPopupMenu();
        Atender = new javax.swing.JMenuItem();
        ActualizarInfo = new javax.swing.JMenuItem();
        MenuVehiculos = new javax.swing.JPopupMenu();
        AsignarV = new javax.swing.JMenuItem();
        ActualizarV = new javax.swing.JMenuItem();
        SolictarMas = new javax.swing.JMenu();
        SolicitarBaja = new javax.swing.JMenuItem();
        Servicio = new javax.swing.JMenuItem();
        ActualizarInfoV = new javax.swing.JMenuItem();
        MenuSolicitudesP = new javax.swing.JPopupMenu();
        ActualizarInfoSP = new javax.swing.JMenuItem();
        MenuPermisosP = new javax.swing.JPopupMenu();
        ActualizarInfoPP = new javax.swing.JMenuItem();
        MenuAsignacionP = new javax.swing.JPopupMenu();
        Reemplazar = new javax.swing.JMenuItem();
        ActualizarAsignacionP = new javax.swing.JMenuItem();
        MenuAsignacionPG = new javax.swing.JPopupMenu();
        ActualizarAsignacionPG = new javax.swing.JMenuItem();
        MenuPendientes = new javax.swing.JPopupMenu();
        Autorizar = new javax.swing.JMenuItem();
        Denegar = new javax.swing.JMenuItem();
        ActualizarPendientes = new javax.swing.JMenuItem();
        MenuAsginados = new javax.swing.JPopupMenu();
        CancelarA = new javax.swing.JMenuItem();
        MenuRecoleccion = new javax.swing.JPopupMenu();
        EntregarTodo = new javax.swing.JMenuItem();
        EntregarParte = new javax.swing.JMenuItem();
        ActualizarInfoReco = new javax.swing.JMenuItem();
        MenuEntregados = new javax.swing.JPopupMenu();
        UpdateInfo = new javax.swing.JMenuItem();
        CancelarEntrega = new javax.swing.JMenuItem();
        MenuStockMin = new javax.swing.JPopupMenu();
        AgregarStock = new javax.swing.JMenuItem();
        ActualizarInfoSM = new javax.swing.JMenuItem();
        Grupo1 = new javax.swing.ButtonGroup();
        bg_manejo_inventario = new javax.swing.ButtonGroup();
        bt_tipo_inventario_asignable = new javax.swing.ButtonGroup();
        GrupoTipoInventario = new javax.swing.ButtonGroup();
        tabbedPrincipal = new javax.swing.JTabbedPane();
        pestañaInventario = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaInventario = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jPanel1 = new javax.swing.JPanel();
        btnAddInventario = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        comboInventario = new javax.swing.JComboBox<>();
        txtBusqueda = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        comboFiltro = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        usuarios = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        pn_tablaUsuarios = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUsuarios = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        btnAddEmpleado = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtBusquedaUsuario = new javax.swing.JTextField();
        comboFiltroUsuario = new javax.swing.JComboBox<>();
        fondo = new javax.swing.JLabel();
        vehiculos = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablaVehiculos = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jPanel14 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        etiquetaKilometraje = new javax.swing.JLabel();
        etiquetaEstado = new javax.swing.JLabel();
        etiquetaMarca = new javax.swing.JLabel();
        etiquetaLinea = new javax.swing.JLabel();
        etiquetaAño = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        campoObservaciones = new javax.swing.JTextArea();
        fondoVehiculo = new javax.swing.JPanel();
        zoom = new javax.swing.JButton();
        imagenVehiculo = new javax.swing.JLabel();
        btnAñadirVehiculo = new javax.swing.JButton();
        comboFiltroVehiculos = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        txtBusquedaVehiculos = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        solicitudes = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaSolicitudes = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jPanel10 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablaStockMin = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jLabel9 = new javax.swing.JLabel();
        empleado = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        ScrollEmpleado = new javax.swing.JScrollPane();
        panelEmpleado = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        lblCargo = new javax.swing.JLabel();
        lblArea = new javax.swing.JLabel();
        lblDomicilio = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblCurp = new javax.swing.JLabel();
        lblRfc = new javax.swing.JLabel();
        lblMunicipio = new javax.swing.JLabel();
        lblLocalidad = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        btnEditar1 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaResguardo = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        btnAñadirResguardo = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaSolicitudesPersonal = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jLabel23 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablaPermisosPersonales = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){return false; }  };
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaAsignacionPersonal = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){return false; }  };
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        radioEquipo = new javax.swing.JRadioButton();
        radioGranel = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        configuracion = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaBD = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaIP = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jPanel15 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        campoip3 = new javax.swing.JSpinner();
        campoip4 = new javax.swing.JSpinner();
        campoip2 = new javax.swing.JSpinner();
        campoip1 = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        manejo_inventario = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        pn_contenedor_ventanas1 = new javax.swing.JPanel();
        sp_asignacion_inventario1 = new javax.swing.JScrollPane();
        pn_asignacion_inventario1 = new javax.swing.JPanel();
        lb_objetos_asignables2 = new javax.swing.JLabel();
        jScrollPane31 = new javax.swing.JScrollPane();
        tablaMAsignados = new javax.swing.JTable();
        lb_objetos_asignables3 = new javax.swing.JLabel();
        jScrollPane32 = new javax.swing.JScrollPane();
        tablaMInventarioA = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        btn_generar_vale3 = new javax.swing.JButton();
        btn_cancelar2 = new javax.swing.JButton();
        rb_inventario_normal1 = new javax.swing.JRadioButton();
        rb_inventario_granel1 = new javax.swing.JRadioButton();
        comboEmpleado = new javax.swing.JComboBox<>();
        lb_objetos_asignables4 = new javax.swing.JLabel();
        sp_recoleccion_inventario1 = new javax.swing.JScrollPane();
        pn_recoleccion_inventario1 = new javax.swing.JPanel();
        lb_empleado3 = new javax.swing.JLabel();
        lb_objetos_asignados1 = new javax.swing.JLabel();
        jScrollPane34 = new javax.swing.JScrollPane();
        tablaRecoleccion = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        lb_objetos_entregados1 = new javax.swing.JLabel();
        jScrollPane35 = new javax.swing.JScrollPane();
        tablaObjetosEntregados = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        btnGenerarValeR = new javax.swing.JButton();
        btn_cancelar3 = new javax.swing.JButton();
        comboEmpleadoR = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        pn_acciones1 = new javax.swing.JPanel();
        rb_asignacion1 = new javax.swing.JRadioButton();
        rb_recoleccion1 = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        itemAnterior = new javax.swing.JMenuItem();
        itemSiguiente = new javax.swing.JMenuItem();
        mi_viaticos = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itemSalir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuOpciones = new javax.swing.JMenu();
        menuPermisos = new javax.swing.JMenuItem();
        menuPuestoArea = new javax.swing.JMenuItem();
        MenuSolicitud = new javax.swing.JMenuItem();
        menuAsignar = new javax.swing.JMenu();
        Asignar = new javax.swing.JMenuItem();
        Equipos = new javax.swing.JMenuItem();

        Eliminar.setText("Eliminar");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });
        MenuUsuarios.add(Eliminar);

        Actualizar.setText("Actualizar");
        Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarActionPerformed(evt);
            }
        });
        MenuUsuarios.add(Actualizar);

        Promover.setText("Promover");
        MenuUsuarios.add(Promover);

        Permisos.setText("Permisos");
        Permisos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PermisosActionPerformed(evt);
            }
        });
        MenuUsuarios.add(Permisos);

        ActualizarInfoU.setText("Refrescar tabla");
        ActualizarInfoU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarInfoUActionPerformed(evt);
            }
        });
        MenuUsuarios.add(ActualizarInfoU);

        ActualizarInfoG.setText("Refrescar tabla");
        ActualizarInfoG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarInfoGActionPerformed(evt);
            }
        });
        MenuInventario.add(ActualizarInfoG);

        Atender.setText("Atender...");
        Atender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtenderActionPerformed(evt);
            }
        });
        MenuSolicitudes.add(Atender);

        ActualizarInfo.setText("Refrescar tabla");
        ActualizarInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarInfoActionPerformed(evt);
            }
        });
        MenuSolicitudes.add(ActualizarInfo);

        AsignarV.setText("Asignar...");
        MenuVehiculos.add(AsignarV);

        ActualizarV.setText("Actualizar");
        ActualizarV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarVActionPerformed(evt);
            }
        });
        MenuVehiculos.add(ActualizarV);

        SolictarMas.setText("Solicitar...");

        SolicitarBaja.setText("Solicitud baja/comodato/donación");
        SolictarMas.add(SolicitarBaja);

        Servicio.setText("Servicio");
        SolictarMas.add(Servicio);

        MenuVehiculos.add(SolictarMas);

        ActualizarInfoV.setText("Refrescar tabla");
        ActualizarInfoV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarInfoVActionPerformed(evt);
            }
        });
        MenuVehiculos.add(ActualizarInfoV);

        ActualizarInfoSP.setText("Refrescar tabla");
        ActualizarInfoSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarInfoSPActionPerformed(evt);
            }
        });
        MenuSolicitudesP.add(ActualizarInfoSP);

        ActualizarInfoPP.setText("Refrescar tabla");
        ActualizarInfoPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarInfoPPActionPerformed(evt);
            }
        });
        MenuPermisosP.add(ActualizarInfoPP);

        Reemplazar.setText("Solicitar reemplazo");
        Reemplazar.setToolTipText("");
        Reemplazar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReemplazarActionPerformed(evt);
            }
        });
        MenuAsignacionP.add(Reemplazar);

        ActualizarAsignacionP.setText("Refrescar tabla");
        ActualizarAsignacionP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarAsignacionPActionPerformed(evt);
            }
        });
        MenuAsignacionP.add(ActualizarAsignacionP);

        ActualizarAsignacionPG.setText("Refrescar tabla");
        ActualizarAsignacionPG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarAsignacionPGActionPerformed(evt);
            }
        });
        MenuAsignacionPG.add(ActualizarAsignacionPG);

        Autorizar.setText("Autorizar");
        Autorizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AutorizarActionPerformed(evt);
            }
        });
        MenuPendientes.add(Autorizar);

        Denegar.setText("Denegar");
        Denegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DenegarActionPerformed(evt);
            }
        });
        MenuPendientes.add(Denegar);

        ActualizarPendientes.setText("Refrescar tabla");
        ActualizarPendientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarPendientesActionPerformed(evt);
            }
        });
        MenuPendientes.add(ActualizarPendientes);

        CancelarA.setText("Cancelar");
        CancelarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarAActionPerformed(evt);
            }
        });
        MenuAsginados.add(CancelarA);

        EntregarTodo.setText("Entrega completa");
        EntregarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EntregarTodoActionPerformed(evt);
            }
        });
        MenuRecoleccion.add(EntregarTodo);

        EntregarParte.setText("Entrega parcial");
        EntregarParte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EntregarParteActionPerformed(evt);
            }
        });
        MenuRecoleccion.add(EntregarParte);

        ActualizarInfoReco.setText("Refrescar tabla");
        ActualizarInfoReco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarInfoRecoActionPerformed(evt);
            }
        });
        MenuRecoleccion.add(ActualizarInfoReco);

        UpdateInfo.setText("Actualizar");
        UpdateInfo.setToolTipText("");
        UpdateInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateInfoActionPerformed(evt);
            }
        });
        MenuEntregados.add(UpdateInfo);

        CancelarEntrega.setText("Cancelar");
        CancelarEntrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarEntregaActionPerformed(evt);
            }
        });
        MenuEntregados.add(CancelarEntrega);

        AgregarStock.setText("Actualizar stock");
        AgregarStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarStockActionPerformed(evt);
            }
        });
        MenuStockMin.add(AgregarStock);

        ActualizarInfoSM.setText("Refrescar tabla");
        ActualizarInfoSM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarInfoSMActionPerformed(evt);
            }
        });
        MenuStockMin.add(ActualizarInfoSM);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sistema Integral - Instituto Estatal Electoral de Nayarit");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tabbedPrincipal.setBackground(new java.awt.Color(255, 204, 204));
        tabbedPrincipal.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        pestañaInventario.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N

        jPanel3.setLayout(null);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/baner inventario.png"))); // NOI18N
        jPanel3.add(jLabel5);
        jLabel5.setBounds(10, 10, 1350, 80);

        tablaInventario.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tablaInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Almacén", "Marca", "Stock"
            }
        ));
        tablaInventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaInventarioMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaInventarioMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tablaInventario);

        jPanel3.add(jScrollPane3);
        jScrollPane3.setBounds(30, 130, 1110, 500);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones :", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI", 0, 12))); // NOI18N

        btnAddInventario.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        btnAddInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/agregar.png"))); // NOI18N
        btnAddInventario.setText("Añadir");
        btnAddInventario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddInventarioActionPerformed(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IEE.png"))); // NOI18N

        comboInventario.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        comboInventario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inventario", "Inventario Granel" }));
        comboInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboInventarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAddInventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboInventario, 0, 174, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnAddInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(330, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel1);
        jPanel1.setBounds(1150, 90, 200, 540);

        txtBusqueda.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });
        jPanel3.add(txtBusqueda);
        txtBusqueda.setBounds(380, 90, 290, 30);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Busqueda por ");
        jPanel3.add(jLabel12);
        jLabel12.setBounds(30, 90, 130, 22);

        comboFiltro.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFiltroActionPerformed(evt);
            }
        });
        jPanel3.add(comboFiltro);
        comboFiltro.setBounds(150, 90, 210, 28);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        jPanel3.add(jLabel1);
        jLabel1.setBounds(0, 0, 1367, 769);

        javax.swing.GroupLayout pestañaInventarioLayout = new javax.swing.GroupLayout(pestañaInventario);
        pestañaInventario.setLayout(pestañaInventarioLayout);
        pestañaInventarioLayout.setHorizontalGroup(
            pestañaInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1417, Short.MAX_VALUE)
        );
        pestañaInventarioLayout.setVerticalGroup(
            pestañaInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("  Inventario", new javax.swing.ImageIcon(getClass().getResource("/Iconos/inventario.png")), pestañaInventario); // NOI18N

        jPanel5.setLayout(null);

        pn_tablaUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        pn_tablaUsuarios.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Usuario", "Nombre(s)", "Apellido Paterno", "Apellido Materno", "Cargo", "Área"
            }
        ));
        tablaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaUsuariosMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tablaUsuarios);

        javax.swing.GroupLayout pn_tablaUsuariosLayout = new javax.swing.GroupLayout(pn_tablaUsuarios);
        pn_tablaUsuarios.setLayout(pn_tablaUsuariosLayout);
        pn_tablaUsuariosLayout.setHorizontalGroup(
            pn_tablaUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_tablaUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 986, Short.MAX_VALUE)
                .addContainerGap())
        );
        pn_tablaUsuariosLayout.setVerticalGroup(
            pn_tablaUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_tablaUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.add(pn_tablaUsuarios);
        pn_tablaUsuarios.setBounds(20, 150, 1010, 360);

        btnAddEmpleado.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        btnAddEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/agregar.png"))); // NOI18N
        btnAddEmpleado.setText("  Agregar");
        btnAddEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmpleadoActionPerformed(evt);
            }
        });
        jPanel5.add(btnAddEmpleado);
        btnAddEmpleado.setBounds(1090, 210, 140, 30);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/baner Usuarios.png"))); // NOI18N
        jPanel5.add(jLabel2);
        jLabel2.setBounds(10, 10, 1350, 80);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Busqueda por ");
        jPanel5.add(jLabel14);
        jLabel14.setBounds(30, 100, 130, 22);

        txtBusquedaUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtBusquedaUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaUsuarioKeyReleased(evt);
            }
        });
        jPanel5.add(txtBusquedaUsuario);
        txtBusquedaUsuario.setBounds(390, 100, 290, 30);

        comboFiltroUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboFiltroUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFiltroUsuarioActionPerformed(evt);
            }
        });
        jPanel5.add(comboFiltroUsuario);
        comboFiltroUsuario.setBounds(150, 100, 210, 28);

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        jPanel5.add(fondo);
        fondo.setBounds(0, 0, 1367, 769);

        javax.swing.GroupLayout usuariosLayout = new javax.swing.GroupLayout(usuarios);
        usuarios.setLayout(usuariosLayout);
        usuariosLayout.setHorizontalGroup(
            usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1417, Short.MAX_VALUE)
        );
        usuariosLayout.setVerticalGroup(
            usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("Usuarios", new javax.swing.ImageIcon(getClass().getResource("/Iconos/usuarios.png")), usuarios); // NOI18N

        vehiculos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vehiculosMouseClicked(evt);
            }
        });

        jPanel6.setLayout(null);

        tablaVehiculos.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tablaVehiculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Marca", "Linea", "Año", "Color", "Matricula"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaVehiculos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaVehiculosMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaVehiculosMouseReleased(evt);
            }
        });
        tablaVehiculos.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablaVehiculosPropertyChange(evt);
            }
        });
        tablaVehiculos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablaVehiculosKeyReleased(evt);
            }
        });
        jScrollPane10.setViewportView(tablaVehiculos);

        jPanel6.add(jScrollPane10);
        jScrollPane10.setBounds(20, 110, 900, 580);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 0, 24)); // NOI18N
        jLabel13.setText("Kilometraje:");

        etiquetaKilometraje.setFont(new java.awt.Font("Yu Gothic UI", 0, 36)); // NOI18N
        etiquetaKilometraje.setText("0 km");

        etiquetaEstado.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        etiquetaEstado.setForeground(new java.awt.Color(0, 204, 0));
        etiquetaEstado.setText("DISPONIBLE");

        etiquetaMarca.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        etiquetaMarca.setText("jLabel6");

        etiquetaLinea.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        etiquetaLinea.setText("jLabel6");

        etiquetaAño.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        etiquetaAño.setText("jLabel6");

        jLabel6.setText("Observaciones:");

        campoObservaciones.setColumns(20);
        campoObservaciones.setRows(5);
        jScrollPane11.setViewportView(campoObservaciones);

        fondoVehiculo.setLayout(null);

        zoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/look.png"))); // NOI18N
        zoom.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        zoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomActionPerformed(evt);
            }
        });
        fondoVehiculo.add(zoom);
        zoom.setBounds(360, 200, 30, 30);

        imagenVehiculo.setBackground(new java.awt.Color(255, 204, 204));
        fondoVehiculo.add(imagenVehiculo);
        imagenVehiculo.setBounds(0, 0, 390, 230);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(etiquetaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                                        .addComponent(etiquetaMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(etiquetaLinea, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(etiquetaAño, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(18, 18, 18)
                                        .addComponent(etiquetaKilometraje)))
                                .addGap(0, 73, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(fondoVehiculo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etiquetaEstado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaMarca)
                    .addComponent(etiquetaLinea)
                    .addComponent(etiquetaAño))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fondoVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(etiquetaKilometraje))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.add(jPanel14);
        jPanel14.setBounds(930, 110, 420, 500);

        btnAñadirVehiculo.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        btnAñadirVehiculo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/agregar.png"))); // NOI18N
        btnAñadirVehiculo.setText(" Añadir");
        btnAñadirVehiculo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAñadirVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirVehiculoActionPerformed(evt);
            }
        });
        jPanel6.add(btnAñadirVehiculo);
        btnAñadirVehiculo.setBounds(930, 30, 420, 30);

        comboFiltroVehiculos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboFiltroVehiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFiltroVehiculosActionPerformed(evt);
            }
        });
        jPanel6.add(comboFiltroVehiculos);
        comboFiltroVehiculos.setBounds(160, 40, 210, 28);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setText("Busqueda por ");
        jPanel6.add(jLabel20);
        jLabel20.setBounds(40, 40, 120, 22);

        txtBusquedaVehiculos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtBusquedaVehiculos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaVehiculosKeyReleased(evt);
            }
        });
        jPanel6.add(txtBusquedaVehiculos);
        txtBusquedaVehiculos.setBounds(390, 40, 290, 30);

        jButton5.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jButton5.setText("Modificar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton5);
        jButton5.setBounds(930, 73, 420, 30);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        jPanel6.add(jLabel4);
        jLabel4.setBounds(0, 0, 1367, 769);

        javax.swing.GroupLayout vehiculosLayout = new javax.swing.GroupLayout(vehiculos);
        vehiculos.setLayout(vehiculosLayout);
        vehiculosLayout.setHorizontalGroup(
            vehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1417, Short.MAX_VALUE)
        );
        vehiculosLayout.setVerticalGroup(
            vehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("  Vehiculos", new javax.swing.ImageIcon(getClass().getResource("/Iconos/vehiculos.png")), vehiculos); // NOI18N

        jPanel7.setLayout(null);

        tablaSolicitudes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Solicitud", "Usuario que solicito", "Producto", "Motivo", "Fecha que se solicito", "Estado"
            }
        ));
        tablaSolicitudes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaSolicitudesMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tablaSolicitudes);
        if (tablaSolicitudes.getColumnModel().getColumnCount() > 0) {
            tablaSolicitudes.getColumnModel().getColumn(1).setHeaderValue("Usuario que solicito");
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1321, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel8);
        jPanel8.setBounds(20, 20, 1340, 330);

        tablaStockMin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Clave", "Producto", "Descripción", "Observaciones", "Stock", "Estado"
            }
        ));
        tablaStockMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaStockMinMouseReleased(evt);
            }
        });
        jScrollPane12.setViewportView(tablaStockMin);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 1320, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel10);
        jPanel10.setBounds(20, 370, 1340, 330);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        jPanel7.add(jLabel9);
        jLabel9.setBounds(0, 0, 1367, 769);

        javax.swing.GroupLayout solicitudesLayout = new javax.swing.GroupLayout(solicitudes);
        solicitudes.setLayout(solicitudesLayout);
        solicitudesLayout.setHorizontalGroup(
            solicitudesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 1417, Short.MAX_VALUE)
        );
        solicitudesLayout.setVerticalGroup(
            solicitudesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("Solicitudes", new javax.swing.ImageIcon(getClass().getResource("/Iconos/vehiculos.png")), solicitudes); // NOI18N

        empleado.setComponentPopupMenu(MenuSolicitudesP);

        jPanel9.setLayout(null);

        lblNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNombre.setText("Nombre");

        lblCargo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCargo.setText("Cargo");

        lblArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblArea.setText("Área");

        lblDomicilio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDomicilio.setText("Domicilio");

        lblTelefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTelefono.setText("Telefono");

        lblCurp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCurp.setText("CURP");

        lblRfc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRfc.setText("RFC");

        lblMunicipio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMunicipio.setText("Municipio");

        lblLocalidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLocalidad.setText("Localidad");

        lblCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCodigo.setText("C.P.");

        lblFecha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFecha.setText("Fecha de nacimiento");

        btnEditar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/modificar.png"))); // NOI18N
        btnEditar.setText(" Editar");
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setText("Información Personal");

        btnEditar1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEditar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/modificar.png"))); // NOI18N
        btnEditar1.setText("Cambiar contraseña");
        btnEditar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditar1ActionPerformed(evt);
            }
        });

        tablaResguardo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Fecha de ingreso", "Observaciones"
            }
        ));
        jScrollPane7.setViewportView(tablaResguardo);

        btnAñadirResguardo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAñadirResguardo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/agregar.png"))); // NOI18N
        btnAñadirResguardo.setText(" Añadir");
        btnAñadirResguardo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAñadirResguardo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirResguardoActionPerformed(evt);
            }
        });

        tablaSolicitudesPersonal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Solicitud", "Producto", "Motivo", "Fecha que se solicito", "Estado"
            }
        ));
        tablaSolicitudesPersonal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaSolicitudesPersonalMouseReleased(evt);
            }
        });
        jScrollPane8.setViewportView(tablaSolicitudesPersonal);

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setText("Solicitudes realizadas");

        tablaPermisosPersonales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Modulo", "Alta", "Baja", "Actualizar", "Consulta"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaPermisosPersonales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaPermisosPersonalesMouseReleased(evt);
            }
        });
        jScrollPane9.setViewportView(tablaPermisosPersonales);

        tablaAsignacionPersonal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Clave", "Producto", "Descripción", "Observaciones", "Cantidad"
            }
        ));
        tablaAsignacionPersonal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaAsignacionPersonalMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tablaAsignacionPersonal);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setText("Resguardo Personal");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setText("Permisos");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setText("Asignacion personal de inventario");

        GrupoTipoInventario.add(radioEquipo);
        radioEquipo.setText("Equipo de computo");
        radioEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioEquipoActionPerformed(evt);
            }
        });

        GrupoTipoInventario.add(radioGranel);
        radioGranel.setSelected(true);
        radioGranel.setText("Inventario granel");
        radioGranel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioGranelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEmpleadoLayout = new javax.swing.GroupLayout(panelEmpleado);
        panelEmpleado.setLayout(panelEmpleadoLayout);
        panelEmpleadoLayout.setHorizontalGroup(
            panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmpleadoLayout.createSequentialGroup()
                .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEmpleadoLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelEmpleadoLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEditar1))
                            .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblLocalidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblMunicipio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblRfc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblCurp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblCargo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))))
                    .addGroup(panelEmpleadoLayout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel22))
                    .addGroup(panelEmpleadoLayout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(btnAñadirResguardo, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEmpleadoLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
                            .addComponent(jScrollPane8))
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEmpleadoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel23)
                        .addGap(337, 337, 337))))
            .addGroup(panelEmpleadoLayout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addGap(386, 386, 386))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEmpleadoLayout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEmpleadoLayout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jLabel27)
                .addGap(18, 18, 18)
                .addComponent(radioGranel)
                .addGap(18, 18, 18)
                .addComponent(radioEquipo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelEmpleadoLayout.setVerticalGroup(
            panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmpleadoLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEmpleadoLayout.createSequentialGroup()
                        .addComponent(lblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCargo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblArea)
                        .addGap(9, 9, 9)
                        .addComponent(lblDomicilio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTelefono)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFecha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCurp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRfc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMunicipio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLocalidad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditar)
                            .addComponent(btnEditar1)))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelEmpleadoLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAñadirResguardo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radioEquipo)
                        .addComponent(radioGranel))
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ScrollEmpleado.setViewportView(panelEmpleado);

        jPanel9.add(ScrollEmpleado);
        ScrollEmpleado.setBounds(10, 20, 1350, 740);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        jPanel9.add(jLabel10);
        jLabel10.setBounds(0, 0, 1367, 769);

        javax.swing.GroupLayout empleadoLayout = new javax.swing.GroupLayout(empleado);
        empleado.setLayout(empleadoLayout);
        empleadoLayout.setHorizontalGroup(
            empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 1417, Short.MAX_VALUE)
        );
        empleadoLayout.setVerticalGroup(
            empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("Empleado", new javax.swing.ImageIcon(getClass().getResource("/Iconos/vehiculos.png")), empleado); // NOI18N

        configuracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                configuracionMouseClicked(evt);
            }
        });

        jPanel4.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Herramienta de configuración de red", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        tablaBD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PC", "IP", "Estado", "Permiso CRUD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaBD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaBDMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaBD);

        jButton4.setText(" Quitar ");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setText("Agregar");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tablaIP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PC", "IP", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaIP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaIPMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaIP);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setText(".");

        jLabel17.setText(".");

        jLabel19.setText(".");

        campoip3.setModel(new javax.swing.SpinnerNumberModel(1, 1, 255, 1));

        campoip4.setModel(new javax.swing.SpinnerNumberModel(20, 1, 255, 1));

        campoip2.setModel(new javax.swing.SpinnerNumberModel(168, 1, 255, 1));

        campoip1.setModel(new javax.swing.SpinnerNumberModel(192, 1, 255, 1));

        jButton1.setText(" Escanear todo");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(" Añadir subred");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(campoip1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoip2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoip3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoip4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(116, 116, 116))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoip1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoip2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(campoip3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoip4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 87, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.add(jPanel2);
        jPanel2.setBounds(10, 90, 1340, 290);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/baner config.png"))); // NOI18N
        jPanel4.add(jLabel7);
        jLabel7.setBounds(10, 10, 1350, 80);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        jPanel4.add(jLabel3);
        jLabel3.setBounds(0, 0, 1367, 769);

        javax.swing.GroupLayout configuracionLayout = new javax.swing.GroupLayout(configuracion);
        configuracion.setLayout(configuracionLayout);
        configuracionLayout.setHorizontalGroup(
            configuracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1417, Short.MAX_VALUE)
        );
        configuracionLayout.setVerticalGroup(
            configuracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("  Configuración", new javax.swing.ImageIcon(getClass().getResource("/Iconos/configuracion.png")), configuracion); // NOI18N

        manejo_inventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manejo_inventarioMouseClicked(evt);
            }
        });

        jPanel16.setLayout(null);

        pn_contenedor_ventanas1.setLayout(new java.awt.CardLayout());

        lb_objetos_asignables2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_objetos_asignables2.setText("Objetos Asignables");

        tablaMAsignados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Clave", "Nombre", "Descripción", "Almacén", "Observaciones", "Cantidad"
            }
        ));
        tablaMAsignados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaMAsignadosMouseReleased(evt);
            }
        });
        jScrollPane31.setViewportView(tablaMAsignados);

        lb_objetos_asignables3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_objetos_asignables3.setText("Objetos Asignados");

        tablaMInventarioA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaMInventarioA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMInventarioAMouseClicked(evt);
            }
        });
        jScrollPane32.setViewportView(tablaMInventarioA);

        btn_generar_vale3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btn_generar_vale3.setText("Generar Vale");
        btn_generar_vale3.setEnabled(false);
        btn_generar_vale3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generar_vale3ActionPerformed(evt);
            }
        });

        btn_cancelar2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btn_cancelar2.setText("Cancelar");
        btn_cancelar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelar2ActionPerformed(evt);
            }
        });

        bt_tipo_inventario_asignable.add(rb_inventario_normal1);
        rb_inventario_normal1.setText("Normal");
        rb_inventario_normal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_inventario_normal1ActionPerformed(evt);
            }
        });

        bt_tipo_inventario_asignable.add(rb_inventario_granel1);
        rb_inventario_granel1.setText("Granel");
        rb_inventario_granel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rb_inventario_granel1MouseClicked(evt);
            }
        });
        rb_inventario_granel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_inventario_granel1ActionPerformed(evt);
            }
        });

        comboEmpleado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEmpleadoActionPerformed(evt);
            }
        });

        lb_objetos_asignables4.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_objetos_asignables4.setText("Responsable:");

        javax.swing.GroupLayout pn_asignacion_inventario1Layout = new javax.swing.GroupLayout(pn_asignacion_inventario1);
        pn_asignacion_inventario1.setLayout(pn_asignacion_inventario1Layout);
        pn_asignacion_inventario1Layout.setHorizontalGroup(
            pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_asignacion_inventario1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_asignacion_inventario1Layout.createSequentialGroup()
                        .addComponent(lb_objetos_asignables4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pn_asignacion_inventario1Layout.createSequentialGroup()
                            .addComponent(btn_cancelar2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btn_generar_vale3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pn_asignacion_inventario1Layout.createSequentialGroup()
                            .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pn_asignacion_inventario1Layout.createSequentialGroup()
                                    .addComponent(lb_objetos_asignables2)
                                    .addGap(8, 8, 8)
                                    .addComponent(rb_inventario_normal1)
                                    .addGap(10, 10, 10)
                                    .addComponent(rb_inventario_granel1))
                                .addComponent(jScrollPane32, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(29, 29, 29)
                            .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lb_objetos_asignables3)))))
                .addContainerGap(1174, Short.MAX_VALUE))
        );
        pn_asignacion_inventario1Layout.setVerticalGroup(
            pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_asignacion_inventario1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_objetos_asignables4))
                .addGap(18, 18, 18)
                .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_objetos_asignables2)
                        .addComponent(rb_inventario_normal1)
                        .addComponent(rb_inventario_granel1))
                    .addComponent(lb_objetos_asignables3))
                .addGap(10, 10, 10)
                .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane31, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                    .addComponent(jScrollPane32, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_generar_vale3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cancelar2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sp_asignacion_inventario1.setViewportView(pn_asignacion_inventario1);

        pn_contenedor_ventanas1.add(sp_asignacion_inventario1, "c_s_asignacion");

        lb_empleado3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_empleado3.setText("Empleado:");

        lb_objetos_asignados1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_objetos_asignados1.setText("Objetos Asignados");

        tablaRecoleccion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vale", "Clave", "Producto", "Descripción", "Observaciones", "Cantidad"
            }
        ));
        tablaRecoleccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaRecoleccionMouseReleased(evt);
            }
        });
        jScrollPane34.setViewportView(tablaRecoleccion);

        lb_objetos_entregados1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_objetos_entregados1.setText("Objetos Entregados");

        tablaObjetosEntregados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vale", "Clave", "Producto", "Descripción", "Observaciones", "Cantidad"
            }
        ));
        tablaObjetosEntregados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaObjetosEntregadosMouseReleased(evt);
            }
        });
        jScrollPane35.setViewportView(tablaObjetosEntregados);

        btnGenerarValeR.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnGenerarValeR.setText("Generar Vale");
        btnGenerarValeR.setEnabled(false);
        btnGenerarValeR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarValeRActionPerformed(evt);
            }
        });

        btn_cancelar3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btn_cancelar3.setText("Cancelar");
        btn_cancelar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelar3ActionPerformed(evt);
            }
        });

        comboEmpleadoR.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboEmpleadoR.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboEmpleadoR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEmpleadoRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_recoleccion_inventario1Layout = new javax.swing.GroupLayout(pn_recoleccion_inventario1);
        pn_recoleccion_inventario1.setLayout(pn_recoleccion_inventario1Layout);
        pn_recoleccion_inventario1Layout.setHorizontalGroup(
            pn_recoleccion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_recoleccion_inventario1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pn_recoleccion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_recoleccion_inventario1Layout.createSequentialGroup()
                        .addComponent(lb_empleado3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboEmpleadoR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_recoleccion_inventario1Layout.createSequentialGroup()
                        .addGroup(pn_recoleccion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_objetos_asignados1)
                            .addComponent(jScrollPane34, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addGroup(pn_recoleccion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_objetos_entregados1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane35, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_recoleccion_inventario1Layout.createSequentialGroup()
                                .addComponent(btn_cancelar3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGenerarValeR)))
                        .addGap(35, 35, 35))))
        );
        pn_recoleccion_inventario1Layout.setVerticalGroup(
            pn_recoleccion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_recoleccion_inventario1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(pn_recoleccion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_empleado3)
                    .addComponent(comboEmpleadoR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pn_recoleccion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pn_recoleccion_inventario1Layout.createSequentialGroup()
                        .addComponent(lb_objetos_asignados1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane34, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_recoleccion_inventario1Layout.createSequentialGroup()
                        .addComponent(lb_objetos_entregados1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane35, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(pn_recoleccion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGenerarValeR)
                    .addComponent(btn_cancelar3))
                .addContainerGap(183, Short.MAX_VALUE))
        );

        sp_recoleccion_inventario1.setViewportView(pn_recoleccion_inventario1);

        pn_contenedor_ventanas1.add(sp_recoleccion_inventario1, "c_s_recoleccion");

        jPanel16.add(pn_contenedor_ventanas1);
        pn_contenedor_ventanas1.setBounds(10, 120, 1350, 660);

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/baner config.png"))); // NOI18N
        jPanel16.add(jLabel11);
        jLabel11.setBounds(10, 10, 1350, 80);

        bg_manejo_inventario.add(rb_asignacion1);
        rb_asignacion1.setSelected(true);
        rb_asignacion1.setText("Asignación");
        rb_asignacion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_asignacion1ActionPerformed(evt);
            }
        });

        bg_manejo_inventario.add(rb_recoleccion1);
        rb_recoleccion1.setText("Recolección");
        rb_recoleccion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_recoleccion1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_acciones1Layout = new javax.swing.GroupLayout(pn_acciones1);
        pn_acciones1.setLayout(pn_acciones1Layout);
        pn_acciones1Layout.setHorizontalGroup(
            pn_acciones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_acciones1Layout.createSequentialGroup()
                .addContainerGap(333, Short.MAX_VALUE)
                .addComponent(rb_asignacion1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rb_recoleccion1)
                .addGap(277, 277, 277))
        );
        pn_acciones1Layout.setVerticalGroup(
            pn_acciones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_acciones1Layout.createSequentialGroup()
                .addGroup(pn_acciones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_asignacion1)
                    .addComponent(rb_recoleccion1))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel16.add(pn_acciones1);
        pn_acciones1.setBounds(230, 90, 770, 20);

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        jPanel16.add(jLabel16);
        jLabel16.setBounds(0, 0, 1367, 769);

        javax.swing.GroupLayout manejo_inventarioLayout = new javax.swing.GroupLayout(manejo_inventario);
        manejo_inventario.setLayout(manejo_inventarioLayout);
        manejo_inventarioLayout.setHorizontalGroup(
            manejo_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 1417, Short.MAX_VALUE)
        );
        manejo_inventarioLayout.setVerticalGroup(
            manejo_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("Manejador de inventario", new javax.swing.ImageIcon(getClass().getResource("/Iconos/configuracion.png")), manejo_inventario); // NOI18N

        jMenu1.setText("Archivo");

        itemAnterior.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_LEFT, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        itemAnterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/atras.png"))); // NOI18N
        itemAnterior.setText("Anterior");
        itemAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAnteriorActionPerformed(evt);
            }
        });
        jMenu1.add(itemAnterior);

        itemSiguiente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_RIGHT, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        itemSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/adelante.png"))); // NOI18N
        itemSiguiente.setText("Siguiente");
        itemSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSiguienteActionPerformed(evt);
            }
        });
        jMenu1.add(itemSiguiente);

        mi_viaticos.setText("Viaticos");
        mi_viaticos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_viaticosActionPerformed(evt);
            }
        });
        jMenu1.add(mi_viaticos);
        jMenu1.add(jSeparator1);

        itemSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        itemSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/if_Exit_728935.png"))); // NOI18N
        itemSalir.setText("Salir");
        itemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSalirActionPerformed(evt);
            }
        });
        jMenu1.add(itemSalir);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        menuOpciones.setText("Permisos");

        menuPermisos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/permisos.png"))); // NOI18N
        menuPermisos.setText("Permisos puestos");
        menuPermisos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPermisosActionPerformed(evt);
            }
        });
        menuOpciones.add(menuPermisos);

        menuPuestoArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/areas.png"))); // NOI18N
        menuPuestoArea.setText("Puestos & Áreas");
        menuPuestoArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPuestoAreaActionPerformed(evt);
            }
        });
        menuOpciones.add(menuPuestoArea);

        MenuSolicitud.setText("Permisos solicitud/vale");
        MenuSolicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuSolicitudActionPerformed(evt);
            }
        });
        menuOpciones.add(MenuSolicitud);

        jMenuBar1.add(menuOpciones);

        menuAsignar.setText("Asignación equipos");

        Asignar.setText("Asignar a conjunto de equipos...");
        Asignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AsignarActionPerformed(evt);
            }
        });
        menuAsignar.add(Asignar);

        Equipos.setText("Conjuntos de equipos de computo");
        Equipos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EquiposActionPerformed(evt);
            }
        });
        menuAsignar.add(Equipos);

        jMenuBar1.add(menuAsignar);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPrincipal)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPrincipal)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
         // TODO add your handling code here:
        Object[] botones = {"Confirmar","Cerrar Sesión","Cancelar"};
        int opcion = JOptionPane.showOptionDialog(this,"¿Salir del Sistema?", "Confirmación",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE  , null, botones, botones[0]);
        
        if(opcion == 0){

            System.exit(0);
        }else if(opcion == 1){
            //Cerrar sesion
            this.dispose();
            Login ob = new Login();
            ob.setVisible(true);   
        }
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        pestañas = 0;
        //COMBOBOX
        
        //COMBOINVENTARIO
        comboInventario.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        comboInventario.addItem("Inventario");
        comboInventario.addItem("Inventario Granel");
        
        //COMBOFILTROUSUARIO
        comboFiltroUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        comboFiltroUsuario.addItem("Usuario");
        comboFiltroUsuario.addItem("Nombre");
        comboFiltroUsuario.addItem("Apellido P");
        comboFiltroUsuario.addItem("Apellido M");
        comboFiltroUsuario.addItem("Cargo");
        comboFiltroUsuario.addItem("Área");
        
        //COMBOFILTROVEHICULOS
        comboFiltroVehiculos.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        comboFiltroVehiculos.addItem("Marca");
        comboFiltroVehiculos.addItem("Linea");
        comboFiltroVehiculos.addItem("Año");
        comboFiltroVehiculos.addItem("Color");
        comboFiltroVehiculos.addItem("Matricula");
        
        //Llenado de tablas
        if(manager_permisos.consulta_user(Username)){
            tablaUsuarios.setModel(manager_users.getEmpleados(Username));
        }
        if(manager_permisos.consulta_vehiculos(Username)){
            tablaVehiculos.setModel(managerVehiculos.getVehiculos());
        }
        
        /*PESTAÑA DE SOLICITUDES*/
        
        //Buscamos si el usuario puede ver solicitudes o no.
        /*
            14 -> todos los tipos de solicitud
            13 -> baja, donación y comodato
            12 -> baja, donación y reemplazo
            11 -> baja, comodato y reemplazo
            10 -> baja y donación 
            9 -> baja y comodato
            8 -> baja y reemplazo
            7 -> reemplazo y comodato
            6 -> reemplazo y donación
            5 -> comodato y donación
            4 -> baja
            3 -> comodato
            2 -> donación
            1 -> reemplazo
            0 -> ningun permiso
        */
        
        if(manager_permisos.verTablaSolicitudes(Username) == 0){
                tabbedPrincipal.removeTabAt(3);//Eliminamos la pestaña
                pestañas++;
        }else{
            if(!(manager_permisos.esPresidencia(Username))){
                
                tablaSolicitudes.setModel(manager_solicitud.tabla_Solicitudes(manager_permisos.verTablaSolicitudes(Username)));
                int solicitud = manager_complemento.cantidadSolicitudes(manager_permisos.verTablaSolicitudes(Username));
                if(solicitud > 0){
                    tabbedPrincipal.setTitleAt(3, "Solicitudes ("+solicitud+")");//Le damos el nombre a esa pestaña
                }//if cantidad
                
            }//if esPresidencia
            else{
                tabbedPrincipal.setTitleAt(3, "Pendientes");//Le damos el nombre a esa pestaña
                tablaSolicitudes.setModel(manager_solicitud.tabla_Pendientes());
                int pendientes = manager_complemento.cantidadPendientes();
                if(pendientes > 0){
                    tabbedPrincipal.setTitleAt(3, "Pendientes ("+pendientes+")");//Le damos el nombre a esa pestaña
                }//if cantidad
                
            }
        }
        
        tablaStockMin.setModel(manejador_inventario.getInventarioStockMin());
        
        /*PESTAÑA DE EMPLEADO*/
        tabbedPrincipal.setTitleAt(4-pestañas, Username.toUpperCase());//Le damos el nombre a esa pestaña
        tablaResguardo.setModel(manager_complemento.getResguardoPersonal(Username));
        tablaSolicitudesPersonal.setModel(manager_solicitud.tabla_Solicitudes_Personales(Username));
        tablaPermisosPersonales.setModel(manager_permisos.getPermisos(tablaPermisosPersonales,Username));
        tablaAsignacionPersonal.setModel(manejador_inventario.getInventarioEmpleadoAsignacionesPersonalesG(Username));
        
        /*PESTAÑA CONFIGURACIÓN*/
        if(!(manager_permisos.esSuperUsuario(Username))){
            tabbedPrincipal.removeTabAt(5-pestañas);//Eliminamos la pestaña
            pestañas++;
        }
        
        comboEmpleado.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        comboEmpleado.addItem("Seleccione al empleado...");
        
        comboEmpleadoR.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        comboEmpleadoR.addItem("Seleccione al empleado...");
        
        /*PESTAÑA DE MANEJADOR INVENTARIO*/
        if(manager_permisos.permisoPorVale(Username, "Vale de asignación")){
            //Asignación
            tablaMInventarioA.setModel(manejador_inventario.getInventario());
            manager_users.getNombresEmpleados(comboEmpleado);
        }else{
            //Bloqueamos el uso de la asignacion
            comboEmpleado.setEnabled(false);
            rb_inventario_normal1.setEnabled(false);
            rb_inventario_granel1.setEnabled(false);
            tablaMInventarioA.setEnabled(false);
            btn_generar_vale3.setEnabled(false);
            btn_cancelar2.setEnabled(false);
        }
        
        if(manager_permisos.permisoPorVale(Username, "Vale de recolección")){
            //Recolección
            manejador_inventario.getEmpleadosAsignacion(comboEmpleadoR);
        }else{
            //Bloqueamos el uso de la asignacion
            comboEmpleadoR.setEnabled(false);
            tablaRecoleccion.setEnabled(false);
            btnGenerarValeR.setEnabled(false);
            btn_cancelar3.setEnabled(false);
        }
        
        //Si no tiene ningun permiso de vale entonces le borramos la pestaña
        if(!(manager_permisos.permisoPorVale(Username, "Vale de asignación") || manager_permisos.permisoPorVale(Username, "Vale de recolección"))){
            tabbedPrincipal.removeTabAt(6-pestañas);//Eliminamos la pestaña
        }
        
    }//GEN-LAST:event_formWindowOpened
    
    private void infoEmpleado(){
        String cadena = manager_users.infoEmpleado(Username);
        String separador [] = cadena.split(",");
        lblNombre.setText("Nombre: "+separador[0]+" "+separador[1]+" "+separador[2]);
        lblDomicilio.setText("Domicilio: "+separador[4]+" "+separador[3]);
        lblTelefono.setText("Telefono: "+separador[5]);
        lblCodigo.setText("C.P.: "+separador[6]);
        lblFecha.setText("Fecha de nacimiento: "+separador[7]);
        lblCurp.setText("CURP: "+separador[8]);
        lblRfc.setText("RFC: "+separador[9]);
        lblMunicipio.setText("Municipio: "+separador[10]);
        lblLocalidad.setText("Localidad: "+separador[11]);
        lblCargo.setText("Puesto: "+separador[12]);
        lblArea.setText("Área: "+separador[13]);
    }
    
    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        // TODO add your handling code here:
        
        if(manager_permisos.baja_user(Username)){
            //Obtenemos la fila y con dicha fila obtenemos el usuario
            int fila = tablaUsuarios.getSelectedRow();
            usuario = tablaUsuarios.getValueAt(fila, 0).toString();
            //Creamos un cuadro de dialogo para que confirme la eliminación del usuario o la cancele
            Object[] botones = {"Confirmar","Cancelar"};
            int opcion = JOptionPane.showOptionDialog(this,"¿Eliminar al usuario "+usuario+"?", "Confirmación",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE  , null, botones, botones[0]);

            //Acepta eliminar al usuario
            if(opcion == 0){

                if(manager_users.eliminarEmpleado(usuario)){
                    JOptionPane.showMessageDialog(null, "El usuario a sido eliminado exisitosamente.");
                    tablaUsuarios.setModel(manager_users.getEmpleados(Username));
                }//if(eliminarEmpleado())
                else{
                        JOptionPane.showMessageDialog(null, "Verificar con el distribuidor.");
                }
            }//if(opcion == 0)
        }else{
            JOptionPane.showMessageDialog(null, "Usted no cuenta con el permiso para eliminar usuarios.");
        }
        
        
    }//GEN-LAST:event_EliminarActionPerformed

    private void ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarActionPerformed
        // TODO add your handling code here:
        /*
          banderaUser estará siempre en 1 cuando se quiera añadir un empleado o mientras no se use
          por eso es necesario cambiarlo a dos para saber que la ventana addEmpleados se utilizarára
          para actualizar.
        */
        if(manager_permisos.update_user(Username)){
        banderaUser = 2;
        try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                // If Nimbus is not available, you can set the GUI to another look and feel.
            }
        int fila = tablaUsuarios.getSelectedRow();
        UserUpdate = tablaUsuarios.getValueAt(fila, 0).toString();
        System.out.println(UserUpdate);
            //Llamamos el forumulario para actuaizar un empleado
            addEmpleados ob = new addEmpleados(this, true);
            ob.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Usted no cuenta con el permiso para actualizar usuarios.");
        }
    }//GEN-LAST:event_ActualizarActionPerformed

    private void PermisosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PermisosActionPerformed
        // TODO add your handling code here:
        //Obtenemos el usuario seleccionado
        int fila = tablaUsuarios.getSelectedRow();
        usuario = tablaUsuarios.getValueAt(fila, 0).toString();
        
        
        //Llamamos el formulario de los permisos
        if(manager_permisos.consulta_permisos(Username) || manager_permisos.update_permisos(Username)){
            if(manager_permisos.update_permisos(Username)){
                Ventana_permisos ob = new Ventana_permisos(this, true);
                ob.setVisible(true);
            }else{
                Ventana_permisos_consulta ob = new Ventana_permisos_consulta(this, true);
                ob.setVisible(true);
            }
        }else{
            JOptionPane.showMessageDialog(null, "No tiene permisos para visualizar los permisos o actualizarlos");
        }
    }//GEN-LAST:event_PermisosActionPerformed

    private void menuPermisosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPermisosActionPerformed
        // TODO add your handling code here:
        if((manager_permisos.alta_permisos(Username) && manager_permisos.baja_permisos(Username)) || manager_permisos.consulta_permisos(Username)){
            if(manager_permisos.alta_permisos(Username) && manager_permisos.baja_permisos(Username)){
                Ventana_permisos_puesto ob = new Ventana_permisos_puesto(this, true);
                ob.setVisible(true);
            }else{
                Ventana_permisos_puesto_consulta ob = new Ventana_permisos_puesto_consulta(this, true);
                ob.setVisible(true);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Usted no tiene acceso a los permisos estaticos de los puestos.");
        }
    }//GEN-LAST:event_menuPermisosActionPerformed

    private void menuPuestoAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPuestoAreaActionPerformed
        // TODO add your handling code here:
        Ventana_Puestos_Area ob = new Ventana_Puestos_Area(this, true);
        ob.setVisible(true);
    }//GEN-LAST:event_menuPuestoAreaActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
        tablaBD.setModel(manajerMySQL.getPermisosMySQL());
        infoEmpleado();
        
        if(manager_permisos.verTablaSolicitudes(Username) > 0){
            
            if(!(manager_permisos.esPresidencia(Username))){
                
                tablaSolicitudes.setModel(manager_solicitud.tabla_Solicitudes(manager_permisos.verTablaSolicitudes(Username)));
                int solicitud = manager_complemento.cantidadSolicitudes(manager_permisos.verTablaSolicitudes(Username));
                if(solicitud > 0){
                    tabbedPrincipal.setTitleAt(3, "Solicitudes ("+solicitud+")");//Le damos el nombre a esa pestaña
                }//if cantidad
                
            }//if esPresidencia
            else{
                
                tablaSolicitudes.setModel(manager_solicitud.tabla_Pendientes());
                int pendientes = manager_complemento.cantidadPendientes();
                if(pendientes > 0){
                    tabbedPrincipal.setTitleAt(3, "Pendientes ("+pendientes+")");//Le damos el nombre a esa pestaña
                }//if cantidad
                
            }
            
        }//if verTablaSolicitudes
        
    }//GEN-LAST:event_formWindowActivated
    
    private void itemAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAnteriorActionPerformed
        // TODO add your handling code here:
        if (tabbedPrincipal.getSelectedIndex() == 0) {
            tabbedPrincipal.setSelectedIndex(3);
        }else{
            tabbedPrincipal.setSelectedIndex(tabbedPrincipal.getSelectedIndex()-1);
        }
    }//GEN-LAST:event_itemAnteriorActionPerformed

    private void itemSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSiguienteActionPerformed
        // TODO add your handling code here:
        if (tabbedPrincipal.getSelectedIndex() == 3) {
            tabbedPrincipal.setSelectedIndex(0);
        }else{
            tabbedPrincipal.setSelectedIndex(tabbedPrincipal.getSelectedIndex()+1);
        }
    }//GEN-LAST:event_itemSiguienteActionPerformed

    private void itemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSalirActionPerformed
        // TODO add your handling code here:
        Object[] botones = {"Confirmar","Cancelar"};
        int opcion = JOptionPane.showOptionDialog(this,"¿Salir del Sistema?", "Confirmación",
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, botones, botones[0]);

        if(opcion == 0){
            System.exit(0);
        }else if(opcion == 1){
            //Cerrar sesion
        }
    }//GEN-LAST:event_itemSalirActionPerformed

    private void MenuSolicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuSolicitudActionPerformed
        // TODO add your handling code here:
        Ventana_permisosSolicitud ob = new Ventana_permisosSolicitud(this, true);
        
        if((manager_permisos.alta_permisos(Username) && manager_permisos.baja_permisos(Username)) || manager_permisos.consulta_permisos(Username)){
            if(manager_permisos.alta_permisos(Username) && manager_permisos.baja_permisos(Username)){
                
                banderaPermisosSolicitud = 1;
                ob.setVisible(true);
            }else{
                
                banderaPermisosSolicitud = 2;
                ob.setVisible(true);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Usted no tiene acceso a los permisos de las solicitudes.");
        }
    }//GEN-LAST:event_MenuSolicitudActionPerformed

    private void ActualizarInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarInfoActionPerformed
        // TODO add your handling code here:
        /*
            14 -> todos los tipos de solicitud
            13 -> baja, donación y comodato
            12 -> baja, donación y reemplazo
            11 -> baja, comodato y reemplazo
            10 -> baja y donación 
            9 -> baja y comodato
            8 -> baja y reemplazo
            7 -> reemplazo y comodato
            6 -> reemplazo y donación
            5 -> comodato y donación
            4 -> baja
            3 -> comodato
            2 -> donación
            1 -> reemplazo
            0 -> ningun permiso
        */
        
        if(manager_permisos.verTablaSolicitudes(Username) == 0){
                tabbedPrincipal.removeTabAt(3);//Eliminamos la pestaña
                pestañas++;
        }else{
            if(!(manager_permisos.esPresidencia(Username))){
                
                tablaSolicitudes.setModel(manager_solicitud.tabla_Solicitudes(manager_permisos.verTablaSolicitudes(Username)));
                int solicitud = manager_complemento.cantidadSolicitudes(manager_permisos.verTablaSolicitudes(Username));
                if(solicitud > 0){
                    tabbedPrincipal.setTitleAt(3, "Solicitudes ("+solicitud+")");//Le damos el nombre a esa pestaña
                }//if cantidad
                
            }//if esPresidencia
            else{
                tabbedPrincipal.setTitleAt(3, "Pendientes");//Le damos el nombre a esa pestaña
                tablaSolicitudes.setModel(manager_solicitud.tabla_Pendientes());
                int pendientes = manager_complemento.cantidadPendientes();
                if(pendientes > 0){
                    tabbedPrincipal.setTitleAt(3, "Pendientes ("+pendientes+")");//Le damos el nombre a esa pestaña
                }//if cantidad
                
            }
        }
    }//GEN-LAST:event_ActualizarInfoActionPerformed
    public void metodoVehiculos(){
        int fila = tablaVehiculos.getSelectedRow();
        Vector vVehiculos = managerVehiculos.infoVehiculos(tablaVehiculos.getValueAt(fila, 4).toString());
        // marca,linea,clase,kilometraje,modelo,color,motor,matricula,observaciones,estado  
        //Cantidad
        String temporal[] = vVehiculos.get(0).toString().split(",");
        etiquetaMarca.setText(temporal[0]);
        etiquetaLinea.setText(temporal[1]);
        etiquetaKilometraje.setText(temporal[3]+" km");
        etiquetaAño.setText(temporal[4]);
        campoObservaciones.setText(temporal[8]);
        
        etiquetaEstado.setText(temporal[9]);
        
        try {
            //System.err.println(""+tablaVehiculos.getValueAt(fila, 4).toString());
            cargarImagen(tablaVehiculos.getValueAt(fila, 4).toString());
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        etiquetaMarca.setVisible(true);
        etiquetaLinea.setVisible(true);
        etiquetaKilometraje.setVisible(true);
        etiquetaAño.setVisible(true);
        campoObservaciones.setVisible(true);
        zoom.setVisible(true);
    }
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
         // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed
        
    private void AsignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AsignarActionPerformed
         // TODO add your handling code here:
        if(manager_permisos.alta_asignacion(Username)){
            Ventana_asignar_EquipoComputo ob = new Ventana_asignar_EquipoComputo(this,true);
            ob.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "No cuentas con los permisos para dar de alta un conjunto de equipos de computo.");
        }
    }//GEN-LAST:event_AsignarActionPerformed

    private void EquiposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EquiposActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.consulta_asignacion(Username)){
            Ventana_EquipoComputo ob = new Ventana_EquipoComputo(this,true);
            ob.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "No cuentas con los permisos para consultar los conjuntos de equipos de computo");
        }
    }//GEN-LAST:event_EquiposActionPerformed

    private void ActualizarVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarVActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.update_vehiculos(Username)){
            
        }else{
            JOptionPane.showMessageDialog(null, "No tiene permisos para actualizar la información del vehiculo.");
        }
        
    }//GEN-LAST:event_ActualizarVActionPerformed

    private void AtenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtenderActionPerformed
        // TODO add your handling code here:
        
        //Obtenemos la fila seleccionada
        int fila = tablaSolicitudes.getSelectedRow();
        //Obtenemos el tipo de la solicitud
        String solicitud = tablaSolicitudes.getValueAt(fila, 1).toString();
        //Obtenemos el id de la solicitud
        idPendiente = Integer.parseInt(tablaSolicitudes.getValueAt(fila, 0).toString());
        //Le quitamos la palabra "Solicitud " y nos quedamos con el estado pendiente al que va a cambiar
        estadoPendiente = solicitud.substring(10, solicitud.length());
        estadoSolicitud = tablaSolicitudes.getValueAt(fila, 6).toString();
        empleadoSolicitud = tablaSolicitudes.getValueAt(fila, 2).toString();
        //Abrimos la ventana para atender la solicitud y actualizar la foto del producto solicitado.
        ventana_AtenderSolicitud ob = new ventana_AtenderSolicitud(this,true);
        ob.setVisible(true);
        
    }//GEN-LAST:event_AtenderActionPerformed

    private void mi_viaticosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_viaticosActionPerformed
 
        PrincipalS a= new PrincipalS();
        a.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_mi_viaticosActionPerformed

    private void ActualizarPendientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarPendientesActionPerformed
        // TODO add your handling code here:
        /*
            14 -> todos los tipos de solicitud
            13 -> baja, donación y comodato
            12 -> baja, donación y reemplazo
            11 -> baja, comodato y reemplazo
            10 -> baja y donación 
            9 -> baja y comodato
            8 -> baja y reemplazo
            7 -> reemplazo y comodato
            6 -> reemplazo y donación
            5 -> comodato y donación
            4 -> baja
            3 -> comodato
            2 -> donación
            1 -> reemplazo
            0 -> ningun permiso
        */
        
        if(manager_permisos.verTablaSolicitudes(Username) == 0){
                tabbedPrincipal.removeTabAt(3);//Eliminamos la pestaña
                pestañas++;
        }else{
            if(!(manager_permisos.esPresidencia(Username))){
                
                tablaSolicitudes.setModel(manager_solicitud.tabla_Solicitudes(manager_permisos.verTablaSolicitudes(Username)));
                int solicitud = manager_complemento.cantidadSolicitudes(manager_permisos.verTablaSolicitudes(Username));
                if(solicitud > 0){
                    tabbedPrincipal.setTitleAt(3, "Solicitudes ("+solicitud+")");//Le damos el nombre a esa pestaña
                }//if cantidad
                
            }//if esPresidencia
            else{
                tabbedPrincipal.setTitleAt(3, "Pendientes");//Le damos el nombre a esa pestaña
                tablaSolicitudes.setModel(manager_solicitud.tabla_Pendientes());
                int pendientes = manager_complemento.cantidadPendientes();
                if(pendientes > 0){
                    tabbedPrincipal.setTitleAt(3, "Pendientes ("+pendientes+")");//Le damos el nombre a esa pestaña
                }//if cantidad
                
            }
        }        
    }//GEN-LAST:event_ActualizarPendientesActionPerformed

    private void AutorizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AutorizarActionPerformed
        // TODO add your handling code here:
        //Obtenemos la fila seleccionada
        int fila = tablaSolicitudes.getSelectedRow();
        //Obtenemos el tipo de la solicitud
        String solicitud = tablaSolicitudes.getValueAt(fila, 1).toString();
        //Obtenemos el id de la solicitud
        int idSolicitud = Integer.parseInt(tablaSolicitudes.getValueAt(fila, 0).toString());
        //Le quitamos la palabra "Solicitud " y nos quedamos con el estado autorizado al que va a cambiar
        String estado = solicitud.substring(10, solicitud.length());
        
        //PREGUNTA AUTORIZACIÓN
        Object[] botones = {"Autorizar","Cancelar"};
        int opcion = JOptionPane.showOptionDialog(this,"¿Está seguro de autorizar la solicitud "+idSolicitud+"?", "Confirmación",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE  , null, botones, botones[0]);
        
        if(opcion == 0){

            if(manager_solicitud.respuesta_Pendiente(idSolicitud, estado.toUpperCase(), "AUTORIZADO")){
                JOptionPane.showMessageDialog(null, "Se autorizo correctamente la solicitud.");
            }//Se realizaron los cambios correctamente
         
        }//Acepto la autorización
        
    }//GEN-LAST:event_AutorizarActionPerformed

    private void DenegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DenegarActionPerformed
        // TODO add your handling code here:
        //Obtenemos la fila seleccionada
        int fila = tablaSolicitudes.getSelectedRow();
        //Obtenemos el tipo de la solicitud
        String solicitud = tablaSolicitudes.getValueAt(fila, 1).toString();
        //Obtenemos el id de la solicitud
        int idSolicitud = Integer.parseInt(tablaSolicitudes.getValueAt(fila, 0).toString());
        //Le quitamos la palabra "Solicitud " y nos quedamos con el estado autorizado al que va a cambiar
        String estado = solicitud.substring(10, solicitud.length());
        
        //PREGUNTA AUTORIZACIÓN
        Object[] botones = {"Denegar","Cancelar"};
        int opcion = JOptionPane.showOptionDialog(this,"¿Está seguro de denegar la solicitud "+idSolicitud+"?", "Confirmación",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE  , null, botones, botones[0]);
        
        if(opcion == 0){

            if(manager_solicitud.respuesta_Pendiente(idSolicitud, estado.toUpperCase(), "DENEGADO")){
                JOptionPane.showMessageDialog(null, "Se autorizo correctamente la solicitud.");
            }//Se realizaron los cambios correctamente
         
        }//Acepto la autorización
    }//GEN-LAST:event_DenegarActionPerformed
    /*-------------------------------PARA LA ASIGNACION EN LA PESTAÑA DE MANEJADOR KEVIN------------------------------------------------*/
    public boolean existeCodigoTablaMAsignados(String codigo,int cantidad){
        boolean existe = false;
        //Buscamos en la tabla si ya existe el codigo
        for(int fila = 0;fila<tablaMAsignados.getRowCount();fila++){
            //Si el codigo ya se habia registrado entonces sumamos uno a la cantidad
            if(tablaMAsignados.getValueAt(fila,0).equals(codigo)){
                int  valor = Integer.parseInt(tablaMAsignados.getValueAt(fila,5).toString());
                valor = valor + cantidad;
                tablaMAsignados.setValueAt(valor,fila,5);
                existe = true;
                break;
            }//if
            
        }//for
        return existe;
    }//Busca si existen coincidencias en la tabla para sumarlas
    
    public void getDatosTablaAsignados(){
        Claves = new String[tablaMAsignados.getRowCount()];
        Cantidad = new int[tablaMAsignados.getRowCount()];
        for(int i = 0;i<tablaMAsignados.getRowCount();i++){
            Claves[i] = tablaMAsignados.getValueAt(i, 0).toString();//Obtenemos las claves
            Cantidad[i] = Integer.parseInt(tablaMAsignados.getValueAt(i, 5).toString());//Obtenemos las cantidades
        }//Llenar vector de los codigos de barras
    }//obtiene los datos de la tabla "tablaMAsignados"
    
    public void regresarInventario(){
        manejador_inventario.regresarInventario(Claves,Cantidad);
    }
    
    public void limpiarTablaMAsignados(){
        
        int a = modelotablaMAsignados.getRowCount() - 1;
        for(int i=0; i<=a;i++){
           modelotablaMAsignados.removeRow(0);
        }
        
        if(rb_inventario_normal1.isSelected()){
            tablaMInventarioA.setModel(manejador_inventario.getInventario());
        }else{
            tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
        }
        
    }//limpia la tabla "tablaMAsignados"
    
    /*--------------------------PARA LA RECOLECCION EN LA PESTAÑA DE MANEJADOR  DE INVENTARIO-----------------------*/
    public boolean existeCodigoTablaObjetosEntregados(String codigo,int cantidad){
        boolean existe = false;
        //Buscamos en la tabla si ya existe el codigo
        for(int fila = 0;fila<tablaObjetosEntregados.getRowCount();fila++){
            //Si el codigo ya se habia registrado entonces sumamos uno a la cantidad
            if(tablaObjetosEntregados.getValueAt(fila,1).equals(codigo)){
                int  valor = Integer.parseInt(tablaObjetosEntregados.getValueAt(fila,5).toString());
                valor = valor + cantidad;
                tablaObjetosEntregados.setValueAt(valor,fila,5);
                existe = true;
                break;
            }//if
            
        }//for
        return existe;
    }//Busca si existen coincidencias en la tabla para sumarlas
    
    public void getDatosTablaRecoleccion(){
        IDVales = new int[tablaObjetosEntregados.getRowCount()];
        Claves = new String[tablaObjetosEntregados.getRowCount()];
        Cantidad = new int[tablaObjetosEntregados.getRowCount()];
        for(int i = 0;i<tablaObjetosEntregados.getRowCount();i++){
            IDVales[i] = Integer.parseInt(tablaObjetosEntregados.getValueAt(i, 0).toString());//Obtenemos los id de los vales
            Claves[i] = tablaObjetosEntregados.getValueAt(i, 1).toString();//Obtenemos las claves
            Cantidad[i] = Integer.parseInt(tablaObjetosEntregados.getValueAt(i, 5).toString());//Obtenemos las cantidades
        }//Llenar arreglos de los codigos de barras y cantidades
    }//obtiene los datos de la tabla "tablaMAsignados"
    
    public void regresarRecoleccion(){
        manejador_inventario.regresarRecoleccion(IDVales,Claves,Cantidad);
    }
    
    public void limpiarTablaRecoleccion(){
        
        int a = modeloObjetosEntregados.getRowCount() - 1;
        for(int i=0; i<=a;i++){
           modeloObjetosEntregados.removeRow(0);
        }
        
        if(comboEmpleadoR.getSelectedIndex() != 0){
            tablaRecoleccion.setModel(manejador_inventario.getInventarioEmpleadoAsignaciones(comboEmpleadoR.getSelectedItem().toString()));
        }else{
            tablaRecoleccion.setModel(modeloRecoleccion);
        }
    }//limpia la tabla "tablaMAsignados"
    
    private void CancelarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarAActionPerformed
        // TODO add your handling code here:
        Claves = new String[1];
        Cantidad = new int[1];
        //Obtenemos la fila
        int fila = tablaMAsignados.getSelectedRow();
        
        //Obtenemos la clave y cantidad
        Claves[0] = tablaMAsignados.getValueAt(fila, 0).toString();
        Cantidad[0] = Integer.parseInt(tablaMAsignados.getValueAt(fila, 5).toString());
        
        //Mostramos mensaje de confirmación para cancelar el producto seleccionado
        String[] opciones = {"Aceptar", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(null, "¿Desea cancelar el producto "+Claves[0]+"?","Confirmación de cancelación", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if(seleccion == 0){
            //Regresamos el producto seleccionado
            manejador_inventario.regresarInventario(Claves,Cantidad);
            //Eliminamos el registro de la tabla
            modelotablaMAsignados.removeRow(fila);
            JOptionPane.showMessageDialog(null, "Se regreso al inventario el producto cancelado.");
            //Actualizamos la tabla de acuerdo al radiobutton seleccionado
            if(rb_inventario_normal1.isSelected()){
                tablaMInventarioA.setModel(manejador_inventario.getInventario());
            }else{
                tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
            }//else
            //Vemos si queda mas de un producto
            if(modelotablaMAsignados.getRowCount() == 0){
                btn_generar_vale3.setEnabled(false);
            }
            
        }//selecciono la opcion "Aceptar"
    }//GEN-LAST:event_CancelarAActionPerformed

    private void EntregarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EntregarTodoActionPerformed
        // TODO add your handling code here:
        int fila = tablaRecoleccion.getSelectedRow();
        //Obtenemos el idvale,el codigo del producto y la cantidad
        int vale = Integer.parseInt(tablaRecoleccion.getValueAt(fila, 0).toString());
        String codigo = tablaRecoleccion.getValueAt(fila, 1).toString();
        int cantidad = Integer.parseInt(tablaRecoleccion.getValueAt(fila, 5).toString());
        
        if(manejador_inventario.recoleccionInventario(vale,codigo,cantidad)){
            btnGenerarValeR.setEnabled(true);
            if(!(existeCodigoTablaObjetosEntregados(codigo,cantidad))){
                modeloObjetosEntregados.addRow(new Object[]{vale,codigo,tablaRecoleccion.getValueAt(fila, 2),tablaRecoleccion.getValueAt(fila, 3),tablaRecoleccion.getValueAt(fila, 4),cantidad});
            }
            tablaRecoleccion.setModel(manejador_inventario.getInventarioEmpleadoAsignaciones(comboEmpleadoR.getSelectedItem().toString()));
        }else{
            JOptionPane.showMessageDialog(null, "Verificar con el distribuidor.");
        }
        
    }//GEN-LAST:event_EntregarTodoActionPerformed

    private void UpdateInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateInfoActionPerformed
        // TODO add your handling code here:
        int fila = tablaObjetosEntregados.getSelectedRow();
        /*
        try {
            modificar_addInventario ob = new modificar_addInventario(this, true);

            modificar_addInventario.txtClave.setText(fila, 1).toString();
            ob.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleccione un vehiculo!", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        */
    }//GEN-LAST:event_UpdateInfoActionPerformed

    private void EntregarParteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EntregarParteActionPerformed
        // TODO add your handling code here:
        int fila = tablaRecoleccion.getSelectedRow();
        //Obtenemos el idvale,el codigo del producto y la cantidad
        int vale = Integer.parseInt(tablaRecoleccion.getValueAt(fila, 0).toString());
        String codigo = tablaRecoleccion.getValueAt(fila, 1).toString();
        int cantidad = Integer.parseInt(tablaRecoleccion.getValueAt(fila, 5).toString());
        
        boolean entero = true;
        boolean canceloCantidad = true;
        int resta = 0;
        while(entero){    

            String cadena = JOptionPane.showInputDialog("Ingrese la cantidad que va a recolectar");
            //Cancelo la solicitud de asignacion
            if(cadena == null){
                entero = false;
                canceloCantidad = false;
            }else{
                try{
                    //Si hace la conversion correctamente entonces no entra en la excepcion y se sale del ciclo
                    resta = Integer.parseInt(cadena);
                    
                    if(cantidad >= resta){
                        entero = false;
                    }else{
                        JOptionPane.showMessageDialog(null, "La cantidad que quiere recolectar ("+resta+") es mayor a la que tiene asignado("+cantidad+")");
                    }
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"Solo ingrese numeros");
                    entero = true;
                }//try catch
            }
        }//while
        if(canceloCantidad){
            
            if(manejador_inventario.recoleccionInventario(vale,codigo,resta)){
                btnGenerarValeR.setEnabled(true);
                if(!(existeCodigoTablaObjetosEntregados(codigo,resta))){
                    modeloObjetosEntregados.addRow(new Object[]{vale,codigo,tablaRecoleccion.getValueAt(fila, 2),tablaRecoleccion.getValueAt(fila, 3),tablaRecoleccion.getValueAt(fila, 4),resta});
                }
                tablaRecoleccion.setModel(manejador_inventario.getInventarioEmpleadoAsignaciones(comboEmpleadoR.getSelectedItem().toString()));
                
            }else{
                JOptionPane.showMessageDialog(null, "Verificar con el distribuidor.");
            }
            
        }//canceloCantidad
    }//GEN-LAST:event_EntregarParteActionPerformed

    private void manejo_inventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manejo_inventarioMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_manejo_inventarioMouseClicked

    private void rb_recoleccion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_recoleccion1ActionPerformed
        // TODO add your handling code here:
        CardLayout c_recoleccion = (CardLayout)pn_contenedor_ventanas1.getLayout();
        c_recoleccion.show(pn_contenedor_ventanas1,"c_s_recoleccion");
        if(manager_permisos.permisoPorVale(Username, "Vale de recolección")){
            comboEmpleadoR.setEnabled(true);
            tablaRecoleccion.setEnabled(true);
            btn_cancelar3.setEnabled(true);
            comboEmpleadoR.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
            comboEmpleadoR.addItem("Seleccione al empleado...");
            manejador_inventario.getEmpleadosAsignacion(comboEmpleadoR);
            
        }else{
            JOptionPane.showMessageDialog(null, "No tienes permisos para realizar vales de recolección");
            //Bloqueamos el uso de la asignacion
            comboEmpleadoR.setEnabled(false);
            tablaRecoleccion.setEnabled(false);
            btnGenerarValeR.setEnabled(false);
            btn_cancelar3.setEnabled(false);
            
            //Devolvemos los productos al estado anterior
            getDatosTablaRecoleccion();
            regresarRecoleccion();
            limpiarTablaRecoleccion();
            comboEmpleadoR.setSelectedIndex(0);
        }
    }//GEN-LAST:event_rb_recoleccion1ActionPerformed

    private void rb_asignacion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_asignacion1ActionPerformed
        // TODO add your handling code here:
        CardLayout c_asignacion = (CardLayout)pn_contenedor_ventanas1.getLayout();
        c_asignacion.show(pn_contenedor_ventanas1,"c_s_asignacion");
            
        if(manager_permisos.permisoPorVale(Username, "Vale de asignación")){
            comboEmpleado.setEnabled(true);
            rb_inventario_normal1.setEnabled(true);
            rb_inventario_granel1.setEnabled(true);
            tablaMInventarioA.setEnabled(true);
            btn_cancelar2.setEnabled(true);
        }else{
            JOptionPane.showMessageDialog(null, "No tienes permisos para realizar vales de asignación");
            //Bloqueamos el uso de la asignacion
            comboEmpleado.setEnabled(false);
            rb_inventario_normal1.setEnabled(false);
            rb_inventario_granel1.setEnabled(false);
            tablaMInventarioA.setEnabled(false);
            btn_generar_vale3.setEnabled(false);
            btn_cancelar2.setEnabled(false);
            
            //Regresamos los productos si es que selecciono alguno
            getDatosTablaAsignados();
            regresarInventario();
            limpiarTablaMAsignados();
            comboEmpleado.setSelectedIndex(0);
            
        }
    }//GEN-LAST:event_rb_asignacion1ActionPerformed

    private void comboEmpleadoRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEmpleadoRActionPerformed
        // TODO add your handling code here:
        if(comboEmpleadoR.getSelectedIndex() != 0){
            tablaRecoleccion.setModel(manejador_inventario.getInventarioEmpleadoAsignaciones(comboEmpleadoR.getSelectedItem().toString()));
            
            if(tablaObjetosEntregados.getRowCount() > 0){
                btnGenerarValeR.setEnabled(true);
            }
            
        }else{
            tablaRecoleccion.setModel(modeloRecoleccion);
            btnGenerarValeR.setEnabled(false);
        }
    }//GEN-LAST:event_comboEmpleadoRActionPerformed

    private void btn_cancelar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelar3ActionPerformed
        // TODO add your handling code here:
        getDatosTablaRecoleccion();
        regresarRecoleccion();
        limpiarTablaRecoleccion();
        btnGenerarValeR.setEnabled(false);
        tablaRecoleccion.setModel(manejador_inventario.getInventarioEmpleadoAsignaciones(comboEmpleadoR.getSelectedItem().toString()));
        
    }//GEN-LAST:event_btn_cancelar3ActionPerformed

    private void btnGenerarValeRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarValeRActionPerformed
        // TODO add your handling code here:
        limpiarTablaRecoleccion();
        btnGenerarValeR.setEnabled(false);
        JOptionPane.showMessageDialog(null, "Aqui deberiamos de hacer el PDF.");
    }//GEN-LAST:event_btnGenerarValeRActionPerformed

    private void tablaObjetosEntregadosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaObjetosEntregadosMouseReleased
        // TODO add your handling code here:
        //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
        if(SwingUtilities.isRightMouseButton(evt)){
            int r = tablaObjetosEntregados.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablaObjetosEntregados.getRowCount())
            tablaObjetosEntregados.setRowSelectionInterval(r, r);
            MenuEntregados.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tablaObjetosEntregadosMouseReleased

    private void tablaRecoleccionMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaRecoleccionMouseReleased
        // TODO add your handling code here:
        //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
        if(SwingUtilities.isRightMouseButton(evt)){
            int r = tablaRecoleccion.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablaRecoleccion.getRowCount())
            tablaRecoleccion.setRowSelectionInterval(r, r);
            MenuRecoleccion.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tablaRecoleccionMouseReleased

    private void comboEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEmpleadoActionPerformed
        // TODO add your handling code here:
        //si es diferente de 0 entonces ya selecciono un empleado
        if(comboEmpleado.getSelectedIndex() != 0){
            empleadoSeleccionado = true;
        }else{
            empleadoSeleccionado = false;
            btn_generar_vale3.setEnabled(false);
        }

    }//GEN-LAST:event_comboEmpleadoActionPerformed

    private void rb_inventario_granel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_inventario_granel1ActionPerformed
        // TODO add your handling code here:
        tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
        esGranel = true;
    }//GEN-LAST:event_rb_inventario_granel1ActionPerformed

    private void rb_inventario_granel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rb_inventario_granel1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_inventario_granel1MouseClicked

    private void rb_inventario_normal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_inventario_normal1ActionPerformed
        // TODO add your handling code here:
        tablaMInventarioA.setModel(manejador_inventario.getInventario());
        esGranel = false;
    }//GEN-LAST:event_rb_inventario_normal1ActionPerformed

    private void btn_cancelar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelar2ActionPerformed
        // TODO add your handling code here:
        getDatosTablaAsignados();
        regresarInventario();
        limpiarTablaMAsignados();
        comboEmpleado.setSelectedIndex(0);
        btn_generar_vale3.setEnabled(false);
    }//GEN-LAST:event_btn_cancelar2ActionPerformed

    private void btn_generar_vale3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generar_vale3ActionPerformed
        // TODO add your handling code here:
        getDatosTablaAsignados();
        if(manejador_inventario.asignarInventario(Claves, Cantidad, comboEmpleado.getSelectedItem().toString())){
            JOptionPane.showMessageDialog(null, "Se han asignado correctamente.");
            limpiarTablaMAsignados();
            btn_generar_vale3.setEnabled(false);
            comboEmpleado.setSelectedIndex(0);
            //Actualizamos el combo de los empleados de recolección
            comboEmpleadoR.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
            comboEmpleadoR.addItem("Seleccione al empleado...");
            manejador_inventario.getEmpleadosAsignacion(comboEmpleadoR);

        }else{
            JOptionPane.showMessageDialog(null,"Verificar con el distribuidor.");
        }

    }//GEN-LAST:event_btn_generar_vale3ActionPerformed

    private void tablaMInventarioAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMInventarioAMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){

            int fila = tablaMInventarioA.getSelectedRow();
            //Obtenemos la clave del producto
            String idProducto = tablaMInventarioA.getValueAt(fila, 0).toString();
            if(empleadoSeleccionado){
                if(esGranel){
                    int cantidad = 0;
                    //Esto es para validar que ingrese solo numeros y mientras no lo haga, seguira preguntado hasta que
                    //solo teclee numeros o cancele el movimiento
                    boolean entero = true;
                    boolean canceloCantidad = true;
                    while(entero){

                        String cadena = JOptionPane.showInputDialog("Ingrese la cantidad que desea asignar");
                        //Cancelo la solicitud de asignacion
                        if(cadena == null){
                            entero = false;
                            canceloCantidad = false;
                        }else{
                            try{
                                //Si hace la conversion correctamente entonces no entra en la excepcion y se sale del ciclo
                                cantidad = Integer.parseInt(cadena);
                                entero = false;

                            }catch(NumberFormatException e){
                                JOptionPane.showMessageDialog(null,"Solo ingrese numeros");
                                entero = true;
                            }//try catch
                        }
                    }//while
                    if(canceloCantidad){
                        btn_generar_vale3.setEnabled(true);
                        //Obtenemos la cantidad directamente de la BD por si se ha actualizado la cantidad
                        int stock = manejador_inventario.cantidadInventarioG(idProducto);

                        //1.- Significa que hubo error al querer hacer la consulta
                        if(stock == -1){
                            JOptionPane.showMessageDialog(null, "Verificar con el distribuidor. -1");
                        }//stock == -1

                        //2.- Se agotaron existencias, entonces alguien mas asigno dichas existencias a alguien más
                        else if(stock == 0){
                            JOptionPane.showMessageDialog(null, "Se han agotado las existencias de ese producto, ya han sido asignadas");
                            tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
                        }//stock == 0
                        //3.- Las existencias son mayores a lo que se solicita, entonces se realiza la asignacion sin problema
                        else if(stock > cantidad){
                            int comprobar = manejador_inventario.productosSuficientesInventarioG(idProducto, cantidad);

                            //Si es mayor entonces si se realizo exitosamente la actualización del inventario
                            if(stock > comprobar){
                                //Se pasa el registro a la otra tabla (0,1,2,3,5,cantidad)
                                if(!(existeCodigoTablaMAsignados(idProducto,cantidad))){
                                    modelotablaMAsignados.addRow(new Object[]{idProducto,tablaMInventarioA.getValueAt(fila, 1),tablaMInventarioA.getValueAt(fila, 2),tablaMInventarioA.getValueAt(fila, 3),tablaMInventarioA.getValueAt(fila, 5),cantidad});
                                    System.out.println("Entro a agregar el registro cuando el stock entro como si nada(parte 1)");
                                }
                                tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
                            }//stock > comprobar

                            //No se realizo la condición anterior porque el stock dejo de ser mayor que la cantidad que se solicita
                            //Comprobamos si se agotaron las exitencias
                            else if(comprobar == 0){
                                JOptionPane.showMessageDialog(null, "Se han agotado las existencias de ese producto, ya han sido asignadas");
                                tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
                            }

                            //No se cumplio la de 0 existencias, entonces comprobamos si es igual de la cantidad que se solicita,
                            //si no, será menor.
                            else if(cantidad == comprobar){
                                //Se pasa el registro a la otra tabla y cambia el estado del producto a agotado(comprobación)
                                int comprobar2 = manejador_inventario.productosIgualesInventarioG(idProducto, cantidad);

                                //Comprobamos si se agotaron las exitencias
                                if(comprobar2 == 0){
                                    //Si es 0 entonces se cambia sin problemas y el estado cambia a agotado
                                    if(!(existeCodigoTablaMAsignados(idProducto,cantidad))){
                                        modelotablaMAsignados.addRow(new Object[]{idProducto,tablaMInventarioA.getValueAt(fila, 1),tablaMInventarioA.getValueAt(fila, 2),tablaMInventarioA.getValueAt(fila, 3),tablaMInventarioA.getValueAt(fila, 5),cantidad});
                                    }
                                    tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
                                }

                                else if(cantidad > comprobar2){
                                    tablaMInventarioA.setModel(manejador_inventario.getInventarioG());

                                    //Preguntara si quiere lo que hay en existencia
                                    String[] opciones = {"Aceptar", "Cancelar"};
                                    int seleccion = JOptionPane.showOptionDialog(null, "Solo se cuenta con "+comprobar2+" de exitencias para el producto "+idProducto+".\nUsted esta solicitando "+cantidad+", ¿Desea aceptar las existencias restantes o esperar a que las agreguen al inventario?","¿Acepta las exitencias restantes?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                                    if(seleccion == 0){
                                        manejador_inventario.productosIgualesInventarioG(idProducto, comprobar2);
                                        if(!(existeCodigoTablaMAsignados(idProducto,comprobar2))){
                                            modelotablaMAsignados.addRow(new Object[]{idProducto,tablaMInventarioA.getValueAt(fila, 1),tablaMInventarioA.getValueAt(fila, 2),tablaMInventarioA.getValueAt(fila, 3),tablaMInventarioA.getValueAt(fila, 5),comprobar2});
                                        }
                                    }//Dio clic en la opcion aceptar

                                    tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
                                }//cantidad > comprobar2

                                //Si no entro a ninguna de las 2 de arriba, entonces se actualizo el stock agregando mas
                                //existencias, entonces se realiza el registro normalmente
                                else{
                                    manejador_inventario.productosSuficientesInventarioG(idProducto, cantidad);
                                    if(!(existeCodigoTablaMAsignados(idProducto,cantidad))){
                                        modelotablaMAsignados.addRow(new Object[]{idProducto,tablaMInventarioA.getValueAt(fila, 1),tablaMInventarioA.getValueAt(fila, 2),tablaMInventarioA.getValueAt(fila, 3),tablaMInventarioA.getValueAt(fila, 5),cantidad});
                                    }
                                    tablaMInventarioA.setModel(manejador_inventario.getInventarioG());

                                }//else

                            }//cantidad == comprobar

                            //La solicitud es mayor a la nueva actualización del stock, entonces pregunta si quiere los
                            //de este producto que superan a lo que se está solicitando, entonces preguntará que solo se cuentan con cierta
                            //cantitidad de exitencias y si desea aceptarla
                            else if(cantidad > comprobar){
                                //Se pregunta si se quieren los productos que se encuentran, en caso de quererlos
                                //se asignan las existencias restantes y cambia a agotado, si no las quiere
                                //entonces no sucede nada
                                tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
                                //Preguntara si quiere lo que hay en existencia
                                String[] opciones = {"Aceptar", "Cancelar"};
                                int seleccion = JOptionPane.showOptionDialog(null, "Solo se cuenta con "+comprobar+" de exitencias para el producto "+idProducto+".\nUsted esta solicitando "+cantidad+", ¿Desea aceptar las existencias restantes o esperar a que las agreguen al inventario?","¿Acepta las exitencias restantes?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                                if(seleccion == 0){
                                    manejador_inventario.productosIgualesInventarioG(idProducto, comprobar);
                                    if(!(existeCodigoTablaMAsignados(idProducto,comprobar))){
                                        modelotablaMAsignados.addRow(new Object[]{idProducto,tablaMInventarioA.getValueAt(fila, 1),tablaMInventarioA.getValueAt(fila, 2),tablaMInventarioA.getValueAt(fila, 3),tablaMInventarioA.getValueAt(fila, 5),comprobar});
                                    }
                                }//Dio clic en aceptar

                                tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
                            }
                            //Si no entro a ninguna de las 3 de arriba, entonces se actualizo el stock agregando mas
                            //existencias, entonces se realiza el registro normalmente
                            else{
                                manejador_inventario.productosSuficientesInventarioG(idProducto, cantidad);
                                if(!(existeCodigoTablaMAsignados(idProducto,cantidad))){
                                    modelotablaMAsignados.addRow(new Object[]{idProducto,tablaMInventarioA.getValueAt(fila, 1),tablaMInventarioA.getValueAt(fila, 2),tablaMInventarioA.getValueAt(fila, 3),tablaMInventarioA.getValueAt(fila, 5),cantidad});
                                }
                                tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
                            }//else

                        }//stock > cantidad

                        //No se cumplio la de 0 existencias, entonces comprobamos si es igual de la cantidad que se solicita,
                        //si no, será menor.
                        else if(stock == cantidad){
                            //Se pasa el registro a la otra tabla y cambia el estado del producto a agotado(comprobación)
                            int comprobar2 = manejador_inventario.productosIgualesInventarioG(idProducto, cantidad);

                            //Comprobamos si se agotaron las exitencias
                            if(comprobar2 == 0){
                                //Si es 0 entonces se cambia sin problemas y el estado cambia a agotado
                                if(!(existeCodigoTablaMAsignados(idProducto,cantidad))){
                                    modelotablaMAsignados.addRow(new Object[]{idProducto,tablaMInventarioA.getValueAt(fila, 1),tablaMInventarioA.getValueAt(fila, 2),tablaMInventarioA.getValueAt(fila, 3),tablaMInventarioA.getValueAt(fila, 5),cantidad});
                                }
                                tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
                            }

                            else if(cantidad > comprobar2){

                                tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
                                //Preguntara si quiere lo que hay en existencia
                                String[] opciones = {"Aceptar", "Cancelar"};
                                int seleccion = JOptionPane.showOptionDialog(null, "Solo se cuenta con "+comprobar2+" de exitencias para el producto "+idProducto+".\nUsted esta solicitando "+cantidad+", ¿Desea aceptar las existencias restantes o esperar a que las agreguen al inventario?","¿Acepta las exitencias restantes?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                                if(seleccion == 0){
                                    manejador_inventario.productosIgualesInventarioG(idProducto, comprobar2);
                                    if(!(existeCodigoTablaMAsignados(idProducto,comprobar2))){
                                        modelotablaMAsignados.addRow(new Object[]{idProducto,tablaMInventarioA.getValueAt(fila, 1),tablaMInventarioA.getValueAt(fila, 2),tablaMInventarioA.getValueAt(fila, 3),tablaMInventarioA.getValueAt(fila, 5),comprobar2});
                                    }
                                }//Dio clic en aceptar

                                tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
                            }//cantidad > stock
                            //Si no entro a ninguna de las 2 de arriba, entonces se actualizo el stock agregando mas
                            //existencias, entonces se realiza el registro normalmente
                            else{
                                manejador_inventario.productosSuficientesInventarioG(idProducto, cantidad);
                                if(!(existeCodigoTablaMAsignados(idProducto,cantidad))){
                                    modelotablaMAsignados.addRow(new Object[]{idProducto,tablaMInventarioA.getValueAt(fila, 1),tablaMInventarioA.getValueAt(fila, 2),tablaMInventarioA.getValueAt(fila, 3),tablaMInventarioA.getValueAt(fila, 5),cantidad});
                                }
                                tablaMInventarioA.setModel(manejador_inventario.getInventarioG());

                            }//else
                        }// stock == cantidad
                        else if (stock < cantidad){
                            tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
                            //Preguntara si quiere lo que hay en existencia
                            String[] opciones = {"Aceptar", "Cancelar"};
                            int seleccion = JOptionPane.showOptionDialog(null, "Solo se cuenta con "+stock+" de exitencias para el producto "+idProducto+".\nUsted esta solicitando "+cantidad+", ¿Desea aceptar las existencias restantes o esperar a que las agreguen al inventario?","¿Acepta las exitencias restantes?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                            if(seleccion == 0){
                                manejador_inventario.productosIgualesInventarioG(idProducto, stock);
                                if(!(existeCodigoTablaMAsignados(idProducto,stock))){
                                    modelotablaMAsignados.addRow(new Object[]{idProducto,tablaMInventarioA.getValueAt(fila, 1),tablaMInventarioA.getValueAt(fila, 2),tablaMInventarioA.getValueAt(fila, 3),tablaMInventarioA.getValueAt(fila, 5),stock});
                                }
                            }//Dio clic en la opcion aceptar

                            tablaMInventarioA.setModel(manejador_inventario.getInventarioG());
                        }//stock < cantidad
                    }//canceloSolicitud
                }//esGranel
                else{
                    //Obtenemos el estado del producto
                    String estado = manager_solicitud.estadoProducto(idProducto);
                    //Si esta disponible entonces lo asignamos
                    if(estado.equals("DISPONIBLE")){

                        if(manager_asignar.asignarEquipo(idProducto)){
                            btn_generar_vale3.setEnabled(true);
                            modelotablaMAsignados.addRow(new Object[]{idProducto,tablaMInventarioA.getValueAt(fila, 1),tablaMInventarioA.getValueAt(fila, 2),tablaMInventarioA.getValueAt(fila, 3),tablaMInventarioA.getValueAt(fila, 6),1});
                            tablaMInventarioA.setModel(manejador_inventario.getInventario());
                        }//asignarEquipo

                    }//estado.equals("DISPONIBLE")

                    //No estaba disponible entonces le avisamos al usuario y actualizamos la tabla
                    else{
                        JOptionPane.showMessageDialog(null, "El equipo ya no se encuentra disponible");
                        tablaMInventarioA.setModel(manejador_inventario.getInventario());
                    }//else

                }//else

            }else{
                JOptionPane.showMessageDialog(null, "Antes de asignar inventario es necesario seleccionar al responsable.");
            }

        }//doble clic
    }//GEN-LAST:event_tablaMInventarioAMouseClicked

    private void tablaMAsignadosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMAsignadosMouseReleased
        // TODO add your handling code here:
        //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
        if(SwingUtilities.isRightMouseButton(evt)){
            int r = tablaMAsignados.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablaMAsignados.getRowCount())
            tablaMAsignados.setRowSelectionInterval(r, r);
            MenuAsginados.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tablaMAsignadosMouseReleased

    private void configuracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_configuracionMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_configuracionMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        // int limite = Integer.parseInt(campoHasta.getText());

        String sub1 = campoip1.getValue().toString();
        String sub2 = campoip2.getValue().toString();
        String sub3 = campoip3.getValue().toString();
        int limite = Integer.parseInt(campoip4.getValue().toString());
        try {
            // TODO add your handling code here:

            checkHosts(sub1,sub2,sub3, limite);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        // int limite = Integer.parseInt(campoHasta.getText());

        String sub1 = campoip1.getValue().toString();
        String sub2 = campoip2.getValue().toString();
        String sub3 = campoip3.getValue().toString();
        int limite = Integer.parseInt(campoip4.getValue().toString());
        try {
            // TODO add your handling code here:

            checkHostsReScan(sub1,sub2,sub3, limite);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tablaIPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaIPMouseClicked
        // TODO add your handling code here:
        tablaBD.clearSelection();
    }//GEN-LAST:event_tablaIPMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        try{
            System.out.println(tablaIP.getValueAt(tablaIP.getSelectedRow(),0)+" "+tablaIP.getValueAt(tablaIP.getSelectedRow(),1));
            if(manajerMySQL.insertarUsuarioBD("PC70", tablaIP.getValueAt(tablaIP.getSelectedRow(),1).toString())){
                JOptionPane.showMessageDialog(null,"Permisos creados con exito!","Información!",JOptionPane.INFORMATION_MESSAGE);

                manajerMySQL.insertarPrivilegios(
                    tablaIP.getValueAt(tablaIP.getSelectedRow(),0).toString(),
                    tablaIP.getValueAt(tablaIP.getSelectedRow(),1).toString(),
                    tablaIP.getValueAt(tablaIP.getSelectedRow(),2).toString());
                modeloTablaIP.removeRow(tablaIP.getSelectedRow());
            }else{
                JOptionPane.showMessageDialog(null, "Error al asignar Permisos","Advertencia!",JOptionPane.WARNING_MESSAGE);
            }//else
        }catch(java.lang.ArrayIndexOutOfBoundsException e){
            //JOptionPane.showMessageDialog(null,"Seleccione una dirección!","Información!",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //System.out.println(tablaIP.getValueAt(tablaIP.getSelectedRow(),0)+" "+tablaIP.getValueAt(tablaIP.getSelectedRow(),1));
        //System.out.println(tablaIP.getValueAt(tablaIP.getSelectedRow(),0)+" "+tablaIP.getValueAt(tablaIP.getSelectedRow(),1));
        try {
            if (manajerMySQL.quitarUsuarioBD(tablaBD.getValueAt(tablaBD.getSelectedRow(), 0).toString(), tablaBD.getValueAt(tablaBD.getSelectedRow(), 1).toString())) {
                //regresar los datos a la tabla de ip

                manajerMySQL.borrarPrivilegios(tablaBD.getValueAt(tablaBD.getSelectedRow(), 1).toString());
                //regresar los datos al modelo de ip para poder reasignar
                modeloTablaIP.addRow(new Object[]{""+tablaBD.getValueAt(tablaBD.getSelectedRow(), 0).toString(),
                    ""+tablaBD.getValueAt(tablaBD.getSelectedRow(), 1).toString(),"Conectado"});

            JOptionPane.showMessageDialog(null, "Permisos retirados con exito!", "Información!", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Error al retirar Permisos","Advertencia!",JOptionPane.WARNING_MESSAGE);
        }
        }catch(java.lang.ArrayIndexOutOfBoundsException e){
            // JOptionPane.showMessageDialog(null,"Seleccione una dirección!","Información!",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tablaBDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaBDMouseClicked
        // TODO add your handling code here:
        tablaIP.clearSelection();
    }//GEN-LAST:event_tablaBDMouseClicked

    private void tablaSolicitudesPersonalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaSolicitudesPersonalMouseReleased
        // TODO add your handling code here:
        //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
        if(SwingUtilities.isRightMouseButton(evt)){
            int r = tablaSolicitudesPersonal.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablaSolicitudesPersonal.getRowCount())
            tablaSolicitudesPersonal.setRowSelectionInterval(r, r);
            MenuSolicitudesP.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tablaSolicitudesPersonalMouseReleased

    private void btnAñadirResguardoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirResguardoActionPerformed
        // TODO add your handling code here:
        addResguardo ob = new addResguardo(this,true);
        ob.setVisible(true);
    }//GEN-LAST:event_btnAñadirResguardoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        banderaUser = 3;
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        //Llamamos el forumulario para actuaizar un empleado
        addEmpleados ob = new addEmpleados(this, true);
        ob.setVisible(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void tablaSolicitudesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaSolicitudesMouseReleased
        // TODO add your handling code here:

        if(!(manager_permisos.esPresidencia(Username))){

            //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
            if(SwingUtilities.isRightMouseButton(evt)){
                int r = tablaSolicitudes.rowAtPoint(evt.getPoint());
                if (r >= 0 && r < tablaSolicitudes.getRowCount())
                tablaSolicitudes.setRowSelectionInterval(r, r);
                MenuSolicitudes.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
            }//clic derecho

        }else{
            if(SwingUtilities.isRightMouseButton(evt)){
                int r = tablaSolicitudes.rowAtPoint(evt.getPoint());
                if (r >= 0 && r < tablaSolicitudes.getRowCount())
                tablaSolicitudes.setRowSelectionInterval(r, r);
                MenuPendientes.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
            }//clic derecho
        }
    }//GEN-LAST:event_tablaSolicitudesMouseReleased

    private void vehiculosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vehiculosMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_vehiculosMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        try {
            ventana_modificar_vehiculo ob = new ventana_modificar_vehiculo(this, true);
            campo.setText(tablaVehiculos.getValueAt(tablaVehiculos.getSelectedRow(), 4).toString());
            ob.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleccione un vehiculo!","Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtBusquedaVehiculosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaVehiculosKeyReleased
        // TODO add your handling code here:
        String filtro = comboFiltroVehiculos.getSelectedItem().toString();
        String busqueda = txtBusquedaVehiculos.getText();

        //Si no hay nada en el campo entonces mostramos todos los empleados
        if(busqueda.equals("")){
            tablaVehiculos.setModel(managerVehiculos.getVehiculos());
        }//if

        else{

            //Si hay coincidencias entonces los muestra
            if(managerVehiculos.existeVehiculo(filtro, busqueda)){
                tablaVehiculos.setModel(managerVehiculos.getVehiculosEspecificos(filtro,busqueda));
            }//if

            //Si no hay coincidecnias entonces mostramos todos los vehiculos
            else{
                tablaVehiculos.setModel(managerVehiculos.getVehiculos());
            }//Segundo else

        }//Primer else
    }//GEN-LAST:event_txtBusquedaVehiculosKeyReleased

    private void comboFiltroVehiculosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFiltroVehiculosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboFiltroVehiculosActionPerformed

    private void btnAñadirVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirVehiculoActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.alta_vehiculos(Username)){
            ventana_añadir_vehiculo añadirVehiculo = new ventana_añadir_vehiculo(this, true);
            añadirVehiculo.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "No tienes permisos para dar de alta nuevos vehiculos.");
        }
    }//GEN-LAST:event_btnAñadirVehiculoActionPerformed

    private void zoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomActionPerformed
        // TODO add your handling code here:
        ventanaZoom ob = new ventanaZoom(this, true);
        ob.setVisible(true);
    }//GEN-LAST:event_zoomActionPerformed

    private void tablaVehiculosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaVehiculosKeyReleased
        // TODO add your handling code here:
        metodoVehiculos();
    }//GEN-LAST:event_tablaVehiculosKeyReleased

    private void tablaVehiculosPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablaVehiculosPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_tablaVehiculosPropertyChange

    private void tablaVehiculosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVehiculosMouseReleased
        // TODO add your handling code here:

        //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
        if(SwingUtilities.isRightMouseButton(evt)){
            int r = tablaVehiculos.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablaVehiculos.getRowCount())
            tablaVehiculos.setRowSelectionInterval(r, r);
            MenuVehiculos.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho

    }//GEN-LAST:event_tablaVehiculosMouseReleased

    private void tablaVehiculosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVehiculosMouseClicked
        // TODO add your handling code here:

        metodoVehiculos();
    }//GEN-LAST:event_tablaVehiculosMouseClicked

    private void comboFiltroUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFiltroUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboFiltroUsuarioActionPerformed

    private void txtBusquedaUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaUsuarioKeyReleased
        // TODO add your handling code here:
        int filtro = comboFiltroUsuario.getSelectedIndex();
        String busqueda = txtBusquedaUsuario.getText();

        //Si no hay nada en el campo entonces mostramos todos los empleados
        if(busqueda.equals("")){
            tablaUsuarios.setModel(manager_users.getEmpleados(Username));
        }//if

        else{

            //Si hay coincidencias entonces los muestra
            if(manager_users.existeEmpleado(filtro, busqueda, Username)){
                tablaUsuarios.setModel(manager_users.getEmpleadosCoincidencia(Username,filtro,busqueda));
            }//if

            //Si no hay coincidecnias entonces mostramos todos los empleados
            else{
                tablaUsuarios.setModel(manager_users.getEmpleados(Username));
            }//Segundo else

        }//Primer else

    }//GEN-LAST:event_txtBusquedaUsuarioKeyReleased

    private void btnAddEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmpleadoActionPerformed
        // TODO add your handling code here:
        //Llamamos el forumulario para añadir un nuevo empleado
        banderaUser = 1;
        if(manager_permisos.alta_user(Username)){
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                // If Nimbus is not available, you can set the GUI to another look and feel.
            }
            addEmpleados ob = new addEmpleados(this, true);
            ob.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "No tienes permiso para registrar nuevos empleados");
        }
    }//GEN-LAST:event_btnAddEmpleadoActionPerformed

    private void tablaUsuariosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaUsuariosMouseReleased
        // TODO add your handling code here:
        //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
        if(SwingUtilities.isRightMouseButton(evt)){
            int r = tablaUsuarios.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablaUsuarios.getRowCount())
            tablaUsuarios.setRowSelectionInterval(r, r);
            MenuUsuarios.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho

    }//GEN-LAST:event_tablaUsuariosMouseReleased

    private void comboFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFiltroActionPerformed
        // TODO add your handling code here:
        //Llenamos la tabla del inventario
        int filtro = comboFiltro.getSelectedIndex();
        String inventario = comboInventario.getSelectedItem().toString();
        String busqueda = txtBusqueda.getText();

        //Si no hay nada en el campo entonces buscamos todos los productos del inventario o inventario a granel
        if(busqueda.equals("")){

            if(inventario.equals("Inventario")){
                tablaInventario.setModel(manager_inventario.getInventario(filtro));
            }
            else{
                tablaInventario.setModel(manager_inventario.getInventarioG(filtro));
            }
        }//if

        else{

            //Si hay coincidencias entonces muestra
            if(manager_inventario.existeProductoEspecifico(filtro, busqueda, inventario)){
                tablaInventario.setModel(manager_inventario.getInventarioEspecifico(filtro, busqueda, inventario));
            }//if

            //Si no hay coincidecnias entonces mostramos el inventario o el inventario a granel
            else{

                if(inventario.equals("Inventario")){
                    tablaInventario.setModel(manager_inventario.getInventario(filtro));
                }
                else{
                    tablaInventario.setModel(manager_inventario.getInventarioG(filtro));
                }

            }//Segundo else

        }//Primer else
    }//GEN-LAST:event_comboFiltroActionPerformed

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
        // TODO add your handling code here:
        if(manager_permisos.consulta_inventario(Username)){
            //Llenamos la tabla del inventario
            int filtro = comboFiltro.getSelectedIndex();
            String inventario = comboInventario.getSelectedItem().toString();
            String busqueda = txtBusqueda.getText();

            //Si no hay nada en el campo entonces buscamos todos los productos del inventario o inventario a granel
            if(busqueda.equals("")){

                if(inventario.equals("Inventario")){
                    tablaInventario.setModel(manager_inventario.getInventario(filtro));
                }
                else{
                    tablaInventario.setModel(manager_inventario.getInventarioG(filtro));
                }
            }//if

            else{

                //Si hay coincidencias entonces muestra
                if(manager_inventario.existeProductoEspecifico(filtro, busqueda, inventario)){
                    tablaInventario.setModel(manager_inventario.getInventarioEspecifico(filtro, busqueda, inventario));
                }//if

                //Si no hay coincidecnias entonces mostramos el inventario o el inventario a granel
                else{

                    if(inventario.equals("Inventario")){
                        tablaInventario.setModel(manager_inventario.getInventario(filtro));
                    }
                    else{
                        tablaInventario.setModel(manager_inventario.getInventarioG(filtro));
                    }

                }//Segundo else

            }//Primer else
        }//if de consulta de inventario
        else{
        
        }
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void comboInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboInventarioActionPerformed
        // TODO add your handling code here:
        int filtro = comboFiltro.getSelectedIndex();
        if(manager_permisos.consulta_inventario(Username)){

            if(comboInventario.getSelectedItem().toString().equals("Inventario")){
                tablaInventario.setModel(manager_inventario.getInventario(filtro));
                banderaInventario = 1;

                comboFiltro.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
                comboFiltro.addItem("Producto");
                comboFiltro.addItem("Almacén");
                comboFiltro.addItem("Marca");

            }else{
                tablaInventario.setModel(manager_inventario.getInventarioG(filtro));
                banderaInventario = 2;

                comboFiltro.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
                comboFiltro.addItem("Clave");
                comboFiltro.addItem("Producto");
                comboFiltro.addItem("Descripción");
                comboFiltro.addItem("Almacén");
                comboFiltro.addItem("Marca");
                comboFiltro.addItem("Observaciones");

            }

        }else{
            JOptionPane.showMessageDialog(null, "No tiene permisos para consultar el inventario.");
        }
    }//GEN-LAST:event_comboInventarioActionPerformed

    private void btnAddInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddInventarioActionPerformed
        // TODO add your handling code here:

        //Llamamos el forumulario para registrar un producto al inventario
        /*
        1 -> Inventario
        2 -> Inventario a granel
        */
        if(banderaInventario == 1){

            if(manager_permisos.alta_inventario(Username)){
                addInventario ob = new addInventario(this, true);
                ob.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "No cuenta con los permisos para registrar nuevos productos al inventario.");
            }//else

        }else{

            if(manager_permisos.alta_inventario(Username)){
                addInventarioGranel ob = new addInventarioGranel(this, true);
                ob.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"No cuenta con los permisos para registrar productos nuevos a granel.");
            }//else

        }//else

    }//GEN-LAST:event_btnAddInventarioActionPerformed

    private void tablaInventarioMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaInventarioMouseReleased
        // TODO add your handling code here:
        if(banderaInventario == 2){

            //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
            if(SwingUtilities.isRightMouseButton(evt)){
                int r = tablaUsuarios.rowAtPoint(evt.getPoint());
                if (r >= 0 && r < tablaUsuarios.getRowCount())
                tablaInventario.setRowSelectionInterval(r, r);
                MenuInventario.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
            }//clic derecho

        }//if

    }//GEN-LAST:event_tablaInventarioMouseReleased

    private void tablaInventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaInventarioMouseClicked
        // TODO add your handling code here:
        if(banderaInventario == 1){

            if(evt.getClickCount() == 2){
                //Obtenemos la fila donde está nuestro nombre del producto que queremos obtener
                int fila = tablaInventario.getSelectedRow();
                //Guardamos nuestro criterio de busqueda para la tabla de coincidencias
                prodInventario = tablaInventario.getValueAt(fila, 0).toString();
                //Mandamos a llamar la ventana de las coincidencias
                tablaDetalleInventario ob = new tablaDetalleInventario(this,true);
                ob.setVisible(true);
            }//getClickCount

        }//if
    }//GEN-LAST:event_tablaInventarioMouseClicked

    private void CancelarEntregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarEntregaActionPerformed
        // TODO add your handling code here:
        IDVales = new int[1];
        Claves = new String[1];
        Cantidad = new int[1];
        //Obtenemos la fila
        int fila = tablaObjetosEntregados.getSelectedRow();
        
        //Obtenemos el idVale, la clave y cantidad
        IDVales[0] = Integer.parseInt(tablaObjetosEntregados.getValueAt(fila, 0).toString());
        Claves[0] = tablaObjetosEntregados.getValueAt(fila, 1).toString();
        Cantidad[0] = Integer.parseInt(tablaObjetosEntregados.getValueAt(fila, 5).toString());
        
        //Mostramos mensaje de confirmación para cancelar el producto seleccionado
        String[] opciones = {"Aceptar", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(null, "¿Desea cancelar el producto "+Claves[0]+"?","Confirmación de cancelación", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if(seleccion == 0){
            //Regresamos el producto seleccionado
            manejador_inventario.regresarRecoleccion(IDVales,Claves,Cantidad);
            //Eliminamos el registro de la tabla
            modeloObjetosEntregados.removeRow(fila);
            tablaRecoleccion.setModel(manejador_inventario.getInventarioEmpleadoAsignaciones(comboEmpleadoR.getSelectedItem().toString()));
            JOptionPane.showMessageDialog(null, "Se cancelo la entrega del producto "+Claves[0]+".");
            //Vemos si queda mas de un producto
            if(modeloObjetosEntregados.getRowCount() == 0){
                btnGenerarValeR.setEnabled(false);
            }
        }//selecciono la opcion "Aceptar"
    }//GEN-LAST:event_CancelarEntregaActionPerformed

    private void btnEditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditar1ActionPerformed

    private void ReemplazarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReemplazarActionPerformed
        // TODO add your handling code here:
        int fila = tablaAsignacionPersonal.getSelectedRow();
        //Obtenemos el id y la cantidad
        productoIDVale = Integer.parseInt(tablaAsignacionPersonal.getValueAt(fila, 0).toString());
        productoAsignacionReemplazo = tablaAsignacionPersonal.getValueAt(fila, 1).toString();
        productoAREstado = tablaAsignacionPersonal.getValueAt(fila, 5).toString();
        
        Ventana_solicitudPersonal ob = new Ventana_solicitudPersonal(this,true);
        ob.setVisible(true);
    }//GEN-LAST:event_ReemplazarActionPerformed

    private void ActualizarAsignacionPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarAsignacionPActionPerformed
        // TODO add your handling code here:
        tablaAsignacionPersonal.setModel(manejador_inventario.getInventarioEmpleadoAsignacionesPersonales(Username));
    }//GEN-LAST:event_ActualizarAsignacionPActionPerformed

    private void tablaAsignacionPersonalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaAsignacionPersonalMouseReleased
        // TODO add your handling code here:
        //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
        if(radioEquipo.isSelected()){
            if(SwingUtilities.isRightMouseButton(evt)){
                int r = tablaAsignacionPersonal.rowAtPoint(evt.getPoint());
                if (r >= 0 && r < tablaAsignacionPersonal.getRowCount())
                tablaAsignacionPersonal.setRowSelectionInterval(r, r);
                MenuAsignacionP.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
            }//clic derecho
        }else{
            if(SwingUtilities.isRightMouseButton(evt)){
                int r = tablaAsignacionPersonal.rowAtPoint(evt.getPoint());
                if (r >= 0 && r < tablaAsignacionPersonal.getRowCount())
                tablaAsignacionPersonal.setRowSelectionInterval(r, r);
                MenuAsignacionPG.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
            }//clic derecho
        }
    }//GEN-LAST:event_tablaAsignacionPersonalMouseReleased

    private void ActualizarInfoGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarInfoGActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.consulta_inventario(Username)){
            //Llenamos la tabla del inventario
            int filtro = comboFiltro.getSelectedIndex();
            String inventario = comboInventario.getSelectedItem().toString();
            String busqueda = txtBusqueda.getText();

            //Si no hay nada en el campo entonces buscamos todos los productos del inventario o inventario a granel
            if(busqueda.equals("")){

                if(inventario.equals("Inventario")){
                    tablaInventario.setModel(manager_inventario.getInventario(filtro));
                }
                else{
                    tablaInventario.setModel(manager_inventario.getInventarioG(filtro));
                }
            }//if

            else{

                //Si hay coincidencias entonces muestra
                if(manager_inventario.existeProductoEspecifico(filtro, busqueda, inventario)){
                    tablaInventario.setModel(manager_inventario.getInventarioEspecifico(filtro, busqueda, inventario));
                }//if

                //Si no hay coincidecnias entonces mostramos el inventario o el inventario a granel
                else{

                    if(inventario.equals("Inventario")){
                        tablaInventario.setModel(manager_inventario.getInventario(filtro));
                    }
                    else{
                        tablaInventario.setModel(manager_inventario.getInventarioG(filtro));
                    }

                }//Segundo else

            }//Primer else
        }//if de la consulta de inventario
        else{
        
        }
    }//GEN-LAST:event_ActualizarInfoGActionPerformed

    private void ActualizarInfoUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarInfoUActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.consulta_user(Username)){
            tablaUsuarios.setModel(manager_users.getEmpleados(Username));
        }else{
            JOptionPane.showMessageDialog(null, "Te han revocado los permisos para la consulta de empleados");
        }
    }//GEN-LAST:event_ActualizarInfoUActionPerformed

    private void ActualizarInfoVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarInfoVActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.consulta_vehiculos(Username)){
            tablaVehiculos.setModel(managerVehiculos.getVehiculos());
        }else{
            JOptionPane.showMessageDialog(null, "Te han revocado los permisos para la consulta de vehiculos");
        }
    }//GEN-LAST:event_ActualizarInfoVActionPerformed

    private void ActualizarInfoSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarInfoSPActionPerformed
        // TODO add your handling code here:
        tablaSolicitudesPersonal.setModel(manager_solicitud.tabla_Solicitudes_Personales(Username));
    }//GEN-LAST:event_ActualizarInfoSPActionPerformed

    private void ActualizarInfoPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarInfoPPActionPerformed
        // TODO add your handling code here:
        tablaPermisosPersonales.setModel(manager_permisos.getPermisos(tablaPermisosPersonales,Username));
    }//GEN-LAST:event_ActualizarInfoPPActionPerformed

    private void tablaPermisosPersonalesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPermisosPersonalesMouseReleased
        // TODO add your handling code here:
        //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
        if(SwingUtilities.isRightMouseButton(evt)){
            int r = tablaPermisosPersonales.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablaPermisosPersonales.getRowCount())
            tablaPermisosPersonales.setRowSelectionInterval(r, r);
            MenuPermisosP.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tablaPermisosPersonalesMouseReleased

    private void ActualizarInfoRecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarInfoRecoActionPerformed
        // TODO add your handling code here:
        tablaRecoleccion.setModel(manejador_inventario.getInventarioEmpleadoAsignaciones(comboEmpleadoR.getSelectedItem().toString()));
    }//GEN-LAST:event_ActualizarInfoRecoActionPerformed

    private void radioGranelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioGranelActionPerformed
        // TODO add your handling code here:
        tablaAsignacionPersonal.setModel(manejador_inventario.getInventarioEmpleadoAsignacionesPersonalesG(Username));
    }//GEN-LAST:event_radioGranelActionPerformed

    private void radioEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioEquipoActionPerformed
        // TODO add your handling code here:
        tablaAsignacionPersonal.setModel(manejador_inventario.getInventarioEmpleadoAsignacionesPersonales(Username));
    }//GEN-LAST:event_radioEquipoActionPerformed

    private void ActualizarAsignacionPGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarAsignacionPGActionPerformed
        // TODO add your handling code here:
        tablaAsignacionPersonal.setModel(manejador_inventario.getInventarioEmpleadoAsignacionesPersonalesG(Username));
    }//GEN-LAST:event_ActualizarAsignacionPGActionPerformed

    private void tablaStockMinMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaStockMinMouseReleased
        // TODO add your handling code here:
        //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
            if(SwingUtilities.isRightMouseButton(evt)){
                int r = tablaStockMin.rowAtPoint(evt.getPoint());
                if (r >= 0 && r < tablaStockMin.getRowCount())
                tablaStockMin.setRowSelectionInterval(r, r);
                MenuStockMin.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
            }//clic derecho
    }//GEN-LAST:event_tablaStockMinMouseReleased

    private void AgregarStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarStockActionPerformed
        // TODO add your handling code here:
        boolean entero = true;
        boolean canceloStockMin = true;
        int cantidad = 0;
        int fila = tablaStockMin.getSelectedRow();
        while(entero){    
            
            String cadena = JOptionPane.showInputDialog("Ingrese la cantidad de stock a agregar");
            
            if(cadena == null){
                entero = false;
                canceloStockMin = false;
            }else{
            
                try{

                    cantidad = Integer.parseInt(cadena);
                    
                    if(cantidad > 0){
                        entero = false;                    
                    }else{
                        JOptionPane.showMessageDialog(null, "Solo ingrese numeros positivos");
                    }
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"Solo ingrese numeros");
                }//try catch
                
            }//else
            
            if(canceloStockMin){
                
                String codigo = tablaStockMin.getValueAt(fila, 0).toString();//Obtenemos el codigo del producto
                
                if(manejador_inventario.actualizarStock(codigo, cantidad)){
                    tablaStockMin.setModel(manejador_inventario.getInventarioStockMin());
                    JOptionPane.showMessageDialog(null, "El inventario se actualizo exitosamente");
                }else{
                    JOptionPane.showMessageDialog(null, "Verificar con el distribuidor");
                }
                
            }//canceloStockMin
            
        }//while
    }//GEN-LAST:event_AgregarStockActionPerformed

    private void ActualizarInfoSMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarInfoSMActionPerformed
        // TODO add your handling code here:
        tablaStockMin.setModel(manejador_inventario.getInventarioStockMin());
    }//GEN-LAST:event_ActualizarInfoSMActionPerformed
       
    public void cargarImagen(String matricula) throws IOException, SQLException {
        
        Image i = null;
        i = javax.imageio.ImageIO.read(managerVehiculos.leerImagen(matricula).getBinaryStream());
//        ImageIcon image = new ImageIcon(i);
//        imagenVehiculo.setIcon(image);
//        this.repaint();
        try {
            ImageIcon fot = new ImageIcon(i);
            ImageIcon icono = new ImageIcon(fot.getImage().getScaledInstance(imagenVehiculo.getWidth(), imagenVehiculo.getHeight(), Image.SCALE_DEFAULT));
            imagenVehiculo.setIcon(icono);
            this.repaint();
        } catch (java.lang.NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener la imagen!", "Información!", JOptionPane.WARNING_MESSAGE);

        }//catch
               
    }
//METODOS PARA IPS
    
    public void checkHosts(String sub1, String sub2, String sub3, int limite) throws UnknownHostException, IOException {
        String subnet = sub1+"." + sub2+"." + sub3;
        int timeout = 1000;
        Vector IPS = new Vector();
        String host = "";
        //progreso.setV
        for (int i = 1; i < limite+1; i++) {

            actualizarEstado(limite, i);
            host = subnet + "." + i;
            //System.out.println(host);
            if (InetAddress.getByName(host).isReachable(timeout)) {
                  
                IPS.add("PC" + i);
                IPS.add(host);

            }//if
        }//for
        this.setTitle("Sistema Integral - Instituto Estatal Electoral de Nayarit");
        for (int i = 0; i < IPS.size(); i = i + 2) {

            modeloTablaIP.addRow(new Object[]{IPS.elementAt(i), IPS.elementAt(i + 1), "Conectado"});

            //System.err.println("" + IPS.elementAt(i));
        }//for
    }//method
        
    public void checkHostsReScan(String sub1, String sub2, String sub3, int limite) throws UnknownHostException, IOException {
        String subnet = sub1+"." + sub2+"." + sub3;
        int timeout = 1000;
        String host = "";
        //progreso.setV
        for (int i = 1; i < limite+1; i++) {
            actualizarEstado(limite, i);
            host = subnet + "." + i;
           // System.out.println(""+host);
            if (InetAddress.getByName(host).isReachable(timeout)) {
                
                IPS.add("PC" + i);
                IPS.add(host);

            }//if
        }//for
        this.setTitle("Sistema Integral - Instituto Estatal Electoral de Nayarit");
        limpiarTablas();
        for (int i = 0; i < IPS.size(); i = i + 2) {

            modeloTablaIP.addRow(new Object[]{IPS.elementAt(i), IPS.elementAt(i + 1), "Conectado"});

            //System.err.println("" + IPS.elementAt(i));
        }//for
    }//method
    
    public void actualizarEstado(int i,int iterator){

        this.setTitle("Red "+(iterator)+" de "+i);
       
        
    }
    
    public void limpiarTablas(){
        int var = modeloTablaIP.getRowCount()-1;
        for(int i = 0; i<=var ;i++){
            modeloTablaIP.removeRow(0);
        }
    }
    
        public void leer(){
        String texto = "";
        try {
            //Creamos un archivo FileReader que obtiene lo que tenga el archivo
            FileReader lector = new FileReader("cnfg.cfg");

            //El contenido de lector se guarda en un BufferedReader
            BufferedReader contenido = new BufferedReader(lector);
            
            Username = contenido.readLine();
        }catch(Exception e){
            
        }
    }
        //Codigo Pablo
        /*METODOS PABLO*/
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
                    new Principal().setVisible(true);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Actualizar;
    private javax.swing.JMenuItem ActualizarAsignacionP;
    private javax.swing.JMenuItem ActualizarAsignacionPG;
    private javax.swing.JMenuItem ActualizarInfo;
    private javax.swing.JMenuItem ActualizarInfoG;
    private javax.swing.JMenuItem ActualizarInfoPP;
    private javax.swing.JMenuItem ActualizarInfoReco;
    private javax.swing.JMenuItem ActualizarInfoSM;
    private javax.swing.JMenuItem ActualizarInfoSP;
    private javax.swing.JMenuItem ActualizarInfoU;
    private javax.swing.JMenuItem ActualizarInfoV;
    private javax.swing.JMenuItem ActualizarPendientes;
    private javax.swing.JMenuItem ActualizarV;
    private javax.swing.JMenuItem AgregarStock;
    private javax.swing.JMenuItem Asignar;
    private javax.swing.JMenuItem AsignarV;
    private javax.swing.JMenuItem Atender;
    private javax.swing.JMenuItem Autorizar;
    private javax.swing.JMenuItem CancelarA;
    private javax.swing.JMenuItem CancelarEntrega;
    private javax.swing.JMenuItem Denegar;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JMenuItem EntregarParte;
    private javax.swing.JMenuItem EntregarTodo;
    private javax.swing.JMenuItem Equipos;
    private javax.swing.ButtonGroup Grupo1;
    private javax.swing.ButtonGroup GrupoTipoInventario;
    private javax.swing.JPopupMenu MenuAsginados;
    private javax.swing.JPopupMenu MenuAsignacionP;
    private javax.swing.JPopupMenu MenuAsignacionPG;
    private javax.swing.JPopupMenu MenuEntregados;
    private javax.swing.JPopupMenu MenuInventario;
    private javax.swing.JPopupMenu MenuPendientes;
    private javax.swing.JPopupMenu MenuPermisosP;
    private javax.swing.JPopupMenu MenuRecoleccion;
    private javax.swing.JMenuItem MenuSolicitud;
    private javax.swing.JPopupMenu MenuSolicitudes;
    private javax.swing.JPopupMenu MenuSolicitudesP;
    private javax.swing.JPopupMenu MenuStockMin;
    private javax.swing.JPopupMenu MenuUsuarios;
    private javax.swing.JPopupMenu MenuVehiculos;
    private javax.swing.JMenuItem Permisos;
    private javax.swing.JMenuItem Promover;
    private javax.swing.JMenuItem Reemplazar;
    private javax.swing.JScrollPane ScrollEmpleado;
    private javax.swing.JMenuItem Servicio;
    private javax.swing.JMenuItem SolicitarBaja;
    private javax.swing.JMenu SolictarMas;
    private javax.swing.JMenuItem UpdateInfo;
    private javax.swing.ButtonGroup bg_manejo_inventario;
    private javax.swing.ButtonGroup bt_tipo_inventario_asignable;
    private javax.swing.JButton btnAddEmpleado;
    private javax.swing.JButton btnAddInventario;
    private javax.swing.JButton btnAñadirResguardo;
    private javax.swing.JButton btnAñadirVehiculo;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEditar1;
    public javax.swing.JButton btnGenerarValeR;
    public javax.swing.JButton btn_cancelar2;
    public javax.swing.JButton btn_cancelar3;
    public javax.swing.JButton btn_generar_vale3;
    private javax.swing.JTextArea campoObservaciones;
    private javax.swing.JSpinner campoip1;
    private javax.swing.JSpinner campoip2;
    private javax.swing.JSpinner campoip3;
    private javax.swing.JSpinner campoip4;
    private javax.swing.JComboBox<String> comboEmpleado;
    private javax.swing.JComboBox<String> comboEmpleadoR;
    public static javax.swing.JComboBox<String> comboFiltro;
    private javax.swing.JComboBox<String> comboFiltroUsuario;
    private javax.swing.JComboBox<String> comboFiltroVehiculos;
    private javax.swing.JComboBox<String> comboInventario;
    private javax.swing.JPanel configuracion;
    private javax.swing.JPanel empleado;
    private javax.swing.JLabel etiquetaAño;
    private javax.swing.JLabel etiquetaEstado;
    private javax.swing.JLabel etiquetaKilometraje;
    private javax.swing.JLabel etiquetaLinea;
    private javax.swing.JLabel etiquetaMarca;
    private javax.swing.JLabel fondo;
    private javax.swing.JPanel fondoVehiculo;
    private javax.swing.JLabel imagenVehiculo;
    private javax.swing.JMenuItem itemAnterior;
    private javax.swing.JMenuItem itemSalir;
    private javax.swing.JMenuItem itemSiguiente;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane31;
    private javax.swing.JScrollPane jScrollPane32;
    private javax.swing.JScrollPane jScrollPane34;
    private javax.swing.JScrollPane jScrollPane35;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lb_empleado3;
    private javax.swing.JLabel lb_objetos_asignables2;
    private javax.swing.JLabel lb_objetos_asignables3;
    private javax.swing.JLabel lb_objetos_asignables4;
    private javax.swing.JLabel lb_objetos_asignados1;
    private javax.swing.JLabel lb_objetos_entregados1;
    private javax.swing.JLabel lblArea;
    private javax.swing.JLabel lblCargo;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCurp;
    private javax.swing.JLabel lblDomicilio;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblLocalidad;
    private javax.swing.JLabel lblMunicipio;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblRfc;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JPanel manejo_inventario;
    private javax.swing.JMenu menuAsignar;
    private javax.swing.JMenu menuOpciones;
    private javax.swing.JMenuItem menuPermisos;
    private javax.swing.JMenuItem menuPuestoArea;
    private javax.swing.JMenuItem mi_viaticos;
    private javax.swing.JPanel panelEmpleado;
    private javax.swing.JPanel pestañaInventario;
    private javax.swing.JPanel pn_acciones1;
    private javax.swing.JPanel pn_asignacion_inventario1;
    private javax.swing.JPanel pn_contenedor_ventanas1;
    private javax.swing.JPanel pn_recoleccion_inventario1;
    private javax.swing.JPanel pn_tablaUsuarios;
    private javax.swing.JRadioButton radioEquipo;
    private javax.swing.JRadioButton radioGranel;
    private javax.swing.JRadioButton rb_asignacion1;
    public javax.swing.JRadioButton rb_inventario_granel1;
    public javax.swing.JRadioButton rb_inventario_normal1;
    public javax.swing.JRadioButton rb_recoleccion1;
    private javax.swing.JPanel solicitudes;
    private javax.swing.JScrollPane sp_asignacion_inventario1;
    private javax.swing.JScrollPane sp_recoleccion_inventario1;
    public static javax.swing.JTabbedPane tabbedPrincipal;
    public static javax.swing.JTable tablaAsignacionPersonal;
    private javax.swing.JTable tablaBD;
    private javax.swing.JTable tablaIP;
    public static javax.swing.JTable tablaInventario;
    public javax.swing.JTable tablaMAsignados;
    public static javax.swing.JTable tablaMInventarioA;
    public javax.swing.JTable tablaObjetosEntregados;
    private javax.swing.JTable tablaPermisosPersonales;
    public javax.swing.JTable tablaRecoleccion;
    public static javax.swing.JTable tablaResguardo;
    public static javax.swing.JTable tablaSolicitudes;
    public static javax.swing.JTable tablaSolicitudesPersonal;
    public static javax.swing.JTable tablaStockMin;
    public static javax.swing.JTable tablaUsuarios;
    public static javax.swing.JTable tablaVehiculos;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtBusquedaUsuario;
    private javax.swing.JTextField txtBusquedaVehiculos;
    private javax.swing.JPanel usuarios;
    private javax.swing.JPanel vehiculos;
    private javax.swing.JButton zoom;
    // End of variables declaration//GEN-END:variables
}
