package PacoSearch.readers;

import PacoSearch.interfaces.Reader;
import PacoSearch.util.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 * 1- Abre un archivo de texto y devuelve cada una de sus palabras
 * teniendo en cuenta que una palabra esta separada por un espacio
 * y no puede tener menos de tres caracteres seguidos
 * @author
 * Guerino
 */
public class TXTReader implements Reader{
    private String encoding;
//    private int contPalabras;
    
    public TXTReader() {
        this.encoding = "windows-1252";
    }
    
    /**
     * Devuelve un Document
     * con la palabra y su correspondiente frecuencia en el texto
     * @param file file to read
     * @return HashMap<String, Integer>
     */
    @Override
    public HashMap<String, Integer> read(File file) {       
        BufferedReader docFile;
	HashMap<String, Integer> terms = null;        
	try {
            //Detectamos la codificacion del archivo
            //String encoding = FileUtils.readFile(file.getAbsolutePath());      
            //System.out.println("File: " + f.toString());           
            docFile = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath()), encoding));
            //new BufferedReader(new FileReader(f) );
            if (file.exists()) {
                String line = docFile.readLine();
                terms = new HashMap<>();
                while (line != null) {
                    //Separo la linea obtenida
                    String palabras[] = StringUtils.split(line);
                    for (int i = 0; i < palabras.length; i++) {
                        String word = palabras[i].trim();
                         //Si son mas de dos caracteres seguidos, lo tomo como palabra relevante
                        if (word.length() > 2) {
                            Integer value = terms.get(word);                            
                            //Si no existe en la tabla, le ponemos un 1, si no incrementamos el contador
                            if (value == null)
                                value = new Integer(1);
                            else
                                value++;                            
                            //System.out.println("Word: " + palabras[i].trim() + " - tf: " + value );
                            //guardar la palabra en la tabla hash
                            terms.put(word, value);                              
                        }
                    }

                    //Leo la siguiente linea
                    line = docFile.readLine();
                } //end while
                
                //Agregamos el nombre de archivo al vocabulario
                String fullName = file.getName().toLowerCase().trim();               
                terms.put(fullName, new Integer(1));  
                
                String name = fullName.substring(0,fullName.lastIndexOf("."));
                terms.put(name, new Integer(1));
                
                String ext = fullName.substring(fullName.indexOf("."), fullName.length());
                terms.put(ext, new Integer(1));
                
                String ext2 = fullName.substring(fullName.lastIndexOf(".")+1, fullName.length());
                terms.put(ext2, new Integer(1));                
               
         }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error FileNotFoundException", JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error IOException", JOptionPane.WARNING_MESSAGE);
        }
        
        if (terms.isEmpty()) return null;        
        
        return terms;        
    }
      
 
}
