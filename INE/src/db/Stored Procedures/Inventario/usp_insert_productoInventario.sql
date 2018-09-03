CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_insert_productoInventario`(IN `Foli` 		CHAR(5),		IN `Numr` 	INT, 			IN `Extn`	CHAR(1), 	 IN `Nom_Prod`	VARCHAR(50),	
												 IN `Desc`		VARCHAR(500),	IN `Ubic` 	VARCHAR(50), 	IN `Marc` 	VARCHAR(50), IN `Obsrv`		VARCHAR(300),
												 IN `No_S`		VARCHAR(45),	IN `Modl` 	VARCHAR(100),	IN `Colr`	VARCHAR(30), IN `Fec_Comp`	DATE,
												 IN `Fact` 		VARCHAR(20), 	IN `Impor` 	FLOAT, 			IN `Imgn`	INT)
BEGIN

	INSERT INTO `INE`.`Inventario` 	(`Inventario`.`Folio`,			`Inventario`.`Numero`,		`Inventario`.`Extension`,	`Inventario`.`Nombre_Prod`,	
									 `Inventario`.`Descripcion`, 	`Inventario`.`Ubicacion`,	`Inventario`.`Marca`, 		`Inventario`.`Observaciones`,
									 `Inventario`.`No_Serie`, 		`Inventario`.`Modelo`, 		`Inventario`.`Color`,		`Inventario`.`Fecha_Compra`,
									 `Inventario`.`Factura`,		`Inventario`.`Importe`, 	`Inventario`.`cantidad_fotos`) 
		VALUES
			(`Nom_Prod`, `Desc`, `Ubic`, `Marc`, `No_S`, `Modl`, `Colr`, `Imgn`, `Fec_Comp`, `Fact`, `Impor`)
		;

END