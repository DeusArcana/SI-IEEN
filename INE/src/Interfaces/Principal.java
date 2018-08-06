/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;


import Clases.Archivo;
import Clases.CrearValeRecoleccionDeBienes;
import Clases.CrearValeResguardoBienes;
import Clases.CrearValeSalidaAlmacen;
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
import Clases.ManagerInventarioGranel;
import Clases.ManagerMySQL;
import Clases.ManagerPermisos;
import Clases.ManagerSolicitud;
import Clases.ManagerComplemento;
import Clases.ManagerVehiculos;
import Clases.ManejadorInventario;
import Clases.ManagerDocumentos;
import Clases.Excel;
import Clases.ManagerInventarioGranel;

//Importamos los formularios
import Formularios.addEmpleados;
import Formularios.addInventario;
import Formularios.addInventarioGranel;
import Formularios.addResguardo;
import Formularios.addUsuarios;
import Formularios.addDocument;
import Formularios.changePassword;
import Formularios.updateEmpleado;
import Formularios.updateInventario;
import Formularios.updateInventarioGranel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.sql.SQLException;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JSpinner;
import static Interfaces.ventana_modificar_vehiculo.campo;
import com.itextpdf.text.DocumentException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

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
    ManagerInventarioGranel manager_inventario_granel;
    ManagerDocumentos manager_asignar;
    
    
    int contadorRutas, nFoto;
    String banderaFoto, auxiliarFoto;
    
    Thread hilo;
    boolean bandera;
    
    public String Responsable,Cargo,Area,Tipo_de_uso,Municipio,Localidad,Responsable1,Cargo1,Area1,Tipo_de_uso1,Municipio1,Localidad1,idAtenderSalida;
    
    
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
    
    //Esta bandera es para desplegar el menu de aceptar o cancelar cuando se quiere cambiar el estatus del inventario
    public static int activarConfirmacion = 2;
    
    //Esta bandera es para saber si necesita volver a iniciar componentes o no (Asignación)
    public static int refrescarResguardoAsignacion = 2;
    
    //Esta bandera es para saber si necesita volver a iniciar componentes o no (Recolección)
    public static int refrescarResguardoRecoleccion = 2;
    
    /*PESTAÑA DE MANEJADOR KEVIN*/
    //Esta bandera es para conocer si selecciono un empleado al momento de querer asignar equipos
    boolean empleadoSeleccionado = false;
    //Esta bandera es saber si son a granel o no
    boolean esGranel = false;
    
    
    //VARIABLES GLOBALES
    public static String usuario = "",prodInventario = "",UserUpdate = "",estadoPendiente = "",estadoSolicitud = "",Username = "",productoAsignacionReemplazo = "",productoAREstado = "",empleadoSolicitud = "",pendientePara="";
    public static int idPendiente,productoIDVale;
    public DefaultTableModel tablaGranel,tablaNormal,modeloRecoleccion,modeloObjetosEntregados,modeloCantidadSalida;
    public static String Claves[],nomeclaturas[];
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
        manager_asignar = new ManagerDocumentos();
        manager_inventario_granel = new ManagerInventarioGranel();
        
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
        tablaResguardoPersonal.getTableHeader().setReorderingAllowed(false);
        tablaMInventarioA.getTableHeader().setReorderingAllowed(false);
        tablaMAsignados.getTableHeader().setReorderingAllowed(false);
        nFoto = 0;
        //Obtenemos el modelo de la tabla 
        modeloTablaIP = (DefaultTableModel) tablaIP.getModel();
        tablaNormal = (DefaultTableModel) tablaMAsignados.getModel();
        modeloRecoleccion = (DefaultTableModel) tablaRecoleccion.getModel();
        modeloCantidadSalida = (DefaultTableModel) tablaCantidadGranel.getModel();
        
        //Añadimos el modelo que queremos que tenga la tabla cuando se vaya a atender una solicitud de salida de almacen
        DefaultTableModel table = new DefaultTableModel();
        table.addColumn("Cantidad con letra");
        table.addColumn("Autorizó");
        table.addColumn("Solicitó");
        table.addColumn("Articulo");
        table.addColumn("Descripción");
        tablaGranel = table;
        
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

        MenuInventario = new javax.swing.JPopupMenu();
        ActualizarProd = new javax.swing.JMenuItem();
        AddObservacion = new javax.swing.JMenuItem();
        DevolverDis = new javax.swing.JMenuItem();
        Pendiente = new javax.swing.JMenu();
        ParaBaja = new javax.swing.JMenuItem();
        ParaComodato = new javax.swing.JMenuItem();
        ParaDonacion = new javax.swing.JMenuItem();
        NoDisponible = new javax.swing.JMenuItem();
        ExcelInventario = new javax.swing.JMenuItem();
        EstatusDefinitivo = new javax.swing.JMenuItem();
        MenuInventarioG = new javax.swing.JPopupMenu();
        ActualizarConsumible = new javax.swing.JMenuItem();
        AddStock = new javax.swing.JMenuItem();
        ExcelConsumibles = new javax.swing.JMenuItem();
        MenuConfirmacion = new javax.swing.JPopupMenu();
        AceptarEstatus = new javax.swing.JMenuItem();
        CancelarEstatus = new javax.swing.JMenuItem();
        MenuEmpleados = new javax.swing.JPopupMenu();
        Actualizar = new javax.swing.JMenuItem();
        Asignar_usuario = new javax.swing.JMenuItem();
        activarE = new javax.swing.JMenuItem();
        bajaE = new javax.swing.JMenuItem();
        ExportarEmpleados = new javax.swing.JMenuItem();
        MenuUsuarios = new javax.swing.JPopupMenu();
        dar_baja = new javax.swing.JMenuItem();
        activar = new javax.swing.JMenuItem();
        Permisos = new javax.swing.JMenuItem();
        ActualizarEmployee = new javax.swing.JMenuItem();
        ExportarUsuarios = new javax.swing.JMenuItem();
        MenuVehiculos = new javax.swing.JPopupMenu();
        ActualizarV = new javax.swing.JMenuItem();
        SolictarMas = new javax.swing.JMenu();
        SolicitarBaja = new javax.swing.JMenuItem();
        Servicio = new javax.swing.JMenuItem();
        ActualizarInfoV = new javax.swing.JMenuItem();
        MenuResguardoPersonal = new javax.swing.JPopupMenu();
        QuitarResguardo = new javax.swing.JMenuItem();
        MenuAsginados = new javax.swing.JPopupMenu();
        CancelarA = new javax.swing.JMenuItem();
        MenuSolicitudes = new javax.swing.JPopupMenu();
        Atender = new javax.swing.JMenuItem();
        CancelarSolicitud = new javax.swing.JMenuItem();
        MenuStockMin = new javax.swing.JPopupMenu();
        AgregarStock = new javax.swing.JMenuItem();
        ActualizarInfoSM = new javax.swing.JMenuItem();
        MenuSolicitudesP = new javax.swing.JPopupMenu();
        ActualizarInfoSP = new javax.swing.JMenuItem();
        MenuAsignacionP = new javax.swing.JPopupMenu();
        ActualizarAsignacionP = new javax.swing.JMenuItem();
        MenuPermisosP = new javax.swing.JPopupMenu();
        ActualizarInfoPP = new javax.swing.JMenuItem();
        MenuSolicitarSalida = new javax.swing.JPopupMenu();
        CancelarSS = new javax.swing.JMenuItem();
        Grupo1 = new javax.swing.ButtonGroup();
        bg_manejo_inventario = new javax.swing.ButtonGroup();
        bt_tipo_inventario_asignable = new javax.swing.ButtonGroup();
        GrupoTipoInventario = new javax.swing.ButtonGroup();
        tabbedPrincipal = new javax.swing.JTabbedPane();
        pestañaInventario = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblProductosTotales = new javax.swing.JLabel();
        comboFiltro = new javax.swing.JComboBox<>();
        comboEstatus = new javax.swing.JComboBox<>();
        comboFolio = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaInventario = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnAddInventario = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        comboInventario = new javax.swing.JComboBox<>();
        txtBusqueda = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        usuarios = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUsuarios = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        comboEmpUsuEstatus = new javax.swing.JComboBox<>();
        btnAddEmpleado = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtBusquedaUsuario = new javax.swing.JTextField();
        comboFiltroUsuario = new javax.swing.JComboBox<>();
        comboEmpUsu = new javax.swing.JComboBox<>();
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
        izquierdaBtn = new javax.swing.JButton();
        derechaBtn = new javax.swing.JButton();
        imagenVehiculo = new javax.swing.JLabel();
        btnAñadirVehiculo = new javax.swing.JButton();
        comboFiltroVehiculos = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        txtBusquedaVehiculos = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
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
        tablaResguardoPersonal = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
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
        jLabel10 = new javax.swing.JLabel();
        resguardo = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        pn_contenedor_ventanas1 = new javax.swing.JPanel();
        sp_asignacion_inventario1 = new javax.swing.JScrollPane();
        pn_asignacion_inventario1 = new javax.swing.JPanel();
        lb_objetos_asignables2 = new javax.swing.JLabel();
        jScrollPane31 = new javax.swing.JScrollPane();
        tablaMAsignados = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        lb_objetos_asignables3 = new javax.swing.JLabel();
        jScrollPane32 = new javax.swing.JScrollPane();
        tablaMInventarioA = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        btn_generar_vale3 = new javax.swing.JButton();
        btn_cancelar2 = new javax.swing.JButton();
        rb_inventario_normal1 = new javax.swing.JRadioButton();
        rb_inventario_granel1 = new javax.swing.JRadioButton();
        comboEmpleado = new javax.swing.JComboBox<>();
        lb_objetos_asignables4 = new javax.swing.JLabel();
        comboFolioAsignacion = new javax.swing.JComboBox<>();
        comboArea = new javax.swing.JComboBox<>();
        lblAreaAsignacion = new javax.swing.JLabel();
        sp_recoleccion_inventario1 = new javax.swing.JScrollPane();
        pn_recoleccion_inventario1 = new javax.swing.JPanel();
        comboArea1 = new javax.swing.JComboBox<>();
        lblAreaAsignacion1 = new javax.swing.JLabel();
        lb_empleado3 = new javax.swing.JLabel();
        lb_objetos_asignados1 = new javax.swing.JLabel();
        jScrollPane34 = new javax.swing.JScrollPane();
        tablaRecoleccion = new javax.swing.JTable();
        comboEmpleadoR = new javax.swing.JComboBox<>();
        btnEntregarRecoleccion = new javax.swing.JButton();
        lblAreaAsignacion2 = new javax.swing.JLabel();
        comboAño = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        pn_acciones1 = new javax.swing.JPanel();
        rb_asignacion1 = new javax.swing.JRadioButton();
        rb_recoleccion1 = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        solicitar_granel = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        pn_tablaUsuarios1 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        tablaSolicitarGranel = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jScrollPane18 = new javax.swing.JScrollPane();
        tablaCantidadGranel = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        comboFiltroConsumibles = new javax.swing.JComboBox<>();
        txtBusquedaConsumibles = new javax.swing.JTextField();
        btnCancelarSalida = new javax.swing.JButton();
        btnSolicitarSalida = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        comboFiltroUsuario1 = new javax.swing.JComboBox<>();
        fondo1 = new javax.swing.JLabel();
        solicitudes = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaSolicitudes = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jScrollPane12 = new javax.swing.JScrollPane();
        tablaStockMin = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jLabel9 = new javax.swing.JLabel();
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
        Documentos = new javax.swing.JMenu();
        AddDocuments = new javax.swing.JMenuItem();
        SelectProducts = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        ActualizarProd.setText("Actualizar");
        ActualizarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarProdActionPerformed(evt);
            }
        });
        MenuInventario.add(ActualizarProd);

        AddObservacion.setText("Añadir observaciones");
        MenuInventario.add(AddObservacion);

        DevolverDis.setText("Devolver a disponible");
        DevolverDis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DevolverDisActionPerformed(evt);
            }
        });
        MenuInventario.add(DevolverDis);

        Pendiente.setText("Pendiente");

        ParaBaja.setText("para baja");
        ParaBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ParaBajaActionPerformed(evt);
            }
        });
        Pendiente.add(ParaBaja);

        ParaComodato.setText("para comodato");
        ParaComodato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ParaComodatoActionPerformed(evt);
            }
        });
        Pendiente.add(ParaComodato);

        ParaDonacion.setText("para donación");
        ParaDonacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ParaDonacionActionPerformed(evt);
            }
        });
        Pendiente.add(ParaDonacion);

        MenuInventario.add(Pendiente);

        NoDisponible.setText("Cambiar a no disponible");
        NoDisponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoDisponibleActionPerformed(evt);
            }
        });
        MenuInventario.add(NoDisponible);

        ExcelInventario.setText("Exportar a excel");
        ExcelInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExcelInventarioActionPerformed(evt);
            }
        });
        MenuInventario.add(ExcelInventario);

        EstatusDefinitivo.setText("Cambiar a ");
        EstatusDefinitivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EstatusDefinitivoActionPerformed(evt);
            }
        });
        MenuInventario.add(EstatusDefinitivo);

        ActualizarConsumible.setText("Actualizar consumible");
        ActualizarConsumible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarConsumibleActionPerformed(evt);
            }
        });
        MenuInventarioG.add(ActualizarConsumible);

        AddStock.setText("Agregar unidades");
        AddStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddStockActionPerformed(evt);
            }
        });
        MenuInventarioG.add(AddStock);

        ExcelConsumibles.setText("Exportar a excel");
        ExcelConsumibles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExcelConsumiblesActionPerformed(evt);
            }
        });
        MenuInventarioG.add(ExcelConsumibles);

        AceptarEstatus.setText("Aceptar");
        AceptarEstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarEstatusActionPerformed(evt);
            }
        });
        MenuConfirmacion.add(AceptarEstatus);

        CancelarEstatus.setText("Cancelar");
        CancelarEstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarEstatusActionPerformed(evt);
            }
        });
        MenuConfirmacion.add(CancelarEstatus);

        Actualizar.setText("Actualizar");
        Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarActionPerformed(evt);
            }
        });
        MenuEmpleados.add(Actualizar);

        Asignar_usuario.setText("Asignar usuario");
        Asignar_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Asignar_usuarioActionPerformed(evt);
            }
        });
        MenuEmpleados.add(Asignar_usuario);

        activarE.setText("Activar");
        activarE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activarEActionPerformed(evt);
            }
        });
        MenuEmpleados.add(activarE);

        bajaE.setText("Baja");
        bajaE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bajaEActionPerformed(evt);
            }
        });
        MenuEmpleados.add(bajaE);

        ExportarEmpleados.setText("Exportar a excel");
        ExportarEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportarEmpleadosActionPerformed(evt);
            }
        });
        MenuEmpleados.add(ExportarEmpleados);

        dar_baja.setText("Baja de usuario");
        dar_baja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dar_bajaActionPerformed(evt);
            }
        });
        MenuUsuarios.add(dar_baja);

        activar.setText("Activar usuario");
        activar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activarActionPerformed(evt);
            }
        });
        MenuUsuarios.add(activar);

        Permisos.setText("Permisos");
        Permisos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PermisosActionPerformed(evt);
            }
        });
        MenuUsuarios.add(Permisos);

        ActualizarEmployee.setText("Actualizar");
        ActualizarEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarEmployeeActionPerformed(evt);
            }
        });
        MenuUsuarios.add(ActualizarEmployee);

        ExportarUsuarios.setText("Exportar a excel");
        ExportarUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportarUsuariosActionPerformed(evt);
            }
        });
        MenuUsuarios.add(ExportarUsuarios);

        ActualizarV.setText("Actualizar");
        ActualizarV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarVActionPerformed(evt);
            }
        });
        MenuVehiculos.add(ActualizarV);

        SolictarMas.setText("Solicitar...");

        SolicitarBaja.setText("Solicitud baja/comodato/donación");
        SolicitarBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SolicitarBajaActionPerformed(evt);
            }
        });
        SolictarMas.add(SolicitarBaja);

        Servicio.setText("Servicio");
        Servicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ServicioActionPerformed(evt);
            }
        });
        SolictarMas.add(Servicio);

        MenuVehiculos.add(SolictarMas);

        ActualizarInfoV.setText("Refrescar tabla");
        ActualizarInfoV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarInfoVActionPerformed(evt);
            }
        });
        MenuVehiculos.add(ActualizarInfoV);

        QuitarResguardo.setText("Salida del producto");
        QuitarResguardo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuitarResguardoActionPerformed(evt);
            }
        });
        MenuResguardoPersonal.add(QuitarResguardo);

        CancelarA.setText("Cancelar");
        CancelarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarAActionPerformed(evt);
            }
        });
        MenuAsginados.add(CancelarA);

        Atender.setText("Atender...");
        Atender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtenderActionPerformed(evt);
            }
        });
        MenuSolicitudes.add(Atender);

        CancelarSolicitud.setText("Cancelar");
        CancelarSolicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarSolicitudActionPerformed(evt);
            }
        });
        MenuSolicitudes.add(CancelarSolicitud);

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

        ActualizarInfoSP.setText("Refrescar");
        ActualizarInfoSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarInfoSPActionPerformed(evt);
            }
        });
        MenuSolicitudesP.add(ActualizarInfoSP);

        ActualizarAsignacionP.setText("Refrescar");
        ActualizarAsignacionP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarAsignacionPActionPerformed(evt);
            }
        });
        MenuAsignacionP.add(ActualizarAsignacionP);

        ActualizarInfoPP.setText("Refrescar");
        ActualizarInfoPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarInfoPPActionPerformed(evt);
            }
        });
        MenuPermisosP.add(ActualizarInfoPP);

        CancelarSS.setText("Cancelar");
        CancelarSS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarSSActionPerformed(evt);
            }
        });
        MenuSolicitarSalida.add(CancelarSS);

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
        tabbedPrincipal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabbedPrincipalFocusGained(evt);
            }
        });
        tabbedPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabbedPrincipalMouseClicked(evt);
            }
        });

        pestañaInventario.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N

        jPanel3.setLayout(null);

        lblProductosTotales.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblProductosTotales.setText("Productos totales: 0");
        jPanel3.add(lblProductosTotales);
        lblProductosTotales.setBounds(150, 40, 300, 17);

        comboFiltro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No. de Inventario", "Nombre corto", "Descripción", "Ubicación", "Marca", "Observaciones", "No. Serie", "Modelo", "Color", "Fecha de compra", "Factura" }));
        comboFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFiltroActionPerformed(evt);
            }
        });
        jPanel3.add(comboFiltro);
        comboFiltro.setBounds(150, 90, 220, 30);

        comboEstatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboEstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disponible", "No disponible", "para baja", "para donación", "para comodato", "Baja", "Donación", "Comodato", "Asignado" }));
        comboEstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEstatusActionPerformed(evt);
            }
        });
        jPanel3.add(comboEstatus);
        comboEstatus.setBounds(940, 90, 200, 30);

        comboFolio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboFolio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFolioActionPerformed(evt);
            }
        });
        jPanel3.add(comboFolio);
        comboFolio.setBounds(700, 90, 220, 30);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/baner inventario.png"))); // NOI18N
        jPanel3.add(jLabel5);
        jLabel5.setBounds(10, 10, 1350, 80);

        tablaInventario.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tablaInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

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
        comboInventario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inventario", "Consumibles", "Resguardo" }));
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
                .addContainerGap(290, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel1);
        jPanel1.setBounds(1150, 130, 200, 500);

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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        jPanel3.add(jLabel1);
        jLabel1.setBounds(0, 0, 1367, 769);

        javax.swing.GroupLayout pestañaInventarioLayout = new javax.swing.GroupLayout(pestañaInventario);
        pestañaInventario.setLayout(pestañaInventarioLayout);
        pestañaInventarioLayout.setHorizontalGroup(
            pestañaInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1397, Short.MAX_VALUE)
        );
        pestañaInventarioLayout.setVerticalGroup(
            pestañaInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("  Inventario", new javax.swing.ImageIcon(getClass().getResource("/Iconos/inventario.png")), pestañaInventario); // NOI18N

        jPanel5.setLayout(null);

        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre(s)", "Apellido Paterno", "Apellido Materno", "Área"
            }
        ));
        tablaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaUsuariosMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tablaUsuarios);

        jPanel5.add(jScrollPane1);
        jScrollPane1.setBounds(30, 150, 1240, 402);

        comboEmpUsuEstatus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboEmpUsuEstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Activo", "Baja" }));
        comboEmpUsuEstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEmpUsuEstatusActionPerformed(evt);
            }
        });
        jPanel5.add(comboEmpUsuEstatus);
        comboEmpUsuEstatus.setBounds(700, 100, 180, 28);

        btnAddEmpleado.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        btnAddEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/agregar.png"))); // NOI18N
        btnAddEmpleado.setText("  Agregar");
        btnAddEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmpleadoActionPerformed(evt);
            }
        });
        jPanel5.add(btnAddEmpleado);
        btnAddEmpleado.setBounds(1130, 100, 140, 30);

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
        comboFiltroUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Apellido P", "Apellido M", "Area", "Puesto" }));
        comboFiltroUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFiltroUsuarioActionPerformed(evt);
            }
        });
        jPanel5.add(comboFiltroUsuario);
        comboFiltroUsuario.setBounds(150, 100, 210, 28);

        comboEmpUsu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboEmpUsu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Empleados sin usuario", "Empleados", "Usuarios" }));
        comboEmpUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEmpUsuActionPerformed(evt);
            }
        });
        jPanel5.add(comboEmpUsu);
        comboEmpUsu.setBounds(900, 100, 210, 28);

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        jPanel5.add(fondo);
        fondo.setBounds(0, 0, 1367, 769);

        javax.swing.GroupLayout usuariosLayout = new javax.swing.GroupLayout(usuarios);
        usuarios.setLayout(usuariosLayout);
        usuariosLayout.setHorizontalGroup(
            usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1397, Short.MAX_VALUE)
        );
        usuariosLayout.setVerticalGroup(
            usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("Empleados", new javax.swing.ImageIcon(getClass().getResource("/Iconos/usuarios.png")), usuarios); // NOI18N

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

        izquierdaBtn.setText("<");
        izquierdaBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        izquierdaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                izquierdaBtnActionPerformed(evt);
            }
        });
        fondoVehiculo.add(izquierdaBtn);
        izquierdaBtn.setBounds(160, 200, 40, 23);

        derechaBtn.setText(">");
        derechaBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        derechaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                derechaBtnActionPerformed(evt);
            }
        });
        fondoVehiculo.add(derechaBtn);
        derechaBtn.setBounds(210, 200, 41, 23);

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
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1397, Short.MAX_VALUE)
        );
        vehiculosLayout.setVerticalGroup(
            vehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("  Vehiculos", new javax.swing.ImageIcon(getClass().getResource("/Iconos/vehiculos.png")), vehiculos); // NOI18N

        empleado.setComponentPopupMenu(MenuSolicitudesP);

        jPanel9.setLayout(null);

        lblNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNombre.setText("Nombre");

        lblCargo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCargo.setText("Puesto");

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

        tablaResguardoPersonal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Fecha de ingreso", "Observaciones"
            }
        ));
        tablaResguardoPersonal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaResguardoPersonalMouseReleased(evt);
            }
        });
        jScrollPane7.setViewportView(tablaResguardoPersonal);

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
        jLabel27.setText("Inventario asignado");

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
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE)
                            .addComponent(jScrollPane9))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(104, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addGap(1090, 1090, 1090))
            .addGroup(panelEmpleadoLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        ScrollEmpleado.setViewportView(panelEmpleado);

        jPanel9.add(ScrollEmpleado);
        ScrollEmpleado.setBounds(10, 20, 1340, 830);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        jPanel9.add(jLabel10);
        jLabel10.setBounds(0, 0, 1400, 769);

        javax.swing.GroupLayout empleadoLayout = new javax.swing.GroupLayout(empleado);
        empleado.setLayout(empleadoLayout);
        empleadoLayout.setHorizontalGroup(
            empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 1397, Short.MAX_VALUE)
        );
        empleadoLayout.setVerticalGroup(
            empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empleadoLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 326, Short.MAX_VALUE))
        );

        tabbedPrincipal.addTab("Empleado", new javax.swing.ImageIcon(getClass().getResource("/Iconos/vehiculos.png")), empleado); // NOI18N

        resguardo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resguardoMouseClicked(evt);
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
                "Clave", "Nombre", "Descripción", "Ubicación", "Cantidad"
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
        btn_cancelar2.setEnabled(false);
        btn_cancelar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelar2ActionPerformed(evt);
            }
        });

        bt_tipo_inventario_asignable.add(rb_inventario_normal1);
        rb_inventario_normal1.setSelected(true);
        rb_inventario_normal1.setText("Normal");
        rb_inventario_normal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_inventario_normal1ActionPerformed(evt);
            }
        });

        bt_tipo_inventario_asignable.add(rb_inventario_granel1);
        rb_inventario_granel1.setText("Granel");
        rb_inventario_granel1.setEnabled(false);
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

        comboFolioAsignacion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboFolioAsignacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFolioAsignacionActionPerformed(evt);
            }
        });

        comboArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboArea.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAreaActionPerformed(evt);
            }
        });

        lblAreaAsignacion.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lblAreaAsignacion.setText("Área:");

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
                        .addComponent(comboEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAreaAsignacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pn_asignacion_inventario1Layout.createSequentialGroup()
                            .addComponent(btn_cancelar2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btn_generar_vale3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pn_asignacion_inventario1Layout.createSequentialGroup()
                            .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(pn_asignacion_inventario1Layout.createSequentialGroup()
                                    .addComponent(jScrollPane32, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(29, 29, 29))
                                .addGroup(pn_asignacion_inventario1Layout.createSequentialGroup()
                                    .addComponent(lb_objetos_asignables2)
                                    .addGap(8, 8, 8)
                                    .addComponent(rb_inventario_normal1)
                                    .addGap(10, 10, 10)
                                    .addComponent(rb_inventario_granel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(comboFolioAsignacion, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(70, 70, 70)))
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
                    .addComponent(lb_objetos_asignables4)
                    .addComponent(comboArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAreaAsignacion))
                .addGap(18, 18, 18)
                .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_objetos_asignables2)
                        .addComponent(rb_inventario_normal1)
                        .addComponent(rb_inventario_granel1))
                    .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_objetos_asignables3)
                        .addComponent(comboFolioAsignacion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane31, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                    .addComponent(jScrollPane32, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_asignacion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_generar_vale3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cancelar2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(149, Short.MAX_VALUE))
        );

        sp_asignacion_inventario1.setViewportView(pn_asignacion_inventario1);

        pn_contenedor_ventanas1.add(sp_asignacion_inventario1, "c_s_asignacion");

        comboArea1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboArea1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboArea1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboArea1ActionPerformed(evt);
            }
        });

        lblAreaAsignacion1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lblAreaAsignacion1.setText("Área:");

        lb_empleado3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_empleado3.setText("Empleado:");

        lb_objetos_asignados1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_objetos_asignados1.setText("Objetos Asignados");

        tablaRecoleccion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Entregar", "Vale", "Clave", "Nombre corto", "Descripción", "Marca", "No. Serie", "Modelo", "Observaciones", "Ubicación Actual", "Nueva Ubicación"
            }
        ));
        tablaRecoleccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaRecoleccionMouseReleased(evt);
            }
        });
        jScrollPane34.setViewportView(tablaRecoleccion);

        comboEmpleadoR.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboEmpleadoR.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboEmpleadoR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEmpleadoRActionPerformed(evt);
            }
        });

        btnEntregarRecoleccion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEntregarRecoleccion.setText("Entregar");
        btnEntregarRecoleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntregarRecoleccionActionPerformed(evt);
            }
        });

        lblAreaAsignacion2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lblAreaAsignacion2.setText("Año:");

        comboAño.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboAño.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAñoActionPerformed(evt);
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
                        .addComponent(comboEmpleadoR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAreaAsignacion1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboArea1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAreaAsignacion2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(309, 309, 309)
                        .addComponent(btnEntregarRecoleccion))
                    .addComponent(lb_objetos_asignados1)
                    .addComponent(jScrollPane34, javax.swing.GroupLayout.PREFERRED_SIZE, 1281, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        pn_recoleccion_inventario1Layout.setVerticalGroup(
            pn_recoleccion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_recoleccion_inventario1Layout.createSequentialGroup()
                .addGroup(pn_recoleccion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_recoleccion_inventario1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(pn_recoleccion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_recoleccion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblAreaAsignacion2))
                            .addGroup(pn_recoleccion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboArea1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblAreaAsignacion1))
                            .addGroup(pn_recoleccion_inventario1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lb_empleado3)
                                .addComponent(comboEmpleadoR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_recoleccion_inventario1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnEntregarRecoleccion)
                        .addGap(1, 1, 1)))
                .addComponent(lb_objetos_asignados1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane34, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(323, Short.MAX_VALUE))
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

        javax.swing.GroupLayout resguardoLayout = new javax.swing.GroupLayout(resguardo);
        resguardo.setLayout(resguardoLayout);
        resguardoLayout.setHorizontalGroup(
            resguardoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 1397, Short.MAX_VALUE)
        );
        resguardoLayout.setVerticalGroup(
            resguardoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("Resguardo", new javax.swing.ImageIcon(getClass().getResource("/Iconos/configuracion.png")), resguardo); // NOI18N

        jPanel12.setLayout(null);

        pn_tablaUsuarios1.setBackground(new java.awt.Color(255, 255, 255));
        pn_tablaUsuarios1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tablaSolicitarGranel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Clave", "Nombre corto", "Descripción"
            }
        ));
        tablaSolicitarGranel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaSolicitarGranelMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaSolicitarGranelMouseReleased(evt);
            }
        });
        jScrollPane17.setViewportView(tablaSolicitarGranel);

        tablaCantidadGranel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Consumible", "Nombre corto", "Descripción", "Cantidad"
            }
        ));
        tablaCantidadGranel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaCantidadGranelMouseReleased(evt);
            }
        });
        jScrollPane18.setViewportView(tablaCantidadGranel);

        javax.swing.GroupLayout pn_tablaUsuarios1Layout = new javax.swing.GroupLayout(pn_tablaUsuarios1);
        pn_tablaUsuarios1.setLayout(pn_tablaUsuarios1Layout);
        pn_tablaUsuarios1Layout.setHorizontalGroup(
            pn_tablaUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_tablaUsuarios1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );
        pn_tablaUsuarios1Layout.setVerticalGroup(
            pn_tablaUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_tablaUsuarios1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_tablaUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_tablaUsuarios1Layout.createSequentialGroup()
                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        jPanel12.add(pn_tablaUsuarios1);
        pn_tablaUsuarios1.setBounds(20, 150, 1330, 500);

        comboFiltroConsumibles.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboFiltroConsumibles.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No. Consumible", "Nombre", "Descripción" }));
        comboFiltroConsumibles.setToolTipText("");
        comboFiltroConsumibles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFiltroConsumiblesActionPerformed(evt);
            }
        });
        jPanel12.add(comboFiltroConsumibles);
        comboFiltroConsumibles.setBounds(30, 100, 180, 30);

        txtBusquedaConsumibles.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtBusquedaConsumibles.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaConsumiblesKeyReleased(evt);
            }
        });
        jPanel12.add(txtBusquedaConsumibles);
        txtBusquedaConsumibles.setBounds(230, 100, 240, 30);

        btnCancelarSalida.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        btnCancelarSalida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/agregar.png"))); // NOI18N
        btnCancelarSalida.setText("Cancelar");
        btnCancelarSalida.setEnabled(false);
        btnCancelarSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarSalidaActionPerformed(evt);
            }
        });
        jPanel12.add(btnCancelarSalida);
        btnCancelarSalida.setBounds(1100, 100, 140, 30);

        btnSolicitarSalida.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        btnSolicitarSalida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/agregar.png"))); // NOI18N
        btnSolicitarSalida.setText("Solicitar");
        btnSolicitarSalida.setEnabled(false);
        btnSolicitarSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolicitarSalidaActionPerformed(evt);
            }
        });
        jPanel12.add(btnSolicitarSalida);
        btnSolicitarSalida.setBounds(890, 100, 140, 30);

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/baner Usuarios.png"))); // NOI18N
        jPanel12.add(jLabel21);
        jLabel21.setBounds(10, 10, 1350, 80);

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel32.setText("Categoria:");
        jPanel12.add(jLabel32);
        jLabel32.setBounds(560, 100, 90, 22);

        comboFiltroUsuario1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboFiltroUsuario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFiltroUsuario1ActionPerformed(evt);
            }
        });
        jPanel12.add(comboFiltroUsuario1);
        comboFiltroUsuario1.setBounds(650, 100, 210, 28);

        fondo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        jPanel12.add(fondo1);
        fondo1.setBounds(0, 0, 1367, 769);

        javax.swing.GroupLayout solicitar_granelLayout = new javax.swing.GroupLayout(solicitar_granel);
        solicitar_granel.setLayout(solicitar_granelLayout);
        solicitar_granelLayout.setHorizontalGroup(
            solicitar_granelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 1397, Short.MAX_VALUE)
        );
        solicitar_granelLayout.setVerticalGroup(
            solicitar_granelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("Solicitar consumibles", new javax.swing.ImageIcon(getClass().getResource("/Iconos/usuarios.png")), solicitar_granel); // NOI18N

        solicitudes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                solicitudesFocusGained(evt);
            }
        });

        jPanel7.setLayout(null);

        tablaSolicitudes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Solicitud", "Usuario que solicito", "Fecha que se solicito", "Estado"
            }
        ));
        tablaSolicitudes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaSolicitudesMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tablaSolicitudes);

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

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 1251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel8);
        jPanel8.setBounds(20, 20, 1270, 650);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        jPanel7.add(jLabel9);
        jLabel9.setBounds(0, 0, 1367, 769);

        javax.swing.GroupLayout solicitudesLayout = new javax.swing.GroupLayout(solicitudes);
        solicitudes.setLayout(solicitudesLayout);
        solicitudesLayout.setHorizontalGroup(
            solicitudesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 1397, Short.MAX_VALUE)
        );
        solicitudesLayout.setVerticalGroup(
            solicitudesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("Solicitudes", new javax.swing.ImageIcon(getClass().getResource("/Iconos/vehiculos.png")), solicitudes); // NOI18N

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

        campoip3.setModel(new javax.swing.SpinnerNumberModel(0, 0, 255, 1));

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
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1397, Short.MAX_VALUE)
        );
        configuracionLayout.setVerticalGroup(
            configuracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("  Configuración", new javax.swing.ImageIcon(getClass().getResource("/Iconos/configuracion.png")), configuracion); // NOI18N

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

        jMenuBar1.add(menuOpciones);

        Documentos.setText("Documentos");

        AddDocuments.setText("Nuevo documento");
        AddDocuments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddDocumentsActionPerformed(evt);
            }
        });
        Documentos.add(AddDocuments);

        SelectProducts.setText("Seleccionar productos");
        SelectProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectProductsActionPerformed(evt);
            }
        });
        Documentos.add(SelectProducts);

        jMenuItem1.setText("Consultar documentos");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        Documentos.add(jMenuItem1);

        jMenuBar1.add(Documentos);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPrincipal)
                .addContainerGap())
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
            cancelarVale();
            System.exit(0);
        }else if(opcion == 1){
            //Cerrar sesion
            this.dispose();
            Login ob = new Login();
            ob.setVisible(true);   
        }
    }//GEN-LAST:event_formWindowClosing
    
    private void comboNomeclaturasFolio(JComboBox combo){
        String lista = manager_inventario.nomeclaturaFolio();
        String [] folios = lista.split(",");
        nomeclaturas = new String[folios.length/2];
        combo.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        combo.addItem("Todos");
        for(int i = 1,j = 0; i <= folios.length;i = i+2,j++){
            combo.addItem(folios[i]);
            nomeclaturas[j] = folios[i-1];
        }
    }//comboNomeclaturasFolio
    
    private void realizarResguardoAsignacion(){
        if(manager_permisos.accesoModulo("consulta","Resguardo",Username)){
            if(refrescarResguardoAsignacion == 2){
                comboNomeclaturasFolio(comboFolioAsignacion);

                //ComboArea
                String lista = manager_complemento.obtenerAreas();
                String[] recoger = lista.split(",,");

                comboArea.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
                for(int i = 1; i <= recoger.length;i = i+2){
                    comboArea.addItem(recoger[i]);
                }
                tablaMInventarioA.setModel(manejador_inventario.getInventarioParaAsignacion(""));
                refrescarResguardoAsignacion = 1;
                
                //Desbloqueamos el uso de la asignacion
                comboEmpleado.setEnabled(true);
                tablaMInventarioA.setEnabled(true);
                btn_generar_vale3.setEnabled(true);
                btn_cancelar2.setEnabled(true);
                comboArea.setEnabled(true);
                comboFolioAsignacion.setEnabled(true);
            }//if(refrescarResguardoAsignacion == 2)
        }else{
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultas para el resguardo de asignación.");
            refrescarResguardoAsignacion = 2;
            
            //Bloqueamos el uso de la asignacion
            comboEmpleado.setEnabled(false);
            tablaMInventarioA.setEnabled(false);
            btn_generar_vale3.setEnabled(false);
            btn_cancelar2.setEnabled(false);
            comboArea.setEnabled(false);
            comboFolioAsignacion.setEnabled(false);
            
            //Regresamos los productos si es que selecciono alguno
            getDatosTablaAsignados();
            regresarInventario();
            limpiarTablaMAsignados();
            comboEmpleado.setSelectedIndex(0);   
        }
    }//realizarResguardoAsignacion
    
    private void realizarResguardoRecoleccion(){ 
        if(manager_permisos.accesoModulo("consulta","Resguardo",Username)){
            if(refrescarResguardoRecoleccion == 2){
                
                String lista = manager_complemento.obtenerAreas();
                String[] recoger = lista.split(",,");

                comboArea1.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
                for(int i = 1; i <= recoger.length;i = i+2){
                    comboArea1.addItem(recoger[i]);
                }

                //Actualizamos el combo de los empleados que tienen productos asignados
                comboEmpleadoR.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
                comboEmpleadoR.addItem("Seleccione al empleado...");
                manejador_inventario.getEmpleadosAsignacion(comboEmpleadoR,1);
                
                refrescarResguardoRecoleccion = 1;
                
                //Desbloqueamos el uso de la asignacion
                comboEmpleadoR.setEnabled(true);
                comboArea1.setEnabled(true);
                tablaRecoleccion.setEnabled(true);
            }
        }else{
            refrescarResguardoRecoleccion = 2;
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultas para el resguardo de recolección.");
            
            //Bloqueamos el uso de la asignacion
            comboEmpleadoR.setEnabled(false);
            comboArea1.setEnabled(false);
            tablaRecoleccion.setEnabled(false);
            
            //Devolvemos los productos al estado anterior
            limpiarTablaRecoleccion();
            comboEmpleadoR.setSelectedIndex(0);
        }
    }//realizarResguardoAsignacion
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        pestañas = 0;
        //activarConfirmacion = 2
        
        //---------------------------------- PESTAÑA INVENTARIO --------------------------------------// 0  
        comboNomeclaturasFolio(comboFolio);
        
        if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
            int folio = comboFolio.getSelectedIndex();
            String estatus = comboEstatus.getSelectedItem().toString();
            String nomeclatura = "";
            int filtro = comboFiltro.getSelectedIndex();
            String busqueda = txtBusqueda.getText();
            //Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
            if(folio > 0){nomeclatura = nomeclaturas[folio-1];}
            tablaInventario.setModel(manager_inventario.getBusquedaInventario(filtro, busqueda, nomeclatura,estatus));
        }
        //---------------------------------- PESTAÑA INVENTARIO --------------------------------------// 0

        //--------------------------- PESTAÑA DE EMPLEADOS Y USUARIOS --------------------------------// 1
        
        comboFiltroVehiculos.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        comboFiltroVehiculos.addItem("Marca");
        comboFiltroVehiculos.addItem("Linea");
        comboFiltroVehiculos.addItem("Año");
        comboFiltroVehiculos.addItem("Color");
        comboFiltroVehiculos.addItem("Matricula");
        
        //--------------------------- PESTAÑA DE EMPLEADOS Y USUARIOS --------------------------------// 1
        
        //---------------------------------- PESTAÑA VEHICULOS --------------------------------------// 2
        
        if(manager_permisos.accesoModulo("consulta","Vehiculos",Username)){
            tablaVehiculos.setModel(managerVehiculos.getVehiculos());
        }
        
        //---------------------------------- PESTAÑA VEHICULOS --------------------------------------// 2
        
        //-------------------------------- PESTAÑA EMPLEADO PERSONAL --------------------------------// 3
        
        tabbedPrincipal.setTitleAt(3, Username);//Le damos el nombre a esa pestaña
        
        //-------------------------------- PESTAÑA EMPLEADO PERSONAL --------------------------------//3
        
        //----------------------------------- PESTAÑA RESGUARDO -------------------------------------// 4
                //---------- Asignación -----------//
        comboEmpleado.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        comboEmpleado.addItem("Seleccione al empleado...");
        
        comboEmpleadoR.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        comboEmpleadoR.addItem("Seleccione al empleado...");
        
        if(manager_permisos.accesoModulo("consulta","Resguardo",Username) && rb_asignacion1.isSelected()){
            realizarResguardoAsignacion();
        }
        //----------------------------------- PESTAÑA RESGUARDO -------------------------------------// 4
        
        //----------------------------------- PESTAÑA SOLICITAR GRANEL -------------------------------------// 5
        if(manager_permisos.accesoModulo("alta","Solicitudes",Username)){
            tablaSolicitarGranel.setModel(manager_inventario_granel.tablaSolicitarInvGranel(0,""));
        }
        
        //----------------------------------- PESTAÑA SOLICITAR GRANEL -------------------------------------// 5
                
        //-------------------------------- PESTAÑA SOLICITUDES --------------------------------------// 6
        
        /*PESTAÑA DE SOLICITUDES*/
        if(manager_permisos.accesoModulo("consulta","Solicitudes",Username)){
            tablaSolicitudes.setModel(manager_solicitud.tabla_SolicitudesMejorada(Username));
            int solicitud = tablaSolicitudes.getRowCount();
            if(solicitud > 0){
                tabbedPrincipal.setTitleAt(6, "Solicitudes ("+solicitud+")");//Le damos el nombre a esa pestaña
            }//if cantidad
            else{
                tabbedPrincipal.setTitleAt(6, "Solicitudes");//Le damos el nombre a esa pestaña
            }
            tablaStockMin.setModel(manejador_inventario.getInventarioStockMin());
        }
        
        //-------------------------------- PESTAÑA SOLICITUDES --------------------------------------// 6
        
        //--------------------------------- PESTAÑA CONFIGURACION -----------------------------------// 7
        
        if(!(manager_permisos.esSuperUsuario(Username))){
            tabbedPrincipal.removeTabAt(7);//Eliminamos la pestaña
        }
        //--------------------------------- PESTAÑA CONFIGURACION -----------------------------------// 7
        
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
    
    private void dar_bajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dar_bajaActionPerformed
        // TODO add your handling code here:
        
        if(manager_permisos.accesoModulo("baja","Usuarios",Username)){
            //Obtenemos la fila y con dicha fila obtenemos el usuario
            int fila = tablaUsuarios.getSelectedRow();
            usuario = tablaUsuarios.getValueAt(fila, 0).toString();
            //Creamos un cuadro de dialogo para que confirme la eliminación del usuario o la cancele
            Object[] botones = {"Confirmar","Cancelar"};
            int opcion = JOptionPane.showOptionDialog(this,"¿Dar baja al usuario "+usuario+"?", "Confirmación",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE  , null, botones, botones[0]);

            //Acepta eliminar al usuario
            if(opcion == 0){

                if(manager_users.estatusUsuario(usuario,"Baja")){
                    JOptionPane.showMessageDialog(null, "El usuario se a dado de baja exisitosamente.");
                    buscarEmpleados();
                }//if(eliminarEmpleado())
                else{
                        JOptionPane.showMessageDialog(null, "Verificar con el distribuidor.");
                }
            }//if(opcion == 0)
        }else{
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para dar de baja usuarios.");
        }
        
        
    }//GEN-LAST:event_dar_bajaActionPerformed

    private void ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Empleados",Username)){
            int filtro = comboFiltroUsuario.getSelectedIndex();
            String busqueda = txtBusquedaUsuario.getText();
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
        updateEmpleado ob;
        try {
            ob = new updateEmpleado(this, true,Integer.parseInt(tablaUsuarios.getValueAt(fila, 0).toString()),2,filtro,busqueda);
            ob.setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }else{
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para actualizar la información de empleados.");
        }
    }//GEN-LAST:event_ActualizarActionPerformed

    private void PermisosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PermisosActionPerformed
        // TODO add your handling code here:
        //Obtenemos el usuario seleccionado
        int fila = tablaUsuarios.getSelectedRow();
        usuario = tablaUsuarios.getValueAt(fila, 0).toString();
        
        //Llamamos el formulario de los permisos
        if(manager_permisos.accesoModulo("actualizar","Usuarios",Username)){
            Ventana_permisos ob = new Ventana_permisos(this, true);
            ob.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para actualizar los permisos de los usuarios.");
        }//else
    }//GEN-LAST:event_PermisosActionPerformed

    private void menuPermisosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPermisosActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Permisos",Username)){
            Ventana_permisos_puesto ob = new Ventana_permisos_puesto(this, true);
            ob.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para actualizar los permisos estáticos de los puestos de trabajo.");
        }//else
    }//GEN-LAST:event_menuPermisosActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
        tablaBD.setModel(manajerMySQL.getPermisosMySQL());
        infoEmpleado();
        
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
        
        etiquetaEstado.setText(temporal[10]);

        //System.err.println(""+tablaVehiculos.getValueAt(fila, 4).toString());
        //Obtener
        
        //reset a botones
        nFoto = 0;
        izquierdaBtn.setEnabled(true);
        derechaBtn.setEnabled(true);
        // Para crear la carpeta se concatena la marca el color y la placa del vehiculo
        auxiliarFoto = tablaVehiculos.getValueAt(fila, 0).toString() + "_"
                + tablaVehiculos.getValueAt(fila, 3).toString() + "_" + tablaVehiculos.getValueAt(fila, 4).toString();
        cargarImagen(auxiliarFoto,nFoto);

        etiquetaMarca.setVisible(true);
        etiquetaLinea.setVisible(true);
        etiquetaKilometraje.setVisible(true);
        etiquetaAño.setVisible(true);
        campoObservaciones.setVisible(true);
       
    }
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
         // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed
        
    private void ActualizarVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarVActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Vehiculos",Username)){
            
        }else{
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para actualizar la información de los vehiculos.");
        }
        
    }//GEN-LAST:event_ActualizarVActionPerformed

    private void AtenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtenderActionPerformed
        // TODO add your handling code here:
        
        cancelarVale();
        if(manager_permisos.accesoModulo("actualizar","Solicitudes",Username)){
            int fila = tablaSolicitudes.getSelectedRow();
            idAtenderSalida = tablaSolicitudes.getValueAt(fila, 0).toString();
            tabbedPrincipal.setSelectedIndex(4);//Direccionamos a la pestaña de Manejador inventario
            rb_asignacion1.setSelected(true);
            rb_recoleccion1.setSelected(false);
            rb_recoleccion1.setEnabled(false);//Deshabilitamos el boton de recolección, esto para que atienda la solicitud
            rb_inventario_granel1.setEnabled(true);//Habilitamos la de inventario a granel
            rb_inventario_granel1.setSelected(true);//Marcamos el radio del inventario a granel
            rb_inventario_normal1.setEnabled(false);//Deshabilitamos la de inventario
            rb_inventario_normal1.setSelected(false);//Desmarcamos el radio del inventario
            comboEmpleado.setSelectedItem(tablaSolicitudes.getValueAt(fila, 1).toString());//Le inidicamos el empleado que realizo la solicitud
            comboEmpleado.setEnabled(false);//Desactivamos el combo para que no cambie el responsable
            esGranel = true;//Activamos la banera de es a granel para saber que hacer con los botones
            comboFolioAsignacion.setVisible(false);
            btn_cancelar2.setEnabled(true);
            comboArea.setVisible(false);
            lblAreaAsignacion.setVisible(false);

            //Cargamos el cardLayout en caso de encontrarse en el recolección
            CardLayout c_asignacion = (CardLayout)pn_contenedor_ventanas1.getLayout();
            c_asignacion.show(pn_contenedor_ventanas1,"c_s_asignacion");

            //Llenamos la tabla con la información de acuerdo a la solicitud que se realizo
            tablaMInventarioA.setModel(manager_solicitud.tabla_SolicitudSalida(idAtenderSalida));
            tablaMAsignados.setModel(tablaGranel);

            //Llenamos la tabla con los productos en 0
            for(int i = 0; i<tablaMInventarioA.getRowCount();i++){
                tablaGranel.addRow(new Object[]{manager_complemento.textoNumero(0),0,tablaMInventarioA.getValueAt(i, 4),tablaMInventarioA.getValueAt(i, 0),tablaMInventarioA.getValueAt(i, 2)});
            }
        }else{
            JOptionPane.showMessageDialog(null,"No cuenta con permisos para atender las solicitudes.");
        }
        
    }//GEN-LAST:event_AtenderActionPerformed

    private void mi_viaticosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_viaticosActionPerformed
 
        PrincipalS a= new PrincipalS();
        a.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_mi_viaticosActionPerformed
    
    public boolean existeCodigoTablaSolicitarGranel(String codigo,int cantidad){
        boolean existe = false;
        //Buscamos en la tabla si ya existe el codigo
        for(int fila = 0;fila<tablaCantidadGranel.getRowCount();fila++){
            //Si el codigo ya se habia registrado entonces sumamos uno a la cantidad
            if(tablaCantidadGranel.getValueAt(fila,0).equals(codigo)){
                    Object[] opciones = {"Aceptar","Cancelar"};
                    int seleccion = JOptionPane.showOptionDialog(null, "¿Desea agregar \""+cantidad+"\" al producto \""+tablaCantidadGranel.getValueAt(fila, 0).toString()+"\"?","Confirmación para añadir cantidad", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                    if(seleccion == 0){
                        int  valor = Integer.parseInt(tablaCantidadGranel.getValueAt(fila,3).toString());
                        valor = valor + cantidad;
                        tablaCantidadGranel.setValueAt(valor,fila,3);
                    }
                    existe = true;
                    break;
            }//if
            
        }//for
        return existe;
    }//existeCodigoTablaSolicitarGranel
    
    public void limpiarTablaCantidadGranel(){
            int a = modeloCantidadSalida.getRowCount() - 1;
            for(int i=0; i<=a;i++){
               modeloCantidadSalida.removeRow(0);
            }
    }
    
    /*-------------------------------PARA LA ASIGNACION EN LA PESTAÑA DE MANEJADOR KEVIN------------------------------------------------*/
    public boolean existeCodigoTablaMAsignados(String codigo,int cantidad){
        boolean existe = false;
        //Buscamos en la tabla si ya existe el codigo
        for(int fila = 0;fila<tablaMAsignados.getRowCount();fila++){
            //Buscamos si el producto ya se habia registrado
            if(tablaMAsignados.getValueAt(fila,3).equals(codigo)){
                
                int solicito = Integer.parseInt(tablaMAsignados.getValueAt(fila,2).toString());
                int autorizado = Integer.parseInt(tablaMAsignados.getValueAt(fila,1).toString());
                int restantes = solicito - autorizado;
                
                
                
                //Verificamos que no pase del maximo (Solicitó)
                if(cantidad > restantes){
                    
                    if(restantes == 0){
                        JOptionPane.showMessageDialog(null,"Ya se asigno la cantidad máxima al producto \""+codigo+"\"");
                    }else{
                        JOptionPane.showMessageDialog(null,"Usted está intentando asignarle más productos de los que se estan solicitando.\n"
                                                     + "Se solicitan \""+solicito+"\" y ya se han autorizado \""+autorizado+"\". Por lo tanto, solo\n"
                                                     + "puede asignar \""+restantes+"\" mas.");
                    }
                    //Regresamos los productos que se intentaron agregar
                    Claves = new String[1];
                    Cantidad = new int[1];
                    Claves[0] = codigo;
                    Cantidad[0] = cantidad;
                    regresarInventario();
                    
                    existe = true;
                    break;
                }else{                
                    int  valor = Integer.parseInt(tablaMAsignados.getValueAt(fila,1).toString());
                    valor = valor + cantidad;
                    tablaMAsignados.setValueAt(valor,fila,1);
                    tablaMAsignados.setValueAt(manager_complemento.textoNumero(valor),fila,0);
                    existe = true;
                    break;
                }
            }//if
            
        }//for
        return existe;
    }//Busca si existen coincidencias en la tabla para sumarlas
    
    public void getDatosTablaAsignados(){
        Claves = new String[tablaMAsignados.getRowCount()];
        Cantidad = new int[tablaMAsignados.getRowCount()];
        int columnaCla = 0;
        int columnaCan = 0;
        if(esGranel){
            columnaCla = 3;
            columnaCan = 1;
        }else{
            columnaCla = 0;
            columnaCan = 4;
        }
        
        for(int i = 0;i<tablaMAsignados.getRowCount();i++){
            Claves[i] = tablaMAsignados.getValueAt(i, columnaCla).toString();//Obtenemos las claves
            Cantidad[i] = Integer.parseInt(tablaMAsignados.getValueAt(i, columnaCan).toString());//Obtenemos las cantidades
        }//Llenar vector de los codigos de barras
    }//obtiene los datos de la tabla "tablaMAsignados"
    
    public void regresarInventario(){
        manejador_inventario.regresarInventario(Claves,Cantidad);
    }
    
    public void limpiarTablaMAsignados(){
        if(esGranel){
            int a = tablaGranel.getRowCount() - 1;
            for(int i=0; i<=a;i++){
               tablaGranel.removeRow(0);
            }
            
            //Deshacemos todos los cambios que habiamos hecho cuando quisimos atender la solicitud para salida de almacen
            rb_recoleccion1.setEnabled(true);
            rb_inventario_granel1.setEnabled(false);
            rb_inventario_granel1.setSelected(false);
            rb_inventario_normal1.setEnabled(true);
            rb_inventario_normal1.setSelected(true);
            comboEmpleado.setSelectedIndex(0);
            comboEmpleado.setEnabled(true);
            comboArea.setVisible(true);
            lblAreaAsignacion.setVisible(true);
            
        }else{
            int a = tablaNormal.getRowCount() - 1;
            for(int i=0; i<=a;i++){
               tablaNormal.removeRow(0);
            }
        }
        if(manager_permisos.accesoModulo("consulta","Resguardo",Username)){
            tablaMInventarioA.setModel(manejador_inventario.getInventarioParaAsignacion(""));
        }else{
            tablaMInventarioA.setModel(new DefaultTableModel());
            
        }
        tablaMAsignados.setModel(tablaNormal);
        esGranel = false;
        comboFolioAsignacion.setVisible(true);
        
    }//limpia la tabla "tablaMAsignados"
    
    /*--------------------------PARA LA RECOLECCION EN LA PESTAÑA DE MANEJADOR  DE INVENTARIO-----------------------*/    
    public void limpiarTablaRecoleccion(){
        int a = modeloObjetosEntregados.getRowCount() - 1;
        for(int i=0; i<=a;i++){
           modeloObjetosEntregados.removeRow(0);
        }
        tablaRecoleccion.setModel(modeloRecoleccion);
    }//limpia la tabla "tablaMAsignados"
    
    private void CancelarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarAActionPerformed
        // TODO add your handling code here:
        Claves = new String[1];
        Cantidad = new int[1];
        //Obtenemos la fila
        int fila = tablaMAsignados.getSelectedRow();

        if(esGranel){
            //Obtenemos la clave y cantidad
             Claves[0] = tablaMAsignados.getValueAt(fila, 3).toString();
             Cantidad[0] = Integer.parseInt(tablaMAsignados.getValueAt(fila, 1).toString());
        }else{
            //Obtenemos la clave y cantidad
            Claves[0] = tablaMAsignados.getValueAt(fila, 0).toString();
            Cantidad[0] = Integer.parseInt(tablaMAsignados.getValueAt(fila, 4).toString());
        }
        
        //Mostramos mensaje de confirmación para cancelar el producto seleccionado
        String[] opciones = {"Aceptar", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(null, "¿Desea cancelar el producto \""+Claves[0]+"\"?","Confirmación de cancelación", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if(seleccion == 0){
            if(!esGranel){
                //Esto es para mostrar la tabla de acuerdo a lo que se quedo seleccionado en el combo
                int folio = comboFolioAsignacion.getSelectedIndex();
                String nomeclatura = "";
                //Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
                if(folio > 0){nomeclatura = nomeclaturas[folio-1];}

                //Regresamos el producto seleccionado
                manejador_inventario.regresarInventario(Claves,Cantidad);
                //Eliminamos el registro de la tabla
                tablaNormal.removeRow(fila);
                JOptionPane.showMessageDialog(null, "Se regreso al inventario el producto \""+Claves[0]+"\".");
                tablaMInventarioA.setModel(manejador_inventario.getInventarioParaAsignacion(nomeclatura));
                
                //Vemos si queda mas de un producto
                if(tablaNormal.getRowCount() == 0){
                    btn_generar_vale3.setEnabled(false);
                    btn_cancelar2.setEnabled(false);
                    
                }
            }//esGranel
            else{
                //Regresamos el producto seleccionado
                manejador_inventario.regresarInventario(Claves,Cantidad);
                //Regresamos los productos al inventario
                tablaGranel.setValueAt(manager_complemento.textoNumero(0), fila, 0);
                tablaGranel.setValueAt(0, fila, 1);
                boolean habilitarBoton = false;
                
                for(int i = 0; i< tablaMAsignados.getRowCount(); i++){
                    if(Integer.parseInt(tablaMAsignados.getValueAt(i, 1).toString()) > 0){
                        habilitarBoton = true;
                        break;
                    }//if
                }//for
                
                btn_generar_vale3.setEnabled(habilitarBoton);
                
                JOptionPane.showMessageDialog(null, "Se regresaron \""+Cantidad[0]+"\" productos al inventario del producto \""+Claves[0]+"\".");
                tablaMInventarioA.setModel(manager_solicitud.tabla_SolicitudSalida(idAtenderSalida));

            }
            
        }//selecciono la opcion "Aceptar"
    }//GEN-LAST:event_CancelarAActionPerformed

    private void resguardoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resguardoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_resguardoMouseClicked

    private void rb_recoleccion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_recoleccion1ActionPerformed
        // TODO add your handling code here:
        CardLayout c_recoleccion = (CardLayout)pn_contenedor_ventanas1.getLayout();
        c_recoleccion.show(pn_contenedor_ventanas1,"c_s_recoleccion");
        realizarResguardoRecoleccion();
    }//GEN-LAST:event_rb_recoleccion1ActionPerformed

    private void rb_asignacion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_asignacion1ActionPerformed
        // TODO add your handling code here:
        CardLayout c_asignacion = (CardLayout)pn_contenedor_ventanas1.getLayout();
        c_asignacion.show(pn_contenedor_ventanas1,"c_s_asignacion");
        realizarResguardoAsignacion();
    }//GEN-LAST:event_rb_asignacion1ActionPerformed

    private void comboEmpleadoRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEmpleadoRActionPerformed
        // TODO add your handling code here:
        if(comboEmpleadoR.getSelectedIndex() != 0){
            if(manager_permisos.accesoModulo("consulta","Resguardo",Username)){
            comboAño.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
            manejador_inventario.getAñoEmpleadoAsignacion(comboAño,comboEmpleadoR.getSelectedItem().toString());
            tablaRecoleccion.setModel(manejador_inventario.getInventarioEmpleadoAsignaciones(comboEmpleadoR.getSelectedItem().toString(),Integer.parseInt(comboAño.getSelectedItem().toString())));
            //Creamos el combobox y lo llenamos

             TableColumn col=tablaRecoleccion.getColumnModel().getColumn(11);
             JComboBox bodegas = new JComboBox();
             bodegas.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
             manager_inventario.getBodegas(bodegas);
             TableCellEditor tce = new DefaultCellEditor(bodegas);
             col.setCellEditor(tce);

             //Agregamos el combo a la tabla
             col.setCellEditor(new DefaultCellEditor(bodegas));
             btnEntregarRecoleccion.setEnabled(true);
             comboAño.setEnabled(true);
            }else{
                refrescarResguardoRecoleccion = 2;
                JOptionPane.showMessageDialog(null, "Su permiso de consulta de resguardo ha sido revocado.");

                //Bloqueamos el uso de la asignacion
                comboEmpleadoR.setEnabled(false);
                comboArea1.setEnabled(false);
                tablaRecoleccion.setEnabled(false);

                //Devolvemos los productos al estado anterior
                limpiarTablaRecoleccion();
                comboEmpleadoR.setSelectedIndex(0);
            }
            
        }else{
            tablaRecoleccion.setModel(modeloRecoleccion);
            btnEntregarRecoleccion.setEnabled(false);
            comboAño.setEnabled(false);
        }
    }//GEN-LAST:event_comboEmpleadoRActionPerformed

    private void tablaRecoleccionMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaRecoleccionMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaRecoleccionMouseReleased

    private void comboEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEmpleadoActionPerformed
        // TODO add your handling code here:
        //si es diferente de 0 entonces ya selecciono un empleado
        if(comboEmpleado.getSelectedIndex() != 0){
            empleadoSeleccionado = true;
            if(tablaMAsignados.getRowCount() > 0){
                btn_generar_vale3.setEnabled(true);
            }
            
        }else{
            empleadoSeleccionado = false;
            btn_generar_vale3.setEnabled(false);
        }

    }//GEN-LAST:event_comboEmpleadoActionPerformed

    private void rb_inventario_granel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_inventario_granel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_inventario_granel1ActionPerformed

    private void rb_inventario_granel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rb_inventario_granel1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_inventario_granel1MouseClicked

    private void rb_inventario_normal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_inventario_normal1ActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("consulta","Resguardo",Username)){
            tablaMInventarioA.setModel(manejador_inventario.getInventarioParaAsignacion(""));
        }else{
            realizarResguardoAsignacion();
        }
        esGranel = false;
        comboFolioAsignacion.setVisible(true);
    }//GEN-LAST:event_rb_inventario_normal1ActionPerformed
    private void cancelarVale(){
            int filas = 0;
            if(esGranel){
                filas = tablaGranel.getRowCount();
            }else{
                filas = tablaNormal.getRowCount();
            }
                String[] opciones = {"Aceptar", "Cancelar"};
                int seleccion = 0;

                if(esGranel){
                    if(filas >0){seleccion = JOptionPane.showOptionDialog(null, "¿Desea cancelar el proceso para atender la solicitud de salida de almacen? \nLos productos que se asignaron volveran a estar disponibles.","Confirmación de cancelación", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);}
                }else{
                    if(filas>0){seleccion = JOptionPane.showOptionDialog(null, "¿Desea cancelar el proceso de asignación? \nLos productos volveran a estar disponibles","Confirmación de cancelación", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);}
                }

                if(seleccion == 0){
                    getDatosTablaAsignados();
                    regresarInventario();
                    limpiarTablaMAsignados();
                    btn_generar_vale3.setEnabled(false);
                    btn_cancelar2.setEnabled(false);
                }
            
    }
    private void btn_cancelar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelar2ActionPerformed
        // TODO add your handling code here:
            if(esGranel){
                cancelarVale();
            }else{
                cancelarVale();
            }
    }//GEN-LAST:event_btn_cancelar2ActionPerformed

    private void btn_generar_vale3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generar_vale3ActionPerformed
        // TODO add your handling code here:
        
            getDatosTablaAsignados();
            if(esGranel){
                if(manager_permisos.accesoModulo("actualizar","Solicitudes",Username)){
                    if(manejador_inventario.estatusSalidaAlmacen(Claves, Cantidad, Username,idAtenderSalida,"Autorizada")){
                        //GENERAR VALE DE SALIDA DE ALMACEN
                        metodoVale();
                        limpiarTablaMAsignados();

                        //Habilitamos las cosas que se deshabilitaron por atender la solicitud de salida de almacen
                        rb_recoleccion1.setEnabled(true);
                        rb_inventario_granel1.setEnabled(false);
                        rb_inventario_granel1.setSelected(false);
                        rb_inventario_normal1.setEnabled(true);
                        rb_inventario_normal1.setSelected(true);
                        comboEmpleado.setSelectedIndex(0);
                        comboEmpleado.setEnabled(true);
                        btn_generar_vale3.setEnabled(false);
                        btn_cancelar2.setEnabled(false);
                        comboArea.setVisible(true);
                        lblAreaAsignacion.setVisible(true);

                        if(manager_permisos.accesoModulo("consulta","Solicitudes",Username)){
                            //Actualizamos la tabla
                            tablaSolicitudes.setModel(manager_solicitud.tabla_SolicitudesMejorada(Username));
                            int solicitud = tablaSolicitudes.getRowCount();
                            if(solicitud > 0){
                                tabbedPrincipal.setTitleAt(6, "Solicitudes ("+solicitud+")");//Le damos el nombre a esa pestaña
                            }else{
                                tabbedPrincipal.setTitleAt(6, "Solicitudes");//Le damos el nombre a esa pestaña
                            }
                            tablaStockMin.setModel(manejador_inventario.getInventarioStockMin());
                        }else{
                            JOptionPane.showMessageDialog(null,"Tus permisos para consultar las solicitudes y productos con stock mínimo han sido revocados.");
                            DefaultTableModel tablaLimpia = new DefaultTableModel();
                            tablaStockMin.setModel(tablaLimpia);
                            tablaSolicitudes.setModel(tablaLimpia);
                            tabbedPrincipal.setTitleAt(6, "Solicitudes");//Le damos el nombre a esa pestaña
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Verificar con el distribuidor.");
                    }//else

                }else{
                    JOptionPane.showMessageDialog(null,"Tu permiso para aceptar solicitudes a sido revocado.");
                }
                    
            }else
            {
                if(manager_permisos.accesoModulo("actualizar","Solicitudes",Username)){
                    if(manejador_inventario.asignarInventario(Claves, Cantidad, comboEmpleado.getSelectedItem().toString(),"RES")){
                        //GENERA EL VALE DE RESGUARDO
                        metodoVale2();
                        limpiarTablaMAsignados();
                        btn_generar_vale3.setEnabled(false);
                        comboEmpleado.setSelectedIndex(0);
                    }else{
                        JOptionPane.showMessageDialog(null,"Verificar con el distribuidor.");
                    }//else
                }else{
                    JOptionPane.showMessageDialog(null,"Usted no cuenta con permiso para dar de alta un vale de resguardo.");
                }
            }//else


    }//GEN-LAST:event_btn_generar_vale3ActionPerformed
    
public void metodoVale(){
    // TODO add your handling code here:
        
        Object[] botones = {"Si", "No", "Cancelar"};
                        int opcion = JOptionPane.showOptionDialog(this, "¿Al generar el vale desea abrirlo?", "Confirmación",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, botones, botones[0]);

                        if (opcion == 0) {
                            metodo(1);
                        } else if (opcion == 1) {
                            metodo(0);
                        }
}

public void metodoVale2(){
    // TODO add your handling code here:
        
        Object[] botones = {"Si", "No", "Cancelar"};
                        int opcion = JOptionPane.showOptionDialog(this, "¿Al generar el vale desea abrirlo?", "Confirmación",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, botones, botones[0]);

                        if (opcion == 0) {
                            metodo2(1);
                        } else if (opcion == 1) {
                            metodo2(0);
                        }
}

public void metodoValeRecoleccion(){
    // TODO add your handling code here:
        
        Object[] botones = {"Si", "No", "Cancelar"};
                        int opcion = JOptionPane.showOptionDialog(this, "¿Al generar el vale desea abrirlo?", "Confirmación",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, botones, botones[0]);

                        if (opcion == 0) {
                            metodoRecoleccion(1);
                        } else if (opcion == 1) {
                            metodoRecoleccion(0);
                        }
}

    public void metodo(int res) {
        
        //Instanciamos el objeto Calendar
        //en fecha obtenemos la fecha y hora del sistema
        Calendar fecha = new GregorianCalendar();
        //Obtenemos el valor del año, mes, día,
        //hora, minuto y segundo del sistema
        //usando el método get y el parámetro correspondiente
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);

        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("dd_MM_yyyy HH_mm_ss");

        //Parse
        String a = hourdateFormat.format(date);

        String cadena = "vale_salida";

        String cadena1 = "" + dia + "/" + (mes + 1) + "/" + año;
        String cadena2 = "" + hora + ":" + minuto + ":" + segundo;

        System.out.println("" + cadena1 + " " + cadena2);
        Vector v = new Vector();
        CrearValeSalidaAlmacen ob = new CrearValeSalidaAlmacen();
        for(int i = 0;i<tablaMAsignados.getRowCount();i++){
           
           v.add(tablaMAsignados.getValueAt(i, 0).toString()
                   +",,"+tablaMAsignados.getValueAt(i, 1).toString()
                   +",,"+tablaMAsignados.getValueAt(i, 2).toString()
                   +",,"+tablaMAsignados.getValueAt(i, 4).toString());
           
        }//Llenar vector de los codigos de barras
        
        try {
            ob.createTicket("salida_almacen_"+dia+"_"+(mes+1)+"_"+año+"_"+hora+"_"+minuto+"_"+segundo, "", "", res, cadena1, cadena2,v,comboEmpleado.getSelectedItem().toString());
        } catch (DocumentException ex) {
            Logger.getLogger(addUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void metodo2(int res) {
        
        //Instanciamos el objeto Calendar
        //en fecha obtenemos la fecha y hora del sistema
        Calendar fecha = new GregorianCalendar();
        //Obtenemos el valor del año, mes, día,
        //hora, minuto y segundo del sistema
        //usando el método get y el parámetro correspondiente
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);

        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("dd_MM_yyyy HH_mm_ss");

        //Parse
        String a = hourdateFormat.format(date);

        String cadena = "vale_salida";

        String cadena1 = "" + dia + "/" + (mes + 1) + "/" + año;
        String cadena2 = "" + hora + ":" + minuto + ":" + segundo;

        System.out.println("" + cadena1 + " " + cadena2);
        Vector v = new Vector();
        
        CrearValeResguardoBienes ob = new CrearValeResguardoBienes();
        
        String datosempleado = manager_inventario_granel.obtenerDatosResponsableResguardo(comboEmpleado.getSelectedItem().toString());
        String numeroResguardo = manager_inventario_granel.obtenerNumeroResguardo(""+año);
        for(int i = 0;i<tablaMAsignados.getRowCount();i++){
           
           v.add(manager_inventario_granel.obtenerDatosResguardo(tablaMAsignados.getValueAt(i, 0).toString()));
           
        }//Llenar vector de los codigos de barras
        
        try {
            ob.createTicket("resguardo_"+dia+"_"+(mes+1)+"_"+año+"_"+hora+"_"+minuto+"_"+segundo, 
                    res, cadena1, cadena2,v,datosempleado,numeroResguardo);
        } catch (DocumentException ex) {
            Logger.getLogger(addUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void metodoRecoleccion(int res) {
        
        //Instanciamos el objeto Calendar
        //en fecha obtenemos la fecha y hora del sistema
        Calendar fecha = new GregorianCalendar();
        //Obtenemos el valor del año, mes, día,
        //hora, minuto y segundo del sistema
        //usando el método get y el parámetro correspondiente
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);

        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("dd_MM_yyyy HH_mm_ss");

        //Parse
        String a = hourdateFormat.format(date);

        String cadena = "vale_salida";

        String cadena1 = "" + dia + "/" + (mes + 1) + "/" + año;
        String cadena2 = "" + hora + ":" + minuto + ":" + segundo;

        System.out.println("" + cadena1 + " " + cadena2);
        Vector v = new Vector();
        Vector vfaltante = new Vector();
        
        CrearValeRecoleccionDeBienes ob = new CrearValeRecoleccionDeBienes();
        
        String datosempleado = manager_inventario_granel.obtenerDatosResponsableResguardo(comboEmpleadoR.getSelectedItem().toString());
        //String numeroResguardo = manager_inventario_granel.obtenerNumeroResguardo("" + año);
        for (int i = 0; i < tablaRecoleccion.getRowCount(); i++) {

            if (Boolean.parseBoolean(tablaRecoleccion.getValueAt(i, 0).toString())) {
                try{
                v.add(tablaRecoleccion.getValueAt(i, 2).toString()+",,"
                        +tablaRecoleccion.getValueAt(i, 6).toString()+",,"
                            +tablaRecoleccion.getValueAt(i, 4).toString()+",,"
                                    +tablaRecoleccion.getValueAt(i, 5).toString()+",,"
                                                +tablaRecoleccion.getValueAt(i, 7).toString()+",,"
                                                    +tablaRecoleccion.getValueAt(i, 8).toString()+",,");
                }catch(NullPointerException e){
                    
                }
            }else{
                try{
                vfaltante.add(tablaRecoleccion.getValueAt(i, 2).toString()+",,"
                        +tablaRecoleccion.getValueAt(i, 6).toString()+",,"
                            +tablaRecoleccion.getValueAt(i, 4).toString()+",,"
                                    +tablaRecoleccion.getValueAt(i, 5).toString()+",,"
                                                +tablaRecoleccion.getValueAt(i, 7).toString()+",,"
                                                    +tablaRecoleccion.getValueAt(i, 8).toString()+",,");
                }catch(NullPointerException e){
                    
                }
            }
            

        }//Llenar vector de los codigos de barras
        
        String recoleccion = manejador_inventario.registrarVale(comboEmpleadoR.getSelectedItem().toString(), "REC");
        
        try {
            ob.createTicket("recoleccion_"+dia+"_"+(mes+1)+"_"+año+"_"+hora+"_"+minuto+"_"+segundo, 
                    res, cadena1, cadena2,v,vfaltante, datosempleado, recoleccion);
        } catch (DocumentException ex) {
            Logger.getLogger(addUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//Se pregunta si se quieren los productos que se encuentran, en caso de quererlos se asignan las existencias restantes y cambia a agotado, 
    //si no las quiere entonces no sucede nada
    public void cantidadMayorAlStock(String id, int comprobar, int cantidad, int fila){
        
        tablaMInventarioA.setModel(manager_solicitud.tabla_SolicitudSalida(idAtenderSalida));
        //Preguntara si quiere lo que hay en existencia
        String[] opciones = {"Aceptar", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(null, "Solo se cuenta con "+comprobar+" de exitencias para el producto "+id+".\nUsted esta solicitando "+cantidad+", ¿Desea aceptar las existencias restantes o esperar a que las agreguen al inventario?","¿Acepta las exitencias restantes?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if(seleccion == 0){
            manejador_inventario.productosIgualesInventarioG(id, comprobar);
            if(!(existeCodigoTablaMAsignados(id,comprobar))){
                tablaGranel.addRow(new Object[]{manager_complemento.textoNumero(comprobar),comprobar,tablaMInventarioA.getValueAt(fila, 4),id,tablaMInventarioA.getValueAt(fila, 2)});
            }
        }//Dio clic en aceptar

        tablaMInventarioA.setModel(manager_solicitud.tabla_SolicitudSalida(idAtenderSalida));
        
    }//cantidadMayorAlStock
    
    //La cantidad es igual al stock entonces se queda en 0 y se cambia a "Agotado"
    public void cantidadIgualAlStock(String id,int cantidad, int fila){
        
        //Se pasa el registro a la otra tabla y cambia el estado del producto a agotado(comprobación)
        int comprobar2 = manejador_inventario.productosIgualesInventarioG(id, cantidad);

        //Comprobamos si se agotaron las exitencias
        if(comprobar2 == 0){
            //Si es 0 entonces se cambia sin problemas y el estado cambia a agotado
            if(!(existeCodigoTablaMAsignados(id,cantidad))){
                tablaGranel.addRow(new Object[]{manager_complemento.textoNumero(cantidad),cantidad,tablaMInventarioA.getValueAt(fila, 4),id,tablaMInventarioA.getValueAt(fila, 2)});
            }
            tablaMInventarioA.setModel(manager_solicitud.tabla_SolicitudSalida(idAtenderSalida));
        }
        //La cantidad que solicita es mayor a la que hay en el stock
        else if(cantidad > comprobar2){
            cantidadMayorAlStock(id,comprobar2,cantidad,fila);
        }//cantidad > comprobar2
        
        //Si no entro a ninguna de las 2 de arriba, entonces se actualizo el stock agregando mas
        //existencias, entonces se realiza el registro normalmente
        else{
            manejador_inventario.productosSuficientesInventarioG(id, cantidad);
            if(!(existeCodigoTablaMAsignados(id,cantidad))){
                tablaGranel.addRow(new Object[]{manager_complemento.textoNumero(cantidad),cantidad,tablaMInventarioA.getValueAt(fila, 4),id,tablaMInventarioA.getValueAt(fila, 2)});
            }
            tablaMInventarioA.setModel(manager_solicitud.tabla_SolicitudSalida(idAtenderSalida));

        }//else
        
    }//cantidadIgualAlStock
    
    private void tablaMInventarioAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMInventarioAMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){

            int fila = tablaMInventarioA.getSelectedRow();
            //Obtenemos la clave del producto
            String idProducto = tablaMInventarioA.getValueAt(fila, 0).toString();
            if(empleadoSeleccionado){
                
                //ES INVENTARIO A GRANELA
                if(esGranel){
                    
                    int cantidad = 0;
                    //Esto es para validar que ingrese solo numeros y mientras no lo haga, seguira preguntado hasta que
                    //solo teclee numeros o cancele el movimiento
                    boolean entero = true;
                    boolean canceloCantidad = false;
                    
                    if(Integer.parseInt(tablaMInventarioA.getValueAt(fila, 5).toString()) == 0){
                        JOptionPane.showMessageDialog(null,"No hay productos en el stock.");
                        tablaMInventarioA.setModel(manager_solicitud.tabla_SolicitudSalida(idAtenderSalida));
                    }//if 
                    else{
                        while(entero){

                            String cadena = JOptionPane.showInputDialog("Ingrese la cantidad que desea asignar");
                            //Cancelo la solicitud de asignacion
                            if(cadena == null){
                                entero=false;
                                canceloCantidad = false;
                            }else{
                                try{
                                    //Si hace la conversion correctamente entonces no entra en la excepcion y se sale del ciclo
                                    cantidad = Integer.parseInt(cadena);
                                    if(cantidad > 0){
                                        entero = false;
                                        canceloCantidad = true;
                                    }else{
                                        JOptionPane.showMessageDialog(null,"Ingrese cantidades mayores a 0.");
                                    }
                                }catch(NumberFormatException e){
                                    JOptionPane.showMessageDialog(null,"Solo ingrese numeros");
                                    entero = true;
                                }//try catch
                            }
                        }//while                        

                        int solicitado = Integer.parseInt(tablaMInventarioA.getValueAt(fila, 4).toString());
                        //Verificamos la cantidad que se quiere asignar con la que se solicito
                        if(cantidad > solicitado){
                            JOptionPane.showMessageDialog(null, "Se están solicitando \""+solicitado+"\" productos, y usted quiere asignar \""+cantidad+"\".\n"
                                    + "Por favor asigne una cantidad menor o igual a la solicitada.");
                            tablaMInventarioA.setModel(manager_solicitud.tabla_SolicitudSalida(idAtenderSalida));
                            canceloCantidad = false;
                        }
                        
                    }//else 
                    
                    if(canceloCantidad){
                        
                        btn_generar_vale3.setEnabled(true);
                        btn_cancelar2.setEnabled(true);
                        
                        //Obtenemos la cantidad directamente de la BD por si se ha actualizado la cantidad
                        int stock = manejador_inventario.cantidadInventarioG(idProducto);

                        //1.- Significa que hubo error al querer hacer la consulta
                        if(stock == -1){
                            JOptionPane.showMessageDialog(null, "Verificar con el distribuidor. -1");
                        }//stock == -1

                        //2.- Se agotaron existencias, entonces alguien mas asigno dichas existencias a alguien más
                        else if(stock == 0){
                            JOptionPane.showMessageDialog(null, "Se han agotado las existencias de ese producto, ya han sido asignadas");
                            tablaMInventarioA.setModel(manager_solicitud.tabla_SolicitudSalida(idAtenderSalida));
                        }//stock == 0
                        //3.- Las existencias son mayores a lo que se solicita, entonces se realiza la asignacion sin problema
                        else if(stock > cantidad){
                            int comprobar = manejador_inventario.productosSuficientesInventarioG(idProducto, cantidad);

                            //Si es mayor entonces si se realizo exitosamente la actualización del inventario
                            if(stock > comprobar){
                                //Se pasa el registro a la otra tabla
                                if(!(existeCodigoTablaMAsignados(idProducto,cantidad))){
                                    tablaGranel.addRow(new Object[]{manager_complemento.textoNumero(cantidad),cantidad,tablaMInventarioA.getValueAt(fila, 4),idProducto,tablaMInventarioA.getValueAt(fila, 2)});
                                }
                                tablaMInventarioA.setModel(manager_solicitud.tabla_SolicitudSalida(idAtenderSalida));
                            }//stock > comprobar

                            //No se realizo la condición anterior porque el stock dejo de ser mayor que la cantidad que se solicita
                            //Comprobamos si se agotaron las exitencias
                            else if(comprobar == 0){
                                JOptionPane.showMessageDialog(null, "Se han agotado las existencias de ese producto, ya han sido asignadas");
                                tablaMInventarioA.setModel(manager_solicitud.tabla_SolicitudSalida(idAtenderSalida));
                            }

                            //No se cumplio la de 0 existencias, entonces comprobamos si es igual de la cantidad que se solicita,
                            //si no, será menor.
                            else if(cantidad == comprobar){
                                cantidadIgualAlStock(idProducto,cantidad,fila);
                            }//cantidad == comprobar

                            //La cantidad es mayor al stock
                            else if(cantidad > comprobar){
                                cantidadMayorAlStock(idProducto,comprobar,cantidad,fila);
                            }
                            //Si no entro a ninguna de las 3 de arriba, entonces se actualizo el stock agregando mas
                            //existencias, y ya se hizo el registro la primera vez
                            else{
                                //Se pasa el registro a la otra tabla
                                if(!(existeCodigoTablaMAsignados(idProducto,cantidad))){
                                    tablaGranel.addRow(new Object[]{manager_complemento.textoNumero(cantidad),cantidad,tablaMInventarioA.getValueAt(fila, 4),idProducto,tablaMInventarioA.getValueAt(fila, 2)});
                                }
                                tablaMInventarioA.setModel(manager_solicitud.tabla_SolicitudSalida(idAtenderSalida));
                            }//else
                            
                        }//stock > cantidad

                        //No se cumplio la de 0 existencias, entonces comprobamos si es igual de la cantidad que se solicita,
                        //si no, será menor.
                        else if(stock == cantidad){
                            cantidadIgualAlStock(idProducto,cantidad,fila);
                        }// stock == cantidad
                        else if (stock < cantidad){
                            cantidadMayorAlStock(idProducto,stock,cantidad,fila);
                        }//stock < cantidad
                        
                    }//canceloSolicitud
                }//esGranel
                
                //ES INVENTARIO NORMAL
                    else{
                    //Obtenemos el estado del producto
                    String estado = manager_solicitud.estadoProducto(idProducto);
                    
                    int folio = comboFolioAsignacion.getSelectedIndex();
                    String nomeclatura = "";
                    //Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
                    if(folio > 0){nomeclatura = nomeclaturas[folio-1];}
                    
                    //Si esta disponible entonces lo asignamos
                    if(estado.equals("Disponible")){
                        
                        if(manager_asignar.asignarEquipo(idProducto)){
                            btn_generar_vale3.setEnabled(true);
                            btn_cancelar2.setEnabled(true);
                            tablaNormal.addRow(new Object[]{idProducto,tablaMInventarioA.getValueAt(fila, 1),tablaMInventarioA.getValueAt(fila, 2),tablaMInventarioA.getValueAt(fila, 3),1});
                            tablaMInventarioA.setModel(manejador_inventario.getInventarioParaAsignacion(nomeclatura));
                        }//asignarEquipo

                    }//estado.equals("Disponible")

                    //No estaba disponible entonces le avisamos al usuario y actualizamos la tabla
                    else{
                        JOptionPane.showMessageDialog(null, "El equipo ya no se encuentra disponible");
                        tablaMInventarioA.setModel(manejador_inventario.getInventarioParaAsignacion(nomeclatura));
                    }//else

                }//else

            }else{
                JOptionPane.showMessageDialog(null, "Antes de asignar inventario es necesario seleccionar al responsable.");
                comboEmpleado.requestFocus();
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
        bandera = true;
        // TODO add your handling code here:
        hilo = new Thread() {
            public void run() {
                //Ciclo infinito
                while (bandera) {

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        checkHostsReScan(sub1, sub2, sub3, limite);
                    } catch (IOException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }//run
        };
        jButton1.setEnabled(false);
        hilo.start();

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
        updateEmpleado ob;
        try {
            int filtro = comboFiltroUsuario.getSelectedIndex();
            String busqueda = txtBusquedaUsuario.getText();
            ob = new updateEmpleado(this, true,manager_users.obtenerIdEmpleado(Username),1,filtro,busqueda);
            ob.setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnEditarActionPerformed

    private void tablaSolicitudesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaSolicitudesMouseReleased
        // TODO add your handling code here:
        //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
        if(SwingUtilities.isRightMouseButton(evt)){
            int r = tablaSolicitudes.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablaSolicitudes.getRowCount())
            tablaSolicitudes.setRowSelectionInterval(r, r);
            MenuSolicitudes.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tablaSolicitudesMouseReleased

    private void vehiculosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vehiculosMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_vehiculosMouseClicked

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
        if(manager_permisos.accesoModulo("alta","Vehiculos",Username)){
            ventana_añadir_vehiculo añadirVehiculo = new ventana_añadir_vehiculo(this, true);
            añadirVehiculo.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para dar de alta vehiculos.");
        }
    }//GEN-LAST:event_btnAñadirVehiculoActionPerformed

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
        buscarEmpleados();
    }//GEN-LAST:event_comboFiltroUsuarioActionPerformed
    public void buscarEmpleados(){
        
        int filtro = comboFiltroUsuario.getSelectedIndex();
        String busqueda = txtBusquedaUsuario.getText();
        
        //comboEmpleado/Usuario
        if(comboEmpUsu.getSelectedItem().toString().equals("Empleados sin usuario")){
            
            if(manager_permisos.accesoModulo("consulta","Empleados",Username)){
                tablaUsuarios.setModel(manager_users.getEmpleadosSinUsuario(filtro,busqueda,comboEmpUsuEstatus.getSelectedItem().toString()));
            }else{
                JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultar empleados.");
                tablaUsuarios.setModel(new DefaultTableModel());
            }//else

        }else if(comboEmpUsu.getSelectedItem().toString().equals("Empleados")){
            
            if(manager_permisos.accesoModulo("consulta","Empleados",Username)){
                tablaUsuarios.setModel(manager_users.getEmpleados(Username,filtro,busqueda,comboEmpUsuEstatus.getSelectedItem().toString()));
            }else{
                JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultar empleados.");
                tablaUsuarios.setModel(new DefaultTableModel());
            }//else
            
        }else{
            if(manager_permisos.accesoModulo("consulta","Usuarios",Username)){
                tablaUsuarios.setModel(manager_users.getUsuarios(Username,filtro,busqueda,comboEmpUsuEstatus.getSelectedItem().toString()));
            }else{
                JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultar usuarios.");
                tablaUsuarios.setModel(new DefaultTableModel());
            }//else
        }//else
        
    }
    private void txtBusquedaUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaUsuarioKeyReleased
        // TODO add your handling code here:
        if((manager_permisos.accesoModulo("consulta","Empleados",Username) && comboEmpUsu.getSelectedItem().toString().equals("Empleados") ) || (manager_permisos.accesoModulo("consulta","Usuarios",Username) && comboEmpUsu.getSelectedItem().toString().equals("Usuarios"))){
            buscarEmpleados();
        }else{
            tablaUsuarios.setModel(new DefaultTableModel());
        }
    }//GEN-LAST:event_txtBusquedaUsuarioKeyReleased

    private void btnAddEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmpleadoActionPerformed
        // TODO add your handling code here:
        int filtro = comboFiltroUsuario.getSelectedIndex();
        String busqueda = txtBusquedaUsuario.getText();
        //Llamamos el forumulario para añadir un nuevo empleado
        if(manager_permisos.accesoModulo("alta","Empleados",Username)){
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
            addEmpleados ob = new addEmpleados(this, true, filtro, busqueda);
            ob.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para dar de alta empleados.");
        }
    }//GEN-LAST:event_btnAddEmpleadoActionPerformed

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
            realizarBusquedaInventario();
        }else{
            tablaInventario.setModel(new DefaultTableModel());
        }//else
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void comboInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboInventarioActionPerformed
        // TODO add your handling code here:
        int filtro = comboFiltro.getSelectedIndex();

            if(comboInventario.getSelectedItem().toString().equals("Inventario")){
                banderaInventario = 1;
                comboFolio.setVisible(true);
                comboEstatus.setVisible(true);
                
                comboFiltro.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
                comboFiltro.addItem("Clave");
                comboFiltro.addItem("Nombre_corto");
                comboFiltro.addItem("Descripción");
                comboFiltro.addItem("Ubicación");
                comboFiltro.addItem("Marca");
                comboFiltro.addItem("Observaciones");
                comboFiltro.addItem("No. Serie");
                comboFiltro.addItem("Modelo");
                comboFiltro.addItem("Color");
                comboFiltro.addItem("Fecha Compra");
                comboFiltro.addItem("Factura");
                if(comboEstatus.getSelectedItem().equals("Asignado")){
                    comboFiltro.addItem("Responsable");
                }//if
                
                if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                    int folio = comboFolio.getSelectedIndex();
                    String estatus = comboEstatus.getSelectedItem().toString();
                    String nomeclatura = "";
                    String busqueda = txtBusqueda.getText();
                    //Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
                    if(folio > 0){nomeclatura = nomeclaturas[folio-1];}
                    tablaInventario.setModel(manager_inventario.getBusquedaInventario(filtro, busqueda, nomeclatura,estatus));
                }else{
                    tablaInventario.setModel(new DefaultTableModel());
                }//else
                
            }else{
                banderaInventario = 2;
                if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                    tablaInventario.setModel(manager_inventario_granel.getInventarioG(filtro));
                }
                
                comboFolio.setVisible(false);
                comboEstatus.setVisible(false);

                comboFiltro.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
                comboFiltro.addItem("Clave");
                comboFiltro.addItem("Producto");
                comboFiltro.addItem("Descripción");
                comboFiltro.addItem("Almacén");
                comboFiltro.addItem("Marca");
                comboFiltro.addItem("Observaciones");
            }
    }//GEN-LAST:event_comboInventarioActionPerformed

    private void btnAddInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddInventarioActionPerformed
        // TODO add your handling code here:

        if(comboInventario.getSelectedItem().equals("Inventario")){

            if(manager_permisos.accesoModulo("alta","Inventario",Username)){
                
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
                
                addInventario ob = new addInventario(this, true);
                ob.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "No cuenta con los permisos para registrar nuevos productos al inventario.");
            }//else

        }else{

            if(manager_permisos.accesoModulo("alta","Inventario",Username)){
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
                addInventarioGranel ob = new addInventarioGranel(this, true);
                ob.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"No cuenta con los permisos para registrar consumibles.");
            }//else

        }//else

    }//GEN-LAST:event_btnAddInventarioActionPerformed

    private void tablaInventarioMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaInventarioMouseReleased
        // TODO add your handling code here:
        if(banderaInventario == 2){

            //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
            if(SwingUtilities.isRightMouseButton(evt)){
                int r = tablaInventario.rowAtPoint(evt.getPoint());
                if (r >= 0 && r < tablaInventario.getRowCount())
                tablaInventario.setRowSelectionInterval(r, r);
                MenuInventarioG.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
            }//clic derecho

        }//if
        else{
            //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
            if(SwingUtilities.isRightMouseButton(evt)){
                AddObservacion.setVisible(false);
                DevolverDis.setVisible(false);//Cambiar el estatus a "Disponible"
                EstatusDefinitivo.setVisible(false);//Cambiar el estatus a "Baja/Donación/Comodato"
                Pendiente.setVisible(false);//Cambiar el estatus a "Pendiente para baja/donación/comodato"
                    ParaBaja.setVisible(false);
                    ParaComodato.setVisible(false);
                    ParaDonacion.setVisible(false);
                NoDisponible.setVisible(false);//Cambiar el estatus a "Reparación/Garantía"
                ActualizarProd.setVisible(false);//Actualizar la información de un producto
                
                int r = tablaInventario.rowAtPoint(evt.getPoint());
                if (r >= 0 && r < tablaInventario.getRowCount()){
                    tablaInventario.setRowSelectionInterval(r, r);

                    if(activarConfirmacion == 1){
                        MenuConfirmacion.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
                    }else{

                        //Mostramos las opciones del popmenú de acuerdo a los filtros.

                        //Selección de pendiente para baja/donación/comodato
                        if(comboEstatus.getSelectedItem().toString().equals("para baja") || comboEstatus.getSelectedItem().toString().equals("para donación") || comboEstatus.getSelectedItem().toString().equals("para comodato")){

                            DevolverDis.setVisible(true);//Cambiar el estatus a "Disponible"
                            EstatusDefinitivo.setVisible(true);//Cambiar el estatus a "Baja/Donación/Comodato"
                            Pendiente.setVisible(true);//Cambiar el estatus a "Pendiente para baja/donación/comodato"
                                ParaBaja.setVisible(true);
                                ParaComodato.setVisible(true);
                                ParaDonacion.setVisible(true);
                            ActualizarProd.setVisible(true);//Actualizar la información de un producto

                            if(comboEstatus.getSelectedItem().toString().equals("para baja")){ EstatusDefinitivo.setText("Cambiar a Baja"); pendientePara = "Baja"; ParaBaja.setVisible(false);}
                            if(comboEstatus.getSelectedItem().toString().equals("para donación")){ EstatusDefinitivo.setText("Cambiar a Donación"); pendientePara = "Donación";ParaDonacion.setVisible(false);}
                            if(comboEstatus.getSelectedItem().toString().equals("para comodato")){ EstatusDefinitivo.setText("Cambiar a Comodato"); pendientePara = "Comodato";ParaComodato.setVisible(false);}   
                            MenuInventario.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
                        }
                        //Selección de Baja/Donación/Comodato o asginado
                        else if(comboEstatus.getSelectedItem().toString().equals("Baja") || comboEstatus.getSelectedItem().toString().equals("Donación") || comboEstatus.getSelectedItem().toString().equals("Comodato") || comboEstatus.getSelectedItem().toString().equals("Asignado")){
                            ActualizarProd.setVisible(true);//Actualizar la información de un producto
                            MenuInventario.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
                        }
                        //Selección de No Disponible
                        else if(comboEstatus.getSelectedItem().toString().equals("No disponible")){
                            DevolverDis.setVisible(true);//Cambiar el estatus a "Disponible"
                            ActualizarProd.setVisible(true);//Actualizar la información de un producto
                            MenuInventario.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
                        }
                        //Selección de Disponible
                        else{
                            AddObservacion.setVisible(true);
                            Pendiente.setVisible(true);//Cambiar el estatus a "Pendiente para baja/donación/comodato"
                                ParaBaja.setVisible(true);
                                ParaComodato.setVisible(true);
                                ParaDonacion.setVisible(true);
                            NoDisponible.setVisible(true);//Cambiar el estatus a "Reparación/Garantía"
                            ActualizarProd.setVisible(true);//Actualizar la información de un producto
                            MenuInventario.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
                        }

                    }//if (r >= 0 && r < tablaInventario.getRowCount())

                }//No se activo el menu de confirmación
                
            }//clic derecho

        }

    }//GEN-LAST:event_tablaInventarioMouseReleased

    private void tablaInventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaInventarioMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaInventarioMouseClicked

    private void btnEditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditar1ActionPerformed
        // TODO add your handling code here:
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
        
        changePassword ob = new changePassword(this, true,Username);
        ob.setVisible(true);
    }//GEN-LAST:event_btnEditar1ActionPerformed

    private void ActualizarAsignacionPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarAsignacionPActionPerformed
        // TODO add your handling code here:
        tablaAsignacionPersonal.setModel(manejador_inventario.getInventarioEmpleadoAsignacionesPersonales(Username));
    }//GEN-LAST:event_ActualizarAsignacionPActionPerformed

    private void tablaAsignacionPersonalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaAsignacionPersonalMouseReleased
        // TODO add your handling code here:
        //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
        if(SwingUtilities.isRightMouseButton(evt)){
            int r = tablaAsignacionPersonal.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablaAsignacionPersonal.getRowCount())
            tablaAsignacionPersonal.setRowSelectionInterval(r, r);
            MenuAsignacionP.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tablaAsignacionPersonalMouseReleased

    private void ActualizarConsumibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarConsumibleActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Inventario",Username)){
            int fila = tablaInventario.getSelectedRow();
            updateInventarioGranel ob;
            ob = new updateInventarioGranel(this, true,tablaInventario.getValueAt(fila, 0).toString());
            ob.setVisible(true);
        }//if del update del consumible
        else{
            JOptionPane.showMessageDialog(null,"Usted no cuenta con los permisos para actualizar un consumible.");
        }
    }//GEN-LAST:event_ActualizarConsumibleActionPerformed

    private void ActualizarInfoVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarInfoVActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("consulta","Vehiculos",Username)){
            tablaVehiculos.setModel(managerVehiculos.getVehiculos());
        }else{
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para consultar los vehiculos.");
        }
    }//GEN-LAST:event_ActualizarInfoVActionPerformed

    private void ActualizarInfoSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarInfoSPActionPerformed
        // TODO add your handling code here:
        tablaSolicitudesPersonal.setModel(manager_solicitud.tabla_Solicitudes_Personales(Username));
    }//GEN-LAST:event_ActualizarInfoSPActionPerformed

    private void ActualizarInfoPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarInfoPPActionPerformed
        // TODO add your handling code here:
        tablaPermisosPersonales.setModel(manager_permisos.getPermisos(false,Username));
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
        if(manager_permisos.accesoModulo("actualizar","Inventario",Username)){
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
                            JOptionPane.showMessageDialog(null, "Solo ingrese cantidades mayores a 0.");
                        }
                    }catch(NumberFormatException e){
                        JOptionPane.showMessageDialog(null,"Solo ingrese numeros");
                    }//try catch

                }//else

                if(canceloStockMin){

                    String codigo = tablaStockMin.getValueAt(fila, 0).toString();//Obtenemos el codigo del producto

                    if(manejador_inventario.actualizarStock(codigo, cantidad)){
                        tablaStockMin.setModel(manejador_inventario.getInventarioStockMin());
                        JOptionPane.showMessageDialog(null, "El inventario se actualizo exitosamente.");
                    }else{
                        JOptionPane.showMessageDialog(null, "Verificar con el distribuidor.");
                    }

                }//canceloStockMin

            }//while
        }else{
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para actualizar los consumibles.");
        }
    }//GEN-LAST:event_AgregarStockActionPerformed

    private void ActualizarInfoSMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarInfoSMActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
            tablaStockMin.setModel(manejador_inventario.getInventarioStockMin());
        }else{
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para consultar los consumibles que estan agotados o estan por agotarse.");
        }
    }//GEN-LAST:event_ActualizarInfoSMActionPerformed

    private void comboEmpUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEmpUsuActionPerformed
        // TODO add your handling code here:
        if(!(comboEmpUsu.getSelectedItem().toString().equals("Usuarios"))){
            tabbedPrincipal.setTitleAt(1, "Empleados");//Le damos el nombre de "Empleados" a la pestaña 
            
            //Botón para agregar empleados
            btnAddEmpleado.setVisible(true);
            
            //COMBOFILTROUSUARIO
            comboFiltroUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
            comboFiltroUsuario.addItem("Nombre");
            comboFiltroUsuario.addItem("Apellido P");
            comboFiltroUsuario.addItem("Apellido M");
            comboFiltroUsuario.addItem("Área");
            comboFiltroUsuario.addItem("Puesto");
            
            //PopuMenu
            if(!(comboEmpUsu.getSelectedItem().toString().equals("Empleados"))){
                Asignar_usuario.setVisible(true);
            }else{
                Asignar_usuario.setVisible(false);
            }
            
        }else{
            btnAddEmpleado.setVisible(false);
            
            tabbedPrincipal.setTitleAt(1, "Usuarios");//Le damos el nombre de "Usuarios" a la pestaña
            //COMBOFILTROUSUARIO
            comboFiltroUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
            comboFiltroUsuario.addItem("Usuario");
            comboFiltroUsuario.addItem("Nombre");
            comboFiltroUsuario.addItem("Apellido P");
            comboFiltroUsuario.addItem("Apellido M");
            comboFiltroUsuario.addItem("Perfil");
            comboFiltroUsuario.addItem("Área");
            comboFiltroUsuario.addItem("Puesto");
        }//else
    }//GEN-LAST:event_comboEmpUsuActionPerformed

    private void Asignar_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Asignar_usuarioActionPerformed
        // TODO add your handling code here:
        
        if(manager_permisos.accesoModulo("alta","Usuarios",Username)){
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
            int id = Integer.parseInt(tablaUsuarios.getValueAt(fila, 0).toString());
            
            addUsuarios ob = new addUsuarios(this, true,id);
            
            ob.txtNombre.setText(tablaUsuarios.getValueAt(fila, 1).toString());
            ob.txtApellidoP.setText(tablaUsuarios.getValueAt(fila, 2).toString());
            ob.txtApellidoM.setText(tablaUsuarios.getValueAt(fila, 3).toString());
            
            
            ob.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para dar de alta usuarios.");
        }
    }//GEN-LAST:event_Asignar_usuarioActionPerformed

    private void activarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activarActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Usuarios",Username)){
            //Obtenemos la fila y con dicha fila obtenemos el usuario
            int fila = tablaUsuarios.getSelectedRow();
            usuario = tablaUsuarios.getValueAt(fila, 0).toString();
            //Creamos un cuadro de dialogo para que confirme la eliminación del usuario o la cancele
            Object[] botones = {"Confirmar","Cancelar"};
            int opcion = JOptionPane.showOptionDialog(this,"¿Activar al usuario "+usuario+"?", "Confirmación",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE  , null, botones, botones[0]);

            //Acepta eliminar al usuario
            if(opcion == 0){

                if(manager_users.estatusUsuario(usuario,"Activo")){
                    JOptionPane.showMessageDialog(null, "El usuario se encuentra activo nuevamente.");
                    buscarEmpleados();
                }//if(eliminarEmpleado())
                else{
                        JOptionPane.showMessageDialog(null, "Verificar con el distribuidor.");
                }
            }//if(opcion == 0)
        }else{
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para activar usuarios.");
        }
    }//GEN-LAST:event_activarActionPerformed

    private void comboFolioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFolioActionPerformed
        // TODO add your handling code here:
        realizarBusquedaInventario();
    }//GEN-LAST:event_comboFolioActionPerformed

    private void comboEstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEstatusActionPerformed
        // TODO add your handling code here:
        realizarBusquedaInventario();
        
        if(comboEstatus.getSelectedItem().equals("Asignado")){
            comboFiltro.addItem("Responsable");
        }else{
            comboFiltro.removeItem("Responsable");
        }
    }//GEN-LAST:event_comboEstatusActionPerformed
    //Esto es para no repetir el mensaje
    public void realizarBusquedaInventario(){
        if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
            int folio = comboFolio.getSelectedIndex();
            String estatus = comboEstatus.getSelectedItem().toString();
            String nomeclatura = "";
            int filtro = comboFiltro.getSelectedIndex();
            String inventario = comboInventario.getSelectedItem().toString();
            String busqueda = txtBusqueda.getText();
            //Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
            if(folio > 0){nomeclatura = nomeclaturas[folio-1];}
                if(inventario.equals("Inventario")){
                        tablaInventario.setModel(manager_inventario.getBusquedaInventario(filtro, busqueda, nomeclatura,estatus));
                }else{
                        tablaInventario.setModel(manager_inventario_granel.getBusquedaInventario(filtro, busqueda, nomeclatura,estatus));
                }
        }else{
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultas en el inventario.");
            tablaInventario.setModel(new DefaultTableModel());
        }
    }
    
    private void comboFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFiltroActionPerformed
        // TODO add your handling code here:
        realizarBusquedaInventario();
    }//GEN-LAST:event_comboFiltroActionPerformed

    private void comboFolioAsignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFolioAsignacionActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("consulta","Resguardo",Username)){
            int folio = comboFolioAsignacion.getSelectedIndex();
            String nomeclatura = "";
            //Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
            if(folio > 0){nomeclatura = nomeclaturas[folio-1];}
            tablaMInventarioA.setModel(manejador_inventario.getInventarioParaAsignacion(nomeclatura));
        }else{
            realizarResguardoAsignacion();
        }
    }//GEN-LAST:event_comboFolioAsignacionActionPerformed

    private void tablaSolicitarGranelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaSolicitarGranelMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaSolicitarGranelMouseReleased

    private void btnSolicitarSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolicitarSalidaActionPerformed
        // TODO add your handling code here:
        String [] ids = new String[tablaCantidadGranel.getRowCount()];
        int[] Cantidad = new int[tablaCantidadGranel.getRowCount()];
                   
        for(int i = 0; i<tablaCantidadGranel.getRowCount();i++){
            
            Cantidad[i] = Integer.parseInt(tablaCantidadGranel.getValueAt(i, 3).toString());
            ids[i] = tablaCantidadGranel.getValueAt(i, 0).toString();
             
            
        }//Recorremos toda la tabla para ver que solicito y cuanto
        
        //Realizamos el registro
        if(manager_solicitud.registro_SolicitudSalida(Username, ids, Cantidad)){
            JOptionPane.showMessageDialog(null, "Se realizo correctamente la solicitud de salida de almacen.");
            limpiarTablaCantidadGranel();
            btnCancelarSalida.setEnabled(false);
            btnSolicitarSalida.setEnabled(false);
            
        }else{
            JOptionPane.showMessageDialog(null, "No se pudo realizar la solicitud de salida de almacen, verificar con el distribuidor.");
            this.dispose();
        }
    }//GEN-LAST:event_btnSolicitarSalidaActionPerformed

    private void comboFiltroUsuario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFiltroUsuario1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboFiltroUsuario1ActionPerformed

    private void tablaCantidadGranelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaCantidadGranelMouseReleased
        // TODO add your handling code here:
        //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
        if(SwingUtilities.isRightMouseButton(evt)){
            int r = tablaCantidadGranel.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablaCantidadGranel.getRowCount())
            tablaCantidadGranel.setRowSelectionInterval(r, r);
            MenuSolicitarSalida.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tablaCantidadGranelMouseReleased

    private void btnCancelarSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarSalidaActionPerformed
        // TODO add your handling code here:
        
        String[] opciones = {"Aceptar", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(null, "¿Desea cancelar la solicitud de salida de almacen?","Confirmación de cancelación", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if(seleccion == 0){
            limpiarTablaCantidadGranel();
            btnCancelarSalida.setEnabled(false);
            btnSolicitarSalida.setEnabled(false);
        }
    }//GEN-LAST:event_btnCancelarSalidaActionPerformed

    private void tablaSolicitarGranelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaSolicitarGranelMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            int cantidad = 0;
            int fila = tablaSolicitarGranel.getSelectedRow();
            //Esto es para validar que ingrese solo numeros y mientras no lo haga, seguira preguntado hasta que
            //solo teclee numeros o cancele el movimiento
            boolean entero = true;
            boolean canceloCantidad = true;
            while(entero){

                String cadena = JOptionPane.showInputDialog("Ingrese la cantidad que desea solicitar");
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
                if(!(existeCodigoTablaSolicitarGranel(tablaSolicitarGranel.getValueAt(fila, 0).toString(),cantidad))){
                    modeloCantidadSalida.addRow(new Object[]{tablaSolicitarGranel.getValueAt(fila, 0),tablaSolicitarGranel.getValueAt(fila, 1),tablaSolicitarGranel.getValueAt(fila, 2),cantidad});
                }
                btnCancelarSalida.setEnabled(true);
                btnSolicitarSalida.setEnabled(true);
            }
                        
        }//If del doble click
    }//GEN-LAST:event_tablaSolicitarGranelMouseClicked

    private void CancelarSSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarSSActionPerformed
        // TODO add your handling code here: tablaCantidadGranel
        int fila = tablaCantidadGranel.getSelectedRow();
        String[] opciones = {"Aceptar", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(null, "¿Desea cancelar el producto \""+tablaCantidadGranel.getValueAt(fila, 0).toString()+"\"?","Confirmación de cancelación", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if(seleccion == 0){
            modeloCantidadSalida.removeRow(fila);
            if(tablaCantidadGranel.getRowCount() == 0){
                btnCancelarSalida.setEnabled(false);
                btnSolicitarSalida.setEnabled(false);
            }//Deshabilitar botones cuando ya no haya registros
        }//Acepto cancelar la solicitud

    }//GEN-LAST:event_CancelarSSActionPerformed

    private void btnEntregarRecoleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntregarRecoleccionActionPerformed
        // TODO add your handling code here:
        
        if(manager_permisos.accesoModulo("actualizar","Resguardo",Username)){
            //Declaramos las variables donde guardaremos los datos para actualizarlos y entregarlos
            String ids = "";
            String claves = "";
            String ubicaciones = "";
            String observaciones="";
            boolean registroCorrecto = false;
            for(int i = 0; i<tablaRecoleccion.getRowCount();i++){

                //Si lo selecciono, entonces intentamos guardar el registro
                if((boolean)tablaRecoleccion.getValueAt(i, 0)){

                    //Verificamos que haya seleccionado la nueva ubicación del equipo o producto que entregará
                    if(!tablaRecoleccion.getValueAt(i, 11).toString().equals("Selecciona la nueva ubicación...")){

                        //Vemos si tiene observaciones el equipo entregado
                        if(tablaRecoleccion.getValueAt(i, 9).toString().equals("")){
                            //Si esta vacío entonces concatenamos un espacio en blanco
                            observaciones += " ,,";
                        }else{
                            observaciones += tablaRecoleccion.getValueAt(i, 9).toString()+",,";
                        }

                        ids += tablaRecoleccion.getValueAt(i, 1).toString()+",";
                        claves += tablaRecoleccion.getValueAt(i, 2).toString()+",";
                        ubicaciones += tablaRecoleccion.getValueAt(i, 10).toString()+",";
                        registroCorrecto = true;
                        
                    }else{
                        //Como no se selecciono la nueva ubicación, le mandamos un mensaje y le marcamos la celda donde no selecciono la ubicación
                        JOptionPane.showMessageDialog(this, "El equipo o producto \""+tablaRecoleccion.getValueAt(i, 2).toString()+"\", no especificó su nueva ubicación.");
                        tablaRecoleccion.changeSelection(i, 10, false, false);
                        registroCorrecto = false;
                        break;
                    }//else

                }//if

            }//for

            if(registroCorrecto){
                //Convertimos en arreglo la información que se obtuvo con el ciclo de la tablaRecolección
                String [] idVales = ids.split(",");
                String [] Claves = claves.split(",");
                String [] Ubicaciones = ubicaciones.split(",");
                String [] Observaciones = observaciones.split(",,");

                //Se realiza la operación de entrega de productos y su respectivo cambio
                if(manejador_inventario.recoleccionInventario(idVales,Claves,Ubicaciones,Observaciones)){
                   // JOptionPane.showMessageDialog(this,"Se registro el movimiento de recolección exitosamente.");
                    metodoValeRecoleccion();
                    //Actualizamos el combo de los empleados que tienen productos asignados
                    comboEmpleadoR.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
                    comboEmpleadoR.addItem("Seleccione al empleado...");
                    manejador_inventario.getEmpleadosAsignacion(comboEmpleadoR,comboArea1.getSelectedIndex()+1);

                    tablaRecoleccion.setModel(modeloRecoleccion);
                    btnEntregarRecoleccion.setEnabled(false);
                }else{
                    JOptionPane.showMessageDialog(this, "Verificar con el distribuidor.");
                }

            }//registroCorrecto
        }else{
            JOptionPane.showMessageDialog(this, "No cuenta con los permisos para generar el vale de recolección.");
        }
        
    }//GEN-LAST:event_btnEntregarRecoleccionActionPerformed

    private void ActualizarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarProdActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Inventario",Username)){
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

            int fila = tablaInventario.getSelectedRow();
            String clave = tablaInventario.getValueAt(fila, 0).toString();

            updateInventario ob = new updateInventario(this, true,clave);
            ob.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para actualizar el estatus del inventario.");
        }
    }//GEN-LAST:event_ActualizarProdActionPerformed

    private void ParaBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ParaBajaActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("baja","Inventario",Username)){
                int fila = tablaInventario.getSelectedRow();
                int folio = comboFolio.getSelectedIndex();
                String estatus = comboEstatus.getSelectedItem().toString();
                String nomeclatura = "";
                int filtro = comboFiltro.getSelectedIndex();
                String busqueda = txtBusqueda.getText();
                //Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
                if(folio > 0){nomeclatura = nomeclaturas[folio-1];}
                pendientePara = "para baja";
                tablaInventario.setModel(manager_inventario.cambiarEstatus("Pendiente ",pendientePara,filtro, busqueda,nomeclatura,estatus));
                
                tablaInventario.setValueAt(true, fila, 0);
                
                //Bandera para activar el popuMenu de aceptar o cancelar
                activarConfirmacion = 1;
                
                //Deshabilitamos las demás opciones para que no actualice la tabla, si quiere cambiar los 
                //criterios de busqueda entonces tendra que dar cancelar
                btnAddInventario.setEnabled(false);
                comboInventario.setEnabled(false);
                comboEstatus.setEnabled(false);
                comboFolio.setEnabled(false);
                comboFiltro.setEnabled(false);
                txtBusqueda.setEnabled(false);

            }else{
                JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para dar de baja productos del inventario.");
            }
    }//GEN-LAST:event_ParaBajaActionPerformed

    private void ParaComodatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ParaComodatoActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Inventario",Username)){
                int fila = tablaInventario.getSelectedRow();
                int folio = comboFolio.getSelectedIndex();
                String estatus = comboEstatus.getSelectedItem().toString();
                String nomeclatura = "";
                int filtro = comboFiltro.getSelectedIndex();
                String busqueda = txtBusqueda.getText();
                //Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
                if(folio > 0){nomeclatura = nomeclaturas[folio-1];}
                pendientePara = "para comodato";
                tablaInventario.setModel(manager_inventario.cambiarEstatus("Pendiente ",pendientePara,filtro, busqueda,nomeclatura,estatus));
                
                
                tablaInventario.setValueAt(true, fila, 0);
                
                //Bandera para activar el popuMenu de aceptar o cancelar
                activarConfirmacion = 1;
                
                //Deshabilitamos las demás opciones para que no actualice la tabla, si quiere cambiar los 
                //criterios de busqueda entonces tendra que dar cancelar
                btnAddInventario.setEnabled(false);
                comboInventario.setEnabled(false);
                comboEstatus.setEnabled(false);
                comboFolio.setEnabled(false);
                comboFiltro.setEnabled(false);
                txtBusqueda.setEnabled(false);

            }else{
                JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para actualizar el estatus del inventario.");
            }
    }//GEN-LAST:event_ParaComodatoActionPerformed

    private void ParaDonacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ParaDonacionActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Inventario",Username)){
                int fila = tablaInventario.getSelectedRow();
                int folio = comboFolio.getSelectedIndex();
                String estatus = comboEstatus.getSelectedItem().toString();
                String nomeclatura = "";
                int filtro = comboFiltro.getSelectedIndex();
                String busqueda = txtBusqueda.getText();
                //Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
                if(folio > 0){nomeclatura = nomeclaturas[folio-1];}
                pendientePara = "para donación";
                tablaInventario.setModel(manager_inventario.cambiarEstatus("Pendiente ",pendientePara,filtro, busqueda,nomeclatura,estatus));
                
                
                tablaInventario.setValueAt(true, fila, 0);
                
                //Bandera para activar el popuMenu de aceptar o cancelar
                activarConfirmacion = 1;
                
                //Deshabilitamos las demás opciones para que no actualice la tabla, si quiere cambiar los 
                //criterios de busqueda entonces tendra que dar cancelar
                btnAddInventario.setEnabled(false);
                comboInventario.setEnabled(false);
                comboEstatus.setEnabled(false);
                comboFolio.setEnabled(false);
                comboFiltro.setEnabled(false);
                txtBusqueda.setEnabled(false);

            }else{
                JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para actualizar el estatus del inventario.");
            }
    }//GEN-LAST:event_ParaDonacionActionPerformed

    private void DevolverDisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DevolverDisActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Inventario",Username)){
                int fila = tablaInventario.getSelectedRow();
                int folio = comboFolio.getSelectedIndex();
                String estatus = comboEstatus.getSelectedItem().toString();
                String nomeclatura = "";
                int filtro = comboFiltro.getSelectedIndex();
                String busqueda = txtBusqueda.getText();
                pendientePara = "Disponible";
                //Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
                if(folio > 0){nomeclatura = nomeclaturas[folio-1];}
                tablaInventario.setModel(manager_inventario.cambiarEstatus("Devolver a ",pendientePara,filtro, busqueda,nomeclatura,estatus));
                
                tablaInventario.setValueAt(true, fila, 0);
                
                //Bandera para activar el popuMenu de aceptar o cancelar
                activarConfirmacion = 1;
                
                //Deshabilitamos las demás opciones para que no actualice la tabla, si quiere cambiar los 
                //criterios de busqueda entonces tendra que dar cancelar
                btnAddInventario.setEnabled(false);
                comboInventario.setEnabled(false);
                comboEstatus.setEnabled(false);
                comboFolio.setEnabled(false);
                comboFiltro.setEnabled(false);
                txtBusqueda.setEnabled(false);

            }else{
                JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para actualizar el estatus del inventario.");
            }
    }//GEN-LAST:event_DevolverDisActionPerformed

    private void ActualizarEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarEmployeeActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Empleados",Username)){
            int filtro = comboFiltroUsuario.getSelectedIndex();
            String busqueda = txtBusquedaUsuario.getText();
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
        updateEmpleado ob;
            try {
                ob = new updateEmpleado(this, true,manager_users.obtenerIdEmpleado(tablaUsuarios.getValueAt(fila,0).toString()),2,filtro,busqueda);
                ob.setVisible(true);
            } catch (ParseException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }else{
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para actualizar empleados.");
        }
    }//GEN-LAST:event_ActualizarEmployeeActionPerformed

    private void AddStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddStockActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Inventario",Username)){
            boolean entero = true;
            boolean canceloStockMin = true;
            int cantidad = 0;
            int fila = tablaInventario.getSelectedRow();
            int folio = comboFolio.getSelectedIndex();
            while(entero){    

                String cadena = JOptionPane.showInputDialog("Ingrese las unidades que se desean agregar");

                if(cadena == null){
                    entero = false;
                    canceloStockMin = false;
                }else{

                    try{

                        cantidad = Integer.parseInt(cadena);

                        if(cantidad > 0){
                            entero = false;                    
                        }else{
                            JOptionPane.showMessageDialog(null, "Solo ingrese cantidades mayores a 0.");
                        }
                    }catch(NumberFormatException e){
                        JOptionPane.showMessageDialog(null,"Solo ingrese numeros");
                    }//try catch

                }//else
            }//while
            if(canceloStockMin){

                String codigo = tablaInventario.getValueAt(fila, 0).toString();//Obtenemos el codigo del producto

                if(manejador_inventario.actualizarStock(codigo, cantidad)){
                    tablaInventario.setModel(manager_inventario_granel.getInventarioG(folio));
                    JOptionPane.showMessageDialog(null, "El inventario se actualizo exitosamente.");
                }else{
                    JOptionPane.showMessageDialog(null, "Verificar con el distribuidor.");
                }

            }//canceloStockMin   
        }else{
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para actualizar el stock de los consumibles.");
        }
    }//GEN-LAST:event_AddStockActionPerformed

    private void comboFiltroConsumiblesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFiltroConsumiblesActionPerformed
        // TODO add your handling code here:
        int filtro = comboFiltroConsumibles.getSelectedIndex();
        String busqueda = txtBusquedaConsumibles.getText();
        tablaSolicitarGranel.setModel(manager_inventario_granel.tablaSolicitarInvGranel(filtro,busqueda));
        
    }//GEN-LAST:event_comboFiltroConsumiblesActionPerformed

    private void txtBusquedaConsumiblesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaConsumiblesKeyReleased
        // TODO add your handling code here:
        int filtro = comboFiltroConsumibles.getSelectedIndex();
        String busqueda = txtBusquedaConsumibles.getText();
        tablaSolicitarGranel.setModel(manager_inventario_granel.tablaSolicitarInvGranel(filtro,busqueda));
    }//GEN-LAST:event_txtBusquedaConsumiblesKeyReleased

    private void solicitudesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_solicitudesFocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_solicitudesFocusGained

    private void tabbedPrincipalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabbedPrincipalFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tabbedPrincipalFocusGained

    private void tabbedPrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabbedPrincipalMouseClicked
        // TODO add your handling code here:
        int indicePestañas = tabbedPrincipal.getSelectedIndex();
        
        switch(indicePestañas){
            //Pestaña de inventario
            case 0:
                if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                    if(activarConfirmacion == 2){
                        realizarBusquedaInventario();
                    }
                }else{
                    tablaInventario.setModel(new DefaultTableModel());
                }//else
                break;
            //Pestaña de empleados y usuarios
            case 1:
                if(comboEmpUsuEstatus.getSelectedItem().equals("Todos")){
                    activarE.setVisible(false);
                    bajaE.setVisible(false);
                    dar_baja.setVisible(false);
                    activar.setVisible(false);
                }else if(comboEmpUsuEstatus.getSelectedItem().equals("Activo")){
                    activarE.setVisible(true);
                    bajaE.setVisible(false);
                    dar_baja.setVisible(false);
                    activar.setVisible(true);
                }else{
                    activarE.setVisible(false);
                    bajaE.setVisible(true);
                    dar_baja.setVisible(true);
                    activar.setVisible(false);
                }
                
                if( (manager_permisos.accesoModulo("consulta","Empleados",Username) && (comboEmpUsu.getSelectedItem().toString().equals("Empleados") || comboEmpUsu.getSelectedItem().toString().equals("Empleados sin usuario")) ) || 
                        (manager_permisos.accesoModulo("consulta","Usuarios",Username) && comboEmpUsu.getSelectedItem().toString().equals("Usuarios"))){
                    buscarEmpleados();
                }else{
                    tablaUsuarios.setModel(new DefaultTableModel());
                }
                break;
            //Pestaña personal del empleado que se logueo
            case 3:
                DefaultTableModel limpiarTabla = new DefaultTableModel();
                tablaResguardoPersonal.setModel(limpiarTabla);
                tablaResguardoPersonal.setModel(manager_complemento.getResguardoPersonal(Username));
                
                tablaSolicitudesPersonal.setModel(limpiarTabla);
                tablaSolicitudesPersonal.setModel(manager_solicitud.tabla_Solicitudes_Personales(Username));
                
                tablaPermisosPersonales.setModel(limpiarTabla);
                tablaPermisosPersonales.setModel(manager_permisos.getPermisos(false,Username));
                
                tablaAsignacionPersonal.setModel(limpiarTabla);
                tablaAsignacionPersonal.setModel(manejador_inventario.getInventarioEmpleadoAsignacionesPersonales(Username));
                break;
            //Pestaña personal del empleado que se logueo
            case 4:
                if(rb_asignacion1.isSelected()){
                    if(manager_permisos.accesoModulo("consulta","Resguardo",Username)){
                        realizarResguardoAsignacion();//Asignación
                    }else{
                        refrescarResguardoAsignacion = 2;
                        //Bloqueamos el uso de la asignacion
                        comboEmpleado.setEnabled(false);
                        rb_inventario_normal1.setEnabled(false);
                        rb_inventario_granel1.setEnabled(false);
                        tablaMInventarioA.setEnabled(false);
                        btn_generar_vale3.setEnabled(false);
                        btn_cancelar2.setEnabled(false);
                    }
                }else{
                    if(manager_permisos.accesoModulo("consulta","Resguardo",Username)){
                        realizarResguardoRecoleccion();
                    }else{
                        refrescarResguardoRecoleccion = 2;
                        
                        //Bloqueamos el uso de la asignacion
                        comboEmpleadoR.setEnabled(false);
                        tablaRecoleccion.setEnabled(false);

                        //Devolvemos los productos al estado anterior
                        limpiarTablaRecoleccion();
                        comboEmpleadoR.setSelectedIndex(0);
                    }
                }//else
                
                break;
            //Pestaña de solicitud y stock minimo
            case 6:
                if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                    if(manager_permisos.verTablaSolicitudes(Username) > 0){

                        tablaSolicitudes.setModel(manager_solicitud.tabla_SolicitudesMejorada(Username));
                        int solicitud = tablaSolicitudes.getRowCount();
                        if(solicitud > 0){
                            tabbedPrincipal.setTitleAt(6, "Solicitudes ("+solicitud+")");//Le damos el nombre a esa pestaña
                        }else{
                            tabbedPrincipal.setTitleAt(6, "Solicitudes");//Le damos el nombre a esa pestaña    
                        }
                    }//if verTablaSolicitudes

                    tablaStockMin.setModel(manejador_inventario.getInventarioStockMin());
                }else{
                    DefaultTableModel tablaLimpia = new DefaultTableModel();
                    tablaStockMin.setModel(tablaLimpia);
                    tablaSolicitudes.setModel(tablaLimpia);
                    tabbedPrincipal.setTitleAt(6, "Solicitudes");//Le damos el nombre a esa pestaña
                }                
                break;
            
        }//switch
        
    }//GEN-LAST:event_tabbedPrincipalMouseClicked

    private void EstatusDefinitivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EstatusDefinitivoActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Inventario",Username)){
                int fila = tablaInventario.getSelectedRow();
                int folio = comboFolio.getSelectedIndex();
                String estatus = comboEstatus.getSelectedItem().toString();
                String nomeclatura = "";
                int filtro = comboFiltro.getSelectedIndex();
                String busqueda = txtBusqueda.getText();
                //Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
                if(folio > 0){nomeclatura = nomeclaturas[folio-1];}
                tablaInventario.setModel(manager_inventario.cambiarEstatus("Cambiar a ",pendientePara,filtro, busqueda,nomeclatura,estatus));
                
                
                tablaInventario.setValueAt(true, fila, 0);
                
                //Bandera para activar el popuMenu de aceptar o cancelar
                activarConfirmacion = 1;
                
                //Deshabilitamos las demás opciones para que no actualice la tabla, si quiere cambiar los 
                //criterios de busqueda entonces tendra que dar cancelar
                btnAddInventario.setEnabled(false);
                comboInventario.setEnabled(false);
                comboEstatus.setEnabled(false);
                comboFolio.setEnabled(false);
                comboFiltro.setEnabled(false);
                txtBusqueda.setEnabled(false);

            }else{
                JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para actualizar el estatus del inventario.");
            }
    }//GEN-LAST:event_EstatusDefinitivoActionPerformed

    private void AddDocumentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddDocumentsActionPerformed
        // TODO add your handling code here:
        addDocument ob = new addDocument(this, true);
        ob.setVisible(true);
    }//GEN-LAST:event_AddDocumentsActionPerformed

    private void SelectProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectProductsActionPerformed
        // TODO add your handling code here:
        Ventana_Documentos ob = new Ventana_Documentos(this, true);
        ob.setVisible(true);
    }//GEN-LAST:event_SelectProductsActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        Ventana_ConsultaDocumentos ob = new Ventana_ConsultaDocumentos(this, true);
        ob.setVisible(true);        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void NoDisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoDisponibleActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Inventario",Username)){
                int fila = tablaInventario.getSelectedRow();
                int folio = comboFolio.getSelectedIndex();
                String estatus = comboEstatus.getSelectedItem().toString();
                String nomeclatura = "";
                int filtro = comboFiltro.getSelectedIndex();
                String busqueda = txtBusqueda.getText();
                pendientePara = "No disponible";
                //Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
                if(folio > 0){nomeclatura = nomeclaturas[folio-1];}
                tablaInventario.setModel(manager_inventario.cambiarEstatus("Producto ",pendientePara,filtro, busqueda,nomeclatura,estatus));
                
                tablaInventario.setValueAt(true, fila, 0);
                
                //Bandera para activar el popuMenu de aceptar o cancelar
                activarConfirmacion = 1;
                
                //Deshabilitamos las demás opciones para que no actualice la tabla, si quiere cambiar los 
                //criterios de busqueda entonces tendra que dar cancelar
                btnAddInventario.setEnabled(false);
                comboInventario.setEnabled(false);
                comboEstatus.setEnabled(false);
                comboFolio.setEnabled(false);
                comboFiltro.setEnabled(false);
                txtBusqueda.setEnabled(false);

            }else{
                JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para actualizar el estatus del inventario.");
            }
    }//GEN-LAST:event_NoDisponibleActionPerformed

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

    private void SolicitarBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SolicitarBajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SolicitarBajaActionPerformed

    private void ServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ServicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ServicioActionPerformed

    private void CancelarSolicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarSolicitudActionPerformed
        // TODO add your handling code here:
        cancelarVale();
        if(manager_permisos.accesoModulo("baja","Solicitudes",Username)){
            /* 1.- Llenamos las tablas con los datos de los productos para cancelar la solicitud*/
            int fila = tablaSolicitudes.getSelectedRow();
            idAtenderSalida = tablaSolicitudes.getValueAt(fila, 0).toString();
            
            JTable tablaCancelacion = new JTable();
            tablaCancelacion.setModel(manager_solicitud.tabla_SolicitudSalida(idAtenderSalida));
            
            /* 2.- Obtenemos los datos de los productos para cancelar la solicitud*/
            Claves = new String[tablaCancelacion.getRowCount()];
            Cantidad = new int[tablaCancelacion.getRowCount()];

            for(int i = 0;i<tablaCancelacion.getRowCount();i++){
                Claves[i] = tablaCancelacion.getValueAt(i, 0).toString();//Obtenemos las claves
                Cantidad[i] = 0;//Porque se cancelo la solicitud
            }//Llenar vector de los codigos de barras
            
            if(manejador_inventario.estatusSalidaAlmacen(Claves, Cantidad, Username,idAtenderSalida,"Cancelada")){
                JOptionPane.showMessageDialog(null,"Solicitud cancelada con exito.");
                
                if(manager_permisos.accesoModulo("consulta","Solicitudes",Username)){
                    tablaSolicitudes.setModel(manager_solicitud.tabla_SolicitudesMejorada(Username));
                    int solicitud = tablaSolicitudes.getRowCount();
                    if(solicitud > 0){
                        tabbedPrincipal.setTitleAt(6, "Solicitudes ("+solicitud+")");//Le damos el nombre a esa pestaña
                    }else{
                        tabbedPrincipal.setTitleAt(6, "Solicitudes");//Le damos el nombre a esa pestaña    
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Tus permisos para consultar las solicitudes y productos con stock mínimo han sido revocados.");
                    tablaStockMin.setModel(new DefaultTableModel());
                    tablaSolicitudes.setModel(new DefaultTableModel());
                    tabbedPrincipal.setTitleAt(6, "Solicitudes");//Le damos el nombre a esa pestaña
                }

                tablaStockMin.setModel(manejador_inventario.getInventarioStockMin());
            }else{
                JOptionPane.showMessageDialog(null,"Verificar con el distribuidor.");
            }
        }else{
        
        }
    }//GEN-LAST:event_CancelarSolicitudActionPerformed

    private void activarEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activarEActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Empleados",Username)){
            //Obtenemos la fila y con dicha fila obtenemos el usuario
            int fila = tablaUsuarios.getSelectedRow();
            String empleado = tablaUsuarios.getValueAt(fila, 1).toString()+" "+tablaUsuarios.getValueAt(fila, 2).toString()+" "+tablaUsuarios.getValueAt(fila, 3).toString();
            //Creamos un cuadro de dialogo para que confirme la eliminación del usuario o la cancele
            Object[] botones = {"Confirmar","Cancelar"};
            int opcion = JOptionPane.showOptionDialog(this,"¿Activar al empleado \""+empleado+"\"?", "Confirmación",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE  , null, botones, botones[0]);

            //Acepta eliminar al usuario
            if(opcion == 0){

                if(manager_users.estatusEmpleado(Integer.parseInt(tablaUsuarios.getValueAt(fila, 0).toString()),"Activo")){
                    JOptionPane.showMessageDialog(null, "El empleado \""+empleado+"\" se encuentra activo nuevamente.");
                    buscarEmpleados();
                }//if(eliminarEmpleado())
                else{
                        JOptionPane.showMessageDialog(null, "Verificar con el distribuidor.");
                }
            }//if(opcion == 0)
        }else{
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para activar empleados.");
        }
    }//GEN-LAST:event_activarEActionPerformed

    private void bajaEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bajaEActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("baja","Empleados",Username)){
            //Obtenemos la fila y con dicha fila obtenemos el usuario
            int fila = tablaUsuarios.getSelectedRow();
            String empleado = tablaUsuarios.getValueAt(fila, 1).toString()+" "+tablaUsuarios.getValueAt(fila, 2).toString()+" "+tablaUsuarios.getValueAt(fila, 3).toString();
            //Creamos un cuadro de dialogo para que confirme la eliminación del usuario o la cancele
            Object[] botones = {"Confirmar","Cancelar"};
            int opcion = JOptionPane.showOptionDialog(this,"¿Dar de baja al empleado \""+empleado+"\"?", "Confirmación",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE  , null, botones, botones[0]);

            //Acepta eliminar al usuario
            if(opcion == 0){

                if(manager_users.estatusEmpleado(Integer.parseInt(tablaUsuarios.getValueAt(fila, 0).toString()),"Baja")){
                    JOptionPane.showMessageDialog(null, "El empleado \""+empleado+"\" se ha dado de baja.");
                    buscarEmpleados();
                }//if(eliminarEmpleado())
                else{
                        JOptionPane.showMessageDialog(null, "Verificar con el distribuidor.");
                }
            }//if(opcion == 0)
        }else{
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para dar de baja empleados.");
        }
    }//GEN-LAST:event_bajaEActionPerformed

    private void tablaUsuariosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaUsuariosMouseReleased
        // TODO add your handling code here:

        if(!(comboEmpUsu.getSelectedItem().toString().equals("Usuarios"))){
            //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
            if(SwingUtilities.isRightMouseButton(evt)){
                int r = tablaUsuarios.rowAtPoint(evt.getPoint());
                if (r >= 0 && r < tablaUsuarios.getRowCount())
                tablaUsuarios.setRowSelectionInterval(r, r);
                MenuEmpleados.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
            }//clic derecho
        }else{
            
            //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
            if(SwingUtilities.isRightMouseButton(evt)){
                int r = tablaUsuarios.rowAtPoint(evt.getPoint());
                if (r >= 0 && r < tablaUsuarios.getRowCount())
                tablaUsuarios.setRowSelectionInterval(r, r);
                
                MenuUsuarios.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
            }//clic derecho

        }

    }//GEN-LAST:event_tablaUsuariosMouseReleased

    private void ExportarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportarUsuariosActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("consulta","Usuarios",Username)){
            try{
                Excel excel = new Excel();
                int filtro = comboFiltroUsuario.getSelectedIndex();
                String busqueda = txtBusquedaUsuario.getText();
                JTable tablaUsuariosExcel = new JTable();
                tablaUsuariosExcel.setModel(manager_users.getUsuariosExcel(Username,filtro,busqueda));
                excel.GuardarComo(tablaUsuariosExcel);
            }catch(NullPointerException e){
            
            }
        }else{
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para consultar usuarios.");
        }//else
            
    }//GEN-LAST:event_ExportarUsuariosActionPerformed

    private void ExportarEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportarEmpleadosActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("consulta","Empleados",Username)){
            try{
                Excel excel = new Excel();
                int filtro = comboFiltroUsuario.getSelectedIndex();
                String busqueda = txtBusquedaUsuario.getText();
                JTable tablaEmpleadosExcel = new JTable();

                if(comboEmpUsu.getSelectedItem().toString().equals("Empleados")){
                    tablaEmpleadosExcel.setModel(manager_users.getEmpleadosExcel(Username,filtro,busqueda));
                }else{
                    tablaEmpleadosExcel.setModel(manager_users.getEmpleadosSinUsuarioExcel(filtro,busqueda));
                }

                excel.GuardarComo(tablaEmpleadosExcel);
            }catch(NullPointerException e){
            
            }
        }else{
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para consultar la infomración de empleados.");
        }
    }//GEN-LAST:event_ExportarEmpleadosActionPerformed

    private void izquierdaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_izquierdaBtnActionPerformed
        // TODO add your handling code here:

        // TODO add your handling code here:
        nFoto--;
        banderaFoto = "i";
        derechaBtn.setEnabled(true);

        cargarImagen(auxiliarFoto,nFoto);
    }//GEN-LAST:event_izquierdaBtnActionPerformed

    private void derechaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_derechaBtnActionPerformed
        // TODO add your handling code here:
        nFoto++;
        banderaFoto = "d";
        izquierdaBtn.setEnabled(true);

        cargarImagen(auxiliarFoto,nFoto);
    }//GEN-LAST:event_derechaBtnActionPerformed

    private void ExcelInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExcelInventarioActionPerformed
        // TODO add your handling code here:
        try{
            if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                Excel excel = new Excel();
                excel.GuardarComo(tablaInventario);
            }else{
                JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultas en el inventario.");
            }
        }catch(NullPointerException e){
        
        }
    }//GEN-LAST:event_ExcelInventarioActionPerformed

    private void ExcelConsumiblesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExcelConsumiblesActionPerformed
        // TODO add your handling code here:
        try{
            if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                Excel excel = new Excel();
                excel.GuardarComo(tablaInventario);
            }else{
                JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultas en el inventario.");
            }
        }catch(NullPointerException e){
        
        }
    }//GEN-LAST:event_ExcelConsumiblesActionPerformed

    private void AceptarEstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarEstatusActionPerformed
        // TODO add your handling code here:
        Object[] botones = {"Aceptar","Cancelar"};
        int opcion = JOptionPane.showOptionDialog(this,"¿Desea realizar el cambio de estatus a "+pendientePara+"?", "Confirmación",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE  , null, botones, botones[0]);
        
        if(opcion == 0){

            if(manager_permisos.accesoModulo("actualizar","Inventario",Username)){
                DefaultTableModel inventario = (DefaultTableModel)tablaInventario.getModel();
                Boolean[] cambio = new Boolean[inventario.getRowCount()];
                String[] ids = new String[inventario.getRowCount()];

                //Llenamos los arreglos con la información
                for(int i = 0; i<ids.length; i++){
                    cambio[i] = Boolean.parseBoolean(tablaInventario.getValueAt(i, 0).toString());
                    ids[i] = tablaInventario.getValueAt(i, 1).toString();
                }

                //Intentamos actualizar el estatus de dichos productos
                if(manager_inventario.actualizarEstatus(ids,cambio,pendientePara)){
                    JOptionPane.showMessageDialog(null, "Se actualizaron con exito los registros a "+pendientePara);
                    if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                        //Mostramos la tabla de acuerdo a los criterios de busqueda que estaban antes
                        int folio = comboFolio.getSelectedIndex();
                        String estatus = comboEstatus.getSelectedItem().toString();
                        String nomeclatura = "";
                        int filtro = comboFiltro.getSelectedIndex();
                        String inventarios = comboInventario.getSelectedItem().toString();
                        String busqueda = txtBusqueda.getText();
                        //Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
                        if(folio > 0){nomeclatura = nomeclaturas[folio-1];}
                        if(inventarios.equals("Inventario"))
                            tablaInventario.setModel(manager_inventario.getBusquedaInventario(filtro, busqueda, nomeclatura,estatus));
                        else
                            tablaInventario.setModel(manager_inventario_granel.getBusquedaInventario(filtro, busqueda, nomeclatura,estatus));
                    }else{
                        JOptionPane.showMessageDialog(null,"Tu permiso para consultar el inventario ha sido revocado.");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "No se pudo realizar el cambio a "+pendientePara);
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para actualizar el estatus del inventario.");                
            }
            
            //Bandera para activar el popuMenu de aceptar o cancelar
            activarConfirmacion = 2;

            //Habilitamos las demás opciones para que siga con las respectivas funciones con las que cuenta la pestaña
            btnAddInventario.setEnabled(true);
            comboInventario.setEnabled(true);
            comboEstatus.setEnabled(true);
            comboFolio.setEnabled(true);
            comboFiltro.setEnabled(true);
            txtBusqueda.setEnabled(true);
            
        }//if(opcion == 0)
    }//GEN-LAST:event_AceptarEstatusActionPerformed

    private void CancelarEstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarEstatusActionPerformed
        // TODO add your handling code here:
        Object[] botones = {"Aceptar","Cancelar"};
        int opcion = JOptionPane.showOptionDialog(this,"¿Desea cancelar la acción del cambio de estatus a "+pendientePara+"?", "Confirmación",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE  , null, botones, botones[0]);
        
        if(opcion == 0){

            if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                int folio = comboFolio.getSelectedIndex();
                String estatus = comboEstatus.getSelectedItem().toString();
                String nomeclatura = "";
                int filtro = comboFiltro.getSelectedIndex();
                String inventario = comboInventario.getSelectedItem().toString();
                String busqueda = txtBusqueda.getText();
                //Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
                if(folio > 0){nomeclatura = nomeclaturas[folio-1];}
                if(inventario.equals("Inventario"))
                        tablaInventario.setModel(manager_inventario.getBusquedaInventario(filtro, busqueda, nomeclatura,estatus));
                else
                        tablaInventario.setModel(manager_inventario_granel.getBusquedaInventario(filtro, busqueda, nomeclatura,estatus));
            }else{
                JOptionPane.showMessageDialog(null,"Tu permiso para consultar el inventario ha sido revocado.");
            }

            //Bandera para activar el popuMenu de aceptar o cancelar
            activarConfirmacion = 2;

            //Habilitamos las demás opciones para que siga con las respectivas funciones con las que cuenta la pestaña
            btnAddInventario.setEnabled(true);
            comboInventario.setEnabled(true);
            comboEstatus.setEnabled(true);
            comboFolio.setEnabled(true);
            comboFiltro.setEnabled(true);
            txtBusqueda.setEnabled(true);
            
        }
    }//GEN-LAST:event_CancelarEstatusActionPerformed

    private void comboAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAreaActionPerformed
        // TODO add your handling code here:
        comboEmpleado.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        comboEmpleado.addItem("Seleccione al empleado...");
        manager_users.getNombresEmpleados(comboEmpleado,comboArea.getSelectedIndex()+1);
    }//GEN-LAST:event_comboAreaActionPerformed

    private void tablaResguardoPersonalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaResguardoPersonalMouseReleased
        // TODO add your handling code here:
        //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
            if(SwingUtilities.isRightMouseButton(evt)){
                int r = tablaResguardoPersonal.rowAtPoint(evt.getPoint());
                if (r >= 0 && r < tablaResguardoPersonal.getRowCount())
                tablaResguardoPersonal.setRowSelectionInterval(r, r);
                MenuResguardoPersonal.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
            }//clic derecho
    }//GEN-LAST:event_tablaResguardoPersonalMouseReleased

    private void QuitarResguardoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuitarResguardoActionPerformed
        // TODO add your handling code here:
        int fila = tablaResguardoPersonal.getSelectedRow();
        if(manager_complemento.quitarRegistroResguardo(Username, tablaResguardoPersonal.getValueAt(fila, 0).toString(), tablaResguardoPersonal.getValueAt(fila, 1).toString(), tablaResguardoPersonal.getValueAt(fila, 2).toString(), tablaResguardoPersonal.getValueAt(fila, 3).toString())){
            JOptionPane.showMessageDialog(null, "Salida del producto \""+tablaResguardoPersonal.getValueAt(fila, 1).toString()+"\"  del resguardo personal con exito.");
            tablaResguardoPersonal.setModel(manager_complemento.getResguardoPersonal(Username));
        }else{
            JOptionPane.showMessageDialog(null, "Verificar con el distribuidor.");
        }
    }//GEN-LAST:event_QuitarResguardoActionPerformed

    private void comboArea1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboArea1ActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("consulta","Resguardo",Username)){
            comboEmpleadoR.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
            comboEmpleadoR.addItem("Seleccione al empleado...");
            manejador_inventario.getEmpleadosAsignacion(comboEmpleadoR,comboArea1.getSelectedIndex()+1);
        }else{
            refrescarResguardoRecoleccion = 2;
            JOptionPane.showMessageDialog(null, "Su permiso de consulta de resguardo ha sido revocado.");

            //Bloqueamos el uso de la asignacion
            comboEmpleadoR.setEnabled(false);
            comboArea1.setEnabled(false);
            tablaRecoleccion.setEnabled(false);

            //Devolvemos los productos al estado anterior
            limpiarTablaRecoleccion();
            comboEmpleadoR.setSelectedIndex(0);
        }
    }//GEN-LAST:event_comboArea1ActionPerformed

    private void comboAñoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAñoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboAñoActionPerformed

    private void comboEmpUsuEstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEmpUsuEstatusActionPerformed
        // TODO add your handling code here:
        if(comboEmpUsuEstatus.getSelectedItem().equals("Todos")){
            activarE.setVisible(false);
            bajaE.setVisible(false);
            dar_baja.setVisible(false);
            activar.setVisible(false);
        }else if(comboEmpUsuEstatus.getSelectedItem().equals("Activo")){
            activarE.setVisible(false);
            bajaE.setVisible(true);
            dar_baja.setVisible(true);
            activar.setVisible(false);
        }else{
            activarE.setVisible(true);
            bajaE.setVisible(false);
            dar_baja.setVisible(false);
            activar.setVisible(true);
        }
        buscarEmpleados();
    }//GEN-LAST:event_comboEmpUsuEstatusActionPerformed
       
    public void cargarImagen(String busqueda,int numero){
        
        //FUNCIONA
        BufferedImage img = null;
        String ip = "";
        try {
            //Creamos un archivo FileReader que obtiene lo que tenga el archivo
            FileReader lector = new FileReader("cnfg.ntw");

            //El contenido de lector se guarda en un BufferedReader
            BufferedReader contenido = new BufferedReader(lector);

            ip = contenido.readLine();
        } catch (Exception e) {

        }
        try {
            // agregar la IP dinamicamente OJO
            
            String cadena = "\\\\"+ip+"\\imagenes\\vehiculos\\" + busqueda+ "\\" + busqueda + numero+".png";
            System.out.println("" + cadena);
            img = ImageIO.read(new File(cadena));
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            if (banderaFoto.equals("i")) {
                izquierdaBtn.setEnabled(false);
                nFoto++;
            } else {
                derechaBtn.setEnabled(false);
                nFoto--;
            }
        }
        Image dimg = img.getScaledInstance(imagenVehiculo.getWidth(), imagenVehiculo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(dimg);
        imagenVehiculo.setIcon(image);
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
        bandera = false;
        jButton1.setEnabled(true);
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
    private javax.swing.JMenuItem AceptarEstatus;
    private javax.swing.JMenuItem Actualizar;
    private javax.swing.JMenuItem ActualizarAsignacionP;
    private javax.swing.JMenuItem ActualizarConsumible;
    private javax.swing.JMenuItem ActualizarEmployee;
    private javax.swing.JMenuItem ActualizarInfoPP;
    private javax.swing.JMenuItem ActualizarInfoSM;
    private javax.swing.JMenuItem ActualizarInfoSP;
    private javax.swing.JMenuItem ActualizarInfoV;
    private javax.swing.JMenuItem ActualizarProd;
    private javax.swing.JMenuItem ActualizarV;
    private javax.swing.JMenuItem AddDocuments;
    private javax.swing.JMenuItem AddObservacion;
    private javax.swing.JMenuItem AddStock;
    private javax.swing.JMenuItem AgregarStock;
    private javax.swing.JMenuItem Asignar_usuario;
    private javax.swing.JMenuItem Atender;
    private javax.swing.JMenuItem CancelarA;
    private javax.swing.JMenuItem CancelarEstatus;
    private javax.swing.JMenuItem CancelarSS;
    private javax.swing.JMenuItem CancelarSolicitud;
    private javax.swing.JMenuItem DevolverDis;
    private javax.swing.JMenu Documentos;
    private javax.swing.JMenuItem EstatusDefinitivo;
    private javax.swing.JMenuItem ExcelConsumibles;
    private javax.swing.JMenuItem ExcelInventario;
    private javax.swing.JMenuItem ExportarEmpleados;
    private javax.swing.JMenuItem ExportarUsuarios;
    private javax.swing.ButtonGroup Grupo1;
    private javax.swing.ButtonGroup GrupoTipoInventario;
    private javax.swing.JPopupMenu MenuAsginados;
    private javax.swing.JPopupMenu MenuAsignacionP;
    private javax.swing.JPopupMenu MenuConfirmacion;
    private javax.swing.JPopupMenu MenuEmpleados;
    private javax.swing.JPopupMenu MenuInventario;
    private javax.swing.JPopupMenu MenuInventarioG;
    private javax.swing.JPopupMenu MenuPermisosP;
    private javax.swing.JPopupMenu MenuResguardoPersonal;
    private javax.swing.JPopupMenu MenuSolicitarSalida;
    private javax.swing.JPopupMenu MenuSolicitudes;
    private javax.swing.JPopupMenu MenuSolicitudesP;
    private javax.swing.JPopupMenu MenuStockMin;
    private javax.swing.JPopupMenu MenuUsuarios;
    private javax.swing.JPopupMenu MenuVehiculos;
    private javax.swing.JMenuItem NoDisponible;
    private javax.swing.JMenuItem ParaBaja;
    private javax.swing.JMenuItem ParaComodato;
    private javax.swing.JMenuItem ParaDonacion;
    private javax.swing.JMenu Pendiente;
    private javax.swing.JMenuItem Permisos;
    private javax.swing.JMenuItem QuitarResguardo;
    private javax.swing.JScrollPane ScrollEmpleado;
    private javax.swing.JMenuItem SelectProducts;
    private javax.swing.JMenuItem Servicio;
    private javax.swing.JMenuItem SolicitarBaja;
    private javax.swing.JMenu SolictarMas;
    private javax.swing.JMenuItem activar;
    private javax.swing.JMenuItem activarE;
    private javax.swing.JMenuItem bajaE;
    private javax.swing.ButtonGroup bg_manejo_inventario;
    private javax.swing.ButtonGroup bt_tipo_inventario_asignable;
    private javax.swing.JButton btnAddEmpleado;
    private javax.swing.JButton btnAddInventario;
    private javax.swing.JButton btnAñadirResguardo;
    private javax.swing.JButton btnAñadirVehiculo;
    private javax.swing.JButton btnCancelarSalida;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEditar1;
    private javax.swing.JButton btnEntregarRecoleccion;
    private javax.swing.JButton btnSolicitarSalida;
    public javax.swing.JButton btn_cancelar2;
    public javax.swing.JButton btn_generar_vale3;
    private javax.swing.JTextArea campoObservaciones;
    private javax.swing.JSpinner campoip1;
    private javax.swing.JSpinner campoip2;
    private javax.swing.JSpinner campoip3;
    private javax.swing.JSpinner campoip4;
    private javax.swing.JComboBox<String> comboArea;
    private javax.swing.JComboBox<String> comboArea1;
    private javax.swing.JComboBox<String> comboAño;
    public static javax.swing.JComboBox<String> comboEmpUsu;
    public static javax.swing.JComboBox<String> comboEmpUsuEstatus;
    private javax.swing.JComboBox<String> comboEmpleado;
    private javax.swing.JComboBox<String> comboEmpleadoR;
    public static javax.swing.JComboBox<String> comboEstatus;
    public static javax.swing.JComboBox<String> comboFiltro;
    public static javax.swing.JComboBox<String> comboFiltroConsumibles;
    private javax.swing.JComboBox<String> comboFiltroUsuario;
    private javax.swing.JComboBox<String> comboFiltroUsuario1;
    private javax.swing.JComboBox<String> comboFiltroVehiculos;
    public static javax.swing.JComboBox<String> comboFolio;
    public static javax.swing.JComboBox<String> comboFolioAsignacion;
    private javax.swing.JComboBox<String> comboInventario;
    private javax.swing.JPanel configuracion;
    private javax.swing.JMenuItem dar_baja;
    private javax.swing.JButton derechaBtn;
    private javax.swing.JPanel empleado;
    private javax.swing.JLabel etiquetaAño;
    private javax.swing.JLabel etiquetaEstado;
    private javax.swing.JLabel etiquetaKilometraje;
    private javax.swing.JLabel etiquetaLinea;
    private javax.swing.JLabel etiquetaMarca;
    private javax.swing.JLabel fondo;
    private javax.swing.JLabel fondo1;
    private javax.swing.JPanel fondoVehiculo;
    private javax.swing.JLabel imagenVehiculo;
    private javax.swing.JMenuItem itemAnterior;
    private javax.swing.JMenuItem itemSalir;
    private javax.swing.JMenuItem itemSiguiente;
    private javax.swing.JButton izquierdaBtn;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
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
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane31;
    private javax.swing.JScrollPane jScrollPane32;
    private javax.swing.JScrollPane jScrollPane34;
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
    public static javax.swing.JLabel lblArea;
    private javax.swing.JLabel lblAreaAsignacion;
    private javax.swing.JLabel lblAreaAsignacion1;
    private javax.swing.JLabel lblAreaAsignacion2;
    public static javax.swing.JLabel lblCargo;
    public static javax.swing.JLabel lblCodigo;
    public static javax.swing.JLabel lblCurp;
    public static javax.swing.JLabel lblDomicilio;
    public static javax.swing.JLabel lblFecha;
    public static javax.swing.JLabel lblLocalidad;
    public static javax.swing.JLabel lblMunicipio;
    public static javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblProductosTotales;
    public static javax.swing.JLabel lblRfc;
    public static javax.swing.JLabel lblTelefono;
    private javax.swing.JMenu menuOpciones;
    private javax.swing.JMenuItem menuPermisos;
    private javax.swing.JMenuItem mi_viaticos;
    private javax.swing.JPanel panelEmpleado;
    private javax.swing.JPanel pestañaInventario;
    private javax.swing.JPanel pn_acciones1;
    private javax.swing.JPanel pn_asignacion_inventario1;
    private javax.swing.JPanel pn_contenedor_ventanas1;
    private javax.swing.JPanel pn_recoleccion_inventario1;
    private javax.swing.JPanel pn_tablaUsuarios1;
    private javax.swing.JRadioButton rb_asignacion1;
    public javax.swing.JRadioButton rb_inventario_granel1;
    public javax.swing.JRadioButton rb_inventario_normal1;
    public javax.swing.JRadioButton rb_recoleccion1;
    private javax.swing.JPanel resguardo;
    private javax.swing.JPanel solicitar_granel;
    private javax.swing.JPanel solicitudes;
    private javax.swing.JScrollPane sp_asignacion_inventario1;
    private javax.swing.JScrollPane sp_recoleccion_inventario1;
    public static javax.swing.JTabbedPane tabbedPrincipal;
    public static javax.swing.JTable tablaAsignacionPersonal;
    private javax.swing.JTable tablaBD;
    public static javax.swing.JTable tablaCantidadGranel;
    private javax.swing.JTable tablaIP;
    public static javax.swing.JTable tablaInventario;
    public javax.swing.JTable tablaMAsignados;
    public static javax.swing.JTable tablaMInventarioA;
    private javax.swing.JTable tablaPermisosPersonales;
    public javax.swing.JTable tablaRecoleccion;
    public static javax.swing.JTable tablaResguardoPersonal;
    public static javax.swing.JTable tablaSolicitarGranel;
    public static javax.swing.JTable tablaSolicitudes;
    public static javax.swing.JTable tablaSolicitudesPersonal;
    public static javax.swing.JTable tablaStockMin;
    public static javax.swing.JTable tablaUsuarios;
    public static javax.swing.JTable tablaVehiculos;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtBusquedaConsumibles;
    private javax.swing.JTextField txtBusquedaUsuario;
    private javax.swing.JTextField txtBusquedaVehiculos;
    private javax.swing.JPanel usuarios;
    private javax.swing.JPanel vehiculos;
    // End of variables declaration//GEN-END:variables
}
