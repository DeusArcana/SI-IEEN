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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class visSolicitudViaticos extends javax.swing.JDialog {

    Conexion cbd = new Conexion();
    Connection cn = cbd.getConexion();
    int id, aoc, ayc;

    /**
     * Creates new form visSolicitudViaticos
     */
    public visSolicitudViaticos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setResizable(false);
    }

    public visSolicitudViaticos() {
                initComponents();
                this.setResizable(false);
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
        txt_Nombre = new javax.swing.JTextField();
        txt_Puesto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_Motivo = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_Lugar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lblAviso = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        txt_Fecha_Salida = new javax.swing.JTextField();
        txt_Fecha_Llegada = new javax.swing.JTextField();
        txt_Vehiculo = new javax.swing.JTextField();
        lbl_Folio = new javax.swing.JLabel();
        txt_Folio = new javax.swing.JTextField();
        txt_Monto = new javax.swing.JTextField();
        lbl_Monto = new javax.swing.JLabel();
        lbl_Motivo = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_Actividad = new javax.swing.JTextArea();
        btnOk = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Solicitud");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pn_addInventario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pn_addInventario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Nombre:");
        pn_addInventario.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, -1, -1));

        txt_Nombre.setEditable(false);
        txt_Nombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_Nombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_NombreFocusLost(evt);
            }
        });
        pn_addInventario.add(txt_Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 40, 215, -1));

        txt_Puesto.setEditable(false);
        txt_Puesto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pn_addInventario.add(txt_Puesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 69, 215, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Puesto:");
        pn_addInventario.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Actividad a realizar:");
        pn_addInventario.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Fecha de salida:");
        pn_addInventario.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Fecha de llegada:");
        pn_addInventario.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        txt_Motivo.setEditable(false);
        txt_Motivo.setColumns(20);
        txt_Motivo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_Motivo.setRows(5);
        jScrollPane1.setViewportView(txt_Motivo);

        pn_addInventario.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 250, 150));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Vehiculo:");
        pn_addInventario.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Lugar:");
        pn_addInventario.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, -1, -1));

        txt_Lugar.setEditable(false);
        txt_Lugar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_Lugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_LugarActionPerformed(evt);
            }
        });
        pn_addInventario.add(txt_Lugar, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 191, 215, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Pernoctado:");
        pn_addInventario.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        lblAviso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pn_addInventario.add(lblAviso, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 225, 15, -1));

        jRadioButton1.setEnabled(false);
        pn_addInventario.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 163, -1, -1));

        txt_Fecha_Salida.setEditable(false);
        txt_Fecha_Salida.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pn_addInventario.add(txt_Fecha_Salida, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 104, 215, -1));

        txt_Fecha_Llegada.setEditable(false);
        txt_Fecha_Llegada.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pn_addInventario.add(txt_Fecha_Llegada, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 133, 215, -1));

        txt_Vehiculo.setEditable(false);
        txt_Vehiculo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pn_addInventario.add(txt_Vehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, 248, -1));

        lbl_Folio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Folio.setText("Folio:");
        pn_addInventario.add(lbl_Folio, new org.netbeans.lib.awtextra.AbsoluteConstraints(394, 80, -1, -1));

        txt_Folio.setEditable(false);
        txt_Folio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pn_addInventario.add(txt_Folio, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, 248, -1));

        txt_Monto.setEditable(false);
        txt_Monto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pn_addInventario.add(txt_Monto, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, 248, -1));

        lbl_Monto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Monto.setText("Monto:");
        pn_addInventario.add(lbl_Monto, new org.netbeans.lib.awtextra.AbsoluteConstraints(383, 100, -1, -1));

        lbl_Motivo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Motivo.setText("Motivo:");
        pn_addInventario.add(lbl_Motivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, -1, -1));

        txt_Actividad.setEditable(false);
        txt_Actividad.setColumns(20);
        txt_Actividad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_Actividad.setRows(5);
        txt_Actividad.setPreferredSize(new java.awt.Dimension(220, 89));
        jScrollPane2.setViewportView(txt_Actividad);

        pn_addInventario.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 550, 130));

        btnOk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/aceptar.png"))); // NOI18N
        btnOk.setText("Ok");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        pn_addInventario.add(btnOk, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 400, 130, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/formularios.png"))); // NOI18N
        pn_addInventario.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 440));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_addInventario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_addInventario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_NombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_NombreFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_NombreFocusLost

    private void txt_LugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_LugarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_LugarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        try {
            if (ayc == 0) {
                Statement sentencia = cn.createStatement();
                ResultSet rs = sentencia.executeQuery("SELECT * FROM Solicitud_viatico WHERE idSolicitud = " + id);
                String p = "";
                while (rs.next()) {
                    txt_Nombre.setText(rs.getString("Nombre"));
                    txt_Puesto.setText(rs.getString("Puesto"));
                    txt_Fecha_Salida.setText(rs.getString("Fecha_salida"));
                    txt_Fecha_Llegada.setText(rs.getString("Fecha_llegada"));
                    txt_Lugar.setText(rs.getString("Lugar"));
                    txt_Actividad.setText(rs.getString("Actividad"));
                    //txt_Vehiculo.setText(rs.getString("Vehiculo"));
                    if(aoc == 2){
                        txt_Motivo.setText(rs.getString("Motivo"));
                    }else{
                        jScrollPane1.setVisible(false);
                        lbl_Motivo.setVisible(false);
                    }
                    p = rs.getString("Pernoctado");
                }
                ResultSet vehiculo = sentencia.executeQuery("select * from vehiculo_viatico VV  inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo inner join vehiculo_usado VU on SV.vehiculo_usado_idvehiculo_usado=VU.idvehiculo_usado where VV.solicitud_viatico_idSolicitud= " + id);
                while(vehiculo.next()){
                    if(vehiculo.getString("agregado").equals("0")){
                        txt_Vehiculo.setText(vehiculo.getString("vehiculos_Matricula")+"-"+vehiculo.getString("Vehiculo")+" (Chofer)");
                    }else{
                        txt_Vehiculo.setText(vehiculo.getString("vehiculos_Matricula")+"-"+vehiculo.getString("Vehiculo"));
                    }
                }
                if ("Si".equals(p)) {
                    jRadioButton1.setSelected(true);
                } else {
                    jRadioButton1.setSelected(false);
                }
                txt_Monto.setVisible(false);
                lbl_Monto.setVisible(false);
                txt_Folio.setVisible(false);
                lbl_Folio.setVisible(false);
            } else {
                Statement sentencia = cn.createStatement();
                ResultSet rs = sentencia.executeQuery("SELECT * FROM Solicitud_viatico WHERE idSolicitud = " + id);
                String p = "";
                while (rs.next()) {
                    txt_Nombre.setText(rs.getString("Nombre"));
                    txt_Puesto.setText(rs.getString("Puesto"));
                    txt_Fecha_Salida.setText(rs.getString("Fecha_salida"));
                    txt_Fecha_Llegada.setText(rs.getString("Fecha_llegada"));
                    txt_Lugar.setText(rs.getString("Lugar"));
                    txt_Actividad.setText(rs.getString("Actividad"));
                    p = rs.getString("Pernoctado");
                }
                ResultSet vehiculo = sentencia.executeQuery("select * from vehiculo_viatico VV  inner join solicitud_vehiculo SV on VV.solicitud_vehiculo_idsolicitud_vehiculo=SV.idsolicitud_vehiculo inner join vehiculo_usado VU on SV.vehiculo_usado_idvehiculo_usado=VU.idvehiculo_usado where VV.solicitud_viatico_idSolicitud= " + id);
                while(vehiculo.next()){
                    if(vehiculo.getString("agregado").equals("0")){
                        txt_Vehiculo.setText(vehiculo.getString("vehiculos_Matricula")+"-"+vehiculo.getString("Vehiculo")+" (Chofer)");
                    }else{
                        txt_Vehiculo.setText(vehiculo.getString("vehiculos_Matricula")+"-"+vehiculo.getString("Vehiculo"));
                    }
                }
                System.out.print(id);
                ResultSet rs1 = sentencia.executeQuery("SELECT O.Folio, O.Monto FROM Solicitud_viatico S, Oficio_comision O WHERE S.idSolicitud = "+id+" AND S.idSolicitud = O.Solicitud_idSolicitud");
                while (rs1.next()) {
                    txt_Folio.setText(rs1.getString("Folio"));
                    txt_Monto.setText(rs1.getString("Monto"));
                }
                if ("Si".equals(p)) {
                    jRadioButton1.setSelected(true);
                } else {
                    jRadioButton1.setSelected(false);
                }
                jScrollPane1.setVisible(false);
                lbl_Motivo.setVisible(false);
            }

        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

        } /*catch (ClassNotFoundException e) {
         e.printStackTrace();
         }//fin del catch*/
        txt_Actividad.setLineWrap(true);
        txt_Actividad.setWrapStyleWord(true);
    }//GEN-LAST:event_formWindowActivated

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        try {
            // TODO add your handling code here:
            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(visSolicitudViaticos.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_btnOkActionPerformed
    public void IdUsuario(int id, int ayc, int aoc) {
        this.id = id;
        this.ayc = ayc;
        this.aoc = aoc;
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
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                visSolicitudViaticos dialog = new visSolicitudViaticos(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JLabel lbl_Folio;
    private javax.swing.JLabel lbl_Monto;
    private javax.swing.JLabel lbl_Motivo;
    private javax.swing.JPanel pn_addInventario;
    private javax.swing.JTextArea txt_Actividad;
    private javax.swing.JTextField txt_Fecha_Llegada;
    private javax.swing.JTextField txt_Fecha_Salida;
    private javax.swing.JTextField txt_Folio;
    private javax.swing.JTextField txt_Lugar;
    private javax.swing.JTextField txt_Monto;
    private javax.swing.JTextArea txt_Motivo;
    private javax.swing.JTextField txt_Nombre;
    private javax.swing.JTextField txt_Puesto;
    private javax.swing.JTextField txt_Vehiculo;
    // End of variables declaration//GEN-END:variables
}
