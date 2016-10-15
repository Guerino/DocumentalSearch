package view.thread;
import PacoSearch.InvertedIndex;
import java.awt.Cursor;
import javax.swing.JOptionPane;
import view.controllers.IndexPanelController;

/**
 *
 * @author
 * Guerino
 */
public class IndexThread extends Thread{
    private String startIndex;
    private String endIndex;    
    private String pathFolder;
    private InvertedIndex indexador;
    private IndexPanelController controller;
    
    public IndexThread(IndexPanelController controller, String pathFolder) {
        this.pathFolder = pathFolder;
        this.controller = controller;
        this.indexador = new InvertedIndex(controller, pathFolder, controller.getTotalFiles()); 
    }
    
    public void stopIndexer(){
        this.indexador.stop();
    }
    
    @Override
    public void run() {
        try {          
            //Thread.sleep(100); 
            TimeThread t = new TimeThread(controller.getLblTiempoTranscurrido());
            t.start();
            long startTime = System.currentTimeMillis();
            controller.getPanel().setCursor(new Cursor(Cursor.WAIT_CURSOR));
            controller.setTxtPathRootEnable(false);
            controller.setBtnSelectFolderEnable(false);
            controller.setBtnStartEnable(false);
            controller.setBtnStopEnable(true);
            controller.setTextEstadoActual("Indexando archivos...");
            
            //Gestor de indizacion                       
            indexador.start();         
                        
            long endTime = System.currentTimeMillis();
            double total = (double)(endTime - startTime)/(double)1000;
            
            controller.setTextEstadoActual("Finalizado.");  
            controller.addTextJTxtConsola("OUT> Tiempo transcurrrido: " + total + " seg" + "\n");
            //Actualiza la posicion del cursor
//            this.txtArea.append(totalFiles + " " + "\n");
//            this.txtArea.setCaretPosition(this.txtArea.getDocument().getLength()-1); 
//            
//            this.txtArea.append(totalFolder + " " + "\n");
//            this.txtArea.setCaretPosition(this.txtArea.getDocument().getLength()-1); 
            
//            this.txtArea.append(totalSize + " " + "\n");
//            this.txtArea.setCaretPosition(this.txtArea.getDocument().getLength()-1); 
            t.stopTiempo(true);
            controller.getPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            controller.setJPbIndividualValue(0);
            controller.setTxtPathRootEnable(true);
            controller.setBtnSelectFolderEnable(true);
            controller.setBtnStartEnable(true);
            controller.setBtnStopEnable(false);
            controller.setBtnOpenFolderIndexEnable(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Descripcion: " + e.getMessage(), "Error: class IndexThread", JOptionPane.WARNING_MESSAGE);
        } 
    }
    
    
}
