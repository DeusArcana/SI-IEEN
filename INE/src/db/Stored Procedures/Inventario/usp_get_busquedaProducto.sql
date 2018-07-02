DROP PROCEDURE IF EXISTS `usp_get_busquedaProducto`;
DELIMITER $$
CREATE PROCEDURE `usp_get_busquedaProducto`(IN `Inv_Filtro` INT, IN `Inv_Estatus` VARCHAR(35), IN `Inv_Busqueda` VARCHAR(255), IN `Inv_Folio` VARCHAR(255))
BEGIN

	IF (`Inv_Filtro` < 11) THEN
		BEGIN
			IF NOT (`Inv_Estatus` = 'Asignado') THEN
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
									WHERE  
										CASE -- Filtro 1
											WHEN `Inv_Estatus` IS NOT NULL THEN `Inventario`.`Estatus`= `Inv_Estatus`
											WHEN `Inv_Estatus` IS NULL THEN 1
										END
									AND 
										CASE -- Filtro 2
											WHEN `Inv_Folio` IS NOT NULL THEN `Inventario`.`Folio` = `Inv_Folio` 
											WHEN `Inv_Folio` IS NULL THEN 1
										END
									AND
										CASE -- Filtro 3
											WHEN `Inv_Busqueda` IS NULL THEN 1
											WHEN `Inv_Filtro` = 0	THEN CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`) LIKE CONCAT('%', `Inv_Busqueda`, '%')
											WHEN `Inv_Filtro` = 1	THEN `Inventario`.`Nombre_Prod`		LIKE CONCAT('%', `Inv_Busqueda`, '%')
											WHEN `Inv_Filtro` = 2	THEN `Inventario`.`Descripcion`		LIKE CONCAT('%', `Inv_Busqueda`, '%')
											WHEN `Inv_Filtro` = 3	THEN `Inventario`.`Ubicacion`		LIKE CONCAT('%', `Inv_Busqueda`, '%')
											WHEN `Inv_Filtro` = 4	THEN `Inventario`.`Marca`			LIKE CONCAT('%', `Inv_Busqueda`, '%')
											WHEN `Inv_Filtro` = 5	THEN `Inventario`.`Observaciones`	LIKE CONCAT('%', `Inv_Busqueda`, '%')
											WHEN `Inv_Filtro` = 6	THEN `Inventario`.`No_Serie`		LIKE CONCAT('%', `Inv_Busqueda`, '%')
											WHEN `Inv_Filtro` = 7	THEN `Inventario`.`Modelo`			LIKE CONCAT('%', `Inv_Busqueda`, '%')
											WHEN `Inv_Filtro` = 8	THEN `Inventario`.`Color`			LIKE CONCAT('%', `Inv_Busqueda`, '%')
											WHEN `Inv_Filtro` = 9	THEN `Inventario`.`Fecha_Compra`	LIKE CONCAT('%', `Inv_Busqueda`, '%')
											WHEN `Inv_Filtro` = 10	THEN `Inventario`.`Factura`			LIKE CONCAT('%', `Inv_Busqueda`, '%')
										END;
				END;
			ELSE 
				BEGIN
					SELECT	CONCAT( `Inventario`.`Folio`, '-', 	`Inventario`.`Numero`, `Inventario`.`Extension`) AS 'Clave',
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
									`Inventario`.`Importe`,
									CONCAT(	`Empleados`.`Nombres`, ' ', `Empleados`.`Apellido_P`, ' ', `Empleados`.`Apellido_M`) AS 'Responsable'  
										FROM `INE`.`Vales`
											INNER JOIN `INE`.`Detalle_Vale`		ON (`Detalle_Vale`.`ID_Vale`		= CONCAT(`Vales`.`Folio`, '-', `Vales`.`Numero`, '-', `Vales`.`Año`))
											INNER JOIN `INE`.`Inventario`		ON (`Detalle_Vale`.`ID_Producto`	= CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`))
											INNER JOIN `INE`.`Empleados`		ON (`Empleados`.`ID_Empleado`		= `Vales`.`ID_Empleado`)
											WHERE  
													CASE -- Filtro 1
														WHEN `Inv_Estatus` IS NOT NULL THEN `Inventario`.`Estatus`= `Inv_Estatus`
														WHEN `Inv_Estatus` IS NULL THEN 1
													END
												AND 
													CASE -- Filtro 2
														WHEN `Inv_Folio` IS NOT NULL THEN `Inventario`.`Folio` = `Inv_Folio` 
														WHEN `Inv_Folio` IS NULL THEN 1
													END
												AND
													CASE -- Filtro 3
														WHEN `Inv_Busqueda` IS NULL THEN 1
														WHEN `Inv_Filtro` = 0	THEN CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`) LIKE CONCAT('%', `Inv_Busqueda`, '%')
														WHEN `Inv_Filtro` = 1	THEN `Inventario`.`Nombre_Prod`		LIKE CONCAT('%', `Inv_Busqueda`, '%')
														WHEN `Inv_Filtro` = 2	THEN `Inventario`.`Descripcion`		LIKE CONCAT('%', `Inv_Busqueda`, '%')
														WHEN `Inv_Filtro` = 3	THEN `Inventario`.`Ubicacion`		LIKE CONCAT('%', `Inv_Busqueda`, '%')
														WHEN `Inv_Filtro` = 4	THEN `Inventario`.`Marca`			LIKE CONCAT('%', `Inv_Busqueda`, '%')
														WHEN `Inv_Filtro` = 5	THEN `Inventario`.`Observaciones`	LIKE CONCAT('%', `Inv_Busqueda`, '%')
														WHEN `Inv_Filtro` = 6	THEN `Inventario`.`No_Serie`		LIKE CONCAT('%', `Inv_Busqueda`, '%')
														WHEN `Inv_Filtro` = 7	THEN `Inventario`.`Modelo`			LIKE CONCAT('%', `Inv_Busqueda`, '%')
														WHEN `Inv_Filtro` = 8	THEN `Inventario`.`Color`			LIKE CONCAT('%', `Inv_Busqueda`, '%')
														WHEN `Inv_Filtro` = 9	THEN `Inventario`.`Fecha_Compra`	LIKE CONCAT('%', `Inv_Busqueda`, '%')
														WHEN `Inv_Filtro` = 10	THEN `Inventario`.`Factura`			LIKE CONCAT('%', `Inv_Busqueda`, '%')
													END;
				END;
			END IF;
		END;
	ELSE 
		BEGIN 
			SELECT	CONCAT( `Inventario`.`Folio`, '-', 	`Inventario`.`Numero`, `Inventario`.`Extension`) AS 'Clave',
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
				`Inventario`.`Importe`,
				CONCAT(	`Empleados`.`Nombres`, ' ', `Empleados`.`Apellido_P`, ' ', `Empleados`.`Apellido_M`) AS 'Responsable'  
					FROM `INE`.`Vales`
						INNER JOIN `INE`.`Detalle_Vale`		ON (`Detalle_Vale`.`ID_Vale`		= CONCAT(`Vales`.`Folio`, '-', `Vales`.`Numero`, '-', `Vales`.`Año`))
						INNER JOIN `INE`.`Inventario`		ON (`Detalle_Vale`.`ID_Producto`	= CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`))
						INNER JOIN `INE`.`Empleados`		ON (`Empleados`.`ID_Empleado`		= `Vales`.`ID_Empleado`)
							WHERE `Inventario`.`Estatus`= `Inv_Estatus`
							AND 
								CASE
									 WHEN `Inv_Busqueda` IS NULL THEN 1
                                     WHEN `Inv_Busqueda` IS NOT NULL THEN CONCAT(`Empleados`.`Nombres`,' ',`Empleados`.`Apellido_P`,' ',`Empleados`.`Apellido_M`)	LIKE CONCAT('%', `Inv_Busqueda`, '%')
								END
                            AND
								CASE
									WHEN `Inv_Folio` IS NULL THEN 1
									WHEN `Inv_Folio` IS NOT NULL THEN CONCAT( `Inventario`.`Folio`, '-', 	`Inventario`.`Numero`, `Inventario`.`Extension`)	LIKE CONCAT('%', `Inv_Folio`, '%')
								END;
		END;
	END IF;

END$$
DELIMITER ;