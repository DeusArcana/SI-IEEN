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
import javax.swing.JScrollPane;
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
    
    public DefaultTableModel getPermisos(boolean editar,String usuario) {
        
        JTable checks = new JTable();
        JScrollPane scroll = new JScrollPane();
        conexion = db.getConexion();
        
        DefaultTableModel table;
        
        //Creamos la tabla con las caracterisiticas que necesitamos
        checks.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        checks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            //Declaramos el titulo de las columnas
            new String [] {
                "Modulo",
                "Alta",
                "Baja",
                "Actualizar",
                "Consulta"
            }
        ){
            //El tipo que sera cada columna, la primera columna un checkbox y los demas seran objetos
            Class[] types = new Class [] { 
                java.lang.Object.class,
                java.lang.Boolean.class,
                java.lang.Boolean.class,
                java.lang.Boolean.class,
                java.lang.Boolean.class
            };

			@Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            //Esto es para indicar que columnas dejaremos editar o no
            boolean[] canEdit = new boolean [] {
                false, editar, editar, editar, editar
            };

			@Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
            
          }
        
        );
        //Agregamos un scroll a la tabla
        scroll.setViewportView(checks);
        scroll.setBounds(30, 130, 1110, 500);
        
        table = (DefaultTableModel)checks.getModel();
        
        try {
            
            //Consulta de los productos
            String sql = "select modulo,alta,baja,actualizar,consulta from permisos where id_user = '"+usuario+"';";
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
            System.out.printf("Error al obtener los permisos del usuario \""+usuario+"\"SQL");
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

    //Este método es para saber si tiene permiso (alta/baja/actualizar/consulta) 
    //en cierto modulo (Usuarios/Inventario/Empleados/Vehiculos/Solicitudes/Resguardo/Configuración/Permisos)
    public boolean accesoModulo(String permiso,String modulo, String usuario){

        boolean estado = false;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Obtenemos la respuesta de permiso para la secretaria de acuerdo al tipo de solicitud
            String sql = "select "+permiso+" from permisos where id_user = '"+usuario+"' and modulo = '"+modulo+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            if(rs.getInt(1) == 1){estado = true;}
            else{estado = false;}
            
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//accesoModulo
    
    //Método para saber puede ver la pestaña de solicitudes
    public int verTablaSolicitudes(String user){
        
        String puesto;
        conexion = db.getConexion();
        try {
            Statement st = conexion.createStatement();
            //obtenemos el puesto 
            String sql = "select puesto from user where id_user = '"+user+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            puesto = rs.getString(1);
            
            sql = "select tipo_solicitud from vista_permisosSolicitud where puesto = '"+puesto+"';";
            rs = st.executeQuery(sql);
            if(rs.next()){
                return 1;//Encontro alguna coincidencia entonces puede ver por lo menos algun tipo de solicitud
            }else{
                return 0;//No se encontro concidencia entonces no puede ver ningun tipo de solicitud
            }
            
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }//
        
    }//verTablaSolicitudes
    
    //-------------------------------------------------------------------------------------------------------------------------------------------//
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
        boolean estado = false;
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            //Obtenemos la respuesta de permiso para la secretaria de acuerdo al tipo de solicitud
            String sql = "select permiso from permiso_vale pv inner join user u on (u.puesto = pv.puesto) where u.id_user = '"+usuario+"' and tipo_vale = '"+tipo+"';";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                estado = rs.getBoolean(1);
            }
            conexion.close();
        } //try  
        
        catch (SQLException ex) {
            Logger.getLogger(ManagerPermisos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch//Catch//Catch//Catch
        
        return estado;
    }//permisoPorVale
    
}//class
