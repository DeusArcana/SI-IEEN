package Clases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DeusArcana
 */
public class ManagerInventarioGranel {
	
	private Connection conexion;
        private final Conexion db;

	public ManagerInventarioGranel() {
		this.db = new Conexion();
	}
	
        public DefaultTableModel getBusquedaInventario(int filtro, String busqueda, String folio, String estatus){
            boolean estado = false;
            //No dejamos editar ninguna celda
            DefaultTableModel table = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        conexion = db.getConexion();
		
        String campoBusca = "";
        String sql = "";

        try{
            switch(filtro){
            
                //BUSQUEDA POR CLAVE
                case 0:
                    campoBusca = "concat(Folio,'-',Numero,Extension)";
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
            
            sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel"
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
                return getInventarioG(filtro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
            return table;
    }//getBusquedaInventarioG

    //Este método es para realizar el registro de un nuevo producto consumible
    public boolean insertarInventarioG(int numero,String extension, String producto, String almacen, String marca,int stockmin, int stock, String descripcion) {
        try {
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Creamos la variable para guardar el resultado de las consultas
            ResultSet rs;
            
            //Insertamos al inventario
            String sql = "insert into inventario_Granel (Folio,Numero,Extension,nombre_prod,almacen,marca,stock_min,stock,descripcion,estatus) "
                         +"values('EY-99',"+numero+",'"+extension+"','"+producto+"','"+almacen+"','"+marca+"','"+stockmin+"','"+stock+"','"
                         +descripcion+"','Disponible');";
            st.executeUpdate(sql);
            
            //Cerramos la conexión
            conexion.close();
            return true;
            
        } catch (SQLException ex) {
            System.out.printf("Error al insertar en el inventario en SQL");
            Logger.getLogger(ManagerInventario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//insertarInventarioGranel
	
    //Este método es para actualizar la información del consumible
    public boolean actualizarInventarioG(String clave, String producto, String almacen, String marca,int stockmin, String descripcion) {
        try {
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Creamos la variable para guardar el resultado de las consultas
            ResultSet rs;
            
            //Actualizamos la información del consumible
            String sql = "update inventario_granel set nombre_prod = '"+producto+"',descripcion = '"+descripcion+"'"
                       + ",almacen='"+almacen+"',marca='"+marca+"',stock_min="+stockmin+"  "
                       + "where concat(Folio,'-',Numero,Extension) = '"+clave+"';";
            st.executeUpdate(sql);
            
            //Cerramos la conexión
            conexion.close();
            return true;
            
        } catch (SQLException ex) {
            System.out.printf("Error al acutalizar la información de consumible \""+clave+"\" en SQL");
            Logger.getLogger(ManagerInventario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//actualizarInventarioGranel
    
    public boolean existeInventario(String id_producto) {

        boolean estado = false;
        
        try {
            //Consulta para saber si existe o no dicho producto
            String sql = "select Folio from inventario_Granel where concat(Folio,'-',Numero,Extension) = '"+id_producto+"';";
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

    }//existeInventarioG
    
    //Este método es para dar la sugerencia del siguiente número para darle clave al nuevo consumible
    public int sugNumero() {

        int numero = 1;
        try {
            //Consulta para saber si existe o no dicho producto
            String sql = "select Numero from inventario_Granel order by Numero desc limit 1;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                numero = rs.getInt(1)+1;
            }
            
            conexion.close();
            
        } catch (SQLException ex) {
            System.out.printf("Error al consultar el inventario en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } 
            return numero;

    }//sugNumero
    
    //Este método es para obtener la información del consumible para después actualizarlo
    public String obtenerDatosConsumible(String clave) {

        try {
            //Obtenemos los datos del empleado
            String sql = "select Numero, Extension, nombre_prod,descripcion,almacen,marca,stock_min,stock "
                       + "from inventario_granel where concat(Folio,'-',Numero,Extension) = '"+clave+"';";
            
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            
            //Llenamos la cadena con los datos
            String datos = rs.getString(1);
            for(int i = 2;i<9;i++){
                datos += ",,"+rs.getString(i);
            }
            
            conexion.close();
            return datos;
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los datos del consumible en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } 
        
    }//obtenerDatosConsumible
    
    
    
    public String obtenerDatosResguardo(String clave) {
        String datos = "";
        try {
            //   No inventario, no serie, descripcion, marca, modelo, color
            //Obtenemos los datos del empleado
            String sql = "select concat(Folio,'-',Numero,Extension),no_serie,descripcion,marca,modelo,color "
                       + "from inventario where concat(Folio,'-',Numero,Extension) = '"+clave+"';";
            
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            
            //Llenamos la cadena con los datos
            
            for(int i = 1; i < 7;i++){
                datos += rs.getString(i)+",,";
            }
            
            conexion.close();
            return datos;
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los datos del consumible en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } 
        
    }//obtenerDatosConsumible
    
    public String obtenerDatosResponsableResguardo(String usuario) {
        String datos = "";
        try {
            //   No inventario, no serie, descripcion, marca, modelo, color
            //Obtenemos los datos del empleado                     0                    1         2           3           4
            String sql = "select concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m), a.Area, pt.Puesto,municipio, localidad  from empleados "
                    + "e inner join area a on (a.ID_Area = e.area) inner join puestos_trabajo pt on (pt.ID_Puesto = e.puesto) "
                    +"where concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) = '"+usuario+"';";
            
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            
            //Llenamos la cadena con los datos
            
            for(int i = 1; i < 6;i++){
                datos += rs.getString(i)+",,";
            }
            
            conexion.close();
            return datos;
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los datos del consumible en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } 
        
    }//obtenerDatosConsumible
    
    
    public String obtenerDatosDocumento(int clave) {
        String datos = "";
        try {
            //   No inventario, no serie, descripcion, marca, modelo, color
            //Obtenemos los datos del empleado
            String sql = "select id.ID_Producto, i.no_serie, i.descripcion, i.marca,i.modelo, i.color, i.cantidad_fotos from inv_docs id " +
                "inner join inventario i on (concat(i.Folio,'-',i.Numero,i.Extension) = id.ID_Producto) " +
                "inner join productos_asignados pa on (pa.ID_Producto = id.ID_Producto) " +
                "where id.ID_Documento = "+clave+";";
            
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            
            while (rs.next()) {
                for (int i = 1; i < 8; i++) {
                    // if funciona para encontrar el final de la consulta
                    if(i != 7){
                        datos += rs.getString(i) + ",,";
                    }else{
                        datos += rs.getString(i) + "//";
                    }
                }
                
            }

            conexion.close();
            return datos;
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los datos del consumible en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } 
        
    }//obtenerDatosConsumible
    
    
    public String obtenerNumeroResguardo(String año) {
        String datos = "";
        try {
            //   No inventario, no serie, descripcion, marca, modelo, color
            //Obtenemos los datos del empleado
            String sql = "select concat(Folio,'-',Numero,'-',Año) from vales where Año = '"+año+"' order by Numero desc limit 1;";
            
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            
            datos = rs.getString(1);
            conexion.close();
            return datos;
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los datos del consumible en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } 
        
    }//obtenerDatosConsumible
	
    public DefaultTableModel getInventarioG(int filtro) {
        String orden = "";
        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Clave");
            table.addColumn("Producto");
            table.addColumn("Descripción");
            table.addColumn("Almacén");
            table.addColumn("Estatus");
            table.addColumn("Marca");
            table.addColumn("Observaciones");
            table.addColumn("Stock");
            
            switch(filtro){
                case 0:
                    orden = "order by concat(Folio,'-',Numero,Extension)";
                    break;
                case 1:
                    orden = "order by nombre_prod";
                    break;
                case 2:
                    orden = "order by descripcion";
                    break;    
                case 3:
                    orden = "order by ubicacion";
                    break;
                case 4:
                    orden = "order by marca";
                    break;
                case 5:
                    orden = "order by observaciones";
                    break;
            }
            
            //Consulta de los empleados
            String sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel "+orden+";";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[8];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<8;i++){
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

    }//getInventarioG
		
	    //Este metodo retorna una tabla para solicitar productos a granel
    public DefaultTableModel tablaSolicitarInvGranel(int indice, String busqueda){
        conexion = db.getConexion();
        DefaultTableModel table = new DefaultTableModel();
        
        table.addColumn("Clave");
        table.addColumn("Nombre corto");
        table.addColumn("Descripción");
        
        //Apartir de aquí se realiza el proceso para llenar la tabla con los datos que se estan buscando
        try{
            String campoBusca = "";
            switch(indice){
                case 0:
                    campoBusca = "concat(Folio,'-',Numero,Extension)";
                    break;
                case 1:
                    campoBusca = "nombre_prod";
                    break;
                case 2:
                    campoBusca = "descripcion";
                    break;
                    
            }//switch
            String sql = "";
            if(indice == 0){
                sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion from inventario_granel where "+campoBusca+" like 'EY-99-"+busqueda+"%';";
            }else{
                sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion from inventario_granel where "+campoBusca+" like '"+busqueda+"%';";
            }
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
}
