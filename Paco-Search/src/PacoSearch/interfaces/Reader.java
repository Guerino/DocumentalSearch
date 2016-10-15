package PacoSearch.interfaces;

import java.io.File;
import java.util.HashMap;

/**
 * Interfaz para implementar lectores de archivos
 * en distintos formatos
 * @author
 * Guerino
 */
public interface Reader {
    /**
     * Devuelve un Document
     * con la palabra y su correspondiente frecuencia en el texto
     * @param f file to read
     * @return HashMap<String, Integer>
     */    
    public HashMap<String, Integer> read(File file);
    
    
}
