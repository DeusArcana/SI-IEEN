/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Clases.ManagerUsers;
import Clases.ManagerComplemento;
import Clases.ManagerPermisos;

import Interfaces.Principal;
import static Interfaces.Principal.comboEmpUsu;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author kevin
 */
public class addEmpleados extends javax.swing.JDialog {
    String nombres,apellido_p,apellido_m,telefono,curp,rfc,calle,colonia,fecha,codigoP,municipio,localidad,busqueda;
    boolean documentacion;
    int area,puesto,filtro;
    int[] ids_area,ids_puesto;
    
    ManagerUsers manager_users;
    ManagerComplemento manager_complemento;
    ManagerPermisos manager_permisos;
    /**
     * Creates new form addEmpleados
     */
    public addEmpleados(java.awt.Frame parent, boolean modal, int filtro, String busqueda) {
        super(parent, modal);
        initComponents();
        this.busqueda = busqueda;
        this.filtro = filtro;
        //Asignamos memoria a los objetos
        manager_users = new ManagerUsers();
        manager_complemento = new ManagerComplemento();
        manager_permisos = new ManagerPermisos();
        
        JTextFieldDateEditor date_Salida_Editor=(JTextFieldDateEditor) txtFecha.getDateEditor();
        date_Salida_Editor.setEditable(false);
        txtFecha.getJCalendar().setMaxSelectableDate(new Date()); // sets today as minimum selectable date        
        
        //Asignar fecha por default
        Date date = new Date();        
        txtFecha.setDate(date);
        
        this.setLocationRelativeTo(null);
        this.setTitle("Registro de nuevo empleado");
        
    }

    private addEmpleados(JFrame jFrame, boolean b) {
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

        pn_empleado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtApellidoP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtApellidoM = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCalle = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtColonia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCurp = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtRfc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtFecha = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        txtMunicipio = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtLocalidad = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        comboArea = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        comboPuesto = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pn_empleado.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Nombre(s):");
        pn_empleado.add(jLabel1);
        jLabel1.setBounds(44, 14, 70, 17);

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNombre.setNextFocusableComponent(txtApellidoP);
        pn_empleado.add(txtNombre);
        txtNombre.setBounds(118, 11, 202, 25);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Apellido Paterno:");
        pn_empleado.add(jLabel2);
        jLabel2.setBounds(12, 43, 102, 17);

        txtApellidoP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtApellidoP.setNextFocusableComponent(txtApellidoM);
        pn_empleado.add(txtApellidoP);
        txtApellidoP.setBounds(118, 40, 202, 25);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Apellido Materno:");
        pn_empleado.add(jLabel3);
        jLabel3.setBounds(10, 72, 104, 17);

        txtApellidoM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtApellidoM.setNextFocusableComponent(txtCalle);
        pn_empleado.add(txtApellidoM);
        txtApellidoM.setBounds(118, 69, 202, 25);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Calle:");
        pn_empleado.add(jLabel4);
        jLabel4.setBounds(82, 101, 32, 17);

        txtCalle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCalle.setNextFocusableComponent(txtColonia);
        pn_empleado.add(txtCalle);
        txtCalle.setBounds(118, 98, 202, 25);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Colonia:");
        pn_empleado.add(jLabel5);
        jLabel5.setBounds(65, 130, 49, 17);

        txtColonia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtColonia.setNextFocusableComponent(txtMunicipio);
        pn_empleado.add(txtColonia);
        txtColonia.setBounds(118, 127, 202, 25);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Teléfono:");
        pn_empleado.add(jLabel6);
        jLabel6.setBounds(420, 14, 57, 17);

        txtTelefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTelefono.setNextFocusableComponent(txtCodigo);
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });
        pn_empleado.add(txtTelefono);
        txtTelefono.setBounds(487, 11, 202, 25);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Código Postal:");
        pn_empleado.add(jLabel7);
        jLabel7.setBounds(395, 43, 88, 17);

        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCodigo.setNextFocusableComponent(txtCurp);
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });
        pn_empleado.add(txtCodigo);
        txtCodigo.setBounds(487, 40, 202, 25);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("CURP:");
        pn_empleado.add(jLabel8);
        jLabel8.setBounds(443, 72, 40, 17);

        txtCurp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCurp.setNextFocusableComponent(txtRfc);
        txtCurp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCurpKeyTyped(evt);
            }
        });
        pn_empleado.add(txtCurp);
        txtCurp.setBounds(487, 69, 202, 25);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("RFC:");
        pn_empleado.add(jLabel9);
        jLabel9.setBounds(453, 101, 30, 17);

        txtRfc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtRfc.setNextFocusableComponent(txtFecha);
        txtRfc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRfcKeyTyped(evt);
            }
        });
        pn_empleado.add(txtRfc);
        txtRfc.setBounds(487, 98, 202, 25);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Fecha de nacimiento:");
        pn_empleado.add(jLabel10);
        jLabel10.setBounds(353, 130, 130, 17);

        txtFecha.setNextFocusableComponent(comboArea);
        pn_empleado.add(txtFecha);
        txtFecha.setBounds(487, 130, 202, 25);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Municipio:");
        pn_empleado.add(jLabel15);
        jLabel15.setBounds(54, 159, 60, 17);

        txtMunicipio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMunicipio.setNextFocusableComponent(txtLocalidad);
        pn_empleado.add(txtMunicipio);
        txtMunicipio.setBounds(118, 156, 202, 25);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Localidad:");
        pn_empleado.add(jLabel16);
        jLabel16.setBounds(53, 188, 61, 17);

        txtLocalidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtLocalidad.setNextFocusableComponent(txtTelefono);
        pn_empleado.add(txtLocalidad);
        txtLocalidad.setBounds(118, 185, 202, 25);

        btnAceptar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/aceptar.png"))); // NOI18N
        btnAceptar.setText(" Aceptar");
        btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        pn_empleado.add(btnAceptar);
        btnAceptar.setBounds(220, 240, 140, 33);

        btnCancelar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText(" Cancelar");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        pn_empleado.add(btnCancelar);
        btnCancelar.setBounds(371, 240, 140, 33);

        comboArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboArea.setNextFocusableComponent(comboPuesto);
        comboArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAreaActionPerformed(evt);
            }
        });
        pn_empleado.add(comboArea);
        comboArea.setBounds(490, 170, 190, 23);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Puesto:");
        pn_empleado.add(jLabel18);
        jLabel18.setBounds(432, 200, 50, 17);

        comboPuesto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboPuesto.setNextFocusableComponent(btnAceptar);
        comboPuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPuestoActionPerformed(evt);
            }
        });
        pn_empleado.add(comboPuesto);
        comboPuesto.setBounds(490, 200, 190, 23);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Área:");
        pn_empleado.add(jLabel14);
        jLabel14.setBounds(450, 170, 32, 17);

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/formularios.png"))); // NOI18N
        pn_empleado.add(jLabel17);
        jLabel17.setBounds(0, 0, 720, 290);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_empleado, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void getInfo(){
        nombres = txtNombre.getText();
        apellido_p = txtApellidoP.getText();
        apellido_m = txtApellidoM.getText();
        
        //Calle
        if(txtCalle.getText().isEmpty()){
            calle = "Sin especificar";
        }else{
            calle = txtCalle.getText();
        }
        //Colonia
        if(txtColonia.getText().isEmpty()){
            colonia = "Sin especificar";
        }else{
            colonia = txtColonia.getText();
        }
        //Telefono
        if(txtTelefono.getText().isEmpty()){
            telefono = "Sin especificar";
        }else{
            telefono = txtTelefono.getText();
        }
        //Código Postal
        if(txtCodigo.getText().isEmpty()){
            codigoP = "Sin CP";
        }else{
            codigoP = txtCodigo.getText();
        }
        //CURP
        if(txtCurp.getText().isEmpty()){
            curp = "Sin especificar";
        }else{
            curp = txtCurp.getText();
        }
        //RFC
        if(txtRfc.getText().isEmpty()){
            rfc = "Sin RFC";
        }else{
            rfc = txtRfc.getText();
        }
        //Municipio
        if(txtMunicipio.getText().isEmpty()){
            municipio = "Sin especificar";
        }else{
            municipio = txtMunicipio.getText();
        }
        //Localidad
        if(txtLocalidad.getText().isEmpty()){
            localidad = "Sin especificar";
        }else{
            localidad = txtLocalidad.getText();
        }
        area = ids_area[comboArea.getSelectedIndex()];
        puesto = ids_puesto[comboPuesto.getSelectedIndex()];
        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        fecha = formato.format(txtFecha.getDate());
    }//getInfo()
    
    private int validar(){
        if(txtNombre.getText().isEmpty()){
            return 1;
        }
        if(txtApellidoP.getText().isEmpty()){
            return 2;
        }
        if(txtApellidoM.getText().isEmpty()){
            return 3;
        }
        if(txtFecha.getDate() == null){
            return 4;
        }
        
        return 0;
    }//validar()
    
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        int res = validar();
        if(manager_permisos.accesoModulo("alta","Empleados",Principal.Username)){
            switch(res){
                case 0:
                    getInfo();
                    boolean insertar = manager_users.insertarEmpleado(nombres, apellido_p, apellido_m, telefono, calle, colonia, curp, rfc, fecha, codigoP, municipio,localidad,area,puesto);
                    if(insertar){
                        JOptionPane.showMessageDialog(null, "El empleado \""+nombres+" "+apellido_p+"\" ha sido registrado en la base de datos exitosamente.");
                        if(manager_permisos.accesoModulo("consulta","Empleados",Principal.Username)){
                            if(comboEmpUsu.getSelectedItem().toString().equals("Empleados sin usuario")){
                                Principal.tablaUsuarios.setModel(manager_users.getEmpleadosSinUsuario(filtro,busqueda,Principal.comboEmpUsuEstatus.getSelectedItem().toString()));
                            }else{
                                Principal.tablaUsuarios.setModel(manager_users.getEmpleados(Principal.Username,filtro,busqueda,Principal.comboEmpUsuEstatus.getSelectedItem().toString()));
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Han revocado sus permisos para consulta de empleados");
                            Principal.tablaUsuarios.setModel(new DefaultTableModel());
                        }
                        this.dispose();
                    }else{
                            JOptionPane.showMessageDialog(null, "Verificar con el distribuidor.");
                            this.dispose();
                    }
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Por favor ingresa el nombre del nuevo empleado");
                    txtNombre.requestFocus();
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Por favor ingresa el apellido paterno del nuevo empleado");
                    txtApellidoP.requestFocus();
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Por favor ingresa el apellido materno del nuevo empleado");
                    txtApellidoM.requestFocus();
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "Por favor ingresa la fecha de nacimiento del nuevo empleado");
                    txtFecha.requestFocus();
                    break;
            }//switch
        }else{
            JOptionPane.showMessageDialog(null, "Te han revocado los permisos para registrar nuevos empleados.");
        }
        
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        
        //ComboArea
        String lista = manager_complemento.obtenerAreas();
        String[] recoger = lista.split(",,");
        ids_area = new int[recoger.length/2];
        
        comboArea.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        for(int i = 1,j = 0; i <= recoger.length;i = i+2,j++){
            comboArea.addItem(recoger[i]);
            ids_area[j] = Integer.parseInt(recoger[i-1]);
        }
    }//GEN-LAST:event_formWindowOpened

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
         // TODO add your handling code here:
         this.dispose();
        
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void comboAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAreaActionPerformed
        // TODO add your handling code here:
        //ComboPuesto
        String lista = manager_complemento.obtenerPuestos(comboArea.getSelectedIndex()+1);
        String[] recoger = lista.split(",,");
        ids_puesto = new int[recoger.length/2];
        
        comboPuesto.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        for(int i = 1,j = 0; i <= recoger.length;i = i+2,j++){
            comboPuesto.addItem(recoger[i]);
            ids_puesto[j] = Integer.parseInt(recoger[i-1]);
        }
    }//GEN-LAST:event_comboAreaActionPerformed

    private void comboPuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPuestoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPuestoActionPerformed

    private void txtCurpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurpKeyTyped
        // TODO add your handling code here:

        char c = evt.getKeyChar();

        if (Character.isLowerCase(c)) {
            String cad = ("" + c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }

        if (txtCurp.getText().length() == 18) {
            evt.consume();
        }


    }//GEN-LAST:event_txtCurpKeyTyped

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        // TODO add your handling code here:
        char caracter = evt.getKeyChar();

        if (txtCodigo.getText().length() == 5) {
            evt.consume();
        } else if (caracter != evt.getKeyCode()) {
        }
        if (((caracter < '0') || (caracter > '9'))) {

            evt.consume();
        } else {

        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtRfcKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRfcKeyTyped
         // TODO add your handling code here:
         char c = evt.getKeyChar();

        if (Character.isLowerCase(c)) {
            String cad = ("" + c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }

        if (txtRfc.getText().length() == 13) {
            evt.consume();
        }
    }//GEN-LAST:event_txtRfcKeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
         // TODO add your handling code here:
        char caracter = evt.getKeyChar();

        if (txtTelefono.getText().length() == 13) {
            evt.consume();
        } else if (caracter != evt.getKeyCode()) {
        }
        if(((caracter < '0') || (caracter > '9'))  && (caracter != evt.VK_SPACE) && (caracter != '-')){
            
            evt.consume();
        }else{
            
        }

    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
         // TODO add your handling code here:
         
    }//GEN-LAST:event_formWindowActivated

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
         // TODO add your handling code here:
    }//GEN-LAST:event_formWindowGainedFocus

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

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                addEmpleados dialog = new addEmpleados(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> comboArea;
    private javax.swing.JComboBox<String> comboPuesto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel pn_empleado;
    private javax.swing.JTextField txtApellidoM;
    private javax.swing.JTextField txtApellidoP;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtColonia;
    private javax.swing.JTextField txtCurp;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtLocalidad;
    private javax.swing.JTextField txtMunicipio;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRfc;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
