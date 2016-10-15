package PacoSearch.exceptions;

import javax.swing.JOptionPane;

/**
 *
 * @author
 * Guerino
 */
public  class PacoSearchException {    
    /**
     *
     * @param messaje
     * @param title
     */
    public static void Exception(String messaje, String title) {
        JOptionPane.showMessageDialog(null, "Error: " + messaje, title, JOptionPane.WARNING_MESSAGE);
    }
    
    
}
