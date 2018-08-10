DROP PROCEDURE IF EXISTS `usp_get_inventarioStockMin`;
DELIMITER $$
CREATE PROCEDURE `usp_get_inventarioStockMin`()
BEGIN

	SELECT CONCAT(	`Inventario_Granel`.`Folio`, '-', `Inventario_Granel`.`Numero`, `Inventario_Granel`.`Extension`),
					`Inventario_Granel`.`Nombre_Prod`,
                    `Inventario_Granel`.`Descripcion`,
                    `Inventario_Granel`.`Observaciones`,
                    `Inventario_Granel`.`Stock`,
                    `Inventario_Granel`.`Estatus` 
		FROM `INE`.`Inventario_Granel` 
			WHERE `Inventario_Granel`.`Stock_Min` >= `Inventario_Granel`.`Stock`;

END$$
DELIMITER ;
