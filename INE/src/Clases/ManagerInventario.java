/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author kevin
 */
public class ManagerInventario {
    
    private Connection conexion;
    private Conexion db;
    
    public ManagerInventario(){
    
        db = new Conexion();
        
    }//Constructor
    
    public boolean insertarInventarioG(String clave, String producto, String almacen, String marca,int stockmin, int stock, String descripcion, String observaciones,String tipo) {
        try {
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Creamos la variable para guardar el resultado de las consultas
            ResultSet rs;
            
            //Insertamos al inventario
            String sql = "insert into inventario_Granel (id_productoGranel,nombre_prod,almacen,marca,stock_min,stock,descripcion,observaciones,estatus,tipo_uso) "
                         +"values('"+clave+"','"+producto+"','"+almacen+"','"+marca+"','"+stockmin+"','"+stock+"','"
                         +descripcion+"','"+observaciones+"','DISPONIBLE','"+tipo+"');";
            st.executeUpdate(sql);
            
            //Cerramos la conexión
            conexion.close();
            return true;
            
        } catch (SQLException ex) {
            System.out.printf("Error al insertar en el inventario en SQL");
            Logger.getLogger(ManagerInventario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//insertarEmpleado
    
    public boolean guardarImagen(String clave, String producto, String almacen, String marca,String noserie, String descripcion, String observaciones,String estatus,String tipo,String modelo,String color,String ruta) {
        conexion = db.getConexion();
        String insert = "insert into inventario (id_producto,nombre_prod,almacen,marca,no_serie,descripcion,observaciones,estatus,tipo_uso,modelo,color,imagen)values(?,?,?,?,?,?,?,?,?,?,?,?);";
        FileInputStream fi = null;
        PreparedStatement ps = null;
        
        try {
            File file = new File(ruta);
            fi = new FileInputStream(file);

            ps = conexion.prepareStatement(insert);

            ps.setString(1, clave);
            ps.setString(2, producto);
            ps.setString(3, almacen);
            ps.setString(4, marca);
            ps.setString(5, noserie);
            ps.setString(6, descripcion);
            ps.setString(7, observaciones);
            ps.setString(8, estatus);
            ps.setString(9, tipo);
            ps.setString(10, modelo);
            ps.setString(11, color);
            ps.setBinaryStream(12, fi);

            ps.executeUpdate();

            //Si es algún CPU o Monitor o Teclado, lo insertamos a su correspondiente tabla para cuando se necesite
            //asignar a un grupo en la tabla de equipo de computo. Cada componente tenga su propia llave.
            if(producto.equals("CPU") || producto.equals("Monitor") || producto.equals("Teclado")){
                String sql = "insert into "+producto+" values('"+clave+"');";
                Statement st = conexion.createStatement(); 
                st.executeUpdate(sql);
            }
            
            return true;
           
        } catch (Exception ex) {
            System.out.println("Error al guardar Imagen " + ex.getMessage());
            return false;

        }
    }
    
    public boolean existeInventarioG(String id_producto) {

        boolean estado = false;
        
        try {
            //Consulta para saber si existe o no dicho producto
            String sql = "select * from inventario_Granel where id_productoGranel = '"+id_producto+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            estado = rs.next();//Guardamos el resultado para retornar la respuesta.
            conexion.close();
            
        } catch (SQLException ex) {
            System.out.printf("Error al consultar el inventario en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
            return estado;

    }//existeInventarioG
    
    public DefaultTableModel getInventarioG(int filtro) {
        String orden = "";
        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Clave");
            table.addColumn("Producto");
            table.addColumn("Descripción");
            table.addColumn("Almacén");
            table.addColumn("Estatus");
            table.addColumn("Marca");
            table.addColumn("Observaciones");
            table.addColumn("Stock");
            
            switch(filtro){
                case 0:
                    orden = "order by id_productoGranel";
                    break;
                case 1:
                    orden = "order by nombre_prod";
                    break;
                case 2:
                    orden = "order by descripcion";
                    break;    
                case 3:
                    orden = "order by almacen";
                    break;
                case 4:
                    orden = "order by marca";
                    break;
                case 5:
                    orden = "order by observaciones";
                    break;
            }
            
            //Consulta de los empleados
            String sql = "select id_productoGranel,nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel "+orden+";";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[8];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<8;i++){
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

    }//getInventarioG

    public DefaultTableModel getInventario(int filtro) {
        String orden = "";
        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Producto");
            table.addColumn("Almacén");
            table.addColumn("Marca");
            table.addColumn("Stock");
            
            switch(filtro){
                case 0:
                    orden = "order by nombre_prod";
                    break;
                case 1:
                    orden = "order by almacen";
                    break;
                case 2:
                    orden = "order by marca";
                    break;    
            }
            
            //Consulta de los empleados
            String sql = "select nombre_prod,almacen,marca,count(nombre_prod and marca) as stock from inventario group by nombre_prod,marca "+orden+";";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[4];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<4;i++){
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

    }//getInventario
    
    public boolean existeInventario(String id_producto) {

        boolean estado = false;
        
        try {
            //Consulta para saber si existe o no dicho producto
            String sql = "select * from inventario where id_producto = '"+id_producto+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            estado = rs.next();//Guardamos el resultado para retornar la respuesta.
            conexion.close();
            
        } catch (SQLException ex) {
            System.out.printf("Error al consultar el inventario en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
            return estado;

    }//existeInventario
    
    public boolean insertarInventario(String clave, String producto, String almacen, String marca,String noserie, String descripcion, String observaciones,String tipo,String modelo,String color) {
        try {
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Creamos la variable para guardar el resultado de las consultas
            ResultSet rs;
            
            //Insertamos al inventario
            String sql = "insert into inventario (id_producto,nombre_prod,almacen,marca,no_serie,descripcion,observaciones,estatus,tipo_uso,modelo,color) "
                         +"values('"+clave+"','"+producto+"','"+almacen+"','"+marca+"','"+noserie+"','"
                         +descripcion+"','"+observaciones+"','DISPONIBLE','"+tipo+"','"+modelo+"','"+color+"');";
            st.executeUpdate(sql);
            
            //Si es algún CPU o Monitor o Teclado, lo insertamos a su correspondiente tabla para cuando se necesite
            //asignar a un grupo en la tabla de equipo de computo. Cada componente tenga su propia llave.
            if(producto.equals("CPU") || producto.equals("Monitor") || producto.equals("Teclado")){
                sql = "insert into "+producto+" values('"+clave+"');";
                st.executeUpdate(sql);
            }
            
            //Cerramos la conexión
            conexion.close();
            return true;
            
        } catch (SQLException ex) {
            System.out.printf("Error al insertar en el inventario en SQL");
            Logger.getLogger(ManagerInventario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//insertarInventario
    
    public DefaultTableModel getInventarioCoincidencias(String prod) {

        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Clave");
            table.addColumn("Producto");
            table.addColumn("Almacén");
            table.addColumn("Descripción");
            table.addColumn("No. serie");
            table.addColumn("Marca");
            table.addColumn("Observaciones");
            table.addColumn("Modelo");
            table.addColumn("Color");
            table.addColumn("Estatus");
            
            //Consulta de los empleados
            String sql = "select id_producto,nombre_prod,almacen,descripcion,no_serie,marca,observaciones,modelo,color,estatus "
                         +"from inventario where nombre_prod = '"+prod+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[10];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<10;i++){
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

    }//getInventarioCoincidencias
    
    public DefaultTableModel getInventarioCoincidenciasEspecifico(int filtro,String prod,String busqueda) {

        DefaultTableModel table = new DefaultTableModel();
        String tipoBusqueda = "";
        
        try {
            table.addColumn("Clave");
            table.addColumn("Producto");
            table.addColumn("Almacén");
            table.addColumn("Descripción");
            table.addColumn("No. serie");
            table.addColumn("Marca");
            table.addColumn("Observaciones");
            table.addColumn("Modelo");
            table.addColumn("Color");
            table.addColumn("Estatus");
            
            /*
            filtro = 0; Clave
            filtro = 1; Almacén
            filtro = 2; Descripción
            filtro = 3; No. serie
            filtro = 4; Marca
            filtro = 5; Observaciones
            filtro = 6; Modelo
            filtro = 7; Color
            filtro = 8; Estatus
            */
            
            switch(filtro){

                case 0:
                    tipoBusqueda = "id_producto";
                    break;

                case 1:
                    tipoBusqueda = "almacen";
                    break;

                case 2:
                    tipoBusqueda = "descripcion";
                    break;

                case 3:
                    tipoBusqueda = "no_serie";
                    break;

                case 4:
                    tipoBusqueda = "marca";
                    break;

                case 5:
                    tipoBusqueda = "observaciones";
                    break;
                    
                case 6:
                    tipoBusqueda = "modelo";
                    break;

                case 7:
                    tipoBusqueda = "color";
                    break;

                case 8:
                    tipoBusqueda = "estatus";
                    break;    

            }//Buscamos el nombre de la columna con lo que vamos a buscar la coincidencia
            
            //Consulta de los empleados
            String sql = "select id_producto,nombre_prod,almacen,descripcion,no_serie,marca,observaciones,modelo,color,estatus "
                         +"from inventario where nombre_prod = '"+prod+"' and "+tipoBusqueda+" like '"+busqueda+"%';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[10];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<10;i++){
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

    }//getInventarioCoincidenciasEspecifico
    
    public boolean existeProductoCoincidenciaEspecifico(int filtro, String prod, String busqueda){
        boolean estado = false;
        String tipoBusqueda = "";
        try{
            /*
            filtro = 0; Clave
            filtro = 1; Almacén
            filtro = 2; Descripción
            filtro = 3; No. serie
            filtro = 4; Marca
            filtro = 5; Observaciones
            filtro = 6; Modelo
            filtro = 7; Color
            filtro = 8; Estatus
            */
            
            switch(filtro){

                case 0:
                    tipoBusqueda = "id_producto";
                    break;

                case 1:
                    tipoBusqueda = "almacen";
                    break;

                case 2:
                    tipoBusqueda = "descripcion";
                    break;

                case 3:
                    tipoBusqueda = "no_serie";
                    break;

                case 4:
                    tipoBusqueda = "marca";
                    break;

                case 5:
                    tipoBusqueda = "observaciones";
                    break;
                    
                case 6:
                    tipoBusqueda = "modelo";
                    break;

                case 7:
                    tipoBusqueda = "color";
                    break;

                case 8:
                    tipoBusqueda = "estatus";
                    break;    

            }//Buscamos el nombre de la columna con lo que vamos a buscar la coincidencia
            
            //Consulta de los empleados
            String sql = "select id_producto,nombre_prod,almacen,descripcion,no_serie,marca,observaciones,modelo,color,estatus "
                         +"from inventario where nombre_prod = '"+prod+"' and "+tipoBusqueda+" like '"+busqueda+"%';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            estado = rs.next();
            
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(ManagerInventario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return estado; //Retorna el resultado, si se encontro o no
        
    }//Buscar si existe el producto de coincidnecia especifico
    
    public boolean existeProductoEspecifico(int filtro, String busqueda,String inventario){
        boolean estado = false;
        try{
            /*
            filtro = 0; Producto
            filtro = 1; Almacén
            filtro = 2; Marca
            */
            String sql;
            Connection c = db.getConexion();
            Statement st = c.createStatement();
            ResultSet rs;
            
            //BUSCA EN EL INVENTARIO
            if(inventario.equals("Inventario")){
                
                switch(filtro){
                    
                    //BUSQUEDA POR PRODUCTO
                    case 0:
                        sql = "select nombre_prod,almacen,marca,count(nombre_prod and marca) as stock from inventario " +
                                "where nombre_prod like '"+busqueda+"%' group by nombre_prod,marca;";
                        rs = st.executeQuery(sql);
                        estado = rs.next();
                        break;

                    //BUSQUEDA POR ALMACEN
                    case 1:
                        sql = "select nombre_prod,almacen,marca,count(nombre_prod and marca) as stock from inventario " +
                                "where almacen like '"+busqueda+"%' group by nombre_prod,marca;";
                        rs = st.executeQuery(sql);
                        estado = rs.next();
                        break;
                        
                    //BUSQUEDA POR MARCA
                    case 2:
                        sql = "select nombre_prod,almacen,marca,count(nombre_prod and marca) as stock from inventario " +
                                "where marca like '"+busqueda+"%' group by nombre_prod,marca;";
                        rs = st.executeQuery(sql);
                        estado = rs.next();
                        break;
                
                }//Hace la busqueda de acuerdo al filtro
                
            }//if
            
            //BUSCA EN EL INVENTARIO A GRANEL
            else{
                
                /*
                filtro = 0; Clave
                filtro = 1; Producto
                filtro = 2; Descripción
                filtro = 3; Almacén
                filtro = 4; Marca
                filtro = 5; Observaciones
                */
                
                switch(filtro){
            
                    //BUSQUEDA POR CLAVE
                    case 0:
                        sql = "select id_productoGranel,nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel"
                                + " where id_productoGranel like '"+busqueda+"%';";
                        rs = st.executeQuery(sql);
                        estado = rs.next();
                        break;

                    //BUSQUEDA POR PRODUCTO
                    case 1:
                        sql = "select id_productoGranel,nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel"
                                + " where nombre_prod like '"+busqueda+"%';";
                        rs = st.executeQuery(sql);
                        estado = rs.next();
                        break;
                        
                    //BUSQUEDA POR DESCRIPCIÓN
                    case 2:
                        sql = "select id_productoGranel,nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel"
                                + " where descripcion like '"+busqueda+"%';";
                        rs = st.executeQuery(sql);
                        estado = rs.next();
                        break;
                        
                    //BUSQUEDA POR ALMACÉN
                    case 3:
                        sql = "select id_productoGranel,nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel"
                                + " where almacen like '"+busqueda+"%';";
                        rs = st.executeQuery(sql);
                        estado = rs.next();
                        break;

                    //BUSQUEDA POR MARCA
                    case 4:
                        sql = "select id_productoGranel,nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel"
                                + " where marca like '"+busqueda+"%';";
                        rs = st.executeQuery(sql);
                        estado = rs.next();
                        break;
                        
                    //BUSQUEDA POR OBSERVACIONES
                    case 5:
                        sql = "select id_productoGranel,nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel"
                                + " where observaciones like '"+busqueda+"%';";
                        rs = st.executeQuery(sql);
                        estado = rs.next();
                        break;    
                
                }//Hace la busqueda de acuerdo al filtro
            
            }//else
            
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(ManagerInventario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return estado; //Retorna el resultado, si se encontro o no
        
    }//Buscar si existe el producto

    public DefaultTableModel getInventarioEspecifico(int filtro, String busqueda,String inventario) {

        DefaultTableModel table = new DefaultTableModel();
        int tamaño;
        try {
            
            if(inventario.equals("Inventario")){
                table.addColumn("Producto");
                table.addColumn("Almacén");
                table.addColumn("Marca");
                table.addColumn("Stock");
                tamaño = 4;
            }else{
                table.addColumn("Clave");
                table.addColumn("Producto");
                table.addColumn("Descripción");
                table.addColumn("Almacén");
                table.addColumn("Estatus");
                table.addColumn("Marca");
                table.addColumn("Observaciones");
                table.addColumn("Stock");
                tamaño = 8;
            }
            
            //Consulta del inventario 
            String sql = "";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[tamaño];
            
            //BUSCA EN EL INVENTARIO
            if(inventario.equals("Inventario")){
                
                switch(filtro){
                    
                    //BUSQUEDA POR PRODUCTO
                    case 0:
                        sql = "select nombre_prod,almacen,marca,count(nombre_prod and marca) as stock from inventario " +
                                "where nombre_prod like '"+busqueda+"%' group by nombre_prod,marca;";
                        break;

                    //BUSQUEDA POR ALMACEN
                    case 1:
                        sql = "select nombre_prod,almacen,marca,count(nombre_prod and marca) as stock from inventario " +
                                "where almacen like '"+busqueda+"%' group by nombre_prod,marca;";
                        break;
                        
                    //BUSQUEDA POR MARCA
                    case 2:
                        sql = "select nombre_prod,almacen,marca,count(nombre_prod and marca) as stock from inventario " +
                                "where marca like '"+busqueda+"%' group by nombre_prod,marca;";
                        break;
                
                }//Hace la busqueda de acuerdo al filtro
                
            }//if
            
            //BUSCA EN EL INVENTARIO A GRANEL
            else{
                
                /*
                filtro = 0; Clave
                filtro = 1; Producto
                filtro = 2; Descripción
                filtro = 3; Almacén
                filtro = 4; Marca
                filtro = 5; Observaciones
                */
                
                switch(filtro){
            
                    //BUSQUEDA POR CLAVE
                    case 0:
                        sql = "select id_productoGranel,nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel"
                                + " where id_productoGranel like '"+busqueda+"%';";
                        break;

                    //BUSQUEDA POR PRODUCTO
                    case 1:
                        sql = "select id_productoGranel,nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel"
                                + " where nombre_prod like '"+busqueda+"%';";
                        break;
                        
                    //BUSQUEDA POR DESCRIPCIÓN
                    case 2:
                        sql = "select id_productoGranel,nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel"
                                + " where descripcion like '"+busqueda+"%';";
                        break;
                        
                    //BUSQUEDA POR ALMACÉN
                    case 3:
                        sql = "select id_productoGranel,nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel"
                                + " where almacen like '"+busqueda+"%';";
                        break;

                    //BUSQUEDA POR MARCA
                    case 4:
                        sql = "select id_productoGranel,nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel"
                                + " where marca like '"+busqueda+"%';";
                        break;
                        
                    //BUSQUEDA POR OBSERVACIONES
                    case 5:
                        sql = "select id_productoGranel,nombre_prod,descripcion,almacen,estatus,marca,observaciones,stock from Inventario_granel"
                                + " where observaciones like '"+busqueda+"%';";
                        break;    
                
                }//Hace la busqueda de acuerdo al filtro
            
            }//else
            
            ResultSet rs = st.executeQuery(sql);
            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<tamaño;i++){
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

    }//getInventario
    
    public Blob leerImagen(String idProducto) throws IOException {
        conexion = db.getConexion();
        //String sSql = "select imagen from inventario where id_producto = '"+idProducto+"';";
        String sSql = "select imagen from inventario where id_producto = '"+idProducto+"';";
        
        PreparedStatement pst;
        Blob blob = null;
        try {
            pst = conexion.prepareStatement(sSql);
            ResultSet res = pst.executeQuery();
            if (res.next()) {

                blob = res.getBlob("imagen");

            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerVehiculos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blob;
    }//leerImagen
    
    public Vector infoProductos(String idProducto) {
        conexion = db.getConexion();
        Vector v = new Vector();
        try {

            Statement st = conexion.createStatement();
            String sql = "select nombre_prod,descripcion,almacen,marca,observaciones,no_serie,tipo_uso,modelo,color from inventario where id_producto = '"+idProducto+"';";
            ResultSet resultados = st.executeQuery(sql);
            while (resultados.next()) {
                String temp = "";
                temp += "" + resultados.getString("nombre_prod") + "," + resultados.getString("descripcion") + "," + resultados.getString("almacen")
                         + "," + resultados.getString("marca")+ "," + resultados.getString("observaciones")+ "," + resultados.getString("no_serie")
                        + "," + resultados.getString("tipo_uso") + "," + resultados.getString("modelo")+ "," + resultados.getString("color");
                        
                v.add(temp);
            }

            conexion.close();

        } //para el ticket
        catch (SQLException ex) {
            Logger.getLogger(ManagerVehiculos.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error producto infoProductos");
        }

        return v;
    }//infoVehiculos
    
     public boolean actualizarProducto(String clave, String producto, String almacen, String marca,String noserie, String descripcion, String observaciones,String tipo,String modelo,String color,String ruta) {
        conexion = db.getConexion();
        
        String update = "update inventario set nombre_prod = ?,almacen = ?,marca = ?,no_serie = ?,descripcion = ?,observaciones = ?,tipo_uso = ?,modelo = ?,color = ?,imagen = ? where id_producto = '"+clave+"'";
        FileInputStream fi = null;
        PreparedStatement ps = null;

        try {
            File file = new File(ruta);
            fi = new FileInputStream(file);

             ps = conexion.prepareStatement(update);

            ps.setString(1, producto);
            ps.setString(2, almacen);
            ps.setString(3, marca);
            ps.setString(4, noserie);
            ps.setString(5, descripcion);
            ps.setString(6, observaciones);
            ps.setString(7, tipo);
            ps.setString(8, modelo);
            ps.setString(9, color);
            ps.setBinaryStream(10, fi);

            ps.executeUpdate();

            return true;

        } catch (Exception ex) {
            System.out.println("Error al actualizar imagen " + ex.getMessage());
            return false;

        }

    }//guardarImagen
     
     
     public boolean actualizarProductoSinFoto(String clave, String producto, String almacen, String marca,String noserie, String descripcion, String observaciones,String tipo,String modelo,String color) {
        conexion = db.getConexion();
        
        String update = "update inventario set nombre_prod = ?,almacen = ?,marca = ?,no_serie = ?,descripcion = ?,observaciones = ?,tipo_uso = ?,modelo = ?,color = ? where id_producto = '"+clave+"'";
        FileInputStream fi = null;
        PreparedStatement ps = null;

        try {
           
            ps = conexion.prepareStatement(update);

            ps.setString(1, producto);
            ps.setString(2, almacen);
            ps.setString(3, marca);
            ps.setString(4, noserie);
            ps.setString(5, descripcion);
            ps.setString(6, observaciones);
            ps.setString(7, tipo);
            ps.setString(8, modelo);
            ps.setString(9, color);

            ps.executeUpdate();

            return true;

        } catch (Exception ex) {
            System.out.println("Error al actualizar imagen " + ex.getMessage());
            return false;

        }

    }//guardarImagen
    
    
}//class
