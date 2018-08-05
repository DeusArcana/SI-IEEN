DROP PROCEDURE IF EXISTS `usp_update_regresarInventarios`;
DELIMITER $$
CREATE PROCEDURE `usp_update_regresarInventarios`(IN `Invs_ID_Producto` VARCHAR(255), IN `InvG_Cantidad_Stock` INT)
BEGIN
	-- Si el producto se encunetra en Inventario Normal se pasa su estado a disponible
	IF (SELECT 1 = 1
			FROM `Inventario`
				WHERE CONCAT(`Inventario`.`Folio`,'-', `Inventario`.`Numero`, `Inventario`.`Extension`) = `Invs_ID_Producto`) THEN
		BEGIN
			UPDATE `INE`.`Inventario`
				SET `Inventario`.`Estatus` = 'Disponible'
				WHERE CONCAT(`Inventario`.`Folio`,'-', `Inventario`.`Numero`, `Inventario`.`Extension`) = `Invs_ID_Producto`;
		END;
	ELSE -- El producto se encuentra en Inventario a Granel
		BEGIN
			-- Se obtiene el valor actual del Stock de inventario
			SET @Actual_Value = (SELECT `Inventario_Granel`.`Stock`
									FROM `INE`.`Inventario_Granel`
										WHERE CONCAT(`Inventario_Granel`.`Folio`, '-', `Inventario_Granel`.`Numero`, `Inventario_Granel`.`Extension`) = `Invs_ID_Producto`);
			-- Se hace la suma de la Cantidad de Stock con el Valor Actual del Stock
			SET @Result = @Actual_Value + `InvG_Cantidad_Stock`;
			-- Se actualiza el stock con el resultado de la suma y se cambia su estado a disponible
			UPDATE `INE`.`Inventario_Granel`
				SET `Inventario_Granel`.`Stock` 	= 	@Result,
					`Inventario_Granel`.`Estatus` 	= 	'Disponible'
					WHERE CONCAT(`Inventario_Granel`.`Folio`, '-', `Inventario_Granel`.`Numero`, `Inventario_Granel`.`Extension`) = `Invs_ID_Producto`;
        END;
	END IF;

END$$
DELIMITER ;
