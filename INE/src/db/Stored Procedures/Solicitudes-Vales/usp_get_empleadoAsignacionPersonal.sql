DROP PROCEDURE IF EXISTS `usp_get_empleadoAsignacionPersonal`;
DELIMITER $$
CREATE PROCEDURE `usp_get_empleadoAsignacionPersonal`(IN `Usuario` VARCHAR(255), IN `Estado_Vale` VARCHAR(255))
BEGIN

	SELECT	`Detalle_Vale`.`ID_Producto`, 
			`Inventario`.`Nombre_Prod`,
            `Inventario`.`Descripcion`,
            `Inventario`.`Marca`,
            `Inventario`.`NO_Serie`,
            `Inventario`.`Modelo`,
            `Inventario`.`Observaciones` 
		FROM `INE`.`Vales`
			INNER JOIN `INE`.`Detalle_Vale`		ON (`Detalle_Vale`.`ID_Vale`		= CONCAT(`Vales`.`Folio`, '-', `Vales`.`Numero`, '-', `Vales`.`Año`))
			INNER JOIN `INE`.`Inventario`		ON (`Detalle_Vale`.`ID_Producto`	= CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`))
			INNER JOIN `INE`.`Empleados`		ON (`Empleados`.`ID_Empleado`		= `Vales`.`ID_Empleado`)
			INNER JOIN `INE`.`User`				ON (`User`.`ID_Empleado`			= `Empleados`.`ID_Empleado`)
				WHERE `User`.`ID_User`			= `Usuario` 
                AND `Detalle_Vale`.`Estado`		= `Estado_Vale` 
					ORDER BY `Inventario`.`Numero`;

END$$
DELIMITER ;
