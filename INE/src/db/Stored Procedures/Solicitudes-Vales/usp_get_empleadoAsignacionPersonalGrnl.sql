DROP PROCEDURE IF EXISTS `usp_get_empleadoAsignacionPersonalGrnl`;
DELIMITER $$
CREATE PROCEDURE `usp_get_empleadoAsignacionPersonalGrnl`(IN `Usuario` VARCHAR(255))
BEGIN

	SELECT	`Vales`.`ID_Vale`,
			`Detalle_Vale`.`ID_Producto`,
            `Inventario_Granel`.`Nombre_Prod`,
            `Inventario_Granel`.`Descripcion`,
            `Inventario_Granel`.`Observaciones`,
            `Detalle_Vale`.`Cantidad` 
		FROM `Vales`
			INNER JOIN `Detalle_Vale`		ON (`Detalle_Vale`.`ID_Vale`		= `Vales`.`ID_Vale`)
			INNER JOIN `Inventario_Granel`	ON (`Detalle_Vale`.`ID_Producto`	= CONCAT(`Inventario_Granel`.`Folio`, '-', `Inventario_Granel`.`Numero`, `Inventario_Granel`.`Extension`))
			INNER JOIN `User`				ON (`User`.`ID_User`				= `Vales`.`ID_User`) 
				WHERE `User`.`ID_User` = `Usuario`;

END$$
DELIMITER ;
