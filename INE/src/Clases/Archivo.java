package Clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Pablo
 */
public class Archivo {
    public static void createArchivo(String ruta, String nombre, String contenido) throws IOException{
        ruta += nombre;
        File archivo = new File(ruta);
        BufferedWriter bw;
        if(archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(contenido+"$");
        } else {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(contenido+"$");
        }
            bw.close();
    }
    
    public static String leerContenidoArchivo(String ruta) throws FileNotFoundException, IOException{
        int i=0;
        String cadena="";
        String content="";
        FileReader f = new FileReader(ruta);
        BufferedReader b = new BufferedReader(f);
        while(cadena!=null){
            cadena = b.readLine();
            if(cadena!=null){
                content+=cadena;
            }
            i++;
        }
        b.close(); 
        return content;
    }
}
