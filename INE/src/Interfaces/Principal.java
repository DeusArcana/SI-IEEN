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

//Importamos los TDA del paquete Clases
import Clases.ManagerUsers;
import Clases.ManagerInventario;
import Clases.ManagerMySQL;
import Clases.ManagerPermisos;
import Clases.ManagerSolicitud;
import Clases.ManagerComplemento;
import Clases.ManagerVehiculos;
import Clases.ManagerVales;
import Clases.MetodosComponentes;
import static Clases.MetodosComponentes.printContenidoLista;
import Clases.Tablas;


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
 * @author oscar
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
    ManagerVales manager_vales;
    
    //VARIABLES PARA CONTROL INVENTARIO PABLO
    //PABLO VARIABLES GLOBALES
    MetodosComponentes metodo;
    public DefaultTableModel modelo;
    public DefaultTableModel modelo0;
    public boolean asignacion_botones_activadas[],yaExisteProductoGranel;
    public int serialVale,stockActualSeleccionado,unidadesAsignadas,unidadesRestantes,unidadesAcumuladas,last_granel_index,last_producto_granel_tabla_id;
    public String objetos_completos_asignables_asignacion;
    public String objetos_completos_asignables_asignacion_granel;
    public String objetos_asignables_asignacion;
    public String objetos_asignados_asignacion;
    public String objetos_asignables_asignacion_granel;
    public String objetos_asignados_asignacion_granel;
    public String id_inventario,last_id_inventarioGranel;
    public String encargado,encargado1;
    public ArrayList<Object[]> l_eliminables_objetos_asignables_asignacion;
    public ArrayList<Object[]> l_obj_asig;
    public ArrayList<Object[]> l_obj_entr;
    public ArrayList<Object[]> l_obj_falt;
    public ArrayList<Object[]> l_objetos_asignables_asignacion;
    public ArrayList<Object[]> l_eliminables_objetos_asignables_asignacion_granel;
    public ArrayList<Object[]> l_objetos_asignables_asignacion_granel; 
    public ArrayList l_cantidades_pedidas_granel;
    //DATOS DE VALES
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
    
    //VARIABLES GLOBALES
    public static String usuario = "",prodInventario = "";
    public static String Username = "";
    public static String UserUpdate = "";
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
        manager_vales = new ManagerVales();
        
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

        //Obtenemos el modelo de la tabla IP
        modeloTablaIP = (DefaultTableModel) tablaIP.getModel();

        campoObservaciones.setLineWrap(true);
        //Quitar editable a spinner
        ((JSpinner.DefaultEditor) campoip1.getEditor()).getTextField().setEditable(false);
        //Quitar editable a spinner
        ((JSpinner.DefaultEditor) campoip2.getEditor()).getTextField().setEditable(false);
        //Quitar editable a spinner
        ((JSpinner.DefaultEditor) campoip3.getEditor()).getTextField().setEditable(false);
        //Quitar editable a spinner
        ((JSpinner.DefaultEditor) campoip4.getEditor()).getTextField().setEditable(false);

        etiquetaMarca.setText("");
        etiquetaLinea.setText("");
        etiquetaKilometraje.setText("");
        etiquetaAño.setText("");
        campoObservaciones.setText("");
        campoObservaciones.setEditable(false);
        zoom.setVisible(false);
        
        /*NUEVO*/
        stockActualSeleccionado=unidadesAsignadas=unidadesRestantes=unidadesAcumuladas=last_granel_index=last_producto_granel_tabla_id=0;
        serialVale=0;
        //serialVale=Integer.parseInt(Archivo.leerContenidoArchivo("serial_vale.vl"));
        System.out.println("Contenido "+((Archivo.leerContenidoArchivo("serial_vale.vl")).replace("$","")));
        serialVale=Integer.parseInt((Archivo.leerContenidoArchivo("serial_vale.vl")).replace("$",""));
     
        last_id_inventarioGranel="";
        CardLayout c_recoleccion = (CardLayout)pn_contenedor_ventanas.getLayout();
        c_recoleccion.show(pn_contenedor_ventanas,"c_s_asignacion");
        
        asignacion_botones_activadas=new boolean[9];asignacion_botones_activadas[0]=asignacion_botones_activadas[1]=asignacion_botones_activadas[2]=asignacion_botones_activadas[3]=false;yaExisteProductoGranel=false;
        asignacion_botones_activadas[4]=true;asignacion_botones_activadas[5]=asignacion_botones_activadas[6]=asignacion_botones_activadas[7]=asignacion_botones_activadas[8]=false;
        
        l_eliminables_objetos_asignables_asignacion=new ArrayList();
        l_objetos_asignables_asignacion=new ArrayList();
        l_eliminables_objetos_asignables_asignacion_granel=new ArrayList();
        l_objetos_asignables_asignacion_granel=new ArrayList();
        l_cantidades_pedidas_granel=new ArrayList();
        l_obj_asig=new ArrayList();
        l_obj_entr=new ArrayList();;
        l_obj_falt=new ArrayList();;
        metodo=new MetodosComponentes(); 
        metodo.pointerA=this;
        metodo.cargarEmpleados(tb_empleado);//cargarEmpleados(tb_empleado);
        metodo.cargarEmpleados(tb_empleado1);
        metodo.cargarInventarioGranel(tb_objetos_asignables);metodo.cargarInventario(tb_producto_a_reemplazar);        
        metodo.copyInventarioGranel(tb_objetos_asignables);
        metodo.cargarInventarioGlobal(tb_inventario_normal_asignado);metodo.cargarInventarioGlobal(tb_objetos_asignados1);
        metodo.cargarInventarioGlobalGranel(tb_inventario_granel_asignado);      
        /*NUEVO*/    
    }
      
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Grupo1 = new javax.swing.ButtonGroup();
        MenuUsuarios = new javax.swing.JPopupMenu();
        Eliminar = new javax.swing.JMenuItem();
        Actualizar = new javax.swing.JMenuItem();
        Promover = new javax.swing.JMenuItem();
        Permisos = new javax.swing.JMenuItem();
        MenuInventario = new javax.swing.JPopupMenu();
        Baja = new javax.swing.JMenuItem();
        Comodato = new javax.swing.JMenuItem();
        AgregarStock = new javax.swing.JMenuItem();
        MenuSolicitudes = new javax.swing.JPopupMenu();
        Atender = new javax.swing.JMenuItem();
        ActualizarInfo = new javax.swing.JMenuItem();
        MenuVehiculos = new javax.swing.JPopupMenu();
        AsignarV = new javax.swing.JMenuItem();
        ActualizarV = new javax.swing.JMenuItem();
        SolictarMas = new javax.swing.JMenu();
        SolicitarBaja = new javax.swing.JMenuItem();
        Servicio = new javax.swing.JMenuItem();
        bg_manejo_inventario = new javax.swing.ButtonGroup();
        bt_tipo_inventario_asignable = new javax.swing.ButtonGroup();
        MenuPersonal = new javax.swing.JPopupMenu();
        CambiarContra = new javax.swing.JMenuItem();
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
        jLabel9 = new javax.swing.JLabel();
        empleado = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
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
        jPanel11 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaResguardo = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        btnAñadirResguardo = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaSolicitudesPersonal = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jPanel13 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablaPermisosPersonales = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){return false; }  };
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
        pn_manejo_inventario = new javax.swing.JPanel();
        pn_general = new javax.swing.JPanel();
        pn_acciones = new javax.swing.JPanel();
        rb_asignacion = new javax.swing.JRadioButton();
        rb_recoleccion = new javax.swing.JRadioButton();
        rb_reemplazo = new javax.swing.JRadioButton();
        rb_asignaciones = new javax.swing.JRadioButton();
        pn_contenedor_ventanas = new javax.swing.JPanel();
        sp_asignacion_inventario = new javax.swing.JScrollPane();
        pn_asignacion_inventario = new javax.swing.JPanel();
        lb_empleado = new javax.swing.JLabel();
        tf_empleado = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_empleado = new javax.swing.JTable();
        btn_seleccionar_empleado = new javax.swing.JButton();
        lb_objetos_asignables = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        tb_objetos_asignados = new javax.swing.JTable();
        lb_objetos_asignables1 = new javax.swing.JLabel();
        jScrollPane23 = new javax.swing.JScrollPane();
        tb_objetos_asignables = new javax.swing.JTable();
        btn_asignar = new javax.swing.JButton();
        btn_generar_vale = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        rb_inventario_normal = new javax.swing.JRadioButton();
        rb_inventario_granel = new javax.swing.JRadioButton();
        tf_cantidad = new javax.swing.JTextField();
        sp_recoleccion_inventario = new javax.swing.JScrollPane();
        pn_recoleccion_inventario = new javax.swing.JPanel();
        lb_empleado1 = new javax.swing.JLabel();
        tf_empleado1 = new javax.swing.JTextField();
        jScrollPane24 = new javax.swing.JScrollPane();
        tb_empleado1 = new javax.swing.JTable();
        btn_seleccionar_empleado1 = new javax.swing.JButton();
        lb_objetos_asignados = new javax.swing.JLabel();
        jScrollPane25 = new javax.swing.JScrollPane();
        tb_objetos_asignados1 = new javax.swing.JTable();
        lb_objetos_entregados = new javax.swing.JLabel();
        jScrollPane26 = new javax.swing.JScrollPane();
        tb_objetos_entregados = new javax.swing.JTable();
        lb_objetos_faltantes = new javax.swing.JLabel();
        jScrollPane27 = new javax.swing.JScrollPane();
        tb_objetos_faltantes = new javax.swing.JTable();
        btn_generar_vale1 = new javax.swing.JButton();
        btn_recolectar_producto = new javax.swing.JButton();
        btn_entregar_objetos = new javax.swing.JButton();
        btn_cancelar1 = new javax.swing.JButton();
        sp_reemplazo_inventario = new javax.swing.JScrollPane();
        pn_reemplazo_inventario = new javax.swing.JPanel();
        jScrollPane28 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tb_producto_a_reemplazar = new javax.swing.JTable();
        btn_generar_vale2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        tf_pruducto_reemplazable = new javax.swing.JTextField();
        sp_asignaciones_inventario = new javax.swing.JScrollPane();
        pn_asignaciones_inventario = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane29 = new javax.swing.JScrollPane();
        tb_inventario_normal_asignado = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane30 = new javax.swing.JScrollPane();
        tb_inventario_granel_asignado = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jTextField2 = new javax.swing.JTextField();
        lb_background = new javax.swing.JLabel();
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

        Baja.setText("Solicitar baja");
        Baja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BajaActionPerformed(evt);
            }
        });
        MenuInventario.add(Baja);

        Comodato.setText("Solicitar comodato");
        MenuInventario.add(Comodato);

        AgregarStock.setText("Actualizar stock");
        MenuInventario.add(AgregarStock);

        Atender.setText("Atender...");
        Atender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtenderActionPerformed(evt);
            }
        });
        MenuSolicitudes.add(Atender);

        ActualizarInfo.setText("Actualizar información");
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

        CambiarContra.setText("Cambiar contraseña");
        MenuPersonal.add(CambiarContra);

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
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1400, Short.MAX_VALUE)
        );
        pestañaInventarioLayout.setVerticalGroup(
            pestañaInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
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
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1400, Short.MAX_VALUE)
        );
        usuariosLayout.setVerticalGroup(
            usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
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
        jScrollPane10.setBounds(10, 100, 900, 580);

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
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1400, Short.MAX_VALUE)
        );
        vehiculosLayout.setVerticalGroup(
            vehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
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

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        jPanel7.add(jLabel9);
        jLabel9.setBounds(0, 0, 1367, 769);

        javax.swing.GroupLayout solicitudesLayout = new javax.swing.GroupLayout(solicitudes);
        solicitudes.setLayout(solicitudesLayout);
        solicitudesLayout.setHorizontalGroup(
            solicitudesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 1400, Short.MAX_VALUE)
        );
        solicitudesLayout.setVerticalGroup(
            solicitudesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("Solicitudes", new javax.swing.ImageIcon(getClass().getResource("/Iconos/vehiculos.png")), solicitudes); // NOI18N

        empleado.setComponentPopupMenu(MenuPersonal);

        jPanel9.setLayout(null);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Información personal", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

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

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblLocalidad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMunicipio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblRfc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCurp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCargo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblArea, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDomicilio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTelefono, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFecha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
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
                .addGap(18, 18, 18)
                .addComponent(btnEditar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel10);
        jPanel10.setBounds(10, 10, 460, 350);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Resguardo personal", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

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

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAñadirResguardo, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(153, 153, 153))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAñadirResguardo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel9.add(jPanel11);
        jPanel11.setBounds(10, 370, 460, 250);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Solicitudes realizadas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

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

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.add(jPanel12);
        jPanel12.setBounds(490, 10, 850, 350);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Permisos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

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
        jScrollPane9.setViewportView(tablaPermisosPersonales);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel13);
        jPanel13.setBounds(490, 370, 850, 250);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        jPanel9.add(jLabel10);
        jLabel10.setBounds(0, 0, 1367, 769);

        javax.swing.GroupLayout empleadoLayout = new javax.swing.GroupLayout(empleado);
        empleado.setLayout(empleadoLayout);
        empleadoLayout.setHorizontalGroup(
            empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 1400, Short.MAX_VALUE)
        );
        empleadoLayout.setVerticalGroup(
            empleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
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
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1400, Short.MAX_VALUE)
        );
        configuracionLayout.setVerticalGroup(
            configuracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("  Configuración", new javax.swing.ImageIcon(getClass().getResource("/Iconos/configuracion.png")), configuracion); // NOI18N

        pn_general.setLayout(null);

        bg_manejo_inventario.add(rb_asignacion);
        rb_asignacion.setSelected(true);
        rb_asignacion.setText("Asignación");
        rb_asignacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_asignacionActionPerformed(evt);
            }
        });

        bg_manejo_inventario.add(rb_recoleccion);
        rb_recoleccion.setText("Recolección");
        rb_recoleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_recoleccionActionPerformed(evt);
            }
        });

        bg_manejo_inventario.add(rb_reemplazo);
        rb_reemplazo.setText("Reemplazo");
        rb_reemplazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_reemplazoActionPerformed(evt);
            }
        });

        bg_manejo_inventario.add(rb_asignaciones);
        rb_asignaciones.setText("Asignaciones");
        rb_asignaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_asignacionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_accionesLayout = new javax.swing.GroupLayout(pn_acciones);
        pn_acciones.setLayout(pn_accionesLayout);
        pn_accionesLayout.setHorizontalGroup(
            pn_accionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_accionesLayout.createSequentialGroup()
                .addGap(226, 226, 226)
                .addComponent(rb_asignacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rb_recoleccion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rb_reemplazo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rb_asignaciones)
                .addContainerGap(216, Short.MAX_VALUE))
        );
        pn_accionesLayout.setVerticalGroup(
            pn_accionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_accionesLayout.createSequentialGroup()
                .addGroup(pn_accionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_asignacion)
                    .addComponent(rb_recoleccion)
                    .addComponent(rb_reemplazo)
                    .addComponent(rb_asignaciones))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pn_general.add(pn_acciones);
        pn_acciones.setBounds(110, 20, 770, 20);

        pn_contenedor_ventanas.setLayout(new java.awt.CardLayout());

        lb_empleado.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_empleado.setText("Empleado");

        tf_empleado.setEditable(false);
        tf_empleado.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        tf_empleado.setPreferredSize(new java.awt.Dimension(59, 30));

        tb_empleado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_empleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_empleadoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb_empleado);

        btn_seleccionar_empleado.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btn_seleccionar_empleado.setText("Seleccionar Empleado");
        btn_seleccionar_empleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_seleccionar_empleadoActionPerformed(evt);
            }
        });

        lb_objetos_asignables.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_objetos_asignables.setText("Objetos Asignables");

        tb_objetos_asignados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Nombre", "Descripción", "Almacén", "Observaciones", "no_serie", "modelo", "color"
            }
        ));
        jScrollPane22.setViewportView(tb_objetos_asignados);

        lb_objetos_asignables1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_objetos_asignables1.setText("Objetos Asignados");

        tb_objetos_asignables.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_objetos_asignables.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_objetos_asignablesMouseClicked(evt);
            }
        });
        jScrollPane23.setViewportView(tb_objetos_asignables);

        btn_asignar.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btn_asignar.setText("Asignar");
        btn_asignar.setEnabled(false);
        btn_asignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_asignarActionPerformed(evt);
            }
        });

        btn_generar_vale.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btn_generar_vale.setText("Generar Vale");
        btn_generar_vale.setEnabled(false);
        btn_generar_vale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generar_valeActionPerformed(evt);
            }
        });

        btn_cancelar.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btn_cancelar.setText("Cancelar");
        btn_cancelar.setEnabled(false);
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        bt_tipo_inventario_asignable.add(rb_inventario_normal);
        rb_inventario_normal.setText("Normal");
        rb_inventario_normal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_inventario_normalActionPerformed(evt);
            }
        });

        bt_tipo_inventario_asignable.add(rb_inventario_granel);
        rb_inventario_granel.setText("Granel");
        rb_inventario_granel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rb_inventario_granelMouseClicked(evt);
            }
        });
        rb_inventario_granel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_inventario_granelActionPerformed(evt);
            }
        });

        tf_cantidad.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        tf_cantidad.setPreferredSize(new java.awt.Dimension(6, 30));

        javax.swing.GroupLayout pn_asignacion_inventarioLayout = new javax.swing.GroupLayout(pn_asignacion_inventario);
        pn_asignacion_inventario.setLayout(pn_asignacion_inventarioLayout);
        pn_asignacion_inventarioLayout.setHorizontalGroup(
            pn_asignacion_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_asignacion_inventarioLayout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addGroup(pn_asignacion_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pn_asignacion_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lb_objetos_asignables1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pn_asignacion_inventarioLayout.createSequentialGroup()
                            .addComponent(lb_empleado)
                            .addGap(39, 39, 39)
                            .addComponent(tf_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                        .addComponent(jScrollPane23, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane22, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pn_asignacion_inventarioLayout.createSequentialGroup()
                            .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btn_generar_vale, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pn_asignacion_inventarioLayout.createSequentialGroup()
                            .addComponent(tf_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btn_asignar, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pn_asignacion_inventarioLayout.createSequentialGroup()
                        .addComponent(lb_objetos_asignables)
                        .addGap(8, 8, 8)
                        .addGroup(pn_asignacion_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_asignacion_inventarioLayout.createSequentialGroup()
                                .addComponent(rb_inventario_normal)
                                .addGap(10, 10, 10)
                                .addComponent(rb_inventario_granel))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_asignacion_inventarioLayout.createSequentialGroup()
                                .addGap(0, 237, Short.MAX_VALUE)
                                .addComponent(btn_seleccionar_empleado)))))
                .addContainerGap(126, Short.MAX_VALUE))
        );
        pn_asignacion_inventarioLayout.setVerticalGroup(
            pn_asignacion_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_asignacion_inventarioLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(pn_asignacion_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_empleado)
                    .addComponent(tf_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_seleccionar_empleado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_asignacion_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_objetos_asignables)
                    .addComponent(rb_inventario_normal)
                    .addComponent(rb_inventario_granel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_asignacion_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_asignar)
                    .addComponent(tf_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(lb_objetos_asignables1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(pn_asignacion_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_generar_vale, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(254, Short.MAX_VALUE))
        );

        sp_asignacion_inventario.setViewportView(pn_asignacion_inventario);

        pn_contenedor_ventanas.add(sp_asignacion_inventario, "c_s_asignacion");

        lb_empleado1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_empleado1.setText("Empleado");

        tf_empleado1.setEditable(false);
        tf_empleado1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        tf_empleado1.setPreferredSize(new java.awt.Dimension(59, 30));

        tb_empleado1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_empleado1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_empleado1MouseClicked(evt);
            }
        });
        jScrollPane24.setViewportView(tb_empleado1);

        btn_seleccionar_empleado1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btn_seleccionar_empleado1.setText("Seleccionar Empleado");
        btn_seleccionar_empleado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_seleccionar_empleado1ActionPerformed(evt);
            }
        });

        lb_objetos_asignados.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_objetos_asignados.setText("Objetos Asignados");

        tb_objetos_asignados1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane25.setViewportView(tb_objetos_asignados1);

        lb_objetos_entregados.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_objetos_entregados.setText("Objetos Entregados");

        tb_objetos_entregados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane26.setViewportView(tb_objetos_entregados);

        lb_objetos_faltantes.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_objetos_faltantes.setText("Objetos Faltantes");

        tb_objetos_faltantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane27.setViewportView(tb_objetos_faltantes);

        btn_generar_vale1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btn_generar_vale1.setText("Generar Vale");
        btn_generar_vale1.setEnabled(false);
        btn_generar_vale1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generar_vale1ActionPerformed(evt);
            }
        });

        btn_recolectar_producto.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btn_recolectar_producto.setText("Recolectar objeto");
        btn_recolectar_producto.setEnabled(false);
        btn_recolectar_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_recolectar_productoActionPerformed(evt);
            }
        });

        btn_entregar_objetos.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btn_entregar_objetos.setText("Entregar Objetos");
        btn_entregar_objetos.setEnabled(false);
        btn_entregar_objetos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_entregar_objetosActionPerformed(evt);
            }
        });

        btn_cancelar1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btn_cancelar1.setText("Cancelar");
        btn_cancelar1.setEnabled(false);
        btn_cancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_recoleccion_inventarioLayout = new javax.swing.GroupLayout(pn_recoleccion_inventario);
        pn_recoleccion_inventario.setLayout(pn_recoleccion_inventarioLayout);
        pn_recoleccion_inventarioLayout.setHorizontalGroup(
            pn_recoleccion_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_recoleccion_inventarioLayout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addGroup(pn_recoleccion_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_entregar_objetos)
                    .addComponent(btn_seleccionar_empleado1)
                    .addGroup(pn_recoleccion_inventarioLayout.createSequentialGroup()
                        .addComponent(btn_cancelar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_generar_vale1))
                    .addGroup(pn_recoleccion_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lb_objetos_faltantes, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pn_recoleccion_inventarioLayout.createSequentialGroup()
                            .addComponent(lb_empleado1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tf_empleado1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lb_objetos_asignados)
                        .addComponent(lb_objetos_entregados, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane24, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                        .addComponent(jScrollPane25)
                        .addComponent(jScrollPane26)
                        .addComponent(jScrollPane27))
                    .addComponent(btn_recolectar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(146, Short.MAX_VALUE))
        );
        pn_recoleccion_inventarioLayout.setVerticalGroup(
            pn_recoleccion_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_recoleccion_inventarioLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(pn_recoleccion_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_empleado1)
                    .addComponent(tf_empleado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_seleccionar_empleado1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_objetos_asignados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_recolectar_producto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_objetos_entregados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_entregar_objetos)
                .addGap(4, 4, 4)
                .addComponent(lb_objetos_faltantes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_recoleccion_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_generar_vale1)
                    .addComponent(btn_cancelar1))
                .addContainerGap(214, Short.MAX_VALUE))
        );

        sp_recoleccion_inventario.setViewportView(pn_recoleccion_inventario);

        pn_contenedor_ventanas.add(sp_recoleccion_inventario, "c_s_recoleccion");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane28.setViewportView(jTextArea1);

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel15.setText("Selecciones producto a reemplazar");

        jLabel24.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel24.setText("Ingrese motivos de reemplazo");

        tb_producto_a_reemplazar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_producto_a_reemplazar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_producto_a_reemplazarMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tb_producto_a_reemplazar);

        btn_generar_vale2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btn_generar_vale2.setText("Generar Vale");
        btn_generar_vale2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generar_vale2ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jButton6.setText("Seleccionar");

        javax.swing.GroupLayout pn_reemplazo_inventarioLayout = new javax.swing.GroupLayout(pn_reemplazo_inventario);
        pn_reemplazo_inventario.setLayout(pn_reemplazo_inventarioLayout);
        pn_reemplazo_inventarioLayout.setHorizontalGroup(
            pn_reemplazo_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_reemplazo_inventarioLayout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addGroup(pn_reemplazo_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_reemplazo_inventarioLayout.createSequentialGroup()
                        .addGroup(pn_reemplazo_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane12)
                            .addGroup(pn_reemplazo_inventarioLayout.createSequentialGroup()
                                .addComponent(tf_pruducto_reemplazable, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6)))
                        .addGap(185, 185, 185))
                    .addGroup(pn_reemplazo_inventarioLayout.createSequentialGroup()
                        .addGroup(pn_reemplazo_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_generar_vale2)
                            .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel15))
                        .addContainerGap(202, Short.MAX_VALUE))))
        );
        pn_reemplazo_inventarioLayout.setVerticalGroup(
            pn_reemplazo_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_reemplazo_inventarioLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel15)
                .addGap(43, 43, 43)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pn_reemplazo_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tf_pruducto_reemplazable))
                .addGap(54, 54, 54)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_generar_vale2)
                .addContainerGap(261, Short.MAX_VALUE))
        );

        sp_reemplazo_inventario.setViewportView(pn_reemplazo_inventario);

        pn_contenedor_ventanas.add(sp_reemplazo_inventario, "c_s_reemplazo");

        jLabel25.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel25.setText("Inventario Normal Asignado");

        tb_inventario_normal_asignado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id_vale", "Estado", "Usuario que autorizó", "Fecha", "Tipo de vale", "Usuario"
            }
        ));
        jScrollPane29.setViewportView(tb_inventario_normal_asignado);

        jComboBox1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Objeto", "Vale", "Fecha", "Tipo de vale", "Usuario que Autorizó" }));

        jLabel26.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel26.setText("Buscar");

        jTextField1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        tb_inventario_granel_asignado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id_vale", "Estado", "Usuario que autorizó", "Fecha", "Tipo de vale", "Usuario"
            }
        ));
        jScrollPane30.setViewportView(tb_inventario_granel_asignado);

        jLabel27.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel27.setText("Buscar");

        jComboBox2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Objeto", "Vale", "Fecha", "Tipo de vale", "Usuario que Autorizó" }));

        jTextField2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout pn_asignaciones_inventarioLayout = new javax.swing.GroupLayout(pn_asignaciones_inventario);
        pn_asignaciones_inventario.setLayout(pn_asignaciones_inventarioLayout);
        pn_asignaciones_inventarioLayout.setHorizontalGroup(
            pn_asignaciones_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_asignaciones_inventarioLayout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(pn_asignaciones_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn_asignaciones_inventarioLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_asignaciones_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel25)
                        .addComponent(jScrollPane29, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_asignaciones_inventarioLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pn_asignaciones_inventarioLayout.setVerticalGroup(
            pn_asignaciones_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_asignaciones_inventarioLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane29, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_asignaciones_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_asignaciones_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(285, Short.MAX_VALUE))
        );

        sp_asignaciones_inventario.setViewportView(pn_asignaciones_inventario);

        pn_contenedor_ventanas.add(sp_asignaciones_inventario, "c_s_asignaciones");

        pn_general.add(pn_contenedor_ventanas);
        pn_contenedor_ventanas.setBounds(110, 50, 770, 690);

        lb_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.png"))); // NOI18N
        pn_general.add(lb_background);
        lb_background.setBounds(0, 0, 1367, 769);

        javax.swing.GroupLayout pn_manejo_inventarioLayout = new javax.swing.GroupLayout(pn_manejo_inventario);
        pn_manejo_inventario.setLayout(pn_manejo_inventarioLayout);
        pn_manejo_inventarioLayout.setHorizontalGroup(
            pn_manejo_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_general, javax.swing.GroupLayout.PREFERRED_SIZE, 1367, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pn_manejo_inventarioLayout.setVerticalGroup(
            pn_manejo_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_general, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        tabbedPrincipal.addTab("Manejo de Inventario", new javax.swing.ImageIcon(getClass().getResource("/Iconos/configuracion.png")), pn_manejo_inventario); // NOI18N

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

        MenuSolicitud.setText("Permisos solicitud");
        MenuSolicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuSolicitudActionPerformed(evt);
            }
        });
        menuOpciones.add(MenuSolicitud);

        jMenuBar1.add(menuOpciones);

        menuAsignar.setText("Asignación equipos");

        Asignar.setText("Asignar...");
        Asignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AsignarActionPerformed(evt);
            }
        });
        menuAsignar.add(Asignar);

        Equipos.setText("Equipos de computo");
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
            7 -> todos los tipos de solicitud
            6 -> baja y donacion
            5 -> baja y comodato
            4 -> comodato y donacion
            3 -> baja
            2 -> comodato
            1 -> donacion
            0 -> ningun permiso
        */
        
        if(manager_permisos.verTablaSolicitudes(Username) == 0){
                //"Ocultamos" la pestaña        
                temporalSolicitud = tabbedPrincipal.getComponentAt(4); //Hacemos una copia de esa pestaña porque será eliminada
                tabbedPrincipal.removeTabAt(3);//Eliminamos la pestaña
                pestañas++;
        }else{
            tablaSolicitudes.setModel(manager_solicitud.tabla_Solicitudes(manager_permisos.verTablaSolicitudes(Username)));
            int cantidad = manager_complemento.cantidadSolicitudes(manager_permisos.verTablaSolicitudes(Username));
            if(cantidad > 0){
                tabbedPrincipal.setTitleAt(3, "Solicitudes ("+cantidad+")");//Le damos el nombre a esa pestaña
            }
        }
        
        /*PESTAÑA DE EMPLEADO*/
        tabbedPrincipal.setTitleAt(4-pestañas, Username.toUpperCase());//Le damos el nombre a esa pestaña
        tablaResguardo.setModel(manager_complemento.getResguardoPersonal(Username));
        tablaSolicitudesPersonal.setModel(manager_solicitud.tabla_Solicitudes_Personales(Username));
        tablaPermisosPersonales.setModel(manager_permisos.getPermisos(tablaPermisosPersonales,Username));
        
        /*PESTAÑA CONFIGURACIÓN*/
        if(!(manager_permisos.esSuperUsuario(Username))){
            tabbedPrincipal.removeTabAt(5-pestañas);//Eliminamos la pestaña
        }
        /*
        int indice = tabbedPrincipal.getSelectedIndex(); //Guardamos el indice de la pestaña
        
        //Agregamos la ficha que eliminamos
        tabbedPrincipal.add(temporal, 2);//Agregamos la pestaña en una posicion especifica
        tabbedPrincipal.setTitleAt(2, "Configuración");//Le damos el nombre a esa pestaña
        */
        
        
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
            tablaSolicitudes.setModel(manager_solicitud.tabla_Solicitudes(manager_permisos.verTablaSolicitudes(Username)));
            int cantidad = manager_complemento.cantidadSolicitudes(manager_permisos.verTablaSolicitudes(Username));
            if(cantidad > 0){
                tabbedPrincipal.setTitleAt(3, "Solicitudes ("+cantidad+")");//Le damos el nombre a esa pestaña
            }
        }
        
    }//GEN-LAST:event_formWindowActivated

    private void BajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BajaActionPerformed

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

    private void tablaBDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaBDMouseClicked
        // TODO add your handling code here:
        tablaIP.clearSelection();
    }//GEN-LAST:event_tablaBDMouseClicked

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
    
    private void tablaIPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaIPMouseClicked
        // TODO add your handling code here:
        tablaBD.clearSelection();
    }//GEN-LAST:event_tablaIPMouseClicked

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

    private void configuracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_configuracionMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_configuracionMouseClicked

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
        if(manager_permisos.verTablaSolicitudes(Username) == 0){
                //"Ocultamos" la pestaña        
                temporalSolicitud = tabbedPrincipal.getComponentAt(4); //Hacemos una copia de esa pestaña porque será eliminada
                tabbedPrincipal.removeTabAt(4);//Eliminamos la pestaña 
        }else{
            tablaSolicitudes.setModel(manager_solicitud.tabla_Solicitudes(manager_permisos.verTablaSolicitudes(Username)));
        }
    }//GEN-LAST:event_ActualizarInfoActionPerformed

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

    private void btnAñadirResguardoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirResguardoActionPerformed
        // TODO add your handling code here:
        addResguardo ob = new addResguardo(this,true);
        ob.setVisible(true);
    }//GEN-LAST:event_btnAñadirResguardoActionPerformed

    private void tablaSolicitudesPersonalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaSolicitudesPersonalMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaSolicitudesPersonalMouseReleased

    private void comboInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboInventarioActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.consulta_inventario(Username)){
            
            if(comboInventario.getSelectedItem().toString().equals("Inventario")){
                tablaInventario.setModel(manager_inventario.getInventario());
                banderaInventario = 1;
                
                comboFiltro.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
                comboFiltro.addItem("Producto");
                comboFiltro.addItem("Almacén");
                comboFiltro.addItem("Marca");
                
            }else{
                tablaInventario.setModel(manager_inventario.getInventarioG());
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

    private void comboFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFiltroActionPerformed
        // TODO add your handling code here:
               //Llenamos la tabla del inventario
        int filtro = comboFiltro.getSelectedIndex();
        String inventario = comboInventario.getSelectedItem().toString();
        String busqueda = txtBusqueda.getText();

        //Si no hay nada en el campo entonces buscamos todos los productos del inventario o inventario a granel
        if(busqueda.equals("")){

            if(inventario.equals("Inventario")){
                tablaInventario.setModel(manager_inventario.getInventario());
            }
            else{
                tablaInventario.setModel(manager_inventario.getInventarioG());
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
                    tablaInventario.setModel(manager_inventario.getInventario());
                }
                else{
                    tablaInventario.setModel(manager_inventario.getInventarioG());
                }

            }//Segundo else

        }//Primer else
    }//GEN-LAST:event_comboFiltroActionPerformed

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
        // TODO add your handling code here:
        //Llenamos la tabla del inventario
        int filtro = comboFiltro.getSelectedIndex();
        String inventario = comboInventario.getSelectedItem().toString();
        String busqueda = txtBusqueda.getText();

        //Si no hay nada en el campo entonces buscamos todos los productos del inventario o inventario a granel
        if(busqueda.equals("")){

            if(inventario.equals("Inventario")){
                tablaInventario.setModel(manager_inventario.getInventario());
            }
            else{
                tablaInventario.setModel(manager_inventario.getInventarioG());
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
                    tablaInventario.setModel(manager_inventario.getInventario());
                }
                else{
                    tablaInventario.setModel(manager_inventario.getInventarioG());
                }

            }//Segundo else

        }//Primer else
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void tablaVehiculosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVehiculosMouseClicked
         // TODO add your handling code here:

        metodoVehiculos();
    }//GEN-LAST:event_tablaVehiculosMouseClicked
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
    private void btnAñadirVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirVehiculoActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.alta_vehiculos(Username)){
            ventana_añadir_vehiculo añadirVehiculo = new ventana_añadir_vehiculo(this, true);
            añadirVehiculo.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "No tienes permisos para dar de alta nuevos vehiculos.");
        }
    }//GEN-LAST:event_btnAñadirVehiculoActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
         // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void tablaVehiculosPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablaVehiculosPropertyChange
         // TODO add your handling code here:
        
    }//GEN-LAST:event_tablaVehiculosPropertyChange

    private void tablaVehiculosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaVehiculosKeyReleased
         // TODO add your handling code here:
         metodoVehiculos();
    }//GEN-LAST:event_tablaVehiculosKeyReleased

    private void zoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomActionPerformed
         // TODO add your handling code here:
        ventanaZoom ob = new ventanaZoom(this, true);
        ob.setVisible(true);
    }//GEN-LAST:event_zoomActionPerformed
    
    private void vehiculosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vehiculosMouseClicked
         // TODO add your handling code here:
         
        
    }//GEN-LAST:event_vehiculosMouseClicked
    
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

    private void ActualizarVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarVActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.update_vehiculos(Username)){
            
        }else{
            JOptionPane.showMessageDialog(null, "No tiene permisos para actualizar la información del vehiculo.");
        }
        
    }//GEN-LAST:event_ActualizarVActionPerformed

    private void rb_asignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_asignacionActionPerformed
        CardLayout c_asignacion = (CardLayout)pn_contenedor_ventanas.getLayout();
        c_asignacion.show(pn_contenedor_ventanas,"c_s_asignacion");
    }//GEN-LAST:event_rb_asignacionActionPerformed

    private void rb_recoleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_recoleccionActionPerformed
        CardLayout c_recoleccion = (CardLayout)pn_contenedor_ventanas.getLayout();
        c_recoleccion.show(pn_contenedor_ventanas,"c_s_recoleccion");
        l_eliminables_objetos_asignables_asignacion.clear();
        l_objetos_asignables_asignacion.clear();
        l_eliminables_objetos_asignables_asignacion_granel.clear();
        l_objetos_asignables_asignacion_granel.clear();
        l_cantidades_pedidas_granel.clear();
    }//GEN-LAST:event_rb_recoleccionActionPerformed

    private void rb_reemplazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_reemplazoActionPerformed
        CardLayout c_reemplazo = (CardLayout)pn_contenedor_ventanas.getLayout();
        c_reemplazo.show(pn_contenedor_ventanas,"c_s_reemplazo");
    }//GEN-LAST:event_rb_reemplazoActionPerformed

    private void rb_asignacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_asignacionesActionPerformed
        CardLayout c_asignaciones = (CardLayout)pn_contenedor_ventanas.getLayout();
        c_asignaciones.show(pn_contenedor_ventanas,"c_s_asignaciones");
    }//GEN-LAST:event_rb_asignacionesActionPerformed

    private void tb_empleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_empleadoMouseClicked
        try {
            asignacion_botones_activadas[0]=true;
            metodo.cargarNombreEmpleado_Asignacion();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tb_empleadoMouseClicked

    private void btn_seleccionar_empleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_seleccionar_empleadoActionPerformed
        if(tb_objetos_asignables.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"No existen objetos libres en el inventario");
            return;
        }
        if(asignacion_botones_activadas[0]==true){tb_empleado.setEnabled(false);asignacion_botones_activadas[0]=false;
            asignacion_botones_activadas[1]=asignacion_botones_activadas[2]=asignacion_botones_activadas[3]=true;
            metodo.activarObjetosAsignables();}
    }//GEN-LAST:event_btn_seleccionar_empleadoActionPerformed

    private void tb_objetos_asignablesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_objetos_asignablesMouseClicked
        int changed_id_tabla=tb_objetos_asignables.getSelectedRow();
        if(changed_id_tabla!=last_producto_granel_tabla_id){
            unidadesRestantes=unidadesAcumuladas=stockActualSeleccionado=unidadesAsignadas=0;
        }
    }//GEN-LAST:event_tb_objetos_asignablesMouseClicked

    private void btn_asignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_asignarActionPerformed
        last_producto_granel_tabla_id=tb_objetos_asignables.getSelectedRow();
        try {
            if(tf_cantidad.getText().isEmpty()&& rb_inventario_granel.isSelected()){JOptionPane.showMessageDialog(null,"Debe ingresar una cantidad");return;}
            if(tb_objetos_asignables.getRowCount()!=0 && rb_inventario_normal.isSelected()){
                objetos_asignables_asignacion=Tablas.retornarCadenaFila(tb_objetos_asignables,tb_objetos_asignables.getSelectedRow());
                l_eliminables_objetos_asignables_asignacion.add(objetos_asignables_asignacion.split(","));
                metodo.renovarInventarioAsignable(tb_objetos_asignables);
                metodo.cargarInventarioAsignado(tb_objetos_asignados);
            }
            if(tb_objetos_asignables.getRowCount()!=0 && rb_inventario_granel.isSelected()){
                stockActualSeleccionado=Integer.parseInt((String)tb_objetos_asignables.getValueAt(tb_objetos_asignables.getSelectedRow(),7));
                unidadesAsignadas=Integer.parseInt(tf_cantidad.getText());
                if(stockActualSeleccionado<unidadesAsignadas||(stockActualSeleccionado-unidadesAsignadas)<Integer.parseInt((String)tb_objetos_asignables.getValueAt(tb_objetos_asignables.getSelectedRow(),6))){
                    showMessageDialog(null,"Se estan pidiendo más productos de los que hay, o más del mínimo de reserva!");return;
                }
                l_eliminables_objetos_asignables_asignacion_granel.add(metodo.getExistenciaGranelArray(Tablas.retornarContenidoFila(tb_objetos_asignables, tb_objetos_asignables.getSelectedRow())));
                metodo.insertarDatosinventario_granel_objetos_asignados();
                metodo.cargarInventarioGranelAuxiliar(tb_objetos_asignados);
            }
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_asignarActionPerformed

    private void btn_generar_valeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generar_valeActionPerformed
        String datosInventarioAsignado="";
        try {
            tb_empleado.setEnabled(true);
            asignacion_botones_activadas[0]=true;
            asignacion_botones_activadas[1]=asignacion_botones_activadas[2]=asignacion_botones_activadas[3]=false;
            metodo.activarObjetosAsignables();

            for(int i=0;i<tb_objetos_asignados.getRowCount();i++){
                datosInventarioAsignado+=Tablas.retornarCadenaFila(tb_objetos_asignados, i)+";"+"\n";
            }
            Archivo.createArchivo("", "vale_resguardo_configsA.txt",
                tf_empleado.getText()+","+"\n"+Cargo+","+Area+","+Tipo_de_uso+","+
                Municipio+","+Localidad
            );
            Archivo.createArchivo("", "vale_resguardo_configsB.txt",datosInventarioAsignado
            );
            metodo.actualizarEstatusInventarioAsignado();
            metodo.actualizarEstatusInventarioGranelAsignado();
            metodo.insertarDatosValeAsignacion();
            if(rb_inventario_normal.isSelected()){

                metodo.insertarDatosDetalleValeAsignacion();
            }else{
                metodo.insertarDatosDetalleValeGranelAsignacion();
                metodo.actualizarExistenciasInventarioGranel();
            }

            metodo.cargarInventarioGlobal(tb_inventario_normal_asignado);
            metodo.cargarInventarioGlobalGranel(tb_inventario_granel_asignado);
            metodo.resetObjetosAsignablesYAsignados();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            metodo.borrarDatosTabla("inventario_granel_objetos_asignados");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        fr_Generacion_Vale_Resguardo_Bienes vale;
        try {
            vale = new fr_Generacion_Vale_Resguardo_Bienes();
            vale.setVisible(true);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btn_generar_valeActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        try {
            metodo.borrarDatosTabla("inventario_granel_objetos_asignados");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        tb_empleado.setEnabled(true);
        unidadesRestantes=unidadesAcumuladas=stockActualSeleccionado=unidadesAsignadas=0;
        asignacion_botones_activadas[0]=true;
        asignacion_botones_activadas[1]=asignacion_botones_activadas[2]=asignacion_botones_activadas[3]=false;
        metodo.activarObjetosAsignables();
        try {
            metodo.resetObjetosAsignablesYAsignados();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void rb_inventario_normalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_inventario_normalActionPerformed
        tf_cantidad.setVisible(false);
        try {

            metodo.resetObjetosAsignablesYAsignados();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_rb_inventario_normalActionPerformed

    private void rb_inventario_granelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rb_inventario_granelMouseClicked
        tf_cantidad.setVisible(true);
    }//GEN-LAST:event_rb_inventario_granelMouseClicked

    private void rb_inventario_granelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_inventario_granelActionPerformed
        tf_cantidad.setVisible(true);
        try {
            metodo.copyInventarioGranel(tb_objetos_asignables);
            metodo.resetObjetosAsignablesYAsignados();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_rb_inventario_granelActionPerformed

    private void tb_empleado1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_empleado1MouseClicked
        try {
            if(tb_empleado1.isEnabled()){
                metodo.cargarNombreEmpleado_Asignacion1();
                metodo.cargarObjetosAsignados_Recoleccion();
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tb_empleado1MouseClicked

    private void btn_seleccionar_empleado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_seleccionar_empleado1ActionPerformed
        if(tb_objetos_asignados1.getRowCount()!=0){
            asignacion_botones_activadas[5]=asignacion_botones_activadas[7]=true;tb_empleado1.setEnabled(false);
            metodo.activarObjetosAsignables();
        }
        
    }//GEN-LAST:event_btn_seleccionar_empleado1ActionPerformed

    private void btn_generar_vale1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generar_vale1ActionPerformed
        String datosInventarioRecolectado="";
        String datosInventarioFaltante="";
        try {
            tb_empleado.setEnabled(true);
            asignacion_botones_activadas[4]=true;
            asignacion_botones_activadas[5]=asignacion_botones_activadas[6]=asignacion_botones_activadas[7]=asignacion_botones_activadas[8]=false;
            metodo.activarObjetosAsignables();
            
            Archivo.createArchivo("", "vale_recoleccion_configs.txt",
                tf_empleado1.getText()+","+"\n"+Cargo1+","+Area1+","+Tipo_de_uso1+","+
                Municipio1+","+Localidad1
            );
            for(int i=0;i<tb_objetos_entregados.getRowCount();i++){
                datosInventarioRecolectado+=Tablas.retornarCadenaFila(tb_objetos_entregados, i)+";"+"\n";
                System.out.println(datosInventarioRecolectado+"\n");
            }
            Archivo.createArchivo("", "vale_recoleccion_configsA.txt",datosInventarioRecolectado
            );
            for(int i=0;i<tb_objetos_faltantes.getRowCount();i++){
                datosInventarioFaltante+=Tablas.retornarCadenaFila(tb_objetos_faltantes, i)+";"+"\n";
            }
            Archivo.createArchivo("", "vale_recoleccion_configsB.txt",datosInventarioFaltante
            );
            
            metodo.insertarDatosValeRecoleccion();
            metodo.insertarDatosDetalleRecoleccion();
            metodo.setInventarioDisponible(tb_objetos_entregados);
            metodo.cargarInventarioGlobal(tb_inventario_normal_asignado);
            
            
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fr_Generacion_Vale_Recoleccion_Bienes vale;
        try {
            vale = new fr_Generacion_Vale_Recoleccion_Bienes();
            vale.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_generar_vale1ActionPerformed

    private void btn_recolectar_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_recolectar_productoActionPerformed
        try {
            asignacion_botones_activadas[6]=true;
            metodo.activarObjetosAsignables();
            if(tb_objetos_asignados1.getRowCount()!=0){
                l_obj_asig=metodo.transformContentTableIntoArrayList(this.tb_objetos_asignados1);

                l_obj_entr.add(Tablas.retornarContenidoFila(tb_objetos_asignados1, tb_objetos_asignados1.getSelectedRow()));
                printContenidoLista(l_obj_asig,"l_obj_asig");
                printContenidoLista(l_obj_entr,"l_obj_entr");
                metodo.renovarInventarioRecoleccion(tb_objetos_asignados1,tb_objetos_entregados);
            }

        }
        catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_recolectar_productoActionPerformed

    private void btn_entregar_objetosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_entregar_objetosActionPerformed
        asignacion_botones_activadas[8]=true;asignacion_botones_activadas[6]=asignacion_botones_activadas[5]=asignacion_botones_activadas[4]=false;
        metodo.activarObjetosAsignables();
        if(tb_objetos_asignados1.getRowCount()!=0){
            metodo.copyContentTabla(tb_objetos_asignados1,tb_objetos_faltantes);
        }
    }//GEN-LAST:event_btn_entregar_objetosActionPerformed

    private void btn_cancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelar1ActionPerformed
        asignacion_botones_activadas[6]=false;
        asignacion_botones_activadas[8]=true;
        metodo.activarObjetosAsignables();

        tb_empleado1.setEnabled(true);

        asignacion_botones_activadas[4]=true;
        asignacion_botones_activadas[5]=asignacion_botones_activadas[6]=asignacion_botones_activadas[7]=asignacion_botones_activadas[8]=false;
        metodo.activarObjetosAsignables();
        try {
            metodo.resetObjetosAsignablesYAsignados();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_cancelar1ActionPerformed

    private void tb_producto_a_reemplazarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_producto_a_reemplazarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_producto_a_reemplazarMouseClicked

    private void btn_generar_vale2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generar_vale2ActionPerformed
        fr_Generacion_Vale_Salida_Almacen vale=new fr_Generacion_Vale_Salida_Almacen();
        vale.setVisible(true);
    }//GEN-LAST:event_btn_generar_vale2ActionPerformed

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

    private void comboFiltroUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFiltroUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboFiltroUsuarioActionPerformed

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

    private void AtenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtenderActionPerformed
        // TODO add your handling code here:
        ventana_AtenderSolicitud ob = new ventana_AtenderSolicitud(this,true);
        ob.setVisible(true);
        
    }//GEN-LAST:event_AtenderActionPerformed

    private void mi_viaticosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_viaticosActionPerformed
 
        PrincipalS a= new PrincipalS();
        a.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_mi_viaticosActionPerformed
       
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
    private javax.swing.JMenuItem ActualizarInfo;
    private javax.swing.JMenuItem ActualizarV;
    private javax.swing.JMenuItem AgregarStock;
    private javax.swing.JMenuItem Asignar;
    private javax.swing.JMenuItem AsignarV;
    private javax.swing.JMenuItem Atender;
    private javax.swing.JMenuItem Baja;
    private javax.swing.JMenuItem CambiarContra;
    private javax.swing.JMenuItem Comodato;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JMenuItem Equipos;
    private javax.swing.ButtonGroup Grupo1;
    private javax.swing.JPopupMenu MenuInventario;
    private javax.swing.JPopupMenu MenuPersonal;
    private javax.swing.JMenuItem MenuSolicitud;
    private javax.swing.JPopupMenu MenuSolicitudes;
    private javax.swing.JPopupMenu MenuUsuarios;
    private javax.swing.JPopupMenu MenuVehiculos;
    private javax.swing.JMenuItem Permisos;
    private javax.swing.JMenuItem Promover;
    private javax.swing.JMenuItem Servicio;
    private javax.swing.JMenuItem SolicitarBaja;
    private javax.swing.JMenu SolictarMas;
    private javax.swing.ButtonGroup bg_manejo_inventario;
    private javax.swing.ButtonGroup bt_tipo_inventario_asignable;
    private javax.swing.JButton btnAddEmpleado;
    private javax.swing.JButton btnAddInventario;
    private javax.swing.JButton btnAñadirResguardo;
    private javax.swing.JButton btnAñadirVehiculo;
    private javax.swing.JButton btnEditar;
    public javax.swing.JButton btn_asignar;
    public javax.swing.JButton btn_cancelar;
    public javax.swing.JButton btn_cancelar1;
    public javax.swing.JButton btn_entregar_objetos;
    public javax.swing.JButton btn_generar_vale;
    public javax.swing.JButton btn_generar_vale1;
    private javax.swing.JButton btn_generar_vale2;
    public javax.swing.JButton btn_recolectar_producto;
    public javax.swing.JButton btn_seleccionar_empleado;
    public javax.swing.JButton btn_seleccionar_empleado1;
    private javax.swing.JTextArea campoObservaciones;
    private javax.swing.JSpinner campoip1;
    private javax.swing.JSpinner campoip2;
    private javax.swing.JSpinner campoip3;
    private javax.swing.JSpinner campoip4;
    private javax.swing.JComboBox<String> comboFiltro;
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
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
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
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
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
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lb_background;
    private javax.swing.JLabel lb_empleado;
    private javax.swing.JLabel lb_empleado1;
    private javax.swing.JLabel lb_objetos_asignables;
    private javax.swing.JLabel lb_objetos_asignables1;
    private javax.swing.JLabel lb_objetos_asignados;
    private javax.swing.JLabel lb_objetos_entregados;
    private javax.swing.JLabel lb_objetos_faltantes;
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
    private javax.swing.JMenu menuAsignar;
    private javax.swing.JMenu menuOpciones;
    private javax.swing.JMenuItem menuPermisos;
    private javax.swing.JMenuItem menuPuestoArea;
    private javax.swing.JMenuItem mi_viaticos;
    private javax.swing.JPanel pestañaInventario;
    private javax.swing.JPanel pn_acciones;
    private javax.swing.JPanel pn_asignacion_inventario;
    private javax.swing.JPanel pn_asignaciones_inventario;
    private javax.swing.JPanel pn_contenedor_ventanas;
    private javax.swing.JPanel pn_general;
    private javax.swing.JPanel pn_manejo_inventario;
    private javax.swing.JPanel pn_recoleccion_inventario;
    private javax.swing.JPanel pn_reemplazo_inventario;
    private javax.swing.JPanel pn_tablaUsuarios;
    private javax.swing.JRadioButton rb_asignacion;
    private javax.swing.JRadioButton rb_asignaciones;
    public javax.swing.JRadioButton rb_inventario_granel;
    public javax.swing.JRadioButton rb_inventario_normal;
    public javax.swing.JRadioButton rb_recoleccion;
    private javax.swing.JRadioButton rb_reemplazo;
    private javax.swing.JPanel solicitudes;
    private javax.swing.JScrollPane sp_asignacion_inventario;
    private javax.swing.JScrollPane sp_asignaciones_inventario;
    private javax.swing.JScrollPane sp_recoleccion_inventario;
    private javax.swing.JScrollPane sp_reemplazo_inventario;
    public static javax.swing.JTabbedPane tabbedPrincipal;
    private javax.swing.JTable tablaBD;
    private javax.swing.JTable tablaIP;
    public static javax.swing.JTable tablaInventario;
    private javax.swing.JTable tablaPermisosPersonales;
    public static javax.swing.JTable tablaResguardo;
    public static javax.swing.JTable tablaSolicitudes;
    public static javax.swing.JTable tablaSolicitudesPersonal;
    public static javax.swing.JTable tablaUsuarios;
    public static javax.swing.JTable tablaVehiculos;
    public javax.swing.JTable tb_empleado;
    public javax.swing.JTable tb_empleado1;
    private javax.swing.JTable tb_inventario_granel_asignado;
    private javax.swing.JTable tb_inventario_normal_asignado;
    public javax.swing.JTable tb_objetos_asignables;
    public javax.swing.JTable tb_objetos_asignados;
    public javax.swing.JTable tb_objetos_asignados1;
    public javax.swing.JTable tb_objetos_entregados;
    public javax.swing.JTable tb_objetos_faltantes;
    private javax.swing.JTable tb_producto_a_reemplazar;
    public javax.swing.JTextField tf_cantidad;
    public javax.swing.JTextField tf_empleado;
    public javax.swing.JTextField tf_empleado1;
    public javax.swing.JTextField tf_pruducto_reemplazable;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtBusquedaUsuario;
    private javax.swing.JTextField txtBusquedaVehiculos;
    private javax.swing.JPanel usuarios;
    private javax.swing.JPanel vehiculos;
    private javax.swing.JButton zoom;
    // End of variables declaration//GEN-END:variables
}
