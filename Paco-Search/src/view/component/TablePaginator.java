package view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author
 * Guerino
 */
public class TablePaginator {
    private final TableRowSorter<TableModel> sorter;
    private Box box = Box.createHorizontalBox();
    private JPanel panelPaginator;
    private JTable tableResult;
    private DefaultTableModel tableModel;
    private LinkViewRadioButtonUI lnkRadioBtn;
    private int LR_PAGE_SIZE = 10;
//    private int endPageIndexOld = 10;
//    private boolean flag = true; 

    public TablePaginator(JTable jTableResult, DefaultTableModel tableModel, JPanel jPanelPaginator) {
        this.tableModel = tableModel;
        this.lnkRadioBtn = new LinkViewRadioButtonUI();
        this.sorter = new TableRowSorter<TableModel>(tableModel);
        this.panelPaginator = jPanelPaginator;
        this.tableResult = jTableResult;
        this.tableResult.setRowSorter(sorter);
        
        this.panelPaginator.setLayout(null);
        this.panelPaginator.setLayout(new BorderLayout());
        this.panelPaginator.add(box, BorderLayout.CENTER);
        //mostrar la lista de 20 resultados comenzando en la 1
        initLinkBox(20, 1);
    }
    
    public void update(){
        //mostrar la lista de 20 resultados comenzando en la 1
        initLinkBox(20, 1);
    }
    
    private void initLinkBox(final int itemsPerPage, final int currentPageIndex) {
        //assert currentPageIndex>0;
        sorter.setRowFilter(makeRowFilter(itemsPerPage, currentPageIndex-1));
        ArrayList<JRadioButton> paginationButtons = new ArrayList<JRadioButton>();

        int startPageIndex = currentPageIndex-LR_PAGE_SIZE;
        if(startPageIndex<=0) startPageIndex = 1;

//        System.out.println(tableModel.getRowCount()%itemsPerPage);
//#if 0
        //int maxPageIndex = (model.getRowCount()/itemsPerPage)+1;
//#else
        /* "maxPageIndex" gives one blank page if the module of the division is not zero.
         *   pointed out by erServi
         * e.g. rowCount=100, maxPageIndex=100
         */
        int rowCount = tableModel.getRowCount();
        int maxPageIndex = (rowCount/itemsPerPage) + (rowCount%itemsPerPage==0?0:1);
//#endif
        int endPageIndex = currentPageIndex+LR_PAGE_SIZE-1;

        if(endPageIndex>maxPageIndex) endPageIndex = maxPageIndex;

        if(currentPageIndex>1) {
            paginationButtons.add(makePNRadioButton(itemsPerPage, currentPageIndex-1, "Anterior"));
        }

        //if I only have one page, I don't want to see pagination buttons
        //suggested by erServi
        if(startPageIndex<endPageIndex) {
            for(int i=startPageIndex;i<=endPageIndex;i++) {
                paginationButtons.add(makeRadioButton(itemsPerPage, currentPageIndex, i-1));
            }            
        }
 
        if(currentPageIndex<maxPageIndex) {
            paginationButtons.add(makePNRadioButton(itemsPerPage, currentPageIndex+1, "Siguiente"));
        }

        box.removeAll(); 
        ButtonGroup bg = new ButtonGroup();
        box.add(Box.createHorizontalGlue());
        for(JRadioButton r:paginationButtons) {
            box.add(r); bg.add(r);
        }
        box.add(Box.createHorizontalGlue());       
        box.revalidate();
        box.repaint();
        paginationButtons.clear();        
    }

    private JRadioButton makeRadioButton(final int itemsPerPage, final int current, final int target) {
        JRadioButton radio = new JRadioButton(""+(target+1)) {
            @Override protected void fireStateChanged() {
                ButtonModel btnModel = getModel();
                if(!btnModel.isEnabled()) {
                    setForeground(Color.GRAY);
                }else if(btnModel.isPressed() && btnModel.isArmed()) {
                    setForeground(Color.GREEN);
                }else if(btnModel.isSelected()) {
                    setForeground(Color.RED);
                //}else if(isRolloverEnabled() && model.isRollover()) {
                //    setForeground(Color.BLUE);
                }
                super.fireStateChanged();
            }
        };
        radio.setForeground(Color.BLUE);
        radio.setUI(lnkRadioBtn);
        if(target+1==current) {
            radio.setSelected(true);
        }
        radio.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                initLinkBox(itemsPerPage, target+1);
            }
        });
        return radio;
    }
    
    private JRadioButton makePNRadioButton(final int itemsPerPage, final int target, String title) {
        JRadioButton radio = new JRadioButton(title);
        radio.setForeground(Color.BLUE);
        radio.setUI(lnkRadioBtn);
        radio.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                initLinkBox(itemsPerPage, target);
            }
        });
        return radio;
    }
    
    private RowFilter<TableModel,Integer> makeRowFilter(final int itemsPerPage, final int target) {
        return new RowFilter<TableModel,Integer>() {
            @Override public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                int ei = entry.getIdentifier();
                return (target*itemsPerPage<=ei && ei<target*itemsPerPage+itemsPerPage);
            }
        };
    }  
    
}
