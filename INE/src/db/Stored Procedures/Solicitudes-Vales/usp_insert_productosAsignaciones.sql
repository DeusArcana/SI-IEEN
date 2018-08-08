DROP PROCEDURE IF EXISTS `usp_insert_productosAsignaciones`;
DELIMITER $$
CREATE PROCEDURE `usp_insert_productosAsignaciones`(IN `Nombre_Empleado` VARCHAR(255), IN `Estado_Vale` VARCHAR(255))
BEGIN

	SELECT 	CONCAT(`Vales`.`Folio` ,'-', `Vales`.`Numero`, '-', `Vales`.`Año`), 
			`Detalle_Vale`.`ID_Producto`, 
            `Inventario`.`Nombre_Prod`,
            `Inventario`.`Descripcion`,
            `Inventario`.`Marca`,
            `Inventario`.`NO_Serie`,
            `Inventario`.`Modelo`,
            `Inventario`.`Color`,
            `Inventario`.`Observaciones`,
            `Inventario`.`Ubicacion` 
		FROM `Vales` 
			INNER JOIN 	`Detalle_Vale`	ON (`Detalle_Vale`.`ID_Vale`		= CONCAT(`Vales`.`Folio`, '-', `Vales`.`Numero`, '-', `Vales`.`Año`)) 
			INNER JOIN	`Inventario`	ON (`Detalle_Vale`.`ID_Producto`	= CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`)) 
			INNER JOIN	`Empleados`		ON (`Empleados`.`ID_Empleado`		= `Vales`.`ID_Empleado`) 
				WHERE CONCAT(`Empleados`.`Nombre`, ' ', `Empleados`.`Àpellido_P`, ' ', `Empleados`.`Apellido_M`)	= `Nombre_Empleado`
				AND `Detalle_Vale`.`Estado` = `Estado_Vale`
					ORDER BY CONCAT(`Vales`.`Folio`, '-', `Vales`.`Numero`, '-', `Vales`.`Año`);

END$$
DELIMITER ;
