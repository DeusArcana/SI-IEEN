DROP PROCEDURE IF EXISTS `usp_get_infoInventario`;
DELIMITER $$
CREATE PROCEDURE `usp_get_infoInventario`(IN `Inv_Estatus` VARCHAR(35), IN `Inv_Folio` CHAR(6))
BEGIN

	IF`Inv_Estatus` IS NOT NULL AND `Inv_Folio` IS NULL THEN 
		BEGIN
			SELECT 	CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`),
				`Inventario`.`Nombre_Prod`,
				`Inventario`.`Descripcion`,  
				`Inventario`.`Ubicacion`,
				`Inventario`.`Marca`,
				`Inventario`.`Observaciones`,
				`Inventario`.`No_Serie`,
				`Inventario`.`Modelo`, 
				`Inventario`.`Color`, 	
				`Inventario`.`Fecha_Compra`, 
				`Inventario`.`Factura`, 
				`Inventario`.`Importe`
			FROM `INE`.`Inventario`
				WHERE `Inventario`.`Estatus` = `Inv_Estatus`;
		END;
	ELSE IF `Inv_Estatus` IS NOT NULL AND `Inv_Folio` IS NOT NULL THEN 
		BEGIN
			SELECT 	CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`),
				`Inventario`.`Nombre_Prod`,
				`Inventario`.`Descripcion`,  
				`Inventario`.`Ubicacion`,
				`Inventario`.`Marca`,
				`Inventario`.`Observaciones`,
				`Inventario`.`No_Serie`,
				`Inventario`.`Modelo`, 
				`Inventario`.`Color`, 	
				`Inventario`.`Fecha_Compra`, 
				`Inventario`.`Factura`, 
				`Inventario`.`Importe`
			FROM `INE`.`Inventario`
				WHERE `Inventario`.`Estatus` = `Inv_Estatus` AND `Inventario`.`Folio` = `Inv_Folio`;
		END;
	END IF;
	END IF;

END$$
DELIMITER ;