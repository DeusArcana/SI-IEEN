/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Clases.Archivo;
import clasesBackground.ImagenFondo;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;

public class fr_Generacion_Vale_Recoleccion_Bienes extends javax.swing.JFrame {

    String Responsable,Cargo,Area,Tipo_de_uso,Municipio,Localidad,No_inventario,No_serie,Descripcion,Marca,Modelo,Color;
    public fr_Generacion_Vale_Recoleccion_Bienes() throws IOException {
        initComponents();
        cargarDatosEncargado();
        dp_background_principal.setBorder(new ImagenFondo());
    }
    
    public void cargarDatosEncargado() throws IOException{
        String datosEncargado[]=(Archivo.leerContenidoArchivo("vale_recoleccion_configs.txt")).replaceAll("$", "").split(",");
        System.out.println("Responsable= "+datosEncargado[0]);
        Responsable=datosEncargado[0];tf_responsable.setText(Responsable);
        Cargo=datosEncargado[1];tf_cargo.setText(Cargo);
        Area=datosEncargado[2];tf_area.setText(Area);
        Tipo_de_uso=datosEncargado[3];tf_tipo_de_uso.setText(Tipo_de_uso);
        Municipio=datosEncargado[4];tf_municipio.setText(Municipio);
        Localidad=datosEncargado[5];tf_localidad.setText(Localidad.replaceAll("$", ""));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pn_encabezado = new javax.swing.JPanel();
        dp_background_principal = new javax.swing.JDesktopPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        pb_principal = new javax.swing.JPanel();
        pn_datos_responsable = new javax.swing.JPanel();
        lb_responsable = new javax.swing.JLabel();
        lb_cargo = new javax.swing.JLabel();
        tf_responsable = new javax.swing.JTextField();
        tf_cargo = new javax.swing.JTextField();
        tf_area = new javax.swing.JTextField();
        lb_area = new javax.swing.JLabel();
        lb_tipo_de_uso = new javax.swing.JLabel();
        tf_tipo_de_uso = new javax.swing.JTextField();
        lb_municipio = new javax.swing.JLabel();
        tf_municipio = new javax.swing.JTextField();
        tf_localidad = new javax.swing.JTextField();
        lb_localidad = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pn_datos_bien_asignados = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pn_acciones = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Generación de Vales de Resguardo de Bienes");
        setPreferredSize(new java.awt.Dimension(1372, 836));

        pn_encabezado.setBackground(new java.awt.Color(255, 204, 204));
        pn_encabezado.setAlignmentX(0.0F);
        pn_encabezado.setAlignmentY(0.0F);
        pn_encabezado.setPreferredSize(new java.awt.Dimension(1576, 150));
        pn_encabezado.setRequestFocusEnabled(false);

        javax.swing.GroupLayout pn_encabezadoLayout = new javax.swing.GroupLayout(pn_encabezado);
        pn_encabezado.setLayout(pn_encabezadoLayout);
        pn_encabezadoLayout.setHorizontalGroup(
            pn_encabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1576, Short.MAX_VALUE)
        );
        pn_encabezadoLayout.setVerticalGroup(
            pn_encabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        getContentPane().add(pn_encabezado, java.awt.BorderLayout.NORTH);

        dp_background_principal.setBackground(new java.awt.Color(153, 153, 255));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pb_principal.setBackground(new java.awt.Color(255, 153, 152));

        pn_datos_responsable.setBackground(new java.awt.Color(255, 153, 153));
        pn_datos_responsable.setLayout(new java.awt.GridBagLayout());

        lb_responsable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_responsable.setText("Responsable");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pn_datos_responsable.add(lb_responsable, gridBagConstraints);

        lb_cargo.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_cargo.setText("Cargo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pn_datos_responsable.add(lb_cargo, gridBagConstraints);

        tf_responsable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        tf_responsable.setToolTipText("");
        tf_responsable.setMargin(new java.awt.Insets(0, 0, 0, 0));
        tf_responsable.setMinimumSize(new java.awt.Dimension(100, 30));
        tf_responsable.setPreferredSize(new java.awt.Dimension(200, 30));
        tf_responsable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_responsableActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pn_datos_responsable.add(tf_responsable, gridBagConstraints);

        tf_cargo.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        tf_cargo.setMinimumSize(new java.awt.Dimension(100, 30));
        tf_cargo.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pn_datos_responsable.add(tf_cargo, gridBagConstraints);

        tf_area.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        tf_area.setMinimumSize(new java.awt.Dimension(100, 30));
        tf_area.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pn_datos_responsable.add(tf_area, gridBagConstraints);

        lb_area.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_area.setText("Area");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pn_datos_responsable.add(lb_area, gridBagConstraints);

        lb_tipo_de_uso.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_tipo_de_uso.setText("Tipo de uso");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pn_datos_responsable.add(lb_tipo_de_uso, gridBagConstraints);

        tf_tipo_de_uso.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        tf_tipo_de_uso.setMinimumSize(new java.awt.Dimension(100, 30));
        tf_tipo_de_uso.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pn_datos_responsable.add(tf_tipo_de_uso, gridBagConstraints);

        lb_municipio.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_municipio.setText("Municipio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pn_datos_responsable.add(lb_municipio, gridBagConstraints);

        tf_municipio.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        tf_municipio.setMinimumSize(new java.awt.Dimension(100, 30));
        tf_municipio.setPreferredSize(new java.awt.Dimension(200, 30));
        tf_municipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_municipioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pn_datos_responsable.add(tf_municipio, gridBagConstraints);

        tf_localidad.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        tf_localidad.setMinimumSize(new java.awt.Dimension(100, 30));
        tf_localidad.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pn_datos_responsable.add(tf_localidad, gridBagConstraints);

        lb_localidad.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lb_localidad.setText("Localidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pn_datos_responsable.add(lb_localidad, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel1.setText("Datos del responsable");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        pn_datos_responsable.add(jLabel1, gridBagConstraints);

        pn_datos_bien_asignados.setBackground(new java.awt.Color(255, 153, 153));
        pn_datos_bien_asignados.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(121, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pb_principalLayout = new javax.swing.GroupLayout(pb_principal);
        pb_principal.setLayout(pb_principalLayout);
        pb_principalLayout.setHorizontalGroup(
            pb_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pb_principalLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(pb_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pn_datos_bien_asignados, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_datos_responsable, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(145, 145, 145))
        );
        pb_principalLayout.setVerticalGroup(
            pb_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pb_principalLayout.createSequentialGroup()
                .addContainerGap(139, Short.MAX_VALUE)
                .addComponent(pn_datos_responsable, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_datos_bien_asignados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176))
        );

        jScrollPane2.setViewportView(pb_principal);

        javax.swing.GroupLayout dp_background_principalLayout = new javax.swing.GroupLayout(dp_background_principal);
        dp_background_principal.setLayout(dp_background_principalLayout);
        dp_background_principalLayout.setHorizontalGroup(
            dp_background_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dp_background_principalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 762, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(804, Short.MAX_VALUE))
        );
        dp_background_principalLayout.setVerticalGroup(
            dp_background_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dp_background_principalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        dp_background_principal.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(dp_background_principal, java.awt.BorderLayout.CENTER);

        pn_acciones.setBackground(new java.awt.Color(255, 204, 204));
        pn_acciones.setAlignmentX(0.0F);
        pn_acciones.setAlignmentY(0.0F);
        pn_acciones.setMinimumSize(new java.awt.Dimension(1372, 100));
        pn_acciones.setPreferredSize(new java.awt.Dimension(1372, 100));

        jButton1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jButton1.setText("Generar PDF");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_accionesLayout = new javax.swing.GroupLayout(pn_acciones);
        pn_acciones.setLayout(pn_accionesLayout);
        pn_accionesLayout.setHorizontalGroup(
            pn_accionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_accionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(1435, Short.MAX_VALUE))
        );
        pn_accionesLayout.setVerticalGroup(
            pn_accionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_accionesLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        getContentPane().add(pn_acciones, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf_municipioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_municipioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_municipioActionPerformed

    private void tf_responsableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_responsableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_responsableActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            Responsable=tf_responsable.getText();Cargo=tf_cargo.getText();Area=tf_area.getText();Tipo_de_uso=tf_tipo_de_uso.getText();
            Municipio=tf_municipio.getText();Localidad=tf_localidad.getText();
            
            JFileChooser file=new JFileChooser();
            file.showSaveDialog(this);
            File guarda =file.getSelectedFile();

            FileOutputStream archivo = new FileOutputStream(guarda+".pdf");

            
            
            
            /**/
            Document doc =new Document(PageSize.LETTER, 30, 30, 30, 30);
            PdfWriter writer= PdfWriter.getInstance(doc, archivo);
            doc.open();
            //doc.add(new Paragraph("Prueba",FontFactory.getFont(FontFactory.COURIER,10,Font.NORMAL,BaseColor.BLACK)));
            //Imagen de fondo
            Image imagen = Image.getInstance("img_vale_recoleccion_bienes.jpg"); 
            imagen.scaleToFit(600, 1300);
            imagen.setAbsolutePosition(0, 0);
            doc.add(imagen);
            //Creación de objeto para formato de texto
            //BaseFont bf = BaseFont.createFont();
            BaseFont bf = BaseFont.createFont(BaseFont.COURIER, "", rootPaneCheckingEnabled);
            //Declaración de textos
            PdfContentByte txt_Responsable = writer.getDirectContent();
            PdfContentByte txt_Cargo = writer.getDirectContent();
            PdfContentByte txt_Area = writer.getDirectContent();
            PdfContentByte txt_Tipo_de_uso = writer.getDirectContent();
            PdfContentByte txt_Municipio = writer.getDirectContent();
            PdfContentByte txt_Localidad = writer.getDirectContent();
            
            //Responsable
            txt_Responsable.saveState();
            txt_Responsable.beginText();
            txt_Responsable.setLineWidth(1.0f);
            txt_Responsable.setRGBColorStroke(0x10, 0x10, 0x10);
            txt_Responsable.setFontAndSize(bf, 9);
            txt_Responsable.setTextMatrix(100, 614);
            txt_Responsable.showText(Responsable);
            txt_Responsable.endText();
            txt_Responsable.restoreState();
            //Cargo
            txt_Cargo.saveState();
            txt_Cargo.beginText();
            txt_Cargo.setLineWidth(1.0f);
            txt_Cargo.setRGBColorStroke(0x10, 0x10, 0x10);
            txt_Cargo.setFontAndSize(bf, 9);
            txt_Cargo.setTextMatrix(100, 600);
            txt_Cargo.showText(Cargo);
            txt_Cargo.endText();
            txt_Cargo.restoreState();
            //Area
            txt_Area.saveState();
            txt_Area.beginText();
            txt_Area.setLineWidth(1.0f);
            txt_Area.setRGBColorStroke(0x10, 0x10, 0x10);
            txt_Area.setFontAndSize(bf, 9);
            txt_Area.setTextMatrix(100, 586);
            txt_Area.showText(Area);
            txt_Area.endText();
            txt_Area.restoreState();
            //Tipo_de_uso
            txt_Tipo_de_uso.saveState();
            txt_Tipo_de_uso.beginText();
            txt_Tipo_de_uso.setLineWidth(1.0f);
            txt_Tipo_de_uso.setRGBColorStroke(0x10, 0x10, 0x10);
            txt_Tipo_de_uso.setFontAndSize(bf, 9);
            txt_Tipo_de_uso.setTextMatrix(425, 614);
            txt_Tipo_de_uso.showText(Tipo_de_uso);
            txt_Tipo_de_uso.endText();
            txt_Tipo_de_uso.restoreState();
            //Municipio
            txt_Municipio.saveState();
            txt_Municipio.beginText();
            txt_Municipio.setLineWidth(1.0f);
            txt_Municipio.setRGBColorStroke(0x10, 0x10, 0x10);
            txt_Municipio.setFontAndSize(bf, 9);
            txt_Municipio.setTextMatrix(425, 600);
            txt_Municipio.showText(Municipio);
            txt_Municipio.endText();
            txt_Municipio.restoreState();
            //Localidad
            txt_Localidad.saveState();
            txt_Localidad.beginText();
            txt_Localidad.setLineWidth(1.0f);
            txt_Localidad.setRGBColorStroke(0x10, 0x10, 0x10);
            txt_Localidad.setFontAndSize(bf, 9);
            txt_Localidad.setTextMatrix(425, 586);
            txt_Localidad.showText(Localidad);
            txt_Localidad.endText();
            txt_Localidad.restoreState();
            
            JOptionPane.showMessageDialog(null,"El archivo se a guardado Exitosamente","Información",JOptionPane.INFORMATION_MESSAGE);
            doc.close();
        }catch(Exception e){}
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(fr_Generacion_Vale_Resguardo_Bienes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(fr_Generacion_Vale_Resguardo_Bienes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(fr_Generacion_Vale_Resguardo_Bienes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(fr_Generacion_Vale_Resguardo_Bienes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new fr_Generacion_Vale_Recoleccion_Bienes().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(fr_Generacion_Vale_Recoleccion_Bienes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane dp_background_principal;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lb_area;
    private javax.swing.JLabel lb_cargo;
    private javax.swing.JLabel lb_localidad;
    private javax.swing.JLabel lb_municipio;
    private javax.swing.JLabel lb_responsable;
    private javax.swing.JLabel lb_tipo_de_uso;
    private javax.swing.JPanel pb_principal;
    private javax.swing.JPanel pn_acciones;
    private javax.swing.JPanel pn_datos_bien_asignados;
    private javax.swing.JPanel pn_datos_responsable;
    private javax.swing.JPanel pn_encabezado;
    private javax.swing.JTextField tf_area;
    private javax.swing.JTextField tf_cargo;
    private javax.swing.JTextField tf_localidad;
    private javax.swing.JTextField tf_municipio;
    private javax.swing.JTextField tf_responsable;
    private javax.swing.JTextField tf_tipo_de_uso;
    // End of variables declaration//GEN-END:variables
}
