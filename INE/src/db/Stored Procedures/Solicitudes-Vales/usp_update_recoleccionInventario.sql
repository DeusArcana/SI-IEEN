DROP PROCEDURE IF EXISTS `usp_update_recoleccionInventario`;
DELIMITER $$
CREATE PROCEDURE `usp_update_recoleccionInventario`(IN `ID_Vale` VARCHAR(255), IN `ID_Producto` VARCHAR(255), IN `Ubicacion` VARCHAR(255), IN `Observacion` VARCHAR(255))
BEGIN

	IF (SELECT 1 = 1 FROM `INE`.`Detalle_Vale`
			WHERE 	`Detalle_Vale`.`ID_Producto` 	= `ID_Producto`
			AND 	`Detalle_Vale`.`ID_Vale` 		= `ID_Vale`)
		THEN 
				UPDATE `INE`.`Detalle_Vale`
					SET `Detalle_Vale`.`Estado` 			= 'Entregado',
						`Detalle_Vale`.`Fecha_Entrega` 		= NOW()
						WHERE 	`Detalle_Vale`.`ID_Producto` 	= `ID_Producto`
						AND 	`Detalle_Vale`.`ID_Vale` 		= `ID_Vale`;

				UPDATE `INE`.`Inventario`
					SET `Inventario`.`Estatus` 			= 'Disponible', 
						`Inventario`.`Ubicacion`		= `Ubicacion`,
						`Inventario`.`Observaciones` 	= `Observacion`
						WHERE CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`) = `ID_Producto`;
	END IF;

END$$
DELIMITER ;
