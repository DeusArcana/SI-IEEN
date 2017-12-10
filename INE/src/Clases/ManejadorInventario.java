/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author kevin
 */
public class ManejadorInventario {
    private Connection conexion;
    private Conexion db;
    
    public ManejadorInventario(){
        db = new Conexion();
    }//constructor
    
    public DefaultTableModel getInventarioG() {
        
        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Clave");
            table.addColumn("Producto");
            table.addColumn("Descripción");
            table.addColumn("Almacén");
            table.addColumn("Marca");
            table.addColumn("Observaciones");
            table.addColumn("Stock");
            
            
            //Consulta de los empleados
            String sql = "select id_productoGranel,nombre_prod,descripcion,almacen,marca,observaciones,stock from Inventario_granel where estatus = 'DISPONIBLE';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[7];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<7;i++){
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

    public DefaultTableModel getInventario() {
        String orden = "";
        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Clave");
            table.addColumn("Producto");
            table.addColumn("Descripción");
            table.addColumn("Almacén");
            table.addColumn("Marca");
            table.addColumn("No. Serie");
            table.addColumn("Observaciones");
            table.addColumn("Modelo");
            table.addColumn("Color");
            //Consulta de los empleados
            String sql = "select id_producto,nombre_prod,descripcion,almacen,marca,no_serie,observaciones,modelo,color from inventario where estatus = 'DISPONIBLE';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[9];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<9;i++){
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
    
    public int cantidadInventarioG(String id_producto) {

        int cantidad;
        
        try {
            //Consulta para saber si existe o no dicho producto
            String sql = "select stock from inventario_Granel where id_productoGranel = '"+id_producto+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();//Guardamos el resultado para retornar la respuesta.
            cantidad = rs.getInt(1);
            conexion.close();
            return cantidad;
        } catch (SQLException ex) {
            System.out.printf("Error al consultar el inventario en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } 

    }//cantidadInventarioG
    
    public int productosSuficientesInventarioG(String id_producto,int cantidad) {
        int stock = 0;
        try {
            //Hacemos el update de la resta del inventario
            String sql = "update inventario_Granel set stock = stock - "+cantidad+" where id_productoGranel = '"+id_producto+"' and stock > "+cantidad+";";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            st.executeUpdate(sql);
            //Obtenemos el stock del producto para saber si se realizo o no el update
            sql = "select stock from inventario_Granel where id_productoGranel = '"+id_producto+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            stock = rs.getInt(1);
            conexion.close();
            return stock;
        } catch (SQLException ex) {
            System.out.printf("Error al consultar el inventario en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }//productosSuficientesInventarioG
    
    public int productosIgualesInventarioG(String id_producto,int cantidad) {
        int stock = 0;
        try {
            //Hacemos el update de la resta del inventario
            String sql = "update inventario_Granel set stock = 0,estatus = 'AGOTADO' where id_productoGranel = '"+id_producto+"' and stock = "+cantidad+";";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            st.executeUpdate(sql);
            //Obtenemos el stock del producto para saber si se realizo o no el update
            sql = "select stock from inventario_Granel where id_productoGranel = '"+id_producto+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            stock = rs.getInt(1);
            conexion.close();
            return stock;
        } catch (SQLException ex) {
            System.out.printf("Error al consultar el inventario en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }//productosIgualesInventarioG
    
}//class
