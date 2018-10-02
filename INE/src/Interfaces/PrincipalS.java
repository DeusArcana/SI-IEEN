/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Clases.Conexion;
import javax.swing.JOptionPane;

import Clases.ManagerSoViaticos;
import Clases.CrearPDF;
import Clases.*;
import Clases.Excel;
import Clases.ManagerComplemento;
import Clases.ManagerPermisos;
import Formularios.addSolicitudVehiculo;

import Formularios.addSolicitudViaticos;
import Formularios.addViaticoVehiculo;
import Formularios.visSolicitudViaticos;
import static Interfaces.Principal.Username;
import static Interfaces.Principal.tablaInventario;
import static Interfaces.Principal.tablaUsuarios;
import com.itextpdf.text.DocumentException;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import org.jvnet.substance.SubstanceLookAndFeel;

/**
 *
 * @author usuario
 */
public class PrincipalS extends javax.swing.JFrame {

    Conexion cbd = new Conexion();
    Connection cn = cbd.getConexion();
    ManagerSoViaticos manager_soviaticos;
    ManagerPermisos manager_permisos;
    visSolicitudViaticos s;
    DefaultTableModel modelo;
    CrearPDF pdf = new CrearPDF();
    boolean limpiar = false;
    boolean superUsuario = false;
    int idArea = 0;
    ManagerComplemento manager_complemento;
    public static int conVehiculo;//Sirve para saber si se tiene que solicitar un vehiculo cuando está en 1
    public static int viatico_vehiculo = 0;

    /**
     * Creates new form PrincipalS
     */
    public PrincipalS() {
        //this.setTitle("Movimientos de viáticos");
        initComponents();
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Iconos/IEE.png")).getImage());
        //tablasolic.setModel(manager_soviaticos.getTasol()); 
        tablasolic.getTableHeader().setReorderingAllowed(false);
        tablonpendientes.getTableHeader().setReorderingAllowed(false);
        tablonaceptadas.getTableHeader().setReorderingAllowed(false);
        tabloncanceladas.getTableHeader().setReorderingAllowed(false);
        tablonarchivadas.getTableHeader().setReorderingAllowed(false);
        manager_soviaticos = new ManagerSoViaticos();
        manager_permisos = new ManagerPermisos();
        manager_complemento = new ManagerComplemento();
        c = 0;
        String lista = manager_complemento.obtenerAreas();
        String[] recoger = lista.split(",,");

        cmbArea.setModel(new javax.swing.DefaultComboBoxModel(new String[]{}));
        cmbArea.addItem("Todos");
        for (int i = 1; i <= recoger.length; i = i + 2) {
            cmbArea.addItem(recoger[i]);
        }
        if (manager_permisos.accesoModulo("consulta", "Informe", Principal.Username)) {
            ResultSet usuario;
            try {
                usuario = cbd.getTabla("select puesto from user where id_user='" + Principal.Username + "'", cn);
                usuario.next();
                if (usuario.getString("puesto").equals("SuperUsuario") || usuario.getString("puesto").equals("Administrador")) {
                    superUsuario = true;
                    if (idArea > 0) {
                        Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S inner join Oficio_comision O on idSolicitud=O.solicitud_idSolicitud inner join puestos_trabajo PT on S.puesto=PT.Puesto WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 and PT.id_Area=" + idArea + " order by O.folio desc");
                        SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S inner join Oficio_comision O on S.idSolicitud=O.solicitud_idSolicitud inner join Informe I on S.idSolicitud=I.solicitud_idSolicitud inner join Puestos_trabajo PT on S.puesto=PT.puesto WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 and PT.id_area=" + idArea + " ORDER BY I.Id_Informe DESC");
                    } else {
                        Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 order by O.folio desc");
                        SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S, Oficio_comision O, Informe I WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 ORDER BY I.Id_Informe DESC");
                    }
                    cmbArea.setVisible(true);
                    lblArea.setVisible(true);
                } else {
                    superUsuario = false;
                    usuario = cbd.getTabla("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m) as nombre from user U inner join empleados E on U.id_empleado=E.id_empleado where U.id_user='" + Principal.Username + "';", cn);
                    usuario.next();
                    Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 and S.nombre='" + usuario.getString("nombre") + "' order by O.folio desc");
                    SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S, Oficio_comision O, Informe I WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 and S.nombre='" + usuario.getString("nombre") + "' ORDER BY I.Id_Informe DESC");
                    cmbArea.setVisible(false);
                    lblArea.setVisible(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    int folio, c;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel17 = new javax.swing.JPanel();
        btnAddInventario2 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        MenuSolicitudViaticos = new javax.swing.JPopupMenu();
        Impri_Sol = new javax.swing.JMenuItem();
        ConsultarP1 = new javax.swing.JMenuItem();
        Add = new javax.swing.JMenuItem();
        SolicitarVehiculo = new javax.swing.JMenuItem();
        CambiarConsejero = new javax.swing.JMenuItem();
        ExportarExcel = new javax.swing.JMenuItem();
        MenuTablonP = new javax.swing.JPopupMenu();
        ConsultarP = new javax.swing.JMenuItem();
        AceptarP = new javax.swing.JMenuItem();
        CancelarP = new javax.swing.JMenuItem();
        ExportarExcelP = new javax.swing.JMenuItem();
        MenuTablonA = new javax.swing.JPopupMenu();
        ConsultarA = new javax.swing.JMenuItem();
        OficioComision = new javax.swing.JMenuItem();
        OficioViatico = new javax.swing.JMenuItem();
        AsignarMonto = new javax.swing.JMenuItem();
        CancelarA = new javax.swing.JMenuItem();
        Archivar = new javax.swing.JMenuItem();
        ExportarExcelA = new javax.swing.JMenuItem();
        AgregarEmpleados = new javax.swing.JMenuItem();
        MenuTablonC = new javax.swing.JPopupMenu();
        ConsultarC = new javax.swing.JMenuItem();
        ExportarExcelC = new javax.swing.JMenuItem();
        MenuPanelSolicitudViatico = new javax.swing.JPopupMenu();
        Add1 = new javax.swing.JMenuItem();
        SolicitarVehiculo1 = new javax.swing.JMenuItem();
        MenuInfSA = new javax.swing.JPopupMenu();
        GenerarInf = new javax.swing.JMenuItem();
        ExportarExcelInA = new javax.swing.JMenuItem();
        MenuInfSF = new javax.swing.JPopupMenu();
        ConsultarInf = new javax.swing.JMenuItem();
        ExportarExcelInFi = new javax.swing.JMenuItem();
        MenuGI = new javax.swing.JPopupMenu();
        AñadirA = new javax.swing.JMenuItem();
        EliminarA = new javax.swing.JMenuItem();
        MenuTablonAr = new javax.swing.JPopupMenu();
        ConsultarAr = new javax.swing.JMenuItem();
        OficioComisionAr = new javax.swing.JMenuItem();
        OficioViaticoAr = new javax.swing.JMenuItem();
        ExportarExcelAr = new javax.swing.JMenuItem();
        MenuSolicitudViaticos1 = new javax.swing.JPopupMenu();
        Impri_Sol1 = new javax.swing.JMenuItem();
        ConsultarP2 = new javax.swing.JMenuItem();
        AsignarVehiculo = new javax.swing.JMenuItem();
        Add2 = new javax.swing.JMenuItem();
        SolicitarVehiculo2 = new javax.swing.JMenuItem();
        ExportarExcel1 = new javax.swing.JMenuItem();
        solicviaticos = new javax.swing.JTabbedPane();
        solicitudviaticos1 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        tablasolic = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jScrollPane12 = new javax.swing.JScrollPane();
        tablasolicvehiculo = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jLabel17 = new javax.swing.JLabel();
        txtbusquedasoli = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnActualizarSolic = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        tablonsolicitud1 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtbusquedasoli1 = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        menutablones = new javax.swing.JTabbedPane();
        solipendientes = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tablonpendientes = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        soliaceptadas = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tablonaceptadas = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        solicarchivadas = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablonarchivadas = new javax.swing.JTable();
        solicanceladas = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tabloncanceladas = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jLabel6 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        btnbuscarporfecha = new javax.swing.JButton();
        fechainicio = new com.toedter.calendar.JDateChooser();
        fechafinal = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        informe = new javax.swing.JPanel();
        jlb = new javax.swing.JLabel();
        txtbusquedasoli2 = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        menuInforme = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablainfo = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblObsVehiculo = new javax.swing.JLabel();
        GaTot = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnguardar = new javax.swing.JButton();
        btnregresar1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaact = new javax.swing.JTable();
        txtKilometraje = new javax.swing.JTextField();
        txtobvia = new javax.swing.JScrollPane();
        txtobvia1 = new javax.swing.JTextArea();
        lblKilometraje = new javax.swing.JLabel();
        txtobveh = new javax.swing.JScrollPane();
        txtobveh1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablainfo1 = new javax.swing.JTable();
        btnregresar = new javax.swing.JButton();
        lblArea = new javax.swing.JLabel();
        cmbArea = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        btnActualizarInforme = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mi_inventario = new javax.swing.JMenuItem();
        mi_pases = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        itemSalir = new javax.swing.JMenuItem();

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones :", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI", 0, 12))); // NOI18N

        btnAddInventario2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        btnAddInventario2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/agregar.png"))); // NOI18N
        btnAddInventario2.setText("Nueva solicitud");
        btnAddInventario2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAddInventario2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddInventario2ActionPerformed(evt);
            }
        });

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IEE.png"))); // NOI18N

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(btnAddInventario2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btnAddInventario2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(339, Short.MAX_VALUE))
        );

        Impri_Sol.setText("Imprimir solicitud");
        Impri_Sol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Impri_SolActionPerformed(evt);
            }
        });
        MenuSolicitudViaticos.add(Impri_Sol);

        ConsultarP1.setText("Consultar");
        ConsultarP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarP1ActionPerformed(evt);
            }
        });
        MenuSolicitudViaticos.add(ConsultarP1);

        Add.setText("Nueva solicitud de viáticos");
        Add.setActionCommand("Solicitud viático");
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });
        MenuSolicitudViaticos.add(Add);

        SolicitarVehiculo.setText("Nueva solicitud de vehículo");
        SolicitarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SolicitarVehiculoActionPerformed(evt);
            }
        });
        MenuSolicitudViaticos.add(SolicitarVehiculo);

        CambiarConsejero.setText("Cambiar Consejero Presidente");
        CambiarConsejero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CambiarConsejeroActionPerformed(evt);
            }
        });
        MenuSolicitudViaticos.add(CambiarConsejero);

        ExportarExcel.setText("ExportarExcel");
        ExportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportarExcelActionPerformed(evt);
            }
        });
        MenuSolicitudViaticos.add(ExportarExcel);

        ConsultarP.setText("Consultar");
        ConsultarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarPActionPerformed(evt);
            }
        });
        MenuTablonP.add(ConsultarP);

        AceptarP.setText("Aceptar");
        AceptarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarPActionPerformed(evt);
            }
        });
        MenuTablonP.add(AceptarP);

        CancelarP.setText("Cancelar");
        CancelarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarPActionPerformed(evt);
            }
        });
        MenuTablonP.add(CancelarP);

        ExportarExcelP.setText("ExportarExcel");
        ExportarExcelP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportarExcelPActionPerformed(evt);
            }
        });
        MenuTablonP.add(ExportarExcelP);

        ConsultarA.setText("Consultar");
        ConsultarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarAActionPerformed(evt);
            }
        });
        MenuTablonA.add(ConsultarA);

        OficioComision.setText("Oficio de comisión");
        OficioComision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OficioComisionActionPerformed(evt);
            }
        });
        MenuTablonA.add(OficioComision);

        OficioViatico.setText("Oficio de viático");
        OficioViatico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OficioViaticoActionPerformed(evt);
            }
        });
        MenuTablonA.add(OficioViatico);

        AsignarMonto.setText("Modificar monto");
        AsignarMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AsignarMontoActionPerformed(evt);
            }
        });
        MenuTablonA.add(AsignarMonto);

        CancelarA.setText("Cancelar");
        CancelarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarAActionPerformed(evt);
            }
        });
        MenuTablonA.add(CancelarA);

        Archivar.setText("Archivar");
        Archivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArchivarActionPerformed(evt);
            }
        });
        MenuTablonA.add(Archivar);

        ExportarExcelA.setText("ExportarExcel");
        ExportarExcelA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportarExcelAActionPerformed(evt);
            }
        });
        MenuTablonA.add(ExportarExcelA);

        AgregarEmpleados.setText("Agregar empleados al vehiculo");
        AgregarEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarEmpleadosActionPerformed(evt);
            }
        });
        MenuTablonA.add(AgregarEmpleados);

        ConsultarC.setText("Consultar");
        ConsultarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarCActionPerformed(evt);
            }
        });
        MenuTablonC.add(ConsultarC);

        ExportarExcelC.setText("ExportarExcel");
        ExportarExcelC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportarExcelCActionPerformed(evt);
            }
        });
        MenuTablonC.add(ExportarExcelC);

        Add1.setText("Nueva solicitud de viáticos");
        Add1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add1ActionPerformed(evt);
            }
        });
        MenuPanelSolicitudViatico.add(Add1);

        SolicitarVehiculo1.setText("Nueva solicitud de vehículo");
        SolicitarVehiculo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SolicitarVehiculo1ActionPerformed(evt);
            }
        });
        MenuPanelSolicitudViatico.add(SolicitarVehiculo1);

        GenerarInf.setText("Generar informe");
        GenerarInf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerarInfActionPerformed(evt);
            }
        });
        MenuInfSA.add(GenerarInf);

        ExportarExcelInA.setText("ExportarExcel");
        ExportarExcelInA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportarExcelInAActionPerformed(evt);
            }
        });
        MenuInfSA.add(ExportarExcelInA);

        ConsultarInf.setText("Consultar");
        ConsultarInf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarInfActionPerformed(evt);
            }
        });
        MenuInfSF.add(ConsultarInf);
        ConsultarInf.getAccessibleContext().setAccessibleName("Consultar informe");

        ExportarExcelInFi.setText("ExportarExcel");
        ExportarExcelInFi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportarExcelInFiActionPerformed(evt);
            }
        });
        MenuInfSF.add(ExportarExcelInFi);

        AñadirA.setText("Añadir actividad");
        AñadirA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AñadirAActionPerformed(evt);
            }
        });
        MenuGI.add(AñadirA);

        EliminarA.setText("Eliminar actividad");
        EliminarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarAActionPerformed(evt);
            }
        });
        MenuGI.add(EliminarA);

        ConsultarAr.setText("Consultar");
        ConsultarAr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarArActionPerformed(evt);
            }
        });
        MenuTablonAr.add(ConsultarAr);

        OficioComisionAr.setText("Oficio de comisión");
        OficioComisionAr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OficioComisionArActionPerformed(evt);
            }
        });
        MenuTablonAr.add(OficioComisionAr);

        OficioViaticoAr.setText("Oficio de viático");
        OficioViaticoAr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OficioViaticoArActionPerformed(evt);
            }
        });
        MenuTablonAr.add(OficioViaticoAr);

        ExportarExcelAr.setText("ExportarExcel");
        ExportarExcelAr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportarExcelArActionPerformed(evt);
            }
        });
        MenuTablonAr.add(ExportarExcelAr);

        Impri_Sol1.setText("Imprimir solicitud");
        Impri_Sol1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Impri_Sol1ActionPerformed(evt);
            }
        });
        MenuSolicitudViaticos1.add(Impri_Sol1);

        ConsultarP2.setText("Consultar");
        ConsultarP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarP2ActionPerformed(evt);
            }
        });
        MenuSolicitudViaticos1.add(ConsultarP2);

        AsignarVehiculo.setText("Asignar vehículo");
        AsignarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AsignarVehiculoActionPerformed(evt);
            }
        });
        MenuSolicitudViaticos1.add(AsignarVehiculo);

        Add2.setText("Nueva solicitud de viáticos");
        Add2.setActionCommand("Solicitud viático");
        Add2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add2ActionPerformed(evt);
            }
        });
        MenuSolicitudViaticos1.add(Add2);

        SolicitarVehiculo2.setText("Nueva solicitud de vehículo");
        SolicitarVehiculo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SolicitarVehiculo2ActionPerformed(evt);
            }
        });
        MenuSolicitudViaticos1.add(SolicitarVehiculo2);

        ExportarExcel1.setText("ExportarExcel");
        ExportarExcel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportarExcel1ActionPerformed(evt);
            }
        });
        MenuSolicitudViaticos1.add(ExportarExcel1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sistema Integral - Instituto Estatal Electoral de Nayarit");
        setIconImage(getIconImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        solicviaticos.setBackground(new java.awt.Color(255, 204, 204));
        solicviaticos.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        solicitudviaticos1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                solicitudviaticos1MouseReleased(evt);
            }
        });
        solicitudviaticos1.setLayout(null);

        jPanel16.setBackground(java.awt.Color.white);

        jScrollPane11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jScrollPane11MouseReleased(evt);
            }
        });

        tablasolic.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Puesto", "Fecha_salida", "Fecha_llegada", "Lugar"
            }
        ));
        tablasolic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablasolicMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablasolicMouseReleased(evt);
            }
        });
        jScrollPane11.setViewportView(tablasolic);

        jTabbedPane1.addTab("Solicitud de viáticos", new javax.swing.ImageIcon(getClass().getResource("/Iconos/solicitudes.png")), jScrollPane11); // NOI18N

        jScrollPane12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jScrollPane12MouseReleased(evt);
            }
        });

        tablasolicvehiculo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Puesto", "Fecha_salida", "Fecha_llegada", "Lugar"
            }
        ));
        tablasolicvehiculo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablasolicvehiculoMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablasolicvehiculoMouseReleased(evt);
            }
        });
        jScrollPane12.setViewportView(tablasolicvehiculo);

        jTabbedPane1.addTab("Solicitud de vehículos", new javax.swing.ImageIcon(getClass().getResource("/Iconos/solicitudes.png")), jScrollPane12); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        solicitudviaticos1.add(jPanel16);
        jPanel16.setBounds(20, 170, 1300, 530);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("Búsqueda:");
        solicitudviaticos1.add(jLabel17);
        jLabel17.setBounds(40, 120, 100, 22);

        txtbusquedasoli.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtbusquedasoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbusquedasoliKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbusquedasoliKeyReleased(evt);
            }
        });
        solicitudviaticos1.add(txtbusquedasoli);
        txtbusquedasoli.setBounds(130, 120, 290, 30);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/banner soviaticos.png"))); // NOI18N
        solicitudviaticos1.add(jLabel5);
        jLabel5.setBounds(20, 20, 1350, 80);

        btnActualizarSolic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/if_view-refresh_118801.png"))); // NOI18N
        btnActualizarSolic.setText("Actualizar");
        btnActualizarSolic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarSolicActionPerformed(evt);
            }
        });
        solicitudviaticos1.add(btnActualizarSolic);
        btnActualizarSolic.setBounds(1060, 120, 120, 30);

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/formularios.png"))); // NOI18N
        solicitudviaticos1.add(jLabel19);
        jLabel19.setBounds(0, 0, 1366, 769);

        solicviaticos.addTab("Solicitud de Viáticos", new javax.swing.ImageIcon(getClass().getResource("/Iconos/solicitudes.png")), solicitudviaticos1); // NOI18N

        tablonsolicitud1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tablonsolicitud1FocusLost(evt);
            }
        });
        tablonsolicitud1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablonsolicitud1MouseReleased(evt);
            }
        });
        tablonsolicitud1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablonsolicitud1KeyPressed(evt);
            }
        });
        tablonsolicitud1.setLayout(null);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setText("Búsqueda:");
        tablonsolicitud1.add(jLabel20);
        jLabel20.setBounds(70, 120, 100, 22);

        txtbusquedasoli1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtbusquedasoli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbusquedasoli1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbusquedasoli1KeyReleased(evt);
            }
        });
        tablonsolicitud1.add(txtbusquedasoli1);
        txtbusquedasoli1.setBounds(170, 120, 290, 30);

        menutablones.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                menutablonesStateChanged(evt);
            }
        });

        solipendientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                solipendientesMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                solipendientesMouseReleased(evt);
            }
        });
        solipendientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                solipendientesKeyPressed(evt);
            }
        });
        solipendientes.setLayout(null);

        tablonpendientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Puesto", "Fecha_salida", "Fecha_llegada", "Lugar"
            }
        ));
        tablonpendientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablonpendientesMouseReleased(evt);
            }
        });
        jScrollPane13.setViewportView(tablonpendientes);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 1308, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        solipendientes.add(jPanel23);
        jPanel23.setBounds(0, 0, 1320, 500);

        menutablones.addTab("Solicitudes Pendientes", new javax.swing.ImageIcon(getClass().getResource("/Iconos/solicitudes.png")), solipendientes); // NOI18N

        soliaceptadas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                soliaceptadasMouseReleased(evt);
            }
        });
        soliaceptadas.setLayout(null);

        tablonaceptadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Folio", "Monto", "Fecha_salida", "Fecha_llegada", "Lugar"
            }
        ));
        tablonaceptadas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablonaceptadasMouseReleased(evt);
            }
        });
        jScrollPane14.setViewportView(tablonaceptadas);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 1308, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 43, Short.MAX_VALUE))
        );

        soliaceptadas.add(jPanel24);
        jPanel24.setBounds(0, 0, 1320, 530);

        menutablones.addTab("Solicitudes Aceptadas", new javax.swing.ImageIcon(getClass().getResource("/Iconos/solicitudes.png")), soliaceptadas); // NOI18N

        solicarchivadas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                solicarchivadasFocusLost(evt);
            }
        });
        solicarchivadas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                solicarchivadasMouseReleased(evt);
            }
        });
        solicarchivadas.setLayout(null);

        jScrollPane4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jScrollPane4MouseReleased(evt);
            }
        });

        tablonarchivadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablonarchivadas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablonarchivadasMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tablonarchivadas);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1320, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 45, Short.MAX_VALUE))
        );

        solicarchivadas.add(jPanel25);
        jPanel25.setBounds(0, 0, 1320, 530);

        menutablones.addTab("Solicitudes Archivadas", new javax.swing.ImageIcon(getClass().getResource("/Iconos/solicitudes.png")), solicarchivadas); // NOI18N

        solicanceladas.setLayout(null);

        tabloncanceladas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Puesto", "Fecha_salida", "Fecha_llegada", "Lugar"
            }
        ));
        tabloncanceladas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tabloncanceladasMouseReleased(evt);
            }
        });
        jScrollPane16.setViewportView(tabloncanceladas);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 1320, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 46, Short.MAX_VALUE))
        );

        solicanceladas.add(jPanel26);
        jPanel26.setBounds(0, 0, 1320, 530);

        menutablones.addTab("Solicitudes Canceladas", new javax.swing.ImageIcon(getClass().getResource("/Iconos/solicitudes.png")), solicanceladas); // NOI18N

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menutablones)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(menutablones, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        tablonsolicitud1.add(jPanel20);
        jPanel20.setBounds(20, 170, 1320, 530);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/banner tablon.png"))); // NOI18N
        tablonsolicitud1.add(jLabel6);
        jLabel6.setBounds(20, 20, 1350, 80);

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/if_view-refresh_118801.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        tablonsolicitud1.add(btnActualizar);
        btnActualizar.setBounds(1060, 120, 120, 30);

        btnbuscarporfecha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnbuscarporfecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/buscar.png"))); // NOI18N
        btnbuscarporfecha.setText("Buscar por fecha");
        btnbuscarporfecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarporfechaActionPerformed(evt);
            }
        });
        tablonsolicitud1.add(btnbuscarporfecha);
        btnbuscarporfecha.setBounds(750, 120, 150, 30);
        tablonsolicitud1.add(fechainicio);
        fechainicio.setBounds(600, 110, 130, 20);
        fechainicio.getDateEditor().addPropertyChangeListener(
            new java.beans.PropertyChangeListener() {
                @Override
                public void propertyChange(java.beans.PropertyChangeEvent e) {
                    if(e.getPropertyName().equals("date")) {
                        fechafinal.getJCalendar().setMinSelectableDate(fechainicio.getDate());
                    }
                }
            });
            Calendar cb1 = new GregorianCalendar();
            fechainicio.setCalendar(cb1);
            tablonsolicitud1.add(fechafinal);
            fechafinal.setBounds(600, 140, 130, 20);
            Calendar cb2 = new GregorianCalendar();
            fechafinal.setCalendar(cb2);

            jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            jLabel4.setText("Fecha final");
            tablonsolicitud1.add(jLabel4);
            jLabel4.setBounds(532, 140, 60, 15);

            jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            jLabel8.setText("Fecha de inicio");
            tablonsolicitud1.add(jLabel8);
            jLabel8.setBounds(510, 110, 80, 15);

            jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/formularios.png"))); // NOI18N
            tablonsolicitud1.add(jLabel28);
            jLabel28.setBounds(0, 0, 1366, 769);

            solicviaticos.addTab("Tablón de Solicitudes", new javax.swing.ImageIcon(getClass().getResource("/Iconos/consumible.png")), tablonsolicitud1); // NOI18N

            informe.setLayout(null);

            jlb.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jlb.setText("Búsqueda:");
            informe.add(jlb);
            jlb.setBounds(40, 125, 100, 22);

            txtbusquedasoli2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            txtbusquedasoli2.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    txtbusquedasoli2KeyPressed(evt);
                }
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    txtbusquedasoli2KeyReleased(evt);
                }
            });
            informe.add(txtbusquedasoli2);
            txtbusquedasoli2.setBounds(140, 120, 290, 30);

            jPanel21.setLayout(null);

            jPanel1.setLayout(null);

            tablainfo.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {

                }
            ));
            tablainfo.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    tablainfoMouseReleased(evt);
                }
            });
            jScrollPane1.setViewportView(tablainfo);

            jPanel1.add(jScrollPane1);
            jScrollPane1.setBounds(0, 0, 1310, 480);

            jLabel1.setText("Reporte de actividaes");
            jPanel1.add(jLabel1);
            jLabel1.setBounds(810, 60, 150, 20);

            lblObsVehiculo.setText("Observaciones Vehículo");
            jPanel1.add(lblObsVehiculo);
            lblObsVehiculo.setBounds(810, 320, 150, 14);
            jPanel1.add(GaTot);
            GaTot.setBounds(210, 440, 240, 20);

            jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel3.setForeground(new java.awt.Color(255, 66, 0));
            jLabel3.setText("Gasto total");
            jPanel1.add(jLabel3);
            jLabel3.setBounds(100, 440, 110, 22);

            btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardarsol.png"))); // NOI18N
            btnguardar.setText("Guardar");
            btnguardar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnguardarActionPerformed(evt);
                }
            });
            jPanel1.add(btnguardar);
            btnguardar.setBounds(1020, 20, 120, 40);

            btnregresar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/atras.png"))); // NOI18N
            btnregresar1.setText("Regresar");
            btnregresar1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnregresar1ActionPerformed(evt);
                }
            });
            jPanel1.add(btnregresar1);
            btnregresar1.setBounds(1180, 20, 100, 40);

            jScrollPane3.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    jScrollPane3MouseReleased(evt);
                }
            });

            tablaact.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {

                }
            ));
            tablaact.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    tablaactFocusGained(evt);
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    tablaactFocusLost(evt);
                }
            });
            tablaact.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    tablaactMouseReleased(evt);
                }
            });
            tablaact.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    tablaactKeyPressed(evt);
                }
            });
            jScrollPane3.setViewportView(tablaact);

            jPanel1.add(jScrollPane3);
            jScrollPane3.setBounds(30, 10, 420, 420);
            jPanel1.add(txtKilometraje);
            txtKilometraje.setBounds(1189, 320, 90, 20);

            txtobvia1.setColumns(20);
            txtobvia1.setRows(5);
            txtobvia1.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    txtobvia1KeyTyped(evt);
                }
            });
            txtobvia.setViewportView(txtobvia1);

            jPanel1.add(txtobvia);
            txtobvia.setBounds(480, 90, 800, 220);

            lblKilometraje.setText("Kilometraje");
            jPanel1.add(lblKilometraje);
            lblKilometraje.setBounds(1114, 320, 60, 14);

            txtobveh1.setColumns(20);
            txtobveh1.setRows(5);
            txtobveh1.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    txtobveh1KeyTyped(evt);
                }
            });
            txtobveh.setViewportView(txtobveh1);

            jPanel1.add(txtobveh);
            txtobveh.setBounds(480, 350, 800, 110);

            menuInforme.addTab("Solicitudes aceptadas", new javax.swing.ImageIcon(getClass().getResource("/Iconos/solicitudes.png")), jPanel1); // NOI18N

            tablainfo1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {

                }
            ));
            tablainfo1.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    tablainfo1MouseReleased(evt);
                }
            });
            jScrollPane2.setViewportView(tablainfo1);

            menuInforme.addTab("Solicitudes finalizadas", new javax.swing.ImageIcon(getClass().getResource("/Iconos/solicitudes.png")), jScrollPane2); // NOI18N

            jPanel21.add(menuInforme);
            menuInforme.setBounds(0, 0, 1310, 520);

            informe.add(jPanel21);
            jPanel21.setBounds(20, 160, 1310, 520);

            btnregresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/atras.png"))); // NOI18N
            btnregresar.setText("Regresar");
            btnregresar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnregresarActionPerformed(evt);
                }
            });
            informe.add(btnregresar);
            btnregresar.setBounds(867, 220, 170, 40);

            lblArea.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            lblArea.setText("Área:");
            informe.add(lblArea);
            lblArea.setBounds(530, 120, 50, 22);

            cmbArea.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            cmbArea.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
            cmbArea.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    cmbAreaActionPerformed(evt);
                }
            });
            informe.add(cmbArea);
            cmbArea.setBounds(590, 120, 250, 20);

            jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/banner informes.png"))); // NOI18N
            informe.add(jLabel7);
            jLabel7.setBounds(20, 20, 1350, 80);

            btnActualizarInforme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/if_view-refresh_118801.png"))); // NOI18N
            btnActualizarInforme.setText("Actualizar");
            btnActualizarInforme.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnActualizarInformeActionPerformed(evt);
                }
            });
            informe.add(btnActualizarInforme);
            btnActualizarInforme.setBounds(1060, 120, 120, 30);

            jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/formularios.png"))); // NOI18N
            informe.add(jLabel24);
            jLabel24.setBounds(0, 0, 1366, 769);

            solicviaticos.addTab("Informe de Actividades", new javax.swing.ImageIcon(getClass().getResource("/Iconos/resguardo.png")), informe); // NOI18N

            jMenu1.setText("Archivo");

            mi_inventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/viatico.png"))); // NOI18N
            mi_inventario.setText("Inventario");
            mi_inventario.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    mi_inventarioActionPerformed(evt);
                }
            });
            jMenu1.add(mi_inventario);

            mi_pases.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
            mi_pases.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pase.png"))); // NOI18N
            mi_pases.setText("Pases E/S");
            mi_pases.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    mi_pasesActionPerformed(evt);
                }
            });
            jMenu1.add(mi_pases);
            jMenu1.add(jSeparator1);

            jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
            jMenuItem2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
            jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/if_Exit_728935.png"))); // NOI18N
            jMenuItem2.setText("Cerrar sesión");
            jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jMenuItem2ActionPerformed(evt);
                }
            });
            jMenu1.add(jMenuItem2);

            itemSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
            itemSalir.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
            itemSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/if_Shutdown_Box_Red_34246.png"))); // NOI18N
            itemSalir.setText("Salir");
            itemSalir.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    itemSalirActionPerformed(evt);
                }
            });
            jMenu1.add(itemSalir);

            jMenuBar1.add(jMenu1);

            setJMenuBar(jMenuBar1);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(solicviaticos, javax.swing.GroupLayout.PREFERRED_SIZE, 1366, javax.swing.GroupLayout.PREFERRED_SIZE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(solicviaticos, javax.swing.GroupLayout.PREFERRED_SIZE, 772, javax.swing.GroupLayout.PREFERRED_SIZE)
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        txtobvia.enable(false);
        txtobveh.enable(false);
        btnregresar.setVisible(false);
        //btnguardar.setVisible(false);
        if (manager_permisos.accesoModulo("consulta", "Solicitud Viaticos", Principal.Username)) {
            tablasolic.setModel(manager_soviaticos.getTasol());
            tablasolicvehiculo.setModel(manager_soviaticos.getTasolVehiculo());
        }
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            tablonpendientes.setModel(manager_soviaticos.SolicitudP());
            tablonaceptadas.setModel(manager_soviaticos.SolicitudA());
            tabloncanceladas.setModel(manager_soviaticos.SolicitudC());
            tablonarchivadas.setModel(manager_soviaticos.SolicitudAr());
        }
        if (manager_permisos.accesoModulo("consulta", "Informe", Principal.Username)) {
            ResultSet usuario;
            try {
                usuario = cbd.getTabla("select puesto from user where id_user='" + Principal.Username + "'", cn);
                usuario.next();
                if (usuario.getString("puesto").equals("SuperUsuario") || usuario.getString("puesto").equals("Administrador")) {
                    superUsuario = true;
                    if (idArea > 0) {
                        Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S inner join Oficio_comision O on idSolicitud=O.solicitud_idSolicitud inner join puestos_trabajo PT on S.puesto=PT.Puesto WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 and PT.id_Area=" + idArea + " order by O.folio desc");
                        SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S inner join Oficio_comision O on S.idSolicitud=O.solicitud_idSolicitud inner join Informe I on S.idSolicitud=I.solicitud_idSolicitud inner join Puestos_trabajo PT on S.puesto=PT.puesto WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 and PT.id_area=" + idArea + " ORDER BY I.Id_Informe DESC");
                    } else {
                        Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 order by O.folio desc");
                        SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S, Oficio_comision O, Informe I WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 ORDER BY I.Id_Informe DESC");
                    }
                } else {
                    usuario = cbd.getTabla("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m) as nombre from user U inner join empleados E on U.id_empleado=E.id_empleado where U.id_user='" + Principal.Username + "';", cn);
                    usuario.next();
                    Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 and S.nombre='" + usuario.getString("nombre") + "' order by O.folio desc");
                    SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S, Oficio_comision O, Informe I WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 and S.nombre='" + usuario.getString("nombre") + "' ORDER BY I.Id_Informe DESC");
                }
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            cmbArea.setVisible(false);
            lblArea.setVisible(false);
        }
    }//GEN-LAST:event_formWindowActivated

    private void btnAddInventario2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddInventario2ActionPerformed
        //addSolicitudViaticos asv = new addSolicitudViaticos(this, true);
        //asv.setVisible(true);
    }//GEN-LAST:event_btnAddInventario2ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        btnregresar1.setVisible(false);
        txtobvia1.setText("");
        txtKilometraje.setVisible(false);
        lblKilometraje.setVisible(false);
        txtobveh1.setText("");
        GaTot.setText("");
        txtobvia.enable(false);
        txtobveh.enable(false);
        txtobvia.setVisible(false);
        txtobveh.setVisible(false);
        btnregresar.setVisible(false);
        btnguardar.setVisible(false);
        tablainfo.enable(true);
        jlb.setVisible(true);
        txtbusquedasoli2.setVisible(true);
        jLabel1.setVisible(false);
        lblObsVehiculo.setVisible(false);
        jLabel3.setVisible(false);
        GaTot.enable(false);
        GaTot.setVisible(false);
        jScrollPane3.setVisible(false);
        jScrollPane1.setVisible(true);
        txtobvia.enable(false);
        txtobveh.enable(false);
        btnregresar.setVisible(false);
        btnguardar.setVisible(false);
        if (manager_permisos.accesoModulo("consulta", "Solicitud Viaticos", Principal.Username)) {
            tablasolic.setModel(manager_soviaticos.getTasol());
            tablasolicvehiculo.setModel(manager_soviaticos.getTasolVehiculo());
        }
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            tablonpendientes.setModel(manager_soviaticos.SolicitudP());
            tablonaceptadas.setModel(manager_soviaticos.SolicitudA());
            tabloncanceladas.setModel(manager_soviaticos.SolicitudC());
            tablonarchivadas.setModel(manager_soviaticos.SolicitudAr());
        }
        if (manager_permisos.accesoModulo("consulta", "Informe", Principal.Username)) {
            ResultSet usuario;
            try {
                usuario = cbd.getTabla("select puesto from user where id_user='" + Principal.Username + "'", cn);
                usuario.next();
                if (usuario.getString("puesto").equals("SuperUsuario") || usuario.getString("puesto").equals("Administrador")) {
                    if (idArea > 0) {
                        Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S inner join Oficio_comision O on idSolicitud=O.solicitud_idSolicitud inner join puestos_trabajo PT on S.puesto=PT.Puesto WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 and PT.id_Area=" + idArea + " order by O.folio desc");
                        SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S inner join Oficio_comision O on S.idSolicitud=O.solicitud_idSolicitud inner join Informe I on S.idSolicitud=I.solicitud_idSolicitud inner join Puestos_trabajo PT on S.puesto=PT.puesto WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 and PT.id_area=" + idArea + " ORDER BY I.Id_Informe DESC");
                    } else {
                        Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 order by O.folio desc");
                        SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S, Oficio_comision O, Informe I WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 ORDER BY I.Id_Informe DESC");
                    }
                    cmbArea.setVisible(true);
                    lblArea.setVisible(true);
                } else {
                    usuario = cbd.getTabla("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m) as nombre from user U inner join empleados E on U.id_empleado=E.id_empleado where U.id_user='" + Principal.Username + "';", cn);
                    usuario.next();
                    Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 and S.nombre='" + usuario.getString("nombre") + "' order by O.folio desc");
                    SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S, Oficio_comision O, Informe I WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 and S.nombre='" + usuario.getString("nombre") + "' ORDER BY I.Id_Informe DESC");
                    cmbArea.setVisible(false);
                    lblArea.setVisible(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            cmbArea.setVisible(false);
            lblArea.setVisible(false);
        }
    }//GEN-LAST:event_formWindowOpened

    private void Impri_SolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Impri_SolActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Solicitud Viaticos", Principal.Username)) {
            int fila = tablasolicvehiculo.getSelectedRow();
            limpiar = false;
            String id = null;
            try {

                tablasolicvehiculo.clearSelection();
                if (fila >= 0) {
                    id = tablasolicvehiculo.getValueAt(fila, 0).toString();
                    CrearSolicitudViatico csv = new CrearSolicitudViatico(id);
                    List<String> datos = cbd.acceder("select fecha_salida,Fecha_llegada,Nombre,lugar,actividad,Pernoctado from solicitud_viatico where idSolicitud=" + id + ";");
                    List<String> vehiculo = cbd.acceder("select SV.Vehiculo,VU.vehiculos_Matricula from solicitud_viatico S inner join vehiculo_viatico VV on S.idSolicitud=VV.solicitud_viatico_idSolicitud inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo inner join vehiculo_usado VU on SV.vehiculo_usado_idvehiculo_usado=VU.idvehiculo_usado where S.idSolicitud=" + id + ";");
                    List<String> responsable = cbd.acceder("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m),PT.Puesto from empleados E inner join area A on E.id_empleado=A.Responsable inner join puestos_trabajo PT on E.puesto=PT.ID_Puesto where A.ID_Area=3;");
                    if (vehiculo.size() < 1) {
                        csv.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), "", responsable.get(0), responsable.get(1));
                    } else {
                        csv.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), vehiculo.get(0) + "-" + vehiculo.get(1), responsable.get(0), responsable.get(1));
                    }
                }
            } catch (Exception e) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para imprimir solicitudes de viáticos.");
        }
    }//GEN-LAST:event_Impri_SolActionPerformed

    private void ConsultarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultarPActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            if (c == 0) {
                c = 1;
            } else {
                s.setVisible(false);
                c = 1;
            }
            int k = tablonpendientes.getSelectedRow();
            if (k >= 0) {
                int id = Integer.parseInt(tablonpendientes.getValueAt(k, 0).toString());
                s = new visSolicitudViaticos(this, true);
                s.setLocationRelativeTo(null);
                s.IdUsuario(id, 0, 0);
                s.setVisible(true);
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para consultar las solicitudes.");
        }
    }//GEN-LAST:event_ConsultarPActionPerformed

    private void ConsultarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultarAActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            if (c == 0) {
                c = 1;
            } else {
                s.setVisible(false);
                c = 1;
            }
            int i = tablonaceptadas.getSelectedRow();
            if (i >= 0) {
                String folio = tablonaceptadas.getValueAt(i, 0).toString();
                String idSolicitud = "";
                try {
                    Statement sentencia = cn.createStatement();
                    ResultSet rs = cbd.getTabla("SELECT Solicitud_idSolicitud FROM Oficio_comision WHERE Folio = '" + folio + "'", cn);
                    rs.next();
                    idSolicitud = rs.getString("Solicitud_idSolicitud");

                    s = new visSolicitudViaticos(this, true);
                    s.setLocationRelativeTo(null);
                    s.IdUsuario(Integer.parseInt(idSolicitud), 1, 1);
                    sentencia.close();
                } catch (SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

                }
                /*catch (ClassNotFoundException e) {
                 e.printStackTrace();
                 }//fin del catch*/

                s.setVisible(true);
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para consultar las solicitudes.");
        }
    }//GEN-LAST:event_ConsultarAActionPerformed

    private void ConsultarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultarCActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            if (c == 0) {
                c = 1;
            } else {
                s.setVisible(false);
                c = 1;
            }
            int k = tabloncanceladas.getSelectedRow();
            if (k >= 0) {
                int id = Integer.parseInt(tabloncanceladas.getValueAt(k, 0).toString());
                s = new visSolicitudViaticos(this, true);
                s.setLocationRelativeTo(null);
                s.IdUsuario(id, 0, 2);
                s.setVisible(true);
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para consultar las solicitudes.");
        }
    }//GEN-LAST:event_ConsultarCActionPerformed

    private void OficioComisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OficioComisionActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            int i = tablonaceptadas.getSelectedRow();
            if (i >= 0) {
                String folio = tablonaceptadas.getValueAt(i, 0).toString();
                /*try {
                 pdf.oficio_comision(folio);
                    
                 } catch (DocumentException ex) {
                 Logger.getLogger(PrincipalS.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (SQLException ex) {
                 Logger.getLogger(PrincipalS.class.getName()).log(Level.SEVERE, null, ex);
                 }*/
                CrearOficioComision coc = new CrearOficioComision(folio);
                try {

                    List<String> datos = cbd.acceder("select O.Folio,S.Nombre,S.Puesto,S.Lugar,S.Fecha_salida,S.Fecha_llegada,S.Actividad from solicitud_viatico S inner join oficio_comision O on S.idSolicitud=O.Solicitud_idSolicitud where O.Folio=" + folio + ";");
                    List<String> vehiculo = cbd.acceder("select SV.Vehiculo,VU.vehiculos_Matricula from solicitud_viatico S inner join oficio_comision O on S.idSolicitud=O.Solicitud_idSolicitud inner join vehiculo_viatico VV on S.idSolicitud=VV.solicitud_viatico_idSolicitud inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo inner join vehiculo_usado VU on SV.vehiculo_usado_idvehiculo_usado=VU.idvehiculo_usado where O.folio=" + folio + ";");
                    List<String> responsable = cbd.acceder("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m),PT.Puesto from empleados E inner join area A on E.id_empleado=A.Responsable inner join puestos_trabajo PT on E.puesto=PT.ID_Puesto where A.ID_Area=4;");
                    if (vehiculo.size() < 1) {
                        coc.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), datos.get(6), "", responsable.get(0), responsable.get(1));
                    } else {
                        coc.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), datos.get(6), vehiculo.get(0) + "-" + vehiculo.get(1), responsable.get(0), responsable.get(1));
                    }
                } catch (DocumentException ex) {
                    Logger.getLogger(PrincipalS.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para imprimir las solicitudes.");
        }
    }//GEN-LAST:event_OficioComisionActionPerformed

    private void AsignarMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AsignarMontoActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("actualizar", "Tablon Solicitudes", Principal.Username)) {
            int k = tablonaceptadas.getSelectedRow();
            if (k >= 0) {
                String folio = tablonaceptadas.getValueAt(k, 0).toString();
                String idSolicitud = "";
                try {

                    Statement sentencia = cn.createStatement();
                    String valor = javax.swing.JOptionPane.showInputDialog("Asignar monto");

                    if (valor == null) {

                    } else {
                        float monto = Float.parseFloat(valor);
                        if (monto < 0) {
                            javax.swing.JOptionPane.showMessageDialog(null, "Monto no valido");
                        } else {
                            sentencia.executeUpdate("UPDATE Oficio_comision SET Monto = " + monto + "WHERE(Folio =" + folio + ")");
                            //sentencia.executeUpdate("UPDATE solicitud_viatico SET Estado = 'C' WHERE (idSolicitud = '" + id + "')");
                            javax.swing.JOptionPane.showMessageDialog(null, "Monto Asignado");
//                            ResultSet rs = cbd.getTabla("SELECT Solicitud_idSolicitud FROM Oficio_comision WHERE Folio = '" + folio + "'",cn);
//                            while (rs.next()) {
//                                idSolicitud = rs.getString("Solicitud_idSolicitud");
//                            }
//                            sentencia.executeUpdate("UPDATE Solicitud_viatico SET Estado = 'AR', gastos_comprobar = 'false' WHERE (idSolicitud = " + idSolicitud + ")");
//                            javax.swing.JOptionPane.showMessageDialog(null, "Solicitud archivada");
                        }
                    }
                    sentencia.close();
                } catch (SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

                } catch (NumberFormatException exp) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Ingresar solo números");
                }//fin del catch

            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
            tablonarchivadas.setModel(manager_soviaticos.SolicitudAr());
            tablonaceptadas.setModel(manager_soviaticos.SolicitudA());
            Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0");
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para modificar el monto.");
        }
    }//GEN-LAST:event_AsignarMontoActionPerformed

    private void AceptarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarPActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("actualizar", "Tablon Solicitudes", Principal.Username)) {
            int k = tablonpendientes.getSelectedRow();
            int id = -1;
            if (k >= 0) {
                id = Integer.parseInt(tablonpendientes.getValueAt(k, 0).toString());
                Calendar calendar = Calendar.getInstance();
                try {
                    String total = "";
                    Statement sentencia = cn.createStatement();
                    ResultSet rs0 = cbd.getTabla("SELECT COUNT(*) as Folio FROM Oficio_comision", cn);
                    while (rs0.next()) {
                        total = rs0.getString("Folio");
                    }
                    int total1 = Integer.parseInt(total);
                    int folio = 0;
                    String valor = "";
                    if (total1 != 0) {
                        ResultSet rs = cbd.getTabla("SELECT MAX(Folio) AS Folio FROM Oficio_comision", cn);
                        while (rs.next()) {
                            valor = rs.getString("Folio");
                        }
                        int an = Integer.parseInt(valor.substring(0, 4));
                        if (an == calendar.get(Calendar.YEAR)) {
                            valor = valor.substring(4);
                            folio = Integer.parseInt(valor) + 1;
                            valor = an + "" + folio;
                            folio = Integer.parseInt(valor);
                        } else {
                            valor = calendar.get(Calendar.YEAR) + "1";
                            folio = Integer.parseInt(valor);
                        }
                    } else {
                        valor = calendar.get(Calendar.YEAR) + "1";
                        folio = Integer.parseInt(valor);
                    }
                    String estadolocalidad = "";
                    String nombre = "";
                    String fecha_salida = "";
                    String fecha_llegada = "";
                    ResultSet rs1 = cbd.getTabla("SELECT Fecha_salida,Lugar,Nombre,Fecha_llegada FROM Solicitud_viatico WHERE (idSolicitud = '" + id + "')", cn);
                    while (rs1.next()) {
                        fecha_salida = rs1.getString("Fecha_salida");
                        estadolocalidad = rs1.getString("Lugar");
                        nombre = rs1.getString("Nombre");
                        fecha_llegada = rs1.getString("Fecha_llegada");
                    }
                    final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
                    int año = Integer.parseInt(fecha_salida.substring(0, 4));
                    int mes = Integer.parseInt(fecha_salida.substring(5, 7));
                    int dia = Integer.parseInt(fecha_salida.substring(8, 10));
                    Calendar calendar2 = new GregorianCalendar(año, mes - 1, dia);
                    java.sql.Date fecha_s = new java.sql.Date(calendar2.getTimeInMillis());
                    año = Integer.parseInt(fecha_llegada.substring(0, 4));
                    mes = Integer.parseInt(fecha_llegada.substring(5, 7));
                    dia = Integer.parseInt(fecha_llegada.substring(8, 10));
                    Calendar calendar3 = new GregorianCalendar(año, mes - 1, dia);
                    java.sql.Date fecha_ll = new java.sql.Date(calendar3.getTimeInMillis());
                    long dias = (fecha_ll.getTime() - fecha_s.getTime()) / MILLSECS_PER_DAY;

                    String et[] = new String[2];
                    et = estadolocalidad.split(",");
                    String empleado[] = new String[4];
                    empleado = nombre.split(" ");
                    //String nombres = empleado[0] + " " + empleado[1];
                    String puesto = "";
                    ResultSet rs2 = cbd.getTabla("SELECT puesto FROM Empleados E WHERE concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m) =  '" + nombre + "'", cn);
                    while (rs2.next()) {
                        puesto = rs2.getString("puesto");
                    }
                    String tarifa = "";
                    float tarif = 0;
                    int estadoFuera=estadolocalidad.split("-").length;
                    if (estadoFuera > 1) {
                        if (dias == 0) {
                            ResultSet rs3 = cbd.getTabla("SELECT SinPernoctarFDE FROM Puestos_Trabajo WHERE ID_Puesto = '" + puesto + "'", cn);
                            while (rs3.next()) {
                                tarifa = rs3.getString("SinPernoctarFDE");
                            }
                        } else {
                            ResultSet rs3 = cbd.getTabla("SELECT PernoctandoFDE FROM Puestos_Trabajo WHERE ID_Puesto = '" + puesto + "'", cn);
                            while (rs3.next()) {
                                tarifa = rs3.getString("PernoctandoFDE");
                            }
                            tarif = Float.parseFloat(tarifa);
                            tarif = (tarif * dias) + tarif;
                            tarifa = tarif + "";
                        }
                    } else {
                        if (et[0].equals("Nayarit")) {
                            float tarifMayor = 0;
                            for (int i = 1; i < et.length; i++) {
                                if (et[i].equals("Bahía de Banderas")) {
                                    if (dias == 0) {
                                        ResultSet rs3 = sentencia.executeQuery("SELECT SinPernoctarBDB FROM Puestos_Trabajo WHERE ID_Puesto = '" + puesto + "'");
                                        while (rs3.next()) {
                                            tarifa = rs3.getString("SinPernoctarBDB");
                                        }
                                        tarif = Float.parseFloat(tarifa);
                                        if (tarif > tarifMayor) {
                                            tarifMayor = tarif;
                                            tarifa = tarifMayor + "";
                                        }
                                    } else {
                                        ResultSet rs3 = sentencia.executeQuery("SELECT PernoctandoBDB FROM Puestos_Trabajo WHERE ID_Puesto = '" + puesto + "'");
                                        while (rs3.next()) {
                                            tarifa = rs3.getString("PernoctandoBDB");
                                        }
                                        tarif = Float.parseFloat(tarifa);
                                        tarif = (tarif * dias) + tarif;
                                        if (tarif > tarifMayor) {
                                            tarifMayor = tarif;
                                            tarifa = tarifMayor + "";
                                        }
                                    }
                                } else {
                                    if (et[i].equals("Tepic") || et[i].equals("Xalisco")) {
                                        tarifa = "0.00";
                                        tarif = Float.parseFloat(tarifa);
                                        if (tarif > tarifMayor) {
                                            tarifMayor = tarif;
                                            tarifa = tarifMayor + "";
                                        }
                                    } else {
                                        if (et[i].equals("Acaponeta") || et[i].equals("Amatlán de Cañas") || et[i].equals("El Nayar") || et[i].equals("Huajicori") || et[i].equals("La Yesca") || et[i].equals("Tecuala")) {
                                            if (dias == 0) {
                                                ResultSet rs3 = sentencia.executeQuery("SELECT  SinPernoctar100 FROM Puestos_Trabajo WHERE ID_Puesto = '" + puesto + "'");
                                                while (rs3.next()) {
                                                    tarifa = rs3.getString("SinPernoctar100");
                                                }
                                                tarif = Float.parseFloat(tarifa);
                                                if (tarif > tarifMayor) {
                                                    tarifMayor = tarif;
                                                    tarifa = tarifMayor + "";
                                                }
                                            } else {
                                                ResultSet rs3 = sentencia.executeQuery("SELECT Pernoctando100 FROM Puestos_Trabajo WHERE ID_Puesto = '" + puesto + "'");
                                                while (rs3.next()) {
                                                    tarifa = rs3.getString("Pernoctando100");
                                                }
                                                tarif = Float.parseFloat(tarifa);
                                                tarif = (tarif * dias) + tarif;
                                                if (tarif > tarifMayor) {
                                                    tarifMayor = tarif;
                                                    tarifa = tarifMayor + "";
                                                }
                                            }
                                        } else {
                                            if (dias == 0) {
                                                ResultSet rs3 = sentencia.executeQuery("SELECT  SinPernoctar30100 FROM Puestos_Trabajo WHERE ID_Puesto = '" + puesto + "'");
                                                while (rs3.next()) {
                                                    tarifa = rs3.getString("SinPernoctar30100");
                                                }
                                                tarif = Float.parseFloat(tarifa);
                                                if (tarif > tarifMayor) {
                                                    tarifMayor = tarif;
                                                    tarifa = tarifMayor + "";
                                                }
                                            } else {
                                                ResultSet rs3 = sentencia.executeQuery("SELECT Pernoctando30100 FROM Puestos_Trabajo WHERE ID_Puesto = '" + puesto + "'");
                                                while (rs3.next()) {
                                                    tarifa = rs3.getString("Pernoctando30100");
                                                }
                                                tarif = Float.parseFloat(tarifa);
                                                tarif = (tarif * dias) + tarif;
                                                if (tarif > tarifMayor) {
                                                    tarifMayor = tarif;
                                                    tarifa = tarifMayor + "";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (dias == 0) {
                                ResultSet rs3 = cbd.getTabla("SELECT SinPernoctarFDE FROM Puestos_Trabajo WHERE ID_Puesto = '" + puesto + "'", cn);
                                while (rs3.next()) {
                                    tarifa = rs3.getString("SinPernoctarFDE");
                                }
                            } else {
                                ResultSet rs3 = cbd.getTabla("SELECT PernoctandoFDE FROM Puestos_Trabajo WHERE ID_Puesto = '" + puesto + "'", cn);
                                while (rs3.next()) {
                                    tarifa = rs3.getString("PernoctandoFDE");
                                }
                                tarif = Float.parseFloat(tarifa);
                                tarif = (tarif * dias) + tarif;
                                tarifa = tarif + "";
                            }
                        }
                    }
                    sentencia.execute("INSERT INTO Oficio_comision VALUES(" + folio + "," + id + "," + tarifa + ")");
                    sentencia.executeUpdate("UPDATE Solicitud_viatico SET Estado = 'A' WHERE (idSolicitud = '" + id + "')");
                    javax.swing.JOptionPane.showMessageDialog(null, "Solicitud aceptada");
                    sentencia.close();
                } catch (SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta o folio ya asignado");
                }/*catch (ClassNotFoundException e) {
                 e.printStackTrace();
                 }*/ //fin del catch

            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
            List<String> datos = cbd.acceder("select * from vehiculo_viatico VV inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo where VV.solicitud_viatico_idSolicitud=" + id + " and SV.viatico_vehiculo=0;");
            if (datos.size() > 0) {
                cbd.ejecutar("update oficio_comision set monto=0 where Solicitud_idSolicitud=" + id + ";");
            }
            tablonpendientes.setModel(manager_soviaticos.SolicitudP());
            tablonaceptadas.setModel(manager_soviaticos.SolicitudA());
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para aceptar solicitudes.");
        }
    }//GEN-LAST:event_AceptarPActionPerformed

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("alta", "Solicitud Viaticos", Principal.Username)) {
            addSolicitudViaticos asv = new addSolicitudViaticos(this, true);
            asv.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar solicitudes de viáticos.");
        }
    }//GEN-LAST:event_AddActionPerformed

    private void Add1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add1ActionPerformed
        // TODO add your handling code here:

        if (manager_permisos.accesoModulo("alta", "Solicitud Viaticos", Principal.Username)) {
            addSolicitudViaticos asv = new addSolicitudViaticos(this, true);
            conVehiculo = 0;
            asv.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar solicitudes de viáticos.");
        }
    }//GEN-LAST:event_Add1ActionPerformed

    private void CambiarConsejeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CambiarConsejeroActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("actualizar", "Solicitud Viaticos", Principal.Username)) {
            String nuevo = JOptionPane.showInputDialog("Inserte el nombre del nuevo director general");
            cbd.ejecutar("update Director_General set Nombre='" + nuevo + "'");
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para modificar el nombre del consejero presidente.");
        }
    }//GEN-LAST:event_CambiarConsejeroActionPerformed

    private void SolicitarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SolicitarVehiculoActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("alta", "Solicitud Viaticos", Principal.Username)) {
            addSolicitudViaticos asv = new addSolicitudViaticos(this, true);
            conVehiculo = 1;
            viatico_vehiculo = 0;
            asv.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar solicitudes de vehículos.");
        }
    }//GEN-LAST:event_SolicitarVehiculoActionPerformed

    private void SolicitarVehiculo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SolicitarVehiculo1ActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("alta", "Solicitud Viaticos", Principal.Username)) {
            addSolicitudViaticos asv = new addSolicitudViaticos(this, true);
            conVehiculo = 1;
            viatico_vehiculo = 0;
            asv.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar solicitudes de vehículos.");
        }
    }//GEN-LAST:event_SolicitarVehiculo1ActionPerformed

    private void tablonaceptadasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablonaceptadasMouseReleased
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt)) {
            int r = tablonaceptadas.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablonaceptadas.getRowCount()) {
                tablonaceptadas.setRowSelectionInterval(r, r);
            }
            MenuTablonA.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tablonaceptadasMouseReleased

    private void tablonpendientesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablonpendientesMouseReleased
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt)) {
            int r = tablonpendientes.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablonpendientes.getRowCount()) {
                tablonpendientes.setRowSelectionInterval(r, r);
            }
            MenuTablonP.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tablonpendientesMouseReleased

    private void btnregresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregresarActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnregresarActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        //Validación de campos
        String cad = "";
        if (tablaact.isEnabled() && tablaact.getRowCount() == 0) {
            cad = "Falta insertar la comprobación de gastos";
            JOptionPane.showMessageDialog(this, cad);
            return;
        }
        //--------------------
        try {
            String idInforme = "";
            int s = JOptionPane.showConfirmDialog(null, "¿Esta seguro?", "Alerta!", JOptionPane.YES_NO_OPTION);
            if (s == JOptionPane.YES_OPTION) {
                String id = "";
                Statement sentencia = cn.createStatement();
                ResultSet rs = cbd.getTabla("SELECT Solicitud_idSolicitud FROM Oficio_comision WHERE Folio = " + folio, cn);
                while (rs.next()) {
                    id = rs.getString("Solicitud_idSolicitud");
                }
                if (!txtKilometraje.getText().equals("") && txtKilometraje.getText() != null) {
                    rs = cbd.getTabla("select * from vehiculo_viatico VV inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo inner join vehiculo_usado VU on SV.vehiculo_usado_idvehiculo_usado=VU.idvehiculo_usado where VV.solicitud_viatico_idSolicitud=" + id, cn);
                    rs.next();
                    String idVehiculo_usado = rs.getString("idVehiculo_usado");
                    String matricula = rs.getString("vehiculos_matricula");
                    int kilometrajeActual = Integer.parseInt(rs.getString("kilometraje"));
                    int kilometrajeActualizado = Integer.parseInt(txtKilometraje.getText());
                    if (kilometrajeActualizado < kilometrajeActual) {
                        JOptionPane.showMessageDialog(this, "El kilometraje debe de ser mayor al kilometraje actual de: " + kilometrajeActual);
                        return;
                    }
                    sentencia.executeUpdate("UPDATE vehiculo_usado SET kilometraje='" + kilometrajeActualizado + "' where idVehiculo_usado=" + idVehiculo_usado);
                    rs = cbd.getTabla("select * from vehiculos where matricula='" + matricula + "'", cn);
                    rs.next();
                    String observaciones = rs.getString("Observaciones") + "\n------------------\n" + txtobveh1.getText();
                    sentencia.executeUpdate("UPDATE vehiculos SET kilometraje='" + kilometrajeActualizado + "' where matricula='" + matricula + "'");
                    sentencia.executeUpdate("UPDATE vehiculos SET observaciones='" + observaciones + "' where matricula='" + matricula + "'");

                }
                sentencia.executeUpdate("UPDATE Solicitud_viatico SET Reporte = '1' WHERE (idSolicitud = " + id + ")");
                if (c == 1) {
                    sentencia.execute("INSERT INTO Informe (Observaciones,Observaciones_Vehiculo,Solicitud_idSolicitud,importe_total) VALUES('" + txtobvia1.getText() + "','" + txtobveh1.getText() + "'," + id + "," + GaTot.getText() + ")");
                    //-----------------------------
                    ResultSet rs2 = cbd.getTabla("SELECT MAX(id_informe) AS id_informe FROM Informe", cn);
                    while (rs2.next()) {
                        idInforme = rs2.getString("id_informe");
                    }
                    //-----------------------------
                    String idGastos = "";
                    int filas = tablaact.getRowCount();
                    if (filas != 0) {
                        for (int j = 0; filas > j; j++) {
                            String factura = "";
                            if (tablaact.getValueAt(j, 2).toString().equals("") || tablaact.getValueAt(j, 2).toString() == null) {
                                factura = "-";
                            } else {
                                factura = tablaact.getValueAt(j, 2).toString();
                            }
                            sentencia.execute("INSERT INTO Gastos (Precio,Descripcion,Factura) VALUES('" + tablaact.getValueAt(j, 1).toString() + "','" + tablaact.getValueAt(j, 0).toString() + "','" + factura + "')");
                            ResultSet rs3 = cbd.getTabla("SELECT MAX(id_Gastos) AS id_Gastos FROM Gastos", cn);
                            while (rs3.next()) {
                                idGastos = rs3.getString("id_Gastos");
                            }
                            sentencia.execute("INSERT INTO Informe_Gastos VALUES(" + idGastos + "," + idInforme + ")");
                        }
                    }
                    javax.swing.JOptionPane.showMessageDialog(null, "Reporte Generado");
                    /////////////////////
                    btnregresar1.setVisible(false);
                    txtobvia1.setText("");
                    txtobveh1.setText("");
                    GaTot.setText("");
                    txtobvia.enable(false);
                    txtobveh.enable(false);
                    txtobvia.setVisible(false);
                    txtobveh.setVisible(false);
                    btnregresar.setVisible(false);
                    btnguardar.setVisible(false);
                    tablainfo.enable(true);
                    jlb.setVisible(true);
                    txtbusquedasoli2.setVisible(true);
                    jLabel1.setVisible(false);
                    lblObsVehiculo.setVisible(false);
                    jLabel3.setVisible(false);
                    GaTot.enable(false);
                    btnActualizarInforme.setVisible(true);
                    GaTot.setVisible(false);
                    jScrollPane3.setVisible(false);
                    jScrollPane1.setVisible(true);
                    lblKilometraje.setVisible(false);
                    txtKilometraje.setVisible(false);
                    if (superUsuario) {
                        cmbArea.setVisible(true);
                        lblArea.setVisible(true);
                    }
                    ////////////////////
                } else {
                    sentencia.execute("INSERT INTO Informe (Observaciones,Observaciones_Vehiculo,Solicitud_idSolicitud) VALUES('" + txtobvia1.getText() + "','" + txtobveh1.getText() + "'," + id + ")");
                    javax.swing.JOptionPane.showMessageDialog(null, "Reporte Generado");
                    /////////////////////
                    btnregresar1.setVisible(false);
                    txtobvia1.setText("");
                    txtobveh1.setText("");
                    GaTot.setText("");
                    txtobvia.enable(false);
                    txtobveh.enable(false);
                    txtobvia.setVisible(false);
                    txtobveh.setVisible(false);
                    btnregresar.setVisible(false);
                    btnguardar.setVisible(false);
                    tablainfo.enable(true);
                    jlb.setVisible(true);
                    txtbusquedasoli2.setVisible(true);
                    jLabel1.setVisible(false);
                    lblObsVehiculo.setVisible(false);
                    jLabel3.setVisible(false);
                    GaTot.enable(false);
                    GaTot.setVisible(false);
                    jScrollPane3.setVisible(false);
                    jScrollPane1.setVisible(true);
                    lblKilometraje.setVisible(false);
                    txtKilometraje.setVisible(false);
                    ////////////////////
                }
                if (c == 1) {
                    txtobvia.enable(false);
                    txtobveh.enable(false);
                    tablaact.enable(false);
                    GaTot.enable(false);
                } else {
                    txtobvia.enable(false);
                    txtobveh.enable(false);
                }
                int imprimir = JOptionPane.showConfirmDialog(null, "¿Desea imprimir informe?");
                if (imprimir == JOptionPane.YES_OPTION) {

                    ResultSet rs2 = cbd.getTabla("SELECT MAX(id_informe) AS id_informe FROM Informe", cn);
                    while (rs2.next()) {
                        idInforme = rs2.getString("id_informe");
                    }
                    List<Gastos_Comprobar> gastos = new ArrayList<Gastos_Comprobar>();
                    CrearReporteActividades cra = new CrearReporteActividades(idInforme);
                    List<String> datos = cbd.acceder("select O.Folio,S.Lugar,S.Actividad,I.Observaciones,S.Nombre,S.Puesto from solicitud_viatico S inner join oficio_comision O on S.idSolicitud=O.Solicitud_idSolicitud inner join informe I on S.idSolicitud=I.Solicitud_idSolicitud where I.Id_Informe=" + idInforme + ";");
                    List<String> vehiculo = cbd.acceder("select SV.Vehiculo,VU.vehiculos_Matricula from solicitud_viatico S inner join oficio_comision O on S.idSolicitud=O.Solicitud_idSolicitud inner join vehiculo_viatico VV on S.idSolicitud=VV.solicitud_viatico_idSolicitud inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo inner join vehiculo_usado VU on SV.vehiculo_usado_idvehiculo_usado=VU.idvehiculo_usado where O.folio=" + folio + ";");
                    List<String> indices = cbd.acceder("select IG.Gastos_id_gastos from informe_gastos IG inner join informe I on IG.Informe_id_informe=I.id_informe where I.id_informe=" + idInforme);
                    if (indices.size() > 0) {
                        String query = "select precio,descripcion,factura from gastos where ";
                        for (int i = 0; i < indices.size(); i++) {
                            if (i == 0) {
                                query += "id_gastos=" + indices.get(i);
                            } else {
                                query += " or id_gastos=" + indices.get(i);
                            }
                        }
                        List<String> gastos_query = cbd.acceder(query);

                        for (int i = 0; i < gastos_query.size(); i += 3) {
                            gastos.add(new Gastos_Comprobar(gastos_query.get(i), gastos_query.get(i + 1), gastos_query.get(i + 2)));
                            //JOptionPane.showMessageDialog(this, gastos_query.get(i)+"-"+gastos_query.get(i+1)+"-"+gastos_query.get(i+2));
                        }
                    }
                    if (vehiculo.size() < 1) {
                        cra.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), gastos);
                    } else {
                        cra.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), gastos);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al generar reporte");
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Kilometraje debe ser un número positivo mayor al kilometraje actual");
        } catch (DocumentException ex) {
            Logger.getLogger(PrincipalS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnguardarActionPerformed

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Iconos/IEE.png"));

        return retValue;
    }
    private void txtbusquedasoli2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbusquedasoli2KeyReleased
        // TODO add your handling code here:
        String soloUsuarioActual = "";
        String buscarArea = "";
        if (cmbArea.getSelectedIndex() > 0) {
            buscarArea = "and PT.id_Area=" + cmbArea.getSelectedIndex();
        }
        if (menuInforme.getSelectedIndex() == 0) {
            modelo = new DefaultTableModel() {
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            modelo.addColumn("Folio");
            modelo.addColumn("Nombre");
            modelo.addColumn("Actividad");
            modelo.addColumn("Lugar");
            modelo.addColumn("Monto");
            this.tablainfo.setModel(modelo);
            try {

                ResultSet usuario;
                usuario = cbd.getTabla("select puesto from user where id_user='" + Principal.Username + "'", cn);
                usuario.next();
                if (usuario.getString("puesto").equals("SuperUsuario") || usuario.getString("puesto").equals("Administrador")) {
                    soloUsuarioActual = "";
                } else {
                    usuario = cbd.getTabla("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m) as nombre from user U inner join empleados E on U.id_empleado=E.id_empleado where U.id_user='" + Principal.Username + "';", cn);
                    usuario.next();
                    soloUsuarioActual = " and nombre='" + usuario.getString("nombre") + "'";
                }
                Statement sentencia = cn.createStatement();
                ResultSet rs = cbd.getTabla("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S inner join Oficio_comision O on S.idSolicitud=O.Solicitud_idSolicitud inner join puestos_trabajo PT on S.puesto=PT.Puesto WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 AND (O.Folio LIKE '%" + txtbusquedasoli2.getText() + "%'"
                        + "OR S.Nombre LIKE '%" + txtbusquedasoli2.getText() + "%' OR S.Actividad LIKE '%" + txtbusquedasoli2.getText() + "%' OR S.Lugar LIKE '%" + txtbusquedasoli2.getText() + "%'OR O.Monto LIKE '%" + txtbusquedasoli2.getText() + "%') " + soloUsuarioActual + " " + buscarArea + " order by idSolicitud desc", cn);

                String solicitud[] = new String[5];
                while (rs.next()) {
                    solicitud[0] = rs.getString("Folio");
                    solicitud[1] = rs.getString("Nombre");
                    solicitud[2] = rs.getString("Actividad");
                    solicitud[3] = rs.getString("Lugar");
                    solicitud[4] = rs.getString("Monto");
                    modelo.addRow(solicitud);
                }
                sentencia.close();
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

            }
        }
        if (menuInforme.getSelectedIndex() == 1) {
            modelo = new DefaultTableModel() {
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            modelo.addColumn("ID Informe");
            modelo.addColumn("Folio");
            modelo.addColumn("Nombre");
            modelo.addColumn("Monto");
            modelo.addColumn("Importe total");
            this.tablainfo1.setModel(modelo);
            try {
                Statement sentencia = cn.createStatement();
                ResultSet rs = cbd.getTabla("SELECT I.Id_Informe, O.Folio, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S inner join Oficio_comision O on S.idSolicitud=O.Solicitud_idSolicitud inner join informe I on S.idSolicitud=I.Solicitud_idSolicitud inner join puestos_trabajo PT on S.puesto=PT.Puesto WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 AND (I.Id_Informe LIKE '%" + txtbusquedasoli2.getText()
                        + "%' OR O.Folio LIKE '%" + txtbusquedasoli2.getText() + "%' OR S.Nombre LIKE '%" + txtbusquedasoli2.getText() + "%' OR O.Monto LIKE '%" + txtbusquedasoli2.getText() + "%' OR I.importe_total LIKE '%" + txtbusquedasoli2.getText() + "%')" + soloUsuarioActual + " " + buscarArea + " ORDER BY I.Id_Informe DESC", cn);

                String solicitud[] = new String[5];
                while (rs.next()) {
                    solicitud[0] = rs.getString("Id_Informe");
                    solicitud[1] = rs.getString("Folio");
                    solicitud[2] = rs.getString("Nombre");
                    solicitud[3] = rs.getString("Monto");
                    solicitud[4] = rs.getString("importe_total");
                    modelo.addRow(solicitud);

                }
                sentencia.close();
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

            }
        }
    }//GEN-LAST:event_txtbusquedasoli2KeyReleased

    private void txtbusquedasoli2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbusquedasoli2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbusquedasoli2KeyPressed

    private void CancelarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarPActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("actualizar", "Tablon Solicitudes", Principal.Username)) {
            int i = tablonpendientes.getSelectedRow();
            if (i >= 0) {
                String id = tablonpendientes.getValueAt(i, 0).toString();
                String motivo = javax.swing.JOptionPane.showInputDialog("Motivo");
                if (motivo == null) {
                } else {
                    try {
                        Statement sentencia = cn.createStatement();
                        sentencia.executeUpdate("UPDATE Solicitud_viatico SET Estado = 'C', Motivo= '" + motivo + "' WHERE (idSolicitud = '" + id + "')");
                        javax.swing.JOptionPane.showMessageDialog(null, "Solicitud cancelada");
                        sentencia.close();
                    } catch (SQLException ex) {
                        javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

                    }
                    /*catch (ClassNotFoundException e) {
                     e.printStackTrace();
                     }//fin del catch
                     */

                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
            tabloncanceladas.setModel(manager_soviaticos.SolicitudC());
            tablonpendientes.setModel(manager_soviaticos.SolicitudP());
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para cancelar solicitudes.");
        }
    }//GEN-LAST:event_CancelarPActionPerformed

    private void tablonsolicitud1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablonsolicitud1MouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_tablonsolicitud1MouseReleased

    private void solipendientesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_solipendientesMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_solipendientesMouseReleased

    private void soliaceptadasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_soliaceptadasMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_soliaceptadasMouseReleased

    private void solicarchivadasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_solicarchivadasMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_solicarchivadasMouseReleased

    private void txtbusquedasoli1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbusquedasoli1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbusquedasoli1KeyPressed

    private void txtbusquedasoli1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbusquedasoli1KeyReleased
        // TODO add your handling code here:
        String soloUsuarioActual = "";
        String queryFecha="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fecha_inicio = sdf.format(fechainicio.getDate().getTime());
            String fecha_final = sdf.format(fechafinal.getDate().getTime());
            if(buscarFecha){
                queryFecha=" and (Fecha_salida >= '" + fecha_inicio + "' AND Fecha_llegada <= '" + fecha_final + "') ";
            }
        if (menutablones.getSelectedIndex() == 0) {
            modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            modelo.addColumn("ID");
            modelo.addColumn("Tipo de solicitud");
            modelo.addColumn("Nombre");
            modelo.addColumn("Puesto");
            modelo.addColumn("Fecha de salida");
            modelo.addColumn("Fecha de llegada");
            modelo.addColumn("Lugar");
            this.tablonpendientes.setModel(modelo);
            try {

                ResultSet usuario;
                usuario = cbd.getTabla("select puesto from user where id_user='" + Principal.Username + "'", cn);
                usuario.next();
                if (usuario.getString("puesto").equals("SuperUsuario") || usuario.getString("puesto").equals("Administrador")) {
                    soloUsuarioActual = "";
                } else {
                    usuario = cbd.getTabla("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m) as nombre from user U inner join empleados E on U.id_empleado=E.id_empleado where U.id_user='" + Principal.Username + "';", cn);
                    usuario.next();
                    soloUsuarioActual = " and nombre='" + usuario.getString("nombre") + "'";
                }
                Statement sentencia = cn.createStatement();

                ResultSet rs = cbd.getTabla("SELECT idSolicitud, Nombre, Puesto, Fecha_salida, Fecha_llegada,Lugar FROM Solicitud_viatico WHERE Estado = 'P' AND (idSolicitud LIKE '%" + txtbusquedasoli1.getText() + "%'"
                        + "OR Nombre LIKE '%" + txtbusquedasoli1.getText() + "%' OR Puesto LIKE '%" + txtbusquedasoli1.getText() + "%' OR Fecha_salida LIKE '%" + txtbusquedasoli1.getText() + "%' OR Fecha_llegada LIKE '%" + txtbusquedasoli1.getText() + "%'"
                        + "OR Lugar LIKE '%" + txtbusquedasoli1.getText() + "%') " + soloUsuarioActual + queryFecha + " order by idSolicitud DESC", cn);

                String solicitud[] = new String[7];
                while (rs.next()) {
                    solicitud[0] = rs.getString("idSolicitud");
                    ResultSet aux = cbd.getTabla("select * from vehiculo_viatico VV inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idSolicitud_vehiculo=idSolicitud_vehiculo where VV.solicitud_viatico_idSolicitud=" + rs.getString("idSolicitud") + " and VV.agregado='0'", cn);
                    if (aux.next()) {
                        solicitud[1] = "Vehículo";
                    } else {
                        solicitud[1] = "Viático";
                    }
                    solicitud[2] = rs.getString("Nombre");
                    solicitud[3] = rs.getString("Puesto");
                    solicitud[4] = rs.getString("Fecha_salida");
                    solicitud[5] = rs.getString("Fecha_llegada");
                    solicitud[6] = rs.getString("Lugar");
                    modelo.addRow(solicitud);
                }
                sentencia.close();
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

            }
        }
        if (menutablones.getSelectedIndex() == 1) {
            modelo = new DefaultTableModel() {
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            modelo.addColumn("Folio");
            modelo.addColumn("Tipo de solicitud");
            modelo.addColumn("Nombre");
            modelo.addColumn("Puesto");
            modelo.addColumn("Monto");
            modelo.addColumn("Fecha de salida");
            modelo.addColumn("Fecha de llegada");
            modelo.addColumn("Lugar");
            this.tablonaceptadas.setModel(modelo);
            try {

                Statement sentencia = cn.createStatement();

                ResultSet rs = cbd.getTabla("SELECT O.Folio,S.nombre, S.puesto, O.Monto, S.Fecha_salida, S.Fecha_llegada,S.Lugar,S.idSolicitud FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'A' AND S.idSolicitud = O.Solicitud_idSolicitud AND (O.Folio LIKE '%" + txtbusquedasoli1.getText() + "%'"
                        + "OR O.Monto LIKE '%" + txtbusquedasoli1.getText() + "%' OR S.Fecha_salida LIKE '%" + txtbusquedasoli1.getText() + "%' OR S.Fecha_llegada LIKE '%" + txtbusquedasoli1.getText() + "%'"
                        + "OR S.Lugar LIKE '%" + txtbusquedasoli1.getText() + "%' OR S.Nombre LIKE'%" + txtbusquedasoli1.getText() + "%') " + soloUsuarioActual + queryFecha + " order by idSolicitud DESC", cn);

                String solicitud[] = new String[8];
                while (rs.next()) {
                    solicitud[0] = rs.getString("Folio");
                    ResultSet aux = cbd.getTabla("select * from vehiculo_viatico VV inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idSolicitud_vehiculo=idSolicitud_vehiculo where VV.solicitud_viatico_idSolicitud=" + rs.getString("idSolicitud") + " and VV.agregado='0'", cn);
                    if (aux.next()) {
                        solicitud[1] = "Vehículo";
                    } else {
                        solicitud[1] = "Viático";
                    }
                    solicitud[2] = rs.getString("Nombre");
                    solicitud[3] = rs.getString("Puesto");
                    solicitud[4] = rs.getString("Monto");
                    solicitud[5] = rs.getString("Fecha_salida");
                    solicitud[6] = rs.getString("Fecha_llegada");
                    solicitud[7] = rs.getString("Lugar");
                    modelo.addRow(solicitud);
                }
                sentencia.close();
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

            }
        }
        if (menutablones.getSelectedIndex() == 2) {
            JTable checks = new JTable();//{  public boolean isCellEditable(int rowIndex, int colIndex){ if(colIndex == 0){return true;} else{return false; } }  };
            JScrollPane scroll = new JScrollPane();
            DefaultTableModel table = new DefaultTableModel();

            //Creamos la tabla con las caracterisiticas que necesitamos
            checks.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
            checks.setModel(new javax.swing.table.DefaultTableModel(
                    new Object[][]{},
                    //Declaramos el titulo de las columnas
                    new String[]{
                        "Folio ", "Tipo de solicitud", "Nombre", "Puesto", "Monto", "Fecha de salida", "Fecha de llegada", "Lugar", "Gastos a comprobar", "Informe"
                    }
            ) {
                //El tipo que sera cada columna, la primera columna un checkbox y los demas seran objetos
                Class[] types = new Class[]{
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Boolean.class,
                    java.lang.Object.class
                };

                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }
                //Esto es para indicar que columnas dejaremos editar o no
                boolean[] canEdit = new boolean[]{
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    true,
                    false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }

            }
            );
            //Agregamos un scroll a la tabla
            scroll.setViewportView(checks);
            scroll.setBounds(30, 130, 1110, 500);

            table = (DefaultTableModel) checks.getModel();

            try {
                //conexion = db.getConexion();
                String sql = "SELECT O.Folio,S.nombre,S.puesto, O.Monto, S.Fecha_salida, S.Fecha_llegada,S.Lugar,S.gastos_comprobar,S.Reporte FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.idSolicitud = O.Solicitud_idSolicitud and (nombre LIKE '%" + txtbusquedasoli1.getText() + "%' or puesto like '%" + txtbusquedasoli1.getText() + "%' or fecha_salida like '%" + txtbusquedasoli1.getText() + "%' or fecha_llegada like '%" + txtbusquedasoli1.getText() + "%' or lugar like '%" + txtbusquedasoli1.getText() + "%') ORDER BY O.FOLIO DESC";
                ResultSet usuario = cbd.getTabla("select puesto from user where id_user='" + Principal.Username + "'", cn);
                usuario.next();
                if (usuario.getString("puesto").equals("SuperUsuario") || usuario.getString("puesto").equals("Administrador")) {
                    sql = "SELECT O.Folio,S.nombre,S.puesto, O.Monto, S.Fecha_salida, S.Fecha_llegada,S.Lugar,S.gastos_comprobar,S.Reporte,S.idSolicitud FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.idSolicitud = O.Solicitud_idSolicitud and (nombre LIKE '%" + txtbusquedasoli1.getText() + "%' or puesto like '%" + txtbusquedasoli1.getText() + "%' or fecha_salida like '%" + txtbusquedasoli1.getText() + "%' or fecha_llegada like '%" + txtbusquedasoli1.getText() + "%' or lugar like '%" + txtbusquedasoli1.getText() + "%') "+queryFecha+" ORDER BY O.FOLIO DESC";
                } else {
                    usuario = cbd.getTabla("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m) as nombre from user U inner join empleados E on U.id_empleado=E.id_empleado where U.id_user='" + Principal.Username + "';", cn);
                    usuario.next();
                    sql = "SELECT O.Folio,S.nombre,S.puesto, O.Monto, S.Fecha_salida, S.Fecha_llegada,S.Lugar,S.gastos_comprobar,S.Reporte,S.idSolicitud FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.idSolicitud = O.Solicitud_idSolicitud and nombre='" + usuario.getString("nombre") + "' and (nombre LIKE '%" + txtbusquedasoli1.getText() + "%')"+queryFecha+" order by O.Folio DESC";
                }
                Statement sentencia = cn.createStatement();
                Object datos[] = new Object[10];
                ResultSet rs = cbd.getTabla(sql, cn);
                //Llenar tabla
                while (rs.next()) {
                    int indexDatos = 0;
                    for (int i = 0; i < 9; i++) {
                        if (indexDatos == 1) {
                            indexDatos = 2;
                        }
                        if (i == 7) {
                            datos[indexDatos] = rs.getBoolean(i + 1);
                        } else {
                            datos[indexDatos] = rs.getObject(i + 1);
                        }
                        if (i == 8) {
                            if (rs.getObject(i + 1).toString().equals("0")) {
                                datos[indexDatos] = "Pendiente";
                            } else {
                                datos[indexDatos] = "Terminado";
                            }
                        }
                        indexDatos++;

                    }//Llenamos las columnas por registro
                    ResultSet aux = cbd.getTabla("select * from vehiculo_viatico VV inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idSolicitud_vehiculo=idSolicitud_vehiculo where VV.solicitud_viatico_idSolicitud=" + rs.getString("idSolicitud") + " and VV.agregado='0'", cn);
                    if (aux.next()) {
                        datos[1] = "Vehículo";
                    } else {
                        datos[1] = "Viático";
                    }
                    table.addRow(datos);//Añadimos la fila
                }//while
                //cn.close();
                sentencia.close();
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta de Solicitudes Archivadas");

            } finally {

                tablonarchivadas.setModel(table);
            }
        }
        if (menutablones.getSelectedIndex() == 3) {
            modelo = new DefaultTableModel() {
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            modelo.addColumn("ID");
            modelo.addColumn("Tipo de solicitud");
            modelo.addColumn("Nombre");
            modelo.addColumn("Puesto");
            modelo.addColumn("Fecha de salida");
            modelo.addColumn("Fecha de llegada");
            modelo.addColumn("Lugar");
            modelo.addColumn("Motivo");
            this.tabloncanceladas.setModel(modelo);
            try {

                Statement sentencia = cn.createStatement();

                ResultSet rs = cbd.getTabla("SELECT idSolicitud, Nombre, Puesto, Fecha_salida, Fecha_llegada,Lugar, Motivo FROM Solicitud_viatico WHERE Estado = 'C' AND (idSolicitud LIKE '%" + txtbusquedasoli1.getText() + "%'"
                        + "OR Nombre LIKE '%" + txtbusquedasoli1.getText() + "%' OR Puesto LIKE '%" + txtbusquedasoli1.getText() + "%' OR Fecha_salida LIKE '%" + txtbusquedasoli1.getText() + "%' OR Fecha_llegada LIKE '%" + txtbusquedasoli1.getText() + "%'"
                        + "OR Lugar LIKE '%" + txtbusquedasoli1.getText() + "%' OR Motivo LIKE '%" + txtbusquedasoli1.getText() + "%')" + soloUsuarioActual + queryFecha + " order by idSolicitud DESC", cn);

                String solicitud[] = new String[8];
                while (rs.next()) {
                    solicitud[0] = rs.getString("idSolicitud");
                    ResultSet aux = cbd.getTabla("select * from vehiculo_viatico VV inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idSolicitud_vehiculo=idSolicitud_vehiculo where VV.solicitud_viatico_idSolicitud=" + rs.getString("idSolicitud") + " and VV.agregado='0'", cn);
                    if (aux.next()) {
                        solicitud[1] = "Vehículo";
                    } else {
                        solicitud[1] = "Viático";
                    }
                    solicitud[2] = rs.getString("Nombre");
                    solicitud[3] = rs.getString("Puesto");
                    solicitud[4] = rs.getString("Fecha_salida");
                    solicitud[5] = rs.getString("Fecha_llegada");
                    solicitud[6] = rs.getString("Lugar");
                    solicitud[7] = rs.getString("Motivo");
                    modelo.addRow(solicitud);
                }
                sentencia.close();
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

            }
        }
    }//GEN-LAST:event_txtbusquedasoli1KeyReleased

    private void CancelarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarAActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("actualizar", "Tablon Solicitudes", Principal.Username)) {
            int i = tablonaceptadas.getSelectedRow();
            if (i >= 0) {
                String folio = tablonaceptadas.getValueAt(i, 0).toString();
                String idSolicitud = "";
                try {
                    Class.forName("com.mysql.jdbc.Driver");

                    Statement sentencia = cn.createStatement();
                    ResultSet rs = cbd.getTabla("SELECT Solicitud_idSolicitud FROM Oficio_comision WHERE Folio = '" + folio + "'", cn);
                    while (rs.next()) {
                        idSolicitud = rs.getString("Solicitud_idSolicitud");
                    }
                    String motivo = javax.swing.JOptionPane.showInputDialog("Motivo");
                    if (motivo == null) {

                    } else {
                        sentencia.executeUpdate("UPDATE Solicitud_viatico SET Estado = 'C', Motivo = '" + motivo + "' WHERE (idSolicitud = " + idSolicitud + ")");
                        javax.swing.JOptionPane.showMessageDialog(null, "Solicitud cancelada");
                    }
                    sentencia.close();
                } catch (SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }//fin del catch

            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
            tabloncanceladas.setModel(manager_soviaticos.SolicitudC());
            tablonaceptadas.setModel(manager_soviaticos.SolicitudA());
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para cancelar solicitudes.");
        }
    }//GEN-LAST:event_CancelarAActionPerformed

    private void solipendientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_solipendientesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_solipendientesKeyPressed

    private void tablonsolicitud1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablonsolicitud1KeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_tablonsolicitud1KeyPressed

    private void solipendientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_solipendientesMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_solipendientesMouseClicked

    private void btnregresar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregresar1ActionPerformed
        // TODO add your handling code here:
        int s = JOptionPane.showConfirmDialog(null, "¿Esta seguro?", "Alerta!", JOptionPane.YES_NO_OPTION);
        if (s == JOptionPane.YES_OPTION) {
            btnregresar1.setVisible(false);
            btnActualizarInforme.setVisible(true);
            txtobvia1.setText("");
            txtobveh1.setText("");
            GaTot.setText("");
            txtobvia.enable(false);
            txtobveh.enable(false);
            txtobvia.setVisible(false);
            txtobveh.setVisible(false);
            btnregresar.setVisible(false);
            btnguardar.setVisible(false);
            tablainfo.enable(true);
            jlb.setVisible(true);
            txtbusquedasoli2.setVisible(true);
            jLabel1.setVisible(false);
            lblObsVehiculo.setVisible(false);
            jLabel3.setVisible(false);
            GaTot.enable(false);
            GaTot.setVisible(false);
            jScrollPane3.setVisible(false);
            jScrollPane1.setVisible(true);
            lblKilometraje.setVisible(false);
            txtKilometraje.setVisible(false);
            if (superUsuario) {
                cmbArea.setVisible(true);
                lblArea.setVisible(true);
            }
            if (idArea > 0) {
                Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S inner join Oficio_comision O on idSolicitud=O.solicitud_idSolicitud inner join puestos_trabajo PT on S.puesto=PT.Puesto WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 and PT.id_Area=" + idArea + " order by O.folio desc");
                SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S inner join Oficio_comision O on S.idSolicitud=O.solicitud_idSolicitud inner join Informe I on S.idSolicitud=I.solicitud_idSolicitud inner join Puestos_trabajo PT on S.puesto=PT.puesto WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 and PT.id_area=" + idArea + " ORDER BY I.Id_Informe DESC");
            } else {
                Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 order by O.folio desc");
                SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S, Oficio_comision O, Informe I WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 ORDER BY I.Id_Informe DESC");
            }
        } else {
        }
    }//GEN-LAST:event_btnregresar1ActionPerformed

    private void tablainfoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablainfoMouseReleased
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt)) {
            int r = tablainfo.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablainfo.getRowCount()) {
                tablainfo.setRowSelectionInterval(r, r);
            }
            MenuInfSA.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tablainfoMouseReleased

    private void tablaactMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaactMouseReleased
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt) && tablaact.isEnabled()) {
            int r = tablaact.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablaact.getRowCount()) {
                tablaact.setRowSelectionInterval(r, r);
            }
            MenuGI.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tablaactMouseReleased

    private void tablainfo1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablainfo1MouseReleased
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt)) {
            int r = tablainfo1.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablainfo1.getRowCount()) {
                tablainfo1.setRowSelectionInterval(r, r);
            }
            MenuInfSF.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tablainfo1MouseReleased

    private void GenerarInfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerarInfActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("alta", "Informe", Principal.Username)) {
            int k = tablainfo.getSelectedRow();
            folio = 0;
            if (k >= 0) {
                folio = Integer.parseInt(tablainfo.getValueAt(k, 0).toString());
                int filas = tablainfo.getRowCount();
                modelo = new DefaultTableModel();
                modelo.addColumn("Descripción");
                modelo.addColumn("Precio");
                modelo.addColumn("# Factura");
                this.tablaact.setModel(modelo);
                txtobvia.enable(true);
                txtobvia.setVisible(true);
                btnregresar1.setVisible(true);
                if (superUsuario) {
                    cmbArea.setVisible(false);
                    lblArea.setVisible(false);
                }
                try {
                    Statement sentencia = cn.createStatement();
                    String gastos_comprobar = "";
                    ResultSet rs = cbd.getTabla("SELECT S.gastos_comprobar FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Folio = " + folio, cn);
                    while (rs.next()) {
                        gastos_comprobar = rs.getString("gastos_comprobar");
                    }
                    if (gastos_comprobar.equals("true")) {
                        jScrollPane3.setVisible(true);
                        GaTot.enable(false);
                        tablaact.setEnabled(true);
                        GaTot.setVisible(true);
                        jLabel3.setVisible(true);
                        c = 1;
                    } else {
                        jScrollPane3.setVisible(true);
                        GaTot.setVisible(true);
                        jLabel3.setEnabled(false);
                        tablaact.setEnabled(false);
                        //jScrollPane3.setEnabled(false);
                        GaTot.enable(false);
                        c = 0;
                    }
                    rs = cbd.getTabla("select * from oficio_comision OC inner join solicitud_viatico SVI on OC.Solicitud_idSolicitud=SVI.idSolicitud inner join vehiculo_viatico VV on SVI.idSolicitud=VV.solicitud_viatico_idSolicitud inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo where OC.Folio=" + folio, cn);
                    boolean bloquearVehiculo = true;
                    while (rs.next()) {
                        bloquearVehiculo = false;
                        if (rs.getString("chofer") != null) {
                            txtobveh.enable(true);
                            txtobveh.setVisible(true);
                            txtKilometraje.setVisible(true);
                            lblKilometraje.setVisible(true);
                            lblObsVehiculo.setVisible(true);
                        } else {
                            txtobveh.enable(false);
                            txtobveh.setVisible(false);
                            txtKilometraje.setVisible(false);
                            lblKilometraje.setVisible(false);
                            lblObsVehiculo.setVisible(false);
                        }
                    }
                    if (bloquearVehiculo) {
                        txtobveh.enable(false);
                        txtobveh.setVisible(false);
                        txtKilometraje.setVisible(false);
                        lblKilometraje.setVisible(false);
                        lblObsVehiculo.setVisible(false);
                    }
                    btnregresar.setVisible(true);
                    btnguardar.setVisible(true);
                    jlb.setVisible(false);
                    txtbusquedasoli2.setVisible(false);
                    btnActualizarInforme.setVisible(false);
                    jLabel1.setVisible(true);
                    jScrollPane1.setVisible(false);
                    sentencia.close();
                } catch (SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar el informe de actividades.");
        }
    }//GEN-LAST:event_GenerarInfActionPerformed

    private void jScrollPane3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane3MouseReleased
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt) && tablaact.isEnabled()) {
            int r = tablaact.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablaact.getRowCount()) {
                tablaact.setRowSelectionInterval(r, r);
            }
            MenuGI.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_jScrollPane3MouseReleased

    private void ConsultarInfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultarInfActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Informe", Principal.Username)) {
            try {
                int k = tablainfo1.getSelectedRow();
                String idInforme = "";
                List<Gastos_Comprobar> gastos = new ArrayList<Gastos_Comprobar>();
                if (k >= 0) {
                    idInforme = tablainfo1.getValueAt(k, 0).toString();
                    CrearReporteActividades cra = new CrearReporteActividades(idInforme);
                    List<String> datos = cbd.acceder("select O.Folio,S.Lugar,S.Actividad,I.Observaciones,S.Nombre,S.Puesto from solicitud_viatico S inner join oficio_comision O on S.idSolicitud=O.Solicitud_idSolicitud inner join informe I on S.idSolicitud=I.Solicitud_idSolicitud where I.Id_Informe=" + idInforme + ";");
                    List<String> vehiculo = cbd.acceder("select SV.Vehiculo,VU.vehiculos_Matricula from solicitud_viatico S inner join oficio_comision O on S.idSolicitud=O.Solicitud_idSolicitud inner join vehiculo_viatico VV on S.idSolicitud=VV.solicitud_viatico_idSolicitud inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo inner join vehiculo_usado VU on SV.vehiculo_usado_idvehiculo_usado=VU.idvehiculo_usado where O.folio=" + folio + ";");
                    List<String> indices = cbd.acceder("select IG.Gastos_id_gastos from informe_gastos IG inner join informe I on IG.Informe_id_informe=I.id_informe where I.id_informe=" + idInforme);
                    if (indices.size() > 0) {
                        String query = "select precio,descripcion,factura from gastos where ";
                        for (int i = 0; i < indices.size(); i++) {
                            if (i == 0) {
                                query += "id_gastos=" + indices.get(i);
                            } else {
                                query += " or id_gastos=" + indices.get(i);
                            }
                        }
                        List<String> gastos_query = cbd.acceder(query);

                        for (int i = 0; i < gastos_query.size(); i += 3) {
                            gastos.add(new Gastos_Comprobar(gastos_query.get(i), gastos_query.get(i + 1), gastos_query.get(i + 2)));
                            //JOptionPane.showMessageDialog(this, gastos_query.get(i)+"-"+gastos_query.get(i+1)+"-"+gastos_query.get(i+2));
                        }
                    }
                    if (vehiculo.size() < 1) {
                        cra.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), gastos);
                    } else {
                        cra.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), gastos);
                    }
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar Informe");
                }
            } catch (DocumentException ex) {
                Logger.getLogger(PrincipalS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para imprimir solicitudes de viáticos.");
        }
    }//GEN-LAST:event_ConsultarInfActionPerformed

    private void AñadirAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AñadirAActionPerformed
        // TODO add your handling code here:
        int filas = tablaact.getRowCount();
        modelo=(DefaultTableModel)tablaact.getModel();
        if (filas == 0) {
            modelo.addRow(new Object[]{"", "", ""});
        } else if (tablaact.getValueAt(filas - 1, 0) != "" && tablaact.getValueAt(filas - 1, 1) != "") {
            modelo.addRow(new Object[]{"", "", ""});
        }
    }//GEN-LAST:event_AñadirAActionPerformed

    private void EliminarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarAActionPerformed
        // TODO add your handling code here:
        int i = tablaact.getSelectedRow();
        if(i>=0){
            modelo=(DefaultTableModel)tablaact.getModel();
            modelo.removeRow(i);
            tablaact.setModel(modelo);
        }
    }//GEN-LAST:event_EliminarAActionPerformed

    private void ConsultarArActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultarArActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            if (c == 0) {
                c = 1;
            } else {
                s.setVisible(false);
                c = 1;
            }
            int i = tablonarchivadas.getSelectedRow();
            if (i >= 0) {
                String folio = tablonarchivadas.getValueAt(i, 0).toString();
                String idSolicitud = "";
                try {
                    Statement sentencia = cn.createStatement();
                    ResultSet rs = cbd.getTabla("SELECT Solicitud_idSolicitud FROM Oficio_comision WHERE Folio = '" + folio + "'", cn);
                    rs.next();
                    idSolicitud = rs.getString("Solicitud_idSolicitud");

                    s = new visSolicitudViaticos(this, true);
                    s.setLocationRelativeTo(null);
                    s.IdUsuario(Integer.parseInt(idSolicitud), 1, 1);
                    sentencia.close();
                } catch (SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

                }
                /*catch (ClassNotFoundException e) {
                 e.printStackTrace();
                 }//fin del catch*/

                s.setVisible(true);
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para consultar las solicitudes.");
        }
    }//GEN-LAST:event_ConsultarArActionPerformed

    private void OficioComisionArActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OficioComisionArActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            int i = tablonarchivadas.getSelectedRow();
            if (i >= 0) {
                String folio = tablonarchivadas.getValueAt(i, 0).toString();
                CrearOficioComision coc = new CrearOficioComision(folio);
                try {
                    List<String> datos = cbd.acceder("select O.Folio,S.Nombre,S.Puesto,S.Lugar,S.Fecha_salida,S.Fecha_llegada,S.Actividad from solicitud_viatico S inner join oficio_comision O on S.idSolicitud=O.Solicitud_idSolicitud where O.Folio=" + folio + ";");
                    List<String> vehiculo = cbd.acceder("select SV.Vehiculo,VU.vehiculos_Matricula from solicitud_viatico S inner join oficio_comision O on S.idSolicitud=O.Solicitud_idSolicitud inner join vehiculo_viatico VV on S.idSolicitud=VV.solicitud_viatico_idSolicitud inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo inner join vehiculo_usado VU on SV.vehiculo_usado_idvehiculo_usado=VU.idvehiculo_usado where O.folio=" + folio + ";");
                    List<String> responsable = cbd.acceder("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m),PT.Puesto from empleados E inner join area A on E.id_empleado=A.Responsable inner join puestos_trabajo PT on E.puesto=PT.ID_Puesto where A.ID_Area=4;");
                    if (vehiculo.size() < 1) {
                        coc.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), datos.get(6), "", responsable.get(0), responsable.get(1));
                    } else {
                        coc.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), datos.get(6), vehiculo.get(0) + "-" + vehiculo.get(1), responsable.get(0), responsable.get(1));
                    }
                } catch (DocumentException ex) {
                    Logger.getLogger(PrincipalS.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para imprimir las solicitudes.");
        }
    }//GEN-LAST:event_OficioComisionArActionPerformed

    private void OficioViaticoArActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OficioViaticoArActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            int i = tablonarchivadas.getSelectedRow();
            if (i >= 0) {
                String folio = tablonarchivadas.getValueAt(i, 0).toString();
                try {
                    CrearOficioViatico cov = new CrearOficioViatico(folio);
                    List<String> datos = cbd.acceder("select O.Folio,S.Nombre,S.Puesto,O.Monto,S.Actividad,S.Lugar,S.Fecha_salida,S.Fecha_llegada,S.Pernoctado from solicitud_viatico S inner join oficio_comision O on S.idSolicitud=O.Solicitud_idSolicitud where O.Folio=" + folio + ";");
                    List<String> vehiculo = cbd.acceder("select SV.Vehiculo,VU.vehiculos_Matricula from solicitud_viatico S inner join oficio_comision O on S.idSolicitud=O.Solicitud_idSolicitud inner join vehiculo_viatico VV on S.idSolicitud=VV.solicitud_viatico_idSolicitud inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo inner join vehiculo_usado VU on SV.vehiculo_usado_idvehiculo_usado=VU.idvehiculo_usado where O.folio=" + folio + ";");
                    List<String> responsable = cbd.acceder("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m),PT.Puesto from empleados E inner join area A on E.id_empleado=A.Responsable inner join puestos_trabajo PT on E.puesto=PT.ID_Puesto where A.ID_Area=4;");
                    if (vehiculo.size() < 1) {
                        cov.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), datos.get(6), datos.get(7), datos.get(8), "", responsable.get(0), responsable.get(1));
                    } else {
                        cov.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), datos.get(6), datos.get(7), datos.get(8), vehiculo.get(0) + "-" + vehiculo.get(1), responsable.get(0), responsable.get(1));
                    }
                } catch (DocumentException e) {
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para imprimir las solicitudes.");
        }
    }//GEN-LAST:event_OficioViaticoArActionPerformed

    private void tablonarchivadasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablonarchivadasMouseReleased
        // TODO add your handling code here:
        if (SwingUtilities.isLeftMouseButton(evt)) {
            int r = tablonarchivadas.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablonarchivadas.getRowCount()) {
                tablonarchivadas.setRowSelectionInterval(r, r);
            }
            int k = tablonarchivadas.getSelectedRow();
            if (k >= 0) {
                String folio = tablonarchivadas.getValueAt(k, 0).toString();
                boolean gastosac = (boolean) tablonarchivadas.getValueAt(k, 8);
                String idSolicitud = "";
                try {

                    Statement sentencia = cn.createStatement();
                    ResultSet rs = cbd.getTabla("SELECT Solicitud_idSolicitud FROM Oficio_comision WHERE Folio = '" + folio + "'", cn);
                    while (rs.next()) {
                        idSolicitud = rs.getString("Solicitud_idSolicitud");
                    }
                    sentencia.executeUpdate("UPDATE Solicitud_viatico SET gastos_comprobar = '" + gastosac + "' WHERE (idSolicitud = " + idSolicitud + ")");
                    //javax.swing.JOptionPane.showMessageDialog(null, "Gastos a comprobar");
                    sentencia.close();
                } catch (SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

                } catch (NumberFormatException exp) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Ingresar solo números");
                }//fin del catch

            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
        }//clic derecho
        if (SwingUtilities.isRightMouseButton(evt)) {
            int r = tablonarchivadas.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablonarchivadas.getRowCount()) {
                tablonarchivadas.setRowSelectionInterval(r, r);
            }
            MenuTablonAr.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tablonarchivadasMouseReleased

    private void jScrollPane4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane4MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane4MouseReleased

    private void tablaactFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablaactFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_tablaactFocusLost

    private void tablaactFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablaactFocusGained
        // TODO add your handling code here:
        //suma de precio o costo
        float valor = 0;
        int filas = tablaact.getRowCount();
        if (filas != 0) {
            for (int j = 0; filas > j; j++) {
                if (tablaact.getValueAt(j, 1) != null || tablaact.getValueAt(j, 1) != "") {
                    valor = valor + Float.parseFloat(tablaact.getValueAt(j, 1) + "");
                }
            }
        }
        GaTot.setText(valor + "");
    }//GEN-LAST:event_tablaactFocusGained

    private void tabloncanceladasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabloncanceladasMouseReleased
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt)) {
            int r = tabloncanceladas.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tabloncanceladas.getRowCount()) {
                tabloncanceladas.setRowSelectionInterval(r, r);
            }
            MenuTablonC.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tabloncanceladasMouseReleased

    private void tablonsolicitud1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablonsolicitud1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tablonsolicitud1FocusLost

    private void ArchivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArchivarActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("actualizar", "Tablon Solicitudes", Principal.Username)) {
            int k = tablonaceptadas.getSelectedRow();
            if (k >= 0) {
                String folio = tablonaceptadas.getValueAt(k, 0).toString();
                String idSolicitud = "";
                try {

                    Statement sentencia = cn.createStatement();
                    ResultSet rs = cbd.getTabla("SELECT Solicitud_idSolicitud FROM Oficio_comision WHERE Folio = '" + folio + "'", cn);
                    while (rs.next()) {
                        idSolicitud = rs.getString("Solicitud_idSolicitud");
                    }
                    sentencia.executeUpdate("UPDATE Solicitud_viatico SET Estado = 'AR', gastos_comprobar = 'false' WHERE (idSolicitud = " + idSolicitud + ")");
                    javax.swing.JOptionPane.showMessageDialog(null, "Solicitud archivada");
                    sentencia.close();
                } catch (SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

                } catch (NumberFormatException exp) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Ingresar solo números");
                }//fin del catch

            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
            tablonarchivadas.setModel(manager_soviaticos.SolicitudAr());
            tablonaceptadas.setModel(manager_soviaticos.SolicitudA());

            if (idArea > 0) {
                Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S inner join Oficio_comision O on idSolicitud=O.solicitud_idSolicitud inner join puestos_trabajo PT on S.puesto=PT.Puesto WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 and PT.id_Area=" + idArea);
            } else {
                Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para archivar solicitudes.");
        }
    }//GEN-LAST:event_ArchivarActionPerformed

    private void Impri_Sol1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Impri_Sol1ActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Solicitud Viaticos", Principal.Username)) {
            int fila = tablasolic.getSelectedRow();;
            limpiar = false;
            String id = null;
            try {

                tablasolic.clearSelection();
                if (fila >= 0) {
                    id = tablasolic.getValueAt(fila, 0).toString();
                    CrearSolicitudViatico csv = new CrearSolicitudViatico(id);
                    List<String> datos = cbd.acceder("select fecha_salida,Fecha_llegada,Nombre,lugar,actividad,Pernoctado from solicitud_viatico where idSolicitud=" + id + ";");
                    List<String> vehiculo = cbd.acceder("select SV.Vehiculo,VU.vehiculos_Matricula from solicitud_viatico S inner join oficio_comision O on S.idSolicitud=O.Solicitud_idSolicitud inner join vehiculo_viatico VV on S.idSolicitud=VV.solicitud_viatico_idSolicitud inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo inner join vehiculo_usado VU on SV.vehiculo_usado_idvehiculo_usado=VU.idvehiculo_usado where S.idSolicitud=" + id + ";");
                    List<String> responsable = cbd.acceder("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m),PT.Puesto from empleados E inner join area A on E.id_empleado=A.Responsable inner join puestos_trabajo PT on E.puesto=PT.ID_Puesto where A.ID_Area=3;");
                    if (vehiculo.size() < 1) {
                        csv.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), "", responsable.get(0), responsable.get(1));
                    } else {
                        csv.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), vehiculo.get(0) + "-" + vehiculo.get(1), responsable.get(0), responsable.get(1));
                    }
                }
            } catch (Exception e) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para imprimir solicitudes de viáticos.");
        }
    }//GEN-LAST:event_Impri_Sol1ActionPerformed

    private void Add2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add2ActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("alta", "Solicitud Viaticos", Principal.Username)) {
            addSolicitudViaticos asv = new addSolicitudViaticos(this, true);
            conVehiculo = 0;
            asv.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar solicitudes de viáticos.");
        }
    }//GEN-LAST:event_Add2ActionPerformed

    private void SolicitarVehiculo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SolicitarVehiculo2ActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("alta", "Solicitud Viaticos", Principal.Username)) {
            addSolicitudViaticos asv = new addSolicitudViaticos(this, true);
            conVehiculo = 1;
            viatico_vehiculo = 0;
            asv.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar solicitudes de vehículos.");
        }
    }//GEN-LAST:event_SolicitarVehiculo2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        Object[] botones = {"Confirmar", "Cerrar Sesión", "Cancelar"};
        int opcion = JOptionPane.showOptionDialog(this, "¿Salir del Sistema?", "Confirmación",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botones, botones[0]);

        if (opcion == 0) {

            System.exit(0);
        } else if (opcion == 1) {
            //Cerrar sesion
            this.dispose();
            Login ob = new Login();
            ob.setVisible(true);
            try {
                cn.close();
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalS.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_formWindowClosing

    private void mi_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_inventarioActionPerformed
        try {
            Principal a = new Principal();
            a.setVisible(true);
            this.dispose();
            cn.close();
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            Logger.getLogger(PrincipalS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mi_inventarioActionPerformed

    private void cmbAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAreaActionPerformed
        // TODO add your handling code here:
        idArea = cmbArea.getSelectedIndex();
        if (idArea != 0) {
            Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S inner join Oficio_comision O on idSolicitud=O.solicitud_idSolicitud inner join puestos_trabajo PT on S.puesto=PT.Puesto WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 and PT.id_Area=" + idArea + " order by O.folio desc");
            SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S inner join Oficio_comision O on S.idSolicitud=O.solicitud_idSolicitud inner join Informe I on S.idSolicitud=I.solicitud_idSolicitud inner join Puestos_trabajo PT on S.puesto=PT.puesto WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 and PT.id_area=" + idArea + " ORDER BY I.Id_Informe DESC");
        } else {
            Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S inner join Oficio_comision O on idSolicitud=O.solicitud_idSolicitud inner join puestos_trabajo PT on S.puesto=PT.Puesto WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 order by O.folio desc");
            SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S inner join Oficio_comision O on S.idSolicitud=O.solicitud_idSolicitud inner join Informe I on S.idSolicitud=I.solicitud_idSolicitud inner join Puestos_trabajo PT on S.puesto=PT.puesto WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 ORDER BY I.Id_Informe DESC");
        }
    }//GEN-LAST:event_cmbAreaActionPerformed

    private void AsignarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AsignarVehiculoActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("alta", "Solicitud Viaticos", Principal.Username)) {
            int k = tablasolic.getSelectedRow();
            if (k >= 0) {
                String folio = tablasolic.getValueAt(k, 0).toString();
                int idSolicitud = (int) tablasolic.getValueAt(k, 0);
                addSolicitudViaticos asv = new addSolicitudViaticos(this, true, idSolicitud);
                conVehiculo = 1;
                viatico_vehiculo = 1;
                asv.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar solicitudes de vehículos.");
        }
    }//GEN-LAST:event_AsignarVehiculoActionPerformed

    private void solicitudviaticos1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_solicitudviaticos1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_solicitudviaticos1MouseReleased

    private void txtbusquedasoliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbusquedasoliKeyReleased
        // TODO add your handling code here:
        String soloUsuarioActual = "";
        if (jTabbedPane1.getSelectedIndex() == 0) {
            modelo = new DefaultTableModel() {
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            modelo.addColumn("idSolicitud");
            modelo.addColumn("Fecha de salida");
            modelo.addColumn("Lugar");
            modelo.addColumn("Nombre");
            modelo.addColumn("Actividad");
            modelo.addColumn("Pernoctado");
            modelo.addColumn("Puesto");
            modelo.addColumn("Fecha llegada");
            modelo.addColumn("Estado");
            this.tablasolic.setModel(modelo);
            try {

                ResultSet usuario;
                usuario = cbd.getTabla("select puesto from user where id_user='" + Principal.Username + "'", cn);
                usuario.next();
                if (usuario.getString("puesto").equals("SuperUsuario") || usuario.getString("puesto").equals("Administrador")) {
                    soloUsuarioActual = "";
                } else {
                    usuario = cbd.getTabla("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m) as nombre from user U inner join empleados E on U.id_empleado=E.id_empleado where U.id_user='" + Principal.Username + "';", cn);
                    usuario.next();
                    soloUsuarioActual = " and nombre='" + usuario.getString("nombre") + "'";
                }
                Statement sentencia = cn.createStatement();

                ResultSet rs = cbd.getTabla("SELECT idSolicitud,Fecha_salida, Lugar, Nombre,Actividad, Pernoctado, Puesto, Fecha_llegada,  Estado FROM Solicitud_viatico WHERE (idSolicitud LIKE '%" + txtbusquedasoli.getText() + "%'"
                        + "OR Nombre LIKE '%" + txtbusquedasoli.getText() + "%' OR Puesto LIKE '%" + txtbusquedasoli.getText() + "%' OR Fecha_salida LIKE '%" + txtbusquedasoli.getText() + "%' OR Fecha_llegada LIKE '%" + txtbusquedasoli.getText() + "%'"
                        + "OR Lugar LIKE '%" + txtbusquedasoli.getText() + "%' OR Pernoctado LIKE '%" + txtbusquedasoli.getText() + "%' OR Actividad LIKE '%" + txtbusquedasoli.getText() + "%' OR Estado LIKE '%" + txtbusquedasoli.getText() + "%') and estado='P' " + soloUsuarioActual + " order by idSolicitud DESC", cn);

                String solicitud[] = new String[10];
                Statement st = cn.createStatement();
                ResultSet sol_vehiculos_query = st.executeQuery("select solicitud_viatico_idSolicitud from vehiculo_viatico");
                List<Integer> sol_vehiculos = new ArrayList<Integer>();
                while (sol_vehiculos_query.next()) {
                    sol_vehiculos.add(sol_vehiculos_query.getInt("solicitud_viatico_idSolicitud"));
                }
                while (rs.next()) {
                    solicitud[0] = rs.getString("idSolicitud");
                    solicitud[1] = rs.getString("Fecha_salida");
                    solicitud[2] = rs.getString("Lugar");
                    solicitud[3] = rs.getString("Nombre");
                    solicitud[4] = rs.getString("Actividad");
                    solicitud[5] = rs.getString("Pernoctado");
                    solicitud[6] = rs.getString("Puesto");
                    solicitud[7] = rs.getString("Fecha_llegada");
                    switch (rs.getString("Estado").toString()) {
                        case "P":
                            solicitud[8] = "Pendiente";
                            break;
                        case "A":
                            solicitud[8] = "Aceptada";
                            break;
                        case "AR":
                            solicitud[8] = "Archivada";
                            break;
                        case "C":
                            solicitud[8] = "Cancelada";
                            break;
                    }
                    boolean insertar = true;
                    for (int i = 0; i < sol_vehiculos.size(); i++) {
                        if (rs.getInt("idSolicitud") == sol_vehiculos.get(i)) {
                            insertar = false;
                        }
                    }
                    if (insertar) {
                        modelo.addRow(solicitud);
                    }
                }
                sentencia.close();
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

            }
        } else {
            modelo = new DefaultTableModel() {
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            modelo.addColumn("idSolicitud");
            modelo.addColumn("Fecha de salida");
            modelo.addColumn("Lugar");
            modelo.addColumn("Nombre");
            modelo.addColumn("Actividad");
            modelo.addColumn("Pernoctado");
            modelo.addColumn("Puesto");
            modelo.addColumn("Fecha de llegada");
            modelo.addColumn("Hora Salida");
            modelo.addColumn("Hota Llegada");
            modelo.addColumn("Estado");
            this.tablasolicvehiculo.setModel(modelo);
            try {

                Statement sentencia = cn.createStatement();

                ResultSet rs = cbd.getTabla("SELECT idSolicitud, Actividad, Nombre, Puesto, Fecha_salida, Fecha_llegada,Lugar,Pernoctado,Hora_salida,Hora_llegada,estado FROM solicitud_viatico SVI inner join vehiculo_viatico VV on SVI.idSolicitud=VV.solicitud_viatico_idSolicitud inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo"
                        + " WHERE (idSolicitud LIKE '%" + txtbusquedasoli.getText() + "%'"
                        + "OR Nombre LIKE '%" + txtbusquedasoli.getText() + "%' OR Puesto LIKE '%" + txtbusquedasoli.getText() + "%' OR Fecha_salida LIKE '%" + txtbusquedasoli.getText() + "%' OR Fecha_llegada LIKE '%" + txtbusquedasoli.getText() + "%'"
                        + "OR Lugar LIKE '%" + txtbusquedasoli.getText() + "%') and SV.chofer='1' and estado='P' " + soloUsuarioActual + " order by idSolicitud DESC", cn);

                String solicitud[] = new String[11];
                while (rs.next()) {
                    solicitud[0] = rs.getString("idSolicitud");
                    solicitud[1] = rs.getString("Fecha_salida");
                    solicitud[2] = rs.getString("Lugar");
                    solicitud[3] = rs.getString("Nombre");
                    solicitud[4] = rs.getString("Actividad");
                    solicitud[5] = rs.getString("Pernoctado");
                    solicitud[6] = rs.getString("Puesto");
                    solicitud[7] = rs.getString("Fecha_llegada");
                    solicitud[8] = rs.getString("Hora_salida");
                    solicitud[9] = rs.getString("Hora_llegada");
                    switch (rs.getString("Estado").toString()) {
                        case "P":
                            solicitud[10] = "Pendiente";
                            break;
                        case "A":
                            solicitud[10] = "Aceptada";
                            break;
                        case "AR":
                            solicitud[10] = "Archivada";
                            break;
                        case "C":
                            solicitud[10] = "Cancelada";
                            break;
                    }
                    modelo.addRow(solicitud);
                }
                sentencia.close();
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

            }
        }
    }//GEN-LAST:event_txtbusquedasoliKeyReleased

    private void txtbusquedasoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbusquedasoliKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbusquedasoliKeyPressed

    private void jScrollPane12MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane12MouseReleased
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt)) {
            MenuPanelSolicitudViatico.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_jScrollPane12MouseReleased

    private void tablasolicvehiculoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablasolicvehiculoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasolicvehiculoMouseClicked

    private void tablasolicvehiculoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablasolicvehiculoMouseReleased
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt)) {
            int r = tablasolicvehiculo.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablasolicvehiculo.getRowCount()) {
                tablasolicvehiculo.setRowSelectionInterval(r, r);
            }
            MenuSolicitudViaticos.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor

        }//clic derecho
    }//GEN-LAST:event_tablasolicvehiculoMouseReleased

    private void jScrollPane11MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane11MouseReleased
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt)) {
            MenuPanelSolicitudViatico.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_jScrollPane11MouseReleased

    private void tablasolicMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablasolicMouseReleased
        // TODO add your handling code here:
        if (SwingUtilities.isRightMouseButton(evt)) {
            int r = tablasolic.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablasolic.getRowCount()) {
                tablasolic.setRowSelectionInterval(r, r);
            }
            MenuSolicitudViaticos1.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor

        }//clic derecho
    }//GEN-LAST:event_tablasolicMouseReleased

    private void tablasolicMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablasolicMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasolicMouseClicked

    private void mi_pasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_pasesActionPerformed
        // TODO add your handling code here:
        try {
            PrincipalP a = new PrincipalP();
            a.setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mi_pasesActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:

        Object[] botones = {"Confirmar", "Cancelar"};
        int opcion = JOptionPane.showOptionDialog(this, "¿Desea cerrar sesión?", "Confirmación",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botones, botones[0]);

        if (opcion == 0) {
            this.dispose();
            Login ob = new Login();
            ob.setVisible(true);
        } else if (opcion == 1) {
            // No hacer nada
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void itemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSalirActionPerformed
        // TODO add your handling code here:
        Object[] botones = {"Confirmar", "Cancelar"};
        int opcion = JOptionPane.showOptionDialog(this, "¿Salir del Sistema?", "Confirmación",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, botones, botones[0]);

        if (opcion == 0) {
            System.exit(0);
        } else if (opcion == 1) {
            //Cerrar sesion
        }
    }//GEN-LAST:event_itemSalirActionPerformed

    private void ExportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportarExcelActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Solicitud Viaticos", Principal.Username)) {
            try {
                //if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                Excel excel = new Excel();
                excel.GuardarComo(tablasolicvehiculo);
                    //}else{
                //JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultas en el inventario.");
                //tablapase.setModel(new DefaultTableModel());
                //}
            } catch (NullPointerException e) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para exportar solicitudes de vehículos.");
        }
    }//GEN-LAST:event_ExportarExcelActionPerformed

    private void ExportarExcelPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportarExcelPActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            try {
                //if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                Excel excel = new Excel();
                excel.GuardarComo(tablonpendientes);
                    //}else{
                //JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultas en el inventario.");
                //tablapase.setModel(new DefaultTableModel());
                //}
            } catch (NullPointerException e) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para exportar solicitudes pendientes.");
        }
    }//GEN-LAST:event_ExportarExcelPActionPerformed

    private void ExportarExcelAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportarExcelAActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            try {
                //if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                Excel excel = new Excel();
                excel.GuardarComo(tablonaceptadas);
                    //}else{
                //JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultas en el inventario.");
                //tablapase.setModel(new DefaultTableModel());
                //}
            } catch (NullPointerException e) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para exportar solicitudes aceptadas.");
        }
    }//GEN-LAST:event_ExportarExcelAActionPerformed

    private void ExportarExcelCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportarExcelCActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            try {
                //if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                Excel excel = new Excel();
                excel.GuardarComo(tabloncanceladas);
                    //}else{
                //JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultas en el inventario.");
                //tablapase.setModel(new DefaultTableModel());
                //}
            } catch (NullPointerException e) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para exportar solicitudes canceladas.");
        }
    }//GEN-LAST:event_ExportarExcelCActionPerformed

    private void ExportarExcelArActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportarExcelArActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            try {
                //if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                Excel excel = new Excel();
                excel.GuardarComo(tablonarchivadas);
                    //}else{
                //JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultas en el inventario.");
                //tablapase.setModel(new DefaultTableModel());
                //}
            } catch (NullPointerException e) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para exportar solicitudes archivadas.");
        }
    }//GEN-LAST:event_ExportarExcelArActionPerformed

    private void ExportarExcel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportarExcel1ActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Solicitud Viaticos", Principal.Username)) {
            try {
                //if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                Excel excel = new Excel();
                excel.GuardarComo(tablasolic);
                    //}else{
                //JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultas en el inventario.");
                //tablapase.setModel(new DefaultTableModel());
                //}
            } catch (NullPointerException e) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para exportar solicitudes de viaticos.");
        }
    }//GEN-LAST:event_ExportarExcel1ActionPerformed

    private void ExportarExcelInAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportarExcelInAActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Informe", Principal.Username)) {
            try {
                //if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                Excel excel = new Excel();
                excel.GuardarComo(tablainfo);
                    //}else{
                //JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultas en el inventario.");
                //tablapase.setModel(new DefaultTableModel());
                //}
            } catch (NullPointerException e) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para exportar informes aceptados.");
        }
    }//GEN-LAST:event_ExportarExcelInAActionPerformed

    private void ExportarExcelInFiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportarExcelInFiActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Informe", Principal.Username)) {
            try {
                //if(manager_permisos.accesoModulo("consulta","Inventario",Username)){
                Excel excel = new Excel();
                excel.GuardarComo(tablainfo1);
                    //}else{
                //JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para realizar consultas en el inventario.");
                //tablapase.setModel(new DefaultTableModel());
                //}
            } catch (NullPointerException e) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para exportar informes finalizados.");
        }
    }//GEN-LAST:event_ExportarExcelInFiActionPerformed

    private void solicarchivadasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_solicarchivadasFocusLost
        // TODO add your handling code hguardargac.setVisible(false);
    }//GEN-LAST:event_solicarchivadasFocusLost

    private void menutablonesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_menutablonesStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_menutablonesStateChanged

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            buscarFecha=false;
            tablonpendientes.setModel(manager_soviaticos.SolicitudP());
            tablonaceptadas.setModel(manager_soviaticos.SolicitudA());
            tabloncanceladas.setModel(manager_soviaticos.SolicitudC());
            tablonarchivadas.setModel(manager_soviaticos.SolicitudAr());
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnActualizarSolicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarSolicActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Solicitud Viaticos", Principal.Username)) {
            tablasolic.setModel(manager_soviaticos.getTasol());
            tablasolicvehiculo.setModel(manager_soviaticos.getTasolVehiculo());
        }
    }//GEN-LAST:event_btnActualizarSolicActionPerformed

    private void btnActualizarInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarInformeActionPerformed
        // TODO add your handling code here:
        ResultSet usuario;
        try {
            usuario = cbd.getTabla("select puesto from user where id_user='" + Principal.Username + "'", cn);
            usuario.next();
            if (usuario.getString("puesto").equals("SuperUsuario") || usuario.getString("puesto").equals("Administrador")) {
                if (idArea > 0) {
                    Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S inner join Oficio_comision O on idSolicitud=O.solicitud_idSolicitud inner join puestos_trabajo PT on S.puesto=PT.Puesto WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 and PT.id_Area=" + idArea + " order by O.folio desc");
                    SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S inner join Oficio_comision O on S.idSolicitud=O.solicitud_idSolicitud inner join Informe I on S.idSolicitud=I.solicitud_idSolicitud inner join Puestos_trabajo PT on S.puesto=PT.puesto WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 and PT.id_area=" + idArea + " ORDER BY I.Id_Informe DESC");
                } else {
                    Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 order by O.folio desc");
                    SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S, Oficio_comision O, Informe I WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 ORDER BY I.Id_Informe DESC");
                }
                cmbArea.setVisible(true);
                lblArea.setVisible(true);
            } else {
                usuario = cbd.getTabla("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m) as nombre from user U inner join empleados E on U.id_empleado=E.id_empleado where U.id_user='" + Principal.Username + "';", cn);
                usuario.next();
                Solicitud("SELECT O.Folio, S.Nombre, S.Actividad, S.Lugar, O.Monto FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.Reporte = '0' AND S.idSolicitud = O.Solicitud_idSolicitud AND O.Monto != 0 and S.nombre='" + usuario.getString("nombre") + "' order by O.folio desc");
                SolicitudR("SELECT I.Id_Informe, O.FOLIO, S.Nombre, O.Monto, I.importe_total FROM Solicitud_viatico S, Oficio_comision O, Informe I WHERE S.Estado = 'AR' AND S.Reporte = '1' AND S.idSolicitud = O.Solicitud_idSolicitud AND I.Solicitud_idSolicitud = S.idSolicitud AND O.Monto != 0 and S.nombre='" + usuario.getString("nombre") + "' ORDER BY I.Id_Informe DESC");
                cmbArea.setVisible(false);
                lblArea.setVisible(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnActualizarInformeActionPerformed
    boolean buscarFecha=false;
    private void btnbuscarporfechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarporfechaActionPerformed
        // TODO add your handling code here:
        String soloUsuarioActual = "";
        buscarFecha=true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha_inicio = sdf.format(fechainicio.getDate().getTime());
        String fecha_final = sdf.format(fechafinal.getDate().getTime());

        try {
            ResultSet usuario;
            usuario = cbd.getTabla("select puesto from user where id_user='" + Principal.Username + "'", cn);
            usuario.next();
            if (usuario.getString("puesto").equals("SuperUsuario") || usuario.getString("puesto").equals("Administrador")) {
                soloUsuarioActual = "";
            } else {
                usuario = cbd.getTabla("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m) as nombre from user U inner join empleados E on U.id_empleado=E.id_empleado where U.id_user='" + Principal.Username + "';", cn);
                usuario.next();
                soloUsuarioActual = " and nombre='" + usuario.getString("nombre") + "'";
            }
        } catch (SQLException e) {
        }
        if (menutablones.getSelectedIndex() == 0) {
            modelo = new DefaultTableModel() {
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            modelo.addColumn("ID");
            modelo.addColumn("Tipo de solicitud");
            modelo.addColumn("Nombre");
            modelo.addColumn("Puesto");
            modelo.addColumn("Fecha de salida");
            modelo.addColumn("Fecha de llegada");
            modelo.addColumn("Lugar");
            this.tablonpendientes.setModel(modelo);
            try {

                Statement sentencia = cn.createStatement();

                ResultSet rs = cbd.getTabla("SELECT idSolicitud, Nombre, Puesto, Fecha_salida, Fecha_llegada,Lugar FROM Solicitud_viatico WHERE Estado = 'P' AND (Fecha_salida >= '" + fecha_inicio + "' AND Fecha_llegada <= '" + fecha_final + "') " + soloUsuarioActual + " order by idSolicitud", cn);

                String solicitud[] = new String[7];
                while (rs.next()) {
                    solicitud[0] = rs.getString("idSolicitud");
                    ResultSet aux = cbd.getTabla("select * from vehiculo_viatico VV inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idSolicitud_vehiculo=idSolicitud_vehiculo where VV.solicitud_viatico_idSolicitud=" + rs.getString("idSolicitud") + " and VV.agregado='0'", cn);
                    if (aux.next()) {
                        solicitud[1] = "Vehículo";
                    } else {
                        solicitud[1] = "Viático";
                    }
                    solicitud[2] = rs.getString("Nombre");
                    solicitud[3] = rs.getString("Puesto");
                    solicitud[4] = rs.getString("Fecha_salida");
                    solicitud[5] = rs.getString("Fecha_llegada");
                    solicitud[6] = rs.getString("Lugar");
                    modelo.addRow(solicitud);
                }
                sentencia.close();
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

            }
        }
        if (menutablones.getSelectedIndex() == 1) {
            modelo = new DefaultTableModel() {
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            modelo.addColumn("Folio");
            modelo.addColumn("Tipo de solicitud");
            modelo.addColumn("Nombre");
            modelo.addColumn("Puesto");
            modelo.addColumn("Monto");
            modelo.addColumn("Fecha de salida");
            modelo.addColumn("Fecha de llegada");
            modelo.addColumn("Lugar");
            this.tablonaceptadas.setModel(modelo);
            try {

                Statement sentencia = cn.createStatement();

                ResultSet rs = cbd.getTabla("SELECT O.Folio,S.nombre, S.puesto, O.Monto, S.Fecha_salida, S.Fecha_llegada,S.Lugar,S.idSolicitud FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'A' AND S.idSolicitud = O.Solicitud_idSolicitud AND (Fecha_salida >= '" + fecha_inicio + "' AND Fecha_llegada <= '" + fecha_final + "') " + soloUsuarioActual + " order by folio", cn);

                String solicitud[] = new String[8];
                while (rs.next()) {
                    solicitud[0] = rs.getString("Folio");
                    ResultSet aux = cbd.getTabla("select * from vehiculo_viatico VV inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idSolicitud_vehiculo=idSolicitud_vehiculo where VV.solicitud_viatico_idSolicitud=" + rs.getString("idSolicitud") + " and VV.agregado='0'", cn);
                    if (aux.next()) {
                        solicitud[1] = "Vehículo";
                    } else {
                        solicitud[1] = "Viático";
                    }
                    solicitud[2] = rs.getString("Nombre");
                    solicitud[3] = rs.getString("Puesto");
                    solicitud[4] = rs.getString("Monto");
                    solicitud[5] = rs.getString("Fecha_salida");
                    solicitud[6] = rs.getString("Fecha_llegada");
                    solicitud[7] = rs.getString("Lugar");
                    modelo.addRow(solicitud);
                }
                sentencia.close();
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

            }
        }
        if (menutablones.getSelectedIndex() == 2) {
            JTable checks = new JTable();//{  public boolean isCellEditable(int rowIndex, int colIndex){ if(colIndex == 0){return true;} else{return false; } }  };
            JScrollPane scroll = new JScrollPane();
            DefaultTableModel table = new DefaultTableModel();

            //Creamos la tabla con las caracterisiticas que necesitamos
            checks.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
            checks.setModel(new javax.swing.table.DefaultTableModel(
                    new Object[][]{},
                    //Declaramos el titulo de las columnas
                    new String[]{
                        "Folio ", "Tipo de solicitud", "Nombre", "Puesto", "Monto", "Fecha de salida", "Fecha de llegada", "Lugar", "Gastos a comprobar", "Informe"
                    }
            ) {
                //El tipo que sera cada columna, la primera columna un checkbox y los demas seran objetos
                Class[] types = new Class[]{
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Object.class,
                    java.lang.Boolean.class,
                    java.lang.Object.class
                };

                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }
                //Esto es para indicar que columnas dejaremos editar o no
                boolean[] canEdit = new boolean[]{
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    true,
                    false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }

            }
            );
            //Agregamos un scroll a la tabla
            scroll.setViewportView(checks);
            scroll.setBounds(30, 130, 1110, 500);

            table = (DefaultTableModel) checks.getModel();

            try {
                //conexion = db.getConexion();
                String sql = "SELECT O.Folio,S.nombre,S.puesto, O.Monto, S.Fecha_salida, S.Fecha_llegada,S.Lugar,S.gastos_comprobar,S.Reporte FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.idSolicitud = O.Solicitud_idSolicitud and (Fecha_salida >= '" + fecha_inicio + "' AND Fecha_llegada <= '" + fecha_final + "')  ORDER BY O.FOLIO DESC";
                ResultSet usuario = cbd.getTabla("select puesto from user where id_user='" + Principal.Username + "'", cn);
                usuario.next();
                if (usuario.getString("puesto").equals("SuperUsuario") || usuario.getString("puesto").equals("Administrador")) {
                    sql = "SELECT O.Folio,S.nombre,S.puesto, O.Monto, S.Fecha_salida, S.Fecha_llegada,S.Lugar,S.gastos_comprobar,S.Reporte,S.idSolicitud FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.idSolicitud = O.Solicitud_idSolicitud and (Fecha_salida >= '" + fecha_inicio + "' AND Fecha_llegada <= '" + fecha_final + "')   ORDER BY O.FOLIO DESC";
                } else {
                    usuario = cbd.getTabla("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m) as nombre from user U inner join empleados E on U.id_empleado=E.id_empleado where U.id_user='" + Principal.Username + "';", cn);
                    usuario.next();
                    sql = "SELECT O.Folio,S.nombre,S.puesto, O.Monto, S.Fecha_salida, S.Fecha_llegada,S.Lugar,S.gastos_comprobar,S.Reporte,S.idSolicitud FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.idSolicitud = O.Solicitud_idSolicitud and nombre='" + usuario.getString("nombre") + "' and (Fecha_salida >= '" + fecha_inicio + "' AND Fecha_llegada <= '" + fecha_final + "') " + soloUsuarioActual + " order by O.Folio DESC";
                }
                Statement sentencia = cn.createStatement();
                Object datos[] = new Object[10];
                ResultSet rs = cbd.getTabla(sql, cn);
                //Llenar tabla
                while (rs.next()) {
                    int indexDatos = 0;
                    for (int i = 0; i < 9; i++) {
                        if (indexDatos == 1) {
                            indexDatos = 2;
                        }
                        if (i == 7) {
                            datos[indexDatos] = rs.getBoolean(i + 1);
                        } else {
                            datos[indexDatos] = rs.getObject(i + 1);
                        }
                        indexDatos++;

                    }//Llenamos las columnas por registro
                    ResultSet aux = cbd.getTabla("select * from vehiculo_viatico VV inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idSolicitud_vehiculo=idSolicitud_vehiculo where VV.solicitud_viatico_idSolicitud=" + rs.getString("idSolicitud") + " and VV.agregado='0'", cn);
                    if (aux.next()) {
                        datos[1] = "Vehículo";
                    } else {
                        datos[1] = "Viático";
                    }
                    table.addRow(datos);//Añadimos la fila
                }//while
                //cn.close();
                sentencia.close();
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta de Solicitudes Archivadas");

            } finally {

                tablonarchivadas.setModel(table);
            }
        }
        if (menutablones.getSelectedIndex() == 3) {
            modelo = new DefaultTableModel() {
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            modelo.addColumn("ID");
            modelo.addColumn("Tipo de solicitud");
            modelo.addColumn("Nombre");
            modelo.addColumn("Puesto");
            modelo.addColumn("Fecha de salida");
            modelo.addColumn("Fecha de llegada");
            modelo.addColumn("Lugar");
            modelo.addColumn("Motivo");
            this.tabloncanceladas.setModel(modelo);
            try {

                Statement sentencia = cn.createStatement();

                ResultSet rs = cbd.getTabla("SELECT idSolicitud, Nombre, Puesto, Fecha_salida, Fecha_llegada,Lugar, Motivo FROM Solicitud_viatico WHERE Estado = 'C' AND (Fecha_salida >= '" + fecha_inicio + "' AND Fecha_llegada <= '" + fecha_final + "') " + soloUsuarioActual + " order by idSolicitud DESC", cn);

                String solicitud[] = new String[8];
                while (rs.next()) {
                    solicitud[0] = rs.getString("idSolicitud");
                    ResultSet aux = cbd.getTabla("select * from vehiculo_viatico VV inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idSolicitud_vehiculo=idSolicitud_vehiculo where VV.solicitud_viatico_idSolicitud=" + rs.getString("idSolicitud") + " and VV.agregado='0'", cn);
                    if (aux.next()) {
                        solicitud[1] = "Vehículo";
                    } else {
                        solicitud[1] = "Viático";
                    }
                    solicitud[2] = rs.getString("Nombre");
                    solicitud[3] = rs.getString("Puesto");
                    solicitud[4] = rs.getString("Fecha_salida");
                    solicitud[5] = rs.getString("Fecha_llegada");
                    solicitud[6] = rs.getString("Lugar");
                    solicitud[7] = rs.getString("Motivo");
                    modelo.addRow(solicitud);
                }
                sentencia.close();
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

            }
        }
    }//GEN-LAST:event_btnbuscarporfechaActionPerformed

    private void tablaactKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaactKeyPressed
        // TODO add your handling code here:
        float valor = 0;
        int filas = tablaact.getRowCount();
        if (filas != 0) {
            for (int j = 0; filas > j; j++) {
                if (tablaact.getValueAt(j, 1) != null || tablaact.getValueAt(j, 1) != "") {
                    valor = valor + Float.parseFloat(tablaact.getValueAt(j, 1) + "");
                }
            }
        }
        GaTot.setText(valor + "");
    }//GEN-LAST:event_tablaactKeyPressed

    private void txtobvia1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtobvia1KeyTyped
        // TODO add your handling code here:
        txtobvia1.setLineWrap(true);
        txtobvia1.setWrapStyleWord(true);
    }//GEN-LAST:event_txtobvia1KeyTyped

    private void txtobveh1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtobveh1KeyTyped
        // TODO add your handling code here:
        txtobveh1.setLineWrap(true);
        txtobveh1.setWrapStyleWord(true);
    }//GEN-LAST:event_txtobveh1KeyTyped

    private void OficioViaticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OficioViaticoActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            int i = tablonaceptadas.getSelectedRow();
            if (i >= 0) {
                String folio = tablonaceptadas.getValueAt(i, 0).toString();
                try {
                    CrearOficioViatico cov = new CrearOficioViatico(folio);
                    List<String> datos = cbd.acceder("select O.Folio,S.Nombre,S.Puesto,O.Monto,S.Actividad,S.Lugar,S.Fecha_salida,S.Fecha_llegada,S.Pernoctado from solicitud_viatico S inner join oficio_comision O on S.idSolicitud=O.Solicitud_idSolicitud where O.Folio=" + folio + ";");
                    List<String> vehiculo = cbd.acceder("select SV.Vehiculo,VU.vehiculos_Matricula from solicitud_viatico S inner join oficio_comision O on S.idSolicitud=O.Solicitud_idSolicitud inner join vehiculo_viatico VV on S.idSolicitud=VV.solicitud_viatico_idSolicitud inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo inner join vehiculo_usado VU on SV.vehiculo_usado_idvehiculo_usado=VU.idvehiculo_usado where O.folio=" + folio + ";");
                    List<String> responsable = cbd.acceder("select concat(E.nombres,\" \",E.apellido_p,\" \",E.apellido_m),PT.Puesto from empleados E inner join area A on E.id_empleado=A.Responsable inner join puestos_trabajo PT on E.puesto=PT.ID_Puesto where A.ID_Area=4;");
                    if (vehiculo.size() < 1) {
                        cov.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), datos.get(6), datos.get(7), datos.get(8), "", responsable.get(0), responsable.get(1));
                    } else {
                        cov.createTicket(1, datos.get(0), datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), datos.get(6), datos.get(7), datos.get(8), vehiculo.get(0) + "-" + vehiculo.get(1), responsable.get(0), responsable.get(1));
                    }
                } catch (DocumentException e) {
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para imprimir las solicitudes.");
        }
    }//GEN-LAST:event_OficioViaticoActionPerformed

    private void ConsultarP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultarP1ActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            if (c == 0) {
                c = 1;
            } else {
                s.setVisible(false);
                c = 1;
            }
            int k = tablasolicvehiculo.getSelectedRow();
            if (k >= 0) {
                int id = Integer.parseInt(tablasolicvehiculo.getValueAt(k, 0).toString());
                s = new visSolicitudViaticos(this, true);
                s.setLocationRelativeTo(null);
                s.IdUsuario(id, 0, 0);
                s.setVisible(true);
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para consultar las solicitudes.");
        }
    }//GEN-LAST:event_ConsultarP1ActionPerformed

    private void ConsultarP2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultarP2ActionPerformed
        // TODO add your handling code here:
        if (manager_permisos.accesoModulo("consulta", "Tablon Solicitudes", Principal.Username)) {
            if (c == 0) {
                c = 1;
            } else {
                s.setVisible(false);
                c = 1;
            }
            int k = tablasolic.getSelectedRow();
            if (k >= 0) {
                int id = Integer.parseInt(tablasolic.getValueAt(k, 0).toString());
                s = new visSolicitudViaticos(this, true);
                s.setLocationRelativeTo(null);
                s.IdUsuario(id, 0, 0);
                s.setVisible(true);
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Seleccionar solicitud");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para consultar las solicitudes.");
        }
    }//GEN-LAST:event_ConsultarP2ActionPerformed

    private void AgregarEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarEmpleadosActionPerformed
        if (manager_permisos.accesoModulo("actualizar", "Tablon Solicitudes", Principal.Username)) {
            try {
                // TODO add your handling code here:
                int fila = tablasolicvehiculo.getSelectedRow();
                String idSolicitud = tablasolicvehiculo.getValueAt(fila, 0) + "";
                String fecha = tablasolicvehiculo.getValueAt(fila, 1) + "";
                ResultSet rs = cbd.getTabla("select * from solicitud_viatico where estado!='AR' and estado!='C' and idSolicitud=" + idSolicitud, cn);
                boolean noModificar = true;
                while (rs.next()) {
                    noModificar = false;
                    addViaticoVehiculo avv = new addViaticoVehiculo(this, true, idSolicitud, fecha);
                    avv.setVisible(true);
                }
                if (noModificar) {
                    JOptionPane.showMessageDialog(this, "No se puede agregar empleados al vehiculo porque la solicitud está cancelada o ya le fue asignada un monto");
                }
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para agregar empleados a las solicitudes de vehículos.");
        }
    }//GEN-LAST:event_AgregarEmpleadosActionPerformed

    public void Solicitud(String s) {
        modelo = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        modelo.addColumn("Folio");
        modelo.addColumn("Nombre");
        modelo.addColumn("Actividad");
        modelo.addColumn("Lugar");
        modelo.addColumn("Monto");
        this.tablainfo.setModel(modelo);
        try {
            Statement sentencia = cn.createStatement();
            ResultSet rs = cbd.getTabla(s, cn);
            String solicitud[] = new String[5];
            while (rs.next()) {
                solicitud[0] = rs.getString("Folio");
                solicitud[1] = rs.getString("Nombre");
                solicitud[2] = rs.getString("Actividad");
                solicitud[3] = rs.getString("Lugar");
                solicitud[4] = rs.getString("Monto");
                modelo.addRow(solicitud);
            }
            sentencia.close();
        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error en la consultaA");
        }
    }

    public void SolicitudR(String s) {
        modelo = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        modelo.addColumn("ID Informe");
        modelo.addColumn("Folio");
        modelo.addColumn("Nombre");
        modelo.addColumn("Monto");
        modelo.addColumn("Importe total");
        this.tablainfo1.setModel(modelo);
        try {
            Statement sentencia = cn.createStatement();
            ResultSet rs = cbd.getTabla(s, cn);
            String solicitud[] = new String[5];
            while (rs.next()) {
                solicitud[0] = rs.getString("Id_Informe");
                solicitud[1] = rs.getString("Folio");
                solicitud[2] = rs.getString("Nombre");
                solicitud[3] = rs.getString("Monto");
                solicitud[4] = rs.getString("importe_total");
                modelo.addRow(solicitud);
            }
            sentencia.close();
        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error en la consultaF");

        }
    }

    public DefaultTableModel SolicitudArB() {
        JTable checks = new JTable();//{  public boolean isCellEditable(int rowIndex, int colIndex){ if(colIndex == 0){return true;} else{return false; } }  };
        JScrollPane scroll = new JScrollPane();
        DefaultTableModel table = new DefaultTableModel();

        //Creamos la tabla con las caracterisiticas que necesitamos
        checks.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        checks.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                //Declaramos el titulo de las columnas
                new String[]{
                    "Folio ", "Monto", "Fecha de salida", "Fecha de llegada", "Lugar", "Gastos a comprobar", "Informe"
                }
        ) {
            //El tipo que sera cada columna, la primera columna un checkbox y los demas seran objetos
            Class[] types = new Class[]{
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Boolean.class,
                java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
            //Esto es para indicar que columnas dejaremos editar o no
            boolean[] canEdit = new boolean[]{
                false,
                false,
                false,
                false,
                false,
                true,
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }

        }
        );
        //Agregamos un scroll a la tabla
        scroll.setViewportView(checks);
        scroll.setBounds(30, 130, 1110, 500);

        table = (DefaultTableModel) checks.getModel();

        try {
            //conexion = db.getConexion();
            String sql = "SELECT O.Folio, O.Monto, S.Fecha_salida, S.Fecha_llegada,S.Lugar,S.gastos_comprobar,S.Reporte FROM Solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'AR' AND S.idSolicitud = O.Solicitud_idSolicitud AND (O.Folio LIKE '%" + txtbusquedasoli1.getText() + "%'"
                    + "OR O.Monto LIKE '%" + txtbusquedasoli1.getText() + "%' OR S.Fecha_salida LIKE '%" + txtbusquedasoli1.getText() + "%' OR S.Fecha_llegada LIKE '%" + txtbusquedasoli1.getText() + "%' OR S.Lugar LIKE '%" + txtbusquedasoli1.getText() + "%'"
                    + "OR S.gastos_comprobar LIKE '%" + txtbusquedasoli1.getText() + "%' OR S.Reporte LIKE '%" + txtbusquedasoli1.getText() + "%')ORDER BY O.FOLIO DESC";
            Statement sentencia = cn.createStatement();
            Object datos[] = new Object[7];
            ResultSet rs = cbd.getTabla(sql, cn);
            //Llenar tabla
            while (rs.next()) {

                for (int i = 0; i < 7; i++) {
                    if (i == 5) {
                        datos[i] = rs.getBoolean(i + 1);
                    } else {
                        datos[i] = rs.getObject(i + 1);
                    }

                }//Llenamos las columnas por registro

                table.addRow(datos);//Añadimos la fila
            }//while
            //cn.close();
            sentencia.close();
        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta de Solicitudes Archivadas");

        } finally {

            return table;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrincipalS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.OfficeSilver2007Skin");
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AceptarP;
    private javax.swing.JMenuItem Add;
    private javax.swing.JMenuItem Add1;
    private javax.swing.JMenuItem Add2;
    private javax.swing.JMenuItem AgregarEmpleados;
    private javax.swing.JMenuItem Archivar;
    private javax.swing.JMenuItem AsignarMonto;
    private javax.swing.JMenuItem AsignarVehiculo;
    private javax.swing.JMenuItem AñadirA;
    private javax.swing.JMenuItem CambiarConsejero;
    private javax.swing.JMenuItem CancelarA;
    private javax.swing.JMenuItem CancelarP;
    private javax.swing.JMenuItem ConsultarA;
    private javax.swing.JMenuItem ConsultarAr;
    private javax.swing.JMenuItem ConsultarC;
    private javax.swing.JMenuItem ConsultarInf;
    private javax.swing.JMenuItem ConsultarP;
    private javax.swing.JMenuItem ConsultarP1;
    private javax.swing.JMenuItem ConsultarP2;
    private javax.swing.JMenuItem EliminarA;
    private javax.swing.JMenuItem ExportarExcel;
    private javax.swing.JMenuItem ExportarExcel1;
    private javax.swing.JMenuItem ExportarExcelA;
    private javax.swing.JMenuItem ExportarExcelAr;
    private javax.swing.JMenuItem ExportarExcelC;
    private javax.swing.JMenuItem ExportarExcelInA;
    private javax.swing.JMenuItem ExportarExcelInFi;
    private javax.swing.JMenuItem ExportarExcelP;
    private javax.swing.JTextField GaTot;
    private javax.swing.JMenuItem GenerarInf;
    private javax.swing.JMenuItem Impri_Sol;
    private javax.swing.JMenuItem Impri_Sol1;
    private javax.swing.JPopupMenu MenuGI;
    private javax.swing.JPopupMenu MenuInfSA;
    private javax.swing.JPopupMenu MenuInfSF;
    private javax.swing.JPopupMenu MenuPanelSolicitudViatico;
    private javax.swing.JPopupMenu MenuSolicitudViaticos;
    private javax.swing.JPopupMenu MenuSolicitudViaticos1;
    private javax.swing.JPopupMenu MenuTablonA;
    private javax.swing.JPopupMenu MenuTablonAr;
    private javax.swing.JPopupMenu MenuTablonC;
    private javax.swing.JPopupMenu MenuTablonP;
    private javax.swing.JMenuItem OficioComision;
    private javax.swing.JMenuItem OficioComisionAr;
    private javax.swing.JMenuItem OficioViatico;
    private javax.swing.JMenuItem OficioViaticoAr;
    private javax.swing.JMenuItem SolicitarVehiculo;
    private javax.swing.JMenuItem SolicitarVehiculo1;
    private javax.swing.JMenuItem SolicitarVehiculo2;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnActualizarInforme;
    private javax.swing.JButton btnActualizarSolic;
    private javax.swing.JButton btnAddInventario2;
    private javax.swing.JButton btnbuscarporfecha;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnregresar;
    private javax.swing.JButton btnregresar1;
    private javax.swing.JComboBox cmbArea;
    private com.toedter.calendar.JDateChooser fechafinal;
    private com.toedter.calendar.JDateChooser fechainicio;
    private javax.swing.JPanel informe;
    private javax.swing.JMenuItem itemSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jlb;
    private javax.swing.JLabel lblArea;
    private javax.swing.JLabel lblKilometraje;
    private javax.swing.JLabel lblObsVehiculo;
    private javax.swing.JTabbedPane menuInforme;
    private javax.swing.JTabbedPane menutablones;
    private javax.swing.JMenuItem mi_inventario;
    private javax.swing.JMenuItem mi_pases;
    private javax.swing.JPanel soliaceptadas;
    private javax.swing.JPanel solicanceladas;
    private javax.swing.JPanel solicarchivadas;
    private javax.swing.JPanel solicitudviaticos1;
    public static javax.swing.JTabbedPane solicviaticos;
    private javax.swing.JPanel solipendientes;
    private javax.swing.JTable tablaact;
    private javax.swing.JTable tablainfo;
    private javax.swing.JTable tablainfo1;
    public static javax.swing.JTable tablasolic;
    public static javax.swing.JTable tablasolicvehiculo;
    public static javax.swing.JTable tablonaceptadas;
    private javax.swing.JTable tablonarchivadas;
    public static javax.swing.JTable tabloncanceladas;
    public static javax.swing.JTable tablonpendientes;
    private javax.swing.JPanel tablonsolicitud1;
    private javax.swing.JTextField txtKilometraje;
    private javax.swing.JTextField txtbusquedasoli;
    private javax.swing.JTextField txtbusquedasoli1;
    private javax.swing.JTextField txtbusquedasoli2;
    private javax.swing.JScrollPane txtobveh;
    private javax.swing.JTextArea txtobveh1;
    private javax.swing.JScrollPane txtobvia;
    private javax.swing.JTextArea txtobvia1;
    // End of variables declaration//GEN-END:variables
}
