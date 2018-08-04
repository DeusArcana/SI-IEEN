DROP PROCEDURE IF EXISTS `usp_get_stockInvGranel`;
DELIMITER $$
CREATE PROCEDURE `usp_get_stockInvGranel`(IN `ID_Producto` VARCHAR(255))
BEGIN

	SELECT `Inventario_Granel`.`Stock`
		FROM `INE`.`Inventario_Granel`
			WHERE CONCAT(`Inventario_Granel`.`Folio`, '-', `Inventario_Granel`.`Numero`, `Inventario_Granel`.`Extension`) = `ID_Producto`;

END$$
DELIMITER ;
