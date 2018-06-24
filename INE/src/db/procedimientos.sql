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
CREATE PROCEDURE `usp_get_sugFolio`(IN `Inv_Folio` VARCHAR(10))
BEGIN

	SET @sugFolio = (SELECT `Inventario`.`Numero`
						FROM `INE`.`Inventario`
							WHERE `Inventario`.`Folio` = `Inv_Folio`
							ORDER BY `Inventario`.`Numero` DESC
							LIMIT 1);
	
    IF @sugFolio IS NULL THEN 
		SET @sugFolio = 0; 
	END IF;
    
	SET @sugFolio = @sugFolio + 1;
	SELECT @sugFolio AS 'Sugerencia_Folio';

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


DROP PROCEDURE IF EXISTS `usp_get_infoInventario`;
DELIMITER $$
CREATE PROCEDURE `usp_get_infoInventario`(IN `Inv_Estatus` VARCHAR(35), IN `Inv_Folio` CHAR(6))
BEGIN

	IF`Inv_Estatus` IS NOT NULL AND `Inv_Folio` IS NULL THEN 
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
				WHERE `Inventario`.`Estatus` = `Inv_Estatus`;
		END;
	ELSE IF `Inv_Estatus` IS NOT NULL AND `Inv_Folio` IS NOT NULL THEN 
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
				WHERE `Inventario`.`Estatus` = `Inv_Estatus` AND `Inventario`.`Folio` = `Inv_Folio`;
        END;
	END IF;
    END IF;

END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `usp_get_existeProducto`;
DELIMITER $$
CREATE PROCEDURE `usp_get_existeProducto`(IN `ID_Producto` VARCHAR(10))
BEGIN

	IF (SELECT 1 = 1 
			FROM `INE`.`Inventario` 
				WHERE CONCAT(`Inventario`.`Folio`, '-', `Inventario`.`Numero`, `Inventario`.`Extension`) = `ID_Producto`) THEN
		BEGIN
			SELECT 1 AS 'res';        
        END;
	ELSE 
		BEGIN
			SELECT 0 AS 'res';
        END;
	END IF;

END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS `usp_get_infoFolio`;
DELIMITER $$
CREATE PROCEDURE `usp_get_infoFolio`()
BEGIN

	SELECT `Folio`.`ID_Folio`, `Folio`.`Descripcion`
		FROM `INE`.`Folio`
			WHERE 1;

END$$
DELIMITER ;