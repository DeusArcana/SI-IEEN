DROP PROCEDURE IF EXISTS `usp_update_registroSolicitud`;
DELIMITER $$
CREATE PROCEDURE `usp_update_registroSolicitud`
	( IN `ID_Solicitud` INT, IN `Empleado` VARCHAR(255))
BEGIN

	DECLARE EXIT HANDLER FOR SQLEXCEPTION, SQLWARNING
		BEGIN
			ROLLBACK;
			SELECT -1 AS 'res';
		END;
	
    START TRANSACTION;
		
        UPDATE `INE`.`Solicitudes` 
			SET `Solicitudes`.`Estado` = 'PENDIENTE PERSONAL' 
				WHERE `Solicitudes`.`ID_Solicitud` = `ID_Solicitud`;
		
        SET @ID_PRODUCTO = (SELECT `Detalle_Solicitud`.`ID_Producto` 
								FROM `INE`.`Detalle_Solicitud` 
									WHERE `Detalle_Solicitud`.`ID_Solicitud` = `ID_Solicitud`);
        
        SET @ID_VALE = (SELECT `Vales`.`ID_Vale` 
							FROM `INE`.`Vales` 
								INNER JOIN `INE`.`Detalle_Vale`		ON (`Detalle_Vale`.`ID_Vale` 		= `Vales`.`ID_Vale`)
								INNER JOIN `INE`.`Inventario`		ON (`Detalle_Vale`.`ID_Producto`	= `Inventario`.`ID_Producto`)
								INNER JOIN `INE`.`User`				ON (`User`.`ID_User`				= `Vales`.`ID_User`)
								INNER JOIN `INE`.`Empleados`		ON (`Empleados`.`ID_Empleado`		= `User`.`ID_Empleado`)
									WHERE CONCAT(`Empleados`.`Nombre`, ' ', `Empleados`.`Apellido_P`, ' ', `Empleados`.`Apellido_M`) = `Empleado`
                                    AND `Detalle_Vale`.`Estado` 	= 'SOLICITUD' 
                                    AND `Inventario`.`ID_Producto`	= `ID_Solicitud`);
		
        UPDATE `INE`.`Detalle_Vale` 
			SET `Detalle_Vale`.`Estado` = 'PENDIENTE' 
				WHERE `Detalle_Vale`.`ID_Producto`	= @ID_PRODUCTO
                AND `Detalle_Vale`.`ID_Vale`		= @ID_VALE;
    
    COMMIT;
	
    SELECT 1 AS 'res';
    
END$$
DELIMITER ;
