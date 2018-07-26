/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 *
 * @author kevin
 */
public class Excel {
    private HSSFWorkbook libro;
    private JFileChooser fileChooser;
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("Hoja de excel","xlsx");
    private boolean isopen =false;//bandera de control para saber si se abrio un archivo
    private ArrayList contenido = new ArrayList();//almacena los registros leidos de *.txt
    private int index = 0; //lleva control del registro actualmente visible
    
    public Excel() {
        this.libro = new HSSFWorkbook();
    }
    
    public boolean GeneraExcel(JTable table,File fichero) {
        //Creamos la hoja de excel
        HSSFSheet hoja = libro.createSheet();
        HSSFRow encabezados = hoja.createRow(0);
        
        for (int j = 0; j <= table.getColumnCount()-1; j++) {
            HSSFCell celda = encabezados.createCell(j);
            celda.setCellValue(new HSSFRichTextString(table.getColumnModel().getColumn(j).getHeaderValue().toString()));
        }//for para los encabezados
        
        //Escribimos en el documento de excel
            try {
                FileOutputStream elFichero = new FileOutputStream(fichero+".xls");
                libro.write(elFichero);
                elFichero.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }//catch
        
        //Comenzamos a llenar los registros en la hoja de excel 
        for (int i = 0; i < table.getRowCount(); i++){
            
            HSSFRow fila = hoja.createRow(i+1);
            for (int j = 0; j <= table.getColumnCount()-1; j++) {
                HSSFCell celda = fila.createCell(j);
                if(table.getValueAt(i, j)!=null)
                    celda.setCellValue(new HSSFRichTextString(table.getValueAt(i, j).toString()));
            }//for para las columnas
            
            //Escribimos en el documento de excel
            try {
                FileOutputStream elFichero = new FileOutputStream(fichero+".xls");
                libro.write(elFichero);
                elFichero.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }//catch
            
        }//for
        return true;
    }//GenerarExcel 
    
    public boolean GuardarComo(JTable table){       
       fileChooser = new JFileChooser();
       fileChooser.setFileFilter(filter);
       int result = fileChooser.showSaveDialog(null);
       if (result == JFileChooser.APPROVE_OPTION ){
                this.isopen = false;
                this.contenido.clear();
                this.index=1;
                if (GeneraExcel(table,fileChooser.getSelectedFile())){
                    JOptionPane.showMessageDialog(null, "Archivo ' " + fileChooser.getSelectedFile().getName() + "'se ha guardado exitosamente");
                    this.isopen=true;
                    return true;
                }//Avisar que si se pudo guardar
        }//if (result == JFileChooser.APPROVE_OPTION )
       JOptionPane.showMessageDialog(null, "Archivo ' " + fileChooser.getSelectedFile().getName() + "'no se ha logrado guardar");
       return false;
    }//GuardarComo
    
}//Class Excel
