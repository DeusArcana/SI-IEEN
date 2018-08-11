package Clases;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private final	Conexion	db;
    
    public ManejadorInventario(){
        db = new Conexion();
    }//constructor
    
	/**
	 * <h1>Obtener Inventario a Granel</h1>
	 * 
	 * <p>Obtiene todos aquellos productos disponibles del
	 * inventario a granel</p>
	 *
	 * @return <code>DefaultTableModel</code> con los productos de la tabla
	 */
	public DefaultTableModel getInventarioG() {
        // Objeto de la tabla
        DefaultTableModel table = new DefaultTableModel();
	
		// Se prepara la llamada al SP, que se destruye al finalizar el TRY-CATCH
        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_infoInventarioGranel`(?, ?}")){
			      // Se agregan las columnas al objeto de la tabla
            table.addColumn("No. Inventario");

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

	/**
	 * <h1>Obtener Inventario para Asignacion</h1>
	 * 
	 *  <p>Este método retorna la tabla de inventario normal solo con los productos disponibles, esto para mostrarse en el Manejador Inventario
	 *	cuando se quiere realizar una asignación</p>
	 * 
	 * @param nomenclatura de el ID del producto
	 * @return <code>DefaultTableModel</code> con los productos de inventario a granel
	 */
    public DefaultTableModel getInventarioParaAsignacion(String nomenclatura) {
        // Objeto para la tabla
        DefaultTableModel table = new DefaultTableModel();
		// Se prepara la llamada al SP, que se destruye al finalizar el TRY-CATCH
        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_inventarioParaAsignacion`(?, ?)}")){
            // Se agregan las columnas al objeto de la tabla
            table.addColumn("No. Inventario");
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
    
	/**
	 * <h1>Cantidad Inventario a Granel</h1>
	 * 
	 * <p>Obtiene el Stock actual de un producto dado su ID</p>
	 *
	 * @param ID_Producto 
	 * @return <code>Int</code> con el stock del producto
	 */
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

	/**
	 * <h1>Productos Suficientes Inventario a Granel</h1>
	 * 
	 * <p>Realiza la resta del stock según el producto dado</p>
	 *
	 * @param ID_Producto
	 * @param Cantidad
	 * @return <code>Int</code> con el Stock actual del producto
	 */
	public int productosSuficientesInventarioG(String ID_Producto, int Cantidad) {
		// Se prepara la llamada al SP, que se destruye al finalizar el TRY-CATCH
		try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_update_stockInvGranel`(?, ?, ?)}")) {
			// Se agregan el parámetro de búsqueda al SP
			cs.setString(1, ID_Producto);
			cs.setInt(2, Cantidad);
			cs.setInt(3, 1);
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

	/**
	 * <h1>Productos Iguales Inventario a Granel</h1>
	 * 
	 * <p>Realiza la resta del stock según el producto dado</p>
	 *
	 * @param ID_Producto
	 * @param Cantidad
	 * @return <code>Int</code> con el Stock actual del producto
	 */
	public int productosIgualesInventarioG(String ID_Producto, int Cantidad) {
		// Se prepara la llamada al SP, que se destruye al finalizar el TRY-CATCH
		try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_update_stockInvGranel`(?, ?, ?)}")) {
			// Se agregan los parámetros de búsqueda al SP
			cs.setString(1, ID_Producto);
			cs.setInt(2, Cantidad);
			cs.setInt(3, 1);
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
    
	/**
	 * <h1>Regresar Inventario</h1>
	 * 
	 * <p>Este método es para cancelar la acción de asignación de uno o mas productos en la pestaña de manejador de inventario, se usa cuando
	 * cancelas un producto, cuando presionas el boton cancelar o cuando se cierra la ventana y quedaron los productos sin generar el vale</p>
	 *
	 * @param Claves referente al ID del producto
	 * @param Cantidad a devolver
	 * @return
	 *		<ul>
	 *			<li><code>true</code> si las operaciones fueron correctas</li>
	 *			<li><code>false</code> si alguna operación no fue realizada</li>
	 *		</ul>
	 * 
	 */
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
    
	/**
	 * <h1>Autorizar Salida del Almacen</h1>
	 * 
	 * <p>Este método realiza la salida de almcen ya con productos autorizados</p>
	 *
	 * @param Claves
	 * @param Cantidad
	 * @param Usuario
	 * @param ID
	 * @return
	 *		<ul>
	 *			<li><code>true</code> si las operaciones fueron correctas</li>
	 *			<li><code>false</code> si alguna operación no fue realizada</li>
	 *		</ul>
	 */
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
    
	/**
	 * <h1>Registrar Vale</h1>
	 * 
	 * <p>Este método es para registrar tanto el vale de resguardo como el de recolección</p>
	 *
	 * @param Empleado - Nombre completo del Empleado
	 * @param Folio	- Tipo de Folio del que será el Vale
	 *		<ul>
	 *			<li><code>RES</code> - Vale de Resguardo</li>
	 *			<li><code>REC</code> - Vale de Recolección</li>
	 *		</ul>
	 * @return <code>String</code> formado por:
	 *		<ul>
	 *			<li><code>ID_Vale</code> del vale creado en la operacion</li>
	 *			<li><code>ID_Empleado</code> dado el nombre que se da como parámetro</li>
	 *			<li><code>Area_Empleado</code> dado el nombre del empleado dado como parámetro</li>
	 *		</ul>
	 */
	public String registrarVale(String Empleado, String Folio){
		// Se prepara la llamada al SP, que se destruye al finalizar el TRY-CATCH
		try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_insert_asignacionVale`(?, ?)}"))  {
			
			// Se agregan los parámetros de ejecución al SP
			cs.setString(1, Empleado);
			cs.setString(2, Folio);
			
			// Ejecución del SP
			ResultSet rs = cs.executeQuery();
			// Construccion del String
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
    
	/**
	 * <h1>Asignar Inventario</h1>
	 * 
	 * <p>Este método realiza el resguardo, en donde todos los productos seleccionados se le asignan a un responsable</p>
	 *
	 * @param Claves - ID de los productos a asignar
	 * @param Cantidad - a asignar en el <em>Detalle_Vale</em>
	 * @param Empleado - Nombre del empleado
	 * @param Folio - Folio del producto
	 * @return
	 *		<ul>
	 *			<li><code>true</code> si las operaciones fueron correctas</li>
	 *			<li><code>false</code> si alguna operación no fue realizada</li>
	 *		</ul>
	 */
    public boolean asignarInventario(String[] Claves, int[] Cantidad, String Empleado, String Folio){
		// Se prepara la llamada al SP, que se destruye al finalizar el TRY-CATCH
		try (PreparedStatement ps = db.getConexion().prepareStatement("{CALL `ine`.`usp_insert_asignacionInventario`(?, ?, ?, ?)}")) {
			// Se obtienen los valores para el SP
            String values = registrarVale(Empleado, Folio);
			String[] array = values.split(",,");
			int row_count = 0;
			
			// Ejecución del SP
			for(int i = 0; i < Claves.length; i++){
				ps.setString(1, array[0]);
				ps.setString(2, Claves[i]);
				ps.setInt(3, Cantidad[i]);
				ps.setString(3, array[2]);
				
				row_count = row_count + ps.executeUpdate();
			}//for
			
			// Retorna true si el número de filas afectadas es diferente de cero
			return row_count != 0;
		} catch (SQLException ex) {
			Logger.getLogger(ManagerDocumentos.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}//asignarInventario
    
	/**
	 * <h1>Obtener Empleados con Asignaciones</h1>
	 * 
	 * <p>Este método es para llenar el combo solamente con los empleados que tengan asignaciones
	 * (vales de resguardo) y que todavia no hayan sido recogidos (vales de recolección)</p>
	 *
	 * @param combo - en el que se rellenan los resultados obtenidos
	 */
    public void getEmpleadosAsignacion(JComboBox combo) {
        // Se prepara la llamada al SP, que se destruye al finalizar el TRY-CATCH
		try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_empleadosAsignacion`()}")){
			// Ejecución del SP
            ResultSet rs = cs.executeQuery();
			// Actualización de resultados
            while(rs.next()){
                combo.addItem(rs.getObject(1).toString());
            }
			
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los nombres de los empleados para ingresarlos al combo SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }//Obtiene todas los nombres de los empleados que tienen productos asignados
    
    //Este método es para llenar el combo solamente con los empleados que tengan asignaciones (vales de resguardo) y que todavia no hayan sido
    //recogidos (vales de recolección)
    public void getAñoEmpleadoAsignacion(JComboBox combo,String empleado) {
        try{
           
            String sql = "select v.Año from vales v "
                    + "inner join detalle_vale dv on (dv.id_vale = concat(v.Folio,'-',v.Numero,'-',v.Año)) "
                    + "inner join inventario ig on (dv.id_producto = concat(ig.Folio,'-',ig.Numero,ig.Extension)) "
                    + "inner join empleados e on (e.id_empleado = v.id_empleado) "
                    + "where concat(e.nombres,' ',e.apellido_p,' ',e.apellido_m)= '"+empleado+"' and dv.estado = 'Asignado' "
                    + "group by v.año order by v.Año desc;";
            conexion = db.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo.addItem(rs.getObject(1).toString());
            }
            
            conexion.close();
        } catch (SQLException ex) {
            System.out.printf("Error al obtener los años de acuerdo al empleado para ver los vales de esa fecha SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }//Obtiene todas los nombres de los empleados que tienen productos asignados
    
	/**
	 * <h1>Obtener Inventario Asignaciones a Empleados</h1>
	 * 
	 * <p>Este método es para obtener los productos que fueron asignados a un empleado.</p>
	 * 
	 * <p>Se mostraran todos los productos de todos los vales que esten a su nombre, 
	 * y solo se mostaran aquellos productos que aun tengan en su estado "Asignado" 
	 * (significa que aun no los entregan en su vale de recolección).</p>
	 * 
	 * <p>Se genera la tabla con su respectiva información y en la columna principal 
	 * se le asigna un checkbox para marcar los productos que quiera entregar.</p>
	 *
	 * @param Empleado - Nombre Completo
	 * @return <code>DefaultTableModel</code> con los resultados obtenidos de la consulta
	 */
    public DefaultTableModel getInventarioEmpleadoAsignaciones(String Empleado) {
            
        DefaultTableModel table;
        JTable checks = new JTable();
        JScrollPane scroll = new JScrollPane();
        
        //Creamos la tabla con las caracterisiticas que necesitamos
        checks.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        checks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            //Declaramos el titulo de las columnas
            new String [] {
                "Entregar",
				"Vale",
				"Clave",
				"Nombre corto",
				"Descripción",
				"Marca",
				"No. Serie",
				"Modelo",
				"Color",
				"Observaciones",
				"Ubicación Actual",
				"Nueva Ubicación"
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
                true,
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				true,
				false,
				true
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
        
        /*FALTA AGREGAR UN COMBOBOX EN LA COLUMNA DE UBICACIÓN*/
        table = (DefaultTableModel) checks.getModel();
		
        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_insert_productosAsignaciones`(?, ?)}")) {
            cs.setString(1, Empleado);
			      cs.setString(2, "Asignado");
            Object datos[] = new Object[12];
			
            ResultSet rs = cs.executeQuery();

            //Llenar tabla
            while (rs.next()) {
                datos[0] = Boolean.FALSE;
				//Llenamos las columnas por registro				
                for(int i = 1; i < 11; i++){
                    datos[i] = rs.getObject(i);
                }
				
                datos[11] = "Selecciona la nueva ubicación...";
				//Añadimos la fila
                table.addRow(datos);
            }//while

        } catch (SQLException ex) {
            System.out.printf("Error al obtener la información de los productos que fueron asignados a un responsable en SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return table;
        }

    }//getInventarioEmpleadoAsignaciones
    
	/**
	 * <h1>Recolección de Inventario</h1>
	 * 
	 * <p>Este método es para regresar al inventario los productos que fueron marcados,
	 * se cambia el estatus de los productos y tambien de la tabla <em>Detalle_Vale</em>, 
	 * para que ya no aparezca asignada</p>
	 *
	 * @param ID_Vales - de la tabla Detalle_Vale
	 * @param Claves - de los productos
	 * @param Ubicaciones - de los productos
	 * @param Observaciones - de los productos
	 * @return
	 *		<ul>
	 *			<li><code>true</code> si las operaciones fueron correctas</li>
	 *			<li><code>false</code> si alguna operación no fue realizada</li>
	 *		</ul>
	 */
    public boolean recoleccionInventario(String[] ID_Vales, String[] Claves, String[] Ubicaciones, String[] Observaciones){
        // Se prepara la llamada al SP, que se destruye al finalizar el TRY-CATCH
        try (PreparedStatement ps = db.getConexion().prepareStatement("{CALL `ine`.`usp_update_recoleccionInventario`(?, ?, ?, ?)}")){
            int row_count = 0;
			// Ejecución del SP
            for(int i = 0; i< ID_Vales.length;i++){
                ps.setString(1, ID_Vales[i]);
				ps.setString(2, Claves[i]);
				ps.setString(3, Ubicaciones[i]);
				ps.setString(4, Observaciones[i]);
				
				row_count = row_count + ps.executeUpdate();
            }//for
			
			// Si no se afecta ningún registro, retorna FALSE
			return row_count != 0;

        } catch (SQLException ex) {
            Logger.getLogger(ManagerDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }//Regresa los productos a su estado orignal (estatus y/o cantidad)
    
	/**
	 * <h1>Obtener Inventario Asignaciones a Usuarios</h1>
	 * 
	 * <p>Este método es para obtener los productos que fueron asignados a un empleado.</p>
	 * 
	 * <p>Se mostraran todos los productos de todos los vales que esten a su nombre, 
	 * y solo se mostaran aquellos productos que aun tengan en su estado "Asignado" 
	 * (significa que aun no los entregan en su vale de recolección).</p>
	 *
	 * @param Usuario - ID
	 * @return <code>DefaultTableModel</code> con los resultados obtenidos de la consulta
	 */
	public DefaultTableModel getInventarioEmpleadoAsignacionesPersonales(String Usuario) {
        DefaultTableModel table = new DefaultTableModel();

        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_empleadoAsignacionPersonal`(?, ?)}")) {
            table.addColumn("No. Inventario");
            table.addColumn("Producto");
            table.addColumn("Descripción");
            table.addColumn("Marca");
            table.addColumn("No. Serie");
            table.addColumn("Modelo");
            table.addColumn("Observaciones");
            
            cs.setString(1, Usuario);
			cs.setString(2, "Asignado");

			ResultSet rs = cs.executeQuery();
            Object datos[] = new Object[7];
            
            // Llenar tabla
            while (rs.next()) {
				// Llenamos las columnas por registro
                for(int i = 0; i < 7; i++){
                    datos[i] = rs.getObject(i + 1);
                }
				// Añadimos la fila
                table.addRow(datos);
            }// while
        } catch (SQLException ex) {
            System.out.printf("Error getTabla Inventario SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return table;
        }
    }// getInventarioEmpleadoAsignacionesPersonales
    
    public DefaultTableModel getInventarioEmpleadoAsignacionesPersonalesG(String Usuario) {
            DefaultTableModel table = new DefaultTableModel();

        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_empleadoAsignacionPersonalGrnl`(?)}")) {
            table.addColumn("Vale");
            table.addColumn("No. Inventario");
            table.addColumn("Producto");
            table.addColumn("Descripción");
            table.addColumn("Observaciones");
            table.addColumn("Cantidad");
            
            cs.setString(1, Usuario);
            Object datos[] = new Object[6];
            ResultSet rs = cs.executeQuery();

            //Llenar tabla
            while (rs.next()) {
				//Llenamos las columnas por registro
                for(int i = 0; i < 6; i++){
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

    }//getInventarioEmpleadoAsignacionesPersonales
    
    public boolean registro_Solicitud(int idVale, String idProd, String tipo, String user, String motivo, int cantidad){
        
        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_insert_registroSolicitud`(?, ?, ?, ?, ?, ?)}")) {
			cs.setInt(1, idVale);
			cs.setString(2, idProd);
			cs.setString(3, tipo);
			cs.setString(4, user);
			cs.setString(5, motivo);
			cs.setInt(6, cantidad);
			
            ResultSet rs = cs.executeQuery();
			
			if (rs.next()) return rs.getInt("res") == 1;
            
        } catch (SQLException ex) {
            System.out.printf("Error al insertar la solicitud en SQL");
            Logger.getLogger(ManagerSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }
		
        return false;
    }//registro_solicitud
    
    public boolean actualizar_Solicitud(int idSol,String empleado){
        
        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_update_registroSolicitud`(?, ?)}")){
            cs.setInt(1, idSol);
			cs.setString(2, empleado);
			
            ResultSet rs = cs.executeQuery();
			
			if (rs.next()) return rs.getInt("res") == 1;
            
        } catch (SQLException ex) {
            System.out.printf("Error al insertar la solicitud en SQL");
            Logger.getLogger(ManagerSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        } 
		
		return false;
    }//actualizar_Solicitud
    
    public DefaultTableModel getInventarioStockMin() {
        DefaultTableModel table = new DefaultTableModel();

        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_get_inventarioStockMin`()}")) {
            table.addColumn("No. Inventario");  
            table.addColumn("Producto");
            table.addColumn("Descripción");
            table.addColumn("Observaciones");
            table.addColumn("Cantidad");
            table.addColumn("Estado");

            Object datos[] = new Object[6];
            ResultSet rs = cs.executeQuery();

            //Llenar tabla
            while (rs.next()) {
				// Llenamos las columnas por registro
                for(int i = 0;i<6;i++){
                    datos[i] = rs.getObject(i+1);
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

    }//getInventarioEmpleadoAsignacionesPersonales
    
	/**
	 * <h1>Actualizar Inventario</h1>
	 * 
	 * <p>Este método es para actualizar la cantidad del producto a granel con la cantidad ingresada</p>
	 *
	 * @param ID_Producto
	 * @param Cantidad
	 * @return 
	 *		<ul>
	 *			<li><code>true</code> si la operacion fue correcta</li>
	 *			<li><code>false</code> si la operación no fue realizada</li>
	 *		</ul>
	 */
    public boolean actualizarStock(String ID_Producto, int Cantidad){
        
        try (CallableStatement cs = db.getConexion().prepareCall("{CALL `ine`.`usp_update_stockInvGranel`(?, ?, ?)}")){
            cs.setString(1, ID_Producto);
			cs.setInt(2, Cantidad);
			cs.setInt(3, 2);
			
			ResultSet rs = cs.executeQuery();
			
			if (rs.next()) return rs.getInt("res") == 1; 
			
        }catch(SQLException ex){
            System.out.printf("Error al querer actualizar el stock SQL");
            Logger.getLogger(ManagerUsers.class.getName()).log(Level.SEVERE, null, ex);

        }
       
		return false; 
    }//actualizarStock
    
}//class
