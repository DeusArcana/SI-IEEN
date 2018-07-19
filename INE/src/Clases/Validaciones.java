package Clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Validaciones {
	private final Conexion db;
	private final int FORMATO_DIGITOS;
	
	/**
	 * <h1>Validaciones</h1>
	 * 
	 * <p>Al mandar llamar al constructor, se extrae de la base de datos el 
	 * número de dígitos que se desea que contenga el formato de ID del producto
	 * en el Inventario</p>
	 */
	public Validaciones() {
		int temp = 0;
		db = new Conexion();
		
		try (PreparedStatement ps = db.getConexion().prepareCall("CALL `ine`.`usp_get_concatZeros`()")) {	
			ResultSet rs = ps.executeQuery();
			if(rs.next()) temp = rs.getInt("res");
		} catch (SQLException ex) {
			Logger.getLogger(Validaciones.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		this.FORMATO_DIGITOS = temp;
	}
	
	
	
    /**
    * <h1>validateAlphanumeric</h1>
    * 
    * Verifica si la cadena es alfanúmerica, según la longitud dada
    * 
    * @param txt Cadena a validar
    * @param lenght Longitod de la cadena
    * @return TRUE si la cadena es alfanumérica, FALSE caso contrario
    */
    public static boolean validateAlphanumeric(String txt, int lenght){
        return Pattern.compile("([A-Z0-9]){" + lenght + "}").matcher(txt).matches();
    }
    
    /**
    * <h1>validateAlphanumeric</h1>
    * 
    * Verifica si la cadena es alfanumerica, según el rango dado
    * por MIN y MAX
    * 
    * @param txt Cadena a validar
    * @param MIN Rango mínimo de la cadena
    * @param MAX Rángo máximo de la cadena
    * @return TRUE si la cadena es alfanumérica, FALSE caso contrario
    */
    public static boolean validateAlphanumeric(String txt, int MIN, int MAX){
        return Pattern.compile("([A-Za-z0-9]){" + MIN + "," + MAX + "}").matcher(txt).matches();
    }

    /**
    * <h1>validateInteger</h1>
    * 
    * Verifica si la cadena es numérica
    * 
    * @param txt Cadena a validar
    * @param lenght Longitud de la cadena
    * @return TRUE si la cadena es numérica, FALSE caso contrario
    */
    public static boolean validateInteger(String txt, int lenght){
        return Pattern.compile("([0-9]){" + lenght + "}").matcher(txt).matches();
    }
    
    /**
    * <h1>validateInteger</h1>
    * 
    * Verifica si la cadena es numérica, según el rango dado
    * por MIN y MAX
    * 
    * @param txt Cadena a validar
    * @param MIN Rango mínimo de la cadena
    * @param MAX Rángo máximo de la cadena
    * @return TRUE si la cadena es alfanumérica, FALSE caso contrario
    */
    public static boolean validateInteger(String txt, int MIN, int MAX){
        return Pattern.compile("([0-9]){" + MIN + "," + MAX + "}").matcher(txt).matches();
    }
    
    /**
    * <h1>validateAlphabetical</h1>
    * 
    * Verifica si la cadena contiene solo letras, 
    * según la longitud dada
    * 
    * @param txt Cadena a validar
    * @param lenght Longitud de la cadena
    * @return TRUE si la cadena solo contiene letras del alfabeto, FALSE caso contrario
    */
    public static boolean validateAlphabetical(String txt, int lenght){
        return Pattern.compile("([A-Za-z]){" + lenght + "}").matcher(txt).matches();
    }

    /**
    * <h1>validateAlphabetical</h1>
    * 
    * Verifica si la cadena contiene solo letras, 
    * según el rango dado por MIN y MAX
    * 
    * @param txt Cadena a validar
    * @param MIN Rango mínimo de la cadena
    * @param MAX Rángo máximo de la cadena
    * @return TRUE si la cadena solo contiene letras del alfabeto, FALSE caso contrario
    */
    public static boolean validateAlphabetical(String txt, int MIN, int MAX){
        return Pattern.compile("([A-Za-z]){" + MIN + "," + MAX + "}").matcher(txt).matches();
    }
 
    /**
    * <h1>validateMayus</h1>
    * 
    * Verifica si la cadena contiene solo letras mayúsculas, 
    * según la longitud dada
    * 
    * @param txt Cadena a validar
    * @param lenght Longitud de la cadena
    * @return TRUE si la cadena solo contiene letras mayúsculas, FALSE caso contrario
    */
    public static boolean validateMayus(String txt, int lenght){
        return Pattern.compile("([A-Z]){" + lenght + "}").matcher(txt).matches();
    }

    /**
    * <h1>validateMayus</h1>
    * 
    * Verifica si la cadena contiene solo letras mayúsculas, 
    * según el rango dado por MIN y MAX
    * 
    * @param txt Cadena a validar
    * @param MIN Rango mínimo de la cadena
    * @param MAX Rángo máximo de la cadena
    * @return TRUE si la cadena solo contiene letras mayúsculas, FALSE caso contrario
    */
    public static boolean validateMayus(String txt, int MIN, int MAX){
        return Pattern.compile("([A-Z]){" + MIN + "," + MAX + "}").matcher(txt).matches();
    }
    
    /**
    * <h1>validateMinus</h1>
    * 
    * Verifica si la cadena contiene solo letras minúsculas, 
    * según la longitud dada
    * 
    * @param txt Cadena a validar
    * @param lenght Longitud de la cadena
    * @return TRUE si la cadena solo contiene letras minúsculas, FALSE caso contrario
    */
    public static boolean validateMinus(String txt, int lenght){
        return Pattern.compile("([a-z]){" + lenght + "}").matcher(txt).matches();
    }

    /**
    * <h1>validateMayus</h1>
    * 
    * Verifica si la cadena contiene solo letras minúsculas, 
    * según el rango dado por MIN y MAX
    * 
    * @param txt Cadena a validar
    * @param MIN Rango mínimo de la cadena
    * @param MAX Rángo máximo de la cadena
    * @return TRUE si la cadena solo contiene letras minúsculas, FALSE caso contrario
    */
    public static boolean validateMinus(String txt, int MIN, int MAX){
        return Pattern.compile("([a-z]){" + MIN + "," + MAX + "}").matcher(txt).matches();
    }
    
    /**
    * <h1>validateClaveInventario</h1>
    * 
    * Verifica si la cadena contiene el formato para 
    * el ID del inventario normal, el cual consiste en 
    * todas las cadenas que empiecen con CMP seguidas
    * de 8 números
    * 
    * @param txt Cadena a validar
    * @return TRUE si la cadena solo contiene el formato especificado, FALSE caso contrario
    */
    public static boolean validateClaveInventario(String txt){
        return Pattern.compile( "CMP([0-9]{8})").matcher(txt).matches();
    }
    /**
    * <h1>validateClaveInventarioGranel</h1>
    * 
    * Verifica si la cadena contiene el formato para 
    * el ID del inventario normal, el cual consiste en 
    * todas las cadenas que empiecen con CMP seguidas
    * de 8 números
    * 
    * @param txt Cadena a validar
    * @return TRUE si la cadena solo contiene el formato especificado, FALSE caso contrario
    */

	public static boolean validateClaveInventarioGranel(String txt){
        return Pattern.compile( "GMP([0-9]{8})").matcher(txt).matches();
    }
    
    /**
    * <h1>validateNoSerieInventario</h1>
    * 
    * Verifica si la cadena contiene el formato para 
    * el número de serie con el formato especificado
    * que son 10 caracteres alfanuméricos
    * 
    * @param txt Cadena a validar
    * @return TRUE si la cadena solo contiene el formato especificado, FALSE caso contrario
    */
    public static boolean validateNoSerieInventario(String txt){
        return Pattern.compile("([A-Z0-9]){10}").matcher(txt).matches();
    }
    
    public static boolean validarNombres_añadirUsuario(String txt){
        return Pattern.compile("[A-Z]([a-z]{1,9})\u0020[A-Z]([a-z]{1,9})|[A-Z]([a-z]{1,19})").matcher(txt).matches();
    }
    
    /**
    * <h1>validateAlphanumericWithSpacing</h1>
    * 
    * Verifica si la cadena contiene el formato para 
    * el número de serie con el formato especificado
    * que son n caracteres alfanuméricos incluyendo espacio
    * 
    * @param txt Cadena a validar
    * @param MIN Rango mínimo de la cadena
    * @param MAX Rángo máximo de la cadena
    * @return TRUE si la cadena solo contiene el formato especificado, FALSE caso contrario
    */
    public static boolean validateAlphanumericWithSpacing(String txt,int MIN, int MAX){
        return Pattern.compile("([a-zA-Z0-9]|\u0020){" + MIN + "," + MAX + "}").matcher(txt).matches();
    }
	
	/**
	 *<h1>Concatenar Ceros</h1>
	 * 
	 * <p>Añade los ceros a la izquierda para el número dado</p>
	 * 
	 * @param integer valor que se desea convertir
	 * @return <code>String</code> con el formato deseado
	 */
	public String concatZeros(int integer){
		// Si el número de digitos es mayor o igual al deseado, se retorna solo la cadena del numero
		if(String.valueOf(integer).length() >= this.FORMATO_DIGITOS) 
			return String.valueOf(integer);
		
		// Objeto para construir la cadena
		StringBuilder sb = new StringBuilder();
		// Se añade el número
		sb.append(integer);
		
		// Adición de los ceros
		for (int i = String.valueOf(integer).length(); i < this.FORMATO_DIGITOS; i++) 
			sb.insert(0, "0");
		
		return sb.toString();
	}
	
	/**
	 * <h1>Extraer Ceros</h1>
	 * 
	 * <p>Se extraen los ceros a la izquierda del formato de ID de un producto
	 * del inventario, para su correcta busqueda en la base de datos</p>
	 * 
	 * <p>Se hace la asunción que el formato de ID no cambia</p>
	 * 
	 * <ul>
	 *	<li>Folio : FY-[0 - 99]</li>
	 *	<li>Guion : - </li>
	 *	<li>Número de Producto </li>
	 *	<li>Extensión</li>
	 * </ul>
	 * 
	 * @param ID del producto de inventario
	 * @return	<code>String</code> del ID del producto, sin ceros a la izquierda
	 *		en el Número del Producto
	 */
	public static String extractZeros(String ID){
		// Objeto para la cadena
		StringBuilder sb = new StringBuilder();
		
		// Se separa la cadena en guión
		String[] array = ID.split("-");
		
		// Se añaden los datos a la cadena
		sb.append(array[0]);
		sb.append("-");
		sb.append(array[1]);
		sb.append("-");
		// Se eliminan los ceros a la izquiera con una expresión regular
		// **	Dado que solo se eliminan los ceros a la izquierda, es decir que 
		//		solo se eliminan elementos al principio de la cadena, no importa si el 
		//		ID del producto cuenta con una extensión o no.
		sb.append(array[2].replaceFirst("^0+(?!$)", ""));
		
		return sb.toString();
	}
}
