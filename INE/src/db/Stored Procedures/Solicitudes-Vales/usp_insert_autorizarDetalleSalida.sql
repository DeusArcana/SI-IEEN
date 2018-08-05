DROP PROCEDURE IF EXISTS `usp_update_autorizarDetalleSalida`;
DELIMITER $$
CREATE PROCEDURE `usp_update_autorizarDetalleSalida`(IN `ID_Solicitud` VARCHAR(255), IN `Invs_ID_Producto` VARCHAR(255), IN `Cantidad` INT)
BEGIN

    UPDATE `INE`.`Detalle_SolicitudSalida`
		SET `Detalle_SolicitudSalida`.`Cantidad_Autorizada`		= `Cantidad`
			WHERE `Detalle_SolicitudSalida`.`ID_Solicitud`		= `ID_Solicitud`
			AND `Detalle_SolicitudSalida`.`ID_Producto`			= `Invs_ID_Producto`;

END$$
DELIMITER ;
