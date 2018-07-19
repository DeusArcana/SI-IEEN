-- Borrado del SP
DROP PROCEDURE IF EXISTS `usp_get_existeProducto`;

-- ====================================================================================
-- Author:      Javier Pazos
-- Description: Revisa si existe un registro en la tabla de inventario
--
-- Parameters:
--   @ID_Producto - Identificador del producto dado por su Folio, Numero y Extensión
-- 
-- Returns:     1	- Si existe un producto con el identificador dado
-- 				2 	- Si NO existe un producto con el identificador dado
-- ====================================================================================
DELIMITER $$
CREATE PROCEDURE `usp_get_existeProducto`(IN `ID_Producto` VARCHAR(255))
BEGIN
	-- Utilización de un IF - SELECT para la realización de la consulta
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