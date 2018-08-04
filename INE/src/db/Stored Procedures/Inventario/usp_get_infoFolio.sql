DROP PROCEDURE IF EXISTS `usp_get_infoFolio`;
DELIMITER $$
CREATE PROCEDURE `usp_get_infoFolio`()
BEGIN

	SELECT `Folio`.`ID_Folio`, `Folio`.`Descripcion`
		FROM `INE`.`Folio`
			WHERE `Folio`.`ID_Folio` != 'EY-10' and `Folio`.`ID_Folio` != 'EY-99';

END$$
DELIMITER ;