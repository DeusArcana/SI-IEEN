-- Borrado del SP
DROP PROCEDURE IF EXISTS `usp_get_busquedaProducto`;

-- ====================================================================
-- Author:      Javier Pazos
-- Description: Se encarga de los principales filtrados de la tabla de 
--
--
-- Parameters:
--   @Inv_Filtro 	- Número que designa la columna por la cual se ha de hacer la búsqueda
--   @Inv_Estatus 	- Status del producto, se filtra si este producto está asignado o no
--	 @Inv_Busqueda 	- Parámetro de búsqueda que se relaciona directamente con @Inv_Filtro
-- 	 @Inv_Folio		- Nomenclatura del Folio relacionado con el producto
--
-- Returns:     Consulta con los filtros designados por los parámetros
-- ====================================================================
DELIMITER $$
CREATE PROCEDURE `usp_get_busquedaProducto`(IN `Inv_Filtro` INT, IN `Inv_Estatus` VARCHAR(35), IN `Inv_Busqueda` VARCHAR(255), IN `Inv_Folio` VARCHAR(255))
BEGIN
	-- Si @Inv_Filtro z 11 significa que no se hace una búsqueda por responsable	
	IF (`Inv_Filtro` < 11) THEN
		BEGIN
			-- Si no se envía un parámetro de estatus o este no contiene la palabra asignado, quiere decir que se buscan productos que se encuentran en
			-- sin responsable.
			IF (`Inv_Estatus` IS NULL) OR NOT (`Inv_Estatus` = 'Asignado') THEN
				BEGIN
					-- Campos a seleccionar
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
										CASE -- Filtro por Status
											WHEN `Inv_Estatus` IS NOT NULL THEN `Inventario`.`Estatus`= `Inv_Estatus`
											WHEN `Inv_Estatus` IS NULL THEN 1
										END
									AND
										CASE -- Filtro por campos de la tabla
											-- Si no hay parámetro de búsqueda se retorna la tabla completa
											WHEN `Inv_Busqueda` IS NULL THEN 1
											--	Si no hay parámetro de Folio (es decir, que NO se ha seleccionado un folio en la opción del ComboBox), se hace la búsqueda
											-- solamente por el parámetro de busqueda
											WHEN `Inv_Filtro` = 0	AND `Inv_Folio` IS NULL 
												THEN CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`) LIKE CONCAT('%', `Inv_Busqueda`, '%')
											-- Si hay un parámetro de Folio (es decir, que se ha seleccionado un folio en la opción del ComboBox), se hace la búsqueda
											-- por parámetro dé Folio concatenado al parámetro de búsqueda, de esta manera no hay que escribir el Folio de nuevamente
											WHEN `Inv_Filtro` = 0	AND `Inv_Folio` IS NOT NULL 
												THEN CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`) LIKE CONCAT('%', `Inv_Folio`, '-', `Inv_Busqueda`, '%')
											-- Búsqueda por el resto de los campos de la tabla
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
					-- Campos a seleccionar
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
											-- Relacion que existen entre los productos asignados y los responsables
											INNER JOIN `INE`.`Detalle_Vale`		ON (`Detalle_Vale`.`ID_Vale`		= CONCAT(`Vales`.`Folio`, '-', `Vales`.`Numero`, '-', `Vales`.`Año`))
											INNER JOIN `INE`.`Inventario`		ON (`Detalle_Vale`.`ID_Producto`	= CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`))
											INNER JOIN `INE`.`Empleados`		ON (`Empleados`.`ID_Empleado`		= `Vales`.`ID_Empleado`)
											WHERE  
													CASE -- Filtro por Status
														WHEN `Inv_Estatus` IS NOT NULL THEN `Inventario`.`Estatus`= `Inv_Estatus`
														WHEN `Inv_Estatus` IS NULL THEN 1
													END
												AND
													CASE -- Filtro por campos de la tabla
														-- Si no hay parámetro de búsqueda se retorna la tabla completa
														WHEN `Inv_Busqueda` IS NULL THEN 1
														--	Si no hay parámetro de Folio (es decir, que NO se ha seleccionado un folio en la opción del ComboBox), se hace la búsqueda
														-- solamente por el parámetro de busqueda
														WHEN `Inv_Filtro` = 0	AND `Inv_Folio` IS NULL 
															THEN CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`) LIKE CONCAT('%', `Inv_Busqueda`, '%')
														-- Si hay un parámetro de Folio (es decir, que se ha seleccionado un folio en la opción del ComboBox), se hace la búsqueda
														-- por parámetro dé Folio concatenado al parámetro de búsqueda, de esta manera no hay que escribir el Folio de nuevamente
														WHEN `Inv_Filtro` = 0	AND `Inv_Folio` IS NOT NULL 
															THEN CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`) LIKE CONCAT('%', `Inv_Folio`, '-', `Inv_Busqueda`, '%')
														-- Búsqueda por el resto de los campos de la tabla
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
		-- Si @Inv_Folio = 11 quiere decir que se hace la búsqueda por responsable
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
						-- Relacion que existen entre los productos asignados y los responsables
						INNER JOIN `INE`.`Detalle_Vale`		ON (`Detalle_Vale`.`ID_Vale`		= CONCAT(`Vales`.`Folio`, '-', `Vales`.`Numero`, '-', `Vales`.`Año`))
						INNER JOIN `INE`.`Inventario`		ON (`Detalle_Vale`.`ID_Producto`	= CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`))
						INNER JOIN `INE`.`Empleados`		ON (`Empleados`.`ID_Empleado`		= `Vales`.`ID_Empleado`)
							WHERE `Inventario`.`Estatus`= `Inv_Estatus`
							AND 
								CASE	-- Filtro por búsqueda de responsable
									 WHEN `Inv_Busqueda` IS NULL THEN 1
                                     WHEN `Inv_Busqueda` IS NOT NULL THEN CONCAT(`Empleados`.`Nombres`,' ',`Empleados`.`Apellido_P`,' ',`Empleados`.`Apellido_M`)	LIKE CONCAT('%', `Inv_Busqueda`, '%')
								END
                            AND
								CASE	-- Filtro por búsqueda de Folio
									WHEN `Inv_Folio` IS NULL THEN 1
									WHEN `Inv_Folio` IS NOT NULL THEN CONCAT( `Inventario`.`Folio`, '-', 	`Inventario`.`Numero`, `Inventario`.`Extension`)	LIKE CONCAT('%', `Inv_Folio`, '%')
								END;
		END;
	END IF;

END$$
DELIMITER ;