
package Clases;

import Interfaces.Principal;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MetodosComponentes {
    public Principal pointerA;
    private Conexion db;
    private Connection conexion;
    public MetodosComponentes(){
        db=new Conexion();
    }
    
    //CARGA DE DATOS EN TABLAS
    public void cargarEmpleados(JTable tabla) throws ClassNotFoundException, SQLException{
        conexion=db.getConexion();  
        pointerA.modelo=new DefaultTableModel();
        ResultSet rs =Conexion.getTabla(ConsultasSQL.datosValeAsignacion_responsable(),conexion);
        pointerA.modelo.setColumnIdentifiers(Tablas.generarColumnasGraficamenteDatosResponsable());
        try{
            while(rs.next()){pointerA.modelo.addRow(Tablas.generarContenidoColumnasDatosResponsable(rs));}
            tabla.setModel(pointerA.modelo);
        }catch(Exception e){System.out.println("Error no se pudieron cargar los empleados");}
        conexion.close();
        
    }
    
    public void cargarInventario(JTable tabla) throws ClassNotFoundException, SQLException{
        conexion=db.getConexion();  
        pointerA.modelo=new DefaultTableModel();
        ResultSet rs =Conexion.getTabla(ConsultasSQL.resumenDatos_inventario(),conexion);
        pointerA.modelo.setColumnIdentifiers(Tablas.generarColumnasGraficamenteInventarioParcial());
        try{
            while(rs.next()){pointerA.modelo.addRow(Tablas.generarContenidoColumnasInventarioParcial(rs));}
            tabla.setModel(pointerA.modelo);
        }
        catch(Exception e){System.out.println("Error no se pudo cargar el inventario");}
        conexion.close();
    }
    
    public void cargarNombreEmpleado_Asignacion() throws ClassNotFoundException{//SE ACTIVA AL TAR CLICK EN UNA FILA DE LA TABLA
        try {//EMPLEADOS. OBTIENE  LOS DATOS DE LA FILA DE LA TABLA EMPLEADOS
            conexion=db.getConexion();  
            ResultSet rs =Conexion.getTabla(ConsultasSQL.datosValeAsignacion_responsableParticular(
                    "nombres",(String)pointerA.tb_empleado.getValueAt(pointerA.tb_empleado.getSelectedRow(), 0)
            ),conexion);
            while(rs.next()){  
                pointerA.encargado=rs.getString("nombres")+" "+rs.getString("apellido_p")+" "+rs.getString("apellido_m");
                pointerA.tf_empleado.setText(pointerA.encargado);pointerA.Cargo=rs.getString("puesto");pointerA.Area=rs.getString("area");
                pointerA.Municipio=rs.getString("municipio");pointerA.Localidad=rs.getString("localidad");pointerA.Tipo_de_uso=rs.getString("tipo_uso");
            }
            conexion.close();
        } catch (SQLException ex) {}
    }
    
    public void cargarNombreEmpleado_Asignacion1() throws ClassNotFoundException{//SE ACTIVA AL TAR CLICK EN UNA FILA DE LA TABLA
        try {//EMPLEADOS. OBTIENE  LOS DATOS DE LA FILA DE LA TABLA EMPLEADOS
            conexion=db.getConexion();  
            ResultSet rs =Conexion.getTabla(ConsultasSQL.datosValeAsignacion_responsableParticular(
                    "nombres",(String)pointerA.tb_empleado1.getValueAt(pointerA.tb_empleado1.getSelectedRow(), 0)
            ),conexion);
            while(rs.next()){  
                pointerA.encargado1=rs.getString("nombres")+" "+rs.getString("apellido_p")+" "+rs.getString("apellido_m");
                System.out.println("ENCARGADO1= "+pointerA.encargado1);
                pointerA.tf_empleado1.setText(pointerA.encargado1);pointerA.Cargo1=rs.getString("puesto");pointerA.Area1=rs.getString("area");
                pointerA.Municipio1=rs.getString("municipio");pointerA.Localidad1=rs.getString("localidad");pointerA.Tipo_de_uso1=rs.getString("tipo_uso");
            }
            conexion.close();
        } catch (SQLException ex) {}
    }
    
    public void cargarNombreEmpleado_Recoleccion() throws ClassNotFoundException{//SE ACTIVA AL TAR CLICK EN UNA FILA DE LA TABLA
        try {//EMPLEADOS. OBTIENE  LOS DATOS DE LA FILA DE LA TABLA EMPLEADOS1
            conexion=db.getConexion();  
            ResultSet rs =Conexion.getTabla(ConsultasSQL.datosValeAsignacion_responsableParticular(
                    "nombres",(String)pointerA.tb_empleado1.getValueAt(pointerA.tb_empleado1.getSelectedRow(), 0)
            ),conexion);
            while(rs.next()){
                pointerA.encargado=rs.getString("nombres")+" "+rs.getString("apellido_p")+" "+rs.getString("apellido_m");
                pointerA.tf_empleado.setText(pointerA.encargado);pointerA.Cargo=rs.getString("puesto");pointerA.Area=rs.getString("area");
                pointerA.Municipio=rs.getString("municipio");pointerA.Localidad=rs.getString("localidad");pointerA.Tipo_de_uso=rs.getString("tipo_uso");
            }
            conexion.close();
        } catch (SQLException ex) {}
    }
    //ACTIVACION DE COMPONENTES VISUALES
    public void activarObjetosAsignables(){       
        pointerA.btn_seleccionar_empleado.setEnabled(pointerA.asignacion_botones_activadas[0]);
        pointerA.btn_asignar.setEnabled(pointerA.asignacion_botones_activadas[1]);
        pointerA.btn_generar_vale.setEnabled(pointerA.asignacion_botones_activadas[2]);
        pointerA.btn_cancelar.setEnabled(pointerA.asignacion_botones_activadas[3]);
        pointerA.btn_seleccionar_empleado1.setEnabled(pointerA.asignacion_botones_activadas[4]);
        pointerA.btn_recolectar_producto.setEnabled(pointerA.asignacion_botones_activadas[5]);
        pointerA.btn_entregar_objetos.setEnabled(pointerA.asignacion_botones_activadas[6]);
        pointerA.btn_cancelar1.setEnabled(pointerA.asignacion_botones_activadas[7]);
        pointerA.btn_generar_vale1.setEnabled(pointerA.asignacion_botones_activadas[8]);
    }
    //ALGORITMO DE ACTUALIZACION DE TABLAS
    
    //
    public void copyInventario(JTable tabla){//RECORRE CADA FILA DE LA TABLA PASADA COMO PARAMETRO.ALMACENA EN UNA CADENA EL
        for(int i=0;i<tabla.getRowCount();i++){//CONTENIDO DE LA FILA. ESTE CONTENIDO SE UTILIZA PARA CREAR UNA CELDA DE LA LISTA
            pointerA.objetos_completos_asignables_asignacion=Tablas.retornarCadenaFila(tabla, i);
            pointerA.l_objetos_asignables_asignacion.add(pointerA.objetos_completos_asignables_asignacion.split(","));
        }
    }
    
    public void copyInventarioGranel(JTable tabla){//RECORRE CADA FILA DE LA TABLA PASADA COMO PARAMETRO.ALMACENA EN UNA CADENA EL
        for(int i=0;i<tabla.getRowCount();i++){//CONTENIDO DE LA FILA. ESTE CONTENIDO SE UTILIZA PARA CREAR UNA CELDA DE LA LISTA
            pointerA.objetos_completos_asignables_asignacion_granel=Tablas.retornarCadenaFila(tabla, i);
            pointerA.l_objetos_asignables_asignacion_granel.add(pointerA.objetos_completos_asignables_asignacion_granel.split(","));
        }
    }
    
    public void cargarInventarioGlobal(JTable tabla) throws ClassNotFoundException, SQLException{
        conexion=db.getConexion();  
        pointerA.modelo=new DefaultTableModel();
        ResultSet rs=Conexion.getTabla(ConsultasSQL.datosObjetosAsignadosGlobales(),conexion);
        pointerA.modelo.setColumnIdentifiers(Tablas.generarColumnasGraficamenteInventarioGlobal());
        try{
            while(rs.next()){pointerA.modelo.addRow(Tablas.generarContenidoColumnasInventarioGlobal(rs));}
            tabla.setModel(pointerA.modelo);
        }catch(Exception e){System.out.println("Error no se pudo cargar inventario global");}
         conexion.close();
    }
    
    public void cargarInventarioGlobalGranel(JTable tabla) throws ClassNotFoundException, SQLException{
        conexion=db.getConexion();  
        pointerA.modelo=new DefaultTableModel();
        ResultSet rs=Conexion.getTabla(ConsultasSQL.datosObjetosAsignadosGlobalesGranel(),conexion);
        pointerA.modelo.setColumnIdentifiers(Tablas.generarColumnasGraficamenteInventarioGlobal());
        try{
            while(rs.next()){pointerA.modelo.addRow(Tablas.generarContenidoColumnasInventarioGlobalGranel(rs));}
            tabla.setModel(pointerA.modelo);
        }catch(Exception e){System.out.println("Error no se pudo cargar inventario global");}
         conexion.close();
    }
    
    public void cargarInventarioAsignaciones(JTable tabla) throws ClassNotFoundException, SQLException{
        conexion=db.getConexion(); 
        pointerA.modelo=new DefaultTableModel();
        ResultSet rs=Conexion.getTabla(ConsultasSQL.resumenDatos_inventarioAsignado(),conexion);
        pointerA.modelo.setColumnIdentifiers(Tablas.generarColumnasGraficamenteInventarioGlobal());
        try{
            while(rs.next()){pointerA.modelo.addRow(Tablas.generarContenidoColumnasInventarioGlobal(rs));}
            tabla.setModel(pointerA.modelo);
        }catch(Exception e){System.out.println("Error no se pudo cargar inventario global");}
        conexion=db.getConexion(); 
    }
    
     public void cargarInventarioGranel(JTable tabla) throws ClassNotFoundException, SQLException{
        conexion=db.getConexion(); 
        pointerA.modelo=new DefaultTableModel();
        ResultSet rs =Conexion.getTabla(ConsultasSQL.resumenDatos_inventarioGranel(),conexion);
        pointerA.modelo.setColumnIdentifiers(Tablas.generarColumnasGraficamenteInventarioGranelParcial());
        
        try{
            while(rs.next()){pointerA.modelo.addRow(Tablas.generarContenidoColumnasInventarioGranelParcial(rs));}
            tabla.setModel(pointerA.modelo);
        }catch(Exception e){System.out.println("Error no se pudo cargar el inventario a granel");}
        conexion.close();
    }
     
    public void cargarInventarioGranelAuxiliar(JTable tabla) throws ClassNotFoundException, SQLException{
        conexion=db.getConexion(); 
        pointerA.modelo=new DefaultTableModel();
        ResultSet rs =Conexion.getTabla(ConsultasSQL.resumenDatos_inventarioGranelAuxiliar(),conexion);
        pointerA.modelo.setColumnIdentifiers(Tablas.generarColumnasGraficamenteInventarioGranelParcial());
        
        try{
            while(rs.next()){pointerA.modelo.addRow(Tablas.generarContenidoColumnasInventarioGranelParcial(rs));}
            tabla.setModel(pointerA.modelo);
        }catch(Exception e){System.out.println("Error no se pudo cargar el inventario a granel");}
        conexion.close();
    }
     
    public void cargarInventarioAsignado(JTable tabla)throws ClassNotFoundException{
        pointerA.modelo=new DefaultTableModel();//DefaultTableModel modelo, ResultSetconsulta,
        pointerA.modelo.setColumnIdentifiers(Tablas.generarColumnasGraficamenteInventarioParcial());
        try{
            for(int i=0;i<pointerA.l_eliminables_objetos_asignables_asignacion.size();i++){                
                pointerA.modelo.addRow(pointerA.l_eliminables_objetos_asignables_asignacion.get(i));       
            }
            tabla.setModel(pointerA.modelo);
        }catch(Exception e){System.out.println("Error no se pudo cargar el inventario asignado");}
    }
    
    public void cargarInventarioRemovido(JTable tabla)throws ClassNotFoundException{
        pointerA.modelo=new DefaultTableModel();//DefaultTableModel modelo, ResultSetconsulta,
        pointerA.modelo.setColumnIdentifiers(Tablas.generarColumnasGraficamenteInventarioGlobal());
        try{
            for(int i=0;i<pointerA.l_eliminables_objetos_asignables_asignacion.size();i++){                
                pointerA.modelo.addRow(pointerA.l_eliminables_objetos_asignables_asignacion.get(i));       
            }
            tabla.setModel(pointerA.modelo);
        }catch(Exception e){System.out.println("Error no se pudo cargar el inventario removido");}
    }
    
    public void cargarInventarioGranelAsignado(JTable tabla)throws ClassNotFoundException{
        pointerA.modelo=new DefaultTableModel();//DefaultTableModel modelo, ResultSetconsulta,
        pointerA.modelo.setColumnIdentifiers(Tablas.generarColumnasGraficamenteInventarioGranelParcial());
        try{
            for(int i=0;i<pointerA.l_eliminables_objetos_asignables_asignacion_granel.size();i++){                
                //pointerA.modelo.removeRow(i);
                pointerA.modelo.addRow(pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(i));
                
            }
            tabla.setModel(pointerA.modelo);
        }catch(Exception e){System.out.println("Error no se pudo cargar el inventario a granel asignado");}
    }    
    
    public void renovarInventarioAsignable(JTable tabla) throws ClassNotFoundException{
        pointerA.modelo=new DefaultTableModel();
        pointerA.modelo.setColumnIdentifiers(Tablas.generarColumnasGraficamenteInventarioParcial());
        
       for(int i=0;i<pointerA.l_objetos_asignables_asignacion.size();i++){
           for(int j=0;j<pointerA.l_eliminables_objetos_asignables_asignacion.size();j++){
               if(pointerA.l_objetos_asignables_asignacion.get(i)[0].equals(pointerA.l_eliminables_objetos_asignables_asignacion.get(j)[0])){
                   pointerA.l_objetos_asignables_asignacion.remove(pointerA.l_objetos_asignables_asignacion.get(i));
               }
           }
       }
        
       for(int i=0;i<pointerA.l_objetos_asignables_asignacion.size();i++){
           for(int j=0;j<pointerA.l_objetos_asignables_asignacion.get(i).length;j++){
              // System.out.println("ObjetosCompletos["+j+"]= "+pointerA.l_objetos_asignables_asignacion.get(i)[j]);
           }
       }
       
       for(int i=0;i<pointerA.l_eliminables_objetos_asignables_asignacion.size();i++){
           for(int j=0;j<pointerA.l_eliminables_objetos_asignables_asignacion.get(i).length;j++){
               //System.out.println("ObjetosEliminables["+j+"]= "+pointerA.l_eliminables_objetos_asignables_asignacion.get(i)[j]);
           }
       }
       
        try{
            for(int i=0;i<pointerA.l_objetos_asignables_asignacion.size();i++){
                pointerA.modelo.addRow(pointerA.l_objetos_asignables_asignacion.get(i));
            }
            
            tabla.setModel(pointerA.modelo);
        }catch(Exception e){System.out.println("Error no se pudo renovar el inventario asignable");}
    }
    
    public void renovarInventarioRemovible(JTable tabla) throws ClassNotFoundException{
        pointerA.modelo=new DefaultTableModel();
        pointerA.modelo.setColumnIdentifiers(Tablas.generarColumnasGraficamenteInventarioGlobal());
        
       for(int i=0;i<pointerA.l_objetos_asignables_asignacion.size();i++){
           for(int j=0;j<pointerA.l_eliminables_objetos_asignables_asignacion.size();j++){
               if(pointerA.l_objetos_asignables_asignacion.get(i)[0].equals(pointerA.l_eliminables_objetos_asignables_asignacion.get(j)[0])){
                   pointerA.l_objetos_asignables_asignacion.remove(pointerA.l_objetos_asignables_asignacion.get(i));
               }
           }
       }
       
        try{
            for(int i=0;i<pointerA.l_objetos_asignables_asignacion.size();i++){
                pointerA.modelo.addRow(pointerA.l_objetos_asignables_asignacion.get(i));
            }
            
            tabla.setModel(pointerA.modelo);
        }catch(Exception e){System.out.println("Error no se pudo renovar el inventario removible");}
    }
    
    public void renovarInventarioGranelAsignable(JTable tabla) throws ClassNotFoundException{
        pointerA.modelo=new DefaultTableModel();
        pointerA.modelo.setColumnIdentifiers(Tablas.generarColumnasGraficamenteInventarioGranelParcial());
        
       for(int i=0;i<pointerA.l_objetos_asignables_asignacion_granel.size();i++){
           for(int j=0;j<pointerA.l_eliminables_objetos_asignables_asignacion_granel.size();j++){
               if(pointerA.l_objetos_asignables_asignacion_granel.get(i)[0].equals(pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(j)[0])){
                   System.out.println("------------------------------RENOVANDO INVENTARIO-----------------------------\n");
                   pointerA.l_objetos_asignables_asignacion_granel.get(i)[7]=pointerA.unidadesRestantes+"";
                   System.out.println("Lista_objetos_asignables= "+pointerA.l_objetos_asignables_asignacion_granel.get(i)[7]); 
                    
                   pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(i)[7]=pointerA.unidadesAcumuladas+"";//NUEVO
                   System.out.println("Lista_objetos_eliminables= "+pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(i)[7]);
               }
           }
       }
        
       for(int i=0;i<pointerA.l_objetos_asignables_asignacion_granel.size();i++){
           for(int j=0;j<pointerA.l_objetos_asignables_asignacion_granel.get(i).length;j++){
               //System.out.println("ObjetosCompletos["+j+"]= "+pointerA.l_objetos_asignables_asignacion_granel.get(i)[j]);
           }
       }
       
       for(int i=0;i<pointerA.l_eliminables_objetos_asignables_asignacion_granel.size();i++){
           for(int j=0;j<pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(i).length;j++){
               //System.out.println("ObjetosEliminables["+j+"]= "+pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(i)[j]);
           }
       }
       
        try{
            for(int i=0;i<pointerA.l_objetos_asignables_asignacion_granel.size();i++){
                pointerA.modelo.addRow(pointerA.l_objetos_asignables_asignacion_granel.get(i));
            }
            
            tabla.setModel(pointerA.modelo);
        }catch(Exception e){System.out.println("Error no se pudo renovar el inventario asignable granel");}
    }
    
    public void renovarInventarioGranelAsignableSinExtras(JTable tabla) throws ClassNotFoundException{
        pointerA.modelo=new DefaultTableModel();
        pointerA.modelo.setColumnIdentifiers(Tablas.generarColumnasGraficamenteInventarioGranelParcial());
        
       for(int i=0;i<pointerA.l_objetos_asignables_asignacion_granel.size();i++){
           for(int j=0;j<pointerA.l_eliminables_objetos_asignables_asignacion_granel.size();j++){
               if(pointerA.l_objetos_asignables_asignacion_granel.get(i)[0].equals(pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(j)[0])){
                   pointerA.l_objetos_asignables_asignacion_granel.remove(pointerA.l_objetos_asignables_asignacion_granel.get(i));
               }
           }
       }
        
       for(int i=0;i<pointerA.l_objetos_asignables_asignacion_granel.size();i++){
           for(int j=0;j<pointerA.l_objetos_asignables_asignacion_granel.get(i).length;j++){
               //System.out.println("ObjetosCompletos["+j+"]= "+pointerA.l_objetos_asignables_asignacion_granel.get(i)[j]);
           }
       }
       
       for(int i=0;i<pointerA.l_eliminables_objetos_asignables_asignacion_granel.size();i++){
           for(int j=0;j<pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(i).length;j++){
               //System.out.println("ObjetosEliminables["+j+"]= "+pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(i)[j]);
           }
       }
       
        try{
            for(int i=0;i<pointerA.l_objetos_asignables_asignacion_granel.size();i++){
                pointerA.modelo.addRow(pointerA.l_objetos_asignables_asignacion_granel.get(i));
            }
            
            tabla.setModel(pointerA.modelo);
        }catch(Exception e){System.out.println("Error no se pudo renovar el inventario asignable granel");}
    }
    
    public void actualizarEstatusInventarioAsignado() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        Conexion con=new Conexion();
        Connection conn =con.getConexion();
        String query = ConsultasSQL.updateStatusInventario_asignado();
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        for(int i=0;i<pointerA.tb_objetos_asignados.getRowCount();i++){
            pointerA.id_inventario=(String)pointerA.tb_objetos_asignados.getValueAt(i, 0);
            //System.out.println("ID_INVENTARIO= "+pointerA.id_inventario);
            preparedStmt.setString(1, "Asignado");
            preparedStmt.setString(2, pointerA.id_inventario);
            preparedStmt.executeUpdate();
        }
        conn.close();
    }
    
    public void actualizarEstatusInventarioGranelAsignado() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        Conexion con=new Conexion();
        Connection conn =con.getConexion();
        String query = ConsultasSQL.updateStatusInventarioGranel_asignado();
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        for(int i=0;i<pointerA.tb_objetos_asignados.getRowCount();i++){
            pointerA.id_inventario=(String)pointerA.tb_objetos_asignados.getValueAt(i, 0);
            //System.out.println("ID_INVENTARIO= "+pointerA.id_inventario);
            preparedStmt.setString(1, "Asignado");
            preparedStmt.setString(2, pointerA.id_inventario);
            preparedStmt.executeUpdate();
        }
        conn.close();
    }
    
    public void actualizarExistenciasInventarioGranel() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        Conexion con=new Conexion();
        Connection conn =con.getConexion();
        String query = ConsultasSQL.updateStockGranel();
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        for(int i=0;i<pointerA.tb_objetos_asignados.getRowCount();i++){
            pointerA.id_inventario=(String)pointerA.tb_objetos_asignados.getValueAt(i, 0);
            preparedStmt.setInt(1,getIntValueWithSameId(pointerA.tb_objetos_asignables,pointerA.id_inventario,Integer.parseInt((String)pointerA.tb_objetos_asignados.getValueAt(i, 7))));
            preparedStmt.setString(2, pointerA.id_inventario);
            preparedStmt.executeUpdate();
        }
        conn.close();
    }
    
    public int getIntValueWithSameId(JTable table,String id,int v_stock_asig){
        int equivalentValue=0;
        System.out.println("ID BRINDADO= "+id);
        System.out.println("STOCK BRINDADO= "+v_stock_asig);
        for(int i=0;i<table.getRowCount();i++){
            System.out.println("ID TABLA OPUESTA= "+table.getValueAt(i, 0));
            if(table.getValueAt(i, 0).equals(id)){
                equivalentValue=Integer.parseInt((String)table.getValueAt(i, 7))-v_stock_asig;return equivalentValue;
            }
        }
        return equivalentValue;
    }
    
    public void insertarDatosValeAsignacion() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException{
        pointerA.serialVale++;
        Archivo.createArchivo("", "serial_vale.vl",pointerA.serialVale+"");
        Conexion con=new Conexion();
        Connection conn =con.getConexion();
        String query = ConsultasSQL.insertarDatosValeAsignacion();
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        //System.out.print("VALOR EMPLEADO= "+(String)pointerA.tb_empleado.getValueAt(pointerA.tb_empleado.getSelectedRow(),0));
        preparedStmt.setInt(1, pointerA.serialVale);
        preparedStmt.setString(2, "Pendiente");
        preparedStmt.setString(3, Archivo.leerContenidoArchivo("cnfg.cfg"));
        preparedStmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
        preparedStmt.setString(5, "resguardo_bienes");
        preparedStmt.setString(6, transformarEmpleadoEnUsuario((String)pointerA.tb_empleado.getValueAt(pointerA.tb_empleado.getSelectedRow(),0)));
        preparedStmt.execute();
        conn.close();
    }
    
    public void insertarDatosDetalleValeAsignacion() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        Conexion con=new Conexion();
        Connection conn =con.getConexion();
        String query = ConsultasSQL.insertarDatosDetalleValeAsignacion();
        for(int i=0;i<pointerA.tb_objetos_asignados.getRowCount();i++){
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            //System.out.println("Iteracion Buena: "+i+" Valor fila: "+Tablas.retornarIdFila(pointerA.tb_objetos_asignados, i));
            preparedStmt.setInt(1, pointerA.serialVale);
            preparedStmt.setString(2,Tablas.retornarIdFila(pointerA.tb_objetos_asignados, i));
            preparedStmt.setString(3, "Asignado");
            preparedStmt.execute();
        }
        
        conn.close();
    }
    
    public void insertarDatosDetalleValeGranelAsignacion() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        Conexion con=new Conexion();
        Connection conn =con.getConexion();
        String query = ConsultasSQL.insertarDatosDetalleValeGranelAsignacion();
        for(int i=0;i<pointerA.tb_objetos_asignados.getRowCount();i++){
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            //System.out.println("Iteracion Buena: "+i+" Valor fila: "+Tablas.retornarIdFila(pointerA.tb_objetos_asignados, i));
            preparedStmt.setInt(1, pointerA.serialVale);
            preparedStmt.setString(2,Tablas.retornarIdFila(pointerA.tb_objetos_asignados, i));
            preparedStmt.setInt(3, Integer.parseInt(pointerA.tf_cantidad.getText()));
            preparedStmt.execute();
        }
        
        conn.close();
    }
    
    public void borrarDatosTabla(String tabla) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        Conexion con=new Conexion();
        Connection conn =con.getConexion();
        String sql = ConsultasSQL.eliminar_registrosTabla(tabla);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        conn.close();
    }
    
    public void resetObjetosAsignablesYAsignados() throws ClassNotFoundException, SQLException{
        pointerA.l_eliminables_objetos_asignables_asignacion.clear();
        pointerA.l_objetos_asignables_asignacion.clear();
        pointerA.l_eliminables_objetos_asignables_asignacion_granel.clear();
        pointerA.l_objetos_asignables_asignacion_granel.clear();
        pointerA.l_cantidades_pedidas_granel.clear();
        cargarInventarioGlobal(pointerA.tb_objetos_asignados1);
        if(pointerA.rb_inventario_normal.isSelected()){
            cargarInventario(pointerA.tb_objetos_asignables);
            copyInventario(pointerA.tb_objetos_asignables);
            cargarInventarioAsignado(pointerA.tb_objetos_asignados);
            //
            cargarInventarioAsignaciones(pointerA.tb_objetos_asignados1);
        }
        else{
            cargarInventarioGranel(pointerA.tb_objetos_asignables);
            copyInventarioGranel(pointerA.tb_objetos_asignables);
            cargarInventarioGranelAsignado(pointerA.tb_objetos_asignados);
        }
        
    }
    
    public String transformarEmpleadoEnUsuario(String empleado) throws ClassNotFoundException, SQLException{
        Object tx[];
        conexion=db.getConexion(); 
        ResultSet rs =Conexion.getTabla(ConsultasSQL.getEmpleado(empleado),conexion);
        try{
            if(rs.next()){
                tx=Tablas.generarContenidoColumaUsuario(rs);
                //System.out.println("TX= "+tx[0]);
                return (String)tx[0];
            }
        }catch(Exception e){System.out.println("Error No se genero contenido de usuario");}
        return "";
    }
    
    public String getExistenciaGranel(String filaInventarioGranel){
        String inventarioConCambios="";
        String filainventarioGranelArreglo[]=filaInventarioGranel.split(",");
        pointerA.unidadesRestantes=pointerA.stockActualSeleccionado-pointerA.unidadesAsignadas;
        pointerA.unidadesAcumuladas+=pointerA.unidadesAsignadas;
        pointerA.last_id_inventarioGranel=filainventarioGranelArreglo[0];
        filainventarioGranelArreglo[7]=(pointerA.unidadesRestantes)+"";
        for(int i=0;i<filainventarioGranelArreglo.length;i++){
            if(i==7){
                inventarioConCambios+=filainventarioGranelArreglo[i];
            }else{inventarioConCambios+=filainventarioGranelArreglo[i]+",";}
        }
        printContenidoArray(filainventarioGranelArreglo,"filainventarioGranelArreglo");
        System.out.println("inventarioConCambios= "+inventarioConCambios);
        return inventarioConCambios;
    }
    
    public  String[] getExistenciaGranelArray(String[] array){
        String filainventarioGranelArreglo[]=array;
        pointerA.unidadesRestantes=pointerA.stockActualSeleccionado-pointerA.unidadesAsignadas;
        pointerA.unidadesAcumuladas+=pointerA.unidadesAsignadas;
        pointerA.last_id_inventarioGranel=filainventarioGranelArreglo[0];
        filainventarioGranelArreglo[7]=(pointerA.unidadesAsignadas)+"";
        return filainventarioGranelArreglo;
    }
    
    public static void printContenidoLista(ArrayList<Object[]> lista,String nomlista){
        System.out.println("----------------------------CONTENIDO DE LISTA "+nomlista+"----------------------------");
        for(int i=0;i<lista.size();i++){
            System.out.println(nomlista+"("+i+")= ");
            for(int j=0;j<lista.get(i).length;j++){
                System.out.print("|"+lista.get(i)[j]+"|");
            }     
        }
    }
    
    public static void printContenidoElementoLista(ArrayList<Object[]> lista,String nomlista,int elemento){
        System.out.println("----------------------------CONTENIDO DE ELEMENTO "+elemento+" DE LISTA "+nomlista+"----------------------------");
        //System.out.print(nomlista+"("+elemento+")= ");
        for(int i=0;i<lista.get(i).length;i++){
            System.out.print("|"+lista.get(elemento)[i]+"|");
        }
    }
    
    public static void printContenidoArray(Object[] array,String nomarray){
        System.out.println("----------------------------CONTENIDO DE ARRAY "+nomarray+"----------------------------");
        for(int i=0;i<array.length;i++){
            System.out.println(nomarray+"["+i+"]= "+array[i]);
        }
    }
    
    public void removeElementoRepetidoLista(ArrayList<Object[]> lista,String id){
        String search_id="";
        for(int i=0;i<lista.size();i++){
                search_id=(String)lista.get(i)[0];
                if(search_id.equals(id)){
                    lista.remove(i);
                }
        }
    }
    
    public void insertarDatosinventario_granel_objetos_asignados() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        Conexion con=new Conexion();
        Connection conn =con.getConexion();
        String query = ConsultasSQL.insertarDatosinventario_granel_objetos_asignados();
        for(int i=0;i<pointerA.l_eliminables_objetos_asignables_asignacion_granel.size();i++){
            PreparedStatement preparedStmt = conn.prepareStatement(query);               
            preparedStmt.setString(1,(String)pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(i)[0]);
            preparedStmt.setString(2,(String)pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(i)[1]);
            preparedStmt.setString(3,(String)pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(i)[2]);
            preparedStmt.setString(4,(String)pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(i)[3]);
            preparedStmt.setString(5,(String)pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(i)[4]);
            preparedStmt.setString(6,(String)pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(i)[5]);
            preparedStmt.setInt(7, Integer.parseInt((String)pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(i)[6]));
            preparedStmt.setInt(8, Integer.parseInt((String)pointerA.l_eliminables_objetos_asignables_asignacion_granel.get(i)[7]));
            preparedStmt.execute(); 
        }
        pointerA.l_eliminables_objetos_asignables_asignacion_granel.clear();
        conn.close();
    }
    
    public void copyContentTabla(JTable torigen,JTable tdestino){
        pointerA.modelo=new DefaultTableModel();
        pointerA.modelo.setColumnIdentifiers(Tablas.generarColumnasGraficamenteInventarioGlobal());
        
        for(int i=0;i<torigen.getRowCount();i++){
            pointerA.modelo.addRow(Tablas.retornarContenidoFila(torigen, i));
        }
        tdestino.setModel(pointerA.modelo);
        //tb_objetos_faltantes
    }
    
}
