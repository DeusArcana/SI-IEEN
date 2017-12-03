/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author oscar
 */
//select usuario from inicio;
public class PreConexion {

    private Connection conexion;
    private Conexion db;

    public PreConexion() {
        db = new Conexion();
    }//constructor

    public boolean existeHost() {

        boolean estado = false;
        
        try {
            //Consulta para saber si existe o no dicho usuario
            String sql = "select usuario from inicio where usuario = 'localhost';";
            conexion = db.getPreConexion();
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
    
    
    public String getPass() {
       
        String estado = "";
        
        try {
            //Consulta para saber si existe o no dicho usuario
            String sql = "select password from inicio where usuario = '';";
            conexion = db.getPreConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            estado = rs.getString("password");//Guardamos el resultado para retornar la respuesta.
            conexion.close();
            
        } catch (SQLException ex) {
            System.out.printf("Error al consultar el usuario en SQL");
            JOptionPane.showMessageDialog(null, "Existe un servidor ya configurado!","Error - SQLDB",JOptionPane.WARNING_MESSAGE);
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            estado =  "";
        } 
            return estado;

    }//existeUsuario
    
    
    public boolean actualizarPrimeraConexion(String host, String ip, String pass, String bandera) {

        conexion = db.getConexion();

        try {
            //  insert into inicio values('localhost','192.168.1.65','sanphoenix','ACTIVO');
            String sql = "update inicio set usuario = ?, ip = ?,password =? ,bandera = ?;";

            PreparedStatement ps = conexion.prepareStatement(sql);

            ps.setString(1, host);
            ps.setString(2, ip);
            ps.setString(3, pass);
            ps.setString(4, bandera);
            ps.execute();

            conexion.close();
        } //
        catch (SQLException ex) {
            Logger.getLogger(PreConexion.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
        return true;

    }//datos de ip

}//class
