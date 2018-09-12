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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GuillermoR 
 */
public class CrearOficioComision {
    private File directorio;
    private File archivo;
    private String path, archivo_nombre;
  //  private infoTicket info;

    public CrearOficioComision(){
    //    obtener_productos = new ObtenerProductos();
        path = "C:\\SIIEEN\\oficioscomision\\";
        directorio = new File(path);
    //    info = new infoTicket();
            
        
        if (directorio.exists()) {
            archivo_nombre = "oficio_" + ".pdf";
            archivo = new File(directorio, archivo_nombre);
        } else {
            directorio.mkdirs();
            if (directorio.exists()) {
                archivo_nombre = "oficio_" +".pdf";
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
    
    
    
    
    
    public void createTicket(int res,String folio,String empleado,String puesto,String lugar,String fecha_salida,String fecha_llegada,String actividad)throws DocumentException {
        
        Rectangle pagesize = new Rectangle(250, 14400);
        Document doc = new Document(pagesize);
        PdfWriter docWriter = null;
        String pdfFilename="pruebaoficomision";
        
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
            
            //folio
            PdfPTable nombre = new PdfPTable(1);
            nombre.setWidthPercentage(100f);
            PdfPCell nombreCell;
            
            nombreCell = new PdfPCell(new Phrase("FOLIO NÚMERO: "+folio, elements3));
            nombreCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            nombreCell.setBorderColor(BaseColor.WHITE);
            nombre.addCell(nombreCell);
            
            //nombreoficio
            PdfPTable nombreofi = new PdfPTable(1);
            nombre.setWidthPercentage(100f);
            PdfPCell nombreCellOfi;
            
            nombreCellOfi = new PdfPCell(new Phrase("OFICIO DE COMISIÓN", Mex));
            nombreCellOfi.setHorizontalAlignment(Element.ALIGN_CENTER);
            nombreCellOfi.setBorderColor(BaseColor.WHITE);
            nombreofi.addCell(nombreCellOfi);           
            
            //NOMBRECONSEJERO
            PdfPTable tableLineas = new PdfPTable(2);
            tableLineas.setWidthPercentage(100f);
            PdfPCell lineas1,lineas2;
            float[] medidaLineas = {0.65f, 2.10f};
            float[] medidaLineas2 = {0.35f,2.40f};
            float[] medidaLineas3 = {0.45f,2.30f};
            tableLineas.setWidths(medidaLineas2);
            
            
            lineas1 = new PdfPCell(new Phrase("Nombre:", elements3));
            lineas1.setHorizontalAlignment(Element.ALIGN_LEFT);
            lineas1.setBorder(0);
            // Contenido del NOMBRE
            lineas2 = new PdfPCell(new Phrase(empleado, elements2));
            //lineas2.setBorderWidthTop(0);
            //lineas2.setBorderWidthLeft(0);
            //lineas2.setBorderWidthRight(0);
            lineas2.setBorder(0);
            lineas2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            
            tableLineas.addCell(lineas1);
            tableLineas.addCell(lineas2);
            
            //PUESTO
            PdfPTable tableLineas2 = new PdfPTable(2);
            tableLineas2.setWidthPercentage(100f);
            PdfPCell lineas12,lineas22;
            tableLineas2.setWidths(medidaLineas2);
            
            lineas12 = new PdfPCell(new Phrase("Puesto:", elements3));
            lineas12.setHorizontalAlignment(Element.ALIGN_LEFT);
            lineas12.setBorder(0);
            // Contenido deL PUESTO
            lineas22 = new PdfPCell(new Phrase(puesto, elements2));
            //lineas22.setBorderWidthTop(0);
            //lineas22.setBorderWidthLeft(0);
            //lineas22.setBorderWidthRight(0);
            lineas22.setBorder(0);
            lineas22.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            
            tableLineas2.addCell(lineas12);
            tableLineas2.addCell(lineas22);
            
            //nombreoficio
            PdfPTable nombrepresente = new PdfPTable(2);
            nombrepresente.setWidthPercentage(100f);
            PdfPCell nombreCellPre,nombreCellPre2;
            nombrepresente.setWidths(medidaLineas);
            
            nombreCellPre = new PdfPCell(new Phrase("P R E S E N T E :", Mex));
            nombreCellPre.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellPre.setBorderColor(BaseColor.WHITE);
            
            nombreCellPre2 = new PdfPCell(new Phrase("", Mex));
            nombreCellPre2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            nombreCellPre2.setBorderColor(BaseColor.WHITE);
            
            nombrepresente.addCell(nombreCellPre);
            nombrepresente.addCell(nombreCellPre2);
            
            //FECHA
            PdfPTable tableLineas4 = new PdfPTable(2);
            tableLineas4.setWidthPercentage(100f);
            PdfPCell lineas14,lineas24;
            float[] medidaLugar={0.65f, 2.50f};
            tableLineas4.setWidths(medidaLugar);
            
            lineas14 = new PdfPCell(new Phrase("Con este lugar y fecha:", elements3));
            lineas14.setHorizontalAlignment(Element.ALIGN_LEFT);
            lineas14.setBorder(0);
            // Contenido de la linea
            //lineas24 = new PdfPCell(new Phrase("ESTE ES UN EJEMPLO DE UNA Dirección/Área", elements2));
            lineas24 = new PdfPCell(new Phrase(lugar, elements2));
            //lineas24.setBorderWidthTop(0);
            //lineas24.setBorderWidthLeft(0);
            //lineas24.setBorderWidthRight(0);
            //lineas24.setBorder(0);
            lineas24.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            
            tableLineas4.addCell(lineas14);
            tableLineas4.addCell(lineas24);
            
            //conignado
            PdfPTable nombrecon = new PdfPTable(1);
            nombrecon.setWidthPercentage(100f);
            PdfPCell nombreCellCon;
            
            nombreCellCon = new PdfPCell(new Phrase("Ha sido comisionado por el suscrito, a efecto de llevar a cabo visita de trabajo:", elements3));
            nombreCellCon.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellCon.setBorderColor(BaseColor.WHITE);
            nombrecon.addCell(nombreCellCon);
            
            //contenidodelsonsignado
            PdfPTable tableLineas5 = new PdfPTable(6);
            tableLineas5.setWidthPercentage(100f);
            float[] contenidomedidash = {2.50f,2.50f,2.50f,2.50f,2.50f,2.50f};
            tableLineas5.setWidths(contenidomedidash);
            PdfPCell lineas15,lineas25,lineas151,lineas251,lineas152,lineas252;
            //tableLineas5.setWidths(medidaLineas);
            
            lineas15 = new PdfPCell(new Phrase("Los dias:", elements3));
            lineas15.setHorizontalAlignment(Element.ALIGN_CENTER);
            lineas15.setBorder(0);
            // Contenido de la linea
            //lineas25 = new PdfPCell(new Phrase("123 "+"Hora de regreso: "+"123123"+" Horas: "+"222hrs", elements2));
            lineas25 = new PdfPCell(new Phrase(fecha_salida+" al "+fecha_llegada, elements2));
            lineas25.setBorderWidthTop(0);
            lineas25.setBorderWidthLeft(0);
            lineas25.setBorderWidthRight(0);
            lineas25.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            //puntohorall
            
            lineas151 = new PdfPCell(new Phrase("del mes:", elements3));
            lineas151.setHorizontalAlignment(Element.ALIGN_CENTER);
            lineas151.setBorder(0);
            // Contenido de la linea
            //lineas25 = new PdfPCell(new Phrase("123 "+"Hora de regreso: "+"123123"+" Horas: "+"222hrs", elements2));
            lineas251 = new PdfPCell(new Phrase("Septiembre", elements2));
            lineas251.setBorderWidthTop(0);
            lineas251.setBorderWidthLeft(0);
            lineas251.setBorderWidthRight(0);
            lineas251.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            //puntohoras
            lineas152 = new PdfPCell(new Phrase("del año:", elements3));
            lineas152.setHorizontalAlignment(Element.ALIGN_CENTER);
            lineas152.setBorder(0);
            // Contenido de la linea
            //lineas25 = new PdfPCell(new Phrase("123 "+"Hora de regreso: "+"123123"+" Horas: "+"222hrs", elements2));
            lineas252 = new PdfPCell(new Phrase("2018", elements2));
            lineas252.setBorderWidthTop(0);
            lineas252.setBorderWidthLeft(0);
            lineas252.setBorderWidthRight(0);
            lineas252.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            tableLineas5.addCell(lineas15);
            tableLineas5.addCell(lineas25);
            tableLineas5.addCell(lineas151);
            tableLineas5.addCell(lineas251);
            tableLineas5.addCell(lineas152);
            tableLineas5.addCell(lineas252);
                        
             //estado
            PdfPTable estado = new PdfPTable(2);
            estado.setWidthPercentage(100f);
            PdfPCell nombreCellEst,nombreCellEst2;
            estado.setWidths(medidaLineas);
            
            
            nombreCellEst = new PdfPCell(new Phrase("En el estado de:", elements3));
            nombreCellEst.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellEst.setBorderColor(BaseColor.WHITE);
            
            nombreCellEst2 = new PdfPCell(new Phrase("", elements3));
            nombreCellEst2.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellEst2.setBorderColor(BaseColor.WHITE);
            
            estado.addCell(nombreCellEst);
            estado.addCell(nombreCellEst2);
            
             //contenidoestado
            PdfPTable estadocon = new PdfPTable(2);
            estadocon.setWidthPercentage(100f);
            PdfPCell nombreCellEstCon,nombreCellEstCon2;
            estadocon.setWidths(medidaLineas);
            
            nombreCellEstCon = new PdfPCell(new Phrase(lugar.split(",")[0], elements3));
            nombreCellEstCon.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellEstCon.setBorderColor(BaseColor.WHITE);
            
            nombreCellEstCon2 = new PdfPCell(new Phrase("", elements3));
            nombreCellEstCon2.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellEstCon2.setBorderColor(BaseColor.WHITE);
            
            estadocon.addCell(nombreCellEstCon);
            estadocon.addCell(nombreCellEstCon2);
            
            //estado
            PdfPTable estado2 = new PdfPTable(2);
            estado2.setWidthPercentage(100f);
            PdfPCell nombreCellEst3,nombreCellEst4;
            estado2.setWidths(medidaLineas);
            
            nombreCellEst3 = new PdfPCell(new Phrase("Que consiste en:", elements3));
            nombreCellEst3.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellEst3.setBorderColor(BaseColor.WHITE);
            
            nombreCellEst4 = new PdfPCell(new Phrase("", elements3));
            nombreCellEst4.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellEst4.setBorderColor(BaseColor.WHITE);
            
            estado2.addCell(nombreCellEst3);
            estado2.addCell(nombreCellEst4);
            
             //contenidoestado
            PdfPTable estadocon2 = new PdfPTable(2);
            estadocon2.setWidthPercentage(100f);
            PdfPCell nombreCellEstCon3,nombreCellEstCon4;
            estadocon2.setWidths(medidaLineas);
            
            nombreCellEstCon3 = new PdfPCell(new Phrase(actividad, elements3));
            nombreCellEstCon3.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellEstCon3.setBorderColor(BaseColor.WHITE);
            
            nombreCellEstCon4 = new PdfPCell(new Phrase("", elements3));
            nombreCellEstCon4.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellEstCon4.setBorderColor(BaseColor.WHITE);
            
            estadocon2.addCell(nombreCellEstCon3);
            estadocon2.addCell(nombreCellEstCon4);
            
            //estado
            PdfPTable estado3 = new PdfPTable(2);
            estado3.setWidthPercentage(100f);
            PdfPCell nombreCellEst5,nombreCellEst6;
            estado3.setWidths(medidaLineas);
            
            nombreCellEst5 = new PdfPCell(new Phrase("Vehiculo Oficial Asignado:", elements3));
            nombreCellEst5.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellEst5.setBorderColor(BaseColor.WHITE);
            
            nombreCellEst6 = new PdfPCell(new Phrase("", elements3));
            nombreCellEst6.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellEst6.setBorderColor(BaseColor.WHITE);
            
            estado3.addCell(nombreCellEst5);
            estado3.addCell(nombreCellEst6);
            
             //contenidoestado
            PdfPTable estadocon3 = new PdfPTable(2);
            estadocon3.setWidthPercentage(100f);
            PdfPCell nombreCellEstCon5,nombreCellEstCon6;
            estadocon3.setWidths(medidaLineas);
            
            nombreCellEstCon5 = new PdfPCell(new Phrase("N/A", elements3));
            nombreCellEstCon5.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellEstCon5.setBorderColor(BaseColor.WHITE);
            
            nombreCellEstCon6 = new PdfPCell(new Phrase("", elements3));
            nombreCellEstCon6.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellEstCon6.setBorderColor(BaseColor.WHITE);
            
            estadocon3.addCell(nombreCellEstCon5);
            estadocon3.addCell(nombreCellEstCon6);
            
            
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
            
            encabezadosfirmasCell2 = new PdfPCell(new Phrase("Firma de autorizado", elements2));
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
            
            encabezadosfirmasCell32 = new PdfPCell(new Phrase("MTRO. RODRIGO ARANZABAL GONZALEZ", elements2));
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
            
            encabezadosfirmasCell321 = new PdfPCell(new Phrase("DIRECTOR ADMINISTRATIVO", elements2));
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
            doc.add(nombre);
            //Espacio en blanco
            doc.add(BLANCO);
            //Encabezados
            doc.add(nombreofi);
            
            doc.add(BLANCO);
            //Espacio en blanco
            doc.add(BLANCO);
            // Lineas
            doc.add(tableLineas);
            //Espacio en blanco
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(tableLineas2);
            //Espacio en blanco
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(nombrepresente);
            //Espacio en blanco
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(tableLineas4);
            //
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(nombrecon);
            //Espacio en blanco
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(tableLineas5);
            //Espacio en blanco
            //doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(estado);
            
            //doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(estadocon);
            //Espacio en blanco
            //doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(estado2);
            
            //doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(estadocon2);
             //Espacio en blanco
            //doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(estado3);
            
            //doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(estadocon3);
            
            //fimas
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
        File fileToPrint = new File("C:\\SIIEEN\\oficioscomision\\"+archivo_nombre+".pdf");
		Desktop.getDesktop().open(fileToPrint);
    }
    
    private static void addEmptyLine(Paragraph paragraph, float number) {
        for (float i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
