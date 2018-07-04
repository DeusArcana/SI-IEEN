USE `INE`;
-- Permite usar los DELETE FROM para vaciar las tablas
SET SQL_SAFE_UPDATES = 0;
-- Permite insertar los datos sin importar si es parent_table
SET FOREIGN_KEY_CHECKS=0;

DELETE FROM `INE`.`Area` WHERE 1;
INSERT INTO `INE`.`Area` 
	VALUES 
        (1,'Asesor CLE',''),
        (2,'Comunicación Social','CS'),
        (3,'Consejo Local',''),
        (4,'Dirección Administración','DA'),
        (5,'Direccion de Organización y Capacitación Electoral','DOYCE'),
        (6,'Dirección Jurídica','DJ'),
        (7,'Organo Interno de Control','OIC'),
        (8,'Presidencia','P'),
        (9,'Secretaría General','SG'),
        (10,'Unidad de Transparencia','UT'),
		(11,'Unidad Técnica de Informática y Estadística','UTIE')
	;

DELETE FROM `INE`.`Puestos_Trabajo` WHERE 1;
INSERT INTO `INE`.`Puestos_Trabajo` 
	VALUES 
        (1,1,'Asesor/a de Consejera/o',0),
        (2,1,'Asesor/a de Consejera/o Presidente',0),
        (3,2,'Auxiliar A',0),
        (4,3,'Consejera/o Electoral',0),
        (5,3,'Consejera/o Presidente',0),
        (6,4,'Área de Nóminas, Planeación y Adquisiciones',0),
        (7,4,'Auxiliar Administrativo',0),
        (8,4,'Auxiliar B',0),
        (9,4,'Auxiliar de Oficina',0),
        (10,4,'Contador',0),
        (11,4,'Director/a de Administración',0),
        (12,5,'Auxiliar B',0),
        (13,5,'Auxiliar de Oficina',0),
        (14,5,'Coordinador/a de Organización Electoral',0),
        (15,5,'Encargado/a del Despacho de la Coordinación de Educación Cívica',0),
        (16,5,'Encargado/a del Despacho de la Dirección de Organización y Capacitación Electoral',0),
        (17,6,'Abogado/a',0),
        (18,6,'Encargado/a del Despacho de la Dirección Jurídica',0),
        (19,7,'Auxiliar C',0),
        (20,7,'Titular de Órgano Interno de Control',0),
        (21,8,'Asistente de Presidencia',0),
        (22,9,'Abogado/a A',0),
        (23,9,'Abogado/a B',0),
        (24,9,'Abogado/a',0),
        (25,9,'Auxiliar C',0),
        (26,9,'Auxiliar de Oficina',0),
        (27,9,'Coordinador/a de Prerrogativas y Partidos Políticos',0),
        (28,9,'Encargado/a del Despacho de la Secretaría General',0),
        (29,10,'Abogado B',0),
        (30,11,'Programador',0),
        (31,11,'Titular de la Unidad Técnica de Informática y Estádistica',0)
	;

/********************************************************************************
**		INSERTS `ine`.`empleados`
********************************************************************************/

-- `id_empleado` 	INT AUTO_INCREMENT,
-- `nombres` 		VARCHAR(60) NULL,
-- `apellido_p` 	VARCHAR(30) NULL,
-- `apellido_m` 	VARCHAR(30) NULL,
-- `calle` 			VARCHAR(100) NULL,
-- `colonia` 		VARCHAR(30) NULL,
-- `telefono` 		VARCHAR(18) NULL,
-- `codigo_postal` 	VARCHAR(8) NULL,
-- `fecha_nacimiento` DATE NULL,
-- `curp` 			VARCHAR(20) NULL,
-- `rfc` 			VARCHAR(13) NULL,
-- `municipio` 		VARCHAR(30) NULL,
-- `localidad` 		VARCHAR(50) NULL,
    
-- Vacia la tabla
DELETE FROM `INE`.`Empleados` WHERE 1;
-- Repara el contador
ALTER TABLE `INE`.`Empleados` AUTO_INCREMENT = 1;
INSERT INTO `INE`.`Empleados`(`nombres`, `apellido_p`, `apellido_m`, `calle`, `colonia`, `telefono`, `codigo_postal`, `fecha_nacimiento`,
									`curp`, `rfc`, `municipio`, `localidad`,`area`,`puesto`) 
	VALUES 
		('Kevin Alejandro','Méndez','Santana','Udine #18','Fracc. Bonaterra','311-162-16-71','63194','1995-09-26','MESK950926HNTNNV08','123456QWEASD','Tepic','Tepic',11,30)
	;

/***************************************************************************
** 			INSERTS `INE`.`Inventario_Granel`
****************************************************************************
**				INSERTS `INE`.`Inventario_granel`
**************************************************************************/

--  `id_productoGranel` VARCHAR(30) NOT NULL,
--  `nombre_prod` VARCHAR(50) NULL,
--  `descripcion` VARCHAR(100) NULL,
--  `almacen` VARCHAR(50) NULL,
--  `estatus` VARCHAR(35) NULL,
--  `marca` VARCHAR(50) NULL,
--  `observaciones` VARCHAR(300) NULL,
--  `stock_min` INT NULL,
--  `stock` INT NULL,
--  `tipo_uso` VARCHAR(100) NULL,

DELETE FROM `INE`.`Inventario_Granel` WHERE 1;
INSERT INTO `INE`.`Inventario_Granel` (`Folio`, `Numero`, `Extension`, `nombre_prod`, `descripcion`, `almacen`, `estatus`, `marca`, `observaciones`, `stock_min`, `stock`)
	VALUES
		('EY-99','1','', 'Archivador', 'Archivador  Carta Marmoleado Verde', 'Almacen 1', 'Disponible', 'OFIX', '',10,11),
        ('EY-99','2','', 'Archivador', 'Archivador Carta Amarillo', 'Almacen 1', 'Disponible', 'SMART','',10,21),
        ('EY-99','3','', 'Archivador', 'Archivador Carta Azul', 'Almacen 1', 'Disponible', 'LEFORT','',10,13 ),
        ('EY-99','4','', 'Sobre', 'Sobre con Burbuja', 'Almacen 1', 'Disponible', 'FORTEC', '18.4X30.48CM',10,15),
        ('EY-99','5','', 'Caja para Archivo', 'Caja Archivo Carta Plástica', 'Almacen 1', 'Disponible', 'OFIX', ' Medidas 38.5 x 31 x 25cm (largo-ancho-alto).',20,30),
        ('EY-99','6','', 'Caja para Archivo', 'Caja Archivo Carta Plastica Tapa Negra', 'Almacen 1', 'Disponible', 'OFIX', 'Medidas 38.5 x 31 x 25cm (largo-ancho-alto).',20,30),
        ('EY-99','7','', 'Hojas', 'Hojas para Carpeta 100H C-5 Clasico', 'Almacen 1', 'Disponible', 'SCRIBE', 'Tamaño carta. Cuadro No. 5.', 100,350),
        ('EY-99','8','', 'Boligrafo Azul', 'Boligrafo Mediano Stick Azul', 'Almacen 1', 'Disponible', 'OFIX', 'Caja con 12 piezas.', 30, 45),
        ('EY-99','9','', 'Boligrafo Rojo', 'Boligrafo Mediano Stick Rojo', 'Almacen 1', 'Disponible', 'Bic', 'Caja con 12 piezas.', 30, 45),
        ('EY-99','10','', 'Lapiz', 'Lapiz de Grafito', 'Almacen 1', 'Disponible', 'Bic', '2B Hexagonal Evolution', 40, 55),
        ('EY-99','11','', 'Lapiz', 'Lapiz de Grafito con Goma', 'Almacen 1', 'Disponible', 'Mirado', '#2 HEXAG MIRADO CJA CON 12', 50, 65),
        ('EY-99','12','', 'Goma', 'Goma para Borrar de Migajón', 'Almacen 1', 'Disponible', 'Pelikan', 'BOL/3 STRIKE 20 Bolsa con 3 piezas', 25, 30),
        ('EY-99','13','', 'Sujeta Documentos', 'SUJETADOR METAL NEGRO 25MM', 'Almacen 1', 'Disponible', 'SMART', 'Agarrapapel 25MM', 15, 20)
	;

DELETE FROM `INE`.`Puestos` WHERE 1;
INSERT INTO `INE`.`Puestos`
	VALUES 
		('SuperUsuario'),
		('Auxiliar'),
		('Administración'),
		('Jefe de departamento'),
		('Usuario Depto.'),
        ('Organización'),
        ('Presidencia'),
        ('Secretaria')
	;
    
DELETE FROM `INE`.`TipoSolicitud` WHERE 1;
INSERT INTO `INE`.`TipoSolicitud` 
	VALUES
		('Solicitud Baja'),
		('Solicitud Comodato'),
        ('Solicitud Donación'),
        ('Solicitud Reemplazo'),
        ('Solicitud Salida')
        
    ;
DELETE FROM `INE`.`Permisos_Solicitud` WHERE 1;
INSERT INTO `INE`.`Permisos_Solicitud` 
	VALUES
		('Solicitud Baja',		'Auxiliar',				false),
		('Solicitud Baja',		'Administración',		true),
		('Solicitud Baja',		'Jefe de departamento',	true),
        ('Solicitud Baja',		'Usuario Depto.',		false),
        ('Solicitud Baja',		'Organización', 		true),
        ('Solicitud Baja',		'Presidencia', 			true),
        ('Solicitud Baja',		'SuperUsuario', 		true),
        ('Solicitud Baja',		'Secretaria', 			false),
		('Solicitud Comodato',	'Auxiliar',				false),
		('Solicitud Comodato',	'Administración',		true),
        ('Solicitud Comodato',	'Jefe de departamento',	true),
        ('Solicitud Comodato',	'Usuario Depto.',		false),
        ('Solicitud Comodato',	'Organización',	    	true),
        ('Solicitud Comodato',	'Presidencia',	    	true),
        ('Solicitud Comodato',	'SuperUsuario',	    	true),
		('Solicitud Comodato',	'Secretaria', 			false),
        ('Solicitud Donación',	'Auxiliar',				false),
        ('Solicitud Donación',	'Administración',		true),
        ('Solicitud Donación',	'Jefe de departamento',	true),
        ('Solicitud Donación',	'Usuario Depto.',		false),
        ('Solicitud Donación',	'Organización',		    true),
        ('Solicitud Donación',	'Presidencia',		    true),
        ('Solicitud Donación',	'SuperUsuario',	    	true),
        ('Solicitud Donación',	'Secretaria', 			false),
        ('Solicitud Reemplazo',	'Auxiliar',				false),
        ('Solicitud Reemplazo',	'Administración',		true),
        ('Solicitud Reemplazo',	'Jefe de departamento',	true),
        ('Solicitud Reemplazo',	'Usuario Depto.',		false),
        ('Solicitud Reemplazo',	'Organización',		    false),
        ('Solicitud Reemplazo',	'Presidencia',		    true),
        ('Solicitud Reemplazo',	'SuperUsuario',	    	true),
        ('Solicitud Reemplazo',	'Secretaria', 			false),
        ('Solicitud Salida',	'Auxiliar',				false),
		('Solicitud Salida',	'Administración',		true),
		('Solicitud Salida',	'Jefe de departamento',	true),
        ('Solicitud Salida',	'Usuario Depto.',		false),
        ('Solicitud Salida',	'Organización', 		true),
        ('Solicitud Salida',	'Presidencia', 			true),
        ('Solicitud Salida',	'SuperUsuario', 		true),
        ('Solicitud Salida',	'Secretaria', 			false)
	;
    
    DELETE FROM `INE`.`TipoVale` WHERE 1;
INSERT INTO `INE`.`TipoVale` 
	VALUES
		('Vale de resguardo'),
		('Vale de recolección'),
        ('Vale de reemplazo')
    ;
DELETE FROM `INE`.`Permiso_Vale` WHERE 1;
INSERT INTO `INE`.`Permiso_Vale` 
	VALUES
		('Vale de resguardo',		'Auxiliar',				false),
		('Vale de resguardo',		'Administración',		true),
		('Vale de resguardo',		'Jefe de departamento',	true),
        ('Vale de resguardo',		'Usuario Depto.',		false),
        ('Vale de resguardo',		'Organización', 		true),
        ('Vale de resguardo',		'Presidencia', 			true),
        ('Vale de resguardo',		'SuperUsuario', 		true),
        ('Vale de resguardo',		'Secretaria', 			false),
		('Vale de recolección',	'Auxiliar',				false),
		('Vale de recolección',	'Administración',		true),
        ('Vale de recolección',	'Jefe de departamento',	true),
        ('Vale de recolección',	'Usuario Depto.',		false),
        ('Vale de recolección',	'Organización',	    	true),
        ('Vale de recolección',	'Presidencia',	    	true),
        ('Vale de recolección',	'SuperUsuario',	    	true),
		('Vale de recolección',	'Secretaria', 			false),
        ('Vale de reemplazo',	'Auxiliar',				false),
        ('Vale de reemplazo',	'Administración',		true),
        ('Vale de reemplazo',	'Jefe de departamento',	true),
        ('Vale de reemplazo',	'Usuario Depto.',		false),
        ('Vale de reemplazo',	'Organización',		    false),
        ('Vale de reemplazo',	'Presidencia',		    true),
        ('Vale de reemplazo',	'SuperUsuario',	    	true),
        ('Vale de reemplazo',	'Secretaria', 			false)
	;

INSERT INTO `Bodegas`(`Bodegas`.`Nom_Bodega`) VALUES
	('Bodega UTIE'),
	('Bodega DOyCE'),
	('Bodega Admon')
;


DELETE FROM `INE`.`User` WHERE 1;    
INSERT INTO `INE`.`User`  (`id_user`, `id_empleado`, `documentacion`, `password`, `puesto`, `estatus`)
	VALUES
		('Mendez26', 1, true, '123', 'SuperUsuario', 'Activo');/*,
        ('Larry', 2, true, '123', 'Organización', 'Administración'),
        ('Leiva', 3, true, '123', 'Auxiliar', 'Almacén'),
        ('Quiñones', 4, true, '123', 'Auxiliar', 'Informática'),
        ('Lima', 5, true, '123', 'Presidencia', 'Administración'),
        ('Emily', 6, true, '123', 'Usuario Depto.', 'Administración'),
        ('Julieta', 7, true, '123', 'Jefe de departamento', 'Informática'),
        ('Alves', 8, true, '123', 'Secretaria', 'Administración'),
        ('Barbosa', 9, true, '123', 'Administración', 'Administración');*/
/********************************************************************************
**		INSERTS `INE`.`Vehiculos`
********************************************************************************/
--  `Marca` VARCHAR(45) NULL,
--  `Linea` VARCHAR(45) NULL,
--  `Clase` VARCHAR(45) NULL,
--  `Kilometraje` VARCHAR(45) NULL,
--  `Modelo` VARCHAR(45) NULL,
--  `Color` VARCHAR(45) NULL,
--  `Motor` VARCHAR(45) NULL,
--  `Matricula` VARCHAR(45) NOT NULL,
--  `Observaciones` VARCHAR(1000) NULL,
--  `Imagen` LONGBLOB NULL,
--  `Estsado` VARCHAR(100) NULL,
--  `id_user` VARCHAR(20) NOT NULL PRIMARY KEY,

DELETE FROM `INE`.`Modulos` WHERE 1;
INSERT INTO `INE`.`Modulos` 
	VALUES
		('Usuarios'),
        ('Inventario'),
        ('Permisos'),
        ('Vehiculos'),
        ('Asignación'),
        ('Solicitud Viaticos'),
        ('Respuesta Viaticos')
	;
DELETE FROM `INE`.`Permisos` WHERE 1;
INSERT INTO `INE`.`Permisos` 
	VALUES
		('Mendez26',	'Usuarios',				true,true,true,true),
        ('Mendez26',	'Inventario',			true,true,true,true),
        ('Mendez26',	'Permisos',				true,true,true,true),
        ('Mendez26',	'Vehiculos',			true,true,true,true),
        ('Mendez26',	'Asignación',			true,true,true,true),
        ('Mendez26',	'Respuesta Viaticos',	true,true,true,true),
        ('Mendez26',	'Solicitud Viaticos',	true,true,true,true);

-- -----------------------------------------------------------------------------
--					INSERTS `INE`.`Folio`
-- ------------------------------------------------------------------------------
INSERT INTO `Folio`(`Folio`.`ID_Folio`, `Folio`.`descripcion`) VALUES
	('EY-01', 'Mobiliario y Equipo de Oficina'),
	('EY-02', 'Equipo de Computo'),
	('EY-03', 'Equipo de Fotografía y Video'),
	('EY-04', 'Aire Acondicionado'),
	('EY-05', 'Equipo de Comunicación'),
	('EY-06', 'Equipos y Aparatos Audiovisuales'),
	('EY-07', 'Equipo Diverso'),
	('EY-10', 'Equipo de Transporte'),
    ('EY-99', 'Consumibles')
;
        
DELETE FROM `INE`.`Permisos_Puesto` WHERE 1;
INSERT INTO `INE`.`Permisos_Puesto` 
	VALUES
		('SuperUsuario',		'Usuarios',		true,true,true,true),
        ('SuperUsuario',		'Inventario',	true,true,true,true),
        ('SuperUsuario',		'Permisos',		true,true,true,true),
        ('SuperUsuario',		'Vehiculos',	true,true,true,true),
        ('SuperUsuario',		'Asignación',	true,true,true,true),
        ('SuperUsuario',		'Respuesta Viaticos',	true,true,true,true),
        ('SuperUsuario',		'Solicitud Viaticos',	true,true,true,true),
		('Auxiliar',			'Usuarios',		false,false,false,false),
        ('Auxiliar',			'Inventario',	false,false,false,true),
        ('Auxiliar',			'Permisos',		false,false,false,false),
        ('Auxiliar',			'Vehiculos',	false,false,false,false),
        ('Auxiliar',			'Asignación',	false,false,false,false),
        ('Auxiliar',		'Respuesta Viaticos',	false,false,false,false),
        ('Auxiliar',		'Solicitud Viaticos',	false,false,false,false),
		('Administración',		'Usuarios',		false,false,false,false),
        ('Administración',		'Inventario',	true,true,true,true),
        ('Administración',		'Permisos',		false,false,false,false),
        ('Administración',		'Vehiculos',	true,true,true,true),
        ('Administración',		'Asignación',	true,true,true,true),
        ('Administración',		'Respuesta Viaticos',	false,false,false,false),
        ('Administración',		'Solicitud Viaticos',	false,false,false,false),
		('Jefe de departamento','Usuarios',		false,false,false,false),
        ('Jefe de departamento','Inventario',	true,true,true,true),
        ('Jefe de departamento','Permisos',		false,false,false,false),
        ('Jefe de departamento','Vehiculos',	false,false,false,false),
        ('Jefe de departamento','Asignación',	false,false,false,false),
        ('Jefe de departamento','Respuesta Viaticos',	false,false,false,false),
        ('Jefe de departamento','Solicitud Viaticos',	false,false,false,false),
		('Usuario Depto.',		'Usuarios',		false,false,false,false),
        ('Usuario Depto.',		'Inventario',	false,false,false,true),
        ('Usuario Depto.',		'Permisos',		false,false,false,false),
        ('Usuario Depto.',		'Vehiculos',	false,false,false,false),
        ('Usuario Depto.',		'Asignación',	false,false,false,false),
        ('Usuario Depto.',		'Respuesta Viaticos',	false,false,false,false),
        ('Usuario Depto.',		'Solicitud Viaticos',	false,false,false,false),
        ('Organización',		'Usuarios',		false,false,false,false),
        ('Organización',		'Inventario',	false,false,false,true),
        ('Organización',		'Permisos',		false,false,false,false),
        ('Organización',		'Vehiculos',	false,false,false,false),
        ('Organización',		'Asignación',	false,false,false,false),
        ('Organización',		'Respuesta Viaticos',	false,false,false,false),
        ('Organización',		'Solicitud Viaticos',	false,false,false,false),
        ('Presidencia',			'Usuarios',		false,false,false,false),
        ('Presidencia',			'Inventario',	false,false,false,true),
        ('Presidencia',			'Permisos',		false,false,false,false),
        ('Presidencia',			'Vehiculos',	false,false,false,false),
        ('Presidencia',			'Asignación',	false,false,false,false),
        ('Presidencia',		'Respuesta Viaticos',	false,false,false,false),
        ('Presidencia',		'Solicitud Viaticos',	false,false,false,false),
        ('Secretaria',			'Usuarios',		false,false,false,false),
        ('Secretaria',			'Inventario',	false,false,false,false),
        ('Secretaria',			'Permisos',		false,false,false,false),
        ('Secretaria',			'Vehiculos',	false,false,false,false),
        ('Secretaria',			'Asignación',	false,false,false,false),
        ('Secretaria',		'Respuesta Viaticos',	false,false,false,false),
        ('Secretaria',		'Solicitud Viaticos',	true,true,true,true);
        

LOAD DATA LOCAL INFILE 'C:\\Users\\kevin\\OneDrive\\Documentos\\GitHub\\SI-IEEN\\INE\\src\\db\\data_test.csv' 
REPLACE INTO TABLE `ine`.`inventario` 
FIELDS TERMINATED BY ',' 
ESCAPED BY '"'
LINES TERMINATED BY '\r\n' 
(`Folio`, `Numero`, `Extension`, `NO_Serie`, `Nombre_Prod`, `Marca`, `Modelo`, `Color`, `Fecha_Compra`, `Factura`, `Importe`, `Observaciones`);

UPDATE `ine`.`inventario` SET `ine`.`inventario`.`estatus` = 'Disponible';

-- Return initial state
SET SQL_SAFE_UPDATES = 1;
SET FOREIGN_KEY_CHECKS=1;