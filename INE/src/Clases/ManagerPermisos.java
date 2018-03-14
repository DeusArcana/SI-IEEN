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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kevin
 */
public class ManagerPermisos {

    private Connection conexion;
    private Conexion db;
    
    public ManagerPermisos(){
        db = new Conexion();
    }//Constructor
    
    public DefaultTableModel getPermisos(JTable tabla,String usuario) {
        
        DefaultTableModel table = new DefaultTableModel();
        table = (DefaultTableModel) tabla.getModel();
        
        try {
            
            //Consulta de los productos
            String sql = "select modulo,alta,baja,actualizar,consulta from permisos where id_user = '"+usuario+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[5];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {
                
                datos[0] = rs.getObject(1);
                
                for(int i = 1;i<5;i++){
                    
                    if(rs.getBoolean(i+1)){
                        datos[i] = Boolean.TRUE;
                    }else{
                        datos[i] = Boolean.FALSE;
                    }
                    
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

    }//getPermisos --> Obtiene todos los permisos que tiene el usuario
    
    public DefaultTableModel getPermisos_Puesto(JTable tabla,String puesto) {
        
        DefaultTableModel table = new DefaultTableModel();
        table = (DefaultTableModel) tabla.getModel();
        
        try {
            
            //Consulta de los productos
            String sql = "select modulo,alta,baja,actualizar,consulta from permisos_puesto where puesto = '"+puesto+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[5];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {
                
                datos[0] = rs.getObject(1);
                
                for(int i = 1;i<5;i++){
                    
                    if(rs.getBoolean(i+1)){
                        datos[i] = Boolean.TRUE;
                    }else{
                        datos[i] = Boolean.FALSE;
                    }
                    
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

    }//getPermisos_Puesto --> Obtiene todos los permisos que tiene el puesto
    
    public boolean actualizarPermisos(String usuario,String modulo, boolean alta, boolean baja, boolean actualizar, boolean consulta) {

        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            String sql = "update permisos set alta = "+alta+", baja = "+baja+",actualizar = "+actualizar+",consulta = "+consulta
                         +" where id_user = '"+usuario+"' and modulo = '"+modulo+"';";

            st.executeUpdate(sql);
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch

        return true; //Inserto correctamente

    }//actualizar permisos de acuerdo al usuario
    
    public boolean actualizarPermisos_Puesto(String puesto,String modulo, boolean alta, boolean baja, boolean actualizar, boolean consulta) {

        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            String sql = "update permisos_puesto set alta = "+alta+", baja = "+baja+",actualizar = "+actualizar+",consulta = "+consulta
                         +" where puesto = '"+puesto+"' and modulo = '"+modulo+"';";

            st.executeUpdate(sql);
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch

        return true; //Inserto correctamente

    }//actualizar permisos estaticos de acuerdo al puesto

    public boolean asignarPermisos_Puesto(String puesto,String usuario) {

        conexion = db.getConexion();
        
        try {
            ResultSet rs;
            Statement st = conexion.createStatement();
            //Primero obtenemos la cantidad de modulos para darle el tamaño a los arreglos
            String sql = "select count(*) from modulos;";
            rs = st.executeQuery(sql);
            rs.next();
            int tamaño = rs.getInt(1);
            
            String [] modulo = new String[tamaño];
            boolean [] alta = new boolean[tamaño];
            boolean [] baja = new boolean[tamaño];
            boolean [] actualizar = new boolean[tamaño];
            boolean [] consulta = new boolean[tamaño];
            
            //Una vez que tenemos el tamaño, entonces llenamos el arreglo con los modulos
            sql = "select * from modulos;";
            rs = st.executeQuery(sql);
            rs.next();
            for(int i = 0; i<tamaño;i++){
                modulo[i] = rs.getString(1);
                rs.next();
            }//for
            
            //Llenamos los arreglos con su permiso correspondiente
            for(int i = 0; i<tamaño; i++){
                sql = "select alta,baja,actualizar,consulta from permisos_puesto where modulo = '"+modulo[i]+"' and puesto = '"+puesto+"';";
                rs = st.executeQuery(sql);
                rs.next();
                alta[i] = rs.getBoolean(1);
                baja[i] = rs.getBoolean(2);
                actualizar[i] = rs.getBoolean(3);
                consulta[i] = rs.getBoolean(4);
            }//for
            
            //Ahora se le daran los permisos de acuerdo al puesto, solo si no los tiene se van a actualizar
            for(int i = 0; i<tamaño;i++){
                if(alta[i]){
                    sql = "update permisos set alta = true where alta = false and modulo = '"+modulo[i]+"' and id_user = '"+usuario+"';";
                    st.executeUpdate(sql);
                }
                if(baja[i]){
                    sql = "update permisos set baja = true where baja = false and modulo = '"+modulo[i]+"' and id_user = '"+usuario+"';";
                    st.executeUpdate(sql);
                }
                if(actualizar[i]){
                    sql = "update permisos set actualizar = true where actualizar = false and modulo = '"+modulo[i]+"' and id_user = '"+usuario+"';";
                    st.executeUpdate(sql);
                }
                if(consulta[i]){
                    sql = "update permisos set consulta = true where consulta = false and modulo = '"+modulo[i]+"' and id_user = '"+usuario+"';";
                    st.executeUpdate(sql);
                }
            }//for
            
            //Cerramos la conexión
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch

        return true; //Inserto correctamente

    }//actualizar permisos estaticos de acuerdo al puesto

//---------------------------------PUESTOS QUE TIENEN PERMISO PARA REVISAR SOLICITUDES BAJA O COMODATO O DONACIÓN O REEMPLAZO---------------------------------------//
    
    public boolean permisosSolicitud(String tipo, boolean usuario, boolean auxiliar, boolean jefe, boolean administracion, boolean organizacion){
    
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Realizamos los nuevos cambios para los permisos de atender la solicitudes de baja o comodato o donación
            String sql = "update permisos_solicitud set permiso = "+usuario+" where puesto = 'Usuario Depto.' and tipo_solicitud = '"+tipo+"';";
            st.executeUpdate(sql);
            System.out.println("Se actualizo la secretaria");
            
            sql = "update permisos_solicitud set permiso = "+auxiliar+" where puesto = 'Auxiliar' and tipo_solicitud = '"+tipo+"';";
            st.executeUpdate(sql);
            System.out.println("Se actualizo el auxiliar");
            
            sql = "update permisos_solicitud set permiso = "+administracion+" where puesto = 'Administración' and tipo_solicitud = '"+tipo+"';";
            st.executeUpdate(sql);
            System.out.println("Se actualizo el supervisor");
            
            sql = "update permisos_solicitud set permiso = "+jefe+" where puesto = 'Jefe de departamento' and tipo_solicitud = '"+tipo+"';";
            st.executeUpdate(sql);
            System.out.println("Se actualizo el jefe");
            
            sql = "update permisos_solicitud set permiso = "+organizacion+" where puesto = 'Organización' and tipo_solicitud = '"+tipo+"';";
            st.executeUpdate(sql);
            System.out.println("Se actualizo la organización");
            
            conexion.close();
            return true;
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
    }//permisosSolicitud
    
    public boolean usuario_Solicitud(String tipo){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Obtenemos la respuesta de permiso para la secretaria de acuerdo al tipo de solicitud
            String sql = "select permiso from permisos_solicitud where tipo_solicitud = '"+tipo+"' and puesto = 'Usuario Depto.';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            estado = rs.getBoolean(1);
            
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//secretaria_solicitud
    
    public boolean auxiliar_Solicitud(String tipo){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Obtenemos la respuesta de permiso para la secretaria de acuerdo al tipo de solicitud
            String sql = "select permiso from permisos_solicitud where tipo_solicitud = '"+tipo+"' and puesto = 'Auxiliar';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            estado = rs.getBoolean(1);
            
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//auxiliar_solicitud
    
    public boolean administracion_Solicitud(String tipo){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Obtenemos la respuesta de permiso para la secretaria de acuerdo al tipo de solicitud
            String sql = "select permiso from permisos_solicitud where tipo_solicitud = '"+tipo+"' and puesto = 'Administración';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            estado = rs.getBoolean(1);
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//supevisor_solicitud
    
    public boolean jefe_Solicitud(String tipo){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Obtenemos la respuesta de permiso para la secretaria de acuerdo al tipo de solicitud
            String sql = "select permiso from permisos_solicitud where tipo_solicitud = '"+tipo+"' and puesto = 'Jefe de departamento';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            estado = rs.getBoolean(1);
            
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//jefe_solicitud
    
    public boolean organizacion_Solicitud(String tipo){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Obtenemos la respuesta de permiso para la secretaria de acuerdo al tipo de solicitud
            String sql = "select permiso from permisos_solicitud where tipo_solicitud = '"+tipo+"' and puesto = 'Organización';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            estado = rs.getBoolean(1);
            
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//organizacion_solicitud
    
    //---------------------------------------------------------------------------------------------------------------------//   
    public int verTablaSolicitudes(String user){
        boolean baja = false,comodato = false,donacion = false,reemplazo = false;
        String puesto;
        conexion = db.getConexion();
        try {
            Statement st = conexion.createStatement();
            //obtenemos el puesto 
            String sql = "select puesto from user where id_user = '"+user+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            puesto = rs.getString(1);
            
            //Obtenemos la respuesta de permiso para el puesto de la persona que se logeo de la solicitud baja
            sql = "select permiso from permisos_solicitud where tipo_solicitud = 'Solicitud Baja' and puesto = '"+puesto+"';";
            rs = st.executeQuery(sql);
            if(rs.next()){
                baja = rs.getBoolean(1);
            }
            //Obtenemos la respuesta de permiso para el puesto de la persona que se logeo de la solicitud comodato
            sql = "select permiso from permisos_solicitud where tipo_solicitud = 'Solicitud Comodato' and puesto = '"+puesto+"';";
            rs = st.executeQuery(sql);
            if(rs.next()){
                comodato = rs.getBoolean(1);
            }
            //Obtenemos la respuesta de permiso para el puesto de la persona que se logeo de la solicitud donación
            sql = "select permiso from permisos_solicitud where tipo_solicitud = 'Solicitud Donación' and puesto = '"+puesto+"';";
            rs = st.executeQuery(sql);
            if(rs.next()){
                donacion = rs.getBoolean(1);
            }
            //Obtenemos la respuesta de permiso para el puesto de la persona que se logeo de la solicitud reemplazo
            sql = "select permiso from permisos_solicitud where tipo_solicitud = 'Solicitud Reemplazo' and puesto = '"+puesto+"';";
            rs = st.executeQuery(sql);
            if(rs.next()){
                reemplazo = rs.getBoolean(1);
            }
            rs.close();
            st.close();
            conexion.close();
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
            
//------------------PERMISO PARA LOS 4 TIPOS DE SOLICITUDES------------------------//
            if(baja == donacion && baja == comodato && baja == reemplazo && baja){
                return 14; //Tiene permiso para ver todos los tipos de solicitud
            }
            
//------------------PERMISO PARA 3 TIPOS DE SOLICITUDES------------------------//
            if(baja == donacion && baja == comodato && baja){
                return 13; //Tiene permiso para ver baja,donacion y comodato
            }
            if(baja == donacion && baja == reemplazo && baja){
                return 12; //Tiene permiso para ver baja, donacion y reemplazo
            }
            if(baja == comodato && baja == reemplazo && baja){
                return 11; //Tiene permiso para ver baja, comodato y reemplazo
            }
            
//------------------PERIMSO PARA 2 TIPOS DE SOLICITUDES------------------------//
            if(baja == donacion && baja){
                return 10; //Tiene permiso para ver baja y donacion
            }
            if(baja == comodato && baja){
                return 9; //Tiene permiso para ver baja y comodato
            }
            if(baja == reemplazo && baja){
                return 8; //Tiene permiso para ver baja y reemplazo
            }
            if(reemplazo == comodato && baja){
                return 7; //Tiene permiso para ver reemplazo y comodato
            }
            if(reemplazo == donacion && baja){
                return 6; //Tiene permiso para ver reemplazo y donacion
            }
            if(comodato == donacion && comodato){
                return 5;//Tiene permiso para ver comodato y donacion
            }

//------------------PERIMSO PARA 1 TIPO DE SOLICITUDES------------------------// 
            if(baja){
                return 4;
            }
            if(comodato){
                return 3;
            }
            if(donacion){
                return 2;
            }
            if(reemplazo){
                return 1;
            }
            
//------------------SIN PERMISOS PARA LAS SOLICITUDES------------------------//            
            return 0;
            
            
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }//Catch//Catch//Catch//Catch
        
    }//verTablaSolicitudes
    
    //---------------------------------PERMISOS EN EL MODULO DE USUARIOS DE ACUERDO AL USUARIO LOGEADO---------------------------------//
    
    public boolean alta_user(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select alta from permisos where id_user = '"+usuario+"' and modulo = 'Usuarios';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//alta_user
    
    public boolean baja_user(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select baja from permisos where id_user = '"+usuario+"' and modulo = 'Usuarios';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//baja_user
    
    public boolean update_user(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select actualizar from permisos where id_user = '"+usuario+"' and modulo = 'Usuarios';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//update_user
    
    public boolean consulta_user(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select consulta from permisos where id_user = '"+usuario+"' and modulo = 'Usuarios';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//consulta_user
    
 //---------------------------------------------------------------------------------------------------------------------//   

    //---------------------------------PERMISOS EN EL MODULO DE PERMISOS DE ACUERDO AL USUARIO LOGEADO---------------------------------//
    
    public boolean alta_permisos(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select alta from permisos where id_user = '"+usuario+"' and modulo = 'Permisos';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//alta_permisos
    
    public boolean baja_permisos(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select baja from permisos where id_user = '"+usuario+"' and modulo = 'Permisos';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//baja_user
    
    public boolean update_permisos(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select actualizar from permisos where id_user = '"+usuario+"' and modulo = 'Permisos';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//update_user
    
    public boolean consulta_permisos(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select consulta from permisos where id_user = '"+usuario+"' and modulo = 'Permisos';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//consulta_user
    
 //---------------------------------------------------------------------------------------------------------------------//   

    //---------------------------------PERMISOS EN EL MODULO DE INVENTARIO DE ACUERDO AL USUARIO LOGEADO---------------------------------//
    
    public boolean alta_inventario(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select alta from permisos where id_user = '"+usuario+"' and modulo = 'Inventario';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//alta_permisos
    
    public boolean baja_inventario(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select baja from permisos where id_user = '"+usuario+"' and modulo = 'Inventario';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//baja_user
    
    public boolean update_inventario(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select actualizar from permisos where id_user = '"+usuario+"' and modulo = 'Inventario';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//update_user
    
    public boolean consulta_inventario(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select consulta from permisos where id_user = '"+usuario+"' and modulo = 'Inventario';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//consulta_user
    
 //---------------------------------------------------------------------------------------------------------------------//   
    //---------------------------------PERMISOS EN EL MODULO DE ASIGNACIÓN DE ACUERDO AL USUARIO LOGEADO---------------------------------//
    public boolean alta_asignacion(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select alta from permisos where id_user = '"+usuario+"' and modulo = 'Asignación';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//alta_asignacion
    
    public boolean baja_asignacion(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select baja from permisos where id_user = '"+usuario+"' and modulo = 'Asignación';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//baja_asignacion
    
    public boolean update_asignacion(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select actualizar from permisos where id_user = '"+usuario+"' and modulo = 'Asignación';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//update_asignacion
    
    public boolean consulta_asignacion(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select consulta from permisos where id_user = '"+usuario+"' and modulo = 'Asignación';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//consulta_asignacion
 //---------------------------------------------------------------------------------------------------------------------//   

    //---------------------------------PERMISOS EN EL MODULO DE VEHICULOS DE ACUERDO AL USUARIO LOGEADO---------------------------------//
    public boolean alta_vehiculos(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select alta from permisos where id_user = '"+usuario+"' and modulo = 'Vehiculos';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//alta_vehiculos
    
    public boolean baja_vehiculos(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select baja from permisos where id_user = '"+usuario+"' and modulo = 'Vehiculos';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//baja_vehiculos
    
    public boolean update_vehiculos(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select actualizar from permisos where id_user = '"+usuario+"' and modulo = 'Vehiculos';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//update_asignacion
    
    public boolean consulta_vehiculos(String usuario){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            
            //obtenemos el puesto
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
            estado = true;
            }else{
                //Obtenemos la respuesta de permiso para la alta de usuarios
                sql = "select consulta from permisos where id_user = '"+usuario+"' and modulo = 'Vehiculos';";
                rs = st.executeQuery(sql);
                rs.next();
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//consulta_asignacion
 //---------------------------------------------------------------------------------------------------------------------//   

    public boolean esSuperUsuario(String usuario){
        String puesto;
        boolean estado = false;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Obtenemos el puesto del usuario
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            puesto = rs.getString(1);
            
            if(puesto.equals("SuperUsuario")){
               estado = true;
            }
            
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//esSuperUsuario
    
    public boolean esPresidencia(String usuario){
        String puesto;
        boolean estado = false;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Obtenemos el puesto del usuario
            String sql = "select puesto from user where id_user = '"+usuario+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            puesto = rs.getString(1);
            
            if(puesto.equals("Presidencia")){
               estado = true;
            }
            
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//esPresidencia
    
    public boolean permisosVale(String tipo, boolean usuario, boolean auxiliar, boolean jefe, boolean administracion, boolean organizacion){
    
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Realizamos los nuevos cambios para los permisos de atender la solicitudes de baja o comodato o donación
            String sql = "update permiso_vale set permiso = "+usuario+" where puesto = 'Usuario Depto.' and tipo_vale = '"+tipo+"';";
            st.executeUpdate(sql);
            System.out.println("Se actualizo la secretaria");
            
            sql = "update permiso_vale set permiso = "+auxiliar+" where puesto = 'Auxiliar' and tipo_vale = '"+tipo+"';";
            st.executeUpdate(sql);
            System.out.println("Se actualizo el auxiliar");
            
            sql = "update permiso_vale set permiso = "+administracion+" where puesto = 'Administración' and tipo_vale = '"+tipo+"';";
            st.executeUpdate(sql);
            System.out.println("Se actualizo el supervisor");
            
            sql = "update permiso_vale set permiso = "+jefe+" where puesto = 'Jefe de departamento' and tipo_vale = '"+tipo+"';";
            st.executeUpdate(sql);
            System.out.println("Se actualizo el jefe");
            
            sql = "update permiso_vale set permiso = "+organizacion+" where puesto = 'Organización' and tipo_vale = '"+tipo+"';";
            st.executeUpdate(sql);
            System.out.println("Se actualizo la organización");
            
            conexion.close();
            return true;
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
    }//permisosSolicitud
    
    public boolean usuario_Vale(String tipo){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Obtenemos la respuesta de permiso para la secretaria de acuerdo al tipo de solicitud
            String sql = "select permiso from permiso_vale where tipo_vale = '"+tipo+"' and puesto = 'Usuario Depto.';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            estado = rs.getBoolean(1);
            
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//secretaria_solicitud
    
    public boolean auxiliar_Vale(String tipo){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Obtenemos la respuesta de permiso para la secretaria de acuerdo al tipo de solicitud
            String sql = "select permiso from permiso_vale where tipo_vale = '"+tipo+"' and puesto = 'Auxiliar';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            estado = rs.getBoolean(1);
            
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//auxiliar_solicitud
    
    public boolean administracion_Vale(String tipo){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Obtenemos la respuesta de permiso para la secretaria de acuerdo al tipo de solicitud
            String sql = "select permiso from permiso_vale where tipo_vale = '"+tipo+"' and puesto = 'Administración';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            estado = rs.getBoolean(1);
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//supevisor_solicitud
    
    public boolean jefe_Vale(String tipo){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Obtenemos la respuesta de permiso para la secretaria de acuerdo al tipo de solicitud
            String sql = "select permiso from permiso_vale where tipo_vale = '"+tipo+"' and puesto = 'Jefe de departamento';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            estado = rs.getBoolean(1);
            
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//jefe_solicitud
    
    public boolean organizacion_Vale(String tipo){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Obtenemos la respuesta de permiso para la secretaria de acuerdo al tipo de solicitud
            String sql = "select permiso from permiso_vale where tipo_vale = '"+tipo+"' and puesto = 'Organización';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            estado = rs.getBoolean(1);
            
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//organizacion_solicitud
    
    public boolean permisoPorVale(String usuario,String tipo){
        boolean estado;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Obtenemos la respuesta de permiso para la secretaria de acuerdo al tipo de solicitud
            String sql = "select permiso from permiso_vale pv inner join user u on (u.puesto = pv.puesto) where u.id_user = '"+usuario+"' and tipo_vale = '"+tipo+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            estado = rs.getBoolean(1);
            
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//permisoPorVale
    
}//class
