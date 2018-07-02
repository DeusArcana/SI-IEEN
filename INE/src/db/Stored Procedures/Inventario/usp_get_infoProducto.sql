DROP PROCEDURE IF EXISTS `usp_get_infoProducto`;
DELIMITER $$
CREATE PROCEDURE `usp_get_infoProducto`(IN `ID_Producto` VARCHAR(255))
BEGIN

	SELECT 	`Inventario`.`Folio`, 
			`Inventario`.`Numero`, 
			`Inventario`.`Extension`, 
			`Inventario`.`Nombre_Prod`, 
			`Inventario`.`No_Serie`,
			`Inventario`.`Marca`, 
			`Inventario`.`Modelo`, 
			`Inventario`.`Color`, 	
			`Inventario`.`Descripcion`, 
			`Inventario`.`Factura`, 
			`Inventario`.`Importe`, 
			`Inventario`.`Fecha_Compra`, 
			`Inventario`.`Ubicacion` 
		FROM `INE`.`Inventario`
			WHERE CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`) = `ID_Producto`;

END$$
DELIMITER ;