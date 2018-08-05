package Clases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author kevin
 */
public class ManagerInventario {
    ManagerInventarioGranel manager_inventario_granel;
    private Connection conexion;
    private final Conexion db;
    
    public ManagerInventario(){
        db = new Conexion();
    }//Constructor
    

    /**
    * <h1>obtenerDatosProd</h1>
    * 
    * <p>Obtiene la información de un producto dada la clave del producto
	* en el formato ya establecido</p>
    * 
    * @param clave La clave del producto
    * @return <code>String</code> información del producto seleccionado, la cual es:
	*	<ul>
	*		<li>Folio</li>
	*		<li>Numero</li>
	*		<li>Extension</li>
	*		<li>Nombre del Producto</li>
	*		<li>Número de Serie</li>
	*		<li>Marca</li>
	*		<li>Modelo</li>
	*		<li>Color</li>
	*		<li>Descripción</li>
	*		<li>Factura</li>
	*		<li>Importe</li>
	*		<li>Fecha de Compra</li>
	*		<li>Ubicación</li>
	*	</ul> 
    */
	public String obtenerDatosProd(String clave) {
		StringBuilder sb = new StringBuilder();
        
        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_infoProducto`(?)}")){		
			
			cs.setString(1, clave);
            ResultSet rs = cs.executeQuery();

			while (rs.next()){
				for (int i = 1; i <= 13; i++) {
					sb.append(rs.getString(i));
					sb.append(",,");
				}
			}
			
        } catch (SQLException ex) {
            System.err.printf("Error al obtener los datos del producto \""+clave+"\" en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
		return sb.toString();

    }//obtenerDatosProd
    
    //realizar una inserción al inventario con imagen
    public boolean guardarImagen(String folio,int numero,String extension, String producto, String descripcion, String ubicacion, String marca, String observaciones,String no_serie,String modelo,String color,String fecha_compra,String factura, float importe,String ruta, int cantidad) {
        conexion = db.getConexion();
        String insert;
        File file;
        try {
            for(int i = 0; i<cantidad;i++){
        
                insert = "insert into inventario (Folio,Numero,Extension,nombre_prod,descripcion,ubicacion,estatus,marca,observaciones,no_serie,modelo,color,imagen,Fecha_Compra,Factura,Importe)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                FileInputStream fi = null;
                PreparedStatement ps = null;


                    file = new File(ruta);
                    fi = new FileInputStream(file);

                    ps = conexion.prepareStatement(insert);

                    ps.setString(1, folio);
                    ps.setInt(2, numero + i);
                    ps.setString(3, extension);
                    ps.setString(4, producto);
                    ps.setString(5, descripcion);
                    ps.setString(6, ubicacion);
                    ps.setString(7, "Disponible");
                    ps.setString(8, marca);
                    ps.setString(9, observaciones);
                    ps.setString(10, no_serie);
                    ps.setString(11, modelo);
                    ps.setString(12, color);
                    ps.setBinaryStream(13, fi);
                    ps.setString(14, fecha_compra);
                    ps.setString(15, factura);
                    ps.setFloat(16, importe);

                    ps.executeUpdate();
            }//for

                return true;
           
        } catch (Exception ex) {
            System.out.println("Error al guardar Imagen " + ex.getMessage());
            return false;

        }
    }//guardarImagen
    
	/**
	 * <h1>Actualizar Producto</h1>
	 * 
	 * <p>Realiza una operación DML UPDATE en un producto dada la clave del mismo</p>
	 * 
	 * @param clave del producto
	 * @param producto - se refiere a el nombre del producto
	 * @param descripcion del producto
	 * @param ubicacion del producto
	 * @param marca del producto
	 * @param no_serie del producto
	 * @param modelo del producto
	 * @param color del producto
	 * @param fecha_compra del producto
	 * @param factura del producto
	 * @param importe del producto
	 * @param ruta de la imágen del producto
	 * @return 
	 *		<ul>
	 *			<li><code>true</code> si la actualización ha sido exitosa</li>
	 *			<li><code>false</code> si hay algún error en la consulta o si no se ha actualizado ningún registro</li>
	 *		</ul>
	 *		
	 */
    public boolean actualizarProducto(	String clave, String producto, String descripcion, String ubicacion, String marca, 
										String no_serie, String modelo, String color, String fecha_compra, String factura, float importe, String ruta) {
        try {
			PreparedStatement ps = db.getConexion().prepareStatement("{CALL `ine`.`usp_update_productoInventario`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            ps.setString(1, producto);
            ps.setString(2, descripcion);
            ps.setString(3, ubicacion);
            ps.setString(4, marca);
            ps.setString(5, no_serie);
            ps.setString(6, modelo);
            ps.setString(7, color);
			
			if(ruta.equals(""))
				ps.setNull(8, 0);
			else
				ps.setBinaryStream(8, new FileInputStream(new File(ruta)));
            
			ps.setString(9, fecha_compra);
            ps.setString(10, factura);
            ps.setFloat(11, importe);
			ps.setString(12, clave);
			
            return ps.executeUpdate() != 0;

        } catch (FileNotFoundException | SQLException ex) {
            System.err.println("Error al actualizar producto: " + ex.getMessage());
			Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
			return false;
	    }

    }//actualizarProducto
    
	/**
	 *
	 * <h1>Sugerencia de Numero</h1>
	 * 
	 * <p>Obtiene una sugerencia para la inserción del número de folio,
	 * la cual consiste en el último número de folio que existe en la base de datos
	 * más uno</p>
	 * 
	 * @param folio del producto
	 * @return <code>String</code> - número del último folio más uno
	 */
	public String getSugerenciaNum(String folio) {

        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_sugFolio`(?)}")){
			
			cs.setString(1, folio);
			ResultSet rs = cs.executeQuery();
			
			while(rs.next()) 
				return rs.getString("Sugerencia_Folio");
			
        } catch (SQLException ex) {
            System.out.printf("Error al obtener el ultimo número del folio en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
		
		return null;
    }//sugerenciaNum
    
	/**
	 *
	 * <h1>Obtener Inventario</h1>
	 * 
	 * <p>Nos devuelve todos los productos de inventario 
	 * con un filtro de nomeclatura de folio y su estatus</p>
	 * 
	 * @param nomenclatura que pertenece al folio del producto
	 * @param estatus en el que se encuentra el producto
	 * @return <code>DefaultTableModel</code> con la consulta realizada dada los 
	 *	parámetros establecidos.
	 */
    public DefaultTableModel getInventario(String nomenclatura, String estatus) {
        //No dejamos editar ninguna celda
        DefaultTableModel table = new DefaultTableModel(){
				@Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };

        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_infoInventario`(?, ?)}")){
            // Se añaden los campos a la tabla
            table.addColumn("No. Inventario");
            table.addColumn("Nombre corto");
            table.addColumn("Descripción");
            table.addColumn("Ubicación");
            table.addColumn("Marca");
            table.addColumn("Observaciones");
            table.addColumn("No. Serie");
            table.addColumn("Modelo");
            table.addColumn("Color");
            table.addColumn("Fecha Compra");
            table.addColumn("Factura");
            table.addColumn("Importe");
			
			// Se añade el parámetro de estatus
			cs.setString(1, estatus);
			
			// El USP hace manejo de la consulta a ejecutar dado si el parámetro nomenclatura está vacío o no 
			if(nomenclatura.equals(""))
				cs.setNull(2, 0);
			else 
				cs.setString(2, nomenclatura);
			
			// Se obtiene la consulta
            ResultSet rs = cs.executeQuery();
			
			//Llenar tabla
			Object datos[] = new Object[12];

            while (rs.next()) {
				//Llenamos las columnas por registro
                for(int i = 0; i < 12; i++){
                    datos[i] = rs.getObject(i + 1);
                }
				//Añadimos la fila
                table.addRow(datos);
           }//while

        } catch (SQLException ex) {
            System.out.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
		return table;
    }//getInventario
    
	/**
	 * 
	 * <h1>Existe en Inventario</h1>
	 *
	 * <p>Este metodo se utiliza en la ventana de insercion de inventario normal, 
	 * cuando se va a dar de alta un ID de un producto (Folio, -, Numero, Extensión),
	 * que arroja un verdadero si existe por lo tanto no deja insertar ese nuevo producto, 
	 * y falso si no existe entonces si deja insertar dicho producto.</p>
	 * 
	 * @param idProducto
	 * @return 
	 *		<ul>
	 *			<li><code>true</code> si existe el producto</li>
	 *			<li><code>false</code> si no existe el producto</li>
	 *		</ul>
	 *	
	 */
    public boolean existeInventario(String idProducto) {
        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_existeProducto`(?)}")) {
			
            cs.setString(1, idProducto);
			ResultSet rs = cs.executeQuery();
			
			if (rs.next()) return rs.getInt("res") == 1;
			
        } catch (SQLException ex) {
            System.err.printf("Error al consultar el inventario en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
		return false;
    }//existeInventario
    
        /**
	 * 
	 * <h1>Cantidad en Inventario</h1>
	 *
	 * <p>Este metodo se utiliza para mostrar la cantidad de productos que se visualizan
         * en la tabla de invetario.</p>
	 * 
	 * @param idProducto
	 * @return 
	 *		<ul>
	 *			<li><code>n</code> la cantidad de productos que se encontraron</li>
	 *			<li><code>0</code> si no se encontraron coincidencias</li>
	 *		</ul>
	 *	
	 */
    public boolean cantidadInventario(String idProducto) {
        try {
			
            
			
        } catch (Exception ex) {
            System.err.printf("Error al consultar el inventario en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
		return false;
    }//existeInventario
    
    
	/**
	 * <h1>Nomenclaturas de Folio</h1>
	 * 
	 * <p> Este método es para llenar los combos de folio y su descripción. </p> <br>
	 * <p>Se utilizan para llenar el combo en el inventario de la ventana principal
	 * en la ventana para añadir productos y en el update del producto</p>
	 *
	 * @return <code>String</code> separado por comas con las nomenclaturas y descripción de Folios
	 */
	public String nomeclaturaFolio() {
        StringBuilder sb = new StringBuilder();
        
        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_infoFolio`()}")) {
            ResultSet rs = cs.executeQuery();
          
            while(rs.next()){
                sb.append(rs.getString(1));
                sb.append(",");
                sb.append(rs.getString(2));
                sb.append(",");
            }
            
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los folios en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
		return sb.toString();
    }//nomeclaturaFolio
    
    public void getBodegas(JComboBox combo) {
        try{
           
            String sql = "select Nom_Bodega from Bodegas;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo.addItem(rs.getObject(1).toString());
            }
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener las bodegas para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }//getBodegas
    
    //Este método funciona en la pestaña de inventario, cuando se quiere buscar por un filtro mas especifico donde el usuario ingresara que esta
    //buscando de acuerdo al campo seleccionado mas aparte las opciones de nomeclatura de folio y estatus.
    public DefaultTableModel getBusquedaInventario(int filtro, String busqueda, String folio, String estatus){

        //No dejamos editar ninguna celda
        DefaultTableModel table = new DefaultTableModel(){
				@Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };

        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_busquedaProducto`(?, ?, ?, ?)}")) {
			cs.setInt(1, filtro);
			
			if (estatus.equals("")) 
				cs.setNull(2, 0);
			else
				cs.setString(2, estatus);
			
			if (busqueda.equals("")) 
				cs.setNull(3, 0);
			else
				cs.setString(3, busqueda);

			if (folio.equals("")) 
				cs.setNull(4, 0);
			else
				cs.setString(4, folio);
				
            ResultSet rs = cs.executeQuery();
            
            int cantidadColumnas = 12;
            //Si el estado es verdadero significa que si encontro coincidencias, entonces mostraremos dichas concidencias
            if(rs.next()){
                table.addColumn("No. Inventario");
                table.addColumn("Nombre corto");
                table.addColumn("Descripción");
                table.addColumn("Ubicación");
                table.addColumn("Marca");
                table.addColumn("Observaciones");
                table.addColumn("No. Serie");
                table.addColumn("Modelo");
                table.addColumn("Color");
                table.addColumn("Fecha Compra");
                table.addColumn("Factura");
                table.addColumn("Importe");

                if(filtro == 11 || estatus.equals("Asignado")){
                    table.addColumn("Responsable");
                    cantidadColumnas++;
                }

                Object datos[] = new Object[cantidadColumnas];

                //Anteriormente se hizo la consulta, y como entro a este if significa que si se encontraron datos, por ende ya estamos posicionados
                //en el primer registro de las concidencias
                for(int i = 0; i < cantidadColumnas; i++){
                    datos[i] = rs.getObject(i + 1);
                }//Llenamos las columnas por registro
                table.addRow(datos);
                    
                //Proseguimos con los registros en caso de exisitir mas
                while (rs.next()) {
                    for(int i = 0; i < cantidadColumnas; i++){
	                    datos[i] = rs.getObject(i + 1);
                    }//Llenamos las columnas por registro
                    table.addRow(datos);//Añadimos la fila
	            }//while
                    
			}
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(ManagerInventario.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
			return table; //Retorna el resultado, si se encontro o no
		}

    }//Buscar si existe el producto
    
    //Este metodo retorna una tabla con el formato de la tabla de inventario pero anexando una columna con un checkbox para marcar los productos que
    //se quiere cambiar a pendiente para baja/comodato/donacion o devolver a disponibles
    public DefaultTableModel cambiarEstatus(String titulo,String pendiente,int filtro, String busqueda,String folio,String estatus){
        JTable checks = new JTable();
        JScrollPane scroll = new JScrollPane();
        conexion = db.getConexion();
        
        DefaultTableModel table;
        
        //Creamos la tabla con las caracterisiticas que necesitamos
        checks.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        checks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            //Declaramos el titulo de las columnas
            new String [] {
                titulo + pendiente,
				"Clave",
				"Nombre corto",
				"Descripción",
				"Ubicación",
				"Marca",
				"Observaciones",
				"No. Serie",
				"Modelo",
				"Color",
				"Fecha de Compra",
				"Factura",
				"Importe"
            }
        ){
            //El tipo que sera cada columna, la primera columna un checkbox y los demas seran objetos
            Class[] types = new Class [] {
                java.lang.Boolean.class, 
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class
            };

			@Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            //Esto es para indicar que columnas dejaremos editar o no
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, false, false, false
            };

			@Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
            
          }
        
        );
        //Agregamos un scroll a la tabla
        scroll.setViewportView(checks);
        scroll.setBounds(30, 130, 1110, 500);
        
        table = (DefaultTableModel)checks.getModel();

        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_busquedaProducto`(?, ?, ?, ?)}")) {
			cs.setInt(1, filtro);
			
			if (estatus.equals("")) 
				cs.setNull(2, 0);
			else
				cs.setString(2, estatus);
			
			if (busqueda.equals("")) 
				cs.setNull(3, 0);
			else
				cs.setString(3, busqueda);

			if (folio.equals("")) 
				cs.setNull(4, 0);
			else
				cs.setString(4, folio);
				
            ResultSet rs = cs.executeQuery();
            
            Object datos[] = new Object[13];

            //Proseguimos con los registros en caso de exisitir mas
            while (rs.next()) {

                datos[0] = Boolean.FALSE;

                for(int i = 1;i<13;i++){
                        datos[i] = rs.getString(i);
                }//Llenamos la fila

                table.addRow(datos);//Añadimos la fila
            }//while

            conexion.close();            
			
        } catch (SQLException ex) {
            System.out.printf("Error al generar la tabla para con checkbox en el Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            return table;
        }
        
    }//Retorna una tabla con un checkbox en la primera columna (cambiarEstatus)
    
    //Este metodo actualiza el estatus de 1 o más productos a pendiente para baja/comodato/donación
    public boolean actualizarEstatus(String[] IDs, Boolean[] cambio, String pendientePara) {

        try (PreparedStatement ps = db.getConexion().prepareCall("{CALL `ine`.`usp_update_statusProductoInv`(?, ?)}")) {
			
            boolean status = false;
            String sql = "";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            
            sql = "select now();";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String fecha = rs.getString(1);
            
            //Recorremos todos los datos para ver cuales fueron marcados y cuales no
            for(int i = 0; i < IDs.length; i++){
                //Si fue marcado entonces cambia el estatus
                if(cambio[i]){
                    ps.setString(1, IDs[i]);
                    ps.setString(2, pendientePara);
                    status = ps.executeUpdate() != 0;
                    //Vemos si el estatus es para Baja/Comodato/Donación definitivo
                    if(pendientePara.equals("Baja") || pendientePara.equals("Donación") || pendientePara.equals("Comodato")){
                        sql = "insert into productos_asignados (ID_Producto,Status,Salida,Fecha_Seleccion) value ('"+IDs[i]+"','"+pendientePara+"',0,'"+fecha+"');";
                        st.executeUpdate(sql);
                    }
                }//if(cambio[i])
            }//for
			
            return status;

        } catch (Exception ex) {
            System.out.println("Error al actualizar el estatus "+ pendientePara + ex.getMessage());
            return false;
        }

    }//actualizarEstatus
    
    public Blob leerImagen(String idProducto) throws IOException {
        conexion = db.getConexion();
        //String sSql = "select imagen from inventario where id_producto = '"+idProducto+"';";
        String sSql = "select imagen from inventario where id_producto = '"+idProducto+"';";
        
        PreparedStatement pst;
        Blob blob = null;
        try {
            pst = conexion.prepareStatement(sSql);
            ResultSet res = pst.executeQuery();
            if (res.next()) {

                blob = res.getBlob("imagen");

            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerVehiculos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blob;
    }//leerImagen
    
    public boolean actualizarProductoSinFoto(String clave, String producto, String almacen, String marca,String noserie, String descripcion, String observaciones,String tipo,String modelo,String color) {
        conexion = db.getConexion();
        
        String update = "update inventario set nombre_prod = ?,almacen = ?,marca = ?,no_serie = ?,descripcion = ?,observaciones = ?,tipo_uso = ?,modelo = ?,color = ? where id_producto = '"+clave+"'";
        FileInputStream fi = null;
        PreparedStatement ps = null;

        try {
           
            ps = conexion.prepareStatement(update);

            ps.setString(1, producto);
            ps.setString(2, almacen);
            ps.setString(3, marca);
            ps.setString(4, noserie);
            ps.setString(5, descripcion);
            ps.setString(6, observaciones);
            ps.setString(7, tipo);
            ps.setString(8, modelo);
            ps.setString(9, color);

            ps.executeUpdate();

            return true;

        } catch (Exception ex) {
            System.out.println("Error al actualizar imagen " + ex.getMessage());
            return false;

        }

    }//guardarImagen
    
}//class
