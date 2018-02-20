/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author oscar
 */


import static com.alee.managers.notification.NotificationIcon.image;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ManagerVehiculos {

    private Connection con;
    private Conexion db;
    
    public ManagerVehiculos(){
        con = null;
        db = new Conexion();
    }//Constructor Conexion
    

    public boolean guardarImagen(String marca, String linea, String clase, String color, String modelo, String motor,
            String kilomentraje, String matricula, String observaciones, String ruta) {
        con = db.getConexion();
        String insert = "insert into vehiculos(marca, linea, clase, color, modelo, motor,kilometraje ,matricula,observaciones,imagen) values(?,?,?,?,?,?,?,?,?,?);";
        FileInputStream fi = null;
        PreparedStatement ps = null;

        try {
            File file = new File(ruta);
            fi = new FileInputStream(file);

            ps = con.prepareStatement(insert);

            ps.setString(1, marca);
            ps.setString(2, linea);
            ps.setString(3, clase);
            ps.setString(4, color);
            ps.setString(5, modelo);
            ps.setString(6, motor);
            ps.setString(7, kilomentraje);
            ps.setString(8, matricula);
            ps.setString(9, observaciones);
            ps.setBinaryStream(10, fi);

            ps.executeUpdate();

            return true;

        } catch (Exception ex) {
            System.out.println("Error al guardar Imagen " + ex.getMessage());
            return false;

        }

    }//guardarImagen
    
    public Blob leerImagen(String matricula) throws IOException {
        con = db.getConexion();
        String sSql = "select imagen from vehiculos where matricula = '"+matricula+"';";
        PreparedStatement pst;
        Blob blob = null;
        try {
            pst = con.prepareStatement(sSql);
            ResultSet res = pst.executeQuery();
            if (res.next()) {

                blob = res.getBlob("imagen");

            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerVehiculos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blob;
    }//leerImagen
    
    
    public DefaultTableModel getVehiculos() {

        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Marca");
            table.addColumn("Linea");
            table.addColumn("Año");
            table.addColumn("Color");
            table.addColumn("Matricula");
            
            
            //Consulta de los empleados
            String sql = "select marca,linea,modelo,color,matricula from vehiculos;";
            con = db.getConexion();
            Statement st = con.createStatement();
            Object datos[] = new Object[5];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<5;i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro

                table.addRow(datos);//Añadimos la fila
           }//while
            //La conexion no se debe de cerrar para no perder los parametros de puerto e ip
           // con.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//getEmpleados
    
    
    public Vector infoVehiculos(String matricula) {
        con = db.getConexion();
        Vector v = new Vector();
        try {

            Statement st = con.createStatement();
            String sql = "select marca,linea,clase,kilometraje,modelo,color,motor,matricula,observaciones,estado from vehiculos where matricula = '"+matricula+"';";
            ResultSet resultados = st.executeQuery(sql);
            while (resultados.next()) {
                String temp = "";
                temp += "" + resultados.getString("marca") + "," + resultados.getString("linea") + "," + resultados.getString("clase")
                         + "," + resultados.getString("kilometraje")+ "," + resultados.getString("modelo")+ "," + resultados.getString("color")
                        + "," + resultados.getString("motor") + "," + resultados.getString("matricula")+ "," + resultados.getString("observaciones")
                        + "," + resultados.getString("estado");
                v.add(temp);
            }

            con.close();

        } //para el ticket
        catch (SQLException ex) {
            Logger.getLogger(ManagerVehiculos.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error Vehiculo infoVehiculos");
        }

        return v;
    }//infoVehiculos
    
        public boolean existeVehiculo(String tipoBusqueda, String busqueda){
        boolean estado = false;
        try{
            
            String sql;
            //Consulta de los vehiculos
            if(tipoBusqueda.equals("Año")){
                sql = "select marca,linea,modelo,color,matricula from vehiculos where modelo like '"+busqueda+"%';";
            }else{
                sql = "select marca,linea,modelo,color,matricula from vehiculos where "+tipoBusqueda+" like '"+busqueda+"%';";
            }
            con = db.getConexion();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            estado = rs.next();
                
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(ManagerInventario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return estado; //Retorna el resultado, si se encontro o no
        
    }//Buscar si existe el vehiculo

    public DefaultTableModel getVehiculosEspecificos(String tipoBusqueda, String busqueda) {

        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Marca");
            table.addColumn("Linea");
            table.addColumn("Año");
            table.addColumn("Color");
            table.addColumn("Matricula");
            
            String sql;
            //Consulta de los vehiculos
            if(tipoBusqueda.equals("Año")){
                sql = "select marca,linea,modelo,color,matricula from vehiculos where modelo like '"+busqueda+"%';";
            }else{
                sql = "select marca,linea,modelo,color,matricula from vehiculos where "+tipoBusqueda+" like '"+busqueda+"%';";
            }
            con = db.getConexion();
            Statement st = con.createStatement();
            Object datos[] = new Object[5];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<5;i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro

                table.addRow(datos);//Añadimos la fila
           }//while
            //La conexion no se debe de cerrar para no perder los parametros de puerto e ip
           // con.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//getEmpleados   
    
    public boolean actualizarVehiculo(String marca, String linea, String clase, String color, String modelo, String motor,
            String kilomentraje, String matricula, String observaciones, String ruta) {
        con = db.getConexion();
        
        String update = "update vehiculos set marca = ?, linea = ?,clase = ?,color = ?,modelo = ?,motor = ?,kilometraje = ?,observaciones = ?,imagen = ? where matricula = '"+matricula+"'";
        FileInputStream fi = null;
        PreparedStatement ps = null;

        try {
            File file = new File(ruta);
            fi = new FileInputStream(file);

             ps = con.prepareStatement(update);

            ps.setString(1, marca);
            ps.setString(2, linea);
            ps.setString(3, clase);
            ps.setString(4, color);
            ps.setString(5, modelo);
            ps.setString(6, motor);
            ps.setString(7, kilomentraje);
            ps.setString(8, observaciones);
            ps.setBinaryStream(9, fi);

            ps.executeUpdate();

            return true;

        } catch (Exception ex) {
            System.out.println("Error al actualizar imagen " + ex.getMessage());
            return false;

        }

    }//guardarImagen
    
    public boolean actualizarVehiculoSinFoto(String marca, String linea, String clase, String color, String modelo, String motor,
            String kilomentraje, String matricula, String observaciones) {
        con = db.getConexion();
        
        String update = "update vehiculos set marca = ?, linea = ?,clase = ?,color = ?,modelo = ?,motor = ?,kilometraje = ?,observaciones = ? where matricula = '"+matricula+"'";
        
        PreparedStatement ps = null;

        try {
            
           

             ps = con.prepareStatement(update);

            ps.setString(1, marca);
            ps.setString(2, linea);
            ps.setString(3, clase);
            ps.setString(4, color);
            ps.setString(5, modelo);
            ps.setString(6, motor);
            ps.setString(7, kilomentraje);
            ps.setString(8, observaciones);
            

            ps.executeUpdate();

            return true;

        } catch (Exception ex) {
            System.out.println("Error al actualizar imagen " + ex.getMessage());
            return false;

        }

    }//guardarImagen
        
    public void getVehiculosDisponibles(JComboBox combo) {
        try{
           
            String sql = "select concat(linea,'-'matricula) from Vehiculos;";
            con = db.getConexion();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo.addItem(rs.getObject(1).toString());
            }
            
            con.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los vehiculos disponibles para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }//Obtiene todas los nombres de los empleados
    
}//class
      


      


