package view.controllers;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import view.component.TablePaginator;
import view.panels.SearchJPanel;

/**
 *
 * @author
 * Guerino
 */
public class SearchPanelController {
    private SearchJPanel panel;
    private JTextField txtQuery; //txt en donde se escriben las palabras a buscar     
    private DefaultTableModel tableModel;
    private JLabel lblResult;
    private JButton btnSearch;
    private JComboBox cmbSearchType;
    private TablePaginator tbPaginator;

    public SearchPanelController(SearchJPanel panel, JTextField txtQuery, 
            DefaultTableModel tableModel, JLabel lblResult, JButton search, JComboBox cmbSearchType, TablePaginator tbPaginator) {
        this.panel = panel;
        this.txtQuery = txtQuery;
        this.tableModel = tableModel;
        this.lblResult = lblResult;
        this.btnSearch = search;
        this.cmbSearchType = cmbSearchType;
        this.tbPaginator = tbPaginator;
    }

    public void updatePaginator(){
        this.tbPaginator.update();
    }
    
    public SearchJPanel getPanel() {
        return panel;
    }
     
    public void setTextLblResult(String str){
        this.lblResult.setText(str);
    }
    
    public void addRowTable(String nombre, String ruta, String tamano){
        Object[] row = {"", " "+nombre, " "+ruta, " "+tamano};
        this.tableModel.addRow(row);
    }
    
    public void cleanRowTable(){
        this.tableModel.setRowCount(0);
    }
    
    public String getTextQuery(){
        return this.txtQuery.getText();
    }
    
    public void setEnableBtnSearch(boolean enable){
        this.btnSearch.setEnabled(enable);
    }
    
    public int getSelectedItem(){
        return this.cmbSearchType.getSelectedIndex()+1;
    }
}
