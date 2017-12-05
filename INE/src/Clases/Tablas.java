
package Clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Pablo
 */
public class Tablas {
    
    public static String[] retornarContenidoFila(JTable tabla,int row){
        String contenido_fila="";
        for(int i=0;i<tabla.getColumnCount();i++){
            contenido_fila+=tabla.getValueAt(row,i)+",";
            
        }
        return contenido_fila.split(",");
    }
    
    public static String retornarCadenaFila(JTable tabla,int row){
        String contenido_fila="";
        for(int i=0;i<tabla.getColumnCount();i++){
            if(i==tabla.getColumnCount()-1){contenido_fila+=tabla.getValueAt(row,i);}
            else{contenido_fila+=tabla.getValueAt(row,i)+",";}   
        }
        //System.out.println(contenido_fila);
        return contenido_fila;
    }
    
    public static String retornarIdFila(JTable tabla,int row){
        return(String)tabla.getValueAt(row, 0);
    }
    
    public static void printArreglo(String[] arreglo){
        for(int i=0;i<arreglo.length;i++){
            System.out.println("Arreglo["+i+"]= "+arreglo[i]);
        }
    }
    //Tablas de BD
    public static Object[] generarColumnasGraficamenteInventario(){
        return new Object[]{"id_producto","nombre_producto","descripcion","almacen","estatus"
        ,"observaciones","no_serie","tipo_uso","modelo","color"};
    }
    
    public static Object[] generarColumnasGraficamenteInventarioParcial(){
        return new Object[]{"id","nombre_producto","descripcion","almacen"
        ,"observaciones","no_serie","modelo","color"};
    }
    
    public static Object[] generarColumnasGraficamenteInventarioGlobal(){
        return new Object[]{"id","nombre_producto","estatus","id_vale"
        ,"tipo_vale","estado_vale","Nombres","Paterno","Materno"};
    }
    
    public static Object[] generarColumnasGraficamenteInventarioGranelParcial(){
        return new Object[]{"id","producto","descripcion","almacen"
        ,"marca","observaciones","stock_min","stock"};
    }
    
    public static Object[] generarColumnasGraficamenteUsuarios(){
        return new Object[]{"usuario"};
    }
    
    public static Object[] generarContenidoColumnasInventario(ResultSet rs) throws SQLException{
        return new Object[]{rs.getString("id_producto"),rs.getString("nombre_prod"),rs.getString("descripcion")
                ,rs.getString("almacen"),rs.getString("estatus"),rs.getString("observaciones"),
                rs.getString("no_serie"),rs.getString("tipo_uso"),rs.getString("modelo"),rs.getString("color")};
    }
    
    public static Object[] generarContenidoColumnasInventarioGlobal(ResultSet rs) throws SQLException{
        return new Object[]{rs.getString("id_producto"),rs.getString("nombre_prod"),rs.getString("estatus")
                ,rs.getString("id_vale"),rs.getString("tipo_vale"),
                rs.getString("estado"),rs.getString("nombres"),rs.getString("apellido_p"),rs.getString("apellido_m")};
    }
    
    public static Object[] generarContenidoColumnasInventarioGlobalGranel(ResultSet rs) throws SQLException{
        return new Object[]{rs.getString("id_productoGranel"),rs.getString("nombre_prod"),rs.getString("estatus")
                ,rs.getString("id_vale"),rs.getString("tipo_vale"),
                rs.getString("estado"),rs.getString("nombres"),rs.getString("apellido_p"),rs.getString("apellido_m")};
    }
    
    public static Object[] generarContenidoColumnasInventarioParcial(ResultSet rs) throws SQLException{
        return new Object[]{rs.getString("id_producto"),rs.getString("nombre_prod"),rs.getString("descripcion")
                ,rs.getString("almacen"),rs.getString("observaciones"),
                rs.getString("no_serie"),rs.getString("modelo"),rs.getString("color")};
    }
    
    public static Object[] generarContenidoColumnasInventarioGranelParcial(ResultSet rs) throws SQLException{
        return new Object[]{rs.getString("id_productoGranel"),rs.getString("nombre_prod"),rs.getString("descripcion")
                ,rs.getString("almacen"),rs.getString("marca"),
                rs.getString("observaciones"),rs.getString("stock_min"),rs.getString("stock")};
    }
    
    public static Object[] generarColumnasGraficamenteDatosResponsable(){
        return new Object[]{"nombres","Paterno","Materno","puesto","area","Municipio","Localidad"};
    }
    
    public static Object[] generarContenidoColumnasDatosResponsable(ResultSet rs) throws SQLException{
        return new Object[]{rs.getString("nombres"),rs.getString("apellido_p"),rs.getString("apellido_m"),rs.getString("puesto"),
            rs.getString("area") ,rs.getString("Municipio"),rs.getString("Localidad")};
    }
    
    public static Object[] generarContenidoColumaUsuario(ResultSet rs) throws SQLException{
        return new Object[]{rs.getString("id_user")};
    }
    
}
