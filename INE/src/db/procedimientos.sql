USE `INE`;

DROP PROCEDURE IF EXISTS `usp_get_infoProducto`;
DELIMITER $$
CREATE PROCEDURE `usp_get_infoProducto`(IN `ID_Producto` VARCHAR(255))
BEGIN

	SELECT 	`Inventario`.`Folio`, 
			`Inventario`.`Numero`, 
			`Inventario`.`Extension`, 
			`Inventario`.`Nombre_Prod`, 
			`Inventario`.`No_Serie`,
			`Inventario`.`Marca`, 
			`Inventario`.`Modelo`, 
			`Inventario`.`Color`, 	
			`Inventario`.`Descripcion`, 
			`Inventario`.`Factura`, 
			`Inventario`.`Importe`, 
			`Inventario`.`Fecha_Compra`, 
			`Inventario`.`Ubicacion` 
		FROM `INE`.`Inventario`
			WHERE CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`) = `ID_Producto`;
        
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS `usp_get_sugFolio`;
DELIMITER $$
CREATE PROCEDURE `usp_get_sugFolio`()
BEGIN

	SET @sugFolio = (SELECT `Inventario`.`Numero`
						FROM `INE`.`Inventario`
							ORDER BY `Inventario`.`Numero` DESC
							LIMIT 1);

	SET @sugFolio = @sugFolio + 1;
	SELECT @sugFolio;
        
END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS `usp_update_productoInventario`;
DELIMITER $$
CREATE PROCEDURE `usp_update_productoInventario`(IN `Nom_Prod`	VARCHAR(50), IN `Desc`	VARCHAR(500), IN `Ubic` VARCHAR(50), IN `Marc` 	VARCHAR(50),
                                                 IN `No_S`		VARCHAR(45), IN `Modl` 	VARCHAR(100), IN `Colr`	VARCHAR(30), IN `Imgn`	LONGBLOB,
                                                 IN `Fec_Comp`	DATE, 		 IN `Fact` 	VARCHAR(20),  IN `Impor` FLOAT,  	 IN `CLAVE` VARCHAR(20))
BEGIN

	IF (SELECT 1 = 1 
			FROM `Inventario` 
				WHERE CONCAT(`Inventario`.`Folio`,'-', `Inventario`.`Numero`, `Inventario`.`Extension`) = `CLAVE`) THEN
		BEGIN
			UPDATE `INE`.`Inventario` 
				SET `Inventario`.`Nombre_Prod` 	= `Nom_Prod`,
					`Inventario`.`Descripcion` 	= `Desc`,
					`Inventario`.`Ubicacion` 	= `Ubic`,
					`Inventario`.`Marca` 		= `Marc`,
					`Inventario`.`No_Serie` 	= `No_S`,
					`Inventario`.`Modelo` 		= `Modl`,
					`Inventario`.`Color` 		= `Colr`,
					`Inventario`.`Imagen` 		= `Imgn`,
					`Inventario`.`Fecha_Compra` = `Fec_Comp`,
					`Inventario`.`Factura` 		= `Fact`,
					`Inventario`.`Importe` 		= `Impor`
				WHERE CONCAT(`Inventario`.`Folio`,'-', `Inventario`.`Numero`, `Inventario`.`Extension`) = `CLAVE`;
        END;
	END IF;

END$$
DELIMITER ;

