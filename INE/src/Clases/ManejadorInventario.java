package Clases;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

/**
 *
 * @author kevin
 */
public class ManejadorInventario {
    private		Connection			conexion;
    private		Conexion			db;
    private		ManagerInventario	manager_inventario;
    
    public ManejadorInventario(){
        db = new Conexion();
        manager_inventario = new ManagerInventario();
    }//constructor
    
    public DefaultTableModel getInventarioG() {
        // Objeto de la tabla
        DefaultTableModel table = new DefaultTableModel();
		
		// Se prepara la llamada al SP, que se destruye al finalizar el TRY-CATCH
        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_infoInventarioGranel`(?, ?}")){
			// Se agregan las columnas al objeto de la tabla
            table.addColumn("Clave");
            table.addColumn("Producto");
            table.addColumn("Descripción");
            table.addColumn("Almacén");
            table.addColumn("Marca");
            table.addColumn("Observaciones");
            table.addColumn("Stock");
            
			// Se agregan los parámetros de búsqueda al SP
            cs.setString(1, "Disponible");
			cs.setNull(2, 0);
			
			// Ejecución del SP
            ResultSet rs = cs.executeQuery();
			
			// Llenar tabla
			Object datos[] = new Object[7];

            while (rs.next()) {
				// Llenamos las columnas por registro
                for(int i = 0; i < 7; i++){
                    datos[i] = rs.getObject(i + 1);
                }
				// Añadimos la fila
                table.addRow(datos);
           }//while
			
        } catch (SQLException ex) {
            System.err.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return table;
        }

    }//getInventarioG

    //Este método retorna la tabla de inventario normal solo con los productos disponibles, esto para mostrarse en el Manejador Inventario
    //cuando se quiere realizar una asignación
    public DefaultTableModel getInventarioParaAsignacion(String nomenclatura) {
        // Objeto para la tabla
        DefaultTableModel table = new DefaultTableModel();
		
		// Se prepara la llamada al SP, que se destruye al finalizar el TRY-CATCH
        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_inventarioParaAsignacion`(?, ?)}")){
            // Se agregan las columnas al objeto de la tabla
            table.addColumn("Clave");
            table.addColumn("Nombre_corto");
            table.addColumn("Descripción");
            table.addColumn("Ubicación");
            table.addColumn("Marca");
            table.addColumn("No. Serie");
            table.addColumn("Modelo");
            
			// Se agregan los parámetros de búsqueda al SP
            cs.setString(1, "Disponible");
			cs.setString(2, nomenclatura);
			
			// Ejecución del SP
			ResultSet rs = cs.executeQuery();
			
            //Llenar tabla
            Object datos[] = new Object[7];

            while (rs.next()) {
				//Llenamos las columnas por registro
                for(int i = 0; i < 7; i++){
                    datos[i] = rs.getObject(i + 1);
                }
				//Añadimos la fila
                table.addRow(datos);
           }//while
        } catch (SQLException ex) {
            System.out.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return table;
        }

    }//getInventarioParaAsignación
    
	public int cantidadInventarioG(String ID_Producto) {
		// Se prepara la llamada al SP, que se destruye al finalizar el TRY-CATCH
		try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_stockInvGranel`(?)}")) {
			// Se agregan el parámetro de búsqueda al SP
			cs.setString(1, ID_Producto);
			// Ejecución del SP
			ResultSet rs = cs.executeQuery();
			// Retorno del valor obtenido
			if (rs.next()) return rs.getInt("res");

		} catch (SQLException ex) {
			System.err.printf("Error al consultar el inventario en SQL");
			Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
		}
		return - 1;
	}//cantidadInventarioG

	public int productosSuficientesInventarioG(String ID_Producto, int Cantidad) {
		// Se prepara la llamada al SP, que se destruye al finalizar el TRY-CATCH
		try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_update_stockInvGranel`(?, ?)}")) {
			// Se agregan el parámetro de búsqueda al SP
			cs.setString(1, ID_Producto);
			cs.setInt(2, Cantidad);
			// Ejecución del SP
			ResultSet rs = cs.executeQuery();
			// Retorno del valor obtenido
			if (rs.next()) return rs.getInt("res");

		} catch (SQLException ex) {
			System.err.printf("Error al consultar el inventario en SQL");
			Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
		}
		return - 1;
	}//productosSuficientesInventarioG

	public int productosIgualesInventarioG(String ID_Producto, int Cantidad) {
		// Se prepara la llamada al SP, que se destruye al finalizar el TRY-CATCH
		try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_update_stockInvGranel`(?, ?)}")) {
			// Se agregan los parámetros de búsqueda al SP
			cs.setString(1, ID_Producto);
			cs.setInt(2, Cantidad);
			// Ejecución del SP
			ResultSet rs = cs.executeQuery();
			// Retorno del valor obtenido
			if (rs.next()) return rs.getInt("res");

		} catch (SQLException ex) {
			System.err.printf("Error al consultar el inventario en SQL");
			Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
		}
		return - 1;
	}//productosIgualesInventarioG
    
    //Este método es para cancelar la acción de asignación de uno o mas productos en la pestaña de manejador de inventario, se usa cuando
    //cancelas un producto, cuando presionas el boton cancelar o cuando se cierra la ventana y quedaron los productos sin generar el vale
	public boolean regresarInventario(String[] Claves, int[] Cantidad){
		// Se prepara la llamada al SP, que se destruye al finalizar el TRY-CATCH
		try (PreparedStatement ps = db.getConexion().prepareStatement("{CALL `ine`.`usp_update_regresarInventarios`(?, ?)}")) {
			// Permite saber cuantos registros se han actualizado
			int row_count = 0;
			// Actualización de los productos
			for(int i = 0; i < Claves.length; i++){
				// Se agregan los parámetros de búsqueda al SP
				ps.setString(1, Claves[i]);
				ps.setInt(2, Cantidad[i]);
				// Ejecución del SP y añade al contador el número de registros afectados
				row_count = row_count + ps.executeUpdate();
			}// for

			// TRUE el número de filas afectadas es diferente de cero
			return row_count != 0;
		} //try
		catch (SQLException ex) {
			System.err.printf("Error al actualizar el inventario en SQL");
			Logger.getLogger(ManagerDocumentos.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}// Regresa los productos a su estado orignal (estatus y/o cantidad)
    
    //Este método realiza la salida de almcen ya con productos autorizados
	public boolean autorizarSalidaAlmacen(String[] Claves, int[] Cantidad, String Usuario, String ID){
		// Se preparan la llamadas a los SPs, que se destruyen al finalizar el TRY-CATCH
		try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_update_autorizarSalida`(?, ?)}");
			 PreparedStatement ps = db.getConexion().prepareStatement("{CALL `ine`.`usp_update_autorizarDetalleSalida`(?, ?, ?)}")) {

			// Se agregan los parámetros de búsqueda a primer SP para hacer UPDATE en SolicitudSalida
			cs.setString(1, Usuario);
			cs.setString(2, ID);

			// Si no se actualiza ningún registro, retorna FALSE
			if(cs.executeUpdate() == 0)
				return false;

			//Ahora actualizamos los registros de Detalle_SolicitudSalida para agregar las cantidades que fueron autorizadas para cada productos
			int row_count = 0;

			for(int i = 0; i < Claves.length; i++){
				// Se agregan los parámetros de búsqueda al segundo SP, estos cambian con cada iteración
				ps.setString(1, ID);
				ps.setString(2, Claves[i]);
				ps.setInt(3, Cantidad[i]);
				// Se cuentan el número de registros afectados
				row_count = row_count + ps.executeUpdate();
			}//for

			// Si no se afecta ningún registro, retorna FALSE
			return row_count != 0;

		} catch (SQLException ex) {
			Logger.getLogger(ManagerDocumentos.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}//autorizarSalidaAlmacen
    
    //Este método es para registrar tanto el vale de resguardo como el de recolección
	public String registrarVale(String Empleado, String Folio){
		// Se prepara la llamada al SP, que se destruye al finalizar el TRY-CATCH
		try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_insert_asignacionVale`(?, ?)}"))  {
			
			// Se agregan los parámetros de ejecución al SP
			cs.setString(1, Empleado);
			cs.setString(2, Folio);
			
			// Ejecución del SP
			ResultSet rs = cs.executeQuery();
			
			StringBuilder sb = new StringBuilder();
						
			if (rs.next()){
				sb.append(rs.getString("ID_Vale"));
				sb.append(",,");
				sb.append(rs.getString("ID_Empleado"));
				sb.append(",,");
				sb.append(rs.getString("Area_Empleado"));
			}
			
			// Retorno del una cadena con la consulta realizada
			return sb.toString();
			
		} catch (SQLException ex) {
			System.err.printf("Error al actualizar el inventario en SQL");
 			Logger.getLogger(ManagerDocumentos.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
    }//registrarVale
    
    //Este método realiza el resguardo, en donde todos los productos seleccionados se le asignan a un responsable
    public boolean asignarInventario(String[] Claves, int[] Cantidad, String Empleado, String Folio){
		
		try (PreparedStatement ps = db.getConexion().prepareStatement("{CALL `ine`.`usp_insert_asignacionInventario`(?, ?, ?, ?)}")) {

            String values = registrarVale(Empleado, Folio);
			String[] array = values.split(",,");
			int row_count = 0;
			
			for(int i = 0; i < Claves.length; i++){
				ps.setString(1, array[0]);
				ps.setString(2, Claves[i]);
				ps.setInt(3, Cantidad[i]);
				ps.setString(3, array[2]);
				
				row_count = row_count + ps.executeUpdate();
			}//for
			
			return row_count != 0;
		} catch (SQLException ex) {
			Logger.getLogger(ManagerDocumentos.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}//asignarInventario
    
    //Este método es para llenar el combo solamente con los empleados que tengan asignaciones (vales de resguardo) y que todavia no hayan sido
    //recogidos (vales de recolección)
    public void getEmpleadosAsignacion(JComboBox combo) {
        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_empleadosAsignacion`()}")){

            ResultSet rs = cs.executeQuery();
			
            while(rs.next()){
                combo.addItem(rs.getObject(1).toString());
            }
			
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
                "Entregar","Vale", "Clave", "Nombre corto", "Descripción","Marca","No. Serie","Modelo","Color", "Observaciones","Ubicación Actual","Nueva Ubicación"
            }
        ){
            //El tipo que sera cada columna, la primera columna un checkbox y los demas seran objetos
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            //Esto es para indicar que columnas dejaremos editar o no
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false,false, false,false,true,false,true
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
            String sql = "select concat(v.Folio,'-',v.Numero,'-',v.Año), dv.id_producto, ig.nombre_prod,ig.descripcion,ig.marca,ig.no_serie,ig.modelo,ig.color,ig.observaciones,ig.ubicacion from vales v "
                    + "inner join detalle_vale dv on (dv.id_vale = concat(v.Folio,'-',v.Numero,'-',v.Año)) "
                    + "inner join inventario ig on (dv.id_producto = concat(ig.Folio,'-',ig.Numero,ig.Extension)) "
                    + "inner join empleados e on (e.id_empleado = v.id_empleado) "
                    + "where concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m)= '"+empleado+"' and dv.estado = 'Asignado' "
                    + "order by concat(v.Folio,'-',v.Numero,'-',v.Año);";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            Object datos[] = new Object[12];
            ResultSet rs = st.executeQuery(sql);

            //Llenar tabla
            while (rs.next()) {

                datos[0] = Boolean.FALSE;
                for(int i = 1;i<11;i++){
                    datos[i] = rs.getObject(i);
                }//Llenamos las columnas por registro
                datos[11] = "Selecciona la nueva ubicación...";
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
            Logger.getLogger(ManagerDocumentos.class.getName()).log(Level.SEVERE, null, ex);
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
