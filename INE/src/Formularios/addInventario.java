/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Clases.ManagerInventario;
import Clases.ManagerPermisos;
import Clases.enviarFotoPOST;

import Interfaces.Principal;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
//import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author kevin
 */
public class addInventario extends javax.swing.JDialog {
    ManagerInventario manager_inventario;
    ManagerPermisos manager_permisos;
    enviarFotoPOST managerPOST;
    
    String folio,extension,producto,descripcion,ubicacion,marca,observaciones,noserie,color,modelo,fecha_compra,factura;
    int numero,cantidad;
    float importe;
    String [] nomeclaturas,folios;
    
    
    private String path, absolute_path, name;
    private int returnVal;
    File[] rutas;
    int contadorRutas;
    
    public addInventario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        //Asginamos memoria al objeto
        manager_inventario = new ManagerInventario();
        manager_permisos = new ManagerPermisos();
        managerPOST = new enviarFotoPOST();
        
        JTextFieldDateEditor date_Salida_Editor=(JTextFieldDateEditor) txtFecha.getDateEditor();
        date_Salida_Editor.setEditable(false);
        txtFecha.getJCalendar().setMaxSelectableDate(new Date()); // sets today as minimum selectable date        
        
        //Asignar fecha por default
        Date date = new Date();        
        txtFecha.setDate(date);
        
        
        this.setLocationRelativeTo(null);
        contadorImg.setVisible(false);
        this.setTitle("Añadir nuevo producto");
    }
    
    public String cargarNoImage() {
        File f = new File("");
        return f.getAbsolutePath();
    }

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
        lblCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNoSerie = new javax.swing.JTextField();
        lblAviso = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtColor = new javax.swing.JTextField();
        comboFolio = new javax.swing.JComboBox<>();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        comboUbicacion = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        btnImagen = new javax.swing.JButton();
        imagenProducto = new javax.swing.JLabel();
        txtNum = new javax.swing.JTextField();
        txtExtension = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        contadorImg = new javax.swing.JLabel();
        txtFactura = new javax.swing.JTextField();
        txtImporte = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtFecha = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pn_addInventario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pn_addInventario.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Clave: *");
        pn_addInventario.add(jLabel1);
        jLabel1.setBounds(48, 60, 50, 17);

        txtProducto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProductoKeyTyped(evt);
            }
        });
        pn_addInventario.add(txtProducto);
        txtProducto.setBounds(120, 100, 210, 30);
        txtProducto.getAccessibleContext().setAccessibleDescription("");

        txtFolio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtFolio.setEnabled(false);
        pn_addInventario.add(txtFolio);
        txtFolio.setBounds(120, 60, 50, 30);
        txtFolio.getAccessibleContext().setAccessibleDescription("");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Producto: *");
        pn_addInventario.add(jLabel2);
        jLabel2.setBounds(22, 110, 80, 17);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Descripción:");
        pn_addInventario.add(jLabel3);
        jLabel3.setBounds(30, 310, 75, 17);

        lblCantidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCantidad.setText("Cantidad: *");
        pn_addInventario.add(lblCantidad);
        lblCantidad.setBounds(330, 400, 80, 17);

        txtCantidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCantidad.setText("1");
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        pn_addInventario.add(txtCantidad);
        txtCantidad.setBounds(420, 390, 150, 30);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Marca:");
        pn_addInventario.add(jLabel5);
        jLabel5.setBounds(50, 190, 41, 17);

        txtMarca.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMarcaKeyTyped(evt);
            }
        });
        pn_addInventario.add(txtMarca);
        txtMarca.setBounds(120, 180, 210, 30);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("No serie:");
        pn_addInventario.add(jLabel8);
        jLabel8.setBounds(40, 150, 53, 17);

        txtNoSerie.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNoSerie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoSerieKeyTyped(evt);
            }
        });
        pn_addInventario.add(txtNoSerie);
        txtNoSerie.setBounds(120, 140, 210, 30);

        lblAviso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pn_addInventario.add(lblAviso);
        lblAviso.setBounds(260, 440, 570, 10);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Modelo:");
        pn_addInventario.add(jLabel10);
        jLabel10.setBounds(50, 230, 48, 17);

        txtModelo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pn_addInventario.add(txtModelo);
        txtModelo.setBounds(120, 220, 210, 30);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Color:");
        pn_addInventario.add(jLabel11);
        jLabel11.setBounds(60, 270, 37, 17);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Ubicación:");
        pn_addInventario.add(jLabel14);
        jLabel14.setBounds(40, 350, 70, 17);

        txtColor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pn_addInventario.add(txtColor);
        txtColor.setBounds(120, 260, 210, 30);

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
        btnAceptar.setEnabled(false);
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
        pn_addInventario.add(comboUbicacion);
        comboUbicacion.setBounds(120, 340, 160, 30);

        jPanel1.setLayout(null);

        btnImagen.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        btnImagen.setText("...");
        btnImagen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImagenActionPerformed(evt);
            }
        });
        jPanel1.add(btnImagen);
        btnImagen.setBounds(360, 210, 30, 20);
        jPanel1.add(imagenProducto);
        imagenProducto.setBounds(0, 0, 410, 240);

        pn_addInventario.add(jPanel1);
        jPanel1.setBounds(380, 20, 410, 240);

        txtNum.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNum.setEnabled(false);
        txtNum.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumFocusLost(evt);
            }
        });
        txtNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumKeyTyped(evt);
            }
        });
        pn_addInventario.add(txtNum);
        txtNum.setBounds(180, 60, 90, 30);

        txtExtension.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtExtension.setToolTipText("");
        txtExtension.setEnabled(false);
        txtExtension.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtExtensionFocusLost(evt);
            }
        });
        txtExtension.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExtensionKeyTyped(evt);
            }
        });
        pn_addInventario.add(txtExtension);
        txtExtension.setBounds(280, 60, 50, 30);

        txtDescripcion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pn_addInventario.add(txtDescripcion);
        txtDescripcion.setBounds(120, 300, 670, 30);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Factura:");
        pn_addInventario.add(jLabel9);
        jLabel9.setBounds(350, 350, 51, 17);

        contadorImg.setText("0");
        pn_addInventario.add(contadorImg);
        contadorImg.setBounds(640, 310, 50, 14);

        txtFactura.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pn_addInventario.add(txtFactura);
        txtFactura.setBounds(420, 340, 150, 30);
        txtFactura.getAccessibleContext().setAccessibleName("");
        txtFactura.getAccessibleContext().setAccessibleDescription("");

        txtImporte.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtImporte.setText("0");
        txtImporte.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtImporteFocusLost(evt);
            }
        });
        txtImporte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtImporteKeyTyped(evt);
            }
        });
        pn_addInventario.add(txtImporte);
        txtImporte.setBounds(670, 340, 120, 30);
        txtImporte.getAccessibleContext().setAccessibleDescription("");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Importe: *");
        pn_addInventario.add(jLabel12);
        jLabel12.setBounds(600, 350, 70, 17);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Fecha compra: *");
        pn_addInventario.add(jLabel13);
        jLabel13.setBounds(10, 400, 120, 17);
        pn_addInventario.add(txtFecha);
        txtFecha.setBounds(120, 390, 160, 30);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/formularios.png"))); // NOI18N
        pn_addInventario.add(jLabel6);
        jLabel6.setBounds(0, 0, 860, 510);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Clave:");
        pn_addInventario.add(jLabel7);
        jLabel7.setBounds(60, 70, 38, 17);

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

    public boolean getInfo(){
		// CAMPOS OBLIGATORIOS
		if(txtCantidad.isVisible()){ 
			if (!txtCantidad.getText().isEmpty())
				cantidad = Integer.parseInt(txtCantidad.getText());
                }else{
                    cantidad = 1;
                }
		if (comboFolio.getSelectedIndex() > 0)
			folio = txtFolio.getText();
		else return false;
		
		if (!txtNum.getText().isEmpty())
			numero = Integer.parseInt(txtNum.getText());
		else return false;
		
		if (!txtProducto.getText().isEmpty())
			producto = txtProducto.getText();
		else return false;
		
		if (!txtImporte.getText().isEmpty())
			importe = Float.parseFloat(txtImporte.getText());
		else return false;
		
		// OPCIONES LIMITADAS
        ubicacion = comboUbicacion.getSelectedItem().toString();
		
		// CAMPOS CON VALORES POR DEFAULT
		if (!txtMarca.getText().isEmpty())
			marca = txtMarca.getText();
		else marca = "No registrada";
		
		// EXTENSION PUEDE IR VACÍO O NO
        extension = txtExtension.getText();
		
        if(!txtNoSerie.getText().isEmpty())
			noserie = txtNoSerie.getText();
        else noserie = "S/N";
        
        if(!txtModelo.getText().isEmpty())
			modelo = txtModelo.getText();
        else modelo = "Sin modelo";
        
		if (!txtColor.getText().isEmpty())
			color = txtColor.getText();
		else color = "No registrado";
		
        if(!txtDescripcion.getText().isEmpty())
			descripcion = txtDescripcion.getText();
		else descripcion = "Sin descripción";
        		
        if(!txtFactura.getText().isEmpty())
			factura = txtFactura.getText();
        else factura = "Sin capturar";
        
		fecha_compra = new SimpleDateFormat("yyyy-MM-dd").format(txtFecha.getDate());
		
		return true;
    }//getInfo
    
    public void clearCampos(){
        txtFolio.setText("");
        txtFolio.setBackground(Color.white);
        txtProducto.setText("");
        txtMarca.setText("");
        txtNoSerie.setText("");
        txtModelo.setText("");
        txtDescripcion.setText("");
        txtColor.setText("");
        txtNum.setText("");
        txtExtension.setText("");
        txtFecha.cleanup();
        txtFactura.setText("");
        txtImporte.setText("");
        comboFolio.setSelectedIndex(0);
    }
    
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
	
		if(getInfo()){
			if(manager_permisos.accesoModulo("alta","Inventario",Principal.Username)){
            
				if (manager_inventario.guardarImagen(folio,numero,extension, producto, descripcion,ubicacion, marca, "Sin observaciones",noserie, modelo, color,contadorImg.getText(), fecha_compra, factura, importe,cantidad)) {
					String nombreParametro = txtFolio.getText() + "-" + txtNum.getText()+ txtExtension.getText();
					managerPOST.prepararImagenesInventario(rutas, nombreParametro, contadorRutas);   
					JOptionPane.showMessageDialog(null, "Se insertó correctamente al inventario");

					int num = comboFolio.getSelectedIndex();
					String nomeclatura = "";
					//Si es diferente de 0 entonces esta seleccionado una nomeclatura de algun folio
					if(num > 0){nomeclatura = nomeclaturas[num-1];}

					if(manager_permisos.accesoModulo("consulta","Inventario",Principal.Username)){            
						String estatus2 = Principal.comboEstatus.getSelectedItem().toString();
						int filtro = Principal.comboFiltro.getSelectedIndex();
						String busqueda = Principal.txtBusqueda.getText();
						Principal.tablaInventario.setModel(manager_inventario.getBusquedaInventario(filtro, busqueda, nomeclatura,estatus2));
						Principal.lblProductosTotales.setText("Productos Totales: ".concat(String.valueOf(manager_inventario.cantidadInventario(filtro, busqueda, nomeclatura,estatus2))));
						Principal.comboFolio.setSelectedIndex(num);
					}

				} else JOptionPane.showMessageDialog(null, "Verificar con el distribuidor.");
			} else JOptionPane.showMessageDialog(null, "No cuenta con permisos para dar de alta nuevos productos al inventario.");
			
			clearCampos();
		} else JOptionPane.showMessageDialog(null, "Verificar campos obligatorios.");
		

    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
         // TODO add your handling code here:
         this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
         // TODO add your handling code here:
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
        
        
//        JFileChooser fc = new JFileChooser();
//        int respuesta = fc.showOpenDialog(this);
//        //Comprobar si se ha pulsado Aceptar
//        if (respuesta == JFileChooser.APPROVE_OPTION) {
//            //Mostrar el nombre del archvivo en un campo de texto
//            campoRuta.setText(fc.getSelectedFile().toString());
//
//        }//if
//        String path = campoRuta.getText();
//        URL url = this.getClass().getResource(path);
//        System.err.println("" + path);
//        ImageIcon imagen = new ImageIcon(path);
//        ImageIcon icono = new ImageIcon(imagen.getImage().getScaledInstance(imagenProducto.getWidth(), imagenProducto.getHeight(), Image.SCALE_DEFAULT));
//        imagenProducto.setIcon(icono);

    }//GEN-LAST:event_btnImagenActionPerformed

    private void comboFolioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFolioActionPerformed
        // TODO add your handling code here:
        int combo_folio = comboFolio.getSelectedIndex();
        
        if(comboFolio.getSelectedItem().toString().equals("Mobiliario y Equipo de Oficina")){
            lblCantidad.setVisible(true);
            txtCantidad.setVisible(true);
			txtNum.requestFocusInWindow();
        }else{
            lblCantidad.setVisible(false);
            txtCantidad.setVisible(false);
			txtNum.requestFocusInWindow();
        }
        txtCantidad.setText("1");
        
        if(combo_folio > 0){
            txtFolio.setText(nomeclaturas[combo_folio-1]);
            txtNum.setEnabled(true);
            txtExtension.setEnabled(true);
            txtNum.setText(manager_inventario.getSugerenciaNum(nomeclaturas[combo_folio - 1]));
            
            if(!(txtNum.getText().isEmpty())){
            
                //Comparamos si existe o no
                if(manager_inventario.existeInventario(txtFolio.getText()+"-"+txtNum.getText()+txtExtension.getText())){
                    //Existe, entonces pintamos en rojo los TextField
                    txtFolio.setBackground(java.awt.Color.RED);
                    txtNum.setBackground(java.awt.Color.RED);
                    txtExtension.setBackground(java.awt.Color.RED);
                    btnAceptar.setEnabled(false);
                    lblAviso.setText("La clave ya se encuentra registrada.");

                }else{
                    //Si no existe, entonces pintamos de verde los TextField
                    txtFolio.setBackground(java.awt.Color.GREEN);
                    txtNum.setBackground(java.awt.Color.GREEN);
                    txtExtension.setBackground(java.awt.Color.GREEN);
                    btnAceptar.setEnabled(true);
                    lblAviso.setText("");
                }

            }//Buscar si existe o no
            else{
                txtFolio.setBackground(java.awt.Color.WHITE);
                txtNum.setBackground(java.awt.Color.WHITE);
                txtExtension.setBackground(java.awt.Color.WHITE);
                btnAceptar.setEnabled(false);
                lblAviso.setText("");
            }
            
        }else{
            txtFolio.setText("");
            txtNum.setText("");
            txtNum.setEnabled(false);
            txtExtension.setText("");
            txtExtension.setEnabled(false);
            btnAceptar.setEnabled(false);
        }
        
    }//GEN-LAST:event_comboFolioActionPerformed

    public void existeClave(){
    
        //Comparamos si existe o no
        if(manager_inventario.existeInventario(txtFolio.getText()+"-"+txtNum.getText()+txtExtension.getText())){
            //Existe, entonces pintamos en rojo los TextField
            txtNum.setBackground(java.awt.Color.RED);
            txtExtension.setBackground(java.awt.Color.RED);
            btnAceptar.setEnabled(false);
            lblAviso.setText("La clave ya se encuentra registrada.");
        }else{
            //Si no existe, entonces pintamos de verde los TextField
            txtNum.setBackground(java.awt.Color.GREEN);
            txtExtension.setBackground(java.awt.Color.GREEN);
            btnAceptar.setEnabled(true);
            lblAviso.setText("");
        }
        
    }//existeClave
    
    private void txtNumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumKeyTyped
        // TODO add your handling code here:
        char caracter = evt.getKeyChar();
        if(caracter != evt.getKeyChar()){
        
        }
        if(caracter < '0' || caracter > '9'){
            evt.consume();
        }
        
    }//GEN-LAST:event_txtNumKeyTyped

    private void txtExtensionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtExtensionFocusLost
        // TODO add your handling code here:
        if(!(txtNum.getText().isEmpty() && txtExtension.getText().isEmpty())){            
            existeClave();
            txtExtension.setText(txtExtension.getText().toUpperCase());
        }//Buscar si existe o no
        else{
            txtFolio.setBackground(java.awt.Color.WHITE);
            txtNum.setBackground(java.awt.Color.WHITE);
            txtExtension.setBackground(java.awt.Color.WHITE);
            btnAceptar.setEnabled(false);
            lblAviso.setText("");
        }
    }//GEN-LAST:event_txtExtensionFocusLost

    private void txtExtensionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExtensionKeyTyped
        // TODO add your handling code here:

        char caracter = evt.getKeyChar();
        if (caracter != evt.getKeyChar()) {

        }
        if (txtExtension.getText().length() == 1) {
            evt.consume();
        } else {
            if ((caracter == 'A' || caracter == 'a') || (caracter == 'B' || caracter == 'b')) {
            } else {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtExtensionKeyTyped

    private void txtImporteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtImporteFocusLost
        // TODO add your handling code here:
        if(txtImporte.getText().isEmpty()){
            txtImporte.setText("0");
        }
    }//GEN-LAST:event_txtImporteFocusLost

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

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        // TODO add your handling code here:
        char caracter = evt.getKeyChar();
        if(caracter != evt.getKeyChar()){
        
        }
        if(caracter < '0' || caracter > '9'){
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductoKeyTyped
        if(txtProducto.getText().length() >= 50) {  
			evt.consume();
		}
    }//GEN-LAST:event_txtProductoKeyTyped

    private void txtNumFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumFocusLost
         // TODO add your handling code here:
         if(!(txtNum.getText().isEmpty() && txtExtension.getText().isEmpty())){            
            existeClave();
            txtExtension.setText(txtExtension.getText().toUpperCase());
        }//Buscar si existe o no
        else{
            txtFolio.setBackground(java.awt.Color.WHITE);
            txtNum.setBackground(java.awt.Color.WHITE);
            txtExtension.setBackground(java.awt.Color.WHITE);
            btnAceptar.setEnabled(false);
            lblAviso.setText("");
        }
    }//GEN-LAST:event_txtNumFocusLost

    private void txtNoSerieKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoSerieKeyTyped
         // TODO add your handling code here:
         char caracter2 = evt.getKeyChar();
        if (txtNoSerie.getText().length() == 15) {
            evt.consume();
        } else if (caracter2 != evt.getKeyCode()) {
        }
    }//GEN-LAST:event_txtNoSerieKeyTyped

    private void txtMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarcaKeyTyped
         // TODO add your handling code here:
         char caracter2 = evt.getKeyChar();
        if (txtMarca.getText().length() == 15) {
            evt.consume();
        } else if (caracter2 != evt.getKeyCode()) {
        }
    }//GEN-LAST:event_txtMarcaKeyTyped

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
			addInventario dialog = new addInventario(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnImagen;
    private javax.swing.JComboBox<String> comboFolio;
    private javax.swing.JComboBox comboUbicacion;
    private javax.swing.JLabel contadorImg;
    private javax.swing.JLabel imagenProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JPanel pn_addInventario;
    private javax.swing.JTextField txtCantidad;
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
