-- Borrado del SP
DROP PROCEDURE IF EXISTS `usp_get_concatZeros`;
-- ====================================================================
-- Author:      Javier Pazos
-- Description: Obtiene el número de dígitos de se deben contener en la 
--				presentación de los ID de producto, este número está 
-- 				previamente guardado en la base de datos.
--
-- Returns:     El número contenido en el campo `INT_String_Format` con
--				la etiqueta 'res'.
--				Cero en caso contrario
-- ====================================================================
DELIMITER $$
CREATE PROCEDURE `usp_get_concatZeros`()
BEGIN
	-- Comprueba que existe dicho valor
	IF EXISTS	(SELECT `INT_String_Format` FROM `INE`.`Admin_Config` WHERE 1) 
		AND 	(SELECT `INT_String_Format` FROM `INE`.`Admin_Config` WHERE 1) IS NOT NULL
		THEN 
			-- Selecciona el campo de la tabla
			SELECT `INT_String_Format` AS 'res' 
				FROM `INE`.`Admin_Config`
					WHERE 1 LIMIT 1;
	ELSE 
		-- Se retorna 0 como respuesta
		SELECT 0 AS 'res';
	END IF;

END$$
DELIMITER ;