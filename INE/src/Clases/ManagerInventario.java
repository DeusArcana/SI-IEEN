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
        
        try{
            
			CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_infoProducto`(?)}");
			cs.setString(1, clave);
            ResultSet rs = cs.executeQuery();
					
			while (rs.next()){
				for (int i = 1; i <= 13; i++) {
					sb.append(rs.getString(i));
					sb.append(",,");
				}
			}
            
			db.getConexion().close();
			
        } catch (SQLException ex) {
            System.err.printf("Error al obtener los datos del producto \""+clave+"\" en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
		return sb.toString();

    }//obtenerDatosProd
    
    //realizar una inserción al inventario con imagen
    public boolean guardarImagen(String folio,int numero,String extension, String producto, String descripcion, String ubicacion, String marca, String observaciones,String no_serie,String modelo,String color,String fecha_compra,String factura, float importe,String ruta) {
        conexion = db.getConexion();
        String insert = "insert into inventario (Folio,Numero,Extension,nombre_prod,descripcion,ubicacion,estatus,marca,observaciones,no_serie,tipo_uso,modelo,color,imagen,Fecha_Compra,Factura,Importe)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        FileInputStream fi = null;
        PreparedStatement ps = null;
        
        try {
            File file = new File(ruta);
            fi = new FileInputStream(file);

            ps = conexion.prepareStatement(insert);

            ps.setString(1, folio);
            ps.setInt(2, numero);
            ps.setString(3, extension);
            ps.setString(4, producto);
            ps.setString(5, descripcion);
            ps.setString(6, ubicacion);
            ps.setString(7, "Disponible");
            ps.setString(8, marca);
            ps.setString(9, observaciones);
            ps.setString(10, no_serie);
            ps.setString(11, "Sin asignación");
            ps.setString(12, modelo);
            ps.setString(13, color);
            ps.setBinaryStream(14, fi);
            ps.setString(15, fecha_compra);
            ps.setString(16, factura);
            ps.setFloat(17, importe);

            ps.executeUpdate();
            
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
	 * @return <code>String</code> - número del último folio más uno
	 */
	public String getSugerenciaNum() {

        try {
			
			ResultSet rs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_sugFolio`()}").executeQuery();
			while(rs.next()) 
				return rs.getString("Sugerencia_Folio");
			db.getConexion().close();
			
        } catch (SQLException ex) {
            System.out.printf("Error al obtener el ultimo número del folio en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
		
		return null;
    }//sugerenciaNum
    
    //Nos devuelve todos los productos de inventario normal con un filtro de nomeclatura de folio y su estatus
    public DefaultTableModel getInventario(String nomeclatura,String estatus) {
        //No dejamos editar ninguna celda
        DefaultTableModel table = new DefaultTableModel(){
				@Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };

        try {
            
            table.addColumn("Clave");
            table.addColumn("Nombre_corto");
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
            
            String sql = "";
            
            if(nomeclatura.equals("")){
                //Consulta de los empleados
                sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,ubicacion,marca,observaciones,no_serie,modelo,color,fecha_compra,factura,importe "
                        + "from inventario where estatus = '"+estatus+"';";
            }
            else{
            //Consulta de los empleados
            sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,ubicacion,marca,observaciones,no_serie,modelo,color,fecha_compra,factura,importe "
                    + "from inventario where Folio = '"+nomeclatura+"' and estatus = '"+estatus+"';";
            }
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[12];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<12;i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro

                table.addRow(datos);//Añadimos la fila
           }//while
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//getInventario
    
    //Este metodo se utiliza en la ventana de insercion de inventario normal, cuando se va a dar de alta un ID de un producto (Folio,-,Numero,Extensión),
    //que arroja un verdadero si existe por lo tanto no deja insertar ese nuevo producto, y falso si no existe entonces si deja insertar dicho producto.
    public boolean existeInventario(String id_producto) {

        boolean estado = false;
        
        try {
            //Consulta para saber si existe o no dicho producto
            String sql = "select * from inventario where concat(Folio,'-',Numero,Extension) = '"+id_producto+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            estado = rs.next();//Guardamos el resultado para retornar la respuesta.
            conexion.close();
            
        } catch (SQLException ex) {
            System.out.printf("Error al consultar el inventario en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
            return estado;

    }//existeInventario
    
    //Este método es para llenar los combos de folio y su descripción. Se utilizan para llenar el combo en el inventario de la ventana principal
    //en la ventana para añadir productos y en el update del producto
    public String nomeclaturaFolio() {

        String lista = "";
        
        try {
            
            String sql = "select ID_Folio,Descripcion from Folio;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            rs.next();
            lista = rs.getString(1)+","+rs.getString(2);
            
            while(rs.next()){
                lista += ","+rs.getString(1)+","+rs.getString(2);
            }
            
            conexion.close();
            
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los folios en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } 
            return lista;

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
    
    public DefaultTableModel getInventarioCoincidencias(String prod) {

        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Clave");
            table.addColumn("Producto");
            table.addColumn("Almacén");
            table.addColumn("Descripción");
            table.addColumn("No. serie");
            table.addColumn("Marca");
            table.addColumn("Observaciones");
            table.addColumn("Modelo");
            table.addColumn("Color");
            table.addColumn("Estatus");
            
            //Consulta de los empleados
            String sql = "select id_producto,nombre_prod,almacen,descripcion,no_serie,marca,observaciones,modelo,color,estatus "
                         +"from inventario where nombre_prod = '"+prod+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[10];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<10;i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro

                table.addRow(datos);//Añadimos la fila
           }//while
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//getInventarioCoincidencias
    
    public DefaultTableModel getInventarioCoincidenciasEspecifico(int filtro,String prod,String busqueda) {

        DefaultTableModel table = new DefaultTableModel();
        String tipoBusqueda = "";
        
        try {
            table.addColumn("Clave");
            table.addColumn("Producto");
            table.addColumn("Almacén");
            table.addColumn("Descripción");
            table.addColumn("No. serie");
            table.addColumn("Marca");
            table.addColumn("Observaciones");
            table.addColumn("Modelo");
            table.addColumn("Color");
            table.addColumn("Estatus");
            
            /*
            filtro = 0; Clave
            filtro = 1; Almacén
            filtro = 2; Descripción
            filtro = 3; No. serie
            filtro = 4; Marca
            filtro = 5; Observaciones
            filtro = 6; Modelo
            filtro = 7; Color
            filtro = 8; Estatus
            */
            
            switch(filtro){

                case 0:
                    tipoBusqueda = "id_producto";
                    break;

                case 1:
                    tipoBusqueda = "almacen";
                    break;

                case 2:
                    tipoBusqueda = "descripcion";
                    break;

                case 3:
                    tipoBusqueda = "no_serie";
                    break;

                case 4:
                    tipoBusqueda = "marca";
                    break;

                case 5:
                    tipoBusqueda = "observaciones";
                    break;
                    
                case 6:
                    tipoBusqueda = "modelo";
                    break;

                case 7:
                    tipoBusqueda = "color";
                    break;

                case 8:
                    tipoBusqueda = "estatus";
                    break;    

            }//Buscamos el nombre de la columna con lo que vamos a buscar la coincidencia
            
            //Consulta de los empleados
            String sql = "select id_producto,nombre_prod,almacen,descripcion,no_serie,marca,observaciones,modelo,color,estatus "
                         +"from inventario where nombre_prod = '"+prod+"' and "+tipoBusqueda+" like '"+busqueda+"%';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[10];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<10;i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro

                table.addRow(datos);//Añadimos la fila
           }//while
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//getInventarioCoincidenciasEspecifico
    
    public boolean existeProductoCoincidenciaEspecifico(int filtro, String prod, String busqueda){
        boolean estado = false;
        String tipoBusqueda = "";
        try{
            /*
            filtro = 0; Clave
            filtro = 1; Almacén
            filtro = 2; Descripción
            filtro = 3; No. serie
            filtro = 4; Marca
            filtro = 5; Observaciones
            filtro = 6; Modelo
            filtro = 7; Color
            filtro = 8; Estatus
            */
            
            switch(filtro){

                case 0:
                    tipoBusqueda = "id_producto";
                    break;

                case 1:
                    tipoBusqueda = "almacen";
                    break;

                case 2:
                    tipoBusqueda = "descripcion";
                    break;

                case 3:
                    tipoBusqueda = "no_serie";
                    break;

                case 4:
                    tipoBusqueda = "marca";
                    break;

                case 5:
                    tipoBusqueda = "observaciones";
                    break;
                    
                case 6:
                    tipoBusqueda = "modelo";
                    break;

                case 7:
                    tipoBusqueda = "color";
                    break;

                case 8:
                    tipoBusqueda = "estatus";
                    break;    

            }//Buscamos el nombre de la columna con lo que vamos a buscar la coincidencia
            
            //Consulta de los empleados
            String sql = "select id_producto,nombre_prod,almacen,descripcion,no_serie,marca,observaciones,modelo,color,estatus "
                         +"from inventario where nombre_prod = '"+prod+"' and "+tipoBusqueda+" like '"+busqueda+"%';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            estado = rs.next();
            
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(ManagerInventario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return estado; //Retorna el resultado, si se encontro o no
        
    }//Buscar si existe el producto de coincidnecia especifico
    
    //Este método funciona en la pestaña de inventario, cuando se quiere buscar por un filtro mas especifico donde el usuario ingresara que esta
    //buscando de acuerdo al campo seleccionado mas aparte las opciones de nomeclatura de folio y estatus.
    public DefaultTableModel existeProductoEspecifico(int filtro, String busqueda,String inventario,String folio,String estatus){
        boolean estado = false;
        //No dejamos editar ninguna celda
        DefaultTableModel table = new DefaultTableModel(){
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
        conexion = db.getConexion();

        try{
            String campoBusca = "";
            String sql = "";
            //BUSCA EN EL INVENTARIO
            if(inventario.equals("Inventario")){
                
                switch(filtro){
                    
                    //BUSQUEDA POR CLAVE
                    case 0:
                        campoBusca = "concat(Folio,'-',Numero,Extension)";
                        break;
                    //BUSQUEDA POR NOMBRE CORTO
                    case 1:
                        campoBusca = "nombre_prod";
                        break;
                        
                    //BUSQUEDA POR DESCRIPCION
                    case 2:
                        campoBusca = "descripcion";
                        break;
                    //BUSQUEDA POR UBICACIÓN
                    case 3:
                        campoBusca = "ubicacion";
                        break;
                    //BUSQUEDA POR MARCA
                    case 4:
                        campoBusca = "marca";
                        break;
                        
                    //BUSQUEDA POR OBSERVACIONES
                    case 5:
                        campoBusca = "observaciones";
                        break;
                    //BUSQUEDA POR NO. SERIE
                    case 6:
                        campoBusca = "no_serie";
                        break;
                    //BUSQUEDA POR MODELO
                    case 7:
                        campoBusca = "modelo";
                        break;
                        
                    //BUSQUEDA POR COLOR
                    case 8:
                        campoBusca = "color";
                        break;
                    //BUSQUEDA POR FECHA DE COMPRA
                    case 9:
                        campoBusca = "fecha_compra";
                        break;
                    //BUSQUEDA POR FACTURA
                    case 10:
                        campoBusca = "factura";
                        break;
                    case 11:
                        campoBusca = "concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m)";
                        break;
                    
                }//Hace la busqueda de acuerdo al filtro
                if(filtro == 11 || (estatus.equals("Asignado") && folio.equals(""))){
                    sql =  "select concat(i.Folio,'-',i.Numero,i.Extension) as Clave,i.nombre_prod,i.descripcion,i.ubicacion,i.marca,i.observaciones,i.no_serie,i.modelo,i.color,i.fecha_compra,i.factura,i.importe,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Responsable  from vales v\n" +
                                "inner join detalle_vale dv on (dv.id_vale = concat(v.Folio,'-',v.Numero,'-',v.Año))\n" +
                                "inner join inventario i on (dv.id_producto = concat(i.Folio,'-',i.Numero,i.Extension))\n" +
                                "inner join empleados e on (e.id_empleado = v.id_empleado)\n" +
                                "where i.estatus= 'Asignado' and concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) like '%"+busqueda+"%';";
                }else{
                    if(filtro == 11 || (estatus.equals("Asignado") && !folio.equals(""))){
                        sql =  "select concat(i.Folio,'-',i.Numero,i.Extension) as Clave,i.nombre_prod,i.descripcion,i.ubicacion,i.marca,i.observaciones,i.no_serie,i.modelo,i.color,i.fecha_compra,i.factura,i.importe,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Responsable  from vales v\n" +
                                "inner join detalle_vale dv on (dv.id_vale = concat(v.Folio,'-',v.Numero,'-',v.Año))\n" +
                                "inner join inventario i on (dv.id_producto = concat(i.Folio,'-',i.Numero,i.Extension))\n" +
                                "inner join empleados e on (e.id_empleado = v.id_empleado)\n" +
                                "where i.estatus= 'Asignado' and concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) like '%"+busqueda+"%' and concat(i.Folio,'-',i.Numero,i.Extension) like '"+folio+"%';";
                    }
                    //Si no tiene nada la variable folio significa que esta buscando entre todas las nomeclaturas
                    else if(folio.equals("")){
                        sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,ubicacion,marca,observaciones,no_serie,modelo,color,fecha_compra,factura,importe "
                                + "from inventario where "+campoBusca+" like '%"+busqueda+"%' and estatus = '"+estatus+"';";
                        }else if(filtro == 0){
                            //Resultados que se quieran buscar por la clave del producto
                            sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,ubicacion,marca,observaciones,no_serie,modelo,color,fecha_compra,factura,importe "
                                    + "from inventario where "+campoBusca+" like '"+folio+"-"+busqueda+"%' and Folio = '"+folio+"' and estatus = '"+estatus+"';";
                        }else{
                            //Resultados por las demas columnas
                            sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,ubicacion,marca,observaciones,no_serie,modelo,color,fecha_compra,factura,importe "
                                    + "from inventario where "+campoBusca+" like '%"+busqueda+"%' and Folio = '"+folio+"' and estatus = '"+estatus+"';";
                        }
                }
                    
                
                Connection c = db.getConexion();
                Statement st = c.createStatement();    
                ResultSet rs = st.executeQuery(sql);
                estado = rs.next();
                int cantidadColumnas = 12;
                //Si el estado es verdadero significa que si encontro coincidencias, entonces mostraremos dichas concidencias
                if(estado){
                    
                    table.addColumn("Clave");
                    table.addColumn("Nombre_corto");
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
                    for(int i = 0;i<cantidadColumnas;i++){
                        datos[i] = rs.getObject(i+1);
                    }//Llenamos las columnas por registro
                    table.addRow(datos);
                    
                    //Proseguimos con los registros en caso de exisitir mas
                    while (rs.next()) {

                        for(int i = 0;i<cantidadColumnas;i++){
                            datos[i] = rs.getObject(i+1);
                        }//Llenamos las columnas por registro

                        table.addRow(datos);//Añadimos la fila
                   }//while
                    
                   conexion.close();
                
                //Si estado es falso, signfica que no encontro coincidencias, entonces retornamos la tabla normal
                }else{
                    return getInventario(folio,estatus);
                }
        
            }//if(inventario)
            //BUSCA EN EL INVENTARIO A GRANEL
            else{
                
                switch(filtro){
            
                    //BUSQUEDA POR CLAVE
                    case 0:
                        campoBusca = "id_productoGranel";
                        break;

                    //BUSQUEDA POR PRODUCTO
                    case 1:
                        campoBusca = "nombre_prod";
                        break;
                        
                    //BUSQUEDA POR DESCRIPCIÓN
                    case 2:
                        campoBusca = "descripcion";
                        break;
                        
                    //BUSQUEDA POR ALMACÉN
                    case 3:
                        campoBusca = "almacen";
                        break;

                    //BUSQUEDA POR MARCA
                    case 4:
                        campoBusca = "marca";
                        break;
                        
                    //BUSQUEDA POR OBSERVACIONES
                    case 5:
                        campoBusca = "observaciones"; 
                        break;    
                
                }//Hace la busqueda de acuerdo al filtro
            
                sql = "select id_productoGranel,nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel"
                                + " where "+campoBusca+" like '"+busqueda+"%';";
                Connection c = db.getConexion();
                Statement st = c.createStatement();    
                ResultSet rs = st.executeQuery(sql);
                estado = rs.next();
                
                //Si estado es verdadero significa que encontro concidencia, entonces mostramos las concidencias que se encontraron en la consulta
                if(estado){
                    
                    table.addColumn("Clave");
                    table.addColumn("Producto");
                    table.addColumn("Descripción");
                    table.addColumn("Almacén");
                    table.addColumn("Estatus");
                    table.addColumn("Marca");
                    table.addColumn("Observaciones");
                    table.addColumn("Stock");
                    
                    Object datos[] = new Object[8];
                    
                    //Anteriormente se hizo la consulta, y como entro a este if significa que si se encontraron datos, por ende ya estamos posicionados
                    //en el primer registro de las concidencias
                    for(int i = 0;i<8;i++){
                        datos[i] = rs.getObject(i+1);
                    }//Llenamos las columnas por registro
                    table.addRow(datos);
                    
                    //Proseguimos con los registros en caso de exisitir mas
                    while (rs.next()) {

                        for(int i = 0;i<8;i++){
                            datos[i] = rs.getObject(i+1);
                        }//Llenamos las columnas por registro

                        table.addRow(datos);//Añadimos la fila

                    }//while
                    
                    conexion.close();
                }else{
                    return manager_inventario_granel.getInventarioG(filtro);
                }
                
            }//else
            
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(ManagerInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return table; //Retorna el resultado, si se encontro o no
        
    }//Buscar si existe el producto
    
    //Este metodo retorna una tabla con el formato de la tabla de inventario pero anexando una columna con un checkbox para marcar los productos que
    //se quiere cambiar a pendiente para baja/comodato/donacion o devolver a disponibles
    public DefaultTableModel cambiarEstatus(String titulo,String pendiente,int filtro, String busqueda,String folio,String estatus){
        JTable checks = new JTable();
        JScrollPane scroll = new JScrollPane();
        conexion = db.getConexion();
        
        DefaultTableModel table = new DefaultTableModel();
        
        //Creamos la tabla con las caracterisiticas que necesitamos
        checks.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        checks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            //Declaramos el titulo de las columnas
            new String [] {
                titulo+pendiente,"Clave", "Nombre corto", "Descripción", "Ubicación", "Marca", "Observaciones", "No. Serie", "Modelo", "Color","Fecha de Compra","Factura","Importe"
            }
        ){
            //El tipo que sera cada columna, la primera columna un checkbox y los demas seran objetos
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            //Esto es para indicar que columnas dejaremos editar o no
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
            
          }
        
        );
        //Agregamos un scroll a la tabla
        scroll.setViewportView(checks);
        scroll.setBounds(30, 130, 1110, 500);
        
        table = (DefaultTableModel)checks.getModel();
        
        
        //Apartir de aquí se realiza el proceso para llenar la tabla con los datos que se estan buscando
        try{
           
            String campoBusca = "";
            String sql = "";
            switch(filtro){

                //BUSQUEDA POR CLAVE
                case 0:
                    campoBusca = "concat(Folio,'-',Numero,Extension)";
                    break;
                //BUSQUEDA POR NOMBRE CORTO
                case 1:
                    campoBusca = "nombre_prod";
                    break;

                //BUSQUEDA POR DESCRIPCION
                case 2:
                    campoBusca = "descripcion";
                    break;
                //BUSQUEDA POR UBICACIÓN
                case 3:
                    campoBusca = "ubicacion";
                    break;
                //BUSQUEDA POR MARCA
                case 4:
                    campoBusca = "marca";
                    break;

                //BUSQUEDA POR OBSERVACIONES
                case 5:
                    campoBusca = "observaciones";
                    break;
                //BUSQUEDA POR NO. SERIE
                case 6:
                    campoBusca = "no_serie";
                    break;
                //BUSQUEDA POR MODELO
                case 7:
                    campoBusca = "modelo";
                    break;

                //BUSQUEDA POR COLOR
                case 8:
                    campoBusca = "color";
                    break;
                //BUSQUEDA POR FECHA DE COMPRA
                case 9:
                    campoBusca = "fecha_compra";
                    break;
                //BUSQUEDA POR FACTURA
                case 10:
                    campoBusca = "factura";
                    break;

            }//Hace la busqueda de acuerdo al filtro

            //Si no tiene nada la variable folio significa que esta buscando entre todas las nomeclaturas
            if(folio.equals("")){
                sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,ubicacion,marca,observaciones,no_serie,modelo,color,fecha_compra,factura,importe "
                    + "from inventario where "+campoBusca+" like '"+busqueda+"%' and estatus = '"+estatus+"';";
            }else{
                if(filtro == 0){
                sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,ubicacion,marca,observaciones,no_serie,modelo,color,fecha_compra,factura,importe "
                        + "from inventario where "+campoBusca+" like '"+folio+"-"+busqueda+"%' and Folio = '"+folio+"' and estatus = '"+estatus+"';";
                }else{
                    sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,ubicacion,marca,observaciones,no_serie,modelo,color,fecha_compra,factura,importe "
                        + "from inventario where "+campoBusca+" like '"+busqueda+"%' and Folio = '"+folio+"' and estatus = '"+estatus+"';";
                }
            }
            Connection c = db.getConexion();
            Statement st = c.createStatement();    
            ResultSet rs = st.executeQuery(sql);
            boolean estado = rs.next();

            //Si el estado es verdadero significa que si encontro coincidencias, entonces mostraremos dichas concidencias
            if(estado){
                
                Object datos[] = new Object[13];

                //Anteriormente se hizo la consulta, y como entro a este if significa que si se encontraron datos, por ende ya estamos posicionados
                //en el primer registro de las concidencias
                datos[0] = Boolean.FALSE;
                
                for(int i = 1;i<13;i++){
                        datos[i] = rs.getString(i);
                }//Llenamos la fila
                table.addRow(datos);
                
                //Proseguimos con los registros en caso de exisitir mas
                while (rs.next()) {

                    datos[0] = Boolean.FALSE;
                
                    for(int i = 1;i<13;i++){
                            datos[i] = rs.getString(i);
                    }//Llenamos la fila

                    table.addRow(datos);//Añadimos la fila
               }//while

               conexion.close();

            //Si estado es falso, signfica que no encontro coincidencias, entonces retornamos la tabla normal
            }else{
                if(folio.equals("")){
                    sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,ubicacion,marca,observaciones,no_serie,modelo,color,fecha_compra,factura,importe "
                        + "from inventario where estatus = '"+estatus+"';";
                }
                else{
                    sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,ubicacion,marca,observaciones,no_serie,modelo,color,fecha_compra,factura,importe "
                        + "from inventario where Folio = '"+folio+"' and estatus = '"+estatus+"';";
                }
            
                Object datos[] = new Object[13];
                rs = st.executeQuery(sql);

                //Llenar tabla
                while (rs.next()) {

                    datos[0] = Boolean.FALSE;

                    for(int i = 1;i<13;i++){
                            datos[i] = rs.getString(i);
                    }//Llenamos la fila

                    table.addRow(datos);//Añadimos la fila
                }//while
                conexion.close();
            }//else
            
        } catch (SQLException ex) {
            System.out.printf("Error al generar la tabla para con checkbox en el Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return table;
        
    }//Retorna una tabla con un checkbox en la primera columna de pendiente para baja/comodato/donación
    
    //Este metodo retorna una tabla para solicitar productos a granel
    public DefaultTableModel tablaSolicitarInvGranel(){
        conexion = db.getConexion();
        DefaultTableModel table = new DefaultTableModel();
        
        table.addColumn("Clave");
        table.addColumn("Nombre corto");
        table.addColumn("Descripción");
        
        //Apartir de aquí se realiza el proceso para llenar la tabla con los datos que se estan buscando
        try{
            
            String sql = "select id_productoGranel,nombre_prod,descripcion from inventario_granel;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();    
            ResultSet rs = st.executeQuery(sql);

            Object datos[] = new Object[3];

            //Llenamos la tabla
            while (rs.next()) {

                for(int i = 0;i<3;i++){
                        datos[i] = rs.getString(i+1);
                }//Llenamos la fila

                table.addRow(datos);//Añadimos la fila
           }//while

           conexion.close();
            
        } catch (SQLException ex) {
            System.out.printf("Error al obtener el Inventario a granel para solicitarlo en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return table;
        
    }//Retorna una tabla con un checkbox en la primera columna (Solicitar Inventario a granel)
    
    //Este metodo actualiza el estatus de 1 o más productos a pendiente para baja/comodato/donación
    public boolean actualizarPendientePara(String[] ids,Boolean[]cambio,String pendientePara) {
        conexion = db.getConexion();
        
        String update = "";
        PreparedStatement ps = null;

        try {
            for(int i = 0;i<ids.length;i++){
                if(cambio[i]){
                    update = "update inventario set estatus = ? where concat(Folio,'-',Numero,Extension) = '"+ids[i]+"'";
                    ps = conexion.prepareStatement(update);
                    ps.setString(1, pendientePara);
                    ps.executeUpdate();
                }
            }
            return true;

        } catch (Exception ex) {
            System.out.println("Error al actualizar el estatus pendiente "+ pendientePara + ex.getMessage());
            return false;

        }

    }//actualizarPendientePara
    
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
    
    public Vector infoProductos(String idProducto) {
        conexion = db.getConexion();
        Vector v = new Vector();
        try {

            Statement st = conexion.createStatement();
            String sql = "select nombre_prod,descripcion,almacen,marca,observaciones,no_serie,tipo_uso,modelo,color from inventario where id_producto = '"+idProducto+"';";
            ResultSet resultados = st.executeQuery(sql);
            while (resultados.next()) {
                String temp = "";
                temp += "" + resultados.getString("nombre_prod") + "," + resultados.getString("descripcion") + "," + resultados.getString("almacen")
                         + "," + resultados.getString("marca")+ "," + resultados.getString("observaciones")+ "," + resultados.getString("no_serie")
                        + "," + resultados.getString("tipo_uso") + "," + resultados.getString("modelo")+ "," + resultados.getString("color");
                        
                v.add(temp);
            }

            conexion.close();

        } //para el ticket
        catch (SQLException ex) {
            Logger.getLogger(ManagerVehiculos.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error producto infoProductos");
        }

        return v;
    }//infoVehiculos
    
     
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
