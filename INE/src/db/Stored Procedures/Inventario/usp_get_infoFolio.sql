-- Borrado del SP
DROP PROCEDURE IF EXISTS `usp_get_infoFolio`;
-- ====================================================================
-- Author:      Javier Pazos
-- Description: Obtiene los registros contenidos en la tabla `Folio`
--
-- Returns:     ID de los Folios y su Descripción
-- ====================================================================
DELIMITER $$
CREATE PROCEDURE `usp_get_infoFolio`()
BEGIN
	-- Consulta SELECT
	SELECT `Folio`.`ID_Folio`, `Folio`.`Descripcion`
		FROM `INE`.`Folio`
			WHERE 1;

END$$
DELIMITER ;