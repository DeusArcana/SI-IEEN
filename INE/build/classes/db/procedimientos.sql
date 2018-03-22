USE `INE`;

-- ====================================================================
-- Author:      Javier Pazos
-- Create date: 15 / 03 / 2018
-- Description: Obtiene los datos de los empleados que cuentan con un usuario asignado
--
-- Returns:     Nombre, Apellidos, Puesto, Area, Municipio y Localidad del empleado
-- ====================================================================
DROP PROCEDURE IF EXISTS `sp_get_userData`;
DELIMITER $$
CREATE PROCEDURE `sp_get_userData`()
BEGIN
	SELECT `Empleados`.`Nombres`, `Empleados`.`apellido_p`, `Empleados`.`apellido_m`,`User`.`puesto`, `User`.`area`,`Empleados`.`municipio`, `Empleados`.`localidad`
		FROM `User`
		INNER JOIN `Empleados`	ON `User`.`id_empleado`	= `Empleados`.`id_empleado`
		INNER JOIN `Puestos`	ON `User`.`puesto`		= `Puestos`.`puesto`
		INNER JOIN `Area`		ON `User`.`area`		= `Area`.`area`
		WHERE 1;
END$$
DELIMITER ;

-- ====================================================================
-- Author:      Javier Pazos
-- Create date: 16 / 03 / 2018
-- Description: Obtiene los datos de un empleado al que se le ha asignado un usuario
--
-- Parameters:
--   @Value - ID del usuario en formato User
-- Returns:     Nombres, Apellidos, Puesto, Area, Municipio y Localidad
-- ====================================================================
DROP PROCEDURE IF EXISTS `sp_find_userDataByID`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_find_userDataByID`(IN `Value` VARCHAR(10))
BEGIN   
	SELECT `Empleados`.`Nombres`, `Empleados`.`apellido_p`, `Empleados`.`apellido_m`,`User`.`puesto`, `User`.`area`,`Empleados`.`municipio`, `Empleados`.`localidad`
		FROM `User`
		INNER JOIN `Empleados`	ON `User`.`id_empleado`	= `Empleados`.`id_empleado`
		INNER JOIN `Puestos`	ON `User`.`puesto`		= `Puestos`.`puesto`
		INNER JOIN `Area`		ON `User`.`area`		= `Area`.`area`
		WHERE `User`.`id_user` = `Value`;
END$$
DELIMITER ;


-- ====================================================================
-- Author:      Javier Pazos
-- Create date: 16 / 03 / 2018
-- Description: Obtiene los objetos que han sido asignados a un empleado con usuario
--
-- Returns:     Productos asignados ordenados por ID_Producto
-- ====================================================================
DROP PROCEDURE IF EXISTS `sp_get_userObjetos`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_userObjetos`()
BEGIN
	SELECT `Inventario`.`ID_Producto`, `Inventario`.`nombre_prod`, `Inventario`.`estatus`, `Vales`.`id_vale`, `Vales`.`tipo_vale`, 
			CONCAT(`Empleados`.`nombres`, ' ' , `Empleados`.`apellido_p`, ' ' , `Empleados`.`apellido_m`) AS 'Nombre'
		FROM `INE`.`Detalle_Vale`
		INNER JOIN `INE`.`inventario`	ON `Detalle_Vale`.`ID_Producto`	= `Inventario`.`ID_Producto`
		INNER JOIN `INE`.`Vales`		ON `Detalle_Vale`.`id_vale`		= `Vales`.`id_vale`
		INNER JOIN `INE`.`User`			ON `Vales`.`id_user`			= `User`.`id_user`
		INNER JOIN `INE`.`Empleados`	ON `User`.`id_empleado`			= `Empleados`.`id_empleado`
		WHERE 1;
END$$
DELIMITER ;

-- ====================================================================
-- Author:      Javier Pazos
-- Create date: 16 / 03 / 2018
-- Description: Obtiene los objetos que han sido asignados a un empleado con usuario dado un status
--
-- Parameters:
--   @Value - Estatus del producto
-- Returns:     Productos asignados ordenados por ID_Producto dado un status
-- ====================================================================
DROP PROCEDURE IF EXISTS `sp_get_userObjetosAsignados`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_userObjetosAsignados`(IN `Value` VARCHAR(15))
BEGIN
	SELECT `Inventario`.`ID_Producto`, `Inventario`.`nombre_prod`, `Inventario`.`estatus`, `Vales`.`id_vale`, `Vales`.`tipo_vale`, 
			CONCAT(`Empleados`.`nombres`, ' ' , `Empleados`.`apellido_p`, ' ' , `Empleados`.`apellido_m`) AS 'Nombre'
		FROM `INE`.`Detalle_Vale`
		INNER JOIN `INE`.`inventario`	ON `Detalle_Vale`.`ID_Producto`	= `Inventario`.`ID_Producto`
		INNER JOIN `INE`.`Vales`		ON `Detalle_Vale`.`id_vale`		= `Vales`.`id_vale`
		INNER JOIN `INE`.`User`			ON `Vales`.`id_user`			= `User`.`id_user`
		INNER JOIN `INE`.`Empleados`	ON `User`.`id_empleado`			= `Empleados`.`id_empleado`
		WHERE `Inventario`.`estatus` = `Value`;
END$$
DELIMITER ;

-- ====================================================================
-- Author:      Javier Pazos
-- Create date: 16 / 03 / 2018
-- Description: Obtiene los objetos a granel asignados a un empleado con usuario
--
-- Returns:     Datos sobre inventario granel junto con informacion del vale
-- ====================================================================
DROP PROCEDURE IF EXISTS `sp_get_userObjetosGranel`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_userObjetosGranel`()
BEGIN
	SELECT `Inventario_Granel`.`id_productoGranel`, `Inventario_Granel`.`nombre_prod`, `Inventario_Granel`.`estatus`,
			`Vales`.`id_vale`, `Vales`.`tipo_vale`, `vales`.`estado`, `Empleados`.`nombres`, `Empleados`.`apellido_p`, `Empleados`.`apellido_m`
		FROM `ÌNE`.`Detalle_Valegranel`
		INNER JOIN `INE`.`Inventario_Granel`	ON `Detalle_Valegranel`.`id_productoGranel` = `Inventario_Granel`.`id_productoGranel`
		INNER JOIN `INE`.`Vales`				ON `Detalle_Vale`.`id_vale`					= `Vales`.`id_vale`
		INNER JOIN `INE`.`User`					ON `Vales`.`id_user`						= `User`.`id_user`
		INNER JOIN `INE`.`Empleados`			ON `User`.`id_empleado`						= `Empleados`.`id_empleado`
		WHERE 1;
END$$
DELIMITER ;

-- ====================================================================
-- Author:      Javier Pazos
-- Create date: 16 / 03 / 2018
-- Description: Actualiza el estatus de un producto por medio de su ID
--
-- Parameters:
--   @Value - Nuevo estatus del producto
--   @ID 	- Identificador del producto
-- Returns:     
-- 	1 - Verdadero
--  2 - Falso
-- ====================================================================
DROP PROCEDURE IF EXISTS `sp_update_statusProducto`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update_statusProducto`(IN `Value` VARCHAR(15), IN `ID` VARCHAR(30))
BEGIN
	DECLARE `Done` BOOL DEFAULT FALSE;

	IF	EXISTS (SELECT `ID_Producto` FROM `INE`.`Inventario` WHERE `Inventario`.`Id_Producto` = `ID` )
	THEN 
		UPDATE `INE`.`Inventario` 
			SET `Inventario`.`estatus` = `Value` 
				WHERE `Inventario`.`Id_Producto` = `ID`;
		SET @Done = TRUE;
		SELECT @Done;
    ELSE 
        SELECT @Done;
    END IF;
    
END$$
DELIMITER ;

-- ====================================================================
-- Author:      Javier Pazos
-- Create date: 16 / 03 / 2018
-- Description: Actualiza el estatus de un producto a granel por medio de su ID
--
-- Parameters:
--   @Value - Nuevo estatus del producto a granel
--   @ID 	- Identificador del producto
-- Returns:     
-- 	1 - Verdadero
--  2 - Falso
-- ====================================================================
DROP PROCEDURE IF EXISTS `sp_update_statusGranel`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update_statusGranel`(IN `Value` VARCHAR(15), IN `ID` VARCHAR(30))
BEGIN
	DECLARE `Done` BOOL DEFAULT FALSE;
	
	IF	EXISTS (SELECT `ID_Producto` FROM `INE`.`Inventario_Granel` WHERE `Inventario`.`Id_Producto` = `ID` )
	THEN
		UPDATE `INE`.`Inventario_Granel`
			SET `ÌNE`.`Inventario_Granel`.`Estatus` = `Value`
				WHERE `INE`.`Inventario_Granel`.`ÌD_productoGranel` = `ID`;
		SET @Done = TRUE;
		SELECT @Done;
	ELSE 
		SELECT @Done;
	END IF;

END$$
DELIMITER ;

-- ====================================================================
-- Author:      Javier Pazos
-- Create date: 16 / 03 / 2018
-- Description: Actualiza el stock de un producto a granel por medio de su ID
--
-- Parameters:
--   @Value - Nuevo stock del producto a granel
--   @ID   	- Identificador del producto
-- Returns:
-- 	1 - Verdadero
--  2 - Falso
-- ====================================================================
DROP PROCEDURE IF EXISTS `sp_update_stockGranel`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update_stockGranel`(IN `Value` VARCHAR(15), IN `ID` VARCHAR(30))
BEGIN
	DECLARE `Done` BOOL DEFAULT FALSE;
	
	IF	EXISTS (SELECT `ID_Producto` FROM `INE`.`Inventario_Granel` WHERE `Inventario`.`Id_Producto` = `ID` )
	THEN
		UPDATE `INE`.`Inventario_Granel`
			SET `INE`.`Inventario_Granel`.`Stock` = `Value`
				WHERE `INE`.`Inventario_Granel`.`ID_productoGranel` = `ID`;
		SET @Done = TRUE;
		SELECT @Done;
	ELSE
		SELECT @Done;
	END IF;
    
END$$
DELIMITER ;

-- ====================================================================
-- Author:      Javier Pazos
-- Create date: 16 / 03 / 2018
-- Description: Obtiene los equipos de computo dado un estatus
--
-- Parameters:
--   @Query - Estatus de los productos a consultar
-- Returns:     
-- 		Inventario -> ID del producto
-- 		Inventario -> Nombre del producto
-- 		Inventario -> Numero de serie
-- 		Inventario -> Modelo del producto
-- ====================================================================
DROP PROCEDURE IF EXISTS `sp_get_equiposComputo`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_equiposComputo`(IN `Query` VARCHAR(15))
BEGIN

	SELECT `Inventario`.`ID_Producto`, `Inventario`.`Nombre_Prod`, `Inventario`.`NO_Serie`, `Inventario`.`Modelo` 
		FROM `INE`.`Inventario`
			WHERE (`Inventario`.`Nombre_Prod` = 'CPU' 
						OR `Inventario`.`Nombre_Prod` = 'Monitor' 
							OR `Inventario`.`Nombre_Prod` = 'Teclado')
			AND `Inventario`.`Estatus` = `Query`
			ORDER BY `Inventario`.`Nombre_Prod`;

END$$
DELIMITER ;


/*
DROP PROCEDURE IF EXISTS `sp_insert_nuevoVale`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_nuevoVale`(IN `Value` VARCHAR(15), IN `ID` VARCHAR(30))
BEGIN
	INSERT INTO `INE`.`Vales` (`Vales`.`ID_Vale`, `Vales`.`Estado`, `Vales`.`User_Autorizo`, `Vales`.`Fecha_Vale`, `Vales`.`Tipo_Vale`, `Vales`.`ID_User`) 
		VALUES
			(?, ?, ?, ?, ?, ?)
		;

END$$
DELIMITER ;
*/

DROP PROCEDURE IF EXISTS `sp_get_conjuntosEquipo`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_conjuntosEquipo`()
BEGIN

	SELECT `Detalles_Equipo_Computo`.`ID_Equipo` AS 'Equipo', 
			`Inv_CPU`.`ID_Producto`  			 AS 'CPU', 
			`Inv_TEC`.`ID_Producto`				 AS 'Teclado', 
			`Inv_MON`.`ID_Producto`				 AS 'Monitor', 
			`Eq_COMP`.`Ubicacion` 				 AS 'Ubicacion'
		FROM `Detalles_Equipo_Computo`
			INNER JOIN `Inventario` 	`Inv_CPU`	ON (`Inv_CPU`.`ID_Producto` 	= `Detalles_Equipo_Computo`.`ID_CPU`)
			INNER JOIN `Inventario` 	`Inv_TEC`	ON (`Inv_TEC`.`ID_Producto` 	= `Detalles_Equipo_Computo`.`ID_Teclado`)
			INNER JOIN `Inventario` 	`Inv_MON` 	ON (`Inv_MON`.`ID_Producto` 	= `Detalles_Equipo_Computo`.`ID_Monitor`)
			INNER JOIN `Equipo_Computo` `Eq_COMP` 	ON (`Eq_COMP`.`ID_Equipo` 		= `Detalles_Equipo_Computo`.`ID_Equipo`);

END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `sp_get_conjuntosEquipoReemplazo`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_conjuntosEquipoReemplazo`(IN `Query` VARCHAR(25))
BEGIN

	SELECT `Detalles_Equipo_Computo`.`ID_Equipo` AS 'Equipo', 
			`Inv_CPU`.`ID_Producto`  			 AS 'CPU', 
			`Inv_TEC`.`ID_Producto`				 AS 'Teclado', 
			`Inv_MON`.`ID_Producto`				 AS 'Monitor', 
			`Eq_COMP`.`Ubicacion` 				 AS 'Ubicacion'
		FROM `Detalles_Equipo_Computo`
			INNER JOIN `Inventario` 	`Inv_CPU`	ON (`Inv_CPU`.`ID_Producto` 	= `Detalles_Equipo_Computo`.`ID_CPU`)
			INNER JOIN `Inventario` 	`Inv_TEC`	ON (`Inv_TEC`.`ID_Producto` 	= `Detalles_Equipo_Computo`.`ID_Teclado`)
			INNER JOIN `Inventario` 	`Inv_MON` 	ON (`Inv_MON`.`ID_Producto` 	= `Detalles_Equipo_Computo`.`ID_Monitor`)
			INNER JOIN `Equipo_Computo` `Eq_COMP` 	ON (`Eq_COMP`.`ID_Equipo` 		= `Detalles_Equipo_Computo`.`ID_Equipo`)
				WHERE  `Inv_CPU`.`Estatus` = `Query` 
					OR `Inv_TEC`.`Estatus` = `Query` 
					OR `Inv_MON`.`Estatus` = `Query`;

END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `sp_get_detallesEquipo`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_detallesEquipo`(IN `Query` VARCHAR(25))
BEGIN

	SELECT `Inventario`.`ID_Producto`, `Inventario`.`Nombre_Prod`, `Inventario`.`NO_Serie`, `Inventario`.`Modelo` 
		FROM `INE`.`Inventario`
			WHERE `ID_Producto` = `Query`;

END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `sp_update_asignarEquipo`;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update_asignarEquipo`(IN `Query` VARCHAR(25))
BEGIN
	
	UPDATE `INE`.`Inventario` 
		SET `Inventario`.`Estatus` = 'ASIGNADO' 
			WHERE `Inventario`.`ID_Producto` = `Query`;

END$$
DELIMITER ;