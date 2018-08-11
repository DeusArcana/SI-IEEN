/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Clases.ManagerInventario;
import Clases.ManagerPermisos;
import Clases.Validaciones;
import Clases.enviarFotoPOST;

import Interfaces.Principal;
import static Interfaces.Principal.comboEstatus;
import static Interfaces.Principal.comboFolio;
import static Interfaces.Principal.nomeclaturas;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
//import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author kevin
 */
public final class updateInventario extends javax.swing.JDialog {
    ManagerInventario manager_inventario;
    ManagerPermisos manager_permisos;
    enviarFotoPOST managerPOST;
    
    String folio,extension,producto,descripcion,ubicacion,marca,observaciones,noserie,color,modelo,imagen,fecha_compra,factura;
    String id;
    int numero;
    float importe;
    private String path, absolute_path, name;
    private int returnVal;
    File[] rutas;
    int contadorRutas;
    String [] nomeclaturas,folios;
    
    /**
     * Creates new form addInventario
     */
    public updateInventario(java.awt.Frame parent, boolean modal,String ClaveProd) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        
        this.setTitle("Actualización de información del produto \""+ClaveProd+"\"");
        
        //Asginamos memoria al objeto
        manager_inventario = new ManagerInventario();
        manager_permisos = new ManagerPermisos();
        managerPOST = new enviarFotoPOST();
        
        //Esto es para obtener todos las nomeclturas y sus descripciones
        String lista = manager_inventario.nomeclaturaFolio();
        folios = lista.split(",");
        nomeclaturas = new String[folios.length/2];
        
        //comboFolio
        comboFolio.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        comboFolio.addItem("Selecciona un folio...");
        for(int i = 1,j = 0; i <= folios.length;i = i+2,j++){
            comboFolio.addItem(folios[i]);
            nomeclaturas[j] = folios[i-1];
        }
        
        //comboUbicacion
        manager_inventario.getBodegas(comboUbicacion);
        
        //Guardamos la clave del producto para hacer la consulta que nos traiga los datos para actualizarlo
        this.id = ClaveProd;
        //Cadena con todos los datos de la consulta
        String datosProd = manager_inventario.obtenerDatosProd(id);
        //Acomodamos los datos donde van
        colocarDatos(datosProd);
        
        //Ruta para la foto por default de "no producto"
//        campoRuta.setVisible(false);
    //    campoRuta.setText(cargarNoImage()+"\\src\\Imagenes\\noimage.png");
    }
    
    public String cargarNoImage() {
        File f = new File("");
        return f.getAbsolutePath();
    }
    
    //Este método es para poner los datos en el lugar correspondiente del formulario
    public void colocarDatos(String info){
        
        String datos [] = info.split(",,");
        
        //Buscamos la posición del folio
        int indiceFolio = 0;
        for(int i = 0; i<=nomeclaturas.length;i++){
            if(nomeclaturas[i].equals(datos[0])){
                indiceFolio = i;
                break;
            }//if
        }//for
        
        //Acomodamos el combo de folios
        comboFolio.setSelectedIndex(indiceFolio);
        comboFolio.setEnabled(false);
        //Separamos la información para asignarla a cada textfield
        txtFolio.setText(datos[0]);
        txtNum.setText(datos[1]);
        txtExtension.setText(datos[2]);
        txtProducto.setText(datos[3]);
        if(datos[4].equals("null")){
            datos[4] = "S/N";
        }
        txtNoSerie.setText(datos[4]);
        txtMarca.setText(datos[5]);
        if(datos[6].equals("null")){
            datos[6] = "Sin modelo";
        }
        txtModelo.setText(datos[6]);
        txtColor.setText(datos[7]);
        if(datos[8].equals("null")){
            datos[8] = "Sin descripción";
        }
        txtDescripcion.setText(datos[8]);
        if(datos[9].equals("null")){
            datos[9] = "Sin Factura";
        }
        txtFactura.setText(datos[9]);
        txtImporte.setText(datos[10]);
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        try{
        txtFecha.setDate(formatoDelTexto.parse(datos[11]));
        }catch(ParseException e){
            System.out.println("Conversión de la fecha fallido.");
        }
        //Colocamos el combo de ubicación
        if(!(datos[12].equals("null"))){
        
            //Buscamos la posición del folio
            boolean coincidencia = false;
            for(int i = 0; i<comboUbicacion.getItemCount();i++){
                if(comboUbicacion.getItemAt(i).toString().equals(datos[12])){
                    coincidencia = true;
                    comboUbicacion.setSelectedItem(datos[12]);
                    break;
                }//if
            }//for
            if(!coincidencia){
                comboUbicacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
                comboUbicacion.addItem(datos[12]);
                comboUbicacion.setEnabled(false);
            }
        }//if datos[12]
    }//colocarDatos

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
        txtProducto = new javax.swing.JTextField();
        txtFolio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNoSerie = new javax.swing.JTextField();
        lblAviso = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtColor = new javax.swing.JTextField();
        comboFolio = new javax.swing.JComboBox<>();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        comboUbicacion = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        btnImagen = new javax.swing.JButton();
        imagenProducto = new javax.swing.JLabel();
        izquierdaBtn = new javax.swing.JButton();
        derechaBtn = new javax.swing.JButton();
        txtNum = new javax.swing.JTextField();
        txtExtension = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtFactura = new javax.swing.JTextField();
        txtImporte = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtFecha = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        contadorImg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pn_addInventario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pn_addInventario.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Clave:");
        pn_addInventario.add(jLabel1);
        jLabel1.setBounds(60, 60, 38, 17);

        txtProducto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtProducto.setToolTipText("Ejemplo CMP00000001");
        txtProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProductoFocusLost(evt);
            }
        });
        txtProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductoActionPerformed(evt);
            }
        });
        pn_addInventario.add(txtProducto);
        txtProducto.setBounds(120, 100, 215, 30);

        txtFolio.setEditable(false);
        txtFolio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtFolio.setToolTipText("Ejemplo CMP00000001");
        txtFolio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFolioFocusLost(evt);
            }
        });
        txtFolio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFolioActionPerformed(evt);
            }
        });
        txtFolio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFolioKeyTyped(evt);
            }
        });
        pn_addInventario.add(txtFolio);
        txtFolio.setBounds(120, 60, 50, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Producto:");
        pn_addInventario.add(jLabel2);
        jLabel2.setBounds(40, 110, 62, 17);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Descripción:");
        pn_addInventario.add(jLabel3);
        jLabel3.setBounds(30, 310, 75, 17);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Ubicación:");
        pn_addInventario.add(jLabel4);
        jLabel4.setBounds(40, 350, 70, 17);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Marca:");
        pn_addInventario.add(jLabel5);
        jLabel5.setBounds(50, 190, 41, 17);

        txtMarca.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMarcaActionPerformed(evt);
            }
        });
        pn_addInventario.add(txtMarca);
        txtMarca.setBounds(120, 180, 215, 30);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("No serie:");
        pn_addInventario.add(jLabel8);
        jLabel8.setBounds(40, 150, 53, 17);

        txtNoSerie.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNoSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoSerieActionPerformed(evt);
            }
        });
        pn_addInventario.add(txtNoSerie);
        txtNoSerie.setBounds(120, 140, 215, 30);

        lblAviso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pn_addInventario.add(lblAviso);
        lblAviso.setBounds(260, 440, 40, 10);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Modelo:");
        pn_addInventario.add(jLabel10);
        jLabel10.setBounds(50, 230, 48, 17);

        txtModelo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtModeloActionPerformed(evt);
            }
        });
        pn_addInventario.add(txtModelo);
        txtModelo.setBounds(120, 220, 215, 30);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Color:");
        pn_addInventario.add(jLabel11);
        jLabel11.setBounds(60, 270, 37, 17);

        txtColor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtColorActionPerformed(evt);
            }
        });
        pn_addInventario.add(txtColor);
        txtColor.setBounds(120, 260, 215, 30);

        comboFolio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboFolio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFolioActionPerformed(evt);
            }
        });
        pn_addInventario.add(comboFolio);
        comboFolio.setBounds(120, 20, 210, 30);

        btnAceptar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/aceptar.png"))); // NOI18N
        btnAceptar.setText(" Aceptar");
        btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        pn_addInventario.add(btnAceptar);
        btnAceptar.setBounds(260, 460, 150, 33);

        btnCancelar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText(" Cancelar");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        pn_addInventario.add(btnCancelar);
        btnCancelar.setBounds(420, 460, 150, 33);

        comboUbicacion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboUbicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboUbicacionActionPerformed(evt);
            }
        });
        pn_addInventario.add(comboUbicacion);
        comboUbicacion.setBounds(120, 340, 160, 30);

        jPanel1.setLayout(null);

        btnImagen.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnImagen.setText("+");
        btnImagen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImagenActionPerformed(evt);
            }
        });
        jPanel1.add(btnImagen);
        btnImagen.setBounds(360, 210, 40, 20);
        jPanel1.add(imagenProducto);
        imagenProducto.setBounds(0, 0, 410, 240);

        izquierdaBtn.setText("<");
        izquierdaBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        izquierdaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                izquierdaBtnActionPerformed(evt);
            }
        });
        jPanel1.add(izquierdaBtn);
        izquierdaBtn.setBounds(150, 210, 40, 23);

        derechaBtn.setText(">");
        derechaBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        derechaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                derechaBtnActionPerformed(evt);
            }
        });
        jPanel1.add(derechaBtn);
        derechaBtn.setBounds(200, 210, 41, 23);

        pn_addInventario.add(jPanel1);
        jPanel1.setBounds(380, 20, 410, 240);

        txtNum.setEditable(false);
        txtNum.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNum.setToolTipText("Ejemplo CMP00000001");
        txtNum.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumFocusLost(evt);
            }
        });
        txtNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumActionPerformed(evt);
            }
        });
        txtNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumKeyTyped(evt);
            }
        });
        pn_addInventario.add(txtNum);
        txtNum.setBounds(180, 60, 70, 30);

        txtExtension.setEditable(false);
        txtExtension.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtExtension.setToolTipText("Ejemplo CMP00000001");
        txtExtension.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtExtensionFocusLost(evt);
            }
        });
        txtExtension.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExtensionActionPerformed(evt);
            }
        });
        txtExtension.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExtensionKeyTyped(evt);
            }
        });
        pn_addInventario.add(txtExtension);
        txtExtension.setBounds(260, 60, 50, 30);

        txtDescripcion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionActionPerformed(evt);
            }
        });
        pn_addInventario.add(txtDescripcion);
        txtDescripcion.setBounds(120, 300, 670, 30);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Factura:");
        pn_addInventario.add(jLabel9);
        jLabel9.setBounds(300, 350, 51, 17);

        txtFactura.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtFactura.setToolTipText("Ejemplo CMP00000001");
        txtFactura.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFacturaFocusLost(evt);
            }
        });
        txtFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFacturaActionPerformed(evt);
            }
        });
        pn_addInventario.add(txtFactura);
        txtFactura.setBounds(360, 340, 120, 30);

        txtImporte.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtImporte.setToolTipText("Ejemplo CMP00000001");
        txtImporte.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtImporteFocusLost(evt);
            }
        });
        txtImporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImporteActionPerformed(evt);
            }
        });
        txtImporte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtImporteKeyTyped(evt);
            }
        });
        pn_addInventario.add(txtImporte);
        txtImporte.setBounds(570, 340, 120, 30);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Importe:");
        pn_addInventario.add(jLabel12);
        jLabel12.setBounds(510, 350, 54, 17);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Fecha de compra:");
        pn_addInventario.add(jLabel13);
        jLabel13.setBounds(260, 400, 120, 17);
        pn_addInventario.add(txtFecha);
        txtFecha.setBounds(380, 390, 200, 30);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/formularios.png"))); // NOI18N
        pn_addInventario.add(jLabel6);
        jLabel6.setBounds(0, 0, 860, 510);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Clave:");
        pn_addInventario.add(jLabel7);
        jLabel7.setBounds(60, 70, 38, 17);

        contadorImg.setText("0");
        pn_addInventario.add(contadorImg);
        contadorImg.setBounds(520, 220, 200, 14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_addInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 845, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_addInventario, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarcaActionPerformed

    private void txtNoSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoSerieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoSerieActionPerformed

    public void getInfo(){
        producto = txtProducto.getText();
        ubicacion = comboUbicacion.getSelectedItem().toString();
        marca = txtMarca.getText();
        if(txtNoSerie.getText().isEmpty()){
            noserie = "S/N";
        }else{
            noserie = txtNoSerie.getText();
        }
        if(txtModelo.getText().isEmpty()){
            modelo = "Sin modelo";
        }else{
            modelo = txtModelo.getText();
        }
        color = txtColor.getText();
        if(txtDescripcion.getText().isEmpty()){
            descripcion = "Sin descripción";
        }else{
            descripcion = txtDescripcion.getText();
        }
   //     imagen = campoRuta.getText();
        importe = Float.parseFloat(txtImporte.getText());
        if(txtFactura.getText().isEmpty()){
            factura = "Sin capturar";
        }else{
            factura = txtFactura.getText();
        }
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        fecha_compra = formato.format(txtFecha.getDate());
    }//getInfo
    
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        getInfo();
        //Vemos si puede actualizar el producto
        if(manager_permisos.accesoModulo("actualizar","Inventario",Principal.Username)){
            //Actualizamos el producto
            if (manager_inventario.actualizarProducto(id,producto, descripcion,ubicacion, marca,noserie, modelo, color, fecha_compra, factura, importe,imagen)) {
                String nombreParametro = txtFolio.getText() + "-" + txtNum.getText()+ txtExtension.getText();
                managerPOST.prepararImagenesInventario(rutas, nombreParametro, contadorRutas); 
                JOptionPane.showMessageDialog(null, "Se actualizo correctamente el producto \""+id+"\"");
                
                //Actualizamos al tabla de inventario de acuerdo a lo que ya se tenia seleccionado anteriormente
                int num = Principal.comboFolio.getSelectedIndex();
                String estatus = Principal.comboEstatus.getSelectedItem().toString();
                String nomeclatura = "";
                //Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
                if(num > 0){nomeclatura = nomeclaturas[num-1];}
        
                if(manager_permisos.accesoModulo("consulta","Inventario",Principal.Username)){
                    Principal.tablaInventario.setModel(manager_inventario.getInventario(nomeclatura,estatus));
                }else{
                    Principal.tablaInventario.setModel(new DefaultTableModel());
                    JOptionPane.showMessageDialog(null, "Le han revoado los permisos para consultar el inventario.");
                }
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(null, "Verificar con el distribuidor.");
            }

        } else {
            JOptionPane.showMessageDialog(null, "No cuenta con permisos para actualizar la información del producto.");
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void txtFolioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFolioFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFolioFocusLost

    private void txtModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtModeloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtModeloActionPerformed

    private void txtColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtColorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtColorActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
         // TODO add your handling code here:
         this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
         // TODO add your handling code here:
       
        
    }//GEN-LAST:event_formWindowOpened

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
            contadorImg.setText("" + contadorRutas);
            contadorImg.setVisible(true);

            try {
                img = ImageIO.read(new File(absolute_path));
                Image dimg = img.getScaledInstance(imagenProducto.getWidth(), imagenProducto.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon image = new ImageIcon(dimg);
                imagenProducto.setText("");
                imagenProducto.setIcon(image);
                //this.jButton1.setEnabled(true);
            } catch (IOException e) {
                System.err.println(e.toString());
            }

        }

    }//GEN-LAST:event_btnImagenActionPerformed

    private void txtFolioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFolioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFolioActionPerformed

    private void txtProductoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductoFocusLost

    private void txtProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductoActionPerformed

    private void comboFolioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFolioActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_comboFolioActionPerformed

    private void txtFolioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFolioKeyTyped
        // TODO add your handling code here:        
    }//GEN-LAST:event_txtFolioKeyTyped

    private void txtNumFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumFocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtNumFocusLost

    private void txtNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumActionPerformed

    private void txtNumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtNumKeyTyped

    private void txtExtensionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtExtensionFocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtExtensionFocusLost

    private void txtExtensionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExtensionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExtensionActionPerformed

    private void txtExtensionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExtensionKeyTyped
        
    }//GEN-LAST:event_txtExtensionKeyTyped

    private void comboUbicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboUbicacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboUbicacionActionPerformed

    private void txtDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionActionPerformed

    private void txtFacturaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFacturaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFacturaFocusLost

    private void txtFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFacturaActionPerformed

    private void txtImporteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtImporteFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImporteFocusLost

    private void txtImporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImporteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImporteActionPerformed

    private void txtImporteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImporteKeyTyped
        // TODO add your handling code here:
        char caracter = evt.getKeyChar();
        if(caracter != evt.getKeyCode()){    
        
        }
        if(((caracter < '0') || (caracter > '9'))  && (caracter != '.')){
            
            evt.consume();
            
        }else{
            
            if(caracter == '.' && txtImporte.getText().contains(".")){
               evt.consume();
            }
        }
    }//GEN-LAST:event_txtImporteKeyTyped

    private void izquierdaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_izquierdaBtnActionPerformed
        // TODO add your handling code here:

        // TODO add your handling code here:
//        nFoto--;
//        banderaFoto = "i";
//        derechaBtn.setEnabled(true);
//
//        cargarImagen(auxiliarFoto,nFoto);
    }//GEN-LAST:event_izquierdaBtnActionPerformed

    private void derechaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_derechaBtnActionPerformed
        // TODO add your handling code here:
//        nFoto++;
//        banderaFoto = "d";
//        izquierdaBtn.setEnabled(true);
//
//        cargarImagen(auxiliarFoto,nFoto);
    }//GEN-LAST:event_derechaBtnActionPerformed

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
                addInventarioGranel dialog = new addInventarioGranel(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnImagen;
    private javax.swing.JComboBox<String> comboFolio;
    private javax.swing.JComboBox comboUbicacion;
    private javax.swing.JLabel contadorImg;
    private javax.swing.JButton derechaBtn;
    private javax.swing.JLabel imagenProducto;
    private javax.swing.JButton izquierdaBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JPanel pn_addInventario;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtExtension;
    private javax.swing.JTextField txtFactura;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtFolio;
    private javax.swing.JTextField txtImporte;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNoSerie;
    private javax.swing.JTextField txtNum;
    private javax.swing.JTextField txtProducto;
    // End of variables declaration//GEN-END:variables
}
