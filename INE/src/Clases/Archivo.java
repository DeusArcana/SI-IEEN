/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    public static void createArchivo(String ruta,String nombre,String contenido) throws IOException{
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
            //System.out.println("i= "+i);
            cadena = b.readLine();//System.out.println("cadena= "+cadena);
            if(cadena!=null){
                content+=cadena;
            }
            //System.out.println("content= "+content);
            i++;
        }
        //content=content.replace("$", "");
        //System.out.println("cadena resultante= "+content);
        b.close(); 
        return content;
    }
}
