/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author kevin
 */
public class validarCeldaNumero extends DefaultTableCellRenderer{
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    
        if(Integer.parseInt(value.toString()) < 1){
            setBackground(Color.yellow);
            setForeground(Color.black);
        }else{
            setBackground(Color.white);
            setForeground(Color.black);
        }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
    }
    
}//class
