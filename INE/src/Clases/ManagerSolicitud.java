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
    
    public boolean registro_Solicitud(String idSol,String idProd, String tipo,String user,String motivo,int cantidad){
        
        try {
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Creamos la variable para guardar el resultado de las consultas
            ResultSet rs;
            
            //Registramos la solicitud
            String sql = "insert into Solicitudes (id_solicitud,tipo_solicitud,id_user,motivo,cantidad,fecha_solicitud,estado) "
                        +"values('"+idSol+"','"+tipo+"','"+user+"','"+motivo+"',"+cantidad+",now(),'PENDIENTE')";
            st.executeUpdate(sql);
            
            //Cambiamos el estatus del equipo seleccionado
            sql = "update Inventario set estatus = '"+tipo+"' where id_producto = '"+idProd+"'";
            st.executeUpdate(sql);
            
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
        
    }//Obtiene todas los puestos y las mete al combobox
    
    public DefaultTableModel tabla_Solicitudes(int permiso) {
        
        DefaultTableModel table = new DefaultTableModel();
        String sql="";
        try {
            
            table.addColumn("No. Solicitud");
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
                    sql = "select s.id_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds\n" +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)\n" +
                            "inner join user u on (u.id_user = s.id_user)\n" +
                            "inner join empleados e on (e.id_empleado = u.id_empleado)\n" +
                            "inner join inventario i on (i.id_producto = ds.id_producto);";
                    break;
                case 13:
                    sql = "select s.id_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds\n" +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)\n" +
                            "inner join user u on (u.id_user = s.id_user)\n" +
                            "inner join empleados e on (e.id_empleado = u.id_empleado)\n" +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud donación' or s.tipo_solicitud = 'Solicitud comodato';";
                    break;
                case 12:
                    sql = "select s.id_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds\n" +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)\n" +
                            "inner join user u on (u.id_user = s.id_user)\n" +
                            "inner join empleados e on (e.id_empleado = u.id_empleado)\n" +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud donación' or s.tipo_solicitud = 'Solicitud reemplazo';";
                    break;
                case 11:
                    sql = "select s.id_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds\n" +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)\n" +
                            "inner join user u on (u.id_user = s.id_user)\n" +
                            "inner join empleados e on (e.id_empleado = u.id_empleado)\n" +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud comodato' or s.tipo_solicitud = 'Solicitud reemplazo';";
                    break;
                case 10:
                    sql = "select s.id_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds\n" +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)\n" +
                            "inner join user u on (u.id_user = s.id_user)\n" +
                            "inner join empleados e on (e.id_empleado = u.id_empleado)\n" +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud donación';";
                    break;
                case 9:
                    sql = "select s.id_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds\n" +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)\n" +
                            "inner join user u on (u.id_user = s.id_user)\n" +
                            "inner join empleados e on (e.id_empleado = u.id_empleado)\n" +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud comodato';";
                    break;
                case 8:
                    sql = "select s.id_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds\n" +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)\n" +
                            "inner join user u on (u.id_user = s.id_user)\n" +
                            "inner join empleados e on (e.id_empleado = u.id_empleado)\n" +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud baja' or s.tipo_solicitud = 'Solicitud reemplazo';";
                    break;
                case 7:
                    sql = "select s.id_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds\n" +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)\n" +
                            "inner join user u on (u.id_user = s.id_user)\n" +
                            "inner join empleados e on (e.id_empleado = u.id_empleado)\n" +
                            "inner join inventario i on (i.id_producto = ds.id_producto)\n"+
                            "where s.tipo_solicitud = 'Solicitud reemplazo' or s.tipo_solicitud = 'Solicitud comodato';";
                    break;
                case 6:
                    sql = "select s.id_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds\n" +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)\n" +
                            "inner join user u on (u.id_user = s.id_user)\n" +
                            "inner join empleados e on (e.id_empleado = u.id_empleado)\n" +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud reemplazo' or s.tipo_solicitud = 'Solicitud donación';";
                    break;
                case 5:
                    sql = "select s.id_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds\n" +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)\n" +
                            "inner join user u on (u.id_user = s.id_user)\n" +
                            "inner join empleados e on (e.id_empleado = u.id_empleado)\n" +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud comodato or s.tipo_solicitud = 'Solicitud donación';";
                    break;
                case 4:
                    sql = "select s.id_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds\n" +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)\n" +
                            "inner join user u on (u.id_user = s.id_user)\n" +
                            "inner join empleados e on (e.id_empleado = u.id_empleado)\n" +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud baja';";
                    break;
                case 3:
                    sql = "select s.id_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds\n" +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)\n" +
                            "inner join user u on (u.id_user = s.id_user)\n" +
                            "inner join empleados e on (e.id_empleado = u.id_empleado)\n" +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud comodato';";
                    break;
                case 2:
                    sql = "select s.id_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds\n" +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)\n" +
                            "inner join user u on (u.id_user = s.id_user)\n" +
                            "inner join empleados e on (e.id_empleado = u.id_empleado)\n" +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud donación';";
                    break;
                case 1:
                    sql = "select s.id_solicitud,concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds\n" +
                            "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)\n" +
                            "inner join user u on (u.id_user = s.id_user)\n" +
                            "inner join empleados e on (e.id_empleado = u.id_empleado)\n" +
                            "inner join inventario i on (i.id_producto = ds.id_producto)"
                            + " where s.tipo_solicitud = 'Solicitud reemplazo';";
                    break;
            }
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[6];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {
                
                for(int i = 0;i<6;i++){
                    
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
    
    public DefaultTableModel tabla_Solicitudes_Personales(String usuario) {
        
        DefaultTableModel table = new DefaultTableModel();
        String sql;
        try {
            
            table.addColumn("No. Solicitud");
            table.addColumn("Producto");
            table.addColumn("Motivo");
            table.addColumn("Fecha cuando se solicito");
            table.addColumn("Estado");
            
            sql = "select s.id_solicitud,i.nombre_prod,s.motivo,date(s.fecha_solicitud) as fecha_solicitud,s.estado from detalle_solicitud ds\n" +
                  "inner join solicitudes s on (s.id_solicitud = ds.id_solicitud)\n" +
                  "inner join user u on (u.id_user = s.id_user)\n" +
                  "inner join empleados e on (e.id_empleado = u.id_empleado)\n" +
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
    
}//class 
