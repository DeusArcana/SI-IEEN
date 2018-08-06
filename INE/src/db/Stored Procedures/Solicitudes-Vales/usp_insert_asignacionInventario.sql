DROP PROCEDURE IF EXISTS `usp_insert_asignacionInventario`;
DELIMITER $$
CREATE PROCEDURE `usp_insert_asignacionInventario`(IN `ID_Vale` VARCHAR(127), IN `ID_Producto` VARCHAR(255), IN `Cantidad` INT, IN `Ubicación` VARCHAR(255))
BEGIN

	INSERT INTO `INE`.`Detalle_Vale` (`Detalle_Vale`.`ID_Vale`, `Detalle_Vale`.`ID_Producto`, `Detalle_Vale`.`ID_Producto`, `Detalle_Vale`.`Estado`)
		VALUES
			(`ID_Vale`, `ID_Producto`, `Cantidad`, 'Asignado')
		;
	
    UPDATE `INE`.`Inventario` 
    	SET `Inventario`.`Ubicacion` = `Ubicación`
    		WHERE CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extensión`) = `ID_Producto`;
    
END$$
DELIMITER ;
