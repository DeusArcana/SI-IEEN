DROP PROCEDURE IF EXISTS `usp_get_empleadosAsignacion`;
DELIMITER $$
CREATE PROCEDURE `usp_get_empleadosAsignacion`()
BEGIN
	
	SELECT CONCAT(`Empleados`.`Nombres`,' ',`Empleados`.`Apellido_P`,' ',`Empleados`.`Apellido_M`) AS 'Empleado' 
	FROM `INE`.`Empleados`
		WHERE `Empleados`.`ID_Empleado` 
        IN ( SELECT `Empleados`.`ID_Empleado` AS 'Empleado' 
				FROM `INE`.`Empleados`
					WHERE `Empleados`.`ID_Empleado` 
                    IN ( SELECT `Vales`.`ID_Empleado` 
							FROM `INE`.`Vales` 
							INNER JOIN `INE`.`Empleados`  		ON (`Empleados`.`ID_Empleado` 		= `Vales`.`ID_Empleado`) 
							INNER JOIN `INE`.`Detalle_Vale`  	ON (`Detalle_Vale`.`ID_Vale` 		= CONCAT(`Vales`.`Folio`, '-', `Vales`.`Numero`, '-', `Vales`.`Año`)) 
							INNER JOIN `INE`.`Inventario`  		ON (`Detalle_Vale`.`ID_Producto` 	= CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`))
					) 
		)
		GROUP BY CONCAT(`Empleados`.`Nombres`,' ',`Empleados`.`Apellido_P`,' ',`Empleados`.`Apellido_M`);

END$$
DELIMITER ;
