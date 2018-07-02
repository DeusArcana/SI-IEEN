DROP PROCEDURE IF EXISTS `usp_get_existeProducto`;
DELIMITER $$
CREATE PROCEDURE `usp_get_existeProducto`(IN `ID_Producto` VARCHAR(255))
BEGIN

	IF (SELECT 1 = 1 
			FROM `INE`.`Inventario` 
				WHERE CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`) = `ID_Producto`) THEN
		BEGIN
			SELECT 1 AS 'res';
		END;
	ELSE 
		BEGIN
			SELECT 0 AS 'res';
		END;
	END IF;

END$$
DELIMITER ;