package view.thread;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.panels.IndexJPanel;

/**
 *
 * @author
 * Guerino
 */
public class OpenFolderThread extends Thread{
    @Override
    public void run() {
        try {
            // de 10!
            File pathIndex = new File("index").getAbsoluteFile();
            Desktop.getDesktop().open(pathIndex);
        } catch (IOException ex) {
            Logger.getLogger(IndexJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
