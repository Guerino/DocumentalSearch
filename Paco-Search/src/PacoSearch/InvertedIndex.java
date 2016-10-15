package PacoSearch;

import PacoSearch.common.Document;
import PacoSearch.exceptions.PacoSearchException;
import PacoSearch.readers.TXTReader;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import view.controllers.IndexPanelController;

/**
 * El indexador creara 3 archivos binarios
 * lista de posteo, vocabulario, y documentos
 * @author
 * Guerino
 */

public class InvertedIndex {
    //Atributos principales
    private int docId;
    private int contFilesProcessed;
    
    //ruta del directorio a indexar
    private String rutaFuente; 
    private static String rutaDestinoIndexer;   
    
    //Cantidad de archivos a indexar
    private int totalFiles;
    private boolean stopIndexer;
    
    private IndexPanelController controller; //Controlador de la UI    
    private InvertedIndexFile indexFile;  
    
    //Constructor
    public InvertedIndex(String pathFolder, int totalFiles) {
        this.totalFiles = totalFiles;
        this.rutaFuente = pathFolder;
        this.docId = 0;
        this.stopIndexer = false;
        this.indexFile = null;  
        this.contFilesProcessed = 0;
    }
    
    //Constructor
    public InvertedIndex(IndexPanelController controller, String pathFolder, int totalFiles) {
        this.controller = controller;
        this.totalFiles = totalFiles;
        this.rutaFuente = pathFolder;
        this.docId = 0;
        this.stopIndexer = false;
        this.indexFile = null;
        this.contFilesProcessed = 0;
    }
    
    /**
     * Metodo que permite detener el proceso
     * de indexacion
     */
    public void stop(){
        this.stopIndexer = true;
    }
    
    /**
     * Metodo encargado de iniciar la indexacion de archivos
     * @return 
     */
    public boolean start(){
        controller.setJPbIndividualRangeValues(0, 100);
        controller.setJPbTotalRangeValues(0, 100);
        controller.setJPbTotalVisible(true);
        //Si, el directorio para el indice no existe, lo creamos 
        //en el mismo directorio del jar 
        File pathIndex = new File("index").getAbsoluteFile();
        if(!pathIndex.exists()){
                pathIndex.mkdir();
                controller.addTextJTxtConsolaOut("Creado el directorio index");                            
        }
        controller.setBtnOpenFolderIndexEnable(true);
        
        rutaDestinoIndexer = pathIndex.getAbsolutePath();   
        controller.addTextJTxtConsola("OUT> Ruta del indice:\n " + rutaDestinoIndexer + "\n");
        
        //1-Analizar recursivamente la ruta de acceso proporcionada al indexador
        File file = new File(this.rutaFuente);        
        controller.setTextEstadoActual("Analizando documentos...");
        List<Document> docList = new LinkedList<>(); 
        indexFile = new InvertedIndexFile(this.controller, rutaDestinoIndexer); 
        
        //Analizamos c/archivo, creamos el documents.dat, y el vocabulario en disco
        scanFolder(file, docList);
        if(!stopIndexer){ 
            controller.setTextEstadoActual("Creando docs.dat...");             
            indexFile.writeDocuments(docList);

            controller.setJPbIndividualValue(0);
            controller.setPanelProgresoTotalVisible(false);
            controller.setJPbTotalValue(0);
            //Cantidad de terminos o palabras: 4.898.674=> 80MB
            //Cantidad de terminos o palabras: 2.522.876 => 40.7MB
            controller.setTextEstadoActual("Ordenando archivo temporal...");  
            indexFile.orderTempFileV2();       

            controller.setTextEstadoActual("Creando indice..."); 
            indexFile.createInvertexIndexV2();

            controller.setJPbInvidualIndeterminate(false);
            controller.setTextEstadoActual("Finalizado.");
        }
            
        return true;
    }
    
    /**
     * Metodo recursivo que lee los documentos a indexar
     * descendiendo en el arbol de directorios
     * @param file 
     */
    private void scanFolder(File file, List<Document> docsList) {
        try {
            //Si es un directorio
            if(file.isDirectory()){            
                //Listamos todo su contenido
                File files[] = file.listFiles();                
                //System.out.println("files.length: " + files.length);
                for (int i = 0; i < files.length; i++){
                    if(stopIndexer) break;                    
                    //Si es un directorio
                    if(files[i].isDirectory()){
                        controller.setTextEstadoActual("Analizando directorio: " + files[i].getAbsolutePath());  
                        //Llamamos recursivamente al directorio hijo
                        scanFolder(files[i], docsList);
                        //System.out.println(files[i].toString());
                    }else{                        
                        //Contador de documentos
                        this.docId++;
                        docsList.add(new Document(docId, files[i].getAbsolutePath(), files[i].length()));
                        
                        //Ahora a procesar palabras
                        TXTReader txtRd = new TXTReader();  
                        HashMap<String, Integer> terms = txtRd.read(files[i]);                        
                      
                        //Archivo temporal con las palabras y sus frecuencias por documento
                        if(terms != null) {
                            indexFile.writeTempFile(docId, terms);
                        } 
                         // Actualizamos los controles
                         int porc = (i*100)/files.length;
                         controller.setJPbIndividualValue(porc);
                         contFilesProcessed++;
                    }  //end else
                    
                  int porc = (contFilesProcessed*100)/this.totalFiles;
                  controller.setJPbTotalValue(porc);                    
                }// end for
            } // end if                   
        }
        catch (NullPointerException ex) {
            PacoSearchException.Exception(ex.getMessage(), "class Indexer: NullPointerException"); 
        } catch (Exception ex) {
            PacoSearchException.Exception(ex.getMessage(), "class Indexer: Exception"); 
        }  
    }
    
    private void listToString(List<Document> docList){
        Iterator it = docList.listIterator();
        while(it.hasNext()){         
            Document t = (Document)it.next();
            System.out.println(t.getId() + " - " + t.getPath());
        } 
    }
  
    
}