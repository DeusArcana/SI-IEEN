package Clases;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {
    public static boolean validarLongitudCadenaAlfanumerica(String txt,int longitud){
        String regex= "([A-Z0-9]){"+longitud+"}";
        Pattern patron= Pattern.compile(regex);//5CD44945F2
        Matcher emparejador = patron.matcher(txt);
        return emparejador.matches();
    }
    
    public static boolean validarLongitudCadenaAlfanumericaRango(String txt,int min,int max){
        String regex= "([A-Z0-9]){"+min+","+max+"}";
        Pattern patron= Pattern.compile(regex);//5CD44945F2
        Matcher emparejador = patron.matcher(txt);
        return emparejador.matches();
    }
    
    public static boolean validarLongitudCantidadEntera(String txt,int longitud){
        String regex= "([0-9]){"+longitud+"}";
        Pattern patron= Pattern.compile(regex);//5CD44945F2
        Matcher emparejador = patron.matcher(txt);
        return emparejador.matches();
    }
    
    public static boolean validarLongitudCantidadEnteraRango(String txt,int min,int max){
        String regex= "([0-9]){"+min+","+max+"}";
        Pattern patron= Pattern.compile(regex);//5CD44945F2
        Matcher emparejador = patron.matcher(txt);
        return emparejador.matches();
    }
    
    public static boolean validarLongitudCadenaMinusculasyMayusculas(String txt,int longitud){
        String regex= "([A-Za-z]){"+longitud+"}";
        Pattern patron= Pattern.compile(regex);//5CD44945F2
        Matcher emparejador = patron.matcher(txt);
        return emparejador.matches();
    }
    
    public static boolean validarLongitudCadenaMinusculasyMayusculasRango(String txt,int min,int max){
        String regex= "([A-Za-z]){"+min+","+max+"}";
        Pattern patron= Pattern.compile(regex);//5CD44945F2
        Matcher emparejador = patron.matcher(txt);
        return emparejador.matches();
    }
    
    public static boolean validarLongitudCadenaSoloMayusculas(String txt,int longitud){
        String regex= "([A-Z]){"+longitud+"}";
        Pattern patron= Pattern.compile(regex);//5CD44945F2
        Matcher emparejador = patron.matcher(txt);
        return emparejador.matches();
    }
    
    public static boolean validarLongitudCadenaSoloMayusculas(String txt,int min,int max){
        String regex= "([A-Z]){"+min+","+max+"}";
        Pattern patron= Pattern.compile(regex);//5CD44945F2
        Matcher emparejador = patron.matcher(txt);
        return emparejador.matches();
    }
    
    public static boolean validarLongitudCadenaSoloMinusculas(String txt,int longitud){
        String regex= "([a-z]){"+longitud+"}";
        Pattern patron= Pattern.compile(regex);//5CD44945F2
        Matcher emparejador = patron.matcher(txt);
        return emparejador.matches();
    }
    
    public static boolean validarLongitudCadenaSoloMinusculasRango(String txt,int min,int max){
        String regex= "([a-z]){"+min+","+max+"}";
        Pattern patron= Pattern.compile(regex);//5CD44945F2
        Matcher emparejador = patron.matcher(txt);
        return emparejador.matches();
    }
    
    public static boolean validarSiCadenaVacia(String txt){
        return txt.isEmpty();
    }
    
    public static boolean validarClave_añadirInventarioNormal(String txt){
        String regex= "CMP([0-9]{8})";
        Pattern patron=Pattern.compile(regex);//CMP00000001
        Matcher emparejador = patron.matcher(txt);
        return emparejador.matches();
    }
        
    public static boolean validarNoSerie_añadirInventarioNormal(String txt){
        String regex= "([A-Z0-9]){10}";
        Pattern patron= Pattern.compile(regex);;//5CD44945F2
        Matcher emparejador = patron.matcher(txt);
        return emparejador.matches();
    }
    
    public static boolean validarNombres_añadirUsuario(String txt){
        String regex="[A-Z]([a-z]{1,9})\u0020[A-Z]([a-z]{1,9})|[A-Z]([a-z]{1,19})";
        Pattern patron= Pattern.compile(regex);
        Matcher emparejador = patron.matcher(txt);
        return emparejador.matches();
    }
    
    
    
    public static void main(String ar[]){
        Validaciones a=new Validaciones();
        //EJEMPLO DE USO
        System.out.println(Validaciones.validarClave_añadirInventarioNormal("5CD44945FZ"));
    }
}
