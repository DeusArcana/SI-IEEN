/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author oscar
 */
public class enviarFotoPOST {
    

    public void prepararImagenesVehiculo(File[] rutas, String nombre, int contadorImg) {
        String ip = "";
        try {
            //Creamos un archivo FileReader que obtiene lo que tenga el archivo
            FileReader lector = new FileReader("cnfg.ntw");

            //El contenido de lector se guarda en un BufferedReader
            BufferedReader contenido = new BufferedReader(lector);

            ip = contenido.readLine();
        } catch (Exception e) {

        }

        String url = "http://" + ip + ":80/IEEN/subirfotoVehiculo.php";

        for (int i = 0; i < contadorImg; i++) {
            File img = new File(rutas[i].toString());
            FileBody fileBody = new FileBody(img, ContentType.DEFAULT_BINARY);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addTextBody("nombre", nombre+i);
            builder.addTextBody("folder", nombre);
            builder.addPart("imageUploaded", fileBody);
            HttpEntity entity = builder.build();
            try {
                this.sendPost(url, entity);
                //this.jButton1.setEnabled(false);
                // this.contenedor.setIcon(null);
                // contenedor.setText("LA IMAGEN SE SUBIO CORRECTAMENTE");
            } catch (Exception ex) {
                //  this.contenedor.setIcon(null);
                //  contenedor.setText("LA IMAGEN NO SE PUDO SUBIR");
                System.err.println(ex.toString());
            }
        }
    }// preparacion
    
    public void prepararImagenesVehiculoSinFoto(String nombre) {
        String ip = "";
        try {
            //Creamos un archivo FileReader que obtiene lo que tenga el archivo
            FileReader lector = new FileReader("cnfg.ntw");

            //El contenido de lector se guarda en un BufferedReader
            BufferedReader contenido = new BufferedReader(lector);

            ip = contenido.readLine();
        } catch (Exception e) {

        }

        String url = "http://" + ip + ":80/IEEN/subirfotoVehiculo.php";

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addTextBody("folder", nombre);
            HttpEntity entity = builder.build();
            try {
                this.sendPost(url, entity);
                //this.jButton1.setEnabled(false);
                // this.contenedor.setIcon(null);
                // contenedor.setText("LA IMAGEN SE SUBIO CORRECTAMENTE");
            } catch (Exception ex) {
                //  this.contenedor.setIcon(null);
                //  contenedor.setText("LA IMAGEN NO SE PUDO SUBIR");
                System.err.println(ex.toString());
            }
        
    }// preparacion
    
    
    public boolean actualizarFoto(File[] rutas, String nombre, int i) {
        String ip = "";
        boolean res = false;
        try {
            //Creamos un archivo FileReader que obtiene lo que tenga el archivo
            FileReader lector = new FileReader("cnfg.ntw");

            //El contenido de lector se guarda en un BufferedReader
            BufferedReader contenido = new BufferedReader(lector);

            ip = contenido.readLine();
        } catch (Exception e) {

        }

        String url = "http://" + ip + ":80/IEEN/subirfotoVehiculo.php";
        File img = new File(rutas[0].toString());
        FileBody fileBody = new FileBody(img, ContentType.DEFAULT_BINARY);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addTextBody("nombre", nombre + i);
        builder.addTextBody("folder", nombre);
        builder.addPart("imageUploaded", fileBody);
        HttpEntity entity = builder.build();
        try {
            this.sendPost(url, entity);
            res = true;
            //this.jButton1.setEnabled(false);
            // this.contenedor.setIcon(null);
            // contenedor.setText("LA IMAGEN SE SUBIO CORRECTAMENTE");
        } catch (Exception ex) {
            res = false;
            //  this.contenedor.setIcon(null);
            //  contenedor.setText("LA IMAGEN NO SE PUDO SUBIR");

        }
        return res;
    }// preparacion
    
    
    public void prepararImagenesVehiculoActualizar(File[] rutas, String nombre, int contadorImg,int fotosAnteriores) {
        String ip = "";
        try {
            //Creamos un archivo FileReader que obtiene lo que tenga el archivo
            FileReader lector = new FileReader("cnfg.ntw");

            //El contenido de lector se guarda en un BufferedReader
            BufferedReader contenido = new BufferedReader(lector);

            ip = contenido.readLine();
        } catch (Exception e) {

        }

        String url = "http://" + ip + ":80/IEEN/subirfotoVehiculo.php";
        System.out.println("CONTADORES "+contadorImg+"   "+fotosAnteriores);
        for (int i = 0; contadorImg < fotosAnteriores; i++) {

            File img = new File(rutas[i].toString());
            FileBody fileBody = new FileBody(img, ContentType.DEFAULT_BINARY);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addTextBody("nombre", nombre + fotosAnteriores);
            fotosAnteriores++;
            builder.addTextBody("folder", nombre);
            builder.addPart("imageUploaded", fileBody);
            HttpEntity entity = builder.build();

            try {
                this.sendPost(url, entity);
                //this.jButton1.setEnabled(false);
                // this.contenedor.setIcon(null);
                // contenedor.setText("LA IMAGEN SE SUBIO CORRECTAMENTE");
            } catch (Exception ex) {
                //  this.contenedor.setIcon(null);
                //  contenedor.setText("LA IMAGEN NO SE PUDO SUBIR");
                System.err.println(ex.toString());
            }
        }
    }// preparacion
    
    public void prepararImagenesVehiculoActualizarNuevasfotos(File[] rutas, String nombre, int contadorImg) {
        String ip = "";
        try {
            //Creamos un archivo FileReader que obtiene lo que tenga el archivo
            FileReader lector = new FileReader("cnfg.ntw");

            //El contenido de lector se guarda en un BufferedReader
            BufferedReader contenido = new BufferedReader(lector);

            ip = contenido.readLine();
        } catch (Exception e) {

        }

        String url = "http://" + ip + ":80/IEEN/subirfotoVehiculo.php";
        System.out.println("CONTADORES "+contadorImg+"   ");
        for (int i = 0; i < contadorImg; i++) {

            File img = new File(rutas[i].toString());
            FileBody fileBody = new FileBody(img, ContentType.DEFAULT_BINARY);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addTextBody("nombre", nombre + i);
            builder.addTextBody("folder", nombre);
            builder.addPart("imageUploaded", fileBody);
            HttpEntity entity = builder.build();

            try {
                this.sendPost(url, entity);
                //this.jButton1.setEnabled(false);
                // this.contenedor.setIcon(null);
                // contenedor.setText("LA IMAGEN SE SUBIO CORRECTAMENTE");
            } catch (Exception ex) {
                //  this.contenedor.setIcon(null);
                //  contenedor.setText("LA IMAGEN NO SE PUDO SUBIR");
                System.err.println(ex.toString());
            }
        }
    }// preparacion
    
    public void prepararImagenesInventario(File[] rutas, String nombre, int contadorImg) {
        String ip = "";
        try {
            //Creamos un archivo FileReader que obtiene lo que tenga el archivo
            FileReader lector = new FileReader("cnfg.ntw");

            //El contenido de lector se guarda en un BufferedReader
            BufferedReader contenido = new BufferedReader(lector);

            ip = contenido.readLine();
        } catch (Exception e) {

        }

        String url = "http://" + ip + ":80/IEEN/subirfotoInventario.php";

        for (int i = 0; i < contadorImg; i++) {
            File img = new File(rutas[i].toString());
            FileBody fileBody = new FileBody(img, ContentType.DEFAULT_BINARY);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addTextBody("nombre", nombre+i);
            builder.addTextBody("folder", nombre);
            builder.addPart("imageUploaded", fileBody);
            HttpEntity entity = builder.build();
            try {
                this.sendPost(url, entity);
                //this.jButton1.setEnabled(false);
                // this.contenedor.setIcon(null);
                // contenedor.setText("LA IMAGEN SE SUBIO CORRECTAMENTE");
            } catch (Exception ex) {
                //  this.contenedor.setIcon(null);
                //  contenedor.setText("LA IMAGEN NO SE PUDO SUBIR");
                System.err.println(ex.toString());
            }
        }
    }// preparacion
    
    private void sendPost(String url, HttpEntity entity) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(url);

        post.setEntity(entity);

        HttpResponse response = client.execute(post);

        int responseCode = response.getStatusLine().getStatusCode();

        System.out.println("URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());
    }
    
}// Class
