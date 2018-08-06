DROP PROCEDURE IF EXISTS `usp_insert_asignacionVale`;
DELIMITER $$
CREATE PROCEDURE `usp_insert_asignacionVale`(IN `Empl_Nombre` VARCHAR(255), IN `Inv_Folio` VARCHAR(127))
BEGIN

	DECLARE Tipo_Folio VARCHAR(30) DEFAULT NULL;

	IF `Inv_Folio`	= 'RES'
		THEN
			SET Tipo_Folio := 'Vale de Resguardo';
	ELSEIF `Inv_Folio` = 'REC'
		THEN
			SET Tipo_Folio := 'Vale de Recolección';
	END IF;

	-- Obtenemos el ID del empleado a través de su nombre para encontrar el usuario
	SET @ID_Empl = (SELECT `Empleados`.`ID_Empleado` AS 'Empleado'
						FROM `INE`.`Empleados`
							WHERE CONCAT(`Empleados`.`Nombres`,' ',`Empleados`.`Apellido_P`,' ',`Empleados`.`Apellido_M`) = `Empl_Nombre`);

	-- Se hace la búsqueda a través de los parámetros
	SET @Numero_Vls = (SELECT `Vales`.`Numero`
						FROM `INE`.`Vales`
							WHERE	`Vales`.`Folio`	= `Inv_Folio`
							AND 	`Vales`.`Año` 	= YEAR(CURDATE())
								ORDER BY `Vales`.`Numero` DESC LIMIT 1);

	-- Si encuentra coincidencias entonces se suma un uno para el siguiente vale
	IF @Numero_Vls IS NOT NULL THEN
		BEGIN
			SET @Numero_Vls = @Numero_Vls + 1;
        END;
	ELSE
		BEGIN
	-- Caso contrario se reinicia el id del Vale
			SET @Numero_Vls = 1;
		END;
	END IF;

	-- Se inserta el registro del vale de asignación
	INSERT INTO `INE`.`Vales` (`Vales`.`Folio`,`Vales`.`Numero`,`Vales`.`Año`,`Vales`.`Tipo_Vale`,`Vale`.`Fecha_Vale`,`Vales`.`ID_Empleado`)
		VALUES
			(`Inv_Folio`, @Numero_Vls, YEAR(CURDATE()), Tipo_Folio, NOW(), @ID_Empl)
		;

    -- Se busca el área del empleado
	SET @Area_Empl = (SELECT `Empleados`.`Area`
						FROM `INE`.`Empleados`
                        INNER JOIN `INE`.`Area` ON `Area`.`ID_Area` = `Empleados`.`ID_Area`
							WHERE `Empleados`.`ID_Empleado` = @ID_Empl);

    -- Se hace la selección a manera de retorno de datos
	SELECT @ID_Empl AS 'ID_Empleado', @Area_Empl AS 'Area_Empleado', CONCAT(`Vales`.`Folio`, '-', `Vales`.`Numero`, '-', `Vales`.`Año`) AS 'ID_Vale'
		FROM `INE`.`Vales`
			WHERE	`Vales`.`Folio` 	= `Inv_Folio`
			AND  	`Vales`.`Numero` 	= @Numero_Vls
            AND 	`Vales`.`Año` 		= YEAR(CURDATE())
;

END$$
DELIMITER ;
