DROP PROCEDURE IF EXISTS `usp_get_sugFolio`;
DELIMITER $$
CREATE PROCEDURE `usp_get_sugFolio`(IN `Inv_Folio` VARCHAR(10))
BEGIN

	SET @sugFolio = (SELECT `Inventario`.`Numero`
						FROM `INE`.`Inventario`
							WHERE `Inventario`.`Folio` = `Inv_Folio`
							ORDER BY `Inventario`.`Numero` DESC
							LIMIT 1);
	
	IF @sugFolio IS NULL THEN 
		SET @sugFolio = 0; 
	END IF;

	SET @sugFolio = @sugFolio + 1;
	SELECT @sugFolio AS 'Sugerencia_Folio';

END$$
DELIMITER ;