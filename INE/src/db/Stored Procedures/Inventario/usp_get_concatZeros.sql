-- Borrado del SP
DROP PROCEDURE IF EXISTS `usp_get_concatZeros`;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_get_concatZeros`()
BEGIN
	
    SET @Ceros_Inventario	:= 0;
    SET @Ceros_Vales 		:= 0;
    
	IF EXISTS	(SELECT `Admin_Config`.`Ceros_Inventario` FROM `INE`.`Admin_Config` WHERE 1) 
		AND 	(SELECT `Admin_Config`.`Ceros_Inventario` FROM `INE`.`Admin_Config` WHERE 1) IS NOT NULL
		THEN 
			
		SET @Ceros_Inventario :=	(SELECT `Admin_Config`.`Ceros_Inventario` AS 'res' 
										FROM `INE`.`Admin_Config`
											WHERE 1 LIMIT 1);
	END IF;
    
	IF EXISTS	(SELECT `Admin_Config`.`Ceros_Vales` FROM `INE`.`Admin_Config` WHERE 1) 
		AND 	(SELECT `Admin_Config`.`Ceros_Vales` FROM `INE`.`Admin_Config` WHERE 1) IS NOT NULL
		THEN 
			
		SET @Ceros_Vales :=	(SELECT `Admin_Config`.`Ceros_Vales` AS 'res' 
								FROM `INE`.`Admin_Config`
									WHERE 1 LIMIT 1);
	END IF;

	SELECT @Ceros_Inventario, @Ceros_Vales;

END$$
DELIMITER ;
