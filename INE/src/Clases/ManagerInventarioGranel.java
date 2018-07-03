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
	
    public boolean insertarInventarioG(String clave, String producto, String almacen, String marca,int stockmin, int stock, String descripcion, String observaciones) {
        try {
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Creamos la variable para guardar el resultado de las consultas
            ResultSet rs;
            
            //Insertamos al inventario
            String sql = "insert into inventario_Granel (concat(Folio,'-',Numero,Extension),nombre_prod,almacen,marca,stock_min,stock,descripcion,observaciones,estatus) "
                         +"values('"+clave+"','"+producto+"','"+almacen+"','"+marca+"','"+stockmin+"','"+stock+"','"
                         +descripcion+"','"+observaciones+"','Disponible');";
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
	
	public boolean existeInventarioG(String id_producto) {

        boolean estado = false;
        
        try {
            //Consulta para saber si existe o no dicho producto
            String sql = "select * from inventario_Granel where concat(Folio,'-',Numero,Extension) = '"+id_producto+"';";
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
    public DefaultTableModel tablaSolicitarInvGranel(){
        conexion = db.getConexion();
        DefaultTableModel table = new DefaultTableModel();
        
        table.addColumn("Clave");
        table.addColumn("Nombre corto");
        table.addColumn("Descripción");
        
        //Apartir de aquí se realiza el proceso para llenar la tabla con los datos que se estan buscando
        try{
            
            String sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion from inventario_granel;";
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
