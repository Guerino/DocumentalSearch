package view.thread;

import java.awt.Cursor;
import java.io.File;
import javax.swing.JOptionPane;
import view.controllers.IndexPanelController;

/**
 *
 * @author
 * Guerino
 */
public class CountFileThread extends Thread {    
    private int contArchivos;
    private int contFolders;
    private File file;
    private IndexPanelController controller;   

    public CountFileThread(IndexPanelController controller,  File pathRoot) {
        this.contArchivos = 0;
        this.contFolders = 0;     
        this.controller = controller;
        this.file = pathRoot;      
    }

    /*
     * Metodo encargado de contar la cantidad
     * de archivos en el arbol de directorios pasado por parametro
     */
    private void contarArchivos(File f){
        try {
            
            //Si es un directorio
            if(f.isDirectory()){            
                //Listamos todo su contenido
                File files[] = f.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if(files[i].isDirectory()){
                        contFolders++;
                        //Llamamos recursivamente al directorio hijo
                        contarArchivos(files[i]);
                        controller.setTextTotalFolders(String.valueOf(contFolders));
                    }else{
                        contArchivos++;
                        controller.setTextTotalFiles(String.valueOf(contArchivos));
                    }                             
                }
            }
            
        } catch (NullPointerException e) {
            System.out.println("class CountFileThread: Archivo nulo o inaccesible.");
        }            
    }
    
    //Metodo encargado de correr el hilo
    @Override
    public void run() {
       try {   
            controller.getPanel().setCursor(new Cursor(Cursor.WAIT_CURSOR));
            controller.setTxtPathRootEnable(false);
            controller.setBtnSelectFolderEnable(false);
            controller.setBtnStartEnable(false);
            controller.setBtnStopEnable(true);
            controller.setTextEstadoActual("Contando archivos...");

            long startTime = System.currentTimeMillis();
            //Llamada al metodo recursivo
            contarArchivos(this.file);        

//            controller.setTextTotalFiles(String.valueOf(contArchivos));
//            controller.setTextTotalFolders(String.valueOf(contFolders));
  
            long endTime = System.currentTimeMillis();
            double total = (double)(endTime - startTime)/(double)1000;

            controller.setTextEstadoActual("Finalizado.");
            controller.addTextJTxtConsola("OUT> Tiempo transcurrrido: " + total + " seg" + "\n");
          
            controller.getPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            controller.setJPbIndividualValue(0);
            controller.setTxtPathRootEnable(true);
            controller.setBtnSelectFolderEnable(true);
            controller.setBtnStartEnable(true);
            controller.setBtnStopEnable(false);

      } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Descripcion: " + e.getMessage(), "Error: class CountFileThread", JOptionPane.WARNING_MESSAGE);
      }    
        
    }    
}
