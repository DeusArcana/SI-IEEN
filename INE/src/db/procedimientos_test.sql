 USE `INE`;
 
/*
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
SET SQL_SAFE_UPDATES = 0;
-- CALL `ine`.`sp_update_statusProducto`('ASIGNADO', 'CMP0011012');

-- CALL `ine`.`sp_get_equiposComputo`("DISPONIBLE");


DELETE FROM inicio WHERE `Usuario` = 'root';
INSERT INTO inicio values ("root","localhost","1234","ACTIVO");
UPDATE inicio 
	SET `password` = 'deuspass' 
		WHERE `Usuario` = 'root';
*/

DELETE FROM inicio WHERE `Usuario` = 'root';
select * from User;
SELECT * FROM inicio;