package Formularios;

import Clases.ManagerInventario;
import Clases.ManagerPermisos;
import Interfaces.Principal;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kevin
 */
public class addObservaciones extends javax.swing.JDialog {
    
    ManagerInventario manager_inventario;
    ManagerPermisos manager_permisos;
    
    public addObservaciones(java.awt.Frame parent, boolean modal,String clave,String nom) {
        super(parent, modal);
        initComponents();
        
        //Asignamos memoria al objeto
        manager_inventario = new ManagerInventario();
        manager_permisos = new ManagerPermisos();
        
        this.setLocationRelativeTo(null);
        txtAObservaciones.setLineWrap(true);
        
        txtClave.setText(clave);
        txtNombre.setText(nom);
        txtAObservaciones.setText(manager_inventario.observacionesProducto(clave));
        this.setTitle("Observaciones al producto \""+clave+"\"");
    }

    private addObservaciones(JFrame jFrame, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtClave = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAObservaciones = new javax.swing.JTextArea();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "Añadir a resguardo personal", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("No. Inventario:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 30, 100, 17);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Observaciones:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 100, 94, 17);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Producto:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(50, 60, 70, 17);

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNombre.setEnabled(false);
        jPanel1.add(txtNombre);
        txtNombre.setBounds(120, 60, 249, 23);

        txtClave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtClave.setEnabled(false);
        jPanel1.add(txtClave);
        txtClave.setBounds(120, 30, 249, 23);

        txtAObservaciones.setColumns(20);
        txtAObservaciones.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAObservaciones.setLineWrap(true);
        txtAObservaciones.setRows(5);
        txtAObservaciones.setWrapStyleWord(true);
        txtAObservaciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAObservacionesKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txtAObservaciones);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(120, 96, 249, 170);

        btnAceptar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/aceptar.png"))); // NOI18N
        btnAceptar.setText(" Aceptar");
        btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        jPanel1.add(btnAceptar);
        btnAceptar.setBounds(60, 290, 130, 33);

        btnCancelar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText(" Cancelar");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar);
        btnCancelar.setBounds(230, 290, 150, 33);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/formularios.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(0, 0, 410, 340);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Inventario",Principal.Username)){
            manager_inventario.agregarObservaciones(txtClave.getText(), txtAObservaciones.getText());
            JOptionPane.showMessageDialog(null, "Observaciones agregadas con exito al producto \""+txtClave.getText()+"\".");
            if(manager_permisos.accesoModulo("consulta","Inventario",Principal.Username)){
                //Actualizamos al tabla de inventario de acuerdo a lo que ya se tenia seleccionado anteriormente
                String estatus = Principal.comboEstatus.getSelectedItem().toString();
                Principal.tablaInventario.setModel(manager_inventario.getInventario("",estatus));
            }else{
                Principal.tablaInventario.setModel(new DefaultTableModel());
                JOptionPane.showMessageDialog(null, "Le ha sido revocado el permiso para consultar el inventario.");
            }
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(null, "Le ha sido revocado el permiso para añadir observaciones a los productos.");
            this.dispose();
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtAObservacionesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAObservacionesKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_TAB) {
            if(evt.getModifiers() > 0) txtAObservaciones.transferFocusBackward();
            else txtAObservaciones.transferFocus(); 
            evt.consume();
        }
    }//GEN-LAST:event_txtAObservacionesKeyPressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
			addObservaciones dialog = new addObservaciones(new javax.swing.JFrame(), true);
			dialog.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosing(java.awt.event.WindowEvent e) {
					System.exit(0);
				}
			});
			dialog.setVisible(true);
		});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtAObservaciones;
    private javax.swing.JTextField txtClave;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
