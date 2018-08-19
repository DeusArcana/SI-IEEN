USE `INE`;
-- Permite usar los DELETE FROM para vaciar las tablas
SET SQL_SAFE_UPDATES	=	0;
-- Permite insertar los datos sin importar si es parent_table
SET FOREIGN_KEY_CHECKS	=	0;

-- *******************************************************************************
-- 		INSERTS `INE`.`Area`
-- *******************************************************************************


DELETE FROM `INE`.`Area` WHERE 1;
INSERT INTO `INE`.`Area` 
	VALUES 
        (1,	'Asesor CLE','ASE'),
        (2,	'Comunicación Social','CS'),
        (3,	'Consejo Local','CLE'),
        (4,	'Dirección Administración','DA'),
        (5,	'Direccion de Organización y Capacitación Electoral','DOYCE'),
        (6,	'Dirección Jurídica','DJ'),
        (7,	'Órgano Interno de Control','OIC'),
        (8,	'Presidencia','P'),
        (9,	'Secretaría General','SG'),
        (10,'Unidad de Transparencia','UT'),
		(11,'Unidad Técnica de Informática y Estadística','UTIE')
	;

-- *******************************************************************************
-- 		INSERTS `INE`.`Puestos_Trabajo`
-- *******************************************************************************
DELETE FROM `INE`.`Puestos_Trabajo` WHERE 1;

INSERT INTO `INE`.`Puestos_Trabajo` 
	VALUES 
        (1,	1,'Asesor/a de Consejera/o',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (2,	1,'Asesor/a de Consejera/o Presidente',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        
        (3,	2,'Auxiliar A',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        
        (4,	3,'Consejera/o Electoral',0,840.00,1400.00,700.00,900.00,840.00,2800.00,2100.00,5600.00),
        (5,	3,'Consejera/o Presidente',0,840.00,1400.00,700.00,900.00,840.00,2800.00,2100.00,5600.00),
        
        (6,	4,'Área de Nóminas, Planeación y Adquisiciones',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (7,	4,'Auxiliar Administrativo',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (8,	4,'Auxiliar B',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (9,	4,'Auxiliar de Oficina',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (10,4,'Contador',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (11,4,'Director/a de Administración',0,540.00,1100.00,400.00,800.00,540.00,2150.00,1340.00,4000.00),
        
        (12,5,'Auxiliar B',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (13,5,'Auxiliar de Oficina',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (14,5,'Coordinador/a de Organización Electoral',0,370.00,730.00,250.00,500.00,370.00,1450.00,905.00,2410.00),
        (15,5,'Encargado/a del Despacho de la Coordinación de Educación Cívica',0,370.00,730.00,250.00,500.00,370.00,1450.00,905.00,2410.00),
        (16,5,'Encargado/a del Despacho de la Dirección de Organización y Capacitación Electoral',0,540.00,1100.00,400.00,800.00,540.00,2150.00,1340.00,4000.00),
        
        (17,6,'Abogado/a',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (18,6,'Encargado/a del Despacho de la Dirección Jurídica',0,540.00,1100.00,400.00,800.00,540.00,2150.00,1340.00,4000.00),
        
        (19,7,'Auxiliar C',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (20,7,'Titular de Órgano Interno de Control',0,540.00,1100.00,400.00,800.00,540.00,2150.00,1340.00,4000.00),
        
        (21,8,'Asistente de Presidencia',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        
        (22,9,'Abogado/a A',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (23,9,'Abogado/a B',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (24,9,'Abogado/a',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (25,9,'Auxiliar C',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (26,9,'Auxiliar de Oficina',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (27,9,'Coordinador/a de Prerrogativas y Partidos Políticos',0,370.00,730.00,250.00,500.00,370.00,1450.00,905.00,2410.00),
        (28,9,'Encargado/a del Despacho de la Secretaría General',0,540.00,1100.00,400.00,800.00,540.00,2150.00,1340.00,4000.00),
        
        (29,10,'Abogado B',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        
        (30,11,'Programador',0,300.00,590.00,180.00,350.00,300.00,1180.00,582.00,1745.00),
        (31,11,'Titular de la Unidad Técnica de Informática y Estádistica',0,370.00,730.00,250.00,500.00,370.00,1450.00,905.00,2410.00)
	;

-- *******************************************************************************
-- 		INSERTS `ine`.`empleados`
-- *******************************************************************************
DELETE FROM `INE`.`Empleados` WHERE 1;
ALTER TABLE `INE`.`Empleados` AUTO_INCREMENT = 1;
INSERT INTO `INE`.`Empleados`(`nombres`, `apellido_p`, `apellido_m`, `calle`, `colonia`, `telefono`, `codigo_postal`, `fecha_nacimiento`,
									`curp`, `rfc`, `municipio`, `localidad`,`area`,`puesto`,`estatus`) 
	VALUES 
		('Kevin Alejandro','Méndez','Santana','Udine #18','Fracc. Bonaterra','311-162-16-71','63194','1995-09-26','MESK950926HNTNNV08','123456QWEASD','Tepic','Tepic',11,30,'Activo')
	;

-- ***************************************************************************
--  			INSERTS `INE`.`Inventario_Granel`
-- ***************************************************************************
DELETE FROM `INE`.`Inventario_Granel` WHERE 1;
INSERT INTO `INE`.`Inventario_Granel` (`Folio`, `Numero`, `Extension`, `Nombre_Prod`, `Descripcion`, `Almacen`, `Estatus`, `Marca`, `Observaciones`, `Stock_Min`, `Stock`,`Categoria`)
	VALUES
		('EY-99','1','', 'Archivador', 'Archivador  Carta Marmoleado Verde', 'Almacen 1', 'Disponible', 'OFIX', '',10,11,'Articulos de Oficina'),
        ('EY-99','2','', 'Archivador', 'Archivador Carta Amarillo', 'Almacen 1', 'Disponible', 'SMART','',10,21,'Articulos de Oficina'),
        ('EY-99','3','', 'Archivador', 'Archivador Carta Azul', 'Almacen 1', 'Disponible', 'LEFORT','',10,13,'Articulos de Oficina'),
        ('EY-99','4','', 'Sobre', 'Sobre con Burbuja', 'Almacen 1', 'Disponible', 'FORTEC', '18.4X30.48CM',10,15,'Articulos de Oficina'),
        ('EY-99','5','', 'Caja para Archivo', 'Caja Archivo Carta Plástica', 'Almacen 1', 'Disponible', 'OFIX', ' Medidas 38.5 x 31 x 25cm (largo-ancho-alto).',20,30,'Articulos de Oficina'),
        ('EY-99','6','', 'Caja para Archivo', 'Caja Archivo Carta Plastica Tapa Negra', 'Almacen 1', 'Disponible', 'OFIX', 'Medidas 38.5 x 31 x 25cm (largo-ancho-alto).',20,30,'Articulos de Oficina'),
        ('EY-99','7','', 'Hojas', 'Hojas para Carpeta 100H C-5 Clasico', 'Almacen 1', 'Disponible', 'SCRIBE', 'Tamaño carta. Cuadro No. 5.', 100,350,'Articulos de Oficina'),
        ('EY-99','8','', 'Boligrafo Azul', 'Boligrafo Mediano Stick Azul', 'Almacen 1', 'Disponible', 'OFIX', 'Caja con 12 piezas.', 30, 45,'Articulos de Oficina'),
        ('EY-99','9','', 'Boligrafo Rojo', 'Boligrafo Mediano Stick Rojo', 'Almacen 1', 'Disponible', 'Bic', 'Caja con 12 piezas.', 30, 45,'Articulos de Oficina'),
        ('EY-99','10','', 'Lapiz', 'Lapiz de Grafito', 'Almacen 1', 'Disponible', 'Bic', '2B Hexagonal Evolution', 40, 55,'Articulos de Oficina'),
        ('EY-99','11','', 'Lapiz', 'Lapiz de Grafito con Goma', 'Almacen 1', 'Disponible', 'Mirado', '#2 HEXAG MIRADO CJA CON 12', 50, 65,'Articulos de Oficina'),
        ('EY-99','12','', 'Goma', 'Goma para Borrar de Migajón', 'Almacen 1', 'Disponible', 'Pelikan', 'BOL/3 STRIKE 20 Bolsa con 3 piezas', 25, 30,'Articulos de Oficina'),
        ('EY-99','13','', 'Sujeta Documentos', 'SUJETADOR METAL NEGRO 25MM', 'Almacen 1', 'Disponible', 'SMART', 'Agarrapapel 25MM', 15, 20,'Articulos de Oficina')
	;

-- *******************************************************************************
-- 		INSERTS `INE`.`Puestos`
-- *******************************************************************************
DELETE FROM `INE`.`Puestos` WHERE 1;
INSERT INTO `INE`.`Puestos`
	VALUES 
		('SuperUsuario'),
		('Administrador'),
        ('Auxiliar'),
		('Asistente'),
		('General')
	;
    
-- *******************************************************************************
-- 		INSERTS `INE`.`TipoSolicitud`
-- *******************************************************************************
DELETE FROM `INE`.`TipoSolicitud` WHERE 1;
INSERT INTO `INE`.`TipoSolicitud` 
	VALUES
        ('Solicitud Salida')
    ;

-- *******************************************************************************
-- 		INSERTS `INE`.`Categorias`
-- *******************************************************************************
DELETE FROM `INE`.`Categorias` WHERE 1;
INSERT INTO `INE`.`Categorias` 
	VALUES
        (1,'Articulos de Oficina'),
        (2,'Papelería Básica'),
        (3,'Cafetería y Limpieza'),
        (4,'Herramientas y Matenimiento'),
        (5,'Cajas'),
        (6,'Carpertas, folder y sobres'),
        (7,'Escritura'),
        (8,'Almacenamiento'),
        (9,'Impresión'),
        (10,'Limpieza de Cómputo');

-- *******************************************************************************
-- 		INSERTS `INE`.`Permisos_Solicitud`
-- *******************************************************************************
DELETE FROM `INE`.`Permisos_Solicitud` WHERE 1;
INSERT INTO `INE`.`Permisos_Solicitud` 
	VALUES
		('Solicitud Salida',	'SuperUsuario',		true),
		('Solicitud Salida',	'Administrador',	true),
        ('Solicitud Salida',	'Auxiliar',			true),
        ('Solicitud Salida',	'Asistente', 		true),
        ('Solicitud Salida',	'General',	 		true)
	;
    
-- *******************************************************************************
-- 		INSERTS `INE`.`TipoVale`
-- *******************************************************************************
DELETE FROM `INE`.`TipoVale` WHERE 1;
INSERT INTO `INE`.`TipoVale` 
	VALUES
		('Vale de resguardo'),
		('Vale de recolección')
    ;

-- *******************************************************************************
-- 		INSERTS `INE`.`Permiso_Vale`
-- *******************************************************************************
DELETE FROM `INE`.`Permiso_Vale` WHERE 1;
INSERT INTO `INE`.`Permiso_Vale` 
	VALUES
		('Vale de resguardo',		'SuperUsuario',		true),
		('Vale de resguardo',		'Administrador',	true),
		('Vale de resguardo',		'Auxiliar',			true),
        ('Vale de resguardo',		'Asistente',		true),
        ('Vale de resguardo',		'General', 			true),
        ('Vale de recolección',		'SuperUsuario',		true),
		('Vale de recolección',		'Administrador',	true),
		('Vale de recolección',		'Auxiliar',			true),
        ('Vale de recolección',		'Asistente',		true),
        ('Vale de recolección',		'General', 			true)
	;

-- *******************************************************************************
-- 		INSERTS `INE`.`Bodegas`
-- *******************************************************************************
INSERT INTO `INE`.`Bodegas`(`Bodegas`.`Nom_Bodega`) VALUES
	('Bodega UTIE'),
	('Bodega DOyCE'),
	('Bodega Admon')
;

-- *******************************************************************************
-- 		INSERTS `INE`.`User`
-- *******************************************************************************
DELETE FROM `INE`.`User` WHERE 1;    
INSERT INTO `INE`.`User`  (`ID_User`, `ID_Empleado`, `Documentacion`, `Password`, `Puesto`, `Estatus`)
	VALUES
		('Mendez26', 1, true,  '123', 'SuperUsuario', 'Activo');

-- *******************************************************************************
-- 		INSERTS `INE`.`Modulos`
-- *******************************************************************************
DELETE FROM `INE`.`Modulos` WHERE 1;
INSERT INTO `INE`.`Modulos` 
	VALUES
		('Usuarios'),
        ('Inventario'),
        ('Empleados'),
        ('Vehiculos'),
        ('Solicitudes'),
        ('Resguardo'),
        ('Configuración'),
        ('Permisos'),
        ('Solicitud Viaticos'),
        ('Tablon Solicitudes'),
        ('Informe'),
        ('Pase Salida')
	;

-- *******************************************************************************
-- 		INSERTS `INE`.`Permisos`
-- *******************************************************************************
DELETE FROM `INE`.`Permisos` WHERE 1;
INSERT INTO `INE`.`Permisos` 
	VALUES
		('Mendez26',	'Usuarios',				true, true, true, true),
        ('Mendez26',	'Inventario',			true, true, true, true),
        ('Mendez26',	'Empleados',			true, true, true, true),
        ('Mendez26',	'Vehiculos',			true, true, true, true),
        ('Mendez26',	'Solicitudes',			true, true, true, true),
        ('Mendez26',	'Resguardo',			true, true, true, true),
        ('Mendez26',	'Configuración',		true, true, true, true),
        ('Mendez26',	'Permisos',				true, true, true, true),
        ('Mendez26',	'Solicitud Viaticos',	true, true, true, true),
        ('Mendez26',	'Tablon Solicitudes',	true, true, true, true),
        ('Mendez26',	'Informe',				true, true, true, true),
        ('Mendez26',	'Pase Salida',			true, true, true, true);

-- -----------------------------------------------------------------------------
--					INSERTS `INE`.`Folio`
-- ------------------------------------------------------------------------------
INSERT INTO `Folio`(`Folio`.`ID_Folio`, `Folio`.`descripcion`) VALUES
	('EY-01', 'Mobiliario y Equipo de Oficina'),
	('EY-02', 'Equipo de Cómputo'),
	('EY-03', 'Equipo de Fotografía y Video'),
	('EY-04', 'Aire Acondicionado'),
	('EY-05', 'Equipo de Comunicación'),
	('EY-06', 'Equipos y Aparatos Audiovisuales'),
	('EY-07', 'Equipo Diverso'),
	('EY-10', 'Equipo de Transporte'),
    ('EY-99', 'Consumibles')
;

-- *******************************************************************************
-- 		INSERTS `INE`.`Permisos_Puesto`
-- *******************************************************************************      
DELETE FROM `INE`.`Permisos_Puesto` WHERE 1;
INSERT INTO `INE`.`Permisos_Puesto` 
	VALUES
		('SuperUsuario',	'Usuarios',			true, true, true, true),
        ('SuperUsuario',	'Inventario',		true, true, true, true),
        ('SuperUsuario',	'Empleados',		true, true, true, true),
        ('SuperUsuario',	'Vehiculos',		true, true, true, true),
        ('SuperUsuario',	'Solicitudes',		true, true, true, true),
        ('SuperUsuario',	'Resguardo',		true, true, true, true),
        ('SuperUsuario',	'Configuración',	true, true, true, true),
        ('SuperUsuario',	'Permisos',			true, true, true, true),
        ('SuperUsuario',	'Solicitud Viaticos',	true, true, true, true),
        ('SuperUsuario',	'Tablon Solicitudes',	true, true, true, true),
        ('SuperUsuario',	'Informe',				true, true, true, true),
        ('SuperUsuario',	'Pase Salida',			true, true, true, true),
        
        ('Administrador',	'Usuarios',			false, false, false, false),
        ('Administrador',	'Inventario',		true, true, true, true),
        ('Administrador',	'Empleados',		true, true, true, true),
        ('Administrador',	'Vehiculos',		true, true, true, true),
        ('Administrador',	'Solicitudes',		false, true, true, true),
        ('Administrador',	'Resguardo',		true, true, true, true),
        ('Administrador',	'Configuración',	false, false, false, false),
        ('Administrador',	'Permisos',			false, false, false, false),
		('Administrador',	'Solicitud Viaticos',	true, true, true, true),
        ('Administrador',	'Tablon Solicitudes',	false, false, false, false),
        ('Administrador',	'Informe',				false, false, false, false),
        ('Administrador',	'Pase Salida',			true, false, false, true),
        
        ('Auxiliar',		'Usuarios',			false, false, false, false),
        ('Auxiliar',		'Inventario',		true, false, false, true),
        ('Auxiliar',		'Empleados',		false, false, false, true),
        ('Auxiliar',		'Vehiculos',		true, false, false, true),
        ('Auxiliar',		'Solicitudes',		false, false, true, true),
        ('Auxiliar',		'Resguardo',		true, true, true, true),
        ('Auxiliar',		'Configuración',	false, false, false, false),
        ('Auxiliar',		'Permisos',			false, false, false, false),
        ('Auxiliar',	'Solicitud Viaticos',	false, false, false, false),
        ('Auxiliar',	'Tablon Solicitudes',	false, false, false, false),
        ('Auxiliar',	'Informe',				false, false, false, false),
        ('Auxiliar',	'Pase Salida',			false, false, false, false),
        
		('Asistente',		'Usuarios',			false, false, false, false),
        ('Asistente',		'Inventario',		false, false, false, true),
        ('Asistente',		'Empleados',		false, false, false, true),
        ('Asistente',		'Vehiculos',		false, false, false, true),
        ('Asistente',		'Solicitudes',		true, false, false, false),
        ('Asistente',		'Resguardo',		false, false, false, false),
        ('Asistente',		'Configuración',	false, false, false, false),
        ('Asistente',		'Permisos',			false, false, false, false),
        ('Asistente',		'Solicitud Viaticos',	true, false, true, true),
        ('Asistente',		'Tablon Solicitudes',	false, false, false, false),
        ('Asistente',		'Informe',				false, false, false, false),
        ('Asistente',		'Pase Salida',			false, false, false, false),
        
        ('General',			'Usuarios',			false, false, false, false),
        ('General',			'Inventario',		false, false, false, false),
        ('General',			'Empleados',		false, false, false, false),
        ('General',			'Vehiculos',		false, false, false, false),
        ('General',			'Solicitudes',		false, false, false, false),
        ('General',			'Resguardo',		false, false, false, false),
        ('General',			'Configuración',	false, false, false, false),
        ('General',			'Permisos',			false, false, false, false),
        ('General',		'Solicitud Viaticos',	false, false, false, false),
        ('General',		'Tablon Solicitudes',	false, false, false, false),
        ('General',		'Informe',				false, false, false, false),
        ('General',		'Pase Salida',			false, false, false, false);
        
-- -----------------------------------------------------------------------------
--	Importación de productos desde data_test.csv para la tabla de Inventario
-- ------------------------------------------------------------------------------
LOAD DATA LOCAL INFILE 'C:\\Users\\kevin\\OneDrive\\Documentos\\GitHub\\SI-IEEN\\INE\\src\\db\\data_test.csv' 
REPLACE INTO TABLE `INE`.`Inventario` 
FIELDS TERMINATED BY ',' 
ESCAPED BY '"'
LINES TERMINATED BY '\r\n' 
(`Folio`, `Numero`, `Extension`, `NO_Serie`, `Nombre_Prod`, `Marca`, `Modelo`, `Color`, `Fecha_Compra`, `Factura`, `Importe`, `Observaciones`);

LOAD DATA local INFILE 'C:\\Users\\kevin\\OneDrive\\Documentos\\GitHub\\SI-IEEN\\INE\\src\\db\\Estados.sql' INTO TABLE Estado(Nombre);
LOAD DATA local INFILE 'C:\\Users\\kevin\\OneDrive\\Documentos\\GitHub\\SI-IEEN\\INE\\src\\db\\Localidades.sql' INTO TABLE Localidad(Estado_idEstado,Nombre);
INSERT INTO director_general VALUES('INSERTE EL CONSEJERO PRESIDENTE');

-- -----------------------------------------------------------------------------
--	Dado que en la importación, no tienen un Estatus, se actualiza aquí
-- ------------------------------------------------------------------------------
UPDATE `INE`.`Inventario` SET `INE`.`Inventario`.`Estatus` = 'Disponible', `INE`.`Inventario`.`cantidad_fotos` = 0;
UPDATE `INE`.`Inventario` SET `INE`.`Inventario`.`Descripcion` = 'Sin descripción' WHERE `INE`.`Inventario`.`Descripcion` IS NULL;

-- -----------------------------------------------------------------------------
--	Número de digitos en los números de inventario
-- ------------------------------------------------------------------------------
INSERT INTO `INE`.`Admin_Config`(`Admin_Config`.`Ceros_Inventario`, `Admin_Config`.`Ceros_Vales`) 
	VALUES
		(5, 3)
;


-- Return initial state
SET SQL_SAFE_UPDATES 	=	1;
SET FOREIGN_KEY_CHECKS	=	1;