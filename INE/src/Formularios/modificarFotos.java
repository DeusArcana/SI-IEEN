/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Clases.enviarFotoPOST;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author oscar
 */
public class modificarFotos extends javax.swing.JDialog {
    String buscar;
    int cantidadFotos;
    private String path, absolute_path, name;
    private int returnVal;
    File[] rutas;
    enviarFotoPOST managerPost;

    /**
     * Creates new form modificarFotos
     */
    public modificarFotos(javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        this.setLocationRelativeTo(null);
    }

    public void recuperarCantidad(int cantidadFotos,String clave) {
        this.cantidadFotos = cantidadFotos;
        //crearPaneles(cantidadFotos);
        crearLabels(cantidadFotos,clave);
        managerPost = new enviarFotoPOST();

    }
    
    public void cargarImagen(String busqueda, int i, JLabel label) {
        //FUNCIONA
        BufferedImage img = null;
        String ip = "";
        try {
            //Creamos un archivo FileReader que obtiene lo que tenga el archivo
            FileReader lector = new FileReader("cnfg.ntw");

            //El contenido de lector se guarda en un BufferedReader
            BufferedReader contenido = new BufferedReader(lector);

            ip = contenido.readLine();
        } catch (Exception e) {

        }
        try {
            // agregar la IP dinamicamente OJO

            String cadena = "\\\\" + ip + "\\imagenes\\vehiculos\\" + busqueda + "\\" + busqueda + i + ".png";
            System.out.println("" + cadena);
            img = ImageIO.read(new File(cadena));
        } catch (IOException ex) {
            Logger.getLogger(modificarFotos.class.getName()).log(Level.SEVERE, null, ex);

        }
        Image dimg = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(dimg);
        label.setIcon(image);

        jPanel1.add(label);

    }


    public void clic(MouseEvent evt, String nombre, int i) {
        System.out.println("CLIC " + nombre);

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

        }
        if (managerPost.actualizarFoto(rutas, nombre, i)) {
            JOptionPane.showMessageDialog(null, "Imagen cambiada correctamente.", "Atención", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Se produjo un error al intentar cambiar la imagen.", "Error!", JOptionPane.WARNING_MESSAGE);
            this.dispose();
        }

    }
    
    public void crearLabels(int cantidad, String nombre) {
        System.out.println("Cantidad: " + cantidad);
        JLabel[] label = new JLabel[cantidad];
        for (int i = 0; i < cantidad; i++) {
            label[i] = new JLabel();
            label[i].setPreferredSize(new Dimension(120, 120));
            label[i].setBackground(Color.red);
            label[i].setOpaque(true);
            label[i].setName(nombre);
            label[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            //label[i].setText(nombre + i);
            GroupLayout layout = new GroupLayout(label[i]);
            label[i].setLayout(layout);

            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            String titulo = label[i].getName();
            int contador = i ;
            label[i].addMouseListener(new java.awt.event.MouseAdapter() {

                public void mouseClicked(MouseEvent evt) {

                    clic(evt, titulo, contador);
                }
            });
            cargarImagen(titulo, i, label[i]);
        }// for
    }// crearLabels

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cambiar imagenes");
        setBackground(new java.awt.Color(255, 102, 102));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel2.add(jPanel1);
        jPanel1.setBounds(10, 50, 690, 360);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Selecciona la foto que deseas cambiar.");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(0, 10, 710, 22);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1MouseClicked

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
            java.util.logging.Logger.getLogger(modificarFotos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(modificarFotos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(modificarFotos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(modificarFotos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                modificarFotos dialog = new modificarFotos(new javax.swing.JDialog(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
