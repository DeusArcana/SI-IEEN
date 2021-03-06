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
public class ManagerComplemento {
    
    private Connection conexion;
    private Conexion db;
    
    public ManagerComplemento(){
        
        db = new Conexion();
        
    }//Constructor
    
    public DefaultTableModel getBusquedaResguardo(int filtro, String busqueda){
        //No dejamos editar ninguna celda
        DefaultTableModel table = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
       conexion = db.getConexion();
		
        String campoBusca = "";
        String sql = "";

        try{
            switch(filtro){
            
                //BUSQUEDA POR PROPIETARIO
                case 0:
                    campoBusca = "concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m)";
                    break;

                //BUSQUEDA POR PRODUCTO
                case 1:
                    campoBusca = "rp.nombre_prod";
                    break;
                        
                //BUSQUEDA POR FECHA DE REGISTRO
                case 2:
                    campoBusca = "rp.fecha_alta";
                    break;

                //BUSQUEDA POR OBSERVACIONES
                case 3:
                    campoBusca = "rp.observaciones";
                    break;

                //BUSQUEDA POR FECHA DE SALIDA
                case 4:
                    campoBusca = "rp.fecha_salida";
                    break;
            }//Hace la busqueda de acuerdo al filtro
            
            sql = "select concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m), rp.nombre_prod, date(rp.fecha_alta),time(rp.fecha_alta),rp.observaciones,date(rp.fecha_salida),time(rp.fecha_salida) from resguardo_personal rp "
                    + "inner join user u on (u.id_user = rp.id_user) "
                    + "inner join empleados e on (e.id_empleado = u.id_empleado) "
                    + "where "+campoBusca+" like '%"+busqueda+"%';";
            Connection c = db.getConexion();
            Statement st = c.createStatement();    
            ResultSet rs = st.executeQuery(sql);
            
            table.addColumn("Propietario");
            table.addColumn("Producto");
            table.addColumn("Fecha de registro");
            table.addColumn("Hora de registro");
            table.addColumn("Observaciones");
            table.addColumn("Fecha de salida");
            table.addColumn("Hora de salida");
            
            Object datos[] = new Object[7];

            //Proseguimos con los registros en caso de exisitir mas
            while (rs.next()) {

                for(int i = 0;i<7;i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro

                table.addRow(datos);//Añadimos la fila

            }//while

            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(ManagerInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
            return table;
    }//getBusquedaResguardo
    
    public DefaultTableModel getPuestos() {

        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Puestos");
            
            //Consulta de los empleados
            String sql = "select * from Puestos;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {
                table.addRow(new Object[]{rs.getObject(1)});//Añadimos la fila
           }//while

            //Cerramos la conexión
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//getPuestos
    
    public DefaultTableModel getAreas() {

        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Áreas");
            
            //Consulta de los empleados
            String sql = "select * from Area;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {
                table.addRow(new Object[]{rs.getObject(1)});//Añadimos la fila
           }//while

            //Cerramos la conexión
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//getAreas
    
    //Este método es para obtener los perfiles de usuario
    public void getComboPerfiles(JComboBox combo) {
        try{
           
            String sql = "select puesto from Puestos;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo.addItem(rs.getObject(1).toString());
            }
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los perfiles de usuario para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }//Obtiene todas los puestos y las mete al combobox
    
    public String obtenerAreas() {
        String resultado = "";
        try{
            String sql = "select ID_Area,Area from Area;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            resultado = rs.getInt(1)+",,"+rs.getString(2);
            while(rs.next()){
                resultado += ",,"+rs.getInt(1)+",,"+rs.getString(2);
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener las áreas para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultado;
        
    }//Obtiene el id del area y su descripción
    
    //Este método es para obtener los puestos de trabajo de acuerdo al área que se selecciono
    public String obtenerPuestos(int idArea) {
        String resultado = "";
        try{
            String sql = "select ID_Puesto,Puesto from Puestos_Trabajo where ID_Area = "+idArea+";";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            resultado = rs.getInt(1)+",,"+rs.getString(2);
            while(rs.next()){
                resultado += ",,"+rs.getInt(1)+",,"+rs.getString(2);
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los puestos para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultado;
        
    }//Obtiene el id del puesto y su descripción
    
    //Este método es para obtener los puestos de trabajo de acuerdo al área que se selecciono
    public String obtenerLocalidades(int idEstado) {
        String resultado = "";
        try{
            String sql = "select idLocalidad,Nombre from Localidad where Estado_idEstado = "+idEstado+";";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            resultado = rs.getInt(1)+",,"+rs.getString(2);
            while(rs.next()){
                resultado += ",,"+rs.getInt(1)+",,"+rs.getString(2);
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener las localidades para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultado;
        
    }//Obtiene el id del puesto y su descripción
    
    public String obtenerEstados() {
        String resultado = "";
        try{
            String sql = "select idEstado,Nombre from Estado;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            resultado = rs.getInt(1)+",,"+rs.getString(2);
            while(rs.next()){
                resultado += ",,"+rs.getInt(1)+",,"+rs.getString(2);
            }
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los estados para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultado;
        
    }//Obtiene el id del area y su descripción
    
    public DefaultTableModel getResguardoPersonal(String usuario) {

        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Producto");
            table.addColumn("Fecha de ingreso");
            table.addColumn("Hora de ingreso");
            table.addColumn("Observaciones");
            
            //Consulta de los empleados
            String sql = "select nombre_prod,date(fecha_alta),time(fecha_alta),observaciones from resguardo_personal where id_user = '"+usuario+"' and fecha_salida is null;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Object[] datos = new Object[4];
            
            //Llenar tabla
            while (rs.next()) {
                
                for(int i = 0;i<4;i++){
                    datos[i] = rs.getString(i+1);
                }
                
                table.addRow(datos);//Añadimos la fila
           
            }//while

            //Cerramos la conexión
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener el resguardo personal del usuario \""+usuario+"\" SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//getResguardoPersonal
    
    public boolean quitarRegistroResguardo(String usuario,String prod,String fecha, String hora, String observaciones) {

        boolean estado = false;
        try {
            
            //Agregamos la fecha de salida
            String sql = "update resguardo_personal set fecha_salida = now() where nombre_prod = '"+prod+"' and date(fecha_alta) = '"+fecha+"' and time(fecha_alta) = '"+hora+"' and observaciones = '"+observaciones+"' and id_user = '"+usuario+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            st.executeUpdate(sql);
            //Cerramos la conexión
            conexion.close();
            estado = true;
        } catch (SQLException ex) {
            System.out.printf("Error al obtener el resguardo personal del usuario \""+usuario+"\" SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return estado;
    }//quitarRegistroResguardo
    
    public boolean insertarResguardo(String usuario,String producto,String observaciones) {
        try{
           
            String sql = "insert into resguardo_personal (id_user,nombre_prod,fecha_alta,observaciones)values('"+usuario+"','"+producto+"',now(),'"+observaciones+"');";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            st.executeUpdate(sql);
            
            conexion.close();
            
            return true;
            
        } catch (SQLException ex) {
            System.out.printf("Error al insertar el resguardo personal SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//Obtiene todas los puestos y las mete al combobox
    
    //Este método es para convertir un numero a su equivalente en texto (letras)
    public String textoNumero(int numero){
        String texto="";
        
        //Primero verificamos si es mayor a las centenas
        int miles = numero/1000;
        numero = numero%1000;
        
        switch(miles){
                case 1:
                    texto += "Mil ";
                    break;
                case 2:
                    texto += "Dos Mil ";
                    break;
                case 3:
                    texto += "Tres Mil ";
                    break;
                case 4:
                    texto += "Cuatro Mil ";
                    break;
                case 5:
                    texto += "Cinco Mil ";
                    break;
                case 6:
                    texto += "Seis Mil ";
                    break;
                case 7:
                    texto += "Siete Mil ";
                    break;
                case 8:
                    texto += "Ocho Mil ";
                    break;
                case 9:
                    texto += "Nueve Mil ";
                    break;
                default:
                    break;
        }//swtich
        
        //Ahora verificamos las centenas
        int centena = numero/100;
        numero = numero%100;
        
        switch(centena){
                case 1:
                    if(numero > 0){
                        texto += "Ciento ";
                    }
                    else{
                        texto += "Cien";
                    }
                    break;
                case 2:
                    texto += "Doscientos ";
                    break;
                case 3:
                    texto += "Trescientos ";
                    break;
                case 4:
                    texto += "Cuatroscientos ";
                    break;
                case 5:
                    texto += "Quinientos ";
                    break;
                case 6:
                    texto += "Seiscientos ";
                    break;
                case 7:
                    texto += "Setescientos ";
                    break;
                case 8:
                    texto += "Ochoscientos ";
                    break;
                case 9:
                    texto += "Novescientos ";
                    break;
                default:
                    break;
        }//swtich
        
        //Enseguida verificamos las decenas
        int decenas = numero/10;
        numero = numero%10;
        boolean unity = false;
        
        switch(decenas){
                case 1:
                    //Aqui es un caso especial, del 11 al 15 tienen su propio nombre, del 16 al 19 tienen en comun "Dieci" y su respectivo numero
                    switch(numero){
                            case 0:
                                texto += "Diez";
                                unity = true;
                                break;
                            case 1:
                                texto += "Once";
                                unity = true;
                                break;
                            case 2:
                                texto += "Doce";
                                unity = true;
                                break;
                            case 3:
                                texto += "Trece";
                                unity = true;
                                break;
                            case 4:
                                texto += "Catorce";
                                unity = true;
                                break;
                            case 5:
                                texto += "Quince";
                                unity = true;
                                break;
                            default:
                                texto += "Dieci";
                                break;
                        }//switch
                        
                    break;
                case 2:
                    if(numero > 0){
                        texto += "Veinti";
                    }
                    else{
                        texto += "Veinte";
                    }
                    break;
                case 3:
                    if(numero > 0){
                        texto += "Treinta y ";
                    }
                    else{
                        texto += "Treinta";
                    }
                    break;
                case 4:
                    if(numero > 0){
                        texto += "Cuarenta y ";
                    }
                    else{
                        texto += "Cuarenta";
                    }
                    break;
                case 5:
                    if(numero > 0){
                        texto += "Ciencuenta y ";
                    }
                    else{
                        texto += "Cincuenta";
                    }
                    break;
                case 6:
                    if(numero > 0){
                        texto += "Sesenta y ";
                    }
                    else{
                        texto += "Sesenta";
                    }
                    break;
                case 7:
                    if(numero > 0){
                        texto += "Setenta y ";
                    }
                    else{
                        texto += "Setenta";
                    }
                    break;
                case 8:
                    if(numero > 0){
                        texto += "Ochenta y ";
                    }
                    else{
                        texto += "Ochenta";
                    }
                    break;
                case 9:
                    if(numero > 0){
                        texto += "Noventa y ";
                    }
                    else{
                        texto += "Noventa";
                    }
                    break;
                default:
                    break;
        }//swtich
        
        //Por ultimo verificamos las unidades en caso de que no se hayan usado en la decena "10"
        if(!unity){

            switch(numero){
                    case 1:
                        texto += "Uno";
                        break;
                    case 2:
                        texto += "Dos";
                        break;
                    case 3:
                        texto += "Tres";
                        break;
                    case 4:
                        texto += "Cuatro";
                        break;
                    case 5:
                        texto += "Cinco";
                        break;
                    case 6:
                        texto += "Seis";
                        break;
                    case 7:
                        texto += "Siete";
                        break;
                    case 8:
                        texto += "Ocho";
                        break;
                    case 9:
                        texto += "Nueve";
                        break;
                    default:
                        if(texto.equals("")){
                            texto += "Cero";
                        }
                        break;
            }//swtich
        }//if(!unity)
        
        return texto;
        
    }//textoNumero
    
    
}//class
