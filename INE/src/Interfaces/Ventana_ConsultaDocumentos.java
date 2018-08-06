/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Clases.ManagerDocumentos;
import Clases.ManagerUsers;
import Clases.ManagerSolicitud;
import Clases.ManagerPermisos;

import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author kevin
 */
public class Ventana_ConsultaDocumentos extends javax.swing.JDialog {
    ManagerDocumentos manager_documentos;
    
    /**
     * Creates new form Ventana_asignar_EquipoComputo
     */
    public Ventana_ConsultaDocumentos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        //Asignamos memoria al objeto
        manager_documentos = new ManagerDocumentos();
        
        this.setTitle("Anexar productos a un documento");
        this.setLocationRelativeTo(null);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MenuDocumentos = new javax.swing.JPopupMenu();
        AsignarActa = new javax.swing.JMenuItem();
        pn_asignarEquipo = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDocumentos = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDocumentosProductos = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        comboEstatus = new javax.swing.JComboBox<>();

        AsignarActa.setText("Asignar acta");
        AsignarActa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AsignarActaActionPerformed(evt);
            }
        });
        MenuDocumentos.add(AsignarActa);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tablaDocumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Clave", "Fecha de inicio", "Productos asignados", "No. Acta"
            }
        ));
        tablaDocumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDocumentosMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaDocumentosMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tablaDocumentos);

        jLabel1.setText("Seleccione un documento:");

        tablaDocumentosProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Clave", "Nombre Corto", "No. de serie", "Descripción", "Observaciones"
            }
        ));
        tablaDocumentosProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDocumentosProductosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDocumentosProductos);

        jLabel4.setText("Productos del documento");

        jLabel2.setText("Estatus:");

        comboEstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEstatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_asignarEquipoLayout = new javax.swing.GroupLayout(pn_asignarEquipo);
        pn_asignarEquipo.setLayout(pn_asignarEquipoLayout);
        pn_asignarEquipoLayout.setHorizontalGroup(
            pn_asignarEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_asignarEquipoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_asignarEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pn_asignarEquipoLayout.createSequentialGroup()
                        .addGroup(pn_asignarEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 979, Short.MAX_VALUE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 979, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(jScrollPane3))
                        .addGap(18, 18, 18))
                    .addGroup(pn_asignarEquipoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 493, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboEstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(202, 202, 202))))
        );
        pn_asignarEquipoLayout.setVerticalGroup(
            pn_asignarEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_asignarEquipoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_asignarEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(comboEstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_asignarEquipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pn_asignarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        
        //COMBOESTATUS
        comboEstatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        comboEstatus.addItem("Todos");
        comboEstatus.addItem("En selección");
        comboEstatus.addItem("Finalizados");
    }//GEN-LAST:event_formWindowOpened
        
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formWindowClosing

    private void tablaDocumentosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDocumentosMouseReleased
        // TODO add your handling code here:
        //Esto es para seleccionar con el click derecho y desplegar el menu solo cuando se seleccione una fila de la tabla
            if(SwingUtilities.isRightMouseButton(evt)){
                int r = tablaDocumentos.rowAtPoint(evt.getPoint());
                if (r >= 0 && r < tablaDocumentos.getRowCount())
                tablaDocumentos.setRowSelectionInterval(r, r);
                
                MenuDocumentos.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
            }//clic derecho
    }//GEN-LAST:event_tablaDocumentosMouseReleased

    private void tablaDocumentosProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDocumentosProductosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaDocumentosProductosMouseClicked

    private void tablaDocumentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDocumentosMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            int fila = tablaDocumentos.getSelectedRow();
            String id = tablaDocumentos.getValueAt(fila, 0).toString();
            String datos [] = id.split("-");
            
            //Llenamos la tabla de la relación documento-productos
            tablaDocumentosProductos.setModel(manager_documentos.getDocumentosProductosMasInfo(Integer.parseInt(datos[1])));
            
        }//getClickCount
    }//GEN-LAST:event_tablaDocumentosMouseClicked

    private void comboEstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEstatusActionPerformed
        // TODO add your handling code here:
        String estatus = comboEstatus.getSelectedItem().toString();
        
        //Llenamos la tabla de los documentos
        tablaDocumentos.setModel(manager_documentos.getDocumentosFiltro(estatus));
    }//GEN-LAST:event_comboEstatusActionPerformed

    private void AsignarActaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AsignarActaActionPerformed
        // TODO add your handling code here:
        int fila = tablaDocumentos.getSelectedRow();
        //Esto es para validar que ingrese solo numeros y mientras no lo haga, seguira preguntado hasta que
        //solo teclee numeros o cancele el movimiento
        boolean string = true;
        boolean cancelo = true;
        String cadena = null;
        while(string){
            cadena = JOptionPane.showInputDialog("Ingrese el No. de Acta");
            //Cancelo la solicitud de asignacion
            if(cadena == null){
                string = false;
                cancelo = false;
            }else{
                if(!cadena.equals("")){
                    string = false;
                    cancelo = true;
                }else{
                    JOptionPane.showMessageDialog(null,"No deje el campo vacio por favor.");
                }
            }
        }//while                        

        if(cancelo){
            if(manager_documentos.asignarActa(tablaDocumentos.getValueAt(fila, 0).toString(), cadena)){
                JOptionPane.showMessageDialog(null, "No. de acta \""+cadena+"\" asignado correctamente.");
                String estatus = comboEstatus.getSelectedItem().toString();

                //Llenamos la tabla de los documentos
                tablaDocumentos.setModel(manager_documentos.getDocumentosFiltro(estatus));
            }else{
                JOptionPane.showMessageDialog(null, "Verificar con el distribuidor.");
            }
        }
        
        
    }//GEN-LAST:event_AsignarActaActionPerformed

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
            java.util.logging.Logger.getLogger(Ventana_ConsultaDocumentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana_ConsultaDocumentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana_ConsultaDocumentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana_ConsultaDocumentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                Ventana_ConsultaDocumentos dialog = new Ventana_ConsultaDocumentos(new javax.swing.JFrame(), true);
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
    private javax.swing.JMenuItem AsignarActa;
    private javax.swing.JPopupMenu MenuDocumentos;
    private javax.swing.JComboBox<String> comboEstatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel pn_asignarEquipo;
    private javax.swing.JTable tablaDocumentos;
    private javax.swing.JTable tablaDocumentosProductos;
    // End of variables declaration//GEN-END:variables
}
