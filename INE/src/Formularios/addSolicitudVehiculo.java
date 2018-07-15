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

import Interfaces.PrincipalS;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Frame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author usuario
 */
public class addSolicitudVehiculo extends javax.swing.JDialog {
    ManagerSoViaticos manager_viaticos;
    ManagerUsers manager_users;
    ManagerVehiculos manager_vehiculo;
    List<Object> datos=new ArrayList<Object>();
    
    public int varida[];
    Conexion cbd=new Conexion();
    Connection cn=cbd.getConexion();
    public static boolean imprimirSolicitud=false;
    JDialog parent;
    /**
     * Creates new form addSolicitudViaticos
     */
    public addSolicitudVehiculo(javax.swing.JDialog parent,java.awt.Frame parentVehiculo, boolean modal,
            int empleado,String puesto,Date fecha_salida,Date fecha_llegada,
            Object hora_salida,Object hora_llegada,boolean pernoctado,int estado,
            int municipio,String actividad){
        super(parentVehiculo, modal);
        this.parent=parent;
        initComponents();
        JTextFieldDateEditor date_Salida_Editor=(JTextFieldDateEditor) date_Salida.getDateEditor();
        JTextFieldDateEditor date_Llegada_Editor=(JTextFieldDateEditor) date_Llegada.getDateEditor();
        date_Salida.getJCalendar().setMinSelectableDate(new Date()); // sets today as minimum selectable date
        date_Llegada.getJCalendar().setMinSelectableDate(new Date());
        date_Salida_Editor.setEditable(false);
        date_Llegada_Editor.setEditable(false);
        
        
        //maxid();
        //txtid.setText(varida[0]+1+"");
        manager_viaticos = new ManagerSoViaticos();
        manager_users = new ManagerUsers();
        manager_vehiculo = new ManagerVehiculos();
          
        
        iniciarEstados();
        
        AutoCompleteDecorator.decorate(this.comboEmpleados);
        AutoCompleteDecorator.decorate(this.cmb_Vehiculo);
        
        
        /////////////////////////////////
        datos.add(empleado);
        datos.add(puesto);
        datos.add(fecha_salida);
        datos.add(fecha_llegada);
        datos.add(hora_salida);
        datos.add(hora_llegada);
        datos.add(pernoctado);
        datos.add(estado);
        datos.add(municipio);
        datos.add(actividad);
    }
    private void agregarDatos(){
        comboEmpleados.setSelectedIndex((int)datos.get(0));//Se agrega el empleado
        txt_Puesto.setText(datos.get(1)+"");
        date_Salida.setDate((Date)datos.get(2));
        date_Llegada.setDate((Date)datos.get(3));
        hora_Salida.setValue(datos.get(4));
        hora_Llegada.setValue(datos.get(5));
        chb_Pernoctado.setSelected((boolean)datos.get(6));
        cmbEstado.setSelectedIndex((int)datos.get(7));
        cmbLocalidad.setSelectedIndex((int)datos.get(8));
        txt_Actividad.setText(datos.get(9)+"");
        
    }
    public addSolicitudVehiculo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        JTextFieldDateEditor date_Salida_Editor=(JTextFieldDateEditor) date_Salida.getDateEditor();
        JTextFieldDateEditor date_Llegada_Editor=(JTextFieldDateEditor) date_Llegada.getDateEditor();
        date_Salida.getJCalendar().setMinSelectableDate(new Date()); // sets today as minimum selectable date
        date_Llegada.getJCalendar().setMinSelectableDate(new Date());
        date_Salida_Editor.setEditable(false);
        date_Llegada_Editor.setEditable(false);
        
        
        //maxid();
        //txtid.setText(varida[0]+1+"");
        manager_viaticos = new ManagerSoViaticos();
        manager_users = new ManagerUsers();
        manager_vehiculo = new ManagerVehiculos();
          
        
        iniciarEstados();
        
        AutoCompleteDecorator.decorate(this.comboEmpleados);
        AutoCompleteDecorator.decorate(this.cmb_Vehiculo);
        
    }
    private void iniciarEstados(){
        List<String> estados=cbd.acceder("select nombre from estado;");
        for(int i=0;i<estados.size();i++){
            cmbEstado.addItem(estados.get(i));
        }
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
        jLabel1 = new javax.swing.JLabel();
        txt_Puesto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_Actividad = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblAviso = new javax.swing.JLabel();
        cmbLocalidad = new javax.swing.JComboBox<>();
        cmbEstado = new javax.swing.JComboBox<>();
        date_Salida = new com.toedter.calendar.JDateChooser();
        date_Llegada = new com.toedter.calendar.JDateChooser();
        chb_Pernoctado = new javax.swing.JCheckBox();
        comboEmpleados = new javax.swing.JComboBox<>();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cmb_Vehiculo = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        txtKilometraje = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtADescripcion = new javax.swing.JTextArea();
        Date date=new Date();
        SpinnerDateModel sdm2=new SpinnerDateModel(date,null
            ,null,Calendar.HOUR_OF_DAY);
        hora_Llegada = new javax.swing.JSpinner(sdm2);
        SpinnerDateModel sdm=new SpinnerDateModel(date,null
            ,null,Calendar.HOUR_OF_DAY);
        hora_Salida = new javax.swing.JSpinner(sdm);
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nueva Solicitud");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pn_addInventario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pn_addInventario.setPreferredSize(new java.awt.Dimension(1150, 500));
        pn_addInventario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Nombre:");
        pn_addInventario.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 18, -1, -1));

        txt_Puesto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_Puesto.setEnabled(false);
        pn_addInventario.add(txt_Puesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(154, 45, 215, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Puesto:");
        pn_addInventario.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 50, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Actividad a realizar:");
        pn_addInventario.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 228, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Fecha de salida:");
        pn_addInventario.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 91, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Fecha de llegada:");
        pn_addInventario.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 127, -1, -1));

        txt_Actividad.setColumns(20);
        txt_Actividad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_Actividad.setRows(5);
        txt_Actividad.setEnabled(false);
        jScrollPane1.setViewportView(txt_Actividad);

        pn_addInventario.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 251, 420, 209));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Lugar:");
        pn_addInventario.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 193, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Pernoctado:");
        pn_addInventario.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 157, -1, -1));

        lblAviso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pn_addInventario.add(lblAviso, new org.netbeans.lib.awtextra.AbsoluteConstraints(446, 228, 15, 233));

        cmbLocalidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione localidad" }));
        cmbLocalidad.setEnabled(false);
        pn_addInventario.add(cmbLocalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, 230, -1));

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione estado" }));
        cmbEstado.setEnabled(false);
        cmbEstado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbEstadoItemStateChanged(evt);
            }
        });
        pn_addInventario.add(cmbEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, -1, -1));

        date_Salida.setEnabled(false);
        pn_addInventario.add(date_Salida, new org.netbeans.lib.awtextra.AbsoluteConstraints(154, 81, 215, -1));
        date_Salida.getDateEditor().addPropertyChangeListener(
            new java.beans.PropertyChangeListener() {
                @Override
                public void propertyChange(java.beans.PropertyChangeEvent e) {
                    if(e.getPropertyName().equals("date")) {
                        date_Llegada.getJCalendar().setMinSelectableDate(date_Salida.getDate());
                    }
                }
            });

            date_Llegada.setEnabled(false);
            pn_addInventario.add(date_Llegada, new org.netbeans.lib.awtextra.AbsoluteConstraints(154, 117, 215, -1));

            chb_Pernoctado.setEnabled(false);
            pn_addInventario.add(chb_Pernoctado, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 157, -1, -1));

            comboEmpleados.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            comboEmpleados.setEnabled(false);
            comboEmpleados.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    comboEmpleadosActionPerformed(evt);
                }
            });
            pn_addInventario.add(comboEmpleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(154, 14, -1, -1));

            btnAceptar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
            btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/aceptar.png"))); // NOI18N
            btnAceptar.setText("Aceptar");
            btnAceptar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAceptarActionPerformed(evt);
                }
            });
            pn_addInventario.add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 450, -1, -1));

            btnCancelar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
            btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cancelar.png"))); // NOI18N
            btnCancelar.setText("Cancelar");
            btnCancelar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnCancelarActionPerformed(evt);
                }
            });
            pn_addInventario.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(814, 450, -1, -1));

            jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
            jLabel7.setText("Vehiculo:");

            cmb_Vehiculo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            cmb_Vehiculo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    cmb_VehiculoActionPerformed(evt);
                }
            });

            jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
            jLabel9.setText("Kilometraje:");

            txtKilometraje.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
            txtKilometraje.setEnabled(false);

            jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
            jLabel11.setText("Descripción:");

            txtADescripcion.setColumns(20);
            txtADescripcion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
            txtADescripcion.setRows(5);
            txtADescripcion.setEnabled(false);
            jScrollPane2.setViewportView(txtADescripcion);

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtKilometraje, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel11)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cmb_Vehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 194, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(cmb_Vehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(23, 23, 23)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtKilometraje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(jLabel11)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane2)
                    .addContainerGap())
            );

            pn_addInventario.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 50, 510, 387));

            hora_Llegada.setEnabled(false);
            pn_addInventario.add(hora_Llegada, new org.netbeans.lib.awtextra.AbsoluteConstraints(387, 117, -1, -1));
            JSpinner.DateEditor de2 = new JSpinner.DateEditor(hora_Llegada, "HH:mm");
            hora_Llegada.setEditor(de2);

            hora_Salida.setEnabled(false);
            pn_addInventario.add(hora_Salida, new org.netbeans.lib.awtextra.AbsoluteConstraints(387, 81, -1, -1));
            JSpinner.DateEditor de = new JSpinner.DateEditor(hora_Salida, "HH:mm");
            hora_Salida.setEditor(de);

            jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/formularios.png"))); // NOI18N
            pn_addInventario.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

            getContentPane().add(pn_addInventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 1290, 500));

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
       int indiceCarro = cmb_Vehiculo.getSelectedIndex();
        try{
            verificar_excepcion=true;
            
            validarDatos(true,"");
            
            //inserta solicitud
            insertar_Solicitud(indiceCarro);
            
        }catch(ExceptionDatosIncompletos e){
            if(verificar_excepcion)JOptionPane.showMessageDialog(this, e.getMessage());
            return;
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "El kilometraje debe ser un numero sin letras.");
        }
       
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
        parent.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        comboEmpleados.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        comboEmpleados.addItem("Selecione empleado...");
        manager_users.getNombresEmpleados(comboEmpleados);
        agregarDatos();
        cmb_Vehiculo.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        cmb_Vehiculo.addItem("Selecione vehiculo...");
        //manager_vehiculo.getVehiculosDisponibles(cmb_Vehiculo);
        try{
            getAutosDisponibles();
        }catch(SQLException e){
            
        } 
    }//GEN-LAST:event_formWindowOpened
    private void getAutosDisponibles() throws SQLException{
        ResultSet res;
        Connection cn=cbd.getConexion();
            res=cbd.getTabla("select marca,matricula from vehiculos where Estado='Disponible'",cn);
            SimpleDateFormat format=new SimpleDateFormat("h:mm:ss a");
            List<String> autos=new ArrayList<String>();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            List<Integer> autos_disponibles=getAutosDisponiblesFecha(sdf.format(date_Salida.getDate().getTime()),format.format((Date)hora_Salida.getValue())+"");
            List<String> autos_noDisponibles=getAutosNoDisponibles(autos_disponibles,sdf.format(date_Salida.getDate().getTime()));
            while(res.next()){
                boolean disponible=true;//se cambia a false si el registro está en los autos no disponibles
                String aux=res.getString("marca")+"-"+res.getString("matricula");
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
            for(int i=0;i<autos.size();i++){
                cmb_Vehiculo.addItem(autos.get(i));
            }
    }
    private List<String> getAutosNoDisponibles(List<Integer> id,String fecha)throws SQLException{
        //Buscamos entre las solicitudes de vehículo los vehiculos que no están disponibles
        //a partir de los que ya se han validado que esten dispnibles
        Connection connection=cbd.getConexion();
        ResultSet res;
        //Obtenemos todos los vehiculos que tienen solicitud de vehiculo a partir de la fecha de salida solicitada
        res=cbd.getTabla("select idvehiculo_usado,vehiculos_Matricula from vehiculo_usado inner join solicitud_vehiculo on idvehiculo_usado=vehiculo_usado_idvehiculo_usado where fecha_salida>'"+fecha+"';", connection);
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
        res=cbd.getTabla("select fecha_salida,Fecha_Llegada,hora_llegada,vehiculo_usado_idvehiculo_usado from solicitud_vehiculo where estado !='C' and fecha_llegada>='"+Calendar.YEAR+
                "-"+Calendar.MONTH+"-"+Calendar.DAY_OF_MONTH+"'",connection);
        //En caso de que sea la primera solicitud
        if(!res.next()){
            datos=obtenerTodosVehiculos();
        }
        //Recorremos todos los registros para obtener los vehiculos que si podemos solicitar
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
    private void comboEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEmpleadosActionPerformed
        // TODO add your handling code here:
        int empleado = comboEmpleados.getSelectedIndex();
        if(empleado > 0){
            txt_Puesto.setText(manager_users.obtenerPuesto(comboEmpleados.getSelectedItem().toString()));
        }else{
            txt_Puesto.setText("");
        }
        
    }//GEN-LAST:event_comboEmpleadosActionPerformed

    private void cmb_VehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_VehiculoActionPerformed
        // TODO add your handling code here:
        if(cmb_Vehiculo.getSelectedIndex() != 0){
            String separador [] = cmb_Vehiculo.getSelectedItem().toString().split("-");
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

    private void cmbEstadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEstadoItemStateChanged
        // TODO add your handling code here:
        cmbLocalidad.removeAllItems();
        cmbLocalidad.addItem("Seleccione localidad");
        List<String> localidades=cbd.acceder("select L.nombre from Localidad L inner join Estado E on L.estado_idestado=E.idestado where E.nombre='"+cmbEstado.getSelectedItem().toString()+"' order by L.nombre;");
        for(int i=0;i<localidades.size();i++){
            cmbLocalidad.addItem(localidades.get(i));
        }
    }//GEN-LAST:event_cmbEstadoItemStateChanged
    public void insertar_Solicitud(int ConCarro){
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String fecha_Salida=sdf.format(date_Salida.getDate().getTime());
            String fecha_Llegada=sdf.format(date_Llegada.getDate().getTime());
            Conexion conexion=new Conexion();
            //conexion.getConexion();
            
            String pernoctado="No";
            if(chb_Pernoctado.isSelected()){
                pernoctado="Si";
            }
            cn=cbd.getConexion();
            String[] arr=cmb_Vehiculo.getSelectedItem().toString().split("-");
            ResultSet res=cbd.getTabla("select idvehiculo_usado from vehiculo_usado where vehiculo_usado.vehiculos_Matricula='"+arr[1]+"';", cn);
            System.out.println("select idvehiculo_usado from vehiculo_usado where vehiculo_usado.vehiculos_Matricula='"+arr[1]+"';");
            res.next();
            String idVehiculo_usado=res.getString("idvehiculo_usado");
            //Inserción de solicitud
            SimpleDateFormat format=new SimpleDateFormat("h:mm:ss a");
            conexion.getConexion();
            boolean insersion = insersion=conexion.ejecutar("insert into Solicitud_vehiculo (Fecha_Salida,Lugar,Nombre,Actividad,Pernoctado,Vehiculo,Puesto,Fecha_Llegada,Estado,Reporte,Hora_Llegada,Hora_Salida,vehiculo_usado_idvehiculo_usado) values('"+fecha_Salida+"','"+
                    cmbLocalidad.getSelectedItem().toString()+","+cmbEstado.getSelectedItem().toString()+"'"
                + ",'"+comboEmpleados.getSelectedItem().toString()+"','"+txt_Actividad.getText()+"','"+pernoctado+"','"+cmb_Vehiculo.getSelectedItem().toString()+"'"
                + ",'"+txt_Puesto.getText()+"','"+fecha_Llegada+"','P','0','"+format.format((Date)hora_Llegada.getValue())+"','"+format.format((Date)hora_Salida.getValue())+"',"+idVehiculo_usado+")");
            
            if(insersion){
                JOptionPane.showMessageDialog(this, "Insersión correcta");
                PrincipalS.tablasolic.setModel(manager_viaticos.getTasol());
                //this.setVisible(false);
                this.dispose();
                parent.dispose();
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
        if(chb_Pernoctado.isSelected()){
            if(date_Salida.getDate().getYear()==date_Llegada.getDate().getYear()
                    && date_Salida.getDate().getMonth()==date_Llegada.getDate().getMonth()
                    && date_Salida.getDate().getDate()==date_Llegada.getDate().getDate()){
                cad+="\nNo se puede seleccionar pernoctadar para una fecha de salida y de llagada igual";
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
                cad+="-No se ha insertado el lugar, escriba el lugar y vuelva a intentarlo";
            }
            else{
                cad+="\n-No se ha insertado el lugar, escriba el lugar y vuelva a intentarlo";
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
        if(cmb_Vehiculo.getSelectedIndex()==0){
            if(cad.equals("")){
                cad+="-No se ha seleccionado vehiculo, seleccione uno y vuelva a intentarlo";
            }
            else{
                cad+="\n-No se ha seleccionado vehiculo, seleccione uno y vuelva a intentarlo";
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(addSolicitudVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addSolicitudVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addSolicitudVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addSolicitudVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                addSolicitudVehiculo dialog = new addSolicitudVehiculo(new javax.swing.JFrame(), true);
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JCheckBox chb_Pernoctado;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<String> cmbLocalidad;
    private javax.swing.JComboBox cmb_Vehiculo;
    private javax.swing.JComboBox<String> comboEmpleados;
    private com.toedter.calendar.JDateChooser date_Llegada;
    private com.toedter.calendar.JDateChooser date_Salida;
    private javax.swing.JSpinner hora_Llegada;
    private javax.swing.JSpinner hora_Salida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JPanel pn_addInventario;
    private javax.swing.JTextArea txtADescripcion;
    private javax.swing.JTextField txtKilometraje;
    private javax.swing.JTextArea txt_Actividad;
    private javax.swing.JTextField txt_Puesto;
    // End of variables declaration//GEN-END:variables
}
