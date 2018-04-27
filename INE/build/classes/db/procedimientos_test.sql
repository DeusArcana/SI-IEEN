 USE `INE`;
 
 -- Permite usar los DELETE FROM para vaciar las tablas
SET SQL_SAFE_UPDATES = 0;
-- Permite insertar los datos sin importar si es parent_table
SET FOREIGN_KEY_CHECKS=1;
 
/*
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- CALL `ine`.`sp_update_statusProducto`('ASIGNADO', 'CMP0011012');

-- CALL `ine`.`sp_get_equiposComputo`("DISPONIBLE");


DELETE FROM inicio WHERE `Usuario` = 'root';
INSERT INTO inicio values ("root","localhost","1234","ACTIVO");
UPDATE inicio 
	SET `password` = 'deuspass' 
		WHERE `Usuario` = 'root';
*/

SELECT * FROM Folio;

SELECT * FROM INVENTARIO 
INNER JOIN Folio ON Folio.ID_Folio = Inventario.Folio
WHERE Folio.ID_Folio = 'EY-02'
 LIMIT 20;




