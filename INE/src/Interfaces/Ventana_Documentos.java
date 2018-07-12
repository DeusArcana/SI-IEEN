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
public class Ventana_Documentos extends javax.swing.JDialog {
    ManagerDocumentos manager_documentos;
    
    public DefaultTableModel modeloProductos,modeloDocumentoProductos;
    int id_documento;
    String status;
    /**
     * Creates new form Ventana_asignar_EquipoComputo
     */
    public Ventana_Documentos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        //Asignamos memoria al objeto
        manager_documentos = new ManagerDocumentos();
        
        modeloProductos = (DefaultTableModel)tablaProductosSeleccionar.getModel();
        modeloDocumentoProductos = (DefaultTableModel)tablaDocumentosProductos.getModel();
        
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

        MenuProductos = new javax.swing.JPopupMenu();
        Aceptar = new javax.swing.JMenuItem();
        Refresh = new javax.swing.JMenuItem();
        MenuDocumentos = new javax.swing.JPopupMenu();
        Finalizar = new javax.swing.JMenuItem();
        pn_asignarEquipo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductosSeleccionar = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDocumentos = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDocumentosProductos = new JTable(){  public boolean isCellEditable(int rowIndex, int colIndex){  return false;  }  };
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        Aceptar.setText("Aceptar");
        Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarActionPerformed(evt);
            }
        });
        MenuProductos.add(Aceptar);

        Refresh.setText("Refrescar tabla");
        Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshActionPerformed(evt);
            }
        });
        MenuProductos.add(Refresh);

        Finalizar.setText("Finalizar");
        Finalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinalizarActionPerformed(evt);
            }
        });
        MenuDocumentos.add(Finalizar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tablaProductosSeleccionar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "¿Cuáles se van?", "Clave", "Nombre corto", "No. de serie", "Fecha"
            }
        ));
        tablaProductosSeleccionar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductosSeleccionarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaProductosSeleccionar);

        tablaDocumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Clave", "Fecha de inicio", "Productos asignados"
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

        jLabel2.setText("Productos a seleccionar");

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

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout pn_asignarEquipoLayout = new javax.swing.GroupLayout(pn_asignarEquipo);
        pn_asignarEquipo.setLayout(pn_asignarEquipoLayout);
        pn_asignarEquipoLayout.setHorizontalGroup(
            pn_asignarEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_asignarEquipoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_asignarEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pn_asignarEquipoLayout.createSequentialGroup()
                        .addGroup(pn_asignarEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(pn_asignarEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_asignarEquipoLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pn_asignarEquipoLayout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(pn_asignarEquipoLayout.createSequentialGroup()
                        .addGroup(pn_asignarEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 979, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 979, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))))
        );
        pn_asignarEquipoLayout.setVerticalGroup(
            pn_asignarEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_asignarEquipoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_asignarEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pn_asignarEquipoLayout.createSequentialGroup()
                        .addGroup(pn_asignarEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_asignarEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        //Llenamos la tabla de los documentos
        tablaDocumentos.setModel(manager_documentos.getDocumentos());
    }//GEN-LAST:event_formWindowOpened
    
    private void tablaProductosSeleccionarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosSeleccionarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaProductosSeleccionarMouseClicked
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formWindowClosing

    private void tablaDocumentosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDocumentosMouseReleased
        // TODO add your handling code here:
        if(SwingUtilities.isRightMouseButton(evt)){
            int r = tablaDocumentos.rowAtPoint(evt.getPoint());
            if (r >= 0 && r < tablaDocumentos.getRowCount())
            tablaDocumentos.setRowSelectionInterval(r, r);
            MenuDocumentos.show(evt.getComponent(), evt.getX(), evt.getY());//Mostramos el popMenu en la posición donde esta el cursor
        }//clic derecho
    }//GEN-LAST:event_tablaDocumentosMouseReleased

    private void AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarActionPerformed
        // TODO add your handling code here:
        DefaultTableModel inventario = (DefaultTableModel)tablaProductosSeleccionar.getModel();
        boolean selecciono = false;
        Boolean[] cambio = new Boolean[inventario.getRowCount()];
        String[] ids = new String[inventario.getRowCount()];

        //Llenamos los arreglos con la información
        for(int i = 0; i<ids.length; i++){
            cambio[i] = Boolean.parseBoolean(tablaProductosSeleccionar.getValueAt(i, 0).toString());
            ids[i] = tablaProductosSeleccionar.getValueAt(i, 1).toString();
        }//for
        
        //Aquí vemos si por lo menos seleccionó algún producto
        for(int i = 0; i<ids.length; i++){
            if(cambio[i]){
                selecciono = true;
                break;
            }//if
        }//for
        
        if(selecciono){
            if(manager_documentos.anexarAlDocumento(ids,cambio,id_documento)){
                JOptionPane.showMessageDialog(null, "Se registraron exitosamente los nuevos productos al documento.");
                
                //Actualizamos la tabla de los productos que quiere agregar al documentos
                tablaProductosSeleccionar.setModel(manager_documentos.productosParaAsignarMenosInfo(status));
                //Actualizamos la tabla de la relación documento-productos
                tablaDocumentosProductos.setModel(manager_documentos.getDocumentosProductos(id_documento));

            }//if
            else{
                JOptionPane.showMessageDialog(null, "Verificar con el distribuidor.");
            }//else
        }//if
    }//GEN-LAST:event_AceptarActionPerformed

    private void tablaDocumentosProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDocumentosProductosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaDocumentosProductosMouseClicked

    private void tablaDocumentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDocumentosMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            int fila = tablaDocumentos.getSelectedRow();
            String id = tablaDocumentos.getValueAt(fila, 0).toString();
            String datos [] = id.split("-");
            
            status = datos[0];
            //Llenamos la tabla de los productos que quiere agregar al documentos
            tablaProductosSeleccionar.setModel(manager_documentos.productosParaAsignarMenosInfo(status));
            //Llenamos la tabla de la relación documento-productos
            id_documento = Integer.parseInt(datos[1]);
            tablaDocumentosProductos.setModel(manager_documentos.getDocumentosProductos(id_documento));
            
            tablaProductosSeleccionar.setComponentPopupMenu(MenuProductos);
            
        }//getClickCount
    }//GEN-LAST:event_tablaDocumentosMouseClicked

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed
        // TODO add your handling code here:
        tablaProductosSeleccionar.setModel(manager_documentos.productosParaAsignarMenosInfo(status));
    }//GEN-LAST:event_RefreshActionPerformed

    private void FinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FinalizarActionPerformed
        // TODO add your handling code here:
        
        if(manager_documentos.finalizarDocumento(id_documento)){
            JOptionPane.showMessageDialog(null, "El documento ha sido finalizado exitosamente.");
            tablaDocumentos.setModel(manager_documentos.getDocumentos());
            tablaProductosSeleccionar.setModel(modeloProductos);
            tablaDocumentosProductos.setModel(modeloDocumentoProductos);
        }//if
        
        
    }//GEN-LAST:event_FinalizarActionPerformed

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
            java.util.logging.Logger.getLogger(Ventana_Documentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana_Documentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana_Documentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana_Documentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                Ventana_Documentos dialog = new Ventana_Documentos(new javax.swing.JFrame(), true);
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
    private javax.swing.JMenuItem Aceptar;
    private javax.swing.JMenuItem Finalizar;
    private javax.swing.JPopupMenu MenuDocumentos;
    private javax.swing.JPopupMenu MenuProductos;
    private javax.swing.JMenuItem Refresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel pn_asignarEquipo;
    private javax.swing.JTable tablaDocumentos;
    private javax.swing.JTable tablaDocumentosProductos;
    private javax.swing.JTable tablaProductosSeleccionar;
    // End of variables declaration//GEN-END:variables
}