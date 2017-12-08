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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author usuario
 */
public class ManagerSoViaticos {
    Conexion cbd=new Conexion();
    Connection cn=cbd.getConexion();
    public static DefaultTableModel modelo;
    
    public ManagerSoViaticos(){
        modelo = new DefaultTableModel();
        
    }//Constructor

       public DefaultTableModel getTasol() {

        DefaultTableModel taso = new DefaultTableModel();
        taso.addColumn("idSolicitud");
        taso.addColumn("Fecha_Salida");
        taso.addColumn("Lugar");
        taso.addColumn("Nombre");
        taso.addColumn("Actividad");
        taso.addColumn("Pernoctado");
        taso.addColumn("Vehiculo");
        taso.addColumn("Puesto");
        taso.addColumn("Fecha_llegada");
        taso.addColumn("Estado");

        try {
            
            //Consulta de los empleados
            String sql = "select idSolicitud,Fecha_salida,Lugar,Nombre,Actividad,Pernoctado,Vehiculo,Puesto,Fecha_llegada,Estado from solicitud_viatico";
            //String sql="select * from solicitud_viatico";
            Statement st = cn.createStatement();
            Object datos[] = new Object[10];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<10;i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro

                taso.addRow(datos);//Añadimos la fila
           }//while
            //cn.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla solicitudviatico SQL");
            Logger.getLogger(ManagerSoViaticos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return taso;
        }

    }//getInventarioG
      
      
      /* public boolean insertarSolicitud(String ID,String fechasali, String lugar, String nombre, String actividad,String pernoctado,String vehiculo, String puesto,String fechallega) {
        try {
            //Hacemos la conexión
            //conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = cn.createStatement();
            //Creamos la variable para guardar el resultado de las consultas
            ResultSet rs;
            System.out.print("insert into solicitud_viatico (idSolicitud,Fecha_Salida,Lugar,Nombre,Actividad,Pernoctado,Vehiculo,Puesto,Fecha_Llegada,Estado) values('"+ID+"','"+fechasali+"','"+lugar+"'"
                + ",'"+nombre+"','"+actividad+"','"+pernoctado+"','"+vehiculo+"'"
                + ",'"+puesto+"','"+fechallega+"','No autorizada')");
            String sql="insert into solicitud_viatico (idSolicitud,Fecha_Salida,Lugar,Nombre,Actividad,Pernoctado,Vehiculo,Puesto,Fecha_Llegada,Estado) values('"+ID+"','"+fechasali+"','"+lugar+"'"
                + ",'"+nombre+"','"+actividad+"','"+pernoctado+"','"+vehiculo+"'"
                + ",'"+puesto+"','"+fechallega+"','No autorizada')";
            st.executeUpdate(sql);
            //Cerramos la conexión
            //cn.close();
            return true;
            
        } catch (SQLException ex) {
            System.out.printf("Error al insertar la solicitud en SQL");
            Logger.getLogger(ManagerSoViaticos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//insertarInventario
       */
       
       public DefaultTableModel SolicitudA() {
        modelo=new DefaultTableModel();
        modelo.addColumn("Folio");
        modelo.addColumn("Monto");
        modelo.addColumn("Fecha de salida");
        modelo.addColumn("Fecha de llegada");
        modelo.addColumn("Lugar");
        try {
            //conexion = db.getConexion();
            String sql="SELECT O.Folio, O.Monto, S.Fecha_salida, S.Fecha_llegada,S.Lugar FROM solicitud_viatico S, Oficio_comision O WHERE S.Estado = 'A' AND S.idSolicitud = O.Solicitud_idSolicitud";
            Statement sentencia = cn.createStatement();
            Object datos[] = new Object[5];
            ResultSet rs = sentencia.executeQuery(sql);
            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<5;i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro

                modelo.addRow(datos);//Añadimos la fila
           }//while
            //cn.close();

        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta de la tabla Aceptados");

        }finally {

            return modelo;
        }
    }
       
       public DefaultTableModel SolicitudC() {
        modelo=new DefaultTableModel();
        //modelo2 = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Puesto");
        modelo.addColumn("Fecha de salida");
        modelo.addColumn("Fecha de llegada");
        modelo.addColumn("Lugar");
        
        try {
            //conexion = db.getConexion();
            Statement sentencia = cn.createStatement();

            ResultSet rs = sentencia.executeQuery("SELECT idSolicitud, Nombre, Puesto, Fecha_salida, Fecha_llegada,Lugar FROM solicitud_viatico WHERE Estado = 'C'");

            Object datos[] = new Object[6];
            while (rs.next()) {

                for(int i = 0;i<6;i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro

                modelo.addRow(datos);//Añadimos la fila
           }//while
           //cn.close();
        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error en la consulta");

        } finally {

            return modelo;
        }
    }
       
       public DefaultTableModel SolicitudP() {
         modelo=new DefaultTableModel();
        //modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Puesto");
        modelo.addColumn("Fecha de salida");
        modelo.addColumn("Fecha de llegada");
        modelo.addColumn("Lugar");

        try {
            String sql="SELECT idSolicitud, Nombre, Puesto, Fecha_salida, Fecha_llegada,Lugar FROM solicitud_viatico WHERE Estado = 'P'";
            //conexion = db.getConexion();
            Statement sentencia = cn.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            Object datos[] = new Object[6];
             //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<6;i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro
                modelo.addRow(datos);//Añadimos la fila
           }//while
           //cn.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla solicitudviatico SQL");
            Logger.getLogger(ManagerSoViaticos.class.getName()).log(Level.SEVERE, null, ex);
        }finally {

            return modelo;
        } 
    }
       
    
}
