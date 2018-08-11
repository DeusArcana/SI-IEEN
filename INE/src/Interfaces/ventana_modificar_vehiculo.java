/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import javax.swing.JTable;
import Clases.ManagerPermisos;
import Clases.ManagerComplemento;
import Clases.ManagerVehiculos;
import Clases.enviarFotoPOST;
import static Interfaces.Principal.Username;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kevin
 */
public class ventana_modificar_vehiculo extends javax.swing.JDialog {
    int tipo = 0;
    ManagerVehiculos vehiculos;
    ManagerPermisos manager_permisos;
    public static DefaultTableModel modelo;
    enviarFotoPOST managerPost;
    
    private String path, absolute_path, name;
    private int returnVal;
    File[] rutas;
    int contadorRutas;
    
    /**
     * Creates new form Ventana_permisos_puesto
     */
    public ventana_modificar_vehiculo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        campoRuta.setVisible(false);
        campo.setVisible(false);
        this.setLocationRelativeTo(null);

        vehiculos = new ManagerVehiculos();
        manager_permisos = new ManagerPermisos();
        managerPost = new enviarFotoPOST();

        //Quitar la coma al spinner
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(campoMotor, "#");
        campoMotor.setEditor(editor);
        //Quitar editable a spinner
        ((DefaultEditor) campoMotor.getEditor()).getTextField().setEditable(false);
        JSpinner.NumberEditor editor2 = new JSpinner.NumberEditor(campoModelo, "#");
        campoModelo.setEditor(editor2);
        //Quitar editable a spinner
        ((DefaultEditor) campoModelo.getEditor()).getTextField().setEditable(false);
        campoMatricula.setEditable(false);
        campoObservaciones.setLineWrap(true);

        
        
    }

   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_permisos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        campoClase = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        campoMotor = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        campoObservaciones = new javax.swing.JTextArea();
        campoMatricula = new javax.swing.JTextField();
        campoColor = new javax.swing.JTextField();
        campoMarca = new javax.swing.JTextField();
        campoModelo = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        campoLinea = new javax.swing.JTextField();
        campoKilometraje = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        imagenVehiculo = new javax.swing.JPanel();
        btnImagen = new javax.swing.JButton();
        contenedor = new javax.swing.JLabel();
        contadorImg = new javax.swing.JLabel();
        nuevasFotos = new javax.swing.JLabel();
        campoRuta = new javax.swing.JTextField();
        campo = new javax.swing.JTextField();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modificar datos del vehiculo");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pn_permisos.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel1.setText("* Linea:");
        pn_permisos.add(jLabel1);
        jLabel1.setBounds(20, 70, 70, 16);

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel2.setText("* Clase:");
        pn_permisos.add(jLabel2);
        jLabel2.setBounds(20, 110, 70, 16);

        campoClase.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        campoClase.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija tipo de carrocería  ...", "SUV", "Sedán", "Hatchback", "Pick Up", "Coupé" }));
        pn_permisos.add(campoClase);
        campoClase.setBounds(30, 130, 240, 30);

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel3.setText("* Marca:");
        pn_permisos.add(jLabel3);
        jLabel3.setBounds(20, 20, 70, 16);

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel4.setText("Modelo:");
        pn_permisos.add(jLabel4);
        jLabel4.setBounds(340, 20, 60, 20);

        campoMotor.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        campoMotor.setModel(new javax.swing.SpinnerNumberModel(4, 4, 8, 2));
        campoMotor.setToolTipText("Numero de cilindros del vehiculo ...");
        pn_permisos.add(campoMotor);
        campoMotor.setBounds(400, 120, 40, 30);

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel5.setText("Motor:");
        pn_permisos.add(jLabel5);
        jLabel5.setBounds(350, 130, 40, 16);

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel6.setText("Observaciones:");
        pn_permisos.add(jLabel6);
        jLabel6.setBounds(30, 220, 100, 16);

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel7.setText("* Color:");
        pn_permisos.add(jLabel7);
        jLabel7.setBounds(340, 80, 50, 16);

        campoObservaciones.setColumns(20);
        campoObservaciones.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        campoObservaciones.setRows(5);
        jScrollPane1.setViewportView(campoObservaciones);

        pn_permisos.add(jScrollPane1);
        jScrollPane1.setBounds(30, 240, 690, 120);

        campoMatricula.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        campoMatricula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoMatriculaKeyTyped(evt);
            }
        });
        pn_permisos.add(campoMatricula);
        campoMatricula.setBounds(400, 170, 110, 30);

        campoColor.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        pn_permisos.add(campoColor);
        campoColor.setBounds(400, 70, 110, 30);

        campoMarca.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        pn_permisos.add(campoMarca);
        campoMarca.setBounds(80, 12, 190, 30);

        campoModelo.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        campoModelo.setModel(new javax.swing.SpinnerNumberModel(2000, 1800, 2017, 1));
        pn_permisos.add(campoModelo);
        campoModelo.setBounds(400, 20, 100, 30);

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel8.setText("* Matricula:");
        pn_permisos.add(jLabel8);
        jLabel8.setBounds(320, 180, 70, 16);

        campoLinea.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        pn_permisos.add(campoLinea);
        campoLinea.setBounds(80, 62, 190, 30);

        campoKilometraje.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        campoKilometraje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoKilometrajeKeyTyped(evt);
            }
        });
        pn_permisos.add(campoKilometraje);
        campoKilometraje.setBounds(100, 170, 170, 30);

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel9.setText("* Kilometraje:");
        pn_permisos.add(jLabel9);
        jLabel9.setBounds(20, 180, 80, 16);

        jButton1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cancelar.png"))); // NOI18N
        jButton1.setText(" Cerrar");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pn_permisos.add(jButton1);
        jButton1.setBounds(370, 380, 140, 30);

        jButton2.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/aceptar.png"))); // NOI18N
        jButton2.setText(" Guardar");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        pn_permisos.add(jButton2);
        jButton2.setBounds(210, 380, 140, 30);

        imagenVehiculo.setBackground(new java.awt.Color(255, 255, 255));
        imagenVehiculo.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        imagenVehiculo.setLayout(null);

        btnImagen.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnImagen.setText("+");
        btnImagen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImagenActionPerformed(evt);
            }
        });
        imagenVehiculo.add(btnImagen);
        btnImagen.setBounds(160, 170, 40, 20);
        imagenVehiculo.add(contenedor);
        contenedor.setBounds(0, 0, 200, 190);

        pn_permisos.add(imagenVehiculo);
        imagenVehiculo.setBounds(520, 10, 200, 190);

        contadorImg.setText("0");
        pn_permisos.add(contadorImg);
        contadorImg.setBounds(610, 220, 50, 14);

        nuevasFotos.setText("0");
        pn_permisos.add(nuevasFotos);
        nuevasFotos.setBounds(520, 220, 50, 14);
        pn_permisos.add(campoRuta);
        campoRuta.setBounds(219, 210, 140, 30);
        pn_permisos.add(campo);
        campo.setBounds(400, 210, 50, 20);

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/formularios.png"))); // NOI18N
        fondo.setText("jLabel1");
        pn_permisos.add(fondo);
        fondo.setBounds(0, 0, 770, 570);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_permisos, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_permisos, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    ///NOTA-------- FALTA HACER COINCIDIR EL COMBO DEL TIPO DE AUTO
    public void cargarDatos() throws IOException, SQLException{
        //marca,linea,clase,kilometraje,modelo,color,motor,matricula,observaciones,estado
        Vector vVehiculos = vehiculos.infoVehiculos(campo.getText());
        String temporal[] = vVehiculos.get(0).toString().split(",");
        
        if(temporal[2].equals("SUV")){
            tipo = 1;
        }else if(temporal[2].equals("Sedán")){
            tipo = 2;
        }else if(temporal[2].equals("Hatchback")){
            tipo = 3;
        }else if(temporal[2].equals("Pick Up")){
            tipo = 4;
        }else if(temporal[2].equals("Coupé")){
            tipo = 5;
        }
        campoMarca.setText(temporal[0]);
        campoLinea.setText(temporal[1]);
        campoClase.setSelectedIndex(tipo);
        campoKilometraje.setText(temporal[3]);
        campoModelo.setValue(Integer.parseInt(temporal[4]));
        campoColor.setText(temporal[5]);
        
        campoMotor.setValue(Integer.parseInt(temporal[6]));
        campoMatricula.setText(temporal[7]);
        campoObservaciones.setText(temporal[8]);
        contadorImg.setText(temporal[9]);
    }
    
    
    
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            cargarDatos();
            // marca,linea,clase,kilometraje,modelo,color,motor,matricula,observaciones,estado
            //Cantidad
            //String temporal[] = vVehiculos.get(0).toString().split(",");
            //campoMarca.setText(temporal[1]);
//        etiquetaLinea.setText(temporal[1]);
//        etiquetaKilometraje.setText(temporal[3]+" km");
//        etiquetaAño.setText(temporal[4]);
//        campoObservaciones.setText(temporal[8]);
//        
//        etiquetaEstado.setText(temporal[9]);
// TODO add your handling code here:
// TODO add your handling code here: 
//        ImageIcon imgThisImg = new ImageIcon(campoRuta.getText());
//        ImageIcon icono = new ImageIcon(imgThisImg.getImage().getScaledInstance(contenedor.getWidth(), contenedor.getHeight(), Image.SCALE_DEFAULT));
//        contenedor.setIcon(icono);
//
//        
//       // ImageIcon imagen = new ImageIcon(path);
//        //ImageIcon icono = new ImageIcon(imagen.getImage().getScaledInstance(contenedor.getWidth(), contenedor.getHeight(), Image.SCALE_DEFAULT));
        } catch (IOException ex) {
            Logger.getLogger(ventana_modificar_vehiculo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ventana_modificar_vehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_formWindowOpened

    private void campoMatriculaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoMatriculaKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();

        if (Character.isLowerCase(c)) {
            String cad = ("" + c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }


    }//GEN-LAST:event_campoMatriculaKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    public boolean validarCampos() {
        boolean res = true;
        String marca, linea, kilometraje, matricula, color = "";
        marca = campoMarca.getText();
        linea = campoLinea.getText();
        kilometraje = campoKilometraje.getText();
        matricula = campoMatricula.getText();
        color = campoColor.getText();

        if (marca.isEmpty() || linea.isEmpty() || kilometraje.isEmpty() || matricula.isEmpty() || color.isEmpty() || campoClase.getSelectedIndex() == 0) {
            res = false;
        }
        return res;
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(manager_permisos.accesoModulo("actualizar","Vehiculos",Username)){
            if (validarCampos()) {
                String ruta = campoRuta.getText();
                if (!campoRuta.getText().isEmpty()) {
                    if (vehiculos.actualizarVehiculo(campoMarca.getText(), campoLinea.getText(), campoClase.getSelectedItem().toString(), campoColor.getText(),
                            campoModelo.getValue().toString(), campoMotor.getValue().toString(), campoKilometraje.getText(),
                            campoMatricula.getText(), campoObservaciones.getText(), contadorRutas+Integer.parseInt(contadorImg.getText()))) {
                        String nombreParametro = campoMarca.getText() + "_" + campoColor.getText() + "_" + campoMatricula.getText();
                        managerPost.prepararImagenesVehiculoActualizar(rutas, nombreParametro, contadorRutas,Integer.parseInt(contadorImg.getText()));
                        System.out.println(""+rutas+"\n"+ nombreParametro+"\n"+ contadorRutas+"\n"+Integer.parseInt(contadorImg.getText()));
                        //vehiculos.guardarImagen("C:\\Users\\oscar\\OneDrive\\Documentos\\NetBeansProjects\\INE\\src\\Iconos\\asd.png", "asd");
                        JOptionPane.showMessageDialog(null, "Informacion actualizada correctamente!", "Información!", JOptionPane.INFORMATION_MESSAGE);
                        Principal.tablaVehiculos.setModel(vehiculos.getVehiculos());
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar!", "Información!", JOptionPane.WARNING_MESSAGE);
                    }//else
                    
                } else {
                    //en este metodo la informacion se guarda sin cambios en la imagen
                    if (vehiculos.actualizarVehiculo(campoMarca.getText(), campoLinea.getText(), campoClase.getSelectedItem().toString(), campoColor.getText(),
                            campoModelo.getValue().toString(), campoMotor.getValue().toString(), campoKilometraje.getText(),
                            campoMatricula.getText(), campoObservaciones.getText(), contadorRutas+Integer.parseInt(contadorImg.getText()))) {
                        String nombreParametro = campoMarca.getText() + "_" + campoColor.getText() + "_" + campoMatricula.getText();
                        JOptionPane.showMessageDialog(null, "Informacion actualizada correctamente!", "Información!", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                        managerPost.prepararImagenesVehiculoActualizar(rutas, nombreParametro, contadorRutas,Integer.parseInt(contadorImg.getText()));
                        
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar!", "Información!", JOptionPane.WARNING_MESSAGE);
                    }//else
                }

            } else {
                JOptionPane.showMessageDialog(null, "Llene todos los campos requeridos!", "Información!", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para actualizar la información del vehiculo.", "Información!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImagenActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser(System.getProperty("user.home") + "\\Pictures");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagenes JPG,GIF & PNG", "jpg", "gif", "png");
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileFilter(filter);
        returnVal = chooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            name = chooser.getSelectedFile().getName();
            absolute_path = chooser.getSelectedFile().getAbsolutePath();
            path = chooser.getSelectedFile().getPath();

            rutas = chooser.getSelectedFiles();
            contadorRutas = chooser.getSelectedFiles().length;
            //System.out.println("Archivo: " + name);
            //System.out.println("Absolute Path: " + absolute_path);
            //System.out.println("Path: " + path);
            BufferedImage img = null;
            nuevasFotos.setText("" + contadorRutas);
            nuevasFotos.setVisible(true);

            try {
                img = ImageIO.read(new File(absolute_path));
                Image dimg = img.getScaledInstance(contenedor.getWidth(), contenedor.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon image = new ImageIcon(dimg);
                contenedor.setText("");
                contenedor.setIcon(image);
                //this.jButton1.setEnabled(true);
            } catch (IOException e) {
                System.err.println(e.toString());
            }

        }

    }//GEN-LAST:event_btnImagenActionPerformed

    private void campoKilometrajeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoKilometrajeKeyTyped
        // TODO add your handling code here:
        // TODO add your handling code here:
        char caracter = evt.getKeyChar();

        if (campoKilometraje.getText().length() == 6) {
            evt.consume();
        } else if (caracter != evt.getKeyCode()) {
        }
        if (((caracter < '0') || (caracter > '9'))) {

            evt.consume();
        } else {

        }
    }//GEN-LAST:event_campoKilometrajeKeyTyped
    
    
    
    public static void limpiarTablaPermisos() {
        int a = modelo.getRowCount() - 1;
        for (int i = 0; i <= a; i++) {
            modelo.removeRow(0);
        }
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
                ventana_modificar_vehiculo dialog = new ventana_modificar_vehiculo(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnImagen;
    public static javax.swing.JTextField campo;
    private javax.swing.JComboBox<String> campoClase;
    private javax.swing.JTextField campoColor;
    private javax.swing.JTextField campoKilometraje;
    private javax.swing.JTextField campoLinea;
    private javax.swing.JTextField campoMarca;
    private javax.swing.JTextField campoMatricula;
    private javax.swing.JSpinner campoModelo;
    private javax.swing.JSpinner campoMotor;
    private javax.swing.JTextArea campoObservaciones;
    private javax.swing.JTextField campoRuta;
    private javax.swing.JLabel contadorImg;
    private javax.swing.JLabel contenedor;
    private javax.swing.JLabel fondo;
    private javax.swing.JPanel imagenVehiculo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nuevasFotos;
    private javax.swing.JPanel pn_permisos;
    // End of variables declaration//GEN-END:variables
}
