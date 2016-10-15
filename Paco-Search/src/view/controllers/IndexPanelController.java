package view.controllers;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import view.panels.IndexJPanel;

/**
 *
 * @author
 * Guerino
 */
public class IndexPanelController {
    private IndexJPanel panel;
    private JButton btnSelectFolder;
    private JButton btnStart;
    private JButton btnStop;
    private JButton btnOpenFolderIndex;
    private JLabel lblTotalFiles;
    private JLabel lblTotalFolders;
    private JLabel lblEstadoActual;
    private JLabel lblTiempoTranscurrido;
    private JProgressBar jpbProgresoIndividual;
    private JProgressBar jpbProgresoTotal;
    private JTextArea txtAreaConsola;
    private JTextField txtPathRoot;
    private JPanel jPanelProgresoTotal;

    public IndexPanelController(IndexJPanel panel, JLabel totalFiles,JLabel totalFolders, JLabel estado, JLabel tiempoTransc, 
            JProgressBar individual,JProgressBar total, JTextArea txtAreaConsola, JTextField txtPathRoot,
            JButton selectFolder, JButton start, JButton stop, JButton openFolderIndex, JPanel jPanelProgresoTotal) {
        //Controles de la UI
        this.panel = panel;
        this.lblTotalFiles = totalFiles;
        this.lblTotalFolders = totalFolders;
        this.lblEstadoActual = estado;
        this.lblTiempoTranscurrido = tiempoTransc;
        this.jpbProgresoIndividual = individual;
        this.jpbProgresoTotal = total;
        this.txtAreaConsola = txtAreaConsola;
        this.btnSelectFolder = selectFolder;
        this.btnStart = start;
        this.btnStop = stop;
        this.btnOpenFolderIndex = openFolderIndex;
        this.txtPathRoot = txtPathRoot;
        this.jPanelProgresoTotal = jPanelProgresoTotal;
    }
    
    public IndexJPanel getPanel(){
        return this.panel;
    }
    
    public void setTextConsola(String txt){
        this.txtAreaConsola.setText(txt);
    }
    
    public void addTextJTxtConsola(String str){
        this.txtAreaConsola.append(str);
        this.txtAreaConsola.setCaretPosition(this.txtAreaConsola.getDocument().getLength()-1); 
    }
    
    public void addTextJTxtConsolaOut(String str){
        this.txtAreaConsola.append("OUT> " + str + "\n");
        this.txtAreaConsola.setCaretPosition(this.txtAreaConsola.getDocument().getLength()-1); 
    }
    
    public void addTextJTxtConsolaErr(String str){
        this.txtAreaConsola.append("ERROR> " + str + "\n");
        this.txtAreaConsola.setCaretPosition(this.txtAreaConsola.getDocument().getLength()-1); 
    }
    
    public void setTxtPathRootEnable(boolean enable){
        this.txtPathRoot.setEnabled(enable);
    }
    
    public void setTextTotalFiles(String str){
        this.lblTotalFiles.setText(str);
    }
    
    public void setTextTotalFolders(String str){
        this.lblTotalFolders.setText(str);
    }
    
    public void setTextEstadoActual(String str){
        this.lblEstadoActual.setText(str);
    }
    
    public int getTotalFiles(){
        return Integer.parseInt(this.lblTotalFiles.getText());
    }
    
    public void setJPbRangeValues(int min, int max){
        this.jpbProgresoIndividual.setMinimum(min);
        this.jpbProgresoIndividual.setMaximum(max);
    }
    
    public void setJPbIndividualRangeValues(int min, int max){
        this.jpbProgresoIndividual.setMinimum(min);
        this.jpbProgresoIndividual.setMaximum(max);
        this.jpbProgresoIndividual.setStringPainted(true);
    }
    
    public void setJPbIndividualValue(int n){
        this.jpbProgresoIndividual.setValue(n);
    }
    
    public void setJPbIndividualVisible(boolean visible){
        this.jpbProgresoIndividual.setVisible(visible);
    }
    
    public void setJPbInvidualIndeterminate(boolean indeterminate){
        if (indeterminate) {
            this.jpbProgresoIndividual.setStringPainted(false);
            this.jpbProgresoIndividual.setIndeterminate(indeterminate);
        }else{
            this.jpbProgresoIndividual.setStringPainted(true);
            this.jpbProgresoIndividual.setIndeterminate(indeterminate);
        }        
    }
    
    public void setJPbTotalIndeterminate(boolean indeterminate){
        if (indeterminate) {
            this.jpbProgresoTotal.setStringPainted(false);
            this.jpbProgresoTotal.setIndeterminate(indeterminate);
        }else{
            this.jpbProgresoTotal.setStringPainted(true);
            this.jpbProgresoTotal.setIndeterminate(indeterminate);
        }        
    }
    
    public void setJPbTotalRangeValues(int min, int max){
        this.jpbProgresoTotal.setMinimum(min);
        this.jpbProgresoTotal.setMaximum(max);
    }
    
    public void setJPbTotalValue(int n){
        this.jpbProgresoTotal.setValue(n);
    }
    
    public void setJPbTotalVisible(boolean visible){
        this.jpbProgresoTotal.setVisible(visible);
    }
    
    //Faltan metodos para los botones
    public void setBtnSelectFolderEnable(boolean enable){
        this.btnSelectFolder.setEnabled(enable);
    }
    
    public void setBtnStartEnable(boolean enable){
        this.btnStart.setEnabled(enable);
    }
    
    public void setBtnStopEnable(boolean enable){
        this.btnStop.setEnabled(enable);
    }

    public void setBtnOpenFolderIndexEnable(boolean enable){
        this.btnOpenFolderIndex.setEnabled(enable);
    }
    
    public JLabel getLblTiempoTranscurrido() {
        return lblTiempoTranscurrido;
    }
    
    public void setPanelProgresoTotalVisible(boolean visible){
        this.jPanelProgresoTotal.setVisible(visible);
    }
    
}
