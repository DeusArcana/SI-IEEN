DROP PROCEDURE IF EXISTS `usp_update_stockInvGranel`;
DELIMITER $$
CREATE PROCEDURE `usp_update_stockInvGranel`(IN `InvG_ID_Producto` VARCHAR(255), IN `InvG_Cantidad` INT)
BEGIN
	-- Se obtiene el valor actual del Stock
	SET @Actual_Value = (SELECT `Inventario_Granel`.`Stock`
							FROM `INE`.`Inventario_Granel`
								WHERE CONCAT(`Inventario_Granel`.`Folio`, '-', `Inventario_Granel`.`Numero`, `Inventario_Granel`.`Extension`) = `InvG_ID_Producto`);

    -- Si el producto existe y el valor del Stock es mayor o igual a la cantidad se realizan los UPDATEs correspondientes
	IF (SELECT 1 = 1
			FROM `INE`.`Inventario_Granel`
				WHERE CONCAT(`Inventario_Granel`.`Folio`, '-', `Inventario_Granel`.`Numero`, `Inventario_Granel`.`Extension`) = `InvG_ID_Producto`)
		AND
			@Actual_Value  >= `InvG_Cantidad`
        THEN
		BEGIN
			-- Se obtiene el resultado de la resta
			SET @Result = @Actual_Value - `InvG_Cantidad`;

            -- Se realiza el UPDATE con este resultado
			UPDATE `INE`.`Inventario_Granel`
				SET `Inventario_Granel`.`Stock` = @Result
					WHERE CONCAT(`Inventario_Granel`.`Folio`, '-', `Inventario_Granel`.`Numero`, `Inventario_Granel`.`Extension`) = `InvG_ID_Producto`
					AND `Inventario_Granel`.`Stock` >= `InvG_Cantidad`;

            -- Si el ressultado es 0 se cambia el Estatus a 'Agotado'
            IF @Result = 0
				THEN
					UPDATE `INE`.`Inventario_Granel`
						SET `Inventario_Granel`.`Estatus` = 'Agotado'
							WHERE CONCAT(`Inventario_Granel`.`Folio`, '-', `Inventario_Granel`.`Numero`, `Inventario_Granel`.`Extension`) = `InvG_ID_Producto`;
				END IF;

            -- Se retorna el Stock actual después de hechas las operaciones
            CALL `ine`.`usp_get_stockInvGranel`(`InvG_ID_Producto`);

		END;
	ELSE
		BEGIN
			-- Se retorna - 1 en caso contrario
			SELECT - 1 AS 'res';
		END;
	END IF;

END$$
DELIMITER ;
