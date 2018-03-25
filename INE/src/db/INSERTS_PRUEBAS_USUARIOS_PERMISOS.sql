USE `INE`;
-- Permite usar los DELETE FROM para vaciar las tablas
SET SQL_SAFE_UPDATES = 0;
-- Permite insertar los datos sin importar si es parent_table
SET FOREIGN_KEY_CHECKS=0;
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

DELETE FROM `INE`.`Area` WHERE 1;
INSERT INTO `INE`.`Area` 
	VALUES 
		('Presidencia'),
		('Secretaría General'),
        ('Dirección Administrativa'),
        ('Organo Interno de Control'),
		('Unidad de Transparencia'),
        ('Dirección Jurídica'),
        ('Direccion de Organización y Capacitación Electoral'),
		('Unidad Técnica de Informática y Estadística'),
        ('Comunicación Social'),
        ('Oficialía de Partes'),
		('Oficialía Electoral'),
        ('Consejos Municipales')
	;
    
-- Vacia la tabla
DELETE FROM `INE`.`Empleados` WHERE 1;
-- Repara el contador
ALTER TABLE `INE`.`Empleados` AUTO_INCREMENT = 1;
INSERT INTO `INE`.`Empleados`(`nombres`, `apellido_p`, `apellido_m`, `calle`, `colonia`, `telefono`, `codigo_postal`, `fecha_nacimiento`,
									`curp`, `rfc`, `municipio`, `localidad`,`area`) 
	VALUES 
		('Kevin Alejandro','Méndez','Santana','Udine #18','Fracc. Bonaterra','311-162-16-71','63194','1995-09-26','MESK950926HNTNNV08','123456QWEASD','Tepic','Tepic','Dirección Administrativa')
	;
/*
INSERT INTO `INE`.`Empleados` (`nombres`, `apellido_p`, `apellido_m`, `calle`, `colonia`, `telefono`, `codigo_postal`, `fecha_nacimiento`,
									`curp`, `rfc`, `municipio`, `localidad`) 
	VALUES 
		('Larry', 'Tirado', 'Oquendo', 'C/ Cuevas de Ambrosio, 19', 'Fraccionamiento Las Rosas', '743 744 008', '63170', '1995-11-6', 'MESK950926HNTNNV08', '123456QWEASD', 'Tepic', 'Tepic'),
        ('Endike', 'Leiva', 'Martínez', '24193 Villaquilambre','Los Arcos', '648 589 384', '63170','1996-8-9', 'MESK950926HNTNNV08',  '123456QWEASD','Tepic','Tepic'),
        ('Ingmar', 'Pedraza', 'Quiñones', 'Bellavista, 47', 'Jardines del Valle', '778 280 380', '63170', '1997-7-6', 'MESK950926HNTNNV08', '123456QWEASD', 'Tepic', 'Tepic'),
        ('Alex', 'Lima', 'Ferreira', 'C/ Amoladera, 69', 'La Taberna', '730 470 846', '63170', '1997-9-11', 'MESK950926HNTNNV08', '123456QWEASD', 'Tepic','Tepic'),
        ('Emilly', 'Goncalves', 'Almeida', 'Pza. Fuensanta, 30', 'Los Robles', '730 470 846', '63170', '1993-6-6', 'MESK950926HNTNNV08', '123456QWEASD', 'Tepic', 'Tepic'),
        ('Julieta', 'Carvalho', 'Castro', 'Prolongacion San Sebastian, 45', 'Las Brisas', '713 743 463', '63170', '1995-11-18', 'MESK950926HNTNNV08', '123456QWEASD', 'Tepic','Tepic'),
        ('Fernanda', 'Alves', 'Correia', 'La Fontanilla, 23', 'Los Alces', '737 144 758', '63170', '1996-4-20', 'MESK950926HNTNNV08', '123456QWEASD', 'Tepic', 'Tepic'),
        ('Carla', 'Araujo', 'Barbosa', 'Avendaño, 25', 'Moctezuma', '646 329 790', '63170', '1996-1-2', 'MESK950926HNTNNV08', '123456QWEASD', 'Tepic', 'Tepic')
	;*/


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
INSERT INTO `INE`.`Inventario_Granel` (`id_productogranel`, `nombre_prod`, `descripcion`, `almacen`, `estatus`, `marca`, `observaciones`, `stock_min`, `stock`, `tipo_uso`)
	VALUES
		('GMP00000001', 'Archivador', 'Archivador  Carta Marmoleado Verde', 'Almacen 1', 'Disponible', 'OFIX', '',10,11,'Papeleria'),
        ('GMP00000002', 'Archivador', 'Archivador Carta Amarillo', 'Almacen 1', 'Disponible', 'SMART','',10,21,'Papeleria'),
        ('GMP00000003', 'Archivador', 'Archivador Carta Azul', 'Almacen 1', 'Disponible', 'LEFORT','',10,13,'Papeleria' ),
        ('GMP00000004', 'Sobre', 'Sobre con Burbuja', 'Almacen 1', 'Disponible', 'FORTEC', '18.4X30.48CM',10,15,'Papeleria'),
        ('GMP00000005', 'Caja para Archivo', 'Caja Archivo Carta Plástica', 'Almacen 1', 'Disponible', 'OFIX', ' Medidas 38.5 x 31 x 25cm (largo-ancho-alto).',20,30,'Papeleria'),
        ('GMP00000006', 'Caja para Archivo', 'Caja Archivo Carta Plastica Tapa Negra', 'Almacen 1', 'Disponible', 'OFIX', 'Medidas 38.5 x 31 x 25cm (largo-ancho-alto).',20,30,'Papeleria'),
        ('GMP00000007', 'Hojas', 'Hojas para Carpeta 100H C-5 Clasico', 'Almacen 1', 'Disponible', 'SCRIBE', 'Tamaño carta. Cuadro No. 5.', 100,350,'Papeleria'),
        ('GMP00000008', 'Boligrafo Azul', 'Boligrafo Mediano Stick Azul', 'Almacen 1', 'Disponible', 'OFIX', 'Caja con 12 piezas.', 30, 45,'Papeleria'),
        ('GMP00000009', 'Boligrafo Rojo', 'Boligrafo Mediano Stick Rojo', 'Almacen 1', 'Disponible', 'Bic', 'Caja con 12 piezas.', 30, 45,'Papeleria'),
        ('GMP00000010', 'Lapiz', 'Lapiz de Grafito', 'Almacen 1', 'Disponible', 'Bic', '2B Hexagonal Evolution', 40, 55,'Papeleria'),
        ('GMP00000011', 'Lapiz', 'Lapiz de Grafito con Goma', 'Almacen 1', 'Disponible', 'Mirado', '#2 HEXAG MIRADO CJA CON 12', 50, 65,'Papeleria'),
        ('GMP00000012', 'Goma', 'Goma para Borrar de Migajón', 'Almacen 1', 'Disponible', 'Pelikan', 'BOL/3 STRIKE 20 Bolsa con 3 piezas', 25, 30,'Papeleria'),
        ('GMP00000013', 'Sujeta Documentos', 'SUJETADOR METAL NEGRO 25MM', 'Almacen 1', 'Disponible', 'SMART', 'Agarrapapel 25MM', 15, 20,'Papeleria')
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
        ('Solicitud Reemplazo')
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
        ('Solicitud Reemplazo',	'Secretaria', 			false)
	;
    
    DELETE FROM `INE`.`TipoVale` WHERE 1;
INSERT INTO `INE`.`TipoVale` 
	VALUES
		('Vale de asignación'),
		('Vale de recolección'),
        ('Vale de reemplazo')
    ;
DELETE FROM `INE`.`Permiso_Vale` WHERE 1;
INSERT INTO `INE`.`Permiso_Vale` 
	VALUES
		('Vale de asignación',		'Auxiliar',				false),
		('Vale de asignación',		'Administración',		true),
		('Vale de asignación',		'Jefe de departamento',	true),
        ('Vale de asignación',		'Usuario Depto.',		false),
        ('Vale de asignación',		'Organización', 		true),
        ('Vale de asignación',		'Presidencia', 			true),
        ('Vale de asignación',		'SuperUsuario', 		true),
        ('Vale de asignación',		'Secretaria', 			false),
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
	('EY-10', 'Equipo de Transporte')
;

INSERT INTO `Bodegas`(`Bodegas`.`Nom_Bodega`) VALUES
	('Bodega UTIE'),
	('Bodega DOyCE'),
	('Bodega Admon')
;


DELETE FROM `INE`.`User` WHERE 1;    
INSERT INTO `INE`.`User`  (`id_user`, `id_empleado`, `documentacion`, `password`, `puesto`, `estatus`)
	VALUES
		('Mendez26', 1, true, '123', 'SuperUsuario', 'ACTIVO');/*,
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
        
