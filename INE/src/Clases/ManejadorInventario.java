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
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Clases.ManagerInventario;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableColumn;
/**
 *
 * @author kevin
 */
public class ManejadorInventario {
    private Connection conexion;
    private Conexion db;
    ManagerInventario manager_inventario;
    
    public ManejadorInventario(){
        db = new Conexion();
        manager_inventario = new ManagerInventario();
    }//constructor
    
    public DefaultTableModel getInventarioG() {
        
        DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Clave");
            table.addColumn("Producto");
            table.addColumn("Descripción");
            table.addColumn("Almacén");
            table.addColumn("Marca");
            table.addColumn("Observaciones");
            table.addColumn("Stock");
            
            
            //Consulta de los empleados
            String sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,almacen,marca,observaciones,stock from Inventario_granel where estatus = 'Disponible';";
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

    }//getInventarioG

    //Este método retorna la tabla de inventario normal solo con los productos disponibles, esto para mostrarse en el Manejador Inventario
    //cuando se quiere realizar una asignación
    public DefaultTableModel getInventarioParaAsignacion(String nomeclatura) {
        String orden = "";
        DefaultTableModel table = new DefaultTableModel();

        try {
            
            table.addColumn("Clave");
            table.addColumn("Nombre_corto");
            table.addColumn("Descripción");
            table.addColumn("Ubicación");
            table.addColumn("Marca");
            table.addColumn("No. Serie");
            table.addColumn("Modelo");
            
            String sql = "";
            if(nomeclatura.equals("")){
                //Consulta de los empleados
                sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,ubicacion,marca,no_serie,modelo "
                        + "from inventario where estatus = 'Disponible';";
            }else{
                sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,ubicacion,marca,no_serie,modelo "
                    + "from inventario where Folio = '"+nomeclatura+"' and estatus = 'Disponible';";
            }
            
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

    }//getInventario
    
    public int cantidadInventarioG(String id_producto) {

        int cantidad;
        
        try {
            //Consulta para saber si existe o no dicho producto
            String sql = "select stock from inventario_Granel where concat(Folio,'-',Numero,Extension) = '"+id_producto+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();//Guardamos el resultado para retornar la respuesta.
            cantidad = rs.getInt(1);
            conexion.close();
            return cantidad;
        } catch (SQLException ex) {
            System.out.printf("Error al consultar el inventario en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } 

    }//cantidadInventarioG
    
    public int productosSuficientesInventarioG(String id_producto,int cantidad) {
        int stock = 0;
        try {
            //Hacemos el update de la resta del inventario
            String sql = "update inventario_Granel set stock = stock - "+cantidad+" where concat(Folio,'-',Numero,Extension) = '"+id_producto+"' and stock > "+cantidad+";";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            st.executeUpdate(sql);
            //Obtenemos el stock del producto para saber si se realizo o no el update
            sql = "select stock from inventario_Granel where concat(Folio,'-',Numero,Extension) = '"+id_producto+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            stock = rs.getInt(1);
            conexion.close();
            return stock;
        } catch (SQLException ex) {
            System.out.printf("Error al consultar el inventario a granel en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }//productosSuficientesInventarioG
    
    public int productosIgualesInventarioG(String id_producto,int cantidad) {
        int stock = 0;
        try {
            //Hacemos el update de la resta del inventario
            String sql = "update inventario_Granel set stock = 0,estatus = 'Agotado' where concat(Folio,'-',Numero,Extension) = '"+id_producto+"' and stock = "+cantidad+";";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            st.executeUpdate(sql);
            //Obtenemos el stock del producto para saber si se realizo o no el update
            sql = "select stock from inventario_Granel where concat(Folio,'-',Numero,Extension) = '"+id_producto+"';";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            stock = rs.getInt(1);
            conexion.close();
            return stock;
        } catch (SQLException ex) {
            System.out.printf("Error al consultar el inventario en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }//productosIgualesInventarioG
    
    //Este método es para cancelar la acción de asignación de uno o mas productos en la pestaña de manejador de inventario, se usa cuando
    //cancelas un producto, cuando presionas el boton cancelar o cuando se cierra la ventana y quedaron los productos sin generar el vale
    public boolean regresarInventario(String[] Claves,int[] Cantidad){
        
        try{
                String sql = "";
                conexion = db.getConexion();
                Statement st = conexion.createStatement();
                ResultSet rs;
                for(int i = 0; i < Claves.length; i++){
                    //Buscamos si es de inventario o de granel
                    sql = "select * from inventario where concat(Folio,'-',Numero,Extension) = '"+Claves[i]+"';";
                    rs = st.executeQuery(sql);
                    System.out.println("Hicimos la consulta para ver si es inventario o granel");
                    //Si entra es a inventario
                    if(rs.next()){
                        sql = "update inventario set estatus = 'Disponible' where concat(Folio,'-',Numero,Extension) = '"+Claves[i]+"';";
                        st.executeUpdate(sql);
                        System.out.println("Es inventario normal y cambio el estatus a disponible");
                    }
                    //Si no entra es a granel
                    else{
                        sql = "update inventario_granel set stock = stock + "+Cantidad[i]+" where concat(Folio,'-',Numero,Extension) = '"+Claves[i]+"' and stock > 0;";
                        st.executeUpdate(sql);
                        System.out.println("Llego a querer hacer el update para sumarle la cantidad que se le quito");
                        
                        sql = "update inventario_granel set estatus = 'Disponible', stock = "+Cantidad[i]+" where concat(Folio,'-',Numero,Extension) = '"+Claves[i]+"' and stock = 0;";
                        st.executeUpdate(sql);
                        System.out.println("Llego a querer hacer el update para ponerlo disponible si el stock es 0");
                    }
                }//for
                conexion.close();
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(ManagerAsignarEquipo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true; //Da una respuesta positiva del incremento del inventario de ese producto 
        
    }//Regresa los productos a su estado orignal (estatus y/o cantidad)
    
    //Este método realiza la salida de almcen ya con productos autorizados
    public boolean autorizarSalidaAlmacen(String[] Claves,int[] Cantidad,String usuario,String id){
        
        try{
                String sql = "";
                conexion = db.getConexion();
                Statement st = conexion.createStatement();
                ResultSet rs;
                
                //Obtenemos la fecha y hora exacta del sistema
                sql = "select now();";
                rs = st.executeQuery(sql);
                rs.next();
                String fecha = rs.getString(1);
                
                //Actualizamos el registro, agregando el usuario que autorizo la salida de almacen y la fecha en que se realizo dicho movimiento
                //y cambios su estado a salida autorizada
                sql = "update solicitudsalida set estado = 'Salida Autorizada', user_autorizo = '"+usuario+"', fecha_respuesta = '"+fecha+"' where concat(Folio,'-',Num,'-',Año) = '"+id+"';";
                st.executeUpdate(sql);
                
                //Ahora actualizamos los registros de detalle_Salida para agregar las cantidades que fueron autorizadas para cada productos
                
                for(int i = 0; i < Claves.length; i++){
                    //Actualizamos la ubicación del producto
                    sql = "update detalle_solicitudsalida set cantidad_autorizada = '"+Cantidad[i]+"' where id_solicitud = '"+id+"' and id_producto = '"+Claves[i]+"';";
                    st.executeUpdate(sql);
                }//for
                
                conexion.close();
                return true;
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(ManagerAsignarEquipo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }//autorizarSalidaAlmacen
    
    //Este método realiza el resguardo, en donde todos los productos seleccionados se le asignan a un responsable
    public boolean asignarInventario(String[] Claves,int[] Cantidad,String empleado,String folio){
        
        try{
                String sql = "";
                conexion = db.getConexion();
                Statement st = conexion.createStatement();
                ResultSet rs;
                
                //Obtenemos la fecha y hora exacta del sistema
                sql = "select now();";
                rs = st.executeQuery(sql);
                rs.next();
                String fecha = rs.getString(1);
                
                //Obtenemos el id del empleado para encontrar el usuario
                sql = "select id_empleado from Empleados where concat(nombres,' ',apellido_p,' ',apellido_m) = '"+empleado+"';";
                rs = st.executeQuery(sql);
                rs.next();
                int id_empleado = rs.getInt(1);
                
                 //Buscamos el año y el numero en el que se quedo la solicitud
                Calendar cal= Calendar.getInstance();
                int year= cal.get(Calendar.YEAR);
                int num = 1;
                
                sql = "select Numero from vales where año = "+year+" and Folio = '"+folio+"' order by Numero desc limit 1;";
                rs = st.executeQuery(sql);
                //Si encuentra coincidencias entonces le sumamos uno para el siguiente vale, 
                //en caso de no encontrarlo entonces se reinicia el contador de solicitudes con el nuevo año
                if(rs.next()){
                    num = rs.getInt(1) + 1;
                }
                
                //Insertamos el registro del vale de asignación
                sql = "insert into vales (Folio,Numero,Año,tipo_vale,fecha_vale,id_empleado) values('"+folio+"',"+num+","+year+",'Vale de resguardo','"+fecha+"',"+id_empleado+");";
                st.executeUpdate(sql);
                
                sql = "select area from empleados where id_empleado = "+id_empleado+";";
                rs = st.executeQuery(sql);
                rs.next();
                String ubicacion = rs.getString(1);
                
                for(int i = 0; i < Claves.length; i++){
                    //Insertamos los datos en la tabla "detalle_vale"
                    sql = "insert into detalle_vale (id_vale,id_producto,cantidad,estado)values('"+folio+"-"+num+"-"+year+"','"+Claves[i]+"',"+Cantidad[i]+",'Asignado');";
                    st.executeUpdate(sql);
                    //Actualizamos la ubicación del producto
                    sql = "update inventario set ubicacion = '"+ubicacion+"' where concat(Folio,'-',Numero,Extension) = '"+Claves[i]+"';";
                    st.executeUpdate(sql);
                }//for
                
                conexion.close();
                return true;
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(ManagerAsignarEquipo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }//asignarInventario
    
    //Este método es para llenar el combo solamente con los empleados que tengan asignaciones (vales de resguardo) y que todavia no hayan sido
    //recogidos (vales de recolección)
    public void getEmpleadosAsignacion(JComboBox combo) {
        try{
           
            String sql = "select concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) as Empleado from empleados e " +
                         "where e.id_empleado in ( " +
                         "select e.id_empleado as Empleado from empleados e " +
                         "where e.id_empleado in ( " +
                         "select v.id_empleado from vales v " +
                         "inner join empleados e on (e.id_empleado = v.id_empleado) " +
                         "inner join detalle_vale dv on (dv.id_vale = concat(v.Folio,'-',v.Numero,'-',v.Año)) " +
                         "inner join inventario i on (dv.id_producto = concat(i.Folio,'-',i.Numero,i.Extension))) " +
                         ") group by concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m);";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo.addItem(rs.getObject(1).toString());
            }
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los nombres de los empleados para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }//Obtiene todas los nombres de los empleados que tienen productos asignados
    
    /*Este método es para obtener los productos que fueron asignados a un empleado, se mostraran todos los productos de todos los vales que esten a su
    nombre, y solo se mostaran aquellos productos que aun tengan en su estado "Asignado" (significa que aun no los entregan en su vale de recolección).
    Se genera la tabla con su respectiva información y en la columna principal se le asigna un checkbox para marcar los productos que quiera entregar.*/
    public DefaultTableModel getInventarioEmpleadoAsignaciones(String empleado) {
            
        DefaultTableModel table = new DefaultTableModel();
        JTable checks = new JTable();
        JScrollPane scroll = new JScrollPane();
        conexion = db.getConexion();
        
        //Creamos la tabla con las caracterisiticas que necesitamos
        checks.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        checks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            //Declaramos el titulo de las columnas
            new String [] {
                "Entregar","Vale", "Clave", "Nombre corto", "Descripción","Marca","No. Serie","Modelo", "Observaciones","Ubicación Actual","Nueva Ubicación"
            }
        ){
            //El tipo que sera cada columna, la primera columna un checkbox y los demas seran objetos
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            //Esto es para indicar que columnas dejaremos editar o no
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false,false, false,true,false,true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
            
          }
        
        );
        //Agregamos un scroll a la tabla
        scroll.setViewportView(checks);
        scroll.setBounds(30, 130, 1110, 500);
        
        /*FALTA AGREGAR UN COMBOBOX EN LA COLUMNA DE UBICACIÓN*/
        table = (DefaultTableModel)checks.getModel();
        try {
            //Obtiene los productos asignados de acuerdo al empleado (Inventario)
            String sql = "select concat(v.Folio,'-',v.Numero,'-',v.Año), dv.id_producto, ig.nombre_prod,ig.descripcion,ig.marca,ig.no_serie,ig.modelo,ig.observaciones,ig.ubicacion from vales v "
                    + "inner join detalle_vale dv on (dv.id_vale = concat(v.Folio,'-',v.Numero,'-',v.Año)) "
                    + "inner join inventario ig on (dv.id_producto = concat(ig.Folio,'-',ig.Numero,ig.Extension)) "
                    + "inner join empleados e on (e.id_empleado = v.id_empleado) "
                    + "where concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m)= '"+empleado+"' and dv.estado = 'Asignado' "
                    + "order by concat(v.Folio,'-',v.Numero,'-',v.Año);";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[11];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                datos[0] = Boolean.FALSE;
                for(int i = 1;i<10;i++){
                    datos[i] = rs.getObject(i);
                }//Llenamos las columnas por registro
                datos[10] = "Selecciona la nueva ubicación...";
                table.addRow(datos);//Añadimos la fila
            }//while
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener la información de los productos que fueron asignados a un responsable en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            return table;
        }

    }//getInventarioEmpleadoAsignaciones
    
    /*Este método es para regresar al inventario los productos que fueron marcados, se cambia el estus de los productos y tambien de la 
    tabla detalle_vales, para que ya no aparezca asignada*/
    public boolean recoleccionInventario(String []idVales,String []Claves,String []Ubicaciones,String []Observaciones){
        
        try{
                String sql = "";
                conexion = db.getConexion();
                Statement st = conexion.createStatement();
                ResultSet rs;
                
                //Obtenemos la fecha del sistema
                sql = "select now();";
                rs = st.executeQuery(sql);
                rs.next();
                String fecha = rs.getString(1); 
                
                //Actualizamos el detalle del vale, para marcar los productos que fueron entregados
                for(int i = 0; i< idVales.length;i++){
                    sql = "update detalle_vale set estado = 'Entregado', fecha_entrega = '"+fecha+"' where id_producto = '"+Claves[i]+"' and id_vale = '"+idVales[i]+"';";
                    st.executeUpdate(sql);
                }//for
                
                //Actualizamos la información del producto, en donde se vuelve disponible, se agregan observaciones(si las tuvo) y su ubicación
                for(int i = 0; i< idVales.length;i++){
                    sql = "update inventario set estatus = 'Disponible', ubicacion = '"+Ubicaciones[i]+"', observaciones = '"+Observaciones[i]+"' where concat(Folio,'-',Numero,Extension) = '"+Claves[i]+"';";
                    st.executeUpdate(sql);
                }//for                
                
                conexion.close();
        } //try  
        catch (SQLException ex) {
            Logger.getLogger(ManagerAsignarEquipo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
        
    }//Regresa los productos a su estado orignal (estatus y/o cantidad)
    
    public DefaultTableModel getInventarioEmpleadoAsignacionesPersonales(String usuario) {
            DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Clave");
            table.addColumn("Producto");
            table.addColumn("Descripción");
            table.addColumn("Marca");
            table.addColumn("No. Serie");
            table.addColumn("Modelo");
            table.addColumn("Observaciones");
            
            //Obtiene los productos asignados de acuerdo al empleado
            String sql = "select dv.id_producto, ig.nombre_prod,ig.descripcion,ig.marca,ig.no_serie,ig.modelo,ig.observaciones from vales v "
                       + "inner join detalle_vale dv on (dv.id_vale = concat(v.Folio,'-',v.Numero,'-',v.Año)) "
                       + "inner join inventario ig on (dv.id_producto = concat(ig.Folio,'-',ig.Numero,ig.Extension)) "
                       + "inner join empleados e on (e.id_empleado = v.id_empleado) "
                       + "inner join user u on (u.id_empleado = e.id_empleado) "
                       + "where u.id_user = '"+usuario+"' and dv.estado = 'Asignado' order by ig.Numero;";
            
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

    }//getInventarioEmpleadoAsignacionesPersonales
    
    public DefaultTableModel getInventarioEmpleadoAsignacionesPersonalesG(String usuario) {
            DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Vale");
            table.addColumn("Clave");
            table.addColumn("Producto");
            table.addColumn("Descripción");
            table.addColumn("Observaciones");
            table.addColumn("Cantidad");
            
            //Obtiene los productos asignados de acuerdo al empleado
            String sql = "select v.id_vale,dv.id_producto, ig.nombre_prod,ig.descripcion,ig.observaciones,dv.cantidad from vales v " +
                         "inner join detalle_vale dv on (dv.id_vale = v.id_vale) " +
                         "inner join inventario_granel ig on (dv.id_producto = concat(ig.Folio,'-',ig.Numero,ig.Extension)) " +
                         "inner join user u on (u.id_user = v.id_user) " +
                         "where u.id_user = '"+usuario+"';";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[6];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<6;i++){
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

    }//getInventarioEmpleadoAsignacionesPersonales
    
    public boolean registro_Solicitud(int idVale,String idProd, String tipo,String user,String motivo,int cantidad){
        
        try {
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Creamos la variable para guardar el resultado de las consultas
            ResultSet rs;
            
            //Obtenemos la fecha del sistema
            String sql = "select now();";
            rs = st.executeQuery(sql);
            rs.next();
            String fecha = rs.getString(1); 
            
            //Registramos la solicitud
            sql = "insert into Solicitudes (tipo_solicitud,id_user,motivo,cantidad,fecha_solicitud,estado) "
                        +"values('"+tipo+"','"+user+"','"+motivo+"',"+cantidad+",'"+fecha+"','SOLICITUD PERSONAL');";
            st.executeUpdate(sql);
            
            //Cambiamos el estatus del equipo seleccionado
            sql = "update detalle_vale set estado = 'SOLICITUD' where id_producto = '"+idProd+"' and id_vale = "+idVale+";";
            st.executeUpdate(sql);
            
            //Buscamos el id de la solicitud
            sql = "select id_solicitud from Solicitudes where fecha_solicitud = '"+fecha+"';";
            rs = st.executeQuery(sql);
            rs.next();
            String idSol = rs.getString(1); 
            
            //Realizamos el registro de los detalles de la solicitud
            sql = "insert into Detalle_solicitud values("+idSol+",'"+idProd+"');";
            st.executeUpdate(sql);
            
            //Cerramos la conexión
            conexion.close();
            return true;
            
        } catch (SQLException ex) {
            System.out.printf("Error al insertar la solicitud en SQL");
            Logger.getLogger(ManagerSolicitud.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//registro_solicitud
    
    public boolean actualizar_Solicitud(int idSol,String empleado){
        
        try {
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            
            //Actualizamos la solicitud
            String sql = "update solicitudes set estado = 'PENDIENTE PERSONAL' where id_solicitud = "+idSol+"";
            st.executeUpdate(sql);
            
            //Obtenemos el id del producto
            sql = "select id_producto from detalle_solicitud where id_solicitud = "+idSol+";";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String idProd = rs.getString(1);
            System.out.println("encontre el producto: "+idProd);
            
            //Obtenemos el id del vale
            sql = "select v.id_vale from vales v " +
                   "inner join detalle_vale dv on (dv.id_vale = v.id_vale) " +
                   "inner join inventario ig on (dv.id_producto = ig.id_producto) " +
                   "inner join user u on (u.id_user = v.id_user) " +
                   "inner join empleados e on (e.id_empleado = u.id_empleado) " +
                   "where concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m) = '"+empleado+"' and dv.estado = 'SOLICITUD' and ig.id_producto = '"+idProd+"';";
            rs = st.executeQuery(sql);
            rs.next();
            int idVale = rs.getInt(1);
                        System.out.println("encontre el vale: "+idVale);
            //Actualizamos el estatus del producto
            sql = "update detalle_vale set estado = 'PENDIENTE' where id_producto = '"+idProd+"' and id_vale = "+idVale+";";
            st.executeUpdate(sql);
                        System.out.println("Actualice todo");

            //Cerramos la conexión
            conexion.close();
            return true;
            
        } catch (SQLException ex) {
            System.out.printf("Error al insertar la solicitud en SQL");
            Logger.getLogger(ManagerSolicitud.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        
    }//actualizar_Solicitud
    
    public DefaultTableModel getInventarioStockMin() {
            DefaultTableModel table = new DefaultTableModel();

        try {
            table.addColumn("Clave");
            table.addColumn("Producto");
            table.addColumn("Descripción");
            table.addColumn("Observaciones");
            table.addColumn("Cantidad");
            table.addColumn("Estado");
            
            //Obtiene los productos que tienen su stock menor o igual que el stock minimo
            String sql = "select concat(Folio,'-',Numero,Extension),nombre_prod,descripcion,observaciones,stock,estatus from inventario_granel where stock_min >= stock;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[6];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                for(int i = 0;i<6;i++){
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

    }//getInventarioEmpleadoAsignacionesPersonales
    
    //Este método es para actualizar la cantidad del producto a granel con la cantidad ingresada
    public boolean actualizarStock(String codigo,int cantidad){
        
        try{
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            
            //Actualizamos el stock y el estado si el stock es igual a 0
            String sql = "update inventario_granel set stock = stock + "+cantidad+" where concat(Folio,'-',Numero,Extension) = '"+codigo+"' and stock > 0;";
            st.executeUpdate(sql);
            
            //Actualizamos el stock y el estado si el stock es igual a 0
            sql = "update inventario_granel set stock = "+cantidad+", estatus = 'Disponible' where concat(Folio,'-',Numero,Extension) = '"+codigo+"' and stock = 0;";
            st.executeUpdate(sql);
            
            conexion.close();
            return true;
        }catch(SQLException ex){
            System.out.printf("Error al querer actualizar el stock SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }//actualizarStock
    
}//class
