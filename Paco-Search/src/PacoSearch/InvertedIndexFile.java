package PacoSearch;

import PacoSearch.common.Document;
import PacoSearch.common.Post;
import PacoSearch.common.Vocabulary;
import PacoSearch.exceptions.PacoSearchException;
import PacoSearch.util.ComparatorTf;
import PacoSearch.util.QuickSortTuple;
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
import java.util.SortedMap;
import java.util.TreeMap;
import view.controllers.IndexPanelController;


/**
 *
 * @author
 * Guerino
 */
public class InvertedIndexFile {
    private String pathIndex;
    private int contTerm=0;
    private List<Tuple> list = null;
    private int docIdActual = 0;
    private IndexPanelController controller; //Controlador de la UI 

    public InvertedIndexFile(String path) {
        this.pathIndex = path;
        this.controller = null;
    }

    public InvertedIndexFile(IndexPanelController controller, String path) {
        this.pathIndex = path;
        this.controller = controller;
    }
        
    public String getPath() {
        return pathIndex;
    }

    public void setPath(String path) {
        this.pathIndex = path;
    }    
    
    private void freeMemory(){
        Runtime rtm = Runtime.getRuntime(); 
        rtm.gc();//liberamos la memoria usada
        rtm.runFinalization();
    }
    
     public int getContTerm() {
        return contTerm;
    }
    
    ///Metodo de escritura para la clase documento
    
    public void writeDocuments(List<Document> docList){
        //Estructura del archivo: ptrStartIndex | docId | path | size |.....| docId | ptrId 
        try {
            //lista para crear el indice principal
            List<Document> listIndex = new LinkedList<>();            
            File fdoc = new File(pathIndex + "\\docs.dat");
            RandomAccessFile raf = new RandomAccessFile(fdoc,"rw");                                  
            
            //Puntero en donde se almacena la direccion del indice
            raf.writeLong(1111);// reservado para el ptrIndex
            
            Iterator it = docList.listIterator();
            while(it.hasNext()){         
                Document doc = (Document)it.next();
                int id = doc.getId();
                long ptrId = raf.getFilePointer();
                                
                raf.writeInt(id);
                raf.writeUTF(doc.getPath());
                raf.writeLong(doc.getSize());
                //Guardamos el id y la posicion de inicio de cada documento, 
                //para ir creando el indice al final del archivo 
                listIndex.add(new Document(id, ptrId));
            }
            
            //Direccion de comienzo del indice principal
            long startIndex = raf.getFilePointer();
            
            Iterator itr = listIndex.listIterator();            
            while(itr.hasNext()){         
                Document doc = (Document)itr.next();
                //Indice principal
                raf.writeInt(doc.getId());
                raf.writeLong(doc.getPtrId());
           }
            
            //Ahora volvemos al principio y guardamos la direccion del indice            
            raf.seek(0);
            raf.writeLong(startIndex);          
            raf.close();
            
            docList = null;
            this.freeMemory();
            
        }catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexFile: FileNotFoundException"); 
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexFile: IOException"); 
        }
    }
        
    ///Metodos de escritura y lectura para la clase vocabulario
    
     /**
     * Mejora del metodo anterior en el que la estructura interna del archivo
     * es la siguiente:
     * word, vocId, nr, tf\n
     * esta estructura esta optimizada para busqueda binaria
     * @param vocList 
     */
    public void writeVocabularyV3(List<Vocabulary> vocList){
        try {
            //lista para crear el indice principal
            List<String> lines = new LinkedList<>();            
            File file = new File(pathIndex + "\\words.dat");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));            
            
            Iterator it = vocList.iterator();
            while(it.hasNext()){         
                Vocabulary voc = (Vocabulary)it.next();
                
                String register = voc.getWord() + "," + voc.getId() + "," 
                        + voc.getCant_doc_aparece_nr() + "," + voc.getMax_veces_tf();
            
                lines.add(register);
            }
            
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        
            writer.close();
            lines = null;
            this.freeMemory();
        }catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: FileNotFoundException");
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: IOException"); 
        }
    }
    
    /**
     * Mejora del metodo anterior en el que la estructura interna del archivo
     * es la siguiente:
     * ptrStartIndex | vocId | nr | tf | ... | word | ptrVocId
     * esta estructura no sirve para busqueda binaria
     * @param vocList 
     */
    public void writeVocabularyV2(List<Vocabulary> vocList){
        //Estructura del archivo: 
        // ptrStartIndex | vocId | nr | tf | ... | word | ptrVocId
        try {
            //lista para crear el indice principal
            List<Vocabulary> listIndex = new LinkedList<>();            
            File fdoc = new File(pathIndex + "\\words.dat");
            RandomAccessFile raf = new RandomAccessFile(fdoc,"rw");
            //Puntero en donde se almacena la direccion del indice
            raf.writeLong(1111);// reservado para el ptrIndex
            
            Iterator it = vocList.iterator();
            while(it.hasNext()){         
                Vocabulary voc = (Vocabulary)it.next();
                int id = voc.getId();
                long ptrId = raf.getFilePointer();
                // a escribir                
                raf.writeInt(id);                
                raf.writeInt(voc.getCant_doc_aparece_nr());
                raf.writeInt(voc.getMax_veces_tf());           
                //Guardamos el id y la posicion de inicio, 
                //para ir creando el indice al final del archivo 
                listIndex.add(new Vocabulary(voc.getWord(), ptrId));
            }
            
            //Direccion de comienzo del indice principal
            long startIndex = raf.getFilePointer();
            //System.out.println("Comienzo del indice: " + startIndex);
            
            Iterator itr = listIndex.listIterator();            
            while(itr.hasNext()){         
                Vocabulary voc = (Vocabulary)itr.next();
                //Indice principal
                raf.writeUTF(voc.getWord());
                raf.writeLong(voc.getPtrId());
           }
            
            //Ahora volvemos al principio y guardamos la direccion del indice            
            raf.seek(0);
            raf.writeLong(startIndex);            
            
            raf.close();
            listIndex = null;
            this.freeMemory();
        }catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: FileNotFoundException");
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: IOException"); 
        }
    }
    
 
    /**
     * Primera version optimizada para acceso directo por id de vocabulario
     * estructura del archivo:
     * ptrStartIndex | vocId | word | nr | tf | ... | vocId | ptrVocId
     * @param vocList 
     */
    public void writeVocabulary(List<Vocabulary> vocList){
        //Estructura del archivo: 
        // ptrStartIndex | vocId | word | nr | tf | ... | vocId | ptrVocId
        try {
            //lista para crear el indice principal
            List<Vocabulary> listIndex = new LinkedList<>();            
            File fdoc = new File(pathIndex + "\\words.dat");
            RandomAccessFile raf = new RandomAccessFile(fdoc,"rw");                                  

            //Puntero en donde se almacena la direccion del indice
            raf.writeLong(1111);// reservado para el ptrIndex
            
            Iterator it = vocList.iterator();
            while(it.hasNext()){         
                Vocabulary voc = (Vocabulary)it.next();
                int id = voc.getId();
                long ptrId = raf.getFilePointer();
                                
                raf.writeInt(id);
                raf.writeUTF(voc.getWord());
                raf.writeInt(voc.getCant_doc_aparece_nr());
                raf.writeInt(voc.getMax_veces_tf());                
                //Guardamos el id y la posicion de inicio, 
                //para ir creando el indice al final del archivo 
                listIndex.add(new Vocabulary(id, ptrId));
            }
            
            //Direccion de comienzo del indice principal
            long startIndex = raf.getFilePointer();
            //System.out.println("Comienzo del indice: " + startIndex);
            
            Iterator itr = listIndex.listIterator();            
            while(itr.hasNext()){         
                Vocabulary voc = (Vocabulary)itr.next();
                //Indice principal
                raf.writeInt(voc.getId());
                raf.writeLong(voc.getPtrId());
           }
            
            //Ahora volvemos al principio y guardamos la direccion del indice            
            raf.seek(0);
            raf.writeLong(startIndex);            
            
            raf.close();
            listIndex = null;
            this.freeMemory();
        }catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: FileNotFoundException");
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: IOException"); 
        }
    }
    
    /**
     * lee un vocabulario a partir de su id
     * el archivo se tiene que haber escrito con writeVocabulary
     * @param vocId
     * @return Vocabulary
     */
    public Vocabulary readVocabulary(int vocId){
        //Estructura del archivo: 
        // ptrStartIndex | vocId | word | nr | tf | ... | vocId | ptrVocId 
        if(vocId == 0) vocId = 1;
        Vocabulary voc = new Vocabulary();        
        try {         
            File file = new File(pathIndex + "\\words.dat");
            RandomAccessFile raf = new RandomAccessFile(file,"r");
            raf.seek(0);
            //Puntero en donde se almacena la direccion del indice principal del archivo words.dat
            long ptrStartIndex = raf.readLong();
            //System.out.println("ptrStartIndex: " + ptrStartIndex);       
            //id medido desde el comienzo del indice
            raf.seek((vocId-1)*12 + ptrStartIndex);            
            int id = raf.readInt();
            long ptr = raf.readLong();  
            
            raf.seek(ptr);//nos posicionamos y leemos el vocabulario
            voc.setId(raf.readInt());
            voc.setWord(raf.readUTF());
            voc.setCant_doc_aparece_nr(raf.readInt());
            voc.setMax_veces_tf(raf.readInt());
            
            raf.close();            
        }catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: FileNotFoundException");
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: IOException"); 
        }
        
        return voc;
    }
   
   // Metodos para el archivo temporal
    
    public void writeTempFile(int docId, HashMap<String, Integer> terms) {
        //Estructura del archivo: 
        // word | docId | tf | ...
        try {                       
            File file = new File(pathIndex + "\\temp.dat");            
            RandomAccessFile raf = new RandomAccessFile(file,"rw");            
            //Si ya existe, le vamos agregando contenido al final del archivo
            if(file.exists()){ 
                raf.seek(raf.length());
            }
            
            list = new LinkedList<>();
            Iterator it = terms.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry)it.next();
                String strWord = e.getKey().toString();
                int tf = Integer.parseInt(e.getValue().toString());
                // Las palabras con tf=1 no las descarto por considerarlas poco relevantes para el documento
//                if(tf > 1)
                {                    
                    list.add(new Tuple(strWord,docId,tf));
                    this.contTerm++;
                }
            }         
       
            //pre-ordenamiento de la lista de palabras para cada docId
            //es bueno hacer esto, ya que reduce casi 5 veces el tiempo, durante la ordenacion final
//            Collections.sort(list, new ComparatorWords());
            Tuple[] vt = list.toArray(new Tuple[list.size()]);
            list = null;
            QuickSortTuple q = new QuickSortTuple();             
            q.sort(vt);
                            
            for (int i = 0; i < vt.length; i++) {
                //Grabamos
                raf.writeUTF(vt[i].getWord());
                raf.writeInt(vt[i].getDocId());
                raf.writeInt(vt[i].getTf());
            }
                        
            raf.close(); 
            this.freeMemory();                       
        }catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: FileNotFoundException");
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: IOException"); 
        }
    }
    
    public void orderTempFile() {
        //Estructura del archivo: 
        // word | tf | docId  | ...
//        NaturalMergeSort nms = new NaturalMergeSort();
//        nms.ordenar(path, "\\temp.dat");
          DataInputStream dis = null;
          DataOutputStream dos = null;
          if(controller !=null){
              controller.setJPbInvidualIndeterminate(true);
          }
          try {           
            List<Tuple> lista = new LinkedList<>();
            dis = new DataInputStream( new FileInputStream(pathIndex + "\\temp.dat"));//Tupla Id: 1.264.148
            if(controller !=null)
                    controller.addTextJTxtConsolaOut("Cargando datos a la lista en memoria...");
               
            while(dis.available() != 0){                
                String word = dis.readUTF();
                int docId = dis.readInt();
                int tf = dis.readInt();
                //////////////////////////////////////
                Tuple t = new Tuple(word, docId, tf);                
                lista.add(t);
             }            
                //vt size: 4.921.060
                //Lista size: 4921060
                //Lista 1 size: 2460530
                //Lista 2 size: = 2460530
            
                //vt size: 5646548
                //Lista size: 5646548
                //Lista 1 size: 2823274
                //Lista 2 size: = 2823274
             Tuple[] vt = lista.toArray(new Tuple[lista.size()]);
             lista = null;
             QuickSortTuple q = new QuickSortTuple();
//             System.out.println("vt size: " + vt.length);
             if(controller !=null)
                controller.addTextJTxtConsolaOut("Ordenando archivo temp.dat en memoria...");
             
                q.sort(vt);
          
            if(controller !=null)
               controller.addTextJTxtConsolaOut("Escribiendo archivo temp.dat en disco...");
            
            //escribimos la lista ordenada de nuevoa disco
            dos = new DataOutputStream(new FileOutputStream(pathIndex + "\\temp.dat",false));            
            
            if(controller !=null){
                controller.setJPbInvidualIndeterminate(false);
                 controller.setJPbIndividualRangeValues(0, 100);               
            }
                
            for (int i = 0; i < vt.length; i++) {
                //Grabamos
                dos.writeUTF(vt[i].getWord());
                dos.writeInt(vt[i].getDocId());
                dos.writeInt(vt[i].getTf());
                controller.setJPbIndividualValue((i*100)/vt.length);
            }
            
            if(controller !=null){
                 controller.setJPbIndividualValue(0);
                 controller.setJPbInvidualIndeterminate(true);
            }
            this.freeMemory();    
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
    
    public void orderTempFileV2() {
        //Estructura del archivo: 
        // word | tf | docId  | ...
//        NaturalMergeSort nms = new NaturalMergeSort();
//        nms.ordenar(path, "\\temp.dat");
          
          try {           
                List<Tuple> lista = new LinkedList<>();
                File file = new File(pathIndex + "\\temp.dat");            
                RandomAccessFile raf = new RandomAccessFile(file,"r");
                if(controller !=null){
                   controller.addTextJTxtConsolaOut("Cargando datos a la lista en memoria..."); 
                   controller.setJPbInvidualIndeterminate(false);
                   controller.setJPbIndividualRangeValues(0, 100);               
                }  
                while(raf.getFilePointer() < raf.length()) {                           
                    String word = raf.readUTF();
                    int docId = raf.readInt();
                    int tf = raf.readInt();
                    //////////////////////////////////////
                    Tuple t = new Tuple(word, docId, tf);                
                    lista.add(t);
                    if(controller !=null){
                        int pos = Integer.parseInt(String.valueOf((raf.getFilePointer()*100)/raf.length()));
                        controller.setJPbIndividualValue(pos);
                    }
                }

                raf.close();
                boolean delete = file.delete();
                if(controller !=null){
                  controller.setJPbInvidualIndeterminate(true);         
            }
                
            List<Tuple> list1 = lista.subList(0, lista.size()/2);
            Tuple[] vt1 = list1.toArray(new Tuple[list1.size()]);

            List<Tuple> list2 = lista.subList(lista.size()/2+1, lista.size());
            Tuple[] vt2 = list2.toArray(new Tuple[list2.size()]);
                       
            int size = vt1.length + vt2.length;     
            Tuple[] vt = new Tuple[size];            
            //copiamos una array al otro
            System.arraycopy(vt1, 0, vt, 0, vt1.length); // de 10!             
            System.arraycopy(vt2, 0, vt, vt1.length, vt2.length);
            lista = null;
                        
            QuickSortTuple q = new QuickSortTuple();
            if(controller !=null)
                controller.addTextJTxtConsolaOut("Ordenando archivo temp.dat en memoria...");
             
            q.sort(vt);          
            if(controller !=null)
               controller.addTextJTxtConsolaOut("Escribiendo archivo temp.dat en disco...");

            //escribimos la lista ordenada de nuevoa disco
            file = new File(pathIndex + "\\temp.dat");            
            raf = new RandomAccessFile(file,"rw");       
            raf.seek(0);
            if(controller !=null){
                controller.setJPbInvidualIndeterminate(false);
                 controller.setJPbIndividualRangeValues(0, 100);               
            }
                
            for (int i = 0; i < vt.length; i++) {
                //Grabamos
                raf.writeUTF(vt[i].getWord());
                raf.writeInt(vt[i].getDocId());
                raf.writeInt(vt[i].getTf());
                controller.setJPbIndividualValue((i*100)/vt.length);
            }
            
            if(controller !=null){
                 controller.setJPbIndividualValue(0);
                 controller.setJPbInvidualIndeterminate(true);
            }
            
            raf.close();
            this.freeMemory();    
        }catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: FileNotFoundException"); 
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: IOException"); 
        }
    }
        
    public void createInvertexIndex(){
        //Estructura del archivo: word | docId | tf | ...
        try {                             
           File ft = new File(pathIndex + "\\temp.dat");    
           RandomAccessFile raf = new RandomAccessFile(ft,"r"); 
           List<Post> lista = new LinkedList<>();
           List<Vocabulary> listVoc = new LinkedList<>();
           boolean flag = true;
           String wordActual = "";
           String word = "";
           int vocId = 0;
           int nr = 0;
           int mayorTf = 0;
           //Bucle principal que recorre el temp.dat
           while(raf.getFilePointer() < raf.length()){
                while(flag) //bucle que recorre una palabra y sus doc|tf asociados
                {   
                    //es necesario este break, porque si no da error "null"
                    if(raf.getFilePointer() == raf.length()) break;
                    
                    long ptrWord = raf.getFilePointer();
                    word = raf.readUTF(); 
                    
                    if(wordActual.equals("")){
                        wordActual = word;
                        vocId++;                   
                    }                    
                    if(!word.equals(wordActual)){
                        flag = false;
                        //volvemos a la posicion de la nueva palabra
                        raf.seek(ptrWord);
                        break;
                    }
                    
                    nr++;
                    int docId = raf.readInt();
                    int tf = raf.readInt();
                   
                    if(tf >= mayorTf) mayorTf = tf;                    
//                    System.out.println("tf: " + tf + " - mayor: " + mayorTf);
                    //////////////////////////////////////                
                    lista.add(new Post(docId, tf));
                }
                
                Vocabulary voc = new Vocabulary();
                voc.setId(vocId);
                voc.setWord(word);
                voc.setCant_doc_aparece_nr(nr);
                voc.setMax_veces_tf(mayorTf);
                listVoc.add(voc);                               
                        
                if(lista != null){
                    //ordenamos de mayor a menor por termino frecuente
                    Collections.sort(lista, new ComparatorTf());             
                    writeInvertedIndex(vocId, lista);
                }
                
                //Reinicializamos variables y objetos
                wordActual = "";
                flag = true;
                nr = 0;
                mayorTf = 0;
                lista = new LinkedList<>();                
           }
           
           raf.close();
           lista = null;
           //ahora escribimos el vocabulario
           this.writeVocabulary(listVoc);
          // System.out.println("Voc: " + listVoc.toString());
           listVoc = null;
           this.freeMemory();
           boolean delete = ft.delete();
        }
        catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: FileNotFoundException");
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: IOException"); 
        }       
    }
    
    /**
     * Genera un HashMap en memoria y se lo pasa por parametro 
     * a writeInvertdeIndex(HashMap<Vocabulary, List<Post>> invertedIndex)
     */
    public void createInvertexIndexV2(){
        //Estructura del archivo: word | docId | tf | ...
        try {                             
           File ft = new File(pathIndex + "\\temp.dat");    
           RandomAccessFile raf = new RandomAccessFile(ft,"r"); 
           List<Post> lista = new LinkedList<>();
           List<Vocabulary> listVoc = new LinkedList<>();
           // HashMap ordenado por campo clave =>vocId
           SortedMap<Integer, List<Post>> invertedIndex = new TreeMap<>();
           boolean flag = true;
           String wordAnterior = "";
           String word = "";
           int vocId = 0;
           if(controller !=null){               
                controller.setJPbInvidualIndeterminate(false);
                 controller.setJPbIndividualRangeValues(0, 100);
                 controller.addTextJTxtConsolaOut("Armando el indice invertido en memoria....");
            }
                           
           //Bucle principal que recorre el temp.dat
           while(raf.getFilePointer() < raf.length()){              
                while(flag) //bucle que recorre una palabra y sus doc|tf asociados
                {           
                    //es necesario este break, porque si no da error "null"
                    if(raf.getFilePointer() == raf.length()) break;
                    word = raf.readUTF(); 
                    int docId = raf.readInt();
                    int tf = raf.readInt();              
                    
                    if(wordAnterior.equals("")){
                        wordAnterior = word;
                        vocId++;
                    }
                    
                    if(!wordAnterior.equals(word)){
                        flag = false;
                        break;
                    }
                    
                    //agregamos a la lista de posteo de la palabra actual              
                    lista.add(new Post(docId, tf)); 
                }

                Vocabulary voc = new Vocabulary();  
                voc.setId(vocId);
                voc.setWord(wordAnterior);
                voc.setCant_doc_aparece_nr(lista.size());             
                
                //ordenamos de mayor a menor por termino frecuente
                Collections.sort(lista, new ComparatorTf());  

                //como ya estan ordenada la lista el mayor tf, el elemento cero
                voc.setMax_veces_tf(lista.get(0).getFrecuenci_tf());
                listVoc.add(voc);   
                
                // armamos el hashmap para el indice
                invertedIndex.put(new Integer(voc.getId()), lista); 
                
                //Reinicializamos variables y objetos
                wordAnterior = "";
                flag = true;
                lista = new LinkedList<>();
               
                int pos = Integer.parseInt(String.valueOf((raf.getFilePointer()*100)/raf.length()));              
                controller.setJPbIndividualValue(pos);
           }//end of principal loop
           
           if(controller !=null){
                 controller.setJPbIndividualValue(0);
                 controller.setJPbInvidualIndeterminate(true);
           }
           raf.close();         
           lista = null;
           
           if(controller !=null){
                 controller.addTextJTxtConsolaOut("Escribiendo el indice en disco...");
           }
           // guardamos el indice ya creado
           this.writeInvertedIndex(invertedIndex);
           
           if(controller !=null){
                 controller.addTextJTxtConsolaOut("Escribiendo el vocabulario en disco...");
           }
           // ahora escribimos el vocabulario
           this.writeVocabularyV3(listVoc);
           
           invertedIndex = null;
           listVoc = null;
           this.freeMemory();
           
           if(ft.exists()){
                boolean delete = ft.delete();
                if(!delete)
                    if(controller !=null){
                        controller.addTextJTxtConsolaOut("No se pudo borrar el archivo temp.dat...");
                    }
           }
        
    // Solo para depuracion
//            System.out.println("vocId: " + listVoc.size()); //vocId: 92787
//            System.out.println("HashMap: " + invertedIndex.size());
//            String str = ""; 
//           Iterator itr = map.keySet().iterator(); 
//            while(itr.hasNext()){         
////                Map.Entry e = (Map.Entry)itr.next();
//                Object key = itr.next();
//                Integer vId = (Integer)key;
//                List<Post> listPost = (List<Post>)map.get(key);                
//                // recorremos su lista de posteo
//                Iterator it = listPost.listIterator();
//                while(it.hasNext()){         
//                    Post p = (Post)it.next();
//                    str += p.toString() + " ";
////                    System.out.println("vId: " + vId.intValue() + ", " + p.toString());
//                }               
//                System.out.println("vId: " + vId.intValue());
//                System.out.println(str);   
//                str ="";         
//                System.out.println("=========================================================================");
//            }  
        }
        catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: FileNotFoundException");
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: IOException"); 
        }       
    }
    
    /**
     * escribe un vocId con su lista de posteo correspondiente
     * Version 1
     * @param vocId
     * @param listPost 
     */
    private void writeInvertedIndex(int vocId, List<Post> listPost){       
        //Estructura del archivo v1.0: 
        // vocId | docId | tf | ... | docId | tf | vocId | docId | tf ... 
        try {        
            File fIndex = new File(pathIndex + "\\index.dat");
            RandomAccessFile raf = new RandomAccessFile(fIndex,"rw");
            
            if(fIndex.exists()) raf.seek(raf.length());
            //Grabamos el vocabulario id
            raf.writeInt(vocId);
            //y luego las listas de posteo
            Iterator it = listPost.listIterator();
            while(it.hasNext()){         
                Post p = (Post)it.next();
                raf.writeInt(p.getDoc().getId());
                raf.writeInt(p.getFrecuenci_tf());
            } 
            
            raf.close();
            listPost = null;
            this.freeMemory();
        }catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexFile: FileNotFoundException");
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexFile: IOException"); 
        }
    }    
    
    /**
     * version 2, mejora de la anterior escribe el indice invertido,
     * y le agrega un indice principal al final del archivo
     * @param invertedIndex 
     */
    private void writeInvertedIndex(SortedMap<Integer, List<Post>> invertedIndex){
        // Estructura del archivo v2.0: 
        // ptrStartIndex | vocId | docId | tf | ... | docId | tf | vocId | docId | tf ...| vocId | ptrvocId
        // para saber cuantos docId|tf(lista de posteo) tengo que leer, se va a usar el parametro nr del vocabulario
        try {
            if(controller !=null){
                 controller.setJPbInvidualIndeterminate(false);
                 controller.setJPbIndividualRangeValues(0, 100);
                 controller.setJPbIndividualValue(0);
            }
            int pos = 0;
            //lista para crear el indice principal
            List<Vocabulary> listIndex = new LinkedList<>();            
            File file = new File(pathIndex + "\\index.dat");
            RandomAccessFile raf = new RandomAccessFile(file,"rw");           
            //Puntero en donde se almacena la direccion del indice
            raf.writeLong(1111);// reservado para el ptrIndex
            //Iterador del HashMap, bucle principal
            Iterator itr = invertedIndex.keySet().iterator(); 
            while(itr.hasNext()){         
                Object key = itr.next();
                Integer vocId = (Integer)key;
                List<Post> listPost = (List<Post>)invertedIndex.get(key);  
                //variable para el indice al final de este archivo                                         
                long ptrVocId = raf.getFilePointer();   
                //escribimos el Id de vocabulario
                raf.writeInt(vocId.intValue());               
                // recorremos su lista de posteo
                Iterator it = listPost.iterator();
                while(it.hasNext()){         
                    Post p = (Post)it.next();
                    raf.writeInt(p.getDoc().getId());
                    raf.writeInt(p.getFrecuenci_tf());
                    
                } 
                pos++;
                //Guardamos el id y la posicion de inicio, 
                //para ir creando el indice al final del archivo 
                listIndex.add(new Vocabulary(vocId.intValue(), ptrVocId));
                if(controller != null)
                       controller.setJPbIndividualValue((pos*100)/(invertedIndex.size()));
            }
            
            //Direccion de comienzo del indice principal
            long startIndex = raf.getFilePointer();       
            Iterator it = listIndex.iterator();            
            while(it.hasNext()){         
                Vocabulary voc = (Vocabulary)it.next();
                //Indice principal
                raf.writeInt(voc.getId());
                raf.writeLong(voc.getPtrId());
            }            
            //Ahora volvemos al principio y guardamos la direccion del indice            
            raf.seek(0);
            raf.writeLong(startIndex);           
            raf.close();
            
            invertedIndex = null;
            listIndex = null;
            this.freeMemory();
            
        }catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexFile: FileNotFoundException");
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexFile: IOException"); 
        }
    }
    
    /**
     * Lee la lista de posteo de un vocId pasado por parametro
     * @param vocId
     * @return 
     */     
    public List<Post> readInvertdeIndexV2(int vocId, int nr){
        if(vocId == 0) vocId = 1;
        List<Post> listPost = new LinkedList<>();
        try {
            //Estructura del archivo: 
            // ptrStartIndex | vocId | docId | tf | ... | vocId | ptrVocId
            File file = new File(pathIndex + "\\index.dat");
            RandomAccessFile raf = new RandomAccessFile(file,"r");  
            //leemos primero el ptr de inicio del indice principal
            long ptrStartIndex = raf.readLong();
            //id medido desde el comienzo del indice
            raf.seek((vocId-1)*12 + ptrStartIndex);            
            int id = raf.readInt(); // 4 bytes            
            long ptr = raf.readLong(); // 8 bytes por lo tanto son 12 bytes
            
            //nos posicionamos y leemos la lista de docs
            raf.seek(ptr);
            int vId = raf.readInt();            
            // recorremos la lista de posteos
            for (int i = 0; i < nr; i++) {
                int docId = raf.readInt();
                int tf =  raf.readInt();
                
                Document d = new Document();
                d.setId(docId);   
               
                //agregamos a la lista de posteo
                listPost.add(new Post(d, tf));
            }           
            
            raf.close();
            
        } catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexFile: FileNotFoundException");
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexFile: IOException"); 
        }
        
        return listPost;
     }
     
    public Document readDocument(int docId) {        
        //Estructura del archivo: startIndex | docId | path | size |....| docId | ptrId 
        Document doc = new Document();  
        try {                   
            File file = new File(pathIndex + "\\docs.dat");
            RandomAccessFile raf = new RandomAccessFile(file,"r");        
            raf.seek(0);
            //Puntero en donde se almacena la direccion del indice
            long ptrStartIndex = raf.readLong();
            //System.out.println("ptrStartIndex: " + ptrStartIndex);
            if(docId == 0) docId = 1;
            
            //id medido desde el comienso del indice
            raf.seek((docId-1)*12 + ptrStartIndex);            
            int id = raf.readInt(); // 4 bytes
            long ptr = raf.readLong(); // 8 bytes por lo tanto son 12 bytes           
   
            //documento solicitado por el docId pasado por parametro
            raf.seek(ptr);
            doc.setId(raf.readInt());
            doc.setPath(raf.readUTF()); 
            doc.setSize(raf.readLong());
            //System.out.println("docId: " + doc.getId() + " - " + doc.getPath());
            
            raf.close();            
        }catch (FileNotFoundException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: FileNotFoundException");
        } 
        catch (IOException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class InvertedIndexerFile: IOException"); 
        }
        
        return doc;
    }
}