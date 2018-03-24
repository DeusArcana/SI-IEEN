-- MySQL Workbench Forward Engineering
DROP DATABASE `INE`;
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema INE
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema INE
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `INE` DEFAULT CHARACTER SET utf8 ;
USE `INE` ;

-- -----------------------------------------------------
-- Table `INE`.`Empleados`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Empleados` (
  `id_empleado` INT NOT NULL AUTO_INCREMENT,
  `nombres` VARCHAR(60) NULL,
  `apellido_p` VARCHAR(30) NULL,
  `apellido_m` VARCHAR(30) NULL,
  `area` VARCHAR(255) NULL,
  `calle` VARCHAR(100) NULL,
  `colonia` VARCHAR(30) NULL,
  `telefono` VARCHAR(18) NULL,
  `codigo_postal` VARCHAR(8) NULL,
  `fecha_nacimiento` DATE NULL,
  `curp` VARCHAR(20) NULL,
  `rfc` VARCHAR(13) NULL,
  `municipio` VARCHAR(30) NULL,
  `localidad` VARCHAR(50) NULL,
  PRIMARY KEY (`id_empleado`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Puestos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Puestos` (
  `puesto` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`puesto`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Area` (
  `Area` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`Area`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`User` (
  `id_user` VARCHAR(20) NOT NULL,
  `id_empleado` INT NOT NULL,
  `documentacion` TINYINT(1) NULL,
  `password` VARCHAR(15) NULL,
  `puesto` VARCHAR(50) NOT NULL,
  `estatus` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id_user`, `id_empleado`, `puesto`),
  INDEX `fk_User_Empleados1_idx` (`id_empleado` ASC),
  INDEX `fk_User_Puestos1_idx` (`puesto` ASC),
  CONSTRAINT `fk_User_Empleados1`
    FOREIGN KEY (`id_empleado`)
    REFERENCES `INE`.`Empleados` (`id_empleado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Puestos1`
    FOREIGN KEY (`puesto`)
    REFERENCES `INE`.`Puestos` (`puesto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Modulos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Modulos` (
  `modulo` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`modulo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Permisos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Permisos` (
  `id_user` VARCHAR(20) NOT NULL,
  `modulo` VARCHAR(100) NOT NULL,
  `alta` TINYINT(1) NULL,
  `baja` TINYINT(1) NULL,
  `actualizar` TINYINT(1) NULL,
  `consulta` TINYINT(1) NULL,
  PRIMARY KEY (`id_user`, `modulo`),
  INDEX `fk_Permisos_Modulos1_idx` (`modulo` ASC),
  CONSTRAINT `fk_Permisos_User1`
    FOREIGN KEY (`id_user`)
    REFERENCES `INE`.`User` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Permisos_Modulos1`
    FOREIGN KEY (`modulo`)
    REFERENCES `INE`.`Modulos` (`modulo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `INE`.`Folio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Folio` (
  `ID_Folio` CHAR(5) NOT NULL,
  `Descripcion` VARCHAR(255),
  PRIMARY KEY `pk_ID_Folio`(`ID_Folio`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `INE`.`Bodegas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Bodegas` (
  `Nom_Bodega` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`Nom_Bodega`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `INE`.`Inventario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Inventario` (
  `Folio` 			CHAR(5) NOT NULL,
  `Numero` 			INT NOT NULL,
  `Extension` 		CHAR(1) NULL,
  `nombre_prod` 	VARCHAR(50) NULL,
  `descripcion` 	VARCHAR(500) NULL,
  `ubicacion` 		VARCHAR(50) NULL,
  `estatus` 		VARCHAR(35) NULL,
  `marca` 			VARCHAR(50) NULL,
  `observaciones` 	VARCHAR(300) NULL,
  `no_serie` 		VARCHAR(45) NULL,
  `tipo_uso` 		VARCHAR(100) NULL,
  `modelo` 			VARCHAR(100) NULL,
  `color` 			VARCHAR(30) NULL,
  `imagen` 			LONGBLOB NULL,
  `Fecha_Compra` 	DATE NOT NULL,
  `Factura` 		VARCHAR(20),
  `Importe` 		FLOAT
  ) ENGINE = InnoDB;

ALTER TABLE `Inventario`
ADD CONSTRAINT `fk_Inventario_Folio`
  FOREIGN KEY (`Folio`)
  REFERENCES `Folio` (`ID_Folio`)
  ON DELETE NO ACTION
  ON UPDATE CASCADE;

-- -----------------------------------------------------
-- Table `INE`.`Resguardo_personal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Resguardo_personal` (
  `id_user` VARCHAR(20) NOT NULL,
  `nombre_prod` VARCHAR(50) NULL,
  `fecha_alta` DATETIME NULL,
  `observaciones` VARCHAR(300) NULL,
  PRIMARY KEY (`id_user`),
  CONSTRAINT `fk_Resguardo_personal_User1`
    FOREIGN KEY (`id_user`)
    REFERENCES `INE`.`User` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Bitacora_inventario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Bitacora_inventario` (
  `nombre_prod` VARCHAR(50) NULL,
  `movimiento` VARCHAR(35) NULL,
  `cantidad` INT NULL,
  `user` VARCHAR(100) NULL,
  `fecha_movimiento` DATETIME NULL,
  `observaciones` VARCHAR(300) NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`TipoSolicitud`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`TipoSolicitud` (
  `tipo_solicitud` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`tipo_solicitud`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Solicitudes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Solicitudes` (
  `id_solicitud` INT NOT NULL AUTO_INCREMENT,
  `tipo_solicitud` VARCHAR(50) NOT NULL,
  `estado` VARCHAR(25) NULL,
  `user_autorizo` VARCHAR(100) NULL,
  `id_user` VARCHAR(20) NOT NULL,
  `motivo` VARCHAR(100) NULL,
  `cantidad` INT NULL,
  `fecha_solicitud` DATETIME NULL,
  `fecha_respuesta` DATETIME NULL,
  PRIMARY KEY (`id_solicitud`, `tipo_solicitud`, `id_user`),
  INDEX `fk_Solicitudes_TipoSolicitud1_idx` (`tipo_solicitud` ASC),
  INDEX `fk_Solicitudes_User1_idx` (`id_user` ASC),
  CONSTRAINT `fk_Solicitudes_TipoSolicitud1`
    FOREIGN KEY (`tipo_solicitud`)
    REFERENCES `INE`.`TipoSolicitud` (`tipo_solicitud`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Solicitudes_User1`
    FOREIGN KEY (`id_user`)
    REFERENCES `INE`.`User` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`tipoVale`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`tipoVale` (
  `tipo_vale` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`tipo_vale`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Vales`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Vales` (
  `id_vale` INT NOT NULL AUTO_INCREMENT,
  `tipo_vale` VARCHAR(30) NOT NULL,
  `fecha_vale` DATETIME NULL,
  `id_user` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id_vale`, `tipo_vale`, `id_user`),
  INDEX `fk_Vales_tipoVale1_idx` (`tipo_vale` ASC),
  INDEX `fk_Vales_User1_idx` (`id_user` ASC),
  CONSTRAINT `fk_Vales_tipoVale1`
    FOREIGN KEY (`tipo_vale`)
    REFERENCES `INE`.`tipoVale` (`tipo_vale`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Vales_User1`
    FOREIGN KEY (`id_user`)
    REFERENCES `INE`.`User` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Permisos_puesto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Permisos_puesto` (
  `puesto` VARCHAR(50) NOT NULL,
  `modulo` VARCHAR(100) NOT NULL,
  `alta` TINYINT(1) NULL,
  `baja` TINYINT(1) NULL,
  `actualizar` TINYINT(1) NULL,
  `consulta` TINYINT(1) NULL,
  PRIMARY KEY (`puesto`, `modulo`),
  INDEX `fk_Permisos_puesto_Modulos1_idx` (`modulo` ASC),
  INDEX `fk_Permisos_puesto_Puestos1_idx` (`puesto` ASC),
  CONSTRAINT `fk_Permisos_puesto_Modulos1`
    FOREIGN KEY (`modulo`)
    REFERENCES `INE`.`Modulos` (`modulo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Permisos_puesto_Puestos1`
    FOREIGN KEY (`puesto`)
    REFERENCES `INE`.`Puestos` (`puesto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Detalle_solicitud`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Detalle_solicitud` (
  `id_solicitud` INT NOT NULL,
  `id_producto` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id_solicitud`),
  INDEX `fk_Detalle_solicitud_Inventario1_idx` (`id_producto` ASC),
  CONSTRAINT `fk_Detalle_solicitud_Solicitudes1`
    FOREIGN KEY (`id_solicitud`)
    REFERENCES `INE`.`Solicitudes` (`id_solicitud`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Permisos_Solicitud`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Permisos_Solicitud` (
  `tipo_solicitud` VARCHAR(50) NOT NULL,
  `puesto` VARCHAR(50) NOT NULL,
  `permiso` TINYINT(1) NULL,
  PRIMARY KEY (`tipo_solicitud`, `puesto`),
  INDEX `fk_Detalle_tipoSolicitud_Puestos1_idx` (`puesto` ASC),
  CONSTRAINT `fk_Detalle_tipoSolicitud_TipoSolicitud1`
    FOREIGN KEY (`tipo_solicitud`)
    REFERENCES `INE`.`TipoSolicitud` (`tipo_solicitud`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Detalle_tipoSolicitud_Puestos1`
    FOREIGN KEY (`puesto`)
    REFERENCES `INE`.`Puestos` (`puesto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Inventario_granel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Inventario_granel` (
  `id_productoGranel` VARCHAR(30) NOT NULL,
  `nombre_prod` VARCHAR(50) NULL,
  `descripcion` VARCHAR(100) NULL,
  `almacen` VARCHAR(50) NULL,
  `estatus` VARCHAR(35) NULL,
  `marca` VARCHAR(50) NULL,
  `observaciones` VARCHAR(300) NULL,
  `stock_min` INT NULL,
  `stock` INT NULL,
  `tipo_uso` VARCHAR(100) NULL,
  PRIMARY KEY (`id_productoGranel`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Permiso_vale`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Permiso_vale` (
  `tipo_vale` VARCHAR(30) NOT NULL,
  `puesto` VARCHAR(50) NOT NULL,
  `permiso` TINYINT(1) NULL,
  PRIMARY KEY (`tipo_vale`, `puesto`),
  INDEX `fk_Detalle_tipoVale_Puestos1_idx` (`puesto` ASC),
  CONSTRAINT `fk_Detalle_tipoVale_tipoVale1`
    FOREIGN KEY (`tipo_vale`)
    REFERENCES `INE`.`tipoVale` (`tipo_vale`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Detalle_tipoVale_Puestos1`
    FOREIGN KEY (`puesto`)
    REFERENCES `INE`.`Puestos` (`puesto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Detalle_vale`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Detalle_vale` (
  `id_vale` INT NOT NULL,
  `id_producto` VARCHAR(45) NULL,
  `cantidad` INT NULL,
  `estado` VARCHAR(25) NULL,
  CONSTRAINT `fk_Detalle_vale_Vales1`
    FOREIGN KEY (`id_vale`)
    REFERENCES `INE`.`Vales` (`id_vale`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Privilegios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Privilegios` (
  `PC` VARCHAR(50) NULL,
  `IP` VARCHAR(50) NULL,
  `Estado` VARCHAR(50) NULL,
  `Permiso` VARCHAR(50) NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Solicitud_viatico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Solicitud_viatico` (
  `idSolicitud` INT NOT NULL AUTO_INCREMENT,
  `Fecha_salida` DATE NULL,
  `Lugar` VARCHAR(100) NULL,
  `Nombre` VARCHAR(100) NULL,
  `Actividad` VARCHAR(500) NULL,
  `Pernoctado` VARCHAR(10) NULL,
  `Vehiculo` VARCHAR(100) NULL,
  `Puesto` VARCHAR(50) NULL,
  `Fecha_llegada` DATE NULL,
  `Estado` VARCHAR(45) NULL,
  `Reporte` VARCHAR(1) NULL,
  `Motivo` VARCHAR(500) NULL,
  `Hora_Salida` varchar(20) NULL,
  `Hora_Llegada` varchar(20) NULL,
  PRIMARY KEY (`idSolicitud`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Oficio_comision`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Oficio_comision` (
  `Folio` INT NOT NULL,
  `Solicitud_idSolicitud` INT NOT NULL,
  `Monto` FLOAT NULL,
  PRIMARY KEY (`Folio`),
  INDEX `fk_Oficio_comision_Solicitud_viatico1_idx` (`Solicitud_idSolicitud` ASC),
  CONSTRAINT `fk_Oficio_comision_Solicitud_viatico1`
    FOREIGN KEY (`Solicitud_idSolicitud`)
    REFERENCES `INE`.`Solicitud_viatico` (`idSolicitud`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Director_General`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Director_General` (
  `Nombre` VARCHAR(45) NOT NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Informe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Informe` (
  `Id_Informe` INT NOT NULL AUTO_INCREMENT,
  `Observaciones` VARCHAR(200) NULL,
  `Observaciones_Vehiculo` VARCHAR(200) NULL,
  `Solicitud_idSolicitud` INT NOT NULL,
  PRIMARY KEY (`Id_Informe`),
  INDEX `fk_Informe_Solicitud_viatico1_idx` (`Solicitud_idSolicitud` ASC),
  CONSTRAINT `fk_Informe_Solicitud_viatico1`
    FOREIGN KEY (`Solicitud_idSolicitud`)
    REFERENCES `INE`.`Solicitud_viatico` (`idSolicitud`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Gastos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Gastos` (
  `Id_Gastos` INT NOT NULL AUTO_INCREMENT,
  `Precio` VARCHAR(20) NULL,
  `Descripcion` VARCHAR(200) NULL,
  PRIMARY KEY (`Id_Gastos`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Informe_Gastos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Informe_Gastos` (
  `Gastos_Id_Gastos` INT NOT NULL,
  `Informe_Id_Informe` INT NOT NULL,
  INDEX `fk_Informe_Gastos_Gastos1_idx` (`Gastos_Id_Gastos` ASC),
  INDEX `fk_Informe_Gastos_Informe1_idx` (`Informe_Id_Informe` ASC),
  CONSTRAINT `fk_Informe_Gastos_Gastos1`
    FOREIGN KEY (`Gastos_Id_Gastos`)
    REFERENCES `INE`.`Gastos` (`Id_Gastos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Informe_Gastos_Informe1`
    FOREIGN KEY (`Informe_Id_Informe`)
    REFERENCES `INE`.`Informe` (`Id_Informe`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Vehiculos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Vehiculos` (
  `Marca` VARCHAR(45) NULL,
  `Linea` VARCHAR(45) NULL,
  `Clase` VARCHAR(45) NULL,
  `Kilometraje` VARCHAR(45) NULL,
  `Modelo` VARCHAR(45) NULL,
  `Color` VARCHAR(45) NULL,
  `Motor` VARCHAR(45) NULL,
  `Matricula` VARCHAR(45) NOT NULL,
  `Observaciones` VARCHAR(1000) NULL,
  `Imagen` LONGBLOB NULL,
  `Estado` VARCHAR(100) NULL,
  PRIMARY KEY (`Matricula`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Inicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Inicio` (
  `usuario` VARCHAR(45) NULL,
  `ip` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `bandera` VARCHAR(45) NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`viaticos_vehiculos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`viaticos_vehiculos` (
  `idviaticos_vehiculos` INT NOT NULL AUTO_INCREMENT,
  `Kilometraje` VARCHAR(45) NULL,
  `Descripcion` VARCHAR(300) NULL,
  `Fecha` DATE NULL,
  `Vehiculos_Matricula` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idviaticos_vehiculos`, `Vehiculos_Matricula`),
  INDEX `fk_viaticos_vehiculos_Vehiculos1_idx` (`Vehiculos_Matricula` ASC),
  CONSTRAINT `fk_viaticos_vehiculos_Vehiculos1`
    FOREIGN KEY (`Vehiculos_Matricula`)
    REFERENCES `INE`.`Vehiculos` (`Matricula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `INE`.`productosEntregados` (
  `id_vale` INT NOT NULL,
  `id_producto` VARCHAR(45) NULL,
  `cantidad` INT NULL,
  CONSTRAINT `fk_Detalle_vale_Vales10`
    FOREIGN KEY (`id_vale`)
    REFERENCES `INE`.`Vales` (`id_vale`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ine`.`vehiculo_usado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ine`.`vehiculo_usado` (
  `idvehiculo_usado` INT NOT NULL AUTO_INCREMENT,
  `kilometraje` VARCHAR(45) NULL,
  `vehiculos_Matricula` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idvehiculo_usado`),
  INDEX `fk_vehiculo_usado_vehiculos_idx` (`vehiculos_Matricula` ASC),
  CONSTRAINT `fk_vehiculo_usado_vehiculos`
    FOREIGN KEY (`vehiculos_Matricula`)
    REFERENCES `ine`.`vehiculos` (`Matricula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ine`.`solicitud_vehiculo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ine`.`solicitud_vehiculo` (
  `idsolicitud_vehiculo` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(100) NULL,
  `Fecha_Salida` DATE NULL,
  `Fecha_Llegada` DATE NULL,
  `Lugar` VARCHAR(100) NULL,
  `Pernoctado` VARCHAR(10) NULL,
  `Puesto` VARCHAR(50) NULL,
  `Estado` VARCHAR(45) NULL,
  `Reporte` VARCHAR(1) NULL,
  `Motivo` VARCHAR(500) NULL,
  `Hora_Salida` VARCHAR(20) NULL,
  `Hora_Llegada` VARCHAR(20) NULL,
  `vehiculo_usado_idvehiculo_usado` INT NOT NULL,
  `Actividad` VARCHAR(500) NULL,
  `Vehiculo` VARCHAR(100) NULL,
  PRIMARY KEY (`idsolicitud_vehiculo`),
  INDEX `fk_solicitud_vehiculo_vehiculo_usado1_idx` (`vehiculo_usado_idvehiculo_usado` ASC),
  CONSTRAINT `fk_solicitud_vehiculo_vehiculo_usado1`
    FOREIGN KEY (`vehiculo_usado_idvehiculo_usado`)
    REFERENCES `ine`.`vehiculo_usado` (`idvehiculo_usado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
