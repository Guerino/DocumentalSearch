package view.test;

import PacoSearch.InvertedIndexFile;
import PacoSearch.common.Document;
import PacoSearch.common.Post;
import PacoSearch.common.Vocabulary;
import PacoSearch.exceptions.PacoSearchException;
import PacoSearch.readers.TXTReader;
import PacoSearch.util.ComparatorWords;
import PacoSearch.util.Tuple;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 * Guerino
 */
public class MainOne {

    public static void main(String[] args) {
        // primer test de lectura del indice conociendo el id de vocabulario
//        testReadInvertexIndexV1();
        
        File pathRoots = new File("index");
        InvertedIndexFile indexFile = new InvertedIndexFile(pathRoots.getAbsolutePath()); 
        
        
        Document doc = indexFile.readDocument(15);        
        System.out.println(doc);
        String str = "";
        //txt,15929,1,2
        List<Post> listPost = indexFile.readInvertdeIndexV2(15929,1); //15929            
             
        Iterator it = listPost.iterator();
         while(it.hasNext()){
             Post p = (Post)it.next();
             str += p.toString() + " ";
//             str += "->";             
//             Document doc = indexFile.readDocument(p.getDoc().getId());        
//             System.out.println(doc.getId() + " - " + doc.getPath());
//             System.out.println(doc.getPath());
         }
//        System.out.println("Leyendo terminos.......");
//        scanFolder(pathRoots, null);
//        
////        System.out.println("Ordenandos terminos.......");
////        Collections.sort(lines, new ComparatorWords());
//        
//        System.out.println("Escribiendo terminos en el archivo....");        
//        writeTempV2(lines);
        
    }
    private static List<String> lines = new LinkedList<>(); 
    private static void scanFolder(File file, List<Document> docsList) {
        try {
            //Si es un directorio
            if(file.isDirectory()){            
                //Listamos todo su contenido
                File files[] = file.listFiles();
                //System.out.println("files.length: " + files.length);
                for (int i = 0; i < files.length; i++){
                    
                    //Si es un directorio
                    if(files[i].isDirectory()){
//                        controller.addTextJTxtConsola("OUT> Analizando: " + files[i].getAbsolutePath() +"\n");  
                        //Llamamos recursivamente al directorio hijo
                        scanFolder(files[i], docsList);
                        //System.out.println(files[i].toString());
                    }else{                        
                        //Ahora a procesar palabras
                        TXTReader txtRd = new TXTReader();  
                        HashMap<String, Integer> terms = txtRd.read(files[i]);
                        //Archivo temporal con las palabras y sus frecuencias por documento
                        if(terms != null){
//                            System.out.println("String: " + terms);
                            
                            Iterator it = terms.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry e = (Map.Entry)it.next();
                                String strWord = e.getKey().toString();
                                int tf = Integer.parseInt(e.getValue().toString());

                                String register = strWord + "," + tf;

                                lines.add(register);
                            } 
                            
                            
                        }                         
                    }  //end else
                    
                 
                }// end for
            } // end if                   
        }
        catch (NullPointerException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class Indexer: NullPointerException"); 
        } catch (Exception ex) {
            PacoSearchException.Exception(ex.getMessage(), "class Indexer: Exception"); 
        }  
    }
    
    public static void writeTempV2(List<String> lines){
        File pathIndex = new File("index").getAbsoluteFile();
        try {           
            File file = new File(pathIndex + "\\vocab.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));   
            
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        
            writer.close();
            lines = null;
 
        }catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: FileNotFoundException");
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: IOException"); 
        }
    }
    private static void binarySearch(String word){
        String q = "hola";
        String indice = "marholakalamaenvaina";
        if(indice.contains(q)){
            System.out.println("Result: " + q);
//            System.out.println("Hashcode: " + q.hashCode());
        }
    }
    
    private static void testReadInvertexIndexV1(){
        File pathIndex = new File("index").getAbsoluteFile();
//        System.out.println("Ordenando el archivo temp.dat....");
//        orderTempFile(pathIndex.getAbsolutePath());
        
//        System.out.println("Creando indice en memoria....");
//        createInvertexIndex(pathIndex.getAbsolutePath());  
        
        InvertedIndexFile indexFile = new InvertedIndexFile(pathIndex.getAbsolutePath());         
        
         int id = 16 ;
         
         String str = "";
         for (int i = 20; i <45; i++) {
             System.out.println("i:" + i);
             Vocabulary voc = indexFile.readVocabulary(i);
             System.out.println(voc);
             List<Post> listPost = indexFile.readInvertdeIndexV2(i,voc.getCant_doc_aparece_nr()); //15929            
             
         Iterator it = listPost.iterator();
         while(it.hasNext()){
             Post p = (Post)it.next();
             str += p.toString() + " ";
//             str += "->";             
//             Document doc = indexFile.readDocument(p.getDoc().getId());        
//             System.out.println(doc.getId() + " - " + doc.getPath());
//             System.out.println(doc.getPath());
         }
         System.out.println(str);   
         str ="";         
         System.out.println("=============================");
        }
    }
    
    
     
    public static void orderTempFile(String path) {
        //Estructura del archivo original: 
        // word | tf | docId  | ...
//        NaturalMergeSort nms = new NaturalMergeSort();
//        nms.ordenar(path, "\\temp.dat");
          DataInputStream dis = null;
          DataOutputStream dos = null;
          try {
            List<Tuple> lista = new LinkedList<>();
            dis = new DataInputStream( new FileInputStream(path + "\\temp.dat"));//Tupla Id: 1.264.148

            while(dis.available() != 0){
                String word = dis.readUTF();
                int docId = dis.readInt();
                int tf = dis.readInt();
                //////////////////////////////////////
                Tuple t = new Tuple(word, docId, tf);                
                lista.add(t);
             }
            
            // Ordenamos alfabeticamente
            Collections.sort(lista, new ComparatorWords());
            //escribimos la lista ordenada de nuevo a disco
            //nueva estructura // word | docId  | tf |...
            dos = new DataOutputStream(new FileOutputStream(path + "\\temp23.dat",false));
            Iterator itr = lista.iterator();
            while(itr.hasNext()){
                Tuple t = (Tuple)itr.next();               
                //Grabamos
                dos.writeUTF(t.getWord());
                dos.writeInt(t.getDocId());
                dos.writeInt(t.getTf());               
            }
                      
            lista = null;
//            this.freeMemory();
    
        }catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: FileNotFoundException"); 
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: IOException"); 
        }  finally {
            try {
                if(dis != null)
                    dis.close();
                
                if(dos != null)
                    dos.close();
            } catch (IOException ex) {
                PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: IOException"); 
            }
        }        
    }
     
    public static void createInvertexIndex(String path){
        //Estructura del archivo: word | docId | tf | ...
        try {                             
           File ft = new File(path + "\\tempx.dat");    
           RandomAccessFile raf = new RandomAccessFile(ft,"r"); 
           List<Post> lista = new LinkedList<>();
           List<Vocabulary> listVoc = new LinkedList<>();
           boolean flag = true;
           String wordAnterior = "";
           String word = "";
           int vocId = 0;
           int nr = 0;
           int mayorTf = 0;
           //Bucle principal que recorre el temp.dat
           while(raf.getFilePointer() < raf.length())
           {                              
                while(flag) //bucle que recorre una palabra y sus doc|tf asociados
                {             
                    //es necesario este break, porque si no da error "null"
                    if(raf.getFilePointer() == raf.length()) break;

                    long ptrWord = raf.getFilePointer();
                    word = raf.readUTF(); 
                    int docId = raf.readInt();
                    int tf = raf.readInt();
                
                    if(wordAnterior.equals("")){
                        wordAnterior = word;
                        vocId++;
                    }                    
                    if(!word.equals(wordAnterior)) {
//                        System.out.println("word anterior: " + wordAnterior + ", word nueva: " + word);
                        flag = false; 
                        raf.seek(ptrWord);
                        break;
                    }                   
                    nr++;
                    System.out.println("vocId: " + vocId + " - word: " + word + " - docId: " + docId + " - tf: " + tf);
                    lista.add(new Post(docId, tf));      
                }                
//                System.out.println("vocId: " + vocId + " - word: " + word);
                System.out.println("Cambio de palabra......");
                
                Vocabulary voc = new Vocabulary();
                voc.setId(vocId);
                voc.setWord(word); 
                voc.setCant_doc_aparece_nr(nr);
                voc.setMax_veces_tf(mayorTf);
                listVoc.add(voc);       
             
                //Reinicializamos variables y objetos
                wordAnterior = "";
                flag = true;
                nr = 0;
                lista = new LinkedList<>();                
           }         
           raf.close();
           
           
           
           lista = null;
           listVoc = null;
        }
        catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: FileNotFoundException");
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: IOException"); 
        }       
    }
}
