DROP PROCEDURE IF EXISTS `usp_get_infoInventarioGranel`;
DELIMITER $$
CREATE PROCEDURE `usp_get_infoInventarioGranel`(IN `InvG_Estatus` VARCHAR(35), IN `InvG_Folio` CHAR(6))
BEGIN

	SELECT 	CONCAT(	`Inventario_Granel`.`Folio`, '-', `Inventario_Granel`.`Numero`, `Inventario_Granel`.`Extension`),
					`Inventario_Granel`.`Nombre_Prod`,
					`Inventario_Granel`.`Descripcion`,
					`Inventario_Granel`.`Almacen`,
					`Inventario_Granel`.`Marca`,
					`Inventario_Granel`.`Observaciones`,
					`Inventario_Granel`.`Stock`
		FROM `INE`.`Inventario_Granel`
			WHERE
				CASE -- Filtro por Status
					WHEN `InvG_Estatus` IS NOT NULL THEN `Inventario_Granel`.`Estatus`= `InvG_Estatus`
					WHEN `InvG_Estatus` IS NULL THEN 1
				END
            AND        
                CASE -- Filtro por Status
					WHEN `InvG_Folio` IS NOT NULL THEN `Inventario_Granel`.`Folio`= `InvG_Folio`
					WHEN `InvG_Folio` IS NULL THEN 1
				END;
    
END$$
DELIMITER ;