DROP PROCEDURE IF EXISTS `usp_get_infoFolio`;
DELIMITER $$
CREATE PROCEDURE `usp_get_infoFolio`()
BEGIN

	SELECT `Folio`.`ID_Folio`, `Folio`.`Descripcion`
		FROM `INE`.`Folio`
			WHERE 1;

END$$
DELIMITER ;