DROP PROCEDURE IF EXISTS `usp_get_inventarioParaAsignacion`;
DELIMITER $$
CREATE PROCEDURE `usp_get_inventarioParaAsignacion`(IN `Inv_Estatus` VARCHAR(35), IN `Inv_Folio` VARCHAR(255))
BEGIN

	SELECT 	CONCAT(	`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`) AS 'ID_Producto',
					`Inventario`.`Nombre_Prod`,
					`Inventario`.`Descripcion`,
					`Inventario`.`Ubicacion`,
					`Inventario`.`Marca`,
					`Inventario`.`No_Serie`,
					`Inventario`.`Modelo`
		FROM `INE`.`Inventario` 
			WHERE
				CASE -- Filtro 1
					WHEN `Inv_Estatus` IS NOT NULL THEN `Inventario`.`Estatus`= `Inv_Estatus`
					WHEN `Inv_Estatus` IS NULL THEN 1
				END
					AND 
				CASE -- Filtro 2
					WHEN `Inv_Folio` IS NOT NULL THEN `Inventario`.`Folio` = `Inv_Folio` 
					WHEN `Inv_Folio` IS NULL THEN 1
				END;

END$$
DELIMITER ;