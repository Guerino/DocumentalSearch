package PacoSearch;

import PacoSearch.common.Vocabulary;
import PacoSearch.exceptions.PacoSearchException;
import PacoSearch.util.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author
 * Guerino
 */
public class SearchIndexFile {
    private String pathIndex;
    private int searchType;
    public static int BINARY_SEARCH  = 1;
    public static int PATTERN_SEARCH = 2;
    
    public SearchIndexFile(String pathIndex) {
        this.pathIndex = pathIndex;
         this.searchType = SearchIndexFile.BINARY_SEARCH;
    }    

    public SearchIndexFile(String pathIndex, int searchType) {
        this.pathIndex = pathIndex;
        this.searchType = searchType;
    }    
        
    public Map<Integer, Vocabulary> readVocabulary(String query){
        //Estructura del archivo: 
        // word, id, nr, max_tf\n
        Map<Integer, Vocabulary> map = new HashMap<>();
        try {         
            File file = new File(pathIndex + "\\words.dat");     
            //Segun el tipo de busque es el caso que se ejecuta
            switch(this.searchType){
                case 1: {
                    RandomAccessFile raf = new RandomAccessFile(file,"r");
                    map = this.binarySearch(raf, query);
                    raf.close();  
                    break;
                }
                case 2: {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    //buscamos la palabra ingresada por el usuario
                    map = this.patternSearch(br, query);
                    br.close();
                    break;
                }                  
            }                
        }catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: FileNotFoundException");
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: IOException"); 
        }        
        return map;
    }
    
    /**
     * Metodo que ejecuta una busqueda binaria en el archivo de vocabulario
     * @param raf
     * @param query
     * @return
     * @throws IOException 
     */
    private Map<Integer, Vocabulary> binarySearch(RandomAccessFile raf, String query) throws IOException{
        Map<Integer, Vocabulary> map = new HashMap<>();        
        /*
        * because we read the second line after each seek there is no way the
        * binary search will find the first line, so check it first.
        */
        raf.seek(0);
        String line = raf.readLine();        
        String[] w = StringUtils.splitByComa(line);        
        if (w[0].indexOf(query) > 0) {
            /*
             * the start is greater than or equal to the target, so it is what
             * we are looking for.
             */
             int vocId = Integer.parseInt(w[1]);
             //si la palabra no esta en la tabla, agregarla.
             //de esta forma evitamos palabras repetidas
             if(Integer.parseInt(w[3]) > 1) //si el max_tf>1 guardamos su lista de posteo, si no la descartamos
             if(!map.containsKey(new Integer(vocId))){           
                    map.put(vocId, new Vocabulary(vocId, w[0],
                            Integer.parseInt(w[2]), Integer.parseInt(w[3])));
             }  
             
             return map;
        }
        
        long beg = 0;
        long end = raf.length()-1;
        while(beg <= end){
             long mid = beg + (end - beg) / 2;             
             raf.seek(mid);
             raf.readLine();
             String linea = raf.readLine();    //necesitamos volver a leer para leer una palabra completa  
             String[] word = StringUtils.splitByComa(linea);             
             if(linea == null || word[0].compareTo(query) >= 0){
                //si la linea leida es mayor o igual que la query se actualiza el limite
                //inferior y se busca hacia arriva
                end = mid - 1;
             }else{
                 //si no, se actualiza el limite superior y se busca hacia abajo
                beg = mid + 1;
             }               
             
             //preguntamos si la palabra actual coincide con la buscada
             if(word[0].matches(".*"+query+".*") || word[0].indexOf(query) > 0){
                 int vocId = Integer.parseInt(word[1]);
                 //si la palabra no esta en la tabla, agregarla.
                 //de esta forma evitamos palabras repetidas
                 if(Integer.parseInt(word[3]) > 1) //max_tf
                 if(!map.containsKey(new Integer(vocId))){           
                        map.put(vocId, new Vocabulary(vocId, word[0],
                                Integer.parseInt(word[2]), Integer.parseInt(word[3])));
                 }             
             }
        }        
        
        // se sale del bucle cuando el rango es pequeño        
        return map;
    }
    
    /**
     * Metodo que realiza una busqueda secueancial segun el patron de texto ingresado 
     * por el usuario
     * @param br
     * @param query
     * @return
     * @throws IOException 
     */
    private Map<Integer, Vocabulary> patternSearch(BufferedReader br, String query) throws IOException{
        Map<Integer, Vocabulary> map = new HashMap<>();
        String linea = ""; 
        String patron = ".*"+query+".*";
        while((linea = br.readLine()) != null){            
             //Busca patron en el texto
             Matcher matcher = Pattern.compile(patron).matcher(linea);
             //preguntamos si la palabra actual coincide con la buscada           
             while (matcher.find()) {
                 String match = matcher.group();  
                 String[] word = StringUtils.splitByComa(match);  
                 int vocId = Integer.parseInt(word[1]);
                 //si la palabra no esta en la tabla, agregarla.
                 //de esta forma evitamos palabras repetidas
                 if(Integer.parseInt(word[3]) > 1) //max_tf
                 if(!map.containsKey(new Integer(vocId))){           
                        map.put(vocId, new Vocabulary(vocId, word[0],
                                Integer.parseInt(word[2]), Integer.parseInt(word[3])));
                 }
             }
        }      
        return map;
    }
}