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
public class CrearUsuarioPDF {
    private float multa,infra,faltante,prestamo,t1,comision,bono,sueldo,t2,totalPaga,horaExtra,horaFalta;
    private String fechaPago,fechaIni,fechaFin, trab;
    private File directorio;
    private File archivo;
    enviarPDF ob;
    private String path, archivo_nombre;
  //  private infoTicket info;
    private Vector ventaTotal;
    private Vector productos;
  //  private ObtenerProductos obtener_productos;
    
    public CrearUsuarioPDF(){
    //    obtener_productos = new ObtenerProductos();
        path = "C:\\SIIEEN\\comprobantes\\";
        directorio = new File(path);
    //    info = new infoTicket();
        ob = new enviarPDF();
        if (directorio.exists()) {
            archivo_nombre = "nomina_" + ".pdf";
            archivo = new File(directorio, archivo_nombre);
        } else {
            directorio.mkdir();
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
    private static Font Mex = new Font(Font.FontFamily.HELVETICA, 20,
            Font.NORMAL);

    private static Font codigo = new Font(Font.FontFamily.COURIER, 12,
            Font.NORMAL);
    
     private static Font catFont = new Font(Font.FontFamily.HELVETICA, 12,
            Font.BOLD);
    private static Font elements = new Font(Font.FontFamily.COURIER, 10,
            Font.NORMAL);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
            Font.BOLD);
    private static Font subFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.BOLD);
    private static Font smallBold0 = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.NORMAL);
    private static Font smallBold3 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);

    private static Font smallBold2 = new Font(Font.FontFamily.HELVETICA, 10,
            Font.NORMAL);
    
    private static Font elements2 = new Font(Font.FontFamily.COURIER, 8,
            Font.NORMAL);
    
    
    
    
    
    public void createTicket(String pdfFilename,String nombre,String area,String puesto,String usuario, String contrasena, int res,String fecha, String hora)throws DocumentException {
        
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
            image.scaleAbsoluteWidth(75f);
            image.scaleAbsoluteHeight(50f);
            PdfPCell LogoCell = new PdfPCell(image, false);

            LogoCell.setBorderColor(BaseColor.WHITE);

            Head.addCell(LogoCell);
            PdfPCell Titulo = new PdfPCell(new Phrase("Instituto Estatal Electoral de Nayarit", Mex));
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

            //Esqueleto firma
            PdfPTable firma = new PdfPTable(1);
            PdfPCell firmaCell;
            firmaCell = new PdfPCell(new Phrase("_______________________________________________\n\nFirma de conformidad.", subFont2));
            firmaCell.setBorderColor(BaseColor.WHITE);
            firmaCell.setHorizontalAlignment(ALIGN_CENTER);
            firma.addCell(firmaCell);

            //Pie de pagina,   pagina 1 de 1
            //ColumnText.showTextAligned(docWriter.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Pagina " + docWriter.getPageNumber() + " de " + docWriter.getCurrentPageNumber()), 300, 20, 0);
            
            PdfPTable proceso = new PdfPTable(1);
            
            PdfPCell tituloproceso = new PdfPCell(new Phrase("Acuse de Recibo dé Cuenta de Usuario", subFont2));
            proceso.setHorizontalAlignment(ALIGN_CENTER);
            tituloproceso.setHorizontalAlignment(ALIGN_CENTER);
            tituloproceso.setBorderColor(BaseColor.WHITE);
            proceso.addCell(tituloproceso);
            
            
            PdfPTable proceso2 = new PdfPTable(1);
            
            PdfPCell tituloproceso2 = new PdfPCell(new Phrase("Datos de la Cuenta de Usuario", subFont2));
            proceso2.setHorizontalAlignment(ALIGN_CENTER);
            tituloproceso2.setHorizontalAlignment(ALIGN_CENTER);
            tituloproceso2.setBorderColor(BaseColor.WHITE);
            proceso2.addCell(tituloproceso2);
            
            PdfPTable empleadoPTable = new PdfPTable(2);
            PdfPTable areaPTable = new PdfPTable(2);
            PdfPTable puestoPTable = new PdfPTable(2);
            PdfPTable usuarioPTable = new PdfPTable(2);
            PdfPTable contraPTable = new PdfPTable(2);
           
            
            PdfPCell empleado1,empleado2,area1,area2,puesto1,puesto2,usuario11,usuario12,contra11,contra12;
            
            empleado1 = new PdfPCell(new Phrase("Empleado: ", subFont));
            empleado2 = new PdfPCell(new Phrase(""+nombre, subFont2));
            
            area1 = new PdfPCell(new Phrase("Área: ", subFont));
            area2 = new PdfPCell(new Phrase(""+area, subFont2));
            
            puesto1 = new PdfPCell(new Phrase("Puesto: ", subFont));
            puesto2 = new PdfPCell(new Phrase(""+puesto, subFont2));
            
            usuario11 = new PdfPCell(new Phrase("Usuario: ", subFont));
            usuario12 = new PdfPCell(new Phrase(""+usuario, subFont2));
            
            contra11 = new PdfPCell(new Phrase("Contraseña:", subFont));
            contra12 = new PdfPCell(new Phrase(""+contrasena, subFont2));

            empleadoPTable.addCell(empleado1);
            empleadoPTable.addCell(empleado2);
            
            areaPTable.addCell(area1);
            areaPTable.addCell(area2);
            
            puestoPTable.addCell(puesto1);
            puestoPTable.addCell(puesto2);
            
            usuarioPTable.addCell(usuario11);
            usuarioPTable.addCell(usuario12);
            
            contraPTable.addCell(contra11);
            contraPTable.addCell(contra12);
            
            PdfPCell cell = new PdfPCell(new Phrase("--------------------------------------------------------------------------------", estilo));
            
            
            PdfPTable midLane = new PdfPTable(1);
           //LINEA A LA MITAD
            midLane.setWidthPercentage(110f);
           
            cell.setBorderColor(BaseColor.WHITE);
            midLane.addCell(cell);
            
            //Texto de conformidad donde se hace responsable
            PdfPTable condiciones = new PdfPTable(1);

            PdfPCell textocondicones = new PdfPCell(new Phrase("Se entiende por usuario la persona que acceda, navegue, utilice o participe en los servicios de este software, que acepta las presentes Condiciones Generales. El uso de este usuario es responsabilidad de la persona a la que se asigna.\nCualquier daño o anomalia causado por este usuario sera completa responsabilidad de la persona asociada a dicho usuario.", smallBold0));
            textocondicones.setHorizontalAlignment(Element.ALIGN_CENTER);
            textocondicones.setVerticalAlignment(Element.ALIGN_MIDDLE);
            condiciones.addCell(textocondicones);
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

            doc.add(empleadoPTable);
            doc.add(areaPTable);
            doc.add(puestoPTable);
            doc.add(usuarioPTable);
            doc.add(contraPTable);

            //Espacio en blanco
            doc.add(BLANCO);

            doc.add(condiciones);
            //Espacio en blanco
            doc.add(BLANCO);
            //Espacio en blanco
            doc.add(BLANCO);

            //Firma de conformidad
            doc.add(firma);

            //COPIA
            doc.add(BLANCO);
            doc.add(BLANCO);
            //LINEA PUNTEADA
            doc.add(midLane);

            doc.add(BLANCO);

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
            doc.add(proceso2);
            //Espacio en blanco
            doc.add(BLANCO);

            doc.add(empleadoPTable);
            doc.add(areaPTable);
            doc.add(puestoPTable);
            doc.add(usuarioPTable);
            doc.add(contraPTable);

            //Espacio en blanco
            doc.add(BLANCO);

            doc.add(condiciones);
            

            archivo_nombre = pdfFilename;
                     
            if (res == 1) {
                imprimir();
            } else {
                JOptionPane.showMessageDialog(null, "Comprobante creado con éxito!", "Información", INFORMATION_MESSAGE);
            }
        } catch (java.io.FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "ERROR: No hay imagen dist\\print\\blogin.png", "Advertencia!", JOptionPane.WARNING_MESSAGE);
        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (doc != null) {
                doc.close();
                System.out.println("PDF****************************************");
                ob.prepararPDF("C:\\SIIEEN\\comprobantes\\" + archivo_nombre + ".pdf","1");
            }
            if (docWriter != null) {
                docWriter.close();
            }
        }
    }
    public void imprimir() throws IOException{
        
        File fileToPrint = new File("C:\\SIIEEN\\comprobantes\\"+archivo_nombre+".pdf");
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
