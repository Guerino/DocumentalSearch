package view.component;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import javax.swing.text.View;

/**
 *
 * @author
 * Guerino
 */
public class LinkViewRadioButtonUI extends BasicRadioButtonUI {

    public LinkViewRadioButtonUI() {
        super();
    }
    

    @Override public Icon getDefaultIcon() {
        return null;
    }
    
    private static Dimension size = new Dimension();
    private static Rectangle viewRect = new Rectangle();
    private static Rectangle iconRect = new Rectangle();
    private static Rectangle textRect = new Rectangle();
    
    @Override public synchronized void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
        Font f = c.getFont();
        g.setFont(f);
        FontMetrics fm = c.getFontMetrics(f);

        Insets i = c.getInsets();
        size = b.getSize(size);
        viewRect.x = i.left;
        viewRect.y = i.top;
        viewRect.width = size.width - (i.right + viewRect.x);
        viewRect.height = size.height - (i.bottom + viewRect.y);
        iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;
        textRect.x = textRect.y = textRect.width = textRect.height = 0;

        String text = SwingUtilities.layoutCompoundLabel(
            c, fm, b.getText(), null, //altIcon != null ? altIcon : getDefaultIcon(),
            b.getVerticalAlignment(), b.getHorizontalAlignment(),
            b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
            viewRect, iconRect, textRect,
            0); //b.getText() == null ? 0 : b.getIconTextGap());

        if(c.isOpaque()) {
            g.setColor(b.getBackground());
            g.fillRect(0,0, size.width, size.height);
        }
        if(text==null) return;
//         // Changing Component State During Painting (an infinite repaint loop)
//         // pointed out by Peter
//         // -note: http://today.java.net/pub/a/today/2007/08/30/debugging-swing.html#changing-component-state-during-the-painting
//         //b.setForeground(Color.BLUE);
//         if(!model.isEnabled()) {
//             //b.setForeground(Color.GRAY);
//         }else if(model.isPressed() && model.isArmed() || model.isSelected()) {
//             //b.setForeground(Color.BLACK);
//         }else if(b.isRolloverEnabled() && model.isRollover()) {

        g.setColor(b.getForeground());
        if(!model.isSelected() && !model.isPressed() && !model.isArmed()
           && b.isRolloverEnabled() && model.isRollover()) {
            g.drawLine(viewRect.x,                viewRect.y+viewRect.height,
                       viewRect.x+viewRect.width, viewRect.y+viewRect.height);
        }
        View v = (View) c.getClientProperty(BasicHTML.propertyKey);
        if(v!=null) {
            v.paint(g, textRect);
        }else{
            paintText(g, b, textRect, text);
        }
    }
}