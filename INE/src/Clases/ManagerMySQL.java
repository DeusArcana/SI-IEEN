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
public class ManagerMySQL {
    ManagerPermisos manager_permisos;
    private Connection conexion;
    private Conexion db;
    
    public ManagerMySQL(){
    
        db = new Conexion();
        manager_permisos = new ManagerPermisos();
        
    }//constructor
    
    public boolean insertarUsuarioBD(String usuario,String ip) {

        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            String sql = "CREATE USER '"+usuario+"'@'"+ip+"';";
            st.executeUpdate(sql);
            
            String sql2 = "GRANT ALL PRIVILEGES ON * . * TO '"+usuario+"'@'"+ip+"';";
            st.executeUpdate(sql2);

            //conexion.close();
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(ManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch

        return true;

    }//Insertar permisos
    
    
    
    public boolean quitarUsuarioBD(String usuario,String ip) {

        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            String sql = "DROP USER 'PC70'@'"+ip+"';";
            st.executeUpdate(sql);
            
            //conexion.close();
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(ManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch

        return true;

    }//quitar permisos  select User from mysql.user where User = 'root';
    
    
    public DefaultTableModel getPermisosMySQL() {

        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("PC");
            table.addColumn("IP");
            table.addColumn("Estado");
            table.addColumn("Permiso CRUD");

            //Consulta de los empleados
            String sql = "select * from privilegios;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[4];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for (int i = 0; i < 4; i++) {
                    datos[i] = rs.getObject(i + 1);
                }//Llenamos las columnas por registro

                table.addRow(datos);//Añadimos la fila
            }//while
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error consulta permisos SQL");
            Logger.getLogger(ManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//getUsuarios
    
    
    
    public boolean insertarPrivilegios(String PC,String IP,String Estado) {

        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            String sql = "insert into privilegios values('"+PC+"','"+IP+"','"+Estado+"','"+"SI"+"');";

            st.executeUpdate(sql);

            //conexion.close();
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(ManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch

        return true; //Inserto

    }//Insertar privilegios
    
    
     public boolean borrarPrivilegios(String IP) {

        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            String sql = "delete from privilegios where ip = '"+IP+"';";

            st.executeUpdate(sql);

            //conexion.close();
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(ManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch

        return true; //Inserto

    }//Insertar privilegios
    
    public boolean duplicados(String ip){
        boolean resultado;
        
        conexion = db.getConexion();
        
        try {
            Statement st = conexion.createStatement();
            String sql = "select * from privilegios where PC = '"+ip+"';";
            ResultSet rs = st.executeQuery(sql);
            resultado = rs.next();
            System.err.println(""+ip+" "+resultado);
            //conexion.close();
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(ManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Catch

        return resultado; //Inserto
    
    }
    
}//class
