/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import static Interfaces.Principal.Username;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @authors oscar & kevin
 */
public class Conexion {
    public String DIRECCIONIP = "";
    public static String IP = "";
    private static String usuario = "PC70";
    private static String contra = "";
    private Connection con;

    public Conexion() {
        con = null;
    }//Constructor Conexion

    public String leer() {
        String texto = "";
        try {
            //Creamos un archivo FileReader que obtiene lo que tenga el archivo
            FileReader lector = new FileReader("cnfg.ntw");

            //El contenido de lector se guarda en un BufferedReader
            BufferedReader contenido = new BufferedReader(lector);

            IP = contenido.readLine();
            
            if (IP.equals(InetAddress.getLocalHost().getHostAddress())) {
                DIRECCIONIP = "localhost";
                usuario = "root";
                contra = "123456";
                //  System.out.println("DATOS "+DIRECCIONIP+" "+usuario+" "+contra);
            } else {
                DIRECCIONIP = IP;

                usuario = "PC70";
                contra = "";

                // System.out.println("DATOS "+DIRECCIONIP+" "+usuario+" "+contra);
            }

        } catch (Exception e) {

        }
        return texto;
    }//leer()

    public Connection getConexion() {
        leer();

        try {
            System.err.println("IP A CONECTARSE " + DIRECCIONIP);
            Class.forName("com.mysql.jdbc.Driver");
            //con = DriverManager.getConnection("jdbc:mysql://localhost/vboutique3",usuario, contra);
            con = DriverManager.getConnection("jdbc:mysql://" + DIRECCIONIP + ":3306/ine", usuario, contra);
            System.out.println("Conexion Correcta");
        } catch (SQLException ex) {
            // throw new SQLException(ex);
            JOptionPane.showMessageDialog(null, "No hay conexión a la base de datos", "ADVERTENCIA SQL", JOptionPane.WARNING_MESSAGE);
        } catch (ClassNotFoundException ex) {
            //throw new ClassCastException(ex.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión a la base de datos", "ADVERTENCIA CLASS", JOptionPane.WARNING_MESSAGE);

        } finally {

            return con;
        }
    }//conexion

    public Connection getPreConexion() {

        usuario = "root";
        contra = "123456";

        try {

            Class.forName("com.mysql.jdbc.Driver");
            //con = DriverManager.getConnection("jdbc:mysql://localhost/vboutique3",usuario, contra);
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ine", usuario, contra);
            System.out.println("Conexion Correcta");
        } catch (SQLException ex) {
            // throw new SQLException(ex);
            JOptionPane.showMessageDialog(null, "No hay conexión a la base de datos", "ADVERTENCIA SQL", JOptionPane.WARNING_MESSAGE);
        } catch (ClassNotFoundException ex) {
            //throw new ClassCastException(ex.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión a la base de datos", "ADVERTENCIA CLASS", JOptionPane.WARNING_MESSAGE);

        } finally {

            return con;
        }
    }//conexion

    public  ResultSet getTabla(String consulta,Connection conn) throws SQLException {
        // Connection cn=getConectivity();
        //con = DriverManager.getConnection("jdbc:mysql://" + DIRECCIONIP + ":3306/ine", usuario, contra);
        conn = DriverManager.getConnection("jdbc:mysql://"+DIRECCIONIP+":3306/ine", usuario, contra);
        Statement st;
        ResultSet datos = null;
        try {
            st = conn.createStatement();
            datos = st.executeQuery(consulta);
        } catch (Exception e) {
            System.out.println("error consulta no se pudo realizar");
        }
        return datos;
    }

    public boolean hayConexion() {
        leer();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + DIRECCIONIP + ":3306/ine", usuario, contra);

        
            System.out.println("Conexion Correcta");
        } catch (SQLException ex) {
            // throw new SQLException(ex);
            JOptionPane.showMessageDialog(null, "No hay conexión a la base de datos", "SQLException", JOptionPane.WARNING_MESSAGE);
            return false;
        } catch (ClassNotFoundException ex) {
            //throw new ClassCastException(ex.getMessage());

            return false;

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return true;
    }
    //GIL
    public ArrayList<String> acceder(String sql){
        ArrayList<String> arr=new ArrayList<String>();
        try {
            Statement comando=con.createStatement();
            ResultSet registro = comando.executeQuery(sql);
            /*for(int i=1;i<=registro.getMetaData().getColumnCount();i++){
                arr.add(registro.getString(i));
                System.out.print(registro.getString(i));
            }*/
            while(registro.next()){
                for(int i=1;i<=registro.getMetaData().getColumnCount();i++){
                System.out.print(registro.getString(i));
                arr.add(registro.getString(i));
                }
            }
            comando.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return arr;
    }
    
    public boolean ejecutar(String sql) {
        try {
            Statement sentencia = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            sentencia.executeUpdate(sql);
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }        return true;
    }
}//ConexionLogin
