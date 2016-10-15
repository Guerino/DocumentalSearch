/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.component;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author
 * Guerino
 */
public class LinkRendererCell extends DefaultTableCellRenderer implements MouseListener, MouseMotionListener{
        private int row = -1;
        private int col = -1;
        private boolean isRollover = false;

        @Override
          public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
              boolean hasFocus, int row, int column) {
            
                super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
                String str = value!=null?value.toString():"";
                
                if(!table.isEditing() && this.row==row && this.col==column && this.isRollover) {
                    setText("<html><u><font color='black'>"+str+"</font></u></html>");              
                }else if(hasFocus) {
                    setText("<html><a href='#'><font color='white'>"+str+"</font></a></html>");
                   
                }else{                                     
                    setText("<html><font color='blue'>"+str+"</font></html>");
                    
                }

            return this;
         }
        
        @Override 
        public void mouseMoved(MouseEvent e) {
            JTable table = (JTable)e.getSource();
            Point pt = e.getPoint();
            int prev_row = row;
            int prev_col = col;
            boolean prev_ro = isRollover;
            row = table.rowAtPoint(pt);
            col = table.columnAtPoint(pt);
            Rectangle rc = table.getCellRect(row, col, false);
            if(rc.contains(pt.getLocation())){
                isRollover = true;
//                table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            else {
                isRollover = false;
//                table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            
            if((row==prev_row && col==prev_col && isRollover==prev_ro) || (!isRollover && !prev_ro)) {
                return;
            }

            Rectangle repaintRect;
            if(isRollover) {                
                Rectangle r = table.getCellRect(row, col, false);
                repaintRect = prev_ro ? r.union(table.getCellRect(prev_row, prev_col, false)) : r;
            }else{ //if(prev_ro) {
                
                repaintRect = table.getCellRect(prev_row, prev_col, false);
            }
            table.repaint(repaintRect);
        }

        @Override public void mouseClicked(MouseEvent e) {}
        @Override public void mouseDragged(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {
            JTable table = (JTable)e.getSource();                
            table.repaint(table.getCellRect(row, col, false));
            row = -1;
            col = -1;
            isRollover = false;            
        }
}    
