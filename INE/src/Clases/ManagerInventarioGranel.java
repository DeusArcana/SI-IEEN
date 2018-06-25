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
	
	
	
	public boolean insertarInventarioG(String clave, String producto, String almacen, String marca,int stockmin, int stock, String descripcion, String observaciones,String tipo) {
		try {
			//Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Creamos la variable para guardar el resultado de las consultas
            ResultSet rs;
            
            //Insertamos al inventario
            String sql = "insert into inventario_Granel (id_productoGranel,nombre_prod,almacen,marca,stock_min,stock,descripcion,observaciones,estatus,tipo_uso) "
                         +"values('"+clave+"','"+producto+"','"+almacen+"','"+marca+"','"+stockmin+"','"+stock+"','"
                         +descripcion+"','"+observaciones+"','Disponible','"+tipo+"');";
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
            String sql = "select * from inventario_Granel where id_productoGranel = '"+id_producto+"';";
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
                    orden = "order by id_productoGranel";
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
            String sql = "select id_productoGranel,nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel "+orden+";";
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
}
