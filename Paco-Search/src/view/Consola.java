package view;

import javax.swing.JTextArea;

/**
 *
 * @author
 * Guerino
 */
public class Consola {
    private final String ERR = "ERR> ";
    private final String OUT = "OUT> ";
    private final String BLOCK = "     ";
    private JTextArea textArea;
    private boolean enabled;

    public Consola() {
        textArea = null;
        enabled = false;
        
    }
    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea aTextArea) {
        this.textArea = aTextArea;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /*
     * Imprimi una salida valida
     */
    
    public void out(String msg) {
        if (enabled) {
            System.out.println(msg);
        }
        printInTextArea(msg, OUT);
    }

    /*
     * Imprime una salida con error
     */
    public void err(String msg) {
        if (enabled) {
            System.err.println(msg);
        }        
        printInTextArea(msg, ERR);
    }

    public void block(String msg) {
        if (enabled) {
            System.out.println(msg);
        }
        printInTextArea(msg, BLOCK);
    }

    /*
     * Imprime una linea concatenandola con el caracter
     * de nueva linea
     */
    private void printInTextArea(String msg, String prefix) {
      if (this.textArea != null) {
            this.textArea.append(prefix + msg + "\n");
            this.textArea.setCaretPosition(this.textArea.getDocument().getLength()-1);
        }
    }    
    
}
