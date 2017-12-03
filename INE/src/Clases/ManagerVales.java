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
 * @author oscar
 */
public class ManagerVales {
    
    private Connection conexion;
    private Conexion db;
    
    public ManagerVales(){
    
        db = new Conexion();
        
    }//constructor
    
    public DefaultTableModel getEmpleados() {

        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Nombres");
            table.addColumn("Paterno");
            table.addColumn("Materno");
            table.addColumn("Puesto");
            table.addColumn("Área");
            table.addColumn("Municipio");
            table.addColumn("Localidad");
            
            //Consulta de los empleados
            String sql = "SELECT e.nombres,e.apellido_p, e.apellido_m,u.puesto, u.area,e.municipio,e.localidad" +
                        " FROM user u " +
                        " INNER JOIN empleados e " +
                        " ON u.id_empleado = e.id_empleado" +
                        " INNER JOIN puestos p" +
                        " ON u.puesto = p.puesto" +
                        " INNER JOIN area a" +
                        " ON u.area= a.area;";
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

    }//getEmpleados
    
    
    
    
}//Class
