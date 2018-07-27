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

    //Este método es para obtener una tabla con toda la información de los empleados y proporcionarla en el documento de Excel
    public DefaultTableModel getEmpleadosExcel(String usuario,int filtro,String busqueda) {

        DefaultTableModel table = new DefaultTableModel();

        try {
            
            /*
            filtro = 0; Nombres
            filtro = 1; Apellido P
            filtro = 2; Apellido M
            */
            String tipoBusqueda = "";
            switch(filtro){

                case 0:
                    tipoBusqueda = "e.nombres";
                    break;

                case 1:
                    tipoBusqueda = "e.apellido_p";
                    break;

                case 2:
                    tipoBusqueda = "e.apellido_m";
                    break;    
                case 3:
                    tipoBusqueda = "a.area";
                    break;    
                case 4:
                    tipoBusqueda = "pt.puesto";
                    break;    

            }//Buscamos el nombre de la columna con lo que vamos a buscar la coincidencia
            
            table.addColumn("Nombre(s)");
            table.addColumn("Apellido Paterno");
            table.addColumn("Apellido Materno");
            table.addColumn("Calle");
            table.addColumn("Colonia");
            table.addColumn("Telefono");
            table.addColumn("Código Postal");
            table.addColumn("Fecha de Nacimiento");
            table.addColumn("CURP");
            table.addColumn("RFC");
            table.addColumn("Municipio");
            table.addColumn("Localidad");
            table.addColumn("Área");
            table.addColumn("Puesto");
            
            //Consulta de los empleados
            String sql = "select e.nombres, e.apellido_p, e.apellido_m, e.calle, e.colonia, e.telefono, e.codigo_postal, e.fecha_nacimiento, e.curp, "
                       + "e.rfc, e.municipio, e.localidad, a.area, pt.Puesto from empleados e "
                       + "inner join area a on (a.ID_Area = e.area) "
                       + "inner join puestos_trabajo pt on (pt.ID_Puesto = e.puesto) "
                       + "where "+tipoBusqueda+" like '%"+busqueda+"%' and e.id_empleado not in (select id_empleado from user);";;
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[14];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<14;i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro

                table.addRow(datos);//Añadimos la fila
           }//while
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error obtener la tabla de empleados con toda la información en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//getEmpleadosExcel
    
    //Este método es para obtener una tabla con toda la información de los usuarios y proporcionarla en el documento de Excel
    public DefaultTableModel getUsuariosExcel(String usuario,int filtro,String busqueda) {

        DefaultTableModel table = new DefaultTableModel();

        try {
            String tipoBusqueda = "";
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
                    tipoBusqueda = "a.area";
                    break;    
                case 6:
                    tipoBusqueda = "pt.puesto";
                    break;

            }//Buscamos el nombre de la columna con lo que vamos a buscar la coincidencia
            
            table.addColumn("Usuario");
            table.addColumn("Nombre(s)");
            table.addColumn("Apellido Paterno");
            table.addColumn("Apellido Materno");
            table.addColumn("Calle");
            table.addColumn("Colonia");
            table.addColumn("Telefono");
            table.addColumn("Código Postal");
            table.addColumn("Fecha de Nacimiento");
            table.addColumn("CURP");
            table.addColumn("RFC");
            table.addColumn("Municipio");
            table.addColumn("Localidad");
            table.addColumn("Área");
            table.addColumn("Puesto");
            
            //Consulta de los empleados
            String sql = "select u.id_user, e.nombres, e.apellido_p, e.apellido_m, e.calle, e.colonia, e.telefono, e.codigo_postal, e.fecha_nacimiento, "
                       + "e.curp, e.rfc, e.municipio, e.localidad, a.area, pt.Puesto from empleados e "
                       + "inner join user u on (u.id_empleado = e.id_empleado) "
                       + "inner join area a on (e.area = a.ID_Area) "
                       + "inner join puestos_trabajo pt on (e.puesto = pt.ID_Puesto) "
                       + "where (u.puesto != 'SuperUsuario' or u.id_user != '"+usuario+"') and "+tipoBusqueda+" like '%"+busqueda+"%';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[15];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<15;i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro

                table.addRow(datos);//Añadimos la fila
           }//while
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error obtener la tabla de usuarios con toda la información en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//getUsuariosExcel
    
    public DefaultTableModel getUsuarios(String usuario,int filtro,String busqueda) {

        DefaultTableModel table = new DefaultTableModel();

        try {
            String tipoBusqueda = "";
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
                    tipoBusqueda = "a.area";
                    break;    
                case 6:
                    tipoBusqueda = "p.puesto";
                    break;

            }//Buscamos el nombre de la columna con lo que vamos a buscar la coincidencia
            
            table.addColumn("Usuario");
            table.addColumn("Nombre(s)");
            table.addColumn("Apellido Paterno");
            table.addColumn("Apellido Materno");
            table.addColumn("Perfil");
            table.addColumn("Área");
            table.addColumn("Puesto");
            table.addColumn("Estatus");
            
            //Consulta de los usuarios
            String sql = "select u.id_user,e.nombres,e.apellido_p,e.apellido_m,u.puesto,a.area,p.puesto,u.estatus from user u "
                       + "inner join empleados e on (u.id_empleado = e.id_empleado) "
                       + "inner join area a on (e.area = a.ID_Area) "
                       + "inner join puestos_trabajo p on (e.puesto = p.ID_Puesto) "
                       + "where (u.puesto != 'SuperUsuario' or u.id_user != '"+usuario+"') and "+tipoBusqueda+" like '%"+busqueda+"%';";
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

    }//getUsuarios
    
    public DefaultTableModel getEmpleados(String usuario,int filtro,String busqueda) {

        DefaultTableModel table = new DefaultTableModel();
        String tipoBusqueda = "";
        try{
            switch(filtro){

                case 0:
                    tipoBusqueda = "e.nombres";
                    break;

                case 1:
                    tipoBusqueda = "e.apellido_p";
                    break;

                case 2:
                    tipoBusqueda = "e.apellido_m";
                    break;
                case 3:
                    tipoBusqueda = "a.area";
                    break;    
                case 4:
                    tipoBusqueda = "p.puesto";
                    break;

            }//Buscamos el nombre de la columna con lo que vamos a buscar la coincidencia

            table.addColumn("ID");
            table.addColumn("Nombre(s)");
            table.addColumn("Apellido Paterno");
            table.addColumn("Apellido Materno");
            table.addColumn("Area");
            table.addColumn("Puesto");
            
            //Consulta de los empleados
            String sql = "select e.id_empleado,e.nombres,e.apellido_p,e.apellido_m,a.area,p.puesto from empleados e "
                       + "inner join area a on (e.area = a.ID_Area) "
                       + "inner join puestos_trabajo p on (e.puesto = p.ID_Puesto) "
                       + "where "+tipoBusqueda+" like '%"+busqueda+"%' and e.id_empleado not in (select id_empleado from user);";
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
            filtro = 0; Nombres
            filtro = 1; Apellido P
            filtro = 2; Apellido M
            */
            
            switch(filtro){

                case 0:
                    tipoBusqueda = "nombres";
                    break;

                case 1:
                    tipoBusqueda = "apellido_p";
                    break;

                case 2:
                    tipoBusqueda = "apellido_m";
                    break;    

            }//Buscamos el nombre de la columna con lo que vamos a buscar la coincidencia
            
            String sql = "select id_empleado,nombres,apellido_p,apellido_m,area from empleados " +
                         "where "+tipoBusqueda+" like '"+busqueda+"%' and id_empleado not in (select id_empleado from user);";
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
    
    public boolean insertarEmpleado(String nombres, String apellidoP, String apellidoM, String telefono,String calle, String colonia, 
                                    String curp,String rfc,String fecha,String codigoP,String municipio,String localidad,int area,int puesto) {
        try {
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Creamos la variable para guardar el resultado de las consultas
            ResultSet rs;
            
            //Primero insertamos al empleado
            String sql = "insert into empleados (nombres,apellido_p,apellido_m,calle,colonia,telefono,codigo_postal,fecha_nacimiento,curp,rfc,municipio,localidad,area,puesto) "
                         +"values('"+nombres+"','"+apellidoP+"','"+apellidoM+"','"+calle+"','"+colonia+"','"
                         +telefono+"','"+codigoP+"','"+fecha+"','"+curp+"','"+rfc+"','"+municipio+"','"+localidad+"',"+area+","+puesto+");";
            st.executeUpdate(sql);
            
            return true;
        } catch (SQLException ex) {
            System.out.printf("Error al insertar el empleado en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//insertarEmpleado
    
    
    public boolean asignarUsuario(int id_empleado,String usuario, String pass,String puesto) {
        
        try {
            //Hacemos la conexión,
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Creamos la variable para guardar el resultado de las consultas
            ResultSet rs;
            
            //Ya se realizo la inserción y se encontro el ID de ese nuevo registro, ahora insertamos el usuario y ligamos el ID, su cargo y su área
            String sql = "insert into user values('"+usuario+"',"+id_empleado+",true,'"+pass+"','"+puesto+"','Activo');";
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
            manager_permisos.asignarPermisos_Puesto(puesto, usuario);
            //Cerramos la conexión
            conexion.close();
            return true;
        } catch (SQLException ex) {
            System.out.printf("Error al insertar el empleado en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//asignarUsuario
    
    public boolean actualizarEmpleado(int id, String nombres, String apellidoP, String apellidoM,String calle,String colonia, String telefono,String codigoP,String fecha,String curp,String rfc,String municipio,String localidad) {

        try {
            //Actualizamos el perfil del empleado
            String sql = "update empleados set nombres = '"+nombres+"',apellido_p = '"+apellidoP+"',apellido_m = '"+apellidoM
                  +"',calle = '"+calle+"',colonia = '"+colonia+"',telefono = '"+telefono+"',codigo_postal = '"+codigoP
                  +"',fecha_nacimiento = '"+fecha+"',curp = '"+curp+"',rfc = '"+rfc+"',municipio = '"+municipio+"',localidad = '"+localidad+"' "
                  + "where id_empleado = "+id+";";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            st.executeUpdate(sql);
            
            conexion.close();
            return true;
        } catch (SQLException ex) {
            System.out.printf("Error al actualizar el empleado en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//actualizarEmpleado
    
    public String obtenerDatosEmpleado(int idEmpleado) {

        try {
            //Obtenemos los datos del empleado
            String sql = "select e.nombres, e.apellido_p, e.apellido_m, a.area, e.calle, e.colonia, e.telefono, "
                       + "e.codigo_postal,e.fecha_nacimiento, e.curp, e.rfc, e.municipio, e.localidad, "
                       + "p.Puesto from empleados e "
                       + "inner join area a on (e.area = a.ID_Area) "
                       + "inner join puestos_trabajo p on (e.puesto = p.ID_Puesto) "
                       + "where id_empleado = "+idEmpleado+";";
            
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            
            //Llenamos la cadena con los datos
            String datos = rs.getString(1);
            for(int i = 2;i<15;i++){
                datos += ",,"+rs.getString(i);
            }
            
            conexion.close();
            return datos;
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los datos del empleado en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } 
        
    }//obtenerDatosEmpleado
    
    public int obtenerIdEmpleado(String usuario) {

        try {
            //Obtenemos el ID del empleado
            String sql = "select id_empleado from user where id_user = '"+usuario+"';";
            
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            
            //Guardamos el id
            int id  = rs.getInt(1);
            
            conexion.close();
            return id;
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los datos del empleado en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } 
        
    }//obtenerIdEmpleado
    
    public boolean passwordEquals(String usuario, String pass) {
        boolean coincidencia = false;
        try {
            //Actualizamos la contraseña
            String sql = "select password from user where id_user = '"+usuario+"' and password = '"+pass+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            coincidencia = rs.next();
            
            conexion.close();
            return coincidencia;
        } catch (SQLException ex) {
            System.out.printf("Error al insertar el empleado en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//changePass
    
    public boolean changePass(String usuario, String antigüa, String nueva) {

        try {
            //Actualizamos la contraseña
            String sql = "update user set password = '"+nueva+"' where id_user = '"+usuario+"' and password = '"+antigüa+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            st.executeUpdate(sql);
            
            conexion.close();
            return true;
        } catch (SQLException ex) {
            System.out.printf("Error al insertar el empleado en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//changePass
    
    public boolean estatusUsuario(String usuario,String estatus) {
        try {
            //Actualizamos el estatus del usuario
            String sql = "update user set estatus = '"+estatus+"' where id_user = '"+usuario+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            st.executeUpdate(sql);
            
            //Cerramos la conexión
            conexion.close();
            return true;
        } catch (SQLException ex) {
            System.out.printf("Error al intentar dar el estatus de "+estatus+" al usuario en SQL");
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
            String sql = "select e.nombres,e.apellido_p,e.apellido_m,e.calle,e.colonia,e.telefono,e.codigo_postal,e.fecha_nacimiento,e.curp,e.rfc,e.municipio,e.localidad,pt.puesto,a.area from empleados e "
                    + "inner join user u on (u.id_empleado = e.id_empleado) "
                    + "inner join area a on (a.ID_Area = e.area) "
                    + "inner join Puestos_Trabajo pt on (pt.ID_Puesto = e.puesto) "
                    + "where u.id_user = '"+usuario+"';";
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
