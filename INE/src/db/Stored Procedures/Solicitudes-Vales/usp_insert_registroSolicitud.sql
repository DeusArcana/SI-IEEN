DROP PROCEDURE IF EXISTS `usp_insert_registroSolicitud`;
DELIMITER $$
CREATE PROCEDURE `usp_insert_registroSolicitud`
	( IN `ID_Vale` INT, IN `ID_Producto` VARCHAR(255),  IN `Tipo_Solicitud` VARCHAR(255),  IN `Usuario` VARCHAR(255), IN `Motivo` VARCHAR(255), IN `Cantidad` INT)
BEGIN

	DECLARE EXIT HANDLER FOR SQLEXCEPTION, SQLWARNING
		BEGIN
			ROLLBACK;
			SELECT -1 AS 'res';
		END;
	
    START TRANSACTION;
		SET @CURRENT_DATETIME := NOW();
    
		INSERT INTO `INE`.`Solicitudes` (`Solicitudes`.`Tipo_Solicitud`, `Solicitudes`.`ID_User`, `Solicitudes`.`Motivo`, `Solicitudes`.`Cantidad`, `Solicitudes`.`Fecha_Solicitud`, `Solicitudes`.`Estado`) 
			VALUES
				(`Tipo_Solicitud`, `Usuario`, `Motivo`, `Cantidad`, @CURRENT_DATETIME, 'SOLICITUD PERSONAL')
			;
		
        UPDATE `INE`.`Detalle_Vale`
			SET `Detalle_Vale`.`Estado` = 'SOLICITUD' 
				WHERE	`Detalle_Vale`.`ID_Producto`	= `ID_Producto`
                AND 	`Detalle_Vale`.`ID_Vale`		= `ID_Vale`;
                
		SET @ID_SOLICITUD = (SELECT `Solicitudes`.`ID_Solicitud` FROM `INE`.`Solicitudes` WHERE `Solicitudes`.`Fecha_Solicitud` = @CURRENT_DATETIME);
        
        INSERT INTO `INE`.`Detalle_Solicitud`
			VALUES
				(@ID_SOLICITUD, `ID_Producto`)
			;
		
    COMMIT;
	
    SELECT 1 AS 'res';
    
END$$
DELIMITER ;
