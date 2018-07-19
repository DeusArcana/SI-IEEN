-- Borrado del SP
DROP PROCEDURE IF EXISTS `usp_get_concatZeros`;

DELIMITER $$
CREATE PROCEDURE `usp_get_concatZeros`()
BEGIN

	IF EXISTS (SELECT 1 = 1 FROM `INE`.`Admin_Config` WHERE 1)
		THEN 
			SELECT `INT_String_Format` AS 'res' FROM `INE`.`Admin_Config`;
	ELSE 
		SELECT 0 AS 'res';
	END IF;

END$$
DELIMITER ;