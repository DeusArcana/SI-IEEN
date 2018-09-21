/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Vector;

import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.border.Border;
/**
 *
 * @author GuillermoR
 */
public class CrearSolicitudViatico {
    private File directorio;
    private File archivo;
    private String path, archivo_nombre;
  //  private infoTicket info;
    public String f;
    public CrearSolicitudViatico(String folio){
    //    obtener_productos = new ObtenerProductos();
        path = "C:\\SIIEEN\\oficiossolicitudesviaticos\\";
        directorio = new File(path);
    //    info = new infoTicket();
            
        f=folio;
        if (directorio.exists()) {
            archivo_nombre = "oficio_" + folio+".pdf";
            archivo = new File(directorio, archivo_nombre);
        } else {
            directorio.mkdirs();
            if (directorio.exists()) {
                archivo_nombre = "oficio_" +folio+".pdf";
                archivo = new File(directorio, archivo_nombre);
            } else {
                System.out.println("No se pudo crear el directorio");
            }//ifelse
        }//if else
        
    }//constructor
    
   

   
    private static Font numero = new Font(Font.FontFamily.HELVETICA, 40,
            Font.BOLD);

    private static Font estilo = new Font(Font.FontFamily.HELVETICA, 22,
            Font.BOLD);
    private static Font Mex = new Font(Font.FontFamily.HELVETICA, 12,
            Font.BOLD);

    private static Font codigo = new Font(Font.FontFamily.COURIER, 12,
            Font.NORMAL);
    
     private static Font catFont = new Font(Font.FontFamily.HELVETICA, 12,
            Font.BOLD);
    private static Font elements = new Font(Font.FontFamily.COURIER, 10,
            Font.NORMAL);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
            Font.NORMAL);
    private static Font subFont2 = new Font(Font.FontFamily.HELVETICA, 10,
            Font.NORMAL);
    private static Font subFont3 = new Font(Font.FontFamily.HELVETICA, 8,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.BOLD);
    private static Font smallBold0 = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.NORMAL);
    private static Font smallBold3 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);

    private static Font smallBold2 = new Font(Font.FontFamily.COURIER, 12,
            Font.BOLD);
    
    private static Font elements2 = new Font(Font.FontFamily.HELVETICA, 9,
            Font.NORMAL);
    private static Font elements2c = new Font(Font.FontFamily.HELVETICA,8,Font.NORMAL,BaseColor.LIGHT_GRAY);
    private static Font elements3 = new Font(Font.FontFamily.HELVETICA, 9,
            Font.BOLD);
    private static Font elements3c = new Font(Font.FontFamily.HELVETICA, 8,
            Font.BOLD);
    private static Font subFontF = new Font(Font.FontFamily.HELVETICA, 8,
            Font.NORMAL);
    
    
    
    
    
    public void createTicket(int res,String fecha_salida,String fecha_llegada,String empleado,String lugarSalida,String actividadRealizar,String pernoctado,String vehiculoAsignado,String consejero,String area)throws DocumentException {
        
        Rectangle pagesize = new Rectangle(250, 14400);
        Document doc = new Document(pagesize);
        PdfWriter docWriter = null;
        String pdfFilename="ofisoliviatico"+f;
        
        /*String[] separar=fecha.split("-");
        String mes="";
        int n=Integer.parseInt(separar[1]);
        switch(n){
            case 1:
                mes="Enero";
                break;
            case 2:
                mes="Febrero";
                break;
            case 3:
                mes="Marzo";
                break;
            case 4:
                mes="Abril";
                break;
            case 5:
                mes="Mayo";
                break;
            case 6:
                mes="Junio";
                break;
            case 7:
                mes="Julio";
                break;
            case 8:
                mes="Agosto";
                break;
            case 9:
                mes="Septiembre";
                break;
            case 10:
                mes="Octubre";
                break;
            case 11:
                mes="Noviembre";
                break;
            case 12:
                mes="Diciembre";
                break;
        }
        String fechacompleta=separar[0]+" de "+mes+" del "+separar[2];*/

        try {

            //CODIGO DE BARRAS------------------------------------------------------------------------
            String path = this.path + pdfFilename + ".pdf";
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.addAuthor("IEEN");
            doc.addCreationDate();
            doc.addProducer();
            doc.setPageSize(PageSize.LETTER);
            doc.open();
            

           
            //------------------------------------------------
            

            Paragraph BLANCO = new Paragraph();

            PdfPTable Head = new PdfPTable(2);
            Head.setWidthPercentage(100f);

           

            //Obtener la imagen
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("print/blogin.png");
            //com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("src/Imagenes/IEE.png");
            image.scaleAbsoluteWidth(90f);
            image.scaleAbsoluteHeight(75f);
            PdfPCell LogoCell = new PdfPCell(image, false);
            LogoCell.setBorderColor(BaseColor.WHITE);
          

            Head.addCell(LogoCell);
            
            PdfPCell Titulo = new PdfPCell(new Phrase("INSTITUTO ESTATAL ELECTORAL DE NAYARIT", Mex));
            
            Titulo.setBorderColor(BaseColor.WHITE);
            Titulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            Titulo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            float[] medidaCeldas = {0.55f, 2.25f};

            // ASIGNAS LAS MEDIDAS A LA TABLA (ANCHO)
            Head.setWidths(medidaCeldas);
            Head.addCell(Titulo);
        
             // ASIGNAS LAS MEDIDAS A LA TABLA (ANCHO)
            Head.setWidths(medidaCeldas);   
            
            addEmptyLine(BLANCO, (float) 1);       
            
            //nombreoficio
            PdfPTable nombreofi = new PdfPTable(1);
            nombreofi.setWidthPercentage(100f);
            PdfPCell nombreCellOfi;
            
            nombreCellOfi = new PdfPCell(new Phrase("SOLICITUD DE VIÁTICOS", Mex));
            nombreCellOfi.setHorizontalAlignment(Element.ALIGN_CENTER);
            nombreCellOfi.setBorderColor(BaseColor.WHITE);
            nombreofi.addCell(nombreCellOfi);           
            
            //NOMBRECONSEJERO
            PdfPTable fechasalida = new PdfPTable(2);
            fechasalida.setWidthPercentage(100f);
            PdfPCell lineas1,lineas2;
            float[] medidaLineas = {0.65f, 2.10f};
            float[] medidaLineas2 = {0.35f,2.40f};
            float[] medidaLineas3 = {0.45f,2.30f};
            fechasalida.setWidths(medidaLineas);
            
            
            lineas1 = new PdfPCell(new Phrase("Fecha de salida:", elements3));
            lineas1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            lineas1.setBorderColor(BaseColor.WHITE);
            //lineas1.setBorder(0);
            // Contenido del NOMBRE
            lineas2 = new PdfPCell(new Phrase(fecha_salida, elements2));
            lineas2.setBorderColor(BaseColor.WHITE);
            //lineas2.setBorderWidthTop(0);
            //lineas2.setBorderWidthLeft(0);
            //lineas2.setBorderWidthRight(0);
            //lineas2.setBorder(0);
            lineas2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            
            fechasalida.addCell(lineas1);
            fechasalida.addCell(lineas2);
            
            //PUESTO
            PdfPTable fechallegada = new PdfPTable(2);
            fechallegada.setWidthPercentage(100f);
            PdfPCell lineas12,lineas22;
            fechallegada.setWidths(medidaLineas);
            
            lineas12 = new PdfPCell(new Phrase("Fecha de llegada:", elements3));
            lineas12.setHorizontalAlignment(Element.ALIGN_RIGHT);
            lineas12.setBorderColor(BaseColor.WHITE);
            //lineas12.setBorder(0);
            // Contenido deL PUESTO
            lineas22 = new PdfPCell(new Phrase(fecha_llegada, elements2));
            lineas22.setBorderColor(BaseColor.WHITE);
            //lineas22.setBorderWidthTop(0);
            //lineas22.setBorderWidthLeft(0);
            //lineas22.setBorderWidthRight(0);
            //lineas22.setBorder(0);
            lineas22.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            
            fechallegada.addCell(lineas12);
            fechallegada.addCell(lineas22);
            
            //nombre
            //nombreoficio
            PdfPTable nombre = new PdfPTable(2);
            nombre.setWidthPercentage(100f);
            PdfPCell nombreCellno,nombreCellno2;
            nombre.setWidths(medidaLineas);
            
            nombreCellno = new PdfPCell(new Phrase("Nombre:", elements3));
            nombreCellno.setHorizontalAlignment(Element.ALIGN_RIGHT);
            nombreCellno.setBorderColor(BaseColor.WHITE);
            
            nombreCellno2 = new PdfPCell(new Phrase(empleado, elements2));
            nombreCellno2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            nombreCellno2.setBorderColor(BaseColor.WHITE);
            
            nombre.addCell(nombreCellno);
            nombre.addCell(nombreCellno2);
            
            //nombreoficio
            PdfPTable lugar = new PdfPTable(2);
            lugar.setWidthPercentage(100f);
            PdfPCell nombreCellPre,nombreCellPre2;
            lugar.setWidths(medidaLineas);
            
            nombreCellPre = new PdfPCell(new Phrase("Lugar:", elements3));
            nombreCellPre.setHorizontalAlignment(Element.ALIGN_RIGHT);
            nombreCellPre.setBorderColor(BaseColor.WHITE);
            
            nombreCellPre2 = new PdfPCell(new Phrase(lugarSalida, elements2));
            nombreCellPre2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            nombreCellPre2.setBorderColor(BaseColor.WHITE);
            
            lugar.addCell(nombreCellPre);
            lugar.addCell(nombreCellPre2);
            
            //FECHA
            PdfPTable actividad = new PdfPTable(2);
            actividad.setWidthPercentage(100f);
            PdfPCell lineas14,lineas24;
            actividad.setWidths(medidaLineas);
            
            lineas14 = new PdfPCell(new Phrase("Actividad:", elements3));
            lineas14.setHorizontalAlignment(Element.ALIGN_RIGHT);
            lineas14.setBorderColor(BaseColor.WHITE);
            //lineas14.setBorder(0);
            // Contenido de la linea
            //lineas24 = new PdfPCell(new Phrase("ESTE ES UN EJEMPLO DE UNA Dirección/Área", elements2));
            lineas24 = new PdfPCell(new Phrase(actividadRealizar, elements2));
            lineas24.setBorderColor(BaseColor.WHITE);
            lineas24.setFixedHeight(100f); 
            //lineas24.setBorderWidthTop(0);
            //lineas24.setBorderWidthLeft(0);
            //lineas24.setBorderWidthRight(0);
            //lineas24.setBorder(0);
            lineas24.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            
            actividad.addCell(lineas14);
            actividad.addCell(lineas24);
            
            //contenidodelsonsignado
            PdfPTable pernoctar = new PdfPTable(6);
            pernoctar.setWidthPercentage(100f);
            float[] contenidomedidash = {2.50f,2.50f,2.50f,2.50f,2.50f,2.50f};
            pernoctar.setWidths(contenidomedidash);
            PdfPCell lineas15,lineas25,lineas151,lineas251,lineas152,lineas252;
            //tableLineas5.setWidths(medidaLineas);
            
            lineas15 = new PdfPCell(new Phrase("", elements3));
            lineas15.setHorizontalAlignment(Element.ALIGN_CENTER);
            lineas15.setBorderColor(BaseColor.WHITE);
            //lineas15.setBorder(0);
            // Contenido de la linea
            //lineas25 = new PdfPCell(new Phrase("123 "+"Hora de regreso: "+"123123"+" Horas: "+"222hrs", elements2));
            lineas25 = new PdfPCell(new Phrase("Pernoctado:", elements2));
            lineas25.setBorderColor(BaseColor.WHITE);
            //lineas25.setBorderWidthTop(0);
            //lineas25.setBorderWidthLeft(0);
            //lineas25.setBorderWidthRight(0);
            lineas25.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            //puntohorall
            String per="";
            if(pernoctado.equals("Si")){
                per="X";
            }
            lineas151 = new PdfPCell(new Phrase(per, elements3));
            lineas151.setHorizontalAlignment(Element.ALIGN_CENTER);
            lineas151.setBorderWidthRight(0);
            lineas151.setBorderWidthLeft(0);
            lineas151.setBorderWidthTop(0);
            //lineas151.setBorder(0);
            // Contenido de la linea
            //lineas25 = new PdfPCell(new Phrase("123 "+"Hora de regreso: "+"123123"+" Horas: "+"222hrs", elements2));
            lineas251 = new PdfPCell(new Phrase("Sin pernoctar", elements2));
            lineas251.setBorderColor(BaseColor.WHITE);
            //lineas251.setBorderWidthTop(0);
            //lineas251.setBorderWidthLeft(0);
            //lineas251.setBorderWidthRight(0);
            lineas251.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            //puntohoras
            if(pernoctado.equals("No")){
                per="X";
            }else{
                per="";
            }
            lineas152 = new PdfPCell(new Phrase(per, elements3));
            lineas152.setHorizontalAlignment(Element.ALIGN_CENTER);
            lineas152.setBorderWidthRight(0);
            lineas152.setBorderWidthLeft(0);
            lineas152.setBorderWidthTop(0);
            // Contenido de la linea
            //lineas25 = new PdfPCell(new Phrase("123 "+"Hora de regreso: "+"123123"+" Horas: "+"222hrs", elements2));
            lineas252 = new PdfPCell(new Phrase("", elements2));
            lineas252.setBorderColor(BaseColor.WHITE);
            //lineas252.setBorderWidthTop(0);
            //lineas252.setBorderWidthLeft(0);
            //lineas252.setBorderWidthRight(0);
            lineas252.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            pernoctar.addCell(lineas15);
            pernoctar.addCell(lineas25);
            pernoctar.addCell(lineas151);
            pernoctar.addCell(lineas251);
            pernoctar.addCell(lineas152);
            pernoctar.addCell(lineas252);
            
            //nombreoficio
            PdfPTable vehiculo = new PdfPTable(2);
            vehiculo.setWidthPercentage(100f);
            PdfPCell nombreCellve,nombreCellve2;
            vehiculo.setWidths(medidaLineas);
            
            nombreCellve = new PdfPCell(new Phrase("Vehiculo:", elements3));
            nombreCellve.setHorizontalAlignment(Element.ALIGN_RIGHT);
            nombreCellve.setBorderColor(BaseColor.WHITE);
            
            nombreCellve2 = new PdfPCell(new Phrase(vehiculoAsignado, elements2));
            nombreCellve2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            nombreCellve2.setBorderColor(BaseColor.WHITE);
            
            vehiculo.addCell(nombreCellve);
            vehiculo.addCell(nombreCellve2);
            
            //area de firma de autorizado
            PdfPTable firmas = new PdfPTable(3);
            firmas.setWidthPercentage(100f);
            float[] medidaCeldasfirmas = {1.10f,1.85f,1.10f};
            firmas.setWidths(medidaCeldasfirmas);
            PdfPCell encabezadosfirmasCell,encabezadosfirmasCell2,encabezadosfirmasCell3;
            
            encabezadosfirmasCell = new PdfPCell(new Phrase("", elements2));
            //encabezadosfirmasCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell.setBorderColor(BaseColor.WHITE);
            encabezadosfirmasCell.setBackgroundColor(BaseColor.WHITE);
            
            encabezadosfirmasCell2 = new PdfPCell(new Phrase("V°.B°.", elements2));
            encabezadosfirmasCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell2.setBorderColor(BaseColor.WHITE);
            
            //encabezadosfirmasCell2.setBorder(0);
            
            encabezadosfirmasCell3 = new PdfPCell(new Phrase("", elements2));
            //encabezadosfirmasCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell3.setBorderColor(BaseColor.WHITE);
            encabezadosfirmasCell3.setBackgroundColor(BaseColor.WHITE);

            firmas.addCell(encabezadosfirmasCell);
            firmas.addCell(encabezadosfirmasCell2);
            firmas.addCell(encabezadosfirmasCell3);
            
            //area de firma
            PdfPTable firmas2 = new PdfPTable(3);
            firmas2.setWidthPercentage(100f);
            float[] medidaCeldasfirmas2 = {1.10f,1.85f,1.10f};
            firmas2.setWidths(medidaCeldasfirmas2);
            PdfPCell encabezadosfirmasCell22,encabezadosfirmasCell32,encabezadosfirmasCell42;
            
            encabezadosfirmasCell22 = new PdfPCell(new Phrase("", elements2));
            //encabezadosfirmasCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell22.setBorderColor(BaseColor.WHITE);
            encabezadosfirmasCell22.setBackgroundColor(BaseColor.WHITE);
            
            encabezadosfirmasCell32 = new PdfPCell(new Phrase(consejero, elements2));
            encabezadosfirmasCell32.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell32.setBorderWidthBottom(0);
            encabezadosfirmasCell32.setBorderWidthLeft(0);
            encabezadosfirmasCell32.setBorderWidthRight(0);
            
            //encabezadosfirmasCell2.setBorder(0);
            
            encabezadosfirmasCell42 = new PdfPCell(new Phrase("", elements2));
            //encabezadosfirmasCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell42.setBorderColor(BaseColor.WHITE);
            encabezadosfirmasCell42.setBackgroundColor(BaseColor.WHITE);

            firmas2.addCell(encabezadosfirmasCell22);
            firmas2.addCell(encabezadosfirmasCell32);
            firmas2.addCell(encabezadosfirmasCell42);
            
            //area de firmas 2
            PdfPTable firmas3 = new PdfPTable(3);
            firmas3.setWidthPercentage(100f);
            float[] medidaCeldasfirmas3 = {1.10f,1.85f,1.10f};
            firmas3.setWidths(medidaCeldasfirmas3);
            PdfPCell encabezadosfirmasCell221,encabezadosfirmasCell321,encabezadosfirmasCell421;
            
            encabezadosfirmasCell221 = new PdfPCell(new Phrase("", elements2));
            //encabezadosfirmasCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell221.setBorderColor(BaseColor.WHITE);
            encabezadosfirmasCell221.setBackgroundColor(BaseColor.WHITE);
            
            encabezadosfirmasCell321 = new PdfPCell(new Phrase(area, elements2));
            encabezadosfirmasCell321.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell321.setBorderColor(BaseColor.WHITE);
            
            //encabezadosfirmasCell2.setBorder(0);
            
            encabezadosfirmasCell421 = new PdfPCell(new Phrase("", elements2));
            //encabezadosfirmasCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell421.setBorderColor(BaseColor.WHITE);
            encabezadosfirmasCell421.setBackgroundColor(BaseColor.WHITE);

            firmas3.addCell(encabezadosfirmasCell221);
            firmas3.addCell(encabezadosfirmasCell321);
            firmas3.addCell(encabezadosfirmasCell421);
            
            //AGREGAR AL DOCUMENTO ----------------------------------------------------------------

            //Encabezado   Logo | Instituto estatal ....
            doc.add(Head); 
            //nombre
            doc.add(BLANCO);
            doc.add(nombreofi);
            //Espacio en blanco
            doc.add(BLANCO);
            doc.add(BLANCO);
            //Encabezados
            doc.add(fechasalida);
            //
            doc.add(BLANCO);
            doc.add(BLANCO);
            // Lineas
            doc.add(fechallegada);
            //Espacio en blanco
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(nombre);
            //Espacio en blanco
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(lugar);
            //Espacio en blanco
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(actividad);

            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(pernoctar);
            //Espacio en blanco
            
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(vehiculo);
                      
            //fimas
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(BLANCO);
            
            //doc.add(BLANCO);
            
            doc.add(firmas);
            
            //fimas
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(firmas2);
            doc.add(firmas3);
            
            archivo_nombre = pdfFilename;

            if (res == 1) {
                imprimir();
            } else {
                JOptionPane.showMessageDialog(null, "Pase creado con éxito!", "Información", INFORMATION_MESSAGE);
            }
        } catch (java.io.FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "ERROR: No hay imagen prro dist\\print\\blogin.png", "Advertencia!", JOptionPane.WARNING_MESSAGE);
        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                doc.close();
            }
            if (docWriter != null) {
                docWriter.close();
            }
        }
    }
    public void imprimir() throws IOException{
        File fileToPrint = new File("C:\\SIIEEN\\oficiossolicitudesviaticos\\"+archivo_nombre+".pdf");
		Desktop.getDesktop().open(fileToPrint);
    }
    
    private static void addEmptyLine(Paragraph paragraph, float number) {
        for (float i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
}
