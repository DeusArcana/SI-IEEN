DROP PROCEDURE IF EXISTS `usp_update_autorizarSalida`;
DELIMITER $$
CREATE PROCEDURE `usp_update_autorizarSalida`(IN `ID_Solicitud` VARCHAR(255), IN `Usuario` VARCHAR(255))
BEGIN

	UPDATE `INE`.`SolicitudSalida`
		SET	`SolicitudSalida`.`Estado` 				=	'Salida Autorizada',
			`SolicitudSalida`.`User_Autorizo` 		=	`Usuario`,
			`SolicitudSalida`.`Fecha_Respuesta` 	=	NOW()
		WHERE CONCAT(`SolicitudSalida`.`Folio`, '-', `SolicitudSalida`.`Num`, '-', `SolicitudSalida`.`Año`) = `ID_Solicitud`;

END$$
DELIMITER ;
