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
public class ManagerUsers {
    ManagerPermisos manager_permisos;
    private Connection conexion;
    private Conexion db;
    
    public ManagerUsers(){
    
        db = new Conexion();
        manager_permisos = new ManagerPermisos();
        
    }//constructor
    
    public DefaultTableModel getEmpleados(String usuario) {

        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Usuario");
            table.addColumn("Nombre(s)");
            table.addColumn("Apellido Paterno");
            table.addColumn("Apellido Materno");
            table.addColumn("Cargo");
            table.addColumn("Área");
            
            //Consulta de los empleados
            String sql = "select u.id_user,e.nombres,e.apellido_p,e.apellido_m,u.puesto,u.area from user u " +
                         "inner join empleados e on (u.id_empleado = e.id_empleado) where u.puesto != 'SuperUsuario' and u.id_user != '"+usuario+"';";
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

    }//getEmpleados
    
    public DefaultTableModel getEmpleadosCoincidencia(String usuario,int filtro,String busqueda) {

        DefaultTableModel table = new DefaultTableModel();
        String tipoBusqueda = "";
        try{
            
            /*
            filtro = 0; Usuario
            filtro = 1; Nombres
            filtro = 2; Apellido P
            filtro = 3; Apellido M
            filtro = 4; Cargo
            filtro = 5; Área
            */
            
            switch(filtro){

                case 0:
                    tipoBusqueda = "u.id_user";
                    break;

                case 1:
                    tipoBusqueda = "e.nombres";
                    break;

                case 2:
                    tipoBusqueda = "e.apellido_p";
                    break;

                case 3:
                    tipoBusqueda = "e.apellido_m";
                    break;

                case 4:
                    tipoBusqueda = "u.puesto";
                    break;

                case 5:
                    tipoBusqueda = "u.area";
                    break;    

            }//Buscamos el nombre de la columna con lo que vamos a buscar la coincidencia

            table.addColumn("Usuario");
            table.addColumn("Nombre(s)");
            table.addColumn("Apellido Paterno");
            table.addColumn("Apellido Materno");
            table.addColumn("Cargo");
            table.addColumn("Área");
            
            //Consulta de los empleados
            String sql = "select u.id_user,e.nombres,e.apellido_p,e.apellido_m,u.puesto,u.area from user u " +
                         "inner join empleados e on (u.id_empleado = e.id_empleado) where u.puesto != 'SuperUsuario' "
                    +    "and u.id_user != '"+usuario+"' and "+tipoBusqueda+" like '"+busqueda+"%';";
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

    }//getEmpleadosCoincidencia

    public boolean existeEmpleado(int filtro, String busqueda,String usuario){
        boolean estado = false;
        String tipoBusqueda = "";
        try{
            
            /*
            filtro = 0; Usuario
            filtro = 1; Nombres
            filtro = 2; Apellido P
            filtro = 3; Apellido M
            filtro = 4; Cargo
            filtro = 5; Área
            */
            
            switch(filtro){

                case 0:
                    tipoBusqueda = "u.id_user";
                    break;

                case 1:
                    tipoBusqueda = "e.nombres";
                    break;

                case 2:
                    tipoBusqueda = "e.apellido_p";
                    break;

                case 3:
                    tipoBusqueda = "e.apellido_m";
                    break;

                case 4:
                    tipoBusqueda = "u.puesto";
                    break;

                case 5:
                    tipoBusqueda = "u.area";
                    break;    

            }//Buscamos el nombre de la columna con lo que vamos a buscar la coincidencia
            
            String sql = "select u.id_user,e.nombres,e.apellido_p,e.apellido_m,u.puesto,u.area from user u " +
                         "inner join empleados e on (u.id_empleado = e.id_empleado) where u.puesto != 'SuperUsuario' "
                    +    "and u.id_user != '"+usuario+"' and "+tipoBusqueda+" like '"+busqueda+"%';";
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
        
    }//Buscar si existe el empleado
    
    public boolean insertarEmpleado(String usuario, String nombres, String apellidoP, String apellidoM, String telefono, String pass,String calle, String colonia, 
                                    String curp,String rfc,String fecha,String codigoP,String puesto, String area,boolean documentacion,String municipio,String localidad) {
        int id_empleado;
        try {
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Creamos la variable para guardar el resultado de las consultas
            ResultSet rs;
            
            //Primero insertamos al empleado
            String sql = "insert into empleados (nombres,apellido_p,apellido_m,calle,colonia,telefono,codigo_postal,fecha_nacimiento,curp,rfc,municipio,localidad) "
                         +"values('"+nombres+"','"+apellidoP+"','"+apellidoM+"','"+calle+"','"+colonia+"','"
                         +telefono+"','"+codigoP+"','"+fecha+"','"+curp+"','"+rfc+"','"+municipio+"','"+localidad+"');";
            st.executeUpdate(sql);
            
            //Una vez insertado, obtendremos el ID del empleado
            sql = "select id_empleado from empleados where nombres = '"+nombres+"' and apellido_p = '"+apellidoP+"' and apellido_m = '"+apellidoM
                  +"'and calle = '"+calle+"' and colonia = '"+colonia+"' and telefono = '"+telefono+"' and codigo_postal = '"+codigoP
                  +"'and fecha_nacimiento = '"+fecha+"' and curp = '"+curp+"' and rfc = '"+rfc+"' and municipio = '"+municipio+"' and localidad = '"+localidad+"';";
            rs = st.executeQuery(sql);
            rs.next();
            id_empleado = rs.getInt(1);
            
            //Ya se realizo la inserción y se encontro el ID de ese nuevo registro, ahora insertamos el usuario y ligamos el ID, su cargo y su área
            sql = "insert into user values('"+usuario+"',"+id_empleado+","+documentacion+",'"+pass+"','"+puesto+"','"+area+"');";
            st.executeUpdate(sql);
            
            //Registramos el nuevo usuario en la tabla de permisos(por el momento no tendra ningún permiso, ya que solo es el registro)
            //Primero obtenemos la cantidad de modulos que hay
            sql = "select count(*) from modulos";
            rs = st.executeQuery(sql);
            rs.next();
            int tamaño = rs.getInt(1);
            
            //Creamos el arreglo donde guardaremos el nombre de cada modulo
            String[] modulos = new String[tamaño];
            //Hacemos la consulta para obtener todos los nombres de los modulos
            sql = "select * from modulos";
            rs = st.executeQuery(sql);
            rs.next();
            //Llenamos el arreglo con los nombres de los modulos
            for(int i = 0;i<tamaño;i++){
                modulos[i] = rs.getString(1);
                rs.next();
            }//for
            
            //Insertamos todos los modulos sin permisos al usuario
            for(int i = 0;i<tamaño;i++){
                sql = "insert into Permisos values('"+usuario+"','"+modulos[i]+"',false,false,false,false);";
                st.executeUpdate(sql);
            }//for
            
            //Ahora le damos los permisos de acuerdo al cargo que tiene
            manager_permisos.asignarPermisos_Puesto(puesto, usuario,area);
            //Cerramos la conexión
            conexion.close();
            return true;
            
        } catch (SQLException ex) {
            System.out.printf("Error al insertar el empleado en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//insertarEmpleado
    
    public boolean actualizarEmpleado(String usuario, String nombres, String apellidoP, String apellidoM,String calle,String colonia, String telefono,String codigoP,String fecha,String curp,String rfc,String municipio,String localidad,String puesto) {

        try {
            //Primero obtenemos el id del empleado
            String sql = "select id_empleado from user where id_user = '"+usuario+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            int empleado = rs.getInt(1);
            
            //Ahora actualizamos el perfil del empleado
            sql = "update empleados set nombres = '"+nombres+"',apellido_p = '"+apellidoP+"',apellido_m = '"+apellidoM
                  +"',calle = '"+calle+"',colonia = '"+colonia+"',telefono = '"+telefono+"',codigo_postal = '"+codigoP
                  +"',fecha_nacimiento = '"+fecha+"',curp = '"+curp+"',rfc = '"+rfc+"',municipio = '"+municipio+"',localidad = '"+localidad+"' "
                  + "where id_empleado = "+empleado+";";
            st.executeUpdate(sql);
            
            //manager_permisos.asignarPermisos_Puesto(puesto, usuario,area);
            
            conexion.close();
            return true;
        } catch (SQLException ex) {
            System.out.printf("Error al insertar el empleado en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//actualizarEmpleado
    
    public boolean eliminarEmpleado(String usuario) {

        int id_empleado;
        
        try {
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs;
            
            //Para poder eliminar al usuario, primero será necesario borrar los registros de otras tablas donde este
            //ligado su llave foraneá y comenzaremos por eliminar los registros de los permisos
            String sql = "delete from permisos where id_user = '"+usuario+"';";
            st.executeUpdate(sql);
            
            //Antes de eliminar al usuario, primero obtenemos el id del empleado
            sql = "select id_empleado from user where id_user = '"+usuario+"';";
            rs = st.executeQuery(sql);
            rs.next();
            id_empleado = rs.getInt(1);
            
            //Ahora eliminamos el registro que contenia dicho usuario
            sql = "delete from user where id_user = '"+usuario+"';";
            st.executeUpdate(sql);
            
            //Y ahora eliminamos el registro del empleado
            sql = "delete from empleados where id_empleado = "+id_empleado+";";
            st.executeUpdate(sql);
            
            //Cerramos la conexión
            conexion.close();
            return true;
        } catch (SQLException ex) {
            System.out.printf("Error al insertar el empleado en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }//Eliminar empleado
    
    public boolean existeUsuario(String usuario) {

        boolean estado = false;
        
        try {
            //Consulta para saber si existe o no dicho usuario
            String sql = "select * from user where id_user = '"+usuario+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            estado = rs.next();//Guardamos el resultado para retornar la respuesta.
            conexion.close();
            
        } catch (SQLException ex) {
            System.out.printf("Error al consultar el usuario en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
            return estado;

    }//existeUsuario
    
    public String infoEmpleado(String usuario){
        String empleado;
        try {
            //Consulta para saber si existe o no dicho usuario
            String sql = "select e.nombres,e.apellido_p,e.apellido_m,e.calle,e.colonia,e.telefono,e.codigo_postal,e.fecha_nacimiento,e.curp,e.rfc,e.municipio,e.localidad,u.puesto,u.area from empleados e "
                    + "inner join user u on (u.id_empleado = e.id_empleado)where u.id_user = '"+usuario+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            empleado = rs.getString(1);
            for(int i = 2;i<15;i++){
                empleado += ","+rs.getString(i);
            }
            conexion.close();
            
        } catch (SQLException ex) {
            System.out.printf("Error al obtener la información del empleado en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } 
            return empleado;
        
    }//infoEmpleado
    
    public void getNombresEmpleados(JComboBox combo) {
        try{
           
            String sql = "select concat(nombres,' ',apellido_p,' ',apellido_m) from Empleados;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo.addItem(rs.getObject(1).toString());
            }
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los nombres de los empleados para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }//Obtiene todas los nombres de los empleados
    
    public String obtenerPuesto(String empleado) {
        try{
           
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Creamos la variable para guardar el resultado de las consultas
            ResultSet rs;

            //Obtenemos el id del empleado para dar con su usuario
            String sql = "select id_empleado from Empleados where concat(nombres,' ',apellido_p,' ',apellido_m) = '"+empleado+"';";
            rs = st.executeQuery(sql);
            rs.next();
            int idEmpleado = rs.getInt(1);
            
            //Ahora obtenemos el usuario gracias al id del empleado
            sql = "select u.id_user from user u inner join empleados e on(e.id_empleado = u.id_empleado) where e.id_empleado = "+idEmpleado+";";
            rs = st.executeQuery(sql);
            rs.next();
            String usuario = rs.getString(1);
            
            //Ahora obtenemos el usuario gracias al id del empleado
            sql = "select puesto from user where id_user = '"+usuario+"';";
            rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            //Cerramos la conexión
            conexion.close();
            return puesto;
            
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los nombres de los empleados para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } 
        
    }//obtenerPuesto
    
}//class
