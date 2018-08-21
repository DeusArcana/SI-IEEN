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
CREATE SCHEMA IF NOT EXISTS `INE` DEFAULT CHARACTER SET latin1 COLLATE latin1_spanish_ci ;
USE `INE` ;

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
  `ID_Area` INT NOT NULL AUTO_INCREMENT,
  `Area` VARCHAR(255) NOT NULL,
  `Siglas` VARCHAR(32),
  PRIMARY KEY (`ID_Area`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `INE`.`Puestos_Trabajo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Puestos_Trabajo`;
CREATE TABLE `Puestos_Trabajo` (
  `ID_Puesto` INT NOT NULL AUTO_INCREMENT,
  `ID_Area` INT NOT NULL,
  `Puesto`  VARCHAR(255) NOT NULL,
  `Sueldo`  DOUBLE NOT NULL,
  `SinPernoctar100` float null,
  `Pernoctando100` float null,
  `SinPernoctar30100` float null,
  `Pernoctando30100` float null,
  `SinPernoctarBDB` float null,
  `PernoctandoBDB` float null,
  `SinPernoctarFDE` float null,
  `PernoctandoFDE` float null,
  PRIMARY KEY (`ID_Puesto`)
) ENGINE = InnoDB;

ALTER TABLE `Puestos_Trabajo`
ADD CONSTRAINT `fk_Puestos_Trabajo_Area`
  FOREIGN KEY (`ID_Area`)
  REFERENCES `Area` (`ID_Area`)
  ON DELETE NO ACTION
  ON UPDATE CASCADE;
  
-- -----------------------------------------------------
-- Table `INE`.`Empleados`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Empleados` (
  `id_empleado` INT NOT NULL AUTO_INCREMENT,
  `nombres` VARCHAR(60) NULL,
  `apellido_p` VARCHAR(30) NULL,
  `apellido_m` VARCHAR(30) NULL,
  `area` INT NOT NULL,
  `puesto` INT NOT NULL,
  `calle` VARCHAR(100) NULL,
  `colonia` VARCHAR(30) NULL,
  `telefono` VARCHAR(18) NULL,
  `codigo_postal` VARCHAR(8) NULL,
  `fecha_nacimiento` DATE NULL,
  `curp` VARCHAR(20) NULL,
  `rfc` VARCHAR(13) NULL,
  `municipio` VARCHAR(30) NULL,
  `localidad` VARCHAR(50) NULL,
  `estatus` VARCHAR(50) NULL,
  PRIMARY KEY (`id_empleado`))
ENGINE = InnoDB;

ALTER TABLE `Empleados`
ADD CONSTRAINT `fk_Empleados_Puestos_Trabajo`
  FOREIGN KEY (`puesto`)
  REFERENCES `Puestos_Trabajo` (`ID_Puesto`)
  ON DELETE NO ACTION
  ON UPDATE CASCADE;
  
ALTER TABLE `Empleados`
ADD CONSTRAINT `fk_Empleados_Area`
  FOREIGN KEY (`area`)
  REFERENCES `Area` (`ID_Area`)
  ON DELETE NO ACTION
  ON UPDATE CASCADE;

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
  `ID_Folio` CHAR(6) NOT NULL,
  `Descripcion` VARCHAR(255),
  PRIMARY KEY `pk_ID_Folio`(`ID_Folio`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `INE`.`Bodegas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Bodegas` (
  `ID_Bodega` INT NOT NULL AUTO_INCREMENT,
  `Nom_Bodega` VARCHAR(255) NOT NULL,
  `ID_Responsable` INT NULL,
  PRIMARY KEY (`ID_Bodega`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `INE`.`Inventario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Inventario` (
  `Folio` 			CHAR(6) NOT NULL,
  `Numero` 			INT NOT NULL,
  `Extension` 		CHAR(1) NULL,
  `nombre_prod` 	VARCHAR(50) NULL,
  `descripcion` 	VARCHAR(500) NULL,
  `ubicacion` 		VARCHAR(255) NULL,
  `estatus` 		VARCHAR(35) NULL,
  `marca` 			VARCHAR(50) NULL,
  `observaciones` 	VARCHAR(300) NULL,
  `no_serie` 		VARCHAR(45) NULL,
  `modelo` 			VARCHAR(100) NULL,
  `color` 			VARCHAR(30) NULL,
  `cantidad_fotos` 	INT NULL,
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
-- Table `INE`.`Productos_Asignados`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Productos_Asignados`;
CREATE TABLE `Productos_Asignados` (
  `ID_Producto` VARCHAR(255),
  `Status` VARCHAR(32),
  `Fecha_Seleccion` VARCHAR(255),
  `Salida` TINYINT(1),
  PRIMARY KEY `pk_ID_Producto`(`ID_Producto`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `INE`.`Manejo_Bienes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Documentos`;
CREATE TABLE `Documentos` (
  `ID_Documento` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Clave` VARCHAR(255),
  `Fecha_Creacion` DATE, 
  `Fecha_Salida` DATE,
  `No_Acta` VARCHAR(255) NULL,
  PRIMARY KEY `pk_ID_Documento`(`ID_Documento`)
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `INE`.`Inv_Docs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Inv_Docs`;
CREATE TABLE `Inv_Docs` (
  `ID_Producto` VARCHAR(255),
  `ID_Documento` INT UNSIGNED 
) ENGINE = InnoDB;

ALTER TABLE `Inv_Docs`
ADD CONSTRAINT `fk_Inv_Docs_Documentos`
  FOREIGN KEY (`ID_Documento`)
  REFERENCES `Documentos` (`ID_Documento`)
  ON DELETE NO ACTION
  ON UPDATE CASCADE;


ALTER TABLE `Inv_Docs`
ADD CONSTRAINT `fk_Inv_Docs_Productos_Asignados`
  FOREIGN KEY (`ID_Producto`)
  REFERENCES `Productos_Asignados` (`ID_Producto`)
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
  `fecha_salida` DATETIME NULL,
  CONSTRAINT `fk_Resguardo_personal_User1`
    FOREIGN KEY (`id_user`)
    REFERENCES `INE`.`User` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
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
-- Table `INE`.`SolicitudSalida`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `INE`.`SolicitudSalida` (
  `Folio` VARCHAR(30) NOT NULL,
  `Num` INT NOT NULL,
  `Año` INT NOT NULL,
  `estado` VARCHAR(50) NULL,
  `user_autorizo` VARCHAR(20) NULL,
  `id_user` VARCHAR(20) NOT NULL,
  `fecha_solicitud` DATETIME NULL,
  `fecha_respuesta` DATETIME NULL,
  PRIMARY KEY (`Folio`, `Num`, `Año`),
  INDEX `fk_Solicitudes_User1_idx` (`id_user` ASC),
  CONSTRAINT `fk_Solicitudes_User2`
    FOREIGN KEY (`id_user`)
    REFERENCES `INE`.`User` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `INE`.`Detalle_solicitudSalida`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `INE`.`Detalle_solicitudSalida` (
  `id_solicitud` VARCHAR(20) NOT NULL,
  `id_producto` VARCHAR(30) NOT NULL,
  `cantidad_solicitada` INT NULL,
  `cantidad_autorizada` INT NULL)
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
-- Table `INE`.`Categorias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Categorias` (
  `ID_Categoria` CHAR(6) NOT NULL,
  `Nom_Categoria` VARCHAR(255),
  PRIMARY KEY `pk_ID_Categoria`(`ID_Categoria`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `INE`.`Inventario_granel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Inventario_granel` (
  `Folio` CHAR(6) NOT NULL,
  `Numero` INT NOT NULL,
  `Extension` CHAR(1) NULL,
  `nombre_prod` VARCHAR(50) NULL,
  `descripcion` VARCHAR(100) NULL,
  `almacen` VARCHAR(50) NULL,
  `estatus` VARCHAR(35) NULL,
  `marca` VARCHAR(50) NULL,
  `observaciones` VARCHAR(300) NULL,
  `stock_min` INT NULL,
  `stock` INT NULL,
  `categoria` VARCHAR(50) NULL)
ENGINE = InnoDB;

ALTER TABLE `Inventario_Granel`
ADD CONSTRAINT `fk_Inventario_Granel_Folio`
  FOREIGN KEY (`Folio`)
  REFERENCES `Folio` (`ID_Folio`)
  ON DELETE NO ACTION
  ON UPDATE CASCADE;
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
-- Table `INE`.`Vales`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Vales` (
  `Folio` varchar(15) NULL,
  `Numero` INT NOT NULL,
  `Año` INT NOT NULL,
  `tipo_vale` VARCHAR(30) NOT NULL,
  `fecha_vale` DATETIME NULL,
  `id_empleado` INT NOT NULL,
  INDEX `fk_Vales_tipoVale1_idx` (`tipo_vale` ASC),
  INDEX `fk_Vales_User1_idx` (`id_empleado` ASC),
  CONSTRAINT `fk_Vales_tipoVale1`
    FOREIGN KEY (`tipo_vale`)
    REFERENCES `INE`.`tipoVale` (`tipo_vale`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Vales_User1`
    FOREIGN KEY (`id_empleado`)
    REFERENCES `INE`.`Empleados` (`id_empleado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `INE`.`Detalle_vale`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `INE`.`Detalle_vale` (
  `id_vale` varchar(20) NOT NULL,
  `id_producto` VARCHAR(45) NULL,
  `cantidad` INT NULL,
  `estado` VARCHAR(25) NULL,
  `fecha_entrega` DATETIME NULL
  )
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
-- Table `ine`.`solicitud_viatico`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ine`.`solicitud_viatico` ;

CREATE TABLE IF NOT EXISTS `ine`.`solicitud_viatico` (
  `idSolicitud` INT(11) NOT NULL AUTO_INCREMENT,
  `Fecha_salida` DATE NULL DEFAULT NULL,
  `Lugar` VARCHAR(100) NULL DEFAULT NULL,
  `Nombre` VARCHAR(100) NULL DEFAULT NULL,
  `Actividad` VARCHAR(500) NULL DEFAULT NULL,
  `Pernoctado` VARCHAR(10) NULL DEFAULT NULL,
  `Puesto` VARCHAR(500) NULL DEFAULT NULL,
  `Fecha_llegada` DATE NULL DEFAULT NULL,
  `Estado` VARCHAR(45) NULL DEFAULT NULL,
  `Reporte` VARCHAR(1) NULL DEFAULT NULL,
  `Motivo` VARCHAR(500) NULL DEFAULT NULL,
  `Hora_Salida` VARCHAR(20) NULL DEFAULT NULL,
  `Hora_Llegada` VARCHAR(20) NULL DEFAULT NULL,
  `gastos_comprobar` VARCHAR(45) NULL,
  `consejero_presidente` VARCHAR(45) NULL,
  PRIMARY KEY (`idSolicitud`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


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
-- Table `ine`.`informe`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ine`.`informe` ;

CREATE TABLE IF NOT EXISTS `ine`.`informe` (
  `Id_Informe` INT(11) NOT NULL AUTO_INCREMENT,
  `Observaciones` VARCHAR(200) NULL DEFAULT NULL,
  `Observaciones_Vehiculo` VARCHAR(200) NULL DEFAULT NULL,
  `Solicitud_idSolicitud` INT(11) NOT NULL,
  `importe_total` FLOAT NULL,
  PRIMARY KEY (`Id_Informe`),
  INDEX `fk_Informe_Solicitud_viatico1_idx` (`Solicitud_idSolicitud` ASC),
  CONSTRAINT `fk_Informe_Solicitud_viatico1`
    FOREIGN KEY (`Solicitud_idSolicitud`)
    REFERENCES `ine`.`solicitud_viatico` (`idSolicitud`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ine`.`gastos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ine`.`gastos` ;

CREATE TABLE IF NOT EXISTS `ine`.`gastos` (
  `Id_Gastos` INT(11) NOT NULL AUTO_INCREMENT,
  `Precio` VARCHAR(20) NULL DEFAULT NULL,
  `Descripcion` VARCHAR(200) NULL DEFAULT NULL,
  `Factura` VARCHAR(45) NULL,
  PRIMARY KEY (`Id_Gastos`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ine`.`informe_gastos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ine`.`informe_gastos` ;

CREATE TABLE IF NOT EXISTS `ine`.`informe_gastos` (
  `Gastos_Id_Gastos` INT(11) NOT NULL,
  `Informe_Id_Informe` INT(11) NOT NULL,
  INDEX `fk_Informe_Gastos_Gastos1_idx` (`Gastos_Id_Gastos` ASC),
  INDEX `fk_Informe_Gastos_Informe1_idx` (`Informe_Id_Informe` ASC),
  CONSTRAINT `fk_Informe_Gastos_Gastos1`
    FOREIGN KEY (`Gastos_Id_Gastos`)
    REFERENCES `ine`.`gastos` (`Id_Gastos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Informe_Gastos_Informe1`
    FOREIGN KEY (`Informe_Id_Informe`)
    REFERENCES `ine`.`informe` (`Id_Informe`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


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
  `cantidad_fotos` INT NULL,
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
DROP TABLE IF EXISTS `ine`.`solicitud_vehiculo` ;

CREATE TABLE IF NOT EXISTS `ine`.`solicitud_vehiculo` (
  `idsolicitud_vehiculo` INT NOT NULL AUTO_INCREMENT,
  `vehiculo_usado_idvehiculo_usado` INT NOT NULL,
  `Vehiculo` VARCHAR(100) NULL,
  `chofer` VARCHAR(1) NULL,
  PRIMARY KEY (`idsolicitud_vehiculo`),
  INDEX `fk_solicitud_vehiculo_vehiculo_usado1_idx` (`vehiculo_usado_idvehiculo_usado` ASC),
  CONSTRAINT `fk_solicitud_vehiculo_vehiculo_usado1`
    FOREIGN KEY (`vehiculo_usado_idvehiculo_usado`)
    REFERENCES `ine`.`vehiculo_usado` (`idvehiculo_usado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ine`.`vehiculo_viatico`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ine`.`vehiculo_viatico` ;

CREATE TABLE IF NOT EXISTS `ine`.`vehiculo_viatico` (
  `solicitud_vehiculo_idsolicitud_vehiculo` INT NOT NULL,
  `solicitud_viatico_idSolicitud` INT(11) NOT NULL,
  `agregado` VARCHAR(1) NULL,
  PRIMARY KEY (`solicitud_vehiculo_idsolicitud_vehiculo`, `solicitud_viatico_idSolicitud`),
  INDEX `fk_vehiculo_viatico_solicitud_viatico1_idx` (`solicitud_viatico_idSolicitud` ASC),
  CONSTRAINT `fk_vehiculo_viatico_solicitud_vehiculo1`
    FOREIGN KEY (`solicitud_vehiculo_idsolicitud_vehiculo`)
    REFERENCES `ine`.`solicitud_vehiculo` (`idsolicitud_vehiculo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vehiculo_viatico_solicitud_viatico1`
    FOREIGN KEY (`solicitud_viatico_idSolicitud`)
    REFERENCES `ine`.`solicitud_viatico` (`idSolicitud`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ine`.`Estado`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ine`.`Estado` ;

CREATE TABLE IF NOT EXISTS `ine`.`Estado` (
  `idEstado` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NULL,
  PRIMARY KEY (`idEstado`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ine`.`Localidad`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ine`.`Localidad` ;

CREATE TABLE IF NOT EXISTS `ine`.`Localidad` (
  `idLocalidad` INT NOT NULL AUTO_INCREMENT,
  `Estado_idEstado` INT NOT NULL,
  `Nombre` VARCHAR(100) NULL,
  PRIMARY KEY (`idLocalidad`),
  INDEX `fk_Localidad_Estado1_idx` (`Estado_idEstado` ASC),
  CONSTRAINT `fk_Localidad_Estado1`
    FOREIGN KEY (`Estado_idEstado`)
    REFERENCES `ine`.`Estado` (`idEstado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `ine`.`Estado` (
  `idEstado` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NULL,
  PRIMARY KEY (`idEstado`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `ine`.`Localidad` (
  `idLocalidad` INT NOT NULL AUTO_INCREMENT,
  `Estado_idEstado` INT NOT NULL,
  `Nombre` VARCHAR(100) NULL,
  PRIMARY KEY (`idLocalidad`),
  INDEX `fk_Localidad_Estado1_idx` (`Estado_idEstado` ASC),
  CONSTRAINT `fk_Localidad_Estado1`
    FOREIGN KEY (`Estado_idEstado`)
    REFERENCES `ine`.`Estado` (`idEstado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP TABLE IF EXISTS `INE`.`solicitud_pase`;
CREATE TABLE IF NOT EXISTS `INE`.`solicitud_pase` (
  `Folio` VARCHAR(12) NULL DEFAULT NULL,
  `Numero` VARCHAR(7) NULL DEFAULT NULL,
  `Año` VARCHAR(10) NULL DEFAULT NULL,
  `Nombre` VARCHAR(100) NULL DEFAULT NULL,
  `Puesto` VARCHAR(100) NULL DEFAULT NULL,
  `Area` VARCHAR(70) NULL DEFAULT NULL,
  `Fecha` VARCHAR(20) NULL DEFAULT NULL,
  `Hora_ES` VARCHAR(20) NULL DEFAULT NULL,
  `Hora_Llegada` VARCHAR(20) NULL DEFAULT NULL,
  `Horas` VARCHAR(20) NULL DEFAULT NULL,
  `Tipo_Horario` VARCHAR(30) NULL DEFAULT NULL,
  `Tipo_Asunto` VARCHAR(30) NULL DEFAULT NULL,
  `Asunto` VARCHAR(500) NULL DEFAULT NULL,
  `Estado` VARCHAR(10) NULL DEFAULT NULL)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Admin_config` (
	`Ceros_Inventario` INT,
	`Ceros_Vales` INT
) ENGINE = InnoDB;
DROP TABLE IF EXISTS `INE`.`acceder_year`;
CREATE TABLE IF NOT EXISTS `INE`.`acceder_year`(
	`Year` VARCHAR(6) NULL DEFAULT NULL
) ENGINE = InnoDB;


/*Vistas*/
CREATE VIEW 
vista_permisosSolicitud AS
select tipo_solicitud, puesto from permisos_solicitud where permiso = 1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
