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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author kevin
 */
public class Ventana_ConsultaDocumentos extends javax.swing.JDialog {
    ManagerDocumentos manager_documentos;
    ManagerPermisos manager_permisos;
    
    /**
     * Creates new form Ventana_asignar_EquipoComputo
     */
    public Ventana_ConsultaDocumentos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        //Asignamos memoria al objeto
        manager_documentos = new ManagerDocumentos();
        manager_permisos = new ManagerPermisos();
        
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
        jLabel3 = new javax.swing.JLabel();

        AsignarActa.setText("Asignar acta");
        AsignarActa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AsignarActaActionPerformed(evt);
            }
        });
        MenuDocumentos.add(AsignarActa);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pn_asignarEquipo.setLayout(null);

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

        pn_asignarEquipo.add(jScrollPane2);
        jScrollPane2.setBounds(10, 37, 979, 161);

        jLabel1.setText("Seleccione un documento:");
        pn_asignarEquipo.add(jLabel1);
        jLabel1.setBounds(10, 14, 490, 14);

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

        pn_asignarEquipo.add(jScrollPane3);
        jScrollPane3.setBounds(10, 232, 979, 328);

        jLabel4.setText("Productos del documento");
        pn_asignarEquipo.add(jLabel4);
        jLabel4.setBounds(10, 212, 490, 14);
        pn_asignarEquipo.add(jSeparator2);
        jSeparator2.setBounds(10, 204, 979, 2);

        jLabel2.setText("Estatus:");
        pn_asignarEquipo.add(jLabel2);
        jLabel2.setBounds(628, 14, 40, 14);

        comboEstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEstatusActionPerformed(evt);
            }
        });
        pn_asignarEquipo.add(comboEstatus);
        comboEstatus.setBounds(682, 11, 300, 20);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/formularios.png"))); // NOI18N
        pn_asignarEquipo.add(jLabel3);
        jLabel3.setBounds(-6, -6, 1020, 590);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_asignarEquipo, javax.swing.GroupLayout.DEFAULT_SIZE, 1010, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_asignarEquipo, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
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
        if(manager_permisos.accesoModulo("consulta","Inventario",Principal.Username)){
            String estatus = comboEstatus.getSelectedItem().toString();
            //Llenamos la tabla de los documentos
            tablaDocumentos.setModel(manager_documentos.getDocumentosFiltro(estatus));
        }else{
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para consultar los documentos.");
        }
    }//GEN-LAST:event_comboEstatusActionPerformed

    private void AsignarActaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AsignarActaActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Inventario",Principal.Username)){
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
        }else{
            JOptionPane.showMessageDialog(null, "Usted no cuenta con permisos para asignar no. de acta a los documentos.");
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel pn_asignarEquipo;
    private javax.swing.JTable tablaDocumentos;
    private javax.swing.JTable tablaDocumentosProductos;
    // End of variables declaration//GEN-END:variables
}
