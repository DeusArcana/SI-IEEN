/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Pablo
 */
public class ConsultasSQL {
    public static String datosValeAsignacion_responsable(){
        return  "SELECT empleados.nombres,empleados.apellido_p, empleados.apellido_m,user.puesto, user.area,empleados.municipio, "
                + "empleados.localidad" +
                " FROM user" +
                " INNER JOIN empleados" +
                " ON user.id_empleado = empleados.id_empleado" +
                " INNER JOIN puestos" +
                " ON user.puesto = puestos.puesto" +
                " INNER JOIN area" +
                " ON user.area= area.area;";
    }
    
    public static String datosValeAsignacion_responsableParticular(String campo, String valor){
        return "SELECT empleados.nombres,empleados.apellido_p, empleados.apellido_m, user.puesto, user.area,empleados.municipio, "
                + " empleados.localidad" +
                " FROM user" +
                " INNER JOIN empleados" +
                " ON user.id_empleado = empleados.id_empleado" +
                " INNER JOIN puestos" +
                " ON user.puesto = puestos.puesto" +
                " INNER JOIN area" +
                " ON user.area= area.area"+
                " WHERE "+campo+"='"+valor+"';";
    }
    
    public static String datosObjetosAsignadosGlobales(){
        return "SELECT inventario.id_producto,Inventario.nombre_prod,Inventario.estatus,vales.id_vale,vales.tipo_vale,vales.estado,empleados.nombres,empleados.apellido_p,empleados.apellido_m"
                +" FROM detalle_vale"
                +" INNER JOIN inventario"
                +" ON detalle_vale.id_producto=inventario.id_producto"
                +" INNER JOIN vales"
                +" ON detalle_vale.id_vale=vales.id_vale"
                +" INNER JOIN user"
                +" ON vales.id_user=user.id_user"
                +" INNER JOIN empleados"
                +" ON user.id_empleado=empleados.id_empleado;";
    }
    
    public static String datosObjetosAsignadosGlobalesDeProductosQuehanSidoAsignados(){
        return "SELECT inventario.id_producto,Inventario.nombre_prod,Inventario.estatus,vales.id_vale,vales.tipo_vale,vales.estado,empleados.nombres,empleados.apellido_p,empleados.apellido_m"
                +" FROM detalle_vale"
                +" INNER JOIN inventario"
                +" ON detalle_vale.id_producto=inventario.id_producto"
                +" INNER JOIN vales"
                +" ON detalle_vale.id_vale=vales.id_vale"
                +" INNER JOIN user"
                +" ON vales.id_user=user.id_user"
                +" INNER JOIN empleados"
                +" ON user.id_empleado=empleados.id_empleado and Inventario.estatus='ASIGNADO';";
    }
    
    public static String datosObjetosAsignadosPersonales(String nombres,String paterno){
        return "SELECT inventario.id_producto,Inventario.nombre_prod,Inventario.estatus,vales.id_vale,vales.tipo_vale,vales.estado,empleados.nombres,empleados.apellido_p,empleados.apellido_m"
                +" FROM detalle_vale"
                +" INNER JOIN inventario"
                +" ON detalle_vale.id_producto=inventario.id_producto"
                +" INNER JOIN vales"
                +" ON detalle_vale.id_vale=vales.id_vale"
                +" INNER JOIN user"
                +" ON vales.id_user=user.id_user"
                +" INNER JOIN empleados"
                +" ON user.id_empleado=empleados.id_empleado"
                + " where empleados.nombres= '"+nombres+"' and empleados.apellido_p= '"+paterno+"';";
    }
    
    public static String datosObjetosAsignadosGlobalesGranel(){
    return "select inventario_granel.id_productoGranel,inventario_granel.nombre_prod,inventario_granel.estatus,vales.id_vale,vales.tipo_vale,vales.estado,empleados.nombres,empleados.apellido_p,empleados.apellido_m"
           +" from detalle_valegranel"
           +" inner join inventario_granel"
           +" on detalle_valegranel.id_productoGranel=inventario_granel.id_productoGranel"
           +" inner join vales"
           +" on detalle_valegranel.id_vale=vales.id_vale"
           +" inner join user"
           +" on vales.id_user=user.id_user"
           +" inner join empleados"
           +" on user.id_empleado=empleados.id_empleado;";
    }
    
    public static String getEmpleado(String nombres){
        return "SELECT user.id_user " +
                "from user " +
                "INNER JOIN empleados " +
                "on user.id_empleado=empleados.id_empleado " +
                "where empleados.nombres='"+nombres+"';";
    }
    
    public static String updateStatusInventario_asignado(){
        return "update inventario set estatus = ? where id_producto = ?";
    }
    
    public static String updateStatusInventarioGranel_asignado(){
        return "update inventario_granel set estatus = ? where id_productoGranel = ?";
    }
    
    public static String updateStockGranel(){
        return "update inventario_granel set stock = ? WHERE id_productoGranel= ?";
    }
    
    public static String insertarDatosValeAsignacion(){
        return " insert into vales (id_vale, estado, user_autorizo, fecha_vale, tipo_vale,id_user) values (?, ?, ?, ?, ?, ?);";
    }
    
    public static String insertarDatosDetalleValeAsignacion(){
        return " insert into detalle_vale (id_vale, id_producto, estado) values (?, ?, ?);";
    }
    
    public static String insertarDatosDetalleValeRecoleccion(){
        return " insert into detalle_vale (id_vale, id_producto, estado) values (?, ?, ?);";
    }
    
    public static String insertarDatosDetalleValeGranelAsignacion(){
        return " insert into detalle_valegranel (id_vale, id_productoGranel, cantidad) values (?, ?, ?);";
    }
    
    public static String insertarDatosinventario_granel_objetos_asignados(){
        return " REPLACE into inventario_granel_objetos_asignados"
                + " (id_productoGranel, nombre_prod, descripcion, almacen, marca, observaciones, stock_min, stock) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?);";
    }
    
    public static String setObjetosInventarioDisponible(){
        return "update inventario set estatus = ? WHERE id_producto= ?";
    }
    
    public static String eliminar_registrosTabla(String tabla){
        return "TRUNCATE TABLE "+tabla+";";
    }
    
    public static String todosDatos_inventario(){
        return  "Select * from inventario;";
    }
    
    public static String resumenDatos_inventario(){
        return  "Select id_producto,nombre_prod,descripcion,almacen,observaciones,no_serie,modelo,color  from inventario where estatus='DISPONIBLE';";
    }
    
    public static String resumenDatos_inventarioAsignado(){
        return  "Select id_producto,nombre_prod,descripcion,almacen,observaciones,no_serie,modelo,color  from inventario where estatus='ASIGNADO';";
    }
    
    public static String resumenDatos_inventarioGranel(){
        return  "select id_productoGranel,nombre_prod,descripcion,almacen,marca,observaciones,stock_min,stock from inventario_granel where stock>stock_min;";
    }
    
    public static String resumenDatos_inventarioGranelAuxiliar(){
        return  "select id_productoGranel,nombre_prod,descripcion,almacen,marca,observaciones,stock_min,stock from inventario_granel_objetos_asignados;";
    }
}
