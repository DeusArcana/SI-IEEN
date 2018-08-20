/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Clases.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author juanpedrog
 */
public class addViaticoVehiculo extends javax.swing.JDialog {
    Conexion cbd=new Conexion();
    Connection cn=cbd.getConexion();
    int idSolicitudVehiculo=-1;
    int idSolicitudViatico=-1;
    String idSolicitud;//Guarda el id de la solicitud
    String fecha;//Guarda la fecha que viene de la otra pantalla
    DefaultTableModel modelEmpleado;
    /**
     * Creates new form addViaticoVehiculo
     */
    public addViaticoVehiculo() {
        initComponents();
    }
    public addViaticoVehiculo(java.awt.Frame parent,boolean modal,String idSolicitud,String fecha){
        super(parent,modal);
        this.setTitle("Agregar empleados al vehículo");
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.idSolicitud=idSolicitud;
        this.fecha=fecha;
        refrescarPantalla(idSolicitud,fecha);
    }
    public void refrescarPantalla(String idSolicitud,String fecha){
        idSolicitudViatico=Integer.parseInt(idSolicitud);
        String idSolicitudVe="";
        try{
            ResultSet res=cbd.getTabla("select * from vehiculo_viatico VV inner join solicitud_viatico SVI on VV.solicitud_viatico_idSolicitud=SVI.idSolicitud where SVI.idSolicitud="+idSolicitud, cn);
            while(res.next()){
                idSolicitudVe=res.getString("solicitud_vehiculo_idSolicitud_vehiculo");
            }
            //Obtenemos todos los empleados que están asociados a esta solicitud de vehiculo
            res=cbd.getTabla("select * from vehiculo_viatico VV inner join solicitud_viatico SVI on VV.solicitud_viatico_idSolicitud=SVI.idSolicitud inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo where SV.idsolicitud_vehiculo="+idSolicitudVe+";", cn); 
            List<String> empleadosAsignados=new ArrayList<String>();
            while(res.next()){
                empleadosAsignados.add(res.getString("solicitud_viatico_idSolicitud"));
            }
            llenarTabla(empleadosAsignados);
            //--------------------------------
            //Obtenemos las solicitudes de empleados que no hayan sido aceptadas a partir de la fecha de la solicitud de vehiculo
            res=cbd.getTabla("select * from solicitud_viatico where estado='A' and fecha_salida>='"+fecha+"';", cn);
            List<String> empleadosDisponibles=new ArrayList<String>();
            while(res.next()){
                boolean disponible=true;
                //Recorremos a los empleados no disponibles para no mostrarlos
                for(int i=0;i<empleadosAsignados.size();i++){
                    if(empleadosAsignados.get(i).equals(res.getString("idSolicitud"))){
                        disponible=false;//Si encontramos a uno de los empleados ya asignados, no lo ponemos disponible
                    }
                }
                if(disponible){
                    empleadosDisponibles.add(res.getString("idSolicitud")+"-"+res.getString("nombre"));
                }
            }
            //Llenamos el combo box de los empleados disponibles para asignar un vehiculo
            llenarComboBoxEmpleados(empleadosDisponibles);
        }catch(SQLException e){}
    }
    private void llenarComboBoxEmpleados(List<String> empleados){
        modelEmpleado=new DefaultTableModel();
        modelEmpleado.addColumn("Empleados disponibles");
        for(int i=0;i<empleados.size();i++){
            modelEmpleado.addRow(new Object[]{empleados.get(i)});
        }
        tablaEmpleados.setModel(modelEmpleado);
    }
    public void llenarTabla(List<String>empleados)throws SQLException{
        //Llenamos la tabla con los empleados que ya han sido asignados a la solicitud de vehiculo
        //Aplicamos el formato al modelo de la tabla
        DefaultTableModel model=new DefaultTableModel();
        model.addColumn("Nombre");
        //Agregamos el nombre de la persona que hizo la solicitud del vehiculo
        ResultSet res=cbd.getTabla("select nombre,idsolicitud_vehiculo from solicitud_vehiculo SV inner join vehiculo_viatico VV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo inner join solicitud_viatico SVI on VV.solicitud_viatico_idSolicitud=SVI.idSolicitud where SVI.idSolicitud="+idSolicitudViatico, cn);
        while(res.next()){
            model.addRow(new Object[]{res.getString("nombre")+" (Chofer)"});
            idSolicitudVehiculo=Integer.parseInt(res.getString("idsolicitud_vehiculo"));
        }
        //sacamos los nombres de los empleados asignados.
        if(empleados.size()>1){
            String query="select nombre from solicitud_viatico where idSolicitud="+empleados.get(0);
            for(int i=1;i<empleados.size();i++){
                query+=" or idSolicitud="+empleados.get(i);
            }
            res=cbd.getTabla(query, cn);
            while(res.next()){
                model.addRow(new Object[]{res.getString("nombre")});
            }
        }
        tabla.setModel(model);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnListo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaEmpleados = new javax.swing.JTable();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Seleccione el id de la solicitud de viático y el empleado");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        btnAceptar.setText("Aceptar");
        btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 120, 90, 40));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tabla);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 320, 344));

        btnListo.setText("Listo");
        btnListo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnListo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListoActionPerformed(evt);
            }
        });
        getContentPane().add(btnListo, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 210, 80, 40));

        tablaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tablaEmpleados);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/formularios.png"))); // NOI18N
        getContentPane().add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 897, 369));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        int k=tablaEmpleados.getSelectedRow();
        if(k>=0){
            String item=tablaEmpleados.getValueAt(k, 0).toString();
            String idSolicitud=item.split("-")[0];
            cbd.ejecutar("INSERT INTO vehiculo_viatico values("+idSolicitudVehiculo+","+idSolicitud+",'1');");
            refrescarPantalla(this.idSolicitud,this.fecha);
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnListoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListoActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnListoActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addViaticoVehiculo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnListo;
    private javax.swing.JLabel fondo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabla;
    private javax.swing.JTable tablaEmpleados;
    // End of variables declaration//GEN-END:variables
}
