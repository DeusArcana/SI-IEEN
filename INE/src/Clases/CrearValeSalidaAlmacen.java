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
public class CrearValeSalidaAlmacen {
    private float multa,infra,faltante,prestamo,t1,comision,bono,sueldo,t2,totalPaga,horaExtra,horaFalta;
    private String fechaPago,fechaIni,fechaFin, trab;
    private File directorio;
    private File archivo;
    private String path, archivo_nombre;
  //  private infoTicket info;
    private Vector ventaTotal;
    private Vector productos;
  //  private ObtenerProductos obtener_productos;
    
    public CrearValeSalidaAlmacen(){
    //    obtener_productos = new ObtenerProductos();
        path = "C:\\SIIEEN\\vales\\salida de almacen\\";
        directorio = new File(path);
    //    info = new infoTicket();
        
        if (directorio.exists()) {
            archivo_nombre = "" + ".pdf";
            archivo = new File(directorio, archivo_nombre);
        } else {
            directorio.mkdirs();
            if (directorio.exists()) {
                archivo_nombre = "" +".pdf";
                archivo = new File(directorio, archivo_nombre);
            } else {
                System.out.println("No se pudo crear el directorio");
            }//ifelse
        }//if else
        
        
    }//constructor
    
   

   
    private static Font elements = new Font(Font.FontFamily.COURIER, 10,
            Font.NORMAL);
    private static Font Mex = new Font(Font.FontFamily.HELVETICA, 10,
            Font.BOLD);

    private static Font subFont2 = new Font(Font.FontFamily.HELVETICA, 8,
            Font.NORMAL);
    private static Font subFont3 = new Font(Font.FontFamily.HELVETICA, 8,
            Font.BOLD);

    private static Font elements2 = new Font(Font.FontFamily.HELVETICA, 7,
            Font.NORMAL);
    
    
    
    
    
    public void createTicket(String pdfFilename,String usuario, String contrasena, int res,String fecha, String hora, Vector productos, String empleado)throws DocumentException {
        
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
            Paragraph encabezadoTOP = new Paragraph();

            Paragraph BLANCO = new Paragraph();

            PdfPTable Head = new PdfPTable(2);

            encabezadoTOP.add(new Paragraph("Fecha de creación: " + fecha+" "+hora, elements));

            //Obtener la imagen
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("print/blogin.png");

            PdfPCell LogoCell = new PdfPCell(image, false);
            LogoCell.setBorderColor(BaseColor.WHITE);
          

            Head.addCell(LogoCell);
            PdfPCell Titulo = new PdfPCell(new Phrase("INSTITUTO ESTATAL ELECTORAL DEL ESTADO DE NAYARIT\nVALE\nSALIDA DE ALMACEN:", Mex));
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

  
            PdfPTable proceso = new PdfPTable(1);
            
            PdfPCell tituloproceso = new PdfPCell(new Phrase("Nombre del Usuario Solicitante: "+empleado, subFont2));
            tituloproceso.setBorderColor(BaseColor.WHITE);
            proceso.setHorizontalAlignment(ALIGN_CENTER);
            proceso.addCell(tituloproceso);
            
            // Tabla encabezados Cantidad    Autorizada     Articulo
            PdfPCell encabezado1 = new PdfPCell(new Phrase("Cantidad", subFont2));
            encabezado1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell encabezado2 = new PdfPCell(new Phrase("Autorizó", subFont2));
            encabezado2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell encabezado22 = new PdfPCell(new Phrase("Solicitó", subFont2));
            encabezado22.setBackgroundColor(BaseColor.LIGHT_GRAY);
            PdfPCell encabezado3 = new PdfPCell(new Phrase("Articulo", subFont2));
            encabezado3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            
            encabezado1.setHorizontalAlignment(ALIGN_CENTER);
            encabezado2.setHorizontalAlignment(ALIGN_CENTER);
            encabezado22.setHorizontalAlignment(ALIGN_CENTER);
            encabezado3.setHorizontalAlignment(ALIGN_CENTER);
            
           
            
            PdfPTable Articulos = new PdfPTable(4);
             float[] medidas2 = {0.10f, 0.10f, 0.10f,0.55f};

            // ASIGNAS LAS MEDIDAS A LA TABLA (ANCHO)
            Articulos.setWidths(medidas2);
            
            Articulos.addCell(encabezado1);
            Articulos.addCell(encabezado2);
            Articulos.addCell(encabezado22);
            Articulos.addCell(encabezado3);
            
            //FOR PARA ARTICULOS ----------------------||||||||||||||||||||||||||||||||||||||||||||||
            //DATOS ------------------------------------------------------------
            
            PdfPTable table = new PdfPTable(4);
             float[] tableMedidas = {0.10f, 0.10f, 0.10f,0.55f};
             table.setWidths(tableMedidas);
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
                
                
                
                table.addCell(cell);
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                
            }//for

            
            // ärea de firmas
            PdfPTable tablaFirmas = new PdfPTable(3);
            PdfPCell firma1 = new PdfPCell(new Phrase("Vo.Bo\n\n\n\n\n____________________\nDirección\nAdministrativa", subFont2));
            PdfPCell firma2 = new PdfPCell(new Phrase("Autorizó\n\n\n\n\n____________________\nNombre y Firma del\nResponsable del Area Solicitante", subFont2));
            PdfPCell firma3 = new PdfPCell(new Phrase("Recibió\n\n\n\n"+empleado+"\n____________________\nNombre y Firma", subFont2));
            
            firma1.setBorderColor(BaseColor.WHITE);
            firma2.setBorderColor(BaseColor.WHITE);
            firma3.setBorderColor(BaseColor.WHITE);
            
            firma1.setHorizontalAlignment(ALIGN_CENTER);
            firma2.setHorizontalAlignment(ALIGN_CENTER);
            firma3.setHorizontalAlignment(ALIGN_CENTER);
            
            tablaFirmas.addCell(firma1);
            tablaFirmas.addCell(firma2);
            tablaFirmas.addCell(firma3);
            
           
            
            //AGREGAR AL DOCUMENTO ----------------------------------------------------------------
            doc.add(encabezadoTOP);
            //Espacio en blanco
            doc.add(BLANCO);
            
            //Espacio en blanco
            doc.add(BLANCO);
            //Encabezado   Logo | Instituto estatal ....
            doc.add(Head);
            //Espacio en blanco
            doc.add(BLANCO);
            //TITULO PROCESO
            doc.add(proceso);
            //Espacio en blanco
            doc.add(BLANCO);
            //Tabla contenido ----------------------------
            doc.add(Articulos);
            // PRODUCTOS!!!!!!!********
            doc.add(table);
            //Espacio en blanco
            doc.add(BLANCO);
            //Tabla de firmas -------********
            doc.add(tablaFirmas);
            //Espacio en blanco
            doc.add(BLANCO);
            //Espacio en blanco
            doc.add(BLANCO);
            //COPIAS *********************-----------------
             //--------------------------------------------COPIA-------------------------------------------------------
            doc.add(encabezadoTOP);
            //Espacio en blanco
            doc.add(BLANCO);
            
            //Espacio en blanco
            doc.add(BLANCO);
            //Encabezado   Logo | Instituto estatal ....
            doc.add(Head);
            //Espacio en blanco
            doc.add(BLANCO);
            //TITULO PROCESO
            doc.add(proceso);
            //Espacio en blanco
            doc.add(BLANCO);
            //Tabla contenido ----------------------------
            doc.add(Articulos);
            // PRODUCTOS!!!!!!!********
            doc.add(table);
            //Espacio en blanco
            doc.add(BLANCO);
            //Tabla de firmas -------********
            doc.add(tablaFirmas);
            //Espacio en blanco
  
            
            

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
            }
            if (docWriter != null) {
                docWriter.close();
            }
        }
    }
    public void imprimir() throws IOException{
        File fileToPrint = new File("C:\\SIIEEN\\vales\\salida de almacen\\"+archivo_nombre+".pdf");
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
