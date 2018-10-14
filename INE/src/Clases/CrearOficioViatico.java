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
public class CrearOficioViatico {
    private File directorio;
    private File archivo;
    private String path, archivo_nombre;
  //  private infoTicket info;
    NumeroLetras nl;

    public CrearOficioViatico(String folio){
    //    obtener_productos = new ObtenerProductos();
        nl = new NumeroLetras();
        path = "C:\\SIIEEN\\oficiosviaticos\\";
        directorio = new File(path);
    //    info = new infoTicket();
            
        
        if (directorio.exists()) {
            archivo_nombre = "oficio_" +folio+ ".pdf";
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
    
    
    
    
    
    public void createTicket(int res,String folioComision,String empleado,String puesto_trabajo,String monto,String actividad,String lugar,String  fecha_salida,String fecha_llegada,String pernoctado,String vehiculoAsignado,String responsable,String puestoResp)throws DocumentException {
        
        Rectangle pagesize = new Rectangle(250, 14400);
        Document doc = new Document(pagesize);
        PdfWriter docWriter = null;
        String pdfFilename="ofiviatico"+folioComision;
        
        String[] separar=fecha_salida.split("-");
        String[] separar_llegada=fecha_llegada.split("-");
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
        n=Integer.parseInt(separar_llegada[1]);
        switch(n){
            case 1:
                if(!mes.equals("Enero")){
                    mes+=" - Enero";
                }
                break;
            case 2:
                if(!mes.equals("Febrero")){
                    mes+=" - Febrero";
                }
                break;
            case 3:
                if(!mes.equals("Marzo")){
                    mes+=" - Marzo";
                }
                break;
            case 4:
                if(!mes.equals("Abril")){
                    mes+=" - Abril";
                }
                break;
            case 5:
                if(!mes.equals("Mayo")){
                    mes+=" - Mayo";
                }
                break;
            case 6:
                if(!mes.equals("Junio")){
                    mes+=" - Junio";
                }
                break;
            case 7:
                if(!mes.equals("Julio")){
                    mes+=" - Julio";
                }
                break;
            case 8:
                if(!mes.equals("Agosto")){
                    mes+=" - Agosto";
                }
                break;
            case 9:
                if(!mes.equals("Septiembre")){
                    mes+=" - Septiembre";
                }
                break;
            case 10:
                if(!mes.equals("Octubre")){
                    mes+=" - Octubre";
                }
                break;
            case 11:
                if(!mes.equals("Noviembre")){
                    mes+=" - Noviembre";
                }
                break;
            case 12:
                if(!mes.equals("Diciembre")){
                    mes+=" - Diciembre";
                }
                break;
        }

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
            PdfPTable folio = new PdfPTable(1);
            folio.setWidthPercentage(100f);
            PdfPCell folioCell;
            
            folioCell = new PdfPCell(new Phrase("FOLIO NÚMERO: "+folioComision, elements3));
            folioCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            folioCell.setBorderColor(BaseColor.WHITE);
            folio.addCell(folioCell);
            //FECHA
            PdfPTable fecha = new PdfPTable(1);
            fecha.setWidthPercentage(100f);
            PdfPCell fechaCell;
            
            fechaCell = new PdfPCell(new Phrase("Tepic, Nayarit 11 de Septiembre del 2018", elements3));
            fechaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            fechaCell.setBorderColor(BaseColor.WHITE);
            fecha.addCell(fechaCell);
            
            //nombreoficio
            PdfPTable nombreofi = new PdfPTable(1);
            nombreofi.setWidthPercentage(100f);
            PdfPCell nombreCellOfi;
            
            nombreCellOfi = new PdfPCell(new Phrase("VIÁTICOS", Mex));
            nombreCellOfi.setHorizontalAlignment(Element.ALIGN_CENTER);
            nombreCellOfi.setBorderColor(BaseColor.WHITE);
            nombreofi.addCell(nombreCellOfi);           
            
            //NOMBRECONSEJERO
            PdfPTable nombre = new PdfPTable(2);
            nombre.setWidthPercentage(100f);
            PdfPCell lineas1,lineas2;
            //float[] medidaLineas = {0.65f, 2.10f};
            float[] medidaLineas2 = {0.35f,2.40f};
            //float[] medidaLineas3 = {0.45f,2.30f};
            float[] medidaLineas4 = {2.10f,0.65f};
            nombre.setWidths(medidaLineas2);
            
            
            lineas1 = new PdfPCell(new Phrase("Nombre:", elements3));
            lineas1.setHorizontalAlignment(Element.ALIGN_LEFT);
            lineas1.setBorder(0);
            // Contenido del NOMBRE
            lineas2 = new PdfPCell(new Phrase(empleado, elements2));
            lineas2.setBorderColor(BaseColor.WHITE);
            //lineas2.setBorderWidthTop(0);
            //lineas2.setBorderWidthLeft(0);
            //lineas2.setBorderWidthRight(0);
            lineas2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            
            nombre.addCell(lineas1);
            nombre.addCell(lineas2);
            
            //PUESTO
            PdfPTable puesto = new PdfPTable(2);
            puesto.setWidthPercentage(100f);
            PdfPCell lineas12,lineas22;
            puesto.setWidths(medidaLineas2);
            
            lineas12 = new PdfPCell(new Phrase("Puesto:", elements3));
            lineas12.setHorizontalAlignment(Element.ALIGN_LEFT);
            lineas12.setBorder(0);
            // Contenido deL PUESTO
            lineas22 = new PdfPCell(new Phrase(puesto_trabajo, elements2));
            lineas22.setBorderColor(BaseColor.WHITE);
            //lineas22.setBorderWidthTop(0);
            //lineas22.setBorderWidthLeft(0);
            //lineas22.setBorderWidthRight(0);
            lineas22.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            
            puesto.addCell(lineas12);
            puesto.addCell(lineas22);
            
            //nombreoficio
            PdfPTable autocantidad = new PdfPTable(2);
            autocantidad.setWidthPercentage(100f);
            PdfPCell nombreCellPre,nombreCellPre2;
            autocantidad.setWidths(medidaLineas4);
            
            nombreCellPre = new PdfPCell(new Phrase("Con base en el oficio de comisión se autoriza la cantidad de:", elements3));
            nombreCellPre.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellPre.setBorderColor(BaseColor.WHITE);
            
            nombreCellPre2 = new PdfPCell(new Phrase("$"+monto, elements2));
            nombreCellPre2.setHorizontalAlignment(Element.ALIGN_CENTER);
            nombreCellPre2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            //nombreCellPre2.setBorderColor(BaseColor.WHITE);
            
            autocantidad.addCell(nombreCellPre);
            autocantidad.addCell(nombreCellPre2);
            
            //nombreoficio
            PdfPTable cantidadletras = new PdfPTable(2);
            cantidadletras.setWidthPercentage(100f);
            PdfPCell nombreCellcanle,nombreCellcanle2;
            float[] medidaLineas44 = {1.60f,1.15f};
            cantidadletras.setWidths(medidaLineas44);
            
            nombreCellcanle = new PdfPCell(new Phrase("", elements3));
            nombreCellcanle.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellcanle.setBorderColor(BaseColor.WHITE);
            
            nombreCellcanle2 = new PdfPCell(new Phrase(nl.convertir(Integer.parseInt(monto)), elements2));
            nombreCellcanle2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            nombreCellcanle2.setBackgroundColor(BaseColor.WHITE);
            nombreCellcanle2.setBorderColor(BaseColor.WHITE);
            
            cantidadletras.addCell(nombreCellcanle);
            cantidadletras.addCell(nombreCellcanle2);
            
            //FECHA
            PdfPTable motivo = new PdfPTable(1);
            motivo.setWidthPercentage(100f);
            PdfPCell lineas14;
            //motivo.setWidths(medidaLineas);
            
            lineas14 = new PdfPCell(new Phrase("Con motivo de:", elements3));
            lineas14.setHorizontalAlignment(Element.ALIGN_LEFT);
            lineas14.setBorder(0);
            
            motivo.addCell(lineas14);
            
            //contenido de motivo
            PdfPTable motivocon = new PdfPTable(1);
            motivocon.setWidthPercentage(100f);
            PdfPCell lineascon;
            
            lineascon = new PdfPCell(new Phrase(actividad, elements2));
            lineascon.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            //lineas14.setBorder(0);
            motivocon.addCell(lineascon);
            
            //nombre estado
            PdfPTable nombreestado = new PdfPTable(1);
            nombreestado.setWidthPercentage(100f);
            PdfPCell nombreCellCon;
            
            nombreCellCon = new PdfPCell(new Phrase("Para llevar a cabo la visita de trabajo en el estado de:", elements3));
            nombreCellCon.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellCon.setBorderColor(BaseColor.WHITE);
            nombreestado.addCell(nombreCellCon);
            
            //nombre estado
            PdfPTable nombreestadocon = new PdfPTable(1);
            nombreestadocon.setWidthPercentage(100f);
            PdfPCell nombreCellCones;
            
            nombreCellCones = new PdfPCell(new Phrase(lugar, elements2));
            nombreCellCones.setHorizontalAlignment(Element.ALIGN_LEFT);
            //nombreCellCon.setBorderColor(BaseColor.WHITE);
            nombreestadocon.addCell(nombreCellCones);
            
            //vehiculo
            PdfPTable vehiculo = new PdfPTable(1);
            vehiculo.setWidthPercentage(100f);
            PdfPCell nombreCellVeh;
            
            nombreCellVeh = new PdfPCell(new Phrase("Vehículo oficial asignado:", elements3));
            nombreCellVeh.setHorizontalAlignment(Element.ALIGN_LEFT);
            nombreCellCon.setBorderColor(BaseColor.WHITE);
            vehiculo.addCell(nombreCellVeh);
            
            //nombre estado
            PdfPTable vehiculocon = new PdfPTable(1);
            vehiculocon.setWidthPercentage(100f);
            PdfPCell nombreCellVehcon;
            
            nombreCellVehcon = new PdfPCell(new Phrase(vehiculoAsignado, elements2));
            nombreCellVehcon.setHorizontalAlignment(Element.ALIGN_LEFT);
            //nombreCellCon.setBorderColor(BaseColor.WHITE);
            vehiculocon.addCell(nombreCellVehcon);
            
            //contenidodelsonsignado
            PdfPTable dias = new PdfPTable(5);
            dias.setWidthPercentage(100f);
            float[] contenidomedidash = {2.15f,2.0f,0.70f,2.0f,3.60f};
            dias.setWidths(contenidomedidash);
            PdfPCell lineas15,lineas25,lineas151,lineas251,lineas152;
            //tableLineas5.setWidths(medidaLineas);
            
            lineas15 = new PdfPCell(new Phrase("Los días:", elements3));
            lineas15.setHorizontalAlignment(Element.ALIGN_CENTER);
            lineas15.setBorder(0);
            // Contenido de la linea
            //lineas25 = new PdfPCell(new Phrase("123 "+"Hora de regreso: "+"123123"+" Horas: "+"222hrs", elements2));
            lineas25 = new PdfPCell(new Phrase(fecha_salida.split("-")[2]+" al "+fecha_llegada.split("-")[2], elements2));
            lineas25.setBorderWidthTop(0);
            lineas25.setBorderWidthLeft(0);
            lineas25.setBorderWidthRight(0);
            lineas25.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            //puntohorall
            
            lineas151 = new PdfPCell(new Phrase("de", elements3));
            lineas151.setHorizontalAlignment(Element.ALIGN_CENTER);
            lineas151.setBorder(0);
            // Contenido de la linea
            //lineas25 = new PdfPCell(new Phrase("123 "+"Hora de regreso: "+"123123"+" Horas: "+"222hrs", elements2));
            lineas251 = new PdfPCell(new Phrase(mes, elements2));
            lineas251.setBorderWidthTop(0);
            lineas251.setBorderWidthLeft(0);
            lineas251.setBorderWidthRight(0);
            lineas251.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            //puntohoras
            lineas152 = new PdfPCell(new Phrase("Pernoctado: "+pernoctado, elements2));
            lineas152.setHorizontalAlignment(Element.ALIGN_CENTER);
            lineas152.setBorder(0);
            
            dias.addCell(lineas15);
            dias.addCell(lineas25);
            dias.addCell(lineas151);
            dias.addCell(lineas251);
            dias.addCell(lineas152);
                        
             //estado
            PdfPTable total = new PdfPTable(2);
            total.setWidthPercentage(100f);
            PdfPCell nombreCellEst,nombreCellEst2;
            float[] medidaLineas5 = {2.10f,0.50f};
            total.setWidths(medidaLineas5);
            
            
            nombreCellEst = new PdfPCell(new Phrase("TOTAL:", elements3));
            nombreCellEst.setHorizontalAlignment(Element.ALIGN_RIGHT);
            nombreCellEst.setBorderColor(BaseColor.WHITE);
            
            nombreCellEst2 = new PdfPCell(new Phrase("$"+monto, elements3));
            nombreCellEst2.setHorizontalAlignment(Element.ALIGN_CENTER);
            nombreCellEst2.setBorderColor(BaseColor.WHITE);
            
            total.addCell(nombreCellEst);
            total.addCell(nombreCellEst2);
            
            
            //area de firma de autorizado
            PdfPTable firmas = new PdfPTable(3);
            firmas.setWidthPercentage(100f);
            float[] medidaCeldasfirmas = {1.85f,1.10f,1.85f};
            firmas.setWidths(medidaCeldasfirmas);
            PdfPCell encabezadosfirmasCell,encabezadosfirmasCell2,encabezadosfirmasCell3;
            
            encabezadosfirmasCell = new PdfPCell(new Phrase("Firma de recibido", elements2));
            encabezadosfirmasCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell.setBorderColor(BaseColor.WHITE);
            encabezadosfirmasCell.setBackgroundColor(BaseColor.WHITE);
            
            encabezadosfirmasCell2 = new PdfPCell(new Phrase("", elements2));
            encabezadosfirmasCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell2.setBorderColor(BaseColor.WHITE);
            
            //encabezadosfirmasCell2.setBorder(0);
            
            encabezadosfirmasCell3 = new PdfPCell(new Phrase("Firma de autorizado", elements2));
            encabezadosfirmasCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell3.setBorderColor(BaseColor.WHITE);
            encabezadosfirmasCell3.setBackgroundColor(BaseColor.WHITE);

            firmas.addCell(encabezadosfirmasCell);
            firmas.addCell(encabezadosfirmasCell2);
            firmas.addCell(encabezadosfirmasCell3);
            
            //area de firma
            PdfPTable firmas2 = new PdfPTable(3);
            firmas2.setWidthPercentage(100f);
            float[] medidaCeldasfirmas2 = {1.85f,1.10f,1.85f};
            firmas2.setWidths(medidaCeldasfirmas2);
            PdfPCell encabezadosfirmasCell22,encabezadosfirmasCell32,encabezadosfirmasCell42;
            
            encabezadosfirmasCell22 = new PdfPCell(new Phrase(empleado, elements2));
            encabezadosfirmasCell22.setHorizontalAlignment(Element.ALIGN_CENTER);
            //encabezadosfirmasCell22.setBorderColor(BaseColor.WHITE);
            encabezadosfirmasCell22.setBorderWidthBottom(0);
            encabezadosfirmasCell22.setBorderWidthLeft(0);
            encabezadosfirmasCell22.setBorderWidthRight(0);
            encabezadosfirmasCell22.setBackgroundColor(BaseColor.WHITE);
            
            encabezadosfirmasCell32 = new PdfPCell(new Phrase("", elements2));
            encabezadosfirmasCell32.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell32.setBorderColor(BaseColor.WHITE);
            //encabezadosfirmasCell32.setBorderWidthBottom(0);
            //encabezadosfirmasCell32.setBorderWidthLeft(0);
            //encabezadosfirmasCell32.setBorderWidthRight(0);
            
            //encabezadosfirmasCell2.setBorder(0);
            
            encabezadosfirmasCell42 = new PdfPCell(new Phrase(responsable, elements2));
            encabezadosfirmasCell42.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell42.setBorderWidthBottom(0);
            encabezadosfirmasCell42.setBorderWidthLeft(0);
            encabezadosfirmasCell42.setBorderWidthRight(0);
            encabezadosfirmasCell42.setBackgroundColor(BaseColor.WHITE);

            firmas2.addCell(encabezadosfirmasCell22);
            firmas2.addCell(encabezadosfirmasCell32);
            firmas2.addCell(encabezadosfirmasCell42);
            
            //area de firmas 2
            PdfPTable firmas3 = new PdfPTable(3);
            firmas3.setWidthPercentage(100f);
            float[] medidaCeldasfirmas3 = {1.85f,1.10f,1.85f};
            firmas3.setWidths(medidaCeldasfirmas3);
            PdfPCell encabezadosfirmasCell221,encabezadosfirmasCell321,encabezadosfirmasCell421;
            
            encabezadosfirmasCell221 = new PdfPCell(new Phrase("COMISIONADO/A", elements2));
            encabezadosfirmasCell221.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell221.setBorderColor(BaseColor.WHITE);
            encabezadosfirmasCell221.setBackgroundColor(BaseColor.WHITE);
            
            encabezadosfirmasCell321 = new PdfPCell(new Phrase("", elements2));
            encabezadosfirmasCell321.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell321.setBorderColor(BaseColor.WHITE);
            
            //encabezadosfirmasCell2.setBorder(0);
            
            encabezadosfirmasCell421 = new PdfPCell(new Phrase(puestoResp, elements2));
            encabezadosfirmasCell421.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadosfirmasCell421.setBorderColor(BaseColor.WHITE);
            encabezadosfirmasCell421.setBackgroundColor(BaseColor.WHITE);

            firmas3.addCell(encabezadosfirmasCell221);
            firmas3.addCell(encabezadosfirmasCell321);
            firmas3.addCell(encabezadosfirmasCell421);
            
            //AGREGAR AL DOCUMENTO ----------------------------------------------------------------

            //Encabezado   Logo | Instituto estatal ....
            doc.add(Head); 
            //nombre
            doc.add(folio);
            doc.add(BLANCO);
            doc.add(fecha);
            //Espacio en blanco
            doc.add(BLANCO);
            //Encabezados
            doc.add(nombreofi);
            //nombre            
            doc.add(BLANCO);
            //doc.add(BLANCO);
            doc.add(nombre);
            //puesto
            doc.add(BLANCO);
            doc.add(puesto);
            //cantidadasignada
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(autocantidad);
            doc.add(cantidadletras);
            //motivo
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(motivo);
            doc.add(motivocon);
            //estado
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(nombreestado);
            doc.add(nombreestadocon);
            //vehiculo
            doc.add(BLANCO);
             doc.add(BLANCO);
            doc.add(vehiculo);
            doc.add(vehiculocon);
            //asignardias
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(dias);
            //Espacio en blanco
            //total
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(total);
            
            //fimas
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(BLANCO);
            
            
            doc.add(firmas);
            
            //fimas
            doc.add(BLANCO);
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
        File fileToPrint = new File("C:\\SIIEEN\\oficiosviaticos\\"+archivo_nombre+".pdf");
		Desktop.getDesktop().open(fileToPrint);
    }
    
    private static void addEmptyLine(Paragraph paragraph, float number) {
        for (float i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
}
