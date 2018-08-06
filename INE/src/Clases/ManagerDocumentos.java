/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kevin
 */
public class ManagerDocumentos {
    
    private Connection conexion;
    private Conexion db;

    public ManagerDocumentos(){
        db = new Conexion();
    }
    
    public DefaultTableModel productosParaAsignar(String status){

        JTable checks = new JTable();
        JScrollPane scroll = new JScrollPane();
        conexion = db.getConexion();
        
        DefaultTableModel table;
        
        //Creamos la tabla con las caracterisiticas que necesitamos
        checks.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        checks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            //Declaramos el titulo de las columnas
            new String [] {
                "¿Cuáles se van?",
                "Clave",
                "Nombre corto",
                "No. Serie",
                "Descripción",
                "Observaciones",
                "Fecha",
            }
        ){
            //El tipo que sera cada columna, la primera columna un checkbox y los demas seran objetos
            Class[] types = new Class [] {
                java.lang.Boolean.class, 
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class
            };

			@Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            //Esto es para indicar que columnas dejaremos editar o no
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false
            };

			@Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
            
          }
        
        );
        //Agregamos un scroll a la tabla
        scroll.setViewportView(checks);
        scroll.setBounds(30, 130, 1110, 500);
        
        table = (DefaultTableModel)checks.getModel();

        try {
            
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Cambiamos el estatus del equipo seleccionado
            String sql = "select pa.ID_Producto, i.nombre_prod, i.no_serie, i.descripcion, i.observaciones, date(pa.Fecha_Seleccion) from productos_asignados pa "
                       + "inner join inventario i on(concat(i.Folio,'-',i.Numero,i.Extension) = pa.ID_Producto) "
                       + "where pa.Status = '"+status+"' and pa.Salida = 0;";
            
            Object datos[] = new Object[7];
            ResultSet rs = st.executeQuery(sql);          
            //Llenar tabla
            while (rs.next()) {
                datos[0] = false;
                for(int i = 1;i<7;i++){
                    datos[i] = rs.getObject(i);
                }//Llenamos las columnas por registro
                table.addRow(datos);//Añadimos la fila
           }//while
            
            conexion.close();
        } catch (SQLException ex) {
            System.err.printf("Error al obtener los datos de los productos_asignados en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return table;
        }

    }//productosParaAsignar
    
    public DefaultTableModel productosParaAsignarMenosInfo(String status){

        JTable checks = new JTable();
        JScrollPane scroll = new JScrollPane();
        conexion = db.getConexion();
        
        DefaultTableModel table;
        
        //Creamos la tabla con las caracterisiticas que necesitamos
        checks.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        checks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            //Declaramos el titulo de las columnas
            new String [] {
                "¿Cuáles se van?",
                "Clave",
                "Nombre corto",
                "No. Serie",
                "Fecha",
            }
        ){
            //El tipo que sera cada columna, la primera columna un checkbox y los demas seran objetos
            Class[] types = new Class [] {
                java.lang.Boolean.class, 
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class
            };

			@Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            //Esto es para indicar que columnas dejaremos editar o no
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false
            };

			@Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
            
          }
        
        );
        //Agregamos un scroll a la tabla
        scroll.setViewportView(checks);
        scroll.setBounds(30, 130, 1110, 500);
        
        table = (DefaultTableModel)checks.getModel();

        try {
            
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Cambiamos el estatus del equipo seleccionado
            String sql = "select pa.ID_Producto, i.nombre_prod, i.no_serie, date(pa.Fecha_Seleccion) from productos_asignados pa "
                       + "inner join inventario i on(concat(i.Folio,'-',i.Numero,i.Extension) = pa.ID_Producto) "
                       + "where pa.Status = '"+status+"' and pa.Salida = 0;";
            
            Object datos[] = new Object[5];
            ResultSet rs = st.executeQuery(sql);          
            //Llenar tabla
            while (rs.next()) {
                datos[0] = false;
                for(int i = 1;i<5;i++){
                    datos[i] = rs.getObject(i);
                }//Llenamos las columnas por registro
                table.addRow(datos);//Añadimos la fila
           }//while
            
            conexion.close();
        } catch (SQLException ex) {
            System.err.printf("Error al obtener los datos de los productos_asignados con menos info en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return table;
        }

    }//productosParaAsignarMenosInfo
    
    //Este método es para crear el documento y para asignar los productos que hayan sido seleccionados(si es que los hubo).
    public boolean crearDocumento(String[] IDs, Boolean[] cambio, String documento) {

        try{
			
            String sql = "";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            
            //Obtenemos la fecha para la creación del documento
            sql = "select date(now());";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String fecha = rs.getString(1);
            
            //Creamos el documento
            sql = "insert into documentos (Clave,Fecha_Creacion)values('"+documento+"','"+fecha+"');";
            st.executeUpdate(sql);
            
            //Obtenemos el id del documento que creamos
            sql = "select ID_Documento from documentos where Clave = '"+documento+"' and Fecha_Creacion = '"+fecha+"' order by ID_Documento desc limit 1;";
            rs = st.executeQuery(sql);
            rs.next();
            int id = rs.getInt(1);
            
            //Recorremos todos los datos para ver cuales fueron marcados y cuales no
            for(int i = 0; i < IDs.length; i++){
                //Si fue marcado cambiamos el status de salida a 1 y la relación documento-producto
                if(cambio[i]){
                    //Marcamos que ya fue seleccionado para un documento
                    sql = "update productos_asignados set Salida = 1 where ID_Producto = '"+IDs[i]+"';";
                    st.executeUpdate(sql);
                    //Hacemos la relación documento-producto
                    sql = "insert into inv_docs (ID_Producto,ID_Documento)values('"+IDs[i]+"',"+id+");";
                    st.executeUpdate(sql);
                }
            }//for
			
            return true;

        } catch (Exception ex) {
            System.out.println("Error al crear el documento y asignar los productos");
            return false;
        }

    }//crearDocumento
    
    //Este método es para axenar los nuevos productos al documento
    public boolean anexarAlDocumento(String[] IDs, Boolean[] cambio,int id_documento) {

        try{
			
            String sql = "";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            
            //Recorremos todos los datos para ver cuales fueron marcados y cuales no
            for(int i = 0; i < IDs.length; i++){
                //Si fue marcado cambiamos el status de salida a 1 y la relación documento-producto
                if(cambio[i]){
                    //Marcamos que ya fue seleccionado para un documento
                    sql = "update productos_asignados set Salida = 1 where ID_Producto = '"+IDs[i]+"';";
                    st.executeUpdate(sql);
                    //Hacemos la relación documento-producto
                    sql = "insert into inv_docs (ID_Producto,ID_Documento)values('"+IDs[i]+"',"+id_documento+");";
                    st.executeUpdate(sql);
                }
            }//for
			
            return true;

        } catch (Exception ex) {
            System.out.println("Error al anexar los nuevos productos al documento en SQL");
            return false;
        }

    }//anexarAlDocumento
    
    //Este método es para finalizar un documento
    public boolean finalizarDocumento(int id_documento) {

        try{
			
            String sql = "";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            
            //Obtenemos la fecha para la creación del documento
            sql = "select date(now());";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String fecha = rs.getString(1);
            
            //Le damos la fecha de salida al documento
            sql = "update documentos set Fecha_Salida = '"+fecha+"' where ID_Documento = '"+id_documento+"';";
            st.executeUpdate(sql);
			
            return true;

        } catch (Exception ex) {
            System.out.println("Error al finalizar el documento en SQL");
            return false;
        }

    }//finalizarDocumento
    
    //Este método es para actualizar el estatus del producto a asignado porque ya se selecciono como posible asignación a algun empleado,
    //sin embargo en esta parte aun no se realiza el vale de resguardo, aqui es simplemente seguridad de cuando se seleccione algun producto 
    //del inventario para que alguien mas no intente asignarselo a otro empleado
    public boolean asignarEquipo(String clave){
        try {
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Cambiamos el estatus del equipo seleccionado
            String sql = "update Inventario set estatus = 'Asignado' " +
                         "where concat(Folio,'-',Numero,Extension) = '"+clave+"';";
            st.executeUpdate(sql);
            //Cerramos la conexión
            conexion.close();
            return true;
            
        } catch (SQLException ex) {
            System.err.printf("Error al intentar actualizar el estauts del producto a \"asignado\" en SQL ");
            Logger.getLogger(ManagerSolicitud.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
    }//AsingarEquipo
   
    //Este método es para mostrar la tabla con los documentos que aún no han marcado la salida de ellos
    public DefaultTableModel getDocumentos() {
        //No dejamos editar ninguna celda
        DefaultTableModel table = new DefaultTableModel();

        try {
            // Se añaden los campos a la tabla
            table.addColumn("Clave");
            table.addColumn("Fecha de inicio");
            table.addColumn("Productos asignados");
			
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Cambiamos el estatus del equipo seleccionado
            String sql = "select concat(d.Clave,'-',d.ID_Documento),d.Fecha_Creacion, count(id.ID_Documento) from documentos d "
                       + "left join inv_docs id on (id.ID_Documento = d.ID_Documento) "
                       + "where d.Fecha_Salida is null group by id.ID_Documento;";
            
            Object datos[] = new Object[3];
            ResultSet rs = st.executeQuery(sql);          
            //Llenar tabla
            while (rs.next()) {
                for(int i = 0;i<3;i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro
                table.addRow(datos);//Añadimos la fila
           }//while
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los datos de la tabla de documentos en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
		return table;
    }//getDocumentos
    
    //Este método es para mostrar la tabla con los documentos de acuerdo a un filtro (Todos, Finalizados, En selección)
    public DefaultTableModel getDocumentosFiltro(String filtro) {
        //No dejamos editar ninguna celda
        DefaultTableModel table = new DefaultTableModel();

        try {
            // Se añaden los campos a la tabla
            table.addColumn("Clave");
            table.addColumn("Fecha de inicio");
            table.addColumn("Fecha de salida");
            table.addColumn("No. Acta");
            table.addColumn("Estatus");
			
            String sql;
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();

            //Cambiamos el estatus del equipo seleccionado
            switch(filtro){
                case "Finalizados":
                    sql = "select concat(Clave,'-',ID_Documento),Fecha_Creacion, Fecha_Salida,No_Acta from documentos where Fecha_Salida is not null;";
                    break;
                case "En selección":
                    sql = "select concat(Clave,'-',ID_Documento),Fecha_Creacion, Fecha_Salida,No_Acta from documentos where Fecha_Salida is null;";
                    break;
                default:
                    sql = "select concat(Clave,'-',ID_Documento),Fecha_Creacion, Fecha_Salida,No_Acta from documentos;";
                    break;
            }//switch
            
            
            Object datos[] = new Object[5];
            ResultSet rs = st.executeQuery(sql);          
            //Llenar tabla
            while (rs.next()) {
                for(int i = 0;i<4;i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro
                if(datos[2] == null){
                    datos[4] = "En selección";
                }else{
                    datos[4] = "Finalizado";
                }
                table.addRow(datos);//Añadimos la fila
           }//while
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los datos de la tabla de documentos en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
		return table;
    }//getDocumentos
    
    //Este método es para mostrar los productos que tiene anexados este documento para la Ventana_Documentos
    public DefaultTableModel getDocumentosProductos(int id_documento) {
        //No dejamos editar ninguna celda
        DefaultTableModel table = new DefaultTableModel();

        try {
            // Se añaden los campos a la tabla
            table.addColumn("Clave");
            table.addColumn("Nombre corto");
            table.addColumn("No. de serie");
            table.addColumn("Descripción");
            table.addColumn("Observaciones");
			
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Cambiamos el estatus del equipo seleccionado
            String sql = "select id.ID_Producto, i.nombre_prod, i.no_serie, i.descripcion, i.observaciones from inv_docs id "
                       + "inner join inventario i on (concat(i.Folio,'-',i.Numero,i.Extension) = id.ID_Producto) "
                       + "where id.ID_Documento = "+id_documento+";";
            
            Object datos[] = new Object[5];
            ResultSet rs = st.executeQuery(sql);          
            //Llenar tabla
            while (rs.next()) {
                for(int i = 0;i<5;i++){
                    datos[i] = rs.getObject(i+1);
                }//Llenamos las columnas por registro
                table.addRow(datos);//Añadimos la fila
           }//while
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los datos de la tabla de documentos en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            return table;
        }
    }//getDocumentosProductos
    
    //Este método es para mostrar los productos que tiene anexados este documento para la Ventana_ConsultaDocumentos
    public DefaultTableModel getDocumentosProductosMasInfo(int id_documento) {
        //No dejamos editar ninguna celda
        DefaultTableModel table = new DefaultTableModel();

        try {
            // Se añaden los campos a la tabla
            table.addColumn("Clave");
            table.addColumn("Nombre corto");
            table.addColumn("No. de serie");
            table.addColumn("Descripción");
            table.addColumn("Marca");
            table.addColumn("Modelo");
            table.addColumn("Color");
            table.addColumn("Factura");
            table.addColumn("Observaciones");
            table.addColumn("Fecha de salida");
			
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            //Cambiamos el estatus del equipo seleccionado
            String sql = "select id.ID_Producto, i.nombre_prod, i.no_serie, i.descripcion,i.marca,i.modelo,i.color,i.factura,i.observaciones,date(pa.Fecha_Seleccion) from inv_docs id "
                       + "inner join inventario i on (concat(i.Folio,'-',i.Numero,i.Extension) = id.ID_Producto) "
                       + "inner join productos_asignados pa on (pa.ID_Producto = id.ID_Producto) "
                        + "where id.ID_Documento = "+id_documento+";";
            
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
            System.out.printf("Error al obtener los datos de la tabla de documentos en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            return table;
        }
    }//getDocumentosProductos
    
    //Este método es para mostrar la tabla con los documentos de acuerdo a un filtro (Todos, Finalizados, En selección)
    public boolean asignarActa (String id,String acta) {
        boolean estado = false;
        try {
            String sql = "update documentos set No_Acta = '"+acta+"' where concat(Clave,'-',ID_Documento) = '"+id+"';";
            //Hacemos la conexión
            conexion = db.getConexion();
            //Creamos la variable para hacer operaciones CRUD
            Statement st = conexion.createStatement();
            st.executeUpdate(sql);            
            conexion.close();
            estado = true;
        } catch (SQLException ex) {
            System.out.printf("Error al asignar el no. de acta a un documento en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }//asignarActa
    
}//class
