/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 *
 * @author kevin
 */
public class StringOnlyDigitField extends JTextField {

    StringOnlyDigitField (){

         this.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
                char caracter = e.getKeyChar();
                if(((caracter < '0') || (caracter > '9')) && caracter!=KeyEvent.VK_BACK_SPACE){
                    e.consume();
                }
            }
          });

     
    }
}
