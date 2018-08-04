DROP PROCEDURE IF EXISTS `usp_get_stockInvGranel`;
DELIMITER $$
CREATE PROCEDURE `usp_get_stockInvGranel`(IN `ID_Producto` VARCHAR(255))
BEGIN

	IF (SELECT 1 = 1 
			FROM `INE`.`Inventario_Granel` 
				WHERE CONCAT(`Inventario_Granel`.`Folio`, '-', `Inventario_Granel`.`Numero`, `Inventario_Granel`.`Extension`) = `ID_Producto`) THEN
		BEGIN
			SELECT `Inventario_Granel`.`Stock` AS 'res'
				FROM `INE`.`Inventario_Granel`
					WHERE CONCAT(`Inventario_Granel`.`Folio`, '-', `Inventario_Granel`.`Numero`, `Inventario_Granel`.`Extension`) = `ID_Producto`;
		END;
	ELSE 
		BEGIN
			SELECT - 1 AS 'res';
		END;
	END IF;

END$$
DELIMITER ;
