/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controls.tableModifiers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * not in use yet
 * @author Γρηγόρης
 */
public class SearchMatchFontColor extends DefaultTableCellRenderer {

    private String regex;

    public SearchMatchFontColor(String regex) {
        this.regex = regex;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        String text = (String) value;
 
        JTextArea cellText;
        cellText = new JTextArea(text);
        cellText.setWrapStyleWord(true);                      
        cellText.setLineWrap(true);
        cellText.setFont(table.getFont());
        
        if(text.contains(regex))
        {  
            cellText.setForeground(Color.red);
        }
        else
        {
            cellText.setForeground(Color.black);
        }
        return cellText;
    }
}
