/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Clases.Conexion;
import java.awt.Color;
import java.sql.Connection;
import javax.swing.JFrame;

/**
 *
 * @author guillermo
 */
public class visSolicitudPase extends javax.swing.JDialog {
    
    Conexion cbd = new Conexion();
    Connection cn = cbd.getConexion();
    
    String folio1="";
    String nombre1="";
    String puesto1="";
    String area1="";
    String fecha1="";
    String horaes1="";
    String horall1="";
    String horas1="";
    String tipohorario1="";
    String tipoasunto1="";
    String asunto1="";
    String estado1="";
    String vehiculo1="";

    /**
     * Creates new form visSolicitudPase
     */
    public visSolicitudPase(java.awt.Frame parent, boolean modal,String fol) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Consulta de información del folio \""+fol+"\"");
    }

    public visSolicitudPase() {
        initComponents();
        
    }

    private visSolicitudPase(JFrame jFrame, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_vissolipase = new javax.swing.JPanel();
        txtnombre = new javax.swing.JTextField();
        txtarea = new javax.swing.JTextField();
        txthoraes = new javax.swing.JTextField();
        lblAviso = new javax.swing.JLabel();
        txtpuesto = new javax.swing.JTextField();
        txtfecha = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtasunto = new javax.swing.JTextArea();
        txtfolio = new javax.swing.JTextField();
        txthorall = new javax.swing.JTextField();
        txthoras = new javax.swing.JTextField();
        txttipohorario = new javax.swing.JTextField();
        txttipoasunto = new javax.swing.JTextField();
        txtestado = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtvehiculo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(590, 510));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        pn_vissolipase.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pn_vissolipase.setLayout(null);

        txtnombre.setEditable(false);
        txtnombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtnombreFocusLost(evt);
            }
        });
        pn_vissolipase.add(txtnombre);
        txtnombre.setBounds(137, 154, 416, 23);

        txtarea.setEditable(false);
        txtarea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pn_vissolipase.add(txtarea);
        txtarea.setBounds(137, 124, 416, 23);

        txthoraes.setEditable(false);
        txthoraes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txthoraes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txthoraesActionPerformed(evt);
            }
        });
        pn_vissolipase.add(txthoraes);
        txthoraes.setBounds(137, 246, 140, 23);

        lblAviso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pn_vissolipase.add(lblAviso);
        lblAviso.setBounds(39, 225, 15, 0);

        txtpuesto.setEditable(false);
        pn_vissolipase.add(txtpuesto);
        txtpuesto.setBounds(137, 184, 416, 20);

        txtfecha.setEditable(false);
        pn_vissolipase.add(txtfecha);
        txtfecha.setBounds(137, 215, 140, 20);

        txtasunto.setEditable(false);
        txtasunto.setColumns(20);
        txtasunto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtasunto.setRows(5);
        jScrollPane2.setViewportView(txtasunto);

        pn_vissolipase.add(jScrollPane2);
        jScrollPane2.setBounds(137, 312, 420, 110);

        txtfolio.setEditable(false);
        txtfolio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfolio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtfolioFocusLost(evt);
            }
        });
        txtfolio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfolioActionPerformed(evt);
            }
        });
        pn_vissolipase.add(txtfolio);
        txtfolio.setBounds(433, 44, 120, 23);

        txthorall.setEditable(false);
        txthorall.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txthorall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txthorallActionPerformed(evt);
            }
        });
        pn_vissolipase.add(txthorall);
        txthorall.setBounds(137, 276, 140, 23);

        txthoras.setEditable(false);
        txthoras.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txthoras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txthorasActionPerformed(evt);
            }
        });
        pn_vissolipase.add(txthoras);
        txthoras.setBounds(360, 246, 140, 23);

        txttipohorario.setEditable(false);
        txttipohorario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txttipohorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttipohorarioActionPerformed(evt);
            }
        });
        pn_vissolipase.add(txttipohorario);
        txttipohorario.setBounds(138, 83, 140, 23);

        txttipoasunto.setEditable(false);
        txttipoasunto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txttipoasunto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttipoasuntoActionPerformed(evt);
            }
        });
        pn_vissolipase.add(txttipoasunto);
        txttipoasunto.setBounds(415, 83, 140, 23);

        txtestado.setEditable(false);
        txtestado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtestado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtestadoActionPerformed(evt);
            }
        });
        pn_vissolipase.add(txtestado);
        txtestado.setBounds(434, 10, 120, 23);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Por horario:");
        pn_vissolipase.add(jLabel15);
        jLabel15.setBounds(50, 90, 73, 17);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Área:");
        pn_vissolipase.add(jLabel1);
        jLabel1.setBounds(93, 130, 32, 17);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Nombre:");
        pn_vissolipase.add(jLabel7);
        jLabel7.setBounds(70, 160, 54, 17);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Puesto:");
        pn_vissolipase.add(jLabel2);
        jLabel2.setBounds(76, 190, 47, 17);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Fecha de entrada:");
        pn_vissolipase.add(jLabel12);
        jLabel12.setBounds(6, 220, 111, 17);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Hora de E/S:");
        pn_vissolipase.add(jLabel4);
        jLabel4.setBounds(44, 252, 78, 17);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Hora de llegada:");
        pn_vissolipase.add(jLabel11);
        jLabel11.setBounds(20, 283, 98, 17);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Asunto:");
        pn_vissolipase.add(jLabel3);
        jLabel3.setBounds(74, 315, 48, 17);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Estado:");
        pn_vissolipase.add(jLabel8);
        jLabel8.setBounds(374, 15, 47, 17);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Por asunto:");
        pn_vissolipase.add(jLabel9);
        jLabel9.setBounds(330, 90, 72, 17);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Horas:");
        pn_vissolipase.add(jLabel5);
        jLabel5.setBounds(310, 252, 40, 17);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Folio:");
        pn_vissolipase.add(jLabel16);
        jLabel16.setBounds(390, 50, 32, 17);

        btnCancelar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cerrar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        pn_vissolipase.add(btnCancelar);
        btnCancelar.setBounds(230, 440, 120, 33);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Vehículo:");
        pn_vissolipase.add(jLabel10);
        jLabel10.setBounds(295, 280, 60, 17);

        txtvehiculo.setEditable(false);
        pn_vissolipase.add(txtvehiculo);
        txtvehiculo.setBounds(360, 276, 140, 20);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/formularios.png"))); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(861, 265));
        pn_vissolipase.add(jLabel6);
        jLabel6.setBounds(0, 0, 590, 510);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_vissolipase, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_vissolipase, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtnombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnombreFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreFocusLost

    private void txthoraesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txthoraesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txthoraesActionPerformed

    private void txtfolioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfolioFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfolioFocusLost

    private void txthorallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txthorallActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txthorallActionPerformed

    private void txthorasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txthorasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txthorasActionPerformed

    private void txttipohorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttipohorarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttipohorarioActionPerformed

    private void txttipoasuntoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttipoasuntoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttipoasuntoActionPerformed

    private void txtestadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtestadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtestadoActionPerformed

    private void txtfolioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfolioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfolioActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        txtfolio.setText(folio1);
        txtnombre.setText(nombre1);
        txtpuesto.setText(puesto1);
        txtarea.setText(area1);
        txtfecha.setText(fecha1);
        txthoraes.setText(horaes1);
        txthorall.setText(horall1);
        txthoras.setText(horas1);
        txttipohorario.setText(tipohorario1);
        txttipoasunto.setText(tipoasunto1);
        txtasunto.setText(asunto1);
        txtestado.setText(estado1);
        txtvehiculo.setText(vehiculo1);
        if(estado1.equals("Aceptado")){
            txtestado.setBackground(Color.GREEN);
        }else{
            txtestado.setBackground(Color.RED);
        }
        txtasunto.setLineWrap(true);
        txtasunto.setWrapStyleWord(true);
    }//GEN-LAST:event_formWindowActivated

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed
public void recibeinfo(String folio,String nombre,String puesto,String area,String fecha,String horaes,String horall,String horas,String tipohorario,String tipoasunto,String asunto,String estado,String vehiculo){
        
    folio1=folio;
    nombre1=nombre;
    puesto1=puesto;
    area1=area;
    fecha1=fecha;
    horaes1=horaes;
    horall1=horall;
    horas1=horas;
    tipohorario1=tipohorario;
    tipoasunto1=tipoasunto;
    asunto1=asunto;
    estado1=estado;
    vehiculo1=vehiculo;
        
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
            java.util.logging.Logger.getLogger(visSolicitudPase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(visSolicitudPase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(visSolicitudPase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(visSolicitudPase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                visSolicitudPase dialog = new visSolicitudPase(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAviso;
    public static javax.swing.JPanel pn_vissolipase;
    private javax.swing.JTextField txtarea;
    public static javax.swing.JTextArea txtasunto;
    private javax.swing.JTextField txtestado;
    private javax.swing.JTextField txtfecha;
    private javax.swing.JTextField txtfolio;
    private javax.swing.JTextField txthoraes;
    private javax.swing.JTextField txthorall;
    private javax.swing.JTextField txthoras;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtpuesto;
    private javax.swing.JTextField txttipoasunto;
    private javax.swing.JTextField txttipohorario;
    private javax.swing.JTextField txtvehiculo;
    // End of variables declaration//GEN-END:variables
}
