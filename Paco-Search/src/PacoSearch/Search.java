package PacoSearch;

import PacoSearch.common.Document;
import PacoSearch.common.Post;
import PacoSearch.common.Vocabulary;
import PacoSearch.util.ComparatorNr;
import PacoSearch.util.StringUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 * Guerino
 */
public class Search {
    private String query;
    private String pathIndex;
    private int totalMatches;
    private int searchType;

    public Search() {
        this.query = "";
        this.pathIndex = "";
        this.totalMatches = 0;
        this.searchType = 1;
    }

    public Search(String pathIndex, int searchType) {
        this.query = "";
        this.pathIndex = pathIndex;
        this.totalMatches = 0;
        this.searchType = searchType;
    }
    
    public Search(String query, String pathIndex, int searchType) {
        this.query = query;
        this.pathIndex = pathIndex;
        this.totalMatches = 0;
        this.searchType = searchType;
    }

    public int getTotalMatches() {
        return totalMatches;
    }  
    
    // buscar la lista de posteo de cada palabra ingresada por el usuario
    public List<Document> search(String query){
        List<Document> listDoc = new LinkedList<>();                
        SearchIndexFile sif = new SearchIndexFile(this.pathIndex, searchType);
        //el usuario puede ingresar mas de una palabra
        //ej: rara vez miento zurita            
        String[] word = StringUtils.split(query);    
        Map<Integer, Vocabulary> vocabulary = new HashMap<>();
        for (int i = 0; i < word.length; i++) {
//            if(word[i].length() > 2) { // si es mayor a 2 caracteres
                //armamos el mapa completo de vocabulario por cada termino ingresado por el usuario
                Map<Integer, Vocabulary> map = sif.readVocabulary(word[i]);        
                vocabulary.putAll(map);
//            }
        }
                
        List<Vocabulary> listVoc = this.mapToList(vocabulary);
        // Ordenamos de menor a mayor segun el nr
        Collections.sort(listVoc, new ComparatorNr());
        
        //2da parte: leer, filtrar documentos repetidos, aumentarlos en el ranking(en
        //el caso de que aparezcan mas de una vez)
        //Mapa para almacenar los documentos por cada termino con mayor similitud
        Map<Document, Integer> docRank = this.documentRank(listVoc);
        //ahora armar la lista de mayor a menor score(puntaje)
        Map map = this.sortByValue(docRank);
              
        Iterator iter = map.keySet().iterator(); 
        while(iter.hasNext()){         
            Object key = iter.next();
            Document docu = (Document)key;
            Integer ranking = (Integer)map.get(key);
            listDoc.add(docu);
        }        
        
        return listDoc;
    }  
    
    private List<Vocabulary> mapToList(Map<Integer, Vocabulary> vocabulary){
        List<Vocabulary> listVoc = new LinkedList<>();
        // Convertimos el map a list para poder ordenarlo por nr
        Iterator itr = vocabulary.keySet().iterator(); 
        while(itr.hasNext()){         
            Object key = itr.next();
            Integer vocId = (Integer)key;
            Vocabulary voc = (Vocabulary)vocabulary.get(key);
            // armamos una lista con las palabras
            listVoc.add(voc);           
        }
        return listVoc;        
    }
    
    private Map<Document, Integer> documentRank(List<Vocabulary> listVoc){
        Map<Document, Integer> docRank = new HashMap<>(); 
        //Obtener los R documentos por cada palabra buscada
        InvertedIndexFile indexFile = new InvertedIndexFile(this.pathIndex); 
        Iterator it = listVoc.iterator();  
        Integer score = null; 
        while(it.hasNext())
        {
            Vocabulary voc = (Vocabulary)it.next();           
            List<Post> listPost = indexFile.readInvertdeIndexV2(voc.getId(), voc.getCant_doc_aparece_nr());
            Iterator itp = listPost.iterator();
            while(itp.hasNext()){
                Post p = (Post)itp.next();
                Document doc = indexFile.readDocument(p.getDoc().getId());
                int tf = p.getFrecuenci_tf();

                //Filtrar documentos repetidos por docId y establecer DocumentRank 
                Integer value = docRank.get(doc);
                //cantidad de veces que aparece el mismo documento en la consulta
                if(value == null) {         
                     score = new Integer(1);
                 }else{
                     score++;
                 }                
                 //armamos el ranking de documentos
                 docRank.put(doc,score);
            }      
        }
        return docRank;
    }
    
    private Map sortByValue(Map map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            @Override
             public int compare(Object o1, Object o2) {
                 return ((Comparable) ((Map.Entry)(o2)).getValue())
                                .compareTo(((Map.Entry)(o1)).getValue());
             }
        });

        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
           Map.Entry entry = (Map.Entry)it.next();
           result.put(entry.getKey(), entry.getValue());
        }
        return result;
    } 
    
    //solo para depuracion
    private void mapToString(Map<Integer, Vocabulary>  map){
         Iterator itr = map.keySet().iterator(); 
            while(itr.hasNext()){         
                Object key = itr.next();
                Integer vocId = (Integer)key;
                Vocabulary voca = (Vocabulary)map.get(key);  

                System.out.println(voca);
            }
    }
  
}
