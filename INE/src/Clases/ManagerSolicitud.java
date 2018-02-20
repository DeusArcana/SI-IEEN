/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kevin
 */
public class ManagerSolicitud {
    
    private Connection conexion;
    private Conexion db;
    
    public ManagerSolicitud(){
        
        db = new Conexion();
        
    }//Constructor
    
    public boolean registro_Solicitud(String idProd, String tipo,String user,String motivo,int cantidad){
        
        try {
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Creamos la variable para guardar el resultado de las consultas
            ResultSet rs;
            
            //Obtenemos la fecha del sistema
            String sql = "select now();";
            rs = st.executeQuery(sql);
            rs.next();
            String fecha = rs.getString(1); 
            
            //Registramos la solicitud
            sql = "insert into Solicitudes (tipo_solicitud,id_user,motivo,cantidad,fecha_solicitud,estado) "
                        +"values('"+tipo+"','"+user+"','"+motivo+"',"+cantidad+",'"+fecha+"','SOLICITUD');";
            st.executeUpdate(sql);
            
            //Cambiamos el estatus del equipo seleccionado
            sql = "update Inventario set estatus = '"+tipo+"' where id_producto = '"+idProd+"'";
            st.executeUpdate(sql);
            
            //Buscamos el id de la solicitud
            sql = "select id_solicitud from Solicitudes where fecha_solicitud = '"+fecha+"';";
            rs = st.executeQuery(sql);
            rs.next();
            String idSol = rs.getString(1); 
            
            //Realizamos el registro de los detalles de la solicitud
            sql = "insert into Detalle_solicitud values('"+idSol+"','"+idProd+"')";
            st.executeUpdate(sql);
            
            //Cerramos la conexión
            conexion.close();
            return true;
            
        } catch (SQLException ex) {
            System.out.printf("Error al insertar la solicitud en SQL");
            Logger.getLogger(ManagerSolicitud.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//registro_solicitud

    public boolean actualizar_Solicitud(int idSol,String tipo){
        
        try {
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            
            //Actualizamos la solicitud
            String sql = "update solicitudes set estado = 'PENDIENTE' where id_solicitud = "+idSol+"";
            st.executeUpdate(sql);
            
            //Obtenemos el id del producto
            sql = "select id_producto from detalle_solicitud where id_solicitud = '"+idSol+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String idProd = rs.getString(1);
            
            //Actualizamos el estatus del producto
            sql = "update Inventario set estatus = 'PENDIENTE "+tipo+"' where id_producto = '"+idProd+"'";
            st.executeUpdate(sql);

            //Cerramos la conexión
            conexion.close();
            return true;
            
        } catch (SQLException ex) {
            System.out.printf("Error al insertar la solicitud en SQL");
            Logger.getLogger(ManagerSolicitud.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//actualizar_Solicitud
    
    public boolean respuesta_Pendiente(int idSol,String tipo,String respuesta){
        
        try {
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            
            //Actualizamos la solicitud
            String sql = "update solicitudes set estado = '"+respuesta+"' where id_solicitud = "+idSol+"";
            st.executeUpdate(sql);
            
            //Obtenemos el id del producto
            sql = "select id_producto from detalle_solicitud where id_solicitud = '"+idSol+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String idProd = rs.getString(1);
            
            //Actualizamos el estatus del producto
            sql = "update Inventario set estatus = '"+tipo+" "+respuesta+"' where id_producto = '"+idProd+"'";
            st.executeUpdate(sql);

            //Cerramos la conexión
            conexion.close();
            return true;
            
        } catch (SQLException ex) {
            System.out.printf("Error al insertar la solicitud en SQL");
            Logger.getLogger(ManagerSolicitud.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//respuesta_Pendiente
    
    public void getComboSolicitud(JComboBox combo) {
        try{
           
            String sql = "select * from tiposolicitud;";
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
        
    }//Obtiene todas los tipos de solicitud y las mete al combobox
    
    public void getComboVale(JComboBox combo) {
        try{
           
            String sql = "select * from tipovale;";
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
    
    public DefaultTableModel tabla_Solicitudes(int permiso) {
        
        DefaultTableModel table = new DefaultTableModel();
        String sql="";
        try {
            
            table.addColumn("No. Solicitud");
            table.addColumn("Solicitud");
            table.addColumn("Empleado que solicito");
            table.addColumn("Producto");
            table.addColumn("Motivo");
            table.addColumn("Fecha cuando se solicito");
            table.addColumn("Estado");
            
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
                    sql = "select s.id_solicitud,s.tipo_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto) "+ 
                            "where s.estado ='SOLICITUD' or s.estado = 'SOLICITUD PERSONAL' order by s.fecha_solicitud;";
                    break;
                case 13:
                    sql = "select s.id_solicitud,s.tipo_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds "
                            + "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)"
                            + "inner join user u on (u.id_user = s.id_user)"
                            + "inner join empleados e on (e.id_empleado = u.id_empleado)"
                            + "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + "where (s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud donación' or s.tipo_solicitud = 'Solicitud comodato') and s.estado = ''SOLICITUD' or s.estado = 'SOLICITUD PERSONAL';";
                    break; // Había un error de sintaxis SQL aquí :P
                case 12:
                    sql = "select s.id_solicitud,s.tipo_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds"
                                + "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)"
                                + "inner join user u on (u.id_user = s.id_user)"
                                + "inner join empleados e on (e.id_empleado = u.id_empleado)"
                                + "inner join inventario i on (i.id_producto = ds.id_producto)"
                                + "where (s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud donación' or s.tipo_solicitud = 'Solicitud reemplazo') and s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL';";
                    break;
                case 11:
                    sql = "select s.id_solicitud,s.tipo_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where (s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud comodato' or s.tipo_solicitud = 'Solicitud reemplazo') and s.estado ='SOLICITUD' or s.estado = 'SOLICITUD PERSONAL' order by s.fecha_solicitud;";
                    break;
                case 10:
                    sql = "select s.id_solicitud,s.tipo_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where (s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud donación') and s.estado ='SOLICITUD' or s.estado = 'SOLICITUD PERSONAL' order by s.fecha_solicitud;";
                    break;
                case 9:
                    sql = "select s.id_solicitud,s.tipo_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where (s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud comodato') and s.estado ='SOLICITUD' or s.estado = 'SOLICITUD PERSONAL' order by s.fecha_solicitud;";
                    break;
                case 8:
                    sql = "select s.id_solicitud,s.tipo_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where (s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud reemplazo') and s.estado = ' 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL' order by s.fecha_solicitud;";
                    break;
                case 7:
                    sql = "select s.id_solicitud,s.tipo_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto) "+
                            "where (s.tipo_solicitud = 'Solicitud reemplazo' or s.tipo_solicitud = 'Solicitud comodato') and s.estado = ''SOLICITUD' or s.estado = 'SOLICITUD PERSONAL' order by s.fecha_solicitud;";
                    break;
                case 6:
                    sql = "select s.id_solicitud,s.tipo_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where (s.tipo_solicitud = 'Solicitud reemplazo' or s.tipo_solicitud = 'Solicitud donación') and s.estado = ' 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL' order by s.fecha_solicitud;";
                    break;
                case 5:
                    sql = "select s.id_solicitud,s.tipo_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where (s.tipo_solicitud = 'Solicitud comodato or s.tipo_solicitud = 'Solicitud donación') and s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL' order by s.fecha_solicitud;";
                    break;
                case 4:
                    sql = "select s.id_solicitud,s.tipo_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud baja' and s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL' order by s.fecha_solicitud;";
                    break;
                case 3:
                    sql = "select s.id_solicitud,s.tipo_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud comodato' and s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL' order by s.fecha_solicitud;";
                    break;
                case 2:
                    sql = "select s.id_solicitud,s.tipo_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud donación' and s.estado = 'SOLICITUD' or s.estado = 'SOLICITUD PERSONAL' order by s.fecha_solicitud;";
                    break;
                case 1:
                    sql = "select s.id_solicitud,s.tipo_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud reemplazo' and s.estado ='SOLICITUD' or s.estado = 'SOLICITUD PERSONAL' order by s.fecha_solicitud;";
                    break;
            }
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

    }//tabla_Solicitudes --> Muestra las solicitudes que puedes ver de acuerdo la tabla de permisos_solicitudes
    
    public DefaultTableModel tabla_Pendientes() {
        
        DefaultTableModel table = new DefaultTableModel();
        
        try {
            
            table.addColumn("No. Solicitud");
            table.addColumn("Solicitud");
            table.addColumn("Empleado que solicito");
            table.addColumn("Producto");
            table.addColumn("Motivo");
            table.addColumn("Fecha cuando se solicito");
            table.addColumn("Estado");
            
            conexion = db.getConexion();
            
            String sql="select s.id_solicitud,s.tipo_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds " +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                            "inner join user u on (u.id_user = s.id_user) " +
                            "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                            "inner join inventario i on (i.id_producto = ds.id_producto) "+ 
                            "where s.estado = 'PENDIENTE' or s.estado = 'PENDIENTE PERSONAL' order by s.fecha_solicitud;";
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

    }//tabla_Pendientes --> Muestra los pendientes, osea las solicitudes que ya revisaron
    
    public DefaultTableModel tabla_Solicitudes_Personales(String usuario) {
        
        DefaultTableModel table = new DefaultTableModel();
        String sql;
        try {
            
            table.addColumn("No. Solicitud");
            table.addColumn("Producto");
            table.addColumn("Motivo");
            table.addColumn("Fecha cuando se solicito");
            table.addColumn("Estado");
            
            sql = "select s.id_solicitud,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds " +
                  "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud) " +
                  "inner join user u on (u.id_user = s.id_user) " +
                  "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                  "inner join inventario i on (i.id_producto = ds.id_producto) where s.id_user = '"+usuario+"';";
            
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[5];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {
                
                for(int i = 0;i<5;i++){
                    
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

    }//tabla_Solicitudes_Personales --> Muestra las solicitudes que realizo el usuario permisos_solicitudes

    public String estadoProducto(String clave) {
        String estado = "";
        try{
           
            String sql = "select estatus from inventario where id_producto = '"+clave+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            estado = rs.getString(1);
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los puestos para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return estado;
        } 
        return estado;
    }//Obtiene el estado del producto
    
    public String getProductoSolicitud(int idSol) {
        String idProducto = "";
        try{
           
            String sql = "select id_producto from detalle_solicitud where id_solicitud = '"+idSol+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            idProducto = rs.getString(1);
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los puestos para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return idProducto;
        } 
        return idProducto;
    }//Obtiene el estado del producto
    
    public boolean guardarImagenSolicitud(String idProducto, String ruta) {
        conexion = db.getConexion();
        String update = "update inventario set imagen = ? where id_producto = '"+idProducto+"'";
       // String insert = "insert into inventario (id_producto,nombre_prod,almacen,marca,no_serie,descripcion,observaciones,estatus,tipo_uso,modelo,color,imagen)values(?,?,?,?,?,?,?,?,?,?,?,?);";
        FileInputStream fi = null;
        PreparedStatement ps = null;

        try {
            File file = new File(ruta);
            fi = new FileInputStream(file);

            ps = conexion.prepareStatement(update);

            ps.setBinaryStream(1, fi);

            ps.executeUpdate();

            return true;
           
        } catch (Exception ex) {
            System.out.println("Error al guardar Imagen " + ex.getMessage());
            return false;

        }
    }
    
}//class 
