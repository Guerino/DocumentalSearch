package view.thread;

import PacoSearch.Search;
import PacoSearch.SearchIndexFile;
import PacoSearch.common.Document;
import java.awt.Cursor;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import view.controllers.SearchPanelController;

/**
 *
 * @author
 * Guerino
 */
public class SearchThread extends Thread{
    private SearchPanelController controller;

    public SearchThread(SearchPanelController controller) {
        this.controller = controller;
    }
    
    @Override
    public void run() {
        try {
          if(!controller.getTextQuery().isEmpty()){
              controller.cleanRowTable();
              controller.setEnableBtnSearch(false);
                //aqui se le pasan las palabras al buscador
                int cantResultados = 0;
                long startTime = System.currentTimeMillis();
                controller.getPanel().setCursor(new Cursor(Cursor.WAIT_CURSOR));

                File pathIndex = new File("index").getAbsoluteFile();
                if(pathIndex.exists()){
                    Search search = null;
                    if(controller.getSelectedItem() == 1)
                       search = new Search(pathIndex.getAbsolutePath(), SearchIndexFile.BINARY_SEARCH);
                    else
                       search = new Search(pathIndex.getAbsolutePath(), SearchIndexFile.PATTERN_SEARCH);
                    
                    List<Document> listDoc = search.search(controller.getTextQuery());                    
                    Iterator it = listDoc.iterator();
                    while(it.hasNext()){
                        Document doc = (Document)it.next();
                        String name = doc.getPath(); 
                        int i = doc.getPath().lastIndexOf(java.io.File.separator);
                        name = name.substring(i+1);
                        controller.addRowTable(" "+name, " "+doc.getPath(), " "+doc.getSizeToString());
                        cantResultados++;
                    }
                }

                long endTime = System.currentTimeMillis();
                double total = (double)(endTime - startTime)/(double)1000;
                controller.updatePaginator();
                controller.setTextLblResult("Aproximadamente "+ cantResultados +" resultados en (" + total + " seg.)");
                controller.getPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                controller.setEnableBtnSearch(true);
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Descripcion: " + e.getMessage(), "Error: class SearchThread", JOptionPane.WARNING_MESSAGE);
        } 
    }
    
    
}
