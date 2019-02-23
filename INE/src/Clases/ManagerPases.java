/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;


import Interfaces.Principal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author guillermo
 */
public class ManagerPases {
    Conexion db;
    Connection conexion;
    public static DefaultTableModel modelo;

    public ManagerPases() {
        db = new Conexion();
        modelo = new DefaultTableModel();
        //conexion = db.getConexion();
    }
    
    public DefaultTableModel getTasolpa(String year) {

        DefaultTableModel tapa = new DefaultTableModel();
        //tapa.addColumn("idSolicitud");
        tapa.addColumn("Folio");
        tapa.addColumn("Nombre");
        tapa.addColumn("Puesto");
        tapa.addColumn("Área");
        tapa.addColumn("Fecha");
        tapa.addColumn("Hora_E/S");
        tapa.addColumn("Hora_Llegada");
        tapa.addColumn("Horas");
        tapa.addColumn("Tipo_Horario");
        tapa.addColumn("Tipo_Asunto");
        tapa.addColumn("Asunto");
        tapa.addColumn("Estado");
        tapa.addColumn("Vehículo_pase");

        try {
            conexion = db.getConexion();
            //conexion = db.getConexion();
            
            String sql="";
            
            ResultSet usuario=db.getTabla("select puesto from User where id_user='"+Principal.Username+"'", conexion);
            usuario.next();
                      
                ResultSet idemp=db.getTabla("select id_empleado from User where id_user='"+Principal.Username+"'", conexion);
                idemp.next();
                
                ResultSet numarea=db.getTabla("select area from Empleados where id_empleado='"+idemp.getString("id_empleado")+"'", conexion);
                numarea.next();
                
                ResultSet area=db.getTabla("select Area from Area where ID_area='"+numarea.getString("area")+"'", conexion);
                area.next();
                    
            if(usuario.getString("puesto").equals("SuperUsuario") || usuario.getString("puesto").equals("Administrador")){
                 sql = "select concat(Folio,'-',Numero),Nombre,Puesto,Area,Fecha,Hora_ES,Hora_Llegada,Horas,Tipo_Horario,Tipo_Asunto,Asunto,Estado,Vehiculo_pase from solicitud_pase where Año = '"+ year +"' order by Folio , Numero DESC;";
                 
            }else{      
                sql = "select concat(Folio,'-',Numero),Nombre,Puesto,Area,Fecha,Hora_ES,Hora_Llegada,Horas,Tipo_Horario,Tipo_Asunto,Asunto,Estado,Vehiculo_pase from solicitud_pase where Año = '"+ year +"' AND Area = '"+ area.getString("Area") +"' order by Numero DESC;";        
                //System.out.printf(idemp.getString("id_empleado"));
                //System.out.printf(numarea.getString("area"));
                //System.out.printf(area.getString("Area"));
            }
            
            //Consulta de los empleados
            //sql = "select concat(Folio,'-',Numero),Nombre,Puesto,Area,Fecha,Hora_ES,Hora_Llegada,Horas,Tipo_Horario,Tipo_Asunto,Asunto,Estado from solicitud_pase where Año = '"+ año +"' order by Numero DESC";
            //String sql="select * from solicitud_viatico";
            Statement st = conexion.createStatement();
            Object datos[] = new Object[tapa.getColumnCount()];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<tapa.getColumnCount();i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro

                tapa.addRow(datos);//Añadimos la fila
           }//while
            st.close();
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla solicitudpase SQL");
            Logger.getLogger(ManagerSoViaticos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return tapa;
        }
        

    }//getInventarioG
    
    public void getArea(JComboBox combo) {
        try{
            String sql="";
            conexion = db.getConexion();
            ResultSet usuario=db.getTabla("select puesto from User where id_user='"+Principal.Username+"'", conexion);
            usuario.next();
                      
                ResultSet idemp=db.getTabla("select id_empleado from User where id_user='"+Principal.Username+"'", conexion);
                idemp.next();
                
                ResultSet numarea=db.getTabla("select area from Empleados where id_empleado='"+idemp.getString("id_empleado")+"'", conexion);
                numarea.next();
                
                //ResultSet area=db.getTabla("select Area from Area where ID_area='"+numarea.getString("area")+"'", conexion);
                //area.next();
                    
            if(usuario.getString("puesto").equals("SuperUsuario") || usuario.getString("puesto").equals("Administrador")){
                 sql = "select Area from Area;";
                 
            }else{      
                sql = "select Area from Area where ID_Area = '"+numarea.getString("area")+"';";        
                
            }
           
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo.addItem(rs.getObject(1).toString());
            }
            
            st.close();
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener las areas para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }//Obtiene todas las areas de trabajo
    
    public String getAreaSiglas(String siglas) {
        try{
           
            String sql = "select Siglas from Area where Area = '"+siglas+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String abre = rs.getString(1);
            
            st.close();
            conexion.close();
            return abre;
        } catch (SQLException ex) {
            System.out.printf("Error al obtener las siglas de la area SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } 
        
    }//Obtiene todas las areas de trabajo
    
    public String getIdResponsableArea(String idresarea) {
        try{
           
            String sql = "select Responsable from Area where Area = '"+idresarea+"';";
            //String sql = "select nombres from Empleados where id_empleado = '"+res+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String abre = rs.getString(1);
            
            st.close();
            conexion.close();
            return abre;
        } catch (SQLException ex) {
            System.out.printf("Error al obtener ID del responsable de la area SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } 
        
    }//Obtiene id_empleado de responsable del area
    
    public String getNomResponsableArea(String nomresarea) {
        try{
           
            String sql = "select concat(nombres,' ',apellido_p,' ',apellido_m) from Empleados where id_empleado = '"+nomresarea+"';";
            //String sql = "select nombres from Empleados where id_empleado = '"+res+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String abre = rs.getString(1);
            
            st.close();
            conexion.close();
            return abre;
        } catch (SQLException ex) {
            System.out.printf("Error al obtener Nombre del responsable de la area SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } 
        
    }//Obtiene todas las areas de trabajo
    
    public void getNombresEmpleadosArea(String area,JComboBox combo) {
        try{
            conexion = db.getConexion();
            ResultSet idarea=db.getTabla("select ID_Area from Area where Area = '"+area+"'", conexion);
            idarea.next();
           
            String sql = "select concat(nombres,' ',apellido_p,' ',apellido_m) from Empleados where area = '"+idarea.getString("ID_Area")+"' and estatus = 'Activo';";
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql); 
            while(rs.next()){
                combo.addItem(rs.getObject(1).toString());
            }
            st.close();
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los nombres de los empleados para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }//Obtiene todas los nombres de los empleados del area
    
    public void getYear(JComboBox combo) {
        try{
            String sql="";
           
           sql = "select * from acceder_year order by Year DESC;";  
           
            conexion = db.getConexion();
            Statement st = conexion.createStatement(); 
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo.addItem(rs.getObject(1).toString());
            }
            
            st.close();
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los años para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }//Obtiene todas las areas de trabajo
    
    public String obtenerPuesto(String empleado) {
        try{
           
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Creamos la variable para guardar el resultado de las consultas
            ResultSet rs;

            //Obtenemos el id del empleado para dar con su usuario
            String sql = "select pt.puesto from Empleados e inner join Puestos_Trabajo pt on (pt.ID_Puesto = e.puesto) where concat(nombres,' ',apellido_p,' ',apellido_m) = '"+empleado+"';";
            rs = st.executeQuery(sql);
            rs.next();
            String puesto = rs.getString(1);
            
            //Cerramos la conexión
            st.close();
            conexion.close();
            return puesto;
            
        } catch (SQLException ex) {
            System.out.printf("Error al obtener el nombre del puesto del empleado \""+empleado+"\" SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } 
        
    }//obtenerPuesto
    
    public void getVehiculos(JComboBox combo) {
        try{
            
            conexion = db.getConexion();
            String sql,sql2="";
            boolean si=false;
            
            ResultSet vacia=db.getTabla("select if(max(Folio) IS NULL,'Esta vacia','No esta vacia') from solicitud_pase", conexion);
            vacia.next();
            
            if(vacia.getString("if(max(Folio) IS NULL,'Esta vacia','No esta vacia')").equals("Esta vacia")){
               sql = "select Matricula,Marca from Vehiculos where Estado='Disponible' and Estatus='Activo';";
               sql2 = "select Vehiculo_pase from solicitud_pase where Vehiculo_estado='Ocupado';"; 
               si=true;
            }else{
               sql = "select Matricula,Marca from Vehiculos where Estado='Disponible' and Estatus='Activo';";
              sql2 = "select Vehiculo_pase from solicitud_pase where Vehiculo_estado='Ocupado';";  
              si=false;
            }           
           //sql="select concat(V.Matricula,' ',V.Marca) from Vehiculos V inner join solicitud_pase SP on concat(V.Marca,' ',V.Matricula) = SP.Vehiculo_pase where V.Estado='Disponible' and SP.Vehiculo_estado='Disponible'";
           
            //conexion = db.getConexion();
            Statement st = conexion.createStatement();
            List<String> vehiculosdisponibles=new ArrayList<String>();
            List<String> vehiculosocupados=new ArrayList<String>();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String todosvehiculos = rs.getString("Matricula") + " " + rs.getString("Marca");
                vehiculosdisponibles.add(todosvehiculos);
            }
            ResultSet rsv = st.executeQuery(sql2);
            
            while(rsv.next()){
                String todosvehiculosocupados = rsv.getString("Vehiculo_pase");
                vehiculosocupados.add(todosvehiculosocupados);
            }
            if(si==true){
              for(int i = 0; i < vehiculosdisponibles.size(); i++){
                combo.addItem(vehiculosdisponibles.get(i)); } 
            }else{
                boolean vd=true;
                for(int i = 0; i < vehiculosdisponibles.size(); i++){
                    vd=true;
                for(int j = 0; j < vehiculosocupados.size(); j++ ){
                    if(vehiculosdisponibles.get(i).equals(vehiculosocupados.get(j))){
                           vd=false;
                           break;
                           
                    }
                }
                if(vd){
                    combo.addItem(vehiculosdisponibles.get(i));
                }
            }
            }                        
            
            st.close();
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los vehículos disponibles para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }//Obtiene todas las areas de trabajo
    
    
}
