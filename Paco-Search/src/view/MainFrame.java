package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.net.URL;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.Painter;
import javax.swing.UIManager;
import view.panels.IndexJPanel;
import view.panels.SearchJPanel;
/**
 *
 * @author
 * Guerino
 */
class FillPainter implements Painter<JComponent> {

    private final Color color;

    FillPainter(Color c) {
        color = c;
    }

    @Override
    public void paint(Graphics2D g, JComponent object, int width, int height) {
        g.setColor(color);
        g.fillRect(0, 0, width - 1, height - 1);
    }
}

public class MainFrame extends javax.swing.JFrame {
    private IndexJPanel IndexPanel;
    private SearchJPanel SearchPanel;
    private boolean alwayOnTop;
    /**
     * Creates
     * new
     * form
     * MainFrame
     */
    public MainFrame() {
        initComponents();
        this.setTitle("Buscador Documental V1.0 | DLC - UTN-FRC 2012");
        this.setIconImage (new ImageIcon(getClass().getResource("/view/resource/32_old-edit-find.png")).getImage());
        
        this.alwayOnTop = false;
        this.jCheckBoxMenuItemAlwayOnTop.setSelected(false);
        
        this.IndexPanel = new IndexJPanel();
        this.SearchPanel = new SearchJPanel();
        ImageIcon iconIndex = new ImageIcon(getClass().getResource("/view/resource/page_attach.png"));
        ImageIcon iconSearch = new ImageIcon(getClass().getResource("/view/resource/database-search.png"));
        
        this.jTabbedPaneMain.removeAll();
        this.jTabbedPaneMain.addTab(" Buscador ",iconSearch, SearchPanel, "Realizar busquedas de documentos");
        this.jTabbedPaneMain.addTab("Indizar archivos...",iconIndex, IndexPanel, "Iniciar el indexador de documentos");       
        
        
        //Ajustar controles y contenedores
        this.pack();
        //Centro el JFrame en la pantalla
        this.setLocationRelativeTo(null);
        
       // Runtime r = Runtime.getRuntime();
        //Process p = null;
        //String comando[] = { "notepad","java814.java" };

        // Datos de la memoria del Sistema
//        System.out.println( "Memoria Total = "+ r.totalMemory() +
//            " Memoria Libre = "+ r.freeMemory() );
       
        
        //Funciona de 10!
        printVersion();
    }

   /**
    * Metodo encagargado de leer la version del manifest.mf
    */
    private void printVersion()
    {
        String jarFileURL = MainFrame.class.getProtectionDomain().getCodeSource().getLocation().toString();
        int pos = jarFileURL.indexOf("!");         
        if(pos != -1) {   
            jarFileURL = jarFileURL.substring(0,pos);  
        }
        
        if(!jarFileURL.startsWith("jar:")) {  
            jarFileURL = "jar:" + jarFileURL; 
        } 
        
        if(jarFileURL.lastIndexOf(".jar") != -1){
            URL manifestUrl; 
            try {

                manifestUrl = new URL(jarFileURL + "!/META-INF/MANIFEST.MF");
                Manifest manifest = new Manifest(manifestUrl.openStream());   

                if(manifest != null){
                    String str = "Buscador Documental v"+manifest.getMainAttributes().getValue("Implementation-Version") 
                            + " (Build: " + manifest.getMainAttributes().getValue("Build-Number") 
                            + ", Compiled: "+  manifest.getMainAttributes().getValue("Built-Date") + ")";
                    setTitle(str);
                }
            }        
            catch (IOException ex) {
                Logger.getLogger(IndexJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jTabbedPaneMain = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuArchivo = new javax.swing.JMenu();
        jCheckBoxMenuItemAlwayOnTop = new javax.swing.JCheckBoxMenuItem();
        jMenuItemSalir = new javax.swing.JMenuItem();
        jMenuAyuda = new javax.swing.JMenu();
        jMenuItemAcercade = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 428, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 239, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Buscador Documental V1.0 | DLC- UTN FRC - 2012");

        jMenuArchivo.setText("Archivo");

        jCheckBoxMenuItemAlwayOnTop.setSelected(true);
        jCheckBoxMenuItemAlwayOnTop.setText("Siempre visible");
        jCheckBoxMenuItemAlwayOnTop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItemAlwayOnTopActionPerformed(evt);
            }
        });
        jMenuArchivo.add(jCheckBoxMenuItemAlwayOnTop);

        jMenuItemSalir.setText("Salir");
        jMenuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSalirActionPerformed(evt);
            }
        });
        jMenuArchivo.add(jMenuItemSalir);

        jMenuBar1.add(jMenuArchivo);

        jMenuAyuda.setText("Ayuda");

        jMenuItemAcercade.setText("Acerca de...");
        jMenuItemAcercade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAcercadeActionPerformed(evt);
            }
        });
        jMenuAyuda.add(jMenuItemAcercade);

        jMenuBar1.add(jMenuAyuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jTabbedPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemAcercadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAcercadeActionPerformed
        // TODO add your handling code here:
        // Ventana Acerca de...
         JOptionPane.showMessageDialog(this,"Realizado por: \n\tPalacios, Javier Güerino\nUniversidad Tecnológica Nacional "
                 + "- Facultad Regional Córdoba\nCátedra: Diseño de Lenguajes de Consulta 4K6-2012" );
    }//GEN-LAST:event_jMenuItemAcercadeActionPerformed

    private void jMenuItemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_jMenuItemSalirActionPerformed

    private void jCheckBoxMenuItemAlwayOnTopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemAlwayOnTopActionPerformed
        // TODO add your handling code here:
        this.alwayOnTop = !this.alwayOnTop; 
        this.setAlwaysOnTop(this.alwayOnTop);
    }//GEN-LAST:event_jCheckBoxMenuItemAlwayOnTopActionPerformed

    /**
     * @param
     * args
     * the
     * command
     * line
     * arguments
     */
    public static void main(String args[]) {
        
        /*
         * Set
         * the
         * Nimbus
         * look
         * and
         * feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">        
        try {
            /*for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("System".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    
                    break;
                }
            }*/
             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
             
//             UIManager.put("ProgressBar.background", Color.BLACK); //colour of the backg
//             UIManager.put("ProgressBar.foreground", Color.RED);  //colour of progress bar
//             UIManager.put("ProgressBar.selectionBackground",Color.YELLOW);  //colour of percentage counter on black background    
//             UIManager.put("ProgressBar.selectionForeground",Color.BLUE);  //colour 
//                
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create
         * and
         * display
         * the
         * form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemAlwayOnTop;
    private javax.swing.JMenu jMenuArchivo;
    private javax.swing.JMenu jMenuAyuda;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemAcercade;
    private javax.swing.JMenuItem jMenuItemSalir;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPaneMain;
    // End of variables declaration//GEN-END:variables
}
