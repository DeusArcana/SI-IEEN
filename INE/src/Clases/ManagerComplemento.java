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
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kevin
 */
public class ManagerComplemento {
    
    private Connection conexion;
    private Conexion db;
    
    public ManagerComplemento(){
        
        db = new Conexion();
        
    }//Constructor
    
    public DefaultTableModel getPuestos() {

        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Puestos");
            
            //Consulta de los empleados
            String sql = "select * from Puestos;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {
                table.addRow(new Object[]{rs.getObject(1)});//Añadimos la fila
           }//while

            //Cerramos la conexión
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//getPuestos
    
    public DefaultTableModel getAreas() {

        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Áreas");
            
            //Consulta de los empleados
            String sql = "select * from Area;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {
                table.addRow(new Object[]{rs.getObject(1)});//Añadimos la fila
           }//while

            //Cerramos la conexión
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//getAreas
    
    public void getComboPuestos(JComboBox combo) {
        try{
           
            String sql = "select * from Puestos;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo.addItem(rs.getObject(1).toString());
            }
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los puestos para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }//Obtiene todas los puestos y las mete al combobox
    
    public void getComboAreas(JComboBox combo) {
        try{
           
            String sql = "select * from Area;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo.addItem(rs.getObject(1).toString());
            }
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener las áreas para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }//Obtiene todas los puestos y las mete al combobox
    
    public DefaultTableModel getResguardoPersonal(String usuario) {

        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Producto");
            table.addColumn("Fecha de ingreso");
            table.addColumn("Observaciones");
            
            //Consulta de los empleados
            String sql = "select nombre_prod,date(fecha_alta),observaciones from resguardo_personal where id_user = '"+usuario+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Object[] datos = new Object[3];
            
            //Llenar tabla
            while (rs.next()) {
                
                for(int i = 0;i<3;i++){
                    datos[i] = rs.getString(i+1);
                }
                
                table.addRow(datos);//Añadimos la fila
           
            }//while

            //Cerramos la conexión
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//getResguardoPersonal
    
    public boolean insertarResguardo(String usuario,String producto,String observaciones) {
        try{
           
            String sql = "insert into resguardo_personal values('"+usuario+"','"+producto+"',now(),'"+observaciones+"');";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            st.executeUpdate(sql);
            
            conexion.close();
            
            return true;
            
        } catch (SQLException ex) {
            System.out.printf("Error al obtener las áreas para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//Obtiene todas los puestos y las mete al combobox
    
    public int cantidadSolicitudes(int permiso) {
        int cantidad = 0;
        
        String sql="";
        try {
            
            /*
            14 -> todos los tipos de solicitud
            13 -> baja, donación y comodato
            12 -> baja, donación y reemplazo
            11 -> baja, comodato y reemplazo
            10 -> baja y donación 
            9 -> baja y comodato
            8 -> baja y reemplazo
            7 -> reemplazo y comodato
            6 -> reemplazo y donación
            5 -> comodato y donación
            4 -> baja
            3 -> comodato
            2 -> donación
            1 -> reemplazo
            0 -> ningun permiso
           */
            
            switch(permiso){
                case 14:
                    sql = "select count(*) from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto) "+ 
                            "where (s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL');";
                    break;
                case 13:
                    sql = "select count(*) from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where (s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud donación' or s.tipo_solicitud = 'Solicitud comodato') and (s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL');";
                    break;
                case 12:
                    sql = "select count(*) from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where (s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud donación' or s.tipo_solicitud = 'Solicitud reemplazo') and (s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL');";
                    break;
                case 11:
                    sql = "select count(*) from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where (s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud comodato' or s.tipo_solicitud = 'Solicitud reemplazo') and (s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL');";
                    break;
                case 10:
                    sql = "select count(*) from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where (s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud donación') and (s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL');";
                    break;
                case 9:
                    sql = "select count(*) from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where (s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud comodato') and (s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL');";
                    break;
                case 8:
                    sql = "select count(*) from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where (s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud reemplazo') and (s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL');";
                    break;
                case 7:
                    sql = "select count(*) from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto) "+
                            "where (s.tipo_solicitud = 'Solicitud reemplazo' or s.tipo_solicitud = 'Solicitud comodato') and (s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL');";
                    break;
                case 6:
                    sql = "select count(*) from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where (s.tipo_solicitud = 'Solicitud reemplazo' or s.tipo_solicitud = 'Solicitud donación') and (s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL');";
                    break;
                case 5:
                    sql = "select count(*) from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where (s.tipo_solicitud = 'Solicitud comodato or s.tipo_solicitud = 'Solicitud donación') and (s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL');";
                    break;
                case 4:
                    sql = "select count(*) from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud baja' and (s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL');";
                    break;
                case 3:
                    sql = "select count(*) from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud comodato' and (s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL');";
                    break;
                case 2:
                    sql = "select count(*) from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud donación' and (s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL');";
                    break;
                case 1:
                    sql = "select count(*) from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud reemplazo' and (s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL');";
                    break;
            }
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            cantidad = rs.getInt(1);
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return cantidad;
        }

    }//cantidadSolicitudes
    
    public int cantidadPendientes() {
        int cantidad = 0;

        try {
            
            conexion = db.getConexion();
            
            String sql="select count(*) from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto) "+ 
                            "where s.estado = 'PENDIENTE' or s.estado = 'PENDIENTE PERSONAL';";
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            cantidad = rs.getInt(1);
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return cantidad;
        }

    }//cantidadPendientes
    
}//class
