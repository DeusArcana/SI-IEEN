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
public class CrearDocumento {
    private float multa,infra,faltante,prestamo,t1,comision,bono,sueldo,t2,totalPaga,horaExtra,horaFalta;
    private String fechaPago,fechaIni,fechaFin, trab;
    private File directorio;
    private File archivo;
    private String path, archivo_nombre;
  //  private infoTicket info;
    private Vector ventaTotal;
    private Vector productos;
  //  private ObtenerProductos obtener_productos;
    
    public CrearDocumento(){
    //    obtener_productos = new ObtenerProductos();
        path = "C:\\SIIEEN\\documentos\\";
        directorio = new File(path);
    //    info = new infoTicket();
        
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
        
        this.multa = multa;
        this.infra = infra;
        this.faltante = faltante;
        this.prestamo = prestamo;
        this.t1 = t1;
        this.comision = comision;
        this.bono = bono;
        this.sueldo = sueldo;
        this.t2 = t2;
        this.totalPaga = totalPaga;
        
        this.fechaPago = fechaPago;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        
        this.trab = trab;
        this.horaExtra = horaExtra;
        this.horaFalta = horaFalta;
    }//constructor
    
   

   
    private static Font numero = new Font(Font.FontFamily.HELVETICA, 40,
            Font.BOLD);

    private static Font estilo = new Font(Font.FontFamily.HELVETICA, 22,
            Font.BOLD);
    private static Font Mex = new Font(Font.FontFamily.HELVETICA, 10,
            Font.BOLD);

    private static Font codigo = new Font(Font.FontFamily.COURIER, 12,
            Font.NORMAL);
    
     private static Font catFont = new Font(Font.FontFamily.HELVETICA, 12,
            Font.BOLD);
    private static Font elements = new Font(Font.FontFamily.COURIER, 10,
            Font.NORMAL);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
            Font.BOLD);
    private static Font subFont2 = new Font(Font.FontFamily.HELVETICA, 8,
            Font.NORMAL);
    private static Font subFont3 = new Font(Font.FontFamily.HELVETICA, 8,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.BOLD);
    private static Font smallBold0 = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.NORMAL);
    private static Font smallBold3 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);

    private static Font smallBold2 = new Font(Font.FontFamily.HELVETICA, 10,
            Font.NORMAL);
    
    private static Font elements2 = new Font(Font.FontFamily.HELVETICA, 7,
            Font.NORMAL);
    
    
    
    
    
    public void createTicket(String pdfFilename, int res,Vector datos,String fecha)throws DocumentException {
        
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
            //RELACION DE INVENTARIO

            Paragraph BLANCO = new Paragraph();
            addEmptyLine(BLANCO, (float) 1);

            PdfPTable Head = new PdfPTable(2);
            Head.setWidthPercentage(100f);

           

            //Obtener la imagen
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("print/blogin.png");

            PdfPCell LogoCell = new PdfPCell(image, false);
            LogoCell.setBorderColor(BaseColor.WHITE);
          
            
            
            PdfPTable noResguardo = new PdfPTable(3);
            noResguardo.setWidthPercentage(100f);
            PdfPCell noResguardoCell,numero;
            float[] medidaCeldasFecha = {0.55f, 0.55f, 2f};
            noResguardo.setWidths(medidaCeldasFecha);
            
            noResguardoCell = new PdfPCell(new Phrase("No. Acta", subFont2));
            noResguardoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            noResguardoCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            numero = new PdfPCell(new Phrase("", subFont2));
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
            
            
            

            
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100f);

            PdfPTable titulos = new PdfPTable(6);
            titulos.setWidthPercentage(100f);

            PdfPCell cellTitulo1,cellTitulo2,cellTitulo3,cellTitulo4,cellTitulo5,cellTitulo6;

            cellTitulo1 = new PdfPCell(new Phrase("No. Inventario", subFont2));
            cellTitulo1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellTitulo1.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            cellTitulo2 = new PdfPCell(new Phrase("No. Serie", subFont2));
            cellTitulo2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellTitulo2.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            cellTitulo3 = new PdfPCell(new Phrase("Descripción", subFont2));
            cellTitulo3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellTitulo3.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            cellTitulo4 = new PdfPCell(new Phrase("Marca", subFont2));
            cellTitulo4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellTitulo4.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            cellTitulo5 = new PdfPCell(new Phrase("Modelo", subFont2));
            cellTitulo5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellTitulo5.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            cellTitulo6 = new PdfPCell(new Phrase("Color", subFont2));
            cellTitulo6.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellTitulo6.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            titulos.addCell(cellTitulo1);
            titulos.addCell(cellTitulo2);
            titulos.addCell(cellTitulo3);
            titulos.addCell(cellTitulo4);
            titulos.addCell(cellTitulo5);
            titulos.addCell(cellTitulo6);

            //float[] tableMedidas = {0.10f, 0.10f, 0.10f,0.55f};
            //table.setWidths(tableMedidas);
            String temporal[] = datos.get(0).toString().split("//");
            //System.out.println("******** "+temporal[0]+"\n***** "+temporal.length);
            for (int i = 0; i < temporal.length; i++) {

                String aux[] = temporal[i].split(",,");
                //Cantidad numero y letra
                PdfPCell cell;
                cell = new PdfPCell(new Phrase("" + aux[0].toUpperCase(), subFont2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                

                //autorizó
                PdfPCell cell1;

                cell1 = new PdfPCell(new Phrase("" + aux[1].toUpperCase(), subFont2));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

                //solicitó
                PdfPCell cell2;

                cell2 = new PdfPCell(new Phrase("" +(aux[2]).toUpperCase(), subFont2));
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                
                //producto
                PdfPCell cell3;

                cell3 = new PdfPCell(new Phrase("" +(aux[3]).toUpperCase(), subFont2));
                cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
                
                //producto
                PdfPCell cell4;

                cell4 = new PdfPCell(new Phrase("" +(aux[4]).toUpperCase(), subFont2));
                cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
                
                //producto
                PdfPCell cell5;

                cell5 = new PdfPCell(new Phrase("" +(aux[5]).toUpperCase(), subFont2));
                cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
                
                 

                table.addCell(cell);
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
                table.addCell(cell5);
                
            }//for
            
            
           
            
            //AGREGAR AL DOCUMENTO ----------------------------------------------------------------

            //for para contenido
            doc.add(noResguardo);
            doc.add(fechaResguardo);
            
            doc.add(BLANCO);
            doc.add(BLANCO);
            doc.add(BLANCO);

            doc.add(titulos);
         
            doc.add(table);
            
            
            
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
        File fileToPrint = new File("C:\\SIIEEN\\documentos\\"+archivo_nombre+".pdf");
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
