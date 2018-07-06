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
import java.util.Calendar;
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

    //Este metodo realiza el registro de la solicitud de salida de almacen y retorna la tabla para que ingresen la cantidad que desean solicitar
    public boolean registro_SolicitudSalida(String user, String [] Productos,int[] Cantidad){
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
            
            //Buscamos el año y el numero en el que se quedo la solicitud
            Calendar cal= Calendar.getInstance();
            int year= cal.get(Calendar.YEAR);
            int num = 1;
            
            sql = "select Num from solicitudSalida where año = "+year+" and Folio = 'SALIDA' order by Num desc limit 1;";
            rs = st.executeQuery(sql);
            //Si encuentra coincidencias entonces le sumamos uno para el siguiente vale, 
            //en caso de no encontrarlo entonces se reinicia el contador de solicitudes con el nuevo año
            if(rs.next()){
                num = rs.getInt(1) + 1;
            }
            
            //Registramos la solicitud
            sql = "insert into solicitudSalida (Folio,Num,Año,id_user,fecha_solicitud,estado) "
                        +"values('SALIDA',"+num+","+year+",'"+user+"','"+fecha+"','Solicitud Salida');";
            st.executeUpdate(sql);
            
            for (int i = 0; i<Productos.length;i++) {
                //Registramos los productos que se solicitaron
                sql = "insert into detalle_solicitudSalida (id_solicitud,id_producto,cantidad_solicitada) "
                        +"values('SALIDA-"+num+"-"+year+"','" + Productos[i] + "',"+Cantidad[i]+");";
                st.executeUpdate(sql);
            }
            
            conexion.close();
            return true;
        } catch (SQLException ex) {
            System.out.printf("Error al insertar la solicitud en SQL");
            Logger.getLogger(ManagerSolicitud.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }//registro_solicitudSalida
    
    //Este metodo agrega la cantidad que se le otorgorá por cada producto solicitado
    public boolean registro_AceptaSalida(String user,String id,String[] Productos,int[] Cantidad){
        String idSol ="";
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
            
            //Actualiza el regitro de la solicitud agregando el usuario que lo autorizo, cuando lo hizo y cambiamos el estado
            sql = "update solicitudSalida set user_autorizo = '"+user+"', fecha_respuesta = '"+fecha+"', estado = 'Atendida' where concat(Folio,'-',Num,'-',Año) = '"+id+"';";
            st.executeUpdate(sql);
            
            for (int i = 0; i<Productos.length;i++) {
                //Registramos los productos que se solicitaron
                sql = "update detalle_solicitudSalida set cantidad_autorizada = "+Cantidad[i]+" where id_solicitud = '"+id+"' and id_producto = '"+Productos[i]+"';";
                st.executeUpdate(sql);
            }
            
            conexion.close();
            return true;
        } catch (SQLException ex) {
            System.out.printf("Error al aceptar la solicitud de salida de almacen en SQL");
            Logger.getLogger(ManagerSolicitud.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }//registro_AceptaSalida
    
    //Este metodo muestra una tabla con los pedidos de productos que se quieren para realizar la solicitud de salida de almacen,
    //esto para que indiquen la cantidad de productos que requieren cada uno.
    public DefaultTableModel tabla_SolicitudSalida(String solicitud) {
        
        DefaultTableModel table = new DefaultTableModel();
        try {
            
            table.addColumn("Clave");
            table.addColumn("Nombre corto");
            table.addColumn("Descripción");
            table.addColumn("Marca");
            table.addColumn("Solicitó");
            table.addColumn("Stock");
            
            conexion = db.getConexion();
            
            String sql="select concat(ig.Folio,'-',ig.Numero,ig.Extension), ig.nombre_prod, ig.descripcion, ig.marca, dss.cantidad_solicitada,ig.stock from detalle_solicitudSalida dss\n" +
                        "inner join solicitudsalida ss on (concat(ss.Folio,'-',ss.Num,'-',ss.Año) = dss.id_solicitud)\n" +
                        "inner join inventario_granel ig on (concat(ig.Folio,'-',ig.Numero,ig.Extension) = dss.id_producto) where dss.id_solicitud = '"+solicitud+"';";
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
            System.out.printf("Error al obtener los productos de la solicitud de la salida de almacen SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//tabla_SolicitudSalida --> Muestra los productos que estan asignados a una solicitud

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
    
    //Este método muestra la tabla de solicitudes de acuerdo a los permisos que tienes por solicitud
    public DefaultTableModel tabla_SolicitudesMejorada(String user) {
        
        DefaultTableModel table = new DefaultTableModel();
        String sql="";
        try {
            
            table.addColumn("Solicitud");
            table.addColumn("Empleado que solicito");
            table.addColumn("Fecha cuando se solicito");
            table.addColumn("Estado");
            
            sql = "select puesto from user where id_user = '"+user+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            
            sql = "select concat(ss.Folio,'-',ss.Num,'-',ss.Año) as ID, concat(e.nombres,' ',e.apellido_p, ' ', e.apellido_m) as Empleado,\n" +
                    "date(ss.fecha_solicitud), ss.estado from solicitudsalida ss\n" +
                    "inner join user u on (u.id_user = ss.id_user) inner join empleados e on (e.id_empleado = u.id_empleado) "
                    + "where ss.estado in(select tipo_solicitud from vista_permisosSolicitud where puesto = '"+puesto+"');";
            
            Object datos[] = new Object[5];
            rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {
                
                for(int i = 0;i<4;i++){
                    
                datos[i] = rs.getObject(i+1);    
                    
                }//Llenamos las columnas por registro
                
                table.addRow(datos);//Añadimos la fila
           }//while
                
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error get tabla solicitudes SQL");
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
            
            table.addColumn("Solicitud");
            table.addColumn("Fecha cuando se solicito");
            table.addColumn("Consumibles");
            table.addColumn("Estado");
            
            sql = "select concat(ss.Folio,'-',ss.Num,'-',ss.Año), date(ss.fecha_solicitud), count(dss.id_solicitud),ss.estado from solicitudsalida ss "
                + "inner join detalle_solicitudsalida dss on (concat(ss.Folio,'-',ss.Num,'-',ss.Año) = dss.id_solicitud) "
                + "where id_user='"+usuario+"' group by dss.id_solicitud;";
            
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[4];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {
                
                for(int i = 0;i<4;i++){
                    
                datos[i] = rs.getObject(i+1);    
                    
                }//Llenamos las columnas por registro
                
                table.addRow(datos);//Añadimos la fila
           }//while
                
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener las solicitudes del usuario \""+usuario+"\" SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//tabla_Solicitudes_Personales --> Muestra las solicitudes que realizo el usuario permisos_solicitudes

    //Este método es para saber si todavia sigue disponible el producto y no lo selecciono alguien más, retorna un respectivo true o false
    //Esto se utiliza en el Manejador Inventario, en la opción de asignación
    public String estadoProducto(String clave) {
        String estado = "";
        try{
           
            String sql = "select estatus from inventario where concat(Folio,'-',Numero,Extension) = '"+clave+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            estado = rs.getString(1);
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener el estatus del producto en SQL");
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
    }//guardarImagenSolicitud
    
    //Este método es para registrar la solicitud de los productos que se requieren pero que aun no han sido autorizados o denegados
    public boolean solicitudResguardo(String idProd[], String tipo,String empleado,String motivo,int cantidad){
        
        if(motivo.equals("")){
            motivo = "Sin especificar el motivo";
        }
        
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
                        +"values('"+tipo+"','"+empleado+"','"+motivo+"',"+cantidad+",'"+fecha+"','SOLICITUD');";
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
    
}//class 
