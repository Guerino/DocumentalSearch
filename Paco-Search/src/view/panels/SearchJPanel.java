package view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import view.component.ImageRenderer;
import view.component.LinkRendererCell;
import view.controllers.SearchPanelController;
import view.component.TablePaginator;
import view.thread.SearchThread;

/**
 *
 * @author
 * Guerino
 */
public class SearchJPanel extends javax.swing.JPanel {
    private DefaultTableModel dft;   
    private SearchPanelController searchController;
    private TablePaginator tbPaginator;
     
    /**
     * Constructor
     */
    public SearchJPanel() {
        initComponents();
        initTableResult();
        
        this.jLblResulInfo.setText("  ");        
        searchController = new SearchPanelController(this, jTextFieldSearch , dft, jLblResulInfo, 
                this.jButtonSearch, this.jCmbSearchType, this.tbPaginator);    
    }      
    
    public String getCommand( String file ){ 
        // Depending on the platform could be
        //String.format("gnome-open %s", fileName)
        //String.format("open %s", fileName)
        String str = String.format("cmd /c start %s", file);//Windows        
        return str;
    }
    
    //Encabezados de la tabla
    private String[] getColumnas() {
        String columna[]=new String[]{"#","Nombre","Ruta de acceso","Tamaño"};
        return columna;
    } 

    private void initTableResult(){
               
        dft = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override public Class<?> getColumnClass(int column) {
                return (column==0)?Integer.class:Object.class;
            }            
        };
                
        String[] str = this.getColumnas();
        //Nombre de las columnas
        for (int i = 0; i < str.length; i++) {
              dft.addColumn(str[i]);            
        }       
        
        //le asignamos su modelo de datos
        jTableResult.setModel(dft);
        jTableResult.setIntercellSpacing(new Dimension());
        jTableResult.setShowGrid(false);
        jTableResult.setFillsViewportHeight(true);
        jTableResult.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jTableResult.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE); 
        
        jTableResult.setRowHeight(20);
        jTableResult.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
        
        LinkRendererCell linkRender = new LinkRendererCell();
        jTableResult.getColumnModel().getColumn(2).setCellRenderer(linkRender);
        jTableResult.addMouseListener(linkRender);
        jTableResult.addMouseMotionListener(linkRender);       
        
//        jTableResult.getColumnModel().getColumn(1).setPreferredWidth(60);
        TableColumn colum1 = jTableResult.getColumn((String)"#");
        colum1.setMaxWidth(20);
       
        //paginador de la tabla de resultados
        tbPaginator = new TablePaginator(this.jTableResult, this.dft, jPanelPaginator);        
       
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldSearch = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();
        jLblResulInfo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableResult = new javax.swing.JTable();
        jCmbSearchType = new javax.swing.JComboBox();
        jPanelPaginator = new javax.swing.JPanel();

        jTextFieldSearch.setMinimumSize(new java.awt.Dimension(6, 23));

        jButtonSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/resource/search.png"))); // NOI18N
        jButtonSearch.setText("Buscar");
        jButtonSearch.setDoubleBuffered(true);
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jLblResulInfo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLblResulInfo.setText("Aproximadamente 1235 resultados (0,15 segundos) ");

        jTableResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        jTableResult.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jTableResult.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTableResult.setDoubleBuffered(true);
        jTableResult.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jTableResult.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableResultMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableResult);

        jCmbSearchType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Busqueda binaria", "Busqueda por patron" }));
        jCmbSearchType.setMinimumSize(new java.awt.Dimension(126, 23));

        javax.swing.GroupLayout jPanelPaginatorLayout = new javax.swing.GroupLayout(jPanelPaginator);
        jPanelPaginator.setLayout(jPanelPaginatorLayout);
        jPanelPaginatorLayout.setHorizontalGroup(
            jPanelPaginatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelPaginatorLayout.setVerticalGroup(
            jPanelPaginatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCmbSearchType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblResulInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelPaginator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCmbSearchType, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelPaginator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblResulInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        //Mostramos los resultados
//        this.jLblResulInfo.setVisible(true);        
        SearchThread st = new SearchThread(searchController);
        st.start();
    }//GEN-LAST:event_jButtonSearchActionPerformed
    
    
    private void jTableResultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableResultMouseClicked
        // handle double click here
        if (evt.getClickCount() == 1 && !evt.isConsumed()) {
            try {
                evt.consume();                
                int col = this.jTableResult.getSelectedColumn();
                if(col == 2){
                    int row = this.jTableResult.getSelectedRow();
                    String path = String.valueOf(jTableResult.getModel().getValueAt(row, 2));                
                    //Abrimos el documento con la aplicacion que tiene asociado
                    Runtime.getRuntime().exec( getCommand( path ) );
                }
            }
            
            catch (IOException ex) {
                Logger.getLogger(SearchJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // handle double click here
    }//GEN-LAST:event_jTableResultMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JComboBox jCmbSearchType;
    private javax.swing.JLabel jLblResulInfo;
    private javax.swing.JPanel jPanelPaginator;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableResult;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
