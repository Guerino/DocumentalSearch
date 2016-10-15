package PacoSearch.util;

/**
 *
 * @author
 * Guerino
 */
public class StringUtils {
    
    public static String[] splitByComa(String line) {
            // separar una linea en palabras usando la coma, como separador
            String[] words = line.split(",");
            return words;
    }
    
    public static String[] split(String line) {
            line = removeAccent(line);
            //quitar caracteres que no sean letras y reemplazarlos por espacios
            line = line.replaceAll("[^a-zA-Z]", " ");
            String[] words = line.toLowerCase().split(" ");
            return words;
    }

    public static String removeAccent(String s) {
            s = s.replaceAll("á", "a");
            s = s.replaceAll("é", "e");
            s = s.replaceAll("í", "i");
            s = s.replaceAll("ó", "o");
            s = s.replaceAll("ú", "u");
            //Ahora las mayusculas
            s = s.replaceAll("Á", "A");
            s = s.replaceAll("É", "E");
            s = s.replaceAll("Í", "I");
            s = s.replaceAll("Ó", "O");
            s = s.replaceAll("Ú", "U");

            return s;
    } 
}