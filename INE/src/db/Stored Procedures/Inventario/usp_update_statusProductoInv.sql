DROP PROCEDURE IF EXISTS `usp_update_statusProductoInv`;
DELIMITER $$
CREATE PROCEDURE `usp_update_statusProductoInv`(IN `ID_Producto` VARCHAR(255), IN `Inv_Estatus` VARCHAR(127))
BEGIN

		IF (SELECT 1 = 1 
			FROM `Inventario` 
				WHERE CONCAT(`Inventario`.`Folio`,'-', `Inventario`.`Numero`, `Inventario`.`Extension`) = `ID_Producto`) THEN
		BEGIN
			UPDATE `INE`.`Inventario` 
				SET `Inventario`.`Estatus` 	= `Inv_Estatus`
				WHERE CONCAT(`Inventario`.`Folio`,'-', `Inventario`.`Numero`, `Inventario`.`Extension`) = `ID_Producto`;
		END;
	END IF;

END$$
DELIMITER ;