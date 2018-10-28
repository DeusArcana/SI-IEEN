/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Clases.Conexion;
import Clases.ExceptionDatosIncompletos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

import Clases.ManagerUsers;
import Clases.ManagerVehiculos;
import Clases.ManagerSoViaticos;
import Clases.ManagerComplemento;

import Interfaces.PrincipalS;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import java.awt.FocusTraversalPolicy;

/**
 *
 * @author usuario
 */
public class addSolicitudViaticos extends javax.swing.JDialog {
    ManagerSoViaticos manager_viaticos;
    ManagerUsers manager_users;
    ManagerVehiculos manager_vehiculo;
    ManagerComplemento manager_complemento;
    String pernoctado="";
    boolean seleccionarVehiculo=false;
    boolean asignarVehiculo=false;
    
    public int varida[];
    Conexion cbd=new Conexion();
    Connection cn=cbd.getConexion();
    public static boolean imprimirSolicitud=false;
    Frame parentVehiculo=null;
    /**
     * Creates new form addSolicitudViaticos
     */
    public addSolicitudViaticos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setSize(new Dimension(530,520));
        this.setTitle("Solicitud de viáticos");
        initComponents();
        cmbArea.setNextFocusableComponent(comboEmpleados);
        comboEmpleados.setNextFocusableComponent(date_Salida);
        date_Salida.setNextFocusableComponent(hora_Salida);
        hora_Salida.setNextFocusableComponent(date_Llegada);
        date_Llegada.setNextFocusableComponent(hora_Llegada);
        hora_Llegada.setNextFocusableComponent(cmbEstado);
        cmbEstado.setNextFocusableComponent(cmbLocalidad);
        cmbLocalidad.setNextFocusableComponent(txt_Actividad);
        txt_Actividad.setNextFocusableComponent(btnAceptar);
        btnAceptar.setNextFocusableComponent(btnCancelar);
        btnCancelar.setNextFocusableComponent(cmbArea);
        cmb_Vehiculo.setNextFocusableComponent(btnAceptar);
        btnEditar.setVisible(false);
        hora_Salida_A.setVisible(false);
        hora_Llegada_A.setVisible(false);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        parentVehiculo=parent;
        JTextFieldDateEditor date_Salida_Editor=(JTextFieldDateEditor) date_Salida.getDateEditor();
        JTextFieldDateEditor date_Llegada_Editor=(JTextFieldDateEditor) date_Llegada.getDateEditor();
        date_Salida_Editor.setEditable(false);
        date_Llegada_Editor.setEditable(false);
        date_Salida.getJCalendar().setMinSelectableDate(new Date()); // sets today as minimum selectable date
        date_Llegada.getJCalendar().setMinSelectableDate(new Date());
        //maxid();
        //txtid.setText(varida[0]+1+"");
        manager_viaticos = new ManagerSoViaticos();
        manager_users = new ManagerUsers();
        manager_vehiculo = new ManagerVehiculos();
        manager_complemento = new ManagerComplemento();
        
        iniciarEstados();
        
        AutoCompleteDecorator.decorate(this.comboEmpleados);
        
    }
    public addSolicitudViaticos(java.awt.Frame parent, boolean modal,int idSolicitud) {
        super(parent, modal);
        this.setSize(new Dimension(530,520));
        asignarVehiculo=true;
        initComponents();
        btnEditar.setVisible(false);
        btnMasMun.setVisible(false);
        btnLimpiarLugar.setVisible(false);
        cmbEstado.setEnabled(false);
        cmbLocalidad.setEnabled(false);
        this.setTitle("Asignar vehículo");
        //JOptionPane.showMessageDialog(this,idSolicitud);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        parentVehiculo=parent;
        JTextFieldDateEditor date_Salida_Editor=(JTextFieldDateEditor) date_Salida.getDateEditor();
        JTextFieldDateEditor date_Llegada_Editor=(JTextFieldDateEditor) date_Llegada.getDateEditor();
        date_Salida_Editor.setEditable(false);
        date_Llegada_Editor.setEditable(false);
        date_Salida.getJCalendar().setMinSelectableDate(new Date()); // sets today as minimum selectable date
        date_Llegada.getJCalendar().setMinSelectableDate(new Date());
        //maxid();
        //txtid.setText(varida[0]+1+"");
        manager_viaticos = new ManagerSoViaticos();
        manager_users = new ManagerUsers();
        manager_vehiculo = new ManagerVehiculos();
        manager_complemento = new ManagerComplemento();
        
        
        AutoCompleteDecorator.decorate(this.comboEmpleados);
        
        try{
            ResultSet rs=cbd.getTabla("select * from solicitud_viatico where idSolicitud="+idSolicitud, cn);
            comboEmpleados.setEnabled(false);
            cmbArea.setVisible(false);
            date_Salida.setEnabled(false);
            date_Llegada.setEnabled(false);
            hora_Salida.setVisible(false);
            hora_Llegada.setVisible(false);
            txt_Actividad.setEnabled(false);
            hora_Salida_A.setVisible(true);
            hora_Llegada_A.setVisible(true);
            hora_Salida_A.setEnabled(false);
            hora_Llegada_A.setEnabled(false);
            cmbEstado.setEditable(false);
            cmbLocalidad.setEditable(false);
            if(rs.next()){
                comboEmpleados.addItem(rs.getString("nombre"));
                comboEmpleados.setSelectedIndex(0);
                txt_Puesto.setText(rs.getString("puesto"));
                String[] fecha_salida=rs.getString("fecha_salida").split("-");
                Calendar c2 = new GregorianCalendar(Integer.parseInt(fecha_salida[0]),Integer.parseInt(fecha_salida[1])-1,Integer.parseInt(fecha_salida[2]));
                date_Salida.setCalendar(c2);
                String[] fecha_llegada=rs.getString("fecha_llegada").split("-");
                Calendar c3 = new GregorianCalendar(Integer.parseInt(fecha_llegada[0]),Integer.parseInt(fecha_llegada[1])-1,Integer.parseInt(fecha_llegada[2]));
                date_Llegada.setCalendar(c3);
                txt_Actividad.setText(rs.getString("Actividad"));
                String horaSalida=rs.getString("hora_Salida").split(":")[0]+":"+rs.getString("hora_Salida").split(":")[1]+" "+rs.getString("hora_Salida").split(" ")[1];
                hora_Salida_A.setText(horaSalida);
                horaSalida=rs.getString("hora_Llegada").split(":")[0]+":"+rs.getString("hora_Llegada").split(":")[1]+" "+rs.getString("hora_Llegada").split(" ")[1];
                hora_Llegada_A.setText(horaSalida);
                cmbEstado.removeAllItems();
                cmbLocalidad.removeAllItems();
                String[] localidad=rs.getString("lugar").split(",");
                cmbEstado.addItem(localidad[0]);
                cmbLocalidad.addItem(localidad[1]);
                cmbEstado.setSelectedIndex(0);
                cmbLocalidad.setSelectedIndex(0);
            }
            this.setSize(new Dimension(880,520));
            seleccionarVehiculo=true;
            cmb_Vehiculo.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
            cmb_Vehiculo.addItem("Selecione vehiculo...");
            //manager_vehiculo.getVehiculosDisponibles(cmb_Vehiculo);
            try{
                getAutosDisponibles();
            }catch(SQLException e){

            }
        }catch(SQLException e){}
        
    }
    private void iniciarEstados(){
        List<String> estados=cbd.acceder("select nombre from Estado;");
        char sep=92;
        for(int i=0;i<estados.size();i++){
            cmbEstado.addItem(estados.get(i).split("\r")[0]);
        }
        cmbEstado.setSelectedItem("Nayarit");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_addInventario = new javax.swing.JPanel();
        cmbArea = new javax.swing.JComboBox();
        comboEmpleados = new javax.swing.JComboBox<String>();
        hora_Llegada_A = new javax.swing.JTextField();
        date_Salida = new com.toedter.calendar.JDateChooser();
        Date date=new Date();
        SpinnerDateModel sdm=new SpinnerDateModel(date,null
            ,null,Calendar.HOUR_OF_DAY);
        hora_Salida = new javax.swing.JSpinner(sdm);
        date_Llegada = new com.toedter.calendar.JDateChooser();
        SpinnerDateModel sdm2=new SpinnerDateModel(date,null
            ,null,Calendar.HOUR_OF_DAY);
        hora_Llegada = new javax.swing.JSpinner(sdm2);
        cmbEstado = new javax.swing.JComboBox<String>();
        cmbLocalidad = new javax.swing.JComboBox<String>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_Actividad = new javax.swing.JTextArea();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_Puesto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblAviso = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmb_Vehiculo = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtKilometraje = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtADescripcion = new javax.swing.JTextArea();
        hora_Salida_A = new javax.swing.JTextField();
        btnEditar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        btnMasMun = new javax.swing.JButton();
        btnLimpiarLugar = new javax.swing.JButton();
        lblLugar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nueva Solicitud");
        setMinimumSize(new java.awt.Dimension(530, 520));
        setPreferredSize(new java.awt.Dimension(530, 520));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pn_addInventario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pn_addInventario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAreaActionPerformed(evt);
            }
        });
        pn_addInventario.add(cmbArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 330, -1));

        comboEmpleados.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comboEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEmpleadosActionPerformed(evt);
            }
        });
        pn_addInventario.add(comboEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 330, -1));

        hora_Llegada_A.setText("jTextField1");
        pn_addInventario.add(hora_Llegada_A, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 150, 100, -1));
        pn_addInventario.add(date_Salida, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 215, -1));
        date_Salida.setDateFormatString("dd-MM-yyyy");
        date_Salida.getDateEditor().addPropertyChangeListener(
            new java.beans.PropertyChangeListener() {
                @Override
                public void propertyChange(java.beans.PropertyChangeEvent e) {
                    if(e.getPropertyName().equals("date")) {
                        date_Llegada.getJCalendar().setMinSelectableDate(date_Salida.getDate());
                        date_Llegada.setDate(date_Salida.getDate());
                    }
                }
            });
            Calendar c2 = new GregorianCalendar();
            date_Salida.setCalendar(c2);

            hora_Salida.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    hora_SalidaStateChanged(evt);
                }
            });
            pn_addInventario.add(hora_Salida, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 100, -1));
            JSpinner.DateEditor de = new JSpinner.DateEditor(hora_Salida, "HH:mm");
            hora_Salida.setEditor(de);

            date_Llegada.setDateFormatString("dd-MM-yyyy");
            date_Llegada.getDateEditor().addPropertyChangeListener(
                new java.beans.PropertyChangeListener() {
                    @Override
                    public void propertyChange(java.beans.PropertyChangeEvent e) {
                        if(e.getPropertyName().equals("date")) {
                            hora_Llegada.setEnabled(true);
                        }
                    }
                });
                c2 = new GregorianCalendar();
                date_Salida.setCalendar(c2);
                pn_addInventario.add(date_Llegada, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 215, -1));

                hora_Llegada.setEnabled(false);
                hora_Llegada.addChangeListener(new javax.swing.event.ChangeListener() {
                    public void stateChanged(javax.swing.event.ChangeEvent evt) {
                        hora_LlegadaStateChanged(evt);
                    }
                });
                pn_addInventario.add(hora_Llegada, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 150, 100, -1));
                JSpinner.DateEditor de2 = new JSpinner.DateEditor(hora_Llegada, "HH:mm");
                hora_Llegada.setEditor(de2);

                cmbEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione estado" }));
                cmbEstado.addItemListener(new java.awt.event.ItemListener() {
                    public void itemStateChanged(java.awt.event.ItemEvent evt) {
                        cmbEstadoItemStateChanged(evt);
                    }
                });
                pn_addInventario.add(cmbEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, -1, -1));

                cmbLocalidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione municipio" }));
                pn_addInventario.add(cmbLocalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, 130, -1));

                txt_Actividad.setColumns(20);
                txt_Actividad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                txt_Actividad.setRows(5);
                txt_Actividad.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyTyped(java.awt.event.KeyEvent evt) {
                        txt_ActividadKeyTyped(evt);
                    }
                });
                jScrollPane1.setViewportView(txt_Actividad);

                pn_addInventario.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 281, 480, 120));

                btnAceptar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/aceptar.png"))); // NOI18N
                btnAceptar.setText("Aceptar");
                btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                btnAceptar.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        btnAceptarActionPerformed(evt);
                    }
                });
                pn_addInventario.add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 423, -1, 30));

                btnCancelar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cancelar.png"))); // NOI18N
                btnCancelar.setText("Cancelar");
                btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                btnCancelar.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        btnCancelarActionPerformed(evt);
                    }
                });
                pn_addInventario.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 420, -1, -1));

                jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jLabel1.setText("Nombre:");
                pn_addInventario.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, -1, -1));

                txt_Puesto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                txt_Puesto.setEnabled(false);
                pn_addInventario.add(txt_Puesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 330, -1));

                jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jLabel2.setText("Puesto:");
                pn_addInventario.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, -1, -1));

                jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jLabel3.setText("Actividad a realizar:");
                pn_addInventario.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

                jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jLabel4.setText("Fecha de salida:");
                pn_addInventario.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

                jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jLabel5.setText("Fecha de llegada:");
                pn_addInventario.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

                jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jLabel8.setText("Lugar:");
                pn_addInventario.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 193, -1, -1));

                lblAviso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
                pn_addInventario.add(lblAviso, new org.netbeans.lib.awtextra.AbsoluteConstraints(466, 228, 15, 233));

                jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jLabel7.setText("Área:");
                pn_addInventario.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

                cmb_Vehiculo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
                cmb_Vehiculo.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        cmb_VehiculoActionPerformed(evt);
                    }
                });
                pn_addInventario.add(cmb_Vehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 20, 190, -1));

                jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jLabel9.setText("Vehiculo:");
                pn_addInventario.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

                jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jLabel10.setText("Kilometraje:");
                pn_addInventario.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, -1, -1));

                txtKilometraje.setEditable(false);
                txtKilometraje.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                pn_addInventario.add(txtKilometraje, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 70, 190, -1));

                jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jLabel11.setText("Descripción:");
                pn_addInventario.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 120, -1, -1));

                txtADescripcion.setEditable(false);
                txtADescripcion.setColumns(20);
                txtADescripcion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                txtADescripcion.setRows(5);
                jScrollPane2.setViewportView(txtADescripcion);

                pn_addInventario.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 150, 280, -1));

                hora_Salida_A.setText("jTextField1");
                pn_addInventario.add(hora_Salida_A, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 100, -1));

                btnEditar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/solicitudes.png"))); // NOI18N
                btnEditar.setText("Editar");
                btnEditar.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        btnEditarActionPerformed(evt);
                    }
                });
                pn_addInventario.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 280, -1, -1));

                jLabel12.setText("*");
                pn_addInventario.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 40, -1));

                jLabel13.setText("*");
                pn_addInventario.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 30, -1));

                jLabel15.setText("*");
                pn_addInventario.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 200, 40, -1));

                jLabel14.setText("*");
                pn_addInventario.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 10, 30));

                jLabel16.setText("*");
                pn_addInventario.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 30, -1));

                jLabel17.setText("*");
                pn_addInventario.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 30, -1));

                jLabel18.setText("*");
                pn_addInventario.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 30, -1));

                jLabel19.setText("* Campo obligatorio");
                pn_addInventario.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 460, -1, -1));

                btnMasMun.setText("+");
                btnMasMun.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        btnMasMunActionPerformed(evt);
                    }
                });
                pn_addInventario.add(btnMasMun, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, 70, -1));

                btnLimpiarLugar.setText("Limpiar");
                btnLimpiarLugar.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        btnLimpiarLugarActionPerformed(evt);
                    }
                });
                pn_addInventario.add(btnLimpiarLugar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 250, 70, -1));

                lblLugar.setEditable(false);
                pn_addInventario.add(lblLugar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 310, -1));

                jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/formularios.png"))); // NOI18N
                pn_addInventario.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 520));

                getContentPane().add(pn_addInventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 520));

                pack();
            }// </editor-fold>//GEN-END:initComponents
    public void maxid(){
        String sql="Select max(idSolicitud) from solicitud_viatico";
        int datos[]=new int[1];
        try{
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                
              datos[0]=rs.getInt("max(idSolicitud)");
            //datos[0]=rs.getString("max(idDatos)");
            varida=datos;
        }
    }catch(SQLException ex){
           javax.swing.JOptionPane.showMessageDialog(null, "Error"); 
        }
    }
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
       if(PrincipalS.conVehiculo==1){
           /*SimpleDateFormat format=new SimpleDateFormat("HH:mm");
           boolean pernoctado=date_Salida.getDate().getDate()==date_Llegada.getDate().getDate() && date_Salida.getDate().getMonth()==date_Llegada.getDate().getMonth() && date_Salida.getDate().getYear()==date_Llegada.getDate().getYear();
           addSolicitudVehiculo asv;
           asv = new addSolicitudVehiculo(this,parentVehiculo,true,comboEmpleados.getSelectedItem()+"",
           txt_Puesto.getText(),date_Salida.getDate(),date_Llegada.getDate(),
           hora_Salida.getValue(),hora_Llegada.getValue(),
           !pernoctado,cmbEstado.getSelectedIndex(),cmbLocalidad.getSelectedIndex(),
           txt_Actividad.getText(),cmbArea.getSelectedItem()+"");
           asv.setVisible(true);*/
           try {
                validarDatos(true,"");
                pernoctado=(date_Salida.getDate().getDate()==date_Llegada.getDate().getDate() && date_Salida.getDate().getMonth()==date_Llegada.getDate().getMonth() && date_Salida.getDate().getYear()==date_Llegada.getDate().getYear())?"No":"Si";
            } catch (ExceptionDatosIncompletos ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
                return;
            }
           if(seleccionarVehiculo){
               verificar_excepcion=true;
               try {
                   validarDatos(true,"");
               } catch (ExceptionDatosIncompletos ex) {
                   JOptionPane.showMessageDialog(this, ex.getMessage());
                   return;
               }

                
                //--------Insertar sol de vehículo---
                int indiceCarro = cmb_Vehiculo.getSelectedIndex();
                try{
                    verificar_excepcion=true;

                    
                    if(indiceCarro==0){
                        JOptionPane.showMessageDialog(this,"-No se ha seleccionado un vehículo");
                        return;
                    }
                    //inserta solicitud
                    insertar_Solicitud_Vehiculo(indiceCarro);

                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(this, "El kilometraje debe ser un numero sin letras.");
                }
                //-----------------------------------
           }else{
               this.setSize(new Dimension(880,520));
               seleccionarVehiculo=true;
               cmb_Vehiculo.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
               cmb_Vehiculo.addItem("Selecione vehiculo...");
               btnEditar.setVisible(true);
               comboEmpleados.setEnabled(false);
                cmbArea.setEnabled(false);
                date_Salida.setEnabled(false);
                date_Llegada.setEnabled(false);
                hora_Salida.setEnabled(false);
                hora_Llegada.setEnabled(false);
                txt_Actividad.setEnabled(false);
                cmbEstado.setEnabled(false);
                cmbLocalidad.setEnabled(false);
                btnAceptar.setText("Aceptar");
               //manager_vehiculo.getVehiculosDisponibles(cmb_Vehiculo);
               try{
                   getAutosDisponibles();
               }catch(SQLException e){

               }
           }
       }else{
           try{
            verificar_excepcion=true;
            validarDatos(true,"");
            
            //inserta solicitud
            insertar_Solicitud(0);
            
        }catch(ExceptionDatosIncompletos e){
            if(verificar_excepcion)JOptionPane.showMessageDialog(this, e.getMessage());
            return;
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "El kilometraje debe ser un numero sin letras.");
        }
       }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        try {
            // TODO add your handling code here:
            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(addSolicitudViaticos.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        //ComboArea
        if(!asignarVehiculo){
            String lista = manager_complemento.obtenerAreas();
            String[] recoger = lista.split(",,");

            cmbArea.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
            for(int i = 1; i <= recoger.length;i = i+2){
                cmbArea.addItem(recoger[i]);
            }

            comboEmpleados.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
            comboEmpleados.addItem("Selecione empleado...");
            manager_users.getNombresEmpleados(comboEmpleados,1);
            if(PrincipalS.conVehiculo==1){
                btnAceptar.setText("Seleccionar vehículo");
            }
        }
    }//GEN-LAST:event_formWindowOpened

    private void comboEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEmpleadosActionPerformed
    // TODO add your handling code here:
        int empleado = comboEmpleados.getSelectedIndex();
        if(empleado > 0){
            txt_Puesto.setText(manager_users.obtenerPuesto(comboEmpleados.getSelectedItem().toString()));
        }else{
            txt_Puesto.setText("");
        }
        
    }//GEN-LAST:event_comboEmpleadosActionPerformed

    private void cmbEstadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEstadoItemStateChanged
        // TODO add your handling code here:
        if(!asignarVehiculo){
            cmbLocalidad.removeAllItems();
            cmbLocalidad.addItem("Seleccione localidad");
            List<String> localidades=cbd.acceder("select L.nombre from Localidad L inner join Estado E on L.estado_idestado=E.idestado where E.nombre='"+cmbEstado.getSelectedItem().toString()+"\r' order by L.nombre;");
            for(int i=0;i<localidades.size();i++){
                cmbLocalidad.addItem(localidades.get(i).split("\r")[0]);
            }
        }
        if(cmbEstado.getSelectedIndex()!=0){
            if(variosEstados==0){
                variosEstados=1;
            }
        }
    }//GEN-LAST:event_cmbEstadoItemStateChanged

    private void cmbAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAreaActionPerformed
        // TODO add your handling code here:
        comboEmpleados.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        comboEmpleados.addItem("Selecione empleado...");
        manager_users.getNombresEmpleados(comboEmpleados,cmbArea.getSelectedIndex()+1);
    }//GEN-LAST:event_cmbAreaActionPerformed

    private void cmb_VehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_VehiculoActionPerformed
        // TODO add your handling code here:
        if(cmb_Vehiculo.getSelectedIndex() != 0){
            String separador [] = cmb_Vehiculo.getSelectedItem().toString().split("_");
            try {
                ResultSet res=cbd.getTabla("select kilometraje from vehiculo_usado where vehiculos_Matricula='"+separador[1]+"'", cn);
                System.out.println("select kilometraje from vehiculo_usado where vehiculos_Matricula='"+separador[1]+"'");
                res.next();
                txtKilometraje.setText(res.getString("kilometraje"));

                res=cbd.getTabla("select observaciones from vehiculos where Matricula='"+separador[1]+"'", cn);
                res.next();
                txtADescripcion.setText(res.getString("observaciones"));
            } catch (SQLException ex) {
                Logger.getLogger(addSolicitudVehiculo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            txtKilometraje.setText("");
            txtADescripcion.setEnabled(false);
            txtADescripcion.setText("");
        }

    }//GEN-LAST:event_cmb_VehiculoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        seleccionarVehiculo=false;
        this.setSize(new Dimension(530,520));
        btnEditar.setVisible(false);
        comboEmpleados.setEnabled(true);
         cmbArea.setEnabled(true);
         date_Salida.setEnabled(true);
         date_Llegada.setEnabled(true);
         hora_Salida.setEnabled(true);
         hora_Llegada.setEnabled(true);
         txt_Actividad.setEnabled(true);
         cmbEstado.setEnabled(true);
         cmbLocalidad.setEnabled(true);
         btnAceptar.setText("Seleccionar vehículo");
    }//GEN-LAST:event_btnEditarActionPerformed

    boolean masMunicipios=false;
    int variosEstados=-1;
    private void btnMasMunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasMunActionPerformed
        // TODO add your handling code here:
        if(cmbLocalidad.getSelectedIndex()>0){
            if(!masMunicipios){
                lblLugar.setText(cmbEstado.getSelectedItem().toString());
                masMunicipios=true;
                variosEstados=0;
            }        
            if(variosEstados==1){
                lblLugar.setText(lblLugar.getText()+"-"+cmbEstado.getSelectedItem().toString()+","+cmbLocalidad.getSelectedItem().toString());
                variosEstados=0;
                lblLugar.setText(ordenarLugar());
                return;
            }
            String[] mun=lblLugar.getText().split(",");
            boolean munAsignados=false;
            for(int i=1;i<mun.length;i++){
                if(cmbLocalidad.getSelectedItem().toString().equals(mun[i])){
                    munAsignados=true;
                }else{
                    if(mun[i].split("-").length>1){
                        if(cmbLocalidad.getSelectedItem().toString().equals(mun[i].split("-")[0])){
                            munAsignados=true;
                        }
                    }
                }
            }
            if(!munAsignados){
                lblLugar.setText(lblLugar.getText()+","+cmbLocalidad.getSelectedItem().toString());
            }
        }
    }//GEN-LAST:event_btnMasMunActionPerformed

    private String ordenarLugar(){
        String[] estados=lblLugar.getText().split("-");
        String cadFinal="";
        String aux=estados[estados.length-1].split(",")[0];
        String auxMun=estados[estados.length-1].split(",")[1];
        boolean estadoRep=false;
        for(int i=0;i<estados.length-1;i++){
            if(aux.equals(estados[i].split(",")[0])){
                estadoRep=true;
                String [] municipios=estados[i].split(",");
                boolean repetido=false;
                for(int j=0;j<municipios.length;j++){
                    if(auxMun.equals(municipios[j])){
                        repetido=true;
                    }
                }
                if(!repetido){
                    estados[i]+=","+auxMun;
                }
            }
        }
        cadFinal+=estados[0];
        for(int i=1;i<estados.length;i++){
            if(!(i==estados.length-1 && estadoRep)){
                cadFinal+="-"+estados[i];
            }
        }
        return cadFinal;
    }
    private void btnLimpiarLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarLugarActionPerformed
        // TODO add your handling code here:
        masMunicipios=false;
        lblLugar.setText("");
        cmbEstado.setEnabled(true);
    }//GEN-LAST:event_btnLimpiarLugarActionPerformed

    private void txt_ActividadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ActividadKeyTyped
        // TODO add your handling code here:
        txt_Actividad.setLineWrap(true);
        txt_Actividad.setWrapStyleWord(true);
    }//GEN-LAST:event_txt_ActividadKeyTyped

    private void hora_LlegadaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_hora_LlegadaStateChanged
        // TODO add your handling code here:
        SimpleDateFormat format=new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String[] hora_salida=format.format(hora_Salida.getValue()).split(":");
        String[] hora_llegada=format.format(hora_Llegada.getValue()).split(":");
        String fecha_Salida=sdf.format(date_Salida.getDate().getTime());
        String fecha_Llegada=sdf.format(date_Llegada.getDate().getTime());
        if(fecha_Salida.equals(fecha_Llegada)){
            if(Integer.parseInt(hora_salida[0])>Integer.parseInt(hora_llegada[0])){
                hora_Llegada.setValue(hora_Salida.getValue());
            }else{
                if(Integer.parseInt(hora_salida[0])==Integer.parseInt(hora_llegada[0])){
                    if(Integer.parseInt(hora_salida[1])>Integer.parseInt(hora_llegada[1])){
                        hora_Llegada.setValue(hora_Salida.getValue());
                    }
                }
            }
        }
    }//GEN-LAST:event_hora_LlegadaStateChanged

    private void hora_SalidaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_hora_SalidaStateChanged
        // TODO add your handling code here:
        SimpleDateFormat format=new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String[] hora_salida=format.format(hora_Salida.getValue()).split(":");
        String[] hora_llegada=format.format(hora_Llegada.getValue()).split(":");
        String fecha_Salida=sdf.format(date_Salida.getDate().getTime());
        String fecha_Llegada=sdf.format(date_Llegada.getDate().getTime());
        if(fecha_Salida.equals(fecha_Llegada)){
            if(Integer.parseInt(hora_salida[0])>Integer.parseInt(hora_llegada[0])){
                hora_Llegada.setValue(hora_Salida.getValue());
            }else{
                if(Integer.parseInt(hora_salida[0])==Integer.parseInt(hora_llegada[0])){
                    if(Integer.parseInt(hora_salida[1])>Integer.parseInt(hora_llegada[1])){
                        hora_Llegada.setValue(hora_Salida.getValue());
                    }
                }
            }
        }
    }//GEN-LAST:event_hora_SalidaStateChanged
    public void insertar_Solicitud(int ConCarro){
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String fecha_Salida=sdf.format(date_Salida.getDate().getTime());
            String fecha_Llegada=sdf.format(date_Llegada.getDate().getTime());
            Conexion conexion=new Conexion();
            //conexion.getConexion();
            String carro = "Sin vehiculo";
            
            
            //Inserción de solicitud
            //Con carro
            conexion.getConexion();
            boolean pernoctado=date_Salida.getDate().getDate()==date_Llegada.getDate().getDate() && date_Salida.getDate().getMonth()==date_Llegada.getDate().getMonth() && date_Salida.getDate().getYear()==date_Llegada.getDate().getYear();
            String perString="";
            if(!pernoctado){
                perString="Si";
            }else{
                perString="No";
            }
            String lugarViatico="";
            if(!masMunicipios){
                lugarViatico=cmbEstado.getSelectedItem().toString()+","+cmbLocalidad.getSelectedItem().toString();
            }else{
                lugarViatico=lblLugar.getText();
            }
            SimpleDateFormat format=new SimpleDateFormat("h:mm:ss a");
            boolean insersion = insersion=conexion.ejecutar("insert into Solicitud_viatico (Fecha_Salida,Lugar,Nombre,Actividad,Pernoctado,Puesto,Fecha_Llegada,Estado,Reporte,Hora_Llegada,Hora_Salida,gastos_comprobar,consejero_presidente) values('"+fecha_Salida+"','"+lugarViatico+"'"
                + ",'"+comboEmpleados.getSelectedItem().toString()+"','"+txt_Actividad.getText()+"','"+perString+"','"+txt_Puesto.getText()+"','"+fecha_Llegada+"','P','0','"+format.format((Date)hora_Llegada.getValue())+"','"+format.format((Date)hora_Salida.getValue())+"','','')");
            
            if(insersion){
                JOptionPane.showMessageDialog(this, "Solicitud de viáticos registrada");
                PrincipalS.tablasolic.setModel(manager_viaticos.getTasol());
                this.setVisible(false);
            }else{
                JOptionPane.showMessageDialog(this, "Error al insertar");
            }
        }catch(Exception e){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String carro = "Sin vehiculo";
            
            String pernoctado="No";
            SimpleDateFormat format=new SimpleDateFormat("HH:mm");
            String fecha_Salida=sdf.format(date_Salida.getDate().getTime());
            String fecha_Llegada=sdf.format(date_Llegada.getDate().getTime());
            String lugarViatico="";
            if(!masMunicipios){
                lugarViatico=cmbEstado.getSelectedItem().toString()+","+cmbLocalidad.getSelectedItem().toString();
            }else{
                lugarViatico=lblLugar.getText();
            }
            System.out.print("insert into Solicitud_viatico (Fecha_Salida,Lugar,Nombre,Actividad,Pernoctado,Vehiculo,Puesto,Fecha_Llegada,Estado,Reporte,Hora_Llegada,Hora_Salida) values('"+fecha_Salida+"','"+lugarViatico+"'"
                + ",'"+comboEmpleados.getSelectedItem().toString()+"','"+txt_Actividad.getText()+"','"+pernoctado+"','"+carro+"'"
                + ",'"+txt_Puesto.getText()+"','"+fecha_Llegada+"','P','0','"+format.format((Date)hora_Llegada.getValue())+"','"+format.format((Date)hora_Salida.getValue())+"')");
        }
    }
    //Función para validar los datos que se insertan en el formulario.
    public void validarDatos(boolean verificar_fecha,String cad)throws ExceptionDatosIncompletos{
        try{
            if(verificar_fecha && date_Llegada.getDate().before(date_Salida.getDate())){
                if(cad.equals("")){
                    cad+="-La fecha de salida es mayor que la de llegada";
                }
                else{
                    cad+="\n-La fecha de salida es mayor que la de llegada";
                }
            }
        }catch(NullPointerException e){
            if(cad.equals("")){
                cad+="-No se ha insertado alguna de las fechas, inserte las fechas de salida y de llegada";
            }
            else{
                cad+="\n-No se ha insertado alguna de las fechas, inserte las fechas de salida y de llegada";
            }
            try{
                verificar_excepcion=false;
                validarDatos(false,cad);
                return;
            }catch(ExceptionDatosIncompletos ex){
                JOptionPane.showMessageDialog(this, ex.getMessage());
                return;
            }
        }
        if(txt_Actividad.getText().equals("")){
            if(cad.equals("")){
                cad+="-No se ha insertado ninguna actividad, escriba la acitividad y vuelva a intentarlo";
            }
            else{
                cad+="\n-No se ha insertado ninguna actividad, escriba la acitividad y vuelva a intentarlo";
            }
        }
        if(cmbEstado.getSelectedItem().toString().equals("Seleccione estado") || cmbLocalidad.getSelectedItem().toString().equals("Seleccione localidad")){
            if(cad.equals("")){
                cad+="-No se ha insertado el lugar, seleccione el lugar y vuelva a intentarlo";
            }
            else{
                cad+="\n-No se ha insertado el lugar, seleccione el lugar y vuelva a intentarlo";
            }
        }
        if(comboEmpleados.getSelectedItem().toString().equals("Selecione empleado...")){
            if(cad.equals("")){
                cad+="-No se ha seleccionado el empleado, seleccione uno de los empleados y vuelva a intentarlo";
            }
            else{
                cad+="\n-No se ha seleccionado el empleado, seleccione uno de los empleados y vuelva a intentarlo";
            }
        }
        if(txt_Puesto.getText().equals("")){
            if(cad.equals("")){
                cad+="-No se ha insertado el puesto del empleado, escriba el nombre del empleado y vuelva a intentarlo";
            }
            else{
                cad+="\n-No se ha insertado el puesto del empleado, escriba el nombre del empleado y vuelva a intentarlo";
            }
        }
        if(!cad.equals("")){
            throw new ExceptionDatosIncompletos(cad);
        }else{
            return;
        }
    }
    
    private boolean verificar_excepcion=true;
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                addSolicitudViaticos dialog = new addSolicitudViaticos(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    //--------------------------METODOS PARA SOLICITUD DE VEHÍCULOS-------------
    private void getAutosDisponibles() throws SQLException{
        ResultSet res;
        Connection cn=cbd.getConexion();
            res=cbd.getTabla("select marca,matricula from vehiculos where Estatus='Activo'",cn);
            SimpleDateFormat format=new SimpleDateFormat("h:mm a");
            List<String> autos=new ArrayList<String>();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            //List<Integer> autos_disponibles=getAutosDisponiblesFecha(sdf.format(date_Salida.getDate().getTime()),format.format((Date)hora_Salida.getValue())+"");
            //List<String> autos_noDisponibles=getAutosNoDisponibles(autos_disponibles,sdf.format(date_Salida.getDate().getTime()));
            List<String> autos_noDisponibles;
            if(asignarVehiculo){
                autos_noDisponibles=vehiculosNoDisp(sdf.format(date_Salida.getDate().getTime()),sdf.format(date_Llegada.getDate().getTime()),hora_Salida_A.getText(),hora_Llegada_A.getText());
            }else{
                autos_noDisponibles=vehiculosNoDisp(sdf.format(date_Salida.getDate().getTime()),sdf.format(date_Llegada.getDate().getTime()),format.format((Date)hora_Salida.getValue()),format.format((Date)hora_Llegada.getValue()));
            }
            while(res.next()){
                boolean disponible=true;//se cambia a false si el registro está en los autos no disponibles
                String aux=res.getString("marca")+"_"+res.getString("matricula");
                System.out.println(aux);
                //Verificamos si el auto se encuentra en el registro de autos no disponibles
                for(int i=0;i<autos_noDisponibles.size();i++){////ERROR AQUI, INSERTA TODOS AUNQUE NO ESTÉN DISPONIBLES.
                    if(res.getString("matricula").equals(autos_noDisponibles.get(i))){
                        disponible=false;
                        i=autos_noDisponibles.size();
                    }
                }
                
                if(disponible){
                    autos.add(aux);
                }
                /*if(autos_noDisponibles.size()==0){
                    autos.add(aux);
                }*/
            }
            try{
                sdf=new SimpleDateFormat("dd-MM-yyyy");
                autos=solicitud_pase(autos,sdf.format(date_Salida.getDate().getTime()));
            }catch(SQLException e){}
            for(int i=0;i<autos.size();i++){
                cmb_Vehiculo.addItem(autos.get(i));
            }
            
    }
    private List<String> solicitud_pase(List<String> autos,String fecha) throws SQLException{
        ResultSet rs=cbd.getTabla("select * from solicitud_pase where fecha='"+fecha+"'", null);
        while(rs.next()){
            if(rs.getString("vehiculo_estado").equals("Ocupado")){
                for(int i=0;i<autos.size();i++){
                    if(rs.getString("vehiculo_pase").split(" ")[0].equals(autos.get(i).split("_")[1])){
                        autos.remove(i);
                    }
                }
            }
        }
        return autos;
    }
    private List<String> getAutosNoDisponibles(List<Integer> id,String fecha)throws SQLException{
        //Buscamos entre las solicitudes de vehículo los vehiculos que no están disponibles
        //a partir de los que ya se han validado que esten dispnibles
        Connection connection=cbd.getConexion();
        ResultSet res;
        //Obtenemos todos los vehiculos que tienen solicitud de vehiculo a partir de la fecha de salida solicitada
        res=cbd.getTabla("select idvehiculo_usado,vehiculos_Matricula from vehiculo_usado inner join solicitud_vehiculo on idvehiculo_usado=vehiculo_usado_idvehiculo_usado inner join vehiculo_viatico on idsolicitud_vehiculo=solicitud_vehiculo_idSolicitud_vehiculo inner join solicitud_viatico on solicitud_viatico_idSolicitud=idSolicitud inner join vehiculos V on vehiculos_Matricula=V.matricula where fecha_salida>='"+fecha+"' and V.Estatus='Activo';", connection);
        List<String> matricula_nodisponible=new ArrayList<String>();
        while(res.next()){
            boolean disponible=false;//Si el registro no está en los autos disponibles entonces no se puede utilizar.
            //Recorremos todos los vehiculos disponibles y los restantes los ponemos como no disponibles
            for(int i=0;i<id.size();i++){
                if(res.getInt("idvehiculo_usado")==id.get(i)){
                    disponible=true;
                    i=id.size();
                }
            }
            //si disponible es false agregamos ese vehiculo a vehiculos no disponibles
            if(!disponible){
                matricula_nodisponible.add(res.getString("vehiculos_Matricula"));
            }
        }
            
        return matricula_nodisponible;
    }
    private List<Integer> getAutosDisponiblesFecha(String fecha_solicitada,String hora_solicitada)
    throws SQLException{
        //Asigna al combo box los vehiculos disponibles entre fecha de salida y de llegada
        Connection connection=cbd.getConexion();
        List<Integer> datos=new ArrayList<Integer>();
        ResultSet res;
        Date fechaActual=new Date();
        int year=fechaActual.getYear()+1900;
        res=cbd.getTabla("select fecha_salida,Fecha_Llegada,hora_llegada,vehiculo_usado_idvehiculo_usado from solicitud_vehiculo inner join vehiculo_viatico on idsolicitud_vehiculo=solicitud_vehiculo_idSolicitud_vehiculo inner join solicitud_viatico on solicitud_viatico_idSolicitud=idSolicitud where estado !='C' and fecha_llegada>='"+year+
                "-"+fechaActual.getMonth()+"-"+fechaActual.getDate()+"'",connection);
        String fcad="select fecha_salida,Fecha_Llegada,hora_llegada,vehiculo_usado_idvehiculo_usado from solicitud_vehiculo inner join vehiculo_viatico on idsolicitud_vehiculo=solicitud_vehiculo_idSolicitud_vehiculo inner join solicitud_viatico on solicitud_viatico_idSolicitud=idSolicitud where estado !='C' and fecha_llegada>='"+year+
                "-"+fechaActual.getMonth()+"-"+fechaActual.getDate()+"'";
        //En caso de que sea la primera solicitud
        if(!res.next()){
            datos=obtenerTodosVehiculos();
        }
        //Recorremos todos los registros para obtener los vehiculos que si podemos solicitar
        res=cbd.getTabla("select fecha_salida,Fecha_Llegada,hora_llegada,vehiculo_usado_idvehiculo_usado from solicitud_vehiculo inner join vehiculo_viatico on idsolicitud_vehiculo=solicitud_vehiculo_idSolicitud_vehiculo inner join solicitud_viatico on solicitud_viatico_idSolicitud=idSolicitud where estado !='C' and fecha_llegada>='"+year+
                "-"+fechaActual.getMonth()+"-"+fechaActual.getDate()+"'",connection);
        while(res.next()){
            //Obtenemos la fehca de llegada y de salida del registro de la solicitud
            String fecha_salida_string=res.getString("fecha_salida");
            String fecha_llegada_string=res.getString("fecha_llegada");
            //-------------------------------------------
            //Verificamos si la fecha solicitada es antes o después de las fechas de la solicitud
            if(valida_fecha(fecha_solicitada,fecha_llegada_string)==2 || valida_fecha(fecha_solicitada,fecha_salida_string)==0){
                //Si la fecha solicitada no afecta a las de la solicitud, entonces este vehiculo está disponible
                datos.add(res.getInt("vehiculo_usado_idvehiculo_usado"));
            }
            //-----------------------------------
            //Verificamos si la fecha solicitada es la misma que la de llegada del vehículo
            if(valida_fecha(fecha_solicitada,fecha_llegada_string)==1){
                //Tenemos que verificar la hora de salida con la hora de llegada del vehículo
                //Separamos las horas de llegada en hora,minuto,pm o am
                String[] hora_llegada_string=res.getString("hora_llegada").split(":");
                int hora_llegada=Integer.parseInt(hora_llegada_string[0]);
                int minuto_llegada=Integer.parseInt(hora_llegada_string[1]);
                //------------------------------------------------------------
                String horario=hora_llegada_string[2].split(" ")[1];//Obtenemos si es am o pm
                //si es pm sumamos 12 horas para tener la horas en sistema de 24 horas
                if(horario.equals("PM")){
                    hora_llegada+=12;
                }else{
                    //Si es am entonces verificamos si la hora es a media noche
                    if(hora_llegada==12){
                        //Si es media noche ponemos la hora en 0
                        hora_llegada=0;
                    }
                }
                //--------------------
                //Separamos la hora solicitada en hora,minuto, pm o am
                String[] hora_solicitada_string=hora_solicitada.split(":");
                int hora_solic=Integer.parseInt(hora_solicitada_string[0]);
                int minuto_solic=Integer.parseInt(hora_solicitada_string[1]);
                //------------------------------------------------------------
                String horario_solic=hora_solicitada_string[2].split(" ")[1];//Obtenemos si es am o pm
                //si es pm sumamos 12 horas para tener la horas en sistema de 24 horas
                if(horario_solic.equals("PM")){
                    hora_solic+=12;
                }else{
                    //Si es am comparamos si es media noche
                    if(hora_solic==12){
                        //Si es media noche ponemos la hora en 0.
                        hora_solic=0;
                    }
                }
                //-----------------
                
                //Comparamos las horas y si la solicitada es mayor a la de llegada, entonces el vehículo está disponible
                if(hora_solic>hora_llegada){
                    datos.add(res.getInt("vehiculo_usado_idvehiculo_usado"));
                }else{
                    if(hora_solic==hora_llegada){
                        //Si la hora de llegada es igua a la solicitada, entonces comparamos los minutos
                        //Comparamos los minutos, si el minuto solicitado es mayor que el de llegada, entonces el vehículo está disponible
                        if(minuto_solic>minuto_llegada){
                            datos.add(res.getInt("vehiculo_usado_idvehiculo_usado"));
                        }
                    }
                }
            }
            //------------------------------------
        }
        //-----------------------------
        return datos;//Regresamos la lista de vehiculos disponibles por fecha
    }
    private List<Integer> obtenerTodosVehiculos() {
        List<Integer> autos=new ArrayList<Integer>();
        //Obtenermos todos los vehiculos existentes
        try{
            ResultSet res=cbd.getTabla("select idvehiculo_usado from vehiculo_usado;", cn);
            while(res.next()){
                autos.add(Integer.parseInt(res.getString("idvehiculo_usado")));
            }
        }catch(SQLException e){}
        return autos;
    }
    private int valida_fecha(String fecha1,String fecha2){
        int[] fechas=separarFecha(fecha1,fecha2);
        int year1=fechas[0];
        int month1=fechas[1];
        int day1=fechas[2];
        int year2=fechas[3];
        int month2=fechas[4];
        int day2=fechas[5];
        if(fecha_antes(year1,month1,day1,year2,month2,day2)){
            return 0;
        }
        if(fecha_igual(year1,month1,day1,year2,month2,day2)){
            return 1;
        }
        if(fecha_despues(year1,month1,day1,year2,month2,day2)){
            return 2;
        }
        return -1;
    }
    private int[] separarFecha(String fecha1,String fecha2){
        //Seaparamos las 2 fechas en año, mes y día y los convertimos a entero
        String[] fecha1_array=fecha1.split("-");
        String[] fecha2_array=fecha2.split("-");
        int[] aux=new int[fecha1_array.length+fecha2_array.length];
        aux[0]=Integer.parseInt(fecha1_array[0]);
        aux[1]=Integer.parseInt(fecha1_array[1]);
        aux[2]=Integer.parseInt(fecha1_array[2]);
        aux[3]=Integer.parseInt(fecha2_array[0]);
        aux[4]=Integer.parseInt(fecha2_array[1]);
        aux[5]=Integer.parseInt(fecha2_array[2]);
        return aux;
    }
    private boolean fecha_despues(int year1,int month1,int day1,int year2,int month2,int day2){
        //Validar si la primera fecha es despues de la segunda
        if(year1>year2){
            return true;
        }else{
            if(year1==year2){
                if(month1>month2){
                    return true;
                }else{
                    if(month1==month2){
                        if(day1>day2){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private boolean fecha_igual(int year1,int month1,int day1,int year2,int month2,int day2){
        //Valida si las dos fechas son iguales
        if(year1==year2 && month1==month2 && day1==day2){
            return true;
        }
        return false;
    }
    private boolean fecha_antes(int year1,int month1,int day1,int year2,int month2,int day2){
        if(year1<year2){
            return true;
        }else{
            if(year1==year2){
                if(month1<month2){
                    return true;
                }else{
                    if(month1==month2){
                        if(day1<day2){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public void insertar_Solicitud_Vehiculo(int ConCarro){
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String fecha_Salida=sdf.format(date_Salida.getDate().getTime());
            String fecha_Llegada=sdf.format(date_Llegada.getDate().getTime());
            Conexion conexion=new Conexion();
            //conexion.getConexion();
            String[] arr=cmb_Vehiculo.getSelectedItem().toString().split("_");
            ResultSet res=cbd.getTabla("select idvehiculo_usado from vehiculo_usado where vehiculo_usado.vehiculos_Matricula='"+arr[1]+"';", cn);
            System.out.println("select idvehiculo_usado from vehiculo_usado where vehiculo_usado.vehiculos_Matricula='"+arr[1]+"';");
            res.next();
            String idVehiculo_usado=res.getString("idvehiculo_usado");
            //Inserción de solicitud
            SimpleDateFormat format=new SimpleDateFormat("h:mm:ss a");
            boolean insersion;
            if(!asignarVehiculo){
                String lugarViatico="";
                if(!masMunicipios){
                    lugarViatico=cmbEstado.getSelectedItem().toString()+","+cmbLocalidad.getSelectedItem().toString();
                }else{
                    lugarViatico=lblLugar.getText();
                }
                insersion = insersion=cbd.ejecutar("insert into Solicitud_viatico (Fecha_Salida,Lugar,Nombre,Actividad,Pernoctado,Puesto,Fecha_Llegada,Estado,Reporte,Hora_Llegada,Hora_Salida) values('"+fecha_Salida+"','"+
                    lugarViatico+"'"
                + ",'"+comboEmpleados.getSelectedItem().toString()+"','"+txt_Actividad.getText()+"','"+pernoctado+"','"+txt_Puesto.getText()+"','"+fecha_Llegada+"','P','0','"+format.format((Date)hora_Llegada.getValue())+"','"+format.format((Date)hora_Salida.getValue())+"')");
            }
            insersion=cbd.ejecutar("insert into solicitud_vehiculo(vehiculo_usado_idvehiculo_usado,vehiculo,chofer,viatico_vehiculo)values("+idVehiculo_usado+",'"+arr[0]+"','1','"+PrincipalS.viatico_vehiculo+"')");
            res=cbd.getTabla("select idSolicitud from solicitud_viatico where Actividad='"+txt_Actividad.getText()+"' and Nombre='"+comboEmpleados.getSelectedItem().toString()+"' and fecha_salida='"+fecha_Salida+"' and fecha_llegada='"+fecha_Llegada+"';", cn);
            res.next();
            String idSolViatico=res.getString("idSolicitud");
            res=cbd.getTabla("select idSolicitud_vehiculo from solicitud_vehiculo order by idSolicitud_vehiculo DESC", cn);
            res.next();
            String idSolVehiculo=res.getString("idSolicitud_vehiculo");
            insersion=cbd.ejecutar("insert into vehiculo_viatico(solicitud_vehiculo_idSolicitud_vehiculo,solicitud_viatico_idSolicitud,agregado)values("+idSolVehiculo+","+idSolViatico+",'0')");
            
            if(insersion){
                JOptionPane.showMessageDialog(this, "Insersión correcta");
                PrincipalS.tablasolic.setModel(manager_viaticos.getTasol());
                //this.setVisible(false);
                this.dispose();
            }else{
                System.out.println("insert into Solicitud_vehiculo (Fecha_Salida,Lugar,Nombre,Actividad,Pernoctado,Vehiculo,Puesto,Fecha_Llegada,Estado,Reporte,Hora_Llegada,Hora_Salida,vehiculo_usado_idvehiculo_usado) values('"+fecha_Salida+"','"+
                        cmbLocalidad.getSelectedItem().toString()+","+cmbEstado.getSelectedItem().toString()+"'"
                + ",'"+comboEmpleados.getSelectedItem().toString()+"','"+txt_Actividad.getText()+"','"+pernoctado+"','"+cmb_Vehiculo.getSelectedItem().toString()+"'"
                + ",'"+txt_Puesto.getText()+"','"+fecha_Llegada+"','P','0','"+format.format((Date)hora_Llegada.getValue())+"','"+format.format((Date)hora_Salida.getValue())+"',"+idVehiculo_usado+")");
                JOptionPane.showMessageDialog(this, "Error al insertar pero no excepción");
            }
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    
    
    public List<String> vehiculosNoDisp(String fss,String fls,String hss,String hls) throws SQLException{
        List<String> autos=new ArrayList<String>();
        List<String[]> solicitudes=new ArrayList<String[]>();
        
        ResultSet rs=cbd.getTabla("select * from solicitud_vehiculo SV inner join vehiculo_viatico VV on SV.idsolicitud_vehiculo=VV.solicitud_vehiculo_idsolicitud_vehiculo inner join solicitud_viatico SVI on VV.solicitud_viatico_idSolicitud=SVI.idSolicitud inner join vehiculo_usado VU on SV.vehiculo_usado_idvehiculo_usado=VU.idvehiculo_usado where (fecha_salida>='"+fss+"' or fecha_llegada>='"+fls+"') and estado!='C'", cn);
        while(rs.next()){
            String[] aux=new String[5];
            aux[0]=rs.getString("fecha_salida");
            aux[1]=rs.getString("fecha_llegada");
            aux[2]=rs.getString("hora_salida");
            aux[3]=rs.getString("hora_llegada");
            aux[4]=rs.getString("vehiculos_Matricula");
            solicitudes.add(aux);
        }
        for(int i=0;i<solicitudes.size();i++){
            if(valida_fecha(fss,solicitudes.get(i)[0])==2){
                if(!(valida_fecha(fss,solicitudes.get(i)[1])==2)){
                    if(valida_fecha(fss,solicitudes.get(i)[1])==1){
                        if(valida_hora(hss,solicitudes.get(i)[3])==0){
                            autos.add(solicitudes.get(i)[4]);
                        }
                    }else{
                        autos.add(solicitudes.get(i)[4]);
                    }
                }
            }else{
                if(valida_fecha(fss,solicitudes.get(i)[0])==0){
                    if(!(valida_fecha(fls,solicitudes.get(i)[0])==0 || valida_fecha(fls,solicitudes.get(i)[0])==1)){
                        autos.add(solicitudes.get(i)[4]);
                    }else{
                        if(valida_fecha(fls,solicitudes.get(i)[0])==1){
                            if(valida_hora(hls,solicitudes.get(i)[2])==2){
                                autos.add(solicitudes.get(i)[4]);
                            }
                        }
                    }
                }else{
                    if(valida_fecha(fss,solicitudes.get(i)[0])==1){
                        if(valida_fecha(fls,solicitudes.get(i)[0])==1){
                            if(!(valida_hora(hss,solicitudes.get(i)[2])==0 && (!(valida_hora(hls,solicitudes.get(i)[2])==2)))||(valida_hora(hss,solicitudes.get(i)[3])==2)){
                                autos.add(solicitudes.get(i)[4]);
                            }
                        }else{
                            if(!(valida_fecha(fls,solicitudes.get(i)[1])==2)){
                                autos.add(solicitudes.get(i)[4]);
                            }else{
                                if(valida_fecha(solicitudes.get(i)[0],solicitudes.get(i)[1])==1){
                                    if(valida_hora(hss,solicitudes.get(i)[3])==0){
                                        autos.add(solicitudes.get(i)[4]);
                                    }
                                }else{
                                    if(!(valida_fecha(fss,solicitudes.get(i)[1])==0)){
                                        if(valida_fecha(fss,solicitudes.get(i)[1])==1){
                                            if(valida_hora(hss,solicitudes.get(i)[3])==0){
                                                autos.add(solicitudes.get(i)[4]);
                                            }
                                        }
                                    }else{
                                        autos.add(solicitudes.get(i)[4]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return autos;
    }
    public int valida_hora(String hora1,String hora2){
        String[] hora1_split=hora1.split(":");
        String[] hora2_split=hora2.split(":");
        int hora1_num;
        int hora2_num;
        hora1_num=Integer.parseInt(hora1_split[0]);
        hora2_num=Integer.parseInt(hora2_split[0]);
        if(hora1_split[1].split(" ")[1].equals("PM")){
            hora1_num+=12;
        }
        if(hora2_split[2].split(" ")[1].equals("PM")){
            hora2_num+=12;
        }
        String aux1=hora1_num+"";
        String aux2=hora2_num+"";
        hora1_num=Integer.parseInt(aux1+hora1_split[1].split(" ")[0]);
        hora2_num=Integer.parseInt(aux2+hora2_split[2].split(" ")[0]);
        if(hora1_num<hora2_num){
            return 0;
        }
        if(hora1_num==hora2_num){
            return 1;
        }
        if(hora1_num>hora2_num){
            return 2;
        }
        return -1;
    }
    
    
    
    ///-------------------------------------------------------------------------
    
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnLimpiarLugar;
    private javax.swing.JButton btnMasMun;
    private javax.swing.JComboBox cmbArea;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<String> cmbLocalidad;
    private javax.swing.JComboBox cmb_Vehiculo;
    private javax.swing.JComboBox<String> comboEmpleados;
    private com.toedter.calendar.JDateChooser date_Llegada;
    private com.toedter.calendar.JDateChooser date_Salida;
    private javax.swing.JSpinner hora_Llegada;
    private javax.swing.JTextField hora_Llegada_A;
    private javax.swing.JSpinner hora_Salida;
    private javax.swing.JTextField hora_Salida_A;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JTextField lblLugar;
    private javax.swing.JPanel pn_addInventario;
    private javax.swing.JTextArea txtADescripcion;
    private javax.swing.JTextField txtKilometraje;
    private javax.swing.JTextArea txt_Actividad;
    private javax.swing.JTextField txt_Puesto;
    // End of variables declaration//GEN-END:variables
}
