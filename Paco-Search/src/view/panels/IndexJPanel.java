package view.panels;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import view.Consola;
import view.controllers.IndexPanelController;
import view.thread.CountFileThread;
import view.thread.IndexThread;
import view.thread.OpenFolderThread;

/**
 *
 * @author
 * Guerino
 */
public class IndexJPanel extends javax.swing.JPanel {
    private DefaultTableModel tblModel;
    private Consola consola;
    private int contArchivos;
    private IndexPanelController controller;
    private IndexThread hiloIndexer;
    private CountFileThread hiloCount;
    /**
     * Creates
     * new
     * form
     * IndexJPanel
     */
    public IndexJPanel() {
        initComponents();
        this.hiloIndexer = null;
        this.hiloCount = null;
        this.contArchivos = 0;
        
        this.jLblTotalArchivos.setText(" ");
        this.jLblTotalDirectorios.setText(" ");
        this.jLblEstadoActual.setText(" ");
        this.jLblTiempoTranscurrido.setText(" ");
        
        this.jButtonStart.setEnabled(false);
        this.jBtnCancelar.setEnabled(false);
        this.jBtnOpenFolderIndex.setEnabled(false);
        // por ahora, hasta que lo implemente
//        this.jPanelProgresoTotal.setVisible(false);
        //this.jProgressBarTotal.setValue(50);
//        
//    // used for Nimbus (beware: just a proof-of-concept - this looks extremely ugly!)
//    Painter p = new Painter() {
//        @Override
//        public void paint(Graphics2D g, Object object, int width, int height) {
//            JProgressBar bar = (JProgressBar) object;
//            g.setColor(bar.getForeground());
//            g.fillRect(0, 0, width, height);
//        } };
//    // install custom painter on the bar
//    UIDefaults properties = new UIDefaults();
//    properties.put("ProgressBar[Enabled].foregroundPainter", p);
//    jProgressBarIndividual.putClientProperty(UIManager.getSystemLookAndFeelClassName()+".Overrides", properties);

        
        //Para la salida de informacion
        this.consola = new Consola();
        this.consola.setTextArea(jTextAreaConsola);  
        //Controlador de la UI       
        controller = new IndexPanelController(this, jLblTotalArchivos, jLblTotalDirectorios, jLblEstadoActual, jLblTiempoTranscurrido,
                jProgressBarIndividual, jProgressBarTotal, jTextAreaConsola, jTxtPathFolder,
                jButtonSeleccion, jButtonStart, jBtnCancelar, jBtnOpenFolderIndex, jPanelProgresoTotal); 
  
    }
       
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonSeleccion = new javax.swing.JButton();
        jButtonStart = new javax.swing.JButton();
        jPanelEstado = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaConsola = new javax.swing.JTextArea();
        jTxtPathFolder = new javax.swing.JTextField();
        jBtnCancelar = new javax.swing.JButton();
        jPanelProgresoTotal = new javax.swing.JPanel();
        jProgressBarTotal = new javax.swing.JProgressBar();
        jPanelEstadisticas = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLblTotalDirectorios = new javax.swing.JLabel();
        jLblTotalArchivos = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLblEstadoActual = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLblTiempoTranscurrido = new javax.swing.JLabel();
        jPanelProgresoIndividual = new javax.swing.JPanel();
        jProgressBarIndividual = new javax.swing.JProgressBar();
        jBtnOpenFolderIndex = new javax.swing.JButton();

        jButtonSeleccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/resource/select_folder_16x16.png"))); // NOI18N
        jButtonSeleccion.setText("Seleccionar directorio...");
        jButtonSeleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeleccionActionPerformed(evt);
            }
        });

        jButtonStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/resource/arrow-right.png"))); // NOI18N
        jButtonStart.setText("Iniciar");
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartActionPerformed(evt);
            }
        });

        jPanelEstado.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles:"));

        jTextAreaConsola.setEditable(false);
        jTextAreaConsola.setBackground(new java.awt.Color(0, 0, 0));
        jTextAreaConsola.setColumns(20);
        jTextAreaConsola.setForeground(new java.awt.Color(51, 204, 0));
        jTextAreaConsola.setRows(5);
        jTextAreaConsola.setDoubleBuffered(true);
        jScrollPane1.setViewportView(jTextAreaConsola);

        javax.swing.GroupLayout jPanelEstadoLayout = new javax.swing.GroupLayout(jPanelEstado);
        jPanelEstado.setLayout(jPanelEstadoLayout);
        jPanelEstadoLayout.setHorizontalGroup(
            jPanelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanelEstadoLayout.setVerticalGroup(
            jPanelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
        );

        jBtnCancelar.setText("Cancelar");
        jBtnCancelar.setPreferredSize(new java.awt.Dimension(75, 25));
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });

        jPanelProgresoTotal.setBorder(javax.swing.BorderFactory.createTitledBorder("Progreso total:"));
        jPanelProgresoTotal.setFocusable(false);

        jProgressBarTotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jProgressBarTotal.setDoubleBuffered(true);
        jProgressBarTotal.setFocusable(false);
        jProgressBarTotal.setMaximumSize(new java.awt.Dimension(32767, 24));
        jProgressBarTotal.setPreferredSize(new java.awt.Dimension(146, 23));
        jProgressBarTotal.setRequestFocusEnabled(false);
        jProgressBarTotal.setStringPainted(true);

        javax.swing.GroupLayout jPanelProgresoTotalLayout = new javax.swing.GroupLayout(jPanelProgresoTotal);
        jPanelProgresoTotal.setLayout(jPanelProgresoTotalLayout);
        jPanelProgresoTotalLayout.setHorizontalGroup(
            jPanelProgresoTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBarTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelProgresoTotalLayout.setVerticalGroup(
            jPanelProgresoTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBarTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanelEstadisticas.setBorder(javax.swing.BorderFactory.createTitledBorder("Estadisticas:"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Archivos:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Directorios:");

        jLblTotalDirectorios.setText("jLabel3");

        jLblTotalArchivos.setText("jLabel3");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Estado:");

        jLblEstadoActual.setText("jLabel3");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Tiempo transcurrido:");

        jLblTiempoTranscurrido.setText("jLabel3");

        javax.swing.GroupLayout jPanelEstadisticasLayout = new javax.swing.GroupLayout(jPanelEstadisticas);
        jPanelEstadisticas.setLayout(jPanelEstadisticasLayout);
        jPanelEstadisticasLayout.setHorizontalGroup(
            jPanelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEstadisticasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEstadisticasLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLblTotalArchivos, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLblTotalDirectorios, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLblTiempoTranscurrido, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelEstadisticasLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLblEstadoActual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelEstadisticasLayout.setVerticalGroup(
            jPanelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEstadisticasLayout.createSequentialGroup()
                .addGroup(jPanelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLblTiempoTranscurrido, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(jLblTotalDirectorios, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLblTotalArchivos, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLblEstadoActual, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanelProgresoIndividual.setBorder(javax.swing.BorderFactory.createTitledBorder("Progreso individual:"));

        jProgressBarIndividual.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jProgressBarIndividual.setDoubleBuffered(true);
        jProgressBarIndividual.setFocusable(false);
        jProgressBarIndividual.setMaximumSize(new java.awt.Dimension(32767, 24));
        jProgressBarIndividual.setPreferredSize(new java.awt.Dimension(146, 23));
        jProgressBarIndividual.setRequestFocusEnabled(false);
        jProgressBarIndividual.setStringPainted(true);

        javax.swing.GroupLayout jPanelProgresoIndividualLayout = new javax.swing.GroupLayout(jPanelProgresoIndividual);
        jPanelProgresoIndividual.setLayout(jPanelProgresoIndividualLayout);
        jPanelProgresoIndividualLayout.setHorizontalGroup(
            jPanelProgresoIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBarIndividual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelProgresoIndividualLayout.setVerticalGroup(
            jPanelProgresoIndividualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBarIndividual, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jBtnOpenFolderIndex.setText("Abrir ubicacion del archivo indice...");
        jBtnOpenFolderIndex.setPreferredSize(new java.awt.Dimension(199, 25));
        jBtnOpenFolderIndex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnOpenFolderIndexActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelEstadisticas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonStart, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jBtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnOpenFolderIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTxtPathFolder)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonSeleccion))
                            .addComponent(jPanelEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelProgresoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelProgresoIndividual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonSeleccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTxtPathFolder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonStart)
                    .addComponent(jBtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnOpenFolderIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelEstadisticas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelProgresoIndividual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelProgresoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeleccionActionPerformed
        //Seleccionamos solo directorios
        /**
         * NOTA: por ahora lo dejamos asi, para la presentacion
         * final hay que dejarlo con Jchooser
         */
        JFileChooser jFChooser = new JFileChooser();
        jFChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);        
        int n = jFChooser.showOpenDialog(null);  
        File path = jFChooser.getSelectedFile();
//        File pathRoot = new File("D:\\db_documentos");       
        if(path != null)
        {
            this.jTxtPathFolder.setText(path.getAbsolutePath());
            this.jTxtPathFolder.setText(path.getAbsolutePath());
            this.jButtonStart.setEnabled(true);
            //Creamos y lanzamos el hilo de conteo de archivos
            hiloCount = new CountFileThread(controller, path);        
            hiloCount.start();
        }
    }//GEN-LAST:event_jButtonSeleccionActionPerformed

    private void jButtonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartActionPerformed
        if(!jTxtPathFolder.getText().trim().equals("")){
            this.jPanelProgresoTotal.setVisible(true);
            //LLamar al hilo de indexacion
            hiloIndexer = new IndexThread(controller, jTxtPathFolder.getText().trim());
            hiloIndexer.start();
        }
    }//GEN-LAST:event_jButtonStartActionPerformed

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        // Opcion de detener el hilo de indexacion      
        if(hiloCount != null)
            hiloCount.interrupt();
        
        if(hiloIndexer != null){
            hiloIndexer.stopIndexer();
            hiloIndexer.interrupt();
        }
        
         this.jBtnCancelar.setEnabled(false);
         this.jButtonStart.setEnabled(true);
         this.jButtonSeleccion.setEnabled(true);
         this.jTxtPathFolder.setEnabled(true);
         
    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jBtnOpenFolderIndexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnOpenFolderIndexActionPerformed
        OpenFolderThread hilo = new OpenFolderThread();
        hilo.start();
    }//GEN-LAST:event_jBtnOpenFolderIndexActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnOpenFolderIndex;
    private javax.swing.JButton jButtonSeleccion;
    private javax.swing.JButton jButtonStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLblEstadoActual;
    private javax.swing.JLabel jLblTiempoTranscurrido;
    private javax.swing.JLabel jLblTotalArchivos;
    private javax.swing.JLabel jLblTotalDirectorios;
    private javax.swing.JPanel jPanelEstadisticas;
    private javax.swing.JPanel jPanelEstado;
    private javax.swing.JPanel jPanelProgresoIndividual;
    private javax.swing.JPanel jPanelProgresoTotal;
    private javax.swing.JProgressBar jProgressBarIndividual;
    private javax.swing.JProgressBar jProgressBarTotal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaConsola;
    private javax.swing.JTextField jTxtPathFolder;
    // End of variables declaration//GEN-END:variables
}
