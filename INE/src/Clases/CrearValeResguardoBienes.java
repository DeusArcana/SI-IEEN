/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author oscar
 */

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

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
 * @author oscar
 */
public class CrearValeResguardoBienes {
    private float multa,infra,faltante,prestamo,t1,comision,bono,sueldo,t2,totalPaga,horaExtra,horaFalta;
    private String fechaPago,fechaIni,fechaFin, trab;
    private File directorio;
    private File archivo;
    private String path, archivo_nombre;
  //  private infoTicket info;
    private Vector ventaTotal;
    enviarPDF ob;
    private Vector productos;
  //  private ObtenerProductos obtener_productos;
    
    public CrearValeResguardoBienes(){
    //    obtener_productos = new ObtenerProductos();
        path = "C:\\SIIEEN\\vales\\resguardo\\";
        directorio = new File(path);
    //    info = new infoTicket();
        ob = new enviarPDF();
        if (directorio.exists()) {
            archivo_nombre = "nomina_" + ".pdf";
            archivo = new File(directorio, archivo_nombre);
        } else {
            directorio.mkdirs();
            if (directorio.exists()) {
                archivo_nombre = "nomina_" +".pdf";
                archivo = new File(directorio, archivo_nombre);
            } else {
                System.out.println("No se pudo crear el directorio");
            }//ifelse
        }//if else
        
        
    }//constructor
    
   

   
    private static Font Mex = new Font(Font.FontFamily.HELVETICA, 10,
            Font.BOLD);


    private static Font subFont2 = new Font(Font.FontFamily.HELVETICA, 8,
            Font.NORMAL);
    private static Font subFont3 = new Font(Font.FontFamily.HELVETICA, 8,
            Font.BOLD);
        
    private static Font elements2 = new Font(Font.FontFamily.HELVETICA, 7,
            Font.NORMAL);
    
    
    
    
    
    public void createTicket(String pdfFilename, int res,String fecha, String hora, 
            Vector productos,String datosempleado, String numeroResguardo)throws DocumentException {
        
        Rectangle pagesize = new Rectangle(250, 14400);
        Document doc = new Document(pagesize);
        PdfWriter docWriter = null;

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

            PdfPCell LogoCell = new PdfPCell(image, false);
            LogoCell.setBorderColor(BaseColor.WHITE);
          

            Head.addCell(LogoCell);
            PdfPCell Titulo = new PdfPCell(new Phrase("INSTITUTO ESTATAL ELECTORAL DEL ESTADO DE NAYARIT\n\nVALE DE RESGUARDO DE BIENES", Mex));
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
            
            //Tablas
            PdfPTable noResguardo = new PdfPTable(3);
            noResguardo.setWidthPercentage(100f);
            PdfPCell noResguardoCell,numero;
            float[] medidaCeldasFecha = {0.55f, 0.55f, 2f};
            noResguardo.setWidths(medidaCeldasFecha);
            
            noResguardoCell = new PdfPCell(new Phrase("No. Resguardo", subFont2));
            noResguardoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            noResguardoCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            numero = new PdfPCell(new Phrase(""+numeroResguardo, subFont2));
            numero.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            noResguardo.addCell(noResguardoCell);
            noResguardo.addCell(numero);
            
            PdfPCell blancoCell2 = new PdfPCell(new Phrase("", subFont2));
            blancoCell2.setBorderWidthBottom(0); 
            blancoCell2.setBorderWidthTop(0);
            blancoCell2.setBorderWidthRight(0);
            noResguardo.addCell(blancoCell2);
            
            //Tablas
            PdfPTable fechaResguardo = new PdfPTable(3);
            fechaResguardo.setWidthPercentage(100f);
            PdfPCell fechaResguardocell,fechacell;
            
            fechaResguardocell = new PdfPCell(new Phrase("Fecha", subFont2));
            fechaResguardocell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            fechacell = new PdfPCell(new Phrase(""+fecha, subFont2));
            fechacell.setHorizontalAlignment(Element.ALIGN_CENTER);
            fechaResguardocell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            fechaResguardo.setWidths(medidaCeldasFecha);
            
            fechaResguardo.addCell(fechaResguardocell);
            fechaResguardo.addCell(fechacell);
            
            PdfPCell blancoCell = new PdfPCell(new Phrase("", subFont2));
            blancoCell.setBorderWidthBottom(0); 
            blancoCell.setBorderWidthTop(0);
            blancoCell.setBorderWidthRight(0);         
            fechaResguardo.addCell(blancoCell);
            
            
           
            //Datos del RESPONSABLE
            //Tablas
            PdfPTable responsable = new PdfPTable(1);
            responsable.setWidthPercentage(100f);
            PdfPCell responsableCell;
            
            responsableCell = new PdfPCell(new Phrase("DATOS DEL RESPONSABLE", subFont3));
            responsableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            responsableCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            responsable.addCell(responsableCell);
            
            PdfPTable datosResponsable = new PdfPTable(4);
            datosResponsable.setWidthPercentage(100f);
            
            PdfPCell datosResponsableCell1,datosResponsableCell2,datosResponsableCell3,datosResponsableCell4;
            float[] medidasResponsable = {0.70f, 2.25f,0.70f, 2.25f};
            datosResponsable.setWidths(medidasResponsable);
            
            // ASIGNAS LAS MEDIDAS A LA TABLA (ANCHO)
            String datos[] = datosempleado.split(",,");
            Head.setWidths(medidaCeldas);
            datosResponsableCell1 = new PdfPCell(new Phrase("Responsable: ", subFont3));
            datosResponsableCell2 = new PdfPCell(new Phrase(""+datos[0], subFont2));
            datosResponsableCell3 = new PdfPCell(new Phrase("Tipo de uso: ", subFont3));
            datosResponsableCell4 = new PdfPCell(new Phrase("Personal", subFont2));
            
            datosResponsableCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            datosResponsableCell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            
            datosResponsable.addCell(datosResponsableCell1);
            datosResponsable.addCell(datosResponsableCell2);
            datosResponsable.addCell(datosResponsableCell3);
            datosResponsable.addCell(datosResponsableCell4);
            
            
            PdfPTable datosResponsable2 = new PdfPTable(4);
            datosResponsable2.setWidthPercentage(100f);
            datosResponsable2.setWidths(medidasResponsable);
            PdfPCell datosResponsableCell12,datosResponsableCell22,datosResponsableCell32,datosResponsableCell42;
            
            datosResponsableCell12 = new PdfPCell(new Phrase("Cargo: ", subFont3));
            datosResponsableCell22 = new PdfPCell(new Phrase(""+datos[2], subFont2));
            datosResponsableCell32 = new PdfPCell(new Phrase("Municipio: ", subFont3));
            datosResponsableCell42 = new PdfPCell(new Phrase(""+datos[3], subFont2));
            
            datosResponsableCell12.setHorizontalAlignment(Element.ALIGN_RIGHT);
            datosResponsableCell32.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            datosResponsable2.addCell(datosResponsableCell12);
            datosResponsable2.addCell(datosResponsableCell22);
            datosResponsable2.addCell(datosResponsableCell32);
            datosResponsable2.addCell(datosResponsableCell42);
            
            
            PdfPTable datosResponsable3 = new PdfPTable(4);
            datosResponsable3.setWidthPercentage(100f);
            datosResponsable3.setWidths(medidasResponsable);
            PdfPCell datosResponsableCell13,datosResponsableCell23,datosResponsableCell33,datosResponsableCell43;
            
            datosResponsableCell13 = new PdfPCell(new Phrase("Área: ", subFont3));
            datosResponsableCell23 = new PdfPCell(new Phrase(""+datos[1], subFont2));
            datosResponsableCell33 = new PdfPCell(new Phrase("Localidad: ", subFont3));
            datosResponsableCell43 = new PdfPCell(new Phrase(""+datos[4], subFont2));
            
            datosResponsableCell13.setHorizontalAlignment(Element.ALIGN_RIGHT);
            datosResponsableCell33.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            datosResponsable2.addCell(datosResponsableCell13);
            datosResponsable2.addCell(datosResponsableCell23);
            datosResponsable2.addCell(datosResponsableCell33);
            datosResponsable2.addCell(datosResponsableCell43);
            
            
            
            //encabezado RELACION MOBILIARIO Y EQUIPO DE COMPUTO
            //Tablas
            PdfPTable encabezado = new PdfPTable(1);
            encabezado.setWidthPercentage(100f);
            PdfPCell encabezadocell;
            
            encabezadocell = new PdfPCell(new Phrase("RELACION DE MOBILIARIO", subFont3));
            encabezadocell.setHorizontalAlignment(Element.ALIGN_CENTER);
            encabezadocell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            
            encabezado.addCell(encabezadocell);
            
            PdfPTable Contenido = new PdfPTable(6);
            Contenido.setWidthPercentage(100f);
            PdfPCell contenido1,contenido2,contenido3,contenido4,contenido5,contenido6;
            
            contenido1 = new PdfPCell(new Phrase("No. Inventario", subFont3));
            contenido2 = new PdfPCell(new Phrase("No. Serie", subFont3));
            contenido3 = new PdfPCell(new Phrase("Descripción", subFont3));
            contenido4 = new PdfPCell(new Phrase("Marca", subFont3));
            contenido5 = new PdfPCell(new Phrase("Modelo", subFont3));
            contenido6 = new PdfPCell(new Phrase("Color", subFont3));
            
            Contenido.addCell(contenido1);
            Contenido.addCell(contenido2);
            Contenido.addCell(contenido3);
            Contenido.addCell(contenido4);
            Contenido.addCell(contenido5);
            Contenido.addCell(contenido6);
            
            PdfPTable firma = new PdfPTable(1);
            PdfPCell firmaCell;
            
            firmaCell = new PdfPCell(new Phrase(datos[0]+"\n_____________________________________\nUsuario Responsable", subFont2));
            firmaCell.setBorderColor(BaseColor.WHITE);
            firmaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            firma.addCell(firmaCell);
            
            
            PdfPTable advertencia = new PdfPTable(1);
            advertencia.setWidthPercentage(100f);
            PdfPCell advertenciaCell;
            
            advertenciaCell = new PdfPCell(new Phrase("NOTA: EN CASO DE RENUNCIA, LICENCIA O CAMBIO DE ADSCRIPCION, SIRVASE HACER ENTREGA DEL EQUIPO A SU CARGO, A FIN DE EVITAR RESPONSABILIDAD POSTERIOR EN EFECTIVO, LA RESPONSABILIDAD DE ESTE RESGUARDO NO FINIQUITA HASTA QUE EL BIEN HAYA SIDO DEVUELTO Y EL ORIGINAL DE ESTE DOCUMENTO SEA CANCELADO.", elements2));
            advertenciaCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            advertencia.addCell(advertenciaCell);
            
            
            //FOR PARA ARTICULOS ----------------------||||||||||||||||||||||||||||||||||||||||||||||
            //DATOS ------------------------------------------------------------
            
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100f);
             //float[] tableMedidas = {0.10f, 0.10f, 0.10f,0.55f};
             //table.setWidths(tableMedidas);
            for (int i = 0; i < productos.size(); i++) {
                
                String temporal[] = productos.get(i).toString().split(",,");
                //Cantidad numero y letra
                PdfPCell cell;
                cell = new PdfPCell(new Phrase("" + temporal[0].toUpperCase(), subFont2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                

                //autorizó
                PdfPCell cell1;

                cell1 = new PdfPCell(new Phrase("" + temporal[1].toUpperCase(), subFont2));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

                //solicitó
                PdfPCell cell2;

                cell2 = new PdfPCell(new Phrase("" +(temporal[2]).toUpperCase(), subFont2));
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                
                //producto
                PdfPCell cell3;

                cell3 = new PdfPCell(new Phrase("" +(temporal[3]).toUpperCase(), subFont2));
                cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
                
                //producto
                PdfPCell cell4;

                cell4 = new PdfPCell(new Phrase("" +(temporal[4]).toUpperCase(), subFont2));
                cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
                
                //producto
                PdfPCell cell5;

                cell5 = new PdfPCell(new Phrase("" +(temporal[5]).toUpperCase(), subFont2));
                cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
                
                
                
                table.addCell(cell);
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
                table.addCell(cell5);
                
            }//for
            
            
            //AGREGAR AL DOCUMENTO ----------------------------------------------------------------

            //Encabezado   Logo | Instituto estatal ....
            doc.add(Head);
            //Espacio en blanco
            doc.add(BLANCO);
            //Tabla 2x2 numero resguardo, fecha
            doc.add(noResguardo);
            doc.add(fechaResguardo);
            //Espacio en blanco
            doc.add(BLANCO);
            doc.add(responsable);
            doc.add(datosResponsable);
            doc.add(datosResponsable2);
            doc.add(datosResponsable3);
            //Espacio en blanco
            doc.add(BLANCO);
            doc.add(encabezado);
            //Tabla de contenido (Encabezado)
            doc.add(Contenido);
            //for para contenido
            doc.add(table);
            //Espacio en blanco
            doc.add(BLANCO);
            //Espacio en blanco
            doc.add(BLANCO);
            doc.add(firma);
            //Espacio en blanco
            doc.add(BLANCO);
            //Espacio en blanco
            doc.add(BLANCO);
            doc.add(advertencia);
            
                       
            archivo_nombre = pdfFilename;

            if (res == 1) {
                imprimir();
            } else {
                JOptionPane.showMessageDialog(null, "Vale creado con éxito!", "Información", INFORMATION_MESSAGE);
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
                System.out.println("PDF****************************************");
                ob.prepararPDF("C:\\SIIEEN\\vales\\resguardo\\" + archivo_nombre + ".pdf","4");
            }
            if (docWriter != null) {
                docWriter.close();
            }
        }
    }
    public void imprimir() throws IOException{
        File fileToPrint = new File("C:\\SIIEEN\\vales\\resguardo\\"+archivo_nombre+".pdf");
		Desktop.getDesktop().open(fileToPrint);
    }
    
    private static void addEmptyLine(Paragraph paragraph, float number) {
        for (float i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
    public void CrearReporteVenta() throws DocumentException {
        try {
            //Ticket
            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(archivo));
            document.open();
//            addContent(document);
            document.close();

            File arc = new File(directorio + "\\" + archivo_nombre);
            //Desktop.getDesktop().print(arc);

        } catch (java.io.FileNotFoundException ax) {
       //     JOptionPane.showMessageDialog(null, "Cierre el archivo con el mismo nombre");
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
//        String pdfFilename = "Ticket.pdf";
//        GenerateBarcodes generateInvoice = new GenerateBarcodes();
//        generateInvoice.createTicket(pdfFilename,"","","","","");
    }//crear ticket
    

}
